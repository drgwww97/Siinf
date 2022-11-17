/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.TConexion;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
/**
 *
 * @author Dayana
 */
@ManagedBean

@ViewScoped
public class tconexionBeans implements Serializable{

    public List<TConexion> TConexiones;
    public List<TConexion> seleccionadas;
    public TConexion tconexionseleccionada;
    
    @PostConstruct
    public void init() {
        this.TConexiones = Ctrl.ctrlTipoConexion.findTConexionEntities();
    }

    public List<TConexion> getTConexiones() {
        return TConexiones;
    }

    public void setTConexiones(List<TConexion> TConexiones) {
        this.TConexiones = TConexiones;
    }

    public List<TConexion> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<TConexion> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public TConexion getTconexionseleccionada() {
        return tconexionseleccionada;
    }

    public void setTconexionseleccionada(TConexion tconexionseleccionada) {
        this.tconexionseleccionada = tconexionseleccionada;
    }
    
    public void nuevo() {
        this.tconexionseleccionada = new TConexion();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        TConexion nueva = new TConexion();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdConexion(tconexionseleccionada.getIdConexion());
            nueva.setNombreConexion(tconexionseleccionada.getNombreConexion());
            Ctrl.ctrlTipoConexion.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Conexión Añadida"));
        } else {
            try {
                nueva = Ctrl.ctrlTipoConexion.findTConexion(tconexionseleccionada.getIdConexion());
            nueva.setIdConexion(tconexionseleccionada.getIdConexion());
            nueva.setNombreConexion(tconexionseleccionada.getNombreConexion());
            Ctrl.ctrlTipoConexion.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Conexión Actualizada"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Conexión Actualizada"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tconexion");
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        TConexion eliminada = new TConexion();
        eliminada.setIdConexion(tconexionseleccionada.getIdConexion());
        System.out.println("entro a elmininar");
        System.out.println(tconexionseleccionada.getIdConexion());
        Ctrl.ctrlTipoConexion.destroy(eliminada.getIdConexion());
        this.tconexionseleccionada = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Conexión Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tconexion");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " tipo de conexión seleccionadas" : "1 tipo de conexión seleccionada";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < TConexiones.size(); i++) {
            if (tconexionseleccionada.getIdConexion().equals(TConexiones.get(i).getIdConexion())) {
                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlTipoConexion.destroy(seleccionadas.get(i).getIdConexion());
        }
        this.TConexiones.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Conexión Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tconexion");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
