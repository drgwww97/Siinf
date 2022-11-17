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
public class pcBeans {

    public List<Pc> pcs;
    public List<Pc> seleccionadas;
    public Pc pcseleccionada;
    public List<String> entidades;
    public List<String> departamentos = new ArrayList<String>();
    public List<String> area = new ArrayList<String>();
    public List<String> t_equipo = new ArrayList<String>();
    public List<String> marca = new ArrayList<String>();
    public List<String> modelo = new ArrayList<String>();
    public List<String> componentes = new ArrayList<String>();
    public List<String> accesorios = new ArrayList<String>();
    public List<String> modificaciones = new ArrayList<String>();
    public List<String> s_operativo = new ArrayList<String>();
    public List<String> estados = new ArrayList<String>();
    public List<String> programas = new ArrayList<String>();
    private String estado;
    private String nombre_entidad;
    private String nombre_departamento;
    private String nombre_area;
    private String nombre_marca;
    private String nombre_modelo;
    private String nombre_tequipo;
    private List<String> programasseleccionados = new ArrayList<String>();

    private String nombre_soperativo;
//    private String nombre_programas;

    @PostConstruct
    public void init() {
        this.pcs = Ctrl.ctrlPc.findPcEntities();
    }

    public List<Pc> getPcs() {
        return pcs;
    }

    public void setPcs(List<Pc> pcs) {
        this.pcs = pcs;
    }

    public List<Pc> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Pc> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Pc getPcseleccionada() {
        return pcseleccionada;
    }

    public void setPcseleccionada(Pc pcseleccionada) {
        this.pcseleccionada = pcseleccionada;
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

    public List<String> getT_equipo() {
        return t_equipo;
    }

    public void setT_equipo(List<String> t_equipo) {
        this.t_equipo = t_equipo;
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

    public List<String> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<String> componentes) {
        this.componentes = componentes;
    }

    public List<String> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<String> accesorios) {
        this.accesorios = accesorios;
    }

    public List<String> getModificaciones() {
        return modificaciones;
    }

    public void setModificaciones(List<String> modificaciones) {
        this.modificaciones = modificaciones;
    }

    public List<String> getS_operativo() {
        return s_operativo;
    }

    public void setS_operativo(List<String> s_operativo) {
        this.s_operativo = s_operativo;
    }

    public List<String> getProgramas() {
        return programas;
    }

