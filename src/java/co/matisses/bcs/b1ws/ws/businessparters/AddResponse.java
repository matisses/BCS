package co.matisses.bcs.b1ws.ws.businessparters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "businessPartnerParams"
})
@XmlRootElement(name = "AddResponse")
public class AddResponse {

    @XmlElement(name = "BusinessPartnerParams")
    protected BusinessPartnerParams businessPartnerParams;

    public BusinessPartnerParams getBusinessPartnerParams() {
        return businessPartnerParams;
    }

    public void setBusinessPartnerParams(BusinessPartnerParams value) {
        this.businessPartnerParams = value;
    }
}
