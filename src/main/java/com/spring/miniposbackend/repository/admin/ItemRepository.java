package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.admin.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
