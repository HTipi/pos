package com.spring.miniposbackend.repository.admin;

import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchUserRepository extends JpaRepository<BranchUser, Long>{
    @Query(value = "select b from BranchUser b where b.enable=true")
    List<BranchUser> findAllActive();
}
