
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
 *         &lt;element ref="{http://www.sap.com/SBO/DIS}WarehouseSublevelCodeCollectionParams" minOccurs="0"/>
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
    "warehouseSublevelCodeCollectionParams"
})
@XmlRootElement(name = "GetListResponse")
public class GetListResponse {

    @XmlElement(name = "WarehouseSublevelCodeCollectionParams")
    protected WarehouseSublevelCodeCollectionParams warehouseSublevelCodeCollectionParams;

    /**
     * Obtiene el valor de la propiedad warehouseSublevelCodeCollectionParams.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseSublevelCodeCollectionParams }
     *     
     */
    public WarehouseSublevelCodeCollectionParams getWarehouseSublevelCodeCollectionParams() {
        return warehouseSublevelCodeCollectionParams;
    }

    /**
     * Define el valor de la propiedad warehouseSublevelCodeCollectionParams.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseSublevelCodeCollectionParams }
     *     
     */
    public void setWarehouseSublevelCodeCollectionParams(WarehouseSublevelCodeCollectionParams value) {
        this.warehouseSublevelCodeCollectionParams = value;
    }

}
