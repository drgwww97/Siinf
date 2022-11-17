/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Entidad;
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
 * @author David Ruiz
 */
@ManagedBean

@ViewScoped

public class entidadBeans implements Serializable {

    public List<Entidad> Entidades;
    public List<Entidad> seleccionadas;
    public Entidad entidadseleccionada;
    
    @PostConstruct
    public void init() {
        this.Entidades = Ctrl.ctrlEntidad.findEntidadEntities();
     
    }

    public List<Entidad> getEntidades() {
        return Entidades;
    }

    public void setEntidades(List<Entidad> Entidades) {
        this.Entidades = Entidades;
    }

    public List<Entidad> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Entidad> Seleccionadas) {
        this.seleccionadas = Seleccionadas;
    }

    public Entidad getEntidadseleccionada() {
        return entidadseleccionada;
    }

    public void setEntidadseleccionada(Entidad entidadseleccionada) {
        this.entidadseleccionada = entidadseleccionada;
    }

    public void nuevo() {
        this.entidadseleccionada = new Entidad();
    }

    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        Entidad nueva = new Entidad();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdEntidad(entidadseleccionada.getIdEntidad());
            nueva.setNombreEntidad(entidadseleccionada.getNombreEntidad());
            nueva.setDireccionEntidad(entidadseleccionada.getDireccionEntidad());
            nueva.setNotelefono(entidadseleccionada.getNotelefono());
            Ctrl.ctrlEntidad.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unidad Añadida"));
        } else {
            try {
                nueva = Ctrl.ctrlEntidad.findEntidad(entidadseleccionada.getIdEntidad());
            nueva.setIdEntidad(entidadseleccionada.getIdEntidad());
            nueva.setNombreEntidad(entidadseleccionada.getNombreEntidad());
            nueva.setDireccionEntidad(entidadseleccionada.getDireccionEntidad());
            nueva.setNotelefono(entidadseleccionada.getNotelefono());
            Ctrl.ctrlEntidad.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unidad Actualizada"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unidad Actualizada"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-entidades");
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Entidad eliminada = new Entidad();
        eliminada.setIdEntidad(entidadseleccionada.getIdEntidad());
        System.out.println("entro a elmininar");
        System.out.println(entidadseleccionada.getIdEntidad());
        Ctrl.ctrlEntidad.destroy(eliminada.getIdEntidad());
        this.entidadseleccionada = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unidad Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-entidades");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " unidades seleccionadas" : "1 unidad seleccionada";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < Entidades.size(); i++) {
            if (entidadseleccionada.getIdEntidad().equals(Entidades.get(i).getIdEntidad())) {
                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlEntidad.destroy(seleccionadas.get(i).getIdEntidad());
        }
        this.Entidades.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unidad Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-entidades");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
