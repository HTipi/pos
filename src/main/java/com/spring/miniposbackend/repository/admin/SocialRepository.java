//package com.spring.miniposbackend.repository.admin;
//
//import com.spring.miniposbackend.model.admin.DeliveryContact;
//import com.spring.miniposbackend.model.admin.Social;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface SocialRepository extends JpaRepository<Social,Integer> {
//    @Query(value = "select s from Social s where s.enable=true")
//    List<Social> findAllActive();
//}
