package io.loli.sc.server.service;

import io.loli.sc.server.dao.AccessStatusDao;
import io.loli.sc.server.entity.AccessStatus;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AccessStatusService {

    @Inject
    private AccessStatusDao accessStatusDao;

    public List<AccessStatus> findLast30Days() {
        List<AccessStatus> stats = accessStatusDao.findLast30Days();
        Collections.reverse(stats);
        return stats;
    }
}
