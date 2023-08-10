package com.spring.miniposbackend.service.dashboard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.modelview.dashboard.BranchSummaryChart;
import com.spring.miniposbackend.modelview.dashboard.BranchSummaryDetail;
import com.spring.miniposbackend.modelview.dashboard.ChannelReceipt;
import com.spring.miniposbackend.modelview.dashboard.ItemList;
import com.spring.miniposbackend.modelview.dashboard.ItemSummaryChart;
import com.spring.miniposbackend.modelview.dashboard.ItemSummaryDetail;
import com.spring.miniposbackend.modelview.dashboard.ItemTypeSummaryChart;
import com.spring.miniposbackend.modelview.dashboard.ItemTypeSummaryDetail;
import com.spring.miniposbackend.modelview.dashboard.PromotionReceipt;
import com.spring.miniposbackend.modelview.dashboard.SummaryDetail;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.sale.SaleDetailRepository;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

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
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ImageUtil imageUtil;
	@Value("${file.path.image.branch}")
	private String imagePath;

	public List<PromotionReceipt> promotionReceipt(Integer branchId,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("end_date", startDate);
		mapSqlParameterSource.addValue("user_id", userProfile.getProfile().getUser().getId());
		return jdbc.query(
				"select p.name_kh promotion,sum(sp.discount) discount_amt,sum(s.quantity) qty from sale_detail_promotion sp inner join sale_details s on sp.sale_detail_id=s.id "
						+ "inner join item_branches ib on ib.id=s.item_branch_id inner join items i on i.id=ib.item_id "
						+ "inner join branch_promotions bp on bp.id=sp.branch_promotion_id "
						+ "inner join promotions p on p.id=bp.promotion_id where s.reverse=false and date_trunc('day',s.value_date)=:end_date and s.user_id=:user_id and i.type='MAINITEM'"
						+ "group by p.name_kh,p.id order by p.id",
				mapSqlParameterSource, (rs, rowNum) -> new PromotionReceipt(rs.getString("promotion"),
						rs.getDouble("discount_amt"), rs.getDouble("qty")));
	}
	public List<PromotionReceipt> promotionReceipts(
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("end_date", end);
		mapSqlParameterSource.addValue("user_id", userProfile.getProfile().getUser().getId());
		return jdbc.query(
				"select p.name_kh promotion,sum(sp.discount) discount_amt,sum(s.quantity) qty from sale_detail_promotion sp inner join sale_details s on sp.sale_detail_id=s.id "
						+ "inner join item_branches ib on ib.id=s.item_branch_id inner join items i on i.id=ib.item_id "
						+ "inner join branch_promotions bp on bp.id=sp.branch_promotion_id "
						+ "inner join promotions p on p.id=bp.promotion_id where s.reverse=false and s.value_date between :startDate and :end_date and s.user_id=:user_id and i.type='MAINITEM'"
						+ "group by p.name_kh,p.id order by p.id",
				mapSqlParameterSource, (rs, rowNum) -> new PromotionReceipt(rs.getString("promotion"),
						rs.getDouble("discount_amt"), rs.getDouble("qty")));
	}

	public List<ChannelReceipt> channelReceipt(Integer branchId,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("end_date", startDate);
		mapSqlParameterSource.addValue("user_id", userProfile.getProfile().getUser().getId());
		return jdbc.query("select * from channelbyuser(:user_id,:end_date)", mapSqlParameterSource,
				(rs, rowNum) -> new ChannelReceipt(rs.getString("name_kh"), rs.getInt("receipt"), rs.getDouble("total"),
						rs.getDouble("discount")));
	}

	public List<ChannelReceipt> channelReceipts(Integer branchId,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("end_date", end);
		mapSqlParameterSource.addValue("user_id", userProfile.getProfile().getUser().getId());
		return jdbc.query("select * from channelbyusers(:user_id,:startDate,:end_date)", mapSqlParameterSource,
				(rs, rowNum) -> new ChannelReceipt(rs.getString("name_kh"), rs.getInt("receipt"), rs.getDouble("total"),
						rs.getDouble("discount")));
	}

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
						rs.getString("branchKh"), rs.getDouble("saleAmt"), rs.getDouble("disAmt"),
						rs.getInt("saleItem")));
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
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),
						rs.getDouble("monthly_discount_amount"), rs.getDouble("weekly_discount_amount"),
						rs.getDouble("daily_discount_amount")));

	}

	public List<SummaryDetail> SummaryByBranchId(Integer branchId, Date startDate, Date enddate, String reportName) {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("enddate", enddate);
		mapSqlParameterSource.addValue("branchId", branchId);

		if (reportName.equalsIgnoreCase("channel")) {

			return jdbc.query("select * from channelbybranches(:branchId,:startDate,:enddate)", mapSqlParameterSource,
					(rs, rowNum) -> new SummaryDetail(rs.getInt("id"), rs.getString("name_kh"), rs.getInt("receipt"),
							rs.getDouble("total"), rs.getDouble("discount")));
		} else if (reportName.equalsIgnoreCase("table")) {

			return jdbc.query("select * from seatbybranches(:branchId,:startDate,:enddate)", mapSqlParameterSource,
					(rs, rowNum) -> new SummaryDetail(rs.getInt("id"), rs.getString("name_kh"), rs.getInt("receipt"),
							rs.getDouble("total"), rs.getDouble("discount")));
		} else if (reportName.equalsIgnoreCase("promotion")) {

			return jdbc.query("select * from promotionbybranches(:branchId,:startDate,:enddate)", mapSqlParameterSource,
					(rs, rowNum) -> new SummaryDetail(rs.getInt("id"), rs.getString("name_kh"), rs.getInt("receipt"),
							rs.getDouble("total"), rs.getDouble("discount")));
		} else {
			return jdbc.query("select * from userbybranches(:branchId,:startDate,:enddate)", mapSqlParameterSource,
					(rs, rowNum) -> new SummaryDetail(rs.getInt("id"), rs.getString("name_kh"), rs.getInt("receipt"),
							rs.getDouble("total"), rs.getDouble("discount")));
		}

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
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),
						rs.getDouble("monthly_discount_amount"), rs.getDouble("weekly_discount_amount"),
						rs.getDouble("daily_discount_amount")));
	}

	public List<ItemTypeSummaryChart> itemTypeChartByBranchId(Integer branchId, Date startDate, Date endDate) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("branchId", branchId);
		return jdbc.query("select * from itemTypeChartByBranchId(:branchId,:startDate,:endDate)", mapSqlParameterSource,
				(rs, rowNum) -> new ItemTypeSummaryChart(rs.getInt("itemTypeId"), rs.getString("itemTypeName"),
						rs.getString("itemTypeKh"), rs.getDouble("saleAmt"), rs.getDouble("disAmt"),
						rs.getInt("saleItem"),rs.getDouble("servicecharge")));
	}

