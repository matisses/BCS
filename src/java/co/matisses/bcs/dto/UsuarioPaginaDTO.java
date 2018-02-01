/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author mperdomo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioPaginaDTO {
    
    
    private Long usuarioId;
   
    private String nombreUsuario;
    
    private String password;
    
    
    private Boolean esLista;
    
    private Date fechaRegistro;
    
    private Boolean estado;
    
    private Boolean esNuevo;
    
    private String token;
    
    private String nombre;
    
    private ListaRegalosDTO idListaRegalos;
    

    public UsuarioPaginaDTO() {
    }
    
    
     public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Boolean getEsLista() {
        return esLista;
    }

    public void setEsLista(Boolean esLista) {
        this.esLista = esLista;
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public Boolean getEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(Boolean esNuevo) {
        this.esNuevo = esNuevo;
    }
    
     public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token= token;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre= nombre;
    }
    
    public ListaRegalosDTO getIdListaRegalos() {
        return idListaRegalos;
    }

    public void setIdListaRegalos(ListaRegalosDTO listaRegalosDTO) {
        this.idListaRegalos= listaRegalosDTO;
    }
    
}
