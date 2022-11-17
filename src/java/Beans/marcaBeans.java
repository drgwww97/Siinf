/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Marca;
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
public class marcaBeans implements Serializable{

    public List<Marca> Marcas;
    public List<Marca> seleccionadas;
    public Marca marcaseleccionada;
    
    @PostConstruct
    public void init() {
        this.Marcas = Ctrl.ctrlMarca.findMarcaEntities();
    }

    public List<Marca> getMarcas() {
        return Marcas;
    }

    public void setMarcas(List<Marca> Marcas) {
        this.Marcas = Marcas;
    }

    public List<Marca> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Marca> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Marca getMarcaseleccionada() {
        return marcaseleccionada;
    }

    public void setMarcaseleccionada(Marca marcaseleccionada) {
        this.marcaseleccionada = marcaseleccionada;
    }

     
    public void nuevo() {
        this.marcaseleccionada = new Marca();
    }
    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        Marca nueva = new Marca();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdMarca(marcaseleccionada.getIdMarca());
            nueva.setNombreMarca(marcaseleccionada.getNombreMarca());
            Ctrl.ctrlMarca.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marca Añadida"));
        } else {
            try {
            nueva = Ctrl.ctrlMarca.findMarca(marcaseleccionada.getIdMarca());
            nueva.setIdMarca(marcaseleccionada.getIdMarca());
            nueva.setNombreMarca(marcaseleccionada.getNombreMarca());
            Ctrl.ctrlMarca.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marca Actualizada"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marca Actualizada"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marca");
    }
    public boolean yaexiste() {
        for (int i = 0; i < Marcas.size(); i++) {
            if (marcaseleccionada.getIdMarca().equals(Marcas.get(i).getIdMarca())) {
                return true;
            }
        }
        return false;
    }
    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Marca eliminada = new Marca();
        eliminada.setIdMarca(marcaseleccionada.getIdMarca());
        System.out.println("entro a elmininar");
        System.out.println(marcaseleccionada.getIdMarca());
        Ctrl.ctrlMarca.destroy(eliminada.getIdMarca());
        this.marcaseleccionada = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marca Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marca");
    }
    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " marcas seleccionadas" : "1 marca seleccionada";
        }

        return "Borrar";
    }
    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }
    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlMarca.destroy(seleccionadas.get(i).getIdMarca());
        }
        this.Marcas.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marca Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marca");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
