package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jguisao
 */
public class MercadoLibreItemModificarDTO {

    private String title;
    private String status;
    private int price;
    private int availableQuantity;
    private List<Picture> pictures;

    public MercadoLibreItemModificarDTO(MercadolibrePublicarItemDTO dto) {
        this.title = dto.getTitle();
        this.status = dto.getStatus();
        this.price = dto.getPrice();
        this.availableQuantity = dto.getAvailableQuantity();
        this.pictures = new ArrayList<>();
        for (MercadolibrePublicarItemDTO.Picture p : dto.getPictures()) {
            Picture imagen = new Picture();
            imagen.setSource(p.getSource());
            this.pictures.add(imagen);
        }
    }

    public MercadoLibreItemModificarDTO() {
        pictures = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonProperty("available_quantity")
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public static class Picture {

        private String source;

        public Picture() {
        }

        public Picture(String source) {
            this.source = source;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

}
