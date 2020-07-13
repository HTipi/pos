package com.spring.miniposbackend.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.stock.StockPost;

public interface StockPostRepository extends JpaRepository<StockPost, Long>{

}
