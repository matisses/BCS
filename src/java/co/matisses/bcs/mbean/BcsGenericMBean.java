package co.matisses.bcs.mbean;

import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.PrecioVentaItemFacade;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map.Entry;
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
@Named(value = "bcsGenericMBean")
public class BCSGenericMBean implements Serializable {

    @Inject
    private BCSApplicationMBean applicationMBean;
    private static final Locale LOCALE_MX = new Locale("es", "MX");
    private static final DecimalFormat FORMATO = (DecimalFormat) NumberFormat.getInstance(LOCALE_MX);
    private static final Logger log = Logger.getLogger(BCSGenericMBean.class.getSimpleName());
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private PrecioVentaItemFacade precioVentaItemFacade;

    public BCSGenericMBean() {
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

    public String convertirCaracteresEspeciales(String texto) {
        log.log(Level.INFO, "Actual texto sin conversion de caracteres especiales {0}", texto);
        for (Entry<String, String> caracteres : applicationMBean.getCaracteresEspeciales().entrySet()) {
            if (texto.contains(caracteres.getKey())) {
                texto = texto.replaceAll(caracteres.getKey(), caracteres.getValue());
            }
        }

        log.log(Level.INFO, "Nuevo texto con conversion de caracteres especiales {0}", texto);
        return texto;
    }

    public Integer redondearValor(Integer valor, Integer multiplo) {
        Integer residuo = valor % multiplo;
        if (residuo == 0) {
            return valor;
        } else {
            return valor + (multiplo - residuo);
        }
    }
}