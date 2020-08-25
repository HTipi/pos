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
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.modelview.dashboard.BranchSummaryDetail;
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

	public List<BranchSummaryDetail> summaryDetail(Integer corporateId, Date startDate, Date startWeek, Date endDate) {
		if (userProfile.getProfile().getCorporate().getId() != corporateId) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("startWeek", startWeek);
		mapSqlParameterSource.addValue("corporateId", corporateId);

		return jdbc.query("select sale.branch_id, " 
				+ "max(branch.name) as branch_name, "
				+ "max(branch.name_kh) as branch_name_kh,"
				+ "sum(case when date_trunc('day',sale.value_date) between :startDate and :endDate then sale.quantity else 0  end) as monthly_sale, "
				+ "sum(case when date_trunc('day',sale.value_date) between :startWeek and :endDate then sale.quantity else 0 end) as weekly_sale, "
				+ "sum(case when date_trunc('day',sale.value_date) between :endDate and :endDate then sale.quantity else 0 end) as daily_sale "
				+ "from sale_details sale "
				+ "inner join branches branch on sale.branch_id = branch.id "
				+ "inner join corporates corporate on branch.corporate_id = corporate.id "
				+ "where sale.reverse = false "
				+ "and date_trunc('day',sale.value_date) between :startDate and :endDate "
				+ "and corporate.id = :corporateId " 
				+ "group by sale.branch_id",
				mapSqlParameterSource,
				(rs, rowNum) -> new BranchSummaryDetail(rs.getInt("branch_id"), rs.getString("branch_name"),
						rs.getString("branch_name_kh"), rs.getDouble("monthly_sale"), rs.getDouble("weekly_sale"),
						rs.getDouble("daily_sale")));
	}

	public Page<SaleDetail> saleDetailByCorporateId(Integer corporateId, Date from, Date to, Pageable pageable) {
		if (userProfile.getProfile().getCorporate().getId() != corporateId && !userProfile.getProfile().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
			throw new UnauthorizedException("Corporate is unauthorized");
		}
		return saleRepository.findByCorporateId(corporateId, from, to, pageable);
	}
	public Page<SaleDetail> saleDetailByBranchId(Integer branchId, Date from, Date to, Pageable pageable) {
		if (userProfile.getProfile().getBranch().getId() != branchId && !userProfile.getProfile().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
			throw new UnauthorizedException("Branch is unauthorized");
		}
		return saleRepository.findByBranchId(branchId, from, to, pageable);
	}
}
