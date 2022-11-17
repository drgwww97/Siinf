/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Modificacion;
import Entidades.TModificacion;
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
public class modificacionBeans implements Serializable{

    public List<Modificacion> Modificaciones;
    public List<TModificacion> TModificaciones;
    public List<Modificacion> seleccionadas;
    public Modificacion modificacionesseleccionadas;
    
    @PostConstruct
    public void init() {
        this.Modificaciones = Ctrl.ctrlModificacion.findModificacionEntities();
        this.TModificaciones = Ctrl.ctrlTipoModificaion.findTModificacionEntities();
    }

    public List<Modificacion> getModificaciones() {
        return Modificaciones;
    }

    public void setModificaciones(List<Modificacion> Modificaciones) {
        this.Modificaciones = Modificaciones;
    }

    public List<TModificacion> getTModificaciones() {
        return TModificaciones;
    }

    public void setTModificaciones(List<TModificacion> TModificaciones) {
        this.TModificaciones = TModificaciones;
    }
    
    

    public List<Modificacion> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Modificacion> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Modificacion getModificacionesseleccionadas() {
        return modificacionesseleccionadas;
    }

    public void setModificacionesseleccionadas(Modificacion modificacionesseleccionadas) {
        this.modificacionesseleccionadas = modificacionesseleccionadas;
    }
    
    public void nuevo() {
        this.modificacionesseleccionadas = new Modificacion();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        Modificacion nueva = new Modificacion();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setFecha(modificacionesseleccionadas.getFecha());
            nueva.setTModificacionidTmodificacion(modificacionesseleccionadas.getTModificacionidTmodificacion());
            nueva.setTecnico(modificacionesseleccionadas.getTecnico());
            nueva.setMotivo(modificacionesseleccionadas.getMotivo());
            Ctrl.ctrlModificacion.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modificación Añadida"));
        } else {
            try {
            nueva = Ctrl.ctrlModificacion.findModificacion(modificacionesseleccionadas.getTModificacionidTmodificacion());
            nueva.setFecha(modificacionesseleccionadas.getFecha());
            nueva.setTModificacionidTmodificacion(modificacionesseleccionadas.getTModificacionidTmodificacion());
            nueva.setTecnico(modificacionesseleccionadas.getTecnico());
            nueva.setMotivo(modificacionesseleccionadas.getMotivo());
            Ctrl.ctrlModificacion.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modificación Actualizada"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modificación Actualizada"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-modificacion");
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Modificacion eliminada = new Modificacion();
        eliminada.setTModificacionidTmodificacion(modificacionesseleccionadas.getTModificacionidTmodificacion());
        System.out.println("entro a elmininar");
        System.out.println(modificacionesseleccionadas.getTModificacion());
        Ctrl.ctrlModificacion.destroy(eliminada.getTModificacionidTmodificacion());
        this.modificacionesseleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modificación Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-modificacion");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " modificaciones seleccionadas" : "1 modificación seleccionada";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < Modificaciones.size(); i++) {
            if (modificacionesseleccionadas.getTModificacionidTmodificacion().equals(Modificaciones.get(i).getTModificacionidTmodificacion())) {
                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlModificacion.destroy(seleccionadas.get(i).getTModificacionidTmodificacion());
        }
        this.Modificaciones.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modificación Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-modificacion");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
