package co.matisses.bcs.b1ws.ws.businessparters;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "BusinessPartnersService", targetNamespace = "BusinessPartnersService", wsdlLocation = "http://192.168.5.76/B1WS/WebReferences/BusinessPartnersService.wsdl")
public class BusinessPartnersService
        extends Service {

    private final static URL BUSINESSPARTNERSSERVICE_WSDL_LOCATION;
    private final static WebServiceException BUSINESSPARTNERSSERVICE_EXCEPTION;
    private final static QName BUSINESSPARTNERSSERVICE_QNAME = new QName("BusinessPartnersService", "BusinessPartnersService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.5.76/B1WS/WebReferences/BusinessPartnersService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BUSINESSPARTNERSSERVICE_WSDL_LOCATION = url;
        BUSINESSPARTNERSSERVICE_EXCEPTION = e;
    }

    public BusinessPartnersService() {
        super(__getWsdlLocation(), BUSINESSPARTNERSSERVICE_QNAME);
    }

    public BusinessPartnersService(WebServiceFeature... features) {
        super(__getWsdlLocation(), BUSINESSPARTNERSSERVICE_QNAME, features);
    }

    public BusinessPartnersService(URL wsdlLocation) {
        super(wsdlLocation, BUSINESSPARTNERSSERVICE_QNAME);
    }

    public BusinessPartnersService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BUSINESSPARTNERSSERVICE_QNAME, features);
    }

    public BusinessPartnersService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BusinessPartnersService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "BusinessPartnersServiceSoap")
    public BusinessPartnersServiceSoap getBusinessPartnersServiceSoap() {
        return super.getPort(new QName("BusinessPartnersService", "BusinessPartnersServiceSoap"), BusinessPartnersServiceSoap.class);
    }

    @WebEndpoint(name = "BusinessPartnersServiceSoap")
    public BusinessPartnersServiceSoap getBusinessPartnersServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("BusinessPartnersService", "BusinessPartnersServiceSoap"), BusinessPartnersServiceSoap.class, features);
    }

    @WebEndpoint(name = "BusinessPartnersServiceSoap12")
    public BusinessPartnersServiceSoap getBusinessPartnersServiceSoap12() {
        return super.getPort(new QName("BusinessPartnersService", "BusinessPartnersServiceSoap12"), BusinessPartnersServiceSoap.class);
    }

    @WebEndpoint(name = "BusinessPartnersServiceSoap12")
    public BusinessPartnersServiceSoap getBusinessPartnersServiceSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("BusinessPartnersService", "BusinessPartnersServiceSoap12"), BusinessPartnersServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BUSINESSPARTNERSSERVICE_EXCEPTION != null) {
            throw BUSINESSPARTNERSSERVICE_EXCEPTION;
        }
        return BUSINESSPARTNERSSERVICE_WSDL_LOCATION;
    }

}
