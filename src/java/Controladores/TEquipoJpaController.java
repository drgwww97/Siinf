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
import Entidades.TEquipo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class TEquipoJpaController implements Serializable {

    public TEquipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TEquipo TEquipo) throws PreexistingEntityException, Exception {
        if (TEquipo.getPcList() == null) {
            TEquipo.setPcList(new ArrayList<Pc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : TEquipo.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            TEquipo.setPcList(attachedPcList);
            em.persist(TEquipo);
            for (Pc pcListPc : TEquipo.getPcList()) {
                TEquipo oldTEquipoidEquipoOfPcListPc = pcListPc.getTEquipoidEquipo();
                pcListPc.setTEquipoidEquipo(TEquipo);
                pcListPc = em.merge(pcListPc);
                if (oldTEquipoidEquipoOfPcListPc != null) {
                    oldTEquipoidEquipoOfPcListPc.getPcList().remove(pcListPc);
                    oldTEquipoidEquipoOfPcListPc = em.merge(oldTEquipoidEquipoOfPcListPc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTEquipo(TEquipo.getIdEquipo()) != null) {
                throw new PreexistingEntityException("TEquipo " + TEquipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TEquipo TEquipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TEquipo persistentTEquipo = em.find(TEquipo.class, TEquipo.getIdEquipo());
            List<Pc> pcListOld = persistentTEquipo.getPcList();
            List<Pc> pcListNew = TEquipo.getPcList();
            List<String> illegalOrphanMessages = null;
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pc " + pcListOldPc + " since its TEquipoidEquipo field is not nullable.");
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
            TEquipo.setPcList(pcListNew);
            TEquipo = em.merge(TEquipo);
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    TEquipo oldTEquipoidEquipoOfPcListNewPc = pcListNewPc.getTEquipoidEquipo();
                    pcListNewPc.setTEquipoidEquipo(TEquipo);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldTEquipoidEquipoOfPcListNewPc != null && !oldTEquipoidEquipoOfPcListNewPc.equals(TEquipo)) {
                        oldTEquipoidEquipoOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldTEquipoidEquipoOfPcListNewPc = em.merge(oldTEquipoidEquipoOfPcListNewPc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = TEquipo.getIdEquipo();
                if (findTEquipo(id) == null) {
                    throw new NonexistentEntityException("The tEquipo with id " + id + " no longer exists.");
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
            TEquipo TEquipo;
            try {
                TEquipo = em.getReference(TEquipo.class, id);
                TEquipo.getIdEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The TEquipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pc> pcListOrphanCheck = TEquipo.getPcList();
            for (Pc pcListOrphanCheckPc : pcListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TEquipo (" + TEquipo + ") cannot be destroyed since the Pc " + pcListOrphanCheckPc + " in its pcList field has a non-nullable TEquipoidEquipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(TEquipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TEquipo> findTEquipoEntities() {
        return findTEquipoEntities(true, -1, -1);
    }

    public List<TEquipo> findTEquipoEntities(int maxResults, int firstResult) {
        return findTEquipoEntities(false, maxResults, firstResult);
    }

    private List<TEquipo> findTEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TEquipo.class));
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

    public TEquipo findTEquipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TEquipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TEquipo> rt = cq.from(TEquipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
