/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controladores.Ctrl;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author David Ruiz
 */
@ManagedBean
@ViewScoped

public class areaBeans {

    public List<Area> areas;
    public List<Area> seleccionadas;
    public Area areaseleccionada;
    public List<Departamento> departamentos;
    public List<Entidad> entidades;
    public Departamento departamento;
    public Entidad entidad;

    @PostConstruct
    public void init() {
        this.areas = Ctrl.ctrlArea.findAreaEntities();
        this.entidades = Ctrl.ctrlEntidad.findEntidadEntities();
    }

    public List<Entidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public List<Area> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Area> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Area getAreaseleccionada() {
        return areaseleccionada;
    }

    public void setAreaseleccionada(Area areaseleccionada) {
        this.areaseleccionada = areaseleccionada;
    }

    public void nuevo() {
        this.areaseleccionada = new Area();
        this.entidad = new Entidad();
    }

    public void guardar() throws Exception {
        Area nueva = new Area();
        if (!yaexiste()) {
            nueva.setIdArea(this.areaseleccionada.getIdArea());
            nueva.setNombreArea(this.areaseleccionada.getNombreArea());
            nueva.setDepartamentoidDepartamento(this.areaseleccionada.getDepartamentoidDepartamento());
            Ctrl.ctrlArea.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Area Añadida"));
        } else {
            try {
                nueva = Ctrl.ctrlArea.findArea(areaseleccionada.getIdArea());
                nueva.setIdArea(areaseleccionada.getIdArea());
                nueva.setNombreArea(areaseleccionada.getNombreArea());
                nueva.setDepartamentoidDepartamento(areaseleccionada.getDepartamentoidDepartamento());
                Ctrl.ctrlArea.edit(nueva);
                System.out.println("edito");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Area Actualizada"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No es posible modificar el ID"));
            }
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-areas");

    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Area eliminada = new Area();
        eliminada.setIdArea(areaseleccionada.getIdArea());
        Ctrl.ctrlArea.destroy(eliminada.getIdArea());
        this.areaseleccionada = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Entidad elininada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-areas");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " Entidades selecciondas" : "1 Entidad Seleccionada";
        }

        return "Eliminar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < areas.size(); i++) {
            if (areaseleccionada.getIdArea().equals(areas.get(i).getIdArea()) || areaseleccionada.getIdArea().equals(areas.get(i).getIdArea())) {

                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlArea.destroy(seleccionadas.get(i).getIdArea());
        }
        this.areas.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Areas Eliminadas"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-areas");
        PrimeFaces.current().executeScript("PF('dtAreas').clearFilters()");
    }

   public void onCountryChange() {
       System.out.println("entro a on chance");
       System.out.println(entidad.getNombreEntidad());
        if (this.entidad != null && !"".equals(this.entidad)) {
            this.departamentos = this.entidad.getDepartamentoList();
        }
        else {
           
        }
    }

    public void displayLocation() {
        FacesMessage msg;
        if (entidad != null && departamento != null) {
            msg = new FacesMessage("Selected", entidad + " of " + departamento);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected.");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
