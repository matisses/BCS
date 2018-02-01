package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author jguisao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoMercadoLibreDTO {

    private String id;
    private String siteId;
    private String title;
    private String subtitle;
    private Long sellerId;
    private String categoryId;
    private String officialStoreId;
    private Long price;
    private Long basePrice;
    private String originalPrice;
    private String currencyId;
    private Integer initialQuantity;
    private Integer availableQuantity;
    private Integer soldQuantity;
    private String buyingMode;
    private String listingTypeId;
    private String startTime;
    private String stopTime;
    private String endTime;
    private String expirationTime;
    private String condition;
    private String permalink;
    private String thumbnail;
    private String secureThumbnail;
    private String videoId;
    private Boolean acceptsMercadoPago;
    private String internationalDeliveryMode;
    private String sellerContact;
    private String listingSource;
    private String status;
    private String warranty;
    private String catalogProductId;
    private String domainId;
    private String sellerCustomField;
    private String parentItemId;
    private String differentialPricing;
    private Boolean automaticRelist;
    private String dateCreated;
    private String lastUpdated;
    private Object location;
    private SellerAddress sellerAddres;
    private Shipping shipping;
    private Geolocation geolocation;
    private List<Picture> pictures;
    private List<Description> descriptions;
    private List<Tags> tags;
    private List<Object> subStatus;
    private List<Object> nonMercadopagoPaymentMethods;
    private List<Object> coverageAreas;
    private List<Object> attributes;
    private List<Object> warnings;
    private List<Object> variations;
    private List<Object> dealIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("site_id")
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @JsonProperty("seller_id")
    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @JsonProperty("base_price")
    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    @JsonProperty("original_price")
    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    @JsonProperty("currency_id")
    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    @JsonProperty("initial_quantity")
    public Integer getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(Integer initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    @JsonProperty("available_quantity")
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @JsonProperty("sold_quantity")
    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    @JsonProperty("buying_mode")
    public String getBuyingMode() {
        return buyingMode;
    }

    public void setBuyingMode(String buyingMode) {
        this.buyingMode = buyingMode;
    }

    @JsonProperty("listing_type_id")
    public String getListingTypeId() {
        return listingTypeId;
    }

    public void setListingTypeId(String listingTypeId) {
        this.listingTypeId = listingTypeId;
    }

    @JsonProperty("start_time")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("stop_time")
    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    @JsonProperty("end_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @JsonProperty("expiration_time")
    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonProperty("secure_thumbnail")
    public String getSecureThumbnail() {
        return secureThumbnail;
    }

    public void setSecureThumbnail(String secureThumbnail) {
        this.secureThumbnail = secureThumbnail;
    }

    @JsonProperty("video_id")
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @JsonProperty("accepts_mercadopago")
    public Boolean getAcceptsMercadoPago() {
        return acceptsMercadoPago;
    }

    public void setAcceptsMercadoPago(Boolean acceptsMercadoPago) {
        this.acceptsMercadoPago = acceptsMercadoPago;
    }

    @JsonProperty("international_delivery_mode")
    public String getInternationalDeliveryMode() {
        return internationalDeliveryMode;
    }

    public void setInternationalDeliveryMode(String internationalDeliveryMode) {
        this.internationalDeliveryMode = internationalDeliveryMode;
    }

    @JsonProperty("seller_contact")
    public String getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    @JsonProperty("listing_source")
    public String getListingSource() {
        return listingSource;
    }

    public void setListingSource(String listingSource) {
        this.listingSource = listingSource;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    @JsonProperty("catalog_product_id")
    public String getCatalogProductId() {
        return catalogProductId;
    }

    public void setCatalogProductId(String catalogProductId) {
        this.catalogProductId = catalogProductId;
    }

    @JsonProperty("domain_id")
    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    @JsonProperty("seller_custom_field")
    public String getSellerCustomField() {
        return sellerCustomField;
    }

    public void setSellerCustomField(String sellerCustomField) {
        this.sellerCustomField = sellerCustomField;
    }

    @JsonProperty("parent_item_id")
    public String getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }

    @JsonProperty("differential_pricing")
    public String getDifferentialPricing() {
        return differentialPricing;
    }

    public void setDifferentialPricing(String differentialPricing) {
        this.differentialPricing = differentialPricing;
    }

    @JsonProperty("automatic_relist")
    public Boolean getAutomaticRelist() {
        return automaticRelist;
    }

    public void setAutomaticRelist(Boolean automaticRelist) {
        this.automaticRelist = automaticRelist;
    }

    @JsonProperty("date_created")
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonProperty("last_updated")
    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    @JsonProperty("seller_address")
    public SellerAddress getSellerAddres() {
        return sellerAddres;
    }

    public void setSellerAddres(SellerAddress sellerAddres) {
        this.sellerAddres = sellerAddres;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    @JsonProperty("non_mercado_pago_payment_methods")
    public List<Object> getNonMercadopagoPaymentMethods() {
        return nonMercadopagoPaymentMethods;
    }

    public void setNonMercadopagoPaymentMethods(List<Object> nonMercadopagoPaymentMethods) {
        this.nonMercadopagoPaymentMethods = nonMercadopagoPaymentMethods;
    }

    @JsonProperty("coverage_areas")
    public List<Object> getCoverageAreas() {
        return coverageAreas;
    }

    public void setCoverageAreas(List<Object> coverageAreas) {
        this.coverageAreas = coverageAreas;
    }

    public List<Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    public List<Object> getVariations() {
        return variations;
    }

    public void setVariations(List<Object> variations) {
        this.variations = variations;
    }

    @JsonProperty("deal_ids")
    public List<Object> getDealIds() {
        return dealIds;
    }

    public void setDealIds(List<Object> dealIds) {
        this.dealIds = dealIds;
    }

    @JsonProperty("sub_status")
    public List<Object> getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(List<Object> subStatus) {
        this.subStatus = subStatus;
    }

    public static class Picture {

        private String id;
        private String url;
        private String secureUrl;
        private String size;
        private String maxSize;
        private String quality;

        public Picture() {
        }

        public Picture(String id, String url, String secureUrl, String size, String maxSize, String quality) {
            this.id = id;
            this.url = url;
            this.secureUrl = secureUrl;
            this.size = size;
            this.maxSize = maxSize;
            this.quality = quality;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @JsonProperty("secure_url")
        public String getSecureUrl() {
            return secureUrl;
        }

        public void setSecureUrl(String secureUrl) {
            this.secureUrl = secureUrl;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        @JsonProperty("max_size")
        public String getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(String maxSize) {
            this.maxSize = maxSize;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }
    }

    public static class Description {

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class Shipping {

        private String mode;
        private String logisticType;
        private String dimensions;
        private Boolean localPickUp;
        private Boolean freeShipping;
        private Boolean storePickUp;
        private List<Object> methods;
        private List<Object> freeMethods;
        private List<Object> tags;

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        @JsonProperty("free_methods")
        public List<Object> getFreeMethods() {
            return freeMethods;
        }

        public void setFreeMethods(List<Object> freeMethods) {
            this.freeMethods = freeMethods;
        }

        @JsonProperty("logistic_type")
        public String getLogisticType() {
            return logisticType;
        }

        public void setLogisticType(String logisticType) {
            this.logisticType = logisticType;
        }

        public String getDimensions() {
            return dimensions;
        }

        public void setDimensions(String dimensions) {
            this.dimensions = dimensions;
        }

        @JsonProperty("local_pick_up")
        public Boolean getLocalPickUp() {
            return localPickUp;
        }

        public void setLocalPickUp(Boolean localPickUp) {
            this.localPickUp = localPickUp;
        }

        @JsonProperty("store_pick_up")
        public Boolean getStorePickUp() {
            return storePickUp;
        }

        public void setStorePickUp(Boolean storePickUp) {
            this.storePickUp = storePickUp;
        }

        @JsonProperty("free_shipping")
        public Boolean getFreeShipping() {
            return freeShipping;
        }

        public void setFreeShipping(Boolean freeShipping) {
            this.freeShipping = freeShipping;
        }

        public List<Object> getMethods() {
            return methods;
        }

        public void setMethods(List<Object> methods) {
            this.methods = methods;
        }

        public List<Object> getTags() {
            return tags;
        }

        public void setTags(List<Object> tags) {
            this.tags = tags;
        }
    }

    public static class SellerAddress {

        private String id;
        private String comment;
        private String addressLine;
        private String zipCode;
        private String latitude;
        private String longitude;
        private City city;
        private State state;
        private Country country;
        private SearchLocation searchLocation;

        public static class City {

            private String id;
            private String name;

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
        }

        public static class State {

            private String id;
            private String name;

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
        }

        public static class Country {

            private String id;
            private String name;

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
        }

        public static class SearchLocation {

            private Neighborhood neighborhood;
            private City city;
            private State state;

            public static class Neighborhood {

                private String id;
                private String name;

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
            }

            public static class City {

                private String id;
                private String name;

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
            }

            public static class State {

                private String id;
                private String name;

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
            }

            public Neighborhood getNeighborhood() {
                return neighborhood;
            }

            public void setNeighborhood(Neighborhood neighborhood) {
                this.neighborhood = neighborhood;
            }

            public City getCity() {
                return city;
            }

            public void setCity(City city) {
                this.city = city;
            }

            public State getState() {
                return state;
            }

            public void setState(State state) {
                this.state = state;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        @JsonProperty("address_line")
        public String getAddressLine() {
            return addressLine;
        }

        public void setAddressLine(String addressLine) {
            this.addressLine = addressLine;
        }

        @JsonProperty("zip_code")
        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public Country getCountry() {
            return country;
        }

        public void setCountry(Country country) {
            this.country = country;
        }

        @JsonProperty("search_location")
        public SearchLocation getSearchLocation() {
            return searchLocation;
        }

        public void setSearchLocation(SearchLocation searchLocation) {
            this.searchLocation = searchLocation;
        }

    }

    public static class Geolocation {

        private Double latitude;
        private Double longitude;

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }

    public static class Tags {

        private String immediatePayment;

        public Tags() {
        }

        public Tags(String immediatePayment) {
            this.immediatePayment = immediatePayment;
        }

        @JsonProperty("immediate_payment")
        public String getImmediatePayment() {
            return immediatePayment;
        }

        public void setImmediatePayment(String immediatePayment) {
            this.immediatePayment = immediatePayment;
        }
    }
}
