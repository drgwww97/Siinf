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
import Entidades.Accesorio;
import Entidades.TAccesorio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dayana
 */
public class TAccesorioJpaController implements Serializable {

    public TAccesorioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TAccesorio TAccesorio) throws PreexistingEntityException, Exception {
        if (TAccesorio.getAccesorioList() == null) {
            TAccesorio.setAccesorioList(new ArrayList<Accesorio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Accesorio> attachedAccesorioList = new ArrayList<Accesorio>();
            for (Accesorio accesorioListAccesorioToAttach : TAccesorio.getAccesorioList()) {
                accesorioListAccesorioToAttach = em.getReference(accesorioListAccesorioToAttach.getClass(), accesorioListAccesorioToAttach.getSnAccesorio());
                attachedAccesorioList.add(accesorioListAccesorioToAttach);
            }
            TAccesorio.setAccesorioList(attachedAccesorioList);
            em.persist(TAccesorio);
            for (Accesorio accesorioListAccesorio : TAccesorio.getAccesorioList()) {
                TAccesorio oldTAccesorioidAccesorioOfAccesorioListAccesorio = accesorioListAccesorio.getTAccesorioidAccesorio();
                accesorioListAccesorio.setTAccesorioidAccesorio(TAccesorio);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
                if (oldTAccesorioidAccesorioOfAccesorioListAccesorio != null) {
                    oldTAccesorioidAccesorioOfAccesorioListAccesorio.getAccesorioList().remove(accesorioListAccesorio);
                    oldTAccesorioidAccesorioOfAccesorioListAccesorio = em.merge(oldTAccesorioidAccesorioOfAccesorioListAccesorio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTAccesorio(TAccesorio.getIdAccesorio()) != null) {
                throw new PreexistingEntityException("TAccesorio " + TAccesorio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TAccesorio TAccesorio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TAccesorio persistentTAccesorio = em.find(TAccesorio.class, TAccesorio.getIdAccesorio());
            List<Accesorio> accesorioListOld = persistentTAccesorio.getAccesorioList();
            List<Accesorio> accesorioListNew = TAccesorio.getAccesorioList();
            List<String> illegalOrphanMessages = null;
            for (Accesorio accesorioListOldAccesorio : accesorioListOld) {
                if (!accesorioListNew.contains(accesorioListOldAccesorio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Accesorio " + accesorioListOldAccesorio + " since its TAccesorioidAccesorio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Accesorio> attachedAccesorioListNew = new ArrayList<Accesorio>();
            for (Accesorio accesorioListNewAccesorioToAttach : accesorioListNew) {
                accesorioListNewAccesorioToAttach = em.getReference(accesorioListNewAccesorioToAttach.getClass(), accesorioListNewAccesorioToAttach.getSnAccesorio());
                attachedAccesorioListNew.add(accesorioListNewAccesorioToAttach);
            }
            accesorioListNew = attachedAccesorioListNew;
            TAccesorio.setAccesorioList(accesorioListNew);
            TAccesorio = em.merge(TAccesorio);
            for (Accesorio accesorioListNewAccesorio : accesorioListNew) {
                if (!accesorioListOld.contains(accesorioListNewAccesorio)) {
                    TAccesorio oldTAccesorioidAccesorioOfAccesorioListNewAccesorio = accesorioListNewAccesorio.getTAccesorioidAccesorio();
                    accesorioListNewAccesorio.setTAccesorioidAccesorio(TAccesorio);
                    accesorioListNewAccesorio = em.merge(accesorioListNewAccesorio);
                    if (oldTAccesorioidAccesorioOfAccesorioListNewAccesorio != null && !oldTAccesorioidAccesorioOfAccesorioListNewAccesorio.equals(TAccesorio)) {
                        oldTAccesorioidAccesorioOfAccesorioListNewAccesorio.getAccesorioList().remove(accesorioListNewAccesorio);
                        oldTAccesorioidAccesorioOfAccesorioListNewAccesorio = em.merge(oldTAccesorioidAccesorioOfAccesorioListNewAccesorio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = TAccesorio.getIdAccesorio();
                if (findTAccesorio(id) == null) {
                    throw new NonexistentEntityException("The tAccesorio with id " + id + " no longer exists.");
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
            TAccesorio TAccesorio;
            try {
                TAccesorio = em.getReference(TAccesorio.class, id);
                TAccesorio.getIdAccesorio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The TAccesorio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Accesorio> accesorioListOrphanCheck = TAccesorio.getAccesorioList();
            for (Accesorio accesorioListOrphanCheckAccesorio : accesorioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TAccesorio (" + TAccesorio + ") cannot be destroyed since the Accesorio " + accesorioListOrphanCheckAccesorio + " in its accesorioList field has a non-nullable TAccesorioidAccesorio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(TAccesorio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TAccesorio> findTAccesorioEntities() {
        return findTAccesorioEntities(true, -1, -1);
    }

    public List<TAccesorio> findTAccesorioEntities(int maxResults, int firstResult) {
        return findTAccesorioEntities(false, maxResults, firstResult);
    }

    private List<TAccesorio> findTAccesorioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TAccesorio.class));
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

    public TAccesorio findTAccesorio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TAccesorio.class, id);
        } finally {
            em.close();
        }
    }

    public int getTAccesorioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TAccesorio> rt = cq.from(TAccesorio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
