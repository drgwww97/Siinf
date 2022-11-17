/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.TEquipo;
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
public class tequipoBeans implements Serializable{

    public List<TEquipo> TEquipos;
    public List<TEquipo> seleccionados;
    public TEquipo tequiposeleccionado;
    
    @PostConstruct
    public void init(){
    this.TEquipos=Ctrl.ctrlTipoEquipo.findTEquipoEntities();
    }

    public List<TEquipo> getTEquipos() {
        return TEquipos;
    }

    public void setTEquipos(List<TEquipo> TEquipos) {
        this.TEquipos = TEquipos;
    }

    public List<TEquipo> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<TEquipo> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public TEquipo getTequiposeleccionado() {
        return tequiposeleccionado;
    }

    public void setTequiposeleccionado(TEquipo tequiposeleccionado) {
        this.tequiposeleccionado = tequiposeleccionado;
    }
      
    public void nuevo() {
        this.tequiposeleccionado = new TEquipo();
    }
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        TEquipo nueva = new TEquipo();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdEquipo(tequiposeleccionado.getIdEquipo());
            nueva.setNombreEquipo(tequiposeleccionado.getNombreEquipo());
            Ctrl.ctrlTipoEquipo.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Equipo Añadido"));
        } else {
            try {
                nueva = Ctrl.ctrlTipoEquipo.findTEquipo(tequiposeleccionado.getIdEquipo());
            nueva.setIdEquipo(tequiposeleccionado.getIdEquipo());
            nueva.setNombreEquipo(tequiposeleccionado.getNombreEquipo());
            Ctrl.ctrlTipoEquipo.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Equipo Actualizado"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Equipo Actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tequipo");
    }
    public boolean yaexiste() {
        for (int i = 0; i < TEquipos.size(); i++) {
            if (tequiposeleccionado.getIdEquipo().equals(TEquipos.get(i).getIdEquipo())) {
                return true;
            }
        }
        return false;
    }
    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        TEquipo eliminada = new TEquipo();
        eliminada.setIdEquipo(tequiposeleccionado.getIdEquipo());
        System.out.println("entro a elmininar");
        System.out.println(tequiposeleccionado.getIdEquipo());
        Ctrl.ctrlTipoEquipo.destroy(eliminada.getIdEquipo());
        this.tequiposeleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Equipo Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tequipo");
    }
    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionados.size();
            return size > 1 ? size + " equipos seleccionados" : "1 equipo seleccionado";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionados != null && !this.seleccionados.isEmpty();
    }
    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionados.size(); i++) {
            Ctrl.ctrlTipoEquipo.destroy(seleccionados.get(i).getIdEquipo());
        }
        this.TEquipos.removeAll(this.seleccionados);
        this.seleccionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Equipo Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-tequipo");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
