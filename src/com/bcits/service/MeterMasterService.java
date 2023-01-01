package com.bcits.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.UploadedFile;
import com.bcits.utility.Resultupdated;

@Component
public interface MeterMasterService extends GenericService<MeterMaster>
{
	public long countPending(MeterMaster meterMaster);
	
	public long countNOI(MeterMaster meterMaster);
	
	public long checkMeterExist(Integer rdngmonth,String metrno);
	
	public abstract List<MeterMaster> findAll();

	public abstract List<MeterMaster> findmonthData(String parameter);

	public abstract List<MeterMaster> findmeterandmonthData(String parameter);	
	
	public List getMeterMakeWiseData(int rdngmonth);
	
	public void  addNewConnectionData(MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model);
		
	public int  getMaxRdgMonthYear(HttpServletRequest request);
	
	public void searchByAccNo(HttpServletRequest request,MeterMaster meterMaster,ModelMap model);
	
	public List<MeterMaster> searchByAccNoSec(HttpServletRequest request,MeterMaster meterMaster,String accno,ModelMap model);
	
	public void  updateConnectionDataValues(MeterMaster newConnectionMeterMaster,HttpServletRequest request,ModelMap model);
	
	//public String getAccountNumber(MeterMaster meterMaster,HttpServletRequest request,ModelMap model);
	
	public Object getAccountNumber(String drddate,String mrno,HttpServletRequest request,ModelMap model);
	
	public Integer updateNewAccount(MeterMaster meterMaster,HttpServletRequest request,ModelMap model);

	public abstract String getMeterMasterData(HttpServletRequest request,MeterMaster meterMaster, ModelMap model);
	public abstract String  getMeterMasterData1(HttpServletRequest request,MeterMaster meterMaster, ModelMap model);
	
	public int updateManualReadingData(HttpServletRequest request,MeterMaster meterMaster,String oldsl, ModelMap model,String xcurrdngkwh);
	
	String checkMeterAccNoExistOrNot(MeterMaster newConnectionMeterMaster,HttpServletRequest request);
	
	String checkMeterExist(MeterMaster newConnectionMeterMaster,HttpServletRequest request);
	
	public abstract List<MeterMaster> findaccnomonthData(MeterMaster meterMaster,ModelMap model);	
	public abstract List<MeterMaster> findaccnomonthData1(MeterMaster meterMaster,ModelMap model);
	public abstract List<MeterMaster> findaccnomonthData2(MeterMaster meterMaster,ModelMap model);
	
  //  public List<MeterMaster>  findDistinct(MeterMaster meterMaster,ModelMap model);
	//public List<MeterMaster>  findDistinct1(MeterMaster meterMaster,ModelMap model);
	
	public Object  getAccountNumber1(String rrdmonth,String oldaccno,HttpServletRequest request,ModelMap model);
	
	public String getMeterMasterData2(HttpServletRequest request,MeterMaster meterMaster, ModelMap model);
	
	public List getMeterMasterDetailsForManualReadingData(String readingMonth,String accno);
	
	public List getMeterMasterDetailsForManualReadingDataMeter(String readingMonth,String metrno);

	public String getPreviousRemark(String accno,int readingmonth);

	public List getMrWiseReprot(MeterMaster meterMaster,HttpServletRequest request,ModelMap model);
	
	public List getMrWiseReprotOne(MeterMaster meterMaster,HttpServletRequest request,ModelMap model,String circle);
	
	public List getMrWiseBilledDetails(String mrname,String rdngmonth,String sdoName,HttpServletRequest request,ModelMap model);
	
	public List getMrWisePendingDetails(String mrname,String sdoname,String rdngmonth,HttpServletRequest request,ModelMap model);
	
	public List getDays(MeterMaster meterMaster,HttpServletRequest request,ModelMap model,String circle);

	// Added by Shivanand 
		public ArrayList<Resultupdated> updateMobileDataToMeterMaster(MeterMaster newConnectionMeterMaster,HttpServletRequest request, JSONArray array);
		
		public double getMFforTheGivenAccnumber(String accno, int readingmth);
		
		public double getCurrentKWH(String accno, int readingmth);
		
		JSONArray getNotMRDdata(String mrname, HttpServletRequest request);
		
		
		//Added by Sunil KJ
		public List<MeterMaster> getBillingDataMM(String billMonth,String meterno,HttpServletRequest request,ModelMap model);
		public List<?> getOtherExternalReportData(String condition,String month);

		public List<Object[]> showVariationOfEnergy(String currentMonth, ModelMap model,String category, String sdocode);

		public List<Object[]> getDistinctMake(int rdngMonth);

		public List<Object[]> showMrDaywiseReport(String parameter, ModelMap model,
				HttpServletRequest request);

		public List<Object[]> getAllPendingList(String month,HttpServletRequest request, ModelMap model);

		public List<Object[]> getPendingList(String mrname, String month,
				HttpServletRequest request, ModelMap model);

		public String meterNoUpload(UploadedFile meterNoUpload, String billmonth, ModelMap model,HttpServletRequest request);

		int UpdateNoMRDflag(String accno, int billmonth, String mrname);

		public void searchByMtrNo(HttpServletRequest request,MeterMaster newConnectionMeterMaster, ModelMap model);

		public void downloadBilledDataPdf(String meterno, String month,ModelMap model, HttpServletResponse response);
		
		
		//data Export

		public List<?> getExportData(String mrNameEx, String sdoCodeEx,
				String tadescEx);

		//delete accnos
		
				public int delByAccNo(String accno);

				
				
	  public List<?> getMrWiseReprotForReportReading(MeterMaster meterMaster, HttpServletRequest request,ModelMap model, String circle);


