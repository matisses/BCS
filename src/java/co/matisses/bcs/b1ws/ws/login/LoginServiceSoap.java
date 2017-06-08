package co.matisses.bcs.b1ws.ws.login;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "LoginServiceSoap", targetNamespace = "LoginService")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface LoginServiceSoap {

    @WebMethod(operationName = "Login", action = "LoginService/Login")
    @WebResult(name = "SessionID", targetNamespace = "http://www.sap.com/SBO/DIS")
    @RequestWrapper(localName = "Login", targetNamespace = "LoginService", className = "co.matisses.b1ws.login.Login")
    @ResponseWrapper(localName = "LoginResponse", targetNamespace = "http://www.sap.com/SBO/DIS", className = "co.matisses.b1ws.login.LoginResponse")
    public String login(
            @WebParam(name = "DatabaseServer", targetNamespace = "LoginService") String databaseServer,
            @WebParam(name = "DatabaseName", targetNamespace = "LoginService") String databaseName,
            @WebParam(name = "DatabaseType", targetNamespace = "LoginService") String databaseType,
            @WebParam(name = "CompanyUsername", targetNamespace = "LoginService") String companyUsername,
            @WebParam(name = "CompanyPassword", targetNamespace = "LoginService") String companyPassword,
            @WebParam(name = "Language", targetNamespace = "LoginService") String language,
            @WebParam(name = "LicenseServer", targetNamespace = "LoginService") String licenseServer);

    @WebMethod(operationName = "Logout", action = "LoginService/Logout")
    @WebResult(name = "LogoutResponse", targetNamespace = "http://www.sap.com/SBO/DIS", partName = "parameters")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public LogoutResponse logout(
            @WebParam(name = "Logout", targetNamespace = "LoginService", partName = "parameters") Logout parameters,
            @WebParam(name = "MsgHeader", targetNamespace = "http://www.sap.com/SBO/DIS", header = true, partName = "request_header") MsgHeader requestHeader);

}
