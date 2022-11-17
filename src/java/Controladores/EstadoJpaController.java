/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Accesorio;
import java.util.ArrayList;
import java.util.List;
import Entidades.Reparacion;
import Entidades.Componente;
import Entidades.Estado;
import Entidades.Impresora;
import Entidades.Pc;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) {
        if (estado.getAccesorioList() == null) {
            estado.setAccesorioList(new ArrayList<Accesorio>());
        }
        if (estado.getReparacionList() == null) {
            estado.setReparacionList(new ArrayList<Reparacion>());
        }
        if (estado.getComponenteList() == null) {
            estado.setComponenteList(new ArrayList<Componente>());
        }
        if (estado.getImpresoraList() == null) {
            estado.setImpresoraList(new ArrayList<Impresora>());
        }
        if (estado.getPcList() == null) {
            estado.setPcList(new ArrayList<Pc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Accesorio> attachedAccesorioList = new ArrayList<Accesorio>();
            for (Accesorio accesorioListAccesorioToAttach : estado.getAccesorioList()) {
                accesorioListAccesorioToAttach = em.getReference(accesorioListAccesorioToAttach.getClass(), accesorioListAccesorioToAttach.getSnAccesorio());
                attachedAccesorioList.add(accesorioListAccesorioToAttach);
            }
            estado.setAccesorioList(attachedAccesorioList);
            List<Reparacion> attachedReparacionList = new ArrayList<Reparacion>();
            for (Reparacion reparacionListReparacionToAttach : estado.getReparacionList()) {
                reparacionListReparacionToAttach = em.getReference(reparacionListReparacionToAttach.getClass(), reparacionListReparacionToAttach.getTReparacionidReparacio());
                attachedReparacionList.add(reparacionListReparacionToAttach);
            }
            estado.setReparacionList(attachedReparacionList);
            List<Componente> attachedComponenteList = new ArrayList<Componente>();
            for (Componente componenteListComponenteToAttach : estado.getComponenteList()) {
                componenteListComponenteToAttach = em.getReference(componenteListComponenteToAttach.getClass(), componenteListComponenteToAttach.getSnComponente());
                attachedComponenteList.add(componenteListComponenteToAttach);
            }
            estado.setComponenteList(attachedComponenteList);
            List<Impresora> attachedImpresoraList = new ArrayList<Impresora>();
            for (Impresora impresoraListImpresoraToAttach : estado.getImpresoraList()) {
                impresoraListImpresoraToAttach = em.getReference(impresoraListImpresoraToAttach.getClass(), impresoraListImpresoraToAttach.getNoInventario());
                attachedImpresoraList.add(impresoraListImpresoraToAttach);
            }
            estado.setImpresoraList(attachedImpresoraList);
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : estado.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            estado.setPcList(attachedPcList);
            em.persist(estado);
            for (Accesorio accesorioListAccesorio : estado.getAccesorioList()) {
                Estado oldEstadoidEstadoOfAccesorioListAccesorio = accesorioListAccesorio.getEstadoidEstado();
                accesorioListAccesorio.setEstadoidEstado(estado);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
                if (oldEstadoidEstadoOfAccesorioListAccesorio != null) {
                    oldEstadoidEstadoOfAccesorioListAccesorio.getAccesorioList().remove(accesorioListAccesorio);
                    oldEstadoidEstadoOfAccesorioListAccesorio = em.merge(oldEstadoidEstadoOfAccesorioListAccesorio);
                }
            }
            for (Reparacion reparacionListReparacion : estado.getReparacionList()) {
                Estado oldEstadoidEstadoOfReparacionListReparacion = reparacionListReparacion.getEstadoidEstado();
                reparacionListReparacion.setEstadoidEstado(estado);
                reparacionListReparacion = em.merge(reparacionListReparacion);
                if (oldEstadoidEstadoOfReparacionListReparacion != null) {
                    oldEstadoidEstadoOfReparacionListReparacion.getReparacionList().remove(reparacionListReparacion);
                    oldEstadoidEstadoOfReparacionListReparacion = em.merge(oldEstadoidEstadoOfReparacionListReparacion);
                }
            }
            for (Componente componenteListComponente : estado.getComponenteList()) {
                Estado oldEstadoidEstadoOfComponenteListComponente = componenteListComponente.getEstadoidEstado();
                componenteListComponente.setEstadoidEstado(estado);
                componenteListComponente = em.merge(componenteListComponente);
                if (oldEstadoidEstadoOfComponenteListComponente != null) {
                    oldEstadoidEstadoOfComponenteListComponente.getComponenteList().remove(componenteListComponente);
                    oldEstadoidEstadoOfComponenteListComponente = em.merge(oldEstadoidEstadoOfComponenteListComponente);
                }
            }
            for (Impresora impresoraListImpresora : estado.getImpresoraList()) {
                Estado oldEstadoidEstadoOfImpresoraListImpresora = impresoraListImpresora.getEstadoidEstado();
                impresoraListImpresora.setEstadoidEstado(estado);
                impresoraListImpresora = em.merge(impresoraListImpresora);
                if (oldEstadoidEstadoOfImpresoraListImpresora != null) {
                    oldEstadoidEstadoOfImpresoraListImpresora.getImpresoraList().remove(impresoraListImpresora);
                    oldEstadoidEstadoOfImpresoraListImpresora = em.merge(oldEstadoidEstadoOfImpresoraListImpresora);
                }
            }
            for (Pc pcListPc : estado.getPcList()) {
                Estado oldEstadoidEstadoOfPcListPc = pcListPc.getEstadoidEstado();
                pcListPc.setEstadoidEstado(estado);
                pcListPc = em.merge(pcListPc);
                if (oldEstadoidEstadoOfPcListPc != null) {
                    oldEstadoidEstadoOfPcListPc.getPcList().remove(pcListPc);
                    oldEstadoidEstadoOfPcListPc = em.merge(oldEstadoidEstadoOfPcListPc);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getIdEstado());
            List<Accesorio> accesorioListOld = persistentEstado.getAccesorioList();
            List<Accesorio> accesorioListNew = estado.getAccesorioList();
            List<Reparacion> reparacionListOld = persistentEstado.getReparacionList();
            List<Reparacion> reparacionListNew = estado.getReparacionList();
            List<Componente> componenteListOld = persistentEstado.getComponenteList();
            List<Componente> componenteListNew = estado.getComponenteList();
            List<Impresora> impresoraListOld = persistentEstado.getImpresoraList();
            List<Impresora> impresoraListNew = estado.getImpresoraList();
            List<Pc> pcListOld = persistentEstado.getPcList();
            List<Pc> pcListNew = estado.getPcList();
            List<String> illegalOrphanMessages = null;
            for (Accesorio accesorioListOldAccesorio : accesorioListOld) {
                if (!accesorioListNew.contains(accesorioListOldAccesorio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Accesorio " + accesorioListOldAccesorio + " since its estadoidEstado field is not nullable.");
                }
            }
            for (Reparacion reparacionListOldReparacion : reparacionListOld) {
                if (!reparacionListNew.contains(reparacionListOldReparacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reparacion " + reparacionListOldReparacion + " since its estadoidEstado field is not nullable.");
                }
            }
            for (Componente componenteListOldComponente : componenteListOld) {
                if (!componenteListNew.contains(componenteListOldComponente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Componente " + componenteListOldComponente + " since its estadoidEstado field is not nullable.");
                }
            }
            for (Impresora impresoraListOldImpresora : impresoraListOld) {
                if (!impresoraListNew.contains(impresoraListOldImpresora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Impresora " + impresoraListOldImpresora + " since its estadoidEstado field is not nullable.");
                }
            }
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pc " + pcListOldPc + " since its estadoidEstado field is not nullable.");
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
            estado.setAccesorioList(accesorioListNew);
            List<Reparacion> attachedReparacionListNew = new ArrayList<Reparacion>();
            for (Reparacion reparacionListNewReparacionToAttach : reparacionListNew) {
                reparacionListNewReparacionToAttach = em.getReference(reparacionListNewReparacionToAttach.getClass(), reparacionListNewReparacionToAttach.getTReparacionidReparacio());
                attachedReparacionListNew.add(reparacionListNewReparacionToAttach);
            }
            reparacionListNew = attachedReparacionListNew;
            estado.setReparacionList(reparacionListNew);
            List<Componente> attachedComponenteListNew = new ArrayList<Componente>();
            for (Componente componenteListNewComponenteToAttach : componenteListNew) {
                componenteListNewComponenteToAttach = em.getReference(componenteListNewComponenteToAttach.getClass(), componenteListNewComponenteToAttach.getSnComponente());
                attachedComponenteListNew.add(componenteListNewComponenteToAttach);
            }
            componenteListNew = attachedComponenteListNew;
            estado.setComponenteList(componenteListNew);
            List<Impresora> attachedImpresoraListNew = new ArrayList<Impresora>();
            for (Impresora impresoraListNewImpresoraToAttach : impresoraListNew) {
                impresoraListNewImpresoraToAttach = em.getReference(impresoraListNewImpresoraToAttach.getClass(), impresoraListNewImpresoraToAttach.getNoInventario());
                attachedImpresoraListNew.add(impresoraListNewImpresoraToAttach);
            }
            impresoraListNew = attachedImpresoraListNew;
            estado.setImpresoraList(impresoraListNew);
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            estado.setPcList(pcListNew);
            estado = em.merge(estado);
            for (Accesorio accesorioListNewAccesorio : accesorioListNew) {
                if (!accesorioListOld.contains(accesorioListNewAccesorio)) {
                    Estado oldEstadoidEstadoOfAccesorioListNewAccesorio = accesorioListNewAccesorio.getEstadoidEstado();
                    accesorioListNewAccesorio.setEstadoidEstado(estado);
                    accesorioListNewAccesorio = em.merge(accesorioListNewAccesorio);
                    if (oldEstadoidEstadoOfAccesorioListNewAccesorio != null && !oldEstadoidEstadoOfAccesorioListNewAccesorio.equals(estado)) {
                        oldEstadoidEstadoOfAccesorioListNewAccesorio.getAccesorioList().remove(accesorioListNewAccesorio);
                        oldEstadoidEstadoOfAccesorioListNewAccesorio = em.merge(oldEstadoidEstadoOfAccesorioListNewAccesorio);
                    }
                }
            }
            for (Reparacion reparacionListNewReparacion : reparacionListNew) {
                if (!reparacionListOld.contains(reparacionListNewReparacion)) {
                    Estado oldEstadoidEstadoOfReparacionListNewReparacion = reparacionListNewReparacion.getEstadoidEstado();
                    reparacionListNewReparacion.setEstadoidEstado(estado);
                    reparacionListNewReparacion = em.merge(reparacionListNewReparacion);
                    if (oldEstadoidEstadoOfReparacionListNewReparacion != null && !oldEstadoidEstadoOfReparacionListNewReparacion.equals(estado)) {
                        oldEstadoidEstadoOfReparacionListNewReparacion.getReparacionList().remove(reparacionListNewReparacion);
                        oldEstadoidEstadoOfReparacionListNewReparacion = em.merge(oldEstadoidEstadoOfReparacionListNewReparacion);
                    }
                }
            }
            for (Componente componenteListNewComponente : componenteListNew) {
                if (!componenteListOld.contains(componenteListNewComponente)) {
                    Estado oldEstadoidEstadoOfComponenteListNewComponente = componenteListNewComponente.getEstadoidEstado();
                    componenteListNewComponente.setEstadoidEstado(estado);
                    componenteListNewComponente = em.merge(componenteListNewComponente);
                    if (oldEstadoidEstadoOfComponenteListNewComponente != null && !oldEstadoidEstadoOfComponenteListNewComponente.equals(estado)) {
                        oldEstadoidEstadoOfComponenteListNewComponente.getComponenteList().remove(componenteListNewComponente);
                        oldEstadoidEstadoOfComponenteListNewComponente = em.merge(oldEstadoidEstadoOfComponenteListNewComponente);
                    }
                }
            }
            for (Impresora impresoraListNewImpresora : impresoraListNew) {
                if (!impresoraListOld.contains(impresoraListNewImpresora)) {
                    Estado oldEstadoidEstadoOfImpresoraListNewImpresora = impresoraListNewImpresora.getEstadoidEstado();
                    impresoraListNewImpresora.setEstadoidEstado(estado);
                    impresoraListNewImpresora = em.merge(impresoraListNewImpresora);
                    if (oldEstadoidEstadoOfImpresoraListNewImpresora != null && !oldEstadoidEstadoOfImpresoraListNewImpresora.equals(estado)) {
                        oldEstadoidEstadoOfImpresoraListNewImpresora.getImpresoraList().remove(impresoraListNewImpresora);
                        oldEstadoidEstadoOfImpresoraListNewImpresora = em.merge(oldEstadoidEstadoOfImpresoraListNewImpresora);
                    }
                }
            }
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    Estado oldEstadoidEstadoOfPcListNewPc = pcListNewPc.getEstadoidEstado();
                    pcListNewPc.setEstadoidEstado(estado);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldEstadoidEstadoOfPcListNewPc != null && !oldEstadoidEstadoOfPcListNewPc.equals(estado)) {
                        oldEstadoidEstadoOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldEstadoidEstadoOfPcListNewPc = em.merge(oldEstadoidEstadoOfPcListNewPc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getIdEstado();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getIdEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Accesorio> accesorioListOrphanCheck = estado.getAccesorioList();
            for (Accesorio accesorioListOrphanCheckAccesorio : accesorioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Accesorio " + accesorioListOrphanCheckAccesorio + " in its accesorioList field has a non-nullable estadoidEstado field.");
            }
            List<Reparacion> reparacionListOrphanCheck = estado.getReparacionList();
            for (Reparacion reparacionListOrphanCheckReparacion : reparacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Reparacion " + reparacionListOrphanCheckReparacion + " in its reparacionList field has a non-nullable estadoidEstado field.");
            }
            List<Componente> componenteListOrphanCheck = estado.getComponenteList();
            for (Componente componenteListOrphanCheckComponente : componenteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Componente " + componenteListOrphanCheckComponente + " in its componenteList field has a non-nullable estadoidEstado field.");
            }
            List<Impresora> impresoraListOrphanCheck = estado.getImpresoraList();
            for (Impresora impresoraListOrphanCheckImpresora : impresoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Impresora " + impresoraListOrphanCheckImpresora + " in its impresoraList field has a non-nullable estadoidEstado field.");
            }
            List<Pc> pcListOrphanCheck = estado.getPcList();
            for (Pc pcListOrphanCheckPc : pcListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Pc " + pcListOrphanCheckPc + " in its pcList field has a non-nullable estadoidEstado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
