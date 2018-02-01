package co.matisses.bcs.b1ws.ws.login;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "LoginService", targetNamespace = "LoginService", wsdlLocation = "http://192.168.5.75/B1WS/WebReferences/LoginService.wsdl")
public class LoginService
        extends Service {

    private final static URL LOGINSERVICE_WSDL_LOCATION;
    private final static WebServiceException LOGINSERVICE_EXCEPTION;
    private final static QName LOGINSERVICE_QNAME = new QName("LoginService", "LoginService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.5.75/B1WS/WebReferences/LoginService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        LOGINSERVICE_WSDL_LOCATION = url;
        LOGINSERVICE_EXCEPTION = e;
    }

    public LoginService() {
        super(__getWsdlLocation(), LOGINSERVICE_QNAME);
    }

    public LoginService(WebServiceFeature... features) {
        super(__getWsdlLocation(), LOGINSERVICE_QNAME, features);
    }

    public LoginService(URL wsdlLocation) {
        super(wsdlLocation, LOGINSERVICE_QNAME);
    }

    public LoginService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, LOGINSERVICE_QNAME, features);
    }

    public LoginService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LoginService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "LoginServiceSoap")
    public LoginServiceSoap getLoginServiceSoap() {
        return super.getPort(new QName("LoginService", "LoginServiceSoap"), LoginServiceSoap.class);
    }

    @WebEndpoint(name = "LoginServiceSoap")
    public LoginServiceSoap getLoginServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("LoginService", "LoginServiceSoap"), LoginServiceSoap.class, features);
    }

    @WebEndpoint(name = "LoginServiceSoap12")
    public LoginServiceSoap getLoginServiceSoap12() {
        return super.getPort(new QName("LoginService", "LoginServiceSoap12"), LoginServiceSoap.class);
    }

    @WebEndpoint(name = "LoginServiceSoap12")
    public LoginServiceSoap getLoginServiceSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("LoginService", "LoginServiceSoap12"), LoginServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (LOGINSERVICE_EXCEPTION != null) {
            throw LOGINSERVICE_EXCEPTION;
        }
        return LOGINSERVICE_WSDL_LOCATION;
    }

}
