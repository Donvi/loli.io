package io.loli.sc.server.dao;

import io.loli.sc.server.entity.ImageInfo;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("imageInfoDao")
public class ImageInfoDao {
    @PersistenceContext
    private EntityManager em;

    public void save(ImageInfo info) {
        em.persist(info);
    }

    public void update(ImageInfo info) {
        em.merge(info);
    }

}
