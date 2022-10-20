/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Impresora;
import Entidades.TTonner;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dayana
 */
public class TTonnerJpaController implements Serializable {

    public TTonnerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TTonner TTonner) throws PreexistingEntityException, Exception {
        if (TTonner.getImpresoraList() == null) {
            TTonner.setImpresoraList(new ArrayList<Impresora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Impresora> attachedImpresoraList = new ArrayList<Impresora>();
            for (Impresora impresoraListImpresoraToAttach : TTonner.getImpresoraList()) {
                impresoraListImpresoraToAttach = em.getReference(impresoraListImpresoraToAttach.getClass(), impresoraListImpresoraToAttach.getNoInventario());
                attachedImpresoraList.add(impresoraListImpresoraToAttach);
            }
            TTonner.setImpresoraList(attachedImpresoraList);
            em.persist(TTonner);
            for (Impresora impresoraListImpresora : TTonner.getImpresoraList()) {
                TTonner oldTTonnersnTonnerOfImpresoraListImpresora = impresoraListImpresora.getTTonnersnTonner();
                impresoraListImpresora.setTTonnersnTonner(TTonner);
                impresoraListImpresora = em.merge(impresoraListImpresora);
                if (oldTTonnersnTonnerOfImpresoraListImpresora != null) {
                    oldTTonnersnTonnerOfImpresoraListImpresora.getImpresoraList().remove(impresoraListImpresora);
                    oldTTonnersnTonnerOfImpresoraListImpresora = em.merge(oldTTonnersnTonnerOfImpresoraListImpresora);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTTonner(TTonner.getSnTonner()) != null) {
                throw new PreexistingEntityException("TTonner " + TTonner + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TTonner TTonner) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TTonner persistentTTonner = em.find(TTonner.class, TTonner.getSnTonner());
            List<Impresora> impresoraListOld = persistentTTonner.getImpresoraList();
            List<Impresora> impresoraListNew = TTonner.getImpresoraList();
            List<Impresora> attachedImpresoraListNew = new ArrayList<Impresora>();
            for (Impresora impresoraListNewImpresoraToAttach : impresoraListNew) {
                impresoraListNewImpresoraToAttach = em.getReference(impresoraListNewImpresoraToAttach.getClass(), impresoraListNewImpresoraToAttach.getNoInventario());
                attachedImpresoraListNew.add(impresoraListNewImpresoraToAttach);
            }
            impresoraListNew = attachedImpresoraListNew;
            TTonner.setImpresoraList(impresoraListNew);
            TTonner = em.merge(TTonner);
            for (Impresora impresoraListOldImpresora : impresoraListOld) {
                if (!impresoraListNew.contains(impresoraListOldImpresora)) {
                    impresoraListOldImpresora.setTTonnersnTonner(null);
                    impresoraListOldImpresora = em.merge(impresoraListOldImpresora);
                }
            }
            for (Impresora impresoraListNewImpresora : impresoraListNew) {
                if (!impresoraListOld.contains(impresoraListNewImpresora)) {
                    TTonner oldTTonnersnTonnerOfImpresoraListNewImpresora = impresoraListNewImpresora.getTTonnersnTonner();
                    impresoraListNewImpresora.setTTonnersnTonner(TTonner);
                    impresoraListNewImpresora = em.merge(impresoraListNewImpresora);
                    if (oldTTonnersnTonnerOfImpresoraListNewImpresora != null && !oldTTonnersnTonnerOfImpresoraListNewImpresora.equals(TTonner)) {
                        oldTTonnersnTonnerOfImpresoraListNewImpresora.getImpresoraList().remove(impresoraListNewImpresora);
                        oldTTonnersnTonnerOfImpresoraListNewImpresora = em.merge(oldTTonnersnTonnerOfImpresoraListNewImpresora);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = TTonner.getSnTonner();
                if (findTTonner(id) == null) {
                    throw new NonexistentEntityException("The tTonner with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TTonner TTonner;
            try {
                TTonner = em.getReference(TTonner.class, id);
                TTonner.getSnTonner();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The TTonner with id " + id + " no longer exists.", enfe);
            }
            List<Impresora> impresoraList = TTonner.getImpresoraList();
            for (Impresora impresoraListImpresora : impresoraList) {
                impresoraListImpresora.setTTonnersnTonner(null);
                impresoraListImpresora = em.merge(impresoraListImpresora);
            }
            em.remove(TTonner);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TTonner> findTTonnerEntities() {
        return findTTonnerEntities(true, -1, -1);
    }

    public List<TTonner> findTTonnerEntities(int maxResults, int firstResult) {
        return findTTonnerEntities(false, maxResults, firstResult);
    }

    private List<TTonner> findTTonnerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TTonner.class));
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

    public TTonner findTTonner(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TTonner.class, id);
        } finally {
            em.close();
        }
    }

    public int getTTonnerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TTonner> rt = cq.from(TTonner.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
