/*

package com.briup.bean.collection;

import com.briup.bean.product.Product;
import com.briup.bean.user.User;

import javax.persistence.*;

@Entity
@Table(name = "t_collection")
public class Collection {
    @Id
    private String id;

    @OneToOne(targetEntity = Product.class)
    @JoinColumn(name = "productId" ,referencedColumnName = "product_code")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = User.class)
    private User user;

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

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }

    public Collection() {
    }
}

*/
