
package co.matisses.bcs.b1ws.ws.warehousesublevelcodes;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WarehouseSublevelCodesService", targetNamespace = "WarehouseSublevelCodesService", wsdlLocation = "http://192.168.5.75/B1WS/WebReferences/WarehouseSublevelCodesService.wsdl")
public class WarehouseSublevelCodesService
    extends Service
{

    private final static URL WAREHOUSESUBLEVELCODESSERVICE_WSDL_LOCATION;
    private final static WebServiceException WAREHOUSESUBLEVELCODESSERVICE_EXCEPTION;
    private final static QName WAREHOUSESUBLEVELCODESSERVICE_QNAME = new QName("WarehouseSublevelCodesService", "WarehouseSublevelCodesService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.5.75/B1WS/WebReferences/WarehouseSublevelCodesService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WAREHOUSESUBLEVELCODESSERVICE_WSDL_LOCATION = url;
        WAREHOUSESUBLEVELCODESSERVICE_EXCEPTION = e;
    }

    public WarehouseSublevelCodesService() {
        super(__getWsdlLocation(), WAREHOUSESUBLEVELCODESSERVICE_QNAME);
    }

    public WarehouseSublevelCodesService(WebServiceFeature... features) {
        super(__getWsdlLocation(), WAREHOUSESUBLEVELCODESSERVICE_QNAME, features);
    }

    public WarehouseSublevelCodesService(URL wsdlLocation) {
        super(wsdlLocation, WAREHOUSESUBLEVELCODESSERVICE_QNAME);
    }

    public WarehouseSublevelCodesService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WAREHOUSESUBLEVELCODESSERVICE_QNAME, features);
    }

    public WarehouseSublevelCodesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WarehouseSublevelCodesService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WarehouseSublevelCodesServiceSoap
     */
    @WebEndpoint(name = "WarehouseSublevelCodesServiceSoap")
    public WarehouseSublevelCodesServiceSoap getWarehouseSublevelCodesServiceSoap() {
        return super.getPort(new QName("WarehouseSublevelCodesService", "WarehouseSublevelCodesServiceSoap"), WarehouseSublevelCodesServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WarehouseSublevelCodesServiceSoap
     */
    @WebEndpoint(name = "WarehouseSublevelCodesServiceSoap")
    public WarehouseSublevelCodesServiceSoap getWarehouseSublevelCodesServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("WarehouseSublevelCodesService", "WarehouseSublevelCodesServiceSoap"), WarehouseSublevelCodesServiceSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns WarehouseSublevelCodesServiceSoap
     */
    @WebEndpoint(name = "WarehouseSublevelCodesServiceSoap12")
    public WarehouseSublevelCodesServiceSoap getWarehouseSublevelCodesServiceSoap12() {
        return super.getPort(new QName("WarehouseSublevelCodesService", "WarehouseSublevelCodesServiceSoap12"), WarehouseSublevelCodesServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WarehouseSublevelCodesServiceSoap
     */
    @WebEndpoint(name = "WarehouseSublevelCodesServiceSoap12")
    public WarehouseSublevelCodesServiceSoap getWarehouseSublevelCodesServiceSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("WarehouseSublevelCodesService", "WarehouseSublevelCodesServiceSoap12"), WarehouseSublevelCodesServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WAREHOUSESUBLEVELCODESSERVICE_EXCEPTION!= null) {
            throw WAREHOUSESUBLEVELCODESSERVICE_EXCEPTION;
        }
        return WAREHOUSESUBLEVELCODESSERVICE_WSDL_LOCATION;
    }

}
