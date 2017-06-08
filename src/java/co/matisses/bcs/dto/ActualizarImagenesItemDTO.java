package co.matisses.bcs.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class ActualizarImagenesItemDTO {

    private List<Picture> pictures;

    public ActualizarImagenesItemDTO() {
        pictures = new ArrayList<>();
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public void agregarImagen(String source) {
        this.pictures.add(new Picture(source));
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
