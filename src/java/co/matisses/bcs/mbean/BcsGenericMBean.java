package co.matisses.bcs.mbean;

import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.PrecioVentaItemFacade;
import co.matisses.persistence.sap.facade.SalesPersonFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ygil
 */
@ApplicationScoped
@Named(value = "baruGenericMBean")
public class BcsGenericMBean implements Serializable {

    @Inject
    private BCSApplicationMBean applicationMBean;
    private static final Locale LOCALE_MX = new Locale("es", "MX");
    private static final DecimalFormat FORMATO = (DecimalFormat) NumberFormat.getInstance(LOCALE_MX);
    private static final Logger log = Logger.getLogger(BcsGenericMBean.class.getSimpleName());
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private PrecioVentaItemFacade precioVentaItemFacade;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private SalesPersonFacade salesPersonFacade;

    public BcsGenericMBean() {
    }

    @PostConstruct
    protected void initialize() {
    }

    public BCSApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BCSApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public String convertirARefCorta(String referencia) {
        if (referencia.length() == 20) {
            if (referencia != null && !referencia.isEmpty()) {
                return referencia = referencia.substring(0, 3) + "*" + referencia.substring(16, 20);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public String obtenerNombreReferencia(String referencia) {
        if (referencia != null && !referencia.isEmpty()) {
            ItemInventario item = itemInventarioFacade.find(referencia);
            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                return item.getItemName();
            }
        }
        return "";
    }

    public String completarReferencia(String ref) {
        if (ref != null && (ref.contains("*") || ref.contains("%") || ref.contains("."))) {
            ref = ref.substring(0, 3) + ref.substring(4);
            ref = StringUtils.rightPad(ref.substring(0, 3), 20 - ref.substring(3).length(), "0") + ref.substring(3);
        }
        return ref;
    }

    public Integer obtenerPrecioVenta(String ref) {
        Integer precio = precioVentaItemFacade.findByItemCodeTaxIncluded(ref);

        if (precio != null && precio != 0) {
            return precio;
        } else {
            return 0;
        }
    }

    public String getValorFormateado(String tipoDato, String valor, Integer decimalesVisibles) {
        if (tipoDato != null && valor != null) {
            switch (tipoDato) {
                case "String":
                    return valor;
                case "Integer":
                    try {
                        return FORMATO.format(NumberFormat.getNumberInstance(LOCALE_MX).parse(valor));
                    } catch (Exception e) {
                        return valor;
                    }
                case "Double":
                    try {
                        NumberFormat format = NumberFormat.getInstance(LOCALE_MX);
                        if (decimalesVisibles != null && decimalesVisibles > 0) {
                            //format.setMaximumFractionDigits(decimalesVisibles);
                        }
                        return FORMATO.format(format.parse(valor));
                    } catch (Exception e) {
                        return valor;
                    }
                default:
            }
        }
        return valor;
    }

    public String obtenerAliasUbicacion(Integer absEntry) {
        if (absEntry != null && absEntry != 0) {
            UbicacionSAP u = ubicacionSAPFacade.find(absEntry);

            if (u != null && u.getAbsEntry() != null && u.getAbsEntry() != 0) {
                return u.getAttr2Val();
            }
        }
        return "";
    }

    public String obtenerNombreWebAlmacen(String almacen) {
        if (almacen != null && !almacen.isEmpty()) {
            Almacen alm = almacenFacade.find(almacen);

            if (alm != null && alm.getWhsCode() != null && !alm.getWhsCode().isEmpty()) {
                if (alm.getUnombrextablet() != null && !alm.getUnombrextablet().isEmpty()) {
                    return alm.getUnombrextablet();
                } else {
                    return alm.getWhsCode();
                }
            } else {
                return almacen;
            }
        }
        return "";
    }

    public String obtenerNombreUsuario(String cardCode) {
        if (cardCode != null && !cardCode.isEmpty()) {
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(cardCode);

            if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
                if (socio.getApellido2() != null && !socio.getApellido2().isEmpty()) {
                    return socio.getNombres() + " " + socio.getApellido1() + " " + socio.getApellido2();
                } else {
                    return socio.getNombres() + " " + socio.getApellido1();
                }
            }
        }
        return cardCode;
    }

    public String obtenerNombreAsesor(Integer slpCode) {
        if (slpCode != null && slpCode != 0) {
            String salesPerson = salesPersonFacade.obtenerNombreAsesor(slpCode);

            if (salesPerson != null && !salesPerson.isEmpty()) {
                return salesPerson;
            }

            return "";
        }
        return "";
    }

    public String obtenerRutaFotoEmpleado(String cedula) {
        String urlWeb = applicationMBean.obtenerValorPropiedad("empleados.url.web.image");
        if (urlWeb == null) {
            log.log(Level.SEVERE, "No se encontro el valor de [empleados.url.web.image] en baru.properties");
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage");
        }
        urlWeb = String.format(urlWeb, cedula);

        String url = applicationMBean.obtenerValorPropiedad("empleados.url.local.image");
        if (url != null) {
            url = String.format(url, cedula);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [empleados.url.local.image] en baru.properties");
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.WARNING, "el empleado [{0}] no tiene imagen ", cedula);
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage");
        }
    }

    public String obtenerRutaPDFFactura(String numeroFactura, boolean adjunto) {
        String urlWeb = applicationMBean.obtenerValorPropiedad("url.web.pdf.factura");
        if (urlWeb == null) {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.pdf.factura] en baru.properties");
            return null;
        }
        urlWeb = String.format(urlWeb, numeroFactura);

        String url = applicationMBean.obtenerValorPropiedad("url.local.pdf.factura");
        if (url != null) {
            url = String.format(url, numeroFactura);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.pdf.factura] en baru.properties");
            return null;
        }

        File f = new File(url);
        if (f.exists()) {
            if (adjunto) {
                return f.getPath();
            } else {
                return urlWeb;
            }
        } else {
            log.log(Level.WARNING, "No se encontro la factura [{0}]", numeroFactura);
            return null;
        }
    }

    public String obtenerRutaPDFCotizacion(String numeroCotizacion, boolean adjunto) {
        String urlWeb = applicationMBean.obtenerValorPropiedad("url.web.pdf.cotizacion");
        if (urlWeb == null) {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.pdf.cotizacion] en baru.properties");
            return null;
        }
        urlWeb = String.format(urlWeb, numeroCotizacion);

        String url = applicationMBean.obtenerValorPropiedad("url.local.pdf.cotizacion");
        if (url != null) {
            url = String.format(url, numeroCotizacion);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.pdf.cotizacion] en baru.properties");
            return null;
        }

        File f = new File(url);
        if (f.exists()) {
            if (adjunto) {
                return f.getPath();
            } else {
                return urlWeb;
            }
        } else {
            log.log(Level.WARNING, "No se encontro la cotizacion [{0}]", numeroCotizacion);
            return null;
        }
    }
}