    public void setProgramas(List<String> programas) {
        this.programas = programas;
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

    public String getNombre_tequipo() {
        return nombre_tequipo;
    }

    public void setNombre_tequipo(String nombre_tequipo) {
        this.nombre_tequipo = nombre_tequipo;
    }

    public String getNombre_soperativo() {
        return nombre_soperativo;
    }

    public void setNombre_soperativo(String nombre_soperativo) {
        this.nombre_soperativo = nombre_soperativo;
    }

    public List<String> getProgramasseleccionados() {
        return programasseleccionados;
    }

    public void setProgramasseleccionados(List<String> programasseleccionados) {
        this.programasseleccionados = programasseleccionados;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    public void nuevo() {
        this.pcseleccionada = new Pc();
        llenar_lista_entidad();
        llenar_lista_tequipo();
        llenar_lista_marca();
        llenar_lista_modelo();
        llenar_lista_componente();
        llenar_lista_accesorio();
        llenar_lista_modificacion();
        llenar_lista_soperativo();
        llenar_lista_programas();
        llenar_lista_estados();
    }

    public void guardar() throws Exception {
        Pc nueva = new Pc();
        System.out.println("entro");
        if (!yaexiste()) {
            nueva.setNoInventario(this.pcseleccionada.getNoInventario());
            nueva.setResponsable(this.pcseleccionada.getResponsable());
            nueva.setSnPc(this.pcseleccionada.getSnPc());
            nueva.setSelloPc(this.pcseleccionada.getSelloPc());
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
            for (int i = 0; i < Ctrl.ctrlTipoEquipo.findTEquipoEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_tequipo.equals(Ctrl.ctrlTipoEquipo.findTEquipoEntities().get(i).getNombreEquipo())) {
                    System.out.println("encontro el nombre equipo");
                    nueva.setTEquipoidEquipo(Ctrl.ctrlTipoEquipo.findTEquipoEntities().get(i));
                    break;
                }
            }
            for (int i = 0; i < Ctrl.ctrlSO.findSOEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_soperativo.equals(Ctrl.ctrlSO.findSOEntities().get(i).getNombreSo())) {
                    System.out.println("encontro el nombre soperativo");
                    nueva.setSOidSo(Ctrl.ctrlSO.findSOEntities().get(i));
                    break;
                }
            }
            for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (estado.equals(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado())) {
                        System.out.println("encontro el estado");
                        nueva.setEstadoidEstado(Ctrl.ctrlEstado.findEstadoEntities().get(i));
                        break;
                    }
                }
//            for (int i = 0; i < Ctrl.ctrlSO.findSOEntities().size(); i++) {
//                System.out.println("entro al for");
//                if (nombre_soperativo.equals(Ctrl.ctrlSO.findSOEntities().get(i).getNombreSo())) {
//                    System.out.println("encontro el nombre equipo");
//                    nueva.setSOidSo(Ctrl.ctrlSO.findSOEntities().get(i));
//                    break;
//                }
//            }
//            
            for (int i = 0; i < Ctrl.ctrlProgramas.findProgramasEntities().size(); i++) {
                for (int j = 0; j < programasseleccionados.size(); j++) {
                    if (programasseleccionados.get(j).equals(Ctrl.ctrlProgramas.findProgramasEntities().get(i).getNombreProg())) {
                        System.out.println("encontro los programas");
                        nueva.setProgramasidProgramas(Ctrl.ctrlProgramas.findProgramasEntities().get(i));
                        System.out.println("añadio los programas");
                    }
                }

            }
            Ctrl.ctrlPc.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Equipo Añadido"));
            marca.clear();
            modelo.clear();
            departamentos.clear();
            area.clear();
        } else {
            try {
                nueva.setNoInventario(this.pcseleccionada.getNoInventario());
                nueva.setResponsable(this.pcseleccionada.getResponsable());
                nueva.setSnPc(this.pcseleccionada.getSnPc());
                nueva.setSelloPc(this.pcseleccionada.getSelloPc());
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
                for (int i = 0; i < Ctrl.ctrlTipoEquipo.findTEquipoEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_tequipo.equals(Ctrl.ctrlTipoEquipo.findTEquipoEntities().get(i).getNombreEquipo())) {
                        System.out.println("encontro el nombre equipo");
                        nueva.setTEquipoidEquipo(Ctrl.ctrlTipoEquipo.findTEquipoEntities().get(i));
                        break;
                    }
                }
                for (int i = 0; i < Ctrl.ctrlSO.findSOEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_soperativo.equals(Ctrl.ctrlSO.findSOEntities().get(i).getNombreSo())) {
                        System.out.println("encontro el so");
                        nueva.setSOidSo(Ctrl.ctrlSO.findSOEntities().get(i));
                        break;
                    }
                }
                for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (estado.equals(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado())) {
                        System.out.println("encontro el estado");
                        nueva.setEstadoidEstado(Ctrl.ctrlEstado.findEstadoEntities().get(i));
                        break;
                    }
                }
                
