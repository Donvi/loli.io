package io.loli.sc.server.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "uploaded_image")
@NamedQueries(value = {
        @NamedQuery(name = "UploadedImage.listByUId", query = "SELECT u FROM UploadedImage u  WHERE u.user.id=:u_id and u.delFlag=false order by u.date desc"),
        @NamedQuery(name = "UploadedImage.listByUIdAndFileName", query = "SELECT u FROM UploadedImage u  WHERE u.info.originName like :file_name and u.user.id=:u_id and u.delFlag=false order by u.date desc"),
        @NamedQuery(name = "UploadedImage.listByUIdAndFileNameAndTag", query = "SELECT u FROM UploadedImage u  WHERE u.info.originName like :file_name and u.user.id=:u_id and u.delFlag=false and u.tag.id=:tag_id order by u.date desc") })
public class UploadedImage implements Serializable {

    private static final long serialVersionUID = 1398371509051853854L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageInfo info;

    /**
     * 图片存储在哪里
     */
    @OneToOne
    @JoinColumn(name = "bucket_id")
    @JsonIgnore
    private StorageBucket storageBucket;

    @Column(name = "redirect_code")
    private String redirectCode;

    @Column(name = "generated_code")
    private String generatedCode;

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
    }

    @Column
    @JsonIgnore
    private String path;

    @Column(name = "del_flag")
    private Boolean delFlag = false;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @JsonIgnore
    private Tag tag;

    @Column
    private Boolean share = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public StorageBucket getStorageBucket() {
        return storageBucket;
    }

    public void setStorageBucket(StorageBucket storageBucket) {
        this.storageBucket = storageBucket;
    }

    @JsonIgnore
    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getRedirectCode() {
        return redirectCode;
    }

    public void setRedirectCode(String redirectCode) {
        this.redirectCode = redirectCode;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Boolean getShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public ImageInfo getInfo() {
        return info;
    }

    public void setInfo(ImageInfo info) {
        this.info = info;
    }

}
