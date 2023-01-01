package com.bcits.mdas.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.bcits.mdas.service.QueryBuilderService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class QueryBuilderServiceIMpl extends GenericServiceImpl<Object> implements QueryBuilderService {

	@Override
	public List<?> getSchemaList() {
		List<?> schemaList = new ArrayList<>();
		try {
			String qyr = "select nspname from pg_catalog.pg_namespace where nspname not in ('pg_toast','pg_temp_1','pg_toast_temp_1','pg_catalog','information_schema','public','office_location')";
			schemaList = postgresMdas.createNativeQuery(qyr).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schemaList;
	}

	@Override
	public List<?> getTableList(HttpServletRequest request) {

		String schemaName = request.getParameter("schemaName");
		List<?> TableList = new ArrayList<>();
		try {
			String qyr = "select  A.table_name from \n"
					+ "(SELECT table_name FROM information_schema.tables WHERE table_schema='" + schemaName + "') A\n"
					+ "order by A.table_name";
			System.out.println(qyr);
			TableList = postgresMdas.createNativeQuery(qyr).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TableList;
	}

	@Override
	public List<?> getColumnList(String tableName, String schemaName) {

		List<?> clumnlist = new ArrayList<>();
		try {
			String qyr = "SELECT  column_name  " + " FROM information_schema.columns\n" + " WHERE table_schema = '"
					+ schemaName + "'\n" + "  AND table_name   = '" + tableName + "'";
			System.out.println(qyr);
			clumnlist = postgresMdas.createNativeQuery(qyr).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clumnlist;

	}

	@Override
	public List<?> getDataList(HttpServletRequest req) {
		List<List<?>> dataList = new ArrayList<>();
		try {

			List<Object> clumnlist = new ArrayList<>();
			List<?> attributeList = new ArrayList<>();

			String qry = req.getParameter("sqlqry");
			boolean isFound = qry.contains("*");
			String tableNames = null;
			String colunmNames = null;
			if (isFound == true) {
				String[] tableName = qry.split("\\.");
				if (tableName[1].contains("where")) {
					String[] whereClause = tableName[1].split("where");
					String selectedTable = whereClause[0];
					tableNames = selectedTable;
				} else if (tableName[1].contains("Group BY")) {
					String[] whereClause = tableName[1].split("Group BY");
					String selectedTable = whereClause[0];
					tableNames = selectedTable;

				} else if (tableName[1].contains("Order BY ")) {
					String[] whereClause = tableName[1].split("Order BY");
					String selectedTable = whereClause[0];
					tableNames = selectedTable;
				} else {
					tableNames = tableName[1];
					if (tableNames.contains("limit")) {
						String[] s = tableNames.split(" ");
						tableNames = s[0];
					}
				}

			} else {
				String[] tablewithSelect = qry.split("from");
				String[] selectedColunm = tablewithSelect[0].split("select");
				String colunmName = selectedColunm[1];
				colunmNames = colunmName;
				String[] strarray = colunmName.split(",");
				for (int i = 0; i < strarray.length; i++) {
					clumnlist.add(strarray[i] + "");
				}
			}
			System.out.println("tableNames  " + tableNames);
			System.out.println("colunmName  " + colunmNames);
			if (tableNames != null) {
				String[] schemaName = qry.split("from");
				String[] schema1 = schemaName[1].split("\\.");
				/*
				 * String qyr2="SELECT  column_name  " + " FROM information_schema.columns\n" +
				 * " WHERE table_schema = '"+(schema1[0].trim())+"'\n" +
				 * "  AND table_name   = '"+tableNames.trim()+"'";
				 */
				String qyr2 = "SELECT a.attname   \r\n" + "FROM pg_attribute a\r\n"
						+ "  JOIN pg_class t on a.attrelid = t.oid\r\n"
						+ "  JOIN pg_namespace s on t.relnamespace = s.oid\r\n" + "WHERE a.attnum > 0 \r\n"
						+ "  AND NOT a.attisdropped\r\n" + "  AND t.relname = '" + tableNames.trim() + "'\r\n"
						+ "  AND s.nspname = '" + (schema1[0].trim()) + "' \r\n" + "ORDER BY a.attnum;";
				clumnlist = postgresMdas.createNativeQuery(qyr2).getResultList();

				System.out.println("SCHEMA=====" + schema1[0]);
				System.out.println("SQLQUERY=====" + qry);
			}
			attributeList = postgresMdas.createNativeQuery(qry).getResultList();
			dataList.add(clumnlist);
			dataList.add(attributeList);

		} catch (Exception e) {
			List<Object> l1 = new ArrayList<>();
			l1.add("ERROR");
			dataList.add(l1);
			e.printStackTrace();
			return dataList;
		}
		return dataList;
	}

	@Override
	public List<?> getCustomList(HttpServletRequest req) {
		List<List<?>> dataList = new ArrayList<>();
		try {

			List<Object> clumnlist = new ArrayList<>();
			List<?> attributeList = new ArrayList<>();

			String qry = req.getParameter("sqlqry");
			boolean isFound = qry.contains("*");
			String tableNames = null;
			String colunmNames = null;
			if (isFound == true) {
				String[] tableName = qry.split("\\.");
				if (tableName[1].contains("where")) {
					String[] whereClause = tableName[1].split("where");
					String selectedTable = whereClause[0];
					tableNames = selectedTable;
				} else if (tableName[1].contains("Group BY")) {
					String[] whereClause = tableName[1].split("Group BY");
					String selectedTable = whereClause[0];
					tableNames = selectedTable;

				} else if (tableName[1].contains("Order BY ")) {
					String[] whereClause = tableName[1].split("Order BY");
					String selectedTable = whereClause[0];
					tableNames = selectedTable;
				} else {
					tableNames = tableName[1];
					if (tableNames.contains("limit")) {
						String[] s = tableNames.split(" ");
						tableNames = s[0];
					}
				}

			} else {
				String[] tablewithSelect = qry.split("from");
				String[] selectedColunm = tablewithSelect[0].split("select");
				String colunmName = selectedColunm[1];
				colunmNames = colunmName;
				String[] strarray = colunmName.split(",");
				for (int i = 0; i < strarray.length; i++) {
					clumnlist.add(strarray[i] + "");
				}
			}
			System.out.println("tableNames  " + tableNames);
			System.out.println("colunmName  " + colunmNames);
			if (tableNames != null) {
				String[] schemaName = qry.split("from");
				String[] schema1 = schemaName[1].split("\\.");
				/*
				 * String qyr2="SELECT  column_name  " + " FROM information_schema.columns\n" +
				 * " WHERE table_schema = '"+(schema1[0].trim())+"'\n" +
				 * "  AND table_name   = '"+tableNames.trim()+"'";
				 */
				String qyr2 = "SELECT a.attname   \r\n" + "FROM pg_attribute a\r\n"
						+ "  JOIN pg_class t on a.attrelid = t.oid\r\n"
						+ "  JOIN pg_namespace s on t.relnamespace = s.oid\r\n" + "WHERE a.attnum > 0 \r\n"
						+ "  AND NOT a.attisdropped\r\n" + "  AND t.relname = '" + tableNames.trim() + "'\r\n"
						+ "  AND s.nspname = '" + (schema1[0].trim()) + "' \r\n" + "ORDER BY a.attnum;";
				clumnlist = postgresMdas.createNativeQuery(qyr2).getResultList();

				System.out.println("SCHEMA=====" + schema1[0]);
				System.out.println("SQLQUERY=====" + qry);
			}
			attributeList = postgresMdas.createNativeQuery(qry).getResultList();
			dataList.add(clumnlist);
			dataList.add(attributeList);

		} catch (Exception e) {
			List<Object> l1 = new ArrayList<>();
			l1.add("ERROR");
			dataList.add(l1);
			e.printStackTrace();
			return dataList;
		}
		return dataList;
	}

	@Override
	public List<?> getDtdashboardReports(String region, String circle, String reportId, String reportIdPeriod) {

//		System.out.println(reportId);
//		System.out.println(reportIdPeriod);
//		System.out.println(region);
//		System.out.println(circle);

		List<List<?>> dtdashboardList = new ArrayList<>();
		String period = "";
		String period1 = "";
		String formula = "";
		String sql = "";
		String condition = "";

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Overload DT")) {

			period = "where ((max_curr/DT_CURR_RATING)*100)>70 AND date(a.yearmonth)=CURRENT_DATE-1)AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Overload DT")) {

			period = " where ((max_curr/DT_CURR_RATING)*100)>70 and date(yearmonth) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1))   AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Overload DT")) {

			period = "where  ((max_curr/DT_CURR_RATING)*100)>70 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date),'yyyymm'))    AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Overload DT")) {

			period = " where  ((max_curr/DT_CURR_RATING)*100)>70 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm'))  AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Overload DT Instances")) {

			period = "where ((max_curr/DT_CURR_RATING)*100)>70 AND date(a.yearmonth)=CURRENT_DATE-1";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Overload DT Instances")) {

			period = " where ((max_curr/DT_CURR_RATING)*100)>70 and date(yearmonth) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1))  ";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Overload DT Instances")) {

			period = "where  ((max_curr/DT_CURR_RATING)*100)>70 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Overload DT Instances")) {

			period = " where  ((max_curr/DT_CURR_RATING)*100)>70 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Underload DT")) {

			period = "where ((max_curr/DT_CURR_RATING)*100)<20 AND date(a.yearmonth)=CURRENT_DATE-1)AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Underload DT")) {

			period = " where ((max_curr/DT_CURR_RATING)*100)<20 and date(yearmonth) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1))    AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Underload DT")) {

			period = "where  ((max_curr/DT_CURR_RATING)*100)<20 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date),'yyyymm'))    AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Underload DT")) {

			period = " where  ((max_curr/DT_CURR_RATING)*100)<20 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm'))  AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Underload DT Instances")) {

			period = "where ( (max_curr/DT_CURR_RATING*100)<20 and date(a.yearmonth)=CURRENT_DATE-1)";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Underload DT Instances")) {

			period = " where ((max_curr/DT_CURR_RATING)*100)<20 and date(yearmonth) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1)) ";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Underload DT Instances")) {

			period = "where  ((max_curr/DT_CURR_RATING)*100)<20 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Underload DT Instances")) {

			period = " where  ((max_curr/DT_CURR_RATING)*100)<20 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Unbalance DT")) {

			period = "and  (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50) and date(yearmonth)=CURRENT_DATE-1)AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Unbalance DT")) {

			period = "and (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50) and (yearmonth) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1)) AA ";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Unbalance DT")) {

			period = " and  (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50) and to_char(yearmonth,'YYYYMM')= to_char(date_trunc('month', current_date),'yyyymm'))AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Unbalance DT")) {

			period = "and (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50) and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm'))AA";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Unbalance DT Instances")) {

			period = "	where (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50)  and date(a.yearmonth)=CURRENT_DATE-1";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Unbalance DT Instances")) {

			period = "	where (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50)  and date(yearmonth) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1)) ";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Unbalance DT Instances")) {

			period = " 	where (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50)  and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Unbalance DT Instances")) {

			period = "	where (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50)  and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("PowerFailure DT")) {

			period = "where event_code='101' and (date(event_time) = (CURRENT_DATE - 1))";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("PowerFailure DT")) {

			period = "where  event_code='101' and date (event_time) between (CURRENT_DATE - 7) AND (CURRENT_DATE - 1)";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("PowerFailure DT")) {

			period = "where event_code='101'  AND (to_char(event_time, 'YYYYMM') = to_char(date_trunc('month', (CURRENT_DATE)), 'yyyymm'))";

		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("PowerFailure DT")) {

			period = "where event_code='101' AND (event_time > (CURRENT_DATE - interval '1' month)) ";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("PowerFailure DT Instances")) {

			period = "where event_code='101' AND (date(e.event_time) = (CURRENT_DATE - 1))  and dttpid is not null and dttpid != '')a";
			period1 = "where event_code='101' AND (date(e.event_time) = (CURRENT_DATE - 1))";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("PowerFailure DT Instances")) {

			period = "where event_code='101'  and  date(e.event_time) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1) and dttpid is not null and dttpid != '' )a ";
			period1 = "where event_code='101'  and  date(e.event_time) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month")
				&& reportId.equalsIgnoreCase("PowerFailure DT Instances")) {

			period = "where event_code='101'  AND (to_char(e.event_time, 'YYYYMM') = to_char(date_trunc('month', (CURRENT_DATE)), 'yyyymm')) and dttpid is not null and dttpid != '')a";
			period1 = "WHERE e.event_code='101' and  (((e.event_code) = '101') AND (to_char(e.event_time, 'YYYYMM') = to_char(date_trunc('month', (CURRENT_DATE)), 'yyyymm')))";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month")
				&& reportId.equalsIgnoreCase("PowerFailure DT Instances")) {

			period = "where event_code='101' and (to_char(event_time,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm')) and dttpid is not null and dttpid != '' AND (e.event_time > (CURRENT_DATE - interval '1' month)))a";
			period1 = "where event_code='101' and (to_char(event_time,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm'))AND (e.event_time > (CURRENT_DATE - interval '1' month))";
		}
		if (reportIdPeriod.equalsIgnoreCase("Current Day") && reportId.equalsIgnoreCase("PowerFailure DT Instances")) {

			period = "where event_code='101' AND (date(e.event_time) = (CURRENT_DATE - 1))  and dttpid is not null and dttpid != '')a";
			period1 = "where event_code='101' AND (date(e.event_time) = (CURRENT_DATE - 1))";
		}

		// POWER FACTOR //----------------------------------------------------------

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Good Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = "WHEN (a.prev_day_wise_avgpf > 0.9)";

			condition = "prev_day_wise_avgpf > 0.9";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Nominal Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = "WHEN ((a.prev_day_wise_avgpf >= 0.5) AND (a.prev_day_wise_avgpf <= 0.90))";

			condition = "((a.prev_day_wise_avgpf >= 0.5) AND (a.prev_day_wise_avgpf <= 0.90))";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Poor Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = "WHEN (a.prev_day_wise_avgpf < 0.5)";

			condition = "a.prev_day_wise_avgpf < 0.5";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Good Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('week', ((CURRENT_DATE - 7))) AND (load_survey_dt.yearmonth < date_trunc('week', ((CURRENT_DATE - 1))))";

			formula = "WHEN (a.prev_day_wise_avgpf > 0.9)";

			condition = "a.prev_day_wise_avgpf > 0.9";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Nominal Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = "WHEN ((a.prev_day_wise_avgpf >= 0.5) AND (a.prev_day_wise_avgpf <= 0.90))";

			condition = "((a.prev_day_wise_avgpf >= 0.5) AND (a.prev_day_wise_avgpf <= 0.90))";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Poor Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = "WHEN (a.prev_day_wise_avgpf < 0.5)";

			condition = "a.prev_day_wise_avgpf < 0.5";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Good Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE))) AND (load_survey_dt.yearmonth <= CURRENT_DATE)) ";

			formula = "WHEN (a.prev_day_wise_avgpf > 0.9)";

			condition = "a.prev_day_wise_avgpf > 0.9";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Nominal Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE))) AND (load_survey_dt.yearmonth <= CURRENT_DATE)) ";

			formula = "WHEN ((a.prev_day_wise_avgpf >= 0.5) AND (a.prev_day_wise_avgpf <= 0.90))";

			condition = "((a.prev_day_wise_avgpf >= 0.5) AND (a.prev_day_wise_avgpf <= 0.90))";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Poor Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE))) AND (load_survey_dt.yearmonth <= CURRENT_DATE)) ";

			formula = "WHEN (a.prev_day_wise_avgpf < 0.5)";

			condition = "a.prev_day_wise_avgpf < 0.5";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Good Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.yearmonth < date_trunc('month', (CURRENT_DATE)))) ";

			formula = "WHEN (a.prev_day_wise_avgpf > 0.9)";

			condition = "a.prev_day_wise_avgpf > 0.9";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Nominal Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.yearmonth < date_trunc('month', (CURRENT_DATE)))) ";

			formula = "WHEN ((a.prev_day_wise_avgpf >= 0.5) AND (a.prev_day_wise_avgpf <= 0.90))";

			condition = "((a.prev_day_wise_avgpf >= 0.5) AND (a.prev_day_wise_avgpf <= 0.90))";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Poor Power Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.yearmonth < date_trunc('month', (CURRENT_DATE))))";

			formula = "WHEN (a.prev_day_wise_avgpf < 0.5)";

			condition = "a.prev_day_wise_avgpf < 0.5";
		}

		// Utilization
		// Factor----------------------------------------------------------------

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Good Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = "WHEN ((a.prev_day_wise_maxkva / nullif (a.dtcapacity, 0)) > 0.50)";

			condition = "aa.test > 0.50";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Nominal Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = "WHEN (((a.prev_day_wise_maxkva / a.dtcapacity) >= 0.20) AND ((a.prev_day_wise_maxkva / a.dtcapacity) <= 0.50))";

			condition = "aa.test >=0.20 and aa.test <= 0.50";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Poor Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = "WHEN ((a.prev_day_wise_maxkva / a.dtcapacity) < 0.20)";

			condition = "aa.test < 0.20";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Good Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('week', ((CURRENT_DATE - 7)))) AND (load_survey_dt.yearmonth < date_trunc('week', (CURRENT_DATE - 1))))";

			formula = "WHEN ((a.prev_day_wise_maxkva / nullif (a.dtcapacity, 0)) > 0.50)";

			condition = "aa.test > 0.50";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Nominal Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('week', ((CURRENT_DATE - 7)))) AND (load_survey_dt.yearmonth < date_trunc('week', (CURRENT_DATE - 1))))";

			formula = "WHEN (((a.prev_day_wise_maxkva / a.dtcapacity) >= 0.20) AND ((a.prev_day_wise_maxkva / a.dtcapacity) <= 0.50))";

			condition = "aa.test >=0.20 and aa.test <= 0.50";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Poor Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('week', ((CURRENT_DATE - 7)))) AND (load_survey_dt.yearmonth < date_trunc('week', (CURRENT_DATE - 1))))";

			formula = "WHEN ((a.prev_day_wise_maxkva / a.dtcapacity) < 0.20)";

			condition = "aa.test < 0.20";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Good Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE))) AND (load_survey_dt.yearmonth <= CURRENT_DATE))  ";

			formula = "WHEN ((a.prev_day_wise_maxkva / nullif (a.dtcapacity, 0)) > 0.50)";

			condition = "aa.test > 0.50";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month")
				&& reportId.equalsIgnoreCase("Nominal Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE))) AND (load_survey_dt.yearmonth <= CURRENT_DATE))  ";

			formula = "WHEN (((a.prev_day_wise_maxkva / a.dtcapacity) >= 0.20) AND ((a.prev_day_wise_maxkva / a.dtcapacity) <= 0.50))";

			condition = "aa.test >=0.20 and aa.test <= 0.50";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Poor Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE))) AND (load_survey_dt.yearmonth <= CURRENT_DATE)) ";

			formula = "WHEN ((a.prev_day_wise_maxkva / a.dtcapacity) < 0.20)";

			condition = "aa.test < 0.20";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Good Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.yearmonth < date_trunc('month', (CURRENT_DATE)))) ";

			formula = "WHEN ((a.prev_day_wise_maxkva / nullif (a.dtcapacity, 0)) > 0.50)";

			condition = "aa.test > 0.50";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month")
				&& reportId.equalsIgnoreCase("Nominal Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.yearmonth < date_trunc('month', (CURRENT_DATE)))) ";

			formula = "WHEN (((a.prev_day_wise_maxkva / a.dtcapacity) >= 0.20) AND ((a.prev_day_wise_maxkva / a.dtcapacity) <= 0.50))";

			condition = "aa.test >=0.20 and aa.test <= 0.50";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Poor Utilization Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.yearmonth < date_trunc('month', (CURRENT_DATE))))";

			formula = "WHEN ((a.prev_day_wise_maxkva / a.dtcapacity) < 0.20)";

			condition = "aa.test < 0.20";
		}

		// Load Factor ------------------------------------------

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Good Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = "WHEN ((a.prev_mnth_wise_avgkva / nullif(a.prev_mnth_wise_maxkva,0)) > 0.50)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Nominal Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = " WHEN (((a.prev_mnth_wise_avgkva / nullif (a.prev_mnth_wise_maxkva, 0)) >= 0.20) AND ((a.prev_mnth_wise_avgkva / nullif (a.prev_mnth_wise_maxkva, 0)) <= 0.50))";

		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Poor Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= (CURRENT_DATE - 1)) AND (load_survey_dt.yearmonth < CURRENT_DATE))";

			formula = " WHEN ((a.prev_mnth_wise_avgkva / nullif(a.prev_mnth_wise_maxkva,0)) < 0.50)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Good Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('week', ((CURRENT_DATE - 7)))) AND (load_survey_dt.yearmonth < date_trunc('week', (CURRENT_DATE - 1))))";

			formula = "WHEN ((a.prev_mnth_wise_avgkva / nullif(a.prev_mnth_wise_maxkva,0)) > 0.50)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Nominal Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('week', ((CURRENT_DATE - 7)))) AND (load_survey_dt.yearmonth < date_trunc('week', (CURRENT_DATE - 1))))";

			formula = " WHEN (((a.prev_mnth_wise_avgkva / nullif (a.prev_mnth_wise_maxkva, 0)) >= 0.20) AND ((a.prev_mnth_wise_avgkva / nullif (a.prev_mnth_wise_maxkva, 0)) <= 0.50))";

		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Poor Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('week', ((CURRENT_DATE - 7)))) AND (load_survey_dt.yearmonth < date_trunc('week', (CURRENT_DATE - 1))))";

			formula = " WHEN ((a.prev_mnth_wise_avgkva / nullif(a.prev_mnth_wise_maxkva,0)) < 0.50)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Good Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE))) AND (load_survey_dt.yearmonth <= CURRENT_DATE))  ";

			formula = "WHEN ((a.prev_mnth_wise_avgkva / nullif(a.prev_mnth_wise_maxkva,0)) > 0.50)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Nominal Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE))) AND (load_survey_dt.yearmonth <= CURRENT_DATE))  ";

			formula = " WHEN (((a.prev_mnth_wise_avgkva / nullif (a.prev_mnth_wise_maxkva, 0)) >= 0.20) AND ((a.prev_mnth_wise_avgkva / nullif (a.prev_mnth_wise_maxkva, 0)) <= 0.50))";

		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Poor Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE))) AND (load_survey_dt.yearmonth <= CURRENT_DATE)) ";

			formula = " WHEN ((a.prev_mnth_wise_avgkva / nullif(a.prev_mnth_wise_maxkva,0)) < 0.50)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Good Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.yearmonth < date_trunc('month', (CURRENT_DATE)))) ";

			formula = "WHEN ((a.prev_mnth_wise_avgkva / nullif(a.prev_mnth_wise_maxkva,0)) > 0.50)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Nominal Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.yearmonth < date_trunc('month', (CURRENT_DATE)))) ";

			formula = " WHEN (((a.prev_mnth_wise_avgkva / nullif (a.prev_mnth_wise_maxkva, 0)) >= 0.20) AND ((a.prev_mnth_wise_avgkva / nullif (a.prev_mnth_wise_maxkva, 0)) <= 0.50))";

		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Poor Load Factor")) {

			period = "WHEN ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.yearmonth < date_trunc('month', (CURRENT_DATE))))";

			formula = " WHEN ((a.prev_mnth_wise_avgkva / nullif(a.prev_mnth_wise_maxkva,0)) < 0.50)";

		}

		try {
			if (reportId.equalsIgnoreCase("Overload DT") || reportId.equalsIgnoreCase("Underload DT")) {

				sql = "select distinct on (AA.dt_code) circle,town,feeder_code,dttype,dtname,AA.dt_code,count(AA.dt_code) as dtcount,section from\r\n"
						+ "(\r\n" + "select  a.dttpid as dt_code ,a.DT_CURR_RATING,a.max_curr\r\n" + "from\r\n"
						+ "(\r\n" + "select ls.dttpid as dttpid,yearmonth ,max(GREATEST(ir,iy,ib)) as max_curr,\r\n"
						+ "max(CASE WHEN DTCAPACITY=100 THEN 133\r\n" + "WHEN DTCAPACITY=250 THEN 333\r\n"
						+ "WHEN DTCAPACITY=500 THEN 666\r\n" + "WHEN DTCAPACITY=25 THEN 33\r\n"
						+ "WHEN DTCAPACITY=40 THEN 53\r\n" + "WHEN DTCAPACITY=63 THEN 83\r\n"
						+ "WHEN DTCAPACITY=315 THEN 420\r\n" + "WHEN DTCAPACITY=300 THEN 400\r\n"
						+ "WHEN DTCAPACITY=200 THEN 266\r\n" + "WHEN DTCAPACITY=175 THEN 233\r\n"
						+ "WHEN DTCAPACITY=160 THEN 213\r\n" + "WHEN DTCAPACITY=150 THEN 200\r\n"
						+ "WHEN DTCAPACITY=75 THEN 100\r\n" + "WHEN DTCAPACITY=50 THEN 66\r\n"
						+ "WHEN DTCAPACITY=16 THEN 21\r\n" + "WHEN DTCAPACITY is null THEN 133\r\n" + "\r\n"
						+ "END) DT_CURR_RATING\r\n" + "\r\n"
						+ "from meter_data.load_survey_dt ls,meter_data.dtdetails dd where ls.dttpid=dd.dttpid AND (ls.yearmonth > (CURRENT_DATE  - INTERVAL '2' MONTH)) \r\n"
						+ "group by ls.dttpid,ls.yearmonth\r\n" + ")a RIGHT JOIN\r\n" + "(\r\n"
						+ "select distinct location_id from meter_data.master_main where zone like '" + region
						+ "' and circle like '" + circle + "'\r\n" + ")z on (a.dttpid=z.location_id)\r\n" + "" + period
						+ " LEFT JOIN\r\n" + "\r\n" + "((\r\n"
						+ "select distinct m.circle,t.town_name as town,d.tpparentid as feeder_code, d.dttype,string_agg(distinct dtname,',') as dtname,d.dttpid as dt_code,string_agg(meterno, ','),x.section from meter_data.master_main m,meter_data.dtdetails d,meter_data.town_master t,meter_data.amilocation x\r\n"
						+ "where m.mtrno=d.meterno and t.towncode=m.town_code and d.dttpid is not null and d.dttpid !='' and d.meterno is not null and d.meterno !='' and d.dttpid=m.location_id and x.tp_towncode=m.town_code group by m.circle, town, feeder_code, d.dttype, dttpid,x.section\r\n"
						+ ")) BB on AA.dt_code=BB.dt_code group by bb.circle,bb.town,bb.feeder_code,bb.dttype,bb.dtname,aa.dt_code,bb.section";

				/*
				 * sql="select distinct * from meter_data.dtoverload_lastday  "
				 * +period+" and zone like '"+region+"' and circle like '"+circle+"' ";
				 */
			}

			if (reportId.equalsIgnoreCase("Overload DT Instances")) {

				sql = "select a.dttpid as dtcode,a.dtname,a.no_meters,a.dt_capacity,a.DT_CURR_RATING as dtcapacity,TO_CHAR(a.yearmonth,'YYYY-MM-DD HH12:MI'\r\n"
						+ "    ) yearmonth,\r\n" + "a.kwh,a.kvah,a.kva,a.ir,a.iy,a.ib,a.vr,a.vy,a.vb,a.pf \r\n" + "\r\n"
						+ "\r\n" + "from \r\n" + "(\r\n"
						+ "select  ls.dttpid as dttpid, yearmonth,max(GREATEST(ir,iy,ib)) as max_curr,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n"
						+ "		WHEN DTCAPACITY=250 THEN 250\r\n" + "		WHEN DTCAPACITY=500 THEN 500\r\n"
						+ "		WHEN DTCAPACITY=25 THEN 25\r\n" + "		WHEN DTCAPACITY=40 THEN 40\r\n"
						+ "		WHEN DTCAPACITY=63 THEN 63\r\n" + "		WHEN DTCAPACITY=315 THEN 315\r\n"
						+ "		WHEN DTCAPACITY=300 THEN 300\r\n" + "		WHEN DTCAPACITY=200 THEN 200\r\n"
						+ "		WHEN DTCAPACITY=175 THEN 175\r\n" + "		WHEN DTCAPACITY=160 THEN 160\r\n"
						+ "		WHEN DTCAPACITY=150 THEN 150\r\n" + "		WHEN DTCAPACITY=75 THEN 75\r\n"
						+ "		WHEN DTCAPACITY=50 THEN 50\r\n" + "		WHEN DTCAPACITY=16 THEN 16\r\n"
						+ "		WHEN DTCAPACITY is null THEN 100 \r\n" + "END) as dt_capacity,\r\n"
						+ "	max(CASE WHEN DTCAPACITY=100 THEN 133 \r\n" + "		WHEN DTCAPACITY=250 THEN 333\r\n"
						+ "		WHEN DTCAPACITY=500 THEN 666\r\n" + "		WHEN DTCAPACITY=25 THEN 33\r\n"
						+ "		WHEN DTCAPACITY=40 THEN 53\r\n" + "		WHEN DTCAPACITY=63 THEN 83\r\n"
						+ "		WHEN DTCAPACITY=315 THEN 420\r\n" + "		WHEN DTCAPACITY=300 THEN 400\r\n"
						+ "		WHEN DTCAPACITY=200 THEN 266\r\n" + "		WHEN DTCAPACITY=175 THEN 233\r\n"
						+ "		WHEN DTCAPACITY=160 THEN 213\r\n" + "		WHEN DTCAPACITY=150 THEN 200\r\n"
						+ "		WHEN DTCAPACITY=75 THEN 100\r\n" + "		WHEN DTCAPACITY=50 THEN 66\r\n"
						+ "		WHEN DTCAPACITY=16 THEN 21\r\n" + "		WHEN DTCAPACITY is null THEN 133 \r\n" + "\r\n"
						+ "END) DT_CURR_RATING,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,count(dd.meterno)no_meters,string_agg(distinct dd.dtname,',') as dtname\r\n"
						+ "	\r\n" + "	\r\n" + "	from meter_data.load_survey_dt ls,meter_data.dtdetails dd \r\n"
						+ "where  ls.dttpid=dd.dttpid AND (ls.yearmonth > (CURRENT_DATE - INTERVAL '2' MONTH))\r\n"
						+ "group by ls.dttpid,yearmonth,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,dtcapacity\r\n"
						+ ")a RIGHT JOIN\r\n" + "(\r\n"
						+ "select distinct location_id from meter_data.master_main where zone like '" + region
						+ "' and circle like '" + circle + "'\r\n" + ")z on (a.dttpid=z.location_id) " + period + "";
			}

			if (reportId.equalsIgnoreCase("Underload DT Instances")) {

				sql = "select a.dttpid as dtcode,a.dtname,a.no_meters,a.dt_capacity,a.DT_CURR_RATING as dtcapacity,TO_CHAR(a.yearmonth,'YYYY-MM-DD HH12:MI'\r\n"
						+ "    ) yearmonth,\r\n" + "a.kwh,a.kvah,a.kva,a.ir,a.iy,a.ib,a.vr,a.vy,a.vb,a.pf \r\n"
						+ "from \r\n" + "(\r\n"
						+ "select  ls.dttpid as dttpid, yearmonth,max(GREATEST(ir,iy,ib)) as max_curr,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n"
						+ "		WHEN DTCAPACITY=250 THEN 250\r\n" + "		WHEN DTCAPACITY=500 THEN 500\r\n"
						+ "		WHEN DTCAPACITY=25 THEN 25\r\n" + "		WHEN DTCAPACITY=40 THEN 40\r\n"
						+ "		WHEN DTCAPACITY=63 THEN 63\r\n" + "		WHEN DTCAPACITY=315 THEN 315\r\n"
						+ "		WHEN DTCAPACITY=300 THEN 300\r\n" + "		WHEN DTCAPACITY=200 THEN 200\r\n"
						+ "		WHEN DTCAPACITY=175 THEN 175\r\n" + "		WHEN DTCAPACITY=160 THEN 160\r\n"
						+ "		WHEN DTCAPACITY=150 THEN 150\r\n" + "		WHEN DTCAPACITY=75 THEN 75\r\n"
						+ "		WHEN DTCAPACITY=50 THEN 50\r\n" + "		WHEN DTCAPACITY=16 THEN 16\r\n"
						+ "		WHEN DTCAPACITY is null THEN 100 \r\n" + "END) as dt_capacity,\r\n"
						+ "	max(CASE WHEN DTCAPACITY=100 THEN 133 \r\n" + "		WHEN DTCAPACITY=250 THEN 333\r\n"
						+ "		WHEN DTCAPACITY=500 THEN 666\r\n" + "		WHEN DTCAPACITY=25 THEN 33\r\n"
						+ "		WHEN DTCAPACITY=40 THEN 53\r\n" + "		WHEN DTCAPACITY=63 THEN 83\r\n"
						+ "		WHEN DTCAPACITY=315 THEN 420\r\n" + "		WHEN DTCAPACITY=300 THEN 400\r\n"
						+ "		WHEN DTCAPACITY=200 THEN 266\r\n" + "		WHEN DTCAPACITY=175 THEN 233\r\n"
						+ "		WHEN DTCAPACITY=160 THEN 213\r\n" + "		WHEN DTCAPACITY=150 THEN 200\r\n"
						+ "		WHEN DTCAPACITY=75 THEN 100\r\n" + "		WHEN DTCAPACITY=50 THEN 66\r\n"
						+ "		WHEN DTCAPACITY=16 THEN 21\r\n" + "		WHEN DTCAPACITY is null THEN 133 \r\n" + "\r\n"
						+ "END) DT_CURR_RATING,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,count(dd.meterno)no_meters,string_agg(distinct dd.dtname,',') as dtname\r\n"
						+ "	\r\n" + "	from meter_data.load_survey_dt ls,meter_data.dtdetails dd \r\n"
						+ "where  ls.dttpid=dd.dttpid AND (ls.yearmonth > (CURRENT_DATE - INTERVAL '2' MONTH ))\r\n"
						+ "group by ls.dttpid,yearmonth,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf\r\n" + ")a RIGHT JOIN\r\n"
						+ "(\r\n" + "select distinct location_id from meter_data.master_main where zone like '" + region
						+ "' and circle like '" + circle + "' and circle is not null\r\n"
						+ ")z on (a.dttpid=z.location_id)  " + period + "";
			}

			if (reportId.equalsIgnoreCase("Unbalance DT")) {

				sql = "select  distinct on (AA.dttpid) circle,town,feeder_code,AA.dttype,AA.dtname,AA.dttpid,count(AA.dttpid) as dtcount,section from\r\n"
						+ "(\r\n" + "select  a.dttpid as dttpid,c.dtname,c.dttype\r\n" + "\r\n" + "from \r\n" + "(\r\n"
						+ "select dttpid,yearmonth,\r\n"
						+ "((ir)-((ir+iy+ib)/3)/NULLIF (((ir+iy+ib)/3),0)) as r_ph_avg,\r\n"
						+ "((iy)-((ir+iy+ib)/3)/NULLIF  (((ir+iy+ib)/3),0)) as y_ph_avg,\r\n"
						+ "((ib)-((ir+iy+ib)/3)/NULLIF  (((ir+iy+ib)/3),0)) as b_ph_avg\r\n" + " from  \r\n"
						+ "meter_data.load_survey_dt dt where dttpid in (\r\n"
						+ "select distinct location_id from meter_data.master_main where zone like '" + region
						+ "'  and circle like '" + circle + "' and fdrcategory='DT')\r\n"
						+ "and (yearmonth > (CURRENT_DATE - INTERVAL '2' MONTH))\r\n"
						+ " group by dttpid,yearmonth,ir,iy,ib \r\n" + " \r\n"
						+ " )a , meter_data.dtdetails c where a.dttpid=c.dttpid " + period + " \r\n" + " \r\n"
						+ " left join\r\n" + "(\r\n"
						+ "select distinct m.circle,t.town_name as town,d.tpparentid as feeder_code, d.dttype,string_agg(distinct dtname,',') as dtname,d.dttpid as dt_code,string_agg(meterno, ','),x.section from meter_data.master_main m,meter_data.dtdetails d,meter_data.town_master t,meter_data.amilocation x\r\n"
						+ "where m.mtrno=d.meterno and t.towncode=m.town_code and d.dttpid is not null and d.dttpid !='' and d.meterno is not null and d.meterno !='' and d.dttpid=m.location_id and x.tp_towncode=m.town_code group by m.circle, town, feeder_code, d.dttype, dttpid,x.section\r\n"
						+ ") BB on AA.dttpid=BB.dt_code group by bb.circle,bb.town,bb.feeder_code,AA.dttype,AA.dtname,aa.dttpid,bb.section";
			}

			if (reportId.equalsIgnoreCase("Unbalance DT Instances")) {

				sql = "select  a.dttpid as dtcode,a.dtname,a.no_meters,a.dt_capacity,a.DT_CURR_RATING as dtcapacity, yearmonth,\r\n"
						+ "a.kwh,a.kvah,a.kva,a.ir,a.iy,a.ib,a.vr,a.vy,a.vb,a.pf \r\n" + "\r\n" + "from \r\n" + "(\r\n"
						+ "select dt.dttpid,yearmonth,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,count(b.meterno)no_meters,string_agg(distinct b.dtname,',') as dtname,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n"
						+ "		WHEN DTCAPACITY=250 THEN 250\r\n" + "		WHEN DTCAPACITY=500 THEN 500\r\n"
						+ "		WHEN DTCAPACITY=25 THEN 25\r\n" + "		WHEN DTCAPACITY=40 THEN 40\r\n"
						+ "		WHEN DTCAPACITY=63 THEN 63\r\n" + "		WHEN DTCAPACITY=315 THEN 315\r\n"
						+ "		WHEN DTCAPACITY=300 THEN 300\r\n" + "		WHEN DTCAPACITY=200 THEN 200\r\n"
						+ "		WHEN DTCAPACITY=175 THEN 175\r\n" + "		WHEN DTCAPACITY=160 THEN 160\r\n"
						+ "		WHEN DTCAPACITY=150 THEN 150\r\n" + "		WHEN DTCAPACITY=75 THEN 75\r\n"
						+ "		WHEN DTCAPACITY=50 THEN 50\r\n" + "		WHEN DTCAPACITY=16 THEN 16\r\n"
						+ "		WHEN DTCAPACITY is null THEN 100 \r\n" + "END) as dt_capacity,\r\n"
						+ "((ir)-((ir+iy+ib)/3)/NULLIF (((ir+iy+ib)/3),0)) as r_ph_avg,\r\n"
						+ "((iy)-((ir+iy+ib)/3)/NULLIF  (((ir+iy+ib)/3),0)) as y_ph_avg,\r\n"
						+ "((ib)-((ir+iy+ib)/3)/NULLIF  (((ir+iy+ib)/3),0)) as b_ph_avg,\r\n"
						+ " string_agg(b.dtname,','),b.dttype,\r\n" + " max(CASE WHEN DTCAPACITY=100 THEN 133 \r\n"
						+ "		WHEN DTCAPACITY=250 THEN 333\r\n" + "		WHEN DTCAPACITY=500 THEN 666\r\n"
						+ "		WHEN DTCAPACITY=25 THEN 33\r\n" + "		WHEN DTCAPACITY=40 THEN 53\r\n"
						+ "		WHEN DTCAPACITY=63 THEN 83\r\n" + "		WHEN DTCAPACITY=315 THEN 420\r\n"
						+ "		WHEN DTCAPACITY=300 THEN 400\r\n" + "		WHEN DTCAPACITY=200 THEN 266\r\n"
						+ "		WHEN DTCAPACITY=175 THEN 233\r\n" + "		WHEN DTCAPACITY=160 THEN 213\r\n"
						+ "		WHEN DTCAPACITY=150 THEN 200\r\n" + "		WHEN DTCAPACITY=75 THEN 100\r\n"
						+ "		WHEN DTCAPACITY=50 THEN 66\r\n" + "		WHEN DTCAPACITY=16 THEN 21\r\n"
						+ "		WHEN DTCAPACITY is null THEN 133 \r\n" + "\r\n" + "END) DT_CURR_RATING\r\n"
						+ " from  \r\n"
						+ "meter_data.load_survey_dt dt, meter_data.dtdetails b where dt.dttpid=b.dttpid and dt.dttpid in (\r\n"
						+ "select distinct location_id from meter_data.master_main where zone like '" + region
						+ "'  and circle like '" + circle + "' and fdrcategory='DT')\r\n"
						+ "and (yearmonth > (CURRENT_DATE - interval '2' MONTH))\r\n"
						+ " group by dt.dttpid,yearmonth,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,b.dttype\r\n" + " \r\n"
						+ ") a\r\n" + "  \r\n" + " " + period + "	";
			}

			if (reportId.equalsIgnoreCase("PowerFailure DT")) {

				sql = "select distinct circle, town, section, feeder_code, dt_code, dtname, instant_count from \r\n"
						+ "(select dttpid, instant_count from \r\n"
						+ "(select dttpid, count(*) as instant_count from \r\n"
						+ "(select dttpid, meterno from meter_data.dtdetails where dttpid is not null and dttpid != '' and meterno IN (select meter_number from meter_data.events)) a1 \r\n"
						+ "INNER JOIN \r\n" + "(select * from meter_data.events " + period + ")a2\r\n"
						+ "ON a1.meterno = a2.meter_number \r\n" + "GROUP BY dttpid ) a3 \r\n" + "INNER JOIN \r\n"
						+ "(select distinct location_id from meter_data.master_main where zone like '" + region
						+ "'  and circle like '" + circle + "' and fdrcategory='DT') a4 \r\n"
						+ "ON a3.dttpid=a4.location_id) a5 \r\n" + "LEFT JOIN \r\n"
						+ "(select distinct m.circle,t.town_name as town,d.tpparentid as feeder_code, d.dttype,string_agg(distinct dtname,',') as dtname,d.dttpid as dt_code,string_agg(meterno, ','),x.section from meter_data.master_main m,meter_data.dtdetails d,meter_data.town_master t,meter_data.amilocation x\r\n"
						+ "where m.mtrno=d.meterno and t.towncode=m.town_code and d.dttpid is not null and d.dttpid !='' and d.meterno is not null and d.meterno !='' and d.dttpid=m.location_id and x.tp_towncode=m.town_code group by m.circle, town, feeder_code, d.dttype, dttpid,x.section) a6 \r\n"
						+ "ON a5.dttpid=a6.dt_code ";

			}

			if (reportId.equalsIgnoreCase("PowerFailure DT Instances")) {

//			sql = "select distinct on AA.dttpid as dtcode,i_r,i_y,i_b,v_r,v_y,v_b, event_time,kwh,kvah,AA.meter_number as no_meter,BB.dtcapacity,BB.dtname from\r\n" + 
//					"(\r\n" + 
//					"select a.dttpid as dttpid,a.event_time,a.i_r,a.i_y,a.i_b,a.v_r,a.v_y,a.v_b,a.kwh,a.kvah,a.meter_number from\r\n" + 
//					"(\r\n" + 
//					"select dttpid,event_time,i_r,i_y,i_b,v_r,v_y,v_b,kwh,kvah,meter_number\r\n" + 
//					"from meter_data.events e left join (meter_data.dtdetails d INNER JOIN (select distinct location_id from meter_data.master_main where zone like '"+region+"'  and circle like '"+circle+"' and fdrcategory='DT')z on d.dttpid=z.location_id )d ON (((e.meter_number) = (d.meterno))) "+period+" )AA  left JOIN\r\n" + 
//					"\r\n" + 
//					"(\r\n" + 
//					"\r\n" + 
//					"select  dttpid ,\r\n" + 
//					"max(CASE WHEN dtcapacity=100 THEN 133 \r\n" + 
//					"		WHEN dtcapacity=250 THEN 333\r\n" + 
//					"		WHEN dtcapacity=500 THEN 666\r\n" + 
//					"		WHEN dtcapacity=25 THEN 33\r\n" + 
//					"		WHEN dtcapacity=40 THEN 53\r\n" + 
//					"		WHEN dtcapacity=63 THEN 83\r\n" + 
//					"		WHEN dtcapacity=315 THEN 420\r\n" + 
//					"		WHEN dtcapacity=300 THEN 400\r\n" + 
//					"		WHEN dtcapacity=200 THEN 266\r\n" + 
//					"		WHEN dtcapacity=175 THEN 233\r\n" + 
//					"		WHEN dtcapacity=160 THEN 213\r\n" + 
//					"		WHEN dtcapacity=150 THEN 200\r\n" + 
//					"		WHEN dtcapacity=75 THEN 100\r\n" + 
//					"		WHEN dtcapacity=50 THEN 66\r\n" + 
//					"		WHEN dtcapacity=16 THEN 21\r\n" + 
//					"		WHEN dtcapacity is null THEN 133 END) as dtcapacity,count(c.meterno)no_meter,string_agg(c.dtname,',') as dtname\r\n" + 
//					"		from meter_data.dtdetails c  left join meter_data.events e on (c.meterno=e.meter_number) WHERE e.event_code='101' and  (((e.event_code) = '101') AND (date(e.event_time) = (CURRENT_DATE - 1))) group by dttpid,dtcapacity\r\n" + 
//					")BB on AA.dttpid=BB.dttpid";	
//			

				sql = "select distinct AA.dttpid as dtcode,AA.meter_number as no_meter,BB.dtcapacity,BB.dt_capacity as dt_capacity,BB.dtname,event_time from\r\n"
						+ "(\r\n" + "select a.dttpid as dttpid,a.event_time,a.meter_number from\r\n" + "\r\n"
						+ "(select dttpid,event_time,meter_number\r\n"
						+ "from meter_data.events e left join (meter_data.dtdetails d INNER JOIN (select distinct location_id from meter_data.master_main where zone like '"
						+ region + "'  and circle like '" + circle
						+ "' and fdrcategory='DT')z on d.dttpid=z.location_id )d ON (((e.meter_number) = (d.meterno))) "
						+ period + ")AA  left JOIN\r\n" + "\r\n" + "(\r\n" + "\r\n"
						+ "select  dttpid,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n"
						+ "						WHEN DTCAPACITY=250 THEN 250\r\n"
						+ "						WHEN DTCAPACITY=500 THEN 500\r\n"
						+ "						WHEN DTCAPACITY=25 THEN 25\r\n"
						+ "						WHEN DTCAPACITY=40 THEN 40\r\n"
						+ "						WHEN DTCAPACITY=63 THEN 63\r\n"
						+ "						WHEN DTCAPACITY=315 THEN 315\r\n"
						+ "						WHEN DTCAPACITY=300 THEN 300\r\n"
						+ "						WHEN DTCAPACITY=200 THEN 200\r\n"
						+ "						WHEN DTCAPACITY=175 THEN 175\r\n"
						+ "						WHEN DTCAPACITY=160 THEN 160\r\n"
						+ "						WHEN DTCAPACITY=150 THEN 150\r\n"
						+ "						WHEN DTCAPACITY=75 THEN 75\r\n"
						+ "						WHEN DTCAPACITY=50 THEN 50\r\n"
						+ "						WHEN DTCAPACITY=16 THEN 16\r\n"
						+ "						WHEN DTCAPACITY is null THEN 100 \r\n"
						+ "				END) as dt_capacity,\r\n" + "max(CASE WHEN dtcapacity=100 THEN 133 \r\n"
						+ "		WHEN dtcapacity=250 THEN 333\r\n" + "		WHEN dtcapacity=500 THEN 666\r\n"
						+ "		WHEN dtcapacity=25 THEN 33\r\n" + "		WHEN dtcapacity=40 THEN 53\r\n"
						+ "		WHEN dtcapacity=63 THEN 83\r\n" + "		WHEN dtcapacity=315 THEN 420\r\n"
						+ "		WHEN dtcapacity=300 THEN 400\r\n" + "		WHEN dtcapacity=200 THEN 266\r\n"
						+ "		WHEN dtcapacity=175 THEN 233\r\n" + "		WHEN dtcapacity=160 THEN 213\r\n"
						+ "		WHEN dtcapacity=150 THEN 200\r\n" + "		WHEN dtcapacity=75 THEN 100\r\n"
						+ "		WHEN dtcapacity=50 THEN 66\r\n" + "		WHEN dtcapacity=16 THEN 21\r\n"
						+ "		WHEN dtcapacity is null THEN 133 END) as dtcapacity,count(c.meterno)no_meter,string_agg(distinct c.dtname,',') as dtname\r\n"
						+ "		from meter_data.dtdetails c  left join meter_data.events e on (c.meterno=e.meter_number) "
						+ period1 + " group by dttpid,dtcapacity\r\n" + ")BB on AA.dttpid=BB.dttpid";
			}

			// if(reportId.equalsIgnoreCase("PowerFailure DT Instances")) {

//		sql ="select distinct AA.dttpid as dtcode,BB.dtname,AA.meter_number as no_meter,BB.dt_capacity as dt_capacity,BB.dtcapacity,event_time,kwh,kvah,i_r,i_y,i_b,v_r,v_y,v_b from \r\n" + 
//				"				( \r\n" + 
//				"				select a.dttpid as dttpid,a.event_time,a.i_r,a.i_y,a.i_b,a.v_r,a.v_y,a.v_b,a.kwh,a.kvah,a.meter_number from \r\n" + 
//				"				( \r\n" + 
//				"				select dttpid,event_time,i_r,i_y,i_b,v_r,v_y,v_b,kwh,kvah,meter_number \r\n" + 
//				"				from meter_data.events e left join (meter_data.dtdetails d INNER JOIN (select distinct location_id from meter_data.master_main where zone like '"+region+"'  and circle like '"+circle+"' and fdrcategory='DT')z on d.dttpid=z.location_id )d ON (((e.meter_number) = (d.meterno))) "+period+")AA  left JOIN \r\n" + 
//				"				 \r\n" + 
//				"				( select  dttpid ,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n" + 
//				"						WHEN DTCAPACITY=250 THEN 250\r\n" + 
//				"						WHEN DTCAPACITY=500 THEN 500\r\n" + 
//				"						WHEN DTCAPACITY=25 THEN 25\r\n" + 
//				"						WHEN DTCAPACITY=40 THEN 40\r\n" + 
//				"						WHEN DTCAPACITY=63 THEN 63\r\n" + 
//				"						WHEN DTCAPACITY=315 THEN 315\r\n" + 
//				"						WHEN DTCAPACITY=300 THEN 300\r\n" + 
//				"						WHEN DTCAPACITY=200 THEN 200\r\n" + 
//				"						WHEN DTCAPACITY=175 THEN 175\r\n" + 
//				"						WHEN DTCAPACITY=160 THEN 160\r\n" + 
//				"						WHEN DTCAPACITY=150 THEN 150\r\n" + 
//				"						WHEN DTCAPACITY=75 THEN 75\r\n" + 
//				"						WHEN DTCAPACITY=50 THEN 50\r\n" + 
//				"						WHEN DTCAPACITY=16 THEN 16\r\n" + 
//				"						WHEN DTCAPACITY is null THEN 100 \r\n" + 
//				"				END) as dt_capacity,\r\n" + 
//				"					MAX(CASE WHEN DTCAPACITY=100 THEN 133 \r\n" + 
//				"						WHEN DTCAPACITY=250 THEN 333\r\n" + 
//				"						WHEN DTCAPACITY=500 THEN 666\r\n" + 
//				"						WHEN DTCAPACITY=25 THEN 33\r\n" + 
//				"						WHEN DTCAPACITY=40 THEN 53\r\n" + 
//				"						WHEN DTCAPACITY=63 THEN 83\r\n" + 
//				"						WHEN DTCAPACITY=315 THEN 420\r\n" + 
//				"						WHEN DTCAPACITY=300 THEN 400\r\n" + 
//				"						WHEN DTCAPACITY=200 THEN 266\r\n" + 
//				"						WHEN DTCAPACITY=175 THEN 233\r\n" + 
//				"						WHEN DTCAPACITY=160 THEN 213\r\n" + 
//				"						WHEN DTCAPACITY=150 THEN 200\r\n" + 
//				"						WHEN DTCAPACITY=75 THEN 100\r\n" + 
//				"						WHEN DTCAPACITY=50 THEN 66\r\n" + 
//				"						WHEN DTCAPACITY=16 THEN 21\r\n" + 
//				"						WHEN DTCAPACITY is null THEN 133 \r\n" + 
//				"\r\n" + 
//				"				END)dtcapacity,count(c.meterno)no_meter,string_agg(DISTINCT c.dtname,',') as dtname \r\n" + 
//				"						from meter_data.dtdetails c  left join meter_data.events e on (c.meterno=e.meter_number) WHERE e.event_code='101' and  (((e.event_code) = '101') AND (date(e.event_time) = (CURRENT_DATE - 1))) group by dttpid,dtcapacity \r\n" + 
//				"				)BB on AA.dttpid=BB.dttpid\r\n" + 
//				"";			

			// }

			// POWER FACTOR //

			if (reportId.equalsIgnoreCase("Good Power Factor") || reportId.equalsIgnoreCase("Nominal Power Factor")
					|| reportId.equalsIgnoreCase("Poor Power Factor")) {

//			sql = "  SELECT distinct on (df.dttId)circle ,al.town_ipds as town,df.tpparentid as feedercode ,df.dttype ,df.dtname,df.dttId  FROM\r\n" + 
//					"(select tp_towncode,town_ipds from meter_data.amilocation) al Right JOIN\r\n" + 
//					"(SELECT aa.zone,aa.circle,aa.dtid as dttId, aa.good_pf_last_day,aa.dtname,aa.tpparentid,aa.tp_town_code,aa.dttype from (\r\n" + 
//					"(SELECT distinct a.dttpid as dtI,a.tp_town_code,\r\n" + 
//					"\r\n" + 
//					"count(\r\n" + 
//					"CASE\r\n" + 
//					""+formula+" THEN 1\r\n" + 
//					"ELSE NULL\r\n" + 
//					"END) AS good_pf_last_day from (SELECT distinct tp_town_code,dttpid,circle,\r\n" + 
//					"avg(\r\n" + 
//					"CASE\r\n" + 
//					""+period+" THEN load_survey_dt.pf\r\n" + 
//					"ELSE NULL\r\n" + 
//					"END) AS prev_day_wise_avgpf\r\n" + 
//					"\r\n" + 
//					"FROM meter_data.load_survey_dt\r\n" + 
//					"WHERE (load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH)))\r\n" + 
//					"GROUP BY load_survey_dt.dttpid,tp_town_code,circle) a GROUP BY a.dttpid,tp_town_code) ss\r\n" + 
//					"LEFT JOIN\r\n" + 
//					"(SELECT cc.zone, cc.circle, cc.dttpid as dtId,cc.dtname,cc.tpparentid,cc.dttype FROM ( (SELECT DISTINCT location_id, zone,circle FROM meter_data.master_main WHERE ((master_main.fdrcategory) = 'DT')) a JOIN\r\n" + 
//					"(SELECT DISTINCT dtdetails.dttpid,tpparentid,dtname,dttype FROM meter_data.dtdetails) ds on ds.dttpid=a.location_id) cc\r\n" + 
//					"\r\n" + 
//					"\r\n" + 
//					") pp ON pp.dtId = ss.dtI )aa where zone like '"+region+"' and circle like '"+circle+"' and aa.good_pf_last_day = '1') df\r\n" + 
//					"on df.tp_town_code = al.tp_towncode\r\n" + 
//					"";

				sql = "SELECT distinct on (df.dttId)circle ,al.town_ipds as town,df.tpparentid as feedercode ,df.dttype ,df.dtname,df.dttId,df.prev_day_wise_avgpf  FROM\r\n"
						+ "(select tp_towncode,town_ipds from meter_data.amilocation) al Right JOIN\r\n"
						+ "(SELECT aa.zone,aa.circle,aa.dtid as dttId, aa.good_pf_last_day,aa.dtname,aa.tpparentid,aa.tp_town_code,aa.dttype,round(aa.prev_day_wise_avgpf,2)as prev_day_wise_avgpf from (\r\n"
						+ "(SELECT distinct a.dttpid as dtI,a.tp_town_code,\r\n" + "\r\n" + "count(\r\n" + "CASE\r\n"
						+ "" + formula + " THEN 1\r\n" + "ELSE NULL\r\n"
						+ "END) AS good_pf_last_day,prev_day_wise_avgpf from (SELECT distinct tp_town_code,dttpid,circle,\r\n"
						+ "avg(\r\n" + "CASE\r\n" + "" + period + " THEN load_survey_dt.pf\r\n" + "ELSE NULL\r\n"
						+ "END) AS prev_day_wise_avgpf\r\n" + "\r\n" + "FROM meter_data.load_survey_dt\r\n"
						+ "WHERE (load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH)))\r\n"
						+ "GROUP BY load_survey_dt.dttpid,tp_town_code,circle) a where prev_day_wise_avgpf is not null GROUP BY a.dttpid,tp_town_code,prev_day_wise_avgpf having "
						+ condition + ") ss\r\n" + "LEFT JOIN\r\n"
						+ "(SELECT cc.zone, cc.circle, cc.dttpid as dtId,cc.dtname,cc.tpparentid,cc.dttype FROM ((SELECT DISTINCT location_id, zone,circle FROM meter_data.master_main WHERE ((master_main.fdrcategory) = 'DT')) a JOIN\r\n"
						+ "(SELECT DISTINCT dtdetails.dttpid,tpparentid,dtname,dttype FROM meter_data.dtdetails) ds on ds.dttpid=a.location_id) cc\r\n"
						+ "\r\n" + "\r\n" + ") pp ON pp.dtId = ss.dtI )aa where zone like '" + region
						+ "' and circle like '" + circle + "' and aa.good_pf_last_day = '1') df\r\n"
						+ "on df.tp_town_code = al.tp_towncode";

			}

			// Utilization Factor

			if (reportId.equalsIgnoreCase("Good Utilization Factor")
					|| reportId.equalsIgnoreCase("Nominal Utilization Factor")
					|| reportId.equalsIgnoreCase("Poor Utilization Factor")) {

//			sql = "  SELECT distinct on (df.dttId)circle ,al.town_ipds as town,df.tpparentid as feedercode,'LT' ,df.dtname,df.dttId  FROM\r\n" + 
//					"(select tp_towncode,town_ipds from meter_data.amilocation) al Right JOIN\r\n" + 
//					"(SELECT aa.zone,aa.circle,aa.dtid as dttId, aa.good_uf_last_day,aa.dtname,aa.tpparentid,aa.tp_town_code from (\r\n" + 
//					"(SELECT distinct a.dttid as dtI,a.tp_town_code,\r\n" + 
//					"\r\n" + 
//					"count(\r\n" + 
//					"CASE\r\n" + 
//					""+formula+" THEN 1\r\n" + 
//					"ELSE NULL\r\n" + 
//					"END) AS good_uf_last_day from ( SELECT son.* from (SELECT dd.* ,ee.* FROM\r\n" + 
//					"\r\n" + 
//					"(SELECT distinct tp_town_code,dttpid as dttid,circle,\r\n" + 
//					"max(\r\n" + 
//					"CASE\r\n" + 
//					""+period+" THEN load_survey_dt.kva\r\n" + 
//					"ELSE NULL\r\n" + 
//					"END) AS prev_day_wise_maxkva\r\n" + 
//					"\r\n" + 
//					"FROM meter_data.load_survey_dt\r\n" + 
//					"WHERE (load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH)))\r\n" + 
//					"GROUP BY load_survey_dt.dttpid,tp_town_code,circle) dd,\r\n" + 
//					"(SELECT DISTINCT dtdetails.dttpid,dtcapacity FROM meter_data.dtdetails) ee WHERE ee.dttpid=dd.dttid ) son\r\n" + 
//					"\r\n" + 
//					") a GROUP BY a.dttid,tp_town_code) ss\r\n" + 
//					"LEFT JOIN\r\n" + 
//					"(SELECT a.zone, a.circle, ds.dttpid as dtId,ds.dtname,ds.tpparentid ,ds.dtcapacity FROM ( (SELECT DISTINCT location_id, zone,circle FROM meter_data.master_main WHERE ((master_main.fdrcategory) = 'DT')) a JOIN\r\n" + 
//					"(SELECT DISTINCT dtdetails.dttpid,tpparentid,dtname,dtcapacity FROM meter_data.dtdetails) ds on ds.dttpid=a.location_id)\r\n" + 
//					"\r\n" + 
//					") pp ON pp.dtId = ss.dtI )aa where zone like '"+region+"' and circle like '"+circle+"' and aa.good_uf_last_day = '1') df\r\n" + 
//					"on df.tp_town_code = al.tp_towncode ";

				sql = "SELECT distinct on (df.dttId)circle ,al.town_ipds as town,df.tpparentid as feedercode,'LT' ,df.dtname,df.dttId,df.test as value  FROM\r\n"
						+ "(select tp_towncode,town_ipds from meter_data.amilocation) al Right JOIN\r\n"
						+ "(SELECT aa.zone,aa.circle,aa.dtid as dttId, aa.good_uf_last_day,aa.dtname,aa.tpparentid,aa.tp_town_code,aa.test from (\r\n"
						+ "(SELECT distinct a.dttid as dtI,a.tp_town_code,\r\n" + "\r\n" + "count(\r\n" + "CASE\r\n"
						+ "" + formula + " THEN 1\r\n" + "ELSE NULL\r\n"
						+ "END) AS good_uf_last_day,round((a.prev_day_wise_maxkva / a.dtcapacity),2) as test from ( SELECT son.* from (SELECT dd.* ,ee.* FROM\r\n"
						+ "\r\n" + "(SELECT distinct tp_town_code,dttpid as dttid,circle,\r\n" + "max(\r\n" + "CASE\r\n"
						+ "" + period + " THEN load_survey_dt.kva\r\n" + "ELSE NULL\r\n"
						+ "END) AS prev_day_wise_maxkva\r\n" + "\r\n" + "FROM meter_data.load_survey_dt\r\n"
						+ "WHERE (load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH)))\r\n"
						+ "GROUP BY load_survey_dt.dttpid,tp_town_code,circle) dd,\r\n"
						+ "(SELECT DISTINCT dtdetails.dttpid,dtcapacity FROM meter_data.dtdetails) ee WHERE ee.dttpid=dd.dttid ) son\r\n"
						+ "\r\n" + ") a GROUP BY a.dttid,tp_town_code,prev_day_wise_maxkva,a.dtcapacity ) ss\r\n"
						+ "LEFT JOIN\r\n"
						+ "(SELECT a.zone, a.circle, ds.dttpid as dtId,ds.dtname,ds.tpparentid ,ds.dtcapacity FROM ((SELECT DISTINCT location_id, zone,circle FROM meter_data.master_main WHERE ((master_main.fdrcategory) = 'DT')) a JOIN\r\n"
						+ "(SELECT DISTINCT dtdetails.dttpid,tpparentid,dtname,dtcapacity FROM meter_data.dtdetails) ds on ds.dttpid=a.location_id)\r\n"
						+ "\r\n" + ") pp ON pp.dtId = ss.dtI )aa where zone like '" + region + "' and circle like '"
						+ circle + "' and aa.good_uf_last_day = '1'\r\n"
						+ "group by aa.zone,aa.circle,aa.dtId,aa.good_uf_last_day,aa.dtname,aa.tpparentid,aa.tp_town_code,aa.test\r\n"
						+ "having " + condition + " ) df\r\n" + "on df.tp_town_code = al.tp_towncode  ";

			}

			// LOAD FACTOR

			if (reportId.equalsIgnoreCase("Good Load Factor") || reportId.equalsIgnoreCase("Nominal Load Factor")
					|| reportId.equalsIgnoreCase("Poor Load Factor")) {

				sql = "SELECT distinct on (df.dttpid)circle ,al.town_ipds as town,df.fedeerCode as feedercode,'LT' ,df.Dt_Name,df.dttpid  FROM\r\n"
						+ "(select tp_towncode,town_ipds from meter_data.amilocation) al Right JOIN\r\n"
						+ "(SELECT cc.dt_total AS dttpid,\r\n" + "    cc.circle,\r\n"
						+ "                cc.fedeerCode,\r\n" + "                bb.tp_town_code,\r\n"
						+ "								cc.Dt_Name\r\n" + "\r\n" + "   FROM ((( SELECT a.dttpid\r\n"
						+ "\r\n" + "           FROM ( SELECT load_survey_dt.dttpid\r\n" + "\r\n"
						+ "                   FROM meter_data.load_survey_dt\r\n"
						+ "                  WHERE (load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH)))\r\n"
						+ "                  GROUP BY load_survey_dt.dttpid) a\r\n"
						+ "          GROUP BY a.dttpid) aa\r\n" + "     LEFT JOIN ( SELECT a.dttpid,a.tp_town_code,\r\n"
						+ "\r\n" + "\r\n" + "            count(\r\n" + "                CASE\r\n"
						+ "                    " + formula + " THEN 1\r\n" + "                    ELSE NULL\r\n"
						+ "                END) AS nominal_lf_last_month\r\n" + "\r\n" + "\r\n"
						+ "           FROM ( SELECT load_survey_dt.dttpid,load_survey_dt.tp_town_code,\r\n" + "\r\n"
						+ "                    max(\r\n" + "                        CASE\r\n"
						+ "                            " + period + " THEN load_survey_dt.kva\r\n"
						+ "                            ELSE NULL\r\n"
						+ "                        END) AS prev_mnth_wise_maxkva,\r\n" + "\r\n"
						+ "                    avg(\r\n" + "                        CASE\r\n"
						+ "                            " + period + " THEN load_survey_dt.kva\r\n"
						+ "                            ELSE NULL\r\n"
						+ "                        END) AS prev_mnth_wise_avgkva\r\n"
						+ "                   FROM meter_data.load_survey_dt\r\n"
						+ "                  WHERE ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.kva <> (0)))\r\n"
						+ "                  GROUP BY load_survey_dt.dttpid,load_survey_dt.tp_town_code) a\r\n"
						+ "          GROUP BY a.dttpid,a.tp_town_code) bb ON (((aa.dttpid) = (bb.dttpid))))\r\n"
						+ "     RIGHT JOIN ( SELECT ds_1.dttpid AS dt_total, ds_1.tpparentid as fedeerCode, ds_1.dtname as Dt_Name,\r\n"
						+ "            ds_1.zone,\r\n" + "            ds_1.circle\r\n"
						+ "           FROM (( SELECT load_survey_dt.dttpid\r\n" + "\r\n"
						+ "                   FROM meter_data.load_survey_dt\r\n"
						+ "                  WHERE ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.kva <> (0)))\r\n"
						+ "                  GROUP BY load_survey_dt.dttpid) ls\r\n"
						+ "             RIGHT JOIN (meter_data.dtdetails ds\r\n"
						+ "             JOIN ( SELECT DISTINCT master_main.location_id,\r\n"
						+ "                    master_main.zone,\r\n" + "                    master_main.circle\r\n"
						+ "                   FROM meter_data.master_main WHERE zone like '" + region
						+ "' and circle like '" + circle + "' and\r\n"
						+ "                   ((master_main.fdrcategory) = 'DT')) z ON (((ds.dttpid) = (z.location_id)))) ds_1 ON (((ls.dttpid) = (ds_1.dttpid))))\r\n"
						+ "          WHERE ((ds_1.meterno IS NOT NULL) AND ((ds_1.meterno) <> ''))\r\n"
						+ "          GROUP BY ds_1.dttpid, ds_1.zone, ds_1.circle,ds_1.tpparentid,ds_1.dtname) cc ON (((aa.dttpid) = (cc.dt_total)))) WHERE nominal_lf_last_month='1'\r\n"
						+ "  GROUP BY   cc.zone, cc.circle, cc.dt_total, cc.fedeerCode,cc.Dt_Name,bb.tp_town_code) df on df.tp_town_code = al.tp_towncode";

				sql = "SELECT distinct on (df.dttpid)circle ,al.town_ipds as town,df.fedeerCode as feedercode,'LT' ,df.Dt_Name,df.dttpid,round(df.checking,2)  FROM\r\n"
						+ "(select tp_towncode,town_ipds from meter_data.amilocation) al Right JOIN\r\n"
						+ "(SELECT cc.dt_total AS dttpid,\r\n" + "    cc.circle,\r\n"
						+ "                cc.fedeerCode,\r\n" + "                bb.tp_town_code,\r\n"
						+ "								cc.Dt_Name,bb.checking\r\n" + "\r\n"
						+ "   FROM ((( SELECT a.dttpid\r\n" + "\r\n"
						+ "           FROM ( SELECT load_survey_dt.dttpid\r\n" + "\r\n"
						+ "                   FROM meter_data.load_survey_dt\r\n"
						+ "                  WHERE (load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH)))\r\n"
						+ "                  GROUP BY load_survey_dt.dttpid) a\r\n"
						+ "          GROUP BY a.dttpid) aa\r\n"
						+ "     LEFT JOIN ( SELECT a.dttpid,a.tp_town_code,((a.prev_mnth_wise_avgkva / nullif(a.prev_mnth_wise_maxkva,0))) as checking,\r\n"
						+ "\r\n" + "\r\n" + "            count(\r\n" + "                CASE\r\n"
						+ "                   " + formula + " THEN 1\r\n" + "                    ELSE NULL\r\n"
						+ "                END) AS nominal_lf_last_month\r\n" + "\r\n" + "\r\n"
						+ "           FROM ( SELECT load_survey_dt.dttpid,load_survey_dt.tp_town_code,\r\n" + "\r\n"
						+ "                    max(\r\n" + "                        CASE\r\n"
						+ "                            " + period + " THEN load_survey_dt.kva\r\n"
						+ "                            ELSE NULL\r\n"
						+ "                        END) AS prev_mnth_wise_maxkva,\r\n" + "\r\n"
						+ "                    avg(\r\n" + "                        CASE\r\n"
						+ "                            " + period + " THEN load_survey_dt.kva\r\n"
						+ "                            ELSE NULL\r\n"
						+ "                        END) AS prev_mnth_wise_avgkva\r\n"
						+ "                   FROM meter_data.load_survey_dt\r\n"
						+ "                  WHERE ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.kva <> (0)))\r\n"
						+ "                  GROUP BY load_survey_dt.dttpid,load_survey_dt.tp_town_code) a\r\n"
						+ "          GROUP BY a.dttpid,a.tp_town_code,a.prev_mnth_wise_avgkva,a.prev_mnth_wise_maxkva ) bb ON (((aa.dttpid) = (bb.dttpid))))\r\n"
						+ "     RIGHT JOIN ( SELECT ds_1.dttpid AS dt_total, ds_1.tpparentid as fedeerCode, ds_1.dtname as Dt_Name,\r\n"
						+ "            ds_1.zone,\r\n" + "            ds_1.circle\r\n"
						+ "           FROM (( SELECT load_survey_dt.dttpid\r\n" + "\r\n"
						+ "                   FROM meter_data.load_survey_dt\r\n"
						+ "                  WHERE ((load_survey_dt.yearmonth >= date_trunc('month', (CURRENT_DATE - INTERVAL '1' MONTH))) AND (load_survey_dt.kva <> (0)))\r\n"
						+ "                  GROUP BY load_survey_dt.dttpid) ls\r\n"
						+ "             RIGHT JOIN (meter_data.dtdetails ds\r\n"
						+ "             JOIN ( SELECT DISTINCT master_main.location_id,\r\n"
						+ "                    master_main.zone,\r\n" + "                    master_main.circle\r\n"
						+ "                   FROM meter_data.master_main WHERE zone like '" + region
						+ "' and circle like '" + circle + "' and\r\n"
						+ "                   ((master_main.fdrcategory) = 'DT')) z ON (((ds.dttpid) = (z.location_id)))) ds_1 ON (((ls.dttpid) = (ds_1.dttpid))))\r\n"
						+ "          WHERE ((ds_1.meterno IS NOT NULL) AND ((ds_1.meterno) <> ''))\r\n"
						+ "          GROUP BY ds_1.dttpid, ds_1.zone, ds_1.circle,ds_1.tpparentid,ds_1.dtname) cc ON (((aa.dttpid) = (cc.dt_total)))) WHERE nominal_lf_last_month='1'\r\n"
						+ "  GROUP BY   cc.zone, cc.circle, cc.dt_total, cc.fedeerCode,cc.Dt_Name,bb.tp_town_code,bb.checking) df on df.tp_town_code = al.tp_towncode";

			}

			System.out.println(sql);
			dtdashboardList = postgresMdas.createNativeQuery(sql).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dtdashboardList;
	}

	@Override
	public List<?> getTotalinstanceDetails(String dttpid, String region, String circle, String reportId,
			String reportIdPeriod, String meterno) {

		List<List<?>> dtdashboardInsatncesList = new ArrayList<>();
		String period = "";
		String period1 = "";
		String period2 = "";
		String formula = "";
		String sql = "";
		String condition = "";

		// System.err.println("dttpid --- "+dttpid);

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Overload DT")) {

			period = "where ((max_curr/DT_CURR_RATING)*100)>70 AND date(a.yearmonth)=CURRENT_DATE-1";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Overload DT")) {

			period = " where ((max_curr/DT_CURR_RATING)*100)>70 and date(yearmonth) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1) ";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Overload DT")) {

			period = "where  ((max_curr/DT_CURR_RATING)*100)>70 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Overload DT")) {

			period = " where  ((max_curr/DT_CURR_RATING)*100)>70 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Underload DT")) {

			period = "where ( (max_curr/DT_CURR_RATING*100)<20 and date(a.yearmonth)=CURRENT_DATE-1)";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Underload DT")) {

			period = " where ((max_curr/DT_CURR_RATING)*100)<20 and date(yearmonth) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1) ";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Underload DT")) {

			period = "where  ((max_curr/DT_CURR_RATING)*100)<20 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Underload DT")) {

			period = " where  ((max_curr/DT_CURR_RATING)*100)<20 and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("Unbalance DT")) {

			period = "	where (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50)  and date(a.yearmonth)=CURRENT_DATE-1";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("Unbalance DT")) {

			period = "	where (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50)  and date(yearmonth) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1)) ";
		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("Unbalance DT")) {

			period = " 	where (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50)  and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("Unbalance DT")) {

			period = "	where (r_ph_avg-y_ph_avg>50 or y_ph_avg-b_ph_avg>50 or b_ph_avg-r_ph_avg>50)  and to_char(yearmonth,'YYYYMM')=to_char(date_trunc('month', current_date - interval '1' month),'yyyymm')";
		}

		if (reportIdPeriod.equalsIgnoreCase("Last Day") && reportId.equalsIgnoreCase("PowerFailure DT")) {

			period = "where event_code='101' AND (date(e.event_time) = (CURRENT_DATE - 1))  and dttpid is not null and dttpid != '')a";
			period1 = "where event_code='101' AND (date(event_time) = (CURRENT_DATE - 1))";

		}

		if (reportIdPeriod.equalsIgnoreCase("Last Week") && reportId.equalsIgnoreCase("PowerFailure DT")) {

			period = "where event_code='101'  and  date(e.event_time) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1) and dttpid is not null and dttpid != '' )a ";
			period1 = "where event_code='101'  and  date(event_time) BETWEEN (CURRENT_DATE - 7) AND (CURRENT_DATE - 1)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Current Month") && reportId.equalsIgnoreCase("PowerFailure DT")) {

//	        period = "where event_code='101'  AND (to_char(e.event_occ_date, 'YYYYMM') = to_char(date_trunc('month', (CURRENT_DATE)), 'yyyymm'))) and dttpid is not null and dttpid != '')a";
			period = "where event_code='101'  and  date(e.event_time) BETWEEN (CURRENT_DATE - 30) AND (CURRENT_DATE - 1) and dttpid is not null and dttpid != '' )a ";
			period1 = "where event_code='101'  and  date(event_time) BETWEEN (CURRENT_DATE - 30) AND (CURRENT_DATE - 1)";

		}

		if (reportIdPeriod.equalsIgnoreCase("Previous Month") && reportId.equalsIgnoreCase("PowerFailure DT")) {

			// period = "where event_code='101' and
			// (to_char(event_occ_date,'YYYYMM')=to_char(date_trunc('month', current_date -
			// interval '1' month),'yyyymm')) and dttpid is not null and dttpid != '' AND
			// (e.event_occ_date > (CURRENT_DATE - interval '1' month)) )a";
			period = "where event_code='101'  and  date(e.event_time) BETWEEN (CURRENT_DATE - 30) AND (CURRENT_DATE - 1) and dttpid is not null and dttpid != '' )a ";
			period1 = "where event_code='101'  and  date(event_time) BETWEEN (CURRENT_DATE - 30) AND (CURRENT_DATE - 1)";
		}

		try {

			if (reportId.equalsIgnoreCase("Overload DT")) {

				sql = "select a.dttpid as dtcode,a.dtname,a.no_meters,a.dt_capacity,a.DT_CURR_RATING as dtcapacity,TO_CHAR(a.yearmonth,'YYYY-MM-DD HH12:MI' \r\n"
						+ "					    ) yearmonth, \r\n"
						+ "					a.kwh,a.kvah,a.kva,a.ir,a.iy,a.ib,a.vr,a.vy,a.vb,a.pf  \r\n"
						+ "					 \r\n" + "					 \r\n" + "					from  \r\n"
						+ "					( \r\n"
						+ "					select  ls.dttpid as dttpid, yearmonth,max(GREATEST(ir,iy,ib)) as max_curr,max(CASE WHEN DTCAPACITY=100 THEN 100  \r\n"
						+ "							WHEN DTCAPACITY=250 THEN 250 \r\n"
						+ "							WHEN DTCAPACITY=500 THEN 500 \r\n"
						+ "							WHEN DTCAPACITY=25 THEN 25 \r\n"
						+ "							WHEN DTCAPACITY=40 THEN 40 \r\n"
						+ "							WHEN DTCAPACITY=63 THEN 63 \r\n"
						+ "							WHEN DTCAPACITY=315 THEN 315 \r\n"
						+ "							WHEN DTCAPACITY=300 THEN 300 \r\n"
						+ "							WHEN DTCAPACITY=200 THEN 200 \r\n"
						+ "							WHEN DTCAPACITY=175 THEN 175 \r\n"
						+ "							WHEN DTCAPACITY=160 THEN 160 \r\n"
						+ "							WHEN DTCAPACITY=150 THEN 150 \r\n"
						+ "							WHEN DTCAPACITY=75 THEN 75 \r\n"
						+ "							WHEN DTCAPACITY=50 THEN 50 \r\n"
						+ "							WHEN DTCAPACITY=16 THEN 16 \r\n"
						+ "							WHEN DTCAPACITY is null THEN 100  \r\n"
						+ "					END) as dt_capacity, \r\n"
						+ "						max(CASE WHEN DTCAPACITY=100 THEN 133  \r\n"
						+ "							WHEN DTCAPACITY=250 THEN 333 \r\n"
						+ "							WHEN DTCAPACITY=500 THEN 666 \r\n"
						+ "							WHEN DTCAPACITY=25 THEN 33 \r\n"
						+ "							WHEN DTCAPACITY=40 THEN 53 \r\n"
						+ "							WHEN DTCAPACITY=63 THEN 83 \r\n"
						+ "							WHEN DTCAPACITY=315 THEN 420 \r\n"
						+ "							WHEN DTCAPACITY=300 THEN 400 \r\n"
						+ "							WHEN DTCAPACITY=200 THEN 266 \r\n"
						+ "							WHEN DTCAPACITY=175 THEN 233 \r\n"
						+ "							WHEN DTCAPACITY=160 THEN 213 \r\n"
						+ "							WHEN DTCAPACITY=150 THEN 200 \r\n"
						+ "							WHEN DTCAPACITY=75 THEN 100 \r\n"
						+ "							WHEN DTCAPACITY=50 THEN 66 \r\n"
						+ "							WHEN DTCAPACITY=16 THEN 21 \r\n"
						+ "							WHEN DTCAPACITY is null THEN 133  \r\n" + "					 \r\n"
						+ "					END) DT_CURR_RATING,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,count(dd.meterno)no_meters,string_agg(dd.dtname,',') as dtname \r\n"
						+ "						 \r\n" + "						 \r\n"
						+ "						from meter_data.load_survey_dt ls,meter_data.dtdetails dd  \r\n"
						+ "					where  ls.dttpid=dd.dttpid AND (ls.yearmonth > (CURRENT_DATE - INTERVAL '2' MONTH)) and ls.dttpid='"
						+ dttpid + "'  \r\n"
						+ "					group by ls.dttpid,yearmonth,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,dtcapacity \r\n"
						+ "					)a RIGHT JOIN \r\n" + "					( \r\n"
						+ "					select distinct location_id from meter_data.master_main where zone like '"
						+ region + "' and circle like '" + circle + "' \r\n"
						+ "					)z on (a.dttpid=z.location_id) " + period + "";
			}

			if (reportId.equalsIgnoreCase("Underload DT")) {

				sql = "select a.dttpid as dtcode,a.dtname,a.no_meters,a.dt_capacity,a.DT_CURR_RATING as dtcapacity,TO_CHAR(a.yearmonth,'YYYY-MM-DD HH12:MI'\r\n"
						+ "    ) yearmonth,\r\n" + "a.kwh,a.kvah,a.kva,a.ir,a.iy,a.ib,a.vr,a.vy,a.vb,a.pf \r\n"
						+ "from \r\n" + "(\r\n"
						+ "select  ls.dttpid as dttpid, yearmonth,max(GREATEST(ir,iy,ib)) as max_curr,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n"
						+ "		WHEN DTCAPACITY=250 THEN 250\r\n" + "		WHEN DTCAPACITY=500 THEN 500\r\n"
						+ "		WHEN DTCAPACITY=25 THEN 25\r\n" + "		WHEN DTCAPACITY=40 THEN 40\r\n"
						+ "		WHEN DTCAPACITY=63 THEN 63\r\n" + "		WHEN DTCAPACITY=315 THEN 315\r\n"
						+ "		WHEN DTCAPACITY=300 THEN 300\r\n" + "		WHEN DTCAPACITY=200 THEN 200\r\n"
						+ "		WHEN DTCAPACITY=175 THEN 175\r\n" + "		WHEN DTCAPACITY=160 THEN 160\r\n"
						+ "		WHEN DTCAPACITY=150 THEN 150\r\n" + "		WHEN DTCAPACITY=75 THEN 75\r\n"
						+ "		WHEN DTCAPACITY=50 THEN 50\r\n" + "		WHEN DTCAPACITY=16 THEN 16\r\n"
						+ "		WHEN DTCAPACITY is null THEN 100 \r\n" + "END) as dt_capacity,\r\n"
						+ "	max(CASE WHEN DTCAPACITY=100 THEN 133 \r\n" + "		WHEN DTCAPACITY=250 THEN 333\r\n"
						+ "		WHEN DTCAPACITY=500 THEN 666\r\n" + "		WHEN DTCAPACITY=25 THEN 33\r\n"
						+ "		WHEN DTCAPACITY=40 THEN 53\r\n" + "		WHEN DTCAPACITY=63 THEN 83\r\n"
						+ "		WHEN DTCAPACITY=315 THEN 420\r\n" + "		WHEN DTCAPACITY=300 THEN 400\r\n"
						+ "		WHEN DTCAPACITY=200 THEN 266\r\n" + "		WHEN DTCAPACITY=175 THEN 233\r\n"
						+ "		WHEN DTCAPACITY=160 THEN 213\r\n" + "		WHEN DTCAPACITY=150 THEN 200\r\n"
						+ "		WHEN DTCAPACITY=75 THEN 100\r\n" + "		WHEN DTCAPACITY=50 THEN 66\r\n"
						+ "		WHEN DTCAPACITY=16 THEN 21\r\n" + "		WHEN DTCAPACITY is null THEN 133 \r\n" + "\r\n"
						+ "END) DT_CURR_RATING,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,count(dd.meterno)no_meters,string_agg(dd.dtname,',') as dtname\r\n"
						+ "	\r\n" + "	from meter_data.load_survey_dt ls,meter_data.dtdetails dd \r\n"
						+ "where  ls.dttpid=dd.dttpid AND (ls.yearmonth > (CURRENT_DATE - INTERVAL '2' MONTH )) and ls.dttpid='"
						+ dttpid + "' \r\n" + "group by ls.dttpid,yearmonth,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf\r\n"
						+ ")a RIGHT JOIN\r\n" + "(\r\n"
						+ "select distinct location_id from meter_data.master_main where zone like '" + region
						+ "' and circle like '" + circle + "' and circle is not null\r\n"
						+ ")z on (a.dttpid=z.location_id)  " + period + "";
			}

			if (reportId.equalsIgnoreCase("Unbalance DT")) {

				sql = "select  a.dttpid as dtcode,a.dtname,a.no_meters,a.dt_capacity,a.DT_CURR_RATING as dtcapacity, yearmonth,\r\n"
						+ "a.kwh,a.kvah,a.kva,a.ir,a.iy,a.ib,a.vr,a.vy,a.vb,a.pf \r\n" + "\r\n" + "from \r\n" + "(\r\n"
						+ "select dt.dttpid,yearmonth,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,count(b.meterno)no_meters,string_agg(b.dtname,',') as dtname,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n"
						+ "		WHEN DTCAPACITY=250 THEN 250\r\n" + "		WHEN DTCAPACITY=500 THEN 500\r\n"
						+ "		WHEN DTCAPACITY=25 THEN 25\r\n" + "		WHEN DTCAPACITY=40 THEN 40\r\n"
						+ "		WHEN DTCAPACITY=63 THEN 63\r\n" + "		WHEN DTCAPACITY=315 THEN 315\r\n"
						+ "		WHEN DTCAPACITY=300 THEN 300\r\n" + "		WHEN DTCAPACITY=200 THEN 200\r\n"
						+ "		WHEN DTCAPACITY=175 THEN 175\r\n" + "		WHEN DTCAPACITY=160 THEN 160\r\n"
						+ "		WHEN DTCAPACITY=150 THEN 150\r\n" + "		WHEN DTCAPACITY=75 THEN 75\r\n"
						+ "		WHEN DTCAPACITY=50 THEN 50\r\n" + "		WHEN DTCAPACITY=16 THEN 16\r\n"
						+ "		WHEN DTCAPACITY is null THEN 100 \r\n" + "END) as dt_capacity,\r\n"
						+ "((ir)-((ir+iy+ib)/3)/NULLIF (((ir+iy+ib)/3),0)) as r_ph_avg,\r\n"
						+ "((iy)-((ir+iy+ib)/3)/NULLIF  (((ir+iy+ib)/3),0)) as y_ph_avg,\r\n"
						+ "((ib)-((ir+iy+ib)/3)/NULLIF  (((ir+iy+ib)/3),0)) as b_ph_avg,\r\n"
						+ " string_agg(b.dtname,','),b.dttype,\r\n" + " max(CASE WHEN DTCAPACITY=100 THEN 133 \r\n"
						+ "		WHEN DTCAPACITY=250 THEN 333\r\n" + "		WHEN DTCAPACITY=500 THEN 666\r\n"
						+ "		WHEN DTCAPACITY=25 THEN 33\r\n" + "		WHEN DTCAPACITY=40 THEN 53\r\n"
						+ "		WHEN DTCAPACITY=63 THEN 83\r\n" + "		WHEN DTCAPACITY=315 THEN 420\r\n"
						+ "		WHEN DTCAPACITY=300 THEN 400\r\n" + "		WHEN DTCAPACITY=200 THEN 266\r\n"
						+ "		WHEN DTCAPACITY=175 THEN 233\r\n" + "		WHEN DTCAPACITY=160 THEN 213\r\n"
						+ "		WHEN DTCAPACITY=150 THEN 200\r\n" + "		WHEN DTCAPACITY=75 THEN 100\r\n"
						+ "		WHEN DTCAPACITY=50 THEN 66\r\n" + "		WHEN DTCAPACITY=16 THEN 21\r\n"
						+ "		WHEN DTCAPACITY is null THEN 133 \r\n" + "\r\n" + "END) DT_CURR_RATING\r\n"
						+ " from  \r\n"
						+ "meter_data.load_survey_dt dt, meter_data.dtdetails b where dt.dttpid=b.dttpid  and dt.dttpid='"
						+ dttpid + "' and dt.dttpid in (\r\n"
						+ "select distinct location_id from meter_data.master_main where zone like '" + region
						+ "'  and circle like '" + circle + "' and fdrcategory='DT')\r\n"
						+ "and (yearmonth > (CURRENT_DATE - interval '2' MONTH))\r\n"
						+ " group by dt.dttpid,yearmonth,kwh,kvah,kva,ir,iy,ib,vr,vy,vb,pf,b.dttype\r\n" + " \r\n"
						+ ") a\r\n" + "  \r\n" + " " + period + "	";
			}

//			if(reportId.equalsIgnoreCase("PowerFailure DT")) {
//				
//				sql="select distinct AA.dttpid as dtcode,i_r,i_y,i_b,v_r,v_y,v_b, event_time,kwh,kvah,AA.meter_number as no_meter,BB.dtcapacity,BB.dtname from \r\n" + 
//						"				( \r\n" + 
//						"				select a.dttpid as dttpid,a.event_time,a.i_r,a.i_y,a.i_b,a.v_r,a.v_y,a.v_b,a.kwh,a.kvah,a.meter_number from \r\n" + 
//						"				( \r\n" + 
//						"				select dttpid,event_time,i_r,i_y,i_b,v_r,v_y,v_b,kwh,kvah,meter_number \r\n" + 
//						"				from meter_data.events e left join (meter_data.dtdetails d INNER JOIN (select distinct location_id from meter_data.master_main where zone like '"+region+"'  and circle like '"+circle+"' and fdrcategory='DT')z on d.dttpid=z.location_id )d ON (((e.meter_number) = (d.meterno))) "+period+")AA  left JOIN \r\n" + 
//						"				 \r\n" + 
//						"				( \r\n" + 
//						"				 \r\n" + 
//						"				select  dttpid , \r\n" + 
//						"				max(CASE WHEN dtcapacity=100 THEN 133  \r\n" + 
//						"						WHEN dtcapacity=250 THEN 333 \r\n" + 
//						"						WHEN dtcapacity=500 THEN 666 \r\n" + 
//						"						WHEN dtcapacity=25 THEN 33 \r\n" + 
//						"						WHEN dtcapacity=40 THEN 53 \r\n" + 
//						"						WHEN dtcapacity=63 THEN 83 \r\n" + 
//						"						WHEN dtcapacity=315 THEN 420 \r\n" + 
//						"						WHEN dtcapacity=300 THEN 400 \r\n" + 
//						"						WHEN dtcapacity=200 THEN 266 \r\n" + 
//						"						WHEN dtcapacity=175 THEN 233 \r\n" + 
//						"						WHEN dtcapacity=160 THEN 213 \r\n" + 
//						"						WHEN dtcapacity=150 THEN 200 \r\n" + 
//						"						WHEN dtcapacity=75 THEN 100 \r\n" + 
//						"						WHEN dtcapacity=50 THEN 66 \r\n" + 
//						"						WHEN dtcapacity=16 THEN 21 \r\n" + 
//						"						WHEN dtcapacity is null THEN 133 END) as dtcapacity,count(c.meterno)no_meter,string_agg(DISTINCT c.dtname,',') as dtname \r\n" + 
//						"						from meter_data.dtdetails c  left join meter_data.events e on (c.meterno=e.meter_number) WHERE e.event_code='101' and  (((e.event_code) = '101') AND (date(e.event_time) = (CURRENT_DATE - 1))) group by dttpid,dtcapacity \r\n" + 
//						"				)BB on AA.dttpid=BB.dttpid WHERE AA.dttpid like '"+dttpid+"' ";
//				}
//			
//			
			if (reportId.equalsIgnoreCase("PowerFailure DT")) {

//				sql="select distinct AA.dttpid as dtcode,BB.dtname,AA.meter_number as no_meter,BB.dt_capacity as dt_capacity,BB.dtcapacity,event_time,kwh,kvah,i_r,i_y,i_b,v_r,v_y,v_b from \r\n" + 
//						"				( \r\n" + 
//						"				select a.dttpid as dttpid,a.event_time,a.i_r,a.i_y,a.i_b,a.v_r,a.v_y,a.v_b,a.kwh,a.kvah,a.meter_number from \r\n" + 
//						"				( \r\n" + 
//						"				select dttpid,event_time,i_r,i_y,i_b,v_r,v_y,v_b,kwh,kvah,meter_number \r\n" + 
//						"				from meter_data.events e left join (meter_data.dtdetails d INNER JOIN (select distinct location_id from meter_data.master_main where zone like '"+region+"'  and circle like '"+circle+"' and fdrcategory='DT')z on d.dttpid=z.location_id )d ON (((e.meter_number) = (d.meterno)))"+period+")AA  left JOIN \r\n" + 
//						"				 \r\n" + 
//						"				( select  dttpid ,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n" + 
//						"						WHEN DTCAPACITY=250 THEN 250\r\n" + 
//						"						WHEN DTCAPACITY=500 THEN 500\r\n" + 
//						"						WHEN DTCAPACITY=25 THEN 25\r\n" + 
//						"						WHEN DTCAPACITY=40 THEN 40\r\n" + 
//						"						WHEN DTCAPACITY=63 THEN 63\r\n" + 
//						"						WHEN DTCAPACITY=315 THEN 315\r\n" + 
//						"						WHEN DTCAPACITY=300 THEN 300\r\n" + 
//						"						WHEN DTCAPACITY=200 THEN 200\r\n" + 
//						"						WHEN DTCAPACITY=175 THEN 175\r\n" + 
//						"						WHEN DTCAPACITY=160 THEN 160\r\n" + 
//						"						WHEN DTCAPACITY=150 THEN 150\r\n" + 
//						"						WHEN DTCAPACITY=75 THEN 75\r\n" + 
//						"						WHEN DTCAPACITY=50 THEN 50\r\n" + 
//						"						WHEN DTCAPACITY=16 THEN 16\r\n" + 
//						"						WHEN DTCAPACITY is null THEN 100 \r\n" + 
//						"				END) as dt_capacity,\r\n" + 
//						"					MAX(CASE WHEN DTCAPACITY=100 THEN 133 \r\n" + 
//						"						WHEN DTCAPACITY=250 THEN 333\r\n" + 
//						"						WHEN DTCAPACITY=500 THEN 666\r\n" + 
//						"						WHEN DTCAPACITY=25 THEN 33\r\n" + 
//						"						WHEN DTCAPACITY=40 THEN 53\r\n" + 
//						"						WHEN DTCAPACITY=63 THEN 83\r\n" + 
//						"						WHEN DTCAPACITY=315 THEN 420\r\n" + 
//						"						WHEN DTCAPACITY=300 THEN 400\r\n" + 
//						"						WHEN DTCAPACITY=200 THEN 266\r\n" + 
//						"						WHEN DTCAPACITY=175 THEN 233\r\n" + 
//						"						WHEN DTCAPACITY=160 THEN 213\r\n" + 
//						"						WHEN DTCAPACITY=150 THEN 200\r\n" + 
//						"						WHEN DTCAPACITY=75 THEN 100\r\n" + 
//						"						WHEN DTCAPACITY=50 THEN 66\r\n" + 
//						"						WHEN DTCAPACITY=16 THEN 21\r\n" + 
//						"						WHEN DTCAPACITY is null THEN 133 \r\n" + 
//						"\r\n" + 
//						"				END)dtcapacity,count(c.meterno)no_meter,string_agg(DISTINCT c.dtname,',') as dtname \r\n" + 
//						"						from meter_data.dtdetails c  left join meter_data.events e on (c.meterno=e.meter_number) WHERE e.event_code='101' and  (((e.event_code) = '101') AND (date(e.event_time) = (CURRENT_DATE - 1))) group by dttpid,dtcapacity \r\n" + 
//						"				)BB on AA.dttpid=BB.dttpid WHERE AA.dttpid like '"+dttpid+"' \r\n" + 
//						"";	
//				

//			sql="select distinct * from\r\n" + 
//						"(select distinct AA.dttpid as dtcode,BB.dtname,AA.meter_number as no_meter,BB.dt_capacity as dt_capacity,BB.dtcapacity,event_time from \r\n" + 
//						"				( \r\n" + 
//						"				select a.dttpid as dttpid,a.event_time,a.meter_number from \r\n" + 
//						"				( \r\n" + 
//						"				select dttpid,event_time,meter_number,meterno\r\n" + 
//						"				from meter_data.events e left join (meter_data.dtdetails d INNER JOIN (select distinct location_id from meter_data.master_main where zone like '"+region+"'   and circle like '"+circle+"' and fdrcategory='DT')z on d.dttpid=z.location_id )d ON (((e.meter_number) = (d.meterno)))"+period+")AA  left JOIN \r\n" + 
//						"				 \r\n" + 
//						"				( select  dttpid ,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n" + 
//						"						WHEN DTCAPACITY=250 THEN 250\r\n" + 
//						"						WHEN DTCAPACITY=500 THEN 500\r\n" + 
//						"						WHEN DTCAPACITY=25 THEN 25\r\n" + 
//						"						WHEN DTCAPACITY=40 THEN 40\r\n" + 
//						"						WHEN DTCAPACITY=63 THEN 63\r\n" + 
//						"						WHEN DTCAPACITY=315 THEN 315\r\n" + 
//						"						WHEN DTCAPACITY=300 THEN 300\r\n" + 
//						"						WHEN DTCAPACITY=200 THEN 200\r\n" + 
//						"						WHEN DTCAPACITY=175 THEN 175\r\n" + 
//						"						WHEN DTCAPACITY=160 THEN 160\r\n" + 
//						"						WHEN DTCAPACITY=150 THEN 150\r\n" + 
//						"						WHEN DTCAPACITY=75 THEN 75\r\n" + 
//						"						WHEN DTCAPACITY=50 THEN 50\r\n" + 
//						"						WHEN DTCAPACITY=16 THEN 16\r\n" + 
//						"						WHEN DTCAPACITY is null THEN 100 \r\n" + 
//						"				END) as dt_capacity,\r\n" + 
//						"					MAX(CASE WHEN DTCAPACITY=100 THEN 133 \r\n" + 
//						"						WHEN DTCAPACITY=250 THEN 333\r\n" + 
//						"						WHEN DTCAPACITY=500 THEN 666\r\n" + 
//						"						WHEN DTCAPACITY=25 THEN 33\r\n" + 
//						"						WHEN DTCAPACITY=40 THEN 53\r\n" + 
//						"						WHEN DTCAPACITY=63 THEN 83\r\n" + 
//						"						WHEN DTCAPACITY=315 THEN 420\r\n" + 
//						"						WHEN DTCAPACITY=300 THEN 400\r\n" + 
//						"						WHEN DTCAPACITY=200 THEN 266\r\n" + 
//					"						WHEN DTCAPACITY=175 THEN 233\r\n" + 
//						"						WHEN DTCAPACITY=160 THEN 213\r\n" + 
//						"						WHEN DTCAPACITY=150 THEN 200\r\n" + 
//						"						WHEN DTCAPACITY=75 THEN 100\r\n" + 
//						"						WHEN DTCAPACITY=50 THEN 66\r\n" + 
//						"						WHEN DTCAPACITY=16 THEN 21\r\n" + 
//						"						WHEN DTCAPACITY is null THEN 133 \r\n" + 
//					"\r\n" + 
//						"				END)dtcapacity,count(c.meterno)no_meter,string_agg(DISTINCT c.dtname,',') as dtname \r\n" + 
//						"						from meter_data.dtdetails c  left join meter_data.events e on (c.meterno=e.meter_number) WHERE e.event_code='101' and  (((e.event_code) = '101') AND (date(e.event_time) = (CURRENT_DATE - 1))) group by dttpid,dtcapacity \r\n" + 
//						"				)BB on AA.dttpid=BB.dttpid WHERE AA.dttpid like '"+dttpid+"')c1,\r\n" + 
//						"				\r\n" + 
//						"(select distinct event_time as event_restore from meter_data.events where event_code='102' and (date(event_time)=CURRENT_DATE-1) and meter_number IN (select distinct meterno from  meter_data.dtdetails where dttpid LIKE '"+dttpid+"'))d1\r\n" + 
//						"";
//	

				sql = "select distinct AA.dttpid as dtcode,BB.dtname,AA.meter_number as meter_number,BB.dt_capacity as dt_capacity,BB.dtcapacity,event_time  from \r\n"
						+ "				( \r\n"
						+ "				select a.dttpid as dttpid,a.dtname,a.event_time,a.meter_number  from \r\n"
						+ "				( \r\n" + "				select dttpid,dtname,event_time,meter_number \r\n"
						+ "				from meter_data.events e left join (meter_data.dtdetails d INNER JOIN (select distinct location_id from meter_data.master_main where zone like '"
						+ region + "'  and circle like '" + circle
						+ "' and fdrcategory='DT')z on d.dttpid=z.location_id )d ON (((e.meter_number) = (d.meterno))) "
						+ period + " )AA  left JOIN \r\n" + "				 \r\n"
						+ "				( select  dttpid ,max(CASE WHEN DTCAPACITY=100 THEN 100 \r\n"
						+ "						WHEN DTCAPACITY=250 THEN 250\r\n"
						+ "						WHEN DTCAPACITY=500 THEN 500\r\n"
						+ "						WHEN DTCAPACITY=25 THEN 25\r\n"
						+ "						WHEN DTCAPACITY=40 THEN 40\r\n"
						+ "						WHEN DTCAPACITY=63 THEN 63\r\n"
						+ "						WHEN DTCAPACITY=315 THEN 315\r\n"
						+ "						WHEN DTCAPACITY=300 THEN 300\r\n"
						+ "						WHEN DTCAPACITY=200 THEN 200\r\n"
						+ "						WHEN DTCAPACITY=175 THEN 175\r\n"
						+ "						WHEN DTCAPACITY=160 THEN 160\r\n"
						+ "						WHEN DTCAPACITY=150 THEN 150\r\n"
						+ "						WHEN DTCAPACITY=75 THEN 75\r\n"
						+ "						WHEN DTCAPACITY=50 THEN 50\r\n"
						+ "						WHEN DTCAPACITY=16 THEN 16\r\n"
						+ "						WHEN DTCAPACITY is null THEN 100 \r\n"
						+ "				END) as dt_capacity,\r\n"
						+ "					MAX(CASE WHEN DTCAPACITY=100 THEN 133 \r\n"
						+ "						WHEN DTCAPACITY=250 THEN 333\r\n"
						+ "						WHEN DTCAPACITY=500 THEN 666\r\n"
						+ "						WHEN DTCAPACITY=25 THEN 33\r\n"
						+ "						WHEN DTCAPACITY=40 THEN 53\r\n"
						+ "						WHEN DTCAPACITY=63 THEN 83\r\n"
						+ "						WHEN DTCAPACITY=315 THEN 420\r\n"
						+ "						WHEN DTCAPACITY=300 THEN 400\r\n"
						+ "						WHEN DTCAPACITY=200 THEN 266\r\n"
						+ "						WHEN DTCAPACITY=175 THEN 233\r\n"
						+ "						WHEN DTCAPACITY=160 THEN 213\r\n"
						+ "						WHEN DTCAPACITY=150 THEN 200\r\n"
						+ "						WHEN DTCAPACITY=75 THEN 100\r\n"
						+ "						WHEN DTCAPACITY=50 THEN 66\r\n"
						+ "						WHEN DTCAPACITY=16 THEN 21\r\n"
						+ "						WHEN DTCAPACITY is null THEN 133 \r\n" + "\r\n"
						+ "				END)dtcapacity,count(c.meterno)no_meter,string_agg(DISTINCT c.dtname,',') as dtname \r\n"
						+ "						from meter_data.dtdetails c  left join meter_data.events e on (c.meterno=e.meter_number) "
						+ period1 + " group by dttpid,dtcapacity \r\n"
						+ "				)BB on AA.dttpid=BB.dttpid WHERE AA.dttpid like '" + dttpid + "' ";

			}

			System.out.println(sql);
			dtdashboardInsatncesList = postgresMdas.createNativeQuery(sql).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dtdashboardInsatncesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getDtpowerfailureReports(String region, String circle, String reportId, String reportIdPeriod) {

		List<List<?>> dtpowerList = new ArrayList<>();
		String period = "";
		String formula = "";
		String sql = "";
		String condition = "";

		if (reportIdPeriod.equalsIgnoreCase("Current Day") && reportId.equalsIgnoreCase("Total powerfailure Dt")) {

			period = "where zone LIKE '" + region + "' and circle LIKE '" + circle + "' and fdrcategory LIKE 'DT' ";

		}

		try {

			if (reportId.equalsIgnoreCase("Total powerfailure Dt")) {

//			sql="\r\n" + 
//				"select  distinct id,zone,circle,town_code,town,section_code,section,dttpid,dtname,meter_number,dtcapacity_amps,dt_capacity_kva,event_occ_date,event_duration from meter_data.poweronoff_cmd "+period+" ";

				sql = " SELECT DISTINCT ON (aa.id) aa.id,aa.dttpid,\r\n" + "    bb.dtname,\r\n" + "    bb.zone,\r\n"
						+ "    bb.circle,\r\n" + "    bb.tp_sectioncode AS section_code,\r\n" + "    bb.section,\r\n"
						+ "    bb.tp_towncode AS town_code,\r\n" + "    bb.town_ipds AS town,\r\n"
						+ "    aa.meter_sr_number AS meter_number,\r\n" + "    bb.dtcapacity AS dtcapacity_amps,\r\n"
						+ "    bb.dt_capacity AS dt_capacity_kva,\r\n" + "    aa.event_occ_date,\r\n"
						+ "    aa.event_restore_date,\r\n" + "    aa.event_duration\r\n"
						+ "   FROM (( SELECT a.id,a.dttpid,\r\n" + "            a.event_occ_date,\r\n"
						+ "            a.meter_sr_number,\r\n" + "            a.event_restore_date,\r\n"
						+ "            a.event_duration\r\n" + "           FROM ( SELECT e.id,d_1.dttpid,\r\n"
						+ "                    e.event_occ_date,\r\n" + "e.event_restore_date,\r\n"
						+ "                    e.meter_sr_number,\r\n" + "e.event_duration\r\n"
						+ "                   FROM (meter_data.dailydt_poweronff_event1 e\r\n"
						+ "                     LEFT JOIN (meter_data.dtdetails d\r\n"
						+ "                     JOIN (SELECT DISTINCT master_main.location_id\r\n"
						+ "                           FROM meter_data.master_main\r\n" + "                           "
						+ period
						+ ") z ON (((d.dttpid) = (z.location_id)))) d_1 ON (((e.meter_sr_number)= (d_1.meterno))))\r\n"
						+ "                  WHERE (((e.event_code) = '101') and date(e.event_occ_date)=CURRENT_DATE AND (e.event_restore_date IS NOT NULL ) AND (d_1.dttpid IS NOT NULL) AND ((d_1.dttpid) <> ''))) a) aa\r\n"
						+ "     LEFT JOIN ( SELECT c.dttpid,\r\n" + "            max(\r\n" + "                CASE\r\n"
						+ "                    WHEN (c.dtcapacity = (100)) THEN 100\r\n"
						+ "                    WHEN (c.dtcapacity = (250)) THEN 250\r\n"
						+ "                    WHEN (c.dtcapacity = (500)) THEN 500\r\n"
						+ "                    WHEN (c.dtcapacity = (25)) THEN 25\r\n"
						+ "                    WHEN (c.dtcapacity = (40)) THEN 40\r\n"
						+ "                    WHEN (c.dtcapacity = (63)) THEN 63\r\n"
						+ "                    WHEN (c.dtcapacity = (315)) THEN 315\r\n"
						+ "                    WHEN (c.dtcapacity = (300)) THEN 300\r\n"
						+ "                    WHEN (c.dtcapacity = (200)) THEN 200\r\n"
						+ "                    WHEN (c.dtcapacity = (175)) THEN 175\r\n"
						+ "                    WHEN (c.dtcapacity = (160)) THEN 160\r\n"
						+ "                    WHEN (c.dtcapacity = (150)) THEN 150\r\n"
						+ "                    WHEN (c.dtcapacity = (75)) THEN 75\r\n"
						+ "                    WHEN (c.dtcapacity = (50)) THEN 50\r\n"
						+ "                    WHEN (c.dtcapacity = (16)) THEN 16\r\n"
						+ "                    WHEN (c.dtcapacity IS NULL) THEN 100\r\n"
						+ "                    ELSE NULL \r\n" + "                END) AS dt_capacity,\r\n"
						+ "            max(\r\n" + "                CASE\r\n"
						+ "                    WHEN (c.dtcapacity = (100)) THEN 133\r\n"
						+ "                    WHEN (c.dtcapacity = (250)) THEN 333\r\n"
						+ "                    WHEN (c.dtcapacity = (500)) THEN 666\r\n"
						+ "                    WHEN (c.dtcapacity = (25)) THEN 33\r\n"
						+ "                    WHEN (c.dtcapacity = (40)) THEN 53\r\n"
						+ "                    WHEN (c.dtcapacity = (63)) THEN 83\r\n"
						+ "                    WHEN (c.dtcapacity = (315)) THEN 420\r\n"
						+ "                    WHEN (c.dtcapacity = (300)) THEN 400\r\n"
						+ "                    WHEN (c.dtcapacity = (200)) THEN 266\r\n"
						+ "                    WHEN (c.dtcapacity = (175)) THEN 233\r\n"
						+ "                    WHEN (c.dtcapacity = (160)) THEN 213\r\n"
						+ "                    WHEN (c.dtcapacity = (150)) THEN 200\r\n"
						+ "                    WHEN (c.dtcapacity = (75)) THEN 100\r\n"
						+ "                    WHEN (c.dtcapacity = (50)) THEN 66\r\n"
						+ "                    WHEN (c.dtcapacity = (16)) THEN 21\r\n"
						+ "                    WHEN (c.dtcapacity IS NULL) THEN 133\r\n"
						+ "                    ELSE NULL\r\n" + "                END) AS dtcapacity,\r\n"
						+ "            count(c.meterno) AS no_meter,\r\n"
						+ "            string_agg(DISTINCT (c.dtname), ',') AS dtname,\r\n"
						+ "            am.tp_zonecode,\r\n" + "            am.zone,\r\n"
						+ "            am.tp_circlecode,\r\n" + "            am.circle,\r\n"
						+ "            am.tp_sectioncode,\r\n" + "            am.section,\r\n"
						+ "            am.tp_towncode,\r\n" + "            am.town_ipds\r\n"
						+ "           FROM ((meter_data.dtdetails c\r\n"
						+ "             LEFT JOIN meter_data.dailydt_poweronff_event1 e ON (((c.meterno) = (e.meter_sr_number))))\r\n"
						+ "             LEFT JOIN meter_data.amilocation am ON (((c.tp_town_code) = (am.tp_towncode))))\r\n"
						+ "          WHERE (((e.event_code) = '101') and date(e.event_occ_date)=CURRENT_DATE)\r\n"
						+ "          GROUP BY c.dttpid, c.dtcapacity, am.tp_zonecode, am.tp_circlecode, am.tp_sectioncode, am.tp_towncode, am.zone, am.circle, am.section, am.town_ipds) bb ON (((aa.dttpid) = (bb.dttpid)))) ";

			}

			System.out.println(sql);
			dtpowerList = postgresMdas.createNativeQuery(sql).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtpowerList;
	}

	@Override
	public List<?> getdtcommReport(String zone, String circle, String town) {

		String sql = "select distinct on(cm.town_code)cm.town_code,cm.zone,cm.circle,am.town_ipds,am.section,am.tp_sectioncode as section_code,cm.total_dts,cm.communicating_dt,cm.not_communicating_dt from meter_data.daily_dt_comm_cmd cm LEFT JOIN meter_data.amilocation am ON(cm.town_code=am.tp_towncode) where cm.zone LIKE '"
				+ zone + "' and cm.circle LIKE '" + circle + "' and cm.town_code LIKE '" + town + "' ";

		System.out.println(sql);
		return postgresMdas.createNativeQuery(sql).getResultList();

	}
	
	@Override
	public List<?> getdtloadphasevr(String zone, String circle,String rdngmnth) {
			
		List<?> pcs=new ArrayList<>();
//		String sql = "(select distinct x.zone,x.circle,x.section,x.town_ipds,x.fdrname,x.customer_name,x.meter_number from\r\n" + 
//				"(select distinct on(ls.meter_number)ls.meter_number, mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name,sum(ls.v_r) as v_r,sum(ls.i_r) as i_r,sum(ls.i_y)as i_y,sum(ls.i_b) as i_b from meter_data.load_survey ls "
//				+ "LEFT JOIN meter_data.master_main mm ON(ls.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation am ON(mm.town_code=am.tp_towncode) "
//				+ " where ls.read_time >= to_date('"+fromdate+"', 'YYYY-MM-DD') +  INTERVAL  '24 hours' and ls.read_time <= to_date('"+todate+"', 'YYYY-MM-DD') and am.circle LIKE '"+circle+"'  "
//				+ "GROUP BY ls.meter_number,mm.zone,mm.circle,am.section,mm.fdrname,am.town_ipds,mm.customer_name)x where  x.v_r<=0 and x.i_r>0 and x.i_y>0 and x.i_b>0)";
		
		
		String sql="select * from meter_data.vr_phase where  circle LIKE '"+circle+"' and month_year='"+rdngmnth+"'  ";
		System.out.println(sql);
		try {
			 pcs=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return pcs;

	}
	
	@Override
	public List<?> getdtloadphasevy(String zone, String circle,String rdngmnth) {
			
		List<?> pcs=new ArrayList<>();
//		String sql = "(select distinct x.zone,x.circle,x.section,x.town_ipds,x.fdrname,x.customer_name,x.meter_number from\r\n" + 
//				"(select distinct on(ls.meter_number)ls.meter_number, mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name,sum(ls.v_y) as v_y,sum(ls.i_r) as i_r,sum(ls.i_y)as i_y,sum(ls.i_b) as i_b from meter_data.load_survey ls "
//				+ "LEFT JOIN meter_data.master_main mm ON(ls.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation am ON(mm.town_code=am.tp_towncode) "
//				+ " where ls.read_time >= to_date('"+fromdate+"', 'YYYY-MM-DD') +  INTERVAL  '24 hours' and ls.read_time <= to_date('"+todate+"', 'YYYY-MM-DD') and am.circle LIKE '"+circle+"'"
//				+ "GROUP BY ls.meter_number,mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name)x where x.v_y<=0 and x.i_r>0 and x.i_y>0 and x.i_b>0)";
			
		
		
		String sql="select * from meter_data.vy_phase where   circle LIKE '"+circle+"' and month_year='"+rdngmnth+"'  ";
		System.out.println(sql);
		try {
			 pcs=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pcs;

	}
	  
	
	@Override
	public List<?> getdtloadphasevb(String zone, String circle,String rdngmnth) {
			
		List<?> pcs=new ArrayList<>();
//		String sql = "(select distinct x.zone,x.circle,x.section,x.town_ipds,x.fdrname,x.customer_name,x.meter_number from\r\n" + 
//				"(select distinct on(ls.meter_number)ls.meter_number,mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name,sum(ls.v_b) as v_b,sum(ls.i_r) as i_r,sum(ls.i_y)as i_y,sum(ls.i_b) as i_b from meter_data.load_survey ls "
//				+ "LEFT JOIN meter_data.master_main mm ON(ls.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation am ON(mm.town_code=am.tp_towncode) "
//				+ " where ls.read_time >= to_date('"+fromdate+"', 'YYYY-MM-DD') +  INTERVAL  '24 hours' and ls.read_time <= to_date('"+todate+"', 'YYYY-MM-DD') and am.circle LIKE '"+circle+"' "
//				+ "GROUP BY ls.meter_number,mm.zone,mm.circle,mm.fdrname,am.section,am.town_ipds,mm.customer_name)x where x.v_b<=0 and x.i_r>0 and x.i_y>0 and x.i_b>0)";
//			
		String sql="select * from meter_data.vb_phase where  circle LIKE '"+circle+"' and month_year='"+rdngmnth+"'  ";
		System.out.println(sql);
		try {
			 pcs=postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pcs;

	}
	@Override
	public List<?> getDTDataAvailability(String region,String subdiv, String circle,String fromDate,String toDate,String division,String town,String towncode) {
		List<?> list = new ArrayList();
		try {

			String sql="(select c.zone,c.circle,c.mtrmake,c.total_mapped_meter,c.Constantly_communicated_meter,round((cast(c.Constantly_communicated_meter as DECIMAL) / c.total_mapped_meter) *100 ,2) as per FROM\r\n" + 
					"(Select a.zone,a.circle,a.mtrmake,count(a.mtrno) as total_mapped_meter,count(b.meter_number) as Constantly_communicated_meter from \r\n" + 
					"(select mm.zone,mm.circle,mm.mtrno,mm.mtrmake from meter_data.master_main mm where mm.zone LIKE '"+region+"' and mm.circle LIKE '"+circle+"')a LEFT JOIN\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"(select x.meter_number,x.mtrmake,x.constant,x.actual FROM\r\n" + 
					"(select mc.meter_number,mm.mtrmake,count(to_char(mc.last_communication,'YYYYMM')) as constant,date_part('days', (date_trunc('month', date '"+fromDate+"') + interval '1 month - 1 day')) as actual from meter_data.master_main mm LEFT JOIN meter_data.modem_communication mc ON(mm.mtrno=mc.meter_number) where to_char(last_communication,'YYYY-MM-DD') BETWEEN '"+fromDate+"' and '"+toDate+"' and mm.zone LIKE '"+region+"' and mm.circle LIKE '"+circle+"'  GROUP BY mc.meter_number,mm.mtrmake)x where x.constant=x.actual)b  ON(a.mtrno=b.meter_number) GROUP BY a.mtrmake,a.zone,a.circle)c GROUP BY c.mtrmake,c.total_mapped_meter,c.constantly_communicated_meter,c.zone,c.circle\r\n" + 
					");";
			System.out.println(sql);
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getDTDataAvailabilitybill(String region,String subdiv, String circle,String fromDate,String toDate,  String division,String town,String towncode) {
		List<?> list = new ArrayList();
		try {

			String sql="\r\n" + 
					"select c.zone,c.circle,c.mtrmake,c.total_mapped_meter,c.Constantly_communicated_meter,\r\n" + 
					"round((cast(c.Constantly_communicated_meter as DECIMAL) / c.total_mapped_meter) *100 ,2) as per FROM\r\n" + 
					"\r\n" + 
					"(Select a.zone,a.circle,a.mtrmake,count(a.mtrno) as total_mapped_meter,count(b.meter_number) as Constantly_communicated_meter from \r\n" + 
					"(select  zone,circle,mtrno,mtrmake from meter_data.master_main mm where mm.zone LIKE '"+region+"' and mm.circle LIKE '"+circle+"')a LEFT JOIN\r\n" + 
					"(select x.meter_number,x.mtrmake,x.constant FROM\r\n" + 
					"(select bh.meter_number,mm.mtrmake,count(to_char(bh.billing_date,'YYYYMM')) as constant from meter_data.master_main mm LEFT JOIN meter_data.bill_history bh ON(mm.mtrno=bh.meter_number) where to_char(bh.billing_date,'YYYY-MM-DD') BETWEEN '"+fromDate+"' and '"+toDate+"' and  mm.zone LIKE '"+region+"' and mm.circle LIKE '"+circle+"' GROUP BY bh.meter_number,mm.mtrmake)x )b  ON(a.mtrno=b.meter_number) GROUP BY a.mtrmake,a.zone,a.circle)c GROUP BY c.mtrmake,c.total_mapped_meter,c.constantly_communicated_meter,c.zone,c.circle";
			System.out.println(sql);
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public List<?> getDTDataAvailabilityconsumption(String region, String circle,String fromDate,String toDate) {
		List<?> list = new ArrayList();
		try {

			String sql="select c.zone,c.circle,c.mtrmake,c.total_mapped_meter,c.Constantly_communicated_meter,\r\n" + 
					"round((cast(c.Constantly_communicated_meter as DECIMAL) / c.total_mapped_meter) *100 ,2) as per FROM\r\n" + 
					"\r\n" + 
					"(Select a.zone,a.circle,a.mtrmake,count(a.mtrno) as total_mapped_meter,count(b.meter_number) as Constantly_communicated_meter from \r\n" + 
					"(select zone,circle,mtrno,mtrmake from meter_data.master_main mm where mm.zone LIKE '"+region+"' and mm.circle LIKE '"+circle+"')a LEFT JOIN\r\n" + 
					"(select x.meter_number,x.mtrmake,x.constant FROM\r\n" + 
					"(select bh.mtrno as meter_number,mm.mtrmake,count(to_char(bh.date,'YYYYMM')) as constant from meter_data.master_main mm LEFT JOIN meter_data.daily_consumption bh ON(mm.mtrno=bh.mtrno) where to_char(bh.date,'YYYY-MM-DD') BETWEEN '"+fromDate+"' and '"+toDate+"' and  mm.zone LIKE '"+region+"' and mm.circle LIKE '"+circle+"' GROUP BY bh.mtrno,mm.mtrmake)x )b  ON(a.mtrno=b.meter_number) GROUP BY a.mtrmake,a.zone,a.circle)c GROUP BY c.mtrmake,c.total_mapped_meter,c.constantly_communicated_meter,c.zone,c.circle\r\n" + 
					" ";
			System.out.println(sql);
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public List<?> getDTDataAvailabilityload(String zone,String subdiv, String circle,String fromDate,String toDate,String division,String town,String towncode) {
		List<?> list = new ArrayList();
		try {

			
		
			
			String qry ="select c.zone,c.circle,c.town_ipds,c.mtrmake,c.total_mapped_meter,c.Constantly_communicated_meter,round((cast(c.Constantly_communicated_meter as DECIMAL) / c.total_mapped_meter) *100 ,2) as per FROM \r\n" + 
					"					(Select a.zone,a.circle,a.town_ipds,a.mtrmake,count(a.mtrno) as total_mapped_meter,count(b.meter_number) as Constantly_communicated_meter from \r\n" + 
					"					(select  mm.zone,mm.circle,mi.town_ipds,mm.mtrno,mm.mtrmake from meter_data.master_main mm LEFT JOIN meter_data.amilocation mi ON(mm.town_code=mi.tp_towncode) where mm.circle like '"+circle+"')a LEFT JOIN \r\n" + 
					"					(select y.meter_number,y.mtrmake,y.constant,y.actual from  \r\n" + 
					"					(select x.meter_number,x.mtrmake,x.constant,sum(x.actual*48) as actual FROM \r\n" + 
					"					(select mc.meter_number,mm.mtrmake,count(to_char(mc.read_time,'YYYYMM')) as constant,date_part('days', (date_trunc('month', date '2022-09-01') + interval '1 month - 1 day')) as actual from meter_data.master_main mm LEFT JOIN meter_data.load_survey mc ON(mm.mtrno=mc.meter_number) where mc.read_time BETWEEN '"+fromDate+" 00:00:00' and '"+toDate+" 23:59:59' and mm.circle like '"+circle+"'  GROUP BY mc.meter_number,mm.mtrmake)x GROUP BY x.meter_number,x.mtrmake,x.constant)y where y.constant=y.actual)b  ON(a.mtrno=b.meter_number) GROUP BY a.mtrmake,a.zone,a.circle,a.town_ipds)c";
			
			
			/*String qry = "select distinct c.zone,c.circle,c.town_ipds,c.mtrmake,c.total_mapped_meter,c.Constantly_communicated_meter,round((cast(c.Constantly_communicated_meter as DECIMAL) / c.total_mapped_meter) *100 ,2) as per FROM\r\n" + 
					"(Select a.zone,a.circle,a.town_ipds,a.mtrmake,count(a.mtrno) as total_mapped_meter,count(b.meter_number) as Constantly_communicated_meter from\r\n" + 
					"(select distinct mm.zone,mm.circle,mi.town_ipds,mm.mtrno,mm.mtrmake from meter_data.master_main mm LEFT JOIN meter_data.amilocation mi ON(mm.town_code=mi.tp_towncode) where mm.zone like '"+zone+"' and mm.circle like '"+circle+"' and  mm.mtrmake is not null)a LEFT JOIN\r\n" + 
					"(select distinct y.meter_number,y.mtrmake,y.constant,y.actual from \r\n" + 
					"(select distinct x.meter_number,x.mtrmake,x.constant,sum(x.actual*48) as actual FROM\r\n" + 
					"(select meter_number,mtrmake,constant,actual from meter_data.load_availablity where  month_year BETWEEN '"+fromDate+" 00:00:00' and '"+toDate+" 23:59:59')x GROUP BY x.meter_number,x.mtrmake,x.constant)y where y.constant=y.actual)b  ON(a.mtrno=b.meter_number) where a.mtrmake <>'' GROUP BY a.mtrmake,a.zone,a.circle,a.town_ipds)c ";*/
			System.out.println(qry);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@Override
	public List<?> getDTDataAvailabilityinst(String zone,String subdiv, String circle,String fromDate,String toDate,  String division,String town,String towncode) {
		List<?> list = new ArrayList();
		try {

			
			String qry="select c.zone,c.circle,c.town_ipds,c.mtrmake,c.total_mapped_meter,c.Constantly_communicated_meter,round((cast(c.Constantly_communicated_meter as DECIMAL) / c.total_mapped_meter) *100 ,2) as per FROM\r\n" + 
					"(Select a.zone,a.circle,a.town_ipds,a.mtrmake,count(a.mtrno) as total_mapped_meter,count(b.meter_number) as Constantly_communicated_meter from\r\n" + 
					"(select  mm.zone,mm.circle,mi.town_ipds,mm.mtrno,mm.mtrmake from meter_data.master_main mm LEFT JOIN meter_data.amilocation mi ON(mm.town_code=mi.tp_towncode) where mm.town_code like '"+towncode+"')a LEFT JOIN\r\n" + 
					"(select y.meter_number,y.mtrmake,y.constant,y.actual from \r\n" + 
					"(select x.meter_number,x.mtrmake,x.constant,sum(x.actual*48) as actual FROM\r\n" + 
					"(select mc.meter_number,mm.mtrmake,count(to_char(mc.read_time,'YYYYMM')) as constant,date_part('days', (date_trunc('month', date '"+fromDate+"') + interval '1 month - 1 day')) as actual from meter_data.master_main mm LEFT JOIN meter_data.amiinstantaneous mc ON(mm.mtrno=mc.meter_number) where to_char(mc.read_time,'YYYY-MM-DD') BETWEEN '"+fromDate+"' and '"+toDate+"' and mm.town_code like '"+towncode+"'  GROUP BY mc.meter_number,mm.mtrmake)x GROUP BY x.meter_number,x.mtrmake,x.constant)y where y.constant=y.actual)b  ON(a.mtrno=b.meter_number) GROUP BY a.mtrmake,a.zone,a.circle,a.town_ipds)c";
			
			System.out.println(qry);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	
	  @Override public List<?> getdtcommReportmonth(String zone, String circle,
	  String town_code,String month) {
	  
	  String sql ="SELECT DISTINCT aa.zone,\r\n" + "    aa.circle,\r\n" +
	  "    aa.subdivision,\r\n" + "    aa.town_code,\r\n" +
	  "    aa.communicating_dt,\r\n" + "    aa.not_communicating_dt,\r\n" +
	  "    aa.total_dts\r\n" + "   FROM ( SELECT a.zone,\r\n" +
	  "            a.circle,\r\n" + "            a.subdivision,\r\n" +
	  "            a.town_code,\r\n" +
	  "            count(DISTINCT a.dttpid) AS total_dts,\r\n" +
	  "            sum(a.communicating_dt) AS communicating_dt,\r\n" +
	  "            sum(a.not_communicating_dt) AS not_communicating_dt\r\n" +
	  "           FROM ( SELECT DISTINCT d.dttpid,\r\n" +
	  "                    mm.zone,\r\n" + "                    mm.circle,\r\n" +
	  "                    mm.subdivision,\r\n" +
	  "                    mm.town_code,\r\n" +
	  "                    count(DISTINCT\r\n" + "                        CASE\r\n"
	  +
	  "                            WHEN ((d.dttpid)::text IN ( SELECT DISTINCT dtdetails.dttpid\r\n"
	  + "                               FROM meter_data.dtdetails\r\n" +
	  "                              WHERE ((dtdetails.meterno)::text IN ( SELECT modem_communication.meter_number\r\n"
	  +
	  "                                       FROM meter_data.modem_communication\r\n"
	  + "                                      WHERE (to_char(date, 'yyyyMM') ='"
	  +month+"'))))) THEN 1\r\n" +
	  "                            ELSE NULL::integer\r\n" +
	  "                        END) AS communicating_dt,\r\n" +
	  "                    count(DISTINCT\r\n" + "                        CASE\r\n"
	  +
	  "                            WHEN (NOT ((d.dttpid)::text IN ( SELECT DISTINCT dtdetails.dttpid\r\n"
	  + "                               FROM meter_data.dtdetails\r\n" +
	  "                              WHERE ((dtdetails.meterno)::text IN ( SELECT modem_communication.meter_number\r\n"
	  +
	  "                                       FROM meter_data.modem_communication\r\n"
	  + "                                      WHERE (to_char(date, 'yyyyMM') ='"
	  +month+"')))))) THEN 1\r\n" +
	  "                            ELSE NULL::integer\r\n" +
	  "                        END) AS not_communicating_dt\r\n" +
	  "                   FROM (meter_data.master_main mm\r\n" +
	  "                     RIGHT JOIN meter_data.dtdetails d ON (((mm.location_id)::text = (d.dttpid)::text)))\r\n"
	  +
	  "                  WHERE ((d.dttpid IS NOT NULL) AND ((d.dttpid)::text <> ''::text) AND mm.zone like '"
	  + zone + "' and mm.circle like '"+circle+"' and mm.town_code like '"
	  +town_code+"' )\r\n" +
	  "                  GROUP BY d.dttpid, mm.zone, mm.circle, mm.town_code, mm.subdivision) a\r\n"
	  + "          GROUP BY a.zone, a.circle, a.subdivision, a.town_code) aa ";
	  
	  System.out.println(sql); return
	  postgresMdas.createNativeQuery(sql).getResultList(); }
	
}
