/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cencor.meif.fix.client.jpa.controllers;

import cencor.meif.fix.client.jpa.controllers.exceptions.NonexistentEntityException;
import cencor.meif.fix.client.jpa.controllers.exceptions.PreexistingEntityException;
import cencor.meif.fix.client.jpa.entities.Ack1Entity;
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
public class Ack1EntityJpaController implements Serializable {

    public Ack1EntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ack1Entity ack1Entity) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ack1Entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAck1Entity(ack1Entity.getId()) != null) {
                throw new PreexistingEntityException("Ack1Entity " + ack1Entity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ack1Entity ack1Entity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ack1Entity = em.merge(ack1Entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ack1Entity.getId();
                if (findAck1Entity(id) == null) {
                    throw new NonexistentEntityException("The ack1Entity with id " + id + " no longer exists.");
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
            Ack1Entity ack1Entity;
            try {
                ack1Entity = em.getReference(Ack1Entity.class, id);
                ack1Entity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ack1Entity with id " + id + " no longer exists.", enfe);
            }
            em.remove(ack1Entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ack1Entity> findAck1EntityEntities() {
        return findAck1EntityEntities(true, -1, -1);
    }

    public List<Ack1Entity> findAck1EntityEntities(int maxResults, int firstResult) {
        return findAck1EntityEntities(false, maxResults, firstResult);
    }

    private List<Ack1Entity> findAck1EntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ack1Entity.class));
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

    public Ack1Entity findAck1Entity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ack1Entity.class, id);
        } finally {
            em.close();
        }
    }

    public int getAck1EntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ack1Entity> rt = cq.from(Ack1Entity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
