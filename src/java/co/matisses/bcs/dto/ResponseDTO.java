package co.matisses.bcs.dto;

/**
 *
 * @author jguisao
 */
public class ResponseDTO {

    private int estado;
    private String mensaje;

    public ResponseDTO() {
    }

    public ResponseDTO(int estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
