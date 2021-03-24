package com.briup.bean.order.ex;

import com.briup.bean.order.Order;
import com.briup.bean.product.Product;
import com.briup.bean.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class OrderRespose {

    private OrderDetail order;

    private Product product;

    private User user;

    public OrderRespose(OrderDetail order, Product product, User user) {
        this.order = order;
        this.product = product;
        this.user = user;
    }

    public OrderRespose(Order order) {
        this.order = new OrderDetail(order);
        this.product = order.getProduct();
    }

    public OrderRespose(Order order,boolean includeUser) {
        this.order = new OrderDetail(order);
        this.product = order.getProduct();
        this.user = order.getUser();
    }

    public OrderRespose() {
    }

    public OrderDetail getOrder() {
        return order;
    }

    public void setOrder(OrderDetail order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
