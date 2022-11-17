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

public class areaBeans {

    public List<Area> areas;
    public List<Area> seleccionadas;
    public Area areaseleccionada;
    public Departamento departamento;
    public Entidad entidad;
    public List<String> departamentos = new ArrayList<String>();
    public List<String> entidades;
    private String nombre_entidad;
    private String nombre_departamento;

    @PostConstruct
    public void init() {
        this.areas = Ctrl.ctrlArea.findAreaEntities();
        llenar_lista_entidad();

    }

    public List<String> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<String> entidades) {
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

    public List<String> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<String> departamentos) {
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

    public String getNombre_entidad() {
        return nombre_entidad;
    }

    public void setNombre_entidad(String nombre_entidad) {
        this.nombre_entidad = nombre_entidad;
    }

    public String getNombre_departamento() {
        return nombre_departamento;
    }

    public void setNombre_departamento(String nombre_departamento) {
        this.nombre_departamento = nombre_departamento;
    }

    public void nuevo() {
        this.areaseleccionada = new Area();

    }

    public void guardar() throws Exception {
        llenar_lista_entidad();
        Area nueva = new Area();
        System.out.println("entro");
        if (!yaexiste()) {
            nueva.setIdArea(this.areaseleccionada.getIdArea());
            nueva.setNombreArea(this.areaseleccionada.getNombreArea());
            System.out.println("añadio id y nombre");
            for (int i = 0; i < Ctrl.ctrlDepartamento.findDepartamentoEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_departamento.equals(Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i).getNombreDepartamento())) {
                    System.out.println("encontro el nombre");
//               idDpto = Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i).getIdDepartamento();
                    nueva.setDepartamentoidDepartamento(Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i));
                    break;
                }
            }

//            nueva.setDepartamentoidDepartamento(this.nombre_departamento);
            Ctrl.ctrlArea.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Area Añadida"));
        } else {
            try {
                nueva = Ctrl.ctrlArea.findArea(areaseleccionada.getIdArea());
                nueva.setIdArea(areaseleccionada.getIdArea());
                nueva.setNombreArea(areaseleccionada.getNombreArea());
                for (int i = 0; i < Ctrl.ctrlDepartamento.findDepartamentoEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_departamento.equals(Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i).getNombreDepartamento())) {
                        System.out.println("encontro el nombre");
//               idDpto = Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i).getIdDepartamento();
                        nueva.setDepartamentoidDepartamento(Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i));
                        break;
                    }
                }
                Ctrl.ctrlArea.edit(nueva);
                System.out.println("edito");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Area Actualizada"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No es posible modificar el ID"));
            }
        }
        departamentos.clear();
        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-areas");

    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Area eliminada = new Area();
        eliminada.setIdArea(areaseleccionada.getIdArea());
        Ctrl.ctrlArea.destroy(eliminada.getIdArea());
        this.areaseleccionada = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Área eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-areas");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " Áreas seleccionadas" : "1 Área Seleccionada";
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Area Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-areas");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public void llenar_lista_entidad() {
       this.entidades = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlEntidad.findEntidadEntities().size(); i++) {
            this.entidades.add(Ctrl.ctrlEntidad.findEntidadEntities().get(i).getNombreEntidad());
        }

    }

    public void llenar_lista_departamento() {

        for (int i = 0; i < Ctrl.ctrlEntidad.findEntidadEntities().size(); i++) {
            if (Ctrl.ctrlEntidad.findEntidadEntities().get(i).getNombreEntidad().equals(nombre_entidad)) {
                for (int j = 0; j < Ctrl.ctrlEntidad.findEntidadEntities().get(i).getDepartamentoList().size(); j++) {
                    departamentos.add(Ctrl.ctrlEntidad.findEntidadEntities().get(i).getDepartamentoList().get(j).getNombreDepartamento());
                    System.out.println(departamentos.get(j));
                }
            } else {
            }
        }
    }

    public void onCountryChange() {
        System.out.println("entro a on chance");
        System.out.println(nombre_entidad);
//        if (nombre_entidad != null && !"".equals(nombre_entidad)) {
//           for (int i = 0; i < Ctrl.ctrlEntidad.findEntidadEntities().size(); i++) {
//               if (Ctrl.ctrlEntidad.findEntidadEntities().get(i).getNombreEntidad().equals(nombre_entidad)) {
//                   for (int j = 0; j < Ctrl.ctrlEntidad.findEntidadEntities().get(i).getDepartamentoList().size(); j++) {
//                       departamentos.add(Ctrl.ctrlEntidad.findEntidadEntities().get(i).getDepartamentoList().get(j).getNombreDepartamento());
//                   }
//               } else {
//               }
//           }
//            llenar_lista_departamento();
//        } else {
//        }

        llenar_lista_departamento();
    }

    public void displayLocation() {
        FacesMessage msg;
        if (entidad != null && departamento != null) {
            msg = new FacesMessage("Selected", entidad + " of " + departamento);
            System.out.println("Selected" + entidad + " of " + departamento);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected.");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
