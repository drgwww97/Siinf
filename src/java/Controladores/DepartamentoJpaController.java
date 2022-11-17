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
import Entidades.Entidad;
import Entidades.Accesorio;
import java.util.ArrayList;
import java.util.List;
import Entidades.Componente;
import Entidades.Area;
import Entidades.Departamento;
import Entidades.Impresora;
import Entidades.Pc;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) throws PreexistingEntityException, Exception {
        if (departamento.getAccesorioList() == null) {
            departamento.setAccesorioList(new ArrayList<Accesorio>());
        }
        if (departamento.getComponenteList() == null) {
            departamento.setComponenteList(new ArrayList<Componente>());
        }
        if (departamento.getAreaList() == null) {
            departamento.setAreaList(new ArrayList<Area>());
        }
        if (departamento.getImpresoraList() == null) {
            departamento.setImpresoraList(new ArrayList<Impresora>());
        }
        if (departamento.getPcList() == null) {
            departamento.setPcList(new ArrayList<Pc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadidEntidad = departamento.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad = em.getReference(entidadidEntidad.getClass(), entidadidEntidad.getIdEntidad());
                departamento.setEntidadidEntidad(entidadidEntidad);
            }
            List<Accesorio> attachedAccesorioList = new ArrayList<Accesorio>();
            for (Accesorio accesorioListAccesorioToAttach : departamento.getAccesorioList()) {
                accesorioListAccesorioToAttach = em.getReference(accesorioListAccesorioToAttach.getClass(), accesorioListAccesorioToAttach.getSnAccesorio());
                attachedAccesorioList.add(accesorioListAccesorioToAttach);
            }
            departamento.setAccesorioList(attachedAccesorioList);
            List<Componente> attachedComponenteList = new ArrayList<Componente>();
            for (Componente componenteListComponenteToAttach : departamento.getComponenteList()) {
                componenteListComponenteToAttach = em.getReference(componenteListComponenteToAttach.getClass(), componenteListComponenteToAttach.getSnComponente());
                attachedComponenteList.add(componenteListComponenteToAttach);
            }
            departamento.setComponenteList(attachedComponenteList);
            List<Area> attachedAreaList = new ArrayList<Area>();
            for (Area areaListAreaToAttach : departamento.getAreaList()) {
                areaListAreaToAttach = em.getReference(areaListAreaToAttach.getClass(), areaListAreaToAttach.getIdArea());
                attachedAreaList.add(areaListAreaToAttach);
            }
            departamento.setAreaList(attachedAreaList);
            List<Impresora> attachedImpresoraList = new ArrayList<Impresora>();
            for (Impresora impresoraListImpresoraToAttach : departamento.getImpresoraList()) {
                impresoraListImpresoraToAttach = em.getReference(impresoraListImpresoraToAttach.getClass(), impresoraListImpresoraToAttach.getNoInventario());
                attachedImpresoraList.add(impresoraListImpresoraToAttach);
            }
            departamento.setImpresoraList(attachedImpresoraList);
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : departamento.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            departamento.setPcList(attachedPcList);
            em.persist(departamento);
            if (entidadidEntidad != null) {
                entidadidEntidad.getDepartamentoList().add(departamento);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            for (Accesorio accesorioListAccesorio : departamento.getAccesorioList()) {
                Departamento oldDepartamentoidDepartamentoOfAccesorioListAccesorio = accesorioListAccesorio.getDepartamentoidDepartamento();
                accesorioListAccesorio.setDepartamentoidDepartamento(departamento);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
                if (oldDepartamentoidDepartamentoOfAccesorioListAccesorio != null) {
                    oldDepartamentoidDepartamentoOfAccesorioListAccesorio.getAccesorioList().remove(accesorioListAccesorio);
                    oldDepartamentoidDepartamentoOfAccesorioListAccesorio = em.merge(oldDepartamentoidDepartamentoOfAccesorioListAccesorio);
                }
            }
            for (Componente componenteListComponente : departamento.getComponenteList()) {
                Departamento oldDepartamentoidDepartamentoOfComponenteListComponente = componenteListComponente.getDepartamentoidDepartamento();
                componenteListComponente.setDepartamentoidDepartamento(departamento);
                componenteListComponente = em.merge(componenteListComponente);
                if (oldDepartamentoidDepartamentoOfComponenteListComponente != null) {
                    oldDepartamentoidDepartamentoOfComponenteListComponente.getComponenteList().remove(componenteListComponente);
                    oldDepartamentoidDepartamentoOfComponenteListComponente = em.merge(oldDepartamentoidDepartamentoOfComponenteListComponente);
                }
            }
            for (Area areaListArea : departamento.getAreaList()) {
                Departamento oldDepartamentoidDepartamentoOfAreaListArea = areaListArea.getDepartamentoidDepartamento();
                areaListArea.setDepartamentoidDepartamento(departamento);
                areaListArea = em.merge(areaListArea);
                if (oldDepartamentoidDepartamentoOfAreaListArea != null) {
                    oldDepartamentoidDepartamentoOfAreaListArea.getAreaList().remove(areaListArea);
                    oldDepartamentoidDepartamentoOfAreaListArea = em.merge(oldDepartamentoidDepartamentoOfAreaListArea);
                }
            }
            for (Impresora impresoraListImpresora : departamento.getImpresoraList()) {
                Departamento oldDepartamentoidDepartamentoOfImpresoraListImpresora = impresoraListImpresora.getDepartamentoidDepartamento();
                impresoraListImpresora.setDepartamentoidDepartamento(departamento);
                impresoraListImpresora = em.merge(impresoraListImpresora);
                if (oldDepartamentoidDepartamentoOfImpresoraListImpresora != null) {
                    oldDepartamentoidDepartamentoOfImpresoraListImpresora.getImpresoraList().remove(impresoraListImpresora);
                    oldDepartamentoidDepartamentoOfImpresoraListImpresora = em.merge(oldDepartamentoidDepartamentoOfImpresoraListImpresora);
                }
            }
            for (Pc pcListPc : departamento.getPcList()) {
                Departamento oldDepartamentoidDepartamentoOfPcListPc = pcListPc.getDepartamentoidDepartamento();
                pcListPc.setDepartamentoidDepartamento(departamento);
                pcListPc = em.merge(pcListPc);
                if (oldDepartamentoidDepartamentoOfPcListPc != null) {
                    oldDepartamentoidDepartamentoOfPcListPc.getPcList().remove(pcListPc);
                    oldDepartamentoidDepartamentoOfPcListPc = em.merge(oldDepartamentoidDepartamentoOfPcListPc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDepartamento(departamento.getIdDepartamento()) != null) {
                throw new PreexistingEntityException("Departamento " + departamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getIdDepartamento());
            Entidad entidadidEntidadOld = persistentDepartamento.getEntidadidEntidad();
            Entidad entidadidEntidadNew = departamento.getEntidadidEntidad();
            List<Accesorio> accesorioListOld = persistentDepartamento.getAccesorioList();
            List<Accesorio> accesorioListNew = departamento.getAccesorioList();
            List<Componente> componenteListOld = persistentDepartamento.getComponenteList();
            List<Componente> componenteListNew = departamento.getComponenteList();
            List<Area> areaListOld = persistentDepartamento.getAreaList();
            List<Area> areaListNew = departamento.getAreaList();
            List<Impresora> impresoraListOld = persistentDepartamento.getImpresoraList();
            List<Impresora> impresoraListNew = departamento.getImpresoraList();
            List<Pc> pcListOld = persistentDepartamento.getPcList();
            List<Pc> pcListNew = departamento.getPcList();
            List<String> illegalOrphanMessages = null;
            for (Area areaListOldArea : areaListOld) {
                if (!areaListNew.contains(areaListOldArea)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Area " + areaListOldArea + " since its departamentoidDepartamento field is not nullable.");
                }
            }
            for (Impresora impresoraListOldImpresora : impresoraListOld) {
                if (!impresoraListNew.contains(impresoraListOldImpresora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Impresora " + impresoraListOldImpresora + " since its departamentoidDepartamento field is not nullable.");
                }
            }
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pc " + pcListOldPc + " since its departamentoidDepartamento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadidEntidadNew != null) {
                entidadidEntidadNew = em.getReference(entidadidEntidadNew.getClass(), entidadidEntidadNew.getIdEntidad());
                departamento.setEntidadidEntidad(entidadidEntidadNew);
            }
            List<Accesorio> attachedAccesorioListNew = new ArrayList<Accesorio>();
            for (Accesorio accesorioListNewAccesorioToAttach : accesorioListNew) {
                accesorioListNewAccesorioToAttach = em.getReference(accesorioListNewAccesorioToAttach.getClass(), accesorioListNewAccesorioToAttach.getSnAccesorio());
                attachedAccesorioListNew.add(accesorioListNewAccesorioToAttach);
            }
            accesorioListNew = attachedAccesorioListNew;
            departamento.setAccesorioList(accesorioListNew);
            List<Componente> attachedComponenteListNew = new ArrayList<Componente>();
            for (Componente componenteListNewComponenteToAttach : componenteListNew) {
                componenteListNewComponenteToAttach = em.getReference(componenteListNewComponenteToAttach.getClass(), componenteListNewComponenteToAttach.getSnComponente());
                attachedComponenteListNew.add(componenteListNewComponenteToAttach);
            }
            componenteListNew = attachedComponenteListNew;
            departamento.setComponenteList(componenteListNew);
            List<Area> attachedAreaListNew = new ArrayList<Area>();
            for (Area areaListNewAreaToAttach : areaListNew) {
                areaListNewAreaToAttach = em.getReference(areaListNewAreaToAttach.getClass(), areaListNewAreaToAttach.getIdArea());
                attachedAreaListNew.add(areaListNewAreaToAttach);
            }
            areaListNew = attachedAreaListNew;
            departamento.setAreaList(areaListNew);
            List<Impresora> attachedImpresoraListNew = new ArrayList<Impresora>();
            for (Impresora impresoraListNewImpresoraToAttach : impresoraListNew) {
                impresoraListNewImpresoraToAttach = em.getReference(impresoraListNewImpresoraToAttach.getClass(), impresoraListNewImpresoraToAttach.getNoInventario());
                attachedImpresoraListNew.add(impresoraListNewImpresoraToAttach);
            }
            impresoraListNew = attachedImpresoraListNew;
            departamento.setImpresoraList(impresoraListNew);
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            departamento.setPcList(pcListNew);
            departamento = em.merge(departamento);
            if (entidadidEntidadOld != null && !entidadidEntidadOld.equals(entidadidEntidadNew)) {
                entidadidEntidadOld.getDepartamentoList().remove(departamento);
                entidadidEntidadOld = em.merge(entidadidEntidadOld);
            }
            if (entidadidEntidadNew != null && !entidadidEntidadNew.equals(entidadidEntidadOld)) {
                entidadidEntidadNew.getDepartamentoList().add(departamento);
                entidadidEntidadNew = em.merge(entidadidEntidadNew);
            }
            for (Accesorio accesorioListOldAccesorio : accesorioListOld) {
                if (!accesorioListNew.contains(accesorioListOldAccesorio)) {
                    accesorioListOldAccesorio.setDepartamentoidDepartamento(null);
                    accesorioListOldAccesorio = em.merge(accesorioListOldAccesorio);
                }
            }
            for (Accesorio accesorioListNewAccesorio : accesorioListNew) {
                if (!accesorioListOld.contains(accesorioListNewAccesorio)) {
                    Departamento oldDepartamentoidDepartamentoOfAccesorioListNewAccesorio = accesorioListNewAccesorio.getDepartamentoidDepartamento();
                    accesorioListNewAccesorio.setDepartamentoidDepartamento(departamento);
                    accesorioListNewAccesorio = em.merge(accesorioListNewAccesorio);
                    if (oldDepartamentoidDepartamentoOfAccesorioListNewAccesorio != null && !oldDepartamentoidDepartamentoOfAccesorioListNewAccesorio.equals(departamento)) {
                        oldDepartamentoidDepartamentoOfAccesorioListNewAccesorio.getAccesorioList().remove(accesorioListNewAccesorio);
                        oldDepartamentoidDepartamentoOfAccesorioListNewAccesorio = em.merge(oldDepartamentoidDepartamentoOfAccesorioListNewAccesorio);
                    }
                }
            }
            for (Componente componenteListOldComponente : componenteListOld) {
                if (!componenteListNew.contains(componenteListOldComponente)) {
                    componenteListOldComponente.setDepartamentoidDepartamento(null);
                    componenteListOldComponente = em.merge(componenteListOldComponente);
                }
            }
            for (Componente componenteListNewComponente : componenteListNew) {
                if (!componenteListOld.contains(componenteListNewComponente)) {
                    Departamento oldDepartamentoidDepartamentoOfComponenteListNewComponente = componenteListNewComponente.getDepartamentoidDepartamento();
                    componenteListNewComponente.setDepartamentoidDepartamento(departamento);
                    componenteListNewComponente = em.merge(componenteListNewComponente);
                    if (oldDepartamentoidDepartamentoOfComponenteListNewComponente != null && !oldDepartamentoidDepartamentoOfComponenteListNewComponente.equals(departamento)) {
                        oldDepartamentoidDepartamentoOfComponenteListNewComponente.getComponenteList().remove(componenteListNewComponente);
                        oldDepartamentoidDepartamentoOfComponenteListNewComponente = em.merge(oldDepartamentoidDepartamentoOfComponenteListNewComponente);
                    }
                }
            }
            for (Area areaListNewArea : areaListNew) {
                if (!areaListOld.contains(areaListNewArea)) {
                    Departamento oldDepartamentoidDepartamentoOfAreaListNewArea = areaListNewArea.getDepartamentoidDepartamento();
                    areaListNewArea.setDepartamentoidDepartamento(departamento);
                    areaListNewArea = em.merge(areaListNewArea);
                    if (oldDepartamentoidDepartamentoOfAreaListNewArea != null && !oldDepartamentoidDepartamentoOfAreaListNewArea.equals(departamento)) {
                        oldDepartamentoidDepartamentoOfAreaListNewArea.getAreaList().remove(areaListNewArea);
                        oldDepartamentoidDepartamentoOfAreaListNewArea = em.merge(oldDepartamentoidDepartamentoOfAreaListNewArea);
                    }
                }
            }
            for (Impresora impresoraListNewImpresora : impresoraListNew) {
                if (!impresoraListOld.contains(impresoraListNewImpresora)) {
                    Departamento oldDepartamentoidDepartamentoOfImpresoraListNewImpresora = impresoraListNewImpresora.getDepartamentoidDepartamento();
                    impresoraListNewImpresora.setDepartamentoidDepartamento(departamento);
                    impresoraListNewImpresora = em.merge(impresoraListNewImpresora);
                    if (oldDepartamentoidDepartamentoOfImpresoraListNewImpresora != null && !oldDepartamentoidDepartamentoOfImpresoraListNewImpresora.equals(departamento)) {
                        oldDepartamentoidDepartamentoOfImpresoraListNewImpresora.getImpresoraList().remove(impresoraListNewImpresora);
                        oldDepartamentoidDepartamentoOfImpresoraListNewImpresora = em.merge(oldDepartamentoidDepartamentoOfImpresoraListNewImpresora);
                    }
                }
            }
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    Departamento oldDepartamentoidDepartamentoOfPcListNewPc = pcListNewPc.getDepartamentoidDepartamento();
                    pcListNewPc.setDepartamentoidDepartamento(departamento);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldDepartamentoidDepartamentoOfPcListNewPc != null && !oldDepartamentoidDepartamentoOfPcListNewPc.equals(departamento)) {
                        oldDepartamentoidDepartamentoOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldDepartamentoidDepartamentoOfPcListNewPc = em.merge(oldDepartamentoidDepartamentoOfPcListNewPc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamento.getIdDepartamento();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getIdDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Area> areaListOrphanCheck = departamento.getAreaList();
            for (Area areaListOrphanCheckArea : areaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Area " + areaListOrphanCheckArea + " in its areaList field has a non-nullable departamentoidDepartamento field.");
            }
            List<Impresora> impresoraListOrphanCheck = departamento.getImpresoraList();
            for (Impresora impresoraListOrphanCheckImpresora : impresoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Impresora " + impresoraListOrphanCheckImpresora + " in its impresoraList field has a non-nullable departamentoidDepartamento field.");
            }
            List<Pc> pcListOrphanCheck = departamento.getPcList();
            for (Pc pcListOrphanCheckPc : pcListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Pc " + pcListOrphanCheckPc + " in its pcList field has a non-nullable departamentoidDepartamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Entidad entidadidEntidad = departamento.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad.getDepartamentoList().remove(departamento);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            List<Accesorio> accesorioList = departamento.getAccesorioList();
            for (Accesorio accesorioListAccesorio : accesorioList) {
                accesorioListAccesorio.setDepartamentoidDepartamento(null);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
            }
            List<Componente> componenteList = departamento.getComponenteList();
            for (Componente componenteListComponente : componenteList) {
                componenteListComponente.setDepartamentoidDepartamento(null);
                componenteListComponente = em.merge(componenteListComponente);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
