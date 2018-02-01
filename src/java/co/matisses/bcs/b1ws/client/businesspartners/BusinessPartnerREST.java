package co.matisses.bcs.b1ws.client.businesspartners;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.b1ws.ws.businessparters.BusinessPartner;
import co.matisses.bcs.dto.ResponseDTO;
import co.matisses.bcs.mbean.SAPB1WSBean;
import co.matisses.persistence.sap.entity.DepartamentoPK;
import co.matisses.persistence.sap.entity.DireccionSocioDeNegocios;
import co.matisses.persistence.sap.entity.DireccionSocioDeNegociosPK;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.BaruMunicipiosFacade;
import co.matisses.persistence.sap.facade.DepartamentoSAPFacade;
import co.matisses.persistence.sap.facade.DireccionSocioDeNegociosFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Path("businesspartner")
public class BusinessPartnerREST {

    private static final Logger console = Logger.getLogger(BusinessPartnerREST.class.getSimpleName());

    @Inject
    private SAPB1WSBean sapB1WSBean;
    private SAPSessionManager sessionManager = new SAPSessionManager();
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private DireccionSocioDeNegociosFacade direccionSocioDeNegociosFacade;
    @EJB
    private DepartamentoSAPFacade departamentoSAPFacade;
    @EJB
    private BaruMunicipiosFacade municipiosFacade;

    @POST
    @Path("create/{usuario}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createBusinessPartner(@PathParam("usuario") String usuario, BusinessPartnerDTO dto) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();
        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }
        if ((dto.getAddresses().get(0).getCityName() == null || dto.getAddresses().get(0).getCityName().isEmpty()//Ciudad direccion Factura
                || dto.getAddresses().get(1).getCityName() == null || dto.getAddresses().get(1).getCityName().isEmpty())) {//Ciudad direccion Entrega
            String ciudad = municipiosFacade.consultarNombreCiudad(dto.getAddresses().get(0).getCityCode());
            for (int i = 0; i < dto.getAddresses().size(); i++) {
                dto.getAddresses().get(i).setCityName(ciudad);
            }
        }
        try {
            BusinessPartnersServiceConnector bpsc = sapB1WSBean.getBusinessPartnersServiceConnectorInstance(sesionId);
            bpsc.createBusinessPartner(dto);
            return Response.ok(new ResponseDTO(0, "")).build();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al crear el cliente. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response find(@PathParam("id") String id) {
        console.log(Level.INFO, "Buscando cliente por id [{0}]", id);
        if (id == null || id.trim().isEmpty()) {
            return Response.serverError().build();
        }
        try {
            SocioDeNegocios entidad = socioDeNegociosFacade.findByCardCode(id);
            if (entidad == null) {
                return Response.ok(entidad).build();
            }

            BusinessPartnerDTO bpDto = new BusinessPartnerDTO();
            bpDto.setBirthDate(entidad.getFechaNacimiento() != null ? new SimpleDateFormat("yyyy-MM-dd").format(entidad.getFechaNacimiento()) : null);
            bpDto.setCardCode(entidad.getCardCode());
            bpDto.setCardName(entidad.getRazonSocial());
            bpDto.setCardType(BusinessPartnerDTO.CardType.getType(entidad.getCardType().toString()));
            bpDto.setDefaultBillingAddress(entidad.getDireccionEstandarFactura());
            bpDto.setDefaultShippingAddress(entidad.getDireccionEstandarEntrega());
            bpDto.setFirstName(entidad.getNombres());
            bpDto.setFiscalID(entidad.getLicTradNum());
            bpDto.setFiscalIdType(entidad.getTipoDocumento());
            bpDto.setForeignType(BusinessPartnerDTO.ForeignType.getType("01"));
            bpDto.setGender(BusinessPartnerDTO.Gender.getGender(entidad.getSexo()));
            bpDto.setLastName1(entidad.getApellido1());
            bpDto.setLastName2(entidad.getApellido2());
            if (entidad.getNacionalidad().equals("01")) {
                bpDto.setNationality(BusinessPartnerDTO.Nationality.NATIONAL);
            } else if (entidad.getNacionalidad().equals("02")) {
                bpDto.setNationality(BusinessPartnerDTO.Nationality.FOREIGN);
            }
            bpDto.setPersonType(BusinessPartnerDTO.PersonType.getType(entidad.getTipoPersona()));
            bpDto.setSalesPersonCode(entidad.getSlpCode());
            bpDto.setSelfRetainer(entidad.getAutorretenedor());
            bpDto.setTaxRegime(BusinessPartnerDTO.TaxRegime.getType(entidad.getRegimenTributario()));

            DireccionSocioDeNegocios direccionFactura = null;
            if (entidad.getDireccionEstandarFactura() != null) {
                DireccionSocioDeNegociosPK searchKey = new DireccionSocioDeNegociosPK(entidad.getDireccionEstandarFactura(), entidad.getCardCode(), 'B');
                direccionFactura = direccionSocioDeNegociosFacade.find(searchKey);
            }

            if (direccionFactura != null) {
                BusinessPartnerAddressDTO address = new BusinessPartnerAddressDTO();
                address.setAddress(direccionFactura.getStreet());
                address.setAddressName(direccionFactura.getDireccionSocioDeNegociosPK().getAddress());
                address.setAddressType(BusinessPartnerAddressDTO.AddressType.BILLING);
                address.setCellphone(direccionFactura.getBuilding());
                address.setCityCode(direccionFactura.getUMunicipio());
                address.setCityName(direccionFactura.getCity());
                address.setCountry(direccionFactura.getCountry());
                address.setEmail(direccionFactura.getCounty());
                address.setLandLine(direccionFactura.getBlock());
                address.setStateCode(direccionFactura.getState());
                try {
                    address.setStateName(departamentoSAPFacade.find(new DepartamentoPK(direccionFactura.getState(), "CO")).getName());
                } catch (Exception e) {
                }
                address.setTaxCode(direccionFactura.getTaxCode());
                bpDto.addAddress(address);
            }

            return Response.ok(bpDto).build();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al consultar o procesar los datos del cliente " + id, e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("find/{id}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response findClient(@PathParam("id") String id) throws B1WSServiceUnavailableException {
        if (!id.contains("CL")) {
            id += "CL";
        }
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            BusinessPartnersServiceConnector bpsc = sapB1WSBean.getBusinessPartnersServiceConnectorInstance(sesionId);

            BusinessPartner client = bpsc.findBusinessPartner(id);
            return Response.ok(client).build();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al consultar el cliente. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }

    @POST
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response findClient(BusinessPartner bussiness) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            BusinessPartnersServiceConnector bpsc = sapB1WSBean.getBusinessPartnersServiceConnectorInstance(sesionId);

            bpsc.createBusinessPartner(bussiness);
            return Response.ok(new ResponseDTO(0)).build();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al crear el cliente. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }

    @POST
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response editClient(BusinessPartner bussiness) throws B1WSServiceUnavailableException {
        String sesionId = sessionManager.login();

        if (sesionId == null) {
            console.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ResponseDTO(0, "No fue posible iniciar una sesión en SAP B1WS.")).build();
        }

        try {
            BusinessPartnersServiceConnector bpsc = sapB1WSBean.getBusinessPartnersServiceConnectorInstance(sesionId);

            bpsc.editBusinessPartner(bussiness);
            return Response.ok(new ResponseDTO(0)).build();
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al editar el cliente. ", e);
            return Response.ok(new ResponseDTO(-1, e.getMessage())).build();
        } finally {
            sessionManager.logout(sesionId);
        }
    }
}
