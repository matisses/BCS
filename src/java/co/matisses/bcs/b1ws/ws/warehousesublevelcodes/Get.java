
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
 *         &lt;element ref="{http://www.sap.com/SBO/DIS}WarehouseSublevelCodeParams" minOccurs="0"/>
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
@XmlRootElement(name = "Get", namespace = "WarehouseSublevelCodesService")
public class Get {

    @XmlElement(name = "WarehouseSublevelCodeParams")
    protected WarehouseSublevelCodeParams warehouseSublevelCodeParams;

    /**
     * Obtiene el valor de la propiedad warehouseSublevelCodeParams.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseSublevelCodeParams }
     *     
     */
    public WarehouseSublevelCodeParams getWarehouseSublevelCodeParams() {
        return warehouseSublevelCodeParams;
    }

    /**
     * Define el valor de la propiedad warehouseSublevelCodeParams.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseSublevelCodeParams }
     *     
     */
    public void setWarehouseSublevelCodeParams(WarehouseSublevelCodeParams value) {
        this.warehouseSublevelCodeParams = value;
    }

}
