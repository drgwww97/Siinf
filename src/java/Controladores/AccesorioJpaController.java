/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Accesorio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Marca;
import Entidades.Modelo;
import Entidades.TAccesorio;
import Entidades.TConexion;
import Entidades.Pc;
import java.util.ArrayList;
import java.util.List;
import Entidades.Almacen;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dayana
 */
public class AccesorioJpaController implements Serializable {

    public AccesorioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Accesorio accesorio) throws PreexistingEntityException, Exception {
        if (accesorio.getPcList() == null) {
            accesorio.setPcList(new ArrayList<Pc>());
        }
        if (accesorio.getAlmacenList() == null) {
            accesorio.setAlmacenList(new ArrayList<Almacen>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca marcaidMarca = accesorio.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca = em.getReference(marcaidMarca.getClass(), marcaidMarca.getIdMarca());
                accesorio.setMarcaidMarca(marcaidMarca);
            }
            Modelo modeloidModelo = accesorio.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo = em.getReference(modeloidModelo.getClass(), modeloidModelo.getIdModelo());
                accesorio.setModeloidModelo(modeloidModelo);
            }
            TAccesorio TAccesorioidAccesorio = accesorio.getTAccesorioidAccesorio();
            if (TAccesorioidAccesorio != null) {
                TAccesorioidAccesorio = em.getReference(TAccesorioidAccesorio.getClass(), TAccesorioidAccesorio.getIdAccesorio());
                accesorio.setTAccesorioidAccesorio(TAccesorioidAccesorio);
            }
            TConexion TConexionidConexion = accesorio.getTConexionidConexion();
            if (TConexionidConexion != null) {
                TConexionidConexion = em.getReference(TConexionidConexion.getClass(), TConexionidConexion.getIdConexion());
                accesorio.setTConexionidConexion(TConexionidConexion);
            }
            List<Pc> attachedPcList = new ArrayList<Pc>();
            for (Pc pcListPcToAttach : accesorio.getPcList()) {
                pcListPcToAttach = em.getReference(pcListPcToAttach.getClass(), pcListPcToAttach.getNoInventario());
                attachedPcList.add(pcListPcToAttach);
            }
            accesorio.setPcList(attachedPcList);
            List<Almacen> attachedAlmacenList = new ArrayList<Almacen>();
            for (Almacen almacenListAlmacenToAttach : accesorio.getAlmacenList()) {
                almacenListAlmacenToAttach = em.getReference(almacenListAlmacenToAttach.getClass(), almacenListAlmacenToAttach.getIdAlmacen());
                attachedAlmacenList.add(almacenListAlmacenToAttach);
            }
            accesorio.setAlmacenList(attachedAlmacenList);
            em.persist(accesorio);
            if (marcaidMarca != null) {
                marcaidMarca.getAccesorioList().add(accesorio);
                marcaidMarca = em.merge(marcaidMarca);
            }
            if (modeloidModelo != null) {
                modeloidModelo.getAccesorioList().add(accesorio);
                modeloidModelo = em.merge(modeloidModelo);
            }
            if (TAccesorioidAccesorio != null) {
                TAccesorioidAccesorio.getAccesorioList().add(accesorio);
                TAccesorioidAccesorio = em.merge(TAccesorioidAccesorio);
            }
            if (TConexionidConexion != null) {
                TConexionidConexion.getAccesorioList().add(accesorio);
                TConexionidConexion = em.merge(TConexionidConexion);
            }
            for (Pc pcListPc : accesorio.getPcList()) {
                Accesorio oldAccesoriosnAccesorioOfPcListPc = pcListPc.getAccesoriosnAccesorio();
                pcListPc.setAccesoriosnAccesorio(accesorio);
                pcListPc = em.merge(pcListPc);
                if (oldAccesoriosnAccesorioOfPcListPc != null) {
                    oldAccesoriosnAccesorioOfPcListPc.getPcList().remove(pcListPc);
                    oldAccesoriosnAccesorioOfPcListPc = em.merge(oldAccesoriosnAccesorioOfPcListPc);
                }
            }
            for (Almacen almacenListAlmacen : accesorio.getAlmacenList()) {
                Accesorio oldAccesoriosnAccesorioOfAlmacenListAlmacen = almacenListAlmacen.getAccesoriosnAccesorio();
                almacenListAlmacen.setAccesoriosnAccesorio(accesorio);
                almacenListAlmacen = em.merge(almacenListAlmacen);
                if (oldAccesoriosnAccesorioOfAlmacenListAlmacen != null) {
                    oldAccesoriosnAccesorioOfAlmacenListAlmacen.getAlmacenList().remove(almacenListAlmacen);
                    oldAccesoriosnAccesorioOfAlmacenListAlmacen = em.merge(oldAccesoriosnAccesorioOfAlmacenListAlmacen);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAccesorio(accesorio.getSnAccesorio()) != null) {
                throw new PreexistingEntityException("Accesorio " + accesorio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Accesorio accesorio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accesorio persistentAccesorio = em.find(Accesorio.class, accesorio.getSnAccesorio());
            Marca marcaidMarcaOld = persistentAccesorio.getMarcaidMarca();
            Marca marcaidMarcaNew = accesorio.getMarcaidMarca();
            Modelo modeloidModeloOld = persistentAccesorio.getModeloidModelo();
            Modelo modeloidModeloNew = accesorio.getModeloidModelo();
            TAccesorio TAccesorioidAccesorioOld = persistentAccesorio.getTAccesorioidAccesorio();
            TAccesorio TAccesorioidAccesorioNew = accesorio.getTAccesorioidAccesorio();
            TConexion TConexionidConexionOld = persistentAccesorio.getTConexionidConexion();
            TConexion TConexionidConexionNew = accesorio.getTConexionidConexion();
            List<Pc> pcListOld = persistentAccesorio.getPcList();
            List<Pc> pcListNew = accesorio.getPcList();
            List<Almacen> almacenListOld = persistentAccesorio.getAlmacenList();
            List<Almacen> almacenListNew = accesorio.getAlmacenList();
            if (marcaidMarcaNew != null) {
                marcaidMarcaNew = em.getReference(marcaidMarcaNew.getClass(), marcaidMarcaNew.getIdMarca());
                accesorio.setMarcaidMarca(marcaidMarcaNew);
            }
            if (modeloidModeloNew != null) {
                modeloidModeloNew = em.getReference(modeloidModeloNew.getClass(), modeloidModeloNew.getIdModelo());
                accesorio.setModeloidModelo(modeloidModeloNew);
            }
            if (TAccesorioidAccesorioNew != null) {
                TAccesorioidAccesorioNew = em.getReference(TAccesorioidAccesorioNew.getClass(), TAccesorioidAccesorioNew.getIdAccesorio());
                accesorio.setTAccesorioidAccesorio(TAccesorioidAccesorioNew);
            }
            if (TConexionidConexionNew != null) {
                TConexionidConexionNew = em.getReference(TConexionidConexionNew.getClass(), TConexionidConexionNew.getIdConexion());
                accesorio.setTConexionidConexion(TConexionidConexionNew);
            }
            List<Pc> attachedPcListNew = new ArrayList<Pc>();
            for (Pc pcListNewPcToAttach : pcListNew) {
                pcListNewPcToAttach = em.getReference(pcListNewPcToAttach.getClass(), pcListNewPcToAttach.getNoInventario());
                attachedPcListNew.add(pcListNewPcToAttach);
            }
            pcListNew = attachedPcListNew;
            accesorio.setPcList(pcListNew);
            List<Almacen> attachedAlmacenListNew = new ArrayList<Almacen>();
            for (Almacen almacenListNewAlmacenToAttach : almacenListNew) {
                almacenListNewAlmacenToAttach = em.getReference(almacenListNewAlmacenToAttach.getClass(), almacenListNewAlmacenToAttach.getIdAlmacen());
                attachedAlmacenListNew.add(almacenListNewAlmacenToAttach);
            }
            almacenListNew = attachedAlmacenListNew;
            accesorio.setAlmacenList(almacenListNew);
            accesorio = em.merge(accesorio);
            if (marcaidMarcaOld != null && !marcaidMarcaOld.equals(marcaidMarcaNew)) {
                marcaidMarcaOld.getAccesorioList().remove(accesorio);
                marcaidMarcaOld = em.merge(marcaidMarcaOld);
            }
            if (marcaidMarcaNew != null && !marcaidMarcaNew.equals(marcaidMarcaOld)) {
                marcaidMarcaNew.getAccesorioList().add(accesorio);
                marcaidMarcaNew = em.merge(marcaidMarcaNew);
            }
            if (modeloidModeloOld != null && !modeloidModeloOld.equals(modeloidModeloNew)) {
                modeloidModeloOld.getAccesorioList().remove(accesorio);
                modeloidModeloOld = em.merge(modeloidModeloOld);
            }
            if (modeloidModeloNew != null && !modeloidModeloNew.equals(modeloidModeloOld)) {
                modeloidModeloNew.getAccesorioList().add(accesorio);
                modeloidModeloNew = em.merge(modeloidModeloNew);
            }
            if (TAccesorioidAccesorioOld != null && !TAccesorioidAccesorioOld.equals(TAccesorioidAccesorioNew)) {
                TAccesorioidAccesorioOld.getAccesorioList().remove(accesorio);
                TAccesorioidAccesorioOld = em.merge(TAccesorioidAccesorioOld);
            }
            if (TAccesorioidAccesorioNew != null && !TAccesorioidAccesorioNew.equals(TAccesorioidAccesorioOld)) {
                TAccesorioidAccesorioNew.getAccesorioList().add(accesorio);
                TAccesorioidAccesorioNew = em.merge(TAccesorioidAccesorioNew);
            }
            if (TConexionidConexionOld != null && !TConexionidConexionOld.equals(TConexionidConexionNew)) {
                TConexionidConexionOld.getAccesorioList().remove(accesorio);
                TConexionidConexionOld = em.merge(TConexionidConexionOld);
            }
            if (TConexionidConexionNew != null && !TConexionidConexionNew.equals(TConexionidConexionOld)) {
                TConexionidConexionNew.getAccesorioList().add(accesorio);
                TConexionidConexionNew = em.merge(TConexionidConexionNew);
            }
            for (Pc pcListOldPc : pcListOld) {
                if (!pcListNew.contains(pcListOldPc)) {
                    pcListOldPc.setAccesoriosnAccesorio(null);
                    pcListOldPc = em.merge(pcListOldPc);
                }
            }
            for (Pc pcListNewPc : pcListNew) {
                if (!pcListOld.contains(pcListNewPc)) {
                    Accesorio oldAccesoriosnAccesorioOfPcListNewPc = pcListNewPc.getAccesoriosnAccesorio();
                    pcListNewPc.setAccesoriosnAccesorio(accesorio);
                    pcListNewPc = em.merge(pcListNewPc);
                    if (oldAccesoriosnAccesorioOfPcListNewPc != null && !oldAccesoriosnAccesorioOfPcListNewPc.equals(accesorio)) {
                        oldAccesoriosnAccesorioOfPcListNewPc.getPcList().remove(pcListNewPc);
                        oldAccesoriosnAccesorioOfPcListNewPc = em.merge(oldAccesoriosnAccesorioOfPcListNewPc);
                    }
                }
            }
            for (Almacen almacenListOldAlmacen : almacenListOld) {
                if (!almacenListNew.contains(almacenListOldAlmacen)) {
                    almacenListOldAlmacen.setAccesoriosnAccesorio(null);
                    almacenListOldAlmacen = em.merge(almacenListOldAlmacen);
                }
            }
            for (Almacen almacenListNewAlmacen : almacenListNew) {
                if (!almacenListOld.contains(almacenListNewAlmacen)) {
                    Accesorio oldAccesoriosnAccesorioOfAlmacenListNewAlmacen = almacenListNewAlmacen.getAccesoriosnAccesorio();
                    almacenListNewAlmacen.setAccesoriosnAccesorio(accesorio);
                    almacenListNewAlmacen = em.merge(almacenListNewAlmacen);
                    if (oldAccesoriosnAccesorioOfAlmacenListNewAlmacen != null && !oldAccesoriosnAccesorioOfAlmacenListNewAlmacen.equals(accesorio)) {
                        oldAccesoriosnAccesorioOfAlmacenListNewAlmacen.getAlmacenList().remove(almacenListNewAlmacen);
                        oldAccesoriosnAccesorioOfAlmacenListNewAlmacen = em.merge(oldAccesoriosnAccesorioOfAlmacenListNewAlmacen);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = accesorio.getSnAccesorio();
                if (findAccesorio(id) == null) {
                    throw new NonexistentEntityException("The accesorio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accesorio accesorio;
            try {
                accesorio = em.getReference(Accesorio.class, id);
                accesorio.getSnAccesorio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accesorio with id " + id + " no longer exists.", enfe);
            }
            Marca marcaidMarca = accesorio.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca.getAccesorioList().remove(accesorio);
                marcaidMarca = em.merge(marcaidMarca);
            }
            Modelo modeloidModelo = accesorio.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo.getAccesorioList().remove(accesorio);
                modeloidModelo = em.merge(modeloidModelo);
            }
            TAccesorio TAccesorioidAccesorio = accesorio.getTAccesorioidAccesorio();
            if (TAccesorioidAccesorio != null) {
                TAccesorioidAccesorio.getAccesorioList().remove(accesorio);
                TAccesorioidAccesorio = em.merge(TAccesorioidAccesorio);
            }
            TConexion TConexionidConexion = accesorio.getTConexionidConexion();
            if (TConexionidConexion != null) {
                TConexionidConexion.getAccesorioList().remove(accesorio);
                TConexionidConexion = em.merge(TConexionidConexion);
            }
            List<Pc> pcList = accesorio.getPcList();
            for (Pc pcListPc : pcList) {
                pcListPc.setAccesoriosnAccesorio(null);
                pcListPc = em.merge(pcListPc);
            }
            List<Almacen> almacenList = accesorio.getAlmacenList();
            for (Almacen almacenListAlmacen : almacenList) {
                almacenListAlmacen.setAccesoriosnAccesorio(null);
                almacenListAlmacen = em.merge(almacenListAlmacen);
            }
            em.remove(accesorio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Accesorio> findAccesorioEntities() {
        return findAccesorioEntities(true, -1, -1);
    }

    public List<Accesorio> findAccesorioEntities(int maxResults, int firstResult) {
        return findAccesorioEntities(false, maxResults, firstResult);
    }

    private List<Accesorio> findAccesorioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Accesorio.class));
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

    public Accesorio findAccesorio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Accesorio.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccesorioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Accesorio> rt = cq.from(Accesorio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
