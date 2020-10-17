package com.spring.miniposbackend.controller.stock;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.stock.StockPostService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("stock-post")
public class StockPostController {

	@Autowired
	private StockPostService stockPostService;
	@Autowired
	private UserProfileUtil userProfile;
	
	@PostMapping("{stockId}")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse create(@PathVariable Long stockId){
		return new SuccessResponse("00", "fetch Stock", stockPostService.create(stockId));
	}
	@GetMapping("/detail")
	@PreAuthorize("hasAnyRole('BRANCH','OWNER')")
	public SuccessResponse StockDetail(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
		return new SuccessResponse("00", "fetch report", stockPostService
				.stockDetailByBranchId(userProfile.getProfile().getBranch().getId(), from, to));
	}
}
