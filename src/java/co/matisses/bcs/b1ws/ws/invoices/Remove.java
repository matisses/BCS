package co.matisses.bcs.b1ws.ws.invoices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "documentParams"
})
@XmlRootElement(name = "Remove", namespace = "InvoicesService")
public class Remove {

    @XmlElement(name = "DocumentParams")
    protected DocumentParams documentParams;

    public DocumentParams getDocumentParams() {
        return documentParams;
    }

    public void setDocumentParams(DocumentParams value) {
        this.documentParams = value;
    }

}
