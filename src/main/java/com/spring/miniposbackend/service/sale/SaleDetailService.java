package com.spring.miniposbackend.service.sale;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.modelview.SaleDetailSummary;
import com.spring.miniposbackend.modelview.SaleDetailTransaction;
import com.spring.miniposbackend.repository.sale.SaleDetailRepository;
import com.spring.miniposbackend.repository.transaction.TransactionSaleRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class SaleDetailService {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	@Autowired
	private UserProfileUtil userProfile;
	
	@Autowired
	private  SaleDetailRepository saleDetailRepository;
	@Autowired
	private TransactionSaleRepository transactionSaleRepository;

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
						+ "min(end_date) as start_date, " + "max(end_date) as end_date,sum(vat) vat,sum(service_charge) service_charge, "
						+ "sum(case when sale.reverse = false then sale.sub_total else 0 end) as sub_total, "
						+ "sum(case when sale.reverse = false then sale.discount_amount else 0 end) as discount_amount, "
						+ "sum(case when sale.reverse = false then sale.discount_sale_detail else 0 end) as discount_sale_detail "
						+ "from sales sale where date_trunc('day',sale.end_date) between :startDate and :endDate "
						+ queryCondition,
				mapSqlParameterSource,
				(rs, rowNum) -> new SaleDetailSummary(rs.getInt("void_invoice"), rs.getInt("paid_invoice"),
						rs.getString("start_date"), rs.getString("end_date"), rs.getDouble("sub_total"),
						rs.getDouble("discount_amount"), rs.getDouble("discount_sale_detail"),rs.getDouble("vat"),rs.getDouble("service_charge")));
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
		String queryCondition = "and sale_details.branch_id = :branchId ";
		String result = "left join (select sale_details.parent_sale_id,STRING_AGG(i.name_kh,',') name_kh,sale_details.branch_id from sale_details inner join item_branches ib on item_branch_id = ib.id ";
		String groupby = " , sale_details.branch_id ";
		if (byUser) {
			if (userProfile.getProfile().getUser().getId() != userProfile.getProfile().getUser().getId()) {
				throw new UnauthorizedException("User is unauthorized");
			}
			mapSqlParameterSource.addValue("userId", userProfile.getProfile().getUser().getId());
			queryCondition = "and sale_details.user_id = :userId ";
			result = "left join (select sale_details.parent_sale_id,STRING_AGG(i.name_kh,',') name_kh,sale_details.user_id from sale_details inner join item_branches ib on item_branch_id = ib.id ";
			groupby = " , sale_details.user_id ";
		} else {
			if (userProfile.getProfile().getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			mapSqlParameterSource.addValue("branchId", userProfile.getProfile().getBranch().getId());
		}

		SaleDetailSummary summary = jdbc.queryForObject(
				"select count(case when sale_details.reverse = true then 1 else null end) as void_invoice, "
						+ "count(case when sale_details.reverse = false then 1 else null end) as paid_invoice, "
						+ "min(end_date) as start_date, " + "max(end_date) as end_date,sum(vat) vat,sum(service_charge) service_charge, "
						+ "sum(case when sale_details.reverse = false then sale_details.sub_total else 0 end) as sub_total, "
						+ "sum(case when sale_details.reverse = false then sale_details.discount_amount else 0 end) as discount_amount, "
						+ "sum(case when sale_details.reverse = false then sale_details.discount_sale_detail else 0 end) as discount_sale_detail "
						+ "from sales sale_details where sale_details.end_date between :startDate and :endDate "
						+ queryCondition,
				mapSqlParameterSource,
				(rs, rowNum) -> new SaleDetailSummary(rs.getInt("void_invoice"), rs.getInt("paid_invoice"),
						rs.getString("start_date"), rs.getString("end_date"), rs.getDouble("sub_total"),
						rs.getDouble("discount_amount"), rs.getDouble("discount_sale_detail"),rs.getDouble("vat"),rs.getDouble("service_charge")));
	
		List<SaleDetailTransaction> details = jdbc
				.query("select parent.item_branch_id as item_id,(parent.name_kh) || ' ' || COALESCE(child.name_kh,' ') as item_name, sum(parent.quantity) as quantity,"
						+ "sum(parent.sub_total) as sub_total,sum(parent.discount_total) as discount_total,parent.item_type_id,parent.is_stock,parent.stock_in-parent.stock_out stocks "
						+ "from (select sale_details.*,i.name_kh,i.item_type_id,i.is_stock,stock_in,stock_out from sale_details inner join item_branches ib on item_branch_id = ib.id "
						+ "inner join items i on ib.item_id=i.id where reverse = false and value_date between :startDate and :endDate and parent_sale_id is null " + queryCondition  + ") parent "
						+ result
						+ "inner join items i on ib.item_id=i.id where reverse = false and value_date between :startDate and :endDate " + queryCondition  + " group by sale_details.parent_sale_id " + groupby  + ") child on child.parent_sale_id=parent.id "
						//				+ "inner join branches branch on sale.branch_id = branch.id "
//				+ "inner join corporates corporate on branch.corporate_id = corporate.id "
						+ "group by parent.item_branch_id,parent.name_kh,child.name_kh,item_type_id,is_stock,stock_in,stock_out",
						mapSqlParameterSource,
						(rs, rowNum) -> new SaleDetailTransaction(rs.getLong("item_id"), rs.getString("item_name"),
								rs.getDouble("quantity"), rs.getDouble("sub_total"), rs.getDouble("discount_total"),rs.getInt("item_type_id"),rs.getBoolean("is_stock"),rs.getDouble("stocks")));
		summary.setDetails(details);
		return summary;

	}
	public List<SaleDetail> showSaleDetail(Long saleId) {
		List<SaleDetail> saledetail = null;
		boolean exits = transactionSaleRepository.existsBySaleId(saleId) ;
		if(exits) {
			 saledetail = saleDetailRepository.findBySaleId(saleId);
		}else {
			throw new ResourceNotFoundException("This sale doesn't exit in transaction");
		}
		return saledetail;
	}

}
