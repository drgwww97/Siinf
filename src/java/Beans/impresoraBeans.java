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
 * @author Dayana
 */
@ManagedBean
@ViewScoped
public class impresoraBeans {

    public List<Impresora> impresoras;
    public List<Impresora> seleccionadas;
    public Impresora impresoraseleccionada;
    public List<String> entidades;
    public List<String> departamentos = new ArrayList<String>();
    public List<String> area = new ArrayList<String>();
    public List<String> marca = new ArrayList<String>();
    public List<String> modelo = new ArrayList<String>();
    public List<String> tonner = new ArrayList<String>();
    private String nombre_entidad;
    private String nombre_departamento;
    private String nombre_area;
    private String nombre_marca;
    private String nombre_modelo;
    private String nombre_tonner;

    @PostConstruct
    public void init() {
        this.impresoras = Ctrl.ctrlImpresora.findImpresoraEntities();
//        llenar_lista_entidad();
//        llenar_lista_marca();
//        llenar_lista_modelo();
//        llenar_lista_tonner();

    }

    public List<Impresora> getImpresoras() {
        return impresoras;
    }

    public void setImpresoras(List<Impresora> impresoras) {
        this.impresoras = impresoras;
    }

    public List<Impresora> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Impresora> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Impresora getImpresoraseleccionada() {
        return impresoraseleccionada;
    }

    public void setImpresoraseleccionada(Impresora impresoraseleccionada) {
        this.impresoraseleccionada = impresoraseleccionada;
    }

