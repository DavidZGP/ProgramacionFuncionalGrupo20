import java.time.LocalDateTime;

public final class RegistroTransporte {

    private final String idUsuario;
    private final String ruta;
    private final String estacion;
    private final String accion;
    private final LocalDateTime timestamp;

    public RegistroTransporte(String idUsuario, String ruta, String estacion,
            String accion, LocalDateTime timestamp) {
        this.idUsuario = idUsuario;
        this.ruta = ruta;
        this.estacion = estacion;
        this.accion = accion;
        this.timestamp = timestamp;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getRuta() {
        return ruta;
    }

    public String getEstacion() {
        return estacion;
    }

    public String getAccion() {
        return accion;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String idUsuario() {
        return idUsuario;
    }

    public String ruta() {
        return ruta;
    }

    public String estacion() {
        return estacion;
    }

    public String accion() {
        return accion;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "RegistroTransporte{" +
                "idUsuario='" + idUsuario + '\'' +
                ", ruta='" + ruta + '\'' +
                ", estacion='" + estacion + '\'' +
                ", accion='" + accion + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}