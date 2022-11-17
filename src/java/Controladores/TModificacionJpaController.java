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
import Entidades.Modificacion;
import Entidades.TModificacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TModificacionJpaController implements Serializable {

    public TModificacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TModificacion TModificacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modificacion modificacion = TModificacion.getModificacion();
            if (modificacion != null) {
                modificacion = em.getReference(modificacion.getClass(), modificacion.getTModificacionidTmodificacion());
                TModificacion.setModificacion(modificacion);
            }
            em.persist(TModificacion);
            if (modificacion != null) {
                TModificacion oldTModificacionOfModificacion = modificacion.getTModificacion();
                if (oldTModificacionOfModificacion != null) {
                    oldTModificacionOfModificacion.setModificacion(null);
                    oldTModificacionOfModificacion = em.merge(oldTModificacionOfModificacion);
                }
                modificacion.setTModificacion(TModificacion);
                modificacion = em.merge(modificacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTModificacion(TModificacion.getIdTmodificacion()) != null) {
                throw new PreexistingEntityException("TModificacion " + TModificacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TModificacion TModificacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TModificacion persistentTModificacion = em.find(TModificacion.class, TModificacion.getIdTmodificacion());
            Modificacion modificacionOld = persistentTModificacion.getModificacion();
            Modificacion modificacionNew = TModificacion.getModificacion();
            List<String> illegalOrphanMessages = null;
            if (modificacionOld != null && !modificacionOld.equals(modificacionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Modificacion " + modificacionOld + " since its TModificacion field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (modificacionNew != null) {
                modificacionNew = em.getReference(modificacionNew.getClass(), modificacionNew.getTModificacionidTmodificacion());
                TModificacion.setModificacion(modificacionNew);
            }
            TModificacion = em.merge(TModificacion);
            if (modificacionNew != null && !modificacionNew.equals(modificacionOld)) {
                TModificacion oldTModificacionOfModificacion = modificacionNew.getTModificacion();
                if (oldTModificacionOfModificacion != null) {
                    oldTModificacionOfModificacion.setModificacion(null);
                    oldTModificacionOfModificacion = em.merge(oldTModificacionOfModificacion);
                }
                modificacionNew.setTModificacion(TModificacion);
                modificacionNew = em.merge(modificacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = TModificacion.getIdTmodificacion();
                if (findTModificacion(id) == null) {
                    throw new NonexistentEntityException("The tModificacion with id " + id + " no longer exists.");
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
            TModificacion TModificacion;
            try {
                TModificacion = em.getReference(TModificacion.class, id);
                TModificacion.getIdTmodificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The TModificacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Modificacion modificacionOrphanCheck = TModificacion.getModificacion();
            if (modificacionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TModificacion (" + TModificacion + ") cannot be destroyed since the Modificacion " + modificacionOrphanCheck + " in its modificacion field has a non-nullable TModificacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(TModificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TModificacion> findTModificacionEntities() {
        return findTModificacionEntities(true, -1, -1);
    }

    public List<TModificacion> findTModificacionEntities(int maxResults, int firstResult) {
        return findTModificacionEntities(false, maxResults, firstResult);
    }

    private List<TModificacion> findTModificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TModificacion.class));
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

    public TModificacion findTModificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TModificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTModificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TModificacion> rt = cq.from(TModificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
