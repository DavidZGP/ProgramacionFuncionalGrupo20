import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<RegistroTransporte> registros = List.of(
                new RegistroTransporte("U001", "R01", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 7, 15)),
                new RegistroTransporte("U001", "R01", "Norte", "salida",
                        LocalDateTime.of(2026, 9, 26, 7, 35)),
                new RegistroTransporte("U002", "R01", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 8, 10)),
                new RegistroTransporte("U002", "R02", "Sur", "salida",
                        LocalDateTime.of(2026, 9, 26, 8, 40)),
                new RegistroTransporte("U003", "R02", "Centro", "entrada",
                        LocalDateTime.of(2026, 9, 26, 8, 20)));

        long umbralSobrecarga = 1;
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
