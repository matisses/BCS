package co.matisses.bcs.b1ws.ws.businessparters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "BusinessPartnerParams")
public class BusinessPartnerParams {

    @XmlElement(name = "CardCode")
    protected String cardCode;

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String value) {
        this.cardCode = value;
    }

}
