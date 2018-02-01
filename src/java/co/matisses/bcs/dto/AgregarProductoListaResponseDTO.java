package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class AgregarProductoListaResponseDTO {

    private int codigo;
    private String mensaje;

    public AgregarProductoListaResponseDTO() {
    }

    public AgregarProductoListaResponseDTO(int codigo, String mensaje) {
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

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}