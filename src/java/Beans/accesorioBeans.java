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
public class accesorioBeans {

    public List<Accesorio> accesorios;
    public List<Accesorio> seleccionadas;
    public Accesorio accesorioseleccionado;
    public List<String> marca = new ArrayList<String>();
    public List<String> modelo = new ArrayList<String>();
    public List<String> t_conexion = new ArrayList<String>();
    public List<String> t_accesorio = new ArrayList<String>();
    public List<String> estados = new ArrayList<String>();
    private String nombre_marca;
    private String nombre_modelo;
    private String nombre_tconexion;
    private String nombre_taccesorio;
    private String estado;
    
    @PostConstruct
    public void init() {
        this.accesorios = Ctrl.ctrlAccesorio.findAccesorioEntities();
        llenar_lista_marca();
        llenar_lista_modelo();
        llenar_lista_tconexion();
        llenar_lista_taccesorio();

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

    
    
    public List<Accesorio> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<Accesorio> accesorios) {
        this.accesorios = accesorios;
    }

    public List<Accesorio> getSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(List<Accesorio> seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public Accesorio getAccesorioseleccionado() {
        return accesorioseleccionado;
    }

    public void setAccesorioseleccionado(Accesorio accesorioseleccionado) {
        this.accesorioseleccionado = accesorioseleccionado;
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

    public List<String> getT_accesorio() {
        return t_accesorio;
    }

    public void setT_accesorio(List<String> t_accesorio) {
        this.t_accesorio = t_accesorio;
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

    public String getNombre_taccesorio() {
        return nombre_taccesorio;
    }

    public void setNombre_taccesorio(String nombre_taccesorio) {
        this.nombre_taccesorio = nombre_taccesorio;
    }

    public void nuevo() {
        this.accesorioseleccionado = new Accesorio();
        llenar_lista_estados();
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
    
    public void llenar_lista_taccesorio() {
        this.t_accesorio = new ArrayList<String>();
        for (int i = 0; i < Ctrl.ctrlTipoAccesosrio.findTAccesorioEntities().size(); i++) {
            this.t_accesorio.add(Ctrl.ctrlTipoAccesosrio.findTAccesorioEntities().get(i).getNombreAccesorio());
        }
    }
    
    public void guardar() throws Exception {
        Accesorio nueva = new Accesorio();
        System.out.println("entro");
        if (!yaexiste()) {
            nueva.setSnAccesorio(this.accesorioseleccionado.getSnAccesorio());
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
            for (int i = 0; i < Ctrl.ctrlTipoAccesosrio.findTAccesorioEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_taccesorio.equals(Ctrl.ctrlTipoAccesosrio.findTAccesorioEntities().get(i).getNombreAccesorio())) {
                    System.out.println("encontro el nombre taccesorio");
                    nueva.setTAccesorioidAccesorio(Ctrl.ctrlTipoAccesosrio.findTAccesorioEntities().get(i));
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
            Ctrl.ctrlAccesorio.create(nueva);
            System.out.println("creo");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accesorio Añadido"));
        } else {
            try {
                nueva.setSnAccesorio(this.accesorioseleccionado.getSnAccesorio());
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
            for (int i = 0; i < Ctrl.ctrlTipoAccesosrio.findTAccesorioEntities().size(); i++) {
                System.out.println("entro al for");
                if (nombre_taccesorio.equals(Ctrl.ctrlTipoAccesosrio.findTAccesorioEntities().get(i).getNombreAccesorio())) {
                    System.out.println("encontro el nombre taccesorio");
                    nueva.setTAccesorioidAccesorio(Ctrl.ctrlTipoAccesosrio.findTAccesorioEntities().get(i));
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
                Ctrl.ctrlAccesorio.edit(nueva);
                System.out.println("edito");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accesorio Actualizado"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No es posible modificar el ID"));
            }
        }
        
        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-accesorios");

    }
    
    public boolean yaexiste() {
        for (int i = 0; i < accesorios.size(); i++) {
            if (accesorioseleccionado.getSnAccesorio().equals(accesorios.get(i).getSnAccesorio()) || accesorioseleccionado.getSnAccesorio().equals(accesorios.get(i).getSnAccesorio())) {

                return true;
            }
        }
        return false;
    }
    
    public void deleteProduct() throws IllegalOrphanException, NonexistentEntityException {
        Accesorio eliminada = new Accesorio();
        eliminada.setSnAccesorio(accesorioseleccionado.getSnAccesorio());
        Ctrl.ctrlAccesorio.destroy(eliminada.getSnAccesorio());
        this.accesorioseleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accesorio eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-accesorios");
    }

    public String getdeleteButtonMessage() {
        if (hasSeleccionadoElementos()) {
            int size = this.seleccionadas.size();
            return size > 1 ? size + " Accesorios seleccionados" : "1 Accesorio Seleccionado";
        }

        return "Eliminar";
    }

    public boolean hasSeleccionadoElementos() {
        return this.seleccionadas != null && !this.seleccionadas.isEmpty();
    }

    public void deleteSelectedProducts() throws IllegalOrphanException, NonexistentEntityException {
        System.out.println("entro a eliminar");
        for (int i = 0; i < seleccionadas.size(); i++) {
            Ctrl.ctrlAccesorio.destroy(seleccionadas.get(i).getSnAccesorio());
        }
        this.accesorios.removeAll(this.seleccionadas);
        this.seleccionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accesorio Eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-accesorios");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }
    
    public void llenar_lista_estados() {
        this.estados = new ArrayList<>();
        for (int i = 0; i < Ctrl.ctrlEstado.findEstadoEntities().size(); i++) {
            this.estados.add(Ctrl.ctrlEstado.findEstadoEntities().get(i).getNombreEstado());
        }
    }
}
