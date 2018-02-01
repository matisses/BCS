package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

/**
 *
 * @author jguisao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoListaRegalosDTO {

    private Long idLista;
    private Long idProductoLista;
    private Integer cantidadSeleccionadaFactura = 0;
    private Integer cantidadElegida;
    private Integer cantidadComprada;
    private Integer cantidadEntregada;
    private Integer precio;
    private String referencia;
    private String descripcionProducto;
    private String mensajeAgradecimiento;
    private Boolean favorito;
    private Boolean active;

    public ProductoListaRegalosDTO() {
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public Long getIdProductoLista() {
        return idProductoLista;
    }

    public void setIdProductoLista(Long idProductoLista) {
        this.idProductoLista = idProductoLista;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Integer getCantidadElegida() {
        return cantidadElegida;
    }

    public void setCantidadElegida(Integer cantidadElegida) {
        this.cantidadElegida = cantidadElegida;
    }

    public Integer getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(Integer cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public String getMensajeAgradecimiento() {
        return mensajeAgradecimiento;
    }

    public void setMensajeAgradecimiento(String mensajeAgradecimiento) {
        this.mensajeAgradecimiento = mensajeAgradecimiento;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Integer getCantidadSeleccionadaFactura() {
        return cantidadSeleccionadaFactura;
    }

    public void setCantidadSeleccionadaFactura(Integer cantidadSeleccionadaFactura) {
        this.cantidadSeleccionadaFactura = cantidadSeleccionadaFactura;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idProductoLista);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductoListaRegalosDTO other = (ProductoListaRegalosDTO) obj;
        return Objects.equals(this.idProductoLista, other.idProductoLista);
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}