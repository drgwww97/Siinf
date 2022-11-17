/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
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
public class tmodificacionBeans {

    public List<TModificacion> TModificaciones;
    public List<TModificacion> seleccionadas;
    public TModificacion tmodoficacionseleccionadas;
    
    @PostConstruct
    public void init() {
        this.TModificaciones = Ctrl.ctrlTipoModificaion.findTModificacionEntities();
    }

    public List<TModificacion> getTModificaciones() {
        return TModificaciones;
    }

    public void setTModificaciones(List<TModificacion> TModificaciones) {
        this.TModificaciones = TModificaciones;
    }

    public List<TModificacion> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<TModificacion> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public TModificacion getTmodoficacionseleccionadas() {
        return tmodoficacionseleccionadas;
    }

    public void setTmodoficacionseleccionadas(TModificacion tmodoficacionseleccionadas) {
        this.tmodoficacionseleccionadas = tmodoficacionseleccionadas;
    }
    
    public void nuevo() {
        this.tmodoficacionseleccionadas = new TModificacion();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        TModificacion nueva = new TModificacion();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdTmodificacion(tmodoficacionseleccionadas.getIdTmodificacion());
            nueva.setNModificacion(tmodoficacionseleccionadas.getNModificacion());
            Ctrl.ctrlTipoModificaion.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Modificación Añadida"));
        } else {
            try {
            nueva = Ctrl.ctrlTipoModificaion.findTModificacion(tmodoficacionseleccionadas.getIdTmodificacion());
            nueva.setIdTmodificacion(tmodoficacionseleccionadas.getIdTmodificacion());
            nueva.setNModificacion(tmodoficacionseleccionadas.getNModificacion());
            Ctrl.ctrlTipoModificaion.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Modificación Actualizada"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Modificación Actualizada"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tmodificaciones");
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        TModificacion eliminada = new TModificacion();
        eliminada.setIdTmodificacion(tmodoficacionseleccionadas.getIdTmodificacion());
        System.out.println("entro a elmininar");
        System.out.println(tmodoficacionseleccionadas.getIdTmodificacion());
        Ctrl.ctrlTipoModificaion.destroy(eliminada.getIdTmodificacion());
        this.tmodoficacionseleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Modificación Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tmodificaciones");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " tipo de modificaciones seleccionadas" : "1 tipo de modificación seleccionada";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < TModificaciones.size(); i++) {
            if (tmodoficacionseleccionadas.getIdTmodificacion().equals(TModificaciones.get(i).getIdTmodificacion())) {
                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlTipoModificaion.destroy(seleccionadas.get(i).getIdTmodificacion());
        }
        this.TModificaciones.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Modificación Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tmodificaciones");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
    
}
