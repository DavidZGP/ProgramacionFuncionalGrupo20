import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Contenedor inmutable con el resultado de todas las tareas
public record InformeDiario(
        Map<String, Long> afluenciaPorEstacion,       
        Map<Integer, Long> registrosPorHora,           
        int horaPico,
        List<Map.Entry<String, Long>> rutasMasUtilizadas, 
        Map<String, List<String>> patronesDeViajePorUsuario,
        Map<String, Double> tiempoPromedioMinutosPorUsuario,
        Map<String, String> estadoDeRutas               
) {
    public InformeDiario {
        afluenciaPorEstacion = Map.copyOf(afluenciaPorEstacion);
        registrosPorHora = Map.copyOf(registrosPorHora);
        rutasMasUtilizadas = rutasMasUtilizadas.stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue()))
                .toList();
        patronesDeViajePorUsuario = patronesDeViajePorUsuario.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        entry -> List.copyOf(entry.getValue())));
        tiempoPromedioMinutosPorUsuario = Map.copyOf(tiempoPromedioMinutosPorUsuario);
        estadoDeRutas = Map.copyOf(estadoDeRutas);
    }
}
