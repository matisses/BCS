
package co.matisses.bcs.b1ws.ws.binlocations;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="BinLocationParams" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="AbsEntry" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *                   &lt;element name="BinCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "binLocationParams"
})
@XmlRootElement(name = "BinLocationCollectionParams")
public class BinLocationCollectionParams {

    @XmlElement(name = "BinLocationParams")
    protected List<BinLocationCollectionParams.BinLocationParams> binLocationParams;

    /**
     * Gets the value of the binLocationParams property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the binLocationParams property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBinLocationParams().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BinLocationCollectionParams.BinLocationParams }
     * 
     * 
     */
    public List<BinLocationCollectionParams.BinLocationParams> getBinLocationParams() {
        if (binLocationParams == null) {
            binLocationParams = new ArrayList<BinLocationCollectionParams.BinLocationParams>();
        }
        return this.binLocationParams;
    }


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
    public static class BinLocationParams {

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

}
