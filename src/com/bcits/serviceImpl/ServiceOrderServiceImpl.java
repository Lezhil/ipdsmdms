package com.bcits.serviceImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.ProcessSchedular;
import com.bcits.entity.ServiceOrder;
import com.bcits.entity.ServiceOrderDetails;
import com.bcits.service.ServiceOrderService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
@Repository
public class ServiceOrderServiceImpl extends GenericServiceImpl<ServiceOrderDetails> implements ServiceOrderService {

	@Override
	public List<ServiceOrder> findALL() {
		
		 return postgresMdas.createNamedQuery("ServiceOrder.findall").getResultList();
	}

	@Override
	public List<ServiceOrder> findalldata(String zone, String circle, String division, String sdoname, String fdate,
			String tdate, HttpServletRequest req) throws ParseException
	{
		DateFormat formatter;
	      formatter = new SimpleDateFormat("yyyy-MM-dd");
	      Date d = (Date) formatter.parse(fdate);
	      java.sql.Timestamp timeStampfDate = new Timestamp(d.getTime());
	      Date d1 = (Date) formatter.parse(tdate);
	      java.sql.Timestamp timeStamptDate = new Timestamp(d1.getTime());

	      //System.out.println("timeStampfDate===>"+fdate);
	    //  System.out.println("timeStamptDate===>"+tdate);

	      
	      
           List<ServiceOrder> list=postgresMdas.createNamedQuery("ServiceOrder.findalldata",ServiceOrder.class).setParameter("circle", circle).setParameter("division", division).setParameter("subdivision", sdoname).setParameter("fdate", fdate).setParameter("tdate", tdate).getResultList();
		
          // System.err.println("THE LIST SIZE ZERO===>"+list.toString());
           
           return list;
	}

