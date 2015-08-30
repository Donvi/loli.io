package io.loli.sc.server.service;

import io.loli.sc.server.dao.ImageUrlDao;
import io.loli.sc.server.entity.ImageUrl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ImageUrlService {
    @Inject
    private ImageUrlDao dao;

    public List<ImageUrl> findAll() {
        return dao.findAll();
    }
}
