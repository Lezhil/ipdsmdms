package com.bcits.mdas.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.PfcD2reportEntity;
import com.bcits.mdas.entity.PfcD7ReportEntity;
import com.bcits.mdas.entity.RptFeederLossesEntity;
import com.bcits.mdas.entity.pfcd1rptentity;
import com.bcits.mdas.service.PfcD1ReportService;
import com.bcits.mdas.service.PfcD2ReportService;
import com.bcits.mdas.service.PfcD3ReportService;
import com.bcits.mdas.service.PfcD4ReportService;
import com.bcits.mdas.service.PfcD5ReportService;
import com.bcits.mdas.service.PfcD7ReportService;
import com.bcits.mdas.service.RptFeederLossesService;




@Controller
public class ReportsDataSyncController {

	@Autowired
	PfcD7ReportService pfcD7ReportService ;
	
	@Autowired
	PfcD1ReportService pfcD1ReportService;
	
	@Autowired
	PfcD2ReportService pfcD2ReportService;
	
	@Autowired
	PfcD3ReportService pfcD3ReportService;
	
	@Autowired
	PfcD4ReportService pfcD4ReportService;
	
	@Autowired
	PfcD5ReportService pfcD5ReportService;
	
	
	@Autowired
	RptFeederLossesService rptFeederLossesService;
	
	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;
	
	
	
	// Process for D1 Reports
	
