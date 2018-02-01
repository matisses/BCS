/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.matisses.bcs.dto;



/**
 *
 * @author mperdomo
 */
public class ConsultaUsuarioResponseDTO {
    
    private int codigo;
    private String mensaje;
    private UsuarioPaginaDTO usuarioPaginaDTO;
    private String tokenSession;
    
    public ConsultaUsuarioResponseDTO() {
    }

    public ConsultaUsuarioResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ConsultaUsuarioResponseDTO(int codigo, String mensaje, UsuarioPaginaDTO usuarioPaginaDTO) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.usuarioPaginaDTO = usuarioPaginaDTO;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public UsuarioPaginaDTO getUsuarioPaginaDTO() {
        return usuarioPaginaDTO;
    }

    public void setUsuarioPaginaDTO(UsuarioPaginaDTO usuario) {
        this.usuarioPaginaDTO = usuario;
    }
    
    public String getTokenSession() {
        return tokenSession;
    }

    public void setTokenSession(String token) {
        this.tokenSession= token;
    }
    
}
