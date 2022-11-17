/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Estado;
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
public class estadoBeans implements Serializable {

    public List<Estado> Estados;
    public List<Estado> seleccionados;
    public Estado testadoseleccionado;
    
    @PostConstruct
    public void init(){
    this.Estados=Ctrl.ctrlEstado.findEstadoEntities();
    }

    public List<Estado> getEstados() {
        return Estados;
    }

    public void setEstados(List<Estado> Estados) {
        this.Estados = Estados;
    }

    public List<Estado> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<Estado> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public Estado getTestadoseleccionado() {
        return testadoseleccionado;
    }

    public void setTestadoseleccionado(Estado testadoseleccionado) {
        this.testadoseleccionado = testadoseleccionado;
    }
    
    public void nuevo() {
        this.testadoseleccionado = new Estado();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        Estado nueva = new Estado();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdEstado(testadoseleccionado.getIdEstado());
            nueva.setNombreEstado(testadoseleccionado.getNombreEstado());
            Ctrl.ctrlEstado.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Estado Añadido"));
        } else {
            try {
                nueva = Ctrl.ctrlEstado.findEstado(testadoseleccionado.getIdEstado());
            nueva.setIdEstado(testadoseleccionado.getIdEstado());
            nueva.setNombreEstado(testadoseleccionado.getNombreEstado());
            Ctrl.ctrlEstado.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Estado Actualizado"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Estado Actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estado");
    }
    public boolean yaexiste() {
        for (int i = 0; i < Estados.size(); i++) {
            if (testadoseleccionado.getIdEstado().equals(Estados.get(i).getIdEstado())) {
                return true;
            }
        }
        return false;
    }
    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Estado eliminada = new Estado();
        eliminada.setIdEstado(testadoseleccionado.getIdEstado());
        System.out.println("entro a elmininar");
        System.out.println(testadoseleccionado.getIdEstado());
        Ctrl.ctrlEstado.destroy(eliminada.getIdEstado());
        this.testadoseleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Estado Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estado");
    }
    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionados.size();
            return size > 1 ? size + " estados seleccionados" : "1 estado seleccionado";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionados != null && !this.seleccionados.isEmpty();
    }
    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionados.size(); i++) {
            Ctrl.ctrlEstado.destroy(seleccionados.get(i).getIdEstado());
        }
        this.Estados.removeAll(this.seleccionados);
        this.seleccionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Estado Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estado");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
    
}
