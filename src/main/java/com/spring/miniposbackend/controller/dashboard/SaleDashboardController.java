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
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse branchSummaryDetail(@RequestParam Optional<Integer> branchId) {
		getDate();

		if (userProfile.getProfile().getAuthorities()
				.stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report", branchDashboardService.branchSummaryByBranchId(
					userProfile.getProfile().getBranch().getId(), startMonth, startWeek, today));
		}
		else {
			if (branchId.isPresent())
				
			return new SuccessResponse("00", "fetch report", branchDashboardService.branchSummaryByBranchId(
					branchId.get(), startMonth, startWeek, today));
			else
				return new SuccessResponse("00", "fetch report", branchDashboardService.itemSummaryByCorporateId(
						userProfile.getProfile().getCorporate().getId(), startMonth, startWeek, today));
		}
		
	}
	@GetMapping("/branch-chart/summary")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse branchSummaryChart(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
		return new SuccessResponse("00", "fetch report", branchDashboardService
				.branchChartByCopId(userProfile.getProfile().getCorporate().getId(), from, to));
	}
	
	@GetMapping("/item/summary")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse itemSummaryDetail(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> to,@RequestParam Optional<Integer> branchId) {
		if (!from.isPresent())
			getDate();
		if (userProfile.getProfile().getAuthorities()
				.stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report", branchDashboardService
					.itemSummaryByBranchId(userProfile.getProfile().getBranch().getId(), startMonth, startWeek, today));
		} else {
			if (branchId.isPresent()) {
				return new SuccessResponse("00", "fetch report", branchDashboardService
						.itemSummaryByBranchId(branchId.get(), startMonth, startWeek, today));
			}
			else
				return new SuccessResponse("00", "fetch report", branchDashboardService.itemSummaryByCorporateId(
						userProfile.getProfile().getCorporate().getId(), startMonth, startWeek, today));
		}

	}

	@GetMapping("/item-chart/summary")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse itemSummaryChart(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, @RequestParam Optional<Integer> branchId) {
		if (userProfile.getProfile().getAuthorities()
				.stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report",
					branchDashboardService.itemChartByBranchId(userProfile.getProfile().getBranch().getId(), from, to));
		} else {
			if (branchId.isPresent()) {
				return new SuccessResponse("00", "fetch report",
						branchDashboardService.itemChartByBranchId(branchId.get(), from, to));
			} else {
				return new SuccessResponse("00", "fetch report", branchDashboardService
						.itemChartByCopId(userProfile.getProfile().getCorporate().getId(), from, to));
			}
		}

	}

	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping("/item-type/summary")
	public SuccessResponse itemTypeSummaryDetail(@RequestParam Optional<Integer> branchId) {
		getDate();
		if (userProfile.getProfile().getAuthorities()
				.stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report", branchDashboardService.itemTypeSummaryByBranchId(
					userProfile.getProfile().getBranch().getId(), startMonth, startWeek, today));
		} else {

			if (branchId.isPresent())
				return new SuccessResponse("00", "fetch report",
						branchDashboardService.itemTypeSummaryByBranchId(branchId.get(), startMonth, startWeek, today));
			else
				return new SuccessResponse("00", "fetch report", branchDashboardService.itemTypeSummaryByCopId(
						userProfile.getProfile().getCorporate().getId(), startMonth, startWeek, today));
		}

	}

	@GetMapping("/item-type-chart/summary")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse itemTypeSummaryChart(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
	
		if (userProfile.getProfile().getAuthorities()
				.stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report",
					branchDashboardService.itemTypeChartByBranchId(userProfile.getProfile().getBranch().getId(), from, to));
		} else {
			return new SuccessResponse("00", "fetch report", branchDashboardService
					.itemTypeChartByCopId(userProfile.getProfile().getCorporate().getId(), from, to));
		}

	}

	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping("/detail")
	public SuccessResponse saleDetail(@RequestParam Integer page, @RequestParam Integer length,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, @RequestParam Optional<Integer> branchId) {
		Pageable pageable = PageRequest.of(page, length);
		if (userProfile.getProfile().getAuthorities()
				.stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report", branchDashboardService
					.saleDetailByBranchId(userProfile.getProfile().getBranch().getId(), from, to, pageable));
		} else {
			if (branchId.isPresent()) {
				return new SuccessResponse("00", "fetch report",
						branchDashboardService.saleDetailByBranchId(branchId.get(), from, to, pageable));
			} else {
				return new SuccessResponse("00", "fetch report", branchDashboardService
						.saleDetailByCorporateId(userProfile.getProfile().getCorporate().getId(), from, to, pageable));
			}
		}

	}
}