	public List<Object[]> getRNAdata(String billmonth, String circle);

	public Object getrnaReportBasedonMr(String circle, String billmonth,String sdocode, String sdoname, String mrname, ModelMap model,HttpServletRequest request, HttpServletResponse response);


	public Object getGrandTotalForReadingReport(MeterMaster meterMaster,HttpServletRequest request, ModelMap model, String circle);

	public List<?> getMrWiseReprotOnebySdoname(MeterMaster meterMaster,HttpServletRequest request, ModelMap model, String circle,String mrname, String sdoname);

	public int updateHtMualAndRRemark(String billMonth, String rdngRemark,String accNo,double currdngkwh,double currdngkvah,double currdngkva);


	public List<?> gethtReadingDetails(String rdngMonth, String circle1);

	public List<?> gethtReadingDetailsForExcel(String rdngMonth, String circle);


	public int getAMRORCMR(int rdngmonth, String meterno);

	public String getmeterno(int rdngmonth, String accno);

	List<?> getCirWiseReport(int rdngMonth);

	List getCircleWiseMeters(String circle, String mrname,int rdngmonth);

	public List getCircleWiseSecondBtopData(String circle, int rdngmonth);

	public  List<MeterMaster> findaccnomonthDataKno(MeterMaster meterMaster,ModelMap model);

	public List getdayWiseMrDetailsForUserRpt(String mrname, String sdoname,String readingdate, String circle, String rdng,
			HttpServletRequest request, ModelMap model);



	public void findAccDATA(String accno,String rdngmonth, HttpServletRequest request);	
		
	  //get Circle data for accno
	
	//ASCII report 
	public String gethtReadingDataAscii(String rdngMonth, String circle1,int acclength,HttpServletResponse response);
	
	//ASCII summary
	public List<?> htSummary(String rdngmonth);

	public List<MeterMaster> getMeterData(String parameter, String parameter2, ModelMap model);

	public Object exportReadingDataDetails(String circle, String subdivision,
			String month, HttpServletRequest request);

	public Object getAdministrativeDetails(String month, ModelMap model,
			HttpServletRequest request);

	public Object viewCirclewiseDetails(String circle, String month,
			ModelMap model, HttpServletRequest request);

	//public Object findmonthData(String parameter, HttpServletRequest request);
	
	public abstract ArrayList<Map<String, Object>> findmonthData1(String parameter,HttpServletRequest request, ModelMap model);


	public Object meterdetails(String circle, String subdivision,
			String division,String date, HttpServletRequest request);


		public List<Object[]> getInstallationDetails(String billmonth, String string,
			ModelMap model, HttpServletRequest request);

	public int updatestatus(String mtrno, String status,String date);

	public List<Object[]> getCategorywiseData(String month, HttpServletRequest request);
	
	public List<Object[]> getActieMeters(String subdivision, String month,HttpServletRequest request);

	public Object modemdetails(String circle, String subdivision,
			String division,String mtrno,  HttpServletRequest request);

	public List<Object[]> getServiceOrder();

	public List<MeterMaster> getMeterData();

	List<String> getDistinctSubdivision(String circle, String division,HttpServletRequest request);

	public void generateMdiExceedRpt(String month, String subdiv,String categoryVal,ModelMap model, HttpServletResponse response);

	
	
	
	//FOR GETTING THE BILLING DATA CONSUMPTION WISE
	public List<?> getBillingDataMM(String selectedDateName, String meterNo,
			ModelMap model, HttpServletRequest request);

	public List findALLZeroConc(String month);

	public List findALLDmndExceed(String month);

	public List findALLrtc(String selectedDateName,String circle,String division,String subdivision);

	public List<MeterMaster> getMeterDataByMeterNo(String meterno);
	
	public int updateBillDataTOMM(String yyyyMM1, String meterno, String kwh,String kvh, String kva, String pf, String todayDate,String t1kwh,String t2kwh,String t3kwh,String t4kwh,
			String t5kwh,String t6kwh,String t7kwh,String t8kwh,String t1kvah,String t2kvah,String t3kvah,String t4kvah,String t5kvah,
			String t6kvah,String t7kvah,String t8kvah,String t1kva,String t2kva,String t3kva,String t4kva,String t5kva,String t6kva,
			String t7kva,String t8kva,String kw,String flag,String billDatechk);
	
	public void updateDataToMetrMaster();
	
	
	MeterMaster getMeterDataByAccno(HttpServletRequest req);
	
	public JSONObject getBillDataByMeterNo(HttpServletRequest req);
	
	public List getMetrConfigData(String meterno);
	
	public List getMetrSyncData(int billmonth);
	
	public List getMetrDetails(String meterno);
	
	public List getCirclesByZone(String zone);
	
	public List getDivisionByCircleMDM(String zone, String circle);
	
	public List getSubdivByDivisionByCircleMDM(String zone, String circle,String division);
	 
	public List<String> getmeternoBySubDivByDivisionMDM(String zone, String circle, String division, String subdivision);
	
	public int updateLogDamageIssue(String meterno,String dmgIssue);
	
	public List getAddress(String meterno);
	
	public List getLocationBySdocode(String sdocode,String meterNo);
	
	//zone,circle,division,subdivision
	
	public List getAllZonesInAmiLocation();
	public List getAllCirclesInAmiLocation();
	public List getAllDivisionsInAmiLocation();
	public List getAllSubDivisionsInAmiLocation();

	public List findmonthData2(String parameter,HttpServletRequest request, ModelMap model);


	public Object getCirclesByCircleCode(String circleCode);

	List getMeterListBySubDiv(String zone, String circle, String division,
			String subdivision);
	

}