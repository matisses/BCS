
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
 *         &lt;element ref="{http://www.sap.com/SBO/DIS}BinLocation" minOccurs="0"/>
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
    "binLocation"
})
@XmlRootElement(name = "GetResponse")
public class GetResponse {

    @XmlElement(name = "BinLocation")
    protected BinLocation binLocation;

    /**
     * Obtiene el valor de la propiedad binLocation.
     * 
     * @return
     *     possible object is
     *     {@link BinLocation }
     *     
     */
    public BinLocation getBinLocation() {
        return binLocation;
    }

    /**
     * Define el valor de la propiedad binLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link BinLocation }
     *     
     */
    public void setBinLocation(BinLocation value) {
        this.binLocation = value;
    }

}
