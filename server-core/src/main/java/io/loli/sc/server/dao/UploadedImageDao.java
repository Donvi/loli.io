package io.loli.sc.server.dao;

import io.loli.sc.server.entity.UploadedImage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;

@Named("imageDao")
public class UploadedImageDao {
    @PersistenceContext
    private EntityManager em;

    public void save(UploadedImage image) {
        em.persist(image);
    }

    public void update(UploadedImage image) {
        em.merge(image);
    }

    /**
     * 根据图片id将查询出此图片的信息
     * 
     * @param id
     *            图片的id
     * @return 查询出的图片，如果没有此id的图片，则返回null
     */
    public UploadedImage findById(int id) {
        return em.find(UploadedImage.class, id);
    }

    /**
     * 分页查询出指定用户的截图列表
     * 
     * @param u_id
     *            用户id
     * @param firstPosition
     *            初始位置
     * @param maxResults
     *            每页的最大数量
     * @return 截图列表
     */
    public List<UploadedImage> listByUId(int u_id, int firstPosition,
            int maxResults, String name) {
        if (StringUtils.isBlank(name)) {
            return em
                    .createNamedQuery("UploadedImage.listByUId",
                            UploadedImage.class).setParameter("u_id", u_id)
                    .setFirstResult(firstPosition).setMaxResults(maxResults)
                    .getResultList();
        } else {
            return this.listByUIdAndFileName(u_id, name, firstPosition,
                    maxResults);
        }

    }

    public int countByUId(int u_id) {
        return em
                .createQuery(
                        "select count(*) from UploadedImage where user_id=:u_id and delFlag=false",
                        Long.class).setParameter("u_id", u_id)
                .getSingleResult().intValue();
    }

    public List<UploadedImage> listByUIdAndFileName(int u_id, String fileName,
            int firstPosition, int maxResults) {
        return em
                .createNamedQuery("UploadedImage.listByUIdAndFileName",
                        UploadedImage.class)
                .setParameter("file_name", "%" + fileName + "%")
                .setParameter("u_id", u_id).setFirstResult(firstPosition)
                .setMaxResults(maxResults).getResultList();
    }

    public List<UploadedImage> listByUIdAndFileName(int u_id, String fileName,
            int firstPosition, int maxResults, int tag) {
        return em
                .createNamedQuery("UploadedImage.listByUIdAndFileNameAndTag",
                        UploadedImage.class)
                .setParameter("file_name", "%" + fileName + "%")
                .setParameter("u_id", u_id).setParameter("tag_id", tag)
                .setFirstResult(firstPosition).setMaxResults(maxResults)
                .getResultList();
    }

    public int countByUIdAndFileName(int u_id, String fileName) {
        return em
                .createNamedQuery("UploadedImage.listByUIdAndFileName",
                        UploadedImage.class).setParameter("u_id", u_id)
                .setParameter("file_name", "%" + fileName + "%")
                .getResultList().size();
    }

    public int countByUIdAndFileName(int u_id, String fileName, int tag) {
        return em
                .createNamedQuery("UploadedImage.listByUIdAndFileNameAndTag",
                        UploadedImage.class).setParameter("u_id", u_id)
                .setParameter("file_name", "%" + fileName + "%")
                .setParameter("tag_id", tag).getResultList().size();
    }

    public List<UploadedImage> checkExists(String code) {
        return em
                .createQuery("from UploadedImage where generatedCode=:code",
                        UploadedImage.class).setParameter("code", code)
                .getResultList();
    }

    public int countByCode(String code) {
        return em
                .createQuery(
                        "select count(*) from UploadedImage where generatedCode=:code",
                        Long.class).setParameter("code", code)
                .getSingleResult().intValue();
    }

    public List<UploadedImage> findByCode(String generatedCode) {
        return em
                .createQuery(
                        "from UploadedImage where generatedCode=:code and delFlag=false",
                        UploadedImage.class)
                .setParameter("code", generatedCode).getResultList();
    }

    public List<UploadedImage> findAll() {
        return em.createQuery(
                "from UploadedImage where delFlag=false order by id desc",
                UploadedImage.class).getResultList();
    }

