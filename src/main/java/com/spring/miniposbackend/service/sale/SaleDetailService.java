package com.spring.miniposbackend.service.sale;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.modelview.SaleDetailSummary;
import com.spring.miniposbackend.modelview.SaleDetailTransaction;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class SaleDetailService {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	@Autowired
	private UserProfileUtil userProfile;

	public List<SaleDetail> showByTable(Integer tableId) {
		return null;
	}

	public SaleDetailSummary getTransactionSummary(Date startDate, Date endDate, boolean byUser) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		String queryCondition = "and sale.branch_id = :branchId ";
		if (byUser) {
			if (userProfile.getProfile().getUser().getId() != userProfile.getProfile().getUser().getId()) {
				throw new UnauthorizedException("User is unauthorized");
			}
			mapSqlParameterSource.addValue("userId", userProfile.getProfile().getUser().getId());
			queryCondition = "and sale.user_id = :userId ";
		} else {
			if (userProfile.getProfile().getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			mapSqlParameterSource.addValue("branchId", userProfile.getProfile().getBranch().getId());
		}

		SaleDetailSummary summary = jdbc.queryForObject(
				"select count(case when sale.reverse = true then 1 else null end) as void_invoice, "
						+ "count(case when sale.reverse = false then 1 else null end) as paid_invoice, "
						+ "min(value_date) as start_date, " + "max(value_date) as end_date, "
						+ "sum(case when sale.reverse = false then sale.sub_total else 0 end) as sub_total, "
						+ "sum(case when sale.reverse = false then sale.discount_amount else 0 end) as discount_amount, "
						+ "sum(case when sale.reverse = false then sale.discount_sale_detail else 0 end) as discount_sale_detail "
						+ "from sales sale where date_trunc('day',sale.value_date) between :startDate and :endDate "
						+ queryCondition,
				mapSqlParameterSource,
				(rs, rowNum) -> new SaleDetailSummary(rs.getInt("void_invoice"), rs.getInt("paid_invoice"),
						rs.getString("start_date"), rs.getString("end_date"), rs.getDouble("sub_total"),
						rs.getDouble("discount_amount"), rs.getDouble("discount_sale_detail")));
		List<SaleDetailTransaction> details = jdbc
				.query("select ib.id as item_id, " + "max(i.name) as item_name, " + "sum(sale.quantity) as quantity, "
						+ "sum(sub_total) as sub_total, " + "sum(sale.discount_total) as discount_total, i.item_type_id,is_stock,stock_in-stock_out stocks "
						+ "from sale_details sale " + "inner join item_branches ib on sale.item_branch_id = ib.id "
						+ "inner join items i on ib.item_id=i.id "
//				+ "inner join branches branch on sale.branch_id = branch.id "
//				+ "inner join corporates corporate on branch.corporate_id = corporate.id "
						+ "where sale.reverse = false and i.type='MAINITEM' " + queryCondition
						+ "and date_trunc('day',sale.value_date) between :startDate and :endDate " + "group by ib.id,item_type_id,is_stock,stock_in,stock_out",
						mapSqlParameterSource,
						(rs, rowNum) -> new SaleDetailTransaction(rs.getLong("item_id"), rs.getString("item_name"),
								rs.getDouble("quantity"), rs.getDouble("sub_total"), rs.getDouble("discount_total"),rs.getInt("item_type_id"),rs.getBoolean("is_stock"),rs.getInt("stocks")));
		summary.setDetails(details);
		return summary;

	}
	public SaleDetailSummary getTransactionRangeSummary(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate, boolean byUser) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		String queryCondition = "and sale.branch_id = :branchId ";
		if (byUser) {
			if (userProfile.getProfile().getUser().getId() != userProfile.getProfile().getUser().getId()) {
				throw new UnauthorizedException("User is unauthorized");
			}
			mapSqlParameterSource.addValue("userId", userProfile.getProfile().getUser().getId());
			queryCondition = "and sale.user_id = :userId ";
		} else {
			if (userProfile.getProfile().getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			mapSqlParameterSource.addValue("branchId", userProfile.getProfile().getBranch().getId());
		}

		SaleDetailSummary summary = jdbc.queryForObject(
				"select count(case when sale.reverse = true then 1 else null end) as void_invoice, "
						+ "count(case when sale.reverse = false then 1 else null end) as paid_invoice, "
						+ "min(value_date) as start_date, " + "max(value_date) as end_date, "
						+ "sum(case when sale.reverse = false then sale.sub_total else 0 end) as sub_total, "
						+ "sum(case when sale.reverse = false then sale.discount_amount else 0 end) as discount_amount, "
						+ "sum(case when sale.reverse = false then sale.discount_sale_detail else 0 end) as discount_sale_detail "
						+ "from sales sale where sale.value_date between :startDate and :endDate "
						+ queryCondition,
				mapSqlParameterSource,
				(rs, rowNum) -> new SaleDetailSummary(rs.getInt("void_invoice"), rs.getInt("paid_invoice"),
						rs.getString("start_date"), rs.getString("end_date"), rs.getDouble("sub_total"),
						rs.getDouble("discount_amount"), rs.getDouble("discount_sale_detail")));
		List<SaleDetailTransaction> details = jdbc
				.query("select ib.id as item_id, " + "max(i.name) as item_name, " + "sum(sale.quantity) as quantity, "
						+ "sum(sub_total) as sub_total, " + "sum(sale.discount_total) as discount_total, i.item_type_id,is_stock,stock_in-stock_out stocks "
						+ "from sale_details sale " + "inner join item_branches ib on sale.item_branch_id = ib.id "
						+ "inner join items i on ib.item_id=i.id "
//				+ "inner join branches branch on sale.branch_id = branch.id "
//				+ "inner join corporates corporate on branch.corporate_id = corporate.id "
						+ "where sale.reverse = false and i.type='MAINITEM' " + queryCondition
						+ "and sale.value_date between :startDate and :endDate " + "group by ib.id,item_type_id,is_stock,stock_in,stock_out",
						mapSqlParameterSource,
						(rs, rowNum) -> new SaleDetailTransaction(rs.getLong("item_id"), rs.getString("item_name"),
								rs.getDouble("quantity"), rs.getDouble("sub_total"), rs.getDouble("discount_total"),rs.getInt("item_type_id"),rs.getBoolean("is_stock"),rs.getInt("stocks")));
		summary.setDetails(details);
		return summary;

	}

}
