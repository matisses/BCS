package co.matisses.bcs.b1ws.ws.businessparters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "OpenningBalanceAccount")
public class OpenningBalanceAccount {

    @XmlElement(name = "OpenBalanceAccount")
    protected String openBalanceAccount;
    @XmlElement(name = "Date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "Ref1")
    protected String ref1;
    @XmlElement(name = "Ref2")
    protected String ref2;
    @XmlElement(name = "Details")
    protected String details;

    public String getOpenBalanceAccount() {
        return openBalanceAccount;
    }

    public void setOpenBalanceAccount(String value) {
        this.openBalanceAccount = value;
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    public String getRef1() {
        return ref1;
    }

    public void setRef1(String value) {
        this.ref1 = value;
    }

    public String getRef2() {
        return ref2;
    }

    public void setRef2(String value) {
        this.ref2 = value;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String value) {
        this.details = value;
    }

}
