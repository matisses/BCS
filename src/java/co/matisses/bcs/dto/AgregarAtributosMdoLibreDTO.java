package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class AgregarAtributosMdoLibreDTO {

    private List<Attribute> attributes;

    public AgregarAtributosMdoLibreDTO() {
        this.attributes = new ArrayList<>();
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public static class Attribute {

        private String id;
        private String valueName;

        public Attribute() {
        }

        public Attribute(String id, String valueName) {
            this.id = id;
            this.valueName = valueName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("value_name")
        public String getValueName() {
            return valueName;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }
    }
}
