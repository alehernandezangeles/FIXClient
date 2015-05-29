/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cencor.meif.fix.client.jpa.controllers;

import cencor.meif.fix.client.jpa.controllers.exceptions.NonexistentEntityException;
import cencor.meif.fix.client.jpa.controllers.exceptions.PreexistingEntityException;
import cencor.meif.fix.client.jpa.entities.OcrEntity;
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
public class OcrEntityJpaController implements Serializable {

    public OcrEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OcrEntity ocrEntity) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ocrEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOcrEntity(ocrEntity.getId()) != null) {
                throw new PreexistingEntityException("OcrEntity " + ocrEntity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OcrEntity ocrEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ocrEntity = em.merge(ocrEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ocrEntity.getId();
                if (findOcrEntity(id) == null) {
                    throw new NonexistentEntityException("The ocrEntity with id " + id + " no longer exists.");
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
            OcrEntity ocrEntity;
            try {
                ocrEntity = em.getReference(OcrEntity.class, id);
                ocrEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ocrEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(ocrEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OcrEntity> findOcrEntityEntities() {
        return findOcrEntityEntities(true, -1, -1);
    }

    public List<OcrEntity> findOcrEntityEntities(int maxResults, int firstResult) {
        return findOcrEntityEntities(false, maxResults, firstResult);
    }

    private List<OcrEntity> findOcrEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OcrEntity.class));
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

    public OcrEntity findOcrEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OcrEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getOcrEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OcrEntity> rt = cq.from(OcrEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
