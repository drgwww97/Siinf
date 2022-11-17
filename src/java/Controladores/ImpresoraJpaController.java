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
import Entidades.Area;
import Entidades.Departamento;
import Entidades.Entidad;
import Entidades.Estado;
import Entidades.Impresora;
import Entidades.Marca;
import Entidades.Modelo;
import Entidades.TTonner;
import Entidades.UsuariosCompartidos;
import java.util.ArrayList;
import java.util.List;
import Entidades.RegDatosToner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class ImpresoraJpaController implements Serializable {

    public ImpresoraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Impresora impresora) throws PreexistingEntityException, Exception {
        if (impresora.getUsuariosCompartidosList() == null) {
            impresora.setUsuariosCompartidosList(new ArrayList<UsuariosCompartidos>());
        }
        if (impresora.getRegDatosTonerList() == null) {
            impresora.setRegDatosTonerList(new ArrayList<RegDatosToner>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area areaidArea = impresora.getAreaidArea();
            if (areaidArea != null) {
                areaidArea = em.getReference(areaidArea.getClass(), areaidArea.getIdArea());
                impresora.setAreaidArea(areaidArea);
            }
            Departamento departamentoidDepartamento = impresora.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento = em.getReference(departamentoidDepartamento.getClass(), departamentoidDepartamento.getIdDepartamento());
                impresora.setDepartamentoidDepartamento(departamentoidDepartamento);
            }
            Entidad entidadidEntidad = impresora.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad = em.getReference(entidadidEntidad.getClass(), entidadidEntidad.getIdEntidad());
                impresora.setEntidadidEntidad(entidadidEntidad);
            }
            Estado estadoidEstado = impresora.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado = em.getReference(estadoidEstado.getClass(), estadoidEstado.getIdEstado());
                impresora.setEstadoidEstado(estadoidEstado);
            }
            Marca marcaidMarca = impresora.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca = em.getReference(marcaidMarca.getClass(), marcaidMarca.getIdMarca());
                impresora.setMarcaidMarca(marcaidMarca);
            }
            Modelo modeloidModelo = impresora.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo = em.getReference(modeloidModelo.getClass(), modeloidModelo.getIdModelo());
                impresora.setModeloidModelo(modeloidModelo);
            }
            TTonner TTonnersnTonner = impresora.getTTonnersnTonner();
            if (TTonnersnTonner != null) {
                TTonnersnTonner = em.getReference(TTonnersnTonner.getClass(), TTonnersnTonner.getSnTonner());
                impresora.setTTonnersnTonner(TTonnersnTonner);
            }
            List<UsuariosCompartidos> attachedUsuariosCompartidosList = new ArrayList<UsuariosCompartidos>();
            for (UsuariosCompartidos usuariosCompartidosListUsuariosCompartidosToAttach : impresora.getUsuariosCompartidosList()) {
                usuariosCompartidosListUsuariosCompartidosToAttach = em.getReference(usuariosCompartidosListUsuariosCompartidosToAttach.getClass(), usuariosCompartidosListUsuariosCompartidosToAttach.getNombreUsuariocomp());
                attachedUsuariosCompartidosList.add(usuariosCompartidosListUsuariosCompartidosToAttach);
            }
            impresora.setUsuariosCompartidosList(attachedUsuariosCompartidosList);
            List<RegDatosToner> attachedRegDatosTonerList = new ArrayList<RegDatosToner>();
            for (RegDatosToner regDatosTonerListRegDatosTonerToAttach : impresora.getRegDatosTonerList()) {
                regDatosTonerListRegDatosTonerToAttach = em.getReference(regDatosTonerListRegDatosTonerToAttach.getClass(), regDatosTonerListRegDatosTonerToAttach.getIdRegToner());
                attachedRegDatosTonerList.add(regDatosTonerListRegDatosTonerToAttach);
            }
            impresora.setRegDatosTonerList(attachedRegDatosTonerList);
            em.persist(impresora);
            if (areaidArea != null) {
                areaidArea.getImpresoraList().add(impresora);
                areaidArea = em.merge(areaidArea);
            }
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getImpresoraList().add(impresora);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            if (entidadidEntidad != null) {
                entidadidEntidad.getImpresoraList().add(impresora);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            if (estadoidEstado != null) {
                estadoidEstado.getImpresoraList().add(impresora);
                estadoidEstado = em.merge(estadoidEstado);
            }
            if (marcaidMarca != null) {
                marcaidMarca.getImpresoraList().add(impresora);
                marcaidMarca = em.merge(marcaidMarca);
            }
            if (modeloidModelo != null) {
                modeloidModelo.getImpresoraList().add(impresora);
                modeloidModelo = em.merge(modeloidModelo);
            }
            if (TTonnersnTonner != null) {
                TTonnersnTonner.getImpresoraList().add(impresora);
                TTonnersnTonner = em.merge(TTonnersnTonner);
            }
            for (UsuariosCompartidos usuariosCompartidosListUsuariosCompartidos : impresora.getUsuariosCompartidosList()) {
                usuariosCompartidosListUsuariosCompartidos.getImpresoraList().add(impresora);
                usuariosCompartidosListUsuariosCompartidos = em.merge(usuariosCompartidosListUsuariosCompartidos);
            }
            for (RegDatosToner regDatosTonerListRegDatosToner : impresora.getRegDatosTonerList()) {
                Impresora oldImpresoranoInventarioOfRegDatosTonerListRegDatosToner = regDatosTonerListRegDatosToner.getImpresoranoInventario();
                regDatosTonerListRegDatosToner.setImpresoranoInventario(impresora);
                regDatosTonerListRegDatosToner = em.merge(regDatosTonerListRegDatosToner);
                if (oldImpresoranoInventarioOfRegDatosTonerListRegDatosToner != null) {
                    oldImpresoranoInventarioOfRegDatosTonerListRegDatosToner.getRegDatosTonerList().remove(regDatosTonerListRegDatosToner);
                    oldImpresoranoInventarioOfRegDatosTonerListRegDatosToner = em.merge(oldImpresoranoInventarioOfRegDatosTonerListRegDatosToner);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findImpresora(impresora.getNoInventario()) != null) {
                throw new PreexistingEntityException("Impresora " + impresora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Impresora impresora) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Impresora persistentImpresora = em.find(Impresora.class, impresora.getNoInventario());
            Area areaidAreaOld = persistentImpresora.getAreaidArea();
            Area areaidAreaNew = impresora.getAreaidArea();
            Departamento departamentoidDepartamentoOld = persistentImpresora.getDepartamentoidDepartamento();
            Departamento departamentoidDepartamentoNew = impresora.getDepartamentoidDepartamento();
            Entidad entidadidEntidadOld = persistentImpresora.getEntidadidEntidad();
            Entidad entidadidEntidadNew = impresora.getEntidadidEntidad();
            Estado estadoidEstadoOld = persistentImpresora.getEstadoidEstado();
            Estado estadoidEstadoNew = impresora.getEstadoidEstado();
            Marca marcaidMarcaOld = persistentImpresora.getMarcaidMarca();
            Marca marcaidMarcaNew = impresora.getMarcaidMarca();
            Modelo modeloidModeloOld = persistentImpresora.getModeloidModelo();
            Modelo modeloidModeloNew = impresora.getModeloidModelo();
            TTonner TTonnersnTonnerOld = persistentImpresora.getTTonnersnTonner();
            TTonner TTonnersnTonnerNew = impresora.getTTonnersnTonner();
            List<UsuariosCompartidos> usuariosCompartidosListOld = persistentImpresora.getUsuariosCompartidosList();
            List<UsuariosCompartidos> usuariosCompartidosListNew = impresora.getUsuariosCompartidosList();
            List<RegDatosToner> regDatosTonerListOld = persistentImpresora.getRegDatosTonerList();
            List<RegDatosToner> regDatosTonerListNew = impresora.getRegDatosTonerList();
            List<String> illegalOrphanMessages = null;
            for (RegDatosToner regDatosTonerListOldRegDatosToner : regDatosTonerListOld) {
                if (!regDatosTonerListNew.contains(regDatosTonerListOldRegDatosToner)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegDatosToner " + regDatosTonerListOldRegDatosToner + " since its impresoranoInventario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (areaidAreaNew != null) {
                areaidAreaNew = em.getReference(areaidAreaNew.getClass(), areaidAreaNew.getIdArea());
                impresora.setAreaidArea(areaidAreaNew);
            }
            if (departamentoidDepartamentoNew != null) {
                departamentoidDepartamentoNew = em.getReference(departamentoidDepartamentoNew.getClass(), departamentoidDepartamentoNew.getIdDepartamento());
                impresora.setDepartamentoidDepartamento(departamentoidDepartamentoNew);
            }
            if (entidadidEntidadNew != null) {
                entidadidEntidadNew = em.getReference(entidadidEntidadNew.getClass(), entidadidEntidadNew.getIdEntidad());
                impresora.setEntidadidEntidad(entidadidEntidadNew);
            }
            if (estadoidEstadoNew != null) {
                estadoidEstadoNew = em.getReference(estadoidEstadoNew.getClass(), estadoidEstadoNew.getIdEstado());
                impresora.setEstadoidEstado(estadoidEstadoNew);
            }
            if (marcaidMarcaNew != null) {
                marcaidMarcaNew = em.getReference(marcaidMarcaNew.getClass(), marcaidMarcaNew.getIdMarca());
                impresora.setMarcaidMarca(marcaidMarcaNew);
            }
            if (modeloidModeloNew != null) {
                modeloidModeloNew = em.getReference(modeloidModeloNew.getClass(), modeloidModeloNew.getIdModelo());
                impresora.setModeloidModelo(modeloidModeloNew);
            }
            if (TTonnersnTonnerNew != null) {
                TTonnersnTonnerNew = em.getReference(TTonnersnTonnerNew.getClass(), TTonnersnTonnerNew.getSnTonner());
                impresora.setTTonnersnTonner(TTonnersnTonnerNew);
            }
            List<UsuariosCompartidos> attachedUsuariosCompartidosListNew = new ArrayList<UsuariosCompartidos>();
            for (UsuariosCompartidos usuariosCompartidosListNewUsuariosCompartidosToAttach : usuariosCompartidosListNew) {
                usuariosCompartidosListNewUsuariosCompartidosToAttach = em.getReference(usuariosCompartidosListNewUsuariosCompartidosToAttach.getClass(), usuariosCompartidosListNewUsuariosCompartidosToAttach.getNombreUsuariocomp());
                attachedUsuariosCompartidosListNew.add(usuariosCompartidosListNewUsuariosCompartidosToAttach);
            }
            usuariosCompartidosListNew = attachedUsuariosCompartidosListNew;
            impresora.setUsuariosCompartidosList(usuariosCompartidosListNew);
            List<RegDatosToner> attachedRegDatosTonerListNew = new ArrayList<RegDatosToner>();
            for (RegDatosToner regDatosTonerListNewRegDatosTonerToAttach : regDatosTonerListNew) {
                regDatosTonerListNewRegDatosTonerToAttach = em.getReference(regDatosTonerListNewRegDatosTonerToAttach.getClass(), regDatosTonerListNewRegDatosTonerToAttach.getIdRegToner());
                attachedRegDatosTonerListNew.add(regDatosTonerListNewRegDatosTonerToAttach);
            }
            regDatosTonerListNew = attachedRegDatosTonerListNew;
            impresora.setRegDatosTonerList(regDatosTonerListNew);
            impresora = em.merge(impresora);
            if (areaidAreaOld != null && !areaidAreaOld.equals(areaidAreaNew)) {
                areaidAreaOld.getImpresoraList().remove(impresora);
                areaidAreaOld = em.merge(areaidAreaOld);
            }
            if (areaidAreaNew != null && !areaidAreaNew.equals(areaidAreaOld)) {
                areaidAreaNew.getImpresoraList().add(impresora);
                areaidAreaNew = em.merge(areaidAreaNew);
            }
            if (departamentoidDepartamentoOld != null && !departamentoidDepartamentoOld.equals(departamentoidDepartamentoNew)) {
                departamentoidDepartamentoOld.getImpresoraList().remove(impresora);
                departamentoidDepartamentoOld = em.merge(departamentoidDepartamentoOld);
            }
            if (departamentoidDepartamentoNew != null && !departamentoidDepartamentoNew.equals(departamentoidDepartamentoOld)) {
                departamentoidDepartamentoNew.getImpresoraList().add(impresora);
                departamentoidDepartamentoNew = em.merge(departamentoidDepartamentoNew);
            }
            if (entidadidEntidadOld != null && !entidadidEntidadOld.equals(entidadidEntidadNew)) {
                entidadidEntidadOld.getImpresoraList().remove(impresora);
                entidadidEntidadOld = em.merge(entidadidEntidadOld);
            }
            if (entidadidEntidadNew != null && !entidadidEntidadNew.equals(entidadidEntidadOld)) {
                entidadidEntidadNew.getImpresoraList().add(impresora);
                entidadidEntidadNew = em.merge(entidadidEntidadNew);
            }
            if (estadoidEstadoOld != null && !estadoidEstadoOld.equals(estadoidEstadoNew)) {
                estadoidEstadoOld.getImpresoraList().remove(impresora);
                estadoidEstadoOld = em.merge(estadoidEstadoOld);
            }
            if (estadoidEstadoNew != null && !estadoidEstadoNew.equals(estadoidEstadoOld)) {
                estadoidEstadoNew.getImpresoraList().add(impresora);
                estadoidEstadoNew = em.merge(estadoidEstadoNew);
            }
            if (marcaidMarcaOld != null && !marcaidMarcaOld.equals(marcaidMarcaNew)) {
                marcaidMarcaOld.getImpresoraList().remove(impresora);
                marcaidMarcaOld = em.merge(marcaidMarcaOld);
            }
            if (marcaidMarcaNew != null && !marcaidMarcaNew.equals(marcaidMarcaOld)) {
                marcaidMarcaNew.getImpresoraList().add(impresora);
                marcaidMarcaNew = em.merge(marcaidMarcaNew);
            }
            if (modeloidModeloOld != null && !modeloidModeloOld.equals(modeloidModeloNew)) {
                modeloidModeloOld.getImpresoraList().remove(impresora);
                modeloidModeloOld = em.merge(modeloidModeloOld);
            }
            if (modeloidModeloNew != null && !modeloidModeloNew.equals(modeloidModeloOld)) {
                modeloidModeloNew.getImpresoraList().add(impresora);
                modeloidModeloNew = em.merge(modeloidModeloNew);
            }
            if (TTonnersnTonnerOld != null && !TTonnersnTonnerOld.equals(TTonnersnTonnerNew)) {
                TTonnersnTonnerOld.getImpresoraList().remove(impresora);
                TTonnersnTonnerOld = em.merge(TTonnersnTonnerOld);
            }
            if (TTonnersnTonnerNew != null && !TTonnersnTonnerNew.equals(TTonnersnTonnerOld)) {
                TTonnersnTonnerNew.getImpresoraList().add(impresora);
                TTonnersnTonnerNew = em.merge(TTonnersnTonnerNew);
            }
            for (UsuariosCompartidos usuariosCompartidosListOldUsuariosCompartidos : usuariosCompartidosListOld) {
                if (!usuariosCompartidosListNew.contains(usuariosCompartidosListOldUsuariosCompartidos)) {
                    usuariosCompartidosListOldUsuariosCompartidos.getImpresoraList().remove(impresora);
                    usuariosCompartidosListOldUsuariosCompartidos = em.merge(usuariosCompartidosListOldUsuariosCompartidos);
                }
            }
            for (UsuariosCompartidos usuariosCompartidosListNewUsuariosCompartidos : usuariosCompartidosListNew) {
                if (!usuariosCompartidosListOld.contains(usuariosCompartidosListNewUsuariosCompartidos)) {
                    usuariosCompartidosListNewUsuariosCompartidos.getImpresoraList().add(impresora);
                    usuariosCompartidosListNewUsuariosCompartidos = em.merge(usuariosCompartidosListNewUsuariosCompartidos);
                }
            }
            for (RegDatosToner regDatosTonerListNewRegDatosToner : regDatosTonerListNew) {
                if (!regDatosTonerListOld.contains(regDatosTonerListNewRegDatosToner)) {
                    Impresora oldImpresoranoInventarioOfRegDatosTonerListNewRegDatosToner = regDatosTonerListNewRegDatosToner.getImpresoranoInventario();
                    regDatosTonerListNewRegDatosToner.setImpresoranoInventario(impresora);
                    regDatosTonerListNewRegDatosToner = em.merge(regDatosTonerListNewRegDatosToner);
                    if (oldImpresoranoInventarioOfRegDatosTonerListNewRegDatosToner != null && !oldImpresoranoInventarioOfRegDatosTonerListNewRegDatosToner.equals(impresora)) {
                        oldImpresoranoInventarioOfRegDatosTonerListNewRegDatosToner.getRegDatosTonerList().remove(regDatosTonerListNewRegDatosToner);
                        oldImpresoranoInventarioOfRegDatosTonerListNewRegDatosToner = em.merge(oldImpresoranoInventarioOfRegDatosTonerListNewRegDatosToner);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = impresora.getNoInventario();
                if (findImpresora(id) == null) {
                    throw new NonexistentEntityException("The impresora with id " + id + " no longer exists.");
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
            Impresora impresora;
            try {
                impresora = em.getReference(Impresora.class, id);
                impresora.getNoInventario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The impresora with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RegDatosToner> regDatosTonerListOrphanCheck = impresora.getRegDatosTonerList();
            for (RegDatosToner regDatosTonerListOrphanCheckRegDatosToner : regDatosTonerListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Impresora (" + impresora + ") cannot be destroyed since the RegDatosToner " + regDatosTonerListOrphanCheckRegDatosToner + " in its regDatosTonerList field has a non-nullable impresoranoInventario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Area areaidArea = impresora.getAreaidArea();
            if (areaidArea != null) {
                areaidArea.getImpresoraList().remove(impresora);
                areaidArea = em.merge(areaidArea);
            }
            Departamento departamentoidDepartamento = impresora.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getImpresoraList().remove(impresora);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            Entidad entidadidEntidad = impresora.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad.getImpresoraList().remove(impresora);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            Estado estadoidEstado = impresora.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado.getImpresoraList().remove(impresora);
                estadoidEstado = em.merge(estadoidEstado);
            }
            Marca marcaidMarca = impresora.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca.getImpresoraList().remove(impresora);
                marcaidMarca = em.merge(marcaidMarca);
            }
            Modelo modeloidModelo = impresora.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo.getImpresoraList().remove(impresora);
                modeloidModelo = em.merge(modeloidModelo);
            }
            TTonner TTonnersnTonner = impresora.getTTonnersnTonner();
            if (TTonnersnTonner != null) {
                TTonnersnTonner.getImpresoraList().remove(impresora);
                TTonnersnTonner = em.merge(TTonnersnTonner);
            }
            List<UsuariosCompartidos> usuariosCompartidosList = impresora.getUsuariosCompartidosList();
            for (UsuariosCompartidos usuariosCompartidosListUsuariosCompartidos : usuariosCompartidosList) {
                usuariosCompartidosListUsuariosCompartidos.getImpresoraList().remove(impresora);
                usuariosCompartidosListUsuariosCompartidos = em.merge(usuariosCompartidosListUsuariosCompartidos);
            }
            em.remove(impresora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Impresora> findImpresoraEntities() {
        return findImpresoraEntities(true, -1, -1);
    }

    public List<Impresora> findImpresoraEntities(int maxResults, int firstResult) {
        return findImpresoraEntities(false, maxResults, firstResult);
    }

    private List<Impresora> findImpresoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Impresora.class));
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

    public Impresora findImpresora(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Impresora.class, id);
        } finally {
            em.close();
        }
    }

    public int getImpresoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Impresora> rt = cq.from(Impresora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