	  @RequestMapping (value = "/dataSyncforD1report", method = { RequestMethod.GET, RequestMethod.POST})
	  @ResponseBody  public String dataSyncforD1report(){
		  //@RequestParam String billmonth
		String billmonthNew = "201904";
	
			List<Object[]> result  = null;
			String msg = null;
			try {
				result = pfcD1ReportService.getPFCD1ReportData(billmonthNew);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				for (Object[] item : result) {
					/*String year = item[6].toString();
					String month = item[5].toString();;
				
					if(month.length() == 1  ){
						String add = "0";
						month= add+month;
					}
					
					String monthYear = month+year;*/
					
					
					pfcd1rptentity pfcD1 = new pfcd1rptentity();
				
					pfcD1.setMonthYear(billmonthNew);
					pfcD1.setTown(item[0].toString());
					pfcD1.setBaselineLoss(0.0);
					
					//Setting Billing Efficency
					if(item[3] == null){
						item[3] = "";
					}
					pfcD1.setBillEff(Double.parseDouble(item[14].toString())); 
				
					
					//Setting collection efficency
					if(item[4] == null){
						item[4] = "";
					}
					pfcD1.setCollEff(Double.parseDouble(item[15].toString()));
					
					//Setting AT&C calculation
					if(item[2] == null){
						item[2] = "0.0";
					}
					pfcD1.setAtcLoss(Double.parseDouble(item[16].toString()));
					pfcD1.setTimestamp(convertDate(item[6].toString()));
					
					try {
						pfcD1ReportService.save(pfcD1);	
					} catch (Exception e) {
						e.printStackTrace();e.getMessage();
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				msg = "failed";
			}
			
			return msg;
	  	}

	  @Transactional
	  @RequestMapping (value = "/dataSyncforD2report", method = {RequestMethod.GET,RequestMethod.POST})
	    public @ResponseBody String dataSyncforD2report(String billmonth){
		  
		  String year = billmonth.substring(0, 4);
			String month = billmonth.substring(4, 6);
			
			String billmonths = month+year;
			System.out.println(billmonths);
		  
		  //@RequestParam String billmonth
	//	String billmonthNew = "122019";
		  
		    System.out.println(billmonth);
			List<Object[]> result  = null;
			String msg = null;
			try {
				result = pfcD2ReportService.getPFCD2ReportData(billmonths);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				for (Object[] item : result) {
				
					
					
					PfcD2reportEntity pfcD2 = new PfcD2reportEntity();
				
					pfcD2.setTown(item[0].toString());
					pfcD2.setMonthYear(item[1].toString());
					pfcD2.setTimestamp(convertDate(item[2].toString()));
					//Setting Billing Efficency
					if(item[3].toString() == null){
						item[3] = "0.0";
					}
					pfcD2.setNcReqOpeningCnt(Double.parseDouble(item[3].toString())); 
				
					
					//Setting collection efficency
					if(item[4] == null){
						item[4] = "0.0";
					}
					pfcD2.setNcReqReceived(Double.parseDouble(item[4].toString()));
					
					
					if(item[5] == null){
						item[5] = "0.0";
					}
					pfcD2.setTotNcReq(Double.parseDouble(item[5].toString()));
					
					if(item[6] == null){
						item[6] = "0.0";
					}
					pfcD2.setNcReqClosed(Double.parseDouble(item[6].toString()));
					
					
					if(item[7] == null){
						item[7] = "0.0";
					}
					pfcD2.setNcReqPending(Double.parseDouble(item[7].toString()));
					
					
					if(item[8] == null){
						item[8] = "0.0";
					}
					pfcD2.setClosedWithInSerc(Double.parseDouble(item[8].toString()));
					
					
					if(item[9] == null){
						item[9] = "0.0";
					}
					pfcD2.setClosedBeyondSerc(Double.parseDouble(item[9].toString()));
					
					
					if(item[10] == null){
						item[10] = "0.0";
					}
					pfcD2.setPercentWithinSerc(Double.parseDouble(item[10].toString()));
					
					
					if(item[11] == null){
						item[11] = "0.0";
					}
					pfcD2.setReleasedByItSystem(Double.parseDouble(item[11].toString()));
					

					
					try {
						pfcD2ReportService.save(pfcD2);
						msg = "success";
					} catch (Exception e) {
						e.printStackTrace();e.getMessage();
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				msg = "failed";
			}
			
			return msg;
	  	}
	  
	  
	  
	  @Transactional
	  @RequestMapping (value = "/dataSyncforRPTfeederLosses", method = { RequestMethod.GET, RequestMethod.POST})
	  @ResponseBody  public String dataSyncforRPTfeederLosses(){
		  //@RequestParam String billmonth
		String billmonthNew = "042019";
	
			List<Object[]> result  = null;
			String msg = null;
			
				result = rptFeederLossesService.getrptFeederLosses(billmonthNew);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				for (Object[] item : result) {
				
					
					
					RptFeederLossesEntity rptFeeder = new RptFeederLossesEntity();
				
				/*try {	pfcD2.setTown(item[0].toString());
					pfcD2.setMonthYear(item[1].toString());
					pfcD2.setTimestamp(convertDate(item[2].toString()));
					//Setting Billing Efficency
					if(item[3].toString() == null){
						item[3] = "0.0";
					}
					pfcD2.setNcReqOpeningCnt(Double.parseDouble(item[3].toString())); 
				
					
					//Setting collection efficency
					if(item[4] == null){
						item[4] = "0.0";
					}
					pfcD2.setNcReqReceived(Double.parseDouble(item[4].toString()));
					
					
					if(item[5] == null){
						item[5] = "0.0";
					}
					pfcD2.setTotNcReq(Double.parseDouble(item[5].toString()));
					
					if(item[6] == null){
						item[6] = "0.0";
					}
					pfcD2.setNcReqClosed(Double.parseDouble(item[6].toString()));
					
					
					if(item[7] == null){
						item[7] = "0.0";
					}
					pfcD2.setNcReqPending(Double.parseDouble(item[7].toString()));
					
					
					if(item[8] == null){
						item[8] = "0.0";
					}
					pfcD2.setClosedWithInSerc(Double.parseDouble(item[8].toString()));
					
					
					if(item[9] == null){
						item[9] = "0.0";
					}
					pfcD2.setClosedBeyondSerc(Double.parseDouble(item[9].toString()));
					
					
					if(item[10] == null){
						item[10] = "0.0";
					}
					pfcD2.setPercentWithinSerc(Double.parseDouble(item[10].toString()));
					
					
					if(item[11] == null){
						item[11] = "0.0";
					}
					pfcD2.setReleasedByItSystem(Double.parseDouble(item[11].toString()));
					

					
					try {
						pfcD2ReportService.save(pfcD2);	
					} catch (Exception e) {
						e.printStackTrace();e.getMessage();
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				msg = "failed";
			}
			*/}
			return "Test";
	  	}
	  
	  // Convert String to timestamp
	  	  public Timestamp convertDate(String str_date) throws ParseException{
		  
		  try {
		    	
		      DateFormat formatter;
		      formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSSS");
		      Date date = (Date) formatter.parse(str_date);
		      java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
		      return timeStampDate;
		    
		    } catch (ParseException e) {
		    	return null;
		    
		    }

	  }
	
	
	  @RequestMapping (value = "/validatefreezeD1Report", method = { RequestMethod.GET, RequestMethod.POST})
		 @ResponseBody   public String validatefreezeD1Report(HttpServletRequest request){
		    	String billmonth = request.getParameter("billmonth");
		    String response = null;
		    	List<?> result =  pfcD1ReportService.checkFreezDataD1(billmonth);
		    	
		    	if(result.size() > 0 ){
		    		response = "dataFreezed";
		    	}else {
		    		response = "notFreezed";
		    	}
			     return response	;
		    }
		
	  @RequestMapping (value = "/freezeD1Report", method = { RequestMethod.GET, RequestMethod.POST})
		@ResponseBody  public String freezeD1report(HttpServletRequest request){
		   	String billmonth = request.getParameter("billmonth");
	    	int x = pfcD1ReportService.freezeDataD1(billmonth);
	 
			 if(x > 0 ){
				 return "DataFreezed"; 
			 }else {
				 return "error"; 
			 }
		    	
		    }
	
	
	  
	  
	  @RequestMapping (value = "/validatefreezeD2Report", method = { RequestMethod.GET, RequestMethod.POST})
		 @ResponseBody   public String validatefreezeD2Report(HttpServletRequest request){
		    	String billmonth = request.getParameter("billmonth");
		    String response = null;
		    	List<?> result =  pfcD2ReportService.checkFreezDataD2(billmonth);
		    	
		    	if(result.size() > 0 ){
		    		response = "dataFreezed";
		    	}else {
		    		response = "notFreezed";
		    	}
			     return response	;
		    }
		
	  @RequestMapping (value = "/freezeD2Report", method = { RequestMethod.GET, RequestMethod.POST})
		@ResponseBody  public String freezeD2report(HttpServletRequest request){
		   	String billmonth = request.getParameter("billmonth");
	    	int x = pfcD2ReportService.freezeDataD2(billmonth);
	 
			 if(x > 0 ){
				 return "DataFreezed"; 
			 }else {
				 return "error"; 
			 }
		    	
		    }
	
	  
	  
	  @RequestMapping (value = "/validatefreezeD3Report", method = { RequestMethod.GET, RequestMethod.POST})
		 @ResponseBody   public String validatefreezeD3Report(HttpServletRequest request){
		    	String billmonth = request.getParameter("billmonth");
		    String response = null;
		    	List<?> result =  pfcD3ReportService.checkFreezDataD3(billmonth);
		    	
		    	if(result.size() > 0 ){
		    		response = "dataFreezed";
		    	}else {
		    		response = "notFreezed";
		    	}
			     return response	;
		    }
		
	  @RequestMapping (value = "/freezeD3Report", method = { RequestMethod.GET, RequestMethod.POST})
		@ResponseBody  public String freezeD3report(HttpServletRequest request){
		   	String billmonth = request.getParameter("billmonth");
	    	int x = pfcD3ReportService.freezeDataD3(billmonth);
	 
			 if(x > 0 ){
				 return "DataFreezed"; 
			 }else {
				 return "error"; 
			 }
		    	
		    }
	
	  
	  @RequestMapping (value = "/validatefreezeD4Report", method = { RequestMethod.GET, RequestMethod.POST})
		 @ResponseBody   public String validatefreezeD4Report(HttpServletRequest request){
		    	String billmonth = request.getParameter("billmonth");
		    String response = null;
		    	List<?> result =  pfcD4ReportService.checkFreezDataD4(billmonth);
		    	
		    	if(result.size() > 0 ){
		    		response = "dataFreezed";
		    	}else {
		    		response = "notFreezed";
		    	}
			     return response	;
		    }
		
	  @RequestMapping (value = "/freezeD4Report", method = { RequestMethod.GET, RequestMethod.POST})
		@ResponseBody  public String freezeD4report(HttpServletRequest request){
		   	String billmonth = request.getParameter("billmonth");
	    	int x = pfcD4ReportService.freezeDataD4(billmonth);
	 
			 if(x > 0 ){
				 return "DataFreezed"; 
			 }else {
				 return "error"; 
			 }
		    	
		    }
	
	  
	  @RequestMapping (value = "/validatefreezeD5Report", method = { RequestMethod.GET, RequestMethod.POST})
		 @ResponseBody   public String validatefreezeD5Report(HttpServletRequest request){
		    	String billmonth = request.getParameter("billmonth");
		    String response = null;
		    	List<?> result =  pfcD5ReportService.checkFreezDataD5(billmonth);
		    	
		    	if(result.size() > 0 ){
		    		response = "dataFreezed";
		    	}else {
		    		response = "notFreezed";
		    	}
			     return response	;
		    }
		
	  @RequestMapping (value = "/freezeD5Report", method = { RequestMethod.GET, RequestMethod.POST})
		@ResponseBody  public String freezeD5report(HttpServletRequest request){
		   	String billmonth = request.getParameter("billmonth");
	    	int x = pfcD5ReportService.freezeDataD5(billmonth);
	 
			 if(x > 0 ){
				 return "DataFreezed"; 
			 }else {
				 return "error"; 
			 }
		    	
		    }
	  
	 @RequestMapping (value = "/validatefreezeD7Report", method = { RequestMethod.GET, RequestMethod.POST})
	 @ResponseBody   public String validatefreezeD7Report(HttpServletRequest request){
	    	String billmonth = request.getParameter("billmonth");
	    String response = null;
	    	List<?> result =  pfcD7ReportService.checkFreezDataD7(billmonth);
	    	
	    	if(result.size() > 0 ){
	    		response = "dataFreezed";
	    	}else {
	    		response = "notFreezed";
	    	}
		     return response	;
	    }
	
	//Below code is to freeze data 
		
	@RequestMapping (value = "/freezeD7Report", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody  public String freezeD7report(HttpServletRequest request){
	   	String billmonth = request.getParameter("billmonth");
    	int x = pfcD7ReportService.freezeData(billmonth);
 
		 if(x > 0 ){
			 return "DataFreezed"; 
		 }else {
			 return "error"; 
		 }
	    	
	    }
	
	
	//Initial Page
	 @RequestMapping (value = "/IntermediateDataSyn", method = { RequestMethod.GET, RequestMethod.POST})
	    public String IntermediateDataSyn(){
	    	
	    	return "IntermediateDataSync";
	    }
	    	  
	  @Transactional
	  @RequestMapping (value = "/d7DataCheck", method = { RequestMethod.GET, RequestMethod.POST})
	  @ResponseBody  public List<?> d7DataCheck(@RequestParam String billmonth){
		  	
		  System.out.println("Selected bill month" +billmonth );
		  
			String checkQry = "Select * from  meter_data.pfc_d7_rpt where month_year = '"+ billmonth+ "'  ";
			String checkQry2 = "Select * from  meter_data.pfc_d7_rpt where month_year = '"+ billmonth+ "'  and freezed = '1'";
			
			String msg = null;
			
			ArrayList<String> returnResult=new ArrayList<String>();
			List<Object[]> result = null;
			List<?> result2 = null;
			
			try {
				result  = pfcD7ReportService.getCustomEntityManager("postgresMdas").createNativeQuery(checkQry).getResultList();
				result2 = pfcD7ReportService.getCustomEntityManager("postgresMdas").createNativeQuery(checkQry2).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}
			 String s1 =null;
			if(result2.size() > 0 ){
				msg = "freezed";
				returnResult.add("freezed");
				
			}else if(result.size() >0 ){
				for(int i=0;i<result.size();i++)
				  {
					  Object[] obj=result.get(i);
					s1  = obj[0].toString();
					 System.out.println(obj[0]+"");
				  }			
				returnResult.add("overWrite");
				returnResult.add(s1);
			}else{
				returnResult.add("noData");
				
			}
			
		  return returnResult;
	  }
	  
	  
	  
	  @RequestMapping (value = "/dataSyncforD7report", method = { RequestMethod.GET, RequestMethod.POST})
	  @ResponseBody  public String syncPfcD7rpt(String billmonth){

		  String year = billmonth.substring(0, 4);
			String month = billmonth.substring(4, 6);
			
			String billmonths = month+year;
			System.out.println(billmonths);
		  String qry  = " select  month_year, town, SUM(consumer_cnt) as consumerCount, SUM(online_payment) as  OnlinePayment, SUM(total_amt_collected) as ToatalAmountCollected ,"
		  		+ " SUM(online_amt) from meter_data.pfc_d7_rpt_intermediate where month_year = '"+billmonths+"' group by month_year, town" ;
			String qry2  = "SELECT * FROM meter_data.pfc_d7_rpt_intermediate where month_year = '"+ billmonths + "'  ";
			List<Object[]> result  = null;
			String msg = null;
			//PfcD7ReportEntity pfcD7 = new PfcD7ReportEntity();
			try {
				System.out.println(qry);
				result = entityManager.createNativeQuery(qry).getResultList();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				for (Object[] item : result) {
					PfcD7ReportEntity pfcD7 = new PfcD7ReportEntity();
					pfcD7.setMonthYear(item[0].toString());
					pfcD7.setTown(item[1].toString());
					
					if(item[2].toString() == null){
						item[2] = "0.0";
					}
					pfcD7.setConsumerCnt(Double.parseDouble(item[2].toString())); 
					if(item[3] == null){
						item[3] = "0.0";
					}
					pfcD7.setOnlinePayment(Double.parseDouble(item[3].toString()));
					if(item[4] == null){
						item[4] = "0.0";
					}
					pfcD7.setTotalAmtCollected(Double.parseDouble(item[4].toString()));
					if(item[5] == null){
						item[5] = "0.0";
					}
					pfcD7.setOnlineAmt(Double.parseDouble(item[5].toString()));
					
					pfcD7.setTimestamp(timestamp);
					
					try {
						pfcD7ReportService.save(pfcD7);
						msg = "success";
					} catch (Exception e) {
						e.printStackTrace();e.getMessage();
					}
					
					System.out.println("this is is #### " +item[0].toString());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				msg = "failed";
			}
			
			return msg;
	  	}
	  
	  
	  @RequestMapping (value = "/dataOverWriteforD7report", method = { RequestMethod.GET, RequestMethod.POST})
	  @ResponseBody  public String dataOverWriteforD7report(@RequestParam String billmonth,@RequestParam String id){

		
		  String qry  = " select  month_year, town, SUM(consumer_cnt) as consumerCount, SUM(online_payment) as  OnlinePayment, SUM(total_amt_collected) as ToatalAmountCollected ,"
		  		+ " SUM(online_amt) from meter_data.pfc_d7_rpt_intermediate where month_year = '"+billmonth+"' group by month_year, town" ;
		
		  String qry2  = "SELECT * FROM meter_data.pfc_d7_rpt_intermediate where month_year = '"+ billmonth + "'  ";
			List<Object[]> result  = null;
			String msg = null;
			PfcD7ReportEntity pfcD7 = pfcD7ReportService.find(Integer.parseInt(id));
			try {
				result = entityManager.createNativeQuery(qry).getResultList();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				for (Object[] item : result) {
			
					pfcD7.setMonthYear(item[0].toString());
					pfcD7.setTown(item[1].toString());
					pfcD7.setConsumerCnt(Double.parseDouble(item[2].toString())); 
					pfcD7.setOnlinePayment(Double.parseDouble(item[3].toString()));
					pfcD7.setTotalAmtCollected(Double.parseDouble(item[4].toString()));
					pfcD7.setOnlineAmt(Double.parseDouble(item[5].toString()));
					pfcD7.setTimestamp(timestamp);
					try {
						pfcD7ReportService.update(pfcD7);	
						msg = "success";
					} catch (Exception e) {
						e.printStackTrace();e.getMessage();
					}
									
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				msg = "failed";
			}
		
			return msg;
	  	}
	  
	  
	 /* @Transactional
	  @RequestMapping (value = "/dataSyncforD2report", method = { RequestMethod.GET, RequestMethod.POST})
	  @ResponseBody  public String syncPfcD2rpt(@RequestParam String billmonth){

		  	System.out.println("Selected bill month" +billmonth );
		String checkQry = "Select * from  meter_data.pfc_d7_rpt_intermediate where month_year = '"+ billmonth+ "' ";
		String msg = null;
		List<?> result = null;
		try {
			result = pfcD7ReportService.getCustomEntityManager("postgresMdas").createNativeQuery(checkQry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		if (result.size() == 0) {
			String qry = "insert into meter_data.pfc_d7_rpt_intermediate ( SELECT * FROM meter_data.pfc_d7_rpt where month_year = '"+ billmonth + "') ";
			int x = 0;
			try {
				x = entityManager.createNativeQuery(qry).executeUpdate();
				if(x > 0){
				msg = "success";
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg = "failed";
			}

		} else {
			msg = "synced";
		}

		return msg;
		
		
	    }*/
	  
	  @Transactional
	  @RequestMapping (value = "/dataSyncforD3report/{billmonth}", method = { RequestMethod.GET, RequestMethod.POST})
	  @ResponseBody  public String syncPfcD3rpt(@PathVariable String billmonth){

		  	System.out.println("Selected bill month" +billmonth );
		String checkQry = "Select * from  meter_data.pfc_d3_rpt where month_year = '"+ billmonth+ "'";
		String msg = null;
		List<?> result = null;
		try {
			result = pfcD7ReportService
					.getCustomEntityManager("postgresMdas")
					.createNativeQuery(checkQry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		if (result.size() == 0) {
			String qry = "insert into meter_data.pfc_d3_rpt  ( SELECT rec_id,\r\n" + 
					"month_year,\r\n" + 
					"town_code,\r\n" + 
					"comp_opening_cnt,\r\n" + 
					"comp_received,\r\n" + 
					"tot_complains,\r\n" + 
					"comp_closed,\r\n" + 
					"comp_pending_period,\r\n" + 
					"comp_pending,\r\n" + 
					"closed_with_in_serc,\r\n" + 
					"closed_beyond_serc,\r\n" + 
					"percent_within_serc,\r\n" + 
					"now() FROM meter_data.pfc_focd3_rpt_intermediate where month_year = '"+ billmonth + "') ";
			int x = 0;
			try {
				x = entityManager.createNativeQuery(qry).executeUpdate();
				if(x > 0){
				msg = "success";
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg = "failed";
			}

		} else {
			msg = "synced";
		}

		return msg;
				
	    }
	  
	  
	  @Transactional
	  @RequestMapping (value = "/dataSyncforRPTFeederLosses", method = { RequestMethod.GET, RequestMethod.POST})
	  @ResponseBody  public String dataSyncforRPTFeederLosses(@RequestParam String billmonth){
		  
		  List<Object[]> result  = null;
			String msg = null;
			
			
		  return "test";
	  }
	  
	  
	  
}
