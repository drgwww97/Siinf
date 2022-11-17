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
import Entidades.Reparacion;
import Entidades.Taller;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TallerJpaController implements Serializable {

    public TallerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Taller taller) throws PreexistingEntityException, Exception {
        if (taller.getReparacionList() == null) {
            taller.setReparacionList(new ArrayList<Reparacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reparacion> attachedReparacionList = new ArrayList<Reparacion>();
            for (Reparacion reparacionListReparacionToAttach : taller.getReparacionList()) {
                reparacionListReparacionToAttach = em.getReference(reparacionListReparacionToAttach.getClass(), reparacionListReparacionToAttach.getTReparacionidReparacio());
                attachedReparacionList.add(reparacionListReparacionToAttach);
            }
            taller.setReparacionList(attachedReparacionList);
            em.persist(taller);
            for (Reparacion reparacionListReparacion : taller.getReparacionList()) {
                Taller oldTallernroContratoOfReparacionListReparacion = reparacionListReparacion.getTallernroContrato();
                reparacionListReparacion.setTallernroContrato(taller);
                reparacionListReparacion = em.merge(reparacionListReparacion);
                if (oldTallernroContratoOfReparacionListReparacion != null) {
                    oldTallernroContratoOfReparacionListReparacion.getReparacionList().remove(reparacionListReparacion);
                    oldTallernroContratoOfReparacionListReparacion = em.merge(oldTallernroContratoOfReparacionListReparacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTaller(taller.getNroContrato()) != null) {
                throw new PreexistingEntityException("Taller " + taller + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Taller taller) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Taller persistentTaller = em.find(Taller.class, taller.getNroContrato());
            List<Reparacion> reparacionListOld = persistentTaller.getReparacionList();
            List<Reparacion> reparacionListNew = taller.getReparacionList();
            List<String> illegalOrphanMessages = null;
            for (Reparacion reparacionListOldReparacion : reparacionListOld) {
                if (!reparacionListNew.contains(reparacionListOldReparacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reparacion " + reparacionListOldReparacion + " since its tallernroContrato field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Reparacion> attachedReparacionListNew = new ArrayList<Reparacion>();
            for (Reparacion reparacionListNewReparacionToAttach : reparacionListNew) {
                reparacionListNewReparacionToAttach = em.getReference(reparacionListNewReparacionToAttach.getClass(), reparacionListNewReparacionToAttach.getTReparacionidReparacio());
                attachedReparacionListNew.add(reparacionListNewReparacionToAttach);
            }
            reparacionListNew = attachedReparacionListNew;
            taller.setReparacionList(reparacionListNew);
            taller = em.merge(taller);
            for (Reparacion reparacionListNewReparacion : reparacionListNew) {
                if (!reparacionListOld.contains(reparacionListNewReparacion)) {
                    Taller oldTallernroContratoOfReparacionListNewReparacion = reparacionListNewReparacion.getTallernroContrato();
                    reparacionListNewReparacion.setTallernroContrato(taller);
                    reparacionListNewReparacion = em.merge(reparacionListNewReparacion);
                    if (oldTallernroContratoOfReparacionListNewReparacion != null && !oldTallernroContratoOfReparacionListNewReparacion.equals(taller)) {
                        oldTallernroContratoOfReparacionListNewReparacion.getReparacionList().remove(reparacionListNewReparacion);
                        oldTallernroContratoOfReparacionListNewReparacion = em.merge(oldTallernroContratoOfReparacionListNewReparacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = taller.getNroContrato();
                if (findTaller(id) == null) {
                    throw new NonexistentEntityException("The taller with id " + id + " no longer exists.");
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
            Taller taller;
            try {
                taller = em.getReference(Taller.class, id);
                taller.getNroContrato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The taller with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Reparacion> reparacionListOrphanCheck = taller.getReparacionList();
            for (Reparacion reparacionListOrphanCheckReparacion : reparacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Taller (" + taller + ") cannot be destroyed since the Reparacion " + reparacionListOrphanCheckReparacion + " in its reparacionList field has a non-nullable tallernroContrato field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(taller);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Taller> findTallerEntities() {
        return findTallerEntities(true, -1, -1);
    }

    public List<Taller> findTallerEntities(int maxResults, int firstResult) {
        return findTallerEntities(false, maxResults, firstResult);
    }

    private List<Taller> findTallerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Taller.class));
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

    public Taller findTaller(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Taller.class, id);
        } finally {
            em.close();
        }
    }

    public int getTallerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Taller> rt = cq.from(Taller.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
