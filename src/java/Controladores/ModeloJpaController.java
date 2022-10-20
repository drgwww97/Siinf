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
import Entidades.Impresora;
import java.util.ArrayList;
import java.util.List;
import Entidades.Pc;
import Entidades.Accesorio;
import Entidades.Componente;
import Entidades.Modelo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dayana
 */
public class ModeloJpaController implements Serializable {

    public ModeloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modelo modelo) throws PreexistingEntityException, Exception {
        if (modelo.getImpresoraList() == null) {
            modelo.setImpresoraList(new ArrayList<Impresora>());
        }
        if (modelo.getPcList() == null) {
            modelo.setPcList(new ArrayList<Pc>());
        }
        if (modelo.getAccesorioList() == null) {
            modelo.setAccesorioList(new ArrayList<Accesorio>());
        }
        if (modelo.getComponenteList() == null) {
            modelo.setComponenteList(new ArrayList<Componente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Impresora> attachedImpresoraList = new ArrayList<Impresora>();
            for (Impresora impresoraListImpresoraToAttach : modelo.getImpresoraList()) {
                impresoraListImpresoraToAttach = em.getReference(impresoraListImpresoraToAttach.getClass(), impresoraListImpresoraToAttach.getNoInventario());
                attachedImpresoraList.add(impresoraListImpresoraToAttach);
            }
            modelo.setImpresoraList(attachedImpresoraList);
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : modelo.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            modelo.setPcList(attachedPcList);
            List<Accesorio> attachedAccesorioList = new ArrayList<Accesorio>();
            for (Accesorio accesorioListAccesorioToAttach : modelo.getAccesorioList()) {
                accesorioListAccesorioToAttach = em.getReference(accesorioListAccesorioToAttach.getClass(), accesorioListAccesorioToAttach.getSnAccesorio());
                attachedAccesorioList.add(accesorioListAccesorioToAttach);
            }
            modelo.setAccesorioList(attachedAccesorioList);
            List<Componente> attachedComponenteList = new ArrayList<Componente>();
            for (Componente componenteListComponenteToAttach : modelo.getComponenteList()) {
                componenteListComponenteToAttach = em.getReference(componenteListComponenteToAttach.getClass(), componenteListComponenteToAttach.getSnComponente());
                attachedComponenteList.add(componenteListComponenteToAttach);
            }
            modelo.setComponenteList(attachedComponenteList);
            em.persist(modelo);
            for (Impresora impresoraListImpresora : modelo.getImpresoraList()) {
                Modelo oldModeloidModeloOfImpresoraListImpresora = impresoraListImpresora.getModeloidModelo();
                impresoraListImpresora.setModeloidModelo(modelo);
                impresoraListImpresora = em.merge(impresoraListImpresora);
                if (oldModeloidModeloOfImpresoraListImpresora != null) {
                    oldModeloidModeloOfImpresoraListImpresora.getImpresoraList().remove(impresoraListImpresora);
                    oldModeloidModeloOfImpresoraListImpresora = em.merge(oldModeloidModeloOfImpresoraListImpresora);
                }
            }
            for (Pc pcListPc : modelo.getPcList()) {
                Modelo oldModeloidModeloOfPcListPc = pcListPc.getModeloidModelo();
                pcListPc.setModeloidModelo(modelo);
                pcListPc = em.merge(pcListPc);
                if (oldModeloidModeloOfPcListPc != null) {
                    oldModeloidModeloOfPcListPc.getPcList().remove(pcListPc);
                    oldModeloidModeloOfPcListPc = em.merge(oldModeloidModeloOfPcListPc);
                }
            }
            for (Accesorio accesorioListAccesorio : modelo.getAccesorioList()) {
                Modelo oldModeloidModeloOfAccesorioListAccesorio = accesorioListAccesorio.getModeloidModelo();
                accesorioListAccesorio.setModeloidModelo(modelo);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
                if (oldModeloidModeloOfAccesorioListAccesorio != null) {
                    oldModeloidModeloOfAccesorioListAccesorio.getAccesorioList().remove(accesorioListAccesorio);
                    oldModeloidModeloOfAccesorioListAccesorio = em.merge(oldModeloidModeloOfAccesorioListAccesorio);
                }
            }
            for (Componente componenteListComponente : modelo.getComponenteList()) {
                Modelo oldModeloidModeloOfComponenteListComponente = componenteListComponente.getModeloidModelo();
                componenteListComponente.setModeloidModelo(modelo);
                componenteListComponente = em.merge(componenteListComponente);
                if (oldModeloidModeloOfComponenteListComponente != null) {
                    oldModeloidModeloOfComponenteListComponente.getComponenteList().remove(componenteListComponente);
                    oldModeloidModeloOfComponenteListComponente = em.merge(oldModeloidModeloOfComponenteListComponente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModelo(modelo.getIdModelo()) != null) {
                throw new PreexistingEntityException("Modelo " + modelo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modelo modelo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modelo persistentModelo = em.find(Modelo.class, modelo.getIdModelo());
            List<Impresora> impresoraListOld = persistentModelo.getImpresoraList();
            List<Impresora> impresoraListNew = modelo.getImpresoraList();
            List<Pc> pcListOld = persistentModelo.getPcList();
            List<Pc> pcListNew = modelo.getPcList();
            List<Accesorio> accesorioListOld = persistentModelo.getAccesorioList();
            List<Accesorio> accesorioListNew = modelo.getAccesorioList();
            List<Componente> componenteListOld = persistentModelo.getComponenteList();
            List<Componente> componenteListNew = modelo.getComponenteList();
            List<String> illegalOrphanMessages = null;
            for (Impresora impresoraListOldImpresora : impresoraListOld) {
                if (!impresoraListNew.contains(impresoraListOldImpresora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Impresora " + impresoraListOldImpresora + " since its modeloidModelo field is not nullable.");
                }
            }
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pc " + pcListOldPc + " since its modeloidModelo field is not nullable.");
                }
            }
            for (Accesorio accesorioListOldAccesorio : accesorioListOld) {
                if (!accesorioListNew.contains(accesorioListOldAccesorio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Accesorio " + accesorioListOldAccesorio + " since its modeloidModelo field is not nullable.");
                }
            }
            for (Componente componenteListOldComponente : componenteListOld) {
                if (!componenteListNew.contains(componenteListOldComponente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Componente " + componenteListOldComponente + " since its modeloidModelo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Impresora> attachedImpresoraListNew = new ArrayList<Impresora>();
            for (Impresora impresoraListNewImpresoraToAttach : impresoraListNew) {
                impresoraListNewImpresoraToAttach = em.getReference(impresoraListNewImpresoraToAttach.getClass(), impresoraListNewImpresoraToAttach.getNoInventario());
                attachedImpresoraListNew.add(impresoraListNewImpresoraToAttach);
            }
            impresoraListNew = attachedImpresoraListNew;
            modelo.setImpresoraList(impresoraListNew);
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            modelo.setPcList(pcListNew);
            List<Accesorio> attachedAccesorioListNew = new ArrayList<Accesorio>();
            for (Accesorio accesorioListNewAccesorioToAttach : accesorioListNew) {
                accesorioListNewAccesorioToAttach = em.getReference(accesorioListNewAccesorioToAttach.getClass(), accesorioListNewAccesorioToAttach.getSnAccesorio());
                attachedAccesorioListNew.add(accesorioListNewAccesorioToAttach);
            }
            accesorioListNew = attachedAccesorioListNew;
            modelo.setAccesorioList(accesorioListNew);
            List<Componente> attachedComponenteListNew = new ArrayList<Componente>();
            for (Componente componenteListNewComponenteToAttach : componenteListNew) {
                componenteListNewComponenteToAttach = em.getReference(componenteListNewComponenteToAttach.getClass(), componenteListNewComponenteToAttach.getSnComponente());
                attachedComponenteListNew.add(componenteListNewComponenteToAttach);
            }
            componenteListNew = attachedComponenteListNew;
            modelo.setComponenteList(componenteListNew);
            modelo = em.merge(modelo);
            for (Impresora impresoraListNewImpresora : impresoraListNew) {
                if (!impresoraListOld.contains(impresoraListNewImpresora)) {
                    Modelo oldModeloidModeloOfImpresoraListNewImpresora = impresoraListNewImpresora.getModeloidModelo();
                    impresoraListNewImpresora.setModeloidModelo(modelo);
                    impresoraListNewImpresora = em.merge(impresoraListNewImpresora);
                    if (oldModeloidModeloOfImpresoraListNewImpresora != null && !oldModeloidModeloOfImpresoraListNewImpresora.equals(modelo)) {
                        oldModeloidModeloOfImpresoraListNewImpresora.getImpresoraList().remove(impresoraListNewImpresora);
                        oldModeloidModeloOfImpresoraListNewImpresora = em.merge(oldModeloidModeloOfImpresoraListNewImpresora);
                    }
                }
            }
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    Modelo oldModeloidModeloOfPcListNewPc = pcListNewPc.getModeloidModelo();
                    pcListNewPc.setModeloidModelo(modelo);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldModeloidModeloOfPcListNewPc != null && !oldModeloidModeloOfPcListNewPc.equals(modelo)) {
                        oldModeloidModeloOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldModeloidModeloOfPcListNewPc = em.merge(oldModeloidModeloOfPcListNewPc);
                    }
                }
            }
            for (Accesorio accesorioListNewAccesorio : accesorioListNew) {
                if (!accesorioListOld.contains(accesorioListNewAccesorio)) {
                    Modelo oldModeloidModeloOfAccesorioListNewAccesorio = accesorioListNewAccesorio.getModeloidModelo();
                    accesorioListNewAccesorio.setModeloidModelo(modelo);
                    accesorioListNewAccesorio = em.merge(accesorioListNewAccesorio);
                    if (oldModeloidModeloOfAccesorioListNewAccesorio != null && !oldModeloidModeloOfAccesorioListNewAccesorio.equals(modelo)) {
                        oldModeloidModeloOfAccesorioListNewAccesorio.getAccesorioList().remove(accesorioListNewAccesorio);
                        oldModeloidModeloOfAccesorioListNewAccesorio = em.merge(oldModeloidModeloOfAccesorioListNewAccesorio);
                    }
                }
            }
            for (Componente componenteListNewComponente : componenteListNew) {
                if (!componenteListOld.contains(componenteListNewComponente)) {
                    Modelo oldModeloidModeloOfComponenteListNewComponente = componenteListNewComponente.getModeloidModelo();
                    componenteListNewComponente.setModeloidModelo(modelo);
                    componenteListNewComponente = em.merge(componenteListNewComponente);
                    if (oldModeloidModeloOfComponenteListNewComponente != null && !oldModeloidModeloOfComponenteListNewComponente.equals(modelo)) {
                        oldModeloidModeloOfComponenteListNewComponente.getComponenteList().remove(componenteListNewComponente);
                        oldModeloidModeloOfComponenteListNewComponente = em.merge(oldModeloidModeloOfComponenteListNewComponente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modelo.getIdModelo();
                if (findModelo(id) == null) {
                    throw new NonexistentEntityException("The modelo with id " + id + " no longer exists.");
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
            Modelo modelo;
            try {
                modelo = em.getReference(Modelo.class, id);
                modelo.getIdModelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modelo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Impresora> impresoraListOrphanCheck = modelo.getImpresoraList();
            for (Impresora impresoraListOrphanCheckImpresora : impresoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Modelo (" + modelo + ") cannot be destroyed since the Impresora " + impresoraListOrphanCheckImpresora + " in its impresoraList field has a non-nullable modeloidModelo field.");
            }
            List<Pc> pcListOrphanCheck = modelo.getPcList();
            for (Pc pcListOrphanCheckPc : pcListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Modelo (" + modelo + ") cannot be destroyed since the Pc " + pcListOrphanCheckPc + " in its pcList field has a non-nullable modeloidModelo field.");
            }
            List<Accesorio> accesorioListOrphanCheck = modelo.getAccesorioList();
            for (Accesorio accesorioListOrphanCheckAccesorio : accesorioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Modelo (" + modelo + ") cannot be destroyed since the Accesorio " + accesorioListOrphanCheckAccesorio + " in its accesorioList field has a non-nullable modeloidModelo field.");
            }
            List<Componente> componenteListOrphanCheck = modelo.getComponenteList();
            for (Componente componenteListOrphanCheckComponente : componenteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Modelo (" + modelo + ") cannot be destroyed since the Componente " + componenteListOrphanCheckComponente + " in its componenteList field has a non-nullable modeloidModelo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(modelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modelo> findModeloEntities() {
        return findModeloEntities(true, -1, -1);
    }

    public List<Modelo> findModeloEntities(int maxResults, int firstResult) {
        return findModeloEntities(false, maxResults, firstResult);
    }

    private List<Modelo> findModeloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modelo.class));
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

    public Modelo findModelo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getModeloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modelo> rt = cq.from(Modelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
