package com.spring.miniposbackend.service.sale;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Map<String, Object> getTransactionSummary(Integer branchId, Date startDate, Date endDate) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("branchId", branchId);
		if (userProfile.getProfile().getBranch().getId() != branchId) {
			throw new UnauthorizedException("Branch is unauthorized");
		}
		Map<String, Object> response = new HashMap<String, Object>();
		SaleDetailSummary summary = jdbc.queryForObject(
				"select count(case when sale.reverse = true then 1 else null end) as void_invoice, "
						+ "count(case when sale.reverse = false then 1 else null end) as paid_invoice, "
						+ "sum(case when sale.reverse = false then sub_total else 0 end) as sub_total, "
						+ "sum(case when sale.reverse = false then discount_total else 0 end) as discount_total "
						+ "where sale.branch_id = :branchId "
						+ "and date_trunc('day',sale.value_date) between :startDate and :endDate",
				mapSqlParameterSource, (rs, rowNum) -> new SaleDetailSummary(rs.getInt(""), rs.getInt(""),
						rs.getDouble(""), rs.getDouble("")));
		List<SaleDetailTransaction> details = jdbc.query("select ib.id as item_id, " + "max(i.name) as item_name, "
				+ "sum(sale.quantity) as quantity, " + "sum(ROUND(sale.quantity*sale.price::numeric,2)) as sub_total, "
				+ "sum(ROUND((sale.quantity*sale.price*sale.discount_percentage/100)::numeric,2)-sale.discount_amount) as discount_total "
				+ "from sale_details sale " + "inner join item_branches ib on sale.item_branch_id = ib.id "
				+ "inner join items i on ib.item_id=i.id "
//				+ "inner join branches branch on sale.branch_id = branch.id "
//				+ "inner join corporates corporate on branch.corporate_id = corporate.id "
				+ "where sale.branch_id = :branchId " + "and sale.reverse = false "
				+ "and date_trunc('day',sale.value_date) between :startDate and :endDate " + "group by ib.id",
				mapSqlParameterSource,
				(rs, rowNum) -> new SaleDetailTransaction(rs.getLong("item_id"), rs.getString("item_name"),
						rs.getInt("quantity"), rs.getDouble("sub_total"), rs.getDouble("discount_total")));
		response.put("summary", summary);
		response.put("details", details);
		return response;

	}

}
