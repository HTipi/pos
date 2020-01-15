package com.spring.miniposbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.models.Logo;

@Repository
public interface BranchLogoRepository extends JpaRepository<Logo,Integer>{

}
