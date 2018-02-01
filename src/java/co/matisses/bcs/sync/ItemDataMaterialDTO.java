package co.matisses.bcs.sync;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author dbotero
 */
public class ItemDataMaterialDTO extends MongoDataDTO<ItemDataMaterialDTO>{

    private String id;
    private String code;
    private String name;
    private String cares;
    private Boolean active;

    public ItemDataMaterialDTO() {
        super(ItemDataMaterialDTO.class);
    }

    public ItemDataMaterialDTO(Document doc) {
        super(ItemDataMaterialDTO.class);
        this.id = doc.get("_id", ObjectId.class).toString();
        this.code = doc.get("code", String.class);
        this.name = doc.get("name", String.class);
        this.cares = doc.get("cares", String.class);
        this.active = doc.get("active", Boolean.class);
    }

    public ItemDataMaterialDTO(String id, String code, String name, String cares) {
        super(ItemDataMaterialDTO.class);
        this.id = id;
        this.code = code;
        this.name = name;
        this.cares = cares;
        this.active = true;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCares() {
        return cares;
    }

    public void setCares(String cares) {
        this.cares = cares;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return this.toDocument().toJson();
        //return "ItemDataMaterialDTO{" + "id=" + id + ", code=" + code + ", name=" + name + ", cares=" + cares + ", active=" + active + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.code);
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
        final ItemDataMaterialDTO other = (ItemDataMaterialDTO) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }
}
