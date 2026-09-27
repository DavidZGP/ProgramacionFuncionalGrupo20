import java.util.List;
import java.util.Map;

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
}
