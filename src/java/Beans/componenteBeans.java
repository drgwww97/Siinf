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
public class componenteBeans {

    public List<Componente> componentes;
    public List<Componente> seleccionadas;
    public Componente componenteseleccionado;
    public List<String> marca = new ArrayList<String>();
    public List<String> modelo = new ArrayList<String>();
    public List<String> t_conexion = new ArrayList<String>();
    public List<String> t_componente = new ArrayList<String>();
    public List<String> estados = new ArrayList<String>();
    private String estado;
    private String nombre_marca;
    private String nombre_modelo;
    private String nombre_tconexion;
    private String nombre_tcomponente;

    @PostConstruct
    public void init() {
        this.componentes = Ctrl.ctrlComponente.findComponenteEntities();
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    public List<Componente> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Componente> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Componente getComponenteseleccionado() {
        return componenteseleccionado;
    }

    public void setComponenteseleccionado(Componente componenteseleccionado) {
        this.componenteseleccionado = componenteseleccionado;
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

    public List<String> getT_conexion() {
        return t_conexion;
    }

    public void setT_conexion(List<String> t_conexion) {
        this.t_conexion = t_conexion;
    }

    public List<String> getT_componente() {
        return t_componente;
    }

    public void setT_componente(List<String> t_componente) {
        this.t_componente = t_componente;
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

    public String getNombre_tconexion() {
        return nombre_tconexion;
    }

    public void setNombre_tconexion(String nombre_tconexion) {
        this.nombre_tconexion = nombre_tconexion;
    }

    public String getNombre_tcomponente() {
        return nombre_tcomponente;
    }

    public void setNombre_tcomponente(String nombre_tcomponente) {
        this.nombre_tcomponente = nombre_tcomponente;
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
        this.componenteseleccionado = new Componente();
        llenar_lista_marca();
        llenar_lista_modelo();
        llenar_lista_tconexion();
        llenar_lista_tcomponente();
        llenar_lista_estados();
    }

    public void llenar_lista_estados() {
        this.estados = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
            this.estados.add(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado());
        }
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

    public void llenar_lista_tconexion() {
        this.t_conexion = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlTipoConexion.findTConexionEntities().size(); i++) {
            this.t_conexion.add(Ctrl.ctrlTipoConexion.findTConexionEntities().get(i).getNombreConexion());
        }
    }

    public void llenar_lista_tcomponente() {
        this.t_componente = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlTipoComponente.findTComponenteEntities().size(); i++) {
            this.t_componente.add(Ctrl.ctrlTipoComponente.findTComponenteEntities().get(i).getNombreComponente());
        }
    }

    public void guardar() throws Exception {
        Componente nueva = new Componente();
        System.out.println("entro");
        if (!yaexiste()) {
            nueva.setSnComponente(this.componenteseleccionado.getSnComponente());
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
            for (int i = 0; i < Ctrl.ctrlTipoConexion.findTConexionEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_tconexion.equals(Ctrl.ctrlTipoConexion.findTConexionEntities().get(i).getNombreConexion())) {
                    System.out.println("encontro el nombre tconexion");
                    nueva.setTConexionidConexion(Ctrl.ctrlTipoConexion.findTConexionEntities().get(i));
                    break;
                }
            }
            for (int i = 0; i < Ctrl.ctrlTipoComponente.findTComponenteEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_tcomponente.equals(Ctrl.ctrlTipoComponente.findTComponenteEntities().get(i).getNombreComponente())) {
                    System.out.println("encontro el nombre taccesorio");
                    nueva.setTComponenteidComponente(Ctrl.ctrlTipoComponente.findTComponenteEntities().get(i));
                    break;
                }
            }
            nueva.setCapacidad(this.componenteseleccionado.getCapacidad());
            for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
                System.out.println("entro al for");
                if (estado.equals(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado())) {
                    System.out.println("encontro el estado");
                    nueva.setEstadoidEstado(Ctrl.ctrlEstado.findEstadoEntities().get(i));
                    break;
                }
            }
            Ctrl.ctrlComponente.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Componente Añadido"));
        } else {
            try {
                nueva.setSnComponente(this.componenteseleccionado.getSnComponente());
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
                for (int i = 0; i < Ctrl.ctrlTipoConexion.findTConexionEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_tconexion.equals(Ctrl.ctrlTipoConexion.findTConexionEntities().get(i).getNombreConexion())) {
                        System.out.println("encontro el nombre tconexion");
                        nueva.setTConexionidConexion(Ctrl.ctrlTipoConexion.findTConexionEntities().get(i));
                        break;
                    }
                }
                for (int i = 0; i < Ctrl.ctrlTipoComponente.findTComponenteEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (nombre_tcomponente.equals(Ctrl.ctrlTipoComponente.findTComponenteEntities().get(i).getNombreComponente())) {
                        System.out.println("encontro el nombre taccesorio");
                        nueva.setTComponenteidComponente(Ctrl.ctrlTipoComponente.findTComponenteEntities().get(i));
                        break;
                    }
                }
                nueva.setCapacidad(this.componenteseleccionado.getCapacidad());
                for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
                    System.out.println("entro al for");
                    if (estado.equals(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado())) {
                        System.out.println("encontro el estado");
                        nueva.setEstadoidEstado(Ctrl.ctrlEstado.findEstadoEntities().get(i));
                        break;
                    }
                }
                Ctrl.ctrlComponente.edit(nueva);
                System.out.println("edito");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Componente Actualizado"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No es posible modificar el ID"));
            }
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-componentes");

    }

    public boolean yaexiste() {
        for (int i = 0; i < componentes.size(); i++) {
            if (componenteseleccionado.getSnComponente().equals(componentes.get(i).getSnComponente()) || componenteseleccionado.getSnComponente().equals(componentes.get(i).getSnComponente())) {

                return true;
            }
        }
        return false;
    }

    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Componente eliminada = new Componente();
        eliminada.setSnComponente(componenteseleccionado.getSnComponente());
        Ctrl.ctrlComponente.destroy(eliminada.getSnComponente());
        this.componenteseleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Componente eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-componentes");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " Componentes seleccionados" : "1 Componente Seleccionado";
        }

        return "Eliminar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlComponente.destroy(seleccionadas.get(i).getSnComponente());
        }
        this.componentes.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Componente Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-componentes");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
}
