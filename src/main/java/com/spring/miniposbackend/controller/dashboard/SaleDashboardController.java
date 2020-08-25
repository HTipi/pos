package com.spring.miniposbackend.controller.dashboard;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.dashboard.SaleDashboardService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("dashboard-sale")
public class SaleDashboardController {
	@Autowired
	private SaleDashboardService branchDashboardService;
	@Autowired
	UserProfileUtil userProfile;
	
	private Date today;
	private Date startWeek;
	private Date startMonth;
	
	private void setDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		today = cal.getTime();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		startWeek = cal.getTime();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		startMonth = cal.getTime();
	}

	@GetMapping("/branch/summary")
	public SuccessResponse branchSummaryDetail() {
		setDate();
		return new SuccessResponse("00", "fetch report", branchDashboardService
				.summaryDetail(userProfile.getProfile().getCorporate().getId(), startMonth, startWeek, today));
	}
	
	@GetMapping("/item/summary")
	public SuccessResponse itemSummaryDetail() {
		setDate();
		return new SuccessResponse("00", "fetch report", branchDashboardService
				.summaryDetail(userProfile.getProfile().getCorporate().getId(), startMonth, startWeek, today));
	}

	@GetMapping("/detail")
	public SuccessResponse saleDetail(@RequestParam Integer page, @RequestParam Integer length,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, @RequestParam Optional<Integer> branchId) {
		Pageable pageable = PageRequest.of(page, length);
		if (branchId.isPresent()) {
			return new SuccessResponse("00", "fetch report",
					branchDashboardService.saleDetailByBranchId(branchId.get(), from, to, pageable));
		} else {
			return new SuccessResponse("00", "fetch report", branchDashboardService
					.saleDetailByCorporateId(userProfile.getProfile().getCorporate().getId(), from, to, pageable));
		}

	}
}
