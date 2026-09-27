import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public final class GeneradorDatos {

    private static final LocalDateTime INICIO = LocalDateTime.of(2026, 9, 26, 5, 0);
    private static final List<String> RUTAS = List.of("R01", "R02", "R03", "R04", "R05");
    private static final List<String> ESTACIONES = List.of(
            "Centro", "Norte", "Sur", "Este", "Oeste");

    private GeneradorDatos() {
    }

    public static List<RegistroTransporte> generarRegistros(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        return IntStream.range(0, cantidad)
                .mapToObj(indice -> {
                    int viaje = indice / 2;
                    return new RegistroTransporte(
                            "U" + (viaje % 50 + 1),
                            RUTAS.get(viaje % RUTAS.size()),
                            ESTACIONES.get(indice % ESTACIONES.size()),
                            indice % 2 == 0 ? "entrada" : "salida",
                            INICIO.plusMinutes(5L * indice));
                })
                .toList();
    }
}