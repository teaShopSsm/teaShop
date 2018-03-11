/*
* TeaOrders.java
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

@Table(name = "tea_orders")
public class TeaOrders implements Serializable {
    private int id;

    private String code;

    private BigDecimal totalprice;

    private Integer quantity;

    private String username;

    private int userid;

    private String telephone;

    private String address;

    private Integer status;

    private Date addtime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return totalprice
     */
    public BigDecimal getTotalprice() {
        return totalprice;
    }

    /**
     * @param totalprice
     */
    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    /**
     * @return quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     * @return telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
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