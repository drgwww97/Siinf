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
import java.util.ArrayList;
import java.util.List;
import Entidades.Componente;
import Entidades.TConexion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dayana
 */
public class TConexionJpaController implements Serializable {

    public TConexionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TConexion TConexion) throws PreexistingEntityException, Exception {
        if (TConexion.getAccesorioList() == null) {
            TConexion.setAccesorioList(new ArrayList<Accesorio>());
        }
        if (TConexion.getComponenteList() == null) {
            TConexion.setComponenteList(new ArrayList<Componente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Accesorio> attachedAccesorioList = new ArrayList<Accesorio>();
            for (Accesorio accesorioListAccesorioToAttach : TConexion.getAccesorioList()) {
                accesorioListAccesorioToAttach = em.getReference(accesorioListAccesorioToAttach.getClass(), accesorioListAccesorioToAttach.getSnAccesorio());
                attachedAccesorioList.add(accesorioListAccesorioToAttach);
            }
            TConexion.setAccesorioList(attachedAccesorioList);
            List<Componente> attachedComponenteList = new ArrayList<Componente>();
            for (Componente componenteListComponenteToAttach : TConexion.getComponenteList()) {
                componenteListComponenteToAttach = em.getReference(componenteListComponenteToAttach.getClass(), componenteListComponenteToAttach.getSnComponente());
                attachedComponenteList.add(componenteListComponenteToAttach);
            }
            TConexion.setComponenteList(attachedComponenteList);
            em.persist(TConexion);
            for (Accesorio accesorioListAccesorio : TConexion.getAccesorioList()) {
                TConexion oldTConexionidConexionOfAccesorioListAccesorio = accesorioListAccesorio.getTConexionidConexion();
                accesorioListAccesorio.setTConexionidConexion(TConexion);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
                if (oldTConexionidConexionOfAccesorioListAccesorio != null) {
                    oldTConexionidConexionOfAccesorioListAccesorio.getAccesorioList().remove(accesorioListAccesorio);
                    oldTConexionidConexionOfAccesorioListAccesorio = em.merge(oldTConexionidConexionOfAccesorioListAccesorio);
                }
            }
            for (Componente componenteListComponente : TConexion.getComponenteList()) {
                TConexion oldTConexionidConexionOfComponenteListComponente = componenteListComponente.getTConexionidConexion();
                componenteListComponente.setTConexionidConexion(TConexion);
                componenteListComponente = em.merge(componenteListComponente);
                if (oldTConexionidConexionOfComponenteListComponente != null) {
                    oldTConexionidConexionOfComponenteListComponente.getComponenteList().remove(componenteListComponente);
                    oldTConexionidConexionOfComponenteListComponente = em.merge(oldTConexionidConexionOfComponenteListComponente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTConexion(TConexion.getIdConexion()) != null) {
                throw new PreexistingEntityException("TConexion " + TConexion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TConexion TConexion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TConexion persistentTConexion = em.find(TConexion.class, TConexion.getIdConexion());
            List<Accesorio> accesorioListOld = persistentTConexion.getAccesorioList();
            List<Accesorio> accesorioListNew = TConexion.getAccesorioList();
            List<Componente> componenteListOld = persistentTConexion.getComponenteList();
            List<Componente> componenteListNew = TConexion.getComponenteList();
            List<String> illegalOrphanMessages = null;
            for (Accesorio accesorioListOldAccesorio : accesorioListOld) {
                if (!accesorioListNew.contains(accesorioListOldAccesorio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Accesorio " + accesorioListOldAccesorio + " since its TConexionidConexion field is not nullable.");
                }
            }
            for (Componente componenteListOldComponente : componenteListOld) {
                if (!componenteListNew.contains(componenteListOldComponente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Componente " + componenteListOldComponente + " since its TConexionidConexion field is not nullable.");
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
            TConexion.setAccesorioList(accesorioListNew);
            List<Componente> attachedComponenteListNew = new ArrayList<Componente>();
            for (Componente componenteListNewComponenteToAttach : componenteListNew) {
                componenteListNewComponenteToAttach = em.getReference(componenteListNewComponenteToAttach.getClass(), componenteListNewComponenteToAttach.getSnComponente());
                attachedComponenteListNew.add(componenteListNewComponenteToAttach);
            }
            componenteListNew = attachedComponenteListNew;
            TConexion.setComponenteList(componenteListNew);
            TConexion = em.merge(TConexion);
            for (Accesorio accesorioListNewAccesorio : accesorioListNew) {
                if (!accesorioListOld.contains(accesorioListNewAccesorio)) {
                    TConexion oldTConexionidConexionOfAccesorioListNewAccesorio = accesorioListNewAccesorio.getTConexionidConexion();
                    accesorioListNewAccesorio.setTConexionidConexion(TConexion);
                    accesorioListNewAccesorio = em.merge(accesorioListNewAccesorio);
                    if (oldTConexionidConexionOfAccesorioListNewAccesorio != null && !oldTConexionidConexionOfAccesorioListNewAccesorio.equals(TConexion)) {
                        oldTConexionidConexionOfAccesorioListNewAccesorio.getAccesorioList().remove(accesorioListNewAccesorio);
                        oldTConexionidConexionOfAccesorioListNewAccesorio = em.merge(oldTConexionidConexionOfAccesorioListNewAccesorio);
                    }
                }
            }
            for (Componente componenteListNewComponente : componenteListNew) {
                if (!componenteListOld.contains(componenteListNewComponente)) {
                    TConexion oldTConexionidConexionOfComponenteListNewComponente = componenteListNewComponente.getTConexionidConexion();
                    componenteListNewComponente.setTConexionidConexion(TConexion);
                    componenteListNewComponente = em.merge(componenteListNewComponente);
                    if (oldTConexionidConexionOfComponenteListNewComponente != null && !oldTConexionidConexionOfComponenteListNewComponente.equals(TConexion)) {
                        oldTConexionidConexionOfComponenteListNewComponente.getComponenteList().remove(componenteListNewComponente);
                        oldTConexionidConexionOfComponenteListNewComponente = em.merge(oldTConexionidConexionOfComponenteListNewComponente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = TConexion.getIdConexion();
                if (findTConexion(id) == null) {
                    throw new NonexistentEntityException("The tConexion with id " + id + " no longer exists.");
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
            TConexion TConexion;
            try {
                TConexion = em.getReference(TConexion.class, id);
                TConexion.getIdConexion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The TConexion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Accesorio> accesorioListOrphanCheck = TConexion.getAccesorioList();
            for (Accesorio accesorioListOrphanCheckAccesorio : accesorioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TConexion (" + TConexion + ") cannot be destroyed since the Accesorio " + accesorioListOrphanCheckAccesorio + " in its accesorioList field has a non-nullable TConexionidConexion field.");
            }
            List<Componente> componenteListOrphanCheck = TConexion.getComponenteList();
            for (Componente componenteListOrphanCheckComponente : componenteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TConexion (" + TConexion + ") cannot be destroyed since the Componente " + componenteListOrphanCheckComponente + " in its componenteList field has a non-nullable TConexionidConexion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(TConexion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TConexion> findTConexionEntities() {
        return findTConexionEntities(true, -1, -1);
    }

    public List<TConexion> findTConexionEntities(int maxResults, int firstResult) {
        return findTConexionEntities(false, maxResults, firstResult);
    }

    private List<TConexion> findTConexionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TConexion.class));
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

    public TConexion findTConexion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TConexion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTConexionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TConexion> rt = cq.from(TConexion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
