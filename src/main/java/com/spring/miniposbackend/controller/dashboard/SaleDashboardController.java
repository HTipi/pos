package com.spring.miniposbackend.controller.dashboard;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.dashboard.SaleDashboardService;
import com.spring.miniposbackend.util.UserProfileUtil;

import net.sf.jasperreports.engine.JRException;

@RestController
@CrossOrigin
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

		if (userProfile.getProfile().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report", branchDashboardService.branchSummaryByBranchId(
					userProfile.getProfile().getBranch().getId(), startMonth, startWeek, today));
		} else {
			if (branchId.isPresent())

				return new SuccessResponse("00", "fetch report",
						branchDashboardService.branchSummaryByBranchId(branchId.get(), startMonth, startWeek, today));
			else
				return new SuccessResponse("00", "fetch report", branchDashboardService.itemSummaryByCorporateId(
						userProfile.getProfile().getCorporate().getId(), startMonth, startWeek, today));
		}

	}

	@GetMapping("/channel/receipt")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse channelReceipt(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from) {

		return new SuccessResponse("00", "fetch report",
				branchDashboardService.channelReceipt(userProfile.getProfile().getBranch().getId(), from));
	}
	@GetMapping("/channel/receipts")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse channelReceipts(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date from,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {

		return new SuccessResponse("00", "fetch report",
				branchDashboardService.channelReceipts(userProfile.getProfile().getBranch().getId(), from,end));
	}

	@GetMapping("/promotion/receipt")
	@PreAuthorize("hasAnyRole('SALE')")
	public SuccessResponse promotionReceipt(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from) {

		return new SuccessResponse("00", "fetch report",
				branchDashboardService.promotionReceipt(userProfile.getProfile().getBranch().getId(), from));
	}

	@GetMapping("/branch-chart/summary")
	@PreAuthorize("hasAnyRole('OWNER')")
	public SuccessResponse branchSummaryChart(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
		return new SuccessResponse("00", "fetch report",
				branchDashboardService.branchChartByCopId(userProfile.getProfile().getCorporate().getId(), from, to));
	}

	@GetMapping("/item/summary")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse chhanelSummaryDetail(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> to,
			@RequestParam Optional<Integer> branchId) {
		if (!from.isPresent())
			getDate();
		if (userProfile.getProfile().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report", branchDashboardService
					.itemSummaryByBranchId(userProfile.getProfile().getBranch().getId(), startMonth, startWeek, today));
		} else {
			if (branchId.isPresent()) {
				return new SuccessResponse("00", "fetch report",
						branchDashboardService.itemSummaryByBranchId(branchId.get(), startMonth, startWeek, today));
			} else
				return new SuccessResponse("00", "fetch report", branchDashboardService.itemSummaryByCorporateId(
						userProfile.getProfile().getCorporate().getId(), startMonth, startWeek, today));
		}

	}

	@GetMapping("/item/list")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse itemListDetail(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> to,
			@RequestParam Optional<Integer> branchId, @RequestParam Integer itemTypeId) {
		return new SuccessResponse("00", "fetch report",
				branchDashboardService.itemListByBranchId(itemTypeId, branchId.get(), from.get(), to.get()));

	}

	@GetMapping("/item-chart/summary")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse itemSummaryChart(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, @RequestParam Optional<Integer> branchId) {
		if (userProfile.getProfile().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
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
		if (userProfile.getProfile().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
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

	@GetMapping("/chart/summary")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse channelSummaryChart(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, @RequestParam Optional<Integer> branchId,@RequestParam String name) {
		return new SuccessResponse("00", "fetch report",
				branchDashboardService.SummaryByBranchId(branchId.get(), from, to, name));

	}

	@GetMapping("/item-type-chart/summary")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse itemTypeSummaryChart(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, @RequestParam Optional<Integer> branchId) {

		if (userProfile.getProfile().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report",
					branchDashboardService.itemTypeChartByBranchId(branchId.get(), from, to));
		} else {

			return new SuccessResponse("00", "fetch report", branchDashboardService
					.itemTypeChartByCopId(userProfile.getProfile().getCorporate().getId(), from, to));
		}

	}

	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping("/channel/summary")
	public SuccessResponse ChannelSummaryDetail(@RequestParam Optional<Integer> branchId) {
		getDate();
		return new SuccessResponse("00", "fetch report", branchDashboardService
				.SummaryByBranchId(userProfile.getProfile().getBranch().getId(), today, today, "channel"));

	}
	
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping("/promotion/summary")
	public SuccessResponse promotionSummaryDetail(@RequestParam Optional<Integer> branchId) {
		getDate();
		return new SuccessResponse("00", "fetch report", branchDashboardService
				.SummaryByBranchId(userProfile.getProfile().getBranch().getId(), today, today, "promotion"));

	}

	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping("/table/summary")
	public SuccessResponse customerSummaryDetail(@RequestParam Optional<Integer> branchId) {
		getDate();
		return new SuccessResponse("00", "fetch report", branchDashboardService
				.SummaryByBranchId(userProfile.getProfile().getBranch().getId(), today, today, "table"));

	}

	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping("/user/summary")
	public SuccessResponse UserSummaryDetail(@RequestParam Optional<Integer> branchId) {
		getDate();
		return new SuccessResponse("00", "fetch report", branchDashboardService
				.SummaryByBranchId(userProfile.getProfile().getBranch().getId(), today, today, "user"));

	}

	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping("/detail")
	public SuccessResponse saleDetail(@RequestParam Integer page, @RequestParam Integer length,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to, @RequestParam Optional<Integer> branchId) {
		Pageable pageable = PageRequest.of(page, length);
		if (userProfile.getProfile().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BRANCH"))) {
			return new SuccessResponse("00", "fetch report",
					branchDashboardService.saleDetailByBranchId(branchId.get(), from, to, pageable));
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

	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping(value = "/download/saleitemreport")
	ResponseEntity<Void> downloadTransactionReport(@RequestParam(value = "exportType") String exportType,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
			@RequestParam(value = "branchId") Integer branchId, @RequestParam Optional<Boolean> detail,
			HttpServletResponse response) throws IOException, JRException, SQLException {
		try {
			if (detail.isPresent()) {
				branchDashboardService.downloadTransactionReport(exportType, response, "sale_matrix", from, to,
						branchId);
			} else {
				branchDashboardService.downloadTransactionReport(exportType, response, "sale_item", from, to, branchId);
			}

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ConflictException(e.getMessage(), "01");
			// TODO: handle exception
		}

	}

	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping(value = "/download/matrixreport")
	ResponseEntity<Void> downloadChannelTransaction(@RequestParam(value = "exportType") String exportType,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
			@RequestParam(value = "branchId") Integer branchId, @RequestParam(value = "reportname") String reportname,
			HttpServletResponse response) throws IOException, JRException, SQLException {
		try {

			branchDashboardService.downloadTransactionReport(exportType, response, reportname, from, to, branchId);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ConflictException(e.getMessage(), "01");
			// TODO: handle exception
		}

	}

	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	@GetMapping(value = "/download/incomestatement")
	ResponseEntity<Void> downloadProfitLoss(@RequestParam(value = "exportType") String exportType,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
			@RequestParam(value = "branchId") Integer branchId, HttpServletResponse response)
			throws IOException, JRException, SQLException {
		try {
			branchDashboardService.downloadTransactionReport(exportType, response, "profitloss", from, to, branchId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ConflictException(e.getMessage(), "01");
			// TODO: handle exception
		}

	}
}
