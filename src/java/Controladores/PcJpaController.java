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
import Entidades.Modificacion;
import Entidades.Accesorio;
import Entidades.Area;
import Entidades.Componente;
import Entidades.Departamento;
import Entidades.Entidad;
import Entidades.Marca;
import Entidades.Modelo;
import Entidades.Pc;
import Entidades.Programas;
import Entidades.SO;
import Entidades.TEquipo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dayana
 */
public class PcJpaController implements Serializable {

    public PcJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pc pc) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modificacion modificaciontModificacionidTmodificacion = pc.getModificaciontModificacionidTmodificacion();
            if (modificaciontModificacionidTmodificacion != null) {
                modificaciontModificacionidTmodificacion = em.getReference(modificaciontModificacionidTmodificacion.getClass(), modificaciontModificacionidTmodificacion.getTModificacionidTmodificacion());
                pc.setModificaciontModificacionidTmodificacion(modificaciontModificacionidTmodificacion);
            }
            Accesorio accesoriosnAccesorio = pc.getAccesoriosnAccesorio();
            if (accesoriosnAccesorio != null) {
                accesoriosnAccesorio = em.getReference(accesoriosnAccesorio.getClass(), accesoriosnAccesorio.getSnAccesorio());
                pc.setAccesoriosnAccesorio(accesoriosnAccesorio);
            }
            Area areaidArea = pc.getAreaidArea();
            if (areaidArea != null) {
                areaidArea = em.getReference(areaidArea.getClass(), areaidArea.getIdArea());
                pc.setAreaidArea(areaidArea);
            }
            Componente componentesnComponente = pc.getComponentesnComponente();
            if (componentesnComponente != null) {
                componentesnComponente = em.getReference(componentesnComponente.getClass(), componentesnComponente.getSnComponente());
                pc.setComponentesnComponente(componentesnComponente);
            }
            Departamento departamentoidDepartamento = pc.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento = em.getReference(departamentoidDepartamento.getClass(), departamentoidDepartamento.getIdDepartamento());
                pc.setDepartamentoidDepartamento(departamentoidDepartamento);
            }
            Entidad entidadidEntidad = pc.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad = em.getReference(entidadidEntidad.getClass(), entidadidEntidad.getIdEntidad());
                pc.setEntidadidEntidad(entidadidEntidad);
            }
            Marca marcaidMarca = pc.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca = em.getReference(marcaidMarca.getClass(), marcaidMarca.getIdMarca());
                pc.setMarcaidMarca(marcaidMarca);
            }
            Modelo modeloidModelo = pc.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo = em.getReference(modeloidModelo.getClass(), modeloidModelo.getIdModelo());
                pc.setModeloidModelo(modeloidModelo);
            }
            Programas programasidProgramas = pc.getProgramasidProgramas();
            if (programasidProgramas != null) {
                programasidProgramas = em.getReference(programasidProgramas.getClass(), programasidProgramas.getIdProgramas());
                pc.setProgramasidProgramas(programasidProgramas);
            }
            SO SOidSo = pc.getSOidSo();
            if (SOidSo != null) {
                SOidSo = em.getReference(SOidSo.getClass(), SOidSo.getIdSo());
                pc.setSOidSo(SOidSo);
            }
            TEquipo TEquipoidEquipo = pc.getTEquipoidEquipo();
            if (TEquipoidEquipo != null) {
                TEquipoidEquipo = em.getReference(TEquipoidEquipo.getClass(), TEquipoidEquipo.getIdEquipo());
                pc.setTEquipoidEquipo(TEquipoidEquipo);
            }
            em.persist(pc);
            if (modificaciontModificacionidTmodificacion != null) {
                modificaciontModificacionidTmodificacion.getPcList().add(pc);
                modificaciontModificacionidTmodificacion = em.merge(modificaciontModificacionidTmodificacion);
            }
            if (accesoriosnAccesorio != null) {
                accesoriosnAccesorio.getPcList().add(pc);
                accesoriosnAccesorio = em.merge(accesoriosnAccesorio);
            }
            if (areaidArea != null) {
                areaidArea.getPcList().add(pc);
                areaidArea = em.merge(areaidArea);
            }
            if (componentesnComponente != null) {
                componentesnComponente.getPcList().add(pc);
                componentesnComponente = em.merge(componentesnComponente);
            }
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getPcList().add(pc);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            if (entidadidEntidad != null) {
                entidadidEntidad.getPcList().add(pc);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            if (marcaidMarca != null) {
                marcaidMarca.getPcList().add(pc);
                marcaidMarca = em.merge(marcaidMarca);
            }
            if (modeloidModelo != null) {
                modeloidModelo.getPcList().add(pc);
                modeloidModelo = em.merge(modeloidModelo);
            }
            if (programasidProgramas != null) {
                programasidProgramas.getPcList().add(pc);
                programasidProgramas = em.merge(programasidProgramas);
            }
            if (SOidSo != null) {
                SOidSo.getPcList().add(pc);
                SOidSo = em.merge(SOidSo);
            }
            if (TEquipoidEquipo != null) {
                TEquipoidEquipo.getPcList().add(pc);
                TEquipoidEquipo = em.merge(TEquipoidEquipo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPc(pc.getNoInventario()) != null) {
                throw new PreexistingEntityException("Pc " + pc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pc pc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pc persistentPc = em.find(Pc.class, pc.getNoInventario());
            Modificacion modificaciontModificacionidTmodificacionOld = persistentPc.getModificaciontModificacionidTmodificacion();
            Modificacion modificaciontModificacionidTmodificacionNew = pc.getModificaciontModificacionidTmodificacion();
            Accesorio accesoriosnAccesorioOld = persistentPc.getAccesoriosnAccesorio();
            Accesorio accesoriosnAccesorioNew = pc.getAccesoriosnAccesorio();
            Area areaidAreaOld = persistentPc.getAreaidArea();
            Area areaidAreaNew = pc.getAreaidArea();
            Componente componentesnComponenteOld = persistentPc.getComponentesnComponente();
            Componente componentesnComponenteNew = pc.getComponentesnComponente();
            Departamento departamentoidDepartamentoOld = persistentPc.getDepartamentoidDepartamento();
            Departamento departamentoidDepartamentoNew = pc.getDepartamentoidDepartamento();
            Entidad entidadidEntidadOld = persistentPc.getEntidadidEntidad();
            Entidad entidadidEntidadNew = pc.getEntidadidEntidad();
            Marca marcaidMarcaOld = persistentPc.getMarcaidMarca();
            Marca marcaidMarcaNew = pc.getMarcaidMarca();
            Modelo modeloidModeloOld = persistentPc.getModeloidModelo();
            Modelo modeloidModeloNew = pc.getModeloidModelo();
            Programas programasidProgramasOld = persistentPc.getProgramasidProgramas();
            Programas programasidProgramasNew = pc.getProgramasidProgramas();
            SO SOidSoOld = persistentPc.getSOidSo();
            SO SOidSoNew = pc.getSOidSo();
            TEquipo TEquipoidEquipoOld = persistentPc.getTEquipoidEquipo();
            TEquipo TEquipoidEquipoNew = pc.getTEquipoidEquipo();
            if (modificaciontModificacionidTmodificacionNew != null) {
                modificaciontModificacionidTmodificacionNew = em.getReference(modificaciontModificacionidTmodificacionNew.getClass(), modificaciontModificacionidTmodificacionNew.getTModificacionidTmodificacion());
                pc.setModificaciontModificacionidTmodificacion(modificaciontModificacionidTmodificacionNew);
            }
            if (accesoriosnAccesorioNew != null) {
                accesoriosnAccesorioNew = em.getReference(accesoriosnAccesorioNew.getClass(), accesoriosnAccesorioNew.getSnAccesorio());
                pc.setAccesoriosnAccesorio(accesoriosnAccesorioNew);
            }
            if (areaidAreaNew != null) {
                areaidAreaNew = em.getReference(areaidAreaNew.getClass(), areaidAreaNew.getIdArea());
                pc.setAreaidArea(areaidAreaNew);
            }
            if (componentesnComponenteNew != null) {
                componentesnComponenteNew = em.getReference(componentesnComponenteNew.getClass(), componentesnComponenteNew.getSnComponente());
                pc.setComponentesnComponente(componentesnComponenteNew);
            }
            if (departamentoidDepartamentoNew != null) {
                departamentoidDepartamentoNew = em.getReference(departamentoidDepartamentoNew.getClass(), departamentoidDepartamentoNew.getIdDepartamento());
                pc.setDepartamentoidDepartamento(departamentoidDepartamentoNew);
            }
            if (entidadidEntidadNew != null) {
                entidadidEntidadNew = em.getReference(entidadidEntidadNew.getClass(), entidadidEntidadNew.getIdEntidad());
                pc.setEntidadidEntidad(entidadidEntidadNew);
            }
            if (marcaidMarcaNew != null) {
                marcaidMarcaNew = em.getReference(marcaidMarcaNew.getClass(), marcaidMarcaNew.getIdMarca());
                pc.setMarcaidMarca(marcaidMarcaNew);
            }
            if (modeloidModeloNew != null) {
                modeloidModeloNew = em.getReference(modeloidModeloNew.getClass(), modeloidModeloNew.getIdModelo());
                pc.setModeloidModelo(modeloidModeloNew);
            }
            if (programasidProgramasNew != null) {
                programasidProgramasNew = em.getReference(programasidProgramasNew.getClass(), programasidProgramasNew.getIdProgramas());
                pc.setProgramasidProgramas(programasidProgramasNew);
            }
            if (SOidSoNew != null) {
                SOidSoNew = em.getReference(SOidSoNew.getClass(), SOidSoNew.getIdSo());
                pc.setSOidSo(SOidSoNew);
            }
            if (TEquipoidEquipoNew != null) {
                TEquipoidEquipoNew = em.getReference(TEquipoidEquipoNew.getClass(), TEquipoidEquipoNew.getIdEquipo());
                pc.setTEquipoidEquipo(TEquipoidEquipoNew);
            }
            pc = em.merge(pc);
            if (modificaciontModificacionidTmodificacionOld != null && !modificaciontModificacionidTmodificacionOld.equals(modificaciontModificacionidTmodificacionNew)) {
                modificaciontModificacionidTmodificacionOld.getPcList().remove(pc);
                modificaciontModificacionidTmodificacionOld = em.merge(modificaciontModificacionidTmodificacionOld);
            }
            if (modificaciontModificacionidTmodificacionNew != null && !modificaciontModificacionidTmodificacionNew.equals(modificaciontModificacionidTmodificacionOld)) {
                modificaciontModificacionidTmodificacionNew.getPcList().add(pc);
                modificaciontModificacionidTmodificacionNew = em.merge(modificaciontModificacionidTmodificacionNew);
            }
            if (accesoriosnAccesorioOld != null && !accesoriosnAccesorioOld.equals(accesoriosnAccesorioNew)) {
                accesoriosnAccesorioOld.getPcList().remove(pc);
                accesoriosnAccesorioOld = em.merge(accesoriosnAccesorioOld);
            }
            if (accesoriosnAccesorioNew != null && !accesoriosnAccesorioNew.equals(accesoriosnAccesorioOld)) {
                accesoriosnAccesorioNew.getPcList().add(pc);
                accesoriosnAccesorioNew = em.merge(accesoriosnAccesorioNew);
            }
            if (areaidAreaOld != null && !areaidAreaOld.equals(areaidAreaNew)) {
                areaidAreaOld.getPcList().remove(pc);
                areaidAreaOld = em.merge(areaidAreaOld);
            }
            if (areaidAreaNew != null && !areaidAreaNew.equals(areaidAreaOld)) {
                areaidAreaNew.getPcList().add(pc);
                areaidAreaNew = em.merge(areaidAreaNew);
            }
            if (componentesnComponenteOld != null && !componentesnComponenteOld.equals(componentesnComponenteNew)) {
                componentesnComponenteOld.getPcList().remove(pc);
                componentesnComponenteOld = em.merge(componentesnComponenteOld);
            }
            if (componentesnComponenteNew != null && !componentesnComponenteNew.equals(componentesnComponenteOld)) {
                componentesnComponenteNew.getPcList().add(pc);
                componentesnComponenteNew = em.merge(componentesnComponenteNew);
            }
            if (departamentoidDepartamentoOld != null && !departamentoidDepartamentoOld.equals(departamentoidDepartamentoNew)) {
                departamentoidDepartamentoOld.getPcList().remove(pc);
                departamentoidDepartamentoOld = em.merge(departamentoidDepartamentoOld);
            }
            if (departamentoidDepartamentoNew != null && !departamentoidDepartamentoNew.equals(departamentoidDepartamentoOld)) {
                departamentoidDepartamentoNew.getPcList().add(pc);
                departamentoidDepartamentoNew = em.merge(departamentoidDepartamentoNew);
            }
            if (entidadidEntidadOld != null && !entidadidEntidadOld.equals(entidadidEntidadNew)) {
                entidadidEntidadOld.getPcList().remove(pc);
                entidadidEntidadOld = em.merge(entidadidEntidadOld);
            }
            if (entidadidEntidadNew != null && !entidadidEntidadNew.equals(entidadidEntidadOld)) {
                entidadidEntidadNew.getPcList().add(pc);
                entidadidEntidadNew = em.merge(entidadidEntidadNew);
            }
            if (marcaidMarcaOld != null && !marcaidMarcaOld.equals(marcaidMarcaNew)) {
                marcaidMarcaOld.getPcList().remove(pc);
                marcaidMarcaOld = em.merge(marcaidMarcaOld);
            }
            if (marcaidMarcaNew != null && !marcaidMarcaNew.equals(marcaidMarcaOld)) {
                marcaidMarcaNew.getPcList().add(pc);
                marcaidMarcaNew = em.merge(marcaidMarcaNew);
            }
            if (modeloidModeloOld != null && !modeloidModeloOld.equals(modeloidModeloNew)) {
                modeloidModeloOld.getPcList().remove(pc);
                modeloidModeloOld = em.merge(modeloidModeloOld);
            }
            if (modeloidModeloNew != null && !modeloidModeloNew.equals(modeloidModeloOld)) {
                modeloidModeloNew.getPcList().add(pc);
                modeloidModeloNew = em.merge(modeloidModeloNew);
            }
            if (programasidProgramasOld != null && !programasidProgramasOld.equals(programasidProgramasNew)) {
                programasidProgramasOld.getPcList().remove(pc);
                programasidProgramasOld = em.merge(programasidProgramasOld);
            }
            if (programasidProgramasNew != null && !programasidProgramasNew.equals(programasidProgramasOld)) {
                programasidProgramasNew.getPcList().add(pc);
                programasidProgramasNew = em.merge(programasidProgramasNew);
            }
            if (SOidSoOld != null && !SOidSoOld.equals(SOidSoNew)) {
                SOidSoOld.getPcList().remove(pc);
                SOidSoOld = em.merge(SOidSoOld);
            }
            if (SOidSoNew != null && !SOidSoNew.equals(SOidSoOld)) {
                SOidSoNew.getPcList().add(pc);
                SOidSoNew = em.merge(SOidSoNew);
            }
            if (TEquipoidEquipoOld != null && !TEquipoidEquipoOld.equals(TEquipoidEquipoNew)) {
                TEquipoidEquipoOld.getPcList().remove(pc);
                TEquipoidEquipoOld = em.merge(TEquipoidEquipoOld);
            }
            if (TEquipoidEquipoNew != null && !TEquipoidEquipoNew.equals(TEquipoidEquipoOld)) {
                TEquipoidEquipoNew.getPcList().add(pc);
                TEquipoidEquipoNew = em.merge(TEquipoidEquipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pc.getNoInventario();
                if (findPc(id) == null) {
                    throw new NonexistentEntityException("The pc with id " + id + " no longer exists.");
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
            Pc pc;
            try {
                pc = em.getReference(Pc.class, id);
                pc.getNoInventario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pc with id " + id + " no longer exists.", enfe);
            }
            Modificacion modificaciontModificacionidTmodificacion = pc.getModificaciontModificacionidTmodificacion();
            if (modificaciontModificacionidTmodificacion != null) {
                modificaciontModificacionidTmodificacion.getPcList().remove(pc);
                modificaciontModificacionidTmodificacion = em.merge(modificaciontModificacionidTmodificacion);
            }
            Accesorio accesoriosnAccesorio = pc.getAccesoriosnAccesorio();
            if (accesoriosnAccesorio != null) {
                accesoriosnAccesorio.getPcList().remove(pc);
                accesoriosnAccesorio = em.merge(accesoriosnAccesorio);
            }
            Area areaidArea = pc.getAreaidArea();
            if (areaidArea != null) {
                areaidArea.getPcList().remove(pc);
                areaidArea = em.merge(areaidArea);
            }
            Componente componentesnComponente = pc.getComponentesnComponente();
            if (componentesnComponente != null) {
                componentesnComponente.getPcList().remove(pc);
                componentesnComponente = em.merge(componentesnComponente);
            }
            Departamento departamentoidDepartamento = pc.getDepartamentoidDepartamento();
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getPcList().remove(pc);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            Entidad entidadidEntidad = pc.getEntidadidEntidad();
            if (entidadidEntidad != null) {
                entidadidEntidad.getPcList().remove(pc);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            Marca marcaidMarca = pc.getMarcaidMarca();
            if (marcaidMarca != null) {
                marcaidMarca.getPcList().remove(pc);
                marcaidMarca = em.merge(marcaidMarca);
            }
            Modelo modeloidModelo = pc.getModeloidModelo();
            if (modeloidModelo != null) {
                modeloidModelo.getPcList().remove(pc);
                modeloidModelo = em.merge(modeloidModelo);
            }
            Programas programasidProgramas = pc.getProgramasidProgramas();
            if (programasidProgramas != null) {
                programasidProgramas.getPcList().remove(pc);
                programasidProgramas = em.merge(programasidProgramas);
            }
            SO SOidSo = pc.getSOidSo();
            if (SOidSo != null) {
                SOidSo.getPcList().remove(pc);
                SOidSo = em.merge(SOidSo);
            }
            TEquipo TEquipoidEquipo = pc.getTEquipoidEquipo();
            if (TEquipoidEquipo != null) {
                TEquipoidEquipo.getPcList().remove(pc);
                TEquipoidEquipo = em.merge(TEquipoidEquipo);
            }
            em.remove(pc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pc> findPcEntities() {
        return findPcEntities(true, -1, -1);
    }

    public List<Pc> findPcEntities(int maxResults, int firstResult) {
        return findPcEntities(false, maxResults, firstResult);
    }

    private List<Pc> findPcEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pc.class));
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

    public Pc findPc(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pc.class, id);
        } finally {
            em.close();
        }
    }

    public int getPcCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pc> rt = cq.from(Pc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
