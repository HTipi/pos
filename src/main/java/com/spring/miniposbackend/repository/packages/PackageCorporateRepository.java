package com.spring.miniposbackend.repository.packages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.packages.PackageCorporate;

@Repository
public interface PackageCorporateRepository extends JpaRepository<PackageCorporate, Long>{

	@Query("select case when sum(s)> ?3 then true else false end from PackageCorporate s where s.item.id = ?1 and s.account.id=?2")
	boolean existsByItemBranchId(Long itemBranchId,Long accountId,float qty);
}
