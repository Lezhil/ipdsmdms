package com.bcits.controller;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.FeederEntity;
import com.bcits.entity.Master;
import com.bcits.entity.MeterChangeTransHistory;
import com.bcits.entity.MeterInventoryEntity;
import com.bcits.entity.MeterIssueOrReturnTransaction;
import com.bcits.entity.SurveyorDetailsEntity;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.InstallationReview;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.InstallationReviewService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterInventoryService;
import com.bcits.mdas.utility.DateOrTimestampConversion;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.DtDetailsService;
import com.bcits.service.MeterChangeTransHistoryService;
import com.bcits.service.SurveyIssueOrReturnService;
import com.bcits.service.SurveyorDetailsService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class ManageSurveyController {
	
	@Autowired
	private MeterInventoryService meterInventoryService;
	
	@Autowired
	private SurveyorDetailsService surveyorDetailsService;
	@Autowired
	private SurveyIssueOrReturnService surveyIssueOrReturnService;
	
	@Autowired
	private FeederMasterService feederMasterService;
	
	@Autowired
	InstallationReviewService irs;
	
	@Autowired
	private MeterChangeTransHistoryService meterChangeTransHistoryService;
	
	@Autowired
	private MasterMainService masterMainService;
	
	@Autowired
	private DtDetailsService dtDetailsService;
	
	@Autowired
	private FeederDetailsService feederDetailsService;
	
	@Autowired
	private ConsumerMasterService consumerMasterService;
	
	@RequestMapping(value="/surveyor",method={RequestMethod.GET,RequestMethod.POST})	
	public String surveyor(HttpServletRequest request,ModelMap model){
		
		//System.out.println("inside surveyor");
		return "addSurveyor";
	}
	
	@RequestMapping(value="/addNewSurveyor",method={RequestMethod.GET,RequestMethod.POST})	
	public @ResponseBody Object addSurveyor(HttpServletRequest request,@RequestParam("surveyorNameId") String surveyorNameId,@RequestParam("surveyorStatusId") String surveyorStatusId,@RequestParam("surveyorIdentityId") String surveyorIdentityId ){
		
		
		/*String name=request.getParameter("surveyorNameId");
		String status=request.getParameter("surveyorStatusId");
		String identity=request.getParameter("surveyorIdentityId");*/
		
		
		SurveyorDetailsEntity sd=new SurveyorDetailsEntity();
		sd.setSurveyorname(surveyorNameId);
		sd.setSurveyorstatus(surveyorStatusId);
		sd.setIdentity(surveyorIdentityId);
		sd.setEntryby(request.getSession().getAttribute("username")+"");
		sd.setEntrydate(new Timestamp(System.currentTimeMillis()));
		sd.setSuid(surveyorDetailsService.sequenceSurveyorID());
		surveyorDetailsService.save(sd);
		return "SUCC";
	}
	@Transactional
	@RequestMapping(value="/updateSurveyor",method={RequestMethod.GET,RequestMethod.POST})	
	public @ResponseBody Object updateSurveyor(HttpServletRequest request,@RequestParam("surveyorNameId") String surveyorNameId,@RequestParam("surveyorStatusId") String surveyorStatusId,@RequestParam("surveyorIdentityId") String surveyorIdentityId,@RequestParam("id") String id ){
		
		SurveyorDetailsEntity sd=surveyorDetailsService.surveyorDetails(id).get(0);
		sd.setSurveyorname(surveyorNameId);
		sd.setSurveyorstatus(surveyorStatusId);
		sd.setIdentity(surveyorIdentityId);
		sd.setUpdateby(request.getSession().getAttribute("username")+"");
		sd.setUpdateddate(new Timestamp(System.currentTimeMillis()));
		
		surveyorDetailsService.update(sd);
		return "SUCC";
	}
	
	@RequestMapping(value="/surveyorList",method= {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<SurveyorDetailsEntity> surveyorList() {
		
		return surveyorDetailsService.surveyorList();
	}
	@RequestMapping(value="/meterAllocation")
	public String meterIssue(HttpServletRequest request,ModelMap model) {
		return "meterIssueOrReturn";
	}
	@RequestMapping(value="/activeSurveyorList",method= {RequestMethod.GET})
	public @ResponseBody Object activeSurveyorList() {
		return surveyorDetailsService.activeSurveyorList();
	}
	
	@RequestMapping(value="/inStockMeterList/{strLoc}/{iortype}",method= {RequestMethod.GET})
	public @ResponseBody Object inStockMeterList(@PathVariable String strLoc,@PathVariable String iortype) {
		return meterInventoryService.getALLInstockMeters(strLoc,iortype);
	}
	@Transactional
	@RequestMapping(value="/meterIssueDataSave",method= {RequestMethod.GET})
	public @ResponseBody Object meterIssueDataSave(HttpServletRequest request,@RequestParam("metersf") String metersf) {
	
		JsonElement jsonEle = new JsonParser().parse(metersf);
	    JsonObject  jsonObj = jsonEle.getAsJsonObject();
	    String sid=jsonObj.get("surveyorID").getAsString();
	    String iDate=jsonObj.get("iDate").getAsString();
	    JsonArray jsonArr = jsonObj.getAsJsonArray("meters");
	    String storeLoc=jsonObj.get("storeLoc").getAsString();
	    String type=jsonObj.get("type").getAsString();
	    String status=jsonObj.get("status").getAsString();
	    
	    Gson gs = new Gson();
        ArrayList mtrList = gs.fromJson(jsonArr, ArrayList.class);
        List<String> lr=new ArrayList<>();
        try {
        for (Object object : mtrList) {
        	 MeterIssueOrReturnTransaction mir=new  MeterIssueOrReturnTransaction();
        	 mir.setMeterNo(object.toString());
        	 mir.setMeterStatus(status);
        	 mir.setSuid(sid);
        	 mir.setInsertBy(request.getSession().getAttribute("username")+"");
        	 mir.setInsertTime(new Timestamp(System.currentTimeMillis()));
        	 if(type.equalsIgnoreCase("ISSUED")) {
        		 mir.setTransType("ISSUED");
        	 }
        	 else if(type.equalsIgnoreCase("INSTOCK")) {
        		 mir.setTransType("RETURN");
        	 }
        	 mir.setIssueOrReturnTime(new DateOrTimestampConversion().convertStringToTimestamp(iDate));
        	 mir.setStoreLoc(storeLoc);
        	 surveyIssueOrReturnService.save(mir);
        	 List<MeterInventoryEntity> l=meterInventoryService.meterNoExistOrNot(object.toString());
        	 MeterInventoryEntity m=l.get(0);
        	 m.setMeter_status(type);
        	 m.setMrFlag(sid);
        	 meterInventoryService.update(m);
        	
        }
        lr.add("Succ");
        return lr;
        }
        catch (Exception e) {
			e.printStackTrace();
			 lr.add("Fail");
		        return lr;
		}
	}
	@RequestMapping(value="/surveyListPersonWise/{suid}/{fromdate}/{todate}",method= {RequestMethod.GET})
	public @ResponseBody Object surveyListPersonWise(@PathVariable String suid,@PathVariable String fromdate,@PathVariable String todate) {
		/*
		 * String
		 * sql="select a.*,b.surveyorname from (select mir.*,mi.meter_make,mi.meter_model,mi.meter_current_rating,mi.meter_voltage_rating,case when meter_connection_type=1 then 'Single Phase' else 'Three Phase' end from meter_data.meter_issue_or_return_transaction mir left join meter_data.meter_inventory mi on mir.meterno=mi.meterno where mir.suid='"
		 * +suid+"') a LEFT JOIN meter_data.surveyordetails b on a.suid=b.suid ";
		 */
		String[] fa=fromdate.split("-");
		String[] ta=todate.split("-");
		String sql="select (select surveyorname from meter_data.surveyordetails where suid=mrcode)	,survey_timings	,imei	,kno	,consumername	,address	,mobileno	,dtcno	,poleno	,meterno	,connectiontype	,old_metermake	,\n" +
				"old_mf	,old_ctrn	,old_ctrd	,premise	,latitude	,longitude	,finalreading	,oldmeterkvah	,oldmeterkva	,newmeterno	,newmetermake	,newmetertype	,newmf	,newctratio	,newinitialreading		,newmeterkvah	,newmeterkva	,\n" +
				"sticker_no,(select case when r.photos_quality='G' then 'Good' when r.photos_quality='A' then 'Average' when r.photos_quality='B' then 'Bad' else ''  end as rs from meter_data.installation_review_history r where r.consumer_no=kno and r.new_mtr_no=newmeterno ORDER BY r.id DESC LIMIT 1)	 from meter_data.survey_output where mrcode='"+suid+"' and to_date(survey_timings,'YYYY-MM-DD')>=to_timestamp('"+fa[2]+"-"+fa[1]+"-"+fa[0]+"','YYYY-MM-DD') and to_date(survey_timings,'YYYY-MM-DD')<=to_timestamp('"+ta[2]+"-"+ta[1]+"-"+ta[0]+"','YYYY-MM-DD')";
		List<Object[]> l=surveyIssueOrReturnService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
	     return l;
	}
	@RequestMapping(value="/feederWise/{fdrid}/{fromdate}/{todate}",method= {RequestMethod.GET})
	public @ResponseBody Object feederWise(@PathVariable String fdrid,@PathVariable String fromdate,@PathVariable String todate) {
		String[] fa=fromdate.split("-");
		String[] ta=todate.split("-");
		String sql="select (select surveyorname from meter_data.surveyordetails where suid=mrcode) 	,survey_timings	,imei	,kno	,consumername	,address	,mobileno	,dtcno	,poleno	,meterno	,connectiontype	,old_metermake	,\n" +
				"old_mf	,old_ctrn	,old_ctrd	,premise	,latitude	,longitude	,finalreading	,oldmeterkvah	,oldmeterkva	,newmeterno	,newmetermake	,newmetertype	,newmf	,newctratio	,newinitialreading		,newmeterkvah	,newmeterkva	,\n" +
				"sticker_no,(select case when r.photos_quality='G' then 'Good' when r.photos_quality='A' then 'Average' when r.photos_quality='B' then 'Bad' else ''  end as rs from meter_data.installation_review_history r where r.consumer_no=kno and r.new_mtr_no=newmeterno ORDER BY r.id DESC LIMIT 1)	 from meter_data.survey_output where dtcno in (select udtccode from spdcl.dtc_master where feederno='"+fdrid+"') and to_date(survey_timings,'YYYY-MM-DD')>=to_timestamp('"+fa[2]+"-"+fa[1]+"-"+fa[0]+"','YYYY-MM-DD') and to_date(survey_timings,'YYYY-MM-DD')<=to_timestamp('"+ta[2]+"-"+ta[1]+"-"+ta[0]+"','YYYY-MM-DD')";
		List<Object[]> l=surveyIssueOrReturnService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
	     return l;
	}
	@RequestMapping(value="/dtWise/{dtid}/{fromdate}/{todate}",method= {RequestMethod.GET})
	public @ResponseBody Object dtWise(@PathVariable String dtid,@PathVariable String fromdate,@PathVariable String todate) {
		String[] fa=fromdate.split("-");
		String[] ta=todate.split("-");
		String sql="select (select surveyorname from meter_data.surveyordetails where suid=mrcode) 	,survey_timings	,imei	,kno	,consumername	,address	,mobileno	,dtcno	,poleno	,meterno	,connectiontype	,old_metermake	,\n" +
				"old_mf	,old_ctrn	,old_ctrd	,premise	,latitude	,longitude	,finalreading	,oldmeterkvah	,oldmeterkva	,newmeterno	,newmetermake	,newmetertype	,newmf	,newctratio	,newinitialreading		,newmeterkvah	,newmeterkva	,\n" +
				"sticker_no,(select case when r.photos_quality='G' then 'Good' when r.photos_quality='A' then 'Average' when r.photos_quality='B' then 'Bad' else ''  end as rs from meter_data.installation_review_history r where r.consumer_no=kno and r.new_mtr_no=newmeterno ORDER BY r.id DESC LIMIT 1)	 from meter_data.survey_output where dtcno='"+dtid+"' and to_date(survey_timings,'YYYY-MM-DD')>=to_timestamp('"+fa[2]+"-"+fa[1]+"-"+fa[0]+"','YYYY-MM-DD') and to_date(survey_timings,'YYYY-MM-DD')<=to_timestamp('"+ta[2]+"-"+ta[1]+"-"+ta[0]+"','YYYY-MM-DD')";
		List<Object[]> l=surveyIssueOrReturnService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
	     return l;
	}
	@RequestMapping(value="/consumerWise/{cno}/{fromdate}/{todate}",method= {RequestMethod.GET})
	public @ResponseBody Object consumerWise(@PathVariable String cno,@PathVariable String fromdate,@PathVariable String todate) {
		String[] fa=fromdate.split("-");
		String[] ta=todate.split("-");
		String sql="select (select surveyorname from meter_data.surveyordetails where suid=mrcode) 	,survey_timings	,imei	,kno	,consumername	,address	,mobileno	,dtcno	,poleno	,meterno	,connectiontype	,old_metermake	,\n" +
				"old_mf	,old_ctrn	,old_ctrd	,premise	,latitude	,longitude	,finalreading	,oldmeterkvah	,oldmeterkva	,newmeterno	,newmetermake	,newmetertype	,newmf	,newctratio	,newinitialreading		,newmeterkvah	,newmeterkva	,\n" +
				"sticker_no,(select case when r.photos_quality='G' then 'Good' when r.photos_quality='A' then 'Average' when r.photos_quality='B' then 'Bad' else ''  end as rs from meter_data.installation_review_history r where r.consumer_no=kno and r.new_mtr_no=newmeterno ORDER BY r.id DESC LIMIT 1)	 from meter_data.survey_output where kno='"+cno+"' and to_date(survey_timings,'YYYY-MM-DD')>=to_timestamp('"+fa[2]+"-"+fa[1]+"-"+fa[0]+"','YYYY-MM-DD') and to_date(survey_timings,'YYYY-MM-DD')<=to_timestamp('"+ta[2]+"-"+ta[1]+"-"+ta[0]+"','YYYY-MM-DD')";
		List<Object[]> l=surveyIssueOrReturnService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
	     return l;
	}
	@RequestMapping(value="/feederDetails",method= {RequestMethod.GET})
	public @ResponseBody Object feederDetails() {
		List<Object[]> l=surveyIssueOrReturnService.getCustomEntityManager("postgresMdas").createNativeQuery("select DISTINCT feederno from spdcl.dtc_master").getResultList();
		return l;
	}
	@RequestMapping(value="/dtDetails",method= {RequestMethod.GET})
	public @ResponseBody Object dtDetails() {
		List<Object[]> l=surveyIssueOrReturnService.getCustomEntityManager("postgresMdas").createNativeQuery("select DISTINCT udtccode from spdcl.dtc_master").getResultList();
		return l;
	}
	@RequestMapping(value="/consumerDetails",method= {RequestMethod.GET})
	public @ResponseBody Object consumerDetails() {
		List<Object[]> l=surveyIssueOrReturnService.getCustomEntityManager("postgresMdas").createNativeQuery("select DISTINCT kno from meter_data.survey_output").getResultList();
		return l;
	}
	
	@RequestMapping(value="/surveyorExistOrNot/{sn}",method= {RequestMethod.GET})
	public @ResponseBody Object surveyorExistOrNot(@PathVariable String sn) {
		String sql="\r\n" + 
				"select surveyorname from meter_data.surveyordetails where surveyorname like '"+sn+"' ";
		List<Object[]> l=surveyorDetailsService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		if(l.isEmpty()) {
			return false;	
		}
		return true;
	}
	@RequestMapping(value="/surveyorDetails/{id}",method= {RequestMethod.GET})
	public @ResponseBody Object surveyorDetails(@PathVariable String id) {
		List<SurveyorDetailsEntity> l=surveyorDetailsService.surveyorDetails(id);
		return l;
		
	}
	@RequestMapping(value="/meterSequenceVerification/{locID}/{ssn}/{esl}/{ms}",method= {RequestMethod.GET})
	public @ResponseBody Object meterSequenceVerification(@PathVariable String locID,@PathVariable String ssn,@PathVariable String esl,@PathVariable String ms) {
		String sl=meterInventoryService.sequenceMeterResponse(ssn, esl);
		if(sl.equalsIgnoreCase("Wrong Sequence Inputs")) {
			return sl;
		}
		else if(sl.equalsIgnoreCase("Application allow maximum 100 meters")) {
			return sl;
		}
		else {
			if(ms.equalsIgnoreCase("ISSUED")) {
				ms="INSTOCK";
			}
			else if(ms.equalsIgnoreCase("INSTOCK")){
				ms="ISSUED";
			}
			String sql="select meterno from meter_data.meter_inventory where meterno in ("+sl+") and store_loc='"+locID+"' and meter_status='"+ms+"'";
			List<Object[]> l=meterInventoryService.getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			String[] ts=sl.split(",");
			int tsl=ts.length;
			int msl=l.size();
			if(tsl==msl) {
				return sl.replace("'", "");
			}
			else {
				return "Few Meters are not avialble in this store";
			}
		}
		
	}
	@RequestMapping(value="/reviewData",method= {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object reviewSave(HttpServletRequest request ,@RequestParam String rid,@RequestParam String qual,@RequestParam String remark ) {
		InstallationReview ir=new InstallationReview();
		String[] ridsa=rid.split("#");
		ir.setConsumerNo(ridsa[0]);
		ir.setOldMtrNo(ridsa[1]);
		ir.setNewMtrNo(ridsa[2]);
		ir.setReviewedBy(request.getSession().getAttribute("username")+"");
		ir.setReviewTime(new Timestamp(System.currentTimeMillis()));
		ir.setPhotoQua(qual);
		irs.save(ir);
		return "succ";
	}
	
	@RequestMapping(value="/dtMeterReplacementRpt",method = { RequestMethod.GET, RequestMethod.POST })
	public String dtMeterReplacementRpt(HttpServletRequest request,ModelMap model) {
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("results", "notDisplay");
		return "dtMeterReplacementRpt";
	}
	
	@RequestMapping(value="/changeMeter",method = { RequestMethod.GET, RequestMethod.POST })
	public String changeMeter(HttpServletRequest request,ModelMap model) {
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("results", "notDisplay");
		return "changeMeter";
	}
	
	@RequestMapping(value="/meterChangeRpt",method = { RequestMethod.GET, RequestMethod.POST })
	public String meterChangeRpt(HttpServletRequest request,ModelMap model) {
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("results", "notDisplay");
		return "meterChangeRpt";
	}
	
	@RequestMapping(value="/viewChangeMeterData",method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List viewChangeMeterData(HttpServletRequest request,ModelMap model) {
		String zone=request.getParameter("zone");
		String circle=request.getParameter("circle");
		String division=request.getParameter("division");
		String subdivision=request.getParameter("subdivision");
		String townCode=request.getParameter("town").split("-")[0];
		String townName=request.getParameter("town").split("-")[1];
		String meterType=request.getParameter("meterType");
		System.out.println("zone=: "+zone+" circle=: "+circle+" division=: "+division+" subdivision=: "+subdivision+" meterType=: "+meterType);
//		System.out.println("townCode =: "+townCode);
//		System.out.println("townName =: "+townName);
		
        List<?> list = null;
		
		if("FEEDER METER".equalsIgnoreCase(meterType)) {
			list = feederMasterService.getFeederMeterChangeDetails(townCode);
			
		}else if("BOUNDARY METER".equalsIgnoreCase(meterType)) {
			list = feederMasterService.getBoundaryMeterChangeDetails(townCode);
			
		}else {
			list = feederMasterService.getDTMeterChangeDetails(townCode);
			
		}
		
		
		
		return list;
	}
	
	
	@RequestMapping(value="/viewMeterDetails",method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List viewMeterDetails(HttpServletRequest request,ModelMap model) {
		List<FeederMasterEntity> zoneList = feederMasterService.getDistinctZone();

		//System.out.println("In consumer master Data Modification--");
		
		String zone=request.getParameter("zone");
		String circle=request.getParameter("circle");
		String division=request.getParameter("division");
		String subdivision=request.getParameter("subdivision");
		String townCode=request.getParameter("town");
		//String townName=request.getParameter("town").split("\\,")[1];
		String meterType=request.getParameter("meterType");
		//System.out.println("zone=: "+zone+" circle=: "+circle+" division=: "+division+" subdivision=: "+subdivision+" meterType=: "+meterType);
	//System.out.println("townCode =: "+townCode);
	//	System.out.println("townName =: "+townName);
		
		List<?> list = feederMasterService.getMeterDetails(zone,circle,division,subdivision,meterType,townCode);
		
		return list;
	}
	
	
	@RequestMapping(value="/getOldMtrDataforMtrChange",method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getOldMtrDataforMtrChange(HttpServletRequest request,ModelMap model) {

		//System.out.println("In consumer master Data Modification--");
		
		//String id=request.getParameter("id");
		//String metertype=request.getParameter("metertype");
		String meterno=request.getParameter("meterno");
		return feederMasterService.getOldMtrDataforMtrChange(meterno);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value="/meterChangeProcess",method={RequestMethod.GET,RequestMethod.POST})	
	public @ResponseBody String meterChangeProcess(HttpServletRequest request) throws java.text.ParseException{
		Timestamp time=new Timestamp(System.currentTimeMillis());
		Date d1=new Date();
		String today=new SimpleDateFormat("dd-MM-yyy").format(d1);
		String msg="NoVal";
		String reason=request.getParameter("reasonid");
		String uoldmtrno=request.getParameter("uoldmtrno");
		String umeterMakeId=request.getParameter("umeterMakeId");
		String uoldmf=request.getParameter("uoldmf");
		String uloccode=request.getParameter("uloccode");
		String ulocname=request.getParameter("ulocname");
		String uoldphase=request.getParameter("uoldphase");
		String oldmtrkwh=request.getParameter("oldmtrkwh");
		String oldmtrkvh=request.getParameter("oldmtrkvh");
		String unewmtrno=request.getParameter("unewmtrno");
		String unewmeterMakeId=request.getParameter("unewmeterMakeId");
		String unewcapacity=request.getParameter("unewcapacity");
		String unewmf=request.getParameter("unewmf");
		String unewmtrkwh=request.getParameter("unewmtrkwh");
		String unewmtrkvh=request.getParameter("unewmtrkvh");
		String installedDate=request.getParameter("installedDate");
		String schemeId=request.getParameter("schemeId");
		String releasedDate=request.getParameter("releasedDate");
		String meterConnType=request.getParameter("meterConnType");
		String mdkw=request.getParameter("mdkw");
//		System.out.println("releasedDate--"+releasedDate);
//		System.out.println("meterConnType--"+meterConnType);
		Timestamp mtrChangeTime=null;
		
		try {
		      DateFormat formatter;
		      formatter = new SimpleDateFormat("dd-MM-yyyy");
		      Date date;
			  date = (Date) formatter.parse(installedDate);
			  mtrChangeTime = new Timestamp(date.getTime());
//			  System.out.println(mtrChangeTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
		try {
			MasterMainEntity master=masterMainService.getFeederData(uoldmtrno).get(0);
		//	System.out.println("master data-->"+master);
			
			MeterChangeTransHistory change=new MeterChangeTransHistory();
			change.setOldmeterno(uoldmtrno);
			change.setNewmeterno(unewmtrno);
			change.setAccno(uloccode);
			change.setConsumername(ulocname);
			change.setEntryby(request.getSession().getAttribute("username")+"");
			if(oldmtrkwh!="" && !oldmtrkwh.equalsIgnoreCase("null")){
			change.setLastinstaneouskwh(Double.parseDouble(oldmtrkwh));
			}
			if(unewmtrkwh!="" && !unewmtrkwh.equalsIgnoreCase("null")){
			change.setInitialreading(Double.parseDouble(unewmtrkwh));
			}
			
			if(mtrChangeTime!=null){
			change.setMtrchangedate(mtrChangeTime);
			}
			change.setEntrydate(time);
			change.setReason(reason);
			change.setOldmf(uoldmf);
			change.setOldkvh(oldmtrkvh);
			change.setNewmf(unewmf);
			change.setNewkvh(unewmtrkvh);
			change.setOldmtrmake(umeterMakeId);
			change.setOldphase(uoldphase);
			change.setNewmtrmake(unewmeterMakeId);
			change.setNewcapacity(unewcapacity);
			change.setIpdsscheme(schemeId);
			JSONObject obj=new JSONObject();
			if(master.getHes_type().equalsIgnoreCase("ANALOGICS")){
			//if(true){ // for testing
			change.setSync_analogics("1");
			obj.put("NewMeterInitialKvah", change.getNewkvh());
			obj.put("IpdsScheme", "IPDS");
			obj.put("OldMaximumDemand", mdkw);
			obj.put("NewMeterType", meterConnType);
			obj.put("OldMeterNumber", change.getOldmeterno());
			obj.put("OldKvah", change.getOldkvh());
			obj.put("NewMeterMake",change.getOldmtrmake());
			obj.put("NewMeterNumber", change.getNewmeterno());
			obj.put("OldKwh", change.getLastinstaneouskwh());
			obj.put("NewMeterInitialKwh", change.getInitialreading());
			obj.put("OldKwhDialCompletion", "");
			obj.put("InstalledDate", installedDate);
			obj.put("OldMeterReleaseDate", releasedDate);
			obj.put("OldKvahDialCompletion", "");
			obj.put("NewMeterCapacity", change.getNewcapacity());
			obj.put("NewMeterMf", change.getNewmf());
			}
			
			
			else if(master.getHes_type().equalsIgnoreCase("GENUS")){
				System.out.println("Call Genus Service");
				msg="Genus Service Under Progress";
				System.out.println(msg);
				change.setSync_genus("0");
				obj.put("NewMeterInitialKvah", change.getNewkvh());
				obj.put("IpdsScheme", "IPDS");
				obj.put("OldMaximumDemand", mdkw);
				obj.put("NewMeterType", meterConnType);
				obj.put("OldMeterNumber", change.getOldmeterno());
				obj.put("OldKvah", change.getOldkvh());
				obj.put("NewMeterMake",change.getOldmtrmake());
				obj.put("NewMeterNumber", change.getNewmeterno());
				obj.put("OldKwh", change.getLastinstaneouskwh());
				obj.put("NewMeterInitialKwh", change.getInitialreading());
				obj.put("OldKwhDialCompletion", "");
				obj.put("InstalledDate", installedDate);
				obj.put("OldMeterReleaseDate", releasedDate);
				obj.put("OldKvahDialCompletion", "");
				obj.put("NewMeterCapacity", change.getNewcapacity());
				obj.put("NewMeterMf", change.getNewmf());
			}
			
			
			String result=meterChangeTransHistoryService.callService(obj,master.getHes_type());
			System.out.println("Json Request for GENUS: " + obj.toString());
			//String result=meterChangeTransHistoryService.callService(obj,"ANALOGICS");  //testing
			//String result="error";//Testing in local env.
			
			
			if(result.equalsIgnoreCase("success")){
//				System.out.println("anologics--");
				master.setOld_mtr_no(uoldmtrno);
				master.setMtrno(unewmtrno);
				master.setMtr_change_date(installedDate);
				masterMainService.update(master);
				if(master.getFdrcategory().equalsIgnoreCase("DT")){
				System.out.println("in dtttttttt");
					List<DtDetailsEntity> dt=dtDetailsService.getDtDetailsByMeterno(uoldmtrno);
					if(dt!=null){
						for (DtDetailsEntity dtDetailsEntity : dt) {
						
								dtDetailsEntity.setOlddtmtr(uoldmtrno);
								dtDetailsEntity.setMeterno(unewmtrno);
								dtDetailsEntity.setMeterchangeflag(1);
								dtDetailsEntity.setMeterchangedate(mtrChangeTime);
								dtDetailsEntity.setUpdatedate(time);
								dtDetailsEntity.setUpdate(String.valueOf(request.getSession().getAttribute("username")));
								dtDetailsService.update(dtDetailsEntity);
							
						}	
					
					}
				}
				
				
				 if((master.getFdrcategory().equalsIgnoreCase("FEEDER METER"))
						 ||(master.getFdrcategory().equalsIgnoreCase("BOUNDARY METER"))){
// 				   System.out.println("feddddd--");
						List<FeederEntity> fdr=feederDetailsService.getFeederByMeterno(uoldmtrno);
						if(fdr!=null){
							for (FeederEntity feederEntity : fdr) {
								feederEntity.setOldfdrmtrno(uoldmtrno);
								feederEntity.setMeterchangedate(mtrChangeTime);
								feederEntity.setMeterchangeflag(1);
								feederEntity.setMeterno(unewmtrno);
								feederEntity.setUpdateby(request.getSession().getAttribute("username")+"");
								feederEntity.setUpdatedate(time);
								feederDetailsService.update(feederEntity);
							}
						}
					}
			
				meterChangeTransHistoryService.save(change);
				
				int count=meterInventoryService.updateMeterStatus(unewmtrno, "INSTALLED");
				int count1=meterInventoryService.updateMeterStatus(uoldmtrno, "DELETED");
				
//				System.out.println("count--"+count);
				msg="MeterNo Changed Successfully";
			}
			else {
				
				msg="Something error occured MeterNo Not Changed Successfully";
			}
			
		} catch (Exception e) {
			msg="Error While changing Meter";
			e.printStackTrace();
		}
		return msg;
	}
	
	@RequestMapping(value="/ChangeMeterPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void ChangeMeterPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String division=request.getParameter("div");
		String subdivision=request.getParameter("subdiv");
		String towncode=request.getParameter("town").split("\\,")[0];
		//String townname=request.getParameter("town").split("\\,")[1];
		String metertype=request.getParameter("meterType");
		//System.out.println(zone+"----"+circle+"----"+division+"----"+subdivision+"----"+towncode+"----"+townname+"---"+metertype);
		
		String zne="",crcl="",dvn="",sdiv="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL")) 
		{
			crcl="%";
		}else {
			crcl=circle;
		}
		if(division.equalsIgnoreCase("ALL"))
		{
			dvn="%";
		}else {
			dvn=division;
		}
		if(subdivision.equalsIgnoreCase("ALL"))
		{
			sdiv="%";
		}else {
			sdiv=subdivision;
		}
		
		feederMasterService.getChangeMeterPDF(request, response, zne, crcl, dvn, sdiv, metertype, towncode);
	}
	
	@RequestMapping(value="/FeederMeterPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void FeederMeterPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{  
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String division=request.getParameter("div");
		String subdivision=request.getParameter("subdiv");
		String towncode=request.getParameter("town").split("-")[0];
		String townname=request.getParameter("town").split("-")[1];
		String metertype=request.getParameter("meterType");
		//System.out.println(zone+"----"+circle+"----"+division+"----"+subdivision+"----"+towncode+"----"+"---"+metertype);
		
		String zne="",crcl="",dvn="",sdiv="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL")) 
		{
			crcl="%";
		}else {
			crcl=circle;
		}
		if(division.equalsIgnoreCase("ALL"))
		{
			dvn="%";
		}else {
			dvn=division;
		}
		if(subdivision.equalsIgnoreCase("ALL"))
		{
			sdiv="%";
		}else {
			sdiv=subdivision;
		}
		
		feederMasterService.getFeederMtrPDF(request, response, zne, crcl, dvn, sdiv, towncode,townname, metertype );
	}
	
	@RequestMapping(value="/DTMeterPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void DTMeterPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String division=request.getParameter("div");
		String subdivision=request.getParameter("subdiv");
		String towncode=request.getParameter("town").split("-")[0];
		String townname=request.getParameter("town").split("-")[1];
		String metertype=request.getParameter("meterType");
		//System.out.println(zone+"----"+circle+"----"+division+"----"+subdivision+"----"+towncode+"----"+townname+"---"+metertype);
		
		String zne="",crcl="",dvn="",sdiv="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL")) 
		{
			crcl="%";
		}else {
			crcl=circle;
		}
		if(division.equalsIgnoreCase("ALL"))
		{
			dvn="%";
		}else {
			dvn=division;
		}
		if(subdivision.equalsIgnoreCase("ALL"))
		{
			sdiv="%";
		}else {
			sdiv=subdivision;
		}
		
		feederMasterService.getDTMtrPDF(request, response, zne, crcl, dvn, sdiv, towncode,townname, metertype);
	}
	
	@RequestMapping(value="/BoundaryMeterPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void BoundaryMeterPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{

		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String division=request.getParameter("div");
		String subdivision=request.getParameter("subdiv");
		String towncode=request.getParameter("town").split("-")[0];
		String townname=request.getParameter("town").split("-")[1];
		String metertype=request.getParameter("meterType");
		//System.out.println(zone+"----"+circle+"----"+division+"----"+subdivision+"----"+towncode+"----"+townname+"---"+metertype);
		
		String zne="",crcl="",dvn="",sdiv="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL")) 
		{
			crcl="%";
		}else {
			crcl=circle;
		}
		if(division.equalsIgnoreCase("ALL"))
		{
			dvn="%";
		}else {
			dvn=division;
		}
		if(subdivision.equalsIgnoreCase("ALL"))
		{
			sdiv="%";
		}else {
			sdiv=subdivision;
		}
		
		feederMasterService.getBoundaryMtrPDF(request, response, zne, crcl, dvn, sdiv, towncode,townname, metertype);
	
	}
	
}
