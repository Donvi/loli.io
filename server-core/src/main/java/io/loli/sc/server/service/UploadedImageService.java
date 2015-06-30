package io.loli.sc.server.service;

import io.loli.sc.server.dao.GalleryDao;
import io.loli.sc.server.dao.ImageInfoDao;
import io.loli.sc.server.dao.UploadedImageDao;
import io.loli.sc.server.entity.Gallery;
import io.loli.sc.server.entity.StorageBucket;
import io.loli.sc.server.entity.UploadedImage;
import io.loli.sc.server.entity.User;
import io.loli.sc.server.storage.StorageUploader;
import io.loli.util.image.ThumbnailUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

@Named("imageService")
public class UploadedImageService {

    @Inject
    @Named("imageDao")
    private UploadedImageDao ud;
    
    @Inject
    private ImageInfoDao infod;

    @Inject
    private GalleryDao gd;

    @Transactional
    public void save(UploadedImage image) {
        ud.save(image);
        if (image.getGallery() != null) {
            image.getGallery().setLastUpdate(new Date());
        }

    }

    @Transactional
    public void update(UploadedImage image) {
        ud.update(image);
    }

    @Transactional
    public void delete(int id) {
        UploadedImage image = this.findById(id);
        StorageBucket sb = image.getStorageBucket();
        StorageUploader.newInstance(sb)
                .delete(image.getPath().substring(
                        image.getPath().lastIndexOf("/") + 1));
        image.setDelFlag(true);
    }

    public List<UploadedImage> findAll() {
        return ud.findAll();
    }

    /**
     * 根据图片id将查询出此图片的信息
     * 
     * @param id
     * @return 查询出的图片，如果没有此id的图片，则返回null
     */
    public UploadedImage findById(int id) {
        return ud.findById(id);
    }

    private int maxResults = 18;

    /**
     * 分页查询出指定用户的截图列表
     * 
     * @param u_id
     *            用户id
     * @param firstPosition
     *            初始位置
     * @param maxResults
     *            每页的最大数量
     * @param name
     *            图片名
     * @return 截图列表
     */
    public List<UploadedImage> listByUId(int u_id, int firstPosition,
            int maxResults, String name) {
        return ud.listByUId(u_id, firstPosition, maxResults, name);
    }

    /**
     * 不包含分页参数的查询，默认查询出20行
     * 
     * @param u_id
     *            用户id
     * @param firstPosition
     *            开始位置
     * @param name
     *            图片名
     * @return 截图列表
     */
    public List<UploadedImage> listByUId(int u_id, int firstPosition,
            String name) {
        return this.listByUId(u_id, firstPosition, maxResults, name);
    }

    public List<UploadedImage> listByUIdAndFileName(int u_id, String fileName,
            int firstPosition, Integer tag) {
        return this.listByUIdAndFileName(u_id, fileName, firstPosition,
                maxResults, tag);
    }

    public List<UploadedImage> listByUIdAndFileName(int u_id, String fileName,
            int firstPosition, int maxResults, Integer tag) {
        if (tag == 0 || tag == null) {
            return ud.listByUIdAndFileName(u_id, fileName, firstPosition,
                    maxResults);
        } else {
            return ud.listByUIdAndFileName(u_id, fileName, firstPosition,
                    maxResults, tag);
        }
    }

    public int countByUId(int u_id) {
        return ud.countByUId(u_id);
    }

