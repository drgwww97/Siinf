/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Reparacion;
import Entidades.TReparacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TReparacionJpaController implements Serializable {

    public TReparacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TReparacion TReparacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reparacion reparacion = TReparacion.getReparacion();
            if (reparacion != null) {
                reparacion = em.getReference(reparacion.getClass(), reparacion.getTReparacionidReparacio());
                TReparacion.setReparacion(reparacion);
            }
            em.persist(TReparacion);
            if (reparacion != null) {
                TReparacion oldTReparacionOfReparacion = reparacion.getTReparacion();
                if (oldTReparacionOfReparacion != null) {
                    oldTReparacionOfReparacion.setReparacion(null);
                    oldTReparacionOfReparacion = em.merge(oldTReparacionOfReparacion);
                }
                reparacion.setTReparacion(TReparacion);
                reparacion = em.merge(reparacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TReparacion TReparacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TReparacion persistentTReparacion = em.find(TReparacion.class, TReparacion.getIdReparacio());
            Reparacion reparacionOld = persistentTReparacion.getReparacion();
            Reparacion reparacionNew = TReparacion.getReparacion();
            List<String> illegalOrphanMessages = null;
            if (reparacionOld != null && !reparacionOld.equals(reparacionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Reparacion " + reparacionOld + " since its TReparacion field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (reparacionNew != null) {
                reparacionNew = em.getReference(reparacionNew.getClass(), reparacionNew.getTReparacionidReparacio());
                TReparacion.setReparacion(reparacionNew);
            }
            TReparacion = em.merge(TReparacion);
            if (reparacionNew != null && !reparacionNew.equals(reparacionOld)) {
                TReparacion oldTReparacionOfReparacion = reparacionNew.getTReparacion();
                if (oldTReparacionOfReparacion != null) {
                    oldTReparacionOfReparacion.setReparacion(null);
                    oldTReparacionOfReparacion = em.merge(oldTReparacionOfReparacion);
                }
                reparacionNew.setTReparacion(TReparacion);
                reparacionNew = em.merge(reparacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = TReparacion.getIdReparacio();
                if (findTReparacion(id) == null) {
                    throw new NonexistentEntityException("The tReparacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TReparacion TReparacion;
            try {
                TReparacion = em.getReference(TReparacion.class, id);
                TReparacion.getIdReparacio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The TReparacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Reparacion reparacionOrphanCheck = TReparacion.getReparacion();
            if (reparacionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TReparacion (" + TReparacion + ") cannot be destroyed since the Reparacion " + reparacionOrphanCheck + " in its reparacion field has a non-nullable TReparacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(TReparacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TReparacion> findTReparacionEntities() {
        return findTReparacionEntities(true, -1, -1);
    }

    public List<TReparacion> findTReparacionEntities(int maxResults, int firstResult) {
        return findTReparacionEntities(false, maxResults, firstResult);
    }

    private List<TReparacion> findTReparacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TReparacion.class));
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

    public TReparacion findTReparacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TReparacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTReparacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TReparacion> rt = cq.from(TReparacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
