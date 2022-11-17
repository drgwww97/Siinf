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
import Entidades.Pc;
import Entidades.SO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class SOJpaController implements Serializable {

    public SOJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SO SO) throws PreexistingEntityException, Exception {
        if (SO.getPcList() == null) {
            SO.setPcList(new ArrayList<Pc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : SO.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            SO.setPcList(attachedPcList);
            em.persist(SO);
            for (Pc pcListPc : SO.getPcList()) {
                SO oldSOidSoOfPcListPc = pcListPc.getSOidSo();
                pcListPc.setSOidSo(SO);
                pcListPc = em.merge(pcListPc);
                if (oldSOidSoOfPcListPc != null) {
                    oldSOidSoOfPcListPc.getPcList().remove(pcListPc);
                    oldSOidSoOfPcListPc = em.merge(oldSOidSoOfPcListPc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSO(SO.getIdSo()) != null) {
                throw new PreexistingEntityException("SO " + SO + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SO SO) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SO persistentSO = em.find(SO.class, SO.getIdSo());
            List<Pc> pcListOld = persistentSO.getPcList();
            List<Pc> pcListNew = SO.getPcList();
            List<String> illegalOrphanMessages = null;
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pc " + pcListOldPc + " since its SOidSo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            SO.setPcList(pcListNew);
            SO = em.merge(SO);
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    SO oldSOidSoOfPcListNewPc = pcListNewPc.getSOidSo();
                    pcListNewPc.setSOidSo(SO);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldSOidSoOfPcListNewPc != null && !oldSOidSoOfPcListNewPc.equals(SO)) {
                        oldSOidSoOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldSOidSoOfPcListNewPc = em.merge(oldSOidSoOfPcListNewPc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = SO.getIdSo();
                if (findSO(id) == null) {
                    throw new NonexistentEntityException("The sO with id " + id + " no longer exists.");
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
            SO SO;
            try {
                SO = em.getReference(SO.class, id);
                SO.getIdSo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SO with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pc> pcListOrphanCheck = SO.getPcList();
            for (Pc pcListOrphanCheckPc : pcListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SO (" + SO + ") cannot be destroyed since the Pc " + pcListOrphanCheckPc + " in its pcList field has a non-nullable SOidSo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(SO);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SO> findSOEntities() {
        return findSOEntities(true, -1, -1);
    }

    public List<SO> findSOEntities(int maxResults, int firstResult) {
        return findSOEntities(false, maxResults, firstResult);
    }

    private List<SO> findSOEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SO.class));
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

    public SO findSO(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SO.class, id);
        } finally {
            em.close();
        }
    }

    public int getSOCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SO> rt = cq.from(SO.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
