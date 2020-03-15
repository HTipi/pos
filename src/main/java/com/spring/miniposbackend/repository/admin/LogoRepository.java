//package com.spring.miniposbackend.repository.admin;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.spring.miniposbackend.model.admin.Logo;
//
//import java.util.List;
//
//@Repository
//public interface LogoRepository extends JpaRepository<Logo,Integer>{
//    @Query(value = "select l from Logo l where l.corporate.id = ?1")
//    List<Logo> getLogoByCorporateId(Integer corporateId);
//    @Query(value = "select l from Logo l where l.branch.id = ?1")
//    List<Logo> getLogoByBranchId(Integer branchId);
//    @Query(value = "select l from Logo l where l.corporate.id = ?1 and l.branch.id = ?2")
//    List<Logo> getLogoByCorporateId_BranchId(Integer corporateId, Integer branchId);
//}
