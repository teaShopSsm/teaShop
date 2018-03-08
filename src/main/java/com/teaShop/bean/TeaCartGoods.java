/*
* TeaCartGoods.java
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

@Table(name = "tea_cart_goods")
public class TeaCartGoods implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String cartid;

    private String goodsid;

    private Integer quantity;

    private BigDecimal price;

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
     * @return cartid
     */
    public String getCartid() {
        return cartid;
    }

    /**
     * @param cartid
     */
    public void setCartid(String cartid) {
        this.cartid = cartid;
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
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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