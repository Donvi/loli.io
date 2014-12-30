package io.loli.sc.server.dao;

import io.loli.sc.server.entity.ImageStatus;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class ImageStatusDao {
    @PersistenceContext
    private EntityManager em;

    public void save(ImageStatus is) {
        em.persist(is);
    }

    public List<ImageStatus> findLast30Days() {
        return em.createQuery("from ImageStatus order by id desc", ImageStatus.class).setMaxResults(30).getResultList();
    }
}
