package com.spring.miniposbackend.controller.dashboard;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.dashboard.ExpenseDashboardService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("dashboard-expense")
public class ExpenseDashboardController {

	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private ExpenseDashboardService expenseDashboardService;

	private Date today;
	private Date startWeek;
	private Date startMonth;

	private void getDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
//		cal.setFirstDayOfWeek(Calendar.MONDAY);
//		cal.set(Calendar.DAY_OF_MONTH, day);
		today = cal.getTime();
		cal.add(Calendar.DATE, -6);
		startWeek = cal.getTime();
		cal.add(Calendar.DATE, -22);
		startMonth = cal.getTime();
	}

	@GetMapping("/branch/summary")
	@PreAuthorize("hasAnyRole('BRANCH','OWNER')")
	public SuccessResponse branchSummaryDetail() {
		getDate();
		return new SuccessResponse("00", "fetch report", expenseDashboardService
				.expenseSummaryByBranchId(userProfile.getProfile().getBranch().getId(), startMonth, startWeek, today));
	}
	@GetMapping("/expense-type/summary")
	@PreAuthorize("hasAnyRole('BRANCH','OWNER')")
	public SuccessResponse expenseTypeSummaryDetail(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,@RequestParam Optional<Integer> branchId) {
		
		if(branchId.isPresent()) {
			return new SuccessResponse("00", "fetch report", expenseDashboardService
					.expenseTypeSummaryByBranchId(branchId.get(), from, to));
		}
		return new SuccessResponse("00", "fetch report", expenseDashboardService
				.expenseTypeSummaryByBranchId(userProfile.getProfile().getBranch().getId(), from, to));
		
	}
	@GetMapping("/detail")
	@PreAuthorize("hasAnyRole('BRANCH','OWNER')")
	public SuccessResponse expenseDetail(@RequestParam Integer page, @RequestParam Integer length,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,@RequestParam Optional<Integer> branchId) {
		Pageable pageable = PageRequest.of(page, length);
		if(branchId.isPresent())
		return new SuccessResponse("00", "fetch report",
				expenseDashboardService.expenseDetailByBranchId(branchId.get(), from, to, pageable));
		return new SuccessResponse("00", "fetch report",
				expenseDashboardService.expenseDetailByBranchId(userProfile.getProfile().getBranch().getId(), from, to, pageable));
	}
}
