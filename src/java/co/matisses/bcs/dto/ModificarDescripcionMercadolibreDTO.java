package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author dbotero
 */
public class ModificarDescripcionMercadolibreDTO {

    private String text;
    private String plainText;

    public ModificarDescripcionMercadolibreDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("plain_text")
    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }
}
