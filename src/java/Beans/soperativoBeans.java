/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.SO;
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
public class soperativoBeans implements Serializable {

    public List<SO> SOperativo;
    public List<SO> seleccionados;
    public SO soseleccionado;
    
    @PostConstruct
    public void init() {
        this.SOperativo = Ctrl.ctrlSO.findSOEntities();
    }    

    public List<SO> getSOperativo() {
        return SOperativo;
    }

    public void setSOperativo(List<SO> SOperativo) {
        this.SOperativo = SOperativo;
    }

    public List<SO> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<SO> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public SO getSoseleccionado() {
        return soseleccionado;
    }

    public void setSoseleccionado(SO soseleccionado) {
        this.soseleccionado = soseleccionado;
    }
    
    public void nuevo() {
        this.soseleccionado = new SO();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        SO nueva = new SO();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdSo(soseleccionado.getIdSo());
            nueva.setNombreSo(soseleccionado.getNombreSo());
            Ctrl.ctrlSO.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema Operativo añadido"));
        } else {
            try {
            nueva = Ctrl.ctrlSO.findSO(soseleccionado.getIdSo());
            nueva.setIdSo(soseleccionado.getIdSo());
            nueva.setNombreSo(soseleccionado.getNombreSo());
            Ctrl.ctrlSO.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema Operativo Actualizado"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema Operativo Actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-soperativo");
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        SO eliminada = new SO();
        eliminada.setIdSo(soseleccionado.getIdSo());
        System.out.println("entro a elmininar");
        System.out.println(soseleccionado.getIdSo());
        Ctrl.ctrlSO.destroy(eliminada.getIdSo());
        this.soseleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema Operativo Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-soperativo");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionados.size();
            return size > 1 ? size + " sistemas operativos seleccionados" : "1 sistema operativo seleccionado";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionados != null && !this.seleccionados.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < SOperativo.size(); i++) {
            if (soseleccionado.getIdSo().equals(SOperativo.get(i).getIdSo())) {
                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionados.size(); i++) {
            Ctrl.ctrlSO.destroy(seleccionados.get(i).getIdSo());
        }
        this.SOperativo.removeAll(this.seleccionados);
        this.seleccionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sistema Operativo Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-soperativo");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
