
package co.matisses.bcs.b1ws.ws.binlocations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.sap.com/SBO/DIS}BinLocationCollectionParams" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "binLocationCollectionParams"
})
@XmlRootElement(name = "GetListResponse")
public class GetListResponse {

    @XmlElement(name = "BinLocationCollectionParams")
    protected BinLocationCollectionParams binLocationCollectionParams;

    /**
     * Obtiene el valor de la propiedad binLocationCollectionParams.
     * 
     * @return
     *     possible object is
     *     {@link BinLocationCollectionParams }
     *     
     */
    public BinLocationCollectionParams getBinLocationCollectionParams() {
        return binLocationCollectionParams;
    }

    /**
     * Define el valor de la propiedad binLocationCollectionParams.
     * 
     * @param value
     *     allowed object is
     *     {@link BinLocationCollectionParams }
     *     
     */
    public void setBinLocationCollectionParams(BinLocationCollectionParams value) {
        this.binLocationCollectionParams = value;
    }

}
