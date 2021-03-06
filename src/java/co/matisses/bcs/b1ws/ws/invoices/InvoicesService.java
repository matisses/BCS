
package co.matisses.bcs.b1ws.ws.invoices;

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
@WebServiceClient(name = "InvoicesService", targetNamespace = "InvoicesService", wsdlLocation = "http://192.168.5.75/B1WS/WebReferences/InvoicesService.wsdl")
public class InvoicesService
    extends Service
{

    private final static URL INVOICESSERVICE_WSDL_LOCATION;
    private final static WebServiceException INVOICESSERVICE_EXCEPTION;
    private final static QName INVOICESSERVICE_QNAME = new QName("InvoicesService", "InvoicesService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.5.75/B1WS/WebReferences/InvoicesService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        INVOICESSERVICE_WSDL_LOCATION = url;
        INVOICESSERVICE_EXCEPTION = e;
    }

    public InvoicesService() {
        super(__getWsdlLocation(), INVOICESSERVICE_QNAME);
    }

    public InvoicesService(WebServiceFeature... features) {
        super(__getWsdlLocation(), INVOICESSERVICE_QNAME, features);
    }

    public InvoicesService(URL wsdlLocation) {
        super(wsdlLocation, INVOICESSERVICE_QNAME);
    }

    public InvoicesService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, INVOICESSERVICE_QNAME, features);
    }

    public InvoicesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public InvoicesService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns InvoicesServiceSoap
     */
    @WebEndpoint(name = "InvoicesServiceSoap")
    public InvoicesServiceSoap getInvoicesServiceSoap() {
        return super.getPort(new QName("InvoicesService", "InvoicesServiceSoap"), InvoicesServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns InvoicesServiceSoap
     */
    @WebEndpoint(name = "InvoicesServiceSoap")
    public InvoicesServiceSoap getInvoicesServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("InvoicesService", "InvoicesServiceSoap"), InvoicesServiceSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns InvoicesServiceSoap
     */
    @WebEndpoint(name = "InvoicesServiceSoap12")
    public InvoicesServiceSoap getInvoicesServiceSoap12() {
        return super.getPort(new QName("InvoicesService", "InvoicesServiceSoap12"), InvoicesServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns InvoicesServiceSoap
     */
    @WebEndpoint(name = "InvoicesServiceSoap12")
    public InvoicesServiceSoap getInvoicesServiceSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("InvoicesService", "InvoicesServiceSoap12"), InvoicesServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (INVOICESSERVICE_EXCEPTION!= null) {
            throw INVOICESSERVICE_EXCEPTION;
        }
        return INVOICESSERVICE_WSDL_LOCATION;
    }

}
