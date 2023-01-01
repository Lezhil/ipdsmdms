package com.bcits.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.AlarmDefinition;
import com.bcits.entity.AssessmentEntity;
import com.bcits.entity.D2Data;
import com.bcits.entity.D3Data;
import com.bcits.entity.D4CdfData;
import com.bcits.entity.D5Data;
import com.bcits.entity.D9Data;
import com.bcits.entity.Master;
import com.bcits.entity.MeterLifeCycleEntity;
import com.bcits.entity.MeterMaster;
import com.bcits.entity.SmsToRecipient;
import com.bcits.mdas.entity.AMIInstantaneousEntity;
import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrBillsEntity.KeyBills;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.service.AlarmDefinitionService;
import com.bcits.service.AlarmHistoryService;
import com.bcits.service.AssessmentsService;
import com.bcits.service.CdfDataService;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.D2DataService;
import com.bcits.service.D3DataService;
import com.bcits.service.D4LoadDataService;
import com.bcits.service.D5DataService;
import com.bcits.service.D9DataService;
import com.bcits.service.MasterService;
import com.bcits.service.MeterLifeCycleService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.SmsToRecipientService;
import com.bcits.utility.MDMLogger;
import com.bcits.utility.SendDocketInfoSMS;
import com.bcits.utility.SendModemAlertViaMail;
import com.bcits.utility.SmsCredentialsDetailsBean;
import com.itextpdf.text.DocumentException;

@Controller
public class MeterDataAcquistionController {

	@Autowired
	private CdfDataService cdfDataService;

	@Autowired
	private D2DataService d2DataService;

	@Autowired
	private MeterMasterService meterMasterService;

	@Autowired
	private MasterService masterService;

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager entityManager;

	@Autowired
	private AssessmentsService assessmentsService;

	@Autowired
	private D5DataService d5DataService;

	@Autowired
	private D4LoadDataService d4LoadDataService;

	@Autowired
	private D3DataService d3DataService;

	@Autowired
	private D9DataService d9DataService;

	@Autowired
	private SmsToRecipientService smsToRecipientService;

	@Autowired
	private AlarmDefinitionService alarmDefinitionService;

	@Autowired
	private MeterLifeCycleService meterLifeCycleService;

	@Autowired
	private AmrInstantaneousService amrInstantaneousService;

	@Autowired
	private FeederMasterService feederService;

	@Autowired
	private MasterMainService MasterMainService;
	@Autowired 
	private VEEController veecon;
	@Autowired
	private ConsumerMasterService consumermaseteService;

	@Autowired
	private FeederMasterService feederMasterService;
	@Autowired
	private AlarmHistoryService alarmHistoryservice;
	
	@RequestMapping(value = "/searchMeter", method = { RequestMethod.POST, RequestMethod.GET })
	public String searchMeter(@ModelAttribute("metermasterSearch") MeterMaster meterMaster, HttpServletRequest request,
			ModelMap model, BindingResult result) {
		MDMLogger.logger.info("In ::::::::::::::::::::::User Serach");
		/*
		 * meterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		 * model.addAttribute("circles",sdoJccService.getAllCircle());
		 */
		return "searchmeter";
	}

	@RequestMapping(value = "/getEventsData/{meterNo}/{selectedDateName}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody List<D5Data> getEventsData(@PathVariable String meterNo, @PathVariable String selectedDateName,
			ModelMap model, HttpServletRequest request) {
		MDMLogger.logger.info("In EventData ");
		System.err.println("event");
		List<D5Data> list = d5DataService.findAll(meterNo, selectedDateName, model);
		return list;
	}

	@RequestMapping(value = "/fullviewmdas", method = { RequestMethod.POST, RequestMethod.GET })
	public String fullView(ModelMap model, HttpServletRequest request) {
		System.out.println("fullView==========" + request.getParameter("fromDate"));
		model.addAttribute("mtrno", request.getParameter("meterNum"));
		model.addAttribute("fromDate", request.getParameter("fromDate"));
		List<MeterMaster> feederData = meterMasterService.getMeterData(request.getParameter("meterNum"),
				request.getParameter("fromDate"), model);
		System.out.println(feederData.size());
		// model.put("mtrFdrList1", feederData);
		model.addAttribute("mtrFdrList1", feederData);
		return "searchmeter";
	}

	@RequestMapping(value = "/phasorDiagram", method = RequestMethod.GET)
	public String phasorDiagram(HttpServletRequest request) {
		return "phasorDiagram";
	}

	@RequestMapping(value = "/exportReadingData", method = { RequestMethod.GET, RequestMethod.POST })
	public String exportReadingData(HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("<===INSIDE EXPORT READING DATA===>");
		// model.put("daysOfMonth",
		// meterMasterService.getNoOfDaysofMonth(Integer.parseInt(cuurentDate.substring(0,
		// 4)),Integer.parseInt(cuurentDate.substring(4, 6))));
		model.put("results", "notDisplay");
		model.put("circleVal", masterService.getDistinctCircle(request));
		model.put("subdivlist", masterService.getAllSubDiv(model, request));
		return "exportReadingData";
	}

	@RequestMapping(value = "/exportReadingDataDetails/{circle}/{subdivision}/{month}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object exportSealEntryDetails(@PathVariable String circle, @PathVariable String subdivision,
			@PathVariable String month, ModelMap model, HttpServletRequest request) {
		MDMLogger.logger.info("circle" + circle + "subdivision" + subdivision + "month" + month);
		return meterMasterService.exportReadingDataDetails(circle, subdivision, month, request);
	}

	@RequestMapping(value = "/getconnectiondetails/{circle}/{subdivision}/{division}/{date}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getconnectiondetails(@PathVariable String circle, @PathVariable String subdivision,
			@PathVariable String division, @PathVariable String date, ModelMap model, HttpServletRequest request) {
		MDMLogger.logger.info("circle" + circle + "subdivision" + subdivision + "division" + division + "date" + date);
		return meterMasterService.meterdetails(circle, subdivision, division, date, request);
	}

	@RequestMapping(value = "/getmodemdata/{circle}/{subdivision}/{division}/{mtrno}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object getmodemdata(@PathVariable String circle, @PathVariable String subdivision,
			@PathVariable String division, @PathVariable String mtrno, ModelMap model, HttpServletRequest request) {
		MDMLogger.logger
				.info("circle" + circle + "subdivision" + subdivision + "division" + division + "mtrno" + mtrno);
		return meterMasterService.modemdetails(circle, subdivision, division, mtrno, request);
	}

	@RequestMapping(value = "/updateMeterlifecycle/{mtrno}/{accno}/{finalkwh}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object updateMeterlifecycle(@PathVariable String mtrno, @PathVariable String accno,
			@PathVariable String finalkwh, ModelMap model, HttpServletRequest request)

	{
		MDMLogger.logger.info("mtrno" + mtrno + "accno" + accno + "finalkwh" + finalkwh);
		List<MeterLifeCycleEntity> list = meterLifeCycleService.searchByAccno(mtrno, accno);
		System.err.println(list.size());
		if (list.size() > 0) {
			MeterLifeCycleEntity lifeCycleEntity = meterLifeCycleService.find(list.get(0).getId());
			lifeCycleEntity.setFinal_reading(Double.parseDouble(finalkwh));
			lifeCycleEntity.setDisconn_date(new Date());
			lifeCycleEntity.setTotal_reading(Double.parseDouble(finalkwh) - list.get(0).getInitial_reading());
			lifeCycleEntity.setMeter_status("INACTIVE");
			meterLifeCycleService.update(lifeCycleEntity);
		}
		return list;
	}

	@RequestMapping(value = "/administrativeReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String administrativeReport(HttpServletRequest request, ModelMap model) {
		String month = request.getParameter("month");
		String circle = request.getParameter("circle");
		MDMLogger.logger.info("billMonth===>" + month + "==circle=" + circle);
		if (month != null && !month.isEmpty() && month.trim() != "") {
			System.err.println("else if month");
		} else if (circle != null && !circle.isEmpty() && circle.trim() != "" && month != null && !month.isEmpty()
				&& month.trim() != "") {
			MDMLogger.logger.info("COMING INSIDE /if");
			model.put("key", "Yes");
		} else {
			MDMLogger.logger.info("COMING INSIDE /Else");
			model.put("key", "No");
			Date billDate = new Date();
			SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
			month = sdfBillDate.format(billDate);
		}
		model.put("dashbo", meterMasterService.getAdministrativeDetails(month, model, request));
		model.put("sectionDash", meterMasterService.viewCirclewiseDetails(circle, month, model, request));
		model.addAttribute("subDivisionName", circle);
		model.addAttribute("selectedMonth", month);
		return "administrativeReport";
	}

	String result1 = "";

