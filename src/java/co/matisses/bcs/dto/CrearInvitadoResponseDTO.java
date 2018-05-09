package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;

/**
 *
 * @author jguisao
 */
public class CrearInvitadoResponseDTO {

    private int codigo;
    private String mensaje;
    private String idInvitado;

    public CrearInvitadoResponseDTO() {
    }

    public CrearInvitadoResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getIdInvitado() {
        return idInvitado;
    }

    public void setIdInvitado(String idInvitado) {
        this.idInvitado = idInvitado;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}