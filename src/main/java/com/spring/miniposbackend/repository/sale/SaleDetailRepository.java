package com.spring.miniposbackend.repository.sale;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.spring.miniposbackend.model.sale.SaleDetail;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
	List<SaleDetail> findBySaleId(Long saleId);

	List<SaleDetail> findByUserId(Integer userId);

	@Query("select s from SaleDetail s where s.branch.corporate.id= ?1 and date_trunc('day',s.valueDate) between ?2 and ?3 and s.reverse=false")
	Page<SaleDetail> findByCorporateId(Integer corporateId, Date from, Date to, Pageable pageable);
	
	@Query("select s from SaleDetail s where s.branch.id= ?1 and date_trunc('day',s.valueDate) between ?2 and ?3 and s.reverse=false")
	Page<SaleDetail> findByBranchId(Integer branchId, Date from, Date to, Pageable pageable);
}
