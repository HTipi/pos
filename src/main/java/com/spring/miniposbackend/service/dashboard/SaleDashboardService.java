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
import com.spring.miniposbackend.modelview.dashboard.BranchSummaryDetail;
import com.spring.miniposbackend.modelview.dashboard.ExpenseTypeSummaryDetail;
import com.spring.miniposbackend.modelview.dashboard.ItemSummaryDetail;
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

		return jdbc.query("select sale.branch_id, " + "max(branch.name) as branch_name, "
				+ "max(branch.name_kh) as branch_name_kh,"
				+ "sum(case when date_trunc('day',sale.value_date) between :startDate and :endDate then sale.quantity else 0  end) as monthly_sale, "
				+ "sum(case when date_trunc('day',sale.value_date) between :startWeek and :endDate then sale.quantity else 0 end) as weekly_sale, "
				+ "sum(case when date_trunc('day',sale.value_date) between :endDate and :endDate then sale.quantity else 0 end) as daily_sale ,"
				+ "sum(case when date_trunc('day',sale.value_date) between :startDate and :endDate then sale.total else 0  end) as monthly_sale_amount, "
				+ "sum(case when date_trunc('day',sale.value_date) between :startWeek and :endDate then sale.total else 0 end) as weekly_sale_amount, "
				+ "sum(case when date_trunc('day',sale.value_date) between :endDate and :endDate then sale.total else 0 end) as daily_sale_amount "
				+ "from sale_details sale " + "inner join branches branch on sale.branch_id = branch.id "
				+ "inner join corporates corporate on branch.corporate_id = corporate.id "
				+ "where sale.reverse = false "
				+ "and date_trunc('day',sale.value_date) between :startDate and :endDate "
				+ "and corporate.id = :corporateId " + "group by sale.branch_id", mapSqlParameterSource,
				(rs, rowNum) -> new BranchSummaryDetail(rs.getInt("branch_id"), rs.getString("branch_name"),
						rs.getString("branch_name_kh"), rs.getInt("monthly_sale"), rs.getInt("weekly_sale"),
						rs.getInt("daily_sale"), rs.getDouble("monthly_sale_amount"),
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount")));
	}

	public List<ItemSummaryDetail> itemSummaryByBranchId(Integer branchId, Date startDate, Date startWeek,
			Date endDate) {

		if (userProfile.getProfile().getBranch().getId() != branchId && !userProfile.getProfile().getAuthorities()
				.stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
			throw new UnauthorizedException("You are unauthorized");
		}
		return branchRepository.findById(branchId).map((branch) -> {
			if (userProfile.getProfile().getCorporate().getId() != branch.getCorporate().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
			mapSqlParameterSource.addValue("startDate", startDate);
			mapSqlParameterSource.addValue("endDate", endDate);
			mapSqlParameterSource.addValue("startWeek", startWeek);
			mapSqlParameterSource.addValue("branchId", branchId);

			return jdbc.query("select item.id as item_id, " + "max(item.name) as item_name, "
					+ "max(item.name_kh) as item_name_kh,"
					+ "sum(case when date_trunc('day',sale.value_date) between :startDate and :endDate then sale.quantity else 0  end) as monthly_sale, "
					+ "sum(case when date_trunc('day',sale.value_date) between :startWeek and :endDate then sale.quantity else 0 end) as weekly_sale, "
					+ "sum(case when date_trunc('day',sale.value_date) between :endDate and :endDate then sale.quantity else 0 end) as daily_sale, "
					+ "sum(case when date_trunc('day',sale.value_date) between :startDate and :endDate then sale.total else 0  end) as monthly_sale_amount, "
					+ "sum(case when date_trunc('day',sale.value_date) between :startWeek and :endDate then sale.total else 0 end) as weekly_sale_amount, "
					+ "sum(case when date_trunc('day',sale.value_date) between :endDate and :endDate then sale.total else 0 end) as daily_sale_amount "
					+ "from sale_details sale " + "inner join item_branches ib on sale.item_branch_id=ib.id "
					+ "inner join items item on ib.item_id=item.id " + "where sale.reverse = false "
					+ "and date_trunc('day',sale.value_date) between :startDate and :endDate "
					+ "and sale.branch_id = :branchId " + "group by item.id", mapSqlParameterSource,
					(rs, rowNum) -> new ItemSummaryDetail(rs.getLong("item_id"), rs.getString("item_name"),
							rs.getString("item_name_kh"), rs.getInt("monthly_sale"), rs.getInt("weekly_sale"),
							rs.getInt("daily_sale"), rs.getDouble("monthly_sale_amount"),
							rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount")));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

	}
	public List<ItemTypeSummaryDetail> itemTypeSummaryByBranchId(Integer branchId, Date startDate,
			Date endDate) {
		if (userProfile.getProfile().getBranch().getId() != branchId) {
			throw new UnauthorizedException("You are unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("branchId", branchId);
		return jdbc.query(
				"select * from itemTypeSummaryByBranchId(:branchId,:startDate,:endDate)",
				mapSqlParameterSource, (rs, rowNum) -> new ItemTypeSummaryDetail(rs.getInt("itemTypeId"),
						rs.getString("itemTypeName"), rs.getString("itemTypeKh"), rs.getDouble("saleAmt"),rs.getInt("saleItem")));
	}
	public List<ItemTypeSummaryDetail> itemTypeSummaryByCopId(Integer corporateId, Date startDate,
			Date endDate) {
		if (userProfile.getProfile().getCorporate().getId() != corporateId) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("corporateId", corporateId);
		return jdbc.query(
				"select * from itemTypeSummaryByCopId(:corporateId,:startDate,:endDate)",
				mapSqlParameterSource, (rs, rowNum) -> new ItemTypeSummaryDetail(rs.getInt("itemTypeId"),
						rs.getString("itemTypeName"), rs.getString("itemTypeKh"), rs.getDouble("saleAmt"),rs.getInt("saleItem")));
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

		return jdbc.query("select item.id as item_id, " + "max(item.name) as item_name, "
				+ "max(item.name_kh) as item_name_kh,"
				+ "sum(case when date_trunc('day',sale.value_date) between :startDate and :endDate then sale.quantity else 0  end) as monthly_sale, "
				+ "sum(case when date_trunc('day',sale.value_date) between :startWeek and :endDate then sale.quantity else 0 end) as weekly_sale, "
				+ "sum(case when date_trunc('day',sale.value_date) between :endDate and :endDate then sale.quantity else 0 end) as daily_sale, "
				+ "sum(case when date_trunc('day',sale.value_date) between :startDate and :endDate then sale.total else 0  end) as monthly_sale_amount, "
				+ "sum(case when date_trunc('day',sale.value_date) between :startWeek and :endDate then sale.total else 0 end) as weekly_sale_amount, "
				+ "sum(case when date_trunc('day',sale.value_date) between :endDate and :endDate then sale.total else 0 end) as daily_sale_amount "
				+ "from sale_details sale " + "inner join item_branches ib on sale.item_branch_id=ib.id "
				+ "inner join items item on ib.item_id=item.id "
				+ "inner join branches branch on sale.branch_id = branch.id "
				+ "inner join corporates corporate on branch.corporate_id = corporate.id "
				+ "where sale.reverse = false "
				+ "and date_trunc('day',sale.value_date) between :startDate and :endDate "
				+ "and corporate.id = :corporateId " + "group by item.id", mapSqlParameterSource,
				(rs, rowNum) -> new ItemSummaryDetail(rs.getLong("item_id"), rs.getString("item_name"),
						rs.getString("item_name_kh"), rs.getInt("monthly_sale"), rs.getInt("weekly_sale"),
						rs.getInt("daily_sale"), rs.getDouble("monthly_sale_amount"),
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount")));
	}

	public Page<SaleDetail> saleDetailByCorporateId(Integer corporateId, Date from, Date to, Pageable pageable) {
		if (userProfile.getProfile().getCorporate().getId() != corporateId || !userProfile.getProfile().getAuthorities()
				.stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		return saleRepository.findByCorporateId(corporateId, from, to, pageable);
	}

	public Page<SaleDetail> saleDetailByBranchId(Integer branchId, Date from, Date to, Pageable pageable) {
		if (userProfile.getProfile().getBranch().getId() != branchId && !userProfile.getProfile().getAuthorities()
				.stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
			throw new UnauthorizedException("You are unauthorized");
		}
		return branchRepository.findById(branchId).map((branch) -> {
			if (userProfile.getProfile().getCorporate().getId() != branch.getCorporate().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			return saleRepository.findByBranchId(branchId, from, to, pageable);
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
	}
}
