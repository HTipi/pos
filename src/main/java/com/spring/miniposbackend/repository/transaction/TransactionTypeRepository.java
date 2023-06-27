package com.spring.miniposbackend.repository.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.transaction.TransactionType;


@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer>{

}

