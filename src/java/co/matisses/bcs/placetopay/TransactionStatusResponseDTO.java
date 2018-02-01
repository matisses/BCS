package co.matisses.bcs.placetopay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionStatusResponseDTO {

    private Integer requestId;
    private String subscription;
    private Status status;
    private Request request;
    private List<Payment> payment;

    public TransactionStatusResponseDTO() {
        payment = new ArrayList<>();
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Status {

        private String status;
        private String reason;
        private String message;
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Request {

        private String locale;
        private Subscription subscription;
        private List<Fields> fields;
        private String returnUrl;
        private String paymentMethod;
        private String cancelUrl;
        private String ipAddress;
        private String userAgent;
        private Date expiration;
        private String captureAddress;
        private String skipResult;
        private String noBuyerFill;
        private Payer payer;
        private Buyer buyer;
        private Payment payment;

        public Request() {
            this.locale = "es_CO";
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public Subscription getSubscription() {
            return subscription;
        }

        public void setSubscription(Subscription subscription) {
            this.subscription = subscription;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Subscription {

            private String reference;
            private String description;
            private Fields fields;

            public String getReference() {
                return reference;
            }

            public void setReference(String reference) {
                this.reference = reference;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Fields getFields() {
                return fields;
            }

            public void setFields(Fields fields) {
                this.fields = fields;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Fields {

                private List<Item> item;

                public List<Item> getItem() {
                    return item;
                }

                public void setItem(List<Item> item) {
                    this.item = item;
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Item {

                    private String keyword;
                    private String value;
                    private String displayOn;

                    public String getKeyword() {
                        return keyword;
                    }

                    public void setKeyword(String keyword) {
                        this.keyword = keyword;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }

                    public String getDisplayOn() {
                        return displayOn;
                    }

                    public void setDisplayOn(String displayOn) {
                        this.displayOn = displayOn;
                    }
                }
            }
        }

        public List<Fields> getFields() {
            return fields;
        }

        public void setFields(List<Fields> fields) {
            this.fields = fields;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Fields {

            private String value;
            private String keyword;
            private String displayOn;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public String getDisplayOn() {
                return displayOn;
            }

            public void setDisplayOn(String displayOn) {
                this.displayOn = displayOn;
            }
        }

        public String getReturnUrl() {
            return returnUrl;
        }

        public void setReturnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getCancelUrl() {
            return cancelUrl;
        }

        public void setCancelUrl(String cancelUrl) {
            this.cancelUrl = cancelUrl;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public Date getExpiration() {
            return expiration;
        }

        public void setExpiration(Date expiration) {
            this.expiration = expiration;
        }

        public String getCaptureAddress() {
            return captureAddress;
        }

        public void setCaptureAddress(String captureAddress) {
            this.captureAddress = captureAddress;
        }

        public String getSkipResult() {
            return skipResult;
        }

        public void setSkipResult(String skipResult) {
            this.skipResult = skipResult;
        }

        public String getNoBuyerFill() {
            return noBuyerFill;
        }

        public void setNoBuyerFill(String noBuyerFill) {
            this.noBuyerFill = noBuyerFill;
        }

        public Payer getPayer() {
            return payer;
        }

        public void setPayer(Payer payer) {
            this.payer = payer;
        }

        public Buyer getBuyer() {
            return buyer;
        }

        public void setBuyer(Buyer buyer) {
            this.buyer = buyer;
        }

        public Payment getPayment() {
            return payment;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Payer {

            private String document;
            private String documentType;
            private String name;
            private String surname;
            private String email;
            private String mobile;
            private String company;
            private Address address;

            public Payer() {
            }

            public String getDocument() {
                return document;
            }

            public void setDocument(String document) {
                this.document = document;
            }

            public String getDocumentType() {
                return documentType;
            }

            public void setDocumentType(String documentType) {
                this.documentType = documentType;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSurname() {
                return surname;
            }

            public void setSurname(String surname) {
                this.surname = surname;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public Address getAddress() {
                return address;
            }

            public void setAddress(Address address) {
                this.address = address;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public class Address {

                private String street;
                private String city;
                private String state;
                private String postalCode;
                private String country;
                private String phone;

                public Address() {
                    this.country = "CO";
                }

                public String getStreet() {
                    return street;
                }

                public void setStreet(String street) {
                    this.street = street;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getPostalCode() {
                    return postalCode;
                }

                public void setPostalCode(String postalCode) {
                    this.postalCode = postalCode;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Buyer {

            private String document;
            private String documentType;
            private String name;
            private String surname;
            private String email;
            private String mobile;
            private String company;
            private Address address;

            public Buyer() {
            }

            public String getDocument() {
                return document;
            }

            public void setDocument(String document) {
                this.document = document;
            }

            public String getDocumentType() {
                return documentType;
            }

            public void setDocumentType(String documentType) {
                this.documentType = documentType;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSurname() {
                return surname;
            }

            public void setSurname(String surname) {
                this.surname = surname;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public Address getAddress() {
                return address;
            }

            public void setAddress(Address address) {
                this.address = address;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public class Address {

                private String street;
                private String city;
                private String state;
                private String postalCode;
                private String country;
                private String phone;

                public Address() {
                }

                public String getStreet() {
                    return street;
                }

                public void setStreet(String street) {
                    this.street = street;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getPostalCode() {
                    return postalCode;
                }

                public void setPostalCode(String postalCode) {
                    this.postalCode = postalCode;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Payment {

            private String reference;
            private String description;
            private String shipping;
            private String items;
            private String recurring;
            private Instrument instrument;
            private String fields;
            private boolean allowPartial;
            private boolean subscribe;
            private Amount amount;

            public Payment() {
                this.allowPartial = false;
            }

            public String getReference() {
                return reference;
            }

            public void setReference(String reference) {
                this.reference = reference;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getShipping() {
                return shipping;
            }

            public void setShipping(String shipping) {
                this.shipping = shipping;
            }

            public String getItems() {
                return items;
            }

            public void setItems(String items) {
                this.items = items;
            }

            public String getRecurring() {
                return recurring;
            }

            public void setRecurring(String recurring) {
                this.recurring = recurring;
            }

            public Instrument getInstrument() {
                return instrument;
            }

            public void setInstrument(Instrument instrument) {
                this.instrument = instrument;
            }

            public String getFields() {
                return fields;
            }

            public void setFields(String fields) {
                this.fields = fields;
            }

            public boolean isAllowPartial() {
                return allowPartial;
            }

            public void setAllowPartial(boolean allowPartial) {
                this.allowPartial = allowPartial;
            }

            public boolean isSubscribe() {
                return subscribe;
            }

            public void setSubscribe(boolean subscribe) {
                this.subscribe = subscribe;
            }

            public Amount getAmount() {
                return amount;
            }

            public void setAmount(Amount amount) {
                this.amount = amount;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Instrument {

                private Bank bank;
                private Card card;
                private Token token;

                public Bank getBank() {
                    return bank;
                }

                public void setBank(Bank bank) {
                    this.bank = bank;
                }

                public Card getCard() {
                    return card;
                }

                public void setCard(Card card) {
                    this.card = card;
                }

                public Token getToken() {
                    return token;
                }

                public void setToken(Token token) {
                    this.token = token;
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Bank {

                    private String inteface;
                    private String code;

                    @JsonProperty("interface")
                    public String getInteface() {
                        return inteface;
                    }

                    public void setInteface(String inteface) {
                        this.inteface = inteface;
                    }

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Card {

                    private String installments;
                    private String kind;
                    private boolean save;

                    public String getInstallments() {
                        return installments;
                    }

                    public void setInstallments(String installments) {
                        this.installments = installments;
                    }

                    public String getKind() {
                        return kind;
                    }

                    public void setKind(String kind) {
                        this.kind = kind;
                    }

                    public boolean isSave() {
                        return save;
                    }

                    public void setSave(boolean save) {
                        this.save = save;
                    }
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Token {

                    private String token;
                    private String subtoken;
                    private String franchiseName;
                    private String lastDigits;
                    private String validUntil;

                    public String getToken() {
                        return token;
                    }

                    public void setToken(String token) {
                        this.token = token;
                    }

                    public String getSubtoken() {
                        return subtoken;
                    }

                    public void setSubtoken(String subtoken) {
                        this.subtoken = subtoken;
                    }

                    public String getFranchiseName() {
                        return franchiseName;
                    }

                    public void setFranchiseName(String franchiseName) {
                        this.franchiseName = franchiseName;
                    }

                    public String getLastDigits() {
                        return lastDigits;
                    }

                    public void setLastDigits(String lastDigits) {
                        this.lastDigits = lastDigits;
                    }

                    public String getValidUntil() {
                        return validUntil;
                    }

                    public void setValidUntil(String validUntil) {
                        this.validUntil = validUntil;
                    }
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Amount {

                private String taxes;
                private String details;
                private String currency;
                private Long total;

                public Amount() {
                    this.currency = "COP";
                }

                public String getTaxes() {
                    return taxes;
                }

                public void setTaxes(String taxes) {
                    this.taxes = taxes;
                }

                public String getDetails() {
                    return details;
                }

                public void setDetails(String details) {
                    this.details = details;
                }

                public String getCurrency() {
                    return currency;
                }

                public void setCurrency(String currency) {
                    this.currency = currency;
                }

                public Long getTotal() {
                    return total;
                }

                public void setTotal(Long total) {
                    this.total = total;
                }
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payment {

        private String internalReference;
        private String paymentMethod;
        private String paymentMethodName;
        private String issuerName;
        private String authorization;
        private String reference;
        private String receipt;
        private String franchise;
        private String refunded;
        private Status status;
        private Amount amount;
        private List<ProcessorField> processorFields;

        public Payment() {
        }

        public Payment(String internalReference, String paymentMethod, String paymentMethodName, String issuerName, String authorization, String reference, String receipt, String franchise, String refunded, Status status, Amount amount, List<ProcessorField> processorFields) {
            this.internalReference = internalReference;
            this.paymentMethod = paymentMethod;
            this.paymentMethodName = paymentMethodName;
            this.issuerName = issuerName;
            this.authorization = authorization;
            this.reference = reference;
            this.receipt = receipt;
            this.franchise = franchise;
            this.refunded = refunded;
            this.status = status;
            this.amount = amount;
            this.processorFields = processorFields;
        }

        public String getInternalReference() {
            return internalReference;
        }

        public void setInternalReference(String internalReference) {
            this.internalReference = internalReference;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getPaymentMethodName() {
            return paymentMethodName;
        }

        public void setPaymentMethodName(String paymentMethodName) {
            this.paymentMethodName = paymentMethodName;
        }

        public String getIssuerName() {
            return issuerName;
        }

        public void setIssuerName(String issuerName) {
            this.issuerName = issuerName;
        }

        public String getAuthorization() {
            return authorization;
        }

        public void setAuthorization(String authorization) {
            this.authorization = authorization;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getReceipt() {
            return receipt;
        }

        public void setReceipt(String receipt) {
            this.receipt = receipt;
        }

        public String getFranchise() {
            return franchise;
        }

        public void setFranchise(String franchise) {
            this.franchise = franchise;
        }

        public String getRefunded() {
            return refunded;
        }

        public void setRefunded(String refunded) {
            this.refunded = refunded;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Amount getAmount() {
            return amount;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public List<ProcessorField> getProcessorFields() {
            return processorFields;
        }

        public void setProcessorFields(List<ProcessorField> processorFields) {
            this.processorFields = processorFields;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Status {

            private String status;
            private String reason;
            private String message;
            private String date;

            public Status() {
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Amount {

            private From from;
            private To to;
            private int factor;

            public Amount() {
                this.factor = 1;
            }

            public From getFrom() {
                return from;
            }

            public void setFrom(From from) {
                this.from = from;
            }

            public To getTo() {
                return to;
            }

            public void setTo(To to) {
                this.to = to;
            }

            public int getFactor() {
                return factor;
            }

            public void setFactor(int factor) {
                this.factor = factor;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class From {

                private String currency;
                private Long total;

                public From() {
                    this.currency = "COP";
                }

                public String getCurrency() {
                    return currency;
                }

                public void setCurrency(String currency) {
                    this.currency = currency;
                }

                public Long getTotal() {
                    return total;
                }

                public void setTotal(Long total) {
                    this.total = total;
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class To {

                private String currency;
                private Long total;

                public To() {
                    this.currency = "COP";
                }

                public String getCurrency() {
                    return currency;
                }

                public void setCurrency(String currency) {
                    this.currency = currency;
                }

                public Long getTotal() {
                    return total;
                }

                public void setTotal(Long total) {
                    this.total = total;
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ProcessorField {

            private String keyword;
            private String value;
            private String displayOn;

            public ProcessorField() {
            }

            public String getKeyword() {
                return keyword;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getDisplayOn() {
                return displayOn;
            }

            public void setDisplayOn(String displayOn) {
                this.displayOn = displayOn;
            }
        }

        @Override
        public String toString() {
            return "Payment{" + "internalReference=" + internalReference + ", paymentMethod=" + paymentMethod + ", paymentMethodName=" + paymentMethodName + ", issuerName=" + issuerName + ", authorization=" + authorization + ", reference=" + reference + ", receipt=" + receipt + ", franchise=" + franchise + ", refunded=" + refunded + ", status=" + status + ", amount=" + amount + ", processorFields=" + processorFields + '}';
        }
    }

    @Override
    public String toString() {
        return "TransactionStatusResponseDTO{" + "requestId=" + requestId + ", subscription=" + subscription + ", status=" + status + ", request=" + request + ", payment=" + payment + '}';
    }
}
