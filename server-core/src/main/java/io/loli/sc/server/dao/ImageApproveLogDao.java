package io.loli.sc.server.dao;

import io.loli.sc.server.entity.ImageApproveLog;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class ImageApproveLogDao {
    @PersistenceContext
    private EntityManager em;

    public void save(ImageApproveLog log) {
        em.persist(log);
    }
}
