/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Taller;
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
public class tallerBeans {

    public List<Taller> Talleres;
    public List<Taller> seleccionadas;
    public Taller tallerseleccionado;
    
    @PostConstruct
    public void init() {
        this.Talleres = Ctrl.ctrlTaller.findTallerEntities();
    }

    public List<Taller> getTalleres() {
        return Talleres;
    }

    public void setTalleres(List<Taller> Talleres) {
        this.Talleres = Talleres;
    }

    public List<Taller> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Taller> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Taller getTallerseleccionado() {
        return tallerseleccionado;
    }

    public void setTallerseleccionado(Taller tallerseleccionado) {
        this.tallerseleccionado = tallerseleccionado;
    }
    
    public void nuevo() {
        this.tallerseleccionado = new Taller();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        Taller nueva = new Taller();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setNroContrato(tallerseleccionado.getNroContrato());
            nueva.setNroContacto(tallerseleccionado.getNroContacto());
            nueva.setDescripcion(tallerseleccionado.getDescripcion());
            nueva.setDireccionTaller(tallerseleccionado.getDireccionTaller());
            nueva.setFIniContrato(tallerseleccionado.getFIniContrato());
            nueva.setFFinContrato(tallerseleccionado.getFFinContrato());
            Ctrl.ctrlTaller.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Taller Añadido"));
        } else {
            try {
                nueva = Ctrl.ctrlTaller.findTaller(tallerseleccionado.getNroContrato());
            nueva.setNroContrato(tallerseleccionado.getNroContrato());
            nueva.setNroContacto(tallerseleccionado.getNroContacto());
            nueva.setDescripcion(tallerseleccionado.getDescripcion());
            nueva.setDireccionTaller(tallerseleccionado.getDireccionTaller());
            nueva.setFIniContrato(tallerseleccionado.getFIniContrato());
            nueva.setFFinContrato(tallerseleccionado.getFFinContrato());
            Ctrl.ctrlTaller.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Taller Actualizado"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Taller Actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-taller");
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Taller eliminada = new Taller();
        eliminada.setNroContrato(tallerseleccionado.getNroContrato());
        System.out.println("entro a elmininar");
        System.out.println(tallerseleccionado.getNroContrato());
        Ctrl.ctrlTaller.destroy(eliminada.getNroContrato());
        this.tallerseleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Taller Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-taller");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " talleres seleccionados" : "1 taller seleccionado";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < Talleres.size(); i++) {
            if (tallerseleccionado.getNroContrato().equals(Talleres.get(i).getNroContrato())) {
                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlTaller.destroy(seleccionadas.get(i).getNroContrato());
        }
        this.Talleres.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Taller Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-taller");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
