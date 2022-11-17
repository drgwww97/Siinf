/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Impresora;
import Entidades.RegDatosToner;
import Entidades.TTonner;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class RegDatosTonerJpaController implements Serializable {

    public RegDatosTonerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegDatosToner regDatosToner) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Impresora impresoranoInventario = regDatosToner.getImpresoranoInventario();
            if (impresoranoInventario != null) {
                impresoranoInventario = em.getReference(impresoranoInventario.getClass(), impresoranoInventario.getNoInventario());
                regDatosToner.setImpresoranoInventario(impresoranoInventario);
            }
            TTonner TTonnersnTonner = regDatosToner.getTTonnersnTonner();
            if (TTonnersnTonner != null) {
                TTonnersnTonner = em.getReference(TTonnersnTonner.getClass(), TTonnersnTonner.getSnTonner());
                regDatosToner.setTTonnersnTonner(TTonnersnTonner);
            }
            em.persist(regDatosToner);
            if (impresoranoInventario != null) {
                impresoranoInventario.getRegDatosTonerList().add(regDatosToner);
                impresoranoInventario = em.merge(impresoranoInventario);
            }
            if (TTonnersnTonner != null) {
                TTonnersnTonner.getRegDatosTonerList().add(regDatosToner);
                TTonnersnTonner = em.merge(TTonnersnTonner);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegDatosToner regDatosToner) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegDatosToner persistentRegDatosToner = em.find(RegDatosToner.class, regDatosToner.getIdRegToner());
            Impresora impresoranoInventarioOld = persistentRegDatosToner.getImpresoranoInventario();
            Impresora impresoranoInventarioNew = regDatosToner.getImpresoranoInventario();
            TTonner TTonnersnTonnerOld = persistentRegDatosToner.getTTonnersnTonner();
            TTonner TTonnersnTonnerNew = regDatosToner.getTTonnersnTonner();
            if (impresoranoInventarioNew != null) {
                impresoranoInventarioNew = em.getReference(impresoranoInventarioNew.getClass(), impresoranoInventarioNew.getNoInventario());
                regDatosToner.setImpresoranoInventario(impresoranoInventarioNew);
            }
            if (TTonnersnTonnerNew != null) {
                TTonnersnTonnerNew = em.getReference(TTonnersnTonnerNew.getClass(), TTonnersnTonnerNew.getSnTonner());
                regDatosToner.setTTonnersnTonner(TTonnersnTonnerNew);
            }
            regDatosToner = em.merge(regDatosToner);
            if (impresoranoInventarioOld != null && !impresoranoInventarioOld.equals(impresoranoInventarioNew)) {
                impresoranoInventarioOld.getRegDatosTonerList().remove(regDatosToner);
                impresoranoInventarioOld = em.merge(impresoranoInventarioOld);
            }
            if (impresoranoInventarioNew != null && !impresoranoInventarioNew.equals(impresoranoInventarioOld)) {
                impresoranoInventarioNew.getRegDatosTonerList().add(regDatosToner);
                impresoranoInventarioNew = em.merge(impresoranoInventarioNew);
            }
            if (TTonnersnTonnerOld != null && !TTonnersnTonnerOld.equals(TTonnersnTonnerNew)) {
                TTonnersnTonnerOld.getRegDatosTonerList().remove(regDatosToner);
                TTonnersnTonnerOld = em.merge(TTonnersnTonnerOld);
            }
            if (TTonnersnTonnerNew != null && !TTonnersnTonnerNew.equals(TTonnersnTonnerOld)) {
                TTonnersnTonnerNew.getRegDatosTonerList().add(regDatosToner);
                TTonnersnTonnerNew = em.merge(TTonnersnTonnerNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = regDatosToner.getIdRegToner();
                if (findRegDatosToner(id) == null) {
                    throw new NonexistentEntityException("The regDatosToner with id " + id + " no longer exists.");
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
            RegDatosToner regDatosToner;
            try {
                regDatosToner = em.getReference(RegDatosToner.class, id);
                regDatosToner.getIdRegToner();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regDatosToner with id " + id + " no longer exists.", enfe);
            }
            Impresora impresoranoInventario = regDatosToner.getImpresoranoInventario();
            if (impresoranoInventario != null) {
                impresoranoInventario.getRegDatosTonerList().remove(regDatosToner);
                impresoranoInventario = em.merge(impresoranoInventario);
            }
            TTonner TTonnersnTonner = regDatosToner.getTTonnersnTonner();
            if (TTonnersnTonner != null) {
                TTonnersnTonner.getRegDatosTonerList().remove(regDatosToner);
                TTonnersnTonner = em.merge(TTonnersnTonner);
            }
            em.remove(regDatosToner);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegDatosToner> findRegDatosTonerEntities() {
        return findRegDatosTonerEntities(true, -1, -1);
    }

    public List<RegDatosToner> findRegDatosTonerEntities(int maxResults, int firstResult) {
        return findRegDatosTonerEntities(false, maxResults, firstResult);
    }

    private List<RegDatosToner> findRegDatosTonerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegDatosToner.class));
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

    public RegDatosToner findRegDatosToner(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegDatosToner.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegDatosTonerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegDatosToner> rt = cq.from(RegDatosToner.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
