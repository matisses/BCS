package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class CrearListaResponseDTO {

    private int codigo;
    private String mensaje;
    private String idLista;

    public CrearListaResponseDTO() {
    }

    public CrearListaResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public CrearListaResponseDTO(int codigo, String mensaje, String idLista) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.idLista = idLista;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setIdLista(String idLista) {
        this.idLista = idLista;
    }

    public String getIdLista() {
        return idLista;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
