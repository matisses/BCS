
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
 *         &lt;element ref="{http://www.sap.com/SBO/DIS}BinLocationAttribute" minOccurs="0"/>
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
    "binLocationAttribute"
})
@XmlRootElement(name = "Add", namespace = "BinLocationAttributesService")
public class Add {

    @XmlElement(name = "BinLocationAttribute")
    protected BinLocationAttribute binLocationAttribute;

    /**
     * Obtiene el valor de la propiedad binLocationAttribute.
     * 
     * @return
     *     possible object is
     *     {@link BinLocationAttribute }
     *     
     */
    public BinLocationAttribute getBinLocationAttribute() {
        return binLocationAttribute;
    }

    /**
     * Define el valor de la propiedad binLocationAttribute.
     * 
     * @param value
     *     allowed object is
     *     {@link BinLocationAttribute }
     *     
     */
    public void setBinLocationAttribute(BinLocationAttribute value) {
        this.binLocationAttribute = value;
    }

}
