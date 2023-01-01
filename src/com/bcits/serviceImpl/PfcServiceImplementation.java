package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.Master;
import com.bcits.service.PfcService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Repository
public class PfcServiceImplementation extends GenericServiceImpl<Master> implements PfcService{


	private EntityManager entityManager;

	@Override
	public List<?> getpfcd7Report(String rdngmnth,String[] town) {
		List<?> pfcD7rpt=new ArrayList<>();
			
		List<?> list = null;
		String qry = null;
	String multipleTown	= getString(town);
	
	
	String isAllSeelected = null; 
	
	for(int i = 0 ; i <= town.length-1; i++ ){
	if (town[i].equalsIgnoreCase("All")){
		isAllSeelected = "yes";
		break; 
	}else {
		isAllSeelected = "no";
	}
	}
	if(isAllSeelected .equalsIgnoreCase("Yes")){
		
		qry="SELECT DISTINCT	* FROM	(\n" +
						"SELECT DISTINCT	* FROM	(SELECT	town,consumer_cnt,online_payment,total_amt_collected,online_amt \n" +
						"FROM	meter_data.pfc_d7_rpt WHERE	month_year = '"+rdngmnth+"'	AND town like '%'	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"	WHERE Y.tp_towncode = X.town ) AA";	
		System.out.println(qry);
	}else{
			String FinalString = null;
			ArrayList<String> ae = new ArrayList<>();
			String testSmaple2  = null;
			int size = town.length;
			String test = null;
			String[] splittest = null ;
			String firsttest =null;
			String finalString = null;
		
			for(int i = 0 ; i <= size-1; i++){
				int x = size-1;
				String[] splittest1 = town[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
				
			}
			
			qry= "SELECT DISTINCT	* FROM	(\n" +
						"SELECT DISTINCT	* FROM	(SELECT	town,consumer_cnt,online_payment,total_amt_collected,online_amt \n" +
						"FROM	meter_data.pfc_d7_rpt WHERE	month_year = '"+rdngmnth+"'	AND town in "+finalString+"	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"	WHERE Y.tp_towncode = X.town ) AA";
			System.out.println(qry);
	}
	/*	String sql="select town,consumer_cnt,online_payment,total_amt_collected,online_amt  "
				+ "from meter_data.pfc_d7_rpt WHERE month_year='"+rdngmnth+"' and town='"+town+"'";*/
			try {
				pfcD7rpt=postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
					e.printStackTrace();
			}
				
