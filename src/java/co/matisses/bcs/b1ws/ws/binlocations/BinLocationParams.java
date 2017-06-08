
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
 *       &lt;all>
 *         &lt;element name="AbsEntry" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="BinCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlRootElement(name = "BinLocationParams")
public class BinLocationParams {

    @XmlElement(name = "AbsEntry")
    protected Long absEntry;
    @XmlElement(name = "BinCode")
    protected String binCode;

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
     * Obtiene el valor de la propiedad binCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBinCode() {
        return binCode;
    }

    /**
     * Define el valor de la propiedad binCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBinCode(String value) {
        this.binCode = value;
    }

}
