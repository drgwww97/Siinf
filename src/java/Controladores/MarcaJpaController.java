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
import Entidades.Impresora;
import Entidades.Marca;
import Entidades.Pc;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class MarcaJpaController implements Serializable {

    public MarcaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marca marca) throws PreexistingEntityException, Exception {
        if (marca.getAccesorioList() == null) {
            marca.setAccesorioList(new ArrayList<Accesorio>());
        }
        if (marca.getComponenteList() == null) {
            marca.setComponenteList(new ArrayList<Componente>());
        }
        if (marca.getImpresoraList() == null) {
            marca.setImpresoraList(new ArrayList<Impresora>());
        }
        if (marca.getPcList() == null) {
            marca.setPcList(new ArrayList<Pc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Accesorio> attachedAccesorioList = new ArrayList<Accesorio>();
            for (Accesorio accesorioListAccesorioToAttach : marca.getAccesorioList()) {
                accesorioListAccesorioToAttach = em.getReference(accesorioListAccesorioToAttach.getClass(), accesorioListAccesorioToAttach.getSnAccesorio());
                attachedAccesorioList.add(accesorioListAccesorioToAttach);
            }
            marca.setAccesorioList(attachedAccesorioList);
            List<Componente> attachedComponenteList = new ArrayList<Componente>();
            for (Componente componenteListComponenteToAttach : marca.getComponenteList()) {
                componenteListComponenteToAttach = em.getReference(componenteListComponenteToAttach.getClass(), componenteListComponenteToAttach.getSnComponente());
                attachedComponenteList.add(componenteListComponenteToAttach);
            }
            marca.setComponenteList(attachedComponenteList);
            List<Impresora> attachedImpresoraList = new ArrayList<Impresora>();
            for (Impresora impresoraListImpresoraToAttach : marca.getImpresoraList()) {
                impresoraListImpresoraToAttach = em.getReference(impresoraListImpresoraToAttach.getClass(), impresoraListImpresoraToAttach.getNoInventario());
                attachedImpresoraList.add(impresoraListImpresoraToAttach);
            }
            marca.setImpresoraList(attachedImpresoraList);
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : marca.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            marca.setPcList(attachedPcList);
            em.persist(marca);
            for (Accesorio accesorioListAccesorio : marca.getAccesorioList()) {
                Marca oldMarcaidMarcaOfAccesorioListAccesorio = accesorioListAccesorio.getMarcaidMarca();
                accesorioListAccesorio.setMarcaidMarca(marca);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
                if (oldMarcaidMarcaOfAccesorioListAccesorio != null) {
                    oldMarcaidMarcaOfAccesorioListAccesorio.getAccesorioList().remove(accesorioListAccesorio);
                    oldMarcaidMarcaOfAccesorioListAccesorio = em.merge(oldMarcaidMarcaOfAccesorioListAccesorio);
                }
            }
            for (Componente componenteListComponente : marca.getComponenteList()) {
                Marca oldMarcaidMarcaOfComponenteListComponente = componenteListComponente.getMarcaidMarca();
                componenteListComponente.setMarcaidMarca(marca);
                componenteListComponente = em.merge(componenteListComponente);
                if (oldMarcaidMarcaOfComponenteListComponente != null) {
                    oldMarcaidMarcaOfComponenteListComponente.getComponenteList().remove(componenteListComponente);
                    oldMarcaidMarcaOfComponenteListComponente = em.merge(oldMarcaidMarcaOfComponenteListComponente);
                }
            }
            for (Impresora impresoraListImpresora : marca.getImpresoraList()) {
                Marca oldMarcaidMarcaOfImpresoraListImpresora = impresoraListImpresora.getMarcaidMarca();
                impresoraListImpresora.setMarcaidMarca(marca);
                impresoraListImpresora = em.merge(impresoraListImpresora);
                if (oldMarcaidMarcaOfImpresoraListImpresora != null) {
                    oldMarcaidMarcaOfImpresoraListImpresora.getImpresoraList().remove(impresoraListImpresora);
                    oldMarcaidMarcaOfImpresoraListImpresora = em.merge(oldMarcaidMarcaOfImpresoraListImpresora);
                }
            }
            for (Pc pcListPc : marca.getPcList()) {
                Marca oldMarcaidMarcaOfPcListPc = pcListPc.getMarcaidMarca();
                pcListPc.setMarcaidMarca(marca);
                pcListPc = em.merge(pcListPc);
                if (oldMarcaidMarcaOfPcListPc != null) {
                    oldMarcaidMarcaOfPcListPc.getPcList().remove(pcListPc);
                    oldMarcaidMarcaOfPcListPc = em.merge(oldMarcaidMarcaOfPcListPc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMarca(marca.getIdMarca()) != null) {
                throw new PreexistingEntityException("Marca " + marca + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marca marca) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca persistentMarca = em.find(Marca.class, marca.getIdMarca());
            List<Accesorio> accesorioListOld = persistentMarca.getAccesorioList();
            List<Accesorio> accesorioListNew = marca.getAccesorioList();
            List<Componente> componenteListOld = persistentMarca.getComponenteList();
            List<Componente> componenteListNew = marca.getComponenteList();
            List<Impresora> impresoraListOld = persistentMarca.getImpresoraList();
            List<Impresora> impresoraListNew = marca.getImpresoraList();
            List<Pc> pcListOld = persistentMarca.getPcList();
            List<Pc> pcListNew = marca.getPcList();
            List<String> illegalOrphanMessages = null;
            for (Accesorio accesorioListOldAccesorio : accesorioListOld) {
                if (!accesorioListNew.contains(accesorioListOldAccesorio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Accesorio " + accesorioListOldAccesorio + " since its marcaidMarca field is not nullable.");
                }
            }
            for (Impresora impresoraListOldImpresora : impresoraListOld) {
                if (!impresoraListNew.contains(impresoraListOldImpresora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Impresora " + impresoraListOldImpresora + " since its marcaidMarca field is not nullable.");
                }
            }
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pc " + pcListOldPc + " since its marcaidMarca field is not nullable.");
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
            marca.setAccesorioList(accesorioListNew);
            List<Componente> attachedComponenteListNew = new ArrayList<Componente>();
            for (Componente componenteListNewComponenteToAttach : componenteListNew) {
                componenteListNewComponenteToAttach = em.getReference(componenteListNewComponenteToAttach.getClass(), componenteListNewComponenteToAttach.getSnComponente());
                attachedComponenteListNew.add(componenteListNewComponenteToAttach);
            }
            componenteListNew = attachedComponenteListNew;
            marca.setComponenteList(componenteListNew);
            List<Impresora> attachedImpresoraListNew = new ArrayList<Impresora>();
            for (Impresora impresoraListNewImpresoraToAttach : impresoraListNew) {
                impresoraListNewImpresoraToAttach = em.getReference(impresoraListNewImpresoraToAttach.getClass(), impresoraListNewImpresoraToAttach.getNoInventario());
                attachedImpresoraListNew.add(impresoraListNewImpresoraToAttach);
            }
            impresoraListNew = attachedImpresoraListNew;
            marca.setImpresoraList(impresoraListNew);
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            marca.setPcList(pcListNew);
            marca = em.merge(marca);
            for (Accesorio accesorioListNewAccesorio : accesorioListNew) {
                if (!accesorioListOld.contains(accesorioListNewAccesorio)) {
                    Marca oldMarcaidMarcaOfAccesorioListNewAccesorio = accesorioListNewAccesorio.getMarcaidMarca();
                    accesorioListNewAccesorio.setMarcaidMarca(marca);
                    accesorioListNewAccesorio = em.merge(accesorioListNewAccesorio);
                    if (oldMarcaidMarcaOfAccesorioListNewAccesorio != null && !oldMarcaidMarcaOfAccesorioListNewAccesorio.equals(marca)) {
                        oldMarcaidMarcaOfAccesorioListNewAccesorio.getAccesorioList().remove(accesorioListNewAccesorio);
                        oldMarcaidMarcaOfAccesorioListNewAccesorio = em.merge(oldMarcaidMarcaOfAccesorioListNewAccesorio);
                    }
                }
            }
            for (Componente componenteListOldComponente : componenteListOld) {
                if (!componenteListNew.contains(componenteListOldComponente)) {
                    componenteListOldComponente.setMarcaidMarca(null);
                    componenteListOldComponente = em.merge(componenteListOldComponente);
                }
            }
            for (Componente componenteListNewComponente : componenteListNew) {
                if (!componenteListOld.contains(componenteListNewComponente)) {
                    Marca oldMarcaidMarcaOfComponenteListNewComponente = componenteListNewComponente.getMarcaidMarca();
                    componenteListNewComponente.setMarcaidMarca(marca);
                    componenteListNewComponente = em.merge(componenteListNewComponente);
                    if (oldMarcaidMarcaOfComponenteListNewComponente != null && !oldMarcaidMarcaOfComponenteListNewComponente.equals(marca)) {
                        oldMarcaidMarcaOfComponenteListNewComponente.getComponenteList().remove(componenteListNewComponente);
                        oldMarcaidMarcaOfComponenteListNewComponente = em.merge(oldMarcaidMarcaOfComponenteListNewComponente);
                    }
                }
            }
            for (Impresora impresoraListNewImpresora : impresoraListNew) {
                if (!impresoraListOld.contains(impresoraListNewImpresora)) {
                    Marca oldMarcaidMarcaOfImpresoraListNewImpresora = impresoraListNewImpresora.getMarcaidMarca();
                    impresoraListNewImpresora.setMarcaidMarca(marca);
                    impresoraListNewImpresora = em.merge(impresoraListNewImpresora);
                    if (oldMarcaidMarcaOfImpresoraListNewImpresora != null && !oldMarcaidMarcaOfImpresoraListNewImpresora.equals(marca)) {
                        oldMarcaidMarcaOfImpresoraListNewImpresora.getImpresoraList().remove(impresoraListNewImpresora);
                        oldMarcaidMarcaOfImpresoraListNewImpresora = em.merge(oldMarcaidMarcaOfImpresoraListNewImpresora);
                    }
                }
            }
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    Marca oldMarcaidMarcaOfPcListNewPc = pcListNewPc.getMarcaidMarca();
                    pcListNewPc.setMarcaidMarca(marca);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldMarcaidMarcaOfPcListNewPc != null && !oldMarcaidMarcaOfPcListNewPc.equals(marca)) {
                        oldMarcaidMarcaOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldMarcaidMarcaOfPcListNewPc = em.merge(oldMarcaidMarcaOfPcListNewPc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marca.getIdMarca();
                if (findMarca(id) == null) {
                    throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
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
            Marca marca;
            try {
                marca = em.getReference(Marca.class, id);
                marca.getIdMarca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Accesorio> accesorioListOrphanCheck = marca.getAccesorioList();
            for (Accesorio accesorioListOrphanCheckAccesorio : accesorioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Marca (" + marca + ") cannot be destroyed since the Accesorio " + accesorioListOrphanCheckAccesorio + " in its accesorioList field has a non-nullable marcaidMarca field.");
            }
            List<Impresora> impresoraListOrphanCheck = marca.getImpresoraList();
            for (Impresora impresoraListOrphanCheckImpresora : impresoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Marca (" + marca + ") cannot be destroyed since the Impresora " + impresoraListOrphanCheckImpresora + " in its impresoraList field has a non-nullable marcaidMarca field.");
            }
            List<Pc> pcListOrphanCheck = marca.getPcList();
            for (Pc pcListOrphanCheckPc : pcListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Marca (" + marca + ") cannot be destroyed since the Pc " + pcListOrphanCheckPc + " in its pcList field has a non-nullable marcaidMarca field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Componente> componenteList = marca.getComponenteList();
            for (Componente componenteListComponente : componenteList) {
                componenteListComponente.setMarcaidMarca(null);
                componenteListComponente = em.merge(componenteListComponente);
            }
            em.remove(marca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marca> findMarcaEntities() {
        return findMarcaEntities(true, -1, -1);
    }

    public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
        return findMarcaEntities(false, maxResults, firstResult);
    }

    private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marca.class));
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

    public Marca findMarca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marca.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marca> rt = cq.from(Marca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
