package com.spring.miniposbackend.service.dashboard;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.modelview.dashboard.BranchSummaryChart;
import com.spring.miniposbackend.modelview.dashboard.BranchSummaryDetail;
import com.spring.miniposbackend.modelview.dashboard.ItemSummaryChart;
import com.spring.miniposbackend.modelview.dashboard.ItemSummaryDetail;
import com.spring.miniposbackend.modelview.dashboard.ItemTypeSummaryChart;
import com.spring.miniposbackend.modelview.dashboard.ItemTypeSummaryDetail;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.sale.SaleDetailRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class SaleDashboardService {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	@Autowired
	private SaleDetailRepository saleRepository;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private BranchRepository branchRepository;

	public List<BranchSummaryDetail> branchSummaryByCorpateId(Integer corporateId, Date startDate, Date startWeek,
			Date endDate) {
		if (userProfile.getProfile().getCorporate().getId() != corporateId) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("startWeek", startWeek);
		mapSqlParameterSource.addValue("corporateId", corporateId);

		return jdbc.query("select * from branchsummarybycopid(:corporateId,:startDate,:startWeek,:endDate)",
				mapSqlParameterSource,
				(rs, rowNum) -> new BranchSummaryDetail(rs.getInt("branch_id"), rs.getString("branch_name"),
						rs.getString("branch_name_kh"), rs.getInt("monthly_sale"), rs.getInt("weekly_sale"),
						rs.getInt("daily_sale"), rs.getDouble("monthly_sale_amount"),
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),
						rs.getDouble("monthly_discount_amount"), rs.getDouble("weekly_discount_amount"),
						rs.getDouble("daily_discount_amount")));
	}

	public List<BranchSummaryDetail> branchSummaryByBranchId(Integer branchId, Date startDate, Date startWeek,
			Date endDate) {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("startWeek", startWeek);
		mapSqlParameterSource.addValue("branchId", branchId);

		return jdbc.query("select * from branchsummarybybranchid(:branchId,:startDate,:startWeek,:endDate)",
				mapSqlParameterSource,
				(rs, rowNum) -> new BranchSummaryDetail(rs.getInt("branch_id"), rs.getString("branch_name"),
						rs.getString("branch_name_kh"), rs.getInt("monthly_sale"), rs.getInt("weekly_sale"),
						rs.getInt("daily_sale"), rs.getDouble("monthly_sale_amount"),
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),
						rs.getDouble("monthly_discount_amount"), rs.getDouble("weekly_discount_amount"),
						rs.getDouble("daily_discount_amount")));

	}

	public List<BranchSummaryChart> branchChartByCopId(Integer copId, Date startDate, Date endDate) {
		if (userProfile.getProfile().getCorporate().getId() != copId) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("copId", copId);
		return jdbc.query("select * from branchChartByCopId(:copId,:startDate,:endDate)", mapSqlParameterSource,
				(rs, rowNum) -> new BranchSummaryChart(rs.getLong("branchId"), rs.getString("branchName"),
						rs.getString("branchKh"), rs.getDouble("saleAmt"),rs.getDouble("disAmt"), rs.getInt("saleItem")));
	}

	public List<ItemTypeSummaryDetail> itemTypeSummaryByBranchId(Integer branchId, Date startDate, Date startWeek,
			Date endDate) {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("startWeek", startWeek);
		mapSqlParameterSource.addValue("branchId", branchId);

		return jdbc.query("select * from itemtypesummarybybranchid(:branchId,:startDate,:startWeek,:endDate)",
				mapSqlParameterSource,
				(rs, rowNum) -> new ItemTypeSummaryDetail(rs.getLong("itemTypeId"), rs.getString("itemTypeName"),
						rs.getString("itemTypeKh"), rs.getInt("monthly_sale"), rs.getInt("weekly_sale"),
						rs.getInt("daily_sale"), rs.getDouble("monthly_sale_amount"),
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),rs.getDouble("monthly_discount_amount"),
						rs.getDouble("weekly_discount_amount"), rs.getDouble("daily_discount_amount")));

	}

	public List<ItemTypeSummaryDetail> itemTypeSummaryByCopId(Integer corporateId, Date startDate, Date startWeek,
			Date endDate) {
		if (userProfile.getProfile().getCorporate().getId() != corporateId) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("startWeek", startWeek);
		mapSqlParameterSource.addValue("corporateId", corporateId);
		return jdbc.query("select * from itemtypesummarybycopid(:corporateId,:startDate,:startWeek,:endDate)",
				mapSqlParameterSource,
				(rs, rowNum) -> new ItemTypeSummaryDetail(rs.getLong("itemTypeId"), rs.getString("itemTypeName"),
						rs.getString("itemTypeKh"), rs.getInt("monthly_sale"), rs.getInt("weekly_sale"),
						rs.getInt("daily_sale"), rs.getDouble("monthly_sale_amount"),
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),rs.getDouble("monthly_discount_amount"),
						rs.getDouble("weekly_discount_amount"), rs.getDouble("daily_discount_amount")));
	}

	public List<ItemTypeSummaryChart> itemTypeChartByBranchId(Integer branchId, Date startDate, Date endDate) {
		if (userProfile.getProfile().getBranch().getId() != branchId) {
			throw new UnauthorizedException("You are unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("branchId", branchId);
		return jdbc.query("select * from itemTypeChartByBranchId(:branchId,:startDate,:endDate)", mapSqlParameterSource,
				(rs, rowNum) -> new ItemTypeSummaryChart(rs.getInt("itemTypeId"), rs.getString("itemTypeName"),
						rs.getString("itemTypeKh"), rs.getDouble("saleAmt"),rs.getDouble("disAmt"), rs.getInt("saleItem")));
	}

	public List<ItemTypeSummaryChart> itemTypeChartByCopId(Integer corporateId, Date startDate, Date endDate) {
		if (userProfile.getProfile().getCorporate().getId() != corporateId) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("corporateId", corporateId);
		return jdbc.query("select * from itemTypeChartByCopId(:corporateId,:startDate,:endDate)", mapSqlParameterSource,
				(rs, rowNum) -> new ItemTypeSummaryChart(rs.getInt("itemTypeId"), rs.getString("itemTypeName"),
						rs.getString("itemTypeKh"), rs.getDouble("saleAmt"),rs.getDouble("disAmt"), rs.getInt("saleItem")));
	}

	public List<ItemSummaryDetail> itemSummaryByCorporateId(Integer corporateId, Date startDate, Date startWeek,
			Date endDate) {
		if (userProfile.getProfile().getCorporate().getId() != corporateId) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("startWeek", startWeek);
		mapSqlParameterSource.addValue("corporateId", corporateId);

		return jdbc.query("select * from itemsummarybycopid(:corporateId,:startDate,:startWeek,:endDate)",
				mapSqlParameterSource,
				(rs, rowNum) -> new ItemSummaryDetail(rs.getLong("item_id"), rs.getString("item_name"),
						rs.getString("item_name_kh"), rs.getInt("monthly_sale"), rs.getInt("weekly_sale"),
						rs.getInt("daily_sale"), rs.getDouble("monthly_sale_amount"),
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),rs.getDouble("monthly_discount_amount"),
						rs.getDouble("weekly_discount_amount"), rs.getDouble("daily_discount_amount")));
	}

	public List<ItemSummaryDetail> itemSummaryByBranchId(Integer branchId, Date startDate, Date startWeek,
			Date endDate) {

		return branchRepository.findById(branchId).map((branch) -> {
			if (userProfile.getProfile().getCorporate().getId() != branch.getCorporate().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
			mapSqlParameterSource.addValue("startDate", startDate);
			mapSqlParameterSource.addValue("endDate", endDate);
			mapSqlParameterSource.addValue("startWeek", startWeek);
			mapSqlParameterSource.addValue("branchId", branchId);

			return jdbc.query("select * from itemsummarybybranchid(:branchId,:startDate,:startWeek,:endDate)",
					mapSqlParameterSource,
					(rs, rowNum) -> new ItemSummaryDetail(rs.getLong("item_id"), rs.getString("item_name"),
							rs.getString("item_name_kh"), rs.getInt("monthly_sale"), rs.getInt("weekly_sale"),
							rs.getInt("daily_sale"), rs.getDouble("monthly_sale_amount"),
							rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),rs.getDouble("monthly_discount_amount"),
							rs.getDouble("weekly_discount_amount"), rs.getDouble("daily_discount_amount")));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

	}

	public List<ItemSummaryChart> itemChartByBranchId(Integer branchId, Date startDate, Date endDate) {
		if (userProfile.getProfile().getBranch().getId() != branchId) {
			throw new UnauthorizedException("You are unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("branchId", branchId);
		return jdbc.query("select * from itemChartByBranchId(:branchId,:startDate,:endDate)", mapSqlParameterSource,
				(rs, rowNum) -> new ItemSummaryChart(rs.getLong("itemId"), rs.getString("itemName"),
						rs.getString("itemKh"), rs.getDouble("saleAmt"),rs.getDouble("disAmt"), rs.getInt("saleItem")));
	}

	public List<ItemSummaryChart> itemChartByCopId(Integer copId, Date startDate, Date endDate) {
		if (userProfile.getProfile().getCorporate().getId() != copId) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("copId", copId);
		return jdbc.query("select * from itemChartByCopId(:copId,:startDate,:endDate)", mapSqlParameterSource,
				(rs, rowNum) -> new ItemSummaryChart(rs.getLong("itemId"), rs.getString("itemName"),
						rs.getString("itemKh"), rs.getDouble("saleAmt"),rs.getDouble("disAmt"), rs.getInt("saleItem")));
	}

	public Page<SaleDetail> saleDetailByCorporateId(Integer corporateId, Date from, Date to, Pageable pageable) {

		return saleRepository.findByCorporateId(corporateId, from, to, pageable);
	}

	public Page<SaleDetail> saleDetailByBranchId(Integer branchId, Date from, Date to, Pageable pageable) {

		return branchRepository.findById(branchId).map((branch) -> {
			if (userProfile.getProfile().getCorporate().getId() != branch.getCorporate().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			return saleRepository.findByBranchId(branchId, from, to, pageable);
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
	}
}
