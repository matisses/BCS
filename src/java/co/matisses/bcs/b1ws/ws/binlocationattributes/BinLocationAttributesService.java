
package co.matisses.bcs.b1ws.ws.binlocationattributes;

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
@WebServiceClient(name = "BinLocationAttributesService", targetNamespace = "BinLocationAttributesService", wsdlLocation = "http://192.168.5.75/B1WS/WebReferences/BinLocationAttributesService.wsdl")
public class BinLocationAttributesService
    extends Service
{

    private final static URL BINLOCATIONATTRIBUTESSERVICE_WSDL_LOCATION;
    private final static WebServiceException BINLOCATIONATTRIBUTESSERVICE_EXCEPTION;
    private final static QName BINLOCATIONATTRIBUTESSERVICE_QNAME = new QName("BinLocationAttributesService", "BinLocationAttributesService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.5.75/B1WS/WebReferences/BinLocationAttributesService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BINLOCATIONATTRIBUTESSERVICE_WSDL_LOCATION = url;
        BINLOCATIONATTRIBUTESSERVICE_EXCEPTION = e;
    }

    public BinLocationAttributesService() {
        super(__getWsdlLocation(), BINLOCATIONATTRIBUTESSERVICE_QNAME);
    }

    public BinLocationAttributesService(WebServiceFeature... features) {
        super(__getWsdlLocation(), BINLOCATIONATTRIBUTESSERVICE_QNAME, features);
    }

    public BinLocationAttributesService(URL wsdlLocation) {
        super(wsdlLocation, BINLOCATIONATTRIBUTESSERVICE_QNAME);
    }

    public BinLocationAttributesService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BINLOCATIONATTRIBUTESSERVICE_QNAME, features);
    }

    public BinLocationAttributesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BinLocationAttributesService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns BinLocationAttributesServiceSoap
     */
    @WebEndpoint(name = "BinLocationAttributesServiceSoap")
    public BinLocationAttributesServiceSoap getBinLocationAttributesServiceSoap() {
        return super.getPort(new QName("BinLocationAttributesService", "BinLocationAttributesServiceSoap"), BinLocationAttributesServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BinLocationAttributesServiceSoap
     */
    @WebEndpoint(name = "BinLocationAttributesServiceSoap")
    public BinLocationAttributesServiceSoap getBinLocationAttributesServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("BinLocationAttributesService", "BinLocationAttributesServiceSoap"), BinLocationAttributesServiceSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns BinLocationAttributesServiceSoap
     */
    @WebEndpoint(name = "BinLocationAttributesServiceSoap12")
    public BinLocationAttributesServiceSoap getBinLocationAttributesServiceSoap12() {
        return super.getPort(new QName("BinLocationAttributesService", "BinLocationAttributesServiceSoap12"), BinLocationAttributesServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BinLocationAttributesServiceSoap
     */
    @WebEndpoint(name = "BinLocationAttributesServiceSoap12")
    public BinLocationAttributesServiceSoap getBinLocationAttributesServiceSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("BinLocationAttributesService", "BinLocationAttributesServiceSoap12"), BinLocationAttributesServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BINLOCATIONATTRIBUTESSERVICE_EXCEPTION!= null) {
            throw BINLOCATIONATTRIBUTESSERVICE_EXCEPTION;
        }
        return BINLOCATIONATTRIBUTESSERVICE_WSDL_LOCATION;
    }

}
