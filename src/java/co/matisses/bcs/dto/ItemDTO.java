package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author ygil
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {

    private Integer selectedQuantity;
    private Integer availableStock;
    private Integer descuento;
    private Integer precio;
    private Double nuevoPrecio;
    private String itemcode;
    private boolean sinSaldo = false;

    public ItemDTO() {
    }

    public ItemDTO(Integer selectedQuantity, String itemcode) {
        this.selectedQuantity = selectedQuantity;
        this.itemcode = itemcode;
    }

    public Integer getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(Integer selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    @JsonProperty("availablestock")
    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    @JsonProperty("priceaftervat")
    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Double getNuevoPrecio() {
        return nuevoPrecio;
    }

    public void setNuevoPrecio(Double nuevoPrecio) {
        this.nuevoPrecio = nuevoPrecio;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public boolean isSinSaldo() {
        return sinSaldo;
    }

    public void setSinSaldo(boolean sinSaldo) {
        this.sinSaldo = sinSaldo;
    }

    @Override
    public String toString() {
        return "ItemDTO{" + "selectedQuantity=" + selectedQuantity + ", availableStock=" + availableStock + ", descuento=" + descuento + ", precio=" + precio + ", itemcode=" + itemcode + ", sinSaldo=" + sinSaldo + '}';
    }
}
