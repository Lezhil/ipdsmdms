package com.bcits.mdas.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.ServiceOrderDetails;
import com.bcits.mdas.utility.BCITSLogger;
import com.bcits.mdas.utility.FilterUnit;
import com.bcits.mdas.utility.SendDocketInfoSMS;
import com.bcits.mdas.utility.SendModemAlertViaMail;
import com.bcits.mdas.utility.SendNotificationAlertViaMail;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.ServiceOrderService;
import com.bcits.utility.SmsCredentialsDetailsBean;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
//import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
//import com.itextpdf.io.image.ImageDataFactory; 

///import com.itextpdf.kernel.pdf.PdfDocument; 
//import com.itextpdf.kernel.pdf.PdfWriter;

//import com.itextpdf.layout.Document; 
//import com.itextpdf.layout.element.Image;
@Controller
public class ServiceOrderController {

	String filepath = null;

	@Autowired
	private ConsumerMasterService consumermasterService;

	@Autowired
	private ServiceOrderService serviceOrderService;

	@PersistenceContext(unitName = "POSTGREDataSource")
	protected EntityManager postgresMdas;

	@RequestMapping(value = "/generateServiceOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public String generateServiceOrder(HttpServletRequest request, ModelMap model, HttpSession session) {
		List<?> circleList = new ArrayList<>();
		circleList = consumermasterService.getCircle();
		model.put("circles", circleList);
		return "generateServiceOrder";
	}