    public UploadedImage findNext(int uid, int imageId) {
        return em
                .createQuery(
                        "from UploadedImage where delFlag=false and user.id=:uid and id>:imageId",
                        UploadedImage.class).setParameter("uid", uid)
                .setParameter("imageId", imageId).setMaxResults(1)
                .getSingleResult();
    }

    public UploadedImage findLast(int uid, int imageId) {
        return em
                .createQuery(
                        "from UploadedImage where delFlag=false and user.id=:uid and id<:imageId",
                        UploadedImage.class).setParameter("uid", uid)
                .setParameter("imageId", imageId).setMaxResults(1)
                .getSingleResult();
    }

    public List<UploadedImage> findByGalIdAndUId(int uid, Integer gid,
            int firstPosition, int maxResults, String name) {
        if (StringUtils.isBlank(name))
            return em
                    .createQuery(
                            "from UploadedImage where delFlag=false and user.id=:uid and gallery.id=:gid order by id desc",
                            UploadedImage.class).setParameter("uid", uid)
                    .setParameter("gid", gid).setFirstResult(firstPosition)
                    .setMaxResults(maxResults).getResultList();
        else {
            return em
                    .createQuery(
                            "from UploadedImage where originName like:name and delFlag=false and user.id=:uid and gallery.id=:gid order by id desc",
                            UploadedImage.class).setParameter("uid", uid)
                    .setParameter("gid", gid)
                    .setParameter("name", "%" + name + "%")
                    .setFirstResult(firstPosition).setMaxResults(maxResults)
                    .getResultList();
        }
    }

    public Long countByGalIdAndUId(int uid, Integer gid) {
        return em
                .createQuery(
                        "select count(*) from UploadedImage where delFlag=false and user.id=:uid and gallery.id=:gid",
                        Long.class).setParameter("uid", uid)
                .setParameter("gid", gid).getSingleResult();
    }

    public List<UploadedImage> findAllByGalIdAndUId(int uid, Integer gid) {
        return em
                .createQuery(
                        "from UploadedImage where delFlag=false and user.id=:uid and gallery.id=:gid order by id desc",
                        UploadedImage.class).setParameter("uid", uid)
                .setParameter("gid", gid).getResultList();
    }

    public List<UploadedImage> findByNameAndUser(String name, int id) {
        return em
                .createQuery(
                        "from UploadedImage where delFlag=false and user.id=:uid and name like :name",
                        UploadedImage.class).setParameter("uid", id)
                .setParameter("name", name).getResultList();
    }

    public int countByUId(int u_id, String name) {
        if (StringUtils.isBlank(name)) {
            return this.countByUId(u_id);
        } else
            return em
                    .createQuery(
                            "SELECT count(*) FROM UploadedImage u WHERE u.user.id=:u_id and u.delFlag=false and u.originName like :originName",
                            Long.class).setParameter("u_id", u_id)
                    .setParameter("originName", "%" + name + "%")
                    .getSingleResult().intValue();
    }

    public Long countByGalIdAndUId(int id, Integer gid, String name) {
        if (StringUtils.isBlank(name)) {
            return this.countByGalIdAndUId(id, gid);
        } else
            return em
                    .createQuery(
                            "select count(*) from UploadedImage where delFlag=false and originName like :originName and user.id=:uid and gallery.id=:gid",
                            Long.class).setParameter("uid", id)
                    .setParameter("originName", "%" + name + "%")
                    .setParameter("gid", gid).getSingleResult();
    }

    public long countByDate(Date from, Date to) {
        return em
                .createQuery(
                        "select count(*) from UploadedImage where date>:from and date<:to",
                        Long.class).setParameter("from", from)
                .setParameter("to", to).getSingleResult();
    }

    public List<UploadedImage> findByStatusAndDateAfter(Date date,
            Integer status) {
        return em
                .createQuery(
                        "from UploadedImage u where u.delFlag=false and u.info.status=:status and u.date>:date",
                        UploadedImage.class).setParameter("status", status)
                .setParameter("date", date).getResultList();
    }

    public List<UploadedImage> findByStatus(List<Integer> illegalStatus) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, -4);
        Date date = cal.getTime();
        return em
                .createQuery(
                        "from UploadedImage u where u.delFlag=false and u.info.status in :list and u.date>:date",
                        UploadedImage.class)
                .setParameter("list", illegalStatus).setParameter("date", date)
                .setMaxResults(20).getResultList();
    }
}
