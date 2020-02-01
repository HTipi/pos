package com.spring.miniposbackend.repository.admin;

import com.spring.miniposbackend.model.admin.BranchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchUserRepository extends JpaRepository<BranchUser, Long>{

}
