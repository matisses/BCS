package co.matisses.bcs.sync;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author dbotero
 */
public class ItemDataStockDTO extends MongoDataDTO<ItemDataStockDTO> {

    private String id;
    private String itemCode;
    private String whsCode;
    private String whsName;
    private Integer quantity;

    public ItemDataStockDTO() {
        super(ItemDataStockDTO.class);
    }

    public ItemDataStockDTO(Document doc) {
        super(ItemDataStockDTO.class);
        this.id = doc.get("_id", ObjectId.class).toString();
        this.itemCode = doc.getString("itemCode");
        this.whsCode = doc.getString("whsCode");
        this.whsName = doc.getString("whsName");
        this.quantity = doc.getInteger("quantity");
    }

    public ItemDataStockDTO(String id, String itemCode, String whsCode, String whsName, Integer quantity) {
        super(ItemDataStockDTO.class);
        this.id = id;
        this.itemCode = itemCode;
        this.whsCode = whsCode;
        this.whsName = whsName;
        this.quantity = quantity;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    public String getWhsName() {
        return whsName;
    }

    public void setWhsName(String whsName) {
        this.whsName = whsName;
    }

    public void addStock(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public String toString() {
        return "ItemDataStockDTO{" + "id=" + id + ", itemCode=" + itemCode + ", whsCode=" + whsCode + ", whsName=" + whsName + ", quantity=" + quantity + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.whsCode);
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
        final ItemDataStockDTO other = (ItemDataStockDTO) obj;
        if (!Objects.equals(this.whsCode, other.whsCode)) {
            return false;
        }
        return true;
    }

}
