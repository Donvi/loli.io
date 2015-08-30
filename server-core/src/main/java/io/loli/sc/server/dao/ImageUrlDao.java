package io.loli.sc.server.dao;

import io.loli.sc.server.entity.ImageUrl;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class ImageUrlDao {
    @PersistenceContext
    private EntityManager em;

    public List<ImageUrl> findAll() {
        return em.createQuery("from ImageUrl", ImageUrl.class).getResultList();
    }

}
