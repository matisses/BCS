package co.matisses.bcs.b1ws.ws.invoices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sessionID",
    "serviceName"
})
@XmlRootElement(name = "MsgHeader")
public class MsgHeader {

    @XmlElement(name = "SessionID")
    protected String sessionID;
    @XmlElement(name = "ServiceName")
    protected String serviceName;

    /**
     * Obtiene el valor de la propiedad sessionID.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * Define el valor de la propiedad sessionID.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSessionID(String value) {
        this.sessionID = value;
    }

    /**
     * Obtiene el valor de la propiedad serviceName.
     *
     * @return possible object is {@link String }
     *
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Define el valor de la propiedad serviceName.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

}
