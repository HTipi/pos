package com.spring.miniposbackend.repository.packages;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.packages.PackageBranch;
import com.spring.miniposbackend.modelview.packages.PackageCountView;

@Repository
public interface PackageBranchRepository extends JpaRepository<PackageBranch, Long>{
	
	@Query("select case when sum(s.qty)>= ?3 then true else false end from PackageBranch s where s.itemBranch.id = ?1 and s.account.id=?2 and (s.expiryDate is null or date_trunc('day',s.expiryDate)>=?4)")
	boolean existsByItemBranchId(Long itemBranchId,Long accountId,double qty,@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
	
	@Query("select s from PackageBranch s where s.itemBranch.id =?1 and s.account.id=?2 and s.qty>0 and (s.expiryDate is null or date_trunc('day',s.expiryDate)>=?3) order by s.id")
	List<PackageBranch> findByItemBranchIdAndAccountId(Long itemBranchId,Long accountId,@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
	
	@Query("select s from PackageBranch s where s.account.id=?1 and s.qty>0 and s.packages.id=?2 and s.sale.id=?3")
	List<PackageBranch> findPackageByAccountId(Long accountId,Long packageId,Long saleId);
	
	@Query("select new com.spring.miniposbackend.modelview.packages.PackageCountView(s.packages.id,s.sale.id) from PackageBranch s where s.account.id=?1 and s.qty>0 group by s.sale.id,s.packages.id")
	List<PackageCountView> findDistinctPackagesByAccountId(Long accountId);
	
	@Query("select sum(s.qty) from PackageBranch s where s.itemBranch.id = ?1 and s.account.id=?2 and (s.expiryDate is null or date_trunc('day',s.expiryDate)>=?3) and s.qty>0")
	double sumQtyByItemBranchId(Long itemBranchId,Long accountId,@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
	
}
