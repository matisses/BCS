package co.matisses.bcs.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ygil
 */
public class DatosCompraWebDTO {

    private Integer metodoEnvio;
    private String idCarrito;
    private String tiendaRecoge;
    private String ciudadDestino;
    private boolean precioNuevo;
    private List<ItemDTO> items;
    private Long idLista;
    private String message;
    private Integer montoPagar;

    public DatosCompraWebDTO() {
        items = new ArrayList<>();
    }

    public DatosCompraWebDTO(Integer metodoEnvio, String idCarrito, String tiendaRecoge, List<ItemDTO> items) {
        this.metodoEnvio = metodoEnvio;
        this.idCarrito = idCarrito;
        this.tiendaRecoge = tiendaRecoge;
        this.items = items;
    }

    public Integer getMetodoEnvio() {
        return metodoEnvio;
    }

    public void setMetodoEnvio(Integer metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getTiendaRecoge() {
        return tiendaRecoge;
    }

    public void setTiendaRecoge(String tiendaRecoge) {
        this.tiendaRecoge = tiendaRecoge;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public boolean isPrecioNuevo() {
        return precioNuevo;
    }

    public void setPrecioNuevo(boolean precioNuevo) {
        this.precioNuevo = precioNuevo;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
    
    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DatosCompraWebDTO{" + "metodoEnvio=" + metodoEnvio + ", idCarrito=" + idCarrito + ", tiendaRecoge=" + tiendaRecoge + ", ciudadDestino=" + ciudadDestino + ", items=" + items + '}';
    }
    
    public Integer getMontoPagar() {
        return montoPagar;
    }

    public void setMontoPagar(Integer montoPagar) {
        this.montoPagar = montoPagar;
    }
}
