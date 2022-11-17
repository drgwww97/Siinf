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
public class compyaccPCBeans {

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

    
}
