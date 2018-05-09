package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jguisao
 */
public class DetallePedidoDTO {

    private Integer cantidad;
    private Integer devuelto;
    private Integer entregado;
    private String nroPedido;
    private String ordenVenta;
    private String formateoFechaPedido;
    private String cliente;
    private String direccionEntrega;
    private String ciudadEntrega;
    private String departamentoEntrega;
    private String telefono;
    private String celular;
    private String item;
    private String producto;
    private String metodoPago;
    private String estadoItem;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private boolean devolucion;
    private boolean despachado;
    private boolean garantia;

    public DetallePedidoDTO() {
    }

    public DetallePedidoDTO(Object[] parametros) {
        this.nroPedido = (String) parametros[0];
        this.ordenVenta = (String) parametros[1];
        if (parametros[2] != null && !parametros[2].toString().trim().isEmpty() && !((new SimpleDateFormat("yyyy-MM-dd").format((Date) parametros[2])).equals("1900-01-01"))) {
            this.formateoFechaPedido = new SimpleDateFormat("dd | MM | yyyy").format((Date) parametros[2]);
        } else {
            this.formateoFechaPedido = null;
        }
        this.cliente = (String) parametros[3];
        this.direccionEntrega = (String) parametros[4];
        this.ciudadEntrega = (String) parametros[5];
        this.departamentoEntrega = (String) parametros[6];
        this.telefono = (String) parametros[7];
        this.celular = (String) parametros[8];
        this.item = (String) parametros[9];
        this.producto = (String) parametros[10];
        this.metodoPago = (String) parametros[11];
        this.cantidad = (Integer) parametros[12];
        this.devuelto = (Integer) parametros[13];
        this.entregado = (Integer) parametros[14];
        this.estadoItem = (String) parametros[15];
        this.precioUnitario = (BigDecimal) parametros[16];
        this.subtotal = (BigDecimal) parametros[17];
        this.devolucion = (boolean) parametros[18];
        this.despachado = (boolean) parametros[19];
        this.garantia = (boolean) parametros[20];
    }

    public String getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(String nroPedido) {
        this.nroPedido = nroPedido;
    }

    public String getOrdenVenta() {
        return ordenVenta;
    }

    public void setOrdenVenta(String ordenVenta) {
        this.ordenVenta = ordenVenta;
    }

    public String getFormateoFechaPedido() {
        return formateoFechaPedido;
    }

    public void setFormateoFechaPedido(String formateoFechaPedido) {
        this.formateoFechaPedido = formateoFechaPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getCiudadEntrega() {
        return ciudadEntrega;
    }

    public void setCiudadEntrega(String ciudadEntrega) {
        this.ciudadEntrega = ciudadEntrega;
    }

    public String getDepartamentoEntrega() {
        return departamentoEntrega;
    }

    public void setDepartamentoEntrega(String departamentoEntrega) {
        this.departamentoEntrega = departamentoEntrega;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(Integer devuelto) {
        this.devuelto = devuelto;
    }

    public Integer getEntregado() {
        return entregado;
    }

    public void setEntregado(Integer entregado) {
        this.entregado = entregado;
    }

    public String getEstadoItem() {
        return estadoItem;
    }

    public void setEstadoItem(String estadoItem) {
        this.estadoItem = estadoItem;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
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

    public boolean isGarantia() {
        return garantia;
    }

    public void setGarantia(boolean garantia) {
        this.garantia = garantia;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}