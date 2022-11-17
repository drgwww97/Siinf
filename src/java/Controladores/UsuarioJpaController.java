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
import Entidades.Login;
import Entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getLoginList() == null) {
            usuario.setLoginList(new ArrayList<Login>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Login> attachedLoginList = new ArrayList<Login>();
            for (Login loginListLoginToAttach : usuario.getLoginList()) {
                loginListLoginToAttach = em.getReference(loginListLoginToAttach.getClass(), loginListLoginToAttach.getLoginPK());
                attachedLoginList.add(loginListLoginToAttach);
            }
            usuario.setLoginList(attachedLoginList);
            em.persist(usuario);
            for (Login loginListLogin : usuario.getLoginList()) {
                Usuario oldUsuarioOfLoginListLogin = loginListLogin.getUsuario();
                loginListLogin.setUsuario(usuario);
                loginListLogin = em.merge(loginListLogin);
                if (oldUsuarioOfLoginListLogin != null) {
                    oldUsuarioOfLoginListLogin.getLoginList().remove(loginListLogin);
                    oldUsuarioOfLoginListLogin = em.merge(oldUsuarioOfLoginListLogin);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuario());
            List<Login> loginListOld = persistentUsuario.getLoginList();
            List<Login> loginListNew = usuario.getLoginList();
            List<String> illegalOrphanMessages = null;
            for (Login loginListOldLogin : loginListOld) {
                if (!loginListNew.contains(loginListOldLogin)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Login " + loginListOldLogin + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Login> attachedLoginListNew = new ArrayList<Login>();
            for (Login loginListNewLoginToAttach : loginListNew) {
                loginListNewLoginToAttach = em.getReference(loginListNewLoginToAttach.getClass(), loginListNewLoginToAttach.getLoginPK());
                attachedLoginListNew.add(loginListNewLoginToAttach);
            }
            loginListNew = attachedLoginListNew;
            usuario.setLoginList(loginListNew);
            usuario = em.merge(usuario);
            for (Login loginListNewLogin : loginListNew) {
                if (!loginListOld.contains(loginListNewLogin)) {
                    Usuario oldUsuarioOfLoginListNewLogin = loginListNewLogin.getUsuario();
                    loginListNewLogin.setUsuario(usuario);
                    loginListNewLogin = em.merge(loginListNewLogin);
                    if (oldUsuarioOfLoginListNewLogin != null && !oldUsuarioOfLoginListNewLogin.equals(usuario)) {
                        oldUsuarioOfLoginListNewLogin.getLoginList().remove(loginListNewLogin);
                        oldUsuarioOfLoginListNewLogin = em.merge(oldUsuarioOfLoginListNewLogin);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Login> loginListOrphanCheck = usuario.getLoginList();
            for (Login loginListOrphanCheckLogin : loginListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Login " + loginListOrphanCheckLogin + " in its loginList field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
