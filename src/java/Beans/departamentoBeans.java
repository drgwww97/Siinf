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
import java.util.ArrayList;
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
public class departamentoBeans {

    public List<Departamento> departamentos;
    public List<Departamento> seleccionados;
    public Departamento deparatamentoleccionado;
    public Entidad entidad;
    public List<String> entidades;
    private String nombre_entidad;

    @PostConstruct
    public void init() {
        this.departamentos = Ctrl.ctrlDepartamento.findDepartamentoEntities();
        llenar_lista_entidad();
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<Departamento> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<Departamento> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public Departamento getDeparatamentoleccionado() {
        return deparatamentoleccionado;
    }

    public void setDeparatamentoleccionado(Departamento deparatamentoleccionado) {
        this.deparatamentoleccionado = deparatamentoleccionado;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public List<String> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<String> entidades) {
        this.entidades = entidades;
    }

    public String getNombre_entidad() {
        return nombre_entidad;
    }

    public void setNombre_entidad(String nombre_entidad) {
        this.nombre_entidad = nombre_entidad;
    }

    public void nuevo() {
        this.deparatamentoleccionado = new Departamento();
    }

    public void guardar() throws Exception {
        System.out.println("entro a guardar");
        Departamento nuevo = new Departamento();
//        System.out.println();
        if (!yaexiste()) {
            nuevo.setIdDepartamento(this.deparatamentoleccionado.getIdDepartamento());
            nuevo.setNombreDepartamento(this.deparatamentoleccionado.getNombreDepartamento());
            for (int i = 0; i < Ctrl.ctrlEntidad.findEntidadEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_entidad.equals(Ctrl.ctrlEntidad.findEntidadEntities().get(i).getNombreEntidad())) {
                    System.out.println("encontro el nombre");
                    nuevo.setEntidadidEntidad(Ctrl.ctrlEntidad.findEntidadEntities().get(i));
                    break;
                }
            }
            Ctrl.ctrlDepartamento.create(nuevo);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Departamento Añadido"));
        } else {
            try {
                nuevo = Ctrl.ctrlDepartamento.findDepartamento(deparatamentoleccionado.getIdDepartamento());
                nuevo.setIdDepartamento(this.deparatamentoleccionado.getIdDepartamento());
                nuevo.setNombreDepartamento(this.deparatamentoleccionado.getNombreDepartamento());
                for (int i = 0; i < Ctrl.ctrlEntidad.findEntidadEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_entidad.equals(Ctrl.ctrlEntidad.findEntidadEntities().get(i).getNombreEntidad())) {
                    System.out.println("encontro el nombre");
                    nuevo.setEntidadidEntidad(Ctrl.ctrlEntidad.findEntidadEntities().get(i));
                    break;
                }
            }
                Ctrl.ctrlDepartamento.edit(nuevo);
                System.out.println("edito");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Departamento Actualizado"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No es posible modificar el ID"));
            }
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departamentos");

    }

    public void llenar_lista_entidad() {
        this.entidades = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlEntidad.findEntidadEntities().size(); i++) {
            this.entidades.add(Ctrl.ctrlEntidad.findEntidadEntities().get(i).getNombreEntidad());
        }

    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Departamento eliminado = new Departamento();
        eliminado.setIdDepartamento(deparatamentoleccionado.getIdDepartamento());
        System.out.println("entro a elmininar");
        System.out.println(deparatamentoleccionado.getIdDepartamento());
        Ctrl.ctrlDepartamento.destroy(eliminado.getIdDepartamento());
        this.deparatamentoleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Departamento eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departamentos");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionados.size();
            return size > 1 ? size + " Departamentos seleccionados" : "1 Departamento Seleccionado";
        }

        return "Eliminar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionados != null && !this.seleccionados.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < departamentos.size(); i++) {
            if (deparatamentoleccionado.getIdDepartamento().equals(departamentos.get(i).getIdDepartamento())) {

                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionados.size(); i++) {
            Ctrl.ctrlDepartamento.destroy(seleccionados.get(i).getIdDepartamento());
        }
        this.departamentos.removeAll(this.seleccionados);
        this.seleccionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Departamentos Eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departamentos");
        PrimeFaces.current().executeScript("PF('dtDepartamento').clearFilters()");
    }

}
