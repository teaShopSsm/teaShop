/*
* TeaGoods.java
* http://www.xxx.com
* Copyright © 2018 xxx All Rights Reserved
* 作者：
* QQ：
* E-Mail：
* 2018-03-08 22:00 Created
*/ 
package com.teaShop.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TeaGoods implements Serializable {

    private Integer id;

    private String goodsname;

    /**
     * 简介
     */
    private String brief;

    private String picpath;

    private String appuserid;

    private String appuser;

    private Integer status;

    private Date addtime;

    private BigDecimal price;

    private static final long serialVersionUID = 1L;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return goodsname
     */
    public String getGoodsname() {
        return goodsname;
    }

    /**
     * @param goodsname
     */
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    /**
     * 获取简介
     *
     * @return brief - 简介
     */
    public String getBrief() {
        return brief;
    }

    /**
     * 设置简介
     *
     * @param brief 简介
     */
    public void setBrief(String brief) {
        this.brief = brief;
    }

    /**
     * @return picpath
     */
    public String getPicpath() {
        return picpath;
    }

    /**
     * @param picpath
     */
    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    /**
     * @return appuserid
     */
    public String getAppuserid() {
        return appuserid;
    }

    /**
     * @param appuserid
     */
    public void setAppuserid(String appuserid) {
        this.appuserid = appuserid;
    }

    /**
     * @return appuser
     */
    public String getAppuser() {
        return appuser;
    }

    /**
     * @param appuser
     */
    public void setAppuser(String appuser) {
        this.appuser = appuser;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return addtime
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}