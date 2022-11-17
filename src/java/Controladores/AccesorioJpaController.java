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
import Entidades.Area;
import Entidades.Departamento;
import Entidades.Entidad;
import Entidades.Estado;
import Entidades.Marca;
import Entidades.Modelo;
import Entidades.Pc;
import Entidades.TAccesorio;
import Entidades.TConexion;
import Entidades.Almacen;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
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
        if (accesorio.getAlmacenList() == null) {
            accesorio.setAlmacenList(new ArrayList<Almacen>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area areaidArea = accesorio.getAreaidArea();
            if (areaidArea != null) {
                areaidArea = em.getReference(areaidArea.getClass(), areaidArea.getIdArea());
                accesorio.setAreaidArea(areaidArea);
            }
            Departamento departamentoidDepartamento = accesorio.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento = em.getReference(departamentoidDepartamento.getClass(), departamentoidDepartamento.getIdDepartamento());
                accesorio.setDepartamentoidDepartamento(departamentoidDepartamento);
            }
            Entidad entidadidEntidad = accesorio.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad = em.getReference(entidadidEntidad.getClass(), entidadidEntidad.getIdEntidad());
                accesorio.setEntidadidEntidad(entidadidEntidad);
            }
            Estado estadoidEstado = accesorio.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado = em.getReference(estadoidEstado.getClass(), estadoidEstado.getIdEstado());
                accesorio.setEstadoidEstado(estadoidEstado);
            }
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
            Pc pcnoInventario = accesorio.getPcnoInventario();
            if (pcnoInventario != null) {
                pcnoInventario = em.getReference(pcnoInventario.getClass(), pcnoInventario.getNoInventario());
                accesorio.setPcnoInventario(pcnoInventario);
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
            List<Almacen> attachedAlmacenList = new ArrayList<Almacen>();
            for (Almacen almacenListAlmacenToAttach : accesorio.getAlmacenList()) {
                almacenListAlmacenToAttach = em.getReference(almacenListAlmacenToAttach.getClass(), almacenListAlmacenToAttach.getIdAlmacen());
                attachedAlmacenList.add(almacenListAlmacenToAttach);
            }
            accesorio.setAlmacenList(attachedAlmacenList);
            em.persist(accesorio);
            if (areaidArea != null) {
                areaidArea.getAccesorioList().add(accesorio);
                areaidArea = em.merge(areaidArea);
            }
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getAccesorioList().add(accesorio);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            if (entidadidEntidad != null) {
                entidadidEntidad.getAccesorioList().add(accesorio);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            if (estadoidEstado != null) {
                estadoidEstado.getAccesorioList().add(accesorio);
                estadoidEstado = em.merge(estadoidEstado);
            }
            if (marcaidMarca != null) {
                marcaidMarca.getAccesorioList().add(accesorio);
                marcaidMarca = em.merge(marcaidMarca);
            }
            if (modeloidModelo != null) {
                modeloidModelo.getAccesorioList().add(accesorio);
                modeloidModelo = em.merge(modeloidModelo);
            }
            if (pcnoInventario != null) {
                pcnoInventario.getAccesorioList().add(accesorio);
                pcnoInventario = em.merge(pcnoInventario);
            }
            if (TAccesorioidAccesorio != null) {
                TAccesorioidAccesorio.getAccesorioList().add(accesorio);
                TAccesorioidAccesorio = em.merge(TAccesorioidAccesorio);
            }
            if (TConexionidConexion != null) {
                TConexionidConexion.getAccesorioList().add(accesorio);
                TConexionidConexion = em.merge(TConexionidConexion);
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
            Area areaidAreaOld = persistentAccesorio.getAreaidArea();
            Area areaidAreaNew = accesorio.getAreaidArea();
            Departamento departamentoidDepartamentoOld = persistentAccesorio.getDepartamentoidDepartamento();
            Departamento departamentoidDepartamentoNew = accesorio.getDepartamentoidDepartamento();
            Entidad entidadidEntidadOld = persistentAccesorio.getEntidadidEntidad();
            Entidad entidadidEntidadNew = accesorio.getEntidadidEntidad();
            Estado estadoidEstadoOld = persistentAccesorio.getEstadoidEstado();
            Estado estadoidEstadoNew = accesorio.getEstadoidEstado();
            Marca marcaidMarcaOld = persistentAccesorio.getMarcaidMarca();
            Marca marcaidMarcaNew = accesorio.getMarcaidMarca();
            Modelo modeloidModeloOld = persistentAccesorio.getModeloidModelo();
            Modelo modeloidModeloNew = accesorio.getModeloidModelo();
            Pc pcnoInventarioOld = persistentAccesorio.getPcnoInventario();
            Pc pcnoInventarioNew = accesorio.getPcnoInventario();
            TAccesorio TAccesorioidAccesorioOld = persistentAccesorio.getTAccesorioidAccesorio();
            TAccesorio TAccesorioidAccesorioNew = accesorio.getTAccesorioidAccesorio();
            TConexion TConexionidConexionOld = persistentAccesorio.getTConexionidConexion();
            TConexion TConexionidConexionNew = accesorio.getTConexionidConexion();
            List<Almacen> almacenListOld = persistentAccesorio.getAlmacenList();
            List<Almacen> almacenListNew = accesorio.getAlmacenList();
            if (areaidAreaNew != null) {
                areaidAreaNew = em.getReference(areaidAreaNew.getClass(), areaidAreaNew.getIdArea());
                accesorio.setAreaidArea(areaidAreaNew);
            }
            if (departamentoidDepartamentoNew != null) {
                departamentoidDepartamentoNew = em.getReference(departamentoidDepartamentoNew.getClass(), departamentoidDepartamentoNew.getIdDepartamento());
                accesorio.setDepartamentoidDepartamento(departamentoidDepartamentoNew);
            }
            if (entidadidEntidadNew != null) {
                entidadidEntidadNew = em.getReference(entidadidEntidadNew.getClass(), entidadidEntidadNew.getIdEntidad());
                accesorio.setEntidadidEntidad(entidadidEntidadNew);
            }
            if (estadoidEstadoNew != null) {
                estadoidEstadoNew = em.getReference(estadoidEstadoNew.getClass(), estadoidEstadoNew.getIdEstado());
                accesorio.setEstadoidEstado(estadoidEstadoNew);
            }
            if (marcaidMarcaNew != null) {
                marcaidMarcaNew = em.getReference(marcaidMarcaNew.getClass(), marcaidMarcaNew.getIdMarca());
                accesorio.setMarcaidMarca(marcaidMarcaNew);
            }
            if (modeloidModeloNew != null) {
                modeloidModeloNew = em.getReference(modeloidModeloNew.getClass(), modeloidModeloNew.getIdModelo());
                accesorio.setModeloidModelo(modeloidModeloNew);
            }
            if (pcnoInventarioNew != null) {
                pcnoInventarioNew = em.getReference(pcnoInventarioNew.getClass(), pcnoInventarioNew.getNoInventario());
                accesorio.setPcnoInventario(pcnoInventarioNew);
            }
            if (TAccesorioidAccesorioNew != null) {
                TAccesorioidAccesorioNew = em.getReference(TAccesorioidAccesorioNew.getClass(), TAccesorioidAccesorioNew.getIdAccesorio());
                accesorio.setTAccesorioidAccesorio(TAccesorioidAccesorioNew);
            }
            if (TConexionidConexionNew != null) {
                TConexionidConexionNew = em.getReference(TConexionidConexionNew.getClass(), TConexionidConexionNew.getIdConexion());
                accesorio.setTConexionidConexion(TConexionidConexionNew);
            }
            List<Almacen> attachedAlmacenListNew = new ArrayList<Almacen>();
            for (Almacen almacenListNewAlmacenToAttach : almacenListNew) {
                almacenListNewAlmacenToAttach = em.getReference(almacenListNewAlmacenToAttach.getClass(), almacenListNewAlmacenToAttach.getIdAlmacen());
                attachedAlmacenListNew.add(almacenListNewAlmacenToAttach);
            }
            almacenListNew = attachedAlmacenListNew;
            accesorio.setAlmacenList(almacenListNew);
            accesorio = em.merge(accesorio);
            if (areaidAreaOld != null && !areaidAreaOld.equals(areaidAreaNew)) {
                areaidAreaOld.getAccesorioList().remove(accesorio);
                areaidAreaOld = em.merge(areaidAreaOld);
            }
            if (areaidAreaNew != null && !areaidAreaNew.equals(areaidAreaOld)) {
                areaidAreaNew.getAccesorioList().add(accesorio);
                areaidAreaNew = em.merge(areaidAreaNew);
            }
            if (departamentoidDepartamentoOld != null && !departamentoidDepartamentoOld.equals(departamentoidDepartamentoNew)) {
                departamentoidDepartamentoOld.getAccesorioList().remove(accesorio);
                departamentoidDepartamentoOld = em.merge(departamentoidDepartamentoOld);
            }
            if (departamentoidDepartamentoNew != null && !departamentoidDepartamentoNew.equals(departamentoidDepartamentoOld)) {
                departamentoidDepartamentoNew.getAccesorioList().add(accesorio);
                departamentoidDepartamentoNew = em.merge(departamentoidDepartamentoNew);
            }
            if (entidadidEntidadOld != null && !entidadidEntidadOld.equals(entidadidEntidadNew)) {
                entidadidEntidadOld.getAccesorioList().remove(accesorio);
                entidadidEntidadOld = em.merge(entidadidEntidadOld);
            }
            if (entidadidEntidadNew != null && !entidadidEntidadNew.equals(entidadidEntidadOld)) {
                entidadidEntidadNew.getAccesorioList().add(accesorio);
                entidadidEntidadNew = em.merge(entidadidEntidadNew);
            }
            if (estadoidEstadoOld != null && !estadoidEstadoOld.equals(estadoidEstadoNew)) {
                estadoidEstadoOld.getAccesorioList().remove(accesorio);
                estadoidEstadoOld = em.merge(estadoidEstadoOld);
            }
            if (estadoidEstadoNew != null && !estadoidEstadoNew.equals(estadoidEstadoOld)) {
                estadoidEstadoNew.getAccesorioList().add(accesorio);
                estadoidEstadoNew = em.merge(estadoidEstadoNew);
            }
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
            if (pcnoInventarioOld != null && !pcnoInventarioOld.equals(pcnoInventarioNew)) {
                pcnoInventarioOld.getAccesorioList().remove(accesorio);
                pcnoInventarioOld = em.merge(pcnoInventarioOld);
            }
            if (pcnoInventarioNew != null && !pcnoInventarioNew.equals(pcnoInventarioOld)) {
                pcnoInventarioNew.getAccesorioList().add(accesorio);
                pcnoInventarioNew = em.merge(pcnoInventarioNew);
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
            Area areaidArea = accesorio.getAreaidArea();
            if (areaidArea != null) {
                areaidArea.getAccesorioList().remove(accesorio);
                areaidArea = em.merge(areaidArea);
            }
            Departamento departamentoidDepartamento = accesorio.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getAccesorioList().remove(accesorio);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            Entidad entidadidEntidad = accesorio.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad.getAccesorioList().remove(accesorio);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            Estado estadoidEstado = accesorio.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado.getAccesorioList().remove(accesorio);
                estadoidEstado = em.merge(estadoidEstado);
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
            Pc pcnoInventario = accesorio.getPcnoInventario();
            if (pcnoInventario != null) {
                pcnoInventario.getAccesorioList().remove(accesorio);
                pcnoInventario = em.merge(pcnoInventario);
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
