package co.matisses.bcs.b1ws.ws.warehousesublevelcodes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para anonymous complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="WarehouseSublevel" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AbsEntry" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "WarehouseSublevelCode")
public class WarehouseSublevelCode {

    @XmlElement(namespace = "", name = "WarehouseSublevel")
    protected Long warehouseSublevel;
    @XmlElement(namespace = "", name = "Code")
    protected String code;
    @XmlElement(namespace = "", name = "Description")
    protected String description;
    @XmlElement(namespace = "", name = "AbsEntry")
    protected Long absEntry;

    /**
     * Obtiene el valor de la propiedad warehouseSublevel.
     *
     * @return possible object is {@link Long }
     *
     */
    public Long getWarehouseSublevel() {
        return warehouseSublevel;
    }

    /**
     * Define el valor de la propiedad warehouseSublevel.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setWarehouseSublevel(Long value) {
        this.warehouseSublevel = value;
    }

    /**
     * Obtiene el valor de la propiedad code.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCode() {
        return code;
    }

    /**
     * Define el valor de la propiedad code.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad absEntry.
     *
     * @return possible object is {@link Long }
     *
     */
    public Long getAbsEntry() {
        return absEntry;
    }

    /**
     * Define el valor de la propiedad absEntry.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setAbsEntry(Long value) {
        this.absEntry = value;
    }

}
