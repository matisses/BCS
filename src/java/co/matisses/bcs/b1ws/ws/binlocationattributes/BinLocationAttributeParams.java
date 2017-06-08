
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
 *       &lt;all>
 *         &lt;element name="AbsEntry" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Attribute" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "BinLocationAttributeParams")
public class BinLocationAttributeParams {

    @XmlElement(name = "AbsEntry")
    protected Long absEntry;
    @XmlElement(name = "Attribute")
    protected Long attribute;
    @XmlElement(name = "Code")
    protected String code;

    /**
     * Obtiene el valor de la propiedad absEntry.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAbsEntry() {
        return absEntry;
    }

    /**
     * Define el valor de la propiedad absEntry.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAbsEntry(Long value) {
        this.absEntry = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAttribute() {
        return attribute;
    }

    /**
     * Define el valor de la propiedad attribute.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAttribute(Long value) {
        this.attribute = value;
    }

    /**
     * Obtiene el valor de la propiedad code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Define el valor de la propiedad code.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

}
