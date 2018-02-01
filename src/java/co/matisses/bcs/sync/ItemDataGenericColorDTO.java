package co.matisses.bcs.sync;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author dbotero
 */
public class ItemDataGenericColorDTO extends MongoDataDTO<ItemDataGenericColorDTO> {

    private String id;
    private String code;
    private String name;

    public ItemDataGenericColorDTO() {
        super(ItemDataGenericColorDTO.class);
    }

    public ItemDataGenericColorDTO(Document doc) {
        super(ItemDataGenericColorDTO.class);
        if (doc != null) {
            this.id = doc.get("_id", ObjectId.class).toString();
            this.code = doc.get("code", String.class);
            this.name = doc.get("name", String.class);
        }
    }

    public ItemDataGenericColorDTO(String id, String code, String name) {
        super(ItemDataGenericColorDTO.class);
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ItemDataGenericColorDTO{" + "id=" + id + ", code=" + code + ", name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.code);
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
        final ItemDataGenericColorDTO other = (ItemDataGenericColorDTO) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }
}
