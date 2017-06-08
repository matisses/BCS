
package co.matisses.bcs.b1ws.ws.warehousesublevelcodes;

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
 *         &lt;element name="WarehouseSublevelCodeParams" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="AbsEntry" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *                   &lt;element name="WarehouseSublevel" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *                   &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "warehouseSublevelCodeParams"
})
@XmlRootElement(name = "WarehouseSublevelCodeCollectionParams")
public class WarehouseSublevelCodeCollectionParams {

    @XmlElement(name = "WarehouseSublevelCodeParams")
    protected List<WarehouseSublevelCodeCollectionParams.WarehouseSublevelCodeParams> warehouseSublevelCodeParams;

    /**
     * Gets the value of the warehouseSublevelCodeParams property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the warehouseSublevelCodeParams property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWarehouseSublevelCodeParams().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WarehouseSublevelCodeCollectionParams.WarehouseSublevelCodeParams }
     * 
     * 
     */
    public List<WarehouseSublevelCodeCollectionParams.WarehouseSublevelCodeParams> getWarehouseSublevelCodeParams() {
        if (warehouseSublevelCodeParams == null) {
            warehouseSublevelCodeParams = new ArrayList<WarehouseSublevelCodeCollectionParams.WarehouseSublevelCodeParams>();
        }
        return this.warehouseSublevelCodeParams;
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
     *         &lt;element name="WarehouseSublevel" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
    public static class WarehouseSublevelCodeParams {

        @XmlElement(name = "AbsEntry")
        protected Long absEntry;
        @XmlElement(name = "WarehouseSublevel")
        protected Long warehouseSublevel;
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
         * Obtiene el valor de la propiedad warehouseSublevel.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getWarehouseSublevel() {
            return warehouseSublevel;
        }

        /**
         * Define el valor de la propiedad warehouseSublevel.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setWarehouseSublevel(Long value) {
            this.warehouseSublevel = value;
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

}
