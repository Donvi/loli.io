package io.loli.sc.server.dao;

import io.loli.sc.server.entity.AccessStatus;
import io.loli.sc.server.entity.ImageStatus;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class AccessStatusDao {
    @PersistenceContext
    private EntityManager em;

    public void save(ImageStatus is) {
        em.persist(is);
    }

    public List<AccessStatus> findLast30Days() {
        return em.createQuery("from AccessStatus order by id desc", AccessStatus.class).setMaxResults(30)
            .getResultList();
    }
}
