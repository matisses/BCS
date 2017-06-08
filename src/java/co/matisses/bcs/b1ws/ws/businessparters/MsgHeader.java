package co.matisses.bcs.b1ws.ws.businessparters;

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

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String value) {
        this.sessionID = value;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String value) {
        this.serviceName = value;
    }

}
