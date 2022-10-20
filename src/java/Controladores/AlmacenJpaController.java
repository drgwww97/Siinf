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
import Entidades.Accesorio;
import Entidades.Almacen;
import Entidades.Componente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dayana
 */
public class AlmacenJpaController implements Serializable {

    public AlmacenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Almacen almacen) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accesorio accesoriosnAccesorio = almacen.getAccesoriosnAccesorio();
            if (accesoriosnAccesorio != null) {
                accesoriosnAccesorio = em.getReference(accesoriosnAccesorio.getClass(), accesoriosnAccesorio.getSnAccesorio());
                almacen.setAccesoriosnAccesorio(accesoriosnAccesorio);
            }
            Componente componentesnComponente = almacen.getComponentesnComponente();
            if (componentesnComponente != null) {
                componentesnComponente = em.getReference(componentesnComponente.getClass(), componentesnComponente.getSnComponente());
                almacen.setComponentesnComponente(componentesnComponente);
            }
            em.persist(almacen);
            if (accesoriosnAccesorio != null) {
                accesoriosnAccesorio.getAlmacenList().add(almacen);
                accesoriosnAccesorio = em.merge(accesoriosnAccesorio);
            }
            if (componentesnComponente != null) {
                componentesnComponente.getAlmacenList().add(almacen);
                componentesnComponente = em.merge(componentesnComponente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlmacen(almacen.getIdAlmacen()) != null) {
                throw new PreexistingEntityException("Almacen " + almacen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Almacen almacen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Almacen persistentAlmacen = em.find(Almacen.class, almacen.getIdAlmacen());
            Accesorio accesoriosnAccesorioOld = persistentAlmacen.getAccesoriosnAccesorio();
            Accesorio accesoriosnAccesorioNew = almacen.getAccesoriosnAccesorio();
            Componente componentesnComponenteOld = persistentAlmacen.getComponentesnComponente();
            Componente componentesnComponenteNew = almacen.getComponentesnComponente();
            if (accesoriosnAccesorioNew != null) {
                accesoriosnAccesorioNew = em.getReference(accesoriosnAccesorioNew.getClass(), accesoriosnAccesorioNew.getSnAccesorio());
                almacen.setAccesoriosnAccesorio(accesoriosnAccesorioNew);
            }
            if (componentesnComponenteNew != null) {
                componentesnComponenteNew = em.getReference(componentesnComponenteNew.getClass(), componentesnComponenteNew.getSnComponente());
                almacen.setComponentesnComponente(componentesnComponenteNew);
            }
            almacen = em.merge(almacen);
            if (accesoriosnAccesorioOld != null && !accesoriosnAccesorioOld.equals(accesoriosnAccesorioNew)) {
                accesoriosnAccesorioOld.getAlmacenList().remove(almacen);
                accesoriosnAccesorioOld = em.merge(accesoriosnAccesorioOld);
            }
            if (accesoriosnAccesorioNew != null && !accesoriosnAccesorioNew.equals(accesoriosnAccesorioOld)) {
                accesoriosnAccesorioNew.getAlmacenList().add(almacen);
                accesoriosnAccesorioNew = em.merge(accesoriosnAccesorioNew);
            }
            if (componentesnComponenteOld != null && !componentesnComponenteOld.equals(componentesnComponenteNew)) {
                componentesnComponenteOld.getAlmacenList().remove(almacen);
                componentesnComponenteOld = em.merge(componentesnComponenteOld);
            }
            if (componentesnComponenteNew != null && !componentesnComponenteNew.equals(componentesnComponenteOld)) {
                componentesnComponenteNew.getAlmacenList().add(almacen);
                componentesnComponenteNew = em.merge(componentesnComponenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = almacen.getIdAlmacen();
                if (findAlmacen(id) == null) {
                    throw new NonexistentEntityException("The almacen with id " + id + " no longer exists.");
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
            Almacen almacen;
            try {
                almacen = em.getReference(Almacen.class, id);
                almacen.getIdAlmacen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The almacen with id " + id + " no longer exists.", enfe);
            }
            Accesorio accesoriosnAccesorio = almacen.getAccesoriosnAccesorio();
            if (accesoriosnAccesorio != null) {
                accesoriosnAccesorio.getAlmacenList().remove(almacen);
                accesoriosnAccesorio = em.merge(accesoriosnAccesorio);
            }
            Componente componentesnComponente = almacen.getComponentesnComponente();
            if (componentesnComponente != null) {
                componentesnComponente.getAlmacenList().remove(almacen);
                componentesnComponente = em.merge(componentesnComponente);
            }
            em.remove(almacen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Almacen> findAlmacenEntities() {
        return findAlmacenEntities(true, -1, -1);
    }

    public List<Almacen> findAlmacenEntities(int maxResults, int firstResult) {
        return findAlmacenEntities(false, maxResults, firstResult);
    }

    private List<Almacen> findAlmacenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Almacen.class));
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

    public Almacen findAlmacen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Almacen.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlmacenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Almacen> rt = cq.from(Almacen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
