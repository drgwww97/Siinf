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
import Entidades.Estado;
import Entidades.Marca;
import Entidades.Modelo;
import Entidades.SO;
import Entidades.TEquipo;
import Entidades.Programas;
import java.util.ArrayList;
import java.util.List;
import Entidades.Accesorio;
import Entidades.Componente;
import Entidades.Modificacion;
import Entidades.Pc;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
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
        if (pc.getProgramasList() == null) {
            pc.setProgramasList(new ArrayList<Programas>());
        }
        if (pc.getAccesorioList() == null) {
            pc.setAccesorioList(new ArrayList<Accesorio>());
        }
        if (pc.getComponenteList() == null) {
            pc.setComponenteList(new ArrayList<Componente>());
        }
        if (pc.getModificacionList() == null) {
            pc.setModificacionList(new ArrayList<Modificacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area areaidArea = pc.getAreaidArea();
            if (areaidArea != null) {
                areaidArea = em.getReference(areaidArea.getClass(), areaidArea.getIdArea());
                pc.setAreaidArea(areaidArea);
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
            Estado estadoidEstado = pc.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado = em.getReference(estadoidEstado.getClass(), estadoidEstado.getIdEstado());
                pc.setEstadoidEstado(estadoidEstado);
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
            List<Programas> attachedProgramasList = new ArrayList<Programas>();
            for (Programas programasListProgramasToAttach : pc.getProgramasList()) {
                programasListProgramasToAttach = em.getReference(programasListProgramasToAttach.getClass(), programasListProgramasToAttach.getIdProgramas());
                attachedProgramasList.add(programasListProgramasToAttach);
            }
            pc.setProgramasList(attachedProgramasList);
            List<Accesorio> attachedAccesorioList = new ArrayList<Accesorio>();
            for (Accesorio accesorioListAccesorioToAttach : pc.getAccesorioList()) {
                accesorioListAccesorioToAttach = em.getReference(accesorioListAccesorioToAttach.getClass(), accesorioListAccesorioToAttach.getSnAccesorio());
                attachedAccesorioList.add(accesorioListAccesorioToAttach);
            }
            pc.setAccesorioList(attachedAccesorioList);
            List<Componente> attachedComponenteList = new ArrayList<Componente>();
            for (Componente componenteListComponenteToAttach : pc.getComponenteList()) {
                componenteListComponenteToAttach = em.getReference(componenteListComponenteToAttach.getClass(), componenteListComponenteToAttach.getSnComponente());
                attachedComponenteList.add(componenteListComponenteToAttach);
            }
            pc.setComponenteList(attachedComponenteList);
            List<Modificacion> attachedModificacionList = new ArrayList<Modificacion>();
            for (Modificacion modificacionListModificacionToAttach : pc.getModificacionList()) {
                modificacionListModificacionToAttach = em.getReference(modificacionListModificacionToAttach.getClass(), modificacionListModificacionToAttach.getTModificacionidTmodificacion());
                attachedModificacionList.add(modificacionListModificacionToAttach);
            }
            pc.setModificacionList(attachedModificacionList);
            em.persist(pc);
            if (areaidArea != null) {
                areaidArea.getPcList().add(pc);
                areaidArea = em.merge(areaidArea);
            }
            if (departamentoidDepartamento != null) {
                departamentoidDepartamento.getPcList().add(pc);
                departamentoidDepartamento = em.merge(departamentoidDepartamento);
            }
            if (entidadidEntidad != null) {
                entidadidEntidad.getPcList().add(pc);
                entidadidEntidad = em.merge(entidadidEntidad);
            }
            if (estadoidEstado != null) {
                estadoidEstado.getPcList().add(pc);
                estadoidEstado = em.merge(estadoidEstado);
            }
            if (marcaidMarca != null) {
                marcaidMarca.getPcList().add(pc);
                marcaidMarca = em.merge(marcaidMarca);
            }
            if (modeloidModelo != null) {
                modeloidModelo.getPcList().add(pc);
                modeloidModelo = em.merge(modeloidModelo);
            }
            if (SOidSo != null) {
                SOidSo.getPcList().add(pc);
                SOidSo = em.merge(SOidSo);
            }
            if (TEquipoidEquipo != null) {
                TEquipoidEquipo.getPcList().add(pc);
                TEquipoidEquipo = em.merge(TEquipoidEquipo);
            }
            for (Programas programasListProgramas : pc.getProgramasList()) {
                programasListProgramas.getPcList().add(pc);
                programasListProgramas = em.merge(programasListProgramas);
            }
            for (Accesorio accesorioListAccesorio : pc.getAccesorioList()) {
                Pc oldPcnoInventarioOfAccesorioListAccesorio = accesorioListAccesorio.getPcnoInventario();
                accesorioListAccesorio.setPcnoInventario(pc);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
                if (oldPcnoInventarioOfAccesorioListAccesorio != null) {
                    oldPcnoInventarioOfAccesorioListAccesorio.getAccesorioList().remove(accesorioListAccesorio);
                    oldPcnoInventarioOfAccesorioListAccesorio = em.merge(oldPcnoInventarioOfAccesorioListAccesorio);
                }
            }
            for (Componente componenteListComponente : pc.getComponenteList()) {
                Pc oldPcnoInventarioOfComponenteListComponente = componenteListComponente.getPcnoInventario();
                componenteListComponente.setPcnoInventario(pc);
                componenteListComponente = em.merge(componenteListComponente);
                if (oldPcnoInventarioOfComponenteListComponente != null) {
                    oldPcnoInventarioOfComponenteListComponente.getComponenteList().remove(componenteListComponente);
                    oldPcnoInventarioOfComponenteListComponente = em.merge(oldPcnoInventarioOfComponenteListComponente);
                }
            }
            for (Modificacion modificacionListModificacion : pc.getModificacionList()) {
                Pc oldPcnoInventarioOfModificacionListModificacion = modificacionListModificacion.getPcnoInventario();
                modificacionListModificacion.setPcnoInventario(pc);
                modificacionListModificacion = em.merge(modificacionListModificacion);
                if (oldPcnoInventarioOfModificacionListModificacion != null) {
                    oldPcnoInventarioOfModificacionListModificacion.getModificacionList().remove(modificacionListModificacion);
                    oldPcnoInventarioOfModificacionListModificacion = em.merge(oldPcnoInventarioOfModificacionListModificacion);
                }
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
            Area areaidAreaOld = persistentPc.getAreaidArea();
            Area areaidAreaNew = pc.getAreaidArea();
            Departamento departamentoidDepartamentoOld = persistentPc.getDepartamentoidDepartamento();
            Departamento departamentoidDepartamentoNew = pc.getDepartamentoidDepartamento();
            Entidad entidadidEntidadOld = persistentPc.getEntidadidEntidad();
            Entidad entidadidEntidadNew = pc.getEntidadidEntidad();
            Estado estadoidEstadoOld = persistentPc.getEstadoidEstado();
            Estado estadoidEstadoNew = pc.getEstadoidEstado();
            Marca marcaidMarcaOld = persistentPc.getMarcaidMarca();
            Marca marcaidMarcaNew = pc.getMarcaidMarca();
            Modelo modeloidModeloOld = persistentPc.getModeloidModelo();
            Modelo modeloidModeloNew = pc.getModeloidModelo();
            SO SOidSoOld = persistentPc.getSOidSo();
            SO SOidSoNew = pc.getSOidSo();
            TEquipo TEquipoidEquipoOld = persistentPc.getTEquipoidEquipo();
            TEquipo TEquipoidEquipoNew = pc.getTEquipoidEquipo();
            List<Programas> programasListOld = persistentPc.getProgramasList();
            List<Programas> programasListNew = pc.getProgramasList();
            List<Accesorio> accesorioListOld = persistentPc.getAccesorioList();
            List<Accesorio> accesorioListNew = pc.getAccesorioList();
            List<Componente> componenteListOld = persistentPc.getComponenteList();
            List<Componente> componenteListNew = pc.getComponenteList();
            List<Modificacion> modificacionListOld = persistentPc.getModificacionList();
            List<Modificacion> modificacionListNew = pc.getModificacionList();
            if (areaidAreaNew != null) {
                areaidAreaNew = em.getReference(areaidAreaNew.getClass(), areaidAreaNew.getIdArea());
                pc.setAreaidArea(areaidAreaNew);
            }
            if (departamentoidDepartamentoNew != null) {
                departamentoidDepartamentoNew = em.getReference(departamentoidDepartamentoNew.getClass(), departamentoidDepartamentoNew.getIdDepartamento());
                pc.setDepartamentoidDepartamento(departamentoidDepartamentoNew);
            }
            if (entidadidEntidadNew != null) {
                entidadidEntidadNew = em.getReference(entidadidEntidadNew.getClass(), entidadidEntidadNew.getIdEntidad());
                pc.setEntidadidEntidad(entidadidEntidadNew);
            }
            if (estadoidEstadoNew != null) {
                estadoidEstadoNew = em.getReference(estadoidEstadoNew.getClass(), estadoidEstadoNew.getIdEstado());
                pc.setEstadoidEstado(estadoidEstadoNew);
            }
            if (marcaidMarcaNew != null) {
                marcaidMarcaNew = em.getReference(marcaidMarcaNew.getClass(), marcaidMarcaNew.getIdMarca());
                pc.setMarcaidMarca(marcaidMarcaNew);
            }
            if (modeloidModeloNew != null) {
                modeloidModeloNew = em.getReference(modeloidModeloNew.getClass(), modeloidModeloNew.getIdModelo());
                pc.setModeloidModelo(modeloidModeloNew);
            }
            if (SOidSoNew != null) {
                SOidSoNew = em.getReference(SOidSoNew.getClass(), SOidSoNew.getIdSo());
                pc.setSOidSo(SOidSoNew);
            }
            if (TEquipoidEquipoNew != null) {
                TEquipoidEquipoNew = em.getReference(TEquipoidEquipoNew.getClass(), TEquipoidEquipoNew.getIdEquipo());
                pc.setTEquipoidEquipo(TEquipoidEquipoNew);
            }
            List<Programas> attachedProgramasListNew = new ArrayList<Programas>();
            for (Programas programasListNewProgramasToAttach : programasListNew) {
                programasListNewProgramasToAttach = em.getReference(programasListNewProgramasToAttach.getClass(), programasListNewProgramasToAttach.getIdProgramas());
                attachedProgramasListNew.add(programasListNewProgramasToAttach);
            }
            programasListNew = attachedProgramasListNew;
            pc.setProgramasList(programasListNew);
            List<Accesorio> attachedAccesorioListNew = new ArrayList<Accesorio>();
            for (Accesorio accesorioListNewAccesorioToAttach : accesorioListNew) {
                accesorioListNewAccesorioToAttach = em.getReference(accesorioListNewAccesorioToAttach.getClass(), accesorioListNewAccesorioToAttach.getSnAccesorio());
                attachedAccesorioListNew.add(accesorioListNewAccesorioToAttach);
            }
            accesorioListNew = attachedAccesorioListNew;
            pc.setAccesorioList(accesorioListNew);
            List<Componente> attachedComponenteListNew = new ArrayList<Componente>();
            for (Componente componenteListNewComponenteToAttach : componenteListNew) {
                componenteListNewComponenteToAttach = em.getReference(componenteListNewComponenteToAttach.getClass(), componenteListNewComponenteToAttach.getSnComponente());
                attachedComponenteListNew.add(componenteListNewComponenteToAttach);
            }
            componenteListNew = attachedComponenteListNew;
            pc.setComponenteList(componenteListNew);
            List<Modificacion> attachedModificacionListNew = new ArrayList<Modificacion>();
            for (Modificacion modificacionListNewModificacionToAttach : modificacionListNew) {
                modificacionListNewModificacionToAttach = em.getReference(modificacionListNewModificacionToAttach.getClass(), modificacionListNewModificacionToAttach.getTModificacionidTmodificacion());
                attachedModificacionListNew.add(modificacionListNewModificacionToAttach);
            }
            modificacionListNew = attachedModificacionListNew;
            pc.setModificacionList(modificacionListNew);
            pc = em.merge(pc);
            if (areaidAreaOld != null && !areaidAreaOld.equals(areaidAreaNew)) {
                areaidAreaOld.getPcList().remove(pc);
                areaidAreaOld = em.merge(areaidAreaOld);
            }
            if (areaidAreaNew != null && !areaidAreaNew.equals(areaidAreaOld)) {
                areaidAreaNew.getPcList().add(pc);
                areaidAreaNew = em.merge(areaidAreaNew);
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
            if (estadoidEstadoOld != null && !estadoidEstadoOld.equals(estadoidEstadoNew)) {
                estadoidEstadoOld.getPcList().remove(pc);
                estadoidEstadoOld = em.merge(estadoidEstadoOld);
            }
            if (estadoidEstadoNew != null && !estadoidEstadoNew.equals(estadoidEstadoOld)) {
                estadoidEstadoNew.getPcList().add(pc);
                estadoidEstadoNew = em.merge(estadoidEstadoNew);
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
            for (Programas programasListOldProgramas : programasListOld) {
                if (!programasListNew.contains(programasListOldProgramas)) {
                    programasListOldProgramas.getPcList().remove(pc);
                    programasListOldProgramas = em.merge(programasListOldProgramas);
                }
            }
            for (Programas programasListNewProgramas : programasListNew) {
                if (!programasListOld.contains(programasListNewProgramas)) {
                    programasListNewProgramas.getPcList().add(pc);
                    programasListNewProgramas = em.merge(programasListNewProgramas);
                }
            }
            for (Accesorio accesorioListOldAccesorio : accesorioListOld) {
                if (!accesorioListNew.contains(accesorioListOldAccesorio)) {
                    accesorioListOldAccesorio.setPcnoInventario(null);
                    accesorioListOldAccesorio = em.merge(accesorioListOldAccesorio);
                }
            }
            for (Accesorio accesorioListNewAccesorio : accesorioListNew) {
                if (!accesorioListOld.contains(accesorioListNewAccesorio)) {
                    Pc oldPcnoInventarioOfAccesorioListNewAccesorio = accesorioListNewAccesorio.getPcnoInventario();
                    accesorioListNewAccesorio.setPcnoInventario(pc);
                    accesorioListNewAccesorio = em.merge(accesorioListNewAccesorio);
                    if (oldPcnoInventarioOfAccesorioListNewAccesorio != null && !oldPcnoInventarioOfAccesorioListNewAccesorio.equals(pc)) {
                        oldPcnoInventarioOfAccesorioListNewAccesorio.getAccesorioList().remove(accesorioListNewAccesorio);
                        oldPcnoInventarioOfAccesorioListNewAccesorio = em.merge(oldPcnoInventarioOfAccesorioListNewAccesorio);
                    }
                }
            }
            for (Componente componenteListOldComponente : componenteListOld) {
                if (!componenteListNew.contains(componenteListOldComponente)) {
                    componenteListOldComponente.setPcnoInventario(null);
                    componenteListOldComponente = em.merge(componenteListOldComponente);
                }
            }
            for (Componente componenteListNewComponente : componenteListNew) {
                if (!componenteListOld.contains(componenteListNewComponente)) {
                    Pc oldPcnoInventarioOfComponenteListNewComponente = componenteListNewComponente.getPcnoInventario();
                    componenteListNewComponente.setPcnoInventario(pc);
                    componenteListNewComponente = em.merge(componenteListNewComponente);
                    if (oldPcnoInventarioOfComponenteListNewComponente != null && !oldPcnoInventarioOfComponenteListNewComponente.equals(pc)) {
                        oldPcnoInventarioOfComponenteListNewComponente.getComponenteList().remove(componenteListNewComponente);
                        oldPcnoInventarioOfComponenteListNewComponente = em.merge(oldPcnoInventarioOfComponenteListNewComponente);
                    }
                }
            }
            for (Modificacion modificacionListOldModificacion : modificacionListOld) {
                if (!modificacionListNew.contains(modificacionListOldModificacion)) {
                    modificacionListOldModificacion.setPcnoInventario(null);
                    modificacionListOldModificacion = em.merge(modificacionListOldModificacion);
                }
            }
            for (Modificacion modificacionListNewModificacion : modificacionListNew) {
                if (!modificacionListOld.contains(modificacionListNewModificacion)) {
                    Pc oldPcnoInventarioOfModificacionListNewModificacion = modificacionListNewModificacion.getPcnoInventario();
                    modificacionListNewModificacion.setPcnoInventario(pc);
                    modificacionListNewModificacion = em.merge(modificacionListNewModificacion);
                    if (oldPcnoInventarioOfModificacionListNewModificacion != null && !oldPcnoInventarioOfModificacionListNewModificacion.equals(pc)) {
                        oldPcnoInventarioOfModificacionListNewModificacion.getModificacionList().remove(modificacionListNewModificacion);
                        oldPcnoInventarioOfModificacionListNewModificacion = em.merge(oldPcnoInventarioOfModificacionListNewModificacion);
                    }
                }
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
            Area areaidArea = pc.getAreaidArea();
            if (areaidArea != null) {
                areaidArea.getPcList().remove(pc);
                areaidArea = em.merge(areaidArea);
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
            Estado estadoidEstado = pc.getEstadoidEstado();
            if (estadoidEstado != null) {
                estadoidEstado.getPcList().remove(pc);
                estadoidEstado = em.merge(estadoidEstado);
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
            List<Programas> programasList = pc.getProgramasList();
            for (Programas programasListProgramas : programasList) {
                programasListProgramas.getPcList().remove(pc);
                programasListProgramas = em.merge(programasListProgramas);
            }
            List<Accesorio> accesorioList = pc.getAccesorioList();
            for (Accesorio accesorioListAccesorio : accesorioList) {
                accesorioListAccesorio.setPcnoInventario(null);
                accesorioListAccesorio = em.merge(accesorioListAccesorio);
            }
            List<Componente> componenteList = pc.getComponenteList();
            for (Componente componenteListComponente : componenteList) {
                componenteListComponente.setPcnoInventario(null);
                componenteListComponente = em.merge(componenteListComponente);
            }
            List<Modificacion> modificacionList = pc.getModificacionList();
            for (Modificacion modificacionListModificacion : modificacionList) {
                modificacionListModificacion.setPcnoInventario(null);
                modificacionListModificacion = em.merge(modificacionListModificacion);
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