	@RequestMapping(value = "/getEventListfromMeterEvents", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getEventListfromMeterEvents(HttpServletRequest request, ModelMap model,
			HttpSession session) {
		List<?> res = null;
		res = serviceOrderService.getEventListforMeterList();
		return res;
	}

	// sonum
	@RequestMapping(value = "/getSOnofromIssueType", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getSOnofromIssueType(HttpServletRequest request, ModelMap model, HttpSession session) {
		String issueType = request.getParameter("issueType");
		List<?> res = null;
		res = serviceOrderService.getSOnofromIssueType(issueType);
		return res;
	}

	@RequestMapping(value = "/getPowerTheftList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getPowerTheftList(HttpServletRequest request, ModelMap model, HttpSession session) {
		List<?> res = null;
		res = serviceOrderService.getPowerTheftList();
		return res;
	}

	@RequestMapping(value = "/getMeterExceptionAlarmList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getMeterExceptionAlarmList(HttpServletRequest request, ModelMap model,
			HttpSession session) {
		List<?> res = null;
		res = serviceOrderService.getMeterExceptionAlarmList();
		return res;
	}
	// generateserviceexcel

	
	
	  @RequestMapping(value="/generateServiceExcel",method={RequestMethod.GET,
	  RequestMethod.POST}) public @ResponseBody void
	  generateServiceExcel(HttpServletRequest request,HttpServletResponse
	  response,ModelMap model) { 
		  String locType = request.getParameter("locType");
	  String circle = request.getParameter("circle");
	  String town =  request.getParameter("town"); 
	  String issue = request.getParameter("issue");
	  String issueType = request.getParameter("issueType");
	  String zone = request.getParameter("zone");
	  System.out.println(locType);
	//  System.out.println(issueType);
	  
		String cancat = "";
		if (locType.equals("BOUNDARY METER")) {
			cancat = "BOUNDARY METER";
		}
		if (locType.equals("FEEDER METER")) {
			cancat = "FEEDER METER";
		}
		if (locType.equals("DT")) {
			cancat = "DT";
		}
		
		
		
	  serviceOrderService.generateServiceExcel(request,response,cancat,circle,town,issueType,issue,zone ); 
	  }
	 
	  
	 

	@RequestMapping(value = "/getConsServiceOrderDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getServiceOrderDetails(HttpServletRequest request, ModelMap model,
			HttpSession session) {
		// System.err.println("++++++++++++++++++++");
		List<Object[]> list1 = null;
		List<Object[]> list2 = null;
		List<Object[]> list3 = null;
		List<Object[]> list4 = null;
		String locType = request.getParameter("locType");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		// String division = request.getParameter("division");
		// String sdoCode = request.getParameter("sdoCode");
		String town = request.getParameter("town");
		String issue = request.getParameter("issue");
		String issueType = request.getParameter("issueType");

		String cancat = "";
		if (locType.equals("BOUNDARY METER")) {
			// String consumerCate1 = "HT";
			// String consumerCate2 = "LT";
			// cancat = consumerCate1 + "'" + "," + "'" + consumerCate2;
			cancat = "BOUNDARY METER";
		}
		if (locType.equals("FEEDER METER")) {
			// String consumerCate1 = "FEEDER METER";
			// String consumerCate2 = "BOUNDARY METER";
			// cancat = consumerCate1 + "'" + "," + "'" + consumerCate2;
			cancat = "FEEDER METER";
		}
		if (locType.equals("DT")) {
			cancat = "DT";
		}

		String sql = "SELECT distinct sitecode from meter_data.amilocation WHERE  zone like '"+zone+"' and circle like '" + circle
				+ "' and tp_towncode like '" + town + "' limit 1 ";
		System.err.println("sql------>" + sql);
		Object sitecode = postgresMdas.createNativeQuery(sql).getSingleResult();
		System.err.println("sql------>" + sql);

		String month = new SimpleDateFormat("yyyyMM").format(new Date());
		System.err.println(month);

		String siteMonth = sitecode + "_" + month;
		 System.err.println(siteMonth);

		Object result = serviceOrderService.getSOnumber(siteMonth);
		 System.err.println(result);

		SimpleDateFormat sdfToday = new SimpleDateFormat("dd-MM-YYYY");
		String todayDate = sdfToday.format(new Date());
		 System.err.println(todayDate);
		String userName = session.getAttribute("username").toString();
		 System.out.println(userName);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long tsTime1 = timestamp.getTime();
		java.util.Date time = new java.util.Date(tsTime1 * 1000);
		 System.err.println("view time -----" + time);

		Map<String, Object> map = new HashMap<String, Object>();

		List<Object[]> array = new ArrayList<Object[]>();
		if (issueType.equals("Power theft")) {
			list1 = (List<Object[]>) serviceOrderService.getServiceOrderforPowerTheft(circle, town, cancat);
			array.addAll(list1);
			// model.put("location", list1);
		}
		if (issueType.equals("Meter Events")) {
			list2 = (List<Object[]>) serviceOrderService.getServiceOrderforMeterEvents(zone,circle, town, cancat, issue);
			array.addAll(list2);
			// model.put("location", list2);
		}
		if (issueType.equals("Meter Exception/Alarms")) {
			list3 = (List<Object[]>) serviceOrderService.getServiceOrderforMeterException(circle, cancat, issue,town);
			array.addAll(list3);
			// model.put("location", list3);
		}
		if (issueType.equals("Communication Fail")) {
			list4 = (List<Object[]>) serviceOrderService.getServiceOrderforCommunicationFail(zone,circle, cancat,town);
			array.addAll(list4);
			// model.put("location", list4);
		}
		map.put("array", array);

		map.put("resultSO", result);
		map.put("date", todayDate);
		map.put("userName", userName);
		map.put("timestamp", time);
		return map;
	}

	@RequestMapping(value = "/savingAllServiceData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object savingAllServiceData(HttpServletRequest request, HttpSession session) {
		// System.out.println("----------");
		String SOnumber = request.getParameter("so_number");
		String locType = request.getParameter("locType");
		String issueType = request.getParameter("issueType");
		String issue = request.getParameter("issue");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String arrayVal = request.getParameter("arrayVal");
		String issueDate = request.getParameter("issueTimestamp");
		String tosectionModal = request.getParameter("tosection");
		String userNameFrom = request.getParameter("userName");
		String mtrarr = request.getParameter("mtrarr");
		// System.out.println("issueType===" + issueType);
		java.util.Date ts = new java.util.Date(Long.parseLong(issueDate) / 1000);
		Timestamp ts1 = new Timestamp(ts.getTime());
		// java.sql.Timestamp ts = java.sql.Timestamp.valueOf( issueDate ) ;

		String[] sonumSplit = SOnumber.split("_");
		// System.out.println(ts1);
		// System.err.println("site---" + sonumSplit[0] + "mon---" + sonumSplit[1] +
		// "incre--" + sonumSplit[2]);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String userName = session.getAttribute("username").toString();
		String officeid = sonumSplit[0];
		String to = request.getParameter("toMailModal");
		String cc = request.getParameter("ccMailModal");
		String sms = request.getParameter("msgModal");
		String locIdset = request.getParameter("optarr");
		// System.err.println(ts);
		String resp = "";
		String toMail = "";
		String ccMail = "";
		String msgses = "";
		String email = "";
		String[] tosend = to.split(",");
		Object response = 0;
		try {
			for (int i = 0; i < tosend.length; i++) {
				if (i < tosend.length - 1) {
					toMail += tosend[i] + ",";
				} else if (i == tosend.length - 1) {
					toMail += tosend[i];
				}
			}

			String[] ccsend = cc.split(",");
			for (int i = 0; i < ccsend.length; i++) {
				if (i < ccsend.length - 1) {
					ccMail += ccsend[i] + ",";
				} else if (i == ccsend.length - 1) {
					ccMail += ccsend[i];
				}
			}

			email = to + "," + ccMail;

			String[] msg = sms.split(",");
			for (int i = 0; i < msg.length; i++) {
				if (i < msg.length - 1) {
					msgses += msg[i] + ",";
				} else if (i == msg.length - 1) {
					msgses += msg[i];
				}
			}
			// System.err.println("mail---" + email);
			// System.err.println("msgs---" + msgses);

			String status = "Open";
			String[] locIdSplit = locIdset.split(",");
			// System.err.println(locIdSplit);

			String locTypeArr = "";
			String locIdArr = "";
			String lonameArr = "";
			String mtrnoArr = "";
			JSONArray recs;

			//pdfConverter(SOnumber, locType, issueType, issue, circle, zone, town, arrayVal, tosectionModal,
				//	userNameFrom);
			try {
				//sendServiceMails(SOnumber, to, ccMail);
				sendServiceMails(SOnumber,locType, issueType, issue, circle, zone, town, arrayVal, tosectionModal,
							userNameFrom, to, ccMail);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		
			sendSMSAlerts("3GC;PULL; " + SOnumber + " Dated " + timestamp + " is raised to you by " + userName
					+ "  for " + issueType + " . Check your email for details.", msgses);

			recs = new JSONArray(arrayVal.toString());

			for (int j = 0; j < recs.length(); j++) {
				org.json.JSONObject obj = recs.getJSONObject(j);
				locTypeArr = obj.optString("loc_type");
				locIdArr = obj.optString("loc_ID");
				lonameArr = obj.optString("loc_name");
				mtrnoArr = obj.optString("mtr_sr");

				ArrayList<String> mylist = new ArrayList<String>();
				mylist.add(locIdArr);
				mylist.add(mtrnoArr);
				// System.err.println("Mylist---------"+mylist);

				String s2 = SendDocketInfoSMS.jsonobj.toString();
				 Thread.sleep(1000);

				JSONObject myJsonObj = new JSONObject(s2);
				String statusMsg = "";
				String statuscode = myJsonObj.getString("Msg");
				if (statuscode.equals("200")) {
					statusMsg = "Message Delivered";
				} else {
					statusMsg = "Message Not Delivered";
				}

				ServiceOrderDetails service = new ServiceOrderDetails();
				service.setOffice_id(officeid == null ? null : officeid);
				service.setSo_number(SOnumber == null ? null : SOnumber);
				service.setLocation_type(locType == null ? null : locType);
				for (int s = 0; s < mylist.size(); s++) {
					service.setLocation_code(mylist.get(0) == null ? null : mylist.get(0));
					service.setMeter_sr_number(mylist.get(1) == null ? null : mylist.get(1));
				}
				service.setIssue(issue == null ? null : issue);
				service.setIssue_date(ts1 == null ? null : ts1);
				service.setSo_create_date(timestamp == null ? null : timestamp);
				service.setEmails(email == null ? null : email);
				service.setSos_status(status == null ? null : status);
				service.setEntry_by(userName == null ? null : userName);
				service.setEntry_date(timestamp == null ? null : timestamp);
				service.setSms(msgses == null ? null : msgses);
				service.setEntered_to(tosectionModal == null ? null : tosectionModal);
				service.setIssue_type(issueType == null ? null : issueType);
				service.setNotified_status(statusMsg == null ? null : statusMsg);
				serviceOrderService.save(service);

				resp = "saved";

			}

		} catch (Exception e) {
			// response = 1;
			e.printStackTrace();
		}
		return resp;
	}

	
	@RequestMapping(value = "/savingAllServiceDataToTNEB", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object savingAllServiceDataToTNEB(HttpServletRequest request, HttpSession session) {
		// System.out.println("----------");
		String SOnumber = request.getParameter("so_number");
		String locType = request.getParameter("locType");
		String issueType = request.getParameter("issueType");
		String issue = request.getParameter("issue");
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		String arrayVal = request.getParameter("arrayVal");
		String issueDate = request.getParameter("issueTimestamp");
		String tosectionModal = request.getParameter("tosection");
		String userNameFrom = request.getParameter("userName");
		String mtrarr = request.getParameter("mtrarr");
		// System.out.println("issueType===" + issueType);
		java.util.Date ts = new java.util.Date(Long.parseLong(issueDate) / 1000);
		Timestamp ts1 = new Timestamp(ts.getTime());
		// java.sql.Timestamp ts = java.sql.Timestamp.valueOf( issueDate ) ;

		String[] sonumSplit = SOnumber.split("_");
		// System.out.println(ts1);
		// System.err.println("site---" + sonumSplit[0] + "mon---" + sonumSplit[1] +
		// "incre--" + sonumSplit[2]);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String userName = session.getAttribute("username").toString();
		String officeid = sonumSplit[0];
		String to = request.getParameter("toMailModal");
		String cc = request.getParameter("ccMailModal");
		String sms = request.getParameter("msgModal");
		String locIdset = request.getParameter("optarr");
		// System.err.println(ts);
		String resp = "";
		String toMail = "";
		String ccMail = "";
		String msgses = "";
		String email = "";
		String[] tosend = to.split(",");
		Object response = 0;
		try {
			for (int i = 0; i < tosend.length; i++) {
				if (i < tosend.length - 1) {
					toMail += tosend[i] + ",";
				} else if (i == tosend.length - 1) {
					toMail += tosend[i];
				}
			}

			String[] ccsend = cc.split(",");
			for (int i = 0; i < ccsend.length; i++) {
				if (i < ccsend.length - 1) {
					ccMail += ccsend[i] + ",";
				} else if (i == ccsend.length - 1) {
					ccMail += ccsend[i];
				}
			}

			email = to + "," + ccMail;

			String[] msg = sms.split(",");
			for (int i = 0; i < msg.length; i++) {
				if (i < msg.length - 1) {
					msgses += msg[i] + ",";
				} else if (i == msg.length - 1) {
					msgses += msg[i];
				}
			}
			// System.err.println("mail---" + email);
			// System.err.println("msgs---" + msgses);

			String status = "Open";
			String[] locIdSplit = locIdset.split(",");
			// System.err.println(locIdSplit);

			String locTypeArr = "";
			String locIdArr = "";
			String lonameArr = "";
			String mtrnoArr = "";
			JSONArray recs;

			pdfConverter(SOnumber, locType, issueType, issue, circle, zone, town, arrayVal, tosectionModal,
					userNameFrom);
			sendServiceMailsNotification(SOnumber, to, ccMail);
			/*
			 * sendSMSAlerts("3GC;PULL; " + SOnumber + " Dated " + timestamp +
			 * " is raised to you by " + userName + "  for " + issueType +
			 * " . Check your email for details.", msgses);
			 */

			recs = new JSONArray(arrayVal.toString());

			for (int j = 0; j < recs.length(); j++) {
				org.json.JSONObject obj = recs.getJSONObject(j);
				locTypeArr = obj.optString("loc_type");
				locIdArr = obj.optString("loc_ID");
				lonameArr = obj.optString("loc_name");
				mtrnoArr = obj.optString("mtr_sr");

				ArrayList<String> mylist = new ArrayList<String>();
				mylist.add(locIdArr);
				mylist.add(mtrnoArr);
				// System.err.println("Mylist---------"+mylist);

//				String s2 = SendDocketInfoSMS.jsonobj.toString();
				// Thread.sleep(1000);

//				JSONObject myJsonObj = new JSONObject(s2);
				String statusMsg = "";
//				String statuscode = myJsonObj.getString("Msg");
//				if (statuscode.equals("200")) {
//					statusMsg = "Message Delivered";
//				} else {
//					statusMsg = "Message Not Delivered";
//				}

				ServiceOrderDetails service = new ServiceOrderDetails();
				service.setOffice_id(officeid == null ? null : officeid);
				service.setSo_number(SOnumber == null ? null : SOnumber);
				service.setLocation_type(locType == null ? null : locType);
				for (int s = 0; s < mylist.size(); s++) {
					service.setLocation_code(mylist.get(0) == null ? null : mylist.get(0));
					service.setMeter_sr_number(mylist.get(1) == null ? null : mylist.get(1));
				}
				service.setIssue(issue == null ? null : issue);
				service.setIssue_date(ts1 == null ? null : ts1);
				service.setSo_create_date(timestamp == null ? null : timestamp);
				service.setEmails(email == null ? null : email);
				service.setSos_status(status == null ? null : status);
				service.setEntry_by(userName == null ? null : userName);
				service.setEntry_date(timestamp == null ? null : timestamp);
				service.setSms(msgses == null ? null : msgses);
				service.setEntered_to(tosectionModal == null ? null : tosectionModal);
				service.setIssue_type(issueType == null ? null : issueType);
				service.setNotified_status(statusMsg == null ? null : statusMsg);
				serviceOrderService.save(service);

				resp = "saved";

			}

		} catch (Exception e) {
			// response = 1;
			e.printStackTrace();
		}
		return resp;
	}
	@RequestMapping(value = "/savingAllServiceDataPrint", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object savingAllServiceDataPrint(HttpServletRequest request, HttpSession session,
			HttpServletResponse httpResponse) {
		// System.out.println("----------");
		String SOnumber = request.getParameter("so_number");
		String locType = request.getParameter("locType");
		String issueType = request.getParameter("issueType");
		String issue = request.getParameter("issue");
		String circle = request.getParameter("circle");
		String zone = request.getParameter("zone");
		String town = request.getParameter("town");
		String arrayVal = request.getParameter("arrayVal");
		String issueDate = request.getParameter("issueTimestamp");
		String tosectionModal = request.getParameter("tosection");
		String userNameFrom = request.getParameter("userName");
		String mtrarr = request.getParameter("mtrarr");
		java.util.Date ts = new java.util.Date(Long.parseLong(issueDate) / 1000);
		Timestamp ts1 = new Timestamp(ts.getTime());

		String[] sonumSplit = SOnumber.split("_");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String userName = session.getAttribute("username").toString();
		String officeid = sonumSplit[0];
		String to = request.getParameter("toMailModal");
		String cc = request.getParameter("ccMailModal");
		String sms = request.getParameter("msgModal");
		String locIdset = request.getParameter("optarr");
		// System.err.println(ts);
		String resp = "";
		String toMail = "";
		String ccMail = "";
		String msgses = "";
		String email = "";
		String[] tosend = to.split(",");
		Object response = 0;
		try {
			for (int i = 0; i < tosend.length; i++) {
				if (i < tosend.length - 1) {
					toMail += tosend[i] + ",";
				} else if (i == tosend.length - 1) {
					toMail += tosend[i];
				}
			}

			String[] ccsend = cc.split(",");
			for (int i = 0; i < ccsend.length; i++) {
				if (i < ccsend.length - 1) {
					ccMail += ccsend[i] + ",";
				} else if (i == ccsend.length - 1) {
					ccMail += ccsend[i];
				}
			}

			email = to + "," + ccMail;

			String[] msg = sms.split(",");
			for (int i = 0; i < msg.length; i++) {
				if (i < msg.length - 1) {
					msgses += msg[i] + ",";
				} else if (i == msg.length - 1) {
					msgses += msg[i];
				}
			}

			String status = "Open";
			String[] locIdSplit = locIdset.split(",");

			String locTypeArr = "";
			String locIdArr = "";
			String lonameArr = "";
			String mtrnoArr = "";
			JSONArray recs;

			String path = pdfConverter(SOnumber, locType, issueType, issue, circle, zone, town, arrayVal,
					tosectionModal, userNameFrom);

			String notifystatus = "Print";
			recs = new JSONArray(arrayVal.toString());

			for (int j = 0; j < recs.length(); j++) {
				org.json.JSONObject obj = recs.getJSONObject(j);
				locTypeArr = obj.optString("loc_type");
				locIdArr = obj.optString("loc_ID");
				lonameArr = obj.optString("loc_name");
				mtrnoArr = obj.optString("mtr_sr");

				ArrayList<String> mylist = new ArrayList<String>();
				mylist.add(locIdArr);
				mylist.add(mtrnoArr);
				// System.err.println("Mylist---------"+mylist);

				ServiceOrderDetails service = new ServiceOrderDetails();
				service.setOffice_id(officeid == null ? null : officeid);
				service.setSo_number(SOnumber == null ? null : SOnumber);
				service.setLocation_type(locType == null ? null : locType);
				for (int s = 0; s < mylist.size(); s++) {
					service.setLocation_code(mylist.get(0) == null ? null : mylist.get(0));
					service.setMeter_sr_number(mylist.get(1) == null ? null : mylist.get(1));
				}
				service.setIssue(issue == null ? null : issue);
				service.setIssue_date(ts1 == null ? null : ts1);
				service.setSo_create_date(timestamp == null ? null : timestamp);
				service.setEmails(email == null ? null : email);
				service.setSos_status(status == null ? null : status);
				service.setEntry_by(userName == null ? null : userName);
				service.setEntry_date(timestamp == null ? null : timestamp);
				service.setSms(msgses == null ? null : msgses);
				service.setEntered_to(tosectionModal == null ? null : tosectionModal);
				service.setIssue_type(issueType == null ? null : issueType);
				service.setNotified_status(notifystatus == null ? null : notifystatus);
				serviceOrderService.save(service);

				resp = "saved";
			}
			// File file = new File(path);
			// java.io.InputStream inputStream = new FileInputStream(file);
			// byte[] bytesPdf=file.get
			byte[] encoded = Files.readAllBytes(Paths.get(path));

			byte[] encodedBytes = Base64.getEncoder().encode(encoded);

			String pdfInBase64 = new String(encodedBytes);

			System.out.println(Arrays.toString(encoded));
			return pdfInBase64;

		} catch (Exception e) {
			// response = 1;
			e.printStackTrace();

		}
		return resp;

	}
	

	@RequestMapping(value = "/viewPrintPdfPage", method = { RequestMethod.GET, RequestMethod.POST })
	public void viewPrintPdfPage(@RequestParam("so_number") String so_number, @RequestParam("locType") String locType,
			@RequestParam("issueType") String issueType, @RequestParam("issue") String issue,
			@RequestParam("circle") String circle, @RequestParam("zone") String zone, @RequestParam("town") String town,
			@RequestParam("arrayVal") String arrayVal, @RequestParam("tosection") String tosection,
			@RequestParam("userName") String userName, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
//		System.err.println("print");
		try {
			String path = pdfConverter(so_number, locType, issueType, issue, circle, zone, town, arrayVal, tosection,
					userName);
			File file = new File(path);
			java.io.InputStream inputStream = new FileInputStream(file);
			System.out.println(file.getName());
			try {
				response.setHeader("Content-Disposition", "attachment; filename=" + "test");
				response.setContentType("application/pdf");
				OutputStream out = response.getOutputStream();
				IOUtils.copy(inputStream, out);
				response.flushBuffer();
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String pdfConverter(String SOnumber, String locType, String issueType, String issue, String circle,
			String zone, String town, String arrayVal, String tosectionModal, String userNameFrom)
			throws FileNotFoundException, DocumentException {

		// HttpServletResponse response=new HttpServletResponse;
		System.out.println("pdfffff print");
		Rectangle pageSize = new Rectangle(720, 1050);
		Document document = new Document(pageSize);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String cir = "";
		String div = "";
		if (circle.equalsIgnoreCase("%")) {
			cir = "ALL";
		} else {
			cir = circle;
		}
		if (zone.equalsIgnoreCase("%")) {
			zone = "ALL";
		} else {
			div = zone;
		}
		System.out.println("pdffffff");
		// Document document = new Document();
		@SuppressWarnings("unused")

		String pdfName = "service_order" + SOnumber;
		String pathPDF = FilterUnit.service_order_savePDF;
		String finalPath = pathPDF + pdfName;
		filepath = finalPath + ".pdf";
		System.err.println("fine path is==>" + filepath);
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(filepath));

		// pdfWriter=PdfWriter.getInstance(document, baos);
		document.open();
		// ResourceBundle resource = ResourceBundle.getBundle("messages");
		// String headerName=resource.getString("pageheader");
		// String logopath=resource.getString("pageheaderlogo");
		// File f = new File("resources/assets/img/tneb_logo.jpg");
		// System.err.println("resource--"+resource+"header+++"+headerName+"logopath****"+logopath);
		/*
		 * try { Image img = Image.getInstance("resources/assets/img/tneb_logo.jpg");
		 * img.scaleToFit(100,100); document.add(img); }catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		/*
		 * InputStream inputStream =
		 * (InputStream)this.getClass().getClassLoader().getResourceAsStream(
		 * "resources/assets/img/tneb_logo.jpg"); byte[] bytes; Image img1 = null; try {
		 * bytes = IOUtils.toByteArray(inputStream); img1 = Image.getInstance(bytes); }
		 * catch (IOException e1) { e1.printStackTrace(); }
		 */

		System.out.println(System.getProperty("catalina.base"));
		String caltelinaHome = System.getProperty("catalina.base");
		System.err.println("path ====>" + caltelinaHome);

		// String imagePath=caltelinaHome +
		// "/webapps/resources/assets/img/tneb_logo.jpg";
		// System.out.println("imagepath--->"+imagePath);

		// File catalinaOut = new File(System.getProperty("catalina.base"),
		// "logs/catalina.out");
		String altPath = caltelinaHome + "/bin/../webapps/resources/assets/img/tneb_logo.jpg";
		System.out.println("Alternate path--->" + altPath);
		// java.io.InputStream image=null;
		// InputStream inputStream =
		// (InputStream)this.getClass().getClassLoader().getResourceAsStream(imagePath);
		// InputStream is = new BufferedInputStream(new FileInputStream(imagePath));
		// BufferedImage image = ImageIO.read(is);
		/*
		 * byte[] bytes; Image img1 = null; try { bytes =
		 * IOUtils.toByteArray(inputStream); img1 = Image.getInstance(bytes); } catch
		 * (IOException e1) { e1.printStackTrace(); }
		 */
		PdfPCell cell = new PdfPCell();
		PdfPTable headerTable = new PdfPTable(2);
		headerTable.setWidthPercentage(100f);
		Image img = null;
		/*
		 * try { //local path fr logo image // img = Image.getInstance(
		 * "E:\\bSmartAMILatest\\WebContent\\resources\\assets\\img\\tneb_logo.jpg");
		 * 
		 * //fr deployment image path img = Image.getInstance(imagePath);
		 * 
		 * } catch (MalformedURLException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } catch (IOException e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); }
		 */
		Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
		Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
		PdfPTable pdf1 = new PdfPTable(1);
		pdf1.setWidthPercentage(100); // percentage
		pdf1.getDefaultCell().setPadding(3);
		pdf1.getDefaultCell().setBorderWidth(0);
		pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPTable pdf2 = new PdfPTable(1);
		pdf2.setWidthPercentage(100); // percentage
		pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		Chunk glue = new Chunk(new VerticalPositionMark());
		PdfPCell cell1 = new PdfPCell();
		Paragraph imgText = new Paragraph();
		Paragraph pstart = new Paragraph();
		Paragraph logo = new Paragraph();
		Phrase pp = new Phrase(200);
		PdfPCell leftCell = new PdfPCell();
		/*
		 * img.scaleToFit(100,60); img.setAlignment(Element.ALIGN_LEFT);
		 */
		// logo.add(new Chunk(img, 250, 50));

		pstart.add(new Phrase("Tamil Nadu Generation and Distribution Corporation Limited",
				new Font(Font.FontFamily.HELVETICA, 17, Font.BOLD)));

		pstart.setAlignment(Element.ALIGN_CENTER);

		// leftCell.addElement(img);
		// leftCell.setBorder(Rectangle.NO_BORDER);
		// headerTable.addCell(leftCell);

		// document.add(img);
		// pstart.add("Tamil Nadu Generation and Distribution Corporation Limited");
		// pstart.add(pp,new Font(Font.FontFamily.HELVETICA, 17, Font.BOLD));

		// PdfPCell rightCell = new PdfPCell();
		// pstart.add("Tamil Nadu Generation and Distribution Corporation Limited");
		// Font tableHeader =
		// FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD);

		// pstart.setFont(tableHeader);
		cell1.setBorder(Rectangle.NO_BORDER);
		cell1.addElement(pstart);
		pdf2.addCell(cell1);
		// pstart.add(new Chunk(glue));
		// pstart.add(new Chunk(glue));
		// rightCell .addElement(pstart);

		// rightCell .setBorder(Rectangle.NO_BORDER);
		// rightCell .setPaddingLeft(5);
		imgText.add(img);
		imgText.add(pdf2);

		// cell.setBorder(Rectangle.NO_BORDER);
		cell.addElement(imgText);

		/*
		 * pstart.add(new
		 * Phrase("Tamil Nadu Generation and Distribution Corporation Limited", new
		 * Font(Font.FontFamily.HELVETICA, 17, Font.BOLD)));
		 * 
		 * pstart.setAlignment(Element.ALIGN_CENTER); //
		 * cell1.setBorder(Rectangle.NO_BORDER); cell1.addElement(pstart);
		 * pdf2.addCell(cell1); pstart.add(new Chunk(glue)); //document.add(pdf2);
		 * pp.add(pstart);
		 * 
		 * logo.add(img); logo.add(pp);
		 */
		// document.add(imgText);

		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(100);
		table.setLockedWidth(false);
		float headerwidths1[] = { 50f, 50f };
		float headerwidths2[] = { 25f, 25f, 25f, 25f };
		float headerwidths3[] = { 50f, 25f, 25f };
		float headerwidths4[] = { 100f };

		PdfPTable header = new PdfPTable(4);
		header.setWidthPercentage(100);
		header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		PdfPCell headerCell = null;

		headerCell = new PdfPCell(new Phrase("Circle:", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerCell.setFixedHeight(20f);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		header.addCell(headerCell);
		header.addCell(getCell(cir, PdfPCell.ALIGN_LEFT));

		headerCell = new PdfPCell(new Phrase("Zone:", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerCell.setFixedHeight(20f);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		header.addCell(headerCell);
		header.addCell(getCell(div, PdfPCell.ALIGN_LEFT));

		headerCell = new PdfPCell(new Phrase("Town:", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerCell.setFixedHeight(20f);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		header.addCell(headerCell);
		header.addCell(getCell(town, PdfPCell.ALIGN_LEFT));

		headerCell = new PdfPCell(new Phrase("SO Number:", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerCell.setFixedHeight(20f);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		header.addCell(headerCell);
		// header.addCell(getCell("Division :", PdfPCell.ALIGN_RIGHT));
		header.addCell(getCell(SOnumber, PdfPCell.ALIGN_LEFT));

		headerCell = new PdfPCell(new Phrase("Issue Type:", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerCell.setFixedHeight(20f);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		header.addCell(headerCell);
		// header.addCell(getCell("Address :", PdfPCell.ALIGN_RIGHT));
		header.addCell(getCell(issueType, PdfPCell.ALIGN_LEFT));

		headerCell = new PdfPCell(new Phrase("Issue :", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerCell.setFixedHeight(20f);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		header.addCell(headerCell);
		// header.addCell(getCell("Sub-Division :", PdfPCell.ALIGN_RIGHT));
		header.addCell(getCell(issue, PdfPCell.ALIGN_LEFT));

		headerCell = new PdfPCell(new Phrase("From :", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerCell.setFixedHeight(20f);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		header.addCell(headerCell);
		// header.addCell(getCell("Sub-Division :", PdfPCell.ALIGN_RIGHT));
		header.addCell(getCell(userNameFrom, PdfPCell.ALIGN_LEFT));

		headerCell = new PdfPCell(new Phrase("To :", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
		headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		headerCell.setFixedHeight(20f);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		header.addCell(headerCell);
		// header.addCell(getCell("Sub-Division :", PdfPCell.ALIGN_RIGHT));
		header.addCell(getCell(tosectionModal, PdfPCell.ALIGN_LEFT));

		header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		document.add(header);

		PdfPTable header2 = new PdfPTable(4);
		header2.setWidthPercentage(100);
		header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		document.add(header2);

		PdfPTable datatable10 = new PdfPTable(2);
		datatable10.setWidths(headerwidths1);
		datatable10.setWidthPercentage(100); // percentage
		datatable10.getDefaultCell().setPadding(3);
		datatable10.getDefaultCell().setBorderWidth(1);
		datatable10.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		document.add(datatable10);

		// Table
		PdfPTable parameterTable = new PdfPTable(4);
		parameterTable.setWidths(new int[] { 2, 2, 2, 2 });
		parameterTable.setWidthPercentage(100);
		PdfPCell parameterCell;
		parameterCell = new PdfPCell(new Phrase("LocType", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
		parameterCell.setFixedHeight(25f);
		parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		parameterTable.addCell(parameterCell);

		parameterCell = new PdfPCell(new Phrase("LocIdentity", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
		parameterCell.setFixedHeight(25f);
		parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		parameterTable.addCell(parameterCell);

		parameterCell = new PdfPCell(new Phrase("LocName", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
		parameterCell.setFixedHeight(25f);
		parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		parameterTable.addCell(parameterCell);

		parameterCell = new PdfPCell(new Phrase("MeterSrNumber", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
		parameterCell.setFixedHeight(25f);
		parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		parameterTable.addCell(parameterCell);

		String locTypeArr = "";
		String locIdArr = "";
		String lonameArr = "";
		String mtrnoArr = "";
		JSONArray recs;
		try {
			recs = new JSONArray(arrayVal.toString());

			for (int j = 0; j < recs.length(); j++) {
				org.json.JSONObject obj = recs.getJSONObject(j);
				locTypeArr = obj.optString("loc_type");
				locIdArr = obj.optString("loc_ID");
				lonameArr = obj.optString("loc_name");
				mtrnoArr = obj.optString("mtr_sr");

				parameterCell = new PdfPCell(new Phrase(locTypeArr, new Font(Font.FontFamily.HELVETICA, 10)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setFixedHeight(25f);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(new Phrase(locIdArr, new Font(Font.FontFamily.HELVETICA, 10)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setFixedHeight(25f);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(new Phrase(lonameArr, new Font(Font.FontFamily.HELVETICA, 10)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setFixedHeight(25f);
				parameterTable.addCell(parameterCell);

				parameterCell = new PdfPCell(new Phrase(mtrnoArr, new Font(Font.FontFamily.HELVETICA, 10)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterCell.setFixedHeight(25f);
				parameterTable.addCell(parameterCell);

			}
			document.add(parameterTable);
			document.close();
			/*
			 * response.setHeader("Content-disposition",
			 * "attachment; filename=Service_order_"+SOnumber+".pdf");
			 * response.setContentType("application/pdf"); ServletOutputStream outstream;
			 * try { outstream = response.getOutputStream(); baos.writeTo(outstream);
			 * outstream.flush(); outstream.close(); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return filepath;

	}

	public PdfPCell getCell(String text, int alignment) {
		PdfPCell cell = new PdfPCell(new Phrase(text));
		cell.setPadding(5);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	private void sendServiceMails(String SOnumber,String locType,String issueType,String issue,String circle,String zone,String town,String arrayVal,String tosectionModal,String
			userNameFrom, String toSend, String ccMail) {
		String newbody = "";
		String body = " <span>Dear Sir,</span><br><br>\r\n";
		body += newbody;
		// List<Object[]> listForxl=new ArrayList<>();
	//	body += "<span>Please verify issue as attached herewith.</span><br><br>\r\n";

		body += "<div style='overflow: scroll;'> <table style='font-size: 9pt;'>\r\n" + 
	     		" <tr>\r\n" + 
	     		"  <th style='text-align: center;'>Service Order Number</th><td>"+SOnumber+"</td></tr>\r\n" + 
	     		"  <th >zone</th><td>"+zone+"</td></tr>\r\n" + 
	     		"  <th >circle</th>  <td>"+circle+"</td></tr>\r\n" + 
	     		"  <th >town</th><td>"+town+"</td></tr>\r\n" + 
	     		"  <th >zone</th><td>"+zone+"</td></tr>\r\n" +
	     		"  <th >locType</th><td>"+locType+"</td></tr>\r\n" +
	     		"  <th >issueType</th><td>"+issueType+"</td></tr>\r\n" +
	     		"  <th >issue</th><td>"+issue+"</td></tr>\r\n" +
	     		"  <th >arrayVal</th><td>"+arrayVal+"</td></tr>\r\n" +
	     		"  <th >tosectionModal</th><td>"+tosectionModal+"</td></tr>\r\n" +
	     		"  <th >userNameFrom</th><td>"+userNameFrom+"</td></tr>\r\n" +
	     		" </tr>\r\n";
		
		body += "<br><br><br><br><br><br><br><br>------------------------------------------------------------------------------------------------------------------<br>"
				+ "<p>********  THIS IS AN AUTO GENERATED E-MAIL. PLEASE DO NOT REPLY. ******** </p>";

		System.out.println(body);
		
		String to = toSend;
		//System.out.println(to);
		// String cc="ranga.reddy@bcits.co.in,srinubabu.takasi@bcits.in";

		// String to="sowjanya.medisetti@bcits.in";
		String cc = ccMail;

		/* String to="salehin.anjum@bcits.in"; */
		String subject = "" + SOnumber + " Service Order Details ";

		//new Thread(new SendModemAlertViaMail(subject, body, to, cc, filepath)).run();
		new Thread(new SendModemAlertViaMail(subject, body, to, cc)).run();
	}
	//new implem
	private void sendServiceMailsNotification(String SOnumber, String toSend, String ccMail) {
		String newbody = "";
		String body = " <span>Dear Sir,</span><br><br>\r\n";
		body += newbody;
		// List<Object[]> listForxl=new ArrayList<>();
		body += "<span>Please verify issue as attached herewith.</span><br><br>\r\n";

		body += "<br><br><br><br><br><br><br><br>------------------------------------------------------------------------------------------------------------------<br>"
				+ "<p>********  THIS IS AN AUTO GENERATED E-MAIL. PLEASE DO NOT REPLY. ******** </p>";

		String to = toSend;
		String cc =ccMail;

		String subject = "" + SOnumber + " Service Order Details ";
		new Thread(new SendNotificationAlertViaMail(subject, body, to, cc, filepath)).run();
     
	}

	@RequestMapping(value = "/serviceOrderFeedback", method = { RequestMethod.POST, RequestMethod.GET })
	public String serviceOrderFeedback(HttpServletRequest request, ModelMap model, HttpSession session) {
		List<?> circleList = new ArrayList<>();
		circleList = consumermasterService.getCircle();
		model.put("circles", circleList);
		/*
		 * List<?> so_number=new ArrayList<>();
		 * so_number=serviceOrderService.getDistinctSOnumber();
		 * model.put("so_number",so_number);
		 */
		return "serviceOrderFeedback";
	}

	@RequestMapping(value = "/getServiceOrderDetailsFeedback", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getServiceOrderDetailsFeedback(HttpServletRequest request, ModelMap model,
			HttpSession session) {
		// System.err.println("inside feedback");
		List<?> list = new ArrayList<>();
		String circle = request.getParameter("circle");
		String zone = request.getParameter("zone");
		String town = request.getParameter("town");
		String locType = request.getParameter("locType");
		String issue = request.getParameter("issue");
		String issueType = request.getParameter("issueType");
		String sonum = request.getParameter("sonum");
		String month = request.getParameter("month");
		String status = request.getParameter("status");

		list = serviceOrderService.getFeedbackServiceOrderDetails(circle, zone, locType, issue, issueType, sonum, month,
				status, town);

		return list;
	}

	@RequestMapping(value = "/getUpadtedServiceFeedback", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getUpadtedServiceFeedback(HttpServletRequest request, ModelMap model,
			HttpSession session) {
		String statusModal = request.getParameter("statusModal");
		String reopenRemark = request.getParameter("reopenRemark");
		String correctiveAction = request.getParameter("correctiveAction");
		String id = request.getParameter("serviceid");
		String userName = session.getAttribute("username").toString();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Object response = 0;
		// ServiceOrderDetails service = new ServiceOrderDetails();
		ServiceOrderDetails service = serviceOrderService.getEntityById(Integer.parseInt(id));
		if (service != null) {
			service.setSos_status(statusModal);
			service.setRemark(reopenRemark);
			service.setCorrective_action(correctiveAction);
			service.setUpdate_by(userName);
			service.setUpdate_date(timestamp);

			try {
				// serviceOrderService.customUpdate(service);
				serviceOrderService.customupdateBySchema(service, "postgresMdas");
				response = 0;
			} catch (Exception e) {
				response = 1;
				e.printStackTrace();
			}
		}
		return response;

	}

	@RequestMapping(value = "/sendidValModal", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object sendidValModal(HttpServletRequest request, ModelMap model, HttpSession session) {
		// System.err.println("-------------------");
		String id = request.getParameter("id");
		System.err.println("controller side " + id);
		List<ServiceOrderDetails> res = new ArrayList<>();
		ServiceOrderDetails ent = serviceOrderService.getEntityById(Integer.parseInt(id));
		res.add(ent);
		return res;
	}

	public void sendSMSAlerts(String mailMessage, String mobNo) {

		System.out.println("======= SMSAlertsToNigamConsumers ========" + mailMessage);
		System.out.println("======= SMSAlertsToNigamConsumers ========" + mobNo);
		BCITSLogger.logger.info("******* SMSAlertsToNigamConsumers *******");
		SmsCredentialsDetailsBean smsCredentialsDetailsBean = new SmsCredentialsDetailsBean();
		smsCredentialsDetailsBean.setNumber(mobNo);
		smsCredentialsDetailsBean.setUserName("BCITS");
		/*
		 * String mailMessage="Dear Customer,JVVNL announces award u p to Rs. 5000* on
		 * online electricity bill payments. " +
		 * " Avail your chance to win lottery by paying your energy bills online. *T&C apply."
		 * + "   " +"      Warm Regards, " +" JVVNL-Administration services";
		 */
		//String mailMessage="711151 : 3gc SETT; 04:"+chIp+"";
		// String mailMessage="3gc SETT; 04:"+chIp+"";
		smsCredentialsDetailsBean.setMessage(mailMessage);
		 new Thread(new SendDocketInfoSMS(smsCredentialsDetailsBean)).start();
		new SendDocketInfoSMS(smsCredentialsDetailsBean).run();
	}

	@RequestMapping(value = "/serviceOrderSummary", method = { RequestMethod.POST, RequestMethod.GET })
	public String serviceOrderSummary(HttpServletRequest request, ModelMap model, HttpSession session) {
		List<?> circleList = new ArrayList<>();
		circleList = consumermasterService.getCircle();
		model.put("circles", circleList);
		return "serviceOrderSummary";
	}
	// generateserviceexcel

//	@RequestMapping(value = "/GenerateServiceExcel", method = { RequestMethod.GET, RequestMethod.POST })
//	public @ResponseBody Object DtDetailsExcel(HttpServletRequest request, HttpServletResponse response,
//			ModelMap model) {
//		try {
//			String Region = request.getParameter("region");
//			String circle = request.getParameter("circle");
//			String ssid = request.getParameter("ssid");
//			String tp_towncode = request.getParameter("town").trim();
//			String fdrID = request.getParameter("fdrID").trim();
////			System.out.println("circle---" + circle + "ssid---" + ssid + "towncode---" + tp_towncode + "fdrid---"
////					+ fdrID + "Region---" + Region);
//
//			String regn = "", circlee = "", towncode = "", fdrid = "";
//			if (regn.equalsIgnoreCase("ALL")) {
//				regn = "%";
//			} else {
//				regn = Region;
//			}
//
//			if (circle.equalsIgnoreCase("ALL")) {
//				circlee = "%";
//			} else {
//				circlee = circle;
//			}
//			if (tp_towncode.equalsIgnoreCase("ALL")) {
//				towncode = "%";
//			} else {
//				towncode = tp_towncode;
//			}
//			if (fdrID.equalsIgnoreCase("ALL")) {
//				fdrid = "%";
//			} else {
//				fdrid = fdrID;
//			}
//
//			String fileName = "DTDetails";
//			XSSFWorkbook wb = new XSSFWorkbook();
//			XSSFSheet sheet = wb.createSheet(fileName);
//			XSSFRow header = sheet.createRow(0);
//
//			CellStyle lockedCellStyle = wb.createCellStyle();
//			lockedCellStyle.setLocked(true);
//			CellStyle unlockedCellStyle = wb.createCellStyle();
//			unlockedCellStyle.setLocked(false);
//
//			XSSFCellStyle style = wb.createCellStyle();
//			style.setWrapText(true);
//			sheet.setColumnWidth(0, 1000);
//			XSSFFont font = wb.createFont();
//			font.setFontName(HSSFFont.FONT_ARIAL);
//			font.setFontHeightInPoints((short) 10);
//			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
//			style.setFont(font);
//
//			//header.createCell(0).setCellValue("Region");
//			header.createCell(0).setCellValue("Circle");
//			header.createCell(1).setCellValue("Town");
//			header.createCell(2).setCellValue("Feeder_Code");
//			header.createCell(3).setCellValue("DT_Code");
//			header.createCell(4).setCellValue("DT_Type");
//			header.createCell(5).setCellValue("DT_Name");
//			header.createCell(6).setCellValue("DT_Capacity");
//			header.createCell(7).setCellValue("Phase");
//			header.createCell(8).setCellValue("Meter_Sr_Number");
//			header.createCell(9).setCellValue("Meter_Manufacturer");
//			header.createCell(10).setCellValue("Latitude");
//			header.createCell(11).setCellValue("Longitude");
//			// header.createCell(8).setCellValue("MF");
//			// header.createCell(9).setCellValue("Town_Code");
//
//			List<?> dtData = null;
//			dtData = dtdetailsService.getdtdetailsBytownCode(fdrid, towncode, circlee);
//
//			int count = 1;
//			// int cellNO=0;
//			for (Iterator<?> iterator = dtData.iterator(); iterator.hasNext();) {
//				final Object[] values = (Object[]) iterator.next();
//
//				XSSFRow row = sheet.createRow(count);
//				// cellNO++;
//				// row.createCell(0).setCellValue(String.valueOf(cellNO+""));
//
////				if (values[11] == null) {
////					row.createCell(0).setCellValue(String.valueOf(""));
////				} else {
////					row.createCell(0).setCellValue(String.valueOf(values[11]));
////				}
//
//				if (values[10] == null) {
//					row.createCell(0).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(0).setCellValue(String.valueOf(values[10]));
//				}
//
//				if (values[11] == null) {
//					row.createCell(1).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(1).setCellValue(String.valueOf(values[9]) + "-" + String.valueOf(values[11]));
//				}
//
//				if (values[5] == null) {
//					row.createCell(2).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(2).setCellValue(String.valueOf(values[5]));
//				}
//
//				if (values[0] == null) {
//					row.createCell(3).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(3).setCellValue(String.valueOf(values[0]));
//				}
//
//				if (values[1] == null) {
//					row.createCell(4).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(4).setCellValue(String.valueOf(values[1]));
//				}
//				if (values[2] == null) {
//					row.createCell(5).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(5).setCellValue(String.valueOf(values[2]));
//				}
//				if (values[3] == null) {
//					row.createCell(6).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(6).setCellValue(String.valueOf(values[3]));
//				}
//				if (values[4] == null) {
//					row.createCell(7).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(7).setCellValue(String.valueOf(values[4]));
//				}
//				if (values[6] == null) {
//					row.createCell(8).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(8).setCellValue(String.valueOf(values[6]));
//				}
//				if (values[7] == null) {
//					row.createCell(9).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(9).setCellValue(String.valueOf(values[7]));
//				}
//				if (values[12] == null) {
//					row.createCell(10).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(10).setCellValue(String.valueOf(values[12]));
//				}
//
//				if (values[13] == null) {
//					row.createCell(11).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(11).setCellValue(String.valueOf(values[13]));
//				}
//
//				 count ++;
//			}
//
//			FileOutputStream fileOut = new FileOutputStream(fileName);
//			wb.write(fileOut);
//			fileOut.flush();
//			fileOut.close();
//
//			ServletOutputStream servletOutputStream;
//
//			servletOutputStream = response.getOutputStream();
//			response.setContentType("application/vnd.ms-excel");
//			response.setHeader("Content-Disposition", "inline;filename=\"DtDetails.xlsx" + "\"");
//			FileInputStream input = new FileInputStream(fileName);
//			IOUtils.copy(input, servletOutputStream);
//			servletOutputStream.flush();
//			servletOutputStream.close();
//
//			return null;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	

	@RequestMapping(value = "/getServiceSummaryDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getServiceSummaryDetails(HttpServletRequest request, ModelMap model,
			HttpSession session) {
		// System.err.println("inside summary");
		List<?> list = new ArrayList<>();
		String circle = request.getParameter("circle");
		String zone = request.getParameter("zone");
		String town = request.getParameter("town");
		String locType = request.getParameter("locType");
		String locId = request.getParameter("locId");
		String mtrno = request.getParameter("mtrno");
		String soPending = request.getParameter("soPending");

		// System.out.println("long----"+soPenDur);
		// String sdfToday ="";
		String status = "Open";
		String trimStr = "";
		String daysStr = "";
		SimpleDateFormat sdfToday = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = sdfToday.format(new Date());
		ArrayList<?> soDate = new ArrayList<>();
		ArrayList<?> test = new ArrayList<>();
		String sql = "select  date(so_create_date) FROM meter_data.service_order_details WHERE sos_status in('Open','Closed','Re open')";
		soDate = (ArrayList<?>) postgresMdas.createNativeQuery(sql).getResultList();

		List<String> dateStrings = new ArrayList<>();
		dateStrings.addAll((Collection<? extends String>) soDate);
		String abc = dateStrings.toString().replaceAll("\\[", "").replaceAll("\\]", "").replace(" ", "").replace(",","','");
	//,	abc.add("'"+abc+"'");
	//	abc.add('"++"')
		
		if (soPending.equalsIgnoreCase("") || soPending.equalsIgnoreCase(null)) {
			status ="%";
			System.out.println("inside if method" + "'"+abc+"'");
			
			list = serviceOrderService.getServiceSummaryDetails(circle, zone, locType, locId, mtrno, "'"+abc+"'", status, town);
		} else {
			System.out.println("inside else");
			Long soPenDur = Long.parseLong(soPending);
			System.err.println("soPenDur---" + soPenDur);
			System.err.println("size is-->" + dateStrings.size());
			StringBuilder sb = new StringBuilder();
			for (Object s : soDate) {
				sb.append(s);
				sb.append("@");
			}
			String newDates = sb.toString();
			System.out.println("sb size is--->" + newDates.length());
			System.out.println("sb size is--->" + newDates);//DATES

			String[] str = newDates.split("@");
			System.out.println(str[0]);
			ArrayList<String> daysList = new ArrayList<>();
			
			for(int i=0; i<str.length;i++)
			{
				daysList.add("'"+str[i]+"'");
				System.err.println("daysList"+daysList);
				
			}
			
			//String[] datearray  

			String pendingDays = "";
			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

			
			try {
				/* for (int i = 0; i < str.length; i++) *//*
														 * { try { Date date1 = myFormat.parse(str[i]); Date date2 =
														 * myFormat.parse(todayDate); long diff = date2.getTime() -
														 * date1.getTime(); long daysDiff = TimeUnit.DAYS.convert(diff,
														 * TimeUnit.MILLISECONDS); System.err.println("daysDiff---" +
														 * daysDiff);
														 * 
														 * if (daysDiff >= soPenDur) {
														 * 
														 * pendingDays += str[i];
														 * 
														 * }
														 * 
														 * 
														 * 
														 * System.err.println("dates are===>" + daysList + "size ====>"
														 * + daysList.size()); } catch (Exception e) {
														 * e.printStackTrace(); }
														 * 
														 * }
														 */
				
				/*
				 * System.out.println("pending days are " + pendingDays);
				 * daysList.add(pendingDays);
				 */

				
				String idList = daysList.toString();
				String csv = idList.substring(1, idList.length() - 1);
				//System.out.println(csv);

				System.err.println(" Comma Sep==>" + csv);
				list = serviceOrderService.getServiceSummaryDetails(circle, zone, locType, locId, mtrno, csv, status,
						town);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
