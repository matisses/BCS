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
public class ConsultaCompraListaRegalosDTO {
    
    
     private Long idLista;
     private String referencia;
     
      public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }
    
     public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
