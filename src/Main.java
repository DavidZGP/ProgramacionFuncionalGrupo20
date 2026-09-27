import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<RegistroTransporte> registros = List.of(
                new RegistroTransporte("U001", "R01", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 7, 10)),
                new RegistroTransporte("U001", "R01", "Norte", "salida",
                        LocalDateTime.of(2026, 9, 26, 7, 30)),
                new RegistroTransporte("U001", "R02", "Sur", "entrada",
                        LocalDateTime.of(2026, 9, 26, 8, 00)),
                new RegistroTransporte("U001", "R02", "Centro", "salida",
                        LocalDateTime.of(2026, 9, 26, 8, 25)),

                new RegistroTransporte("U002", "R01", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 7, 20)),
                new RegistroTransporte("U002", "R01", "Norte", "salida",
                        LocalDateTime.of(2026, 9, 26, 7, 45)),
                new RegistroTransporte("U002", "R03", "Sur", "entrada",
                        LocalDateTime.of(2026, 9, 26, 9, 00)),
                new RegistroTransporte("U002", "R03", "Centro", "salida",
                        LocalDateTime.of(2026, 9, 26, 9, 35)),

                new RegistroTransporte("U003", "R02", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 8, 15)),
                new RegistroTransporte("U003", "R02", "Sur", "salida",
                        LocalDateTime.of(2026, 9, 26, 8, 40)),
                new RegistroTransporte("U003", "R01", "Norte", "entrada",
                        LocalDateTime.of(2026, 9, 26, 10, 05)),
                new RegistroTransporte("U003", "R01", "Centro", "salida",
                        LocalDateTime.of(2026, 9, 26, 10, 30)),

                new RegistroTransporte("U004", "R01", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 7, 35)),
                new RegistroTransporte("U004", "R01", "Norte", "salida",
                        LocalDateTime.of(2026, 9, 26, 8, 00)),
                new RegistroTransporte("U005", "R02", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 8, 25)),
                new RegistroTransporte("U005", "R02", "Sur", "salida",
                        LocalDateTime.of(2026, 9, 26, 8, 55)),

                new RegistroTransporte("U006", "R01", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 9, 10)),
                new RegistroTransporte("U006", "R01", "Norte", "salida",
                        LocalDateTime.of(2026, 9, 26, 9, 40)),
                new RegistroTransporte("U007", "R03", "Sur", "entrada",
                        LocalDateTime.of(2026, 9, 26, 10, 20)),
                new RegistroTransporte("U008", "R01", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 11, 00)));

        long umbralSobrecarga = 3;
        InformeDiario informe = ProcesadorTransporte.generarInformeDiario(
                registros, umbralSobrecarga);

        System.out.println("Afluencia por estación: " + informe.afluenciaPorEstacion());
        System.out.println("Registros por hora: " + informe.registrosPorHora());
        System.out.println("Hora pico: " + informe.horaPico());
        System.out.println("Rutas más utilizadas: " + informe.rutasMasUtilizadas());
        System.out.println("Patrones de viaje: " + informe.patronesDeViajePorUsuario());
        System.out.println("Tiempo promedio (minutos): "
                + informe.tiempoPromedioMinutosPorUsuario());
        System.out.println("Estado de rutas: " + informe.estadoDeRutas());
    }
}
