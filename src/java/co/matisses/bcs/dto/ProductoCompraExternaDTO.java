package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;

/**
 *
 * @author jguisao
 */
public class ProductoCompraExternaDTO {

    private String referencia;
    private Integer cantidad;

    public ProductoCompraExternaDTO() {
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}