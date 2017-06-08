package co.matisses.bcs.b1ws.ws.invoices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "document"
})
@XmlRootElement(name = "Add", namespace = "InvoicesService")
public class Add {

    @XmlElement(name = "Document")
    protected Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document value) {
        this.document = value;
    }

}
