/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.TAccesorio;
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
public class taccesorioBeans implements Serializable{

    public List<TAccesorio> TAccesorios;
    public List<TAccesorio> seleccionados;
    public TAccesorio taccesoriosseleccionados;
    
    @PostConstruct
    public void init() {
        this.TAccesorios = Ctrl.ctrlTipoAccesosrio.findTAccesorioEntities();
    }

    public List<TAccesorio> getTAccesorios() {
        return TAccesorios;
    }

    public void setTAccesorios(List<TAccesorio> TAccesorios) {
        this.TAccesorios = TAccesorios;
    }

    public List<TAccesorio> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<TAccesorio> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public TAccesorio getTaccesoriosseleccionados() {
        return taccesoriosseleccionados;
    }

    public void setTaccesoriosseleccionados(TAccesorio taccesoriosseleccionados) {
        this.taccesoriosseleccionados = taccesoriosseleccionados;
    }
    
    public void nuevo() {
        this.taccesoriosseleccionados = new TAccesorio();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        TAccesorio nueva = new TAccesorio();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdAccesorio(taccesoriosseleccionados.getIdAccesorio());
            nueva.setNombreAccesorio(taccesoriosseleccionados.getNombreAccesorio());
            Ctrl.ctrlTipoAccesosrio.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accesorio Añadido"));
        } else {
            try {
                nueva = Ctrl.ctrlTipoAccesosrio.findTAccesorio(taccesoriosseleccionados.getIdAccesorio());
            nueva.setIdAccesorio(taccesoriosseleccionados.getIdAccesorio());
            nueva.setNombreAccesorio(taccesoriosseleccionados.getNombreAccesorio());
            Ctrl.ctrlTipoAccesosrio.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accesorio Actualizado"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accesorio Actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
    public boolean yaexiste() {
        for (int i = 0; i < TAccesorios.size(); i++) {
            if (taccesoriosseleccionados.getIdAccesorio().equals(TAccesorios.get(i).getIdAccesorio())) {
                return true;
            }
        }
        return false;
    }
    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        TAccesorio eliminada = new TAccesorio();
        eliminada.setIdAccesorio(taccesoriosseleccionados.getIdAccesorio());
        System.out.println("entro a elmininar");
        System.out.println(taccesoriosseleccionados.getIdAccesorio());
        Ctrl.ctrlTipoAccesosrio.destroy(eliminada.getIdAccesorio());
        this.taccesoriosseleccionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accesorio Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }
    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionados.size();
            return size > 1 ? size + " accesorios seleccionados" : "1 accesorio seleccionado";
        }

        return "Borrar";
    }
    public boolean hasSeleccionadoElementos() {
        return this.seleccionados != null && !this.seleccionados.isEmpty();
    }
    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionados.size(); i++) {
            Ctrl.ctrlTipoAccesosrio.destroy(seleccionados.get(i).getIdAccesorio());
        }
        this.TAccesorios.removeAll(this.seleccionados);
        this.seleccionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accesorio Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