	@Override
	public List<?> getEventListforMeterList() {
		List<?> list=null;
		try {
			String sql="SELECT DISTINCT event from meter_data.event_master where event_type is not null";
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	
	
	
	@Override
	public List<Object[]> getServiceOrderforPowerTheft(String circle, String town, String cancat) {
		List<Object[]> list=null;
		
		try {
			String sql=" SELECT distinct  B.circle,B.division,B.subdivision,B.customer_name,B.accno,B.mtrno ,\n" +
					"(CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.fdrcode ELSE B.accno END) as locId,\n" +
					"(CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.fdrname ELSE customer_name END) as loc_name\n" +
					" FROM meter_data.master_main B \n" +
					" WHERE B.sdocode in \n" +
					"(SELECT distinct cast(sitecode as text) from meter_data.amilocation WHERE town_ipds like  '"+town+"') and\n" +
					"B.zone like '%' and B.circle like '"+circle+"' \n" +
					"and B.fdrcategory in ('"+cancat+"') ";
			list=postgresMdas.createNativeQuery(sql).getResultList();
			//System.err.println("sql1========="+sql);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<Object[]> getServiceOrderforMeterEvents(String zone,String circle, String town,
			String cancat, String issue) {
List<Object[]> list=null;
		
		try {
			String sql="SELECT DISTINCT B.circle,B.division,B.subdivision,B.customer_name,B.accno,B.mtrno ,\n" +
					" (CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.fdrcode ELSE B.accno END) as locId,\n" +
					"(CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.fdrname ELSE customer_name END) as loc_name\n" +
					" FROM meter_data.master_main B WHERE \n" +
					" B.mtrno in(\n" +
					" SELECT DISTINCT  meter_number FROM meter_data.events WHERE meter_number IN ( SELECT DISTINCT mtrno\n" +
					" FROM meter_data.master_main WHERE zone LIKE '"+zone+"' AND circle LIKE '"+circle+"' and town_code like '"+town+"' AND fdrcategory IN ('"+cancat+"')) AND cast(event_code as text) IN (SELECT cast(event_code as text) from meter_data.event_master WHERE event='"+issue+"') GROUP BY meter_number \n" +
					" )";
			System.err.println("sql2========="+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
			System.err.println("sql2========="+sql);
		}catch (Exception e) {
			
		}
		return list;
	}
	
//sono
	@Override
	public List<Object[]> getSOnofromIssueType(String issueType) {
       List<Object[]> list=null;
		
		try {
			String sql="SELECT DISTINCT so_number from meter_data.service_order_details  where issue= '"+issueType+"' ORDER BY  so_number ASC";
			list=postgresMdas.createNativeQuery(sql).getResultList();
			System.err.println("sql3========="+sql);
		}catch (Exception e) {
			
		}
		return list;
	}
	
	
	
	
	

	@Override
	public List<Object[]> getServiceOrderforCommunicationFail(String zone,String circle, 
			String cancat,String town) {
List<Object[]> list=null;
		
		try {
			String sql=" SELECT DISTINCT B.circle,B.division,B.subdivision,B.customer_name,B.accno,B.mtrno ,\n" +
					" (CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.location_id ELSE B.location_id END) as locId,\n" +
					"(CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.fdrname ELSE customer_name END) as loc_name\n" +
					" FROM meter_data.master_main B WHERE \n" +
					" B.mtrno in(\n" +
					" SELECT DISTINCT  meter_number FROM meter_data.modem_communication WHERE meter_number IN ( SELECT DISTINCT mtrno\n" +
					" FROM meter_data.master_main WHERE zone LIKE '"+zone+"' AND circle LIKE '"+circle+"' and town_code like '"+town+"' AND fdrcategory IN ('"+cancat+"')) GROUP BY meter_number HAVING \"max\"(date(last_communication))< CURRENT_DATE - 7\n" +
					" )";
			System.out.println(sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
			System.err.println("sql3========="+sql);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<?> getPowerTheftList() {
		List<?> list=null;
		try {
			String sql="SELECT distinct event from meter_data.event_master WHERE category='Tamper'";
			//System.err.println("sql4========="+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<?> getMeterExceptionAlarmList() {
		List<?> list=null;
		try {
			String sql="SELECT DISTINCT alarm_active from meter_data.meter_alarm"; 
					
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<Object[]> getServiceOrderforMeterException(String circle,
			String cancat, String issue,String town) {
List<Object[]> list=null;
		System.out.println("++++++++++++++");
		try {
			String sql=" SELECT DISTINCT B.circle,B.division,B.subdivision,B.customer_name,B.accno,B.mtrno ,\n" +
					" (CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.fdrcode ELSE B.accno END) as locId,\n" +
					"(CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.fdrname ELSE customer_name END) as loc_name\n" +
					" FROM meter_data.master_main B WHERE\n" +
					"  B.mtrno in(\n" +
					" SELECT DISTINCT  meter_number FROM meter_data.meter_alarm WHERE meter_number IN ( SELECT DISTINCT mtrno\n" +
					" FROM meter_data.master_main WHERE ZONE LIKE '%' AND circle LIKE '"+circle+"'  and town_code like '"+town+"'"
					+ "AND fdrcategory IN ('"+cancat+"')) AND alarm_active='"+issue+"' GROUP BY meter_number )";
			list=postgresMdas.createNativeQuery(sql).getResultList();
			System.err.println(sql);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public Object getSOnumber(String siteMonth) {
		Object res=null;
		try {
			String sql="SELECT B.rule||'_'||\n" +
					"(case \n" +
					"WHEN length(B.id)=1 then '00000'||B.id   \n" +
					"WHEN length(B.id)=2 then '0000'||B.id   \n" +
					"WHEN length(B.id)=3 then '000'||B.id   \n" +
					"WHEN length(B.id)=4 then '00'||B.id\n" +
					"WHEN length(B.id)=5 then '0'||B.id  \n" +
					"ELSE B.id \n" +
					"END) as so_number from  ( SELECT split_part(A.rule_id, '_', 1)||'_'||split_part(A.rule_id, '_', 2) as rule,\n" +
					"CAST(CAST(split_part(A.rule_id, '_', 3) as INTEGER)+1 as TEXT) as id FROM  \n" +
					"( SELECT COALESCE(max(so_number),'"+siteMonth+"_000000') as rule_id FROM meter_data.service_order_details)A  )B";
					res=postgresMdas.createNativeQuery(sql).getSingleResult();
			System.err.println("SOquery"+sql);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}

	@Override
	public List<?> getDistinctSOnumber() {
		List<?> list=null;
		try {
			String sql="SELECT DISTINCT so_number from meter_data.service_order_details ORDER BY  so_number ASC";
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<?> getFeedbackServiceOrderDetails(String circle, String zone,  String locType,
			String issue, String issueType, String sonum, String month, String status, String town) {
		List<?> list=null;
		try {
			String sql="SELECT A.*,B.subdivision FROM meter_data.service_order_details A,meter_data.amilocation B WHERE so_number like '"+sonum+"' AND issue='"+issue+"'AND sos_status like '"+status+"' AND location_type='"+locType+"' and office_id IN (SELECT cast(sitecode as text) from meter_data.amilocation WHERE circle like '"+circle+"' and zone like '"+zone+"'AND tp_towncode like '"+town+"' ) and A.office_id=cast(B.sitecode as text) AND date(so_create_date)='"+month+"'";
//			System.err.println(sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
			System.err.println(sql);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public ServiceOrderDetails getEntityById(int parseInt) {
		try {
			System.out.println(parseInt);
			return postgresMdas.createNamedQuery("ServiceOrderDetails.findById", ServiceOrderDetails.class).setParameter("id", parseInt).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
@Transactional
	@Override
	
	public List<?> getServiceSummaryDetails(String circle, String zone, String locType,
			String locId, String mtrno,String trimStr, String status,String town) {
		List<?> list=null;
		System.out.println("inside sql===");
		try {
			String sql ="SELECT A.so_number,A.so_create_date,A.issue_type,A.issue,A.sos_status,A.update_date,A.meter_sr_number,B.subdivision FROM meter_data.service_order_details A,meter_data.amilocation B WHERE A.office_id=cast(B.sitecode as text) AND  B.circle like '"+circle+"' AND B.zone like '"+zone+"' and B.tp_towncode  like '"+town+"' and A.meter_sr_number LIKE '"+mtrno+"'and date(A.so_create_date) IN ("+trimStr+") AND A.sos_status like '"+status+"' AND A.location_type like '"+locType+"' AND A.location_code LIKE '%'";
					
			System.err.println(sql);		
					
					
					/*
							 * "SELECT A.so_number,A.so_create_date,A.issue_type,A.issue,A.sos_status,A.update_date,"
							 * +
							 * "A.meter_sr_number,B.subdivision FROM meter_data.service_order_details A,meter_data.amilocation B"
							 * + " WHERE A.office_id=cast(B.sitecode as text) AND  B.circle like '"
							 * +circle+"' AND B.zone like '"+zone+"' and B.tp_town_code like '"
							 * +town+"' and A.meter_sr_number LIKE '"
							 * +mtrno+"'and date(A.so_create_date) IN ('"+trimStr+"') AND A.sos_status='"
							 * +status+"' AND A.location_type like '"+locType+"' AND A.location_code LIKE '"
							 * +locId+"'"; list=postgresMdas.createNativeQuery(sql).getResultList();
							 * 
							 */
			list=postgresMdas.createNativeQuery(sql).getResultList();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	//generateexcel
	@Override
	public List<?> generateServiceExcel(HttpServletRequest request,HttpServletResponse response, String locType, String circle, String town,String issueType,String issue,String zone) {
		List<?> list=null;
		//System.out.println("inside generate sql===");
		//System.out.println(circle);
		//System.out.println(locType);
		
		// String issueType = request.getParameter("issueType");
		 System.out.println(issueType);
		try {
			
			
			if (issueType.equals("Power theft")) {
				list = (List<Object[]>) getServiceOrderforPowerTheft(circle, town, locType);
				
			}
			if (issueType.equals("Meter Events")) {
				list = (List<Object[]>) getServiceOrderforMeterEvents( zone,circle, town, locType, issue);
				
			}
			if (issueType.equals("Meter Exception/Alarms")) {
				list = (List<Object[]>) getServiceOrderforMeterException(circle, locType, issue,town);
				
			}
			if (issueType.equals("Communication Fail")) {
				list = (List<Object[]>) getServiceOrderforCommunicationFail(zone,circle, locType,town);
				
				
				
			} /*
				 * String sql
				 * ="SELECT DISTINCT B.circle,B.division,B.subdivision,B.customer_name,B.accno,B.mtrno ,\n"
				 * +
				 * " (CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.location_id ELSE B.location_id END) as locId,\n"
				 * +
				 * "(CASE WHEN (B.fdrcategory='FEEDER METER' OR B.fdrcategory='BOUNDARY METER') THEN B.fdrname ELSE customer_name END) as loc_name\n"
				 * + " FROM meter_data.master_main B WHERE \n" + " B.mtrno in(\n" +
				 * " SELECT DISTINCT  meter_number FROM meter_data.modem_communication WHERE meter_number IN ( SELECT DISTINCT mtrno\n"
				 * + " FROM meter_data.master_main WHERE ZONE LIKE '%' AND circle LIKE '"
				 * +circle+"' AND town_code like '"+town+"'and fdrcategory IN ('"
				 * +locType+"')) GROUP BY meter_number HAVING \"max\"(date(last_communication))< CURRENT_DATE - 7\n"
				 * + " )";
				 */
			/*
			 * System.out.println(sql);
			 * list=postgresMdas.createNativeQuery(sql).getResultList();
			 */
	
	String fileName = "GenerateServiceDetails";
    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet(fileName);
    XSSFRow header  = sheet.createRow(0);
    
     CellStyle lockedCellStyle = wb.createCellStyle();
     lockedCellStyle.setLocked(true);
     CellStyle unlockedCellStyle = wb.createCellStyle();
     unlockedCellStyle.setLocked(false);
    
     XSSFCellStyle style = wb.createCellStyle();
     style.setWrapText(true);
     sheet.setColumnWidth(0, 1000);
     XSSFFont font = wb.createFont();
     font.setFontName(HSSFFont.FONT_ARIAL);
     font.setFontHeightInPoints((short)10);
     font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
     style.setFont(font);

		header.createCell(0).setCellValue("Town");
		header.createCell(1).setCellValue("Location Type");
		header.createCell(2).setCellValue("Location Identity");
		header.createCell(3).setCellValue("Location Name");
		header.createCell(4).setCellValue("MeterSrNumber");
		int count =1;
		//int cellNO=0;
        for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
  	    final Object[] values=(Object[]) iterator.next();
  		
  		XSSFRow row = sheet.createRow(count);
  		if(values[0]==null)
  		{
  			row.createCell(0).setCellValue(String.valueOf(""));
  		}else
  		{
  		row.createCell(0).setCellValue(String.valueOf(values[0]));
  		}
  		
  		if(values[1]==null)
  		{
  			row.createCell(1).setCellValue(String.valueOf(""));
  		}else
  		{
  			row.createCell(1).setCellValue(String.valueOf(values[1]));
  		}
  		
  		if(values[2]==null)
  		{
  			row.createCell(2).setCellValue(String.valueOf(""));
  		}else
  		{
  			row.createCell(2).setCellValue(String.valueOf(values[2]));
  		}
  		
  		if(values[3]==null)
  		{
  			row.createCell(3).setCellValue(String.valueOf(""));
  		}else
  		{
  			row.createCell(3).setCellValue(String.valueOf(values[3]));
  		}
  		
  		if(values[5]==null)
  		{
  			row.createCell(5).setCellValue(String.valueOf(""));
  		}else
  		{
  			row.createCell(5).setCellValue(String.valueOf(values[5]));
  		}
  		count ++;
         }
		
        FileOutputStream fileOut = new FileOutputStream(fileName);    	
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	    
	    ServletOutputStream servletOutputStream;

		servletOutputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=\"GenerateServiceDetails.xlsx"+"\"");
		FileInputStream input = new FileInputStream(fileName);
		IOUtils.copy(input, servletOutputStream);
		servletOutputStream.flush();
		servletOutputStream.close();
		
	
} catch (Exception e) {
e.printStackTrace();
}
return null;
}
	

	
}
