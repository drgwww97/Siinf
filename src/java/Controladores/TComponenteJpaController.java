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
import Entidades.Componente;
import Entidades.TComponente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TComponenteJpaController implements Serializable {

    public TComponenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TComponente TComponente) throws PreexistingEntityException, Exception {
        if (TComponente.getComponenteList() == null) {
            TComponente.setComponenteList(new ArrayList<Componente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Componente> attachedComponenteList = new ArrayList<Componente>();
            for (Componente componenteListComponenteToAttach : TComponente.getComponenteList()) {
                componenteListComponenteToAttach = em.getReference(componenteListComponenteToAttach.getClass(), componenteListComponenteToAttach.getSnComponente());
                attachedComponenteList.add(componenteListComponenteToAttach);
            }
            TComponente.setComponenteList(attachedComponenteList);
            em.persist(TComponente);
            for (Componente componenteListComponente : TComponente.getComponenteList()) {
                TComponente oldTComponenteidComponenteOfComponenteListComponente = componenteListComponente.getTComponenteidComponente();
                componenteListComponente.setTComponenteidComponente(TComponente);
                componenteListComponente = em.merge(componenteListComponente);
                if (oldTComponenteidComponenteOfComponenteListComponente != null) {
                    oldTComponenteidComponenteOfComponenteListComponente.getComponenteList().remove(componenteListComponente);
                    oldTComponenteidComponenteOfComponenteListComponente = em.merge(oldTComponenteidComponenteOfComponenteListComponente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTComponente(TComponente.getIdComponente()) != null) {
                throw new PreexistingEntityException("TComponente " + TComponente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TComponente TComponente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TComponente persistentTComponente = em.find(TComponente.class, TComponente.getIdComponente());
            List<Componente> componenteListOld = persistentTComponente.getComponenteList();
            List<Componente> componenteListNew = TComponente.getComponenteList();
            List<String> illegalOrphanMessages = null;
            for (Componente componenteListOldComponente : componenteListOld) {
                if (!componenteListNew.contains(componenteListOldComponente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Componente " + componenteListOldComponente + " since its TComponenteidComponente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Componente> attachedComponenteListNew = new ArrayList<Componente>();
            for (Componente componenteListNewComponenteToAttach : componenteListNew) {
                componenteListNewComponenteToAttach = em.getReference(componenteListNewComponenteToAttach.getClass(), componenteListNewComponenteToAttach.getSnComponente());
                attachedComponenteListNew.add(componenteListNewComponenteToAttach);
            }
            componenteListNew = attachedComponenteListNew;
            TComponente.setComponenteList(componenteListNew);
            TComponente = em.merge(TComponente);
            for (Componente componenteListNewComponente : componenteListNew) {
                if (!componenteListOld.contains(componenteListNewComponente)) {
                    TComponente oldTComponenteidComponenteOfComponenteListNewComponente = componenteListNewComponente.getTComponenteidComponente();
                    componenteListNewComponente.setTComponenteidComponente(TComponente);
                    componenteListNewComponente = em.merge(componenteListNewComponente);
                    if (oldTComponenteidComponenteOfComponenteListNewComponente != null && !oldTComponenteidComponenteOfComponenteListNewComponente.equals(TComponente)) {
                        oldTComponenteidComponenteOfComponenteListNewComponente.getComponenteList().remove(componenteListNewComponente);
                        oldTComponenteidComponenteOfComponenteListNewComponente = em.merge(oldTComponenteidComponenteOfComponenteListNewComponente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = TComponente.getIdComponente();
                if (findTComponente(id) == null) {
                    throw new NonexistentEntityException("The tComponente with id " + id + " no longer exists.");
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
            TComponente TComponente;
            try {
                TComponente = em.getReference(TComponente.class, id);
                TComponente.getIdComponente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The TComponente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Componente> componenteListOrphanCheck = TComponente.getComponenteList();
            for (Componente componenteListOrphanCheckComponente : componenteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TComponente (" + TComponente + ") cannot be destroyed since the Componente " + componenteListOrphanCheckComponente + " in its componenteList field has a non-nullable TComponenteidComponente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(TComponente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TComponente> findTComponenteEntities() {
        return findTComponenteEntities(true, -1, -1);
    }

    public List<TComponente> findTComponenteEntities(int maxResults, int firstResult) {
        return findTComponenteEntities(false, maxResults, firstResult);
    }

    private List<TComponente> findTComponenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TComponente.class));
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

    public TComponente findTComponente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TComponente.class, id);
        } finally {
            em.close();
        }
    }

    public int getTComponenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TComponente> rt = cq.from(TComponente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
