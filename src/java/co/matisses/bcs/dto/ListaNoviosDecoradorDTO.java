package co.matisses.bcs.dto;

import co.matisses.bcs.util.ObjectUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jguisao
 */
public class ListaNoviosDecoradorDTO {

    private Integer codigoDecorador;
    private Integer idLista;
    private Integer puntos;
    private String codigo;
    private String novios;
    private String formateoFechaEvento;

    public ListaNoviosDecoradorDTO() {
    }

    public ListaNoviosDecoradorDTO(Object[] parametros) {
        this.idLista = (Integer) parametros[0];
        this.codigoDecorador = (Integer) parametros[1];
        this.codigo = codigo = (String) parametros[2];
        this.novios = novios = (String) parametros[3];
        this.formateoFechaEvento = new SimpleDateFormat("dd | MM | yyyy").format((Date) parametros[4]);
        this.puntos = (Integer) parametros[5];
    }

    public Integer getCodigoDecorador() {
        return codigoDecorador;
    }

    public void setCodigoDecorador(Integer codigoDecorador) {
        this.codigoDecorador = codigoDecorador;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNovios() {
        return novios;
    }

    public void setNovios(String novios) {
        this.novios = novios;
    }

    public String getFormateoFechaEvento() {
        return formateoFechaEvento;
    }

    public void setFormateoFechaEvento(String formateoFechaEvento) {
        this.formateoFechaEvento = formateoFechaEvento;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getILista() {
        return idLista;
    }

    public void setIdLista(Integer idLista) {
        this.idLista = idLista;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}