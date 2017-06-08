package co.matisses.bcs.b1ws.client.payments;

import co.matisses.bcs.util.ObjectUtils;
import java.math.BigDecimal;

/**
 *
 * @author dbotero
 */
public class PaymentAccountDTO {

    private String accountCode;
    private BigDecimal sumPaid;

    public PaymentAccountDTO() {
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public BigDecimal getSumPaid() {
        return sumPaid;
    }

    public void setSumPaid(BigDecimal sumPaid) {
        this.sumPaid = sumPaid;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
