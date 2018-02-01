package co.matisses.bcs.sync;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author dbotero
 */
public class ItemDataColorDTO extends MongoDataDTO<ItemDataColorDTO>{

    private String id;
    private String code;
    private String name;
    private String hexa;
    private Boolean active;
    private ItemDataGenericColorDTO genericColor;

    public ItemDataColorDTO() {
        super(ItemDataColorDTO.class);
    }

    public ItemDataColorDTO(Document doc) {
        super(ItemDataColorDTO.class);
        this.id = doc.get("_id", ObjectId.class).toString();
        this.code = doc.get("code", String.class);
        this.name = doc.get("name", String.class);
        this.hexa = doc.get("hexa", String.class);
        this.active = doc.get("active", Boolean.class);
        if(doc.get("genericcolor", Document.class) != null){
            this.genericColor = new ItemDataGenericColorDTO(doc.get("genericcolor", Document.class));
        }
    }

    public ItemDataColorDTO(String id, String code, String name, String hexa, ItemDataGenericColorDTO genericColor) {
        super(ItemDataColorDTO.class);
        this.id = id;
        this.code = code;
        this.name = name;
        this.hexa = hexa;
        this.genericColor = genericColor;
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

    public String getHexa() {
        return hexa;
    }

    public void setHexa(String hexa) {
        this.hexa = hexa;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ItemDataGenericColorDTO getGenericColor() {
        return genericColor;
    }

    public void setGenericColor(ItemDataGenericColorDTO genericColor) {
        this.genericColor = genericColor;
    }

    @Override
    public String toString() {
        return "ItemDataColorDTO{" + "id=" + id + ", code=" + code + ", name=" + name + ", hexa=" + hexa + ", active=" + active + ", genericColor=" + genericColor + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final ItemDataColorDTO other = (ItemDataColorDTO) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }
}