		return pfcD7rpt;
	}
	public String getString(String[] townCode){
		String FinalString = null;
		ArrayList<String> ae = new ArrayList<>();
		String testSmaple2  = null;
		int size = townCode.length;
		String test = null;
		String[] splittest = null ;
		String firsttest =null;
		String finalString = null;
	
		for(int i = 0 ; i <= size-1; i++){
			int x = size-1;
			String[] splittest1 = townCode[i].split("-");
			if(i== 0){
			 firsttest	= "('";
			 	test = splittest1[0];
			 	finalString = firsttest+test+"')";
			}else if (i != x){
				test = test+"','"+splittest1[0];
			}else {
				test = test+"','"+splittest1[0]+"')";
				finalString = firsttest+test;
			}
		
	}
		return finalString;
		}

	@Override
	public List<?> gettowns(String GovtSchemeId) {
		List<?> list = null;
		String qry = null;
		try{
			if(GovtSchemeId.equalsIgnoreCase("RAPDRP")){
				qry="select town_rapdrp from meter_data.amilocation where town_rapdrp is not null";
				////System.out.println(qry);
			}
			else{
				qry="select town_ipds from meter_data.amilocation where town_rapdrp is not null";
				////System.out.println(qry);
			}
			list= postgresMdas.createNativeQuery(qry).getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getpfcConnectionData(String PeriodId,String[] TownId) {
		List<?> list = null;
		String qry = null;
		String isAllSeelected = null; 
		for(int i = 0 ; i <= TownId.length-1; i++ ){
			if (TownId[i].equalsIgnoreCase("All")){
				isAllSeelected = "yes";
				break; 
			}else{
				isAllSeelected = "no";
			}
			}
			if(isAllSeelected.equalsIgnoreCase("yes")){
				
				qry= "SELECT DISTINCT	* FROM	(\n" +
						"SELECT DISTINCT	* FROM	(SELECT	town,	nc_req_opening_cnt,	nc_req_received,	tot_nc_req,	nc_req_closed,	nc_req_pending,	closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system  \n" +
						"FROM	meter_data.pfc_d2_rpt WHERE	month_year = '"+PeriodId+"'	AND town like '%'	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"	WHERE Y.tp_towncode = X.town ) AA";	
				System.out.println(qry);
			}else{
		
			String FinalString = null;
			ArrayList<String> ae = new ArrayList<>();
			String testSmaple2  = null;
			int size = TownId.length;
			String test = null;
			String[] splittest = null ;
			String firsttest =null;
			String finalString = null;
		
			for(int i = 0 ; i <= size-1; i++){
				int x = size-1;
				String[] splittest1 = TownId[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
				
			}
			
			qry= "SELECT DISTINCT	* FROM	(\n" +
						"SELECT DISTINCT	* FROM	(SELECT	town,	nc_req_opening_cnt,	nc_req_received,	tot_nc_req,	nc_req_closed,	nc_req_pending,	closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system   \n" +
						"FROM	meter_data.pfc_d2_rpt WHERE	month_year = '"+PeriodId+"'	AND town in "+finalString+"	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"	WHERE Y.tp_towncode = X.town ) AA";
			System.out.println(qry);
			}
		//	qry=" select town,nc_req_opening_cnt,nc_req_received,tot_nc_req,nc_req_closed,nc_req_pending,closed_with_in_serc from meter_data.pfc_d2_rpt where month_year='"+PeriodId+"' and town='"+TownId+"'";
			try{
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			//System.out.println(qry);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<?> getpfcD1ReportData(String PeriodId,String[] TownId) {
		List<?> list = null;
		String qry = null;
		String isAllSeelected = null; 
		
		for(int i = 0 ; i <= TownId.length-1; i++ ){
		if (TownId[i].equalsIgnoreCase("All")){
			isAllSeelected = "yes";
			break; 
		}else{
			isAllSeelected = "no";
		}
		}
		if(isAllSeelected.equalsIgnoreCase("Yes")){
			
			/*
			 * qry="SELECT DISTINCT	* FROM	(\n" +
			 * "SELECT DISTINCT	* FROM	(SELECT	town,	bill_eff,	coll_eff,	atc_loss	\n"
			 * + "FROM	meter_data.pfc_d1_rpt WHERE	month_year = '"
			 * +PeriodId+"'	AND town like '%'	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n"
			 * + "WHERE Y.tp_towncode = X.town ) AA";
			 */
			qry= "select * from (SELECT town_code,town_ipds,  round((1-(a.actualbillng_efficiency * a.actualcollection_efficiency))*100,8) as  atc_loss_percent,billing_efficiency,collection_efficiency from\r\n" + 
					"					(select town_code,town_ipds,		round((unit_billed/unit_supply),4) AS 		     actualbillng_efficiency,\r\n" + 
					"					round((amt_collected/amt_billed),4) AS actualcollection_efficiency,\r\n" + 
					"					round((unit_billed/unit_supply)*100,4) AS billing_efficiency,\r\n" + 
					"					round((amt_collected/amt_billed)*100,4) AS collection_efficiency from(Select 	town_code,town_ipds,	SUM (unit_supply) as unit_supply,\r\n" + 
					"					SUM (unit_billed) as unit_billed,\r\n" + 
					"					SUM (amt_billed) as amt_billed,\r\n" + 
					"					SUM (amt_collected) as amt_collected\r\n" + 
					"					from  meter_data.rpt_eamainfeeder_losses_02months mfl \r\n" + 
					"					left join meter_data.amilocation al on al.tp_towncode = mfl.town_code  WHERE	month_year = '"+PeriodId+"'	AND town_code like '%'  GROUP BY town_code,town_ipds)firstqry )a)f LEFT JOIN (select baseline_loss,towncode from meter_data.town_master)z on z.towncode=f.town_code\r\n" + 
					"				\r\n" + 
					"				";
					
					
					
					/*"SELECT town_code,town_ipds,  round((1-(a.actualbillng_efficiency * a.actualcollection_efficiency))*100,8) as  atc_loss_percent,billing_efficiency,collection_efficiency from\n" +
					"(select town_code,town_ipds,		round((unit_billed/unit_supply),4) AS 		     actualbillng_efficiency,\n" +
					"round((amt_collected/amt_billed),4) AS actualcollection_efficiency,\n" +
					"round((unit_billed/unit_supply)*100,4) AS billing_efficiency,\n" +
					"round((amt_collected/amt_billed)*100,4) AS collection_efficiency from(Select 	town_code,town_ipds,	SUM (unit_supply) as unit_supply,\n" +
					"SUM (unit_billed) as unit_billed,\n" +
					"SUM (amt_billed) as amt_billed,\n" +
					"SUM (amt_collected) as amt_collected\n" +
					"from  meter_data.rpt_eamainfeeder_losses_02months mfl \n" +
					"left join meter_data.amilocation al on al.tp_towncode = mfl.town_code  WHERE	month_year = '" +PeriodId+"'	AND town_code like '%'  GROUP BY town_code,town_ipds)firstqry )a";*/
				
					 
		}else{
			String FinalString = null;
			ArrayList<String> ae = new ArrayList<>();
			String testSmaple2  = null;
			int size = TownId.length;
			String test = null;
			String[] splittest = null ;
			String firsttest =null;
			String finalString = null;
		
			for(int i = 0 ; i <= size-1; i++){
				int x = size-1;
				String[] splittest1 = TownId[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
				
			}
			
			/*
			 * qry="SELECT DISTINCT	* FROM	(\n" +
			 * "SELECT DISTINCT	* FROM	(SELECT	town,	bill_eff,	coll_eff,	atc_loss	\n"
			 * + "FROM	meter_data.pfc_d1_rpt WHERE	month_year = '"
			 * +PeriodId+"'	AND town in "
			 * +finalString+"	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n"
			 * + "WHERE Y.tp_towncode = X.town ) AA";
			 */
			
			
			qry="	select * from  (SELECT town_code,town_ipds,  round((1-(a.actualbillng_efficiency * a.actualcollection_efficiency))*100,8) as  atc_loss_percent,billing_efficiency,collection_efficiency from\r\n" + 
					"					(select town_code,town_ipds,		round((unit_billed/unit_supply),4) AS 		     actualbillng_efficiency,\r\n" + 
					"					round((amt_collected/amt_billed),4) AS actualcollection_efficiency,\r\n" + 
					"					round((unit_billed/unit_supply)*100,4) AS billing_efficiency,\r\n" + 
					"					round((amt_collected/amt_billed)*100,4) AS collection_efficiency from(Select 	town_code,town_ipds,	SUM (unit_supply) as unit_supply,\r\n" + 
					"					SUM (unit_billed) as unit_billed,\r\n" + 
					"					SUM (amt_billed) as amt_billed,\r\n" + 
					"					SUM (amt_collected) as amt_collected\r\n" + 
					"					from  meter_data.rpt_eamainfeeder_losses_02months mfl \r\n" + 
					"					left join meter_data.amilocation al on al.tp_towncode = mfl.town_code  WHERE	month_year = '"+PeriodId+"'	AND town_code in "+finalString+"	  GROUP BY town_code,town_ipds)firstqry )a)f LEFT JOIN (select baseline_loss,towncode from meter_data.town_master)z on z.towncode=f.town_code\r\n" + 
					"			";
					
					
					/* "SELECT town_code,town_ipds,  round((1-(a.actualbillng_efficiency * a.actualcollection_efficiency))*100,8) as  atc_loss_percent,billing_efficiency,collection_efficiency from\n" +
					"(select town_code,town_ipds,		round((unit_billed/unit_supply),4) AS 		     actualbillng_efficiency,\n" +
					"round((amt_collected/amt_billed),4) AS actualcollection_efficiency,\n" +
					"round((unit_billed/unit_supply)*100,4) AS billing_efficiency,\n" +
					"round((amt_collected/amt_billed)*100,4) AS collection_efficiency from(Select 	town_code,town_ipds,	SUM (unit_supply) as unit_supply,\n" +
					"SUM (unit_billed) as unit_billed,\n" +
					"SUM (amt_billed) as amt_billed,\n" +
					"SUM (amt_collected) as amt_collected\n" +
					"from  meter_data.rpt_eamainfeeder_losses_02months mfl \n" +
					"left join meter_data.amilocation al on al.tp_towncode = mfl.town_code  WHERE	month_year = '"+PeriodId+"'	AND town_code in "+finalString+"	  GROUP BY town_code,town_ipds)firstqry )a";
			*/
			
			System.out.println(qry);
		}
		//	qry=" select town,nc_req_opening_cnt,nc_req_received,tot_nc_req,nc_req_closed,nc_req_pending,closed_with_in_serc from meter_data.pfc_d2_rpt where month_year='"+PeriodId+"' and town='"+TownId+"'";
		
		try{
		list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			System.out.println(qry);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public List<?> getPeriodD2() {
		List<?> list = null;
		String qry = null;
		try{
			qry="select DISTINCT month_year from meter_data.pfc_d2_rpt";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getState() {
		String list = null;
		String qry = null;
		try{
			qry="select DISTINCT state_name from meter_data.state_discom_master";
			list = (String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getDiscom() {
		String list = null;
		String qry = null;
		try{
			qry="select DISTINCT discom_name from meter_data.state_discom_master";
			list = (String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public List<?> getTown(String scheme){
		List<?> resultList =null;
		String qry = null;

		try {
			////System.out.println("town-------");
				if (scheme.equalsIgnoreCase("RAPDRP")) {
					 qry= "select DISTINCT town_rapdrp from meter_data.amilocation where town_rapdrp is not null ORDER BY town_rapdrp asc";
					 ////System.out.println("qry");
				} else {
					qry= "select DISTINCT town_ipds from meter_data.amilocation where town_ipds is not null ORDER BY town_ipds asc";
					//System.out.println("qry");
				}
				resultList= postgresMdas.createNativeQuery(qry).getResultList();	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	    return resultList;
	}

	
	
	public List<?> getPeriodD5()
	{
		String qry;
		List<?> resultList =null;
		qry="select distinct month_year from meter_data.pfc_d5_rpt ORDER BY month_year asc";
		resultList= postgresMdas.createNativeQuery(qry).getResultList();
		return resultList;
	}
	
	
	public List<?> getTablepfcD5Details(String period, String TownId[])
	{
		   List<?> list=null;
		   
		   String qry = null;
			String isAllSeelected = null; 
			
			for(int i = 0 ; i <= TownId.length-1; i++ ){
			if (TownId[i].equalsIgnoreCase("All")){
				isAllSeelected = "yes";
				break; 
			}else{
				isAllSeelected = "no";
			}
			}
			if(isAllSeelected.equalsIgnoreCase("Yes")){
				
				qry="SELECT DISTINCT	* FROM	(\n" +
						"SELECT DISTINCT	* FROM	(select town,fdr_name,total_consumers,power_off_count,power_off_duration \n" +
						"from meter_data.pfc_d5_rpt where month_year='"+period+"'	AND town like '%'	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"WHERE Y.tp_towncode = X.town ) AA";	
			}else{
				String FinalString = null;
				ArrayList<String> ae = new ArrayList<>();
				String testSmaple2  = null;
				int size = TownId.length;
				String test = null;
				String[] splittest = null ;
				String firsttest =null;
				String finalString = null;
			
				for(int i = 0 ; i <= size-1; i++){
					int x = size-1;
					String[] splittest1 = TownId[i].split("-");
					if(i== 0){
					 firsttest	= "('";
					 	test = splittest1[0];
					 	finalString = firsttest+test+"')";
					}else if (i != x){
						test = test+"','"+splittest1[0];
					}else {
						test = test+"','"+splittest1[0]+"')";
						finalString = firsttest+test;
					}
					
				}
				
				qry="SELECT DISTINCT	* FROM	(\n" +
						"SELECT DISTINCT	* FROM	(select town,fdr_name,total_consumers,power_off_count,power_off_duration \n" +
						"from meter_data.pfc_d5_rpt where month_year='"+period+"'	AND town in "+finalString+"	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"WHERE Y.tp_towncode = X.town ) AA";
			}
		   //String qry="select town,fdr_name,total_consumers,power_off_count,power_off_duration from meter_data.pfc_d5_rpt where month_year='"+period+"' and town = '"+town+"'";
			try {
				list= postgresMdas.createNativeQuery(qry).getResultList();	
			} catch (Exception e) {
				e.printStackTrace();
			}		
		   
		   ////System.out.println("list..."+list);
		   return list;	
	}
	
	public List<?> getPeriodD3()
	{
		String qry;
		List<?> resultList =null;
		qry="select distinct month_year from meter_data.pfc_d3_rpt";
		resultList= postgresMdas.createNativeQuery(qry).getResultList();
		return resultList;
	}
	
	public List<?> getComplaintDetails(String periodd3,String[] TownId)
	{
		List<?> resultlist=null;	
		String qry = null;
		String isAllSeelected = null; 
		for(int i = 0 ; i <= TownId.length-1; i++ ){
			if (TownId[i].equalsIgnoreCase("All")){
				isAllSeelected = "yes";
				break; 
			}else{
				isAllSeelected = "no";
			}
			}
		if(isAllSeelected.equalsIgnoreCase("Yes")){
			
			 qry="SELECT DISTINCT	* FROM	(	SELECT DISTINCT	* FROM"
			 		+ "	(select town,comp_opening_cnt,comp_received,tot_complains,comp_closed,(CASE WHEN comp_pending_period is null  THEN '' else comp_pending_period END)as comp_pending_period from meter_data.pfc_d3_rpt  where month_year='"+periodd3+"' and town like '%'	) X,	"
			 		+ "( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y WHERE Y.tp_towncode = X.town ) AA";	
		}else{	
			int size = TownId.length;
			String test = null;
			String firsttest =null;
			String finalString = null;
		
			for(int i = 0 ; i <= size-1; i++){
				int x = size-1;
				String[] splittest1 = TownId[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
				
			}			
			
			 qry="SELECT DISTINCT	* FROM	(	SELECT DISTINCT	* FROM"
			 		+ "	(select town,comp_opening_cnt,comp_received,tot_complains,comp_closed,(CASE WHEN comp_pending_period is null  THEN '' else comp_pending_period END)as comp_pending_period from meter_data.pfc_d3_rpt  where month_year='"+periodd3+"' and town in "+finalString+" 	) X,	"
			 		+ "( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y WHERE Y.tp_towncode = X.town ) AA";	
			
			/* qry="select town,comp_opening_cnt,comp_received,tot_complains,comp_closed,comp_pending_period from meter_data.pfc_d3_rpt "
						+ "where month_year='"+periodd3+"' and town in "+finalString+" ";	*/
		}
		try {
			resultlist= postgresMdas.createNativeQuery(qry).getResultList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultlist;
	}
	
    public List<?> getNppReportDetails(String period,String town, String scheme)
    {
    	////System.out.println("NPP Report Details.....");
		List<?> List =null;
		String qry=null;
    	
		qry="select b.town,d.discom_code,b.nc_req_opening_cnt,b.nc_req_received,b.nc_req_closed,b.closed_with_in_serc, \n" +
				"c.comp_opening_cnt,c.comp_received,c.comp_closed,c.closed_with_in_serc as serc from meter_data.pfc_d2_rpt b\n" +
				"INNER JOIN meter_data.pfc_d3_rpt c  on b.month_year=c.month_year and b.town=c.town INNER JOIN \n" +
				"meter_data.amilocation d on  b.town=d.tp_towncode where b.month_year='"+period+"' and d.tp_towncode  = '"+town+"' ";
		
		/*String isAllSeelected = null; 
		for(int i = 0 ; i <= town.length-1; i++ ){
			if (town[i].equalsIgnoreCase("All")){
				isAllSeelected = "yes";
				break; 
			}else{
				isAllSeelected = "no";
			}
			}
		if(isAllSeelected.equalsIgnoreCase("Yes")){
    	
			////System.out.println("RAPDRP-IPDS Query-------");
				
					qry="select b.town,d.discom_code,b.nc_req_opening_cnt,b.nc_req_received,b.nc_req_closed,b.closed_with_in_serc, \n" +
							"c.comp_opening_cnt,c.comp_received,c.comp_closed,c.closed_with_in_serc as serc from meter_data.pfc_d2_rpt b\n" +
							"INNER JOIN meter_data.pfc_d3_rpt c  on b.month_year=c.month_year and b.town=c.town INNER JOIN \n" +
							"meter_data.amilocation d on  b.town=d.tp_towncode where b.month_year='"+period+"' and d.tp_towncode like  '%' ";
					 ////System.out.println("qry");

		}else{
			
			int size = town.length;
			String test = null;
			String firsttest =null;
			String finalString = null;
		
			for(int i = 0 ; i <= size-1; i++){
				int x = size-1;
				String[] splittest1 = town[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
				
			}
			qry="select b.town,d.discom_code,b.nc_req_opening_cnt,b.nc_req_received,b.nc_req_closed,b.closed_with_in_serc, \n" +
					"c.comp_opening_cnt,c.comp_received,c.comp_closed,c.closed_with_in_serc as serc from meter_data.pfc_d2_rpt b\n" +
					"INNER JOIN meter_data.pfc_d3_rpt c  on b.month_year=c.month_year and b.town=c.town INNER JOIN \n" +
					"meter_data.amilocation d on  b.town=d.tp_towncode where b.month_year='"+period+"' and d.tp_towncode  in "+finalString+" ";
		}*/
		try {
			List= postgresMdas.createNativeQuery(qry).getResultList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return List;
    }



	@Override
	public List<?> getDistinctPeriod() {
		List<?> resultList =null;
        try {
				String qry= "select DISTINCT month_year from meter_data.pfc_d4_rpt";
				resultList= postgresMdas.createNativeQuery(qry).getResultList();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return resultList;
	}



	@Override
	public List<?> getPfcD4RepData(String TownId[], String period) {
		
		List<?> list = null;
		String qry = null;
		String isAllSeelected = null; 
		
		for(int i = 0 ; i <= TownId.length-1; i++ ){
		if (TownId[i].equalsIgnoreCase("All")){
			isAllSeelected = "yes";
			break; 
		}else{
			isAllSeelected = "no";
		}
		}
		if(isAllSeelected.equalsIgnoreCase("Yes")){
			
			
			
				qry= "select * from meter_data.pfc_d4_rpt where town like '%' and month_year = '"+period+"'";
			/*qry="SELECT DISTINCT	* FROM	(\n" +
					"SELECT DISTINCT	* FROM	(SELECT	town,tot_fdr,	bill_eff,	coll_eff,	atc_loss	\n" +
					"FROM	meter_data.pfc_d1_rpt WHERE	month_year = '"+PeriodId+"'	AND town like '%'	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
					"WHERE Y.tp_towncode = X.town ) AA";*/	
		}else{
			String FinalString = null;
			ArrayList<String> ae = new ArrayList<>();
			String testSmaple2  = null;
			int size = TownId.length;
			String test = null;
			String[] splittest = null ;
			String firsttest =null;
			String finalString = null;
		
			for(int i = 0 ; i <= size-1; i++){
				int x = size-1;
				String[] splittest1 = TownId[i].split("-");
				if(i== 0){
				 firsttest	= "('";
				 	test = splittest1[0];
				 	finalString = firsttest+test+"')";
				}else if (i != x){
					test = test+"','"+splittest1[0];
				}else {
					test = test+"','"+splittest1[0]+"')";
					finalString = firsttest+test;
				}
				
			}
			
			/*qry="SELECT DISTINCT	* FROM	(\n" +
					"SELECT DISTINCT	* FROM	(SELECT	town,	bill_eff,	coll_eff,	atc_loss	\n" +
					"FROM	meter_data.pfc_d1_rpt WHERE	month_year = '"+PeriodId+"'	AND town in "+finalString+"	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
					"WHERE Y.tp_towncode = X.town ) AA";*/
			qry= "select * from meter_data.pfc_d4_rpt where town in "+finalString+" and month_year = '"+period+"'";
		}
		//	qry=" select town,nc_req_opening_cnt,nc_req_received,tot_nc_req,nc_req_closed,nc_req_pending,closed_with_in_serc from meter_data.pfc_d2_rpt where month_year='"+PeriodId+"' and town='"+TownId+"'";
		
		try{
		list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			//System.out.println(qry);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
		
	/*	List<?> resultList =null;
		////System.out.println("reprt");
        try {
				String qry= "select * from meter_data.pfc_d4_rpt where town in ("+town+") and month_year = '"+period+"'";
				resultList= postgresMdas.createNativeQuery(qry).getResultList();
				//System.out.println("reprt----"+qry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return resultList;*/
	}
	
	@Override
	public void getPfcreportD1Pdf(HttpServletRequest request, HttpServletResponse response, String scheme, String town,
			String period,String state,String discom,String month,String ieperiod,String townname,String twn) {
	
		try {
			
		
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
			//	LocalDateTime sysdatetime = LocalDateTime.now();
				LocalDateTime localDateTime = LocalDateTime.now();
				String sysdatetime = dtf.format(localDateTime);	
			
		Rectangle pageSize = new Rectangle(1050, 720);
		Document document = new Document(pageSize);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer =PdfWriter.getInstance(document, baos);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);
		document.open();
		
		Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
	    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
	    PdfPTable pdf1 = new PdfPTable(1);
	    PdfPTable pdf3 = new PdfPTable(1);
	    PdfPTable pdf4 = new PdfPTable(1);
	    PdfPTable pdf5 = new PdfPTable(1);
	    pdf1.setWidthPercentage(100); 
	    pdf1.getDefaultCell().setPadding(4);
	    pdf1.getDefaultCell().setBorderWidth(0);
	    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    
	    PdfPTable pdf2 = new PdfPTable(1);
	    pdf2.setWidthPercentage(100);
	    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    Chunk glue = new Chunk(new VerticalPositionMark());
	    PdfPCell cell1 = new PdfPCell();
	    Paragraph pstart = new Paragraph();
	    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
	    pstart.setAlignment(Element.ALIGN_CENTER);
	    cell1.setBorder(Rectangle.NO_BORDER);
	    cell1.addElement(pstart);
	    pdf2.addCell(cell1);
	    document.add(pdf2);
	    
	    PdfPCell cell2 = new PdfPCell();
	    Paragraph p1 = new Paragraph();
	    p1.add(new Phrase("PFC Report-D1 ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p1.setAlignment(Element.ALIGN_CENTER);
	    cell2.addElement(p1);
	    cell2.setBorder(Rectangle.NO_BORDER);
	    pdf1.addCell(cell2);
	    document.add(pdf1);
	    
	    PdfPCell cell3 = new PdfPCell();
	    Paragraph p2 = new Paragraph();
	    p2.add(new Phrase("Town wise AT&C Loss report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p2.setAlignment(Element.ALIGN_CENTER);
	    cell3.addElement(p2);
	    cell3.setBorder(Rectangle.NO_BORDER);
	    pdf4.addCell(cell3);
	    document.add(pdf4);
	    
	    PdfPCell cell5 = new PdfPCell();
	    Paragraph p4 = new Paragraph();
	    p4.add(new Phrase("Level of Monitoring:PFC/MoP",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p4.setAlignment(Element.ALIGN_CENTER);
	    cell5.addElement(p4);
	    cell5.setBorder(Rectangle.NO_BORDER);
	    pdf5.addCell(cell5);
	    document.add(pdf5);
	    
	    PdfPCell cell4 = new PdfPCell();
	    Paragraph p3 = new Paragraph();
	    p3.add(new Phrase("Format:D1",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p3.setAlignment(Element.ALIGN_CENTER);
	    cell4.addElement(p3);
	    cell4.setBorder(Rectangle.BOTTOM);
	    pdf3.addCell(cell4);
	    document.add(pdf3);
	    
	    PdfPTable header = new PdfPTable(3);
	    header.setWidths(new int[]{1,1,1});
	    header.setWidthPercentage(100);
	    
	    PdfPCell headerCell=null;
	    headerCell = new PdfPCell(new Phrase("Scheme :"+scheme,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    
	    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    /*headerCell = new PdfPCell(new Phrase("Period :"+period,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);*/
	    
	    headerCell = new PdfPCell(new Phrase("State :"+state,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("Discom :"+discom,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("Report Month :"+month,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("current time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("Input Energy Period :"+ieperiod,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	     
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	    
	    document.add(header);	    
	    
	    String query="";
		List<Object[]> PfcreportD1=null;
		
		if (twn=="%") {
				/*
				 * query="SELECT DISTINCT	town_ipds,round(bill_eff,4) as bill_eff,round(coll_eff,4) as coll_eff,round(atc_loss,4) as atc_loss FROM	(\n"
				 * +
				 * "	SELECT DISTINCT	* FROM	(SELECT	town,	bill_eff,	coll_eff,	atc_loss	\n"
				 * + "	FROM	meter_data.pfc_d1_rpt WHERE	month_year = '"
				 * +period+"'	AND town like '%') X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n"
				 * + "	WHERE Y.tp_towncode = X.town ) AA";
				 */
			query= " select * from (SELECT town_code,town_ipds,  round((1-(a.actualbillng_efficiency * a.actualcollection_efficiency))*100,8) as  atc_loss_percent,billing_efficiency,collection_efficiency from\n" +
					"(select town_code,town_ipds,		round((unit_billed/unit_supply),4) AS 		     actualbillng_efficiency,\n" +
					"round((amt_collected/amt_billed),4) AS actualcollection_efficiency,\n" +
					"round((unit_billed/unit_supply)*100,4) AS billing_efficiency,\n" +
					"round((amt_collected/amt_billed)*100,4) AS collection_efficiency from(Select 	town_code,town_ipds,	SUM (unit_supply) as unit_supply,\n" +
					"SUM (unit_billed) as unit_billed,\n" +
					"SUM (amt_billed) as amt_billed,\n" +
					"SUM (amt_collected) as amt_collected\n" +
					"from  meter_data.rpt_eamainfeeder_losses_02months mfl \n" +
					"left join meter_data.amilocation al on al.tp_towncode = mfl.town_code  WHERE	month_year = '" +period+"'	AND town_code like '%'  GROUP BY town_code,town_ipds)firstqry )a)f LEFT JOIN (select baseline_loss,towncode from meter_data.town_master)z on z.towncode=f.town_code";
				
					 
			
		}else {
				/*
				 * query="SELECT DISTINCT	town_ipds,round(bill_eff,4) as bill_eff,round(coll_eff,4) as coll_eff,round(atc_loss,4) as atc_loss FROM	(\n"
				 * +
				 * "	SELECT DISTINCT	* FROM	(SELECT	town,	bill_eff,	coll_eff,	atc_loss	\n"
				 * + "	FROM	meter_data.pfc_d1_rpt WHERE	month_year = '"
				 * +period+"'	AND town in "
				 * +town+") X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n"
				 * + "	WHERE Y.tp_towncode = X.town ) AA";
				 */
		query= "select * from (SELECT town_code,town_ipds,  round((1-(a.actualbillng_efficiency * a.actualcollection_efficiency))*100,8) as  atc_loss_percent,billing_efficiency,collection_efficiency from\n" +
				"(select town_code,town_ipds,		round((unit_billed/unit_supply),4) AS 		     actualbillng_efficiency,\n" +
				"round((amt_collected/amt_billed),4) AS actualcollection_efficiency,\n" +
				"round((unit_billed/unit_supply)*100,4) AS billing_efficiency,\n" +
				"round((amt_collected/amt_billed)*100,4) AS collection_efficiency from(Select 	town_code,town_ipds,	SUM (unit_supply) as unit_supply,\n" +
				"SUM (unit_billed) as unit_billed,\n" +
				"SUM (amt_billed) as amt_billed,\n" +
				"SUM (amt_collected) as amt_collected\n" +
				"from  meter_data.rpt_eamainfeeder_losses_02months mfl \n" +
				"left join meter_data.amilocation al on al.tp_towncode = mfl.town_code  WHERE	month_year = '" +period+"'	AND town_code like '%'  GROUP BY town_code,town_ipds)firstqry )a)f LEFT JOIN (select baseline_loss,towncode from meter_data.town_master)z on z.towncode=f.town_code ";
		
		
		}
		
		PfcreportD1=postgresMdas.createNativeQuery(query).getResultList();
		   PdfPTable parameterTable = new PdfPTable(6);
           parameterTable.setWidths(new int[]{1,1,1,1,1,1});
           parameterTable.setWidthPercentage(100);
           PdfPCell parameterCell;
           
           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setRowspan(2);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
	    
           parameterCell = new PdfPCell(new Phrase("Town Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setRowspan(2);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("Baseline Loss %",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setRowspan(2);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           
           parameterCell = new PdfPCell(new Phrase("Cumulative Billing Efficiency,Collection Efficiency,AT&C Losses in %",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setColspan(3);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           		parameterCell = new PdfPCell(new Phrase("Billing Efficiency(%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           		parameterTable.addCell(parameterCell);
           
           		parameterCell = new PdfPCell(new Phrase("Collection Efficiency(%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           		parameterTable.addCell(parameterCell);
           
           		parameterCell = new PdfPCell(new Phrase("AT&C Loss(%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           		parameterTable.addCell(parameterCell);
           		
           		
           
           
           for (int i = 0; i < PfcreportD1.size(); i++) 
           {

        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
					 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					 parameterTable.addCell(parameterCell);
					
					 
            	Object[] obj=PfcreportD1.get(i);
            	//double obj1=obj;
            	for (int j = 0; j < obj.length; j++) 
            	{
            		if(j==0)
            		{
            			String value1=obj[0]+"";
            			parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 
						 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
											 							
						

            		}
            	}
            
           }
           document.add(parameterTable);
           
           			document.add(new Phrase("\n"));
           				LineSeparator separator = new LineSeparator();
           				separator.setPercentage(98);
           				separator.setLineColor(BaseColor.WHITE);
           				Chunk linebreak = new Chunk(separator);
           				document.add(linebreak);
		         
	       
           
           
		        	document.close();
		        	response.setHeader("Content-disposition", "attachment; filename=PFCreportD1.pdf");
		        	response.setContentType("application/pdf");
		        	ServletOutputStream outstream = response.getOutputStream();
		        	baos.writeTo(outstream);
		        	outstream.flush();
		        	outstream.close();
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void getLossCalculator(HttpServletRequest request,HttpServletResponse response,double inputEnergy,double billedEnergy,double amountBilled,double amountCollected,String anyTextId) {
		
		
		try {
			
			double[] arrayobj = new double[4];
			List<double[]> list = Arrays.asList(arrayobj);
			DecimalFormat formatter = new DecimalFormat("#.###");

			double billingEfficiency= (billedEnergy/inputEnergy)*100;
			arrayobj[0] = Double.parseDouble(formatter.format(billingEfficiency));
			double collectionEfficiency = (amountCollected/amountBilled)*100;
			arrayobj[1] = Double.parseDouble(formatter.format(collectionEfficiency));
			double be = (billedEnergy/inputEnergy);
			double ce = (amountCollected/amountBilled);
			double atAndCLoss = (1-(be*ce))*100;
			arrayobj[2] = Double.parseDouble(formatter.format(atAndCLoss));
			
						
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
			LocalDateTime localDateTime = LocalDateTime.now();
			String sysdatetime = dtf.format(localDateTime);	
			
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    PdfPTable pdf3 = new PdfPTable(1);
		    PdfPTable pdf4 = new PdfPTable(1);
		    PdfPTable pdf5 = new PdfPTable(1);
		    PdfPTable pdf6 = new PdfPTable(1);
		    PdfPTable pdf7 = new PdfPTable(1);
		    PdfPTable pdf9 = new PdfPTable(1);
		    PdfPTable pdf8 = new PdfPTable(1);


		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(4);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
		    pstart.setAlignment(Element.ALIGN_CENTER);
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    pdf2.addCell(cell1);
		    document.add(pdf2);   
		   
		    PdfPCell cell5 = new PdfPCell();
		    Paragraph p4 = new Paragraph();
		    p4.add(new Phrase("AT&C Loss Calculation",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p4.setAlignment(Element.ALIGN_CENTER);
		    cell5.addElement(p4);
		    cell5.setBorder(Rectangle.NO_BORDER);
		    pdf5.addCell(cell5);
		    document.add(pdf5);
		    
		    PdfPCell cell20 = new PdfPCell();
		    Paragraph p20 = new Paragraph();
		    p20.add(new Phrase(""+anyTextId,new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p20.setAlignment(Element.ALIGN_CENTER);
		    cell20.addElement(p20);
		    cell20.setBorder(Rectangle.NO_BORDER);
		    pdf8.addCell(cell20);		
		    document.add(pdf8);
		   
		    PdfPCell cell4 = new PdfPCell();
		    Paragraph p3 = new Paragraph();
		    p3.add(new Phrase("Date And Time :"+ sysdatetime,new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD)));
		    p3.setAlignment(Element.ALIGN_CENTER);
		    cell4.addElement(p3);
		    cell4.setBorder(Rectangle.BOTTOM);
		    pdf4.addCell(cell4);
		    document.add(pdf4);
		    
		    PdfPTable header = new PdfPTable(4);
		    header.setWidths(new int[]{1,1,1,1});
		    header.setWidthPercentage(90);
		    
		    PdfPCell headerCell=null;
		    
		    PdfPCell cell6 = new PdfPCell();
		    Paragraph p6 = new Paragraph();
		    p6.add(new Phrase("Input Energy:"+ inputEnergy,new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD)));
		    p6.setAlignment(Element.ALIGN_CENTER);
		    cell6.addElement(p6);
		   // cell6.setBorder(Rectangle.BOTTOM);
		    pdf3.addCell(cell6);
		    document.add(pdf3);
		    
		    PdfPCell cell7 = new PdfPCell();
		    Paragraph p7 = new Paragraph();
		    p7.add(new Phrase("Billed Energy:"+ billedEnergy,new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD)));
		    p7.setAlignment(Element.ALIGN_CENTER);
		    cell7.addElement(p7);
		   // cell7.setBorder(Rectangle.BOTTOM);
		    pdf6.addCell(cell7);
		    document.add(pdf6);
		    
		    PdfPCell cell8 = new PdfPCell();
		    Paragraph p8 = new Paragraph();
		    p8.add(new Phrase("Amount Billed:"+ amountBilled,new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD)));
		    p8.setAlignment(Element.ALIGN_CENTER);
		    cell8.addElement(p8);
		   // cell8.setBorder(Rectangle.BOTTOM);
		    pdf7.addCell(cell8);
		    document.add(pdf7);
		    
		    PdfPCell cell10 = new PdfPCell();
		    Paragraph p9 = new Paragraph();
		    p9.add(new Phrase("Amount Collected:"+ amountCollected,new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD)));
		    p9.setAlignment(Element.ALIGN_CENTER);
		    cell10.addElement(p9);
		   // cell9.setBorder(Rectangle.BOTTOM);
		    pdf9.addCell(cell10);
		    document.add(pdf9);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));

		    document.add(header);	
		    PdfPCell parameterCell;
			PdfPTable parameterTable = new PdfPTable(4);
			//PdfPTable parameterTable1 = new PdfPTable(5);

			
		       parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
		    
	           parameterCell = new PdfPCell(new Phrase("Billing Efficiency (%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Collection Efficiency(%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("AT&C Loss (%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	            
	           for (int i = 0; i < list.size(); i++) 
	           {

	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	double[] obj=list.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 												 											 

	            		}
	            	}
	            
	           }
	          
	           
	           
	           document.add(parameterTable);
	          // document.add(parameterTable1);
	           
      			document.add(new Phrase("\n"));
      				LineSeparator separator = new LineSeparator();
      				separator.setPercentage(98);
      				separator.setLineColor(BaseColor.WHITE);
      				Chunk linebreak = new Chunk(separator);
      				document.add(linebreak);
	         
      
      
      
	        	document.close();
	        	response.setHeader("Content-disposition", "attachment; filename=ATandC Loss Calculation.pdf");
	        	response.setContentType("application/pdf");
	        	ServletOutputStream outstream = response.getOutputStream();
	        	baos.writeTo(outstream);
	        	outstream.flush();
	        	outstream.close();

	           
	           
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
	}
	
	
	@Override
	public void getPfcreportD2Pdf(HttpServletRequest request, HttpServletResponse response, String scheme, String town,
			String period, String state, String discom, String month, String ieperiod,String townname,String twn) {
		try {
				
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
			//	LocalDateTime sysdatetime = LocalDateTime.now();
				LocalDateTime localDateTime = LocalDateTime.now();
				String sysdatetime = dtf.format(localDateTime);	
				
			Rectangle pageSize = new Rectangle(1550, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    PdfPTable pdf3 = new PdfPTable(1);
		    PdfPTable pdf4 = new PdfPTable(1);
		    PdfPTable pdf5 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(4);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
		    pstart.setAlignment(Element.ALIGN_CENTER);
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    pdf2.addCell(cell1);
		    document.add(pdf2);
		    
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("PFC Report-D2 ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.NO_BORDER);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPCell cell3 = new PdfPCell();
		    Paragraph p2 = new Paragraph();
		    p2.add(new Phrase("New Service Connection Report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p2.setAlignment(Element.ALIGN_CENTER);
		    cell3.addElement(p2);
		    cell3.setBorder(Rectangle.NO_BORDER);
		    pdf4.addCell(cell3);
		    document.add(pdf4);
		    
		    PdfPCell cell5 = new PdfPCell();
		    Paragraph p4 = new Paragraph();
		    p4.add(new Phrase("Level of Monitoring:PFC/MoP ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p4.setAlignment(Element.ALIGN_CENTER);
		    cell5.addElement(p4);
		    cell5.setBorder(Rectangle.NO_BORDER);
		    pdf5.addCell(cell5);
		    document.add(pdf5);
		    
		    PdfPCell cell4 = new PdfPCell();
		    Paragraph p3 = new Paragraph();
		    p3.add(new Phrase("Format:D2",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p3.setAlignment(Element.ALIGN_CENTER);
		    cell4.addElement(p3);
		    cell4.setBorder(Rectangle.BOTTOM);
		    pdf3.addCell(cell4);
		    document.add(pdf3);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Scheme :"+scheme,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    /*headerCell = new PdfPCell(new Phrase("Period :"+period,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);*/
		    
		    headerCell = new PdfPCell(new Phrase("State :"+state,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Discom :"+discom,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Report Month :"+month,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("current time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Input Energy Period :"+ieperiod,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);	    
		    
		    String query="";
			List<Object[]> PfcreportD2=null;
			if (twn=="%") {
				query="SELECT DISTINCT	town_ipds,nc_req_opening_cnt,nc_req_received,tot_nc_req,nc_req_closed,nc_req_pending,closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system  FROM	(\n" +
						"	SELECT DISTINCT	* FROM	(SELECT	town,	nc_req_opening_cnt,	nc_req_received,	tot_nc_req,	nc_req_closed,	nc_req_pending,	closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system  \n" +
						"	FROM	meter_data.pfc_d2_rpt WHERE	month_year = '"+period+"'	AND town like '%') X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"	WHERE Y.tp_towncode = X.town ) AA";
			}
			else {
			query="SELECT DISTINCT	town_ipds,nc_req_opening_cnt,nc_req_received,tot_nc_req,nc_req_closed,nc_req_pending,closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system  FROM	(\n" +
					"	SELECT DISTINCT	* FROM	(SELECT	town,	nc_req_opening_cnt,	nc_req_received,	tot_nc_req,	nc_req_closed,	nc_req_pending,	closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system  \n" +
					"	FROM	meter_data.pfc_d2_rpt WHERE	month_year = '"+period+"'	AND town in "+town+"	) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
					"	WHERE Y.tp_towncode = X.town ) AA";
			}

			PfcreportD2=postgresMdas.createNativeQuery(query).getResultList();
			   PdfPTable parameterTable = new PdfPTable(11);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Name of Town",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("New Connections Pending From Previous Period",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("New Connections applied in Current Period",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total New Connections Pending for Release",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Connections Released in Current Period",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           		
	           parameterCell = new PdfPCell(new Phrase("Connections Yet to be Released",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           		
	           parameterCell = new PdfPCell(new Phrase("Connections Released within SERC time limit",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Connections Released beyond SERC time limit",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase(" % of Connections released within  SERC time limit",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           parameterCell = new PdfPCell(new Phrase("Connection released by it system",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           

	           
	           
	           for (int i = 0; i < PfcreportD2.size(); i++) 
	           {

	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=PfcreportD2.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 												 											 

	            		}
	            	}
	            
	           }
	           document.add(parameterTable);
	           
	           			document.add(new Phrase("\n"));
	           				LineSeparator separator = new LineSeparator();
	           				separator.setPercentage(98);
	           				separator.setLineColor(BaseColor.WHITE);
	           				Chunk linebreak = new Chunk(separator);
	           				document.add(linebreak);
			         
		       
	           
	           
			        	document.close();
			        	response.setHeader("Content-disposition", "attachment; filename=PFCreportD2.pdf");
			        	response.setContentType("application/pdf");
			        	ServletOutputStream outstream = response.getOutputStream();
			        	baos.writeTo(outstream);
			        	outstream.flush();
			        	outstream.close();
		
			
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
	}
	
	@Override
	public void getPfcreportD3Pdf(HttpServletRequest request, HttpServletResponse response, String scheme, String town,
			String period, String state, String discom, String month, String ieperiod,String townname,String twn) {
		try {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
			//	LocalDateTime sysdatetime = LocalDateTime.now();
				LocalDateTime localDateTime = LocalDateTime.now();
				String sysdatetime = dtf.format(localDateTime);	
				
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    PdfPTable pdf3 = new PdfPTable(1);
		    PdfPTable pdf4 = new PdfPTable(1);
		    PdfPTable pdf5 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(4);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
		    pstart.setAlignment(Element.ALIGN_CENTER);
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    pdf2.addCell(cell1);
		    document.add(pdf2);
		    
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("PFC Report-D3 ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.NO_BORDER);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPCell cell3 = new PdfPCell();
		    Paragraph p2 = new Paragraph();
		    p2.add(new Phrase("Consumer Complaints redressal report ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p2.setAlignment(Element.ALIGN_CENTER);
		    cell3.addElement(p2);
		    cell3.setBorder(Rectangle.NO_BORDER);
		    pdf4.addCell(cell3);
		    document.add(pdf4);
		    
		    PdfPCell cell5 = new PdfPCell();
		    Paragraph p4 = new Paragraph();
		    p4.add(new Phrase("Level of Monitoring:PFC/MOP",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p4.setAlignment(Element.ALIGN_CENTER);
		    cell5.addElement(p4);
		    cell5.setBorder(Rectangle.NO_BORDER);
		    pdf5.addCell(cell5);
		    document.add(pdf5);
		    
		    PdfPCell cell4 = new PdfPCell();
		    Paragraph p3 = new Paragraph();
		    p3.add(new Phrase("Format:D3",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p3.setAlignment(Element.ALIGN_CENTER);
		    cell4.addElement(p3);
		    cell4.setBorder(Rectangle.BOTTOM);
		    pdf3.addCell(cell4);
		    document.add(pdf3);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Scheme :"+scheme,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    /*headerCell = new PdfPCell(new Phrase("Period :"+period,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);*/
		    
		    headerCell = new PdfPCell(new Phrase("State :"+state,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Discom :"+discom,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Report Month :"+month,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("current time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Input Energy Period :"+ieperiod,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);	    
		    
		    String query="";
			List<Object[]> PfcreportD3=null;
			if (twn=="%") {
			query="SELECT DISTINCT	town_ipds,comp_opening_cnt,comp_received,tot_complains,comp_closed,comp_pending_period FROM	(	SELECT DISTINCT	* FROM\n" +
						"			 	(select town,comp_opening_cnt,comp_received,tot_complains,comp_closed,comp_pending_period from meter_data.pfc_d3_rpt  where month_year='"+period+"' and town like '%') X,	\n" +
						"			 	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y WHERE Y.tp_towncode = X.town ) AA";
			}
			else {
			query="SELECT DISTINCT	town_ipds,comp_opening_cnt,comp_received,tot_complains,comp_closed,comp_pending_period FROM	(	SELECT DISTINCT	* FROM\n" +
					"			 	(select town,comp_opening_cnt,comp_received,tot_complains,comp_closed,comp_pending_period from meter_data.pfc_d3_rpt  where month_year='"+period+"' and town in "+town+"	) X,	\n" +
					"			 	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y WHERE Y.tp_towncode = X.town ) AA";
			}
			PfcreportD3=postgresMdas.createNativeQuery(query).getResultList();
			   PdfPTable parameterTable = new PdfPTable(7);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Name of Town",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Complaints Pending From Previous Period",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Complaints Registered in Current Period",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Pending Complaints",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Complaints Closed",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           		
	           parameterCell = new PdfPCell(new Phrase("Complaints Pending Period(Average)HH:MM",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);        		
	           
	           
	           for (int i = 0; i < PfcreportD3.size(); i++) 
	           {

	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=PfcreportD3.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
				 											 

	            		}
	            	}
	            
	           }
	           document.add(parameterTable);
	           
	           			document.add(new Phrase("\n"));
	           				LineSeparator separator = new LineSeparator();
	           				separator.setPercentage(98);
	           				separator.setLineColor(BaseColor.WHITE);
	           				Chunk linebreak = new Chunk(separator);
	           				document.add(linebreak);
			         
		       
	           
	           
			        	document.close();
			        	response.setHeader("Content-disposition", "attachment; filename=PFCreportD3.pdf");
			        	response.setContentType("application/pdf");
			        	ServletOutputStream outstream = response.getOutputStream();
			        	baos.writeTo(outstream);
			        	outstream.flush();
			        	outstream.close();
		
			
			
		
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
	}
	
	
	@Override
	public void getPfcreportD4Pdf(HttpServletRequest request, HttpServletResponse response, String scheme, String town,
			String period, String state, String discom, String month, String ieperiod,String townname,String twn) {
		try {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
			//	LocalDateTime sysdatetime = LocalDateTime.now();
				LocalDateTime localDateTime = LocalDateTime.now();
				String sysdatetime = dtf.format(localDateTime);	
				
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    PdfPTable pdf3 = new PdfPTable(1);
		    PdfPTable pdf4 = new PdfPTable(1);
		    PdfPTable pdf5 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(4);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
		    pstart.setAlignment(Element.ALIGN_CENTER);
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    pdf2.addCell(cell1);
		    document.add(pdf2);
		    
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("PFC Report-D4",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.NO_BORDER);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPCell cell3 = new PdfPCell();
		    Paragraph p2 = new Paragraph();
		    p2.add(new Phrase("Feeder wise AT & C loss report (10% worst feeder)",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p2.setAlignment(Element.ALIGN_CENTER);
		    cell3.addElement(p2);
		    cell3.setBorder(Rectangle.NO_BORDER);
		    pdf4.addCell(cell3);
		    document.add(pdf4);
		    
		    PdfPCell cell5 = new PdfPCell();
		    Paragraph p4 = new Paragraph();
		    p4.add(new Phrase("Level of Monitoring:PFC/MoP",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p4.setAlignment(Element.ALIGN_CENTER);
		    cell5.addElement(p4);
		    cell5.setBorder(Rectangle.NO_BORDER);
		    pdf5.addCell(cell5);
		    document.add(pdf5);
		    
		    PdfPCell cell4 = new PdfPCell();
		    Paragraph p3 = new Paragraph();
		    p3.add(new Phrase("Format:D4",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p3.setAlignment(Element.ALIGN_CENTER);
		    cell4.addElement(p3);
		    cell4.setBorder(Rectangle.BOTTOM);
		    pdf3.addCell(cell4);
		    document.add(pdf3);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Scheme :"+scheme,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    /*headerCell = new PdfPCell(new Phrase("Period :"+period,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);*/
		    
		    headerCell = new PdfPCell(new Phrase("State :"+state,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Discom :"+discom,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Report Month :"+month,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("current time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Input Energy Period :"+ieperiod,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);	    
		    
		    String query="";
			List<Object[]> PfcreportD4=null;
			if (twn=="%") {
				query="select town,tot_fdr,fdr1_name,fdr1_be,fdr1_ce,fdr1_atc from meter_data.pfc_d4_rpt where town like '%' and month_year = '"+period+"'";
			}else {
			query="select town,tot_fdr,fdr1_name,fdr1_be,fdr1_ce,fdr1_atc from meter_data.pfc_d4_rpt where town in "+town+" and month_year = '"+period+"'";
			}
			PfcreportD4=postgresMdas.createNativeQuery(query).getResultList();
			   PdfPTable parameterTable = new PdfPTable(7);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setRowspan(2);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Town Code",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setRowspan(2);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Feeder",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setRowspan(2);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Name of Feeder",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setRowspan(2);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Cumulative Billing Efficiency, Collection Efficiency & AT&C Losses in %",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setColspan(3);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           		parameterCell = new PdfPCell(new Phrase("Billing Efficiency(%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           		parameterTable.addCell(parameterCell);
	           
	           		parameterCell = new PdfPCell(new Phrase("Collection Efficiency(%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           		parameterTable.addCell(parameterCell);
	           
	           		parameterCell = new PdfPCell(new Phrase("AT&C Loss(%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           		parameterTable.addCell(parameterCell);        		
	           
	           
	           for (int i = 0; i < PfcreportD4.size(); i++) 
	           {

	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=PfcreportD4.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
				 											 

	            		}
	            	}
	            
	           }
	           document.add(parameterTable);
	           
	           			document.add(new Phrase("\n"));
	           				LineSeparator separator = new LineSeparator();
	           				separator.setPercentage(98);
	           				separator.setLineColor(BaseColor.WHITE);
	           				Chunk linebreak = new Chunk(separator);
	           				document.add(linebreak);
			         
		       
	           
	           
			        	document.close();
			        	response.setHeader("Content-disposition", "attachment; filename=PFCreportD4.pdf");
			        	response.setContentType("application/pdf");
			        	ServletOutputStream outstream = response.getOutputStream();
			        	baos.writeTo(outstream);
			        	outstream.flush();
			        	outstream.close();
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
	}
	
	
	@Override
	public void getPfcreportD5Pdf(HttpServletRequest request, HttpServletResponse response, String scheme, String town,
			String period, String state, String discom, String month, String ieperiod,String townname,String twn) {
			try {
		
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
				//	LocalDateTime sysdatetime = LocalDateTime.now();
					LocalDateTime localDateTime = LocalDateTime.now();
					String sysdatetime = dtf.format(localDateTime);	
					
				Rectangle pageSize = new Rectangle(1050, 720);
				Document document = new Document(pageSize);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter writer =PdfWriter.getInstance(document, baos);
				HeaderFooterPageEvent event = new HeaderFooterPageEvent();
				writer.setPageEvent(event);
				document.open();
				
				Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
			    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
			    PdfPTable pdf1 = new PdfPTable(1);
			    PdfPTable pdf3 = new PdfPTable(1);
			    PdfPTable pdf4 = new PdfPTable(1);
			    PdfPTable pdf5 = new PdfPTable(1);
			    pdf1.setWidthPercentage(100); 
			    pdf1.getDefaultCell().setPadding(4);
			    pdf1.getDefaultCell().setBorderWidth(0);
			    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			    
			    PdfPTable pdf2 = new PdfPTable(1);
			    pdf2.setWidthPercentage(100);
			    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			    Chunk glue = new Chunk(new VerticalPositionMark());
			    PdfPCell cell1 = new PdfPCell();
			    Paragraph pstart = new Paragraph();
			    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
			    pstart.setAlignment(Element.ALIGN_CENTER);
			    cell1.setBorder(Rectangle.NO_BORDER);
			    cell1.addElement(pstart);
			    pdf2.addCell(cell1);
			    document.add(pdf2);
			    
			    PdfPCell cell2 = new PdfPCell();
			    Paragraph p1 = new Paragraph();
			    p1.add(new Phrase("PFC Report-D5 ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
			    p1.setAlignment(Element.ALIGN_CENTER);
			    cell2.addElement(p1);
			    cell2.setBorder(Rectangle.NO_BORDER);
			    pdf1.addCell(cell2);
			    document.add(pdf1);
			    
			    PdfPCell cell3 = new PdfPCell();
			    Paragraph p2 = new Paragraph();
			    p2.add(new Phrase("SAIDI-SAIF I Report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
			    p2.setAlignment(Element.ALIGN_CENTER);
			    cell3.addElement(p2);
			    cell3.setBorder(Rectangle.NO_BORDER);
			    pdf4.addCell(cell3);
			    document.add(pdf4);
			    
			    PdfPCell cell5 = new PdfPCell();
			    Paragraph p4 = new Paragraph();
			    p4.add(new Phrase("Level of Monitoring:PFC/MOP",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
			    p4.setAlignment(Element.ALIGN_CENTER);
			    cell5.addElement(p4);
			    cell5.setBorder(Rectangle.NO_BORDER);
			    pdf5.addCell(cell5);
			    document.add(pdf5);
			    
			    PdfPCell cell4 = new PdfPCell();
			    Paragraph p3 = new Paragraph();
			    p3.add(new Phrase("Format:D5",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
			    p3.setAlignment(Element.ALIGN_CENTER);
			    cell4.addElement(p3);
			    cell4.setBorder(Rectangle.BOTTOM);
			    pdf3.addCell(cell4);
			    document.add(pdf3);
			    
			    PdfPTable header = new PdfPTable(3);
			    header.setWidths(new int[]{1,1,1});
			    header.setWidthPercentage(100);
			    
			    PdfPCell headerCell=null;
			    headerCell = new PdfPCell(new Phrase("Scheme :"+scheme,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
			    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    headerCell.setFixedHeight(20f);
			    headerCell.setBorder(PdfPCell.NO_BORDER);
			    header.addCell(headerCell);
			    
			    
			    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
			    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    headerCell.setFixedHeight(20f);
			    headerCell.setBorder(PdfPCell.NO_BORDER);
			    header.addCell(headerCell);
			    
			    /*headerCell = new PdfPCell(new Phrase("Period :"+period,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
			    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    headerCell.setFixedHeight(20f);
			    headerCell.setBorder(PdfPCell.NO_BORDER);
			    header.addCell(headerCell);*/
			    
			    headerCell = new PdfPCell(new Phrase("State :"+state,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
			    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    headerCell.setFixedHeight(20f);
			    headerCell.setBorder(PdfPCell.NO_BORDER);
			    header.addCell(headerCell);
			    
			    headerCell = new PdfPCell(new Phrase("Discom :"+discom,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
			    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    headerCell.setFixedHeight(20f);
			    headerCell.setBorder(PdfPCell.NO_BORDER);
			    header.addCell(headerCell);
			    
			    headerCell = new PdfPCell(new Phrase("Report Month :"+month,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
			    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    headerCell.setFixedHeight(20f);
			    headerCell.setBorder(PdfPCell.NO_BORDER);
			    header.addCell(headerCell);
			    
			    headerCell = new PdfPCell(new Phrase("current time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
			    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    headerCell.setFixedHeight(20f);
			    headerCell.setBorder(PdfPCell.NO_BORDER);
			    header.addCell(headerCell);
			    
			    headerCell = new PdfPCell(new Phrase("Input Energy Period :"+ieperiod,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
			    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    headerCell.setFixedHeight(20f);
			    headerCell.setBorder(PdfPCell.NO_BORDER);
			    header.addCell(headerCell);
			    
			    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			    
			    document.add(header);	    
			    
			    String query="";
				List<Object[]> PfcreportD5=null;
				if (twn=="%") {
					query="SELECT DISTINCT	town_ipds,fdr_name,total_consumers,power_off_count,power_off_duration FROM	(\n" +
							"						SELECT DISTINCT	* FROM	(select town,fdr_name,total_consumers,power_off_count,power_off_duration \n" +
							"						from meter_data.pfc_d5_rpt where month_year='"+period+"' AND town like '%') X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
							"						WHERE Y.tp_towncode = X.town ) AA";
				}else {
				query="SELECT DISTINCT	town_ipds,fdr_name,total_consumers,power_off_count,power_off_duration FROM	(\n" +
						"						SELECT DISTINCT	* FROM	(select town,fdr_name,total_consumers,power_off_count,power_off_duration \n" +
						"						from meter_data.pfc_d5_rpt where month_year='"+period+"' AND town in "+town+" ) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"						WHERE Y.tp_towncode = X.town ) AA";
				}
				PfcreportD5=postgresMdas.createNativeQuery(query).getResultList();
				System.out.println("qry is"+query);
				   PdfPTable parameterTable = new PdfPTable(6);
		           parameterTable.setWidths(new int[]{1,1,1,1,1,1});
		           parameterTable.setWidthPercentage(100);
		           PdfPCell parameterCell;
		           
		           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		           parameterCell.setFixedHeight(30f);
		           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		           parameterTable.addCell(parameterCell);
			    
		           parameterCell = new PdfPCell(new Phrase("Name of Town",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		           parameterCell.setFixedHeight(30f);
		           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		           parameterTable.addCell(parameterCell);
		           
		           parameterCell = new PdfPCell(new Phrase("Name of Feeder",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		           parameterCell.setFixedHeight(30f);
		           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		           parameterTable.addCell(parameterCell);
		           
		           parameterCell = new PdfPCell(new Phrase("Number of Consumers",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		           parameterCell.setFixedHeight(30f);
		           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		           parameterTable.addCell(parameterCell);
		           
		           parameterCell = new PdfPCell(new Phrase("Number of Outages(Nos.)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		           parameterCell.setFixedHeight(30f);
		           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		           parameterTable.addCell(parameterCell);
		           
		           parameterCell = new PdfPCell(new Phrase("Duration of Outages(Sec)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		           parameterCell.setFixedHeight(30f);
		           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		           parameterTable.addCell(parameterCell);        		
		           
		           
		           for (int i = 0; i < PfcreportD5.size(); i++) 
		           {

		        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterTable.addCell(parameterCell);
							
							 
		            	Object[] obj=PfcreportD5.get(i);
		            	for (int j = 0; j < obj.length; j++) 
		            	{
		            		if(j==0)
		            		{
		            			String value1=obj[0]+"";
		            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								 parameterCell.setFixedHeight(30f);
								 parameterTable.addCell(parameterCell);
								 
								 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								 parameterCell.setFixedHeight(30f);
								 parameterTable.addCell(parameterCell);
								 
								 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								 parameterCell.setFixedHeight(30f);
								 parameterTable.addCell(parameterCell);
								 
								 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								 parameterCell.setFixedHeight(30f);
								 parameterTable.addCell(parameterCell);
								 
								 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								 parameterCell.setFixedHeight(30f);
								 parameterTable.addCell(parameterCell);
								 
					 											 

		            		}
		            	}
		            
		           }
		           document.add(parameterTable);
		           
		           			document.add(new Phrase("\n"));
		           				LineSeparator separator = new LineSeparator();
		           				separator.setPercentage(98);
		           				separator.setLineColor(BaseColor.WHITE);
		           				Chunk linebreak = new Chunk(separator);
		           				document.add(linebreak);
				         
			       
		           
		           
				        	document.close();
				        	response.setHeader("Content-disposition", "attachment; filename=PFCreportD5.pdf");
				        	response.setContentType("application/pdf");
				        	ServletOutputStream outstream = response.getOutputStream();
				        	baos.writeTo(outstream);
				        	outstream.flush();
				        	outstream.close();
			
				
			} catch (Exception e) {
				e.printStackTrace();		
			}		
	}
	
	
	@Override
	public void getPfcreportD6Pdf(HttpServletRequest request, HttpServletResponse response, String scheme, String town,
			String period, String state, String discom, String month, String ieperiod,String townname,String twn) {
		try {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
			//	LocalDateTime sysdatetime = LocalDateTime.now();
				LocalDateTime localDateTime = LocalDateTime.now();
				String sysdatetime = dtf.format(localDateTime);	
				
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    PdfPTable pdf3 = new PdfPTable(1);
		    PdfPTable pdf4 = new PdfPTable(1);
		    PdfPTable pdf5 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(4);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
		    pstart.setAlignment(Element.ALIGN_CENTER);
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    pdf2.addCell(cell1);
		    document.add(pdf2);
		    
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("PFC Report-D6 ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.NO_BORDER);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPCell cell3 = new PdfPCell();
		    Paragraph p2 = new Paragraph();
		    p2.add(new Phrase("Feeder Meter Communication Status Report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p2.setAlignment(Element.ALIGN_CENTER);
		    cell3.addElement(p2);
		    cell3.setBorder(Rectangle.NO_BORDER);
		    pdf4.addCell(cell3);
		    document.add(pdf4);
		    
		    PdfPCell cell5 = new PdfPCell();
		    Paragraph p4 = new Paragraph();
		    p4.add(new Phrase("Level of Monitoring:PFC/MOP",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p4.setAlignment(Element.ALIGN_CENTER);
		    cell5.addElement(p4);
		    cell5.setBorder(Rectangle.NO_BORDER);
		    pdf5.addCell(cell5);
		    document.add(pdf5);
		    
		    PdfPCell cell4 = new PdfPCell();
		    Paragraph p3 = new Paragraph();
		    p3.add(new Phrase("Format:D6",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p3.setAlignment(Element.ALIGN_CENTER);
		    cell4.addElement(p3);
		    cell4.setBorder(Rectangle.BOTTOM);
		    pdf3.addCell(cell4);
		    document.add(pdf3);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Scheme :"+scheme,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    /*headerCell = new PdfPCell(new Phrase("Period :"+period,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);*/
		    
		    headerCell = new PdfPCell(new Phrase("State :"+state,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Discom :"+discom,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Report Month :"+month,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("current time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Input Energy Period :"+ieperiod,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);	    
		    
		    String query="";
			List<Object[]> PfcreportD6=null;
			if (twn=="%") {
				query="SELECT DISTINCT	town_ipds,fdr_name,meter_sr_number,meter_communicating FROM	(\n" +
						"					SELECT DISTINCT	* FROM	(select rec_id,town,fdr_name,meter_sr_number,meter_communicating \n" +
						"					from meter_data.pfc_d5_rpt where month_year='"+period+"' 	AND town like '%' ) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"					WHERE Y.tp_towncode = X.town ) AA";
				
				//System.out.println("twn..."+query);
			}else {
			query="SELECT DISTINCT	town_ipds,fdr_name,meter_sr_number,meter_communicating FROM	(\n" +
					"					SELECT DISTINCT	* FROM	(select rec_id,town,fdr_name,meter_sr_number,meter_communicating \n" +
					"					from meter_data.pfc_d5_rpt where month_year='"+period+"' 	AND town in "+town+" ) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
					"					WHERE Y.tp_towncode = X.town ) AA";
			//System.out.println("twn2..."+query);
			}
			PfcreportD6=postgresMdas.createNativeQuery(query).getResultList();
			   PdfPTable parameterTable = new PdfPTable(5);
	           parameterTable.setWidths(new int[]{1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Name of Town",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder Meters Communicating With Data Center",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);        		
	           
	           
	           for (int i = 0; i < PfcreportD6.size(); i++) 
	           {

	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=PfcreportD6.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
				 											 

	            		}
	            	}
	            
	           }
	           document.add(parameterTable);
	           
	           			document.add(new Phrase("\n"));
	           				LineSeparator separator = new LineSeparator();
	           				separator.setPercentage(98);
	           				separator.setLineColor(BaseColor.WHITE);
	           				Chunk linebreak = new Chunk(separator);
	           				document.add(linebreak);
			         
		       
	           
	           
			        	document.close();
			        	response.setHeader("Content-disposition", "attachment; filename=PFCreportD6.pdf");
			        	response.setContentType("application/pdf");
			        	ServletOutputStream outstream = response.getOutputStream();
			        	baos.writeTo(outstream);
			        	outstream.flush();
			        	outstream.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
	}
	
	@Override
	public void getPfcreportD7Pdf(HttpServletRequest request, HttpServletResponse response, String scheme, String town,
			String period, String state, String discom, String month, String ieperiod,String townname,String twn) {
		
	try {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
	//	LocalDateTime sysdatetime = LocalDateTime.now();
		LocalDateTime localDateTime = LocalDateTime.now();
		String sysdatetime = dtf.format(localDateTime);	
			
		Rectangle pageSize = new Rectangle(1050, 720);
		Document document = new Document(pageSize);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer =PdfWriter.getInstance(document, baos);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);
		document.open();
		
		Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
	    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
	    PdfPTable pdf1 = new PdfPTable(1);
	    PdfPTable pdf3 = new PdfPTable(1);
	    PdfPTable pdf4 = new PdfPTable(1);
	    PdfPTable pdf5 = new PdfPTable(1);
	    pdf1.setWidthPercentage(100); 
	    pdf1.getDefaultCell().setPadding(4);
	    pdf1.getDefaultCell().setBorderWidth(0);
	    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    
	    PdfPTable pdf2 = new PdfPTable(1);
	    pdf2.setWidthPercentage(100);
	    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    Chunk glue = new Chunk(new VerticalPositionMark());
	    PdfPCell cell1 = new PdfPCell();
	    Paragraph pstart = new Paragraph();
	    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
	    pstart.setAlignment(Element.ALIGN_CENTER);
	    cell1.setBorder(Rectangle.NO_BORDER);
	    cell1.addElement(pstart);
	    pdf2.addCell(cell1);
	    document.add(pdf2);
	    
	    PdfPCell cell2 = new PdfPCell();
	    Paragraph p1 = new Paragraph();
	    p1.add(new Phrase("PFC Report-D7 ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p1.setAlignment(Element.ALIGN_CENTER);
	    cell2.addElement(p1);
	    cell2.setBorder(Rectangle.NO_BORDER);
	    pdf1.addCell(cell2);
	    document.add(pdf1);
	    
	    PdfPCell cell3 = new PdfPCell();
	    Paragraph p2 = new Paragraph();
	    p2.add(new Phrase("E-Payment report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p2.setAlignment(Element.ALIGN_CENTER);
	    cell3.addElement(p2);
	    cell3.setBorder(Rectangle.NO_BORDER);
	    pdf4.addCell(cell3);
	    document.add(pdf4);
	    
	    PdfPCell cell5 = new PdfPCell();
	    Paragraph p4 = new Paragraph();
	    p4.add(new Phrase("Level of Monitoring:PFC/MOP",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p4.setAlignment(Element.ALIGN_CENTER);
	    cell5.addElement(p4);
	    cell5.setBorder(Rectangle.NO_BORDER);
	    pdf5.addCell(cell5);
	    document.add(pdf5);
	    
	    PdfPCell cell4 = new PdfPCell();
	    Paragraph p3 = new Paragraph();
	    p3.add(new Phrase("Format:D7",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
	    p3.setAlignment(Element.ALIGN_CENTER);
	    cell4.addElement(p3);
	    cell4.setBorder(Rectangle.BOTTOM);
	    pdf3.addCell(cell4);
	    document.add(pdf3);
	    
	    PdfPTable header = new PdfPTable(3);
	    header.setWidths(new int[]{1,1,1});
	    header.setWidthPercentage(100);
	    
	    PdfPCell headerCell=null;
	    headerCell = new PdfPCell(new Phrase("Scheme :"+scheme,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    
	    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    /*headerCell = new PdfPCell(new Phrase("Period :"+period,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);*/
	    
	    headerCell = new PdfPCell(new Phrase("State :"+state,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("Discom :"+discom,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("Report Month :"+month,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("current time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    headerCell = new PdfPCell(new Phrase("Input Energy Period :"+ieperiod,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    headerCell.setFixedHeight(20f);
	    headerCell.setBorder(PdfPCell.NO_BORDER);
	    header.addCell(headerCell);
	    
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	    
	    document.add(header);	    
	    
	    String query="";
		List<Object[]> PfcreportD7=null;
		if (twn=="%") {
			query="SELECT DISTINCT	town_ipds,consumer_cnt,online_payment,total_amt_collected,online_amt FROM	(\n" +
					"						SELECT DISTINCT	* FROM	(SELECT	town,consumer_cnt,online_payment,total_amt_collected,online_amt \n" +
					"						FROM	meter_data.pfc_d7_rpt WHERE	month_year = '"+period+"'	AND town like '%') X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
					"							WHERE Y.tp_towncode = X.town ) AA";
		}else {
		query="SELECT DISTINCT	town_ipds,consumer_cnt,online_payment,total_amt_collected,online_amt FROM	(\n" +
				"						SELECT DISTINCT	* FROM	(SELECT	town,consumer_cnt,online_payment,total_amt_collected,online_amt \n" +
				"						FROM	meter_data.pfc_d7_rpt WHERE	month_year = '"+period+"'	AND town in "+town+") X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
				"							WHERE Y.tp_towncode = X.town ) AA";
		}
		PfcreportD7=postgresMdas.createNativeQuery(query).getResultList();
		   PdfPTable parameterTable = new PdfPTable(6);
           parameterTable.setWidths(new int[]{1,1,1,1,1,1});
           parameterTable.setWidthPercentage(100);
           PdfPCell parameterCell;
           
           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
	    
           parameterCell = new PdfPCell(new Phrase("Name of the Town",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("Total Consumers(Nos.)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("Total Consumers Using E-Payment(Nos)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           parameterCell = new PdfPCell(new Phrase("Total Collection (Rs.in Lac)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);        		
           
           parameterCell = new PdfPCell(new Phrase("Collection Through E-Payment (Rs.in Lac)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
           parameterCell.setFixedHeight(30f);
           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           parameterTable.addCell(parameterCell);
           
           for (int i = 0; i < PfcreportD7.size(); i++) 
           {

        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
					 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					 parameterTable.addCell(parameterCell);
					
					 
            	Object[] obj=PfcreportD7.get(i);
            	for (int j = 0; j < obj.length; j++) 
            	{
            		if(j==0)
            		{
            			String value1=obj[0]+"";
            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
						 
						 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterCell.setFixedHeight(30f);
						 parameterTable.addCell(parameterCell);
			 											 

            		}
            	}
            
           }
           document.add(parameterTable);
           
           			document.add(new Phrase("\n"));
           				LineSeparator separator = new LineSeparator();
           				separator.setPercentage(98);
           				separator.setLineColor(BaseColor.WHITE);
           				Chunk linebreak = new Chunk(separator);
           				document.add(linebreak);
		         
	       
           
           
		        	document.close();
		        	response.setHeader("Content-disposition", "attachment; filename=PFCreportD7.pdf");
		        	response.setContentType("application/pdf");
		        	ServletOutputStream outstream = response.getOutputStream();
		        	baos.writeTo(outstream);
		        	outstream.flush();
		        	outstream.close();
	
		
	} catch (Exception e) {
		e.printStackTrace();		
	}
		
	}
	
	
	@Override
	public void getNPPReportConsumerPdf(HttpServletRequest request, HttpServletResponse response, String scheme,
			String town, String period, String state, String discom, String month, String ieperiod,String townname,String twn) {
		
		try {
				
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
			//	LocalDateTime sysdatetime = LocalDateTime.now();
				LocalDateTime localDateTime = LocalDateTime.now();
				String sysdatetime = dtf.format(localDateTime);	
				
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    PdfPTable pdf3 = new PdfPTable(1);
		    PdfPTable pdf4 = new PdfPTable(1);
		    PdfPTable pdf5 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(4);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
		    pstart.setAlignment(Element.ALIGN_CENTER);
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    pdf2.addCell(cell1);
		    document.add(pdf2);
		    
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		   // p1.add(new Phrase("NPP Report Consumer",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    //p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.NO_BORDER);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPCell cell3 = new PdfPCell();
		    Paragraph p2 = new Paragraph();
		    //p2.add(new Phrase("NPP Report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    //p2.setAlignment(Element.ALIGN_CENTER);
		    cell3.addElement(p2);
		    cell3.setBorder(Rectangle.NO_BORDER);
		    pdf4.addCell(cell3);
		    document.add(pdf4);
		    
		    PdfPCell cell5 = new PdfPCell();
		    Paragraph p4 = new Paragraph();
		    p4.add(new Phrase("Level of Monitoring:NPP",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p4.setAlignment(Element.ALIGN_CENTER);
		    cell5.addElement(p4);
		    cell5.setBorder(Rectangle.NO_BORDER);
		    pdf5.addCell(cell5);
		    document.add(pdf5);
		    
		    PdfPCell cell4 = new PdfPCell();
		    Paragraph p3 = new Paragraph();
		    p3.add(new Phrase("Format:Consumer",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p3.setAlignment(Element.ALIGN_CENTER);
		    cell4.addElement(p3);
		    cell4.setBorder(Rectangle.BOTTOM);
		    pdf3.addCell(cell4);
		    document.add(pdf3);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Scheme :"+scheme,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    /*headerCell = new PdfPCell(new Phrase("Period :"+period,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);*/
		    
		    headerCell = new PdfPCell(new Phrase("State :"+state,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Discom :"+discom,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Report Month :"+month,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("current time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Input Energy Period :"+ieperiod,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);	    
		    
		    String query="";
			List<Object[]> nppreportconsum=null;
			if (twn=="%") {
				query="select b.town,d.discom_code,b.nc_req_opening_cnt,b.nc_req_received,b.nc_req_closed,b.closed_with_in_serc,\n" +
						" c.comp_opening_cnt,c.comp_received,c.comp_closed,c.closed_with_in_serc as serc from meter_data.pfc_d2_rpt b\n" +
						" INNER JOIN meter_data.pfc_d3_rpt c  on b.month_year=c.month_year and b.town=c.town INNER JOIN \n" +
						" meter_data.amilocation d on  b.town=d.tp_towncode where b.month_year='"+period+"' and d.tp_towncode like '%' ";
			}else {
			query="select b.town,d.discom_code,b.nc_req_opening_cnt,b.nc_req_received,b.nc_req_closed,b.closed_with_in_serc,\n" +
					" c.comp_opening_cnt,c.comp_received,c.comp_closed,c.closed_with_in_serc as serc from meter_data.pfc_d2_rpt b\n" +
					" INNER JOIN meter_data.pfc_d3_rpt c  on b.month_year=c.month_year and b.town=c.town INNER JOIN \n" +
					" meter_data.amilocation d on  b.town=d.tp_towncode where b.month_year='"+period+"' and d.tp_towncode in "+town+" ";
			}
			nppreportconsum=postgresMdas.createNativeQuery(query).getResultList();
			   PdfPTable parameterTable = new PdfPTable(11);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setRowspan(2);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Town Code",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setRowspan(2);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DISCOM Code(NPP)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setRowspan(2);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell); 	
	           
	           parameterCell = new PdfPCell(new Phrase("New Connection",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setColspan(4);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Complaints",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	 	       parameterCell.setFixedHeight(30f);
	 	       parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	 	       parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 	       parameterCell.setColspan(4);
	 	       parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	 	       parameterTable.addCell(parameterCell);
	           
	           		parameterCell = new PdfPCell(new Phrase("New Connection Pending From Previous Month",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           		parameterTable.addCell(parameterCell);
	           
	           		parameterCell = new PdfPCell(new Phrase("New Connection Applied in Current Month",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           		parameterTable.addCell(parameterCell);
	           
	           		parameterCell = new PdfPCell(new Phrase("Total Connection Released in Current Month",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           		parameterTable.addCell(parameterCell);
	           		
	           		parameterCell = new PdfPCell(new Phrase("Connection Released Within SERC Limit",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           	    parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           		parameterTable.addCell(parameterCell);
	 	           
	 	            parameterCell = new PdfPCell(new Phrase("Complaints Pending From Previous Month",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	 	           	parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	 	           	parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 	           	parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	 	           	parameterTable.addCell(parameterCell);
	 	           
	 	            parameterCell = new PdfPCell(new Phrase("New Complaints Received in Current Month",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	 	           	parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	 	           	parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 	           	parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	 	           	parameterTable.addCell(parameterCell);
	 	           
	 	           	parameterCell = new PdfPCell(new Phrase("Totl Complaints Closed in Current Month",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	 	           	parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	 	           	parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 	           	parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	 	           	parameterTable.addCell(parameterCell);
	 	           		
	 	           	parameterCell = new PdfPCell(new Phrase("Complaints Closed Within SERC Limit",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	 	           	parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	 	           	parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 	           	parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	 	           	parameterTable.addCell(parameterCell);

	           
	           
	           for (int i = 0; i < nppreportconsum.size(); i++) 
	           {

	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=nppreportconsum.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);

												 											 

	            		}
	            	}
	            
	           }
	           document.add(parameterTable);
	           
	           			document.add(new Phrase("\n"));
	           				LineSeparator separator = new LineSeparator();
	           				separator.setPercentage(98);
	           				separator.setLineColor(BaseColor.WHITE);
	           				Chunk linebreak = new Chunk(separator);
	           				document.add(linebreak);
			         
		       
	           
	           
			        	document.close();
			        	response.setHeader("Content-disposition", "attachment; filename=NPPReportConsumer.pdf");
			        	response.setContentType("application/pdf");
			        	ServletOutputStream outstream = response.getOutputStream();
			        	baos.writeTo(outstream);
			        	outstream.flush();
			        	outstream.close();
		
			}catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	@Override
	public void getNPPReportFeederPdf(HttpServletRequest request, HttpServletResponse response, String scheme,
			String town, String period, String state, String discom, String month, String ieperiod,String townname,String twn) {
		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
			//	LocalDateTime sysdatetime = LocalDateTime.now();
				LocalDateTime localDateTime = LocalDateTime.now();
				String sysdatetime = dtf.format(localDateTime);	
				
				
			Rectangle pageSize = new Rectangle(3550, 1000);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer =PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    PdfPTable pdf3 = new PdfPTable(1);
		    PdfPTable pdf4 = new PdfPTable(1);
		    PdfPTable pdf5 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(4);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("TAMIL NADU GENERATION AND DISTRIBUTION CORPORATION",new Font(Font.FontFamily.HELVETICA  ,18, Font.BOLD)));
		    pstart.setAlignment(Element.ALIGN_CENTER);
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    pdf2.addCell(cell1);
		    document.add(pdf2);
		    
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    //p1.add(new Phrase("NPP Report Feeder ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    //p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.NO_BORDER);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPCell cell3 = new PdfPCell();
		    Paragraph p2 = new Paragraph();
		    //p2.add(new Phrase("NPP REPORT",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    //p2.setAlignment(Element.ALIGN_CENTER);
		    cell3.addElement(p2);
		    cell3.setBorder(Rectangle.NO_BORDER);
		    pdf4.addCell(cell3);
		    document.add(pdf4);
		    
		    PdfPCell cell5 = new PdfPCell();
		    Paragraph p4 = new Paragraph();
		    p4.add(new Phrase("Level of Monitoring : NPP",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p4.setAlignment(Element.ALIGN_CENTER);
		    cell5.addElement(p4);
		    cell5.setBorder(Rectangle.NO_BORDER);
		    pdf5.addCell(cell5);
		    document.add(pdf5);
		    
		    PdfPCell cell4 = new PdfPCell();
		    Paragraph p3 = new Paragraph();
		    p3.add(new Phrase("Format : Feeder",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p3.setAlignment(Element.ALIGN_CENTER);
		    cell4.addElement(p3);
		    cell4.setBorder(Rectangle.BOTTOM);
		    pdf3.addCell(cell4);
		    document.add(pdf3);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Scheme :"+scheme,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		   /* headerCell = new PdfPCell(new Phrase("Period :"+period,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);*/
		    
		    headerCell = new PdfPCell(new Phrase("State :"+state,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Discom :"+discom,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Report Month :"+month,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("current time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Input Energy Period :"+ieperiod,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);	    
		    
		    String query="";
			List<Object[]> nppreportfeeder=null;
			if (twn=="%") {
				query="Select xa.*, kwh_imp, kwh_exp,  min_voltage,max_current ,\n" +
						"						to_char(to_date('"+period+"', 'YYYYMM') - INTERVAL '15 MONTH' , 'yyyy-mm-dd') as start_period,\n" +
						"					 	to_char(to_date('"+period+"', 'YYYYMM') - INTERVAL '1 MONTH + 1 DAY', 'yyyy-mm-dd') as end_period,\n" +
						"					 	'U' as feeder_type from  (\n" +
						"						Select feedercode,monthyear,sum(totalnooccurence) as totalnooccurence,meterid,\n" +
						"						Sum(totalpowerfailureduration) as totalpowerfailureduration ,\n" +
						"						Sum(totalpowerfailureduration/60) as totalpowerfailuredurationinmin,\n" +
						"						ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,lt_dom_con_count,govt_con_count,agri_con_count,other_con_count,ht_ind_energy_bill,\n" +
						"						ht_com_energy_bill,lt_ind_energy_bill,lt_com_energy_bill,lt_dom_energy_bill,govt_energy_bill,agri_energy_bill,other_energy_bill,\n" +
						"						ht_ind_amount_bill,ht_com_amount_bill,lt_ind_amount_bill,lt_com_amount_bill,lt_dom_amount_bill,govt_amount_bill,agri_amount_bill,\n" +
						"						other_amount_bill,ht_ind_amount_collected,ht_com_amount_collected,lt_ind_amount_collected,lt_com_amount_collected,lt_dom_amount_collected,govt_amount_collected,agri_amount_collected,other_amount_collected		\n" +
						"						from meter_data.power_onoff po 				LEFT JOIN meter_data.npp_data_monthly_calculation npp on npp.tp_feeder_code = po.feedercode and to_char(date, 'yyyymm') = monthyear			where to_char(date, 'yyyymm') = '"+period+"' and po.towncode like '%' \n" +
						"						GROUP BY feedercode ,meterid,monthyear,		ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_dom_con_count,govt_con_count,\n" +
						"						agri_con_count,other_con_count,ht_ind_energy_bill,ht_com_energy_bill,lt_ind_energy_bill,lt_com_energy_bill,lt_dom_energy_bill,govt_energy_bill,agri_energy_bill,other_energy_bill,ht_ind_amount_bill,ht_com_amount_bill,lt_ind_amount_bill,lt_com_amount_bill,\n" +
						"						lt_dom_amount_bill,govt_amount_bill,agri_amount_bill,other_amount_bill,ht_ind_amount_collected,ht_com_amount_collected,lt_com_con_count,lt_ind_amount_collected,lt_com_amount_collected,lt_dom_amount_collected,govt_amount_collected,agri_amount_collected,other_amount_collected) xa\n" +
						"					 	LEFT JOIN meter_data.monthly_consumption mc on xa.meterid= mc.mtrno and CAST(monthyear as numeric) = mc.billmonth";
			}else {
			query="Select xa.*, kwh_imp, kwh_exp,  min_voltage,max_current ,\n" +
					"						to_char(to_date('"+period+"', 'YYYYMM') - INTERVAL '15 MONTH' , 'yyyy-mm-dd') as start_period,\n" +
					"					 	to_char(to_date('"+period+"', 'YYYYMM') - INTERVAL '1 MONTH + 1 DAY', 'yyyy-mm-dd') as end_period,\n" +
					"					 	'U' as feeder_type from  (\n" +
					"						Select feedercode,monthyear,sum(totalnooccurence) as totalnooccurence,meterid,\n" +
					"						Sum(totalpowerfailureduration) as totalpowerfailureduration ,\n" +
					"						Sum(totalpowerfailureduration/60) as totalpowerfailuredurationinmin,\n" +
					"						ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_com_con_count,lt_dom_con_count,govt_con_count,agri_con_count,other_con_count,ht_ind_energy_bill,\n" +
					"						ht_com_energy_bill,lt_ind_energy_bill,lt_com_energy_bill,lt_dom_energy_bill,govt_energy_bill,agri_energy_bill,other_energy_bill,\n" +
					"						ht_ind_amount_bill,ht_com_amount_bill,lt_ind_amount_bill,lt_com_amount_bill,lt_dom_amount_bill,govt_amount_bill,agri_amount_bill,\n" +
					"						other_amount_bill,ht_ind_amount_collected,ht_com_amount_collected,lt_ind_amount_collected,lt_com_amount_collected,lt_dom_amount_collected,govt_amount_collected,agri_amount_collected,other_amount_collected		\n" +
					"						from meter_data.power_onoff po 				LEFT JOIN meter_data.npp_data_monthly_calculation npp on npp.tp_feeder_code = po.feedercode and to_char(date, 'yyyymm') = monthyear			where to_char(date, 'yyyymm') = '"+period+"' and po.towncode = '"+twn+"' \n" +
					"						GROUP BY feedercode ,meterid,monthyear,		ht_ind_con_count,ht_com_con_count,lt_ind_con_count,lt_dom_con_count,govt_con_count,\n" +
					"						agri_con_count,other_con_count,ht_ind_energy_bill,ht_com_energy_bill,lt_ind_energy_bill,lt_com_energy_bill,lt_dom_energy_bill,govt_energy_bill,agri_energy_bill,other_energy_bill,ht_ind_amount_bill,ht_com_amount_bill,lt_ind_amount_bill,lt_com_amount_bill,\n" +
					"						lt_dom_amount_bill,govt_amount_bill,agri_amount_bill,other_amount_bill,ht_ind_amount_collected,ht_com_amount_collected,lt_com_con_count,lt_ind_amount_collected,lt_com_amount_collected,lt_dom_amount_collected,govt_amount_collected,agri_amount_collected,other_amount_collected) xa\n" +
					"					 	LEFT JOIN meter_data.monthly_consumption mc on xa.meterid= mc.mtrno and CAST(monthyear as numeric) = mc.billmonth";
			}
//			System.out.println(query);
			nppreportfeeder=postgresMdas.createNativeQuery(query).getResultList();
			   PdfPTable parameterTable = new PdfPTable(43);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
			
			  parameterCell = new PdfPCell(new Phrase("S.NO",new
			  Font(Font.FontFamily.HELVETICA ,8, Font.BOLD)));
			  parameterCell.setFixedHeight(30f);
			  parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			  parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			  parameterTable.addCell(parameterCell);
			      		
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder Code",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder_Type(U/R/M)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Start Billing Period",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("End Billing Period",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("No_of_Power_Failure",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Duration_of_Power_Failure(Second)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Minimum_voltage(V)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Maximum_Current(A)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Input Energy(kwh)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Export Energy(kwh)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("HT_Industrial_Consumer_Count",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("HT_Commercial_Consumer_Count",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Industrial_Consumer_Count",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Commercial_Consumer_Count",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Domestic_Consumer_Count",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Govt_Consumer_Count",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Agri_Consumer_Count",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Others_Consumer_Count",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("HT_Industrial_Energy_Billed(kwh)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("HT_Commercial",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Industrial_Energy_Billed(kwh)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Commercial_Energy_Billed(kwh)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Domestic_Energy_Billed(kwh)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Govt_Energy_Billed(kwh)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Agri_Energy_Billed(kwh)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Others_Energy_Billed(kwh)",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("HT_Industrial_Amount_Billed",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("HT_Commercial",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Industrial_Amount_Billed",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Commercial_Amount_Billed",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Domestic_Amount_Billed",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Govt_Amount_Billed",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Agri_Amount_Billed",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Others_Amount_Billed",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("HT_Industrial_Amount_Collected",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("HT_Commercial_Amount_Collected",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Industrial_Amount_Collected",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Commercial_Amount_Collected",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LT_Domestic_Amount_Collected",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Govt_Amount_Collected",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Agri_Amount_Collected",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Others_Amount_Collected",new Font(Font.FontFamily.HELVETICA  ,8, Font.BOLD)));
	           parameterCell.setFixedHeight(30f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);	           
	           
	           for (int i = 0; i < nppreportfeeder.size(); i++) 
	           {

	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=nppreportfeeder.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			//String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[44]==null?null:obj[44]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[42]==null?null:obj[42]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[43]==null?null:obj[43]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
				 											 
							 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[40]==null?null:obj[40]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[41]==null?null:obj[41]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[38]==null?null:obj[38]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[39]==null?null:obj[39]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[12]==null?null:obj[12]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[13]==null?null:obj[13]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[14]==null?null:obj[14]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[15]==null?null:obj[15]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[16]==null?null:obj[16]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[17]==null?null:obj[17]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[18]==null?null:obj[18]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[19]==null?null:obj[19]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[20]==null?null:obj[20]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[21]==null?null:obj[21]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[22]==null?null:obj[22]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[23]==null?null:obj[23]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[24]==null?null:obj[24]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[25]==null?null:obj[25]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[26]==null?null:obj[26]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[27]==null?null:obj[27]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[28]==null?null:obj[28]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[29]==null?null:obj[29]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[30]==null?null:obj[30]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[31]==null?null:obj[31]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[32]==null?null:obj[32]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[33]==null?null:obj[33]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[34]==null?null:obj[34]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[35]==null?null:obj[35]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[36]==null?null:obj[36]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[37]==null?null:obj[37]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);

							 
	            		}
	            	}
	            
	           }
	           document.add(parameterTable);
	           
	           			document.add(new Phrase("\n"));
	           				LineSeparator separator = new LineSeparator();
	           				separator.setPercentage(98);
	           				separator.setLineColor(BaseColor.WHITE);
	           				Chunk linebreak = new Chunk(separator);
	           				document.add(linebreak);
			         
		       
	           
	           
			        	document.close();
			        	response.setHeader("Content-disposition", "attachment; filename=NPPReportFeeder.pdf");
			        	response.setContentType("application/pdf");
			        	ServletOutputStream outstream = response.getOutputStream();
			        	baos.writeTo(outstream);
			        	outstream.flush();
			        	outstream.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
	}
	
	@Override
	public List<?> getpfcD1Data(String period, String finalString,String twn) {
		List<?> resultList =null;
		String qry="";
        try {
        	if (twn=="%") {
        		/*qry="SELECT DISTINCT	town_ipds,round(bill_eff,4) as bill_eff,round(coll_eff,4) as coll_eff,round(atc_loss,4) as atc_loss FROM	(\n" +
						"					SELECT DISTINCT	* FROM	(SELECT	town,	bill_eff,	coll_eff,	atc_loss	\n" +
						"					FROM	meter_data.pfc_d1_rpt WHERE	month_year = '"+period+"'	AND town like '%') X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"					WHERE Y.tp_towncode = X.town ) AA;";
        	}else {
				qry= "SELECT DISTINCT	town_ipds,round(bill_eff,4) as bill_eff,round(coll_eff,4) as coll_eff,round(atc_loss,4) as atc_loss FROM	(\n" +
						"					SELECT DISTINCT	* FROM	(SELECT	town,	bill_eff,	coll_eff,	atc_loss	\n" +
						"					FROM	meter_data.pfc_d1_rpt WHERE	month_year = '"+period+"'	AND town in "+finalString+") X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"					WHERE Y.tp_towncode = X.town ) AA;";
        	}*/
        		qry= "SELECT town_code,town_ipds,  round((1-(a.actualbillng_efficiency * a.actualcollection_efficiency))*100,8) as  atc_loss_percent,billing_efficiency,collection_efficiency from\n" +
    					"(select town_code,town_ipds,		round((unit_billed/unit_supply),4) AS 		     actualbillng_efficiency,\n" +
    					"round((amt_collected/amt_billed),4) AS actualcollection_efficiency,\n" +
    					"round((unit_billed/unit_supply)*100,4) AS billing_efficiency,\n" +
    					"round((amt_collected/amt_billed)*100,4) AS collection_efficiency from(Select 	town_code,town_ipds,	SUM (unit_supply) as unit_supply,\n" +
    					"SUM (unit_billed) as unit_billed,\n" +
    					"SUM (amt_billed) as amt_billed,\n" +
    					"SUM (amt_collected) as amt_collected\n" +
    					"from  meter_data.rpt_eamainfeeder_losses_02months mfl \n" +
    					"left join meter_data.amilocation al on al.tp_towncode = mfl.town_code  WHERE	month_year = '" +period+"'	AND town_code like '%'  GROUP BY town_code,town_ipds)firstqry )a";
    				
    					 
    			
    		}else {
    			
    				
    		qry= "SELECT town_code,town_ipds,  round((1-(a.actualbillng_efficiency * a.actualcollection_efficiency))*100,8) as  atc_loss_percent,billing_efficiency,collection_efficiency from\n" +
    				"(select town_code,town_ipds,		round((unit_billed/unit_supply),4) AS 		     actualbillng_efficiency,\n" +
    				"round((amt_collected/amt_billed),4) AS actualcollection_efficiency,\n" +
    				"round((unit_billed/unit_supply)*100,4) AS billing_efficiency,\n" +
    				"round((amt_collected/amt_billed)*100,4) AS collection_efficiency from(Select 	town_code,town_ipds,	SUM (unit_supply) as unit_supply,\n" +
    				"SUM (unit_billed) as unit_billed,\n" +
    				"SUM (amt_billed) as amt_billed,\n" +
    				"SUM (amt_collected) as amt_collected\n" +
    				"from  meter_data.rpt_eamainfeeder_losses_02months mfl \n" +
    				"left join meter_data.amilocation al on al.tp_towncode = mfl.town_code  WHERE	month_year = '" +period+"'	AND town_code like '%'  GROUP BY town_code,town_ipds)firstqry )a";
    		
    		}
        	resultList= postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
      
		return resultList;
	}
	@Override
	public List<?> getpfcD2Data(String period, String finalString, String twn) {
		List<?> resultList =null;
		String qry="";
		try {
			if (twn=="%") {
				qry="SELECT DISTINCT	town_ipds,nc_req_opening_cnt,nc_req_received,tot_nc_req,nc_req_closed,nc_req_pending,closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system  FROM	(\n" +
						"						SELECT DISTINCT	* FROM	(SELECT	town,	nc_req_opening_cnt,	nc_req_received,	tot_nc_req,	nc_req_closed,	nc_req_pending,	closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system  \n" +
						"						FROM	meter_data.pfc_d2_rpt WHERE	month_year = '"+period+"'	AND town like '%') X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"						WHERE Y.tp_towncode = X.town ) AA;";
			}else {
			qry="SELECT DISTINCT	town_ipds,nc_req_opening_cnt,nc_req_received,tot_nc_req,nc_req_closed,nc_req_pending,closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system  FROM	(\n" +
					"						SELECT DISTINCT	* FROM	(SELECT	town,	nc_req_opening_cnt,	nc_req_received,	tot_nc_req,	nc_req_closed,	nc_req_pending,	closed_with_in_serc,closed_beyond_serc,percent_within_serc,released_by_it_system  \n" +
					"						FROM	meter_data.pfc_d2_rpt WHERE	month_year = '"+period+"'	AND town in "+finalString+") X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
					"						WHERE Y.tp_towncode = X.town ) AA;";
			}
					resultList= postgresMdas.createNativeQuery(qry).getResultList();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	@Override
	public List<?> getpfcD3Data(String period, String finalString, String twn) {
		List<?> resultList =null;
		String qry="";
		try {
			if (twn=="%") {
				qry="SELECT DISTINCT	town_ipds,comp_opening_cnt,comp_received,tot_complains,comp_closed,comp_pending_period FROM	(	SELECT DISTINCT	* FROM\n" +
						"				(select town,comp_opening_cnt,comp_received,tot_complains,comp_closed,comp_pending_period from meter_data.pfc_d3_rpt  where month_year='"+period+"' and town like '%') X,	\n" +
						"				( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y WHERE Y.tp_towncode = X.town ) AA";
			}else {
			qry="SELECT DISTINCT	town_ipds,comp_opening_cnt,comp_received,tot_complains,comp_closed,comp_pending_period FROM	(	SELECT DISTINCT	* FROM\n" +
					"				(select town,comp_opening_cnt,comp_received,tot_complains,comp_closed,comp_pending_period from meter_data.pfc_d3_rpt  where month_year='"+period+"' and town in "+finalString+") X,	\n" +
					"				( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y WHERE Y.tp_towncode = X.town ) AA";
			}
			resultList= postgresMdas.createNativeQuery(qry).getResultList();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	@Override
	public List<?> getpfcD4Data(String period, String finalString, String twn) {
		List<?> resultList =null;
		String qry="";
		try {
			if (twn=="%") {
				qry="select town,tot_fdr,fdr1_name,fdr1_be,fdr1_ce,fdr1_atc from meter_data.pfc_d4_rpt where town like '%' and month_year = '"+period+"'";
			}else {
			qry="select town,tot_fdr,fdr1_name,fdr1_be,fdr1_ce,fdr1_atc from meter_data.pfc_d4_rpt where town in "+finalString+" and month_year = '"+period+"'";
			}
			resultList= postgresMdas.createNativeQuery(qry).getResultList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	@Override
	public List<?> getpfcD5Data(String period, String finalString, String twn) {
		List<?> resultList =null;
		String qry="";
		try {
			if (twn=="%") {
				qry="SELECT DISTINCT	town_ipds,fdr_name,total_consumers,power_off_count,power_off_duration FROM	(\n" +
						"												SELECT DISTINCT	* FROM	(select town,fdr_name,total_consumers,power_off_count,power_off_duration \n" +
						"												from meter_data.pfc_d5_rpt where month_year='"+period+"' AND town like '%' ) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"												WHERE Y.tp_towncode = X.town ) AA;";
			}else {
			qry="SELECT DISTINCT	town_ipds,fdr_name,total_consumers,power_off_count,power_off_duration FROM	(\n" +
					"												SELECT DISTINCT	* FROM	(select town,fdr_name,total_consumers,power_off_count,power_off_duration \n" +
					"												from meter_data.pfc_d5_rpt where month_year='"+period+"' AND town in "+finalString+" ) X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
					"												WHERE Y.tp_towncode = X.town ) AA;";
			}
			resultList= postgresMdas.createNativeQuery(qry).getResultList();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	@Override
	public List<?> getpfcD6Data(String period, String finalString, String twn) {
		List<?> resultList =null;
		String qry="";
		try {
			if (twn=="%") {
				qry="SELECT DISTINCT	town_ipds,fdr_name,meter_sr_number,meter_communicating FROM	(\n" +
						"										SELECT DISTINCT	* FROM	(select rec_id,town,fdr_name,meter_sr_number,meter_communicating \n" +
						"										from meter_data.pfc_d5_rpt where month_year='"+period+"' AND town like '%') X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"										WHERE Y.tp_towncode = X.town ) AA;";
			}else {
			qry="SELECT DISTINCT	town_ipds,fdr_name,meter_sr_number,meter_communicating FROM	(\n" +
					"										SELECT DISTINCT	* FROM	(select rec_id,town,fdr_name,meter_sr_number,meter_communicating \n" +
					"										from meter_data.pfc_d5_rpt where month_year='"+period+"' AND town in "+finalString+") X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
					"										WHERE Y.tp_towncode = X.town ) AA;";
			}
			resultList= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	@Override
	public List<?> getpfcD7Data(String period, String finalString, String twn) {
		List<?> resultList =null;
		String qry="";
		try {
			if (twn=="%") {
				qry="SELECT DISTINCT	town_ipds,consumer_cnt,online_payment,total_amt_collected,online_amt FROM	(\n" +
						"										SELECT DISTINCT	* FROM	(SELECT	town,consumer_cnt,online_payment,total_amt_collected,online_amt \n" +
						"										FROM	meter_data.pfc_d7_rpt WHERE	month_year = '"+period+"'	AND town like '%') X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
						"											WHERE Y.tp_towncode = X.town ) AA;";
			}else {
			qry="SELECT DISTINCT	town_ipds,consumer_cnt,online_payment,total_amt_collected,online_amt FROM	(\n" +
					"										SELECT DISTINCT	* FROM	(SELECT	town,consumer_cnt,online_payment,total_amt_collected,online_amt \n" +
					"										FROM	meter_data.pfc_d7_rpt WHERE	month_year = '"+period+"'	AND town in "+finalString+") X,	( SELECT tp_towncode,town_ipds FROM meter_data.amilocation )Y \n" +
					"											WHERE Y.tp_towncode = X.town ) AA;";
			}
			resultList= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	@Override
	public List<?> getnppconsumData(String period, String finalString, String twn) {
		List<?> resultList =null;
		String qry="";
		try {
			if (twn=="%") {
				qry="select b.town,d.discom_code,b.nc_req_opening_cnt,b.nc_req_received,b.nc_req_closed,b.closed_with_in_serc,\n" +
						"					 c.comp_opening_cnt,c.comp_received,c.comp_closed,c.closed_with_in_serc as serc from meter_data.pfc_d2_rpt b\n" +
						"					 INNER JOIN meter_data.pfc_d3_rpt c  on b.month_year=c.month_year and b.town=c.town INNER JOIN \n" +
						"					 meter_data.amilocation d on  b.town=d.tp_towncode where b.month_year='"+period+"' and d.tp_towncode like '%' ";
			}else {
			qry="select b.town,d.discom_code,b.nc_req_opening_cnt,b.nc_req_received,b.nc_req_closed,b.closed_with_in_serc,\n" +
					"					 c.comp_opening_cnt,c.comp_received,c.comp_closed,c.closed_with_in_serc as serc from meter_data.pfc_d2_rpt b\n" +
					"					 INNER JOIN meter_data.pfc_d3_rpt c  on b.month_year=c.month_year and b.town=c.town INNER JOIN \n" +
					"					 meter_data.amilocation d on  b.town=d.tp_towncode where b.month_year='"+period+"' and d.tp_towncode in "+finalString+"";
			}
			resultList= postgresMdas.createNativeQuery(qry).getResultList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	@Override
	public List<?> getnppfeederData(String period, String finalString, String twn) {
		List<?> resultList =null;
		String qry="";
		try {
			if (twn=="%") {
				qry="select A.town_ipds,A.monthyear,A.town_rapdrp,A.feedername,A.fdr_id,A.feeder_type,A.start_billing_date,A.end_billling_date,A.no_of_power_faliure,A.duration_of_power_faliure,round(A.min_voltage,0),A.max_current,A.input_energy,A.export_energy,A.ht_ind_con_count,A.ht_com_con_count,A.lt_ind_con_count,A.lt_com_con_count,A.lt_dom_con_count,A.govt_con_count,A.agri_con_count,'',A.other_con_count,A.ht_ind_energy_bill,A.ht_com_energy_bill,A.lt_ind_energy_bill,A.lt_com_energy_bill,A.lt_dom_energy_bill,A.govt_energy_bill,A.agri_energy_bill,'',A.other_energy_bill,A.ht_ind_amount_bill,A.ht_com_amount_bill,A.lt_ind_amount_bill,A.lt_com_amount_bill,A.lt_dom_amount_bill,A.govt_amount_bill,A.agri_amount_bill,'',A.other_amount_bill,A.ht_ind_amount_collected,A.ht_com_amount_collected,A.lt_ind_amount_collected,A.lt_com_amount_collected,A.lt_dom_amount_collected,A.govt_amount_collected,A.agri_amount_collected,A.other_amount_collected,'','','',''\n" +
						"from\n" +
						"(SELECT DISTINCT l.discom,l.town_ipds,l.town_rapdrp,b.* FROM meter_data.amilocation l,\n" +
						"					(\n" +
						"						SELECT s.parent_town,a.* FROM meter_data.substation_details s,\n" +
						"					(\n" +
						"						SELECT f.feedername,f.fdr_id,f.parentid,f.tpparentid,n.* FROM meter_data.npp_data n, meter_data.feederdetails f WHERE n.feeder_code=f.fdr_id and n.monthyear='"+period+"'\n" +
						"					)a WHERE s.sstp_id=a.tpparentid AND s.parent_town like '%' \n" +
						"					)b WHERE l.tp_towncode=b.parent_town)A";
			}else {
			qry="select A.town_ipds,A.monthyear,A.town_rapdrp,A.feedername,A.fdr_id,A.feeder_type,A.start_billing_date,A.end_billling_date,A.no_of_power_faliure,A.duration_of_power_faliure,round(A.min_voltage,0),A.max_current,A.input_energy,A.export_energy,A.ht_ind_con_count,A.ht_com_con_count,A.lt_ind_con_count,A.lt_com_con_count,A.lt_dom_con_count,A.govt_con_count,A.agri_con_count,'',A.other_con_count,A.ht_ind_energy_bill,A.ht_com_energy_bill,A.lt_ind_energy_bill,A.lt_com_energy_bill,A.lt_dom_energy_bill,A.govt_energy_bill,A.agri_energy_bill,'',A.other_energy_bill,A.ht_ind_amount_bill,A.ht_com_amount_bill,A.lt_ind_amount_bill,A.lt_com_amount_bill,A.lt_dom_amount_bill,A.govt_amount_bill,A.agri_amount_bill,'',A.other_amount_bill,A.ht_ind_amount_collected,A.ht_com_amount_collected,A.lt_ind_amount_collected,A.lt_com_amount_collected,A.lt_dom_amount_collected,A.govt_amount_collected,A.agri_amount_collected,A.other_amount_collected,'','','',''\n" +
					"from\n" +
					"(SELECT DISTINCT l.discom,l.town_ipds,l.town_rapdrp,b.* FROM meter_data.amilocation l,\n" +
					"					(\n" +
					"						SELECT s.parent_town,a.* FROM meter_data.substation_details s,\n" +
					"					(\n" +
					"						SELECT f.feedername,f.fdr_id,f.parentid,f.tpparentid,n.* FROM meter_data.npp_data n, meter_data.feederdetails f WHERE n.feeder_code=f.fdr_id and n.monthyear='"+period+"'\n" +
					"					)a WHERE s.sstp_id=a.tpparentid AND s.parent_town in "+finalString+" \n" +
					"					)b WHERE l.tp_towncode=b.parent_town)A";
			}
			resultList= postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	@Override
	public void getdtlossPDF(String town, String monthyear, String periodMonth, String feederTpId, String zone,
			String circle,HttpServletRequest request,HttpServletResponse response) {
		List<Object[]> BounData=null;
		String zonename="", cirname="",townname="";
		try {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
			LocalDateTime sysdatetime = LocalDateTime.now();

			
			if (zone.equalsIgnoreCase("%") && circle.equalsIgnoreCase("%") && town.equalsIgnoreCase("%")){
				zonename="ALL";
				townname="ALL";
				cirname="ALL";
			}else {
				String qry="select distinct ami.zone, ami.circle,ami.town_ipds,ssd.ss_name from meter_data.feederdetails fdr,meter_data.amilocation ami,meter_data.substation_details ssd \n" +
						"where ssd.parent_town=ami.tp_towncode and fdr.tpparentid = ssd.sstp_id and ami.zone like '"+zone+"' and ami.circle like '"+circle+"' and fdr.tp_town_code like '"+town+"'";
				BounData=postgresMdas.createNativeQuery(qry).getResultList();
				System.out.println("qry1------+" + qry);
				if(BounData.size()>0){
					cirname =(String)BounData.get(0)[0];
					townname=(String)BounData.get(0)[1];
					zonename =(String)BounData.get(0)[3];

				}
			}
			
			if (zone.equalsIgnoreCase("%")){
				zonename="ALL";
			}else {
				zonename=zone;
			}
			
			if (circle.equalsIgnoreCase("%")){
				cirname="ALL";
			}else {
				cirname=circle;
			}
			if (town.equalsIgnoreCase("%")){
				townname="ALL";
			}else {
				townname=town;
			}
			

			Rectangle pageSize = new Rectangle(1550, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("DT LOSSES ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(4);
		    header.setWidths(new int[]{1,1,1,1});
		    header.setWidthPercentage(100);
		    
		    
		    PdfPCell headerCell=null;
		    
		    headerCell = new PdfPCell(new Phrase("Region :"+zonename,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Circle :"+cirname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Current Date&Time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    
		    document.add(header);
			
		    
		    String query="";
			List<Object[]> DtLosses=null;
			query="	select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,round((loss/nullif(total_unit_supply,0))* 100,2) as loss_per from\r\n" + 
					"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
					"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
					"							total_consumer_count,feedername,\r\n" + 
					"round(total_unit_supply,2)total_unit_supply,\r\n" + 
					"round(total_unit_billed,2)total_unit_billed,\r\n" + 
					"round(total_amount_billed,2)total_amount_billed,\r\n" + 
					"round(total_amount_collected,2)total_amount_collected,	\r\n" + 
					"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
					"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
					"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
					" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
					" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
					"							tech_loss,\r\n" + 
					"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
					"							time_stamp,meterno,\r\n" + 
					"				CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'14 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR)\r\n" + 
					" as input_eng_per,\r\n" + 
					" CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'13 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_lt,\r\n" + 
					" \r\n" + 
					" CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'12 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
					"						\r\n" + 
					"							FROM meter_data.rpt_eadt_losses_12months where town_code ='"+town+"'\r\n" + 
					"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
					"							 )A )X ,\r\n" + 
					"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode='"+town+"')Y\r\n" + 
					"							WHERE Y.sitecode=X.office_id\r\n" + 
					"							AND '"+periodMonth+"'='12'\r\n" + 
					"							\r\n" + 
					"							\r\n" + 
					"						UNION ALL \r\n" + 
					"							\r\n" + 
					"		select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,round((loss/nullif(total_unit_supply,0))* 100,2) as loss_per from\r\n" + 
					"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
					"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
					"							total_consumer_count,feedername,\r\n" + 
					"round(total_unit_supply,2)total_unit_supply,\r\n" + 
					"round(total_unit_billed,2)total_unit_billed,\r\n" + 
					"round(total_amount_billed,2)total_amount_billed,\r\n" + 
					"round(total_amount_collected,2)total_amount_collected,\r\n" + 
					"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
					"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
					"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
					" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
					" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
					"							tech_loss,\r\n" + 
					"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
					"							time_stamp,meterno,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'12 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'11 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'10 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
					"\r\n" + 
					"		FROM meter_data.rpt_eadt_losses_10months where town_code ='"+town+"'\r\n" + 
					"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
					"							 )A )X ,\r\n" + 
					"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode='"+town+"')Y\r\n" + 
					"							WHERE Y.sitecode=X.office_id\r\n" + 
					"							AND '"+periodMonth+"'='10'	\r\n" + 
					"							\r\n" + 
					"							\r\n" + 
					"													\r\n" + 
					"	UNION ALL\r\n" + 
					"	\r\n" + 
					"	\r\n" + 
					"	select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,round((loss/nullif(total_unit_supply,0))* 100,2) as loss_per from\r\n" + 
					"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
					"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
					"							total_consumer_count,feedername,\r\n" + 
					"round(total_unit_supply,2)total_unit_supply,\r\n" + 
					"round(total_unit_billed,2)total_unit_billed,\r\n" + 
					"round(total_amount_billed,2)total_amount_billed,\r\n" + 
					"round(total_amount_collected,2)total_amount_collected,\r\n" + 
					"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
					"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
					"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
					" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
					" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
					"							tech_loss,\r\n" + 
					"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
					"							time_stamp,meterno,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'10 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'9 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'8 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
					"							FROM meter_data.rpt_eadt_losses_08months where town_code ='"+town+"'\r\n" + 
					"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
					"							 )A )X ,\r\n" + 
					"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode='"+town+"')Y\r\n" + 
					"							WHERE Y.sitecode=X.office_id\r\n" + 
					"							AND '"+periodMonth+"'='08'\r\n" + 
					"					\r\n" + 
					"					UNION ALL\r\n" + 
					"			\r\n" + 
					"			\r\n" + 
					"			select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,round((loss/nullif(total_unit_supply,0))* 100,2) as loss_per from\r\n" + 
					"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
					"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
					"							total_consumer_count,feedername,\r\n" + 
					"round(total_unit_supply,2)total_unit_supply,\r\n" + 
					"round(total_unit_billed,2)total_unit_billed,\r\n" + 
					"round(total_amount_billed,2)total_amount_billed,\r\n" + 
					"round(total_amount_collected,2)total_amount_collected,\r\n" + 
					"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
					"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
					"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
					" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
					" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
					"							tech_loss,\r\n" + 
					"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
					"							time_stamp,meterno,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'8 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'7 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'6 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
					"							FROM meter_data.rpt_eadt_losses_06months where town_code ='"+town+"'\r\n" + 
					"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
					"							 )A )X ,\r\n" + 
					"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode='"+town+"')Y\r\n" + 
					"							WHERE Y.sitecode=X.office_id\r\n" + 
					"							AND '"+periodMonth+"'='06'\r\n" + 
					"							\r\n" + 
					"						\r\n" + 
					"							UNION ALL\r\n" + 
					"							\r\n" + 
					"							\r\n" + 
					"							select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,round((loss/nullif(total_unit_supply,0))* 100,2) as loss_per from\r\n" + 
					"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
					"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
					"							total_consumer_count,feedername,\r\n" + 
					"round(total_unit_supply,2)total_unit_supply,\r\n" + 
					"round(total_unit_billed,2)total_unit_billed,\r\n" + 
					"round(total_amount_billed,2)total_amount_billed,\r\n" + 
					"round(total_amount_collected,2)total_amount_collected,\r\n" + 
					"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
					"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
					"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
					" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
					" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
					"							tech_loss,\r\n" + 
					"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
					"							time_stamp,meterno,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'6 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'5 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'4 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
					"\r\n" + 
					"							FROM meter_data.rpt_eadt_losses_04months where town_code ='"+town+"'\r\n" + 
					"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
					"							 )A )X ,\r\n" + 
					"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode='"+town+"')Y\r\n" + 
					"							WHERE Y.sitecode=X.office_id\r\n" + 
					"							AND '"+periodMonth+"'='04'\r\n" + 
					"							\r\n" + 
					"							\r\n" + 
					"							\r\n" + 
					"								\r\n" + 
					"							UNION ALL\r\n" + 
					"							\r\n" + 
					"							select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,round((loss/nullif(total_unit_supply,0))* 100,2) as loss_per from\r\n" + 
					"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
					"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
					"							total_consumer_count,feedername,\r\n" + 
					"round(total_unit_supply,2)total_unit_supply,\r\n" + 
					"round(total_unit_billed,2)total_unit_billed,\r\n" + 
					"round(total_amount_billed,2)total_amount_billed,\r\n" + 
					"round(total_amount_collected,2)total_amount_collected,\r\n" + 
					"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
					"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
					"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
					" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
					" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
					"							tech_loss,\r\n" + 
					"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
					"							time_stamp,meterno,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'4 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'3 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'2 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'1 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
					"\r\n" + 
					"							FROM meter_data.rpt_eadt_losses_02months where town_code ='"+town+"'\r\n" + 
					"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
					"							)A )X ,\r\n" + 
					"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode='"+town+"')Y\r\n" + 
					"							WHERE Y.sitecode=X.office_id\r\n" + 
					"							AND '"+periodMonth+"'='02'\r\n" + 
					"					\r\n" + 
					"						UNION ALL		\r\n" + 
					"							\r\n" + 
					"					select DISTINCT dt_name,tpdt_id,meterno,total_consumer_count,total_unit_supply,total_unit_billed,loss,round((loss/nullif(total_unit_supply,0))* 100,2) as loss_per from\r\n" + 
					"							 (SELECT A.*,round((1-((total_unit_billed/nullif(total_unit_supply,0))*(total_amount_collected/nullif(total_amount_billed,0))))*100,2) as atc_loss_percent,(total_unit_supply-total_unit_billed) as loss  FROM(\r\n" + 
					"							select office_id,trim(TO_CHAR(TO_DATE(substr(month_year,5,6), 'MM'), 'Month'))||'-'||substr(month_year,1,4) as month_year,tpdt_id,dt_name,\r\n" + 
					"							total_consumer_count,feedername,\r\n" + 
					"round(total_unit_supply,2)total_unit_supply,\r\n" + 
					"round(total_unit_billed,2)total_unit_billed,\r\n" + 
					"round(total_amount_billed,2)total_amount_billed,\r\n" + 
					"round(total_amount_collected,2)total_amount_collected,\r\n" + 
					"							round((total_unit_billed/nullif(total_unit_supply,0))*100,2) AS billing_efficiency,\r\n" + 
					"							round((total_amount_collected/nullif(total_amount_billed,0))*100,2) AS collection_efficiency,\r\n" + 
					"							round(((total_unit_supply-total_unit_billed)/nullif(total_unit_supply,0))*100,2) as t_d_loss,\r\n" + 
					" 							round((total_unit_billed/nullif(total_unit_supply,0)),2) AS Actualbilling_efficiency,\r\n" + 
					" 							round((total_amount_collected/nullif(total_amount_billed,0)),2) AS Actualcollection_efficiency,\r\n" + 
					"							tech_loss,\r\n" + 
					"							round(((tech_loss/nullif(total_unit_supply,0))*100),2) as tech_loss_perc,\r\n" + 
					"							time_stamp,meterno,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR) as input_eng_per,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR)as coll_amt_lt,\r\n" + 
					"							CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR)||' TO '|| CAST( to_char(to_date('"+monthyear+"', 'YYYYMM') - INTERVAL'00 MONTH','Mon-YYYY') AS VARCHAR) as coll_amt_ht\r\n" + 
					"\r\n" + 
					"							FROM meter_data.rpt_eadt_losses_00months where town_code ='"+town+"'\r\n" + 
					"							 and month_year ='"+monthyear+"' and feeder_tp_id like '"+feederTpId+"'\r\n" + 
					"							)A )X ,\r\n" + 
					"						(select zone,circle,tp_subdivcode,sitecode,subdivision,town_ipds from meter_data.amilocation where zone like '"+zone+"' and circle like '"+circle+"' and tp_towncode='"+town+"')Y\r\n" + 
					"							WHERE Y.sitecode=X.office_id\r\n" + 
					"							AND '"+periodMonth+"'='00'\r\n" + 
					"";
			DtLosses=postgresMdas.createNativeQuery(query).getResultList();
			
			//System.out.println("PDF query----->"+query);
			
			   PdfPTable parameterTable = new PdfPTable(9);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("DT NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("METER NUMBER",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TOTAL CONSUMER",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("UNIT SUPPLIED A (kwh)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("UNIT BILLED B (kwh)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LOSS C=(A-B)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("LOSS PERCENTAGE (%)",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	         
			
	           for (int i = 0; i < DtLosses.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=DtLosses.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							

							 
							 
							 
	            		}
	            	}
	            }
	           
	           document.add(parameterTable);
	           
	           document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=DTLosses.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
