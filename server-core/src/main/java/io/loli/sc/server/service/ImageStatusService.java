package io.loli.sc.server.service;

import io.loli.sc.server.dao.ImageStatusDao;
import io.loli.sc.server.entity.ImageStatus;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class ImageStatusService {

    @Inject
    private ImageStatusDao imageStatusDao;

    public List<ImageStatus> findLast30Days() {
        List<ImageStatus> stats = imageStatusDao.findLast30Days();
        Collections.reverse(stats);
        return stats;
    }

    @Transactional
    public void save(ImageStatus status) {
        imageStatusDao.save(status);
    }
}
