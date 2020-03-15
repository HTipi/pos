//package com.spring.miniposbackend.repository.admin;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.spring.miniposbackend.model.admin.Branch;
//
//import java.util.List;
//
//@Repository
//public interface BranchRepository extends JpaRepository<Branch,Integer> {
//    @Query(value = "select b from Branch b where b.enable=true")
//    List<Branch> findAllActive();
//    @Query(value = "select b from Branch b where b.enable=true and b.isMain=true")
//    List<Branch> findAllActiveMainBranch();
//
//}
