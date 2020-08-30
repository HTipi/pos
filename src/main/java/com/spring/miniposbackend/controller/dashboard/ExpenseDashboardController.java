//package com.spring.miniposbackend.controller.dashboard;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("dashboard-expense")
//public class ExpenseDashboardController {
//
//	private Date today;
//	private Date startWeek;
//	private Date startMonth;
//
//	private void getDate() {
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.clear(Calendar.MINUTE);
//		cal.clear(Calendar.SECOND);
//		cal.clear(Calendar.MILLISECOND);
//		cal.setFirstDayOfWeek(Calendar.MONDAY);
//		cal.set(Calendar.DAY_OF_MONTH, 15);
//		today = cal.getTime();
//		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//		startWeek = cal.getTime();
//		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//		startMonth = cal.getTime();
//	}
//	
//	
//}
