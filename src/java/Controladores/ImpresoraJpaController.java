/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

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
import Entidades.Impresora;
import Entidades.Marca;
import Entidades.Modelo;
import Entidades.TTonner;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dayana
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

    public void edit(Impresora impresora) throws NonexistentEntityException, Exception {
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
            Marca marcaidMarcaOld = persistentImpresora.getMarcaidMarca();
            Marca marcaidMarcaNew = impresora.getMarcaidMarca();
            Modelo modeloidModeloOld = persistentImpresora.getModeloidModelo();
            Modelo modeloidModeloNew = impresora.getModeloidModelo();
            TTonner TTonnersnTonnerOld = persistentImpresora.getTTonnersnTonner();
            TTonner TTonnersnTonnerNew = impresora.getTTonnersnTonner();
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

    public void destroy(String id) throws NonexistentEntityException {
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
