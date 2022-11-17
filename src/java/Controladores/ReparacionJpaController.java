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
import Entidades.Estado;
import Entidades.Reparacion;
import Entidades.TReparacion;
import Entidades.Taller;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class ReparacionJpaController implements Serializable {

    public ReparacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reparacion reparacion) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        TReparacion TReparacionOrphanCheck = reparacion.getTReparacion();
        if (TReparacionOrphanCheck != null) {
            Reparacion oldReparacionOfTReparacion = TReparacionOrphanCheck.getReparacion();
            if (oldReparacionOfTReparacion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TReparacion " + TReparacionOrphanCheck + " already has an item of type Reparacion whose TReparacion column cannot be null. Please make another selection for the TReparacion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado estadoidEstado = reparacion.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado = em.getReference(estadoidEstado.getClass(), estadoidEstado.getIdEstado());
                reparacion.setEstadoidEstado(estadoidEstado);
            }
            TReparacion TReparacion = reparacion.getTReparacion();
            if (TReparacion != null) {
                TReparacion = em.getReference(TReparacion.getClass(), TReparacion.getIdReparacio());
                reparacion.setTReparacion(TReparacion);
            }
            Taller tallernroContrato = reparacion.getTallernroContrato();
            if (tallernroContrato != null) {
                tallernroContrato = em.getReference(tallernroContrato.getClass(), tallernroContrato.getNroContrato());
                reparacion.setTallernroContrato(tallernroContrato);
            }
            em.persist(reparacion);
            if (estadoidEstado != null) {
                estadoidEstado.getReparacionList().add(reparacion);
                estadoidEstado = em.merge(estadoidEstado);
            }
            if (TReparacion != null) {
                TReparacion.setReparacion(reparacion);
                TReparacion = em.merge(TReparacion);
            }
            if (tallernroContrato != null) {
                tallernroContrato.getReparacionList().add(reparacion);
                tallernroContrato = em.merge(tallernroContrato);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReparacion(reparacion.getTReparacionidReparacio()) != null) {
                throw new PreexistingEntityException("Reparacion " + reparacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reparacion reparacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reparacion persistentReparacion = em.find(Reparacion.class, reparacion.getTReparacionidReparacio());
            Estado estadoidEstadoOld = persistentReparacion.getEstadoidEstado();
            Estado estadoidEstadoNew = reparacion.getEstadoidEstado();
            TReparacion TReparacionOld = persistentReparacion.getTReparacion();
            TReparacion TReparacionNew = reparacion.getTReparacion();
            Taller tallernroContratoOld = persistentReparacion.getTallernroContrato();
            Taller tallernroContratoNew = reparacion.getTallernroContrato();
            List<String> illegalOrphanMessages = null;
            if (TReparacionNew != null && !TReparacionNew.equals(TReparacionOld)) {
                Reparacion oldReparacionOfTReparacion = TReparacionNew.getReparacion();
                if (oldReparacionOfTReparacion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TReparacion " + TReparacionNew + " already has an item of type Reparacion whose TReparacion column cannot be null. Please make another selection for the TReparacion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estadoidEstadoNew != null) {
                estadoidEstadoNew = em.getReference(estadoidEstadoNew.getClass(), estadoidEstadoNew.getIdEstado());
                reparacion.setEstadoidEstado(estadoidEstadoNew);
            }
            if (TReparacionNew != null) {
                TReparacionNew = em.getReference(TReparacionNew.getClass(), TReparacionNew.getIdReparacio());
                reparacion.setTReparacion(TReparacionNew);
            }
            if (tallernroContratoNew != null) {
                tallernroContratoNew = em.getReference(tallernroContratoNew.getClass(), tallernroContratoNew.getNroContrato());
                reparacion.setTallernroContrato(tallernroContratoNew);
            }
            reparacion = em.merge(reparacion);
            if (estadoidEstadoOld != null && !estadoidEstadoOld.equals(estadoidEstadoNew)) {
                estadoidEstadoOld.getReparacionList().remove(reparacion);
                estadoidEstadoOld = em.merge(estadoidEstadoOld);
            }
            if (estadoidEstadoNew != null && !estadoidEstadoNew.equals(estadoidEstadoOld)) {
                estadoidEstadoNew.getReparacionList().add(reparacion);
                estadoidEstadoNew = em.merge(estadoidEstadoNew);
            }
            if (TReparacionOld != null && !TReparacionOld.equals(TReparacionNew)) {
                TReparacionOld.setReparacion(null);
                TReparacionOld = em.merge(TReparacionOld);
            }
            if (TReparacionNew != null && !TReparacionNew.equals(TReparacionOld)) {
                TReparacionNew.setReparacion(reparacion);
                TReparacionNew = em.merge(TReparacionNew);
            }
            if (tallernroContratoOld != null && !tallernroContratoOld.equals(tallernroContratoNew)) {
                tallernroContratoOld.getReparacionList().remove(reparacion);
                tallernroContratoOld = em.merge(tallernroContratoOld);
            }
            if (tallernroContratoNew != null && !tallernroContratoNew.equals(tallernroContratoOld)) {
                tallernroContratoNew.getReparacionList().add(reparacion);
                tallernroContratoNew = em.merge(tallernroContratoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reparacion.getTReparacionidReparacio();
                if (findReparacion(id) == null) {
                    throw new NonexistentEntityException("The reparacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reparacion reparacion;
            try {
                reparacion = em.getReference(Reparacion.class, id);
                reparacion.getTReparacionidReparacio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reparacion with id " + id + " no longer exists.", enfe);
            }
            Estado estadoidEstado = reparacion.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado.getReparacionList().remove(reparacion);
                estadoidEstado = em.merge(estadoidEstado);
            }
            TReparacion TReparacion = reparacion.getTReparacion();
            if (TReparacion != null) {
                TReparacion.setReparacion(null);
                TReparacion = em.merge(TReparacion);
            }
            Taller tallernroContrato = reparacion.getTallernroContrato();
            if (tallernroContrato != null) {
                tallernroContrato.getReparacionList().remove(reparacion);
                tallernroContrato = em.merge(tallernroContrato);
            }
            em.remove(reparacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reparacion> findReparacionEntities() {
        return findReparacionEntities(true, -1, -1);
    }

    public List<Reparacion> findReparacionEntities(int maxResults, int firstResult) {
        return findReparacionEntities(false, maxResults, firstResult);
    }

    private List<Reparacion> findReparacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reparacion.class));
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

    public Reparacion findReparacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reparacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getReparacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reparacion> rt = cq.from(Reparacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
