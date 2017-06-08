package co.matisses.bcs.b1ws.ws.invoices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "DocumentParams")
public class DocumentParams {

    @XmlElement(name = "DocEntry")
    protected Long docEntry;

    public Long getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Long value) {
        this.docEntry = value;
    }

}
