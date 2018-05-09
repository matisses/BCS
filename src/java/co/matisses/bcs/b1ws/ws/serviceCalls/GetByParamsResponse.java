package co.matisses.bcs.b1ws.ws.serviceCalls;

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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.sap.com/SBO/DIS}ServiceCall" minOccurs="0"/>
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
    "serviceCall"
})
@XmlRootElement(name = "GetByParamsResponse")
public class GetByParamsResponse {

    @XmlElement(name = "ServiceCall")
    protected ServiceCall serviceCall;

    /**
     * Obtiene el valor de la propiedad serviceCall.
     *
     * @return possible object is {@link ServiceCall }
     *
     */
    public ServiceCall getServiceCall() {
        return serviceCall;
    }

    /**
     * Define el valor de la propiedad serviceCall.
     *
     * @param value allowed object is {@link ServiceCall }
     *
     */
    public void setServiceCall(ServiceCall value) {
        this.serviceCall = value;
    }
}
