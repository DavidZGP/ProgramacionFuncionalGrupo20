import java.time.Duration;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * // Módulo funcional de procesamiento y agregación de datos diarios
 *
 * Principios aplicados en TODA la clase:
 * - Funciones puras: cada método solo depende de sus argumentos y siempre
 * devuelve el mismo resultado para la misma entrada. Ninguno modifica
 * el List<RegistroTransporte> recibido ni ningún estado externo/estático.
 * - Inmutabilidad: las listas y mapas de entrada NUNCA se alteran; los
 * resultados se devuelven en colecciones inmutables
 * (Collectors.toUnmodifiableXxx / List.copyOf), de modo que quien las
 * reciba tampoco pueda mutarlas por accidente.
 * - Programación declarativa: no hay bucles for/while ni contadores
 * mutables; todo se expresa como pipelines de Stream (filter, map,
 * groupingBy, reduce, sorted, collect...).
 * - Paralelización: como no hay estado compartido mutable ni efectos
 * secundarios, cualquier stream() de este archivo puede cambiarse por
 * parallelStream() sin alterar el resultado (ver comentario en
 * calcularAfluenciaPorEstacion).
 */
public final class ProcesadorTransporte {

    // Clase de utilidades: no debe instanciarse.
    private ProcesadorTransporte() {
    }

    // a) Cálculo de afluencia por estación

    // Cuenta cuántos usuarios ingresan ("entrada") a cada estación.

        public static Map<String, Long> calcularAfluenciaPorEstacion(
               List<RegistroTransporte> registros) {

            return registros.stream()
           .filter(r -> "entrada".equals(r.accion()))
            .collect(Collectors.collectingAndThen(
                    Collectors.groupingBy(
                            RegistroTransporte::estacion,
                            Collectors.counting()),
                    Map::copyOf));
        }
    // b) Identificación de horas pico

    // Agrupa todos los registros (entradas y salidas) por hora del día
    public static Map<Integer, Long> contarRegistrosPorHora(List<RegistroTransporte> registros) {
        return registros.stream()
                .collect(Collectors.groupingBy(
                        r -> r.timestamp().getHour(),
                        Collectors.counting()));
    }

    // A partir del conteo por hora, determina la hora pico.
    // Recibe el resultado de contarRegistrosPorHora,
    // lo que permite reutilizar el cálculo si ya se ha realizado.
    public static int identificarHoraPico(Map<Integer, Long> registrosPorHora) {
        return registrosPorHora.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    // c) Rutas más utilizadas
    // Cuenta los usos de cada ruta

    public static List<Map.Entry<String, Long>> calcularRutasMasUtilizadas(List<RegistroTransporte> registros) {
        Map<String, Long> conteoPorRuta = registros.stream()
                .filter(r -> "entrada".equals(r.accion()))
                .collect(Collectors.groupingBy(
                        RegistroTransporte::ruta,
                        Collectors.counting()));

        List<Map.Entry<String, Long>> ordenado = conteoPorRuta.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toList());

        return List.copyOf(ordenado);
    }

    // d) Patrones de viaje por usuario

    // Para cada usuario, genera la secuencia de estaciones visitadas en el
    // orden cronológico en que ocurrieron sus registros.

    public static Map<String, List<String>> generarPatronesDeViaje(List<RegistroTransporte> registros) {
        Map<String, List<String>> patrones = registros.stream()
                .sorted(Comparator.comparing(RegistroTransporte::timestamp))
                .collect(Collectors.groupingBy(
                        RegistroTransporte::idUsuario,
                        LinkedHashMap::new,
                        Collectors.mapping(RegistroTransporte::estacion, Collectors.toList())));

        // Se devuelve un mapa inmutable, y cada lista interna también inmutable.
        Map<String, List<String>> inmutable = new LinkedHashMap<>();
        patrones.forEach((usuario, estaciones) -> inmutable.put(usuario, List.copyOf(estaciones)));
        return java.util.Collections.unmodifiableMap(inmutable);
    }
    // e) Cálculo de tiempo promedio entre estaciones

    // Función pura auxiliar: dado el histórico YA ordenado cronológicamente de un
    // solo usuario, calcula el promedio (en minutos) del tiempo
    // transcurrido entre estaciones consecutivas.
    // Se expone como Function<> para dejar explícito que es una función de
    // primera clase, reutilizable como lambda en otros pipelines.

    public static final Function<List<RegistroTransporte>, Double> tiempoPromedioMinutos = registrosUsuario -> {
        List<RegistroTransporte> ordenados = registrosUsuario.stream()
                .sorted(Comparator.comparing(RegistroTransporte::timestamp))
                .toList();

        return java.util.stream.IntStream.range(1, ordenados.size())
                .mapToLong(i -> Duration.between(
                        ordenados.get(i - 1).timestamp(),
                        ordenados.get(i).timestamp()).toMinutes())
                .average()
                .orElse(0.0);
    };

    // Aplica tiempoPromedioMinutos a cada usuario del conjunto de registros.

    public static Map<String, Double> calcularTiempoPromedioPorUsuario(List<RegistroTransporte> registros) {
        Map<String, List<RegistroTransporte>> porUsuario = registros.stream()
                .collect(Collectors.groupingBy(RegistroTransporte::idUsuario));

        return porUsuario.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        entry -> tiempoPromedioMinutos.apply(entry.getValue())));
    }

    // f) Detección de sobrecarga en rutas

    // Marca cada ruta como "crítica" si su número de usos (entradas) supera
    // el umbral dado, o "normal" en caso contrario.

    public static Map<String, String> detectarRutasSobrecargadas(List<RegistroTransporte> registros, long umbral) {
        return calcularRutasMasUtilizadas(registros).stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() > umbral ? "crítica" : "normal"));
    }

    // Orquestación: construye el informe diario completo

    // Combina todas las tareas anteriores en un único InformeDiario
    // inmutable.
    public static InformeDiario generarInformeDiario(List<RegistroTransporte> registros, long umbralSobrecarga) {
        Map<String, Long> afluencia = calcularAfluenciaPorEstacion(registros);
        Map<Integer, Long> porHora = contarRegistrosPorHora(registros);
        int horaPico = identificarHoraPico(porHora);
        List<Map.Entry<String, Long>> rutas = calcularRutasMasUtilizadas(registros);
        Map<String, List<String>> patrones = generarPatronesDeViaje(registros);
        Map<String, Double> tiempos = calcularTiempoPromedioPorUsuario(registros);
        Map<String, String> estadoRutas = detectarRutasSobrecargadas(registros, umbralSobrecarga);

        return new InformeDiario(afluencia, porHora, horaPico, rutas, patrones, tiempos, estadoRutas);
    }
}
