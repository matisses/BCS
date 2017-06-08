
package co.matisses.bcs.b1ws.ws.warehousesublevelcodes;

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
 *         &lt;element ref="{http://www.sap.com/SBO/DIS}WarehouseSublevelCode" minOccurs="0"/>
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
    "warehouseSublevelCode"
})
@XmlRootElement(name = "Add", namespace = "WarehouseSublevelCodesService")
public class Add {

    @XmlElement(name = "WarehouseSublevelCode")
    protected WarehouseSublevelCode warehouseSublevelCode;

    /**
     * Obtiene el valor de la propiedad warehouseSublevelCode.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseSublevelCode }
     *     
     */
    public WarehouseSublevelCode getWarehouseSublevelCode() {
        return warehouseSublevelCode;
    }

    /**
     * Define el valor de la propiedad warehouseSublevelCode.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseSublevelCode }
     *     
     */
    public void setWarehouseSublevelCode(WarehouseSublevelCode value) {
        this.warehouseSublevelCode = value;
    }

}
