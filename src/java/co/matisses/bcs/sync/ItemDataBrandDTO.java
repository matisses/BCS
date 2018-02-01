package co.matisses.bcs.sync;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author dbotero
 */
public class ItemDataBrandDTO extends MongoDataDTO<ItemDataBrandDTO>{

    private String id;
    private String code;
    private String name;
    private Boolean active;

    public ItemDataBrandDTO() {
        super(ItemDataBrandDTO.class);
    }

    public ItemDataBrandDTO(Document doc) {
        super(ItemDataBrandDTO.class);
        this.id = doc.get("_id", ObjectId.class).toString();
        this.code = doc.get("code", String.class);
        this.name = doc.get("name", String.class);
        this.active = true;
    }

    public ItemDataBrandDTO(String id, String code, String name) {
        super(ItemDataBrandDTO.class);
        this.id = id;
        this.code = code;
        this.name = name;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ItemDataBrandDTO{" + "id=" + id + ", code=" + code + ", name=" + name + ", active=" + active + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.code);
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
        final ItemDataBrandDTO other = (ItemDataBrandDTO) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

}
