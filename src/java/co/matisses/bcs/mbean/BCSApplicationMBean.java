package co.matisses.bcs.mbean;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.dto.EmailTemplateDestinationDTO;
import co.matisses.bcs.dto.SesionSAPB1WSDTO;
import co.matisses.persistence.sap.entity.SalesPerson;
import co.matisses.persistence.sap.facade.SalesPersonFacade;
import co.matisses.persistence.web.entity.BwsSesionSAP;
import co.matisses.persistence.web.entity.DestinatarioPlantillaEmail;
import co.matisses.persistence.web.facade.BwsSesionSAPFacade;
import co.matisses.persistence.web.facade.DestinatarioPlantillaEmailFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author jguisao
 */
@ApplicationScoped
@Named("bcsApplicationBean")
@Path("bcsapplication")
public class BCSApplicationMBean implements Serializable {

    private static final Logger log = Logger.getLogger(BCSApplicationMBean.class.getSimpleName());
    private Properties props = new Properties();
    private SalesPerson empleadoMercadoLibre;
    private SAPSessionManager sapSessionManager = new SAPSessionManager();
    private HashMap<String, EmailTemplateDestinationDTO> destinatariosPlantillaEmail;
    private HashMap<String, SesionSAPB1WSDTO> sesionesSAPActivas;
    @EJB
    private DestinatarioPlantillaEmailFacade destinatarioPlantillaFacade;
    @EJB
    private SalesPersonFacade vendedorFacade;
    @EJB
    private BwsSesionSAPFacade sesionSAPFacade;

    public BCSApplicationMBean() {
        log.info("Iniciando [BCSApplicationMBean]");
        destinatariosPlantillaEmail = new HashMap<>();
        sesionesSAPActivas = new HashMap<>();
    }

    public HashMap<String, EmailTemplateDestinationDTO> getDestinatariosPlantillaEmail() {
        return destinatariosPlantillaEmail;
    }

    @PostConstruct
    @GET
    @Path("recargar/")
    public void initialize() {
        cargarProperties();
        cargarDestinatariosPlantillasEmail();
        cargarEmpleadoMercadoLibre();
    }

    private void cargarEmpleadoMercadoLibre() {
        empleadoMercadoLibre = vendedorFacade.cargarEmpleadoMercadoLibre();
    }

    private void cargarDestinatariosPlantillasEmail() {
        log.log(Level.INFO, "Cargando destinatarios por plantilla para correo");
        destinatariosPlantillaEmail = new HashMap<>();
        try {
            for (DestinatarioPlantillaEmail entidad : destinatarioPlantillaFacade.findAll()) {
                if (destinatariosPlantillaEmail.containsKey(entidad.getNombrePlantilla())) {
                    destinatariosPlantillaEmail.get(entidad.getNombrePlantilla()).addDestinations(entidad.getPara(), entidad.getCopia(), entidad.getCopiaOculta());
                } else {
                    destinatariosPlantillaEmail.put(entidad.getNombrePlantilla(), new EmailTemplateDestinationDTO(entidad.getNombrePlantilla(), entidad.getPara(), entidad.getCopia(), entidad.getCopiaOculta()));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al cargar los destinatarios por plantilla de correo. ", e);
        }
    }

    public void cargarProperties() {
        props = new Properties();
        String serverConfUrl = System.getProperty("jboss.server.config.dir");
        log.log(Level.INFO, "Server config URL [{0}]", serverConfUrl);
        String propertiesFileName = "bcs.properties";
        String path = serverConfUrl + File.separator + propertiesFileName;
        log.log(Level.INFO, "Loading properties file: [{0}]", path);

        try {
            File propsFile = new File(path);
            if (propsFile.exists()) {
                props.load(new FileInputStream(propsFile));
            } else {
                props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + propertiesFileName));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "There was an error loading the file.", e);
        }
    }

    public String obtenerValorPropiedad(String prop) {
        return props.getProperty(prop);
    }

    public SalesPerson getEmpleadoMercadoLibre() {
        return empleadoMercadoLibre;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SesionSAPB1WSDTO obtenerSesionSAP(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return null;
        }
        SesionSAPB1WSDTO sesion = sesionesSAPActivas.get(usuario);
        if (sesion != null) {
            log.log(Level.INFO, "Se encontro la sesion {0} en el mapa de sesiones", sesion);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Si la fecha de la sesion corresponde a la fecha actual, la sesion es valida
            if (sdf.format(sesion.getFecha()).equals(sdf.format(new Date()))) {
                return sesion;
            } else {
                log.log(Level.WARNING, " Esta sesion se encuentra vencida y es necesario generar una nueva");
                sesion = null;
                sesionesSAPActivas.remove(usuario);
            }
        }

        //Se consultan todas las sesiones vencidas del usuario
        List<String> sesionesSAPVencidas = sesionSAPFacade.consultarSesionesVencidasActivas(usuario);
        if (sesionesSAPVencidas != null && !sesionesSAPVencidas.isEmpty()) {
            log.log(Level.WARNING, "Se encontraron {0} sesiones vencidas en base de datos. Se procede a inactivarlas. ", sesionesSAPVencidas.size());
            //Se finalizan todas las sesiones vencidas del usuario
            for (String sesionVencida : sesionesSAPVencidas) {
                try {
                    sapSessionManager.logout(sesionVencida);
                } catch (B1WSServiceUnavailableException e) {
                    if (e.getMessage() != null && e.getMessage().contains("Invalid Session ID")) {
                        log.log(Level.WARNING, "La sesion {0} no es valida en SAP y no se pudo finalizar.", sesionVencida);
                    } else {
                        log.log(Level.SEVERE, "No fue posible comunicarse con el B1WS. ", e);
                        break;
                    }
                }
            }

            //Se inactivan todas las sesiones vencidas del usuario
            sesionSAPFacade.inactivarSesiones(usuario);
        }

        //Consulta si existe una sesion activa
        SesionSAPB1WSDTO dto = obtenerSesionSAPActiva(usuario);
        if (dto != null) {
            log.log(Level.INFO, "El usuario tiene una sesion activa en base de datos. {0}", dto);
            sesionesSAPActivas.put(usuario, dto);
            return dto;
        }

        try {
            String sessionId = sapSessionManager.login();

            BwsSesionSAP sesionEntidad = new BwsSesionSAP();
            sesionEntidad.setEstado("A");
            sesionEntidad.setFecha(new Date());
            sesionEntidad.setIdSesionSAP(sessionId);
            sesionEntidad.setUsuario(usuario);
            //sesionEntidad.setIdSesionBCS(RandomStringUtils.randomAlphanumeric(20));

            sesionSAPFacade.create(sesionEntidad);

            dto = obtenerSesionSAPActiva(usuario);
            sesionesSAPActivas.put(usuario, dto);

            log.log(Level.INFO, "Se creo la sesion SAP {0}", dto);
            return dto;
        } catch (B1WSServiceUnavailableException e) {
            log.log(Level.SEVERE, "No fue posible iniciar sesion en B1WS. ", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible registrar la sesion. ", e);
        }
        return null;
    }

    private SesionSAPB1WSDTO obtenerSesionSAPActiva(String usuario) {
        Object[] data = sesionSAPFacade.consultarSesionActiva(usuario);
        if (data == null) {
            return null;
        }
        SesionSAPB1WSDTO dto = new SesionSAPB1WSDTO();
        dto.setId((Integer) data[0]);
        dto.setUsuario((String) data[1]);
        dto.setFecha((Date) data[2]);
        dto.setIdSesionSAP((String) data[3]);
        dto.setEstado((String) data[4]);

        return dto;
    }
}
