package com.spring.miniposbackend.controller.dashboard;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
//	private Date startWeek;
//	private Date startMonth;

	private void getDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		today = cal.getTime();
	}

	@GetMapping("/branch/summary")
	public SuccessResponse branchSummaryDetail() {
		getDate();
		return new SuccessResponse("00", "fetch report", expenseDashboardService
				.expenseSummaryByBranchId(userProfile.getProfile().getBranch().getId(), today, today));
	}
}
