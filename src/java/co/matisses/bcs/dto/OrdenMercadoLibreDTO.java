package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdenMercadoLibreDTO {

    private Long id;
    private Long totalAmount;
    private Long totalAmountWithShipping;
    private Long paidAmount;
    private Buyer buyer;
    private Shipping shipping;
    private List<OrderItem> orderItems;

    public OrdenMercadoLibreDTO() {
        orderItems = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("order_items")
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    @JsonProperty("paid_amount")
    public Long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }

    @JsonProperty("total_amount")
    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @JsonProperty("total_amount_with_shipping")
    public Long getTotalAmountWithShipping() {
        return totalAmountWithShipping;
    }

    public void setTotalAmountWithShipping(Long totalAmountWithShipping) {
        this.totalAmountWithShipping = totalAmountWithShipping;
    }

    @Override
    public String toString() {
        return "OrdenMercadoLibreDTO{" + "id=" + id + ", buyer=" + buyer + ", shipping=" + shipping + ", orderItems=" + orderItems + '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Shipping {

        private ReceiverAddress receiverAddress;

        @JsonProperty("receiver_address")
        public ReceiverAddress getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(ReceiverAddress receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        @Override
        public String toString() {
            return "Shipping{" + "receiverAddress=" + receiverAddress + '}';
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ReceiverAddress {

            private String addressLine;
            private City city;
            private State state;

            @JsonProperty("address_line")
            public String getAddressLine() {
                return addressLine;
            }

            public void setAddressLine(String addressLine) {
                this.addressLine = addressLine;
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

            @Override
            public String toString() {
                return "ReceiverAddress{" + "addressLine=" + addressLine + ", city=" + city + ", state=" + state + '}';
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class City {

                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                @Override
                public String toString() {
                    return "City{" + "name=" + name + '}';
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class State {

                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                @Override
                public String toString() {
                    return "State{" + "name=" + name + '}';
                }
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Buyer {

        private Long id;
        private String nickname;
        private String email;
        private String firstName;
        private String lastName;
        private Phone phone;
        private BillingInfo billingInfo;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @JsonProperty("first_name")
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        @JsonProperty("last_name")
        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Phone getPhone() {
            return phone;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        @JsonProperty("billing_info")
        public BillingInfo getBillingInfo() {
            return billingInfo;
        }

        public void setBillingInfo(BillingInfo billingInfo) {
            this.billingInfo = billingInfo;
        }

        @Override
        public String toString() {
            return "Buyer{" + "id=" + id + ", nickname=" + nickname + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", billingInfo=" + billingInfo + '}';
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Phone {

            private String areaCode;
            private String number;

            @JsonProperty("area_code")
            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            @Override
            public String toString() {
                return "Phone{" + "areaCode=" + areaCode + ", number=" + number + '}';
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class BillingInfo {

            private String docType;
            private String docNumber;

            @JsonProperty("doc_number")
            public String getDocNumber() {
                return docNumber;
            }

            public void setDocNumber(String docNumber) {
                this.docNumber = docNumber;
            }

            @JsonProperty("doc_type")
            public String getDocType() {
                return docType;
            }

            public void setDocType(String docType) {
                this.docType = docType;
            }

            @Override
            public String toString() {
                return "BillingInfo{" + "docType=" + docType + ", docNumber=" + docNumber + '}';
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderItem {

        private Item item;
        private Integer quantity;
        private Long unitPrice;

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        @JsonProperty("unit_price")
        public Long getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Long unitPrice) {
            this.unitPrice = unitPrice;
        }

        @Override
        public String toString() {
            return "OrderItem{" + "item=" + item + ", quantity=" + quantity + ", unitPrice=" + unitPrice + '}';
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Item {

            private String id;
            private String title;
            private String categoryId;

            @JsonProperty("category_id")
            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public String toString() {
                return "Item{" + "id=" + id + ", title=" + title + ", categoryId=" + categoryId + '}';
            }
        }
    }
}
