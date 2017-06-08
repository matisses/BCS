package co.matisses.bcs.b1ws.ws.businessparters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "businessPartner"
})
@XmlRootElement(name = "Update", namespace = "BusinessPartnersService")
public class Update {

    @XmlElement(name = "BusinessPartner")
    protected BusinessPartner businessPartner;

    public BusinessPartner getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(BusinessPartner value) {
        this.businessPartner = value;
    }

}
