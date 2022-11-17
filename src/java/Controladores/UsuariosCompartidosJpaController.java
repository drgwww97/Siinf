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
import Entidades.UsuariosCompartidos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class UsuariosCompartidosJpaController implements Serializable {

    public UsuariosCompartidosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuariosCompartidos usuariosCompartidos) throws PreexistingEntityException, Exception {
        if (usuariosCompartidos.getImpresoraList() == null) {
            usuariosCompartidos.setImpresoraList(new ArrayList<Impresora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Impresora> attachedImpresoraList = new ArrayList<Impresora>();
            for (Impresora impresoraListImpresoraToAttach : usuariosCompartidos.getImpresoraList()) {
                impresoraListImpresoraToAttach = em.getReference(impresoraListImpresoraToAttach.getClass(), impresoraListImpresoraToAttach.getNoInventario());
                attachedImpresoraList.add(impresoraListImpresoraToAttach);
            }
            usuariosCompartidos.setImpresoraList(attachedImpresoraList);
            em.persist(usuariosCompartidos);
            for (Impresora impresoraListImpresora : usuariosCompartidos.getImpresoraList()) {
                impresoraListImpresora.getUsuariosCompartidosList().add(usuariosCompartidos);
                impresoraListImpresora = em.merge(impresoraListImpresora);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuariosCompartidos(usuariosCompartidos.getNombreUsuariocomp()) != null) {
                throw new PreexistingEntityException("UsuariosCompartidos " + usuariosCompartidos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuariosCompartidos usuariosCompartidos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuariosCompartidos persistentUsuariosCompartidos = em.find(UsuariosCompartidos.class, usuariosCompartidos.getNombreUsuariocomp());
            List<Impresora> impresoraListOld = persistentUsuariosCompartidos.getImpresoraList();
            List<Impresora> impresoraListNew = usuariosCompartidos.getImpresoraList();
            List<Impresora> attachedImpresoraListNew = new ArrayList<Impresora>();
            for (Impresora impresoraListNewImpresoraToAttach : impresoraListNew) {
                impresoraListNewImpresoraToAttach = em.getReference(impresoraListNewImpresoraToAttach.getClass(), impresoraListNewImpresoraToAttach.getNoInventario());
                attachedImpresoraListNew.add(impresoraListNewImpresoraToAttach);
            }
            impresoraListNew = attachedImpresoraListNew;
            usuariosCompartidos.setImpresoraList(impresoraListNew);
            usuariosCompartidos = em.merge(usuariosCompartidos);
            for (Impresora impresoraListOldImpresora : impresoraListOld) {
                if (!impresoraListNew.contains(impresoraListOldImpresora)) {
                    impresoraListOldImpresora.getUsuariosCompartidosList().remove(usuariosCompartidos);
                    impresoraListOldImpresora = em.merge(impresoraListOldImpresora);
                }
            }
            for (Impresora impresoraListNewImpresora : impresoraListNew) {
                if (!impresoraListOld.contains(impresoraListNewImpresora)) {
                    impresoraListNewImpresora.getUsuariosCompartidosList().add(usuariosCompartidos);
                    impresoraListNewImpresora = em.merge(impresoraListNewImpresora);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuariosCompartidos.getNombreUsuariocomp();
                if (findUsuariosCompartidos(id) == null) {
                    throw new NonexistentEntityException("The usuariosCompartidos with id " + id + " no longer exists.");
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
            UsuariosCompartidos usuariosCompartidos;
            try {
                usuariosCompartidos = em.getReference(UsuariosCompartidos.class, id);
                usuariosCompartidos.getNombreUsuariocomp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuariosCompartidos with id " + id + " no longer exists.", enfe);
            }
            List<Impresora> impresoraList = usuariosCompartidos.getImpresoraList();
            for (Impresora impresoraListImpresora : impresoraList) {
                impresoraListImpresora.getUsuariosCompartidosList().remove(usuariosCompartidos);
                impresoraListImpresora = em.merge(impresoraListImpresora);
            }
            em.remove(usuariosCompartidos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuariosCompartidos> findUsuariosCompartidosEntities() {
        return findUsuariosCompartidosEntities(true, -1, -1);
    }

    public List<UsuariosCompartidos> findUsuariosCompartidosEntities(int maxResults, int firstResult) {
        return findUsuariosCompartidosEntities(false, maxResults, firstResult);
    }

    private List<UsuariosCompartidos> findUsuariosCompartidosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuariosCompartidos.class));
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

    public UsuariosCompartidos findUsuariosCompartidos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuariosCompartidos.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCompartidosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuariosCompartidos> rt = cq.from(UsuariosCompartidos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
