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
import Entidades.Componente;
import Entidades.Modificacion;
import Entidades.Pc;
import Entidades.TModificacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class ModificacionJpaController implements Serializable {

    public ModificacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modificacion modificacion) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        TModificacion TModificacionOrphanCheck = modificacion.getTModificacion();
        if (TModificacionOrphanCheck != null) {
            Modificacion oldModificacionOfTModificacion = TModificacionOrphanCheck.getModificacion();
            if (oldModificacionOfTModificacion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TModificacion " + TModificacionOrphanCheck + " already has an item of type Modificacion whose TModificacion column cannot be null. Please make another selection for the TModificacion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Componente componentesnComponente = modificacion.getComponentesnComponente();
            if (componentesnComponente != null) {
                componentesnComponente = em.getReference(componentesnComponente.getClass(), componentesnComponente.getSnComponente());
                modificacion.setComponentesnComponente(componentesnComponente);
            }
            Pc pcnoInventario = modificacion.getPcnoInventario();
            if (pcnoInventario != null) {
                pcnoInventario = em.getReference(pcnoInventario.getClass(), pcnoInventario.getNoInventario());
                modificacion.setPcnoInventario(pcnoInventario);
            }
            TModificacion TModificacion = modificacion.getTModificacion();
            if (TModificacion != null) {
                TModificacion = em.getReference(TModificacion.getClass(), TModificacion.getIdTmodificacion());
                modificacion.setTModificacion(TModificacion);
            }
            em.persist(modificacion);
            if (componentesnComponente != null) {
                componentesnComponente.getModificacionList().add(modificacion);
                componentesnComponente = em.merge(componentesnComponente);
            }
            if (pcnoInventario != null) {
                pcnoInventario.getModificacionList().add(modificacion);
                pcnoInventario = em.merge(pcnoInventario);
            }
            if (TModificacion != null) {
                TModificacion.setModificacion(modificacion);
                TModificacion = em.merge(TModificacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModificacion(modificacion.getTModificacionidTmodificacion()) != null) {
                throw new PreexistingEntityException("Modificacion " + modificacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modificacion modificacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modificacion persistentModificacion = em.find(Modificacion.class, modificacion.getTModificacionidTmodificacion());
            Componente componentesnComponenteOld = persistentModificacion.getComponentesnComponente();
            Componente componentesnComponenteNew = modificacion.getComponentesnComponente();
            Pc pcnoInventarioOld = persistentModificacion.getPcnoInventario();
            Pc pcnoInventarioNew = modificacion.getPcnoInventario();
            TModificacion TModificacionOld = persistentModificacion.getTModificacion();
            TModificacion TModificacionNew = modificacion.getTModificacion();
            List<String> illegalOrphanMessages = null;
            if (TModificacionNew != null && !TModificacionNew.equals(TModificacionOld)) {
                Modificacion oldModificacionOfTModificacion = TModificacionNew.getModificacion();
                if (oldModificacionOfTModificacion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TModificacion " + TModificacionNew + " already has an item of type Modificacion whose TModificacion column cannot be null. Please make another selection for the TModificacion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (componentesnComponenteNew != null) {
                componentesnComponenteNew = em.getReference(componentesnComponenteNew.getClass(), componentesnComponenteNew.getSnComponente());
                modificacion.setComponentesnComponente(componentesnComponenteNew);
            }
            if (pcnoInventarioNew != null) {
                pcnoInventarioNew = em.getReference(pcnoInventarioNew.getClass(), pcnoInventarioNew.getNoInventario());
                modificacion.setPcnoInventario(pcnoInventarioNew);
            }
            if (TModificacionNew != null) {
                TModificacionNew = em.getReference(TModificacionNew.getClass(), TModificacionNew.getIdTmodificacion());
                modificacion.setTModificacion(TModificacionNew);
            }
            modificacion = em.merge(modificacion);
            if (componentesnComponenteOld != null && !componentesnComponenteOld.equals(componentesnComponenteNew)) {
                componentesnComponenteOld.getModificacionList().remove(modificacion);
                componentesnComponenteOld = em.merge(componentesnComponenteOld);
            }
            if (componentesnComponenteNew != null && !componentesnComponenteNew.equals(componentesnComponenteOld)) {
                componentesnComponenteNew.getModificacionList().add(modificacion);
                componentesnComponenteNew = em.merge(componentesnComponenteNew);
            }
            if (pcnoInventarioOld != null && !pcnoInventarioOld.equals(pcnoInventarioNew)) {
                pcnoInventarioOld.getModificacionList().remove(modificacion);
                pcnoInventarioOld = em.merge(pcnoInventarioOld);
            }
            if (pcnoInventarioNew != null && !pcnoInventarioNew.equals(pcnoInventarioOld)) {
                pcnoInventarioNew.getModificacionList().add(modificacion);
                pcnoInventarioNew = em.merge(pcnoInventarioNew);
            }
            if (TModificacionOld != null && !TModificacionOld.equals(TModificacionNew)) {
                TModificacionOld.setModificacion(null);
                TModificacionOld = em.merge(TModificacionOld);
            }
            if (TModificacionNew != null && !TModificacionNew.equals(TModificacionOld)) {
                TModificacionNew.setModificacion(modificacion);
                TModificacionNew = em.merge(TModificacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modificacion.getTModificacionidTmodificacion();
                if (findModificacion(id) == null) {
                    throw new NonexistentEntityException("The modificacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modificacion modificacion;
            try {
                modificacion = em.getReference(Modificacion.class, id);
                modificacion.getTModificacionidTmodificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modificacion with id " + id + " no longer exists.", enfe);
            }
            Componente componentesnComponente = modificacion.getComponentesnComponente();
            if (componentesnComponente != null) {
                componentesnComponente.getModificacionList().remove(modificacion);
                componentesnComponente = em.merge(componentesnComponente);
            }
            Pc pcnoInventario = modificacion.getPcnoInventario();
            if (pcnoInventario != null) {
                pcnoInventario.getModificacionList().remove(modificacion);
                pcnoInventario = em.merge(pcnoInventario);
            }
            TModificacion TModificacion = modificacion.getTModificacion();
            if (TModificacion != null) {
                TModificacion.setModificacion(null);
                TModificacion = em.merge(TModificacion);
            }
            em.remove(modificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modificacion> findModificacionEntities() {
        return findModificacionEntities(true, -1, -1);
    }

    public List<Modificacion> findModificacionEntities(int maxResults, int firstResult) {
        return findModificacionEntities(false, maxResults, firstResult);
    }

    private List<Modificacion> findModificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modificacion.class));
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

    public Modificacion findModificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getModificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modificacion> rt = cq.from(Modificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
