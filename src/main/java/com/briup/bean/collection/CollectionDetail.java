


package com.briup.bean.collection;

import com.briup.bean.product.Product;
import com.briup.bean.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "t_collection")
public class CollectionDetail {
    @Id
    private String id;
    /*@OneToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;
*/

    private String productCode;

    private String mobileNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public CollectionDetail() {
    }

    public CollectionDetail(String id, String productCode, String mobileNumber) {
        this.id = id;
        this.productCode = productCode;
        this.mobileNumber = mobileNumber;
    }
}