	@RequestMapping(value = "/assesmentsNew", method = { RequestMethod.GET, RequestMethod.POST })
	public String assesmentsNew(HttpServletRequest request, ModelMap model) {
		try {
			model.put("billmonthVal", assessmentsService.getDistinctBillmonth());
			model.put("circleVal", assessmentsService.getDistinctCircle());
			model.put("categoryval", assessmentsService.getDistinctCategory());
			model.put("tamperTypeVal", assessmentsService.getDistinctTamperType());
			model.addAttribute("result", "notDisplay");
			if (result1.equalsIgnoreCase("Data parsed succesfully")) {
				model.addAttribute("result", "Data parsed succesfully");
			} else if (result1.equalsIgnoreCase("Please try Again")) {
				model.addAttribute("result", "Please Try Again!.");
			}
			result1 = "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "assesments";
	}

	@RequestMapping(value = "/parseassesments", method = { RequestMethod.GET, RequestMethod.POST })
	public String parseassesments(HttpServletRequest request, ModelMap model) {
		System.out.println("Inside parseassesments method ");
		String month = request.getParameter("billmonth");
		System.out.println("month---------------- " + month);
		result1 = cdfDataService.findData(month, model, request);

		model.addAttribute("result", "notDisplay");
		return "redirect:/assesmentsNew";
	}

	@RequestMapping(value = "/assesmentReportDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public String assesmentReportDetails(HttpServletRequest request, ModelMap model) {
		// @RequestParam("billmonth") String billmonth,@RequestParam("circle") String
		// circle,@RequestParam("category") String category,@RequestParam("tamperType")
		// String tamperType,@RequestParam("meterNo") String meterNo,

		String billmonth = request.getParameter("billmonth");
		String circle = request.getParameter("circle");
		String category = request.getParameter("category");
		String tamperType = request.getParameter("tamperType");
		String meterNo = request.getParameter("meterNo");
		System.out.println(
				"----" + billmonth + " " + circle + " " + category + " " + tamperType + "meterNo-- " + meterNo);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			List<AssessmentEntity> d5data = assessmentsService.getAssesmentDetails(billmonth, circle, category,
					tamperType, "UHBVN");
			System.out.println("d5data------------" + d5data.size());
			model.put("eventDetails", d5data);
			model.addAttribute("result", "notDisplay");
			if (d5data.size() > 0) {
				model.put("eventLength", "eventLength");
			} else {
				model.put("eventLength1", "Data not found");

			}
			if (meterNo != null) {
				List<AssessmentEntity> assesmentdat = assessmentsService.getAssesmentMoreDetails(billmonth, circle,
						category, tamperType, meterNo);
				model.put("assesmentdat", assesmentdat);
				model.put("meterNo", meterNo);
				if (assesmentdat.size() > 0) {
					model.put("eventLength2", "eventLength2");
				} else {
					model.put("eventLength21", "eventLength21");
				}
			}
			model.put("billmonthVal", assessmentsService.getDistinctBillmonth());
			model.put("circleVal", assessmentsService.getDistinctCircle());
			model.put("categoryval", assessmentsService.getDistinctCategory());
			model.put("tamperTypeVal", assessmentsService.getDistinctTamperType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("inside");
		return "assesments";
	}

	@RequestMapping(value = "/getInstansData/{mtrNo}/{selectedDateName}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody Object getInstansData(@PathVariable String mtrNo, @PathVariable String selectedDateName,
			HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		System.out.println("inside Instans Data");
		List<D2Data> list = d2DataService.findAll(mtrNo, selectedDateName, model);
		return list;
	}

	@RequestMapping(value = "/getLoadSurveyData/{mtrNo}/{frmDate}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getLoadSurveyData(@PathVariable String mtrNo, @PathVariable String frmDate,
			HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		System.out.println("inside Load Survey Data==>" + frmDate);
		List<D4CdfData> eventData = d4LoadDataService.getLoadSurveyData(mtrNo, frmDate);
		return eventData;
	}

	@RequestMapping(value = "/getBillingDatas/{mtrNo}/{frmDate}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getBillingData(@PathVariable String mtrNo, @PathVariable String frmDate, ModelMap model,
			HttpServletRequest request) {
		MDMLogger.logger.info("In BillingData ");
		List<D3Data> list = d3DataService.getDetailsBasedOnMeterNo(mtrNo, frmDate, model);
		return list;
	}

	@RequestMapping(value = "/getDailyLoadSurveyData/{mtrNo}/{frmDate}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody Object getDailyLoadSurveyData(@PathVariable String mtrNo, @PathVariable String frmDate,
			ModelMap model, HttpServletRequest request) {
		MDMLogger.logger.info("In EventData ");
		List<D9Data> list = d9DataService.findAll(mtrNo, frmDate, model);
		System.err.println(list.size());
		return list;
	}

	@RequestMapping(value = "/alarms", method = { RequestMethod.POST, RequestMethod.GET })
	public String alarms(HttpServletRequest request, ModelMap model) {
		MDMLogger.logger.info("In ::::::::::::::::::::::User Serach");

		return "alarm";
	}

	@RequestMapping(value = "/alarmSetting", method = { RequestMethod.GET, RequestMethod.POST })
	public String alarmSetting(HttpServletRequest request, ModelMap model) {
		List<?> circleList = consumermaseteService.getCircle();
		model.addAttribute("circleList", circleList);
		List<?> ZoneList = feederMasterService.getDistinctZone();
		model.addAttribute("ZoneList", ZoneList);
		//System.out.println("zone is------" + ZoneList);
		List<?> userroleList = masterService.getUserRoleData();
		String discomList1 = "SELECT DISTINCT discom FROM meter_data.amilocation";
		model.addAttribute("userroleList", userroleList);
		String qryRegion = "SELECT DISTINCT ZONE  FROM meter_data.amilocation ORDER BY ZONE ";
		List<?>   zoneList = entityManager.createNativeQuery(qryRegion).getResultList();
		List<?>   discomList = entityManager.createNativeQuery(discomList1).getResultList();
		
		model.put("discomList", discomList);
        model.put("circles", circleList);
        model.put("zoneList", zoneList);
		//System.out.println("user role are-----" + userroleList);
		/*
		 * model.put("divisionList",masterService.getDivisionByCircle(circle,model));
		 * model.put("subdivList",
		 * masterService.getSubdivByDivisionByCircle(circle,division,model));
		 */
		return "alarmSetting";
	}

	@RequestMapping(value = "/showCirclelist/{zone}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> showCirclelist(@PathVariable String zone, HttpServletRequest request, ModelMap model) {
		// System.out.println("inside------");
		List<?> circleList2 = masterService.getCircleByZone(zone, model);
		model.put("circleList", circleList2);
		return circleList2;
	}
	
	@RequestMapping(value = "/getcirclebyzone", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getcirclebyzone(HttpServletRequest request,ModelMap model) {
		// System.out.println("inside------");
		String zone = request.getParameter("zone");
		List<?> circleList = masterService.getcirclebyzone(zone, model);
		model.put("circleList", circleList);
		return circleList;
	}
	
	@RequestMapping(value = "/getdivisionbycircle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getdivisionbycircle(HttpServletRequest request,ModelMap model) {
		// System.out.println("inside------");
		String circle = request.getParameter("circle");
		List<?> divisionList = masterService.getDivisionByCircle(circle, model);
		model.put("divisionList1", divisionList);
		return divisionList;
	}

//	@RequestMapping(value = "/getSubdivByDiv", method = { RequestMethod.GET, RequestMethod.POST })
//	public @ResponseBody List<?> getsubdivisionbycircle(HttpServletRequest request, ModelMap model) {
//		// System.out.println("inside notifyyy------");
//		
//		String circle = request.getParameter("circle");
//		String division = request.getParameter("division");
//
//		List<?> subdivList = masterService.getSubdivByDivisionByCircle(circle, division, model);
//		model.put("subdivList", subdivList);
//		return subdivList;
//	}

	@RequestMapping(value = "/geteventdescription", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> geteventdescription(HttpServletRequest request, ModelMap model) {
		// System.out.println("inside------");
		List<?> li = masterService.geteventdescdata();
		//System.out.println("list size" + li.size());
		return li;
	}

	@RequestMapping(value = "/getUserRoleDetailsData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getUserRoleDetailsData( HttpServletRequest request,
			ModelMap model) {
		String userrole=request.getParameter("userrole");
		//System.out.println("inside user role------");
		List<?> li = masterService.getUserRoledata(userrole);
		//System.out.println("list size" + li.size());
		return li;
	}

	@RequestMapping(value = "/addingAllAlarmData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object addingAllAlarmData(HttpServletRequest request, ModelMap model, HttpSession session) {
		String all_types = request.getParameter("all_types");
		String accno = request.getParameter("accno");
		String notificationParameters = request.getParameter("idaccno");
		String meterloctype = request.getParameter("meteringloctype");
		String notifyloctype = request.getParameter("notifyloctype");
		String notifyDetails=request.getParameter("notificationDetails");

		String userName = session.getAttribute("username").toString();
		String data = all_types;
		String fname = request.getParameter("fname");
		String[] accNum = accno.split(",");
		String[] notificationAccno = notificationParameters.split(",");

		String resp="";
		
		try {
			if(accno.length()!=0){
			JSONArray recs = new JSONArray(all_types.toString());

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			int[] accnolen = new int[accNum.length];
			int[] idaccnolen = new int[notificationAccno.length];

			// alarmDefinitionService.saveMeterLocationAlarms(accNum,recs,fname,list);
			// metering location
			for (int i = 0; i < accnolen.length; i++) {
				List<?>  list=new ArrayList<>();
				if("Consumer".equalsIgnoreCase(meterloctype)){

					Object accNumber="";

				    try{
				    	accNumber = Integer.parseInt(accNum[i]);
				    }catch(NumberFormatException ex){ // handle your exception
				    	 accNumber = accNum[i];
				    }

				list = masterService.getAccnoID(accNumber);
				}
				else if("Dt".equalsIgnoreCase(meterloctype)){
					String dtID=accNum[i];
					list = masterService.getDtDetails(dtID);
				}
				else{
					String fdrId=accNum[i];
					list=masterService.getFdrDetails(fdrId);
					
				}
				Object[] userDetails = (Object[]) list.get(0);
				
                
				for (int j = 0; j < recs.length(); j++) {
					org.json.JSONObject obj = recs.getJSONObject(j);
					String event_id = obj.optString("event_id");
					String priority = obj.optString("priority");
					String val_id = obj.optString("val_id");
					String priority1 = obj.optString("priority1");

					AlarmDefinition alarm = new AlarmDefinition();
					alarm.setEvents(event_id);
					if (priority1 == "" || priority1 == null) {
						alarm.setPriority(priority);
					} else {
						alarm.setPriority(priority1);
					}
					
					alarm.setValidations(val_id);
					alarm.setAlarmname(fname);
					alarm.setEntrydate(timestamp);
					alarm.setEntryby(userName);
					alarm.setLocationid(userDetails[0].toString());
					alarm.setTownId(userDetails[2].toString());
					if(userDetails[1]!=null)
					alarm.setLocationcode(userDetails[1].toString());
					alarm.setLocationtype(meterloctype);
					if("Consumer".equalsIgnoreCase(meterloctype)){
					if(userDetails[2]!=null)
					alarm.setSms(userDetails[2].toString());
					if(userDetails[3]!=null)
					alarm.setEmailids(userDetails[3].toString());
					}
					
					// alarm.setLocationtype(notifyloctype);

					alarmDefinitionService.save(alarm);
					resp="saved";
				}

			}
		}
			//Notification Details
			JSONArray notify_details = new JSONArray(notifyDetails.toString());
			if(!"Consumer".equalsIgnoreCase(notifyloctype)){
			for(int i=0;i<notify_details.length();i++){
				org.json.JSONObject notifyObject=notify_details.getJSONObject(i);
				String designation = notifyObject.optString("acc_num");
				String emailId = notifyObject.optString("email_id");

				String contactNumber = notifyObject.optString("contact_num");
				
				
				List<AlarmDefinition> userDetails=alarmDefinitionService.getAlarmEntityBylocationCode(meterloctype);
				//List<AlarmDefinition> a=alarmDefinitionService.getAlarmEntityByAccnum(designation);
				for(int k=0;k<userDetails.size();k++){
					AlarmDefinition allist=userDetails.get(k);
					int id = allist.getId();
					
					AlarmDefinition alarmEntity =alarmDefinitionService.find(allist.getId());
					//System.out.println(alarmEntity.getLocationid()+"by list===="+alarmEntity.getEvents()+alarmEntity.getLocationtype());
					alarmEntity.setId(id);
					alarmEntity.setEmailids(emailId);
					alarmEntity.setSms(contactNumber);
					alarmDefinitionService.update(alarmEntity);
					//AlarmDefinition alarmNotifyDetails=new AlarmDefinition();
					
				//System.out.println("find==="+al.getAlarmname()+al.getLocationid());
				
				}
				
				
			}
			}
			else{
				
				for(int i=0;i<notify_details.length();i++){
					org.json.JSONObject notifyObject=notify_details.getJSONObject(i);
					String accountNumber = notifyObject.optString("acc_num");
					String emailId = notifyObject.optString("email_id");

					String contactNumber = notifyObject.optString("contact_num");
					
					int acc=Integer.parseInt(accountNumber);
					List<AlarmDefinition> a=alarmDefinitionService.getAlarmEntityByAccnum(accountNumber);
					for(int k=0;k<a.size();k++){
						//Object[] al= (Object[]) a.get(k);
						AlarmDefinition allist=a.get(k);
						int id = allist.getId();
						AlarmDefinition alarmEntity =alarmDefinitionService.find(a.get(k).getId());
						alarmEntity.setId(id);
						alarmEntity.setEmailids(emailId);
						alarmEntity.setSms(contactNumber);
						alarmDefinitionService.update(alarmEntity);
						//AlarmDefinition alarmNotifyDetails=new AlarmDefinition();
						
					//System.out.println("find==="+al.getAlarmname()+al.getLocationid());
					
					}
					
					
				}
			}
					} catch (Exception e) {
			e.printStackTrace();
		}

		return resp;
	}

	@RequestMapping(value = "/getvalidationfailure", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getvalidationfailure(HttpServletRequest request, ModelMap model) {
		// System.out.println("inside------");
		List<?> li = masterService.getvalfaildata();
		return li;
	}

	@RequestMapping(value = "/getConsumercatedata", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getConsumercatedata(HttpServletRequest request, Model model) {

		model.addAttribute("results", "notDisplay");
		String sqlqry = " select DISTINCT(COALESCE(tadesc,'')) as cs from meter_data.consumermaster WHERE tadesc  NOT like '' and  tadesc not like '0'";
		Query query = entityManager.createNativeQuery(sqlqry);
		List li = query.getResultList();
		return li;
	}

	@RequestMapping(value = "/getConsumerDetailsData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getConsumerDetailsData(HttpServletRequest request, Model model) {
		// System.err.println("hii");
		model.addAttribute("results", "notDisplay");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("sdoCode").trim();
		String consumerCatgry = request.getParameter("consumerCatgry").trim();
		String acno = request.getParameter("acno").trim();
		String kno = request.getParameter("kno").trim();
		String mtrno = request.getParameter("mtrno").trim();
		/*
		 * String loctype = request.getParameter("optionsRadios");
		 * System.err.println("location type---------"+loctype);
		 */

		/*
		 * try { AlarmDefinition alarm=new AlarmDefinition();
		 * alarm.setLocationtype(loctype); alarmDefinitionService.customSave(alarm);
		 * //model.addAttribute("results","Recipients added Successfully");
		 * }catch(Exception e) { e.printStackTrace(); }
		 */

		/*String queryTail = "";
		if ("ALL".equalsIgnoreCase(circle)) {
			queryTail = "";
		} else if ("ALL".equalsIgnoreCase(division)) {
			queryTail = " WHERE circle = '" + circle + "'";
		} else if ("ALL".equalsIgnoreCase(subdivision)) {
			queryTail = " WHERE  circle = '" + circle + "' AND division= '" + division + "'";
		} else {
			queryTail = " WHERE  circle = '" + circle + "' AND division= '" + division + "' AND subdivision = '"
					+ subdivision + "' ";
		}
		String sql = "SELECT distinct sdocode from meter_data.master_main" + queryTail;
		//System.err.println("---query feeder sun station---:" + sql);

		List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();*/
		List<?> sdocode=alarmHistoryservice.getAllOfficeCodes(circle, division, subdivision);
		String subcode = "";
		for (Object item : sdocode) {
			subcode += "'" + item + "',";
		}
		if (subcode.endsWith(",")) {
			subcode = subcode.substring(0, subcode.length() - 1);
		}
		String finalQury = "";
		String sqlqry = "select mm.id,mm.circle,mm.division,mm.subdivision,cm.accno,cm.kno,cm.name,COALESCE(cm.tadesc,''),cm.meterno "
				+ "from meter_data.consumermaster cm,meter_data.master_main mm WHERE mm.sdocode = cm.sdocode and mm.accno=cm.accno"
				+ " AND cm.sdocode  in (" + subcode
				+ ") AND cm.accno not in (SELECT location_code FROM meter_data.alarm_definition WHERE location_type='Consumer')";
		if (!consumerCatgry.isEmpty()) {
			sqlqry += " and cm.tadesc='" + consumerCatgry + "'";
		}
		if (!acno.isEmpty()) {
			sqlqry += " and cm.accno='" + acno + "'";
		}
		if (!kno.isEmpty()) {
			sqlqry += " and cm.kno='" + kno + "'";
		}
		if (!mtrno.isEmpty()) {
			sqlqry += " and cm.meterno='" + mtrno + "'";
		}

		finalQury += sqlqry + ";";
		//System.err.println("Final query---:" + finalQury);
		List<?> list = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}

	@RequestMapping(value="/getDtData",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getDtData(HttpServletRequest request, HttpServletResponse response){
		List<?> list=new ArrayList<>();
		//String zone="All";
		String zone = request.getParameter("zone").trim();
		String crdt="";
		String circle = request.getParameter("circle").trim();
		/*String division = request.getParameter("division").trim();
		String subdivision = request.getParameter("sdoCode").trim();*/
		String town = request.getParameter("town").trim();
		String dtcode = request.getParameter("dtcode").trim();
		String crossdt = request.getParameter("crossdt").trim();
		String mtrno = request.getParameter("dtmtrno").trim();
	
		System.out.println("dtcode----" + dtcode);
		System.out.println("mtrno----" + mtrno);
	
		String divQry=veecon.getSubdivisionQuery(zone,circle,town);
		
        List<?> sdocode = entityManager.createNativeQuery(divQry).getResultList();
		//System.out.println("hi=="+sdocode);
		String subcode="";
		for(Object item : sdocode){
			if(item !=null){
			subcode+="'"+item+"',";	
		}
		}
		if(subcode.endsWith(","))
		{
			subcode = subcode.substring(0,subcode.length() - 1);
		}
	//	System.out.println(subcode);
			
		if(crossdt.equals("true")) {
			crdt="1";
		}
		else {
			crdt="0";
		}
		String finalQury="";
		String sqlqry="select  distinct on (cm.meterno) mm.zone,mm.circle,mm.division,mm.subdivision,CAST(cm.crossdt AS varchar),COALESCE(cm.dttpid,''),\n" +
				"cm.dtcapacity,cm.meterno,vv.town_ipds from meter_data.dtdetails cm,meter_data.master_main mm, meter_data.amilocation vv \n" +
				"WHERE  cast(mm.sdocode as INT) = cm.officeid and \r\n" + 
				"vv.tp_towncode=cm.tp_town_code and\n" +
				"cm.meterno=mm.mtrno and\n" +
				"mm.sdocode   in (" + subcode + ") AND \n" +
				"mm.accno not in (SELECT location_code FROM meter_data.alarm_definition WHERE location_type='DT')  ";

		     if (!dtcode.isEmpty()) { 
			  sqlqry+= " and cm.dttpid='"+dtcode+"'";
			  } if (!crdt.isEmpty()) {
				  sqlqry+= " and CAST(cm.crossdt AS varchar)='"+crdt+"'"; 
			  } if (!mtrno.isEmpty()) { 
				  sqlqry+= " and cm.meterno='"+mtrno+"'";  
			  }
		finalQury+=sqlqry+";";
		System.err.println("Final DT query---:" + finalQury);
		list  = entityManager.createNativeQuery(finalQury).getResultList();
				
		return list;
	}
		@RequestMapping(value="/getFdrData",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody List<?> getFederData(HttpServletRequest request){
			List<?> list=new ArrayList<>();
			String zone= request.getParameter("zone");
			String crfdr="";
			String circle = request.getParameter("circle").trim();
			/*String division = request.getParameter("division").trim();*/
/*			String subdivision = request.getParameter("sdoCode").trim();
*/			String fdrcode = request.getParameter("fdrcode").trim();
			String crossfdr = request.getParameter("crossfdr").trim();
			String mtrno = request.getParameter("fdrmtrno").trim();
			String town = request.getParameter("town").trim();
			String divQry= veecon.getSubdivisionQueryFrFeeder(zone, circle, town);
		//	System.out.println(circle+"div-"+division+"sub--"+subdivision+"dtcode=="+fdrcode+"crodd=="+crossfdr+"mtr="+mtrno+"divqry=="+divQry);
			
			
	        List<?> sdocode = entityManager.createNativeQuery(divQry).getResultList();
			//System.out.println("hi=="+sdocode);
			String subcode="";
			for(Object item : sdocode){
				if(item !=null){
				subcode+="'"+item+"',";	
			}
			}
			if(subcode.endsWith(","))
			{
				subcode = subcode.substring(0,subcode.length() - 1);
			}
		//	System.out.println(subcode);
				
			if(crossfdr.equals("true")) {
				crfdr="1";
			}
			else {
				crfdr="0";
			}
			String finalQury="";
			String sqlqry="select mm.zone,mm.circle,mm.division,mm.subdivision,CAST(cm.crossfdr AS varchar),COALESCE(cm.tp_fdr_id,''),\n" +
					"cm.meterno,vv.town_ipds from meter_data.feederdetails cm,meter_data.master_main mm,meter_data.amilocation vv\n" +
					"WHERE cast(mm.sdocode as INT) = cm.officeid and vv.tp_towncode=cm.tp_town_code  and \n" +
					"cm.meterno=mm.mtrno and\n" +
					"mm.sdocode  in ("+subcode+") AND \n" +
					"mm.accno not in (SELECT location_code FROM meter_data.alarm_definition WHERE location_type='FEEDER METER')";

			     if (!fdrcode.isEmpty()) { 
				  sqlqry+= " and cm.tp_fdr_id='"+fdrcode+"'";
				  } if (!crfdr.isEmpty()) {
					  sqlqry+= " and CAST(cm.crossfdr AS varchar)='"+crfdr+"'"; 
				  } if (!mtrno.isEmpty()) { 
					  sqlqry+= " and cm.meterno='"+mtrno+"'";  
				  }
			finalQury+=sqlqry+";";
			System.out.println("final query---"+finalQury);
			list  = entityManager.createNativeQuery(finalQury).getResultList();
			
			//System.err.println("Final Feeder query---:" + finalQury+"\n==="+list.size());
		
			return list;
		
	}
	@RequestMapping(value = "/getNotifySettingDetailsData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<?> getNotifySettingDetailsData(HttpServletRequest request, Model model) {
		// System.err.println("notify setting----");
		String circle = request.getParameter("circle");
		String division = request.getParameter("division");
		String subdivision = request.getParameter("sdoCode").trim();
		String consumerCatgry = request.getParameter("consumerCatgry").trim();
		String acno = request.getParameter("acno").trim();
		String kno = request.getParameter("kno").trim();
		String mtrno = request.getParameter("mtrno").trim();

		String queryTail = "";
		if ("ALL".equalsIgnoreCase(circle)) {
			queryTail = "";
		} else if ("ALL".equalsIgnoreCase(division)) {
			queryTail = " WHERE circle like '" + circle + "' and sdocode is not null ";
		} else if ("ALL".equalsIgnoreCase(subdivision)) {
			queryTail = " WHERE  circle like  '" + circle + "' AND division like  '" + division + "' and sdocode is not null ";
		} else {
			queryTail = " WHERE  circle like  '" + circle + "' AND division like  '" + division + "' AND subdivision like '"
					+ subdivision + "' and sdocode is not null ";
		}
		String sql = "SELECT distinct sdocode from meter_data.master_main" + queryTail;
		//System.err.println("---query feeder sun station---:" + sql);

		List<?> sdocode = entityManager.createNativeQuery(sql).getResultList();
		//System.out.println("hi==" + sdocode);
		String subcode = "";
		for (Object item : sdocode) {
			subcode += "'" + item + "',";
		}
		if (subcode.endsWith(",")) {
			subcode = subcode.substring(0, subcode.length() - 1);
		}
		String finalQury = "";
		String sqlqry = "select mm.id,mm.circle,mm.division,mm.subdivision,cm.accno,cm.kno,cm.name,COALESCE(cm.tadesc,''),cm.meterno,cm.phoneno,cm.email "
				+ "from meter_data.consumermaster cm,meter_data.master_main mm WHERE mm.sdocode = cm.sdocode and mm.accno=cm.accno"
				+ " AND cm.sdocode  in (" + subcode
				+ ") AND cm.accno not in (SELECT location_code FROM meter_data.alarm_definition WHERE location_type='Consumer')";
		if (!consumerCatgry.isEmpty()) {
			sqlqry += " and cm.tadesc='" + consumerCatgry + "'";
		}
		if (!acno.isEmpty()) {
			sqlqry += " and cm.accno='" + acno + "'";
		}
		if (!kno.isEmpty()) {
			sqlqry += " and cm.kno='" + kno + "'";
		}
		if (!mtrno.isEmpty()) {
			sqlqry += " and cm.meterno='" + mtrno + "'";
		}

		finalQury += sqlqry + ";";
		//System.err.println("Final notify query---:" + finalQury);
		List<?> list = entityManager.createNativeQuery(finalQury).getResultList();
		return list;
	}

	@RequestMapping(value = "/getDiscomUsersList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getDiscomUsersList(HttpServletRequest request) {
		//System.out.println("inside discom list-----");
		List<?> list = masterService.getDiscomList();
		return list;
	}

	@RequestMapping(value = "/alarms/{valtype}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object alarmsdata(HttpServletRequest request, @PathVariable String valtype) {
		MDMLogger.logger.info("In ::::::::::::::::::::::User Serach");
		List<Object[]> l = d5DataService.gettotalAlaramData(valtype);
		return l;
	}

	@RequestMapping(value = "/smsconfig", method = { RequestMethod.GET, RequestMethod.POST })
	public String smsconfig(@ModelAttribute("smstorecipients") SmsToRecipient recipient, BindingResult bindingResult,
			ModelMap model, HttpServletRequest request) {
		smsToRecipientService.findAllRecipients(model);
		model.put("circle", masterService.getCircleForMrWiseTotal());
		int value = 0;
		if (request.getParameter("flag") != null) {

			value = Integer.parseInt(request.getParameter("flag"));
			if (value == 0) {
				model.addAttribute("results", "Recipient Deleted Successfully.");
			} else if (value == 1) {
				model.addAttribute("results", "Error While Deleting ....!!!");
			}
		} else {
			model.addAttribute("results", "notDisplay");
		}
		return "smsusers";
	}

	@RequestMapping(value = "/getdivBasedOncir", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getdivBasedOncir(HttpServletRequest request, ModelMap model) {
		System.out.println("mrnames for circle");
		String circle = request.getParameter("param");
		return masterService.getALLSubdivByCIR(circle);
	}

	@RequestMapping(value = "/getsubdivBasedOncir", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getsubdivBasedOncir(HttpServletRequest request, ModelMap model) {
		System.out.println("mrnames for circle");
		String div = request.getParameter("param");
		String circle = request.getParameter("circle");
		return masterService.getDistinctSubdivision(circle, div, request);
	}

	@RequestMapping(value = "/addNewRecipients", method = { RequestMethod.POST, RequestMethod.GET })
	public String addNewRecipients(@ModelAttribute("smstorecipients") SmsToRecipient recipient,
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		MDMLogger.logger.info("In ::::::::::: Add And Update JVVNL USERS ::::::::: ");

		System.out.println("userId----" + recipient.getId());
		int uid = recipient.getId();

		if (uid == 0) {
			smsToRecipientService.customsaveBySchema(recipient, "postgresMdas");
			model.addAttribute("results", "Recipients added Successfully");
		} else {
			smsToRecipientService.customupdateBySchema(recipient, "postgresMdas");
			model.addAttribute("results", "Recipients updated Successfully");
		}
		model.addAttribute("newJvvnlUsers", new SmsToRecipient());
		smsToRecipientService.findAllRecipients(model);

		return "smsusers";
	}

	@RequestMapping(value = "/editRecipientsList/{opera}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object editRecipientsList(@PathVariable int opera, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		MDMLogger.logger.info("In Edit Jvvnl User Details ----" + opera);
		model.addAttribute("newJvvnlUsers", new SmsToRecipient());
		SmsToRecipient jvvnlUsers = smsToRecipientService.customfind(opera);
		smsToRecipientService.findAllRecipients(model);
		return jvvnlUsers;
	}

	@RequestMapping(value = "/smsUserList/{opera}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object smsUserList(@PathVariable int opera, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {

		MDMLogger.logger.info("In Delte Jvvnl User Details ----" + opera);
		model.put("circle", masterService.getCircleForMrWiseTotal());

		String val1 = "deleted";
		String val2 = "notDeleted";
		int UsrId = opera;
		smsToRecipientService.delete(UsrId);
		SmsToRecipient entity = smsToRecipientService.customfind(UsrId);

		if (entity == null) {

			return val1;
		} else {
			return val2;
		}

	}

	// @Scheduled(cron="0 0/1 * * * ?")
	public void SendEmail() {
		System.err.println("inside cron");
		String meterno, eventtime, circle, division, subdivision;
		List<Object[]> list = d5DataService.geteventdata();
		for (Iterator<?> iterator1 = list.iterator(); iterator1.hasNext();) {
			System.out.println("insss for");
			Object[] obj = (Object[]) iterator1.next();
			meterno = obj[0] + "";
			eventtime = obj[1] + "";
			circle = obj[2] + "";
			division = obj[3] + "";
			subdivision = obj[4] + "";
			System.err.println(division + "" + subdivision);
			List<SmsToRecipient> list2 = smsToRecipientService.getDataBySubdiv(division, subdivision);
			String name = list2.get(0).getR_name();
			String to = list2.get(0).getR_mail();
			String cc = "iswarya.kasukurthy@bcits.in";
			String header = "Power Failure for meterno " + meterno;
			String body = "<html>" + "<style type=\"text/css\">" + "table.hovertable {"
					+ "font-family: verdana,arial,sans-serif;" + "font-size:11px;" + "color:#333333;"
					+ "border-width: 1px;" + "border-collapse: collapse;" + "}" + "table.hovertable th {"
					+ "background-color:#c3dde0;" + "border-width: 1px;" + "padding: 8px;" + "border-style: solid;"
					+ "border-color: #394c2e;" + "}" + "table.hovertable tr {" + "background-color:#88ab74;" + "}"
					+ "table.hovertable td {" + "border-width: 100%;" + "padding: 8px;" + "border-style: collapse;"
					+ "border-color: #394c2e;" + "}" + "</style><body>"
					+ "<h3  align=\"left\"  style=\"background-color:#1ac6ff;\">Event Report</h3> <br/><br /> Dear <b>"
					+ name + ",</b><br/> <br/> " + "<table border=\"1\">" + "<tr><th>METERNO</th>"
					+ "<th>EVENT DATE </th>" + "<th>EVENT TYPE </th>" + "<th>CIRCLE </th>" + "<th>DIVISION</th>"
					+ "<th>SUBDIVISION</th>" + "</tr>"

					+ "<tr>" + "<td>" + meterno + "</td>" + "<td>" + eventtime + "</td>" + "<td>" + "POWER FAILURE"
					+ "</td>" + "<td>" + circle + "</td>" + "<td>" + division + "</td>" + "<td>" + subdivision + "</td>"
					+ "</tr></table>"

					+ "<br/><br/>" + "</body></html>" + "<br/><br/>" + "<br/>Thanks,<br/>" + "AMR Team <br/> <br/>";
			new Thread(new SendModemAlertViaMail(to, cc, header, body)).run();
		}
	}

	// @Scheduled(cron="0 0/1 * * * ?")
	public void SendSMS() {
		System.err.println("sms cron");
		String meterno, eventtime, circle, division, subdivision;
		List<Object[]> list = d5DataService.geteventdata();
		for (Iterator<?> iterator1 = list.iterator(); iterator1.hasNext();) {
			System.out.println("insss for");
			Object[] obj = (Object[]) iterator1.next();
			meterno = obj[0] + "";
			eventtime = obj[1] + "";
			circle = obj[2] + "";
			division = obj[3] + "";
			subdivision = obj[4] + "";
			System.err.println(division + "" + subdivision);
			List<SmsToRecipient> list2 = smsToRecipientService.getDataBySubdiv(division, subdivision);
			String name = list2.get(0).getR_name();
			String to = list2.get(0).getR_mobile_num();
			String body = "Dear" + name + ",The meter no " + meterno + " is power failed on the date " + eventtime
					+ " for the subdivision " + subdivision;
			SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
			smsCredentialsDetailsBean.setNumber(to);
			smsCredentialsDetailsBean.setUserName(name);
			smsCredentialsDetailsBean.setMessage("3GC;PULL;" + body);
			new Thread(new SendDocketInfoSMS(smsCredentialsDetailsBean)).start();
		}

	}

	@RequestMapping(value = "/checkAlaram", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkAlaram(HttpServletRequest request, ModelMap model) {
		System.err.println("inside");
		String fromdate = request.getParameter("fromdate");
		String todate = request.getParameter("todate");
		String alaramtype = request.getParameter("alaramtypeValue");
		String metertype = request.getParameter("metertypeValue");
		// System.err.println(fromdate+""+todate+""+alaramtype+""+metertype);
		model.put("alaramlist", d5DataService.getalaramData(fromdate, todate, alaramtype, metertype, request, model));
		return "alarm";

	}

	@RequestMapping(value = "/zeroConsumption", method = { RequestMethod.GET, RequestMethod.POST })
	public String zeroConsumption(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{
		System.out.println("===inside zeroConsumption===");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date d = new Date();
		String selectedDateName = sdf.format(d);
		model.put("month", selectedDateName);
		return "zeroConsumption";
	}

	@RequestMapping(value = "/zeroConsumptiongetDATA", method = { RequestMethod.GET, RequestMethod.POST })
	public String zeroConsumption(ModelMap model, HttpServletRequest request, @RequestParam String selectedDateName)
			throws java.text.ParseException//
	{
		System.out.println("===inside zeroConsumption getData ===" + selectedDateName);

		List result = meterMasterService.findALLZeroConc(selectedDateName);
		model.put("zeroConcmp", result);
		model.put("month", selectedDateName);
		return "zeroConsumption";
	}

	@RequestMapping(value = "/demandExceed", method = { RequestMethod.GET, RequestMethod.POST })
	public String demandExceed(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{
		System.out.println("===inside demandExceed===");

		return "demandExceed";
	}

	@RequestMapping(value = "/demandExceedList", method = { RequestMethod.GET, RequestMethod.POST })
	public String demandExceedList(ModelMap model, HttpServletRequest request, @RequestParam String selectedDateName)
			throws java.text.ParseException//
	{
		System.out.println("===inside demandExceedList getData ===" + selectedDateName);

		List result = meterMasterService.findALLDmndExceed(selectedDateName);
		model.put("zeroConcmp", result);
		model.put("month", selectedDateName);
		return "demandExceed";
	}

	@RequestMapping(value = "/rtcReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String rtcReport(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{
		System.out.println("===inside rtcReport===");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date d = new Date();
		String currentMonth = null;
		currentMonth = sdf.format(d);
		System.out.println("=======>" + currentMonth);
		model.put("currentMonth", currentMonth);
		List<Master> circlelist = meterMasterService.getCirclesByZone("All");
		model.put("circleList", circlelist);

		return "rtcReport";
	}

	/*
	 * @RequestMapping(value="/rtcList",method={RequestMethod.GET,RequestMethod.POST
	 * }) public String rtcList(ModelMap model, HttpServletRequest
	 * request,@RequestParam String selectedDateName) throws
	 * java.text.ParseException// {
	 * System.out.println("===inside rtcList getData ==="+selectedDateName);
	 * 
	 * List result= meterMasterService.findALLrtc(selectedDateName);
	 * model.put("zeroConcmp", result); model.put("month",selectedDateName); return
	 * "rtcReport"; }
	 */

	@RequestMapping(value = "/rtcList", method = { RequestMethod.GET, RequestMethod.POST })
	public String rtcList(ModelMap model, HttpServletRequest request) throws java.text.ParseException//
	{
		System.out.println("selectedDateName---->" + request.getParameter("selectedDateName"));
		String selectedDateName = request.getParameter("selectedDateName");
		String circle = request.getParameter("circle");
		String div = request.getParameter("division");
		String subdivision = request.getParameter("sdoCode");
		String division = div.trim();

		System.out.println("circle--->" + circle);
		System.out.println("division--->" + division);
		System.out.println("subdivision--->" + subdivision);
		List<Master> circlelist = meterMasterService.getCirclesByZone("All");
		model.put("circleList", circlelist);
		/*
		 * System.out.println("===inside rtcList getData ==="+selectedDateName);
		 * 
		 * @RequestParam String selectedDateName,@RequestParam String
		 * circle,@RequestParam String division,@RequestParam String subdivision,
		 */
		List result = meterMasterService.findALLrtc(selectedDateName, circle, division, subdivision);
		model.put("zeroConcmp", result);
		model.put("month", selectedDateName);
		return "rtcReport";
	}

	@RequestMapping(value = "/getconsumptionanalysyis", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getconsumptionanalysyis(@RequestParam String division, @RequestParam String circle,
			@RequestParam String subdivision, @RequestParam String month, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) {
		
		
		System.out.println("inside Load Survey Data==>" + division + "" + circle + "" + subdivision + "" + month);
		List<?> consumptionanalysis = d4LoadDataService.getconsumptionanalysis(division, circle, subdivision, month);
		return consumptionanalysis;
	}

	@RequestMapping(value = "/getPhaseForInstantanousData", method = { RequestMethod.GET, RequestMethod.POST })
	public String getPhaseForInstantanousData(ModelMap model, HttpServletRequest request, @RequestParam String meterNo,
			@RequestParam String selectedDateName) {
		MDMLogger.logger.info("In intantanous ");
		List<AMIInstantaneousEntity> list = amrInstantaneousService.meterByDate(meterNo, selectedDateName, model);
		System.err.println(list.size());
		if (list.size() > 0) {
			double a = Math.PI;
			double irpf = list.get(0).getPfR();
			double ira = (Math.acos(Math.abs(irpf)) * 180.0f / a);
			double iypf = list.get(0).getPfY();
			double iya = (Math.acos(Math.abs(iypf)) * 180.0f / a);
			double ibpf = list.get(0).getPfB();
			double iba = (Math.acos(Math.abs(ibpf)) * 180.0f / a);
			model.put("rPhaseAngle", ira);
			model.put("yPhaseAngle", iya);
			model.put("bPhaseAngle", iba);
		} else {
			model.put("msg", "Data not available");
		}
		model.put("instantanousData", list);
		List<MasterMainEntity> feederData = MasterMainService.getFeederData(meterNo);
		model.put("feederData", feederData);
		return "phasorDiagram";
	}

	@RequestMapping(value = { "/downloadReciept" }, method = { RequestMethod.POST, RequestMethod.GET })
	public void downloadReciept(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws IOException, DocumentException {
		/*
		 * System.out.
		 * println("--------------------------inside download reciept---------------");
		 * String meterNo=request.getParameter("meterNo"); String
		 * month=request.getParameter("month"); try { SimpleDateFormat sdf=new
		 * SimpleDateFormat("dd-MM-yyyy");
		 * 
		 * // List paySlipList1=null; List<D2Data>
		 * list=d2DataService.meterByDate(meterNo,month,model);
		 * System.out.println(list); if(list.size()==0) { Rectangle pageSize = new
		 * Rectangle(1000, 720); Document document = new Document(pageSize); Font font1
		 * = new Font(Font.FontFamily.HELVETICA ,13, Font.BOLD); Font boldFont = new
		 * Font(Font.FontFamily.HELVETICA, 15, Font.BOLD); ByteArrayOutputStream baos =
		 * new ByteArrayOutputStream(); PdfWriter
		 * pdfWriter=PdfWriter.getInstance(document, baos);
		 * 
		 * document.open(); PdfContentByte content = pdfWriter.getDirectContent();
		 * Rectangle pageRect = document.getPageSize();
		 * pageRect.setLeft(pageRect.getLeft() + 10);
		 * pageRect.setRight(pageRect.getRight() - 10);
		 * pageRect.setTop(pageRect.getTop() - 10);
		 * pageRect.setBottom(pageRect.getBottom() +10);
		 * 
		 * content.setColorStroke( BaseColor.GRAY);
		 * content.rectangle(pageRect.getLeft(), pageRect.getBottom(),
		 * pageRect.getWidth(), pageRect.getHeight()); content.setLineWidth(3);
		 * content.stroke(); content.fillStroke();
		 * 
		 * PdfPCell cell = new PdfPCell(); PdfPTable tab1 = new PdfPTable(1);
		 * cell.setBorder(Rectangle.NO_BORDER);
		 * tab1.getDefaultCell().setBorder(Rectangle.NO_BORDER); tab1.setHeaderRows(0);
		 * PdfPCell firstRow = new PdfPCell(); firstRow.setBorder(Rectangle.NO_BORDER);
		 * firstRow.setMinimumHeight(50); firstRow.setPadding(0);
		 * tab1.addCell(firstRow); tab1.addCell(""); cell.setPadding(0);
		 * cell.addElement(tab1);
		 * 
		 * PdfPTable pdf1 = new PdfPTable(1); pdf1.setWidthPercentage(100); //
		 * percentage pdf1.getDefaultCell().setPadding(3);
		 * pdf1.getDefaultCell().setBorderWidth(1);
		 * pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		 * 
		 * PdfPCell cell2 = new PdfPCell(); Paragraph p7 = new Paragraph(); Paragraph
		 * pstart = new Paragraph(); Paragraph p = new Paragraph(); Paragraph p1 = new
		 * Paragraph(); Paragraph p2 = new Paragraph(); Paragraph p3 = new Paragraph();
		 * Paragraph p4 = new Paragraph(); Paragraph p5 = new Paragraph(); Paragraph p6
		 * = new Paragraph();
		 * 
		 * 
		 * p7.add(new Phrase("                    ")); p1.add(new
		 * Phrase(meterNo+" data is not there for the date "+month,new
		 * Font(Font.FontFamily.HELVETICA ,22, Font.BOLD)));
		 * p1.setAlignment(Element.ALIGN_CENTER); p2.add(new
		 * Chunk("(Wholly owned Government of Karnataka Undertaking)",new
		 * Font(Font.FontFamily.HELVETICA ,15, Font.NORMAL)));
		 * p2.setAlignment(Element.ALIGN_CENTER); p6.add(new
		 * Phrase("                    "));
		 * 
		 * p5.add(new Phrase("                    ")); cell2.addElement(pstart);
		 * cell2.addElement(p); cell2.addElement(p1); cell2.addElement(p2);
		 * cell2.addElement(p6); cell2.addElement(p3); cell2.addElement(p4);
		 * cell2.addElement(p5);
		 * 
		 * pdf1.addCell(cell2); document.add(pdf1); document.close();
		 * 
		 * 
		 * response.setHeader("Content-disposition",
		 * "inline; filename=PaySliperror.pdf");
		 * response.setContentType("application/pdf"); ServletOutputStream outstream =
		 * response.getOutputStream(); baos.writeTo(outstream); outstream.flush();
		 * outstream.close(); } else { Rectangle pageSize = new Rectangle(595,842);
		 * Document document = new Document(pageSize); Font font1 = new
		 * Font(Font.FontFamily.HELVETICA ,13, Font.BOLD); Font boldFont = new
		 * Font(Font.FontFamily.HELVETICA, 15, Font.BOLD); ByteArrayOutputStream baos =
		 * new ByteArrayOutputStream(); PdfWriter
		 * pdfWriter=PdfWriter.getInstance(document, baos);
		 * 
		 * document.open(); // InputStream inputStream =
		 * this.getClass().getClassLoader().getResourceAsStream("files/bcitslogo1.png");
		 * // byte[] bytes = IOUtils.toByteArray(inputStream); // Image img1 =
		 * Image.getInstance(bytes); PdfContentByte content =
		 * pdfWriter.getDirectContent(); PdfTemplate pdfTemplateChartHolder =
		 * content.createTemplate(505,225); Graphics2D graphicsChart =
		 * pdfTemplateChartHolder.createGraphics(505,225,new DefaultFontMapper());
		 * Rectangle2D chartRegion = new Rectangle2D.Double(0,0,500,225);
		 * generatePieChart(list.get(0).getiRAngle(),list.get(0).getiYAngle(),list.get(0
		 * ).getiBAngle()).draw(graphicsChart,chartRegion); graphicsChart.dispose();
		 * 
		 * 
		 * PdfPCell cell = new PdfPCell(); PdfPTable tab1 = new PdfPTable(1);
		 * cell.setBorder(Rectangle.NO_BORDER);
		 * tab1.getDefaultCell().setBorder(Rectangle.NO_BORDER); tab1.setHeaderRows(0);
		 * PdfPCell firstRow = new PdfPCell(); firstRow.setBorder(Rectangle.NO_BORDER);
		 * firstRow.setMinimumHeight(50); firstRow.setPadding(0);
		 * tab1.addCell(firstRow); tab1.addCell(""); cell.setPadding(0);
		 * cell.addElement(tab1);
		 * 
		 * PdfPTable pdf1 = new PdfPTable(1); pdf1.setWidthPercentage(100); //
		 * percentage pdf1.getDefaultCell().setPadding(3);
		 * pdf1.getDefaultCell().setBorderWidth(1);
		 * pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		 * 
		 * PdfPCell cell2 = new PdfPCell(); PdfPCell cell3 = new PdfPCell(); PdfPCell
		 * cell4 = new PdfPCell(); PdfPCell cell5 = new PdfPCell(); PdfPCell cell6 = new
		 * PdfPCell(); PdfPCell cell7 = new PdfPCell(); PdfPCell cell8 = new PdfPCell();
		 * PdfPCell cell9 = new PdfPCell(); PdfPCell cell10 = new PdfPCell(); Paragraph
		 * p7 = new Paragraph(); Paragraph pstart = new Paragraph(); Paragraph p = new
		 * Paragraph(); Paragraph p1 = new Paragraph(); Paragraph p2 = new Paragraph();
		 * Paragraph p3 = new Paragraph(); Paragraph p4 = new Paragraph(); Paragraph p5
		 * = new Paragraph(); Paragraph p6 = new Paragraph(); Paragraph p10 = new
		 * Paragraph(); Paragraph p11 = new Paragraph(); Paragraph p12 = new
		 * Paragraph(); Paragraph p13 = new Paragraph(); Paragraph p14 = new
		 * Paragraph(); Paragraph p15 = new Paragraph();
		 * 
		 * // img1.scaleToFit(100,70); // img1.setAlignment(Element.ALIGN_RIGHT);
		 * 
		 * // pstart.add(new Chunk(img1,30,-50)); p1.add(new
		 * Phrase(meterNo+" - Instantaneous Values",new Font(Font.FontFamily.TIMES_ROMAN
		 * ,15,Font.BOLD,BaseColor.BLACK))); p1.setAlignment(Element.ALIGN_CENTER);
		 * p11.add(new Phrase("read on : ")); p11.add(new Phrase(month,new
		 * Font(Font.FontFamily.TIMES_ROMAN ,12,Font.NORMAL,BaseColor.BLUE)));
		 * p11.setAlignment(Element.ALIGN_CENTER); p3.add(new
		 * Phrase("# 86,3rd Cross, Bhoopasandra Main Road",new
		 * Font(Font.FontFamily.TIMES_ROMAN ,13)));
		 * p3.setAlignment(Element.ALIGN_RIGHT);
		 * 
		 * p4.add(new Phrase("RMV 2nd Stage, Bangalore-560 094",new
		 * Font(Font.FontFamily.TIMES_ROMAN ,13)));
		 * p4.setAlignment(Element.ALIGN_RIGHT);
		 * 
		 * p10.add(new Phrase("Ph: 080-23414178.",new Font(Font.FontFamily.TIMES_ROMAN
		 * ,13))); p10.setAlignment(Element.ALIGN_RIGHT);
		 * 
		 * 
		 * 
		 * p6.add(new Phrase("Annual Compensation",new Font(Font.FontFamily.TIMES_ROMAN
		 * ,14,Font.BOLD,BaseColor.BLACK))); p6.setAlignment(Element.ALIGN_CENTER);
		 * 
		 * p5.add(new Phrase("                    ")); cell2.addElement(pstart);
		 * cell2.addElement(p1); cell2.addElement(p3); cell2.addElement(p4);
		 * cell2.addElement(p10); cell2.addElement(p11); cell2.addElement(p5);
		 * cell9.addElement(p6); cell9.setBackgroundColor(BaseColor.LIGHT_GRAY);
		 * pdf1.addCell(cell2); pdf1.addCell(cell9); document.add(pdf1);
		 * 
		 * PdfPTable table = new PdfPTable(2); table.setTotalWidth(100);
		 * table.setLockedWidth(false); float headerwidths1[] = {50f,50f}; float
		 * headerwidths2[] = {25f,25f,25f,25f}; float headerwidths3[] = {50f,25f,25f};
		 * float headerwidths4[] = {100f};
		 * 
		 * PdfPTable table5 = new PdfPTable(2); table5.setTotalWidth(300);
		 * table5.setLockedWidth(false); table5.getAbsoluteWidths();
		 * table5.setWidths(headerwidths1); table5.setWidthPercentage(100); //
		 * percentage table5.getDefaultCell().setPadding(3);
		 * table5.getDefaultCell().setBorderWidth(1);
		 * table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * table5.addCell("Meter Serial Number");
		 * table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		 * table5.addCell(list.get(0).getCdfData().getMeterNo());
		 * table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * table5.addCell("Meter Date-Time");
		 * table5.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		 * table5.addCell(list.get(0).getCdfData().getD1data().getMeterDate());
		 * document.add(table5);
		 * 
		 * PdfPTable datatable = new PdfPTable(2); datatable.setTotalWidth(300);
		 * datatable.setLockedWidth(false); datatable.getAbsoluteWidths();
		 * datatable.setWidths(headerwidths1); datatable.setWidthPercentage(100); //
		 * percentage datatable.getDefaultCell().setPadding(3);
		 * datatable.getDefaultCell().setBorderWidth(1);
		 * datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable.addCell("Meter Type");
		 * datatable.addCell(list.get(0).getCdfData().getD1data().getMeterType());
		 * datatable.addCell("Meter processor Family"); datatable.addCell("");
		 * document.add(datatable);
		 * 
		 * 
		 * PdfPTable datatable6 = new PdfPTable(2); datatable6.setTotalWidth(300);
		 * datatable6.setLockedWidth(false); datatable6.getAbsoluteWidths();
		 * datatable6.setWidths(headerwidths1); datatable6.setWidthPercentage(100); //
		 * percentage datatable6.getDefaultCell().setPadding(3);
		 * datatable6.getDefaultCell().setBorderWidth(1);
		 * datatable6.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable6.addCell("Primary Current"); datatable6.addCell("test");
		 * datatable6.addCell("Primary Voltage"); datatable6.addCell("test");
		 * document.add(datatable6);
		 * 
		 * PdfPTable datatable2 = new PdfPTable(2); datatable2.setTotalWidth(300);
		 * datatable2.setLockedWidth(false); datatable2.getAbsoluteWidths();
		 * datatable2.setWidths(headerwidths1); datatable2.setWidthPercentage(100); //
		 * percentage datatable2.getDefaultCell().setPadding(3);
		 * datatable2.getDefaultCell().setBorderWidth(1);
		 * datatable2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable2.addCell("Secondary Current"); datatable2.addCell("test");
		 * datatable2.addCell("Secondary Voltage"); datatable2.addCell("test");
		 * document.add(datatable2);
		 * 
		 * table.getAbsoluteWidths(); table.setWidths(headerwidths1);
		 * table.setWidthPercentage(100); // percentage
		 * table.getDefaultCell().setPadding(3);
		 * table.getDefaultCell().setBorderWidth(1);
		 * table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * table.addCell("Max current"); table.addCell("test"); document.add(table);
		 * 
		 * Paragraph ph = new Paragraph("\n"); document.add(ph);
		 * 
		 * PdfPTable datatable10 = new PdfPTable(2);
		 * datatable10.setWidths(headerwidths1); datatable10.setWidthPercentage(100); //
		 * percentage datatable10.getDefaultCell().setPadding(3);
		 * datatable10.getDefaultCell().setBorderWidth(1);
		 * datatable10.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		 * document.add(datatable10);
		 * 
		 * PdfPTable datatable11 = new PdfPTable(4); datatable11.setTotalWidth(300);
		 * datatable11.setLockedWidth(false); datatable11.getAbsoluteWidths();
		 * datatable11.setWidths(headerwidths2); datatable11.setWidthPercentage(100); //
		 * percentage datatable11.getDefaultCell().setPadding(3);
		 * datatable11.getDefaultCell().setBorderWidth(1);
		 * datatable11.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable11.addCell("Parameter"); datatable11.addCell("L1/Element1");
		 * datatable11.addCell("L2/Element2"); datatable11.addCell("L3/Element3");
		 * document.add(datatable11);
		 * 
		 * PdfPTable datatable51 = new PdfPTable(4); datatable51.setTotalWidth(300);
		 * datatable51.setLockedWidth(false); datatable51.getAbsoluteWidths();
		 * datatable51.setWidths(headerwidths2); datatable51.setWidthPercentage(100); //
		 * percentage datatable51.getDefaultCell().setPadding(3);
		 * datatable51.getDefaultCell().setBorderWidth(1);
		 * datatable51.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable51.addCell("Voltages");
		 * datatable51.addCell(list.get(0).getrPhaseVal()+"v");
		 * datatable51.addCell(list.get(0).getyPhaseVal()+"v");
		 * datatable51.addCell(list.get(0).getbPhaseVal()+"v");
		 * document.add(datatable51);
		 * 
		 * PdfPTable datatable52 = new PdfPTable(4); datatable52.setTotalWidth(300);
		 * datatable52.setLockedWidth(false); datatable52.getAbsoluteWidths();
		 * datatable52.setWidths(headerwidths2); datatable52.setWidthPercentage(100); //
		 * percentage datatable52.getDefaultCell().setPadding(3);
		 * datatable52.getDefaultCell().setBorderWidth(1);
		 * datatable52.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable52.addCell("Line Current");
		 * datatable52.addCell(list.get(0).getrPhaseLineVal()+"A");
		 * datatable52.addCell(list.get(0).getyPhaseLineVal()+"A");
		 * datatable52.addCell(list.get(0).getbPhaseLineVal()+"A");
		 * document.add(datatable52);
		 * 
		 * 
		 * PdfPTable datatable53 = new PdfPTable(4); datatable53.setTotalWidth(300);
		 * datatable53.setLockedWidth(false); datatable53.getAbsoluteWidths();
		 * datatable53.setWidths(headerwidths2); datatable53.setWidthPercentage(100); //
		 * percentage datatable53.getDefaultCell().setPadding(3);
		 * datatable53.getDefaultCell().setBorderWidth(1);
		 * datatable53.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable53.addCell("Active Current");
		 * datatable53.addCell(list.get(0).getrPhaseActiveVal()+"A");
		 * datatable53.addCell(list.get(0).getyPhaseActiveVal()+"A");
		 * datatable53.addCell(list.get(0).getbPhaseActiveVal()+"A");
		 * document.add(datatable53);
		 * 
		 * PdfPTable datatable54 = new PdfPTable(4); datatable54.setTotalWidth(300);
		 * datatable54.setLockedWidth(false); datatable54.getAbsoluteWidths();
		 * datatable54.setWidths(headerwidths2); datatable54.setWidthPercentage(100); //
		 * percentage datatable54.getDefaultCell().setPadding(3);
		 * datatable54.getDefaultCell().setBorderWidth(1);
		 * datatable54.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable54.addCell("Reactive Current");
		 * datatable54.addCell(list.get(0).getRphaseReactiveCurrent()+"A");
		 * datatable54.addCell(list.get(0).getYphaseReactiveCurrent()+"A");
		 * datatable54.addCell(list.get(0).getBphaseReactiveCurrent()+"A");
		 * document.add(datatable54);
		 * 
		 * PdfPTable datatable55 = new PdfPTable(4); datatable55.setTotalWidth(300);
		 * datatable55.setLockedWidth(false); datatable55.getAbsoluteWidths();
		 * datatable55.setWidths(headerwidths2); datatable55.setWidthPercentage(100); //
		 * percentage datatable55.getDefaultCell().setPadding(3);
		 * datatable55.getDefaultCell().setBorderWidth(1);
		 * datatable55.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable54.addCell("Power Factor");
		 * datatable55.addCell(list.get(0).getrPhasePfVal()+"");
		 * datatable55.addCell(list.get(0).getyPhasePfVal()+"");
		 * datatable55.addCell(list.get(0).getbPhasePfVal()+"");
		 * document.add(datatable55);
		 * 
		 * document.add(ph);
		 * 
		 * PdfPTable datatable28 = new PdfPTable(2); datatable28.setTotalWidth(300);
		 * datatable28.setLockedWidth(false); datatable28.getAbsoluteWidths();
		 * datatable28.setWidths(headerwidths1); datatable28.setWidthPercentage(100); //
		 * percentage datatable28.getDefaultCell().setPadding(3);
		 * datatable28.getDefaultCell().setBorderWidth(1);
		 * datatable28.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable28.addCell("Active Power");
		 * datatable28.addCell(list.get(0).getActivePowerVal()+"");
		 * document.add(datatable28);
		 * 
		 * PdfPTable datatable50 = new PdfPTable(2); datatable50.setTotalWidth(300);
		 * datatable50.setLockedWidth(false); datatable50.getAbsoluteWidths();
		 * datatable50.setWidths(headerwidths1); datatable50.setWidthPercentage(100); //
		 * percentage datatable50.getDefaultCell().setPadding(3);
		 * datatable50.getDefaultCell().setBorderWidth(1);
		 * datatable50.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable50.addCell("Reactive Power"); if(list.get(0).getKvar()==null) {
		 * datatable50.addCell("0"); }else{
		 * datatable50.addCell(list.get(0).getKvar()+"");} document.add(datatable50);
		 * 
		 * 
		 * PdfPTable datatable29 = new PdfPTable(2); datatable29.setTotalWidth(300);
		 * datatable29.setLockedWidth(false); datatable29.getAbsoluteWidths();
		 * datatable29.setWidths(headerwidths1); datatable29.setWidthPercentage(100); //
		 * percentage datatable29.getDefaultCell().setPadding(3);
		 * datatable29.getDefaultCell().setBorderWidth(1);
		 * datatable29.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable29.addCell("Apparent Power"); if(list.get(0).getKva()==null) {
		 * datatable29.addCell("0"); }else{
		 * datatable29.addCell(list.get(0).getKva()+"");} document.add(datatable29);
		 * 
		 * 
		 * 
		 * System.out.println("---amountPaid---->"+1); PdfPTable datatable56 = new
		 * PdfPTable(2); datatable56.setTotalWidth(300);
		 * datatable56.setLockedWidth(false); datatable56.getAbsoluteWidths();
		 * datatable56.setWidths(headerwidths1); datatable56.setWidthPercentage(100); //
		 * percentage datatable56.getDefaultCell().setPadding(3);
		 * datatable56.getDefaultCell().setBorderWidth(1);
		 * datatable56.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable56.addCell("Average power factor");
		 * if(list.get(0).getAvgPfVal()+""==null) { datatable56.addCell("0"); }else{
		 * datatable56.addCell(list.get(0).getAvgPfVal()+"");}
		 * document.add(datatable56);
		 * 
		 * PdfPTable datatable57 = new PdfPTable(2); datatable57.setTotalWidth(300);
		 * datatable57.setLockedWidth(false); datatable57.getAbsoluteWidths();
		 * datatable57.setWidths(headerwidths1); datatable57.setWidthPercentage(100); //
		 * percentage datatable57.getDefaultCell().setPadding(3);
		 * datatable57.getDefaultCell().setBorderWidth(1);
		 * datatable57.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable57.addCell("Frequency"); if(list.get(0).getFrequency()==null) {
		 * datatable57.addCell("0"); }else{
		 * datatable57.addCell(list.get(0).getFrequency()+"");}
		 * document.add(datatable57);
		 * 
		 * PdfPTable datatable27 = new PdfPTable(2); datatable27.setTotalWidth(300);
		 * datatable27.setLockedWidth(false); datatable27.getAbsoluteWidths();
		 * datatable27.setWidths(headerwidths1); datatable27.setWidthPercentage(100); //
		 * percentage datatable27.getDefaultCell().setPadding(3);
		 * datatable27.getDefaultCell().setBorderWidth(1);
		 * datatable27.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable27.addCell("Phase sequence");
		 * if(list.get(0).getPhaseSequence()==null) { datatable27.addCell("0"); }else{
		 * datatable27.addCell(list.get(0).getPhaseSequence()+"");}
		 * document.add(datatable27);
		 * 
		 * PdfPTable datatable2 = new PdfPTable(2); datatable2.setTotalWidth(300);
		 * datatable2.setLockedWidth(false); datatable2.getAbsoluteWidths();
		 * datatable2.setWidths(headerwidths1); datatable2.setWidthPercentage(100); //
		 * percentage datatable2.getDefaultCell().setPadding(3);
		 * datatable2.getDefaultCell().setBorderWidth(1);
		 * datatable2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable2.addCell("Angle 1");
		 * datatable2.addCell(list.get(0).getiRAngle()+""); document.add(datatable2);
		 * 
		 * PdfPTable datatable26 = new PdfPTable(2); datatable26.setTotalWidth(300);
		 * datatable26.setLockedWidth(false); datatable26.getAbsoluteWidths();
		 * datatable26.setWidths(headerwidths1); datatable26.setWidthPercentage(100); //
		 * percentage datatable26.getDefaultCell().setPadding(3);
		 * datatable26.getDefaultCell().setBorderWidth(1);
		 * datatable26.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable26.addCell("Angle 2");
		 * datatable26.addCell(list.get(0).getiYAngle()+""); document.add(datatable26);
		 * 
		 * PdfPTable datatable58 = new PdfPTable(2); datatable58.setTotalWidth(300);
		 * datatable58.setLockedWidth(false); datatable58.getAbsoluteWidths();
		 * datatable58.setWidths(headerwidths1); datatable58.setWidthPercentage(100); //
		 * percentage datatable58.getDefaultCell().setPadding(3);
		 * datatable58.getDefaultCell().setBorderWidth(1);
		 * datatable58.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		 * datatable58.addCell("Angle 3");
		 * datatable58.addCell(list.get(0).getiBAngle()+""); document.add(datatable58);
		 * 
		 * 
		 * Image chartImage = Image.getInstance(pdfTemplateChartHolder);
		 * document.add(chartImage);
		 * 
		 * Paragraph p17 = new Paragraph(); p17.add(new
		 * Chunk("This is a computer generated PO",new Font(Font.FontFamily.TIMES_ROMAN
		 * ,16, Font.NORMAL))); ColumnText.showTextAligned(content,
		 * Element.ALIGN_CENTER,p17,(document.right() - document.left()) / 2 +
		 * document.leftMargin(),document.bottom() - 10, 0);
		 * 
		 * document.close();
		 * 
		 * 
		 * response.setHeader("Content-disposition",
		 * "inline; filename=Instantaneous_Report.pdf");
		 * response.setContentType("application/pdf"); ServletOutputStream outstream =
		 * response.getOutputStream(); baos.writeTo(outstream); outstream.flush();
		 * outstream.close();
		 * 
		 * }
		 * 
		 * } catch (Exception de) { de.printStackTrace(); }
		 */}

	/*
	 * public static JFreeChart generatePieChart(Double r,Double y,Double b) {
	 * DefaultPieDataset dataSet = new DefaultPieDataset(); dataSet.setValue("r",r);
	 * dataSet.setValue("y",y); dataSet.setValue("b",b);
	 * 
	 * JFreeChart chart = ChartFactory.createPieChart("Vector Diagram", dataSet,
	 * true, true, false); return chart; }
	 */

		 
		 @RequestMapping(value="/alarmDispatchdetails",method={RequestMethod.POST,RequestMethod.GET})
			public String dispatchAlarms(HttpServletRequest request,ModelMap model)
			{
			 List<?> circles=new ArrayList<>();
			 List<?> locationType=new ArrayList<>();
				circles=consumermaseteService.getCircle();
				model.put("circles", circles);
				locationType=alarmHistoryservice.getLocationType();
				model.put("locationtype", locationType);
				return "dispatchalarms";
			}
		 
		 @RequestMapping(value="/getDispatchdetails",method={RequestMethod.POST,RequestMethod.GET})
			public @ResponseBody List<Object []> getDispatchalarma(HttpServletRequest request,ModelMap model)
			{
				String zone=request.getParameter("zone");
				String circle=request.getParameter("circle");
				String town=request.getParameter("town");
				//String subdivision=request.getParameter("subdivision");
				String loctype=request.getParameter("loctype");
				String fromdate=request.getParameter("fromdate");
				String todate=request.getParameter("todate");
				List<Object []> list = null;
				list = alarmDefinitionService.dispatchAlarms(zone,circle,town,loctype,fromdate,todate);
				
				
				return list ;
			}
			
			
			@RequestMapping(value = "/getUserRoleDetailsDatas", method = { RequestMethod.GET, RequestMethod.POST })
			public @ResponseBody List<?> getUserRoleDetailsDatas(HttpServletRequest request,ModelMap model) {
				System.out.println("inside user role------");
				String Zn = request.getParameter("zn");
				String cr = request.getParameter("cr");
				String twn = request.getParameter("tw");
				String usrole = request.getParameter("usrole");
				
				String sdiv="",div="",cir="",zonee="",role="",dcom="",tw="";
		        if(twn.equalsIgnoreCase("ALL"))
		 			{
		 				tw="%";
		 			}else
		 			{
		 				tw=twn;
		 			}
		 		/*if (dvs.equalsIgnoreCase("ALL")) 
		 		    {
		 			    div="%";
		 		    }else 
		 		    {
		 	            div=dvs;
		 		    }*/
		 			
		 		if(cr.equalsIgnoreCase("ALL"))
		 			{
		 				cir="%";
		 			}else
		 			{
		 				cir=cr;
		 			}
		 		if(Zn.equalsIgnoreCase("ALL"))
		 		    {
		 			    zonee="%";
		 		    }else
		 		    {
		 			    zonee=Zn;
		 		    }
		 		if(usrole.equalsIgnoreCase("ALL"))
				    {
					    role="%";
				    }else
				    {
					    role=usrole;
				    }
		 		/*if(disco.equalsIgnoreCase("ALL"))
			    {
		 			dcom="%";
			    }else
			    {
			    	dcom=disco;
			    }*/
				List<?> li = masterService.getUserRoledataForAlarm(zonee, cir, twn, role);
				System.out.println("list size" + li.size());
				return li;
			}
}
