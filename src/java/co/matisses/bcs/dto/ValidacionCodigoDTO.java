package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class ValidacionCodigoDTO {

    private ClienteSAPDTO cliente;
    private String codigo;

    public ValidacionCodigoDTO() {
    }

    public void setCliente(ClienteSAPDTO cliente) {
        this.cliente = cliente;
    }

    public ClienteSAPDTO getCliente() {
        return cliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}