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
import Entidades.Pc;
import Entidades.Programas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class ProgramasJpaController implements Serializable {

    public ProgramasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Programas programas) throws PreexistingEntityException, Exception {
        if (programas.getPcList() == null) {
            programas.setPcList(new ArrayList<Pc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : programas.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            programas.setPcList(attachedPcList);
            em.persist(programas);
            for (Pc pcListPc : programas.getPcList()) {
                pcListPc.getProgramasList().add(programas);
                pcListPc = em.merge(pcListPc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProgramas(programas.getIdProgramas()) != null) {
                throw new PreexistingEntityException("Programas " + programas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Programas programas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Programas persistentProgramas = em.find(Programas.class, programas.getIdProgramas());
            List<Pc> pcListOld = persistentProgramas.getPcList();
            List<Pc> pcListNew = programas.getPcList();
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            programas.setPcList(pcListNew);
            programas = em.merge(programas);
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    pcListOldPc.getProgramasList().remove(programas);
                    pcListOldPc = em.merge(pcListOldPc);
                }
            }
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    pcListNewPc.getProgramasList().add(programas);
                    pcListNewPc = em.merge(pcListNewPc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = programas.getIdProgramas();
                if (findProgramas(id) == null) {
                    throw new NonexistentEntityException("The programas with id " + id + " no longer exists.");
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
            Programas programas;
            try {
                programas = em.getReference(Programas.class, id);
                programas.getIdProgramas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programas with id " + id + " no longer exists.", enfe);
            }
            List<Pc> pcList = programas.getPcList();
            for (Pc pcListPc : pcList) {
                pcListPc.getProgramasList().remove(programas);
                pcListPc = em.merge(pcListPc);
            }
            em.remove(programas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Programas> findProgramasEntities() {
        return findProgramasEntities(true, -1, -1);
    }

    public List<Programas> findProgramasEntities(int maxResults, int firstResult) {
        return findProgramasEntities(false, maxResults, firstResult);
    }

    private List<Programas> findProgramasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Programas.class));
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

    public Programas findProgramas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Programas.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Programas> rt = cq.from(Programas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
