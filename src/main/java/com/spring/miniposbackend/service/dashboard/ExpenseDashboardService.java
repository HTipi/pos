package com.spring.miniposbackend.service.dashboard;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.modelview.dashboard.ExpenseSummaryDetail;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class ExpenseDashboardService {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	@Autowired
	private UserProfileUtil userProfile;

	public List<ExpenseSummaryDetail> expenseSummaryByBranchId(Integer branchId, Date startDate, Date endDate) {
		if (userProfile.getProfile().getBranch().getId() != branchId) {
			throw new UnauthorizedException("You are unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("branchId", branchId);
		return jdbc.query(
				"select exps.branch_id, " + "max(branch.name) as branch_name, "
						+ "max(branch.name_kh) as branch_name_kh, " + "sum(expense_amt) as expense_amount "
						+ "from expense exps " + "inner join branches branch on exps.branch_id = branch.id "
						+ "where exps.reverse = false "
						+ "and date_trunc('day',exps.value_date) between :startDate and :endDate "
						+ "and exps.branch = :branchId " + "group by exps.branch_id",
				mapSqlParameterSource, (rs, rowNum) -> new ExpenseSummaryDetail(rs.getInt("branch_id"),
						rs.getString("branch_name"), rs.getString("branch_name_kh"), rs.getDouble("expense_amount")));
	}
}
