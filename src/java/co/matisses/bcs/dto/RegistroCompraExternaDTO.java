package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jguisao
 */
public class RegistroCompraExternaDTO {

    private String docNumFV;
    private String codigoLista;
    private String mensaje;
    private List<ProductoCompraExternaDTO> productos;

    public RegistroCompraExternaDTO() {
        productos = new ArrayList<>();
    }

    public String getCodigoLista() {
        return codigoLista;
    }

    public void setCodigoLista(String codigoLista) {
        this.codigoLista = codigoLista;
    }

    public String getDocNumFV() {
        return docNumFV;
    }

    public void setDocNumFV(String docNumFV) {
        this.docNumFV = docNumFV;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<ProductoCompraExternaDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCompraExternaDTO> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}