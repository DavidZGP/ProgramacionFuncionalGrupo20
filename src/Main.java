import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<RegistroTransporte> registros = GeneradorDatos.generarRegistros(100);

        long umbralSobrecarga = 1;
        InformeDiario informe = ProcesadorTransporte.generarInformeDiario(
                registros, umbralSobrecarga);

        System.out.println("Registros generados: " + registros.size());
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