//            for (int i = 0; i < Ctrl.ctrlSO.findSOEntities().size(); i++) {
//                System.out.println("entro al for");
//                if (nombre_soperativo.equals(Ctrl.ctrlSO.findSOEntities().get(i).getNombreSo())) {
//                    System.out.println("encontro el nombre equipo");
//                    nueva.setSOidSo(Ctrl.ctrlSO.findSOEntities().get(i));
//                    break;
//                }
//            }
//            
                for (int i = 0; i < Ctrl.ctrlProgramas.findProgramasEntities().size(); i++) {
                    for (int j = 0; j < programasseleccionados.size(); j++) {
                        if (programasseleccionados.get(j).equals(Ctrl.ctrlProgramas.findProgramasEntities().get(i).getNombreProg())) {
                            System.out.println("encontro los programas");
                            nueva.setProgramasidProgramas(Ctrl.ctrlProgramas.findProgramasEntities().get(i));
                            System.out.println("añadio los programas");
                        }
                    }

                }

                Ctrl.ctrlPc.edit(nueva);
                System.out.println("modifico");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Equipo Modificado"));
                marca.clear();
                modelo.clear();
                departamentos.clear();
                area.clear();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No es posible modificar el ID"));
            }

        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-pc");

    }

    public void llenar_lista_entidad() {
        this.entidades = new ArrayList<String>();
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

    public void llenar_lista_tequipo() {
        this.t_equipo = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlTipoEquipo.findTEquipoEntities().size(); i++) {
            this.t_equipo.add(Ctrl.ctrlTipoEquipo.findTEquipoEntities().get(i).getNombreEquipo());
        }
    }

    public void llenar_lista_marca() {
        this.marca = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlMarca.findMarcaEntities().size(); i++) {
            this.marca.add(Ctrl.ctrlMarca.findMarcaEntities().get(i).getNombreMarca());
        }
    }

    public void llenar_lista_modelo() {
        this.modelo = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlModelo.findModeloEntities().size(); i++) {
            this.modelo.add(Ctrl.ctrlModelo.findModeloEntities().get(i).getNombreModelo());
        }
    }

    public void llenar_lista_componente() {
        this.componentes = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlComponente.findComponenteEntities().size(); i++) {
            this.componentes.add(Ctrl.ctrlComponente.findComponenteEntities().get(i).getTComponenteidComponente().getNombreComponente());
        }
    }

    public void llenar_lista_accesorio() {
        this.accesorios = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlAccesorio.findAccesorioEntities().size(); i++) {
            this.accesorios.add(Ctrl.ctrlAccesorio.findAccesorioEntities().get(i).getTAccesorioidAccesorio().getNombreAccesorio());
        }
    }

    public void llenar_lista_modificacion() {
        this.modificaciones = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlTipoModificaion.findTModificacionEntities().size(); i++) {
            this.modificaciones.add(Ctrl.ctrlTipoModificaion.findTModificacionEntities().get(i).getNModificacion());
        }
    }

    public void llenar_lista_soperativo() {
        this.s_operativo = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlSO.findSOEntities().size(); i++) {
            this.s_operativo.add(Ctrl.ctrlSO.findSOEntities().get(i).getNombreSo());
        }
    }

    public void llenar_lista_programas() {
        this.programas = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlProgramas.findProgramasEntities().size(); i++) {
            this.programas.add(Ctrl.ctrlProgramas.findProgramasEntities().get(i).getNombreProg());
        }
    }
    
    public void llenar_lista_estados() {
        this.estados = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
            this.estados.add(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado());
        }
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Pc eliminada = new Pc();
        eliminada.setNoInventario(pcseleccionada.getNoInventario());
        System.out.println("entro a elmininar");
        Ctrl.ctrlPc.destroy(eliminada.getNoInventario());
        this.pcseleccionada = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Equipo eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-pc");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " Equipos seleccionados" : "1 Equipo seleccionado";
        }

        return "Eliminar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public boolean yaexiste() {
        for (int i = 0; i < pcs.size(); i++) {
            if (pcseleccionada.getNoInventario().equals(pcs.get(i).getNoInventario())) {

                return true;
            }
        }
        return false;
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlPc.destroy(seleccionadas.get(i).getNoInventario());
        }
        this.pcs.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Equipos Eliminados"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-pc");
        PrimeFaces.current().executeScript("PF('dtPc').clearFilters()");
    }

}
