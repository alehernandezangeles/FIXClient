/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cencor.meif.fix.client.jpa.controllers;

import cencor.meif.fix.client.jpa.controllers.exceptions.NonexistentEntityException;
import cencor.meif.fix.client.jpa.controllers.exceptions.PreexistingEntityException;
import cencor.meif.fix.client.jpa.entities.CatEstatusEntity;
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
public class CatEstatusEntityJpaController implements Serializable {

    public CatEstatusEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CatEstatusEntity catEstatusEntity) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(catEstatusEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCatEstatusEntity(catEstatusEntity.getId()) != null) {
                throw new PreexistingEntityException("CatEstatusEntity " + catEstatusEntity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CatEstatusEntity catEstatusEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            catEstatusEntity = em.merge(catEstatusEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = catEstatusEntity.getId();
                if (findCatEstatusEntity(id) == null) {
                    throw new NonexistentEntityException("The catEstatusEntity with id " + id + " no longer exists.");
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
            CatEstatusEntity catEstatusEntity;
            try {
                catEstatusEntity = em.getReference(CatEstatusEntity.class, id);
                catEstatusEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catEstatusEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(catEstatusEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CatEstatusEntity> findCatEstatusEntityEntities() {
        return findCatEstatusEntityEntities(true, -1, -1);
    }

    public List<CatEstatusEntity> findCatEstatusEntityEntities(int maxResults, int firstResult) {
        return findCatEstatusEntityEntities(false, maxResults, firstResult);
    }

    private List<CatEstatusEntity> findCatEstatusEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CatEstatusEntity.class));
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

    public CatEstatusEntity findCatEstatusEntity(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CatEstatusEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatEstatusEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CatEstatusEntity> rt = cq.from(CatEstatusEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