    public int countByUId(int u_id, String name) {
        return ud.countByUId(u_id, name);
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int countByUIdAndFileName(int u_id, String fileName) {
        return ud.countByUIdAndFileName(u_id, fileName);
    }

    public int countByUIdAndFileName(int u_id, String fileName, Integer tag) {
        if (tag == 0 || tag == null) {
            return ud.countByUIdAndFileName(u_id, fileName);
        } else {
            return ud.countByUIdAndFileName(u_id, fileName, tag);
        }
    }

    public boolean checkExists(String code) {
        long count = ud.countByCode(code);
        return count != 0;
    }

    public UploadedImage findByCode(String redirectCode) {
        List<UploadedImage> images = ud.findByCode(redirectCode);
        if (images.isEmpty() || images.size() > 1) {
            throw new IllegalArgumentException("Invalid code:" + redirectCode);
        } else {
            return images.get(0);
        }
    }

    @Transactional
    public void updateThumbnail(UploadedImage image, File file,
            StorageUploader uploader) {
        String format = file.getName().contains(".") ? file.getName()
                .substring(file.getName().lastIndexOf(".") + 1) : "png";

        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            File f0 = new File(tempDir, image.getGeneratedCode() + "q."
                    + format);
            toFile(ThumbnailUtil.cutSqureWithResizeSmall(
                    new BufferedInputStream(new FileInputStream(file)), format),
                    f0);
            uploader.upload(f0, image.getInfo().getContentType());
            image.getInfo().setSmallSquareName(f0.getName());

            File f1 = new File(tempDir, image.getGeneratedCode() + "s."
                    + format);
            toFile(ThumbnailUtil.resizeSmall(new BufferedInputStream(
                    new FileInputStream(file)), format), f1);
            uploader.upload(f1, image.getInfo().getContentType());
            image.getInfo().setSmallName(f1.getName());

            File f2 = new File(tempDir, image.getGeneratedCode() + "m."
                    + format);
            toFile(ThumbnailUtil.resizeMiddle(new BufferedInputStream(
                    new FileInputStream(file)), format), f2);
            uploader.upload(f2, image.getInfo().getContentType());
            image.getInfo().setMiddleName(f2.getName());

            File f3 = new File(tempDir, image.getGeneratedCode() + "l."
                    + format);
            toFile(ThumbnailUtil.resizeBig(new BufferedInputStream(
                    new FileInputStream(file)), format), f3);
            uploader.upload(f3, image.getInfo().getContentType());
            image.getInfo().setLargeName(f3.getName());
            this.update(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void toFile(OutputStream os, File file) {
        try {
            FileUtils.writeByteArrayToFile(file,
                    ((ByteArrayOutputStream) os).toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageType(File file) {
        try (ImageInputStream iis = ImageIO.createImageInputStream(file);) {
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                return "";
            }

            ImageReader reader = iter.next();

            return reader.getFormatName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The image could not be read
        return "";
    }

    public List<UploadedImage> findByGalIdAndUId(int id, Integer gid, int page,
            String name) {
        int start = this.getMaxResults() * (page - 1);
        return ud.findByGalIdAndUId(id, gid, start, this.getMaxResults(), name);
    }

    public int countByGalIdAndUId(int id, Integer gid) {
        return ud.countByGalIdAndUId(id, gid).intValue();
    }

    public int countByGalIdAndUId(int id, Integer gid, String name) {
        return ud.countByGalIdAndUId(id, gid, name).intValue();
    }

    @Transactional
    public void batchDelete(String ids) {
        for (String id : ids.split(",")) {
            if (StringUtils.isNoneBlank(id)) {
                Integer iid = Integer.parseInt(id.trim());
                UploadedImage img = ud.findById(iid);
                img.setDelFlag(true);
            }
        }
    }

    @Transactional
    public void batchDelete(String ids, User user) {
        for (String id : ids.split(",")) {
            if (StringUtils.isNoneBlank(id)) {
                Integer iid = Integer.parseInt(id.trim());
                UploadedImage img = ud.findById(iid);
                if (user.getId() != img.getUser().getId()) {
                    new IllegalArgumentException("图片" + img.getRedirectCode()
                            + "非该用户所有");
                }
                this.delete(iid);
            }
        }
    }

    @Transactional
    public void batchMove(String ids, User user, Integer gid) {
        Gallery gal = gd.findById(gid);
        gal.setLastUpdate(new Date());
        for (String id : ids.split(",")) {
            if (StringUtils.isNoneBlank(id)) {
                Integer iid = Integer.parseInt(id.trim());
                UploadedImage img = ud.findById(iid);
                if (user.getId() != img.getUser().getId()) {
                    new IllegalArgumentException("图片" + img.getRedirectCode()
                            + "非该用户所有");
                } else {
                    img.setGallery(gal);
                }
            }
        }
    }

    public List<UploadedImage> findByNameAndUser(String name, User user) {
        return ud.findByNameAndUser("%" + name + "%", user.getId());
    }
}
