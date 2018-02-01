package co.matisses.bcs.dto;

/**
 *
 * @author jguisao
 */
public class ResponseDTO {

    private int estado;
    private Integer valor;
    private String mensaje;

    public ResponseDTO() {
    }

    public ResponseDTO(int estado) {
        this.estado = estado;
    }

    public ResponseDTO(int estado, Object object) {
        this.estado = estado;
        if (object != null) {
            if (object instanceof String) {
                this.mensaje = (String) object;
            } else {
                this.valor = (Integer) object;
            }
        }
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
