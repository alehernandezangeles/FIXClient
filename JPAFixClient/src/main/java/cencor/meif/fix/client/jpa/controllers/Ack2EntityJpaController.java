/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cencor.meif.fix.client.jpa.controllers;

import cencor.meif.fix.client.jpa.controllers.exceptions.NonexistentEntityException;
import cencor.meif.fix.client.jpa.controllers.exceptions.PreexistingEntityException;
import cencor.meif.fix.client.jpa.entities.Ack2Entity;
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
public class Ack2EntityJpaController implements Serializable {

    public Ack2EntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ack2Entity ack2Entity) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ack2Entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAck2Entity(ack2Entity.getId()) != null) {
                throw new PreexistingEntityException("Ack2Entity " + ack2Entity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ack2Entity ack2Entity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ack2Entity = em.merge(ack2Entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ack2Entity.getId();
                if (findAck2Entity(id) == null) {
                    throw new NonexistentEntityException("The ack2Entity with id " + id + " no longer exists.");
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
            Ack2Entity ack2Entity;
            try {
                ack2Entity = em.getReference(Ack2Entity.class, id);
                ack2Entity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ack2Entity with id " + id + " no longer exists.", enfe);
            }
            em.remove(ack2Entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ack2Entity> findAck2EntityEntities() {
        return findAck2EntityEntities(true, -1, -1);
    }

    public List<Ack2Entity> findAck2EntityEntities(int maxResults, int firstResult) {
        return findAck2EntityEntities(false, maxResults, firstResult);
    }

    private List<Ack2Entity> findAck2EntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ack2Entity.class));
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

    public Ack2Entity findAck2Entity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ack2Entity.class, id);
        } finally {
            em.close();
        }
    }

    public int getAck2EntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ack2Entity> rt = cq.from(Ack2Entity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
