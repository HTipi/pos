package com.spring.miniposbackend.repository.admin;

import com.spring.miniposbackend.model.admin.SocialBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialBranchRepository extends JpaRepository<SocialBranch,Integer> {
}
