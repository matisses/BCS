package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jguisao
 */
public class PedidoDTO {

    private String nroPedido;
    private String formateoFechaPedido;
    private String ordenVenta;
    private boolean devolucion;
    private boolean despachado;
    private BigDecimal totalPedido;

    public PedidoDTO() {
    }

    public PedidoDTO(Object[] parametros) {
        this.nroPedido = (String) parametros[0];
        this.formateoFechaPedido = new SimpleDateFormat("dd | MM | yyyy").format((Date) parametros[1]);
        this.ordenVenta = (String) parametros[2];
        this.devolucion = (boolean) parametros[3];
        this.despachado = (boolean) parametros[4];
        this.totalPedido = (BigDecimal) parametros[5];
    }

    public String getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(String nroPedido) {
        this.nroPedido = nroPedido;
    }

    public String getFormateoFechaPedido() {
        return formateoFechaPedido;
    }

    public void setFormateoFechaPedido(String formateoFechaPedido) {
        this.formateoFechaPedido = formateoFechaPedido;
    }

    public String getOrdenVenta() {
        return ordenVenta;
    }

    public void setOrdenVenta(String ordenVenta) {
        this.ordenVenta = ordenVenta;
    }

    public boolean isDevolucion() {
        return devolucion;
    }

    public void setDevolucion(boolean devolucion) {
        this.devolucion = devolucion;
    }

    public boolean isDespachado() {
        return despachado;
    }

    public void setDespachado(boolean despachado) {
        this.despachado = despachado;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}