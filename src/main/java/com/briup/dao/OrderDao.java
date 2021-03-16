package com.briup.dao;

import com.briup.bean.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDao extends JpaRepository<Order,String> {


}
