
package co.matisses.bcs.b1ws.ws.stocktransfer;

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
@WebServiceClient(name = "StockTransferService", targetNamespace = "StockTransferService", wsdlLocation = "http://192.168.5.75/B1WS/WebReferences/StockTransferService.wsdl")
public class StockTransferService
    extends Service
{

    private final static URL STOCKTRANSFERSERVICE_WSDL_LOCATION;
    private final static WebServiceException STOCKTRANSFERSERVICE_EXCEPTION;
    private final static QName STOCKTRANSFERSERVICE_QNAME = new QName("StockTransferService", "StockTransferService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.5.75/B1WS/WebReferences/StockTransferService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        STOCKTRANSFERSERVICE_WSDL_LOCATION = url;
        STOCKTRANSFERSERVICE_EXCEPTION = e;
    }

    public StockTransferService() {
        super(__getWsdlLocation(), STOCKTRANSFERSERVICE_QNAME);
    }

    public StockTransferService(WebServiceFeature... features) {
        super(__getWsdlLocation(), STOCKTRANSFERSERVICE_QNAME, features);
    }

    public StockTransferService(URL wsdlLocation) {
        super(wsdlLocation, STOCKTRANSFERSERVICE_QNAME);
    }

    public StockTransferService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, STOCKTRANSFERSERVICE_QNAME, features);
    }

    public StockTransferService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StockTransferService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns StockTransferServiceSoap
     */
    @WebEndpoint(name = "StockTransferServiceSoap")
    public StockTransferServiceSoap getStockTransferServiceSoap() {
        return super.getPort(new QName("StockTransferService", "StockTransferServiceSoap"), StockTransferServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StockTransferServiceSoap
     */
    @WebEndpoint(name = "StockTransferServiceSoap")
    public StockTransferServiceSoap getStockTransferServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("StockTransferService", "StockTransferServiceSoap"), StockTransferServiceSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns StockTransferServiceSoap
     */
    @WebEndpoint(name = "StockTransferServiceSoap12")
    public StockTransferServiceSoap getStockTransferServiceSoap12() {
        return super.getPort(new QName("StockTransferService", "StockTransferServiceSoap12"), StockTransferServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StockTransferServiceSoap
     */
    @WebEndpoint(name = "StockTransferServiceSoap12")
    public StockTransferServiceSoap getStockTransferServiceSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("StockTransferService", "StockTransferServiceSoap12"), StockTransferServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (STOCKTRANSFERSERVICE_EXCEPTION!= null) {
            throw STOCKTRANSFERSERVICE_EXCEPTION;
        }
        return STOCKTRANSFERSERVICE_WSDL_LOCATION;
    }

}
