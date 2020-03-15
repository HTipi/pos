//package com.spring.miniposbackend.repository.admin;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.spring.miniposbackend.model.admin.Address;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface AddressRepository extends JpaRepository<Address, Integer>{
//    @Query(value = "select a from Address a where a.address.id = ?1")
//    List<Address> findAllAddressByParentId(Integer parentId);
//}
