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
import Entidades.Pc;
import Entidades.Departamento;
import Entidades.Entidad;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class EntidadJpaController implements Serializable {

    public EntidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entidad entidad) throws PreexistingEntityException, Exception {
        if (entidad.getAccesorioList() == null) {
            entidad.setAccesorioList(new ArrayList<Accesorio>());
        }
        if (entidad.getComponenteList() == null) {
            entidad.setComponenteList(new ArrayList<Componente>());
        }
        if (entidad.getImpresoraList() == null) {
            entidad.setImpresoraList(new ArrayList<Impresora>());
        }
        if (entidad.getPcList() == null) {
            entidad.setPcList(new ArrayList<Pc>());
        }
        if (entidad.getDepartamentoList() == null) {
            entidad.setDepartamentoList(new ArrayList<Departamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Accesorio> attachedAccesorioList = new ArrayList<Accesorio>();
            for (Accesorio accesorioListAccesorioToAttach : entidad.getAccesorioList()) {
                accesorioListAccesorioToAttach = em.getReference(accesorioListAccesorioToAttach.getClass(), accesorioListAccesorioToAttach.getSnAccesorio());
                attachedAccesorioList.add(accesorioListAccesorioToAttach);
            }
            entidad.setAccesorioList(attachedAccesorioList);
            List<Componente> attachedComponenteList = new ArrayList<Componente>();
            for (Componente componenteListComponenteToAttach : entidad.getComponenteList()) {
                componenteListComponenteToAttach = em.getReference(componenteListComponenteToAttach.getClass(), componenteListComponenteToAttach.getSnComponente());
                attachedComponenteList.add(componenteListComponenteToAttach);
            }
            entidad.setComponenteList(attachedComponenteList);
            List<Impresora> attachedImpresoraList = new ArrayList<Impresora>();
            for (Impresora impresoraListImpresoraToAttach : entidad.getImpresoraList()) {
                impresoraListImpresoraToAttach = em.getReference(impresoraListImpresoraToAttach.getClass(), impresoraListImpresoraToAttach.getNoInventario());
                attachedImpresoraList.add(impresoraListImpresoraToAttach);
            }
            entidad.setImpresoraList(attachedImpresoraList);
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : entidad.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            entidad.setPcList(attachedPcList);
            List<Departamento> attachedDepartamentoList = new ArrayList<Departamento>();
            for (Departamento departamentoListDepartamentoToAttach : entidad.getDepartamentoList()) {
                departamentoListDepartamentoToAttach = em.getReference(departamentoListDepartamentoToAttach.getClass(), departamentoListDepartamentoToAttach.getIdDepartamento());
                attachedDepartamentoList.add(departamentoListDepartamentoToAttach);
            }
            entidad.setDepartamentoList(attachedDepartamentoList);
            em.persist(entidad);
            for (Accesorio accesorioListAccesorio : entidad.getAccesorioList()) {
                Entidad oldEntidadidEntidadOfAccesorioListAccesorio = accesorioListAccesorio.getEntidadidEntidad();
                accesorioListAccesorio.setEntidadidEntidad(entidad);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
                if (oldEntidadidEntidadOfAccesorioListAccesorio != null) {
                    oldEntidadidEntidadOfAccesorioListAccesorio.getAccesorioList().remove(accesorioListAccesorio);
                    oldEntidadidEntidadOfAccesorioListAccesorio = em.merge(oldEntidadidEntidadOfAccesorioListAccesorio);
                }
            }
            for (Componente componenteListComponente : entidad.getComponenteList()) {
                Entidad oldEntidadidEntidadOfComponenteListComponente = componenteListComponente.getEntidadidEntidad();
                componenteListComponente.setEntidadidEntidad(entidad);
                componenteListComponente = em.merge(componenteListComponente);
                if (oldEntidadidEntidadOfComponenteListComponente != null) {
                    oldEntidadidEntidadOfComponenteListComponente.getComponenteList().remove(componenteListComponente);
                    oldEntidadidEntidadOfComponenteListComponente = em.merge(oldEntidadidEntidadOfComponenteListComponente);
                }
            }
            for (Impresora impresoraListImpresora : entidad.getImpresoraList()) {
                Entidad oldEntidadidEntidadOfImpresoraListImpresora = impresoraListImpresora.getEntidadidEntidad();
                impresoraListImpresora.setEntidadidEntidad(entidad);
                impresoraListImpresora = em.merge(impresoraListImpresora);
                if (oldEntidadidEntidadOfImpresoraListImpresora != null) {
                    oldEntidadidEntidadOfImpresoraListImpresora.getImpresoraList().remove(impresoraListImpresora);
                    oldEntidadidEntidadOfImpresoraListImpresora = em.merge(oldEntidadidEntidadOfImpresoraListImpresora);
                }
            }
            for (Pc pcListPc : entidad.getPcList()) {
                Entidad oldEntidadidEntidadOfPcListPc = pcListPc.getEntidadidEntidad();
                pcListPc.setEntidadidEntidad(entidad);
                pcListPc = em.merge(pcListPc);
                if (oldEntidadidEntidadOfPcListPc != null) {
                    oldEntidadidEntidadOfPcListPc.getPcList().remove(pcListPc);
                    oldEntidadidEntidadOfPcListPc = em.merge(oldEntidadidEntidadOfPcListPc);
                }
            }
            for (Departamento departamentoListDepartamento : entidad.getDepartamentoList()) {
                Entidad oldEntidadidEntidadOfDepartamentoListDepartamento = departamentoListDepartamento.getEntidadidEntidad();
                departamentoListDepartamento.setEntidadidEntidad(entidad);
                departamentoListDepartamento = em.merge(departamentoListDepartamento);
                if (oldEntidadidEntidadOfDepartamentoListDepartamento != null) {
                    oldEntidadidEntidadOfDepartamentoListDepartamento.getDepartamentoList().remove(departamentoListDepartamento);
                    oldEntidadidEntidadOfDepartamentoListDepartamento = em.merge(oldEntidadidEntidadOfDepartamentoListDepartamento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEntidad(entidad.getIdEntidad()) != null) {
                throw new PreexistingEntityException("Entidad " + entidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entidad entidad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad persistentEntidad = em.find(Entidad.class, entidad.getIdEntidad());
            List<Accesorio> accesorioListOld = persistentEntidad.getAccesorioList();
            List<Accesorio> accesorioListNew = entidad.getAccesorioList();
            List<Componente> componenteListOld = persistentEntidad.getComponenteList();
            List<Componente> componenteListNew = entidad.getComponenteList();
            List<Impresora> impresoraListOld = persistentEntidad.getImpresoraList();
            List<Impresora> impresoraListNew = entidad.getImpresoraList();
            List<Pc> pcListOld = persistentEntidad.getPcList();
            List<Pc> pcListNew = entidad.getPcList();
            List<Departamento> departamentoListOld = persistentEntidad.getDepartamentoList();
            List<Departamento> departamentoListNew = entidad.getDepartamentoList();
            List<String> illegalOrphanMessages = null;
            for (Impresora impresoraListOldImpresora : impresoraListOld) {
                if (!impresoraListNew.contains(impresoraListOldImpresora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Impresora " + impresoraListOldImpresora + " since its entidadidEntidad field is not nullable.");
                }
            }
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pc " + pcListOldPc + " since its entidadidEntidad field is not nullable.");
                }
            }
            for (Departamento departamentoListOldDepartamento : departamentoListOld) {
                if (!departamentoListNew.contains(departamentoListOldDepartamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Departamento " + departamentoListOldDepartamento + " since its entidadidEntidad field is not nullable.");
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
            entidad.setAccesorioList(accesorioListNew);
            List<Componente> attachedComponenteListNew = new ArrayList<Componente>();
            for (Componente componenteListNewComponenteToAttach : componenteListNew) {
                componenteListNewComponenteToAttach = em.getReference(componenteListNewComponenteToAttach.getClass(), componenteListNewComponenteToAttach.getSnComponente());
                attachedComponenteListNew.add(componenteListNewComponenteToAttach);
            }
            componenteListNew = attachedComponenteListNew;
            entidad.setComponenteList(componenteListNew);
            List<Impresora> attachedImpresoraListNew = new ArrayList<Impresora>();
            for (Impresora impresoraListNewImpresoraToAttach : impresoraListNew) {
                impresoraListNewImpresoraToAttach = em.getReference(impresoraListNewImpresoraToAttach.getClass(), impresoraListNewImpresoraToAttach.getNoInventario());
                attachedImpresoraListNew.add(impresoraListNewImpresoraToAttach);
            }
            impresoraListNew = attachedImpresoraListNew;
            entidad.setImpresoraList(impresoraListNew);
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            entidad.setPcList(pcListNew);
            List<Departamento> attachedDepartamentoListNew = new ArrayList<Departamento>();
            for (Departamento departamentoListNewDepartamentoToAttach : departamentoListNew) {
                departamentoListNewDepartamentoToAttach = em.getReference(departamentoListNewDepartamentoToAttach.getClass(), departamentoListNewDepartamentoToAttach.getIdDepartamento());
                attachedDepartamentoListNew.add(departamentoListNewDepartamentoToAttach);
            }
            departamentoListNew = attachedDepartamentoListNew;
            entidad.setDepartamentoList(departamentoListNew);
            entidad = em.merge(entidad);
            for (Accesorio accesorioListOldAccesorio : accesorioListOld) {
                if (!accesorioListNew.contains(accesorioListOldAccesorio)) {
                    accesorioListOldAccesorio.setEntidadidEntidad(null);
                    accesorioListOldAccesorio = em.merge(accesorioListOldAccesorio);
                }
            }
            for (Accesorio accesorioListNewAccesorio : accesorioListNew) {
                if (!accesorioListOld.contains(accesorioListNewAccesorio)) {
                    Entidad oldEntidadidEntidadOfAccesorioListNewAccesorio = accesorioListNewAccesorio.getEntidadidEntidad();
                    accesorioListNewAccesorio.setEntidadidEntidad(entidad);
                    accesorioListNewAccesorio = em.merge(accesorioListNewAccesorio);
                    if (oldEntidadidEntidadOfAccesorioListNewAccesorio != null && !oldEntidadidEntidadOfAccesorioListNewAccesorio.equals(entidad)) {
                        oldEntidadidEntidadOfAccesorioListNewAccesorio.getAccesorioList().remove(accesorioListNewAccesorio);
                        oldEntidadidEntidadOfAccesorioListNewAccesorio = em.merge(oldEntidadidEntidadOfAccesorioListNewAccesorio);
                    }
                }
            }
            for (Componente componenteListOldComponente : componenteListOld) {
                if (!componenteListNew.contains(componenteListOldComponente)) {
                    componenteListOldComponente.setEntidadidEntidad(null);
                    componenteListOldComponente = em.merge(componenteListOldComponente);
                }
            }
            for (Componente componenteListNewComponente : componenteListNew) {
                if (!componenteListOld.contains(componenteListNewComponente)) {
                    Entidad oldEntidadidEntidadOfComponenteListNewComponente = componenteListNewComponente.getEntidadidEntidad();
                    componenteListNewComponente.setEntidadidEntidad(entidad);
                    componenteListNewComponente = em.merge(componenteListNewComponente);
                    if (oldEntidadidEntidadOfComponenteListNewComponente != null && !oldEntidadidEntidadOfComponenteListNewComponente.equals(entidad)) {
                        oldEntidadidEntidadOfComponenteListNewComponente.getComponenteList().remove(componenteListNewComponente);
                        oldEntidadidEntidadOfComponenteListNewComponente = em.merge(oldEntidadidEntidadOfComponenteListNewComponente);
                    }
                }
            }
            for (Impresora impresoraListNewImpresora : impresoraListNew) {
                if (!impresoraListOld.contains(impresoraListNewImpresora)) {
                    Entidad oldEntidadidEntidadOfImpresoraListNewImpresora = impresoraListNewImpresora.getEntidadidEntidad();
                    impresoraListNewImpresora.setEntidadidEntidad(entidad);
                    impresoraListNewImpresora = em.merge(impresoraListNewImpresora);
                    if (oldEntidadidEntidadOfImpresoraListNewImpresora != null && !oldEntidadidEntidadOfImpresoraListNewImpresora.equals(entidad)) {
                        oldEntidadidEntidadOfImpresoraListNewImpresora.getImpresoraList().remove(impresoraListNewImpresora);
                        oldEntidadidEntidadOfImpresoraListNewImpresora = em.merge(oldEntidadidEntidadOfImpresoraListNewImpresora);
                    }
                }
            }
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    Entidad oldEntidadidEntidadOfPcListNewPc = pcListNewPc.getEntidadidEntidad();
                    pcListNewPc.setEntidadidEntidad(entidad);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldEntidadidEntidadOfPcListNewPc != null && !oldEntidadidEntidadOfPcListNewPc.equals(entidad)) {
                        oldEntidadidEntidadOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldEntidadidEntidadOfPcListNewPc = em.merge(oldEntidadidEntidadOfPcListNewPc);
                    }
                }
            }
            for (Departamento departamentoListNewDepartamento : departamentoListNew) {
                if (!departamentoListOld.contains(departamentoListNewDepartamento)) {
                    Entidad oldEntidadidEntidadOfDepartamentoListNewDepartamento = departamentoListNewDepartamento.getEntidadidEntidad();
                    departamentoListNewDepartamento.setEntidadidEntidad(entidad);
                    departamentoListNewDepartamento = em.merge(departamentoListNewDepartamento);
                    if (oldEntidadidEntidadOfDepartamentoListNewDepartamento != null && !oldEntidadidEntidadOfDepartamentoListNewDepartamento.equals(entidad)) {
                        oldEntidadidEntidadOfDepartamentoListNewDepartamento.getDepartamentoList().remove(departamentoListNewDepartamento);
                        oldEntidadidEntidadOfDepartamentoListNewDepartamento = em.merge(oldEntidadidEntidadOfDepartamentoListNewDepartamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = entidad.getIdEntidad();
                if (findEntidad(id) == null) {
                    throw new NonexistentEntityException("The entidad with id " + id + " no longer exists.");
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
            Entidad entidad;
            try {
                entidad = em.getReference(Entidad.class, id);
                entidad.getIdEntidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entidad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Impresora> impresoraListOrphanCheck = entidad.getImpresoraList();
            for (Impresora impresoraListOrphanCheckImpresora : impresoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entidad (" + entidad + ") cannot be destroyed since the Impresora " + impresoraListOrphanCheckImpresora + " in its impresoraList field has a non-nullable entidadidEntidad field.");
            }
            List<Pc> pcListOrphanCheck = entidad.getPcList();
            for (Pc pcListOrphanCheckPc : pcListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entidad (" + entidad + ") cannot be destroyed since the Pc " + pcListOrphanCheckPc + " in its pcList field has a non-nullable entidadidEntidad field.");
            }
            List<Departamento> departamentoListOrphanCheck = entidad.getDepartamentoList();
            for (Departamento departamentoListOrphanCheckDepartamento : departamentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entidad (" + entidad + ") cannot be destroyed since the Departamento " + departamentoListOrphanCheckDepartamento + " in its departamentoList field has a non-nullable entidadidEntidad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Accesorio> accesorioList = entidad.getAccesorioList();
            for (Accesorio accesorioListAccesorio : accesorioList) {
                accesorioListAccesorio.setEntidadidEntidad(null);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
            }
            List<Componente> componenteList = entidad.getComponenteList();
            for (Componente componenteListComponente : componenteList) {
                componenteListComponente.setEntidadidEntidad(null);
                componenteListComponente = em.merge(componenteListComponente);
            }
            em.remove(entidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entidad> findEntidadEntities() {
        return findEntidadEntities(true, -1, -1);
    }

    public List<Entidad> findEntidadEntities(int maxResults, int firstResult) {
        return findEntidadEntities(false, maxResults, firstResult);
    }

    private List<Entidad> findEntidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entidad.class));
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

    public Entidad findEntidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entidad> rt = cq.from(Entidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
