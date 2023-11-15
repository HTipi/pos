package com.spring.miniposbackend.service.packages;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.spring.miniposbackend.model.packages.PackageSale;
import com.spring.miniposbackend.repository.packages.PackageSaleRepository;

@Service
public class PackageSaleService {


	@Autowired
	private PackageSaleRepository packageSaleRepository;
	
	public List<PackageSale> getPackageSaleByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,long accountId) {
		return packageSaleRepository.findPackageSaleByDate(from,to,accountId);
	}

}
