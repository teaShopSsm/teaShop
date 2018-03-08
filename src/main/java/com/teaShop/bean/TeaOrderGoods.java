/*
* TeaOrderGoods.java
* http://www.xxx.com
* Copyright © 2018 xxx All Rights Reserved
* 作者：
* QQ：
* E-Mail：
* 2018-03-08 22:00 Created
*/ 
package com.teaShop.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "tea_order_goods")
public class TeaOrderGoods implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String orderid;

    private String goodsid;

    @Column(name = "goods_price")
    private BigDecimal goodsPrice;

    @Column(name = "goods_quantity")
    private Integer goodsQuantity;

    private String goodsname;

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
     * @return goodsid
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * @param goodsid
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    /**
     * @return goods_price
     */
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * @param goodsPrice
     */
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * @return goods_quantity
     */
    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    /**
     * @param goodsQuantity
     */
    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
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
}