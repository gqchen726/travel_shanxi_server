package com.briup.dao;

import com.briup.bean.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDao extends JpaRepository<Stock,Long> {
}
