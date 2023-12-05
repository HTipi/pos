package com.spring.miniposbackend.service.dashboard;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.expense.Expense;
import com.spring.miniposbackend.modelview.dashboard.ExpenseSummaryDetail;
import com.spring.miniposbackend.modelview.dashboard.ExpenseTypeSummaryDetail;
import com.spring.miniposbackend.repository.expense.ExpenseRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class ExpenseDashboardService {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private ExpenseRepository expenseRepository;

	public List<ExpenseSummaryDetail> expenseSummaryByBranchId(Integer branchId, Date startDate, Date startWeek,
			Date endDate) {
		if (userProfile.getProfile().getBranch().getId() != branchId) {
			throw new UnauthorizedException("You are unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("startWeek", startWeek);
		mapSqlParameterSource.addValue("branchId", branchId);
		return jdbc.query("select exps.branch_id, "
				+ "max(branch.name) as branch_name,max(branch.name_kh) as branch_name_kh, "
				+ "sum(case when date_trunc('day',exps.value_date) between :startDate and :endDate then exps.expense_amt else 0  end) as monthly_sale_amount, "
				+ "sum(case when date_trunc('day',exps.value_date) between :startWeek and :endDate then exps.expense_amt else 0 end) as weekly_sale_amount, "
				+ "sum(case when date_trunc('day',exps.value_date) between :endDate and :endDate then exps.expense_amt else 0 end) as daily_sale_amount "
				+ "from expense exps " + "inner join branches branch on exps.branch_id = branch.id "
				+ "where exps.reverse = false "
				+ "and date_trunc('day',exps.value_date) between :startDate and :endDate "
				+ "and exps.branch_id = :branchId " + "group by exps.branch_id", mapSqlParameterSource,
				(rs, rowNum) -> new ExpenseSummaryDetail(rs.getInt("branch_id"), rs.getString("branch_name"),
						rs.getString("branch_name_kh"), rs.getDouble("monthly_sale_amount"),
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount")));
	}

	public List<ExpenseTypeSummaryDetail> expenseTypeSummaryByBranchId(Integer branchId, Date startDate, Date endDate) {
//		if (userProfile.getProfile().getBranch().getId() != branchId) {
//			throw new UnauthorizedException("You are unauthorized");
//		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("branchId", branchId);
		return jdbc.query("SELECT ext.id as expenseTypeId,ext.name as expenseTypeName,ext.name_kh as expenseTypeKh,"
				+ "sum(ex.expense_amt) as expenseAmt,count(ex.id) as expenseItem "
				+ "FROM expense ex inner join expense_type ext on ex.expense_type_id=ext.id "
				+ "where ex.reverse = false  and ex.branch_id = :branchId "
				+ "and date_trunc('day',ex.value_date) between :startDate and :endDate group by ext.id,ext.name,ext.name_kh ",
				mapSqlParameterSource,
				(rs, rowNum) -> new ExpenseTypeSummaryDetail(rs.getInt("expenseTypeId"),
						rs.getString("expenseTypeName"), rs.getString("expenseTypeKh"), rs.getDouble("expenseAmt"),
						rs.getInt("expenseItem")));
	}

	

	public Page<Expense> expenseDetailByBranchId(Integer branchId, Date from, Date to, Pageable pageable) {
		if (userProfile.getProfile().getBranch().getId() != branchId) {
			throw new UnauthorizedException("Branch is unauthorized");
		}
		return expenseRepository.findByBranchId(branchId, from, to, pageable);
	}
}
