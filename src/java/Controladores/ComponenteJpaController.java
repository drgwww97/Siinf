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
import Entidades.Marca;
import Entidades.Modelo;
import Entidades.TComponente;
import Entidades.TConexion;
import Entidades.Pc;
import java.util.ArrayList;
import java.util.List;
import Entidades.Almacen;
import Entidades.Componente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dayana
 */
public class ComponenteJpaController implements Serializable {

    public ComponenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Componente componente) throws PreexistingEntityException, Exception {
        if (componente.getPcList() == null) {
            componente.setPcList(new ArrayList<Pc>());
        }
        if (componente.getAlmacenList() == null) {
            componente.setAlmacenList(new ArrayList<Almacen>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca marcaidMarca = componente.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca = em.getReference(marcaidMarca.getClass(), marcaidMarca.getIdMarca());
                componente.setMarcaidMarca(marcaidMarca);
            }
            Modelo modeloidModelo = componente.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo = em.getReference(modeloidModelo.getClass(), modeloidModelo.getIdModelo());
                componente.setModeloidModelo(modeloidModelo);
            }
            TComponente TComponenteidComponente = componente.getTComponenteidComponente();
            if (TComponenteidComponente != null) {
                TComponenteidComponente = em.getReference(TComponenteidComponente.getClass(), TComponenteidComponente.getIdComponente());
                componente.setTComponenteidComponente(TComponenteidComponente);
            }
            TConexion TConexionidConexion = componente.getTConexionidConexion();
            if (TConexionidConexion != null) {
                TConexionidConexion = em.getReference(TConexionidConexion.getClass(), TConexionidConexion.getIdConexion());
                componente.setTConexionidConexion(TConexionidConexion);
            }
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : componente.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            componente.setPcList(attachedPcList);
            List<Almacen> attachedAlmacenList = new ArrayList<Almacen>();
            for (Almacen almacenListAlmacenToAttach : componente.getAlmacenList()) {
                almacenListAlmacenToAttach = em.getReference(almacenListAlmacenToAttach.getClass(), almacenListAlmacenToAttach.getIdAlmacen());
                attachedAlmacenList.add(almacenListAlmacenToAttach);
            }
            componente.setAlmacenList(attachedAlmacenList);
            em.persist(componente);
            if (marcaidMarca != null) {
                marcaidMarca.getComponenteList().add(componente);
                marcaidMarca = em.merge(marcaidMarca);
            }
            if (modeloidModelo != null) {
                modeloidModelo.getComponenteList().add(componente);
                modeloidModelo = em.merge(modeloidModelo);
            }
            if (TComponenteidComponente != null) {
                TComponenteidComponente.getComponenteList().add(componente);
                TComponenteidComponente = em.merge(TComponenteidComponente);
            }
            if (TConexionidConexion != null) {
                TConexionidConexion.getComponenteList().add(componente);
                TConexionidConexion = em.merge(TConexionidConexion);
            }
            for (Pc pcListPc : componente.getPcList()) {
                Componente oldComponentesnComponenteOfPcListPc = pcListPc.getComponentesnComponente();
                pcListPc.setComponentesnComponente(componente);
                pcListPc = em.merge(pcListPc);
                if (oldComponentesnComponenteOfPcListPc != null) {
                    oldComponentesnComponenteOfPcListPc.getPcList().remove(pcListPc);
                    oldComponentesnComponenteOfPcListPc = em.merge(oldComponentesnComponenteOfPcListPc);
                }
            }
            for (Almacen almacenListAlmacen : componente.getAlmacenList()) {
                Componente oldComponentesnComponenteOfAlmacenListAlmacen = almacenListAlmacen.getComponentesnComponente();
                almacenListAlmacen.setComponentesnComponente(componente);
                almacenListAlmacen = em.merge(almacenListAlmacen);
                if (oldComponentesnComponenteOfAlmacenListAlmacen != null) {
                    oldComponentesnComponenteOfAlmacenListAlmacen.getAlmacenList().remove(almacenListAlmacen);
                    oldComponentesnComponenteOfAlmacenListAlmacen = em.merge(oldComponentesnComponenteOfAlmacenListAlmacen);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComponente(componente.getSnComponente()) != null) {
                throw new PreexistingEntityException("Componente " + componente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Componente componente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componente persistentComponente = em.find(Componente.class, componente.getSnComponente());
            Marca marcaidMarcaOld = persistentComponente.getMarcaidMarca();
            Marca marcaidMarcaNew = componente.getMarcaidMarca();
            Modelo modeloidModeloOld = persistentComponente.getModeloidModelo();
            Modelo modeloidModeloNew = componente.getModeloidModelo();
            TComponente TComponenteidComponenteOld = persistentComponente.getTComponenteidComponente();
            TComponente TComponenteidComponenteNew = componente.getTComponenteidComponente();
            TConexion TConexionidConexionOld = persistentComponente.getTConexionidConexion();
            TConexion TConexionidConexionNew = componente.getTConexionidConexion();
            List<Pc> pcListOld = persistentComponente.getPcList();
            List<Pc> pcListNew = componente.getPcList();
            List<Almacen> almacenListOld = persistentComponente.getAlmacenList();
            List<Almacen> almacenListNew = componente.getAlmacenList();
            List<String> illegalOrphanMessages = null;
            for (Almacen almacenListOldAlmacen : almacenListOld) {
                if (!almacenListNew.contains(almacenListOldAlmacen)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Almacen " + almacenListOldAlmacen + " since its componentesnComponente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (marcaidMarcaNew != null) {
                marcaidMarcaNew = em.getReference(marcaidMarcaNew.getClass(), marcaidMarcaNew.getIdMarca());
                componente.setMarcaidMarca(marcaidMarcaNew);
            }
            if (modeloidModeloNew != null) {
                modeloidModeloNew = em.getReference(modeloidModeloNew.getClass(), modeloidModeloNew.getIdModelo());
                componente.setModeloidModelo(modeloidModeloNew);
            }
            if (TComponenteidComponenteNew != null) {
                TComponenteidComponenteNew = em.getReference(TComponenteidComponenteNew.getClass(), TComponenteidComponenteNew.getIdComponente());
                componente.setTComponenteidComponente(TComponenteidComponenteNew);
            }
            if (TConexionidConexionNew != null) {
                TConexionidConexionNew = em.getReference(TConexionidConexionNew.getClass(), TConexionidConexionNew.getIdConexion());
                componente.setTConexionidConexion(TConexionidConexionNew);
            }
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            componente.setPcList(pcListNew);
            List<Almacen> attachedAlmacenListNew = new ArrayList<Almacen>();
            for (Almacen almacenListNewAlmacenToAttach : almacenListNew) {
                almacenListNewAlmacenToAttach = em.getReference(almacenListNewAlmacenToAttach.getClass(), almacenListNewAlmacenToAttach.getIdAlmacen());
                attachedAlmacenListNew.add(almacenListNewAlmacenToAttach);
            }
            almacenListNew = attachedAlmacenListNew;
            componente.setAlmacenList(almacenListNew);
            componente = em.merge(componente);
            if (marcaidMarcaOld != null && !marcaidMarcaOld.equals(marcaidMarcaNew)) {
                marcaidMarcaOld.getComponenteList().remove(componente);
                marcaidMarcaOld = em.merge(marcaidMarcaOld);
            }
            if (marcaidMarcaNew != null && !marcaidMarcaNew.equals(marcaidMarcaOld)) {
                marcaidMarcaNew.getComponenteList().add(componente);
                marcaidMarcaNew = em.merge(marcaidMarcaNew);
            }
            if (modeloidModeloOld != null && !modeloidModeloOld.equals(modeloidModeloNew)) {
                modeloidModeloOld.getComponenteList().remove(componente);
                modeloidModeloOld = em.merge(modeloidModeloOld);
            }
            if (modeloidModeloNew != null && !modeloidModeloNew.equals(modeloidModeloOld)) {
                modeloidModeloNew.getComponenteList().add(componente);
                modeloidModeloNew = em.merge(modeloidModeloNew);
            }
            if (TComponenteidComponenteOld != null && !TComponenteidComponenteOld.equals(TComponenteidComponenteNew)) {
                TComponenteidComponenteOld.getComponenteList().remove(componente);
                TComponenteidComponenteOld = em.merge(TComponenteidComponenteOld);
            }
            if (TComponenteidComponenteNew != null && !TComponenteidComponenteNew.equals(TComponenteidComponenteOld)) {
                TComponenteidComponenteNew.getComponenteList().add(componente);
                TComponenteidComponenteNew = em.merge(TComponenteidComponenteNew);
            }
            if (TConexionidConexionOld != null && !TConexionidConexionOld.equals(TConexionidConexionNew)) {
                TConexionidConexionOld.getComponenteList().remove(componente);
                TConexionidConexionOld = em.merge(TConexionidConexionOld);
            }
            if (TConexionidConexionNew != null && !TConexionidConexionNew.equals(TConexionidConexionOld)) {
                TConexionidConexionNew.getComponenteList().add(componente);
                TConexionidConexionNew = em.merge(TConexionidConexionNew);
            }
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    pcListOldPc.setComponentesnComponente(null);
                    pcListOldPc = em.merge(pcListOldPc);
                }
            }
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    Componente oldComponentesnComponenteOfPcListNewPc = pcListNewPc.getComponentesnComponente();
                    pcListNewPc.setComponentesnComponente(componente);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldComponentesnComponenteOfPcListNewPc != null && !oldComponentesnComponenteOfPcListNewPc.equals(componente)) {
                        oldComponentesnComponenteOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldComponentesnComponenteOfPcListNewPc = em.merge(oldComponentesnComponenteOfPcListNewPc);
                    }
                }
            }
            for (Almacen almacenListNewAlmacen : almacenListNew) {
                if (!almacenListOld.contains(almacenListNewAlmacen)) {
                    Componente oldComponentesnComponenteOfAlmacenListNewAlmacen = almacenListNewAlmacen.getComponentesnComponente();
                    almacenListNewAlmacen.setComponentesnComponente(componente);
                    almacenListNewAlmacen = em.merge(almacenListNewAlmacen);
                    if (oldComponentesnComponenteOfAlmacenListNewAlmacen != null && !oldComponentesnComponenteOfAlmacenListNewAlmacen.equals(componente)) {
                        oldComponentesnComponenteOfAlmacenListNewAlmacen.getAlmacenList().remove(almacenListNewAlmacen);
                        oldComponentesnComponenteOfAlmacenListNewAlmacen = em.merge(oldComponentesnComponenteOfAlmacenListNewAlmacen);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = componente.getSnComponente();
                if (findComponente(id) == null) {
                    throw new NonexistentEntityException("The componente with id " + id + " no longer exists.");
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
            Componente componente;
            try {
                componente = em.getReference(Componente.class, id);
                componente.getSnComponente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The componente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Almacen> almacenListOrphanCheck = componente.getAlmacenList();
            for (Almacen almacenListOrphanCheckAlmacen : almacenListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Componente (" + componente + ") cannot be destroyed since the Almacen " + almacenListOrphanCheckAlmacen + " in its almacenList field has a non-nullable componentesnComponente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Marca marcaidMarca = componente.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca.getComponenteList().remove(componente);
                marcaidMarca = em.merge(marcaidMarca);
            }
            Modelo modeloidModelo = componente.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo.getComponenteList().remove(componente);
                modeloidModelo = em.merge(modeloidModelo);
            }
            TComponente TComponenteidComponente = componente.getTComponenteidComponente();
            if (TComponenteidComponente != null) {
                TComponenteidComponente.getComponenteList().remove(componente);
                TComponenteidComponente = em.merge(TComponenteidComponente);
            }
            TConexion TConexionidConexion = componente.getTConexionidConexion();
            if (TConexionidConexion != null) {
                TConexionidConexion.getComponenteList().remove(componente);
                TConexionidConexion = em.merge(TConexionidConexion);
            }
            List<Pc> pcList = componente.getPcList();
            for (Pc pcListPc : pcList) {
                pcListPc.setComponentesnComponente(null);
                pcListPc = em.merge(pcListPc);
            }
            em.remove(componente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Componente> findComponenteEntities() {
        return findComponenteEntities(true, -1, -1);
    }

    public List<Componente> findComponenteEntities(int maxResults, int firstResult) {
        return findComponenteEntities(false, maxResults, firstResult);
    }

    private List<Componente> findComponenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Componente.class));
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

    public Componente findComponente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Componente.class, id);
        } finally {
            em.close();
        }
    }

    public int getComponenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Componente> rt = cq.from(Componente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