//	public List<ItemTypeSummaryChart> itemTypeChartByCopId(Integer corporateId, Date startDate, Date endDate) {
//		if (userProfile.getProfile().getCorporate().getId() != corporateId) {
//			throw new UnauthorizedException("Corporate is unauthorized");
//		}
//		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
//		mapSqlParameterSource.addValue("startDate", startDate);
//		mapSqlParameterSource.addValue("endDate", endDate);
//		mapSqlParameterSource.addValue("corporateId", corporateId);
//		return jdbc.query("select * from itemTypeChartByCopId(:corporateId,:startDate,:endDate)", mapSqlParameterSource,
//				(rs, rowNum) -> new ItemTypeSummaryChart(rs.getInt("itemTypeId"), rs.getString("itemTypeName"),
//						rs.getString("itemTypeKh"), rs.getDouble("saleAmt"), rs.getDouble("disAmt"),
//						rs.getInt("saleItem")));
//	}

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
						rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),
						rs.getDouble("monthly_discount_amount"), rs.getDouble("weekly_discount_amount"),
						rs.getDouble("daily_discount_amount")));
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
							rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),
							rs.getDouble("monthly_discount_amount"), rs.getDouble("weekly_discount_amount"),
							rs.getDouble("daily_discount_amount")));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

	}

	public List<ItemSummaryDetail> channelummaryByBranchId(Integer branchId, Date startDate, Date startWeek,
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
							rs.getDouble("weekly_sale_amount"), rs.getDouble("daily_sale_amount"),
							rs.getDouble("monthly_discount_amount"), rs.getDouble("weekly_discount_amount"),
							rs.getDouble("daily_discount_amount")));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

	}

	public List<ItemList> itemListByBranchId(Integer itemTypeId, Integer branchId, Date startDate, Date endDate) {

		return branchRepository.findById(branchId).map((branch) -> {
			if (userProfile.getProfile().getCorporate().getId() != branch.getCorporate().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
			mapSqlParameterSource.addValue("startDate", startDate);
			mapSqlParameterSource.addValue("endDate", endDate);
			mapSqlParameterSource.addValue("branchId", branchId);
			mapSqlParameterSource.addValue("itemTypeId", itemTypeId);

			return jdbc.query("select * from itemlistbybranchid(:itemTypeId,:branchId,:startDate,:endDate)",
					mapSqlParameterSource,
					(rs, rowNum) -> new ItemList(rs.getLong("itemid"), rs.getString("itemname"), rs.getString("itemkh"),
							rs.getDouble("saleamt"), rs.getDouble("disamt"), rs.getInt("saleitem"),
							rs.getDouble("total")));
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
						rs.getString("itemKh"), rs.getDouble("saleAmt"), rs.getDouble("disAmt"),
						rs.getInt("saleItem")));
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
						rs.getString("itemKh"), rs.getDouble("saleAmt"), rs.getDouble("disAmt"),
						rs.getInt("saleItem")));
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

	public void downloadTransactionReport(String exportType, HttpServletResponse response, String fileName, Date from,
			Date to, Integer branchId) throws JRException, IOException, SQLException {

		exportReport(exportType, response, fileName, from, to, branchId);
	}

	private void exportReport(String exportType, HttpServletResponse response, String fileName, Date from, Date to,
			Integer branchId) throws JRException, IOException, SQLException {
		InputStream transactionReportStream = getClass().getResourceAsStream("/" + fileName + ".jrxml");
		String titleTransactionBy = "Transactions Report";

		JasperReport jasperReport = JasperCompileManager.compileReport(transactionReportStream);
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		// JRBeanCollectionDataSource beanColDataSource =
		// new JRBeanCollectionDataSource(beanCollection);
		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("corporateName", userProfile.getProfile().getCorporate().getNameKh());
			parameters.put("branchId", branchId);
			parameters.put("start", from);
			parameters.put("end", to);
			parameters.put("branchName", userProfile.getProfile().getBranch().getNameKh());
			String fileLocation = imagePath + "/" + userProfile.getProfile().getBranch().getLogo();
			byte[] image;
			try {
				image = imageUtil.getImage(fileLocation);
			} catch (IOException e) {
				image = null;
			}
			if (image != null) {
				ByteArrayInputStream bis = new ByteArrayInputStream(image);
				parameters.put("logo", bis);
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
			if (exportType.equalsIgnoreCase("pdf")) {

				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
				response.setContentType("application/pdf");
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".pdf;");
				exporter.exportReport();

			} else {

				JRXlsxExporter exporter = new JRXlsxExporter();
				SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
				reportConfigXLS.setSheetNames(new String[] { titleTransactionBy });
				reportConfigXLS.setDetectCellType(true);
				reportConfigXLS.setCollapseRowSpan(false);
				exporter.setConfiguration(reportConfigXLS);
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".xlsx;");
				response.setContentType("application/octet-stream");
				exporter.exportReport();

			}
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
