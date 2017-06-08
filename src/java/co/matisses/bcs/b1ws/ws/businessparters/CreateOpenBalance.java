package co.matisses.bcs.b1ws.ws.businessparters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "openningBalanceAccount",
    "bpCodes"
})
@XmlRootElement(name = "CreateOpenBalance", namespace = "BusinessPartnersService")
public class CreateOpenBalance {

    @XmlElement(name = "OpenningBalanceAccount")
    protected OpenningBalanceAccount openningBalanceAccount;
    @XmlElement(name = "BPCodes")
    protected BPCodes bpCodes;

    public OpenningBalanceAccount getOpenningBalanceAccount() {
        return openningBalanceAccount;
    }

    public void setOpenningBalanceAccount(OpenningBalanceAccount value) {
        this.openningBalanceAccount = value;
    }

    public BPCodes getBPCodes() {
        return bpCodes;
    }

    public void setBPCodes(BPCodes value) {
        this.bpCodes = value;
    }

}