    public List<String> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<String> entidades) {
        this.entidades = entidades;
    }

    public List<String> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<String> departamentos) {
        this.departamentos = departamentos;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    public List<String> getMarca() {
        return marca;
    }

    public void setMarca(List<String> marca) {
        this.marca = marca;
    }

    public List<String> getModelo() {
        return modelo;
    }

    public void setModelo(List<String> modelo) {
        this.modelo = modelo;
    }

    public List<String> getTonner() {
        return tonner;
    }

    public void setTonner(List<String> tonner) {
        this.tonner = tonner;
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

    public String getNombre_area() {
        return nombre_area;
    }

    public void setNombre_area(String nombre_area) {
        this.nombre_area = nombre_area;
    }

    public String getNombre_marca() {
        return nombre_marca;
    }

    public void setNombre_marca(String nombre_marca) {
        this.nombre_marca = nombre_marca;
    }

    public String getNombre_modelo() {
        return nombre_modelo;
    }

    public void setNombre_modelo(String nombre_modelo) {
        this.nombre_modelo = nombre_modelo;
    }

    public String getNombre_tonner() {
        return nombre_tonner;
    }

    public void setNombre_tonner(String nombre_tonner) {
        this.nombre_tonner = nombre_tonner;
    }

    public void nuevo() {
        this.impresoraseleccionada = new Impresora();
        llenar_lista_entidad();
        llenar_lista_marca();
        llenar_lista_modelo();
        llenar_lista_tonner();
    }

    public void guardar() throws Exception {
//        llenar_lista_entidad();
//        llenar_lista_marca();
//        llenar_lista_modelo();
//        llenar_lista_tonner();

        Impresora nueva = new Impresora();
        System.out.println("entro");
        if (!yaexiste()) {
            nueva.setNoInventario(this.impresoraseleccionada.getNoInventario());
            nueva.setResponsable(this.impresoraseleccionada.getResponsable());
            System.out.println("añadio id y responsable");
            for (int i = 0; i < Ctrl.ctrlMarca.findMarcaEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_marca.equals(Ctrl.ctrlMarca.findMarcaEntities().get(i).getNombreMarca())) {
                    System.out.println("encontro el nombre marca");
                    nueva.setMarcaidMarca(Ctrl.ctrlMarca.findMarcaEntities().get(i));
                    break;
                }
            }
            for (int i = 0; i < Ctrl.ctrlModelo.findModeloEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_modelo.equals(Ctrl.ctrlModelo.findModeloEntities().get(i).getNombreModelo())) {
                    System.out.println("encontro el nombre modelo");
                    nueva.setModeloidModelo(Ctrl.ctrlModelo.findModeloEntities().get(i));
                    break;
                }
            }
            for (int i = 0; i < Ctrl.ctrlTipoTonner.findTTonnerEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_tonner.equals(Ctrl.ctrlTipoTonner.findTTonnerEntities().get(i).getTipo())) {
                    System.out.println("encontro el nombre toner");
                    nueva.setTTonnersnTonner(Ctrl.ctrlTipoTonner.findTTonnerEntities().get(i));
                    break;
                }
            }
            for (int i = 0; i < Ctrl.ctrlEntidad.findEntidadEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_entidad.equals(Ctrl.ctrlEntidad.findEntidadEntities().get(i).getNombreEntidad())) {
                    System.out.println("encontro el nombre unidad");
                    nueva.setEntidadidEntidad(Ctrl.ctrlEntidad.findEntidadEntities().get(i));
                    break;
                }
            }
            for (int i = 0; i < Ctrl.ctrlDepartamento.findDepartamentoEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_departamento.equals(Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i).getNombreDepartamento())) {
                    System.out.println("encontro el nombre dpto");
                    nueva.setDepartamentoidDepartamento(Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i));
                    break;
                }
            }
            for (int i = 0; i < Ctrl.ctrlArea.findAreaEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_area.equals("")) {
                    nueva.setAreaidArea(Ctrl.ctrlArea.findAreaEntities().get(0));
                    break;
                } else {
                    if (nombre_area.equals(Ctrl.ctrlArea.findAreaEntities().get(i).getNombreArea())) {
                        System.out.println("encontro el nombre area");
                        nueva.setAreaidArea(Ctrl.ctrlArea.findAreaEntities().get(i));
                        break;
                    }
                }

            }
            Ctrl.ctrlImpresora.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impresora Añadida"));
            marca.clear();
            modelo.clear();
            tonner.clear();
            departamentos.clear();
            area.clear();
        } else {
            try {
                nueva.setNoInventario(this.impresoraseleccionada.getNoInventario());
                nueva.setResponsable(this.impresoraseleccionada.getResponsable());
                System.out.println("añadio id y responsable");
                for (int i = 0; i < Ctrl.ctrlMarca.findMarcaEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_marca.equals(Ctrl.ctrlMarca.findMarcaEntities().get(i).getNombreMarca())) {
                        System.out.println("encontro el nombre marca");
                        nueva.setMarcaidMarca(Ctrl.ctrlMarca.findMarcaEntities().get(i));
                        break;
                    }
                }
                for (int i = 0; i < Ctrl.ctrlModelo.findModeloEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_modelo.equals(Ctrl.ctrlModelo.findModeloEntities().get(i).getNombreModelo())) {
                        System.out.println("encontro el nombre modelo");
                        nueva.setModeloidModelo(Ctrl.ctrlModelo.findModeloEntities().get(i));
                        break;
                    }
                }
                for (int i = 0; i < Ctrl.ctrlTipoTonner.findTTonnerEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_tonner.equals(Ctrl.ctrlTipoTonner.findTTonnerEntities().get(i).getTipo())) {
                        System.out.println("encontro el nombre toner");
                        nueva.setTTonnersnTonner(Ctrl.ctrlTipoTonner.findTTonnerEntities().get(i));
                        break;
                    }
                }
                for (int i = 0; i < Ctrl.ctrlEntidad.findEntidadEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_entidad.equals(Ctrl.ctrlEntidad.findEntidadEntities().get(i).getNombreEntidad())) {
                        System.out.println("encontro el nombre unidad");
                        nueva.setEntidadidEntidad(Ctrl.ctrlEntidad.findEntidadEntities().get(i));
                        break;
                    }
                }
                for (int i = 0; i < Ctrl.ctrlDepartamento.findDepartamentoEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_departamento.equals(Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i).getNombreDepartamento())) {
                        System.out.println("encontro el nombre dpto");
                        nueva.setDepartamentoidDepartamento(Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i));
                        break;
                    }
                }
                for (int i = 0; i < Ctrl.ctrlArea.findAreaEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_area.equals("")) {
                        nueva.setAreaidArea(Ctrl.ctrlArea.findAreaEntities().get(0));
                        break;
                    } else {
                        if (nombre_area.equals(Ctrl.ctrlArea.findAreaEntities().get(i).getNombreArea())) {
                            System.out.println("encontro el nombre area");
                            nueva.setAreaidArea(Ctrl.ctrlArea.findAreaEntities().get(i));
                            break;
                        }
                    }

                }
                Ctrl.ctrlImpresora.edit(nueva);
                System.out.println("edito");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impresora Actualizada"));
                marca.clear();
                modelo.clear();
                tonner.clear();
                departamentos.clear();
                area.clear();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No es posible modificar el ID"));
            }

        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-impresoras");

    }

    public void llenar_lista_marca() {
        this.marca = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlMarca.findMarcaEntities().size(); i++) {
            this.marca.add(Ctrl.ctrlMarca.findMarcaEntities().get(i).getNombreMarca());
        }
    }

    public void llenar_lista_modelo() {
        this.modelo = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlModelo.findModeloEntities().size(); i++) {
            this.modelo.add(Ctrl.ctrlModelo.findModeloEntities().get(i).getNombreModelo());
        }
    }

    public void llenar_lista_tonner() {
        this.tonner = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlTipoTonner.findTTonnerEntities().size(); i++) {
            this.tonner.add(Ctrl.ctrlTipoTonner.findTTonnerEntities().get(i).getTipo());
        }

    }

    public void llenar_lista_entidad() {
        this.entidades = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlEntidad.findEntidadEntities().size(); i++) {
            this.entidades.add(Ctrl.ctrlEntidad.findEntidadEntities().get(i).getNombreEntidad());
        }
        llenar_lista_departamento();

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
//        llenar_lista_area();
    }

    public void llenar_lista_area() {
        for (int i = 0; i < Ctrl.ctrlDepartamento.findDepartamentoEntities().size(); i++) {
            if (Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i).getNombreDepartamento().equals(nombre_departamento)) {
                for (int j = 0; j < Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i).getAreaList().size(); j++) {
                    area.add(Ctrl.ctrlDepartamento.findDepartamentoEntities().get(i).getAreaList().get(j).getNombreArea());
                    System.out.println(area.get(j));
                }
            } else {
            }
        }
    }

    public boolean yaexiste() {
        for (int i = 0; i < impresoras.size(); i++) {
            if (impresoraseleccionada.getNoInventario().equals(impresoras.get(i).getNoInventario()) || impresoraseleccionada.getNoInventario().equals(impresoras.get(i).getNoInventario())) {

                return true;
            }
        }
        return false;
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Impresora eliminada = new Impresora();
        eliminada.setNoInventario(impresoraseleccionada.getNoInventario());
        Ctrl.ctrlImpresora.destroy(eliminada.getNoInventario());
        this.impresoraseleccionada = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impresora eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-impresoras");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " Impresoras seleccionadas" : "1 Impresora Seleccionada";
        }

        return "Eliminar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlImpresora.destroy(seleccionadas.get(i).getNoInventario());
        }
        this.impresoras.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impresora Eliminada"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-impresoras");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
