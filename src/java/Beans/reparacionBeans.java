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
import Entidades.Reparacion;
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
public class reparacionBeans {

    public List<Reparacion> Reparaciones;
    public List<Reparacion> seleccionadas;
    public Reparacion repseleccionado;
    public List<String> estados = new ArrayList<String>();
    public List<String> talleres = new ArrayList<String>();
    private String estado;
    private String taller;
    
    @PostConstruct
    public void init() {
        this.Reparaciones = Ctrl.ctrlReparacion.findReparacionEntities();
    }

    public List<Reparacion> getReparaciones() {
        return Reparaciones;
    }

    public void setReparaciones(List<Reparacion> Reparaciones) {
        this.Reparaciones = Reparaciones;
    }

    public List<Reparacion> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Reparacion> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Reparacion getRepseleccionado() {
        return repseleccionado;
    }

    public void setRepseleccionado(Reparacion repseleccionado) {
        this.repseleccionado = repseleccionado;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<String> getTalleres() {
        return talleres;
    }

    public void setTalleres(List<String> talleres) {
        this.talleres = talleres;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTaller() {
        return taller;
    }

    public void setTaller(String taller) {
        this.taller = taller;
    }
    
    public void nuevo() {
        this.repseleccionado = new Reparacion();
        llenar_lista_estados();
        llenar_lista_talleres();
    }
    
    public void llenar_lista_estados() {
        this.estados = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
            this.estados.add(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado());
        }
    }

    public void llenar_lista_talleres() {
        this.talleres = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlTaller.findTallerEntities().size(); i++) {
            this.talleres.add(Ctrl.ctrlTaller.findTallerEntities().get(i).getDescripcion());
        }
    }

    
    public void guardar() throws Exception, NonexistentEntityException {
        System.out.println("entro a guardar");
        Reparacion nueva = new Reparacion();
        if (!yaexiste()) {
            System.out.println("entro");
            for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (estado.equals(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado())) {
                        System.out.println("encontro el estado");
                        nueva.setEstadoidEstado(Ctrl.ctrlEstado.findEstadoEntities().get(i));
                        break;
                    }
                }
            for (int i = 0; i < Ctrl.ctrlTaller.findTallerEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (taller.equals(Ctrl.ctrlTaller.findTallerEntities().get(i).getDescripcion())) {
                        System.out.println("encontro el taller");
                        nueva.setTallernroContrato(Ctrl.ctrlTaller.findTallerEntities().get(i).getNroContrato());
                        break;
                    }
                }
            nueva.setDescripcion(repseleccionado.getDescripcion());
            nueva.setNInv(repseleccionado.getNInv());
            nueva.setTipoEquipo(repseleccionado.getTipoEquipo());
            nueva.setFIda(repseleccionado.getFIda());
            nueva.setFRegreso(repseleccionado.getFRegreso());
            Ctrl.ctrlReparacion.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reparación Añadida"));
        } else {
            try {
                nueva = Ctrl.ctrlReparacion.findReparacion(repseleccionado.getTallernroContrato());
            for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (estado.equals(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado())) {
                        System.out.println("encontro el estado");
                        nueva.setEstadoidEstado(Ctrl.ctrlEstado.findEstadoEntities().get(i));
                        break;
                    }
                }
            for (int i = 0; i < Ctrl.ctrlTaller.findTallerEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (taller.equals(Ctrl.ctrlTaller.findTallerEntities().get(i).getDescripcion())) {
                        System.out.println("encontro el taller");
                        nueva.setTallernroContrato(Ctrl.ctrlTaller.findTallerEntities().get(i).getNroContrato());
                        break;
                    }
                }
            nueva.setDescripcion(repseleccionado.getDescripcion());
            nueva.setNInv(repseleccionado.getNInv());
            nueva.setTipoEquipo(repseleccionado.getTipoEquipo());
            nueva.setFIda(repseleccionado.getFIda());
            nueva.setFRegreso(repseleccionado.getFRegreso());
            Ctrl.ctrlReparacion.edit(nueva);
            System.out.println("edito");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reparación Actualizada"));
            } catch (Exception e) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reparación Actualizada"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-rep");
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Reparacion eliminada = new Reparacion();
        eliminada.setTallernroContrato(repseleccionado.getTallernroContrato());
        System.out.println("entro a elmininar");
        System.out.println(repseleccionado.getTallernroContrato());
        Ctrl.ctrlReparacion.destroy(eliminada.getTallernroContrato());
        this.repseleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reparación Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-rep");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " reparaciones seleccionadas" : "1 reparacion seleccionada";
        }

        return "Borrar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public boolean yaexiste() {
        
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlReparacion.destroy(seleccionadas.get(i).getTallernroContrato());
        }
        this.Reparaciones.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reparación Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-rep");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
