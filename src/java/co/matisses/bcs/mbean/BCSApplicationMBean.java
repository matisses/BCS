package co.matisses.bcs.mbean;

import co.matisses.bcs.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.bcs.b1ws.client.SAPSessionManager;
import co.matisses.bcs.dto.EmailTemplateDestinationDTO;
import co.matisses.bcs.dto.InformacionAlmacenDTO;
import co.matisses.bcs.dto.SesionListaRegalosDTO;
import co.matisses.bcs.dto.SesionSAPB1WSDTO;
import co.matisses.bcs.ldap.BaruLDAPAuth;
import co.matisses.persistence.sap.entity.SalesPerson;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.SalesPersonFacade;
import co.matisses.persistence.web.BwsSecurityManager;
import co.matisses.persistence.web.entity.BwsSesionSAP;
import co.matisses.persistence.web.entity.TipoTarjetaPlaceToPay;
import co.matisses.persistence.web.facade.BwsSesionSAPFacade;
import co.matisses.persistence.web.facade.BwsTerminalPOSFacade;
import co.matisses.persistence.web.facade.DestinatarioPlantillaEmailFacade;
import co.matisses.persistence.web.facade.TipoTarjetaPlaceToPayFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static final Logger CONSOLE = Logger.getLogger(BCSApplicationMBean.class.getSimpleName());
    private static final long DIA_EN_MILLIS = 86400000;
    private Properties props = new Properties();
    private SalesPerson empleadoMercadoLibre;
    private final SAPSessionManager sapSessionManager = new SAPSessionManager();
    private List<String> terminalesPOS;
    private HashMap<String, EmailTemplateDestinationDTO> destinatariosPlantillaEmail;
    private final HashMap<String, SesionSAPB1WSDTO> sesionesSAPActivas;
    private HashMap<String, TipoTarjetaPlaceToPay> tiposTarjetaP2P;
    private final HashMap<String, InformacionAlmacenDTO> infoAlmacen;
    private Map<String, String> caracteresEspeciales;
    private HashMap<String, SesionListaRegalosDTO> sesionesListaRegalosActivas;
    @EJB
    private DestinatarioPlantillaEmailFacade destinatarioPlantillaFacade;
    @EJB
    private SalesPersonFacade vendedorFacade;
    @EJB
    private BwsSesionSAPFacade sesionSAPFacade;
    @EJB
    private TipoTarjetaPlaceToPayFacade tipoTarjetaP2pFacade;
    @EJB
    private BwsTerminalPOSFacade terminalPOSFacade;
    @EJB
    private BwsSecurityManager securityManager;
    @EJB
    private BaruLDAPAuth ldapManager;
    @EJB
    private AlmacenFacade almacenFacade;

    public BCSApplicationMBean() {
        CONSOLE.info("Iniciando [BCSApplicationMBean]");
        terminalesPOS = new ArrayList<>();
        destinatariosPlantillaEmail = new HashMap<>();
        sesionesSAPActivas = new HashMap<>();
        tiposTarjetaP2P = new HashMap<>();
        caracteresEspeciales = new HashMap<>();
        infoAlmacen = new HashMap<>();
    }

    public HashMap<String, EmailTemplateDestinationDTO> getDestinatariosPlantillaEmail() {
        return destinatariosPlantillaEmail;
    }

    @PostConstruct
    @GET
    @Path("recargar/")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void initialize() {
        cargarProperties();
        cargarDestinatariosPlantillasEmail();
        cargarEmpleadoMercadoLibre();
        cargarTiposTarjetaP2P();
        cargarCaracteresEspeciales();
        cargarUbicacionesTM();
    }

    public TipoTarjetaPlaceToPay getTipoTarjetaP2P(String idTarjetaP2P) {
        return tiposTarjetaP2P.get(idTarjetaP2P);
    }

    public Map<String, String> getCaracteresEspeciales() {
        return caracteresEspeciales;
    }

    public HashMap<String, InformacionAlmacenDTO> getInfoAlmacen() {
        return infoAlmacen;
    }

    public InformacionAlmacenDTO getInfoAlmacen(String whsCode) {
        return infoAlmacen.get(whsCode);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void cargarEmpleadoMercadoLibre() {
        empleadoMercadoLibre = vendedorFacade.cargarEmpleadoMercadoLibre();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void cargarDestinatariosPlantillasEmail() {
        CONSOLE.log(Level.INFO, "Cargando destinatarios por plantilla para correo");
        destinatariosPlantillaEmail = new HashMap<>();
        try {
            destinatarioPlantillaFacade.findAll().stream().forEach((entidad) -> {
                if (destinatariosPlantillaEmail.containsKey(entidad.getNombrePlantilla())) {
                    destinatariosPlantillaEmail.get(entidad.getNombrePlantilla()).addDestinations(entidad.getPara(), entidad.getCopia(), entidad.getCopiaOculta());
                } else {
                    destinatariosPlantillaEmail.put(entidad.getNombrePlantilla(), new EmailTemplateDestinationDTO(entidad.getNombrePlantilla(), entidad.getPara(), entidad.getCopia(), entidad.getCopiaOculta()));
                }
            });
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al cargar los destinatarios por plantilla de correo. ", e);
        }
    }

    public void cargarProperties() {
        props = new Properties();
        String serverConfUrl = System.getProperty("jboss.server.config.dir");
        CONSOLE.log(Level.INFO, "Server config URL [{0}]", serverConfUrl);
        String propertiesFileName = "bcs.properties";
        String path = serverConfUrl + File.separator + propertiesFileName;
        CONSOLE.log(Level.INFO, "Loading properties file: [{0}]", path);

        try {
            File propsFile = new File(path);
            if (propsFile.exists()) {
                FileInputStream input = new FileInputStream(propsFile);
                props.load(new InputStreamReader(input, Charset.forName("UTF-8")));

                //props.load(new FileInputStream(propsFile));
            } else {
                props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + propertiesFileName));
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "There was an error loading the file.", e);
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
            CONSOLE.log(Level.INFO, "Se encontro la sesion {0} en el mapa de sesiones", sesion);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Si la fecha de la sesion corresponde a la fecha actual, la sesion es valida
            if (sdf.format(sesion.getFecha()).equals(sdf.format(new Date()))) {
                return sesion;
            } else {
                CONSOLE.log(Level.WARNING, " Esta sesion se encuentra vencida y es necesario generar una nueva");
                sesion = null;
                sesionesSAPActivas.remove(usuario);
            }
        }

        //Se consultan todas las sesiones vencidas del usuario
        List<String> sesionesSAPVencidas = sesionSAPFacade.consultarSesionesVencidasActivas(usuario);
        if (sesionesSAPVencidas != null && !sesionesSAPVencidas.isEmpty()) {
            CONSOLE.log(Level.WARNING, "Se encontraron {0} sesiones vencidas en base de datos. Se procede a inactivarlas. ", sesionesSAPVencidas.size());
            //Se finalizan todas las sesiones vencidas del usuario
            for (String sesionVencida : sesionesSAPVencidas) {
                try {
                    sapSessionManager.logout(sesionVencida);
                } catch (B1WSServiceUnavailableException e) {
                    if (e.getMessage() != null && e.getMessage().contains("Invalid Session ID")) {
                        CONSOLE.log(Level.WARNING, "La sesion {0} no es valida en SAP y no se pudo finalizar.", sesionVencida);
                    } else {
                        CONSOLE.log(Level.SEVERE, "No fue posible comunicarse con el B1WS. ", e);
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
            CONSOLE.log(Level.INFO, "El usuario tiene una sesion activa en base de datos. {0}", dto);
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

            CONSOLE.log(Level.INFO, "Se creo la sesion SAP {0}", dto);
            return dto;
        } catch (B1WSServiceUnavailableException e) {
            CONSOLE.log(Level.SEVERE, "No fue posible iniciar sesion en B1WS. ", e);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "No fue posible registrar la sesion. ", e);
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

    private void cargarTiposTarjetaP2P() {
        CONSOLE.log(Level.INFO, "Cargando tipos de tarjetas de placetopay");
        tiposTarjetaP2P = new HashMap<>();
        try {
            tipoTarjetaP2pFacade.findAll().stream().forEach((entidad) -> {
                tiposTarjetaP2P.put(entidad.getIdTarjetaPlaceToPay(), entidad);
            });
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al consultar los tipos de tarjetas de placetopay. ", e);
        }
    }

    private void cargarCaracteresEspeciales() {
        caracteresEspeciales = new HashMap<>();

        String[] caracteres = obtenerValorPropiedad("caracteres.especiales").split("_");

        for (String c : caracteres) {
            String[] s1 = c.split(",");

            caracteresEspeciales.put(s1[0], s1[1]);
        }
    }

    public void recargarTerminalesPOSAutorizadas() {
        cargarTerminalesPOSAutorizadas();
    }

    private void cargarTerminalesPOSAutorizadas() {
        CONSOLE.log(Level.INFO, "Cargando lista de terminales POS autorizadas");
        terminalesPOS = new ArrayList<>();

        terminalPOSFacade.listarTerminalesActivas().stream().forEach((entidad) -> {
            terminalesPOS.add(entidad.getIp());
        });
    }

    public boolean usuarioPuede(String usuario, String psswd, String accion, String objeto) {
        boolean usuarioValido;
        String claveAlmacenada = securityManager.consultarClaveUsuario(usuario);
        String claveCodificada = codificarClave(psswd);
        if (claveAlmacenada != null && claveAlmacenada.equals(claveCodificada)) {
            usuarioValido = true;
        } else {
            usuarioValido = ldapManager.authenticateUser(usuario, psswd);
        }
        if (usuarioValido) {
            return securityManager.validarPermisoUsuario(usuario, accion, objeto);
        }
        return false;
    }

    public String codificarClave(String clave) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(clave.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return null;
    }

    public void cargarUbicacionesTM() {
        CONSOLE.log(Level.INFO, "Cargando ubicaciones TM");
        List<Object[]> datos = almacenFacade.consultarInfoAlmacenes();
        if (datos.isEmpty()) {
            return;
        }
        datos.stream().forEach((row) -> {
            infoAlmacen.put((String) row[0], new InformacionAlmacenDTO((String) row[0], (Integer) row[1], (String) row[2]));
        });
    }

    public SesionListaRegalosDTO obtenerSesionListaRegalos(String sessionId) {
        //La sesion debe tener una vigencia de 24 horas. Despues de eso se debe inactivar
        SesionListaRegalosDTO sesion = sesionesListaRegalosActivas.get(sessionId);
        if (sesion == null) {
            //TODO: consultar sesion en base de datos y cargar al mapa en memoria
        }
        if (sesion != null && System.currentTimeMillis() - sesion.getFechaCreacion().getTime() > DIA_EN_MILLIS) {
            sesion.setSesionValida(false);
        }
        return sesion;
    }

    public void finalizarSesionSAP(SesionSAPB1WSDTO sesion) {
        try {
            BwsSesionSAP entidad = new BwsSesionSAP();
            entidad.setId(sesion.getId());
            entidad.setFecha(sesion.getFecha());
            entidad.setIdSesionSAP(sesion.getIdSesionSAP());
            entidad.setUsuario(sesion.getUsuario());
            entidad.setEstado("I");
            sesionSAPFacade.edit(entidad);
            sesionesSAPActivas.remove(sesion.getUsuario());
            sapSessionManager.logout(sesion.getIdSesionSAP());
        } catch (Exception e) {
            CONSOLE.log(Level.WARNING, "Ocurrio un error al finalizar la sesion {0} en sap. ", e);
        }
    }

    public void agregarSesionListaRegalos(String sessionId, SesionListaRegalosDTO dto) {
        CONSOLE.log(Level.INFO, "Agregando identificador de session ListaRegalos [{0}] al mapa de sesiones activas", sessionId);
        sesionesListaRegalosActivas.put(sessionId, dto);
    }

    public void finalizarSesionListaRegalos(String sessionId) {
        sesionesListaRegalosActivas.remove(sessionId);
    }
}