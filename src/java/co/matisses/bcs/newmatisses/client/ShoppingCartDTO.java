package co.matisses.bcs.newmatisses.client;

import co.matisses.bcs.dto.ItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author ygil
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartDTO {

    private String id;
    private String fechaCreacion;
    private String tiendaRecoge;
    private String metodoEnvio;
    private List<ItemDTO> items;

    public ShoppingCartDTO() {
    }

    public ShoppingCartDTO(String id, String fechaCreacion, String tiendaRecoge, String metodoEnvio, List<ItemDTO> items) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.tiendaRecoge = tiendaRecoge;
        this.metodoEnvio = metodoEnvio;
        this.items = items;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("fechacreacion")
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTiendaRecoge() {
        return tiendaRecoge;
    }

    public void setTiendaRecoge(String tiendaRecoge) {
        this.tiendaRecoge = tiendaRecoge;
    }

    public String getMetodoEnvio() {
        return metodoEnvio;
    }

    public void setMetodoEnvio(String metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ShoppingCartDTO{" + "id=" + id + ", fechaCreacion=" + fechaCreacion + ", tiendaRecoge=" + tiendaRecoge + ", metodoEnvio=" + metodoEnvio + ", items=" + items + '}';
    }
}
