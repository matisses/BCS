package co.matisses.bcs.b1ws.ws.login;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sessionID"
})
@XmlRootElement(name = "LoginResponse")
public class LoginResponse {

    @XmlElement(name = "SessionID")
    protected String sessionID;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String value) {
        this.sessionID = value;
    }

}
