/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Area;
import Entidades.Departamento;
import Entidades.Entidad;
import Entidades.Estado;
import Entidades.Marca;
import Entidades.Modelo;
import Entidades.Pc;
import Entidades.TComponente;
import Entidades.TConexion;
import Entidades.Almacen;
import Entidades.Componente;
import java.util.ArrayList;
import java.util.List;
import Entidades.Modificacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class ComponenteJpaController implements Serializable {

    public ComponenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Componente componente) throws PreexistingEntityException, Exception {
        if (componente.getAlmacenList() == null) {
            componente.setAlmacenList(new ArrayList<Almacen>());
        }
        if (componente.getModificacionList() == null) {
            componente.setModificacionList(new ArrayList<Modificacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area areaidArea = componente.getAreaidArea();
            if (areaidArea != null) {
                areaidArea = em.getReference(areaidArea.getClass(), areaidArea.getIdArea());
                componente.setAreaidArea(areaidArea);
            }
            Departamento departamentoidDepartamento = componente.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento = em.getReference(departamentoidDepartamento.getClass(), departamentoidDepartamento.getIdDepartamento());
                componente.setDepartamentoidDepartamento(departamentoidDepartamento);
            }
            Entidad entidadidEntidad = componente.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad = em.getReference(entidadidEntidad.getClass(), entidadidEntidad.getIdEntidad());
                componente.setEntidadidEntidad(entidadidEntidad);
            }
            Estado estadoidEstado = componente.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado = em.getReference(estadoidEstado.getClass(), estadoidEstado.getIdEstado());
                componente.setEstadoidEstado(estadoidEstado);
            }
            Marca marcaidMarca = componente.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca = em.getReference(marcaidMarca.getClass(), marcaidMarca.getIdMarca());
                componente.setMarcaidMarca(marcaidMarca);
            }
            Modelo modeloidModelo = componente.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo = em.getReference(modeloidModelo.getClass(), modeloidModelo.getIdModelo());
                componente.setModeloidModelo(modeloidModelo);
            }
            Pc pcnoInventario = componente.getPcnoInventario();
            if (pcnoInventario != null) {
                pcnoInventario = em.getReference(pcnoInventario.getClass(), pcnoInventario.getNoInventario());
                componente.setPcnoInventario(pcnoInventario);
            }
            TComponente TComponenteidComponente = componente.getTComponenteidComponente();
            if (TComponenteidComponente != null) {
                TComponenteidComponente = em.getReference(TComponenteidComponente.getClass(), TComponenteidComponente.getIdComponente());
                componente.setTComponenteidComponente(TComponenteidComponente);
            }
            TConexion TConexionidConexion = componente.getTConexionidConexion();
            if (TConexionidConexion != null) {
                TConexionidConexion = em.getReference(TConexionidConexion.getClass(), TConexionidConexion.getIdConexion());
                componente.setTConexionidConexion(TConexionidConexion);
            }
            List<Almacen> attachedAlmacenList = new ArrayList<Almacen>();
            for (Almacen almacenListAlmacenToAttach : componente.getAlmacenList()) {
                almacenListAlmacenToAttach = em.getReference(almacenListAlmacenToAttach.getClass(), almacenListAlmacenToAttach.getIdAlmacen());
                attachedAlmacenList.add(almacenListAlmacenToAttach);
            }
            componente.setAlmacenList(attachedAlmacenList);
            List<Modificacion> attachedModificacionList = new ArrayList<Modificacion>();
            for (Modificacion modificacionListModificacionToAttach : componente.getModificacionList()) {
                modificacionListModificacionToAttach = em.getReference(modificacionListModificacionToAttach.getClass(), modificacionListModificacionToAttach.getTModificacionidTmodificacion());
                attachedModificacionList.add(modificacionListModificacionToAttach);
            }
            componente.setModificacionList(attachedModificacionList);
            em.persist(componente);
            if (areaidArea != null) {
                areaidArea.getComponenteList().add(componente);
                areaidArea = em.merge(areaidArea);
            }
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getComponenteList().add(componente);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            if (entidadidEntidad != null) {
                entidadidEntidad.getComponenteList().add(componente);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            if (estadoidEstado != null) {
                estadoidEstado.getComponenteList().add(componente);
                estadoidEstado = em.merge(estadoidEstado);
            }
            if (marcaidMarca != null) {
                marcaidMarca.getComponenteList().add(componente);
                marcaidMarca = em.merge(marcaidMarca);
            }
            if (modeloidModelo != null) {
                modeloidModelo.getComponenteList().add(componente);
                modeloidModelo = em.merge(modeloidModelo);
            }
            if (pcnoInventario != null) {
                pcnoInventario.getComponenteList().add(componente);
                pcnoInventario = em.merge(pcnoInventario);
            }
            if (TComponenteidComponente != null) {
                TComponenteidComponente.getComponenteList().add(componente);
                TComponenteidComponente = em.merge(TComponenteidComponente);
            }
            if (TConexionidConexion != null) {
                TConexionidConexion.getComponenteList().add(componente);
                TConexionidConexion = em.merge(TConexionidConexion);
            }
            for (Almacen almacenListAlmacen : componente.getAlmacenList()) {
                Componente oldComponentesnComponenteOfAlmacenListAlmacen = almacenListAlmacen.getComponentesnComponente();
                almacenListAlmacen.setComponentesnComponente(componente);
                almacenListAlmacen = em.merge(almacenListAlmacen);
                if (oldComponentesnComponenteOfAlmacenListAlmacen != null) {
                    oldComponentesnComponenteOfAlmacenListAlmacen.getAlmacenList().remove(almacenListAlmacen);
                    oldComponentesnComponenteOfAlmacenListAlmacen = em.merge(oldComponentesnComponenteOfAlmacenListAlmacen);
                }
            }
            for (Modificacion modificacionListModificacion : componente.getModificacionList()) {
                Componente oldComponentesnComponenteOfModificacionListModificacion = modificacionListModificacion.getComponentesnComponente();
                modificacionListModificacion.setComponentesnComponente(componente);
                modificacionListModificacion = em.merge(modificacionListModificacion);
                if (oldComponentesnComponenteOfModificacionListModificacion != null) {
                    oldComponentesnComponenteOfModificacionListModificacion.getModificacionList().remove(modificacionListModificacion);
                    oldComponentesnComponenteOfModificacionListModificacion = em.merge(oldComponentesnComponenteOfModificacionListModificacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComponente(componente.getSnComponente()) != null) {
                throw new PreexistingEntityException("Componente " + componente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Componente componente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componente persistentComponente = em.find(Componente.class, componente.getSnComponente());
            Area areaidAreaOld = persistentComponente.getAreaidArea();
            Area areaidAreaNew = componente.getAreaidArea();
            Departamento departamentoidDepartamentoOld = persistentComponente.getDepartamentoidDepartamento();
            Departamento departamentoidDepartamentoNew = componente.getDepartamentoidDepartamento();
            Entidad entidadidEntidadOld = persistentComponente.getEntidadidEntidad();
            Entidad entidadidEntidadNew = componente.getEntidadidEntidad();
            Estado estadoidEstadoOld = persistentComponente.getEstadoidEstado();
            Estado estadoidEstadoNew = componente.getEstadoidEstado();
            Marca marcaidMarcaOld = persistentComponente.getMarcaidMarca();
            Marca marcaidMarcaNew = componente.getMarcaidMarca();
            Modelo modeloidModeloOld = persistentComponente.getModeloidModelo();
            Modelo modeloidModeloNew = componente.getModeloidModelo();
            Pc pcnoInventarioOld = persistentComponente.getPcnoInventario();
            Pc pcnoInventarioNew = componente.getPcnoInventario();
            TComponente TComponenteidComponenteOld = persistentComponente.getTComponenteidComponente();
            TComponente TComponenteidComponenteNew = componente.getTComponenteidComponente();
            TConexion TConexionidConexionOld = persistentComponente.getTConexionidConexion();
            TConexion TConexionidConexionNew = componente.getTConexionidConexion();
            List<Almacen> almacenListOld = persistentComponente.getAlmacenList();
            List<Almacen> almacenListNew = componente.getAlmacenList();
            List<Modificacion> modificacionListOld = persistentComponente.getModificacionList();
            List<Modificacion> modificacionListNew = componente.getModificacionList();
            List<String> illegalOrphanMessages = null;
            for (Modificacion modificacionListOldModificacion : modificacionListOld) {
                if (!modificacionListNew.contains(modificacionListOldModificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Modificacion " + modificacionListOldModificacion + " since its componentesnComponente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (areaidAreaNew != null) {
                areaidAreaNew = em.getReference(areaidAreaNew.getClass(), areaidAreaNew.getIdArea());
                componente.setAreaidArea(areaidAreaNew);
            }
            if (departamentoidDepartamentoNew != null) {
                departamentoidDepartamentoNew = em.getReference(departamentoidDepartamentoNew.getClass(), departamentoidDepartamentoNew.getIdDepartamento());
                componente.setDepartamentoidDepartamento(departamentoidDepartamentoNew);
            }
            if (entidadidEntidadNew != null) {
                entidadidEntidadNew = em.getReference(entidadidEntidadNew.getClass(), entidadidEntidadNew.getIdEntidad());
                componente.setEntidadidEntidad(entidadidEntidadNew);
            }
            if (estadoidEstadoNew != null) {
                estadoidEstadoNew = em.getReference(estadoidEstadoNew.getClass(), estadoidEstadoNew.getIdEstado());
                componente.setEstadoidEstado(estadoidEstadoNew);
            }
            if (marcaidMarcaNew != null) {
                marcaidMarcaNew = em.getReference(marcaidMarcaNew.getClass(), marcaidMarcaNew.getIdMarca());
                componente.setMarcaidMarca(marcaidMarcaNew);
            }
            if (modeloidModeloNew != null) {
                modeloidModeloNew = em.getReference(modeloidModeloNew.getClass(), modeloidModeloNew.getIdModelo());
                componente.setModeloidModelo(modeloidModeloNew);
            }
            if (pcnoInventarioNew != null) {
                pcnoInventarioNew = em.getReference(pcnoInventarioNew.getClass(), pcnoInventarioNew.getNoInventario());
                componente.setPcnoInventario(pcnoInventarioNew);
            }
            if (TComponenteidComponenteNew != null) {
                TComponenteidComponenteNew = em.getReference(TComponenteidComponenteNew.getClass(), TComponenteidComponenteNew.getIdComponente());
                componente.setTComponenteidComponente(TComponenteidComponenteNew);
            }
            if (TConexionidConexionNew != null) {
                TConexionidConexionNew = em.getReference(TConexionidConexionNew.getClass(), TConexionidConexionNew.getIdConexion());
                componente.setTConexionidConexion(TConexionidConexionNew);
            }
            List<Almacen> attachedAlmacenListNew = new ArrayList<Almacen>();
            for (Almacen almacenListNewAlmacenToAttach : almacenListNew) {
                almacenListNewAlmacenToAttach = em.getReference(almacenListNewAlmacenToAttach.getClass(), almacenListNewAlmacenToAttach.getIdAlmacen());
                attachedAlmacenListNew.add(almacenListNewAlmacenToAttach);
            }
            almacenListNew = attachedAlmacenListNew;
            componente.setAlmacenList(almacenListNew);
            List<Modificacion> attachedModificacionListNew = new ArrayList<Modificacion>();
            for (Modificacion modificacionListNewModificacionToAttach : modificacionListNew) {
                modificacionListNewModificacionToAttach = em.getReference(modificacionListNewModificacionToAttach.getClass(), modificacionListNewModificacionToAttach.getTModificacionidTmodificacion());
                attachedModificacionListNew.add(modificacionListNewModificacionToAttach);
            }
            modificacionListNew = attachedModificacionListNew;
            componente.setModificacionList(modificacionListNew);
            componente = em.merge(componente);
            if (areaidAreaOld != null && !areaidAreaOld.equals(areaidAreaNew)) {
                areaidAreaOld.getComponenteList().remove(componente);
                areaidAreaOld = em.merge(areaidAreaOld);
            }
            if (areaidAreaNew != null && !areaidAreaNew.equals(areaidAreaOld)) {
                areaidAreaNew.getComponenteList().add(componente);
                areaidAreaNew = em.merge(areaidAreaNew);
            }
            if (departamentoidDepartamentoOld != null && !departamentoidDepartamentoOld.equals(departamentoidDepartamentoNew)) {
                departamentoidDepartamentoOld.getComponenteList().remove(componente);
                departamentoidDepartamentoOld = em.merge(departamentoidDepartamentoOld);
            }
            if (departamentoidDepartamentoNew != null && !departamentoidDepartamentoNew.equals(departamentoidDepartamentoOld)) {
                departamentoidDepartamentoNew.getComponenteList().add(componente);
                departamentoidDepartamentoNew = em.merge(departamentoidDepartamentoNew);
            }
            if (entidadidEntidadOld != null && !entidadidEntidadOld.equals(entidadidEntidadNew)) {
                entidadidEntidadOld.getComponenteList().remove(componente);
                entidadidEntidadOld = em.merge(entidadidEntidadOld);
            }
            if (entidadidEntidadNew != null && !entidadidEntidadNew.equals(entidadidEntidadOld)) {
                entidadidEntidadNew.getComponenteList().add(componente);
                entidadidEntidadNew = em.merge(entidadidEntidadNew);
            }
            if (estadoidEstadoOld != null && !estadoidEstadoOld.equals(estadoidEstadoNew)) {
                estadoidEstadoOld.getComponenteList().remove(componente);
                estadoidEstadoOld = em.merge(estadoidEstadoOld);
            }
            if (estadoidEstadoNew != null && !estadoidEstadoNew.equals(estadoidEstadoOld)) {
                estadoidEstadoNew.getComponenteList().add(componente);
                estadoidEstadoNew = em.merge(estadoidEstadoNew);
            }
            if (marcaidMarcaOld != null && !marcaidMarcaOld.equals(marcaidMarcaNew)) {
                marcaidMarcaOld.getComponenteList().remove(componente);
                marcaidMarcaOld = em.merge(marcaidMarcaOld);
            }
            if (marcaidMarcaNew != null && !marcaidMarcaNew.equals(marcaidMarcaOld)) {
                marcaidMarcaNew.getComponenteList().add(componente);
                marcaidMarcaNew = em.merge(marcaidMarcaNew);
            }
            if (modeloidModeloOld != null && !modeloidModeloOld.equals(modeloidModeloNew)) {
                modeloidModeloOld.getComponenteList().remove(componente);
                modeloidModeloOld = em.merge(modeloidModeloOld);
            }
            if (modeloidModeloNew != null && !modeloidModeloNew.equals(modeloidModeloOld)) {
                modeloidModeloNew.getComponenteList().add(componente);
                modeloidModeloNew = em.merge(modeloidModeloNew);
            }
            if (pcnoInventarioOld != null && !pcnoInventarioOld.equals(pcnoInventarioNew)) {
                pcnoInventarioOld.getComponenteList().remove(componente);
                pcnoInventarioOld = em.merge(pcnoInventarioOld);
            }
            if (pcnoInventarioNew != null && !pcnoInventarioNew.equals(pcnoInventarioOld)) {
                pcnoInventarioNew.getComponenteList().add(componente);
                pcnoInventarioNew = em.merge(pcnoInventarioNew);
            }
            if (TComponenteidComponenteOld != null && !TComponenteidComponenteOld.equals(TComponenteidComponenteNew)) {
                TComponenteidComponenteOld.getComponenteList().remove(componente);
                TComponenteidComponenteOld = em.merge(TComponenteidComponenteOld);
            }
            if (TComponenteidComponenteNew != null && !TComponenteidComponenteNew.equals(TComponenteidComponenteOld)) {
                TComponenteidComponenteNew.getComponenteList().add(componente);
                TComponenteidComponenteNew = em.merge(TComponenteidComponenteNew);
            }
            if (TConexionidConexionOld != null && !TConexionidConexionOld.equals(TConexionidConexionNew)) {
                TConexionidConexionOld.getComponenteList().remove(componente);
                TConexionidConexionOld = em.merge(TConexionidConexionOld);
            }
            if (TConexionidConexionNew != null && !TConexionidConexionNew.equals(TConexionidConexionOld)) {
                TConexionidConexionNew.getComponenteList().add(componente);
                TConexionidConexionNew = em.merge(TConexionidConexionNew);
            }
            for (Almacen almacenListOldAlmacen : almacenListOld) {
                if (!almacenListNew.contains(almacenListOldAlmacen)) {
                    almacenListOldAlmacen.setComponentesnComponente(null);
                    almacenListOldAlmacen = em.merge(almacenListOldAlmacen);
                }
            }
            for (Almacen almacenListNewAlmacen : almacenListNew) {
                if (!almacenListOld.contains(almacenListNewAlmacen)) {
                    Componente oldComponentesnComponenteOfAlmacenListNewAlmacen = almacenListNewAlmacen.getComponentesnComponente();
                    almacenListNewAlmacen.setComponentesnComponente(componente);
                    almacenListNewAlmacen = em.merge(almacenListNewAlmacen);
                    if (oldComponentesnComponenteOfAlmacenListNewAlmacen != null && !oldComponentesnComponenteOfAlmacenListNewAlmacen.equals(componente)) {
                        oldComponentesnComponenteOfAlmacenListNewAlmacen.getAlmacenList().remove(almacenListNewAlmacen);
                        oldComponentesnComponenteOfAlmacenListNewAlmacen = em.merge(oldComponentesnComponenteOfAlmacenListNewAlmacen);
                    }
                }
            }
            for (Modificacion modificacionListNewModificacion : modificacionListNew) {
                if (!modificacionListOld.contains(modificacionListNewModificacion)) {
                    Componente oldComponentesnComponenteOfModificacionListNewModificacion = modificacionListNewModificacion.getComponentesnComponente();
                    modificacionListNewModificacion.setComponentesnComponente(componente);
                    modificacionListNewModificacion = em.merge(modificacionListNewModificacion);
                    if (oldComponentesnComponenteOfModificacionListNewModificacion != null && !oldComponentesnComponenteOfModificacionListNewModificacion.equals(componente)) {
                        oldComponentesnComponenteOfModificacionListNewModificacion.getModificacionList().remove(modificacionListNewModificacion);
                        oldComponentesnComponenteOfModificacionListNewModificacion = em.merge(oldComponentesnComponenteOfModificacionListNewModificacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = componente.getSnComponente();
                if (findComponente(id) == null) {
                    throw new NonexistentEntityException("The componente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componente componente;
            try {
                componente = em.getReference(Componente.class, id);
                componente.getSnComponente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The componente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Modificacion> modificacionListOrphanCheck = componente.getModificacionList();
            for (Modificacion modificacionListOrphanCheckModificacion : modificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Componente (" + componente + ") cannot be destroyed since the Modificacion " + modificacionListOrphanCheckModificacion + " in its modificacionList field has a non-nullable componentesnComponente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Area areaidArea = componente.getAreaidArea();
            if (areaidArea != null) {
                areaidArea.getComponenteList().remove(componente);
                areaidArea = em.merge(areaidArea);
            }
            Departamento departamentoidDepartamento = componente.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getComponenteList().remove(componente);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            Entidad entidadidEntidad = componente.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad.getComponenteList().remove(componente);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            Estado estadoidEstado = componente.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado.getComponenteList().remove(componente);
                estadoidEstado = em.merge(estadoidEstado);
            }
            Marca marcaidMarca = componente.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca.getComponenteList().remove(componente);
                marcaidMarca = em.merge(marcaidMarca);
            }
            Modelo modeloidModelo = componente.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo.getComponenteList().remove(componente);
                modeloidModelo = em.merge(modeloidModelo);
            }
            Pc pcnoInventario = componente.getPcnoInventario();
            if (pcnoInventario != null) {
                pcnoInventario.getComponenteList().remove(componente);
                pcnoInventario = em.merge(pcnoInventario);
            }
            TComponente TComponenteidComponente = componente.getTComponenteidComponente();
            if (TComponenteidComponente != null) {
                TComponenteidComponente.getComponenteList().remove(componente);
                TComponenteidComponente = em.merge(TComponenteidComponente);
            }
            TConexion TConexionidConexion = componente.getTConexionidConexion();
            if (TConexionidConexion != null) {
                TConexionidConexion.getComponenteList().remove(componente);
                TConexionidConexion = em.merge(TConexionidConexion);
            }
            List<Almacen> almacenList = componente.getAlmacenList();
            for (Almacen almacenListAlmacen : almacenList) {
                almacenListAlmacen.setComponentesnComponente(null);
                almacenListAlmacen = em.merge(almacenListAlmacen);
            }
            em.remove(componente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Componente> findComponenteEntities() {
        return findComponenteEntities(true, -1, -1);
    }

    public List<Componente> findComponenteEntities(int maxResults, int firstResult) {
        return findComponenteEntities(false, maxResults, firstResult);
    }

    private List<Componente> findComponenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Componente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Componente findComponente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Componente.class, id);
        } finally {
            em.close();
        }
    }

    public int getComponenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Componente> rt = cq.from(Componente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
