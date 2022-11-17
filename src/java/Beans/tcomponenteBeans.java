/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.TComponente;
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
public class tcomponenteBeans implements Serializable{

    public List<TComponente> TComponentes;
    public List<TComponente> seleccionados;
    public TComponente tcomponenteseleccionado;
    
    @PostConstruct
    public void init() {
        this.TComponentes = Ctrl.ctrlTipoComponente.findTComponenteEntities();
    }

    public List<TComponente> getTComponentes() {
        return TComponentes;
    }

    public void setTComponentes(List<TComponente> TComponentes) {
        this.TComponentes = TComponentes;
    }

    public List<TComponente> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<TComponente> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public TComponente getTcomponenteseleccionado() {
        return tcomponenteseleccionado;
    }

    public void setTcomponenteseleccionado(TComponente tcomponenteseleccionado) {
        this.tcomponenteseleccionado = tcomponenteseleccionado;
    }
    
    public void nuevo() {
        this.tcomponenteseleccionado = new TComponente();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        TComponente nueva = new TComponente();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdComponente(tcomponenteseleccionado.getIdComponente());
            nueva.setNombreComponente(tcomponenteseleccionado.getNombreComponente());
            Ctrl.ctrlTipoComponente.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Componente Añadido"));
        } else {
            try {
                nueva = Ctrl.ctrlTipoComponente.findTComponente(tcomponenteseleccionado.getIdComponente());
            nueva.setIdComponente(tcomponenteseleccionado.getIdComponente());
            nueva.setNombreComponente(tcomponenteseleccionado.getNombreComponente());
            Ctrl.ctrlTipoComponente.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Componente Actualizado"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Componente Actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tcomponente");
    }
    
    public boolean yaexiste() {
        for (int i = 0; i < TComponentes.size(); i++) {
            if (tcomponenteseleccionado.getIdComponente().equals(TComponentes.get(i).getIdComponente())) {
                return true;
            }
        }
        return false;
    }
    
    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        TComponente eliminada = new TComponente();
        eliminada.setIdComponente(tcomponenteseleccionado.getIdComponente());
        System.out.println("entro a elmininar");
        System.out.println(tcomponenteseleccionado.getIdComponente());
        Ctrl.ctrlTipoComponente.destroy(eliminada.getIdComponente());
        this.tcomponenteseleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Componente Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tcomponente");
    }
    
    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionados.size();
            return size > 1 ? size + " componentes seleccionados" : "1 componente seleccionado";
        }

        return "Borrar";
    }
    
    public boolean hasSeleccionadoElementos() {
        return this.seleccionados != null && !this.seleccionados.isEmpty();
    }
    
    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionados.size(); i++) {
            Ctrl.ctrlTipoComponente.destroy(seleccionados.get(i).getIdComponente());
        }
        this.TComponentes.removeAll(this.seleccionados);
        this.seleccionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Componente Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tcomponente");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
