package co.matisses.bcs.mbean;

import java.io.File;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Path;

/**
 *
 * @author dbotero
 */
@Path("imagenes")
@RequestScoped
@Named(value = "imagenProductoMBean")
public class ImagenProductoMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ImagenProductoMBean.class.getSimpleName());
    @Inject
    private BCSApplicationMBean aplicacionBean;

    public ImagenProductoMBean() {
    }

    public BCSApplicationMBean getAplicacionBean() {
        return aplicacionBean;
    }

    public void setAplicacionBean(BCSApplicationMBean aplicacionBean) {
        this.aplicacionBean = aplicacionBean;
    }

    public String obtenerUrlProducto(String referencia, boolean mini) {
        String sufijoMini = "";
        if (mini) {
            sufijoMini = ".mini";
        }
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.images" + sufijoMini);
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.images[{0}]] en baru.properties", sufijoMini);
            return aplicacionBean.obtenerValorPropiedad(mini ? "url.web.noimage.mini" : "url.web.noimage");
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.images" + sufijoMini);
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.images[{0}]] en baru.properties", sufijoMini);
            return aplicacionBean.obtenerValorPropiedad(mini ? "url.web.noimage.mini" : "url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad(mini ? "url.web.noimage.mini" : "url.web.noimage");
        }
    }

    public String obtenerPlantilla(String referencia) {
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.plantilla");
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.plantilla] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.plantilla");
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.plantilla] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
    }

    public String obtener360(String referencia) {
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.360");
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.360] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.360");
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.360] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
    }

    public String obtenerWOW(String referencia) {
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.wow");
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.wow] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.wow");
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.wow] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
    }
}
