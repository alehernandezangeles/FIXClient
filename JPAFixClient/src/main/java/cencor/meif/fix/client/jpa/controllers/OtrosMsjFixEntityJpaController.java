/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cencor.meif.fix.client.jpa.controllers;

import cencor.meif.fix.client.jpa.controllers.exceptions.NonexistentEntityException;
import cencor.meif.fix.client.jpa.controllers.exceptions.PreexistingEntityException;
import cencor.meif.fix.client.jpa.entities.OtrosMsjFixEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author mhernandez
 */
public class OtrosMsjFixEntityJpaController implements Serializable {

    public OtrosMsjFixEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OtrosMsjFixEntity otrosMsjFixEntity) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(otrosMsjFixEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOtrosMsjFixEntity(otrosMsjFixEntity.getId()) != null) {
                throw new PreexistingEntityException("OtrosMsjFixEntity " + otrosMsjFixEntity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OtrosMsjFixEntity otrosMsjFixEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            otrosMsjFixEntity = em.merge(otrosMsjFixEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = otrosMsjFixEntity.getId();
                if (findOtrosMsjFixEntity(id) == null) {
                    throw new NonexistentEntityException("The otrosMsjFixEntity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OtrosMsjFixEntity otrosMsjFixEntity;
            try {
                otrosMsjFixEntity = em.getReference(OtrosMsjFixEntity.class, id);
                otrosMsjFixEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The otrosMsjFixEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(otrosMsjFixEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OtrosMsjFixEntity> findOtrosMsjFixEntityEntities() {
        return findOtrosMsjFixEntityEntities(true, -1, -1);
    }

    public List<OtrosMsjFixEntity> findOtrosMsjFixEntityEntities(int maxResults, int firstResult) {
        return findOtrosMsjFixEntityEntities(false, maxResults, firstResult);
    }

    private List<OtrosMsjFixEntity> findOtrosMsjFixEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OtrosMsjFixEntity.class));
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

    public OtrosMsjFixEntity findOtrosMsjFixEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OtrosMsjFixEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getOtrosMsjFixEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OtrosMsjFixEntity> rt = cq.from(OtrosMsjFixEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
