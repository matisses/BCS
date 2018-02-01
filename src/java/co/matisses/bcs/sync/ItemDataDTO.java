package co.matisses.bcs.sync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDataDTO extends MongoDataDTO<ItemDataDTO> {

    private String id;
    private String itemCode;
    private String shortItemCode;
    private String itemName;
    private String description;
    private String model;
    private String newFrom;
    private String collection;
    private String urlMercadolibre;
    private Double priceBeforeVAT;
    private Double priceAfterVAT;
    private Double taxPercent;
    private Integer availableStock;
    private BasicDTO department;
    private BasicDTO group;
    private BasicDTO subgroup;
    private Integer weight;
    private Boolean showPrice;
    private Boolean discount;
    private ItemDataDimensionsDTO dimensions;
    private ItemDataColorDTO color;
    private ItemDataBrandDTO brand;
    private List<ItemDataStockDTO> stock;
    private List<ItemDataMaterialDTO> materials;
    private List<String> images;

    public ItemDataDTO() {
        super(ItemDataDTO.class);
        stock = new ArrayList<>();
        materials = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public ItemDataDTO(Document doc) {
        super(ItemDataDTO.class);
        this.id = doc.get("_id", ObjectId.class).toString();
        this.itemCode = doc.get("itemcode").toString();
        this.showPrice = doc.get("showPrice", Boolean.class);
        this.images = new ArrayList<>();
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

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getShortItemCode() {
        return shortItemCode;
    }

    public void setShortItemCode(String shortItemCode) {
        this.shortItemCode = shortItemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(Double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getNewFrom() {
        return newFrom;
    }

    public void setNewFrom(String newFrom) {
        this.newFrom = newFrom;
    }

    public Double getPriceAfterVAT() {
        return priceAfterVAT;
    }

    public void setPriceAfterVAT(Double priceAfterVAT) {
        this.priceAfterVAT = priceAfterVAT;
    }

    public Double getPriceBeforeVAT() {
        return priceBeforeVAT;
    }

    public void setPriceBeforeVAT(Double priceBeforeVAT) {
        this.priceBeforeVAT = priceBeforeVAT;
    }

    public BasicDTO getDepartment() {
        return department;
    }

    public void setDepartment(BasicDTO department) {
        this.department = department;
    }

    public BasicDTO getGroup() {
        return group;
    }

    public void setGroup(BasicDTO group) {
        this.group = group;
    }

    public BasicDTO getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(BasicDTO subgroup) {
        this.subgroup = subgroup;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public ItemDataDimensionsDTO getDimensions() {
        return dimensions;
    }

    public void setDimensions(ItemDataDimensionsDTO dimensions) {
        this.dimensions = dimensions;
    }

    public ItemDataColorDTO getColor() {
        return color;
    }

    public void setColor(ItemDataColorDTO color) {
        this.color = color;
    }

    public ItemDataBrandDTO getBrand() {
        return brand;
    }

    public void setBrand(ItemDataBrandDTO brand) {
        this.brand = brand;
    }

    public List<ItemDataStockDTO> getStock() {
        return stock;
    }

    public void setStock(List<ItemDataStockDTO> stock) {
        this.stock = stock;
    }

    public List<ItemDataMaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<ItemDataMaterialDTO> materials) {
        this.materials = materials;
    }

    public String getUrlMercadolibre() {
        return urlMercadolibre;
    }

    public void setUrlMercadolibre(String urlMercadolibre) {
        this.urlMercadolibre = urlMercadolibre;
    }

    public Boolean getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(Boolean showPrice) {
        this.showPrice = showPrice;
    }

    public void addStock(String whsCode, Integer quantity) {
        ItemDataStockDTO itemStock = new ItemDataStockDTO(null, this.itemCode, whsCode, null, quantity);
        if (stock.contains(itemStock)) {
            stock.get(stock.indexOf(itemStock)).addStock(quantity);
        } else {
            stock.add(itemStock);
        }
    }

    public void addMaterial(ItemDataMaterialDTO material) {
        if (!materials.contains(material)) {
            materials.add(material);
        }
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public boolean containsStockInWarehouse(String whsCode) {
        ItemDataStockDTO itemStock = new ItemDataStockDTO(null, this.itemCode, whsCode, null, 0);
        if (stock.contains(itemStock)) {
            return true;
        } else {
            return false;
        }
    }

    public void mergeStockAndMaterials(ItemDataDTO item) {
        //Merges stock. 
        for (ItemDataStockDTO stck : item.getStock()) {
            if (!this.containsStockInWarehouse(stck.getWhsCode())) {
                this.addStock(stck.getWhsCode(), stck.getQuantity());
            }
        }

        //Merges materials
        for (ItemDataMaterialDTO mat : item.getMaterials()) {
            if (!this.getMaterials().contains(mat)) {
                this.addMaterial(mat);
            }
        }
    }

    @Override
    public String toString() {
        return "ItemDataDTO{" + "id=" + id + ", itemCode=" + itemCode + ", shortItemCode="
                + shortItemCode + ", itemName=" + itemName + ", description=" + description
                + ", model=" + model + ", newFrom=" + newFrom + ", priceAfterVAT=" + priceAfterVAT
                + ", priceBeforeVAT=" + priceBeforeVAT + ", department=" + department + ", group=" + group
                + ", subgroup=" + subgroup + ", weight=" + weight + ", dimensions=" + dimensions
                + ", color=" + color + ", brand=" + brand + ", stock=" + stock + ", availableStock="
                + availableStock + ", taxPercent=" + taxPercent + ", materials=" + materials + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.itemCode);
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
        final ItemDataDTO other = (ItemDataDTO) obj;
        if (!Objects.equals(this.itemCode, other.itemCode)) {
            return false;
        }
        return true;
    }
}