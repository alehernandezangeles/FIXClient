/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cencor.meif.fix.client.jpa.controllers;

import cencor.meif.fix.client.jpa.controllers.exceptions.NonexistentEntityException;
import cencor.meif.fix.client.jpa.controllers.exceptions.PreexistingEntityException;
import cencor.meif.fix.client.jpa.entities.ErroresEntity;
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
public class ErroresEntityJpaController implements Serializable {

    public ErroresEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ErroresEntity erroresEntity) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(erroresEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findErroresEntity(erroresEntity.getId()) != null) {
                throw new PreexistingEntityException("ErroresEntity " + erroresEntity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ErroresEntity erroresEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            erroresEntity = em.merge(erroresEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = erroresEntity.getId();
                if (findErroresEntity(id) == null) {
                    throw new NonexistentEntityException("The erroresEntity with id " + id + " no longer exists.");
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
            ErroresEntity erroresEntity;
            try {
                erroresEntity = em.getReference(ErroresEntity.class, id);
                erroresEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The erroresEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(erroresEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ErroresEntity> findErroresEntityEntities() {
        return findErroresEntityEntities(true, -1, -1);
    }

    public List<ErroresEntity> findErroresEntityEntities(int maxResults, int firstResult) {
        return findErroresEntityEntities(false, maxResults, firstResult);
    }

    private List<ErroresEntity> findErroresEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ErroresEntity.class));
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

    public ErroresEntity findErroresEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ErroresEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getErroresEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ErroresEntity> rt = cq.from(ErroresEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
