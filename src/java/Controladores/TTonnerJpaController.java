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
import Entidades.RegDatosToner;
import java.util.ArrayList;
import java.util.List;
import Entidades.Modelo;
import Entidades.Impresora;
import Entidades.TTonner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
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
        if (TTonner.getRegDatosTonerList() == null) {
            TTonner.setRegDatosTonerList(new ArrayList<RegDatosToner>());
        }
        if (TTonner.getModeloList() == null) {
            TTonner.setModeloList(new ArrayList<Modelo>());
        }
        if (TTonner.getImpresoraList() == null) {
            TTonner.setImpresoraList(new ArrayList<Impresora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RegDatosToner> attachedRegDatosTonerList = new ArrayList<RegDatosToner>();
            for (RegDatosToner regDatosTonerListRegDatosTonerToAttach : TTonner.getRegDatosTonerList()) {
                regDatosTonerListRegDatosTonerToAttach = em.getReference(regDatosTonerListRegDatosTonerToAttach.getClass(), regDatosTonerListRegDatosTonerToAttach.getIdRegToner());
                attachedRegDatosTonerList.add(regDatosTonerListRegDatosTonerToAttach);
            }
            TTonner.setRegDatosTonerList(attachedRegDatosTonerList);
            List<Modelo> attachedModeloList = new ArrayList<Modelo>();
            for (Modelo modeloListModeloToAttach : TTonner.getModeloList()) {
                modeloListModeloToAttach = em.getReference(modeloListModeloToAttach.getClass(), modeloListModeloToAttach.getIdModelo());
                attachedModeloList.add(modeloListModeloToAttach);
            }
            TTonner.setModeloList(attachedModeloList);
            List<Impresora> attachedImpresoraList = new ArrayList<Impresora>();
            for (Impresora impresoraListImpresoraToAttach : TTonner.getImpresoraList()) {
                impresoraListImpresoraToAttach = em.getReference(impresoraListImpresoraToAttach.getClass(), impresoraListImpresoraToAttach.getNoInventario());
                attachedImpresoraList.add(impresoraListImpresoraToAttach);
            }
            TTonner.setImpresoraList(attachedImpresoraList);
            em.persist(TTonner);
            for (RegDatosToner regDatosTonerListRegDatosToner : TTonner.getRegDatosTonerList()) {
                TTonner oldTTonnersnTonnerOfRegDatosTonerListRegDatosToner = regDatosTonerListRegDatosToner.getTTonnersnTonner();
                regDatosTonerListRegDatosToner.setTTonnersnTonner(TTonner);
                regDatosTonerListRegDatosToner = em.merge(regDatosTonerListRegDatosToner);
                if (oldTTonnersnTonnerOfRegDatosTonerListRegDatosToner != null) {
                    oldTTonnersnTonnerOfRegDatosTonerListRegDatosToner.getRegDatosTonerList().remove(regDatosTonerListRegDatosToner);
                    oldTTonnersnTonnerOfRegDatosTonerListRegDatosToner = em.merge(oldTTonnersnTonnerOfRegDatosTonerListRegDatosToner);
                }
            }
            for (Modelo modeloListModelo : TTonner.getModeloList()) {
                TTonner oldTTonnersnTonnerOfModeloListModelo = modeloListModelo.getTTonnersnTonner();
                modeloListModelo.setTTonnersnTonner(TTonner);
                modeloListModelo = em.merge(modeloListModelo);
                if (oldTTonnersnTonnerOfModeloListModelo != null) {
                    oldTTonnersnTonnerOfModeloListModelo.getModeloList().remove(modeloListModelo);
                    oldTTonnersnTonnerOfModeloListModelo = em.merge(oldTTonnersnTonnerOfModeloListModelo);
                }
            }
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

    public void edit(TTonner TTonner) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TTonner persistentTTonner = em.find(TTonner.class, TTonner.getSnTonner());
            List<RegDatosToner> regDatosTonerListOld = persistentTTonner.getRegDatosTonerList();
            List<RegDatosToner> regDatosTonerListNew = TTonner.getRegDatosTonerList();
            List<Modelo> modeloListOld = persistentTTonner.getModeloList();
            List<Modelo> modeloListNew = TTonner.getModeloList();
            List<Impresora> impresoraListOld = persistentTTonner.getImpresoraList();
            List<Impresora> impresoraListNew = TTonner.getImpresoraList();
            List<String> illegalOrphanMessages = null;
            for (RegDatosToner regDatosTonerListOldRegDatosToner : regDatosTonerListOld) {
                if (!regDatosTonerListNew.contains(regDatosTonerListOldRegDatosToner)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegDatosToner " + regDatosTonerListOldRegDatosToner + " since its TTonnersnTonner field is not nullable.");
                }
            }
            for (Impresora impresoraListOldImpresora : impresoraListOld) {
                if (!impresoraListNew.contains(impresoraListOldImpresora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Impresora " + impresoraListOldImpresora + " since its TTonnersnTonner field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RegDatosToner> attachedRegDatosTonerListNew = new ArrayList<RegDatosToner>();
            for (RegDatosToner regDatosTonerListNewRegDatosTonerToAttach : regDatosTonerListNew) {
                regDatosTonerListNewRegDatosTonerToAttach = em.getReference(regDatosTonerListNewRegDatosTonerToAttach.getClass(), regDatosTonerListNewRegDatosTonerToAttach.getIdRegToner());
                attachedRegDatosTonerListNew.add(regDatosTonerListNewRegDatosTonerToAttach);
            }
            regDatosTonerListNew = attachedRegDatosTonerListNew;
            TTonner.setRegDatosTonerList(regDatosTonerListNew);
            List<Modelo> attachedModeloListNew = new ArrayList<Modelo>();
            for (Modelo modeloListNewModeloToAttach : modeloListNew) {
                modeloListNewModeloToAttach = em.getReference(modeloListNewModeloToAttach.getClass(), modeloListNewModeloToAttach.getIdModelo());
                attachedModeloListNew.add(modeloListNewModeloToAttach);
            }
            modeloListNew = attachedModeloListNew;
            TTonner.setModeloList(modeloListNew);
            List<Impresora> attachedImpresoraListNew = new ArrayList<Impresora>();
            for (Impresora impresoraListNewImpresoraToAttach : impresoraListNew) {
                impresoraListNewImpresoraToAttach = em.getReference(impresoraListNewImpresoraToAttach.getClass(), impresoraListNewImpresoraToAttach.getNoInventario());
                attachedImpresoraListNew.add(impresoraListNewImpresoraToAttach);
            }
            impresoraListNew = attachedImpresoraListNew;
            TTonner.setImpresoraList(impresoraListNew);
            TTonner = em.merge(TTonner);
            for (RegDatosToner regDatosTonerListNewRegDatosToner : regDatosTonerListNew) {
                if (!regDatosTonerListOld.contains(regDatosTonerListNewRegDatosToner)) {
                    TTonner oldTTonnersnTonnerOfRegDatosTonerListNewRegDatosToner = regDatosTonerListNewRegDatosToner.getTTonnersnTonner();
                    regDatosTonerListNewRegDatosToner.setTTonnersnTonner(TTonner);
                    regDatosTonerListNewRegDatosToner = em.merge(regDatosTonerListNewRegDatosToner);
                    if (oldTTonnersnTonnerOfRegDatosTonerListNewRegDatosToner != null && !oldTTonnersnTonnerOfRegDatosTonerListNewRegDatosToner.equals(TTonner)) {
                        oldTTonnersnTonnerOfRegDatosTonerListNewRegDatosToner.getRegDatosTonerList().remove(regDatosTonerListNewRegDatosToner);
                        oldTTonnersnTonnerOfRegDatosTonerListNewRegDatosToner = em.merge(oldTTonnersnTonnerOfRegDatosTonerListNewRegDatosToner);
                    }
                }
            }
            for (Modelo modeloListOldModelo : modeloListOld) {
                if (!modeloListNew.contains(modeloListOldModelo)) {
                    modeloListOldModelo.setTTonnersnTonner(null);
                    modeloListOldModelo = em.merge(modeloListOldModelo);
                }
            }
            for (Modelo modeloListNewModelo : modeloListNew) {
                if (!modeloListOld.contains(modeloListNewModelo)) {
                    TTonner oldTTonnersnTonnerOfModeloListNewModelo = modeloListNewModelo.getTTonnersnTonner();
                    modeloListNewModelo.setTTonnersnTonner(TTonner);
                    modeloListNewModelo = em.merge(modeloListNewModelo);
                    if (oldTTonnersnTonnerOfModeloListNewModelo != null && !oldTTonnersnTonnerOfModeloListNewModelo.equals(TTonner)) {
                        oldTTonnersnTonnerOfModeloListNewModelo.getModeloList().remove(modeloListNewModelo);
                        oldTTonnersnTonnerOfModeloListNewModelo = em.merge(oldTTonnersnTonnerOfModeloListNewModelo);
                    }
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<RegDatosToner> regDatosTonerListOrphanCheck = TTonner.getRegDatosTonerList();
            for (RegDatosToner regDatosTonerListOrphanCheckRegDatosToner : regDatosTonerListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TTonner (" + TTonner + ") cannot be destroyed since the RegDatosToner " + regDatosTonerListOrphanCheckRegDatosToner + " in its regDatosTonerList field has a non-nullable TTonnersnTonner field.");
            }
            List<Impresora> impresoraListOrphanCheck = TTonner.getImpresoraList();
            for (Impresora impresoraListOrphanCheckImpresora : impresoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TTonner (" + TTonner + ") cannot be destroyed since the Impresora " + impresoraListOrphanCheckImpresora + " in its impresoraList field has a non-nullable TTonnersnTonner field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Modelo> modeloList = TTonner.getModeloList();
            for (Modelo modeloListModelo : modeloList) {
                modeloListModelo.setTTonnersnTonner(null);
                modeloListModelo = em.merge(modeloListModelo);
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
