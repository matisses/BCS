package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagoMercadoLibreDTO {

    private Long id;
    private String orderId;
    private Payer payer;
    private Long transactionAmount;
    private Long netReceivedAmount;
    private Long shippingCost;
    private Long totalPaidAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("order_id")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public Long getNetReceivedAmount() {
        return netReceivedAmount;
    }

    public void setNetReceivedAmount(Long netReceivedAmount) {
        this.netReceivedAmount = netReceivedAmount;
    }

    public Long getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Long shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Long getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(Long totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        return "PagoMercadoLibreDTO{" + "id=" + id + ", orderId=" + orderId + ", payer=" + payer + ", transactionAmount=" + transactionAmount + ", netReceivedAmount=" + netReceivedAmount + ", shippingCost=" + shippingCost + ", totalPaidAmount=" + totalPaidAmount + '}';
    }

    public static class Payer {

        private Long id;
        private String email;
        private Phone phone;
        private Identification identification;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Phone getPhone() {
            return phone;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Identification getIdentification() {
            return identification;
        }

        public void setIdentification(Identification identification) {
            this.identification = identification;
        }

        @Override
        public String toString() {
            return "Payer{" + "id=" + id + ", email=" + email + ", phone=" + phone + ", identification=" + identification + '}';
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Phone {

            private String number;
            private String areaCode;

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
                return "Phone{" + "number=" + number + ", areaCode=" + areaCode + '}';
            }
        }

        public static class Identification {

            private String type;
            private String number;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String toString() {
                return "Identification{" + "type=" + type + ", number=" + number + '}';
            }

        }
    }
}
