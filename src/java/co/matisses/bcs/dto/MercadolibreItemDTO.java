package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class MercadolibreItemDTO {

    private String title;
    private String categoryId;
    private int price;
    private String currencyId;
    private int availableQuantity;
    private String buyingMode;
    private String listingType;
    private String condition;
    private String description;
    private String officialStoreId;
    private String warranty;
    private String status;
    private List<Picture> pictures;
    private List<Attribute> attributes;
    private Shipping shipping;

    public MercadolibreItemDTO() {
        pictures = new ArrayList<>();
        attributes = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("category_id")
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @JsonProperty("official_store_id")
    public String getOfficialStoreId() {
        return officialStoreId;
    }

    public void setOfficialStoreId(String officialStoreId) {
        this.officialStoreId = officialStoreId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonProperty("currency_id")
    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    @JsonProperty("available_quantity")
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @JsonProperty("buying_mode")
    public String getBuyingMode() {
        return buyingMode;
    }

    public void setBuyingMode(String buyingMode) {
        this.buyingMode = buyingMode;
    }

    @JsonProperty("listing_type_id")
    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
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

    public static class Shipping {

        private String mode;
        private Boolean freeShipping;
        private Boolean localPickUp;
        private List<FreeMethod> freeMethods;

        public Shipping() {
            freeMethods = new ArrayList<>();
        }

        @JsonProperty("free_methods")
        public List<FreeMethod> getFreeMethods() {
            return freeMethods;
        }

        public void setFreeMethods(List<FreeMethod> freeMethods) {
            this.freeMethods = freeMethods;
        }

        @JsonProperty("free_shipping")
        public Boolean getFreeShipping() {
            return freeShipping;
        }

        public void setFreeShipping(Boolean freeShipping) {
            this.freeShipping = freeShipping;
        }

        @JsonProperty("local_pick_up")
        public Boolean getLocalPickUp() {
            return localPickUp;
        }

        public void setLocalPickUp(Boolean localPickUp) {
            this.localPickUp = localPickUp;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public static class FreeMethod {

            private Long id;
            private Rule rule;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Rule getRule() {
                return rule;
            }

            public void setRule(Rule rule) {
                this.rule = rule;
            }

            public static class Rule {

                private Boolean def;
                private String freeMode;
                private Boolean freeShippingFlag;

                @JsonProperty("default")
                public Boolean getDef() {
                    return def;
                }

                public void setDef(Boolean def) {
                    this.def = def;
                }

                @JsonProperty("free_mode")
                public String getFreeMode() {
                    return freeMode;
                }

                public void setFreeMode(String freeMode) {
                    this.freeMode = freeMode;
                }

                @JsonProperty("free_shipping_flag")
                public Boolean getFreeShippingFlag() {
                    return freeShippingFlag;
                }

                public void setFreeShippingFlag(Boolean freeShippingFlag) {
                    this.freeShippingFlag = freeShippingFlag;
                }
            }
        }
    }

    public static class Attribute {

        private String id;
        private String valueName;

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