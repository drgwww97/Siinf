/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Modelo;
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
public class modeloBeans implements Serializable {

    public List<Modelo> Modelos;
    public List<Modelo> seleccionadas;
    public Modelo modeloselecionado;

    @PostConstruct
    public void init() {
        this.Modelos = Ctrl.ctrlModelo.findModeloEntities();
    }

    public List<Modelo> getModelos() {
        return Modelos;
    }

    public void setModelos(List<Modelo> Modelos) {
        this.Modelos = Modelos;
    }

    public List<Modelo> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Modelo> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Modelo getModeloselecionado() {
        return modeloselecionado;
    }

    public void setModeloselecionado(Modelo modeloselecionado) {
        this.modeloselecionado = modeloselecionado;
    }

    public void nuevo() {
        this.modeloselecionado = new Modelo();
    }

    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        Modelo nueva = new Modelo();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setIdModelo(modeloselecionado.getIdModelo());
            nueva.setNombreModelo(modeloselecionado.getNombreModelo());
            for (int i = 0; i < Ctrl.ctrlTipoTonner.findTTonnerEntities().size(); i++) {
                System.out.println("entro al for");
                if (modeloselecionado.getTTonnersnTonner().getSnTonner().equals("")) {
                    nueva.setTTonnersnTonner(Ctrl.ctrlTipoTonner.findTTonnerEntities().get(0));
                    break;
                } else {
                    if ((modeloselecionado.getTTonnersnTonner().getSnTonner().equals(Ctrl.ctrlTipoTonner.findTTonnerEntities().get(i).getSnTonner()))) {
                        System.out.println("encontro el nombre tonner");
                        nueva.setTTonnersnTonner(Ctrl.ctrlTipoTonner.findTTonnerEntities().get(i));
                        break;
                    }
                }

            }
            Ctrl.ctrlModelo.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modelo Añadido"));
        } else {
            try {
                nueva = Ctrl.ctrlModelo.findModelo(modeloselecionado.getIdModelo());
                nueva.setIdModelo(modeloselecionado.getIdModelo());
                nueva.setNombreModelo(modeloselecionado.getNombreModelo());
                Ctrl.ctrlModelo.edit(nueva);
                System.out.println("edito");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modelo Actualizado"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modelo Actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public boolean yaexiste() {
        for (int i = 0; i < Modelos.size(); i++) {
            if (modeloselecionado.getIdModelo().equals(Modelos.get(i).getIdModelo())) {
                return true;
            }
        }
        return false;
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Modelo eliminada = new Modelo();
        eliminada.setIdModelo(modeloselecionado.getIdModelo());
        System.out.println("entro a elmininar");
        System.out.println(modeloselecionado.getIdModelo());
        Ctrl.ctrlModelo.destroy(eliminada.getIdModelo());
        this.modeloselecionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modelo Eliminado"));
        PrimeFaces.current().ajax().update("for:messages", "form:dt-products");

    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " modelos seleccionados" : "1 modelo seleccionado";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlModelo.destroy(seleccionadas.get(i).getIdModelo());
        }
        this.Modelos.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modelo Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

}
