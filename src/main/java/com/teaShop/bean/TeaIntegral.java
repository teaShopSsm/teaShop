/*
* TeaIntegral.java
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

@Table(name = "tea_integral")
public class TeaIntegral implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 积分数
     */
    private BigDecimal quantity;

    private String orderid;

    private String userid;

    private String username;

    /**
     * 积分操作（+/-）
     */
    private Integer operator;

    private BigDecimal orderprice;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取积分数
     *
     * @return quantity - 积分数
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * 设置积分数
     *
     * @param quantity 积分数
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * @return orderid
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * @return userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
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
     * 获取积分操作（+/-）
     *
     * @return operator - 积分操作（+/-）
     */
    public Integer getOperator() {
        return operator;
    }

    /**
     * 设置积分操作（+/-）
     *
     * @param operator 积分操作（+/-）
     */
    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    /**
     * @return orderprice
     */
    public BigDecimal getOrderprice() {
        return orderprice;
    }

    /**
     * @param orderprice
     */
    public void setOrderprice(BigDecimal orderprice) {
        this.orderprice = orderprice;
    }
}