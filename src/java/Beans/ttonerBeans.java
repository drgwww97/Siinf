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
import Entidades.TTonner;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ttonerBeans implements Serializable {

    public List<TTonner> TToner;
    public List<TTonner> seleccionados;
    public TTonner ttonereleccionado;
    public List<String> modelo = new ArrayList<String>();
    public List<String> modeloseleccionados = new ArrayList<String>();
    public String nombre_modelo;

    @PostConstruct
    public void init() {
        this.TToner = Ctrl.ctrlTipoTonner.findTTonnerEntities();
    }

    public String getNombre_modelo() {
        return nombre_modelo;
    }

    public void setNombre_modelo(String nombre_modelo) {
        this.nombre_modelo = nombre_modelo;
    }

    public List<TTonner> getTToner() {
        return TToner;
    }

    public void setTToner(List<TTonner> TToner) {
        this.TToner = TToner;
    }

    public List<TTonner> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<TTonner> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public TTonner getTtonereleccionado() {
        return ttonereleccionado;
    }

    public void setTtonereleccionado(TTonner ttonereleccionado) {
        this.ttonereleccionado = ttonereleccionado;
    }

    public List<String> getModelo() {
        return modelo;
    }

    public void setModelo(List<String> modelo) {
        this.modelo = modelo;
    }

    public List<String> getModeloseleccionados() {
        return modeloseleccionados;
    }

    public void setModeloseleccionados(List<String> modeloseleccionados) {
        this.modeloseleccionados = modeloseleccionados;
    }

    public void nuevo() {
        this.ttonereleccionado = new TTonner();
        llenar_lista_modelo();
    }

    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        TTonner nueva = new TTonner();
        if (!yaexiste()) {
            System.out.println("entro");
            nueva.setSnTonner(ttonereleccionado.getSnTonner());
            nueva.setTipoToner(ttonereleccionado.getTipoToner());
            nueva.setCantidadToner(ttonereleccionado.getCantidadToner());
            for (int i = 0; i < Ctrl.ctrlModelo.findModeloEntities().size(); i++) {
                for (int j = 0; j < modeloseleccionados.size(); j++) {
                    if (modeloseleccionados.get(j).equals(Ctrl.ctrlModelo.findModeloEntities().get(i).getNombreModelo())) {
                        System.out.println("encontro los modelos");
                      //  nueva.(Ctrl.ctrlModelo.findModeloEntities().get(i));
                        System.out.println("añadio los modelos");
                    }
                }
//                nueva.setModeloidModelo((Modelo) modeloseleccBD);

            }

            Ctrl.ctrlTipoTonner.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Toner Añadido"));
        } else {
            try {
                nueva = Ctrl.ctrlTipoTonner.findTTonner(ttonereleccionado.getSnTonner());
                nueva.setSnTonner(ttonereleccionado.getSnTonner());
                nueva.setTipoToner(ttonereleccionado.getTipoToner());
                nueva.setCantidadToner(ttonereleccionado.getCantidadToner());
                for (int i = 0; i < Ctrl.ctrlModelo.findModeloEntities().size(); i++) {
                    for (int j = 0; j < modeloseleccionados.size(); j++) {
                        if (modeloseleccionados.get(j).equals(Ctrl.ctrlModelo.findModeloEntities().get(i).getNombreModelo())) {
                            System.out.println("encontro los modelos");
                            modeloseleccBD.add(Ctrl.ctrlModelo.findModeloEntities().get(i));

                            System.out.println("añadio los modelos");
                        }
                    }
                    nueva.setModeloidModelo((Modelo) modeloseleccBD);
                    break;
                }
                Ctrl.ctrlTipoTonner.edit(nueva);
                System.out.println("edito");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Toner Actualizado"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Toner Actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-ttoner");
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        TTonner eliminada = new TTonner();
        eliminada.setSnTonner(ttonereleccionado.getSnTonner());
        System.out.println("entro a elmininar");
        System.out.println(ttonereleccionado.getSnTonner());
        Ctrl.ctrlTipoTonner.destroy(eliminada.getSnTonner());
        this.ttonereleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Toner Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-ttoner");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionados.size();
            return size > 1 ? size + " tipos de toner seleccionados" : "1 tipo de toner seleccionado";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionados != null && !this.seleccionados.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < TToner.size(); i++) {
            if (ttonereleccionado.getSnTonner().equals(TToner.get(i).getSnTonner())) {
                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionados.size(); i++) {
            Ctrl.ctrlTipoTonner.destroy(seleccionados.get(i).getSnTonner());
        }
        this.TToner.removeAll(this.seleccionados);
        this.seleccionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo de Toner Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-ttoner");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public void llenar_lista_modelo() {
        this.modelo = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlModelo.findModeloEntities().size(); i++) {
            this.modelo.add(Ctrl.ctrlModelo.findModeloEntities().get(i).getNombreModelo());
        }
    }
}
