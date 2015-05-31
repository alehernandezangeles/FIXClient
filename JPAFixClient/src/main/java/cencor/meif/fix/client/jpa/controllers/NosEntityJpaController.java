/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cencor.meif.fix.client.jpa.controllers;

import cencor.meif.fix.client.jpa.controllers.exceptions.NonexistentEntityException;
import cencor.meif.fix.client.jpa.controllers.exceptions.PreexistingEntityException;
import cencor.meif.fix.client.jpa.entities.NosEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author mhernandez
 */
public class NosEntityJpaController implements Serializable {

    public NosEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NosEntity nosEntity) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nosEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNosEntity(nosEntity.getId()) != null) {
                throw new PreexistingEntityException("NosEntity " + nosEntity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NosEntity nosEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nosEntity = em.merge(nosEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = nosEntity.getId();
                if (findNosEntity(id) == null) {
                    throw new NonexistentEntityException("The nosEntity with id " + id + " no longer exists.");
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
            NosEntity nosEntity;
            try {
                nosEntity = em.getReference(NosEntity.class, id);
                nosEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nosEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(nosEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NosEntity> findNosEntityEntities() {
        return findNosEntityEntities(true, -1, -1);
    }

    public List<NosEntity> findNosEntityEntities(int maxResults, int firstResult) {
        return findNosEntityEntities(false, maxResults, firstResult);
    }

    private List<NosEntity> findNosEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NosEntity.class));
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

    public List<NosEntity> findNosByEstatus(int estatus) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<NosEntity> query = cb.createQuery(NosEntity.class);
            Root<NosEntity> nos = query.from(NosEntity.class);
            query.select(nos)
                    .where(cb.equal(nos.get("estatus"), estatus));
            TypedQuery<NosEntity> q = em.createQuery(query);

            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public NosEntity findNosEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NosEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getNosEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NosEntity> rt = cq.from(NosEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
