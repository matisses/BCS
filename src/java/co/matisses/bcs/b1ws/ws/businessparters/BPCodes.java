package co.matisses.bcs.b1ws.ws.businessparters;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bpCode"
})
@XmlRootElement(name = "BPCodes")
public class BPCodes {

    @XmlElement(name = "BPCode")
    protected List<BPCodes.BPCode> bpCode;

    public List<BPCodes.BPCode> getBPCode() {
        if (bpCode == null) {
            bpCode = new ArrayList<>();
        }
        return this.bpCode;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {})
    public static class BPCode {

        @XmlElement(name = "Code")
        protected String code;
        @XmlElement(name = "DueDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dueDate;
        @XmlElement(name = "Debit")
        protected Double debit;
        @XmlElement(name = "Credit")
        protected Double credit;
        @XmlElement(name = "SystemDebit")
        protected Double systemDebit;
        @XmlElement(name = "SystemCredit")
        protected Double systemCredit;
        @XmlElement(name = "ForeignDebit")
        protected Double foreignDebit;
        @XmlElement(name = "ForeignCredit")
        protected Double foreignCredit;
        @XmlElement(name = "ForeignCurrency")
        protected String foreignCurrency;

        public String getCode() {
            return code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public XMLGregorianCalendar getDueDate() {
            return dueDate;
        }

        public void setDueDate(XMLGregorianCalendar value) {
            this.dueDate = value;
        }

        public Double getDebit() {
            return debit;
        }

        public void setDebit(Double value) {
            this.debit = value;
        }

        public Double getCredit() {
            return credit;
        }

        public void setCredit(Double value) {
            this.credit = value;
        }

        public Double getSystemDebit() {
            return systemDebit;
        }

        public void setSystemDebit(Double value) {
            this.systemDebit = value;
        }

        public Double getSystemCredit() {
            return systemCredit;
        }

        public void setSystemCredit(Double value) {
            this.systemCredit = value;
        }

        public Double getForeignDebit() {
            return foreignDebit;
        }

        public void setForeignDebit(Double value) {
            this.foreignDebit = value;
        }

        public Double getForeignCredit() {
            return foreignCredit;
        }

        public void setForeignCredit(Double value) {
            this.foreignCredit = value;
        }

        public String getForeignCurrency() {
            return foreignCurrency;
        }

        public void setForeignCurrency(String value) {
            this.foreignCurrency = value;
        }
    }
}
