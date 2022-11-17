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
import Entidades.Departamento;
import Entidades.Accesorio;
import Entidades.Area;
import java.util.ArrayList;
import java.util.List;
import Entidades.Componente;
import Entidades.Impresora;
import Entidades.Pc;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class AreaJpaController implements Serializable {

    public AreaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Area area) throws PreexistingEntityException, Exception {
        if (area.getAccesorioList() == null) {
            area.setAccesorioList(new ArrayList<Accesorio>());
        }
        if (area.getComponenteList() == null) {
            area.setComponenteList(new ArrayList<Componente>());
        }
        if (area.getImpresoraList() == null) {
            area.setImpresoraList(new ArrayList<Impresora>());
        }
        if (area.getPcList() == null) {
            area.setPcList(new ArrayList<Pc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento departamentoidDepartamento = area.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento = em.getReference(departamentoidDepartamento.getClass(), departamentoidDepartamento.getIdDepartamento());
                area.setDepartamentoidDepartamento(departamentoidDepartamento);
            }
            List<Accesorio> attachedAccesorioList = new ArrayList<Accesorio>();
            for (Accesorio accesorioListAccesorioToAttach : area.getAccesorioList()) {
                accesorioListAccesorioToAttach = em.getReference(accesorioListAccesorioToAttach.getClass(), accesorioListAccesorioToAttach.getSnAccesorio());
                attachedAccesorioList.add(accesorioListAccesorioToAttach);
            }
            area.setAccesorioList(attachedAccesorioList);
            List<Componente> attachedComponenteList = new ArrayList<Componente>();
            for (Componente componenteListComponenteToAttach : area.getComponenteList()) {
                componenteListComponenteToAttach = em.getReference(componenteListComponenteToAttach.getClass(), componenteListComponenteToAttach.getSnComponente());
                attachedComponenteList.add(componenteListComponenteToAttach);
            }
            area.setComponenteList(attachedComponenteList);
            List<Impresora> attachedImpresoraList = new ArrayList<Impresora>();
            for (Impresora impresoraListImpresoraToAttach : area.getImpresoraList()) {
                impresoraListImpresoraToAttach = em.getReference(impresoraListImpresoraToAttach.getClass(), impresoraListImpresoraToAttach.getNoInventario());
                attachedImpresoraList.add(impresoraListImpresoraToAttach);
            }
            area.setImpresoraList(attachedImpresoraList);
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : area.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            area.setPcList(attachedPcList);
            em.persist(area);
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getAreaList().add(area);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            for (Accesorio accesorioListAccesorio : area.getAccesorioList()) {
                Area oldAreaidAreaOfAccesorioListAccesorio = accesorioListAccesorio.getAreaidArea();
                accesorioListAccesorio.setAreaidArea(area);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
                if (oldAreaidAreaOfAccesorioListAccesorio != null) {
                    oldAreaidAreaOfAccesorioListAccesorio.getAccesorioList().remove(accesorioListAccesorio);
                    oldAreaidAreaOfAccesorioListAccesorio = em.merge(oldAreaidAreaOfAccesorioListAccesorio);
                }
            }
            for (Componente componenteListComponente : area.getComponenteList()) {
                Area oldAreaidAreaOfComponenteListComponente = componenteListComponente.getAreaidArea();
                componenteListComponente.setAreaidArea(area);
                componenteListComponente = em.merge(componenteListComponente);
                if (oldAreaidAreaOfComponenteListComponente != null) {
                    oldAreaidAreaOfComponenteListComponente.getComponenteList().remove(componenteListComponente);
                    oldAreaidAreaOfComponenteListComponente = em.merge(oldAreaidAreaOfComponenteListComponente);
                }
            }
            for (Impresora impresoraListImpresora : area.getImpresoraList()) {
                Area oldAreaidAreaOfImpresoraListImpresora = impresoraListImpresora.getAreaidArea();
                impresoraListImpresora.setAreaidArea(area);
                impresoraListImpresora = em.merge(impresoraListImpresora);
                if (oldAreaidAreaOfImpresoraListImpresora != null) {
                    oldAreaidAreaOfImpresoraListImpresora.getImpresoraList().remove(impresoraListImpresora);
                    oldAreaidAreaOfImpresoraListImpresora = em.merge(oldAreaidAreaOfImpresoraListImpresora);
                }
            }
            for (Pc pcListPc : area.getPcList()) {
                Area oldAreaidAreaOfPcListPc = pcListPc.getAreaidArea();
                pcListPc.setAreaidArea(area);
                pcListPc = em.merge(pcListPc);
                if (oldAreaidAreaOfPcListPc != null) {
                    oldAreaidAreaOfPcListPc.getPcList().remove(pcListPc);
                    oldAreaidAreaOfPcListPc = em.merge(oldAreaidAreaOfPcListPc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArea(area.getIdArea()) != null) {
                throw new PreexistingEntityException("Area " + area + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Area area) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area persistentArea = em.find(Area.class, area.getIdArea());
            Departamento departamentoidDepartamentoOld = persistentArea.getDepartamentoidDepartamento();
            Departamento departamentoidDepartamentoNew = area.getDepartamentoidDepartamento();
            List<Accesorio> accesorioListOld = persistentArea.getAccesorioList();
            List<Accesorio> accesorioListNew = area.getAccesorioList();
            List<Componente> componenteListOld = persistentArea.getComponenteList();
            List<Componente> componenteListNew = area.getComponenteList();
            List<Impresora> impresoraListOld = persistentArea.getImpresoraList();
            List<Impresora> impresoraListNew = area.getImpresoraList();
            List<Pc> pcListOld = persistentArea.getPcList();
            List<Pc> pcListNew = area.getPcList();
            List<String> illegalOrphanMessages = null;
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pc " + pcListOldPc + " since its areaidArea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (departamentoidDepartamentoNew != null) {
                departamentoidDepartamentoNew = em.getReference(departamentoidDepartamentoNew.getClass(), departamentoidDepartamentoNew.getIdDepartamento());
                area.setDepartamentoidDepartamento(departamentoidDepartamentoNew);
            }
            List<Accesorio> attachedAccesorioListNew = new ArrayList<Accesorio>();
            for (Accesorio accesorioListNewAccesorioToAttach : accesorioListNew) {
                accesorioListNewAccesorioToAttach = em.getReference(accesorioListNewAccesorioToAttach.getClass(), accesorioListNewAccesorioToAttach.getSnAccesorio());
                attachedAccesorioListNew.add(accesorioListNewAccesorioToAttach);
            }
            accesorioListNew = attachedAccesorioListNew;
            area.setAccesorioList(accesorioListNew);
            List<Componente> attachedComponenteListNew = new ArrayList<Componente>();
            for (Componente componenteListNewComponenteToAttach : componenteListNew) {
                componenteListNewComponenteToAttach = em.getReference(componenteListNewComponenteToAttach.getClass(), componenteListNewComponenteToAttach.getSnComponente());
                attachedComponenteListNew.add(componenteListNewComponenteToAttach);
            }
            componenteListNew = attachedComponenteListNew;
            area.setComponenteList(componenteListNew);
            List<Impresora> attachedImpresoraListNew = new ArrayList<Impresora>();
            for (Impresora impresoraListNewImpresoraToAttach : impresoraListNew) {
                impresoraListNewImpresoraToAttach = em.getReference(impresoraListNewImpresoraToAttach.getClass(), impresoraListNewImpresoraToAttach.getNoInventario());
                attachedImpresoraListNew.add(impresoraListNewImpresoraToAttach);
            }
            impresoraListNew = attachedImpresoraListNew;
            area.setImpresoraList(impresoraListNew);
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            area.setPcList(pcListNew);
            area = em.merge(area);
            if (departamentoidDepartamentoOld != null && !departamentoidDepartamentoOld.equals(departamentoidDepartamentoNew)) {
                departamentoidDepartamentoOld.getAreaList().remove(area);
                departamentoidDepartamentoOld = em.merge(departamentoidDepartamentoOld);
            }
            if (departamentoidDepartamentoNew != null && !departamentoidDepartamentoNew.equals(departamentoidDepartamentoOld)) {
                departamentoidDepartamentoNew.getAreaList().add(area);
                departamentoidDepartamentoNew = em.merge(departamentoidDepartamentoNew);
            }
            for (Accesorio accesorioListOldAccesorio : accesorioListOld) {
                if (!accesorioListNew.contains(accesorioListOldAccesorio)) {
                    accesorioListOldAccesorio.setAreaidArea(null);
                    accesorioListOldAccesorio = em.merge(accesorioListOldAccesorio);
                }
            }
            for (Accesorio accesorioListNewAccesorio : accesorioListNew) {
                if (!accesorioListOld.contains(accesorioListNewAccesorio)) {
                    Area oldAreaidAreaOfAccesorioListNewAccesorio = accesorioListNewAccesorio.getAreaidArea();
                    accesorioListNewAccesorio.setAreaidArea(area);
                    accesorioListNewAccesorio = em.merge(accesorioListNewAccesorio);
                    if (oldAreaidAreaOfAccesorioListNewAccesorio != null && !oldAreaidAreaOfAccesorioListNewAccesorio.equals(area)) {
                        oldAreaidAreaOfAccesorioListNewAccesorio.getAccesorioList().remove(accesorioListNewAccesorio);
                        oldAreaidAreaOfAccesorioListNewAccesorio = em.merge(oldAreaidAreaOfAccesorioListNewAccesorio);
                    }
                }
            }
            for (Componente componenteListOldComponente : componenteListOld) {
                if (!componenteListNew.contains(componenteListOldComponente)) {
                    componenteListOldComponente.setAreaidArea(null);
                    componenteListOldComponente = em.merge(componenteListOldComponente);
                }
            }
            for (Componente componenteListNewComponente : componenteListNew) {
                if (!componenteListOld.contains(componenteListNewComponente)) {
                    Area oldAreaidAreaOfComponenteListNewComponente = componenteListNewComponente.getAreaidArea();
                    componenteListNewComponente.setAreaidArea(area);
                    componenteListNewComponente = em.merge(componenteListNewComponente);
                    if (oldAreaidAreaOfComponenteListNewComponente != null && !oldAreaidAreaOfComponenteListNewComponente.equals(area)) {
                        oldAreaidAreaOfComponenteListNewComponente.getComponenteList().remove(componenteListNewComponente);
                        oldAreaidAreaOfComponenteListNewComponente = em.merge(oldAreaidAreaOfComponenteListNewComponente);
                    }
                }
            }
            for (Impresora impresoraListOldImpresora : impresoraListOld) {
                if (!impresoraListNew.contains(impresoraListOldImpresora)) {
                    impresoraListOldImpresora.setAreaidArea(null);
                    impresoraListOldImpresora = em.merge(impresoraListOldImpresora);
                }
            }
            for (Impresora impresoraListNewImpresora : impresoraListNew) {
                if (!impresoraListOld.contains(impresoraListNewImpresora)) {
                    Area oldAreaidAreaOfImpresoraListNewImpresora = impresoraListNewImpresora.getAreaidArea();
                    impresoraListNewImpresora.setAreaidArea(area);
                    impresoraListNewImpresora = em.merge(impresoraListNewImpresora);
                    if (oldAreaidAreaOfImpresoraListNewImpresora != null && !oldAreaidAreaOfImpresoraListNewImpresora.equals(area)) {
                        oldAreaidAreaOfImpresoraListNewImpresora.getImpresoraList().remove(impresoraListNewImpresora);
                        oldAreaidAreaOfImpresoraListNewImpresora = em.merge(oldAreaidAreaOfImpresoraListNewImpresora);
                    }
                }
            }
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    Area oldAreaidAreaOfPcListNewPc = pcListNewPc.getAreaidArea();
                    pcListNewPc.setAreaidArea(area);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldAreaidAreaOfPcListNewPc != null && !oldAreaidAreaOfPcListNewPc.equals(area)) {
                        oldAreaidAreaOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldAreaidAreaOfPcListNewPc = em.merge(oldAreaidAreaOfPcListNewPc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = area.getIdArea();
                if (findArea(id) == null) {
                    throw new NonexistentEntityException("The area with id " + id + " no longer exists.");
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
            Area area;
            try {
                area = em.getReference(Area.class, id);
                area.getIdArea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The area with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pc> pcListOrphanCheck = area.getPcList();
            for (Pc pcListOrphanCheckPc : pcListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the Pc " + pcListOrphanCheckPc + " in its pcList field has a non-nullable areaidArea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento departamentoidDepartamento = area.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getAreaList().remove(area);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            List<Accesorio> accesorioList = area.getAccesorioList();
            for (Accesorio accesorioListAccesorio : accesorioList) {
                accesorioListAccesorio.setAreaidArea(null);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
            }
            List<Componente> componenteList = area.getComponenteList();
            for (Componente componenteListComponente : componenteList) {
                componenteListComponente.setAreaidArea(null);
                componenteListComponente = em.merge(componenteListComponente);
            }
            List<Impresora> impresoraList = area.getImpresoraList();
            for (Impresora impresoraListImpresora : impresoraList) {
                impresoraListImpresora.setAreaidArea(null);
                impresoraListImpresora = em.merge(impresoraListImpresora);
            }
            em.remove(area);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Area> findAreaEntities() {
        return findAreaEntities(true, -1, -1);
    }

    public List<Area> findAreaEntities(int maxResults, int firstResult) {
        return findAreaEntities(false, maxResults, firstResult);
    }

    private List<Area> findAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Area.class));
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

    public Area findArea(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Area.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Area> rt = cq.from(Area.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
