package com.briup.dao;

import com.briup.bean.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
@Component
public interface ProductDao extends JpaRepository<Product, String> {
    @Query(value = "select p from Product as p where p.productCode like concat(:key,'%')  or p.productName like concat(:key,'%') or p.category like concat(:key,'%')")
    List<Product> searchProduct(@Param("key") String key);
    @Query(value = "select distinct p.category from Product as p where 1 = 1")
    List<String> getAllCategory();


}
