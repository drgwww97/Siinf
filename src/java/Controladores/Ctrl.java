package Controladores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dayana
 */
public class Ctrl {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIInfPU");
    public static AccesorioJpaController ctrlAccesorio = new AccesorioJpaController(emf);
    public static AlmacenJpaController ctrlAlmacen = new AlmacenJpaController(emf);
    public static AreaJpaController ctrlArea = new AreaJpaController(emf);
    public static EstadoJpaController ctrlEstado = new EstadoJpaController(emf);
    public static ComponenteJpaController ctrlComponente = new ComponenteJpaController(emf);
    public static DepartamentoJpaController ctrlDepartamento = new DepartamentoJpaController(emf);
    public static EntidadJpaController ctrlEntidad = new EntidadJpaController(emf);
    public static ImpresoraJpaController ctrlImpresora = new ImpresoraJpaController(emf);
    public static LoginJpaController ctrlLogin = new LoginJpaController(emf);
    public static MarcaJpaController ctrlMarca = new MarcaJpaController(emf);
    public static ModeloJpaController ctrlModelo = new ModeloJpaController(emf);
    public static ModificacionJpaController ctrlModificacion = new ModificacionJpaController(emf);
    public static PcJpaController ctrlPc = new PcJpaController(emf);
    public static ProgramasJpaController ctrlProgramas = new ProgramasJpaController(emf);
    public static SOJpaController ctrlSO = new SOJpaController(emf);
    public static TAccesorioJpaController ctrlTipoAccesosrio = new TAccesorioJpaController(emf);
    public static TComponenteJpaController ctrlTipoComponente = new TComponenteJpaController(emf);
    public static TConexionJpaController ctrlTipoConexion = new TConexionJpaController(emf);
    public static TEquipoJpaController ctrlTipoEquipo = new TEquipoJpaController(emf);
    public static TModificacionJpaController ctrlTipoModificaion = new TModificacionJpaController(emf);
    public static TTonnerJpaController ctrlTipoTonner = new TTonnerJpaController(emf);
    public static UsuarioJpaController ctrlUsuario = new UsuarioJpaController(emf);
}
