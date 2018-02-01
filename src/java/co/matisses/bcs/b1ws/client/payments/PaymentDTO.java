package co.matisses.bcs.b1ws.client.payments;

import co.matisses.bcs.util.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {

    private String cardCode;
    private String cashAccount;
    private String seriesCode;
    private String paidCash;
    private String paidBalance;
    private String creditType;
    private String transferAccount;
    private String transferReference;
    private String transferSum;
    private PaymentTypeDTO paymentType;
    private ConstantTypes.DocType docType;
    private ConstantTypes.BPType businessPartnerType;
    private List<CreditCardPaymentDTO> creditCardPayments;
    private List<PaymentAccountDTO> accountPayments;
    private List<PaymentInvoicesDTO> paymentInvoice;

    public PaymentDTO() {
        creditCardPayments = new ArrayList<>();
        accountPayments = new ArrayList<>();
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public ConstantTypes.DocType getDocType() {
        return docType;
    }

    public void setDocType(ConstantTypes.DocType docType) {
        this.docType = docType;
    }

    public ConstantTypes.BPType getBusinessPartnerType() {
        return businessPartnerType;
    }

    public void setBusinessPartnerType(ConstantTypes.BPType businessPartnerType) {
        this.businessPartnerType = businessPartnerType;
    }

    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    public String getPaidCash() {
        return paidCash;
    }

    public void setPaidCash(String paidCash) {
        this.paidCash = paidCash;
    }

    public String getPaidBalance() {
        return paidBalance;
    }

    public void setPaidBalance(String paidBalance) {
        this.paidBalance = paidBalance;
    }

    public String getTransferReference() {
        return transferReference;
    }

    public void setTransferReference(String transferReference) {
        this.transferReference = transferReference;
    }

    public String getTransferSum() {
        return transferSum;
    }

    public void setTransferSum(String transferSum) {
        this.transferSum = transferSum;
    }

    public String getTransferAccount() {
        return transferAccount;
    }

    public void setTransferAccount(String transferAccount) {
        this.transferAccount = transferAccount;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public List<CreditCardPaymentDTO> getCreditCardPayments() {
        return creditCardPayments;
    }

    public void setCreditCardPayments(List<CreditCardPaymentDTO> creditCardPayments) {
        this.creditCardPayments = creditCardPayments;
    }

    public void addCreditCardPayment(CreditCardPaymentDTO payment) {
        creditCardPayments.add(payment);
    }

    public List<PaymentAccountDTO> getAccountPayments() {
        return accountPayments;
    }

    public void setAccountPayments(List<PaymentAccountDTO> accountPayments) {
        this.accountPayments = accountPayments;
    }

    public void addAccountPayment(PaymentAccountDTO accountPayment) {
        this.accountPayments.add(accountPayment);
    }

    public String getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(String cashAccount) {
        this.cashAccount = cashAccount;
    }

    public PaymentTypeDTO getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeDTO paymentType) {
        this.paymentType = paymentType;
    }

    public List<PaymentInvoicesDTO> getPaymentInvoice() {
        return paymentInvoice;
    }

    public void setPaymentInvoice(List<PaymentInvoicesDTO> paymentInvoice) {
        this.paymentInvoice = paymentInvoice;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

    public static enum PaymentTypeDTO {
        CUSTOMER("C"), SUPPLIER("S"), ACCOUNT("A");
        private String type;

        private PaymentTypeDTO(String t) {
            this.type = t;
        }

        public String getType() {
            return type;
        }

        public PaymentTypeDTO getType(String t) {
            for (PaymentTypeDTO dto : values()) {
                if (dto.getType().equals(t)) {
                    return dto;
                }
            }
            return null;
        }
    }
}
