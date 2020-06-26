package com.spring.miniposbackend.repository.admin;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,UUID>{

}
