/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Programas;
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
public class programasBeans implements Serializable{

    public List<Programas> Programas;
    public List<Programas> seleccionados;
    public Programas programaseleccionado;
    
    @PostConstruct
    public void init() {
        this.Programas = Ctrl.ctrlProgramas.findProgramasEntities();
    }

    public List<Programas> getProgramas() {
        return Programas;
    }

    public void setProgramas(List<Programas> Programas) {
        this.Programas = Programas;
    }

    public List<Programas> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<Programas> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public Programas getProgramaseleccionado() {
        return programaseleccionado;
    }

    public void setProgramaseleccionado(Programas programaseleccionado) {
        this.programaseleccionado = programaseleccionado;
    }
    
    public void nuevo() {
        this.programaseleccionado = new Programas();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        Programas nueva = new Programas();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdProgramas(programaseleccionado.getIdProgramas());
            nueva.setNombreProg(programaseleccionado.getNombreProg());
            nueva.setVersion(programaseleccionado.getVersion());
            Ctrl.ctrlProgramas.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Programa Añadido"));
        } else {
            try {
            nueva = Ctrl.ctrlProgramas.findProgramas(programaseleccionado.getIdProgramas());
            nueva.setIdProgramas(programaseleccionado.getIdProgramas());
            nueva.setNombreProg(programaseleccionado.getNombreProg());
            nueva.setVersion(programaseleccionado.getVersion());
            Ctrl.ctrlProgramas.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Programa Actualizado"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Programa Actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-programas");
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Programas eliminada = new Programas();
        eliminada.setIdProgramas(programaseleccionado.getIdProgramas());
        System.out.println("entro a elmininar");
        System.out.println(programaseleccionado.getIdProgramas());
        Ctrl.ctrlProgramas.destroy(eliminada.getIdProgramas());
        this.programaseleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Programa Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-programas");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionados.size();
            return size > 1 ? size + " programas seleccionados" : "1 programa seleccionado";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionados != null && !this.seleccionados.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < Programas.size(); i++) {
            if (programaseleccionado.getIdProgramas().equals(Programas.get(i).getIdProgramas())) {
                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionados.size(); i++) {
            Ctrl.ctrlProgramas.destroy(seleccionados.get(i).getIdProgramas());
        }
        this.Programas.removeAll(this.seleccionados);
        this.seleccionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Programa Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-programas");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
