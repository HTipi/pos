package com.spring.miniposbackend.repository.admin;

import com.spring.miniposbackend.model.admin.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialRepository extends JpaRepository<Social,Integer> {
}
