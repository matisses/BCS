
package co.matisses.bcs.b1ws.ws.binlocationattributes;

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
 *         &lt;element ref="{http://www.sap.com/SBO/DIS}BinLocationAttributeParams" minOccurs="0"/>
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
    "binLocationAttributeParams"
})
@XmlRootElement(name = "Get", namespace = "BinLocationAttributesService")
public class Get {

    @XmlElement(name = "BinLocationAttributeParams")
    protected BinLocationAttributeParams binLocationAttributeParams;

    /**
     * Obtiene el valor de la propiedad binLocationAttributeParams.
     * 
     * @return
     *     possible object is
     *     {@link BinLocationAttributeParams }
     *     
     */
    public BinLocationAttributeParams getBinLocationAttributeParams() {
        return binLocationAttributeParams;
    }

    /**
     * Define el valor de la propiedad binLocationAttributeParams.
     * 
     * @param value
     *     allowed object is
     *     {@link BinLocationAttributeParams }
     *     
     */
    public void setBinLocationAttributeParams(BinLocationAttributeParams value) {
        this.binLocationAttributeParams = value;
    }

}
