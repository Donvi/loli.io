package io.loli.sc.server.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table
@Entity(name = "image_info")
public class ImageInfo implements Serializable {
    private static final long serialVersionUID = -6571271267375935734L;
    public static final Integer STATUS_NOT_VERIFIED = 0;
    public static final Integer STATUS_VERIFIED = 1;
    public static final Integer STATUS_ANIME_PORN = 2;
    public static final Integer STATUS_ADULE_PORN = 3;
    public static final Integer STATUS_ILLEGAL = 4;
    public static final Integer STATUS_APPROVED = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @OneToOne(mappedBy = "info")
    @JoinColumn(name = "image_id")
    @JsonIgnore
    private UploadedImage image;
    @JsonIgnore
    private String ip;
    @JsonIgnore
    private String ua;
    @JsonIgnore
    private Integer status = STATUS_NOT_VERIFIED;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    @Column(name = "small_name")
    private String smallName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "large_name")
    private String largeName;

    @Column(name = "internal_path")
    @JsonIgnore
    private String internalPath;

    @JsonIgnore
    @Column(name = "content_type")
    private String contentType;

    /**
     * 图片描述显示在alt标签中
     */
    @Column
    private String description;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UploadedImage getImage() {
        return image;
    }

    public void setImage(UploadedImage image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmallName() {
        return smallName;
    }

    public void setSmallName(String smallName) {
        this.smallName = smallName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLargeName() {
        return largeName;
    }

    public void setLargeName(String largeName) {
        this.largeName = largeName;
    }

    public String getInternalPath() {
        return internalPath;
    }

    public void setInternalPath(String internalPath) {
        this.internalPath = internalPath;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
