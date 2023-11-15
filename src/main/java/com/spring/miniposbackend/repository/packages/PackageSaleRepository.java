package com.spring.miniposbackend.repository.packages;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.miniposbackend.model.packages.PackageSale;

@Repository
public interface PackageSaleRepository extends JpaRepository<PackageSale, Long>{
	
	@Query("select s from PackageSale s where s.qrNumber=?1")
	Optional<PackageSale> findByQr(UUID qr);
	
	@Query("select s from PackageSale s where date_trunc('day',s.saleDetail.valueDate) between ?1 and ?2 and s.account.id=?3")
	List<PackageSale> findPackageSaleByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,long accountId);
}
