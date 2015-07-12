package io.loli.sc.server.service;

import io.loli.sc.server.dao.ImageApproveLogDao;
import io.loli.sc.server.entity.ImageApproveLog;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ImageApproveLogService {
    @Inject
    private ImageApproveLogDao logDao;

    public void save(ImageApproveLog log) {
        logDao.save(log);
    }
}
