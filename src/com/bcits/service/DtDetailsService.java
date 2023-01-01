package com.bcits.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.bcits.entity.DtDetailsEntity;

public interface DtDetailsService extends GenericService<DtDetailsEntity> {

	// public List getsubdivncodes();

	public List<?> getdtdetails();

	public List<?> getChangedDetails(int id);

	int getmodifydtdetails(String dtide, String dtnamee,String editdttype, double editdtcapacity,int editdtphase,
			String edittpdtcode, String edittpparentcode, String editmetersrno,
			String editmetermf, double editmf, double editConsumptionper);

	public List<?> getsubstation(String officeid);

	public List<?> getdtname();

	public List<?> getShowdivision(String circle);

	//public List<?> getmeterManuFacture();

	// public List<?> getCircleList();

	public List<?> getFeederNameForDt(String subStationName);
	
	public List<DtDetailsEntity> getDtDetailsBymetrno(String meterno);
	
	public String getDtid();


	public List<?> getDistinctCircle();

	public List<?> getDivByCircle(String circle, ModelMap model);

	public List<?> getSubdivByDivByCircle(String circle, String division, ModelMap model);


	public String deletedt(String dtide);

	
	public List<?> getDTdataByfeederNames(String fdrname);
	
	public HashMap<String, String> getALLLocationData(String sitecode);
	
	public int updateConsumption(String dtid,String consperc);
	public Object getDTIdNamenameBySubdiv(String parentid, String sdoCode);

	public List<?> getDtHealthRep(String zone, String monthAlt, String circle, String town);

	public Object getMaxRdngmnth();

	//public List<?> getNtwrkDetails(String divsion, String subdiv, ModelMap model);
	
	public List<?> getNtwrkDetails(String ofcid, String subdiv,String month, ModelMap model);

	public List<?> getoverloadedDetails(String officeid, String subdiv, String monthAlt, ModelMap model, String town,String towncode,String zone,String circle);

	public List<?> getunderloadedDetails(String officeid, String subdiv, String monthAlt, ModelMap model, String town,String circle,String zone,String towncode);

	public List<?> getunbalancedDetails(String officeid, String subdiv, String monthAlt, ModelMap model, String town,String circle,String zone,String towncode);

	public Object getDTIdNamenameBySubdivandSubs(String parentid,String officeId);

	public List<?> gettotalDTDetails(String officeid, String subdiv, String monthAlt, ModelMap model, String town,String otown,String zone,String circle,String towncode,String circlecode,String zonecode);
	
	public List<?> getdtdetailsBytownCode(String region,String ssid,String townCode,String circle);

	public List<DtDetailsEntity> getDtDetailsByDttpid(String dttpid, String tp_towncode);
	
	public List<DtDetailsEntity> getDtDetailsByMeterno(String meterno);
	
	void getDtDetailsPdf(HttpServletRequest request,HttpServletResponse response,String circle,String town,String substation);

	public DtDetailsEntity getDtDetailsByMeter(String meterid);
	
	List<?> getIndividualDtConsumData1(String circle,String division,String subdivision,String towncode,String townname,String dt,String period,String fromdate,String todate);
	
	 List<?> getIndividualDtConsumData2(String circle,String division,String subdivision,String towncode,String townname,String dt,String period,String frommonth,String tomonth);
		
	List<?> getIndividualDtConsumData3(String circle,String division,String subdivision,String towncode,String townname,String dt,String period,String ipwisedate);
		
	List<?> getIndividualDtConsumData11(String towncode);
		
	List<?> getNoOfMeterCount(String dttpcode);
	List<?> getdtconsumdata(String zone,String circle,String division,String subdivision,String towncode,String feederTpId,String period,String date,String month);
	public  void dtConsumptionpdf( HttpServletRequest request,HttpServletResponse response,String circle,String towncode,String period,String date,String month,String zone,String frommonthh,String tomonthh,String dtt);
	

		public void getdtLoadSummpdf(HttpServletRequest request, HttpServletResponse response, String zone1,String crcl,String twn, String month, String townname1);

		public DtDetailsEntity getDtByFdrcodeMrtId(String dtcode, String feedercode, String meterno, String towncode);

		public List<DtDetailsEntity> getDtDetailsByDttpid(String dttpid);

		public List<DtDetailsEntity> getDtDetailsByTownFdrDtId(String dttpid, String tp_towncode, String frdId);
		


}
