package com.briup.dao;

import com.briup.bean.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrderDao extends JpaRepository<Order, String> {
    @Query("select o from Order as o where o.user.mobileNumber = :mobileNumber")
    List<Order> listAllOrder(String mobileNumber);
}
