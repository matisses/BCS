package co.matisses.bcs.b1ws.ws.businessparters;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "BusinessPartnersServiceSoap", targetNamespace = "BusinessPartnersService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BusinessPartnersServiceSoap {

    @WebMethod(operationName = "Add", action = "BusinessPartnersService/Add")
    @WebResult(name = "AddResponse", targetNamespace = "http://www.sap.com/SBO/DIS", partName = "parameters")
    public AddResponse add(
            @WebParam(name = "Add", targetNamespace = "BusinessPartnersService", partName = "parameters") Add parameters,
            @WebParam(name = "MsgHeader", targetNamespace = "http://www.sap.com/SBO/DIS", header = true, partName = "request_header") MsgHeader requestHeader);

    @WebMethod(operationName = "GetByParams", action = "BusinessPartnersService/GetByParams")
    @WebResult(name = "GetByParamsResponse", targetNamespace = "http://www.sap.com/SBO/DIS", partName = "parameters")
    public GetByParamsResponse getByParams(
            @WebParam(name = "GetByParams", targetNamespace = "BusinessPartnersService", partName = "parameters") GetByParams parameters,
            @WebParam(name = "MsgHeader", targetNamespace = "http://www.sap.com/SBO/DIS", header = true, partName = "request_header") MsgHeader requestHeader);

    @WebMethod(operationName = "Update", action = "BusinessPartnersService/Update")
    @WebResult(name = "UpdateResponse", targetNamespace = "http://www.sap.com/SBO/DIS", partName = "parameters")
    public UpdateResponse update(
            @WebParam(name = "Update", targetNamespace = "BusinessPartnersService", partName = "parameters") Update parameters,
            @WebParam(name = "MsgHeader", targetNamespace = "http://www.sap.com/SBO/DIS", header = true, partName = "request_header") MsgHeader requestHeader);

    @WebMethod(operationName = "Remove", action = "BusinessPartnersService/Remove")
    @WebResult(name = "RemoveResponse", targetNamespace = "http://www.sap.com/SBO/DIS", partName = "parameters")
    public RemoveResponse remove(
            @WebParam(name = "Remove", targetNamespace = "BusinessPartnersService", partName = "parameters") Remove parameters,
            @WebParam(name = "MsgHeader", targetNamespace = "http://www.sap.com/SBO/DIS", header = true, partName = "request_header") MsgHeader requestHeader);

    @WebMethod(operationName = "CreateOpenBalance", action = "BusinessPartnersService/CreateOpenBalance")
    @WebResult(name = "CreateOpenBalanceResponse", targetNamespace = "http://www.sap.com/SBO/DIS", partName = "parameters")
    public CreateOpenBalanceResponse createOpenBalance(
            @WebParam(name = "CreateOpenBalance", targetNamespace = "BusinessPartnersService", partName = "parameters") CreateOpenBalance parameters,
            @WebParam(name = "MsgHeader", targetNamespace = "http://www.sap.com/SBO/DIS", header = true, partName = "request_header") MsgHeader requestHeader);

    @WebMethod(operationName = "UpdateFromXML", action = "BusinessPartnersService/UpdateFromXML")
    @WebResult(name = "UpdateFromXMLResponse", targetNamespace = "http://www.sap.com/SBO/DIS", partName = "parameters")
    public UpdateFromXMLResponse updateFromXML(
            @WebParam(name = "UpdateFromXML", targetNamespace = "BusinessPartnersService", partName = "parameters") UpdateFromXML parameters,
            @WebParam(name = "MsgHeader", targetNamespace = "http://www.sap.com/SBO/DIS", header = true, partName = "request_header") MsgHeader requestHeader);

}
