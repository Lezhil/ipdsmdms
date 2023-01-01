
package com.bcits.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.restlet.resource.Finder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bcits.entity.ConsumerMasterEntity;
import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.DtDetailsHistoryEntity;
import com.bcits.entity.FeederEntity;
import com.bcits.entity.MeterLifeCycleEntity;
import com.bcits.entity.Mrname;
import com.bcits.entity.User;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterInventoryService;
import com.bcits.service.DtDetailsHistoryService;
import com.bcits.service.DtDetailsService;
import com.bcits.service.MeterMasterService;
import com.bcits.utility.MDMLogger;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


@Controller
public class DTDetailsController {

	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager entityManager;
	
	@Autowired
	private DtDetailsService dtdetailsService;
	
	@Autowired
	private MeterInventoryService meterInventoryService;
	
	@Autowired
	private MasterMainService masterMainService;
	
	@Autowired
	private DtDetailsHistoryService dtDetailsHistoryService;
	
	@Autowired
	private FeederDetailsService feederdetailsservice;

	

	String msg = "";
	
	int uploadFlag = 0;

	@RequestMapping(value = "/DTdetails", method = { RequestMethod.POST,RequestMethod.GET })
	public String DTdetails(ModelMap model, HttpServletRequest requst,HttpSession session) 
	{
		
		/*List<?> dtdetails = dtdetailsService.getdtdetails();
		model.addAttribute("dtDetails", dtdetails);*/
		List<?> circleList = feederdetailsservice.getcircle();
	//	model.put("circleList", circleList);
		
		String userName = (String) session.getAttribute("username");

		// sessions
		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		String qry = null;

	/*	if (officeType.equalsIgnoreCase("circle")) {
			qry = "SELECT s.office_id,SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f"
					+ " WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND circle_code = '"+ officeCode + "' GROUP BY s.office_id ,SUBDIVISION ORDER BY SUBDIVISION";
		} else if (officeType.equalsIgnoreCase("division")) {
			qry = "SELECT s.office_id,SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f "
					+ "WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND  division_code = '"+ officeCode + "' GROUP BY s.office_id ,SUBDIVISION ORDER BY SUBDIVISION";
		} else if (officeType.equalsIgnoreCase("subdivision")) {
			qry = "SELECT s.office_id,SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f "
					+ "WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND  sitecode = '"+ officeCode + "' GROUP BY s.office_id ,SUBDIVISION ORDER BY SUBDIVISION";
		} else if (officeType.equalsIgnoreCase("discom")){
		qry = "SELECT s.office_id, SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f"
				+ " WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND discom_code = '"+ officeCode+"' AND "
				+ "subdivision is NOT NULL GROUP BY s.office_id ,subdivision ORDER BY SUBDIVISION" ;
		}*/
		
		
		if (officeType.equalsIgnoreCase("circle")) {
			qry = "SELECT s.office_id,SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f"
					+ " WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND circle_code = '"+ officeCode + "' GROUP BY s.office_id ,SUBDIVISION ORDER BY SUBDIVISION";
		} else if (officeType.equalsIgnoreCase("region")) {
			qry = "SELECT s.office_id,SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f "
					+ "WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND  zone_code = '"+ officeCode + "' GROUP BY s.office_id ,SUBDIVISION ORDER BY SUBDIVISION";
		} else if (officeType.equalsIgnoreCase("corporate")) {
			qry = "SELECT s.office_id,SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f "
					+ "WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND  discom_code = '"+ officeCode + "' GROUP BY s.office_id ,SUBDIVISION ORDER BY SUBDIVISION";
		}
//		System.out.println("subdivision-- qry:"+qry);
		Object SubdivName = new ArrayList<>();
		try {
			
			SubdivName =    dtdetailsService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("InstockMeters", meterInventoryService.getALLInstockMeters());
		model.addAttribute("SubdivName", SubdivName);
		model.put("msg", msg);
		//model.put("ssnames", dtdetailsService.getsubstation());
		msg = "";
		return "DTdetails";

	}
	// Cross DT No
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/addNoCrossDTdetails", method = {RequestMethod.POST, RequestMethod.GET })
	public String addDTdetails(ModelMap model, HttpServletRequest request,HttpSession session) {
		long currtime = System.currentTimeMillis();
		String dtname = request.getParameter("dtname");
		String dttype = request.getParameter("dttype");
		String dtcapacity = request.getParameter("dtcapacity");
		String dtphase = request.getParameter("dtphase");
		String tpdtcode = request.getParameter("tpdtcode");
		String tpparentcode = request.getParameter("tpparentcode");
		String parentfeederIdName = request.getParameter("parentfeeder");
		String[] fidfname =parentfeederIdName.split("@");
		String parentfeeder = fidfname[0];
		String feedername = fidfname[1];
		String meterserialno = request.getParameter("meterno");
		String metermanufacture = request.getParameter("metermanufacturer");
		String mf = request.getParameter("mf");
		String parentfeedersub=request.getParameter("parentfeedersub");
		String substationcode=parentfeedersub.split("@")[0];
		String[] sdoname=request.getParameter("subdiv").split("@");
		String[] town=request.getParameter("town").split("@");
		
		
		String sdocode=sdoname[0];
		HashMap<String, String> hh=new HashMap<>();
		try {
		 hh=dtdetailsService.getALLLocationData(sdocode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String zone=hh.get("ZONE");
		String circle=hh.get("CIRCLE");
		String division=hh.get("DIVISION");
		String subdivision=hh.get("SUBDIVISION");
		DtDetailsEntity d = new DtDetailsEntity();
		MasterMainEntity master=new MasterMainEntity();
		
		d.setDtname(dtname);
		d.setDttpid(tpdtcode);
		d.setDttype(dttype);
		String sql="SELECT DISTINCT sstp_id FROM meter_data.substation_details WHERE sstp_id='"+substationcode+"' AND tp_parent_id is not NULL AND trim(tp_parent_id)<>'';";
		try{
			String ss_tp_id=String.valueOf(entityManager.createNativeQuery(sql).getSingleResult());
			d.setParent_substation(ss_tp_id);
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			d.setPhase(Integer.parseInt(dtphase));
			master.setPhase((String)dtphase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		d.setParent_feeder(feedername);
		d.setTpparentid(tpparentcode);
		d.setParentid(parentfeeder);
		d.setMeterno(meterserialno);
		d.setMetermanufacture(metermanufacture);
		d.setCrossdt(0);
		d.setMf(1.0);
		d.setVolt_mf(1.0);
		d.setCurrent_mf(1.0);
		d.setSubdivision(subdivision);
		d.setTp_town_code(town[0]);
		d.setDeleted(0);
		
		String dtid=dtdetailsService.getDtid();
		try {
			if(dtid!=null && dtid!="")
			{
				d.setDt_id(dtid);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (dtcapacity != null && dtcapacity!="") {
				d.setDtcapacity(Double.parseDouble(dtcapacity));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(sdocode!=null && sdocode!=""){
				d.setOfficeid(Long.parseLong(sdocode));
				master.setSdocode(sdocode);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			if(mf!=null && mf!=""){
//				d.setMf(Double.parseDouble(mf));
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		String userName = (String) session.getAttribute("username");
		d.setEntryby(userName);
		d.setEntrydate(new Timestamp(currtime));
		try {
			dtdetailsService.save(d);
			//Object[] obj=(Object[]) meterInventoryService.getCtRatioPtRatio(meterserialno);
			master.setMtrno(meterserialno);
			master.setMtrmake(metermanufacture);
			master.setFdrcategory("DT");
			master.setCustomer_name(dtname);
			master.setAccno(dtid);
			master.setZone(zone);
			master.setCircle(circle);
			
			master.setDivision(division);
			master.setSubdivision(subdivision);
			master.setTown_code(town[0]);
			master.setSubstation(parentfeedersub.split("@")[1]);
		//	if(obj.length>0) {
			master.setCt_ratio("1");
			master.setPt_ratio("1");
		//	}
			master.setFdrname(feedername);
			master.setFdrcode(parentfeeder);
			//master.setMf(mf);
			master.setFdrtype("IPDS");
			master.setDlms("DLMS");
			master.setDiscom("TNEB");
			master.setInstallation_date(new Date());
			master.setHes_type("GENUS");
			master.setFeeder_type("Regular Feeder");
			master.setFeeder_status("Feeder Live");
			master.setLocation_id(tpdtcode);
			master.setCreate_time(new Timestamp(currtime));
			master.setMf("1");
			//master.setVoltage_kv("230");
			masterMainService.update(master);
			meterInventoryService.updateMeterNoInstalled(meterserialno, userName);
			/* msg = "DT DETAILS ADDED SUCCESFULLY"; */
		} catch (Exception e) {
			msg=e.getMessage();
			
			e.printStackTrace();
		}
		msg = "DT DETAILS ADDED SUCCESFULLY";
		/*List<?> dtdetails = dtdetailsService.getdtdetails();
		model.addAttribute("dtDetails", dtdetails);*/
		return "redirect:/DTdetails";

	}

	//  cross DT yes
	@RequestMapping(value = "/addCrossDTdetails", method = {RequestMethod.POST, RequestMethod.GET })
	public String addCrossDTdetails(ModelMap model, HttpServletRequest request,HttpSession session) {
		try {
		
		long currtime = System.currentTimeMillis();
		String dtide = request.getParameter("dtId");
		//String dtname = request.getParameter("dtNamesList");
		
		String[] dtNameId=request.getParameter("dtNamesList").split("@");
		String dtname=dtNameId[1];
		
		String dttype = request.getParameter("dttype");
		String parentfeederIdName = request.getParameter("parentfeeder");
		String[] fidfname =parentfeederIdName.split("@");
		String parentfeeder = fidfname[0];
		String feedername = fidfname[1];
		String meterserialno = request.getParameter("meterno");
		String metermanufacture = request.getParameter("metermanufacturer");
		String mf = request.getParameter("mf");
		String parentfeedersub=request.getParameter("parentfeedersub");
		String substationname=parentfeedersub.split("@")[1];
		String[] sdoname=request.getParameter("subdiv").split("@");
		String sdocode=sdoname[0];
		String[] town=request.getParameter("town").split("@");
		
		
		DtDetailsEntity d = new DtDetailsEntity();
		d.setDtname(dtname);
		d.setDttype(dttype);
		d.setParentid(parentfeeder);
		d.setMeterno(meterserialno);
		d.setParent_feeder(feedername);
		d.setMetermanufacture(metermanufacture);
		d.setParent_substation(substationname);
		String userName = (String) session.getAttribute("username");
		d.setEntryby(userName);
		d.setEntrydate(new Timestamp(currtime));
		d.setCrossdt(1);
		d.setVolt_mf(1.0);
		d.setCurrent_mf(1.0);
		d.setTp_town_code(town[0]);
		d.setDeleted(0);
		String dtid=dtdetailsService.getDtid();
		try {
			if(dtid!=null && dtid!="")
			{
				d.setDt_id(dtid);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(mf!=null && mf!=""){
				d.setMf(Double.parseDouble(mf));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(sdocode!=null && sdocode!=""){
				d.setOfficeid(Long.parseLong(sdocode));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<String, String> hh=dtdetailsService.getALLLocationData(sdocode);
		String zone=hh.get("ZONE");
		String circle=hh.get("CIRCLE");
		String division=hh.get("DIVISION");
		String subdivision=hh.get("SUBDIVISION");
			try {
				d.setSubdivision(subdivision);
				dtdetailsService.save(d);
				MasterMainEntity master=new MasterMainEntity();
				master.setMtrno(meterserialno);
				master.setMtrmake(metermanufacture);
				master.setFdrcategory("CROSSDT");
				master.setCustomer_name(dtname);
				master.setAccno(dtid);
				master.setZone(zone);
				master.setCircle(circle);
				master.setDivision(division);
				master.setSubdivision(subdivision);
				master.setVoltage_kv("230");
				
				masterMainService.save(master);
				
				meterInventoryService.updateMeterNoInstalled(meterserialno, userName);
				msg = "DT DETAILS ADDED SUCCESFULLY";
			} catch (Exception e) {
			msg=e.getMessage();
			e.printStackTrace();
		   }
		} catch (Exception e) {
			e.printStackTrace();
			msg=e.getMessage();
		}
		/*List<?> dtdetails = dtdetailsService.getdtdetails();
		model.addAttribute("dtDetails", dtdetails);*/
		return "redirect:/DTdetails";
	}

	// cross DT yes Cross Point Metered(No)

	@RequestMapping(value = "/addCrossDTSubNo", method = { RequestMethod.POST,RequestMethod.GET })
	public String addCrossDTSubNo(ModelMap model, HttpServletRequest request,HttpSession session) {
		/*
		long currtime = System.currentTimeMillis();
		String dtname = request.getParameter("dtname");
		String Consumptionper = request.getParameter("Consumptionper");
		String officeCode = (String) session.getAttribute("officeCode");
		
		DtDetailsEntity d = new DtDetailsEntity();
		
		String parentfeederIdName = request.getParameter("parentfeeder");
		String[] fidfname =parentfeederIdName.split("@");
		
		String parentfeeder = fidfname[0];
		String feedername = fidfname[1];
		d.setParentid(parentfeeder);
		d.setParent_feeder(feedername);
		String[] sdoname=request.getParameter("subdiv").split("@");
		
		String dtid=dtdetailsService.getDtid();
		try {
			if(dtid!=null && dtid!="")
			{
				d.setDt_id(dtid);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		d.setDtname(dtname);
		try {
			if (Consumptionper!=null && Consumptionper!="") {
				d.setConsumptionperc(Double.parseDouble(Consumptionper));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userName = (String) session.getAttribute("username");
		d.setEntryby(userName);
		try {
			if (officeCode!=null && officeCode!="" ) {
				d.setOfficeid(Long.parseLong(officeCode));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		d.setCrossdt(1);
		d.setEntrydate(new Timestamp(currtime));
		//dtdetailsService.save(d);
		msg = "DT DETAILS ADDED SUCCESFULLY";
		List<?> dtdetails = dtdetailsService.getdtdetails();
		model.addAttribute("dtDetails", dtdetails);
		return "redirect:/DTdetails";
	*/
		try {
	
		String[] dtNameId=request.getParameter("dtNamesList").split("@");
		String dtid=dtNameId[0];
		String Consumptionper = request.getParameter("Consumptionper");
		
		int result= dtdetailsService.updateConsumption(dtid, Consumptionper);
		if(result>0){
		msg = "DT DETAILS ADDED SUCCESFULLY";
		}
		else if(result==0){
		msg = "FAILED TO ADD DT DETAILS";
		}
		List<?> dtdetails = dtdetailsService.getdtdetails();
		model.addAttribute("dtDetails", dtdetails);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAILED TO ADD DT DETAILS";
		}
		return "redirect:/DTdetails";
	
	}

	@RequestMapping(value = "/editdtdetails/{id}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> editdtdetails(@PathVariable String id, ModelMap model,HttpServletRequest request) {
		List<?> changedlist = dtdetailsService.getChangedDetails(Integer.parseInt(id));
		return changedlist;
	}

	@RequestMapping(value = "/Modifydtdetails", method = { RequestMethod.POST,RequestMethod.GET })
	public String Modifydtdetails(ModelMap model, HttpServletRequest request,HttpSession session) {
		try {
		
		String userName = (String) session.getAttribute("username");
		String oldmeterno=request.getParameter("oldmeterno");
		String mfdatechng=request.getParameter("mfdatechngId");
		String mtrChangeDate=request.getParameter("mtrdatechngId");
		long currtime = System.currentTimeMillis();
		String dtide = request.getParameter("dtId");
		String dtnamee = request.getParameter("dtname");
		String editdttype = request.getParameter("dttype");
		String Editdtcapacity = request.getParameter("dtcapacity");
		String Editdtphase = request.getParameter("dtphase");
		String edittpdtcode = request.getParameter("tpdtcode");
		String edittpparentcode = request.getParameter("tpparentcode");
		String editmetersrno = request.getParameter("meterno");
		String editmetermf = request.getParameter("metermanufacturer");
		String Editmf = request.getParameter("mf");
		String EditConsumptionper = request.getParameter("Consumptionper");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		System.out.println(latitude +"----"  +longitude);
		System.out.println(dtnamee +"----"  +Editdtphase);
		System.out.println(Editdtcapacity +"----"  +edittpdtcode);
		
		
		List<DtDetailsEntity> dtEntity = dtdetailsService.getDtDetailsByDttpid(edittpdtcode);
		// If multiDt or singleDt is present in this particular  then below steps
		if (!dtEntity.isEmpty()) {
			for (int i = 0; i < dtEntity.size(); i++) {
					
						DtDetailsEntity dtMaster = (DtDetailsEntity) dtEntity.get(i);
						
						dtMaster.setDtname(dtnamee);		
						dtMaster.setDttype(editdttype);		
						Double editdtcapacity = null;
//						try {
//							if(Editdtcapacity!=null && Editdtcapacity!=""){
						if(!(Editdtcapacity.isEmpty())) {
							editdtcapacity=Double.parseDouble(Editdtcapacity);
							dtMaster.setDtcapacity(editdtcapacity);
						}
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
						//}
						
						Integer editdtphase=null;
						System.err.println("Editdtphase---"+Editdtphase);
//						try {
//							if(Editdtphase!=null && Editdtphase!=""){
						     if(!(Editdtphase.isEmpty())) {
								editdtphase =Integer.parseInt(Editdtphase);
								dtMaster.setPhase(editdtphase);
						     }
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						try {
//							if(latitude!=null && longitude!=null){
						if(!(latitude.isEmpty())) {
						dtMaster.setGeo_cord_x(Double.parseDouble(latitude));//Issue
						}
						if(!(longitude.isEmpty())) {
						dtMaster.setGeo_cord_y(Double.parseDouble(longitude));
						}
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
						
				//		System.out.println(dtMaster.getGeo_cord_x()+"----------"+dtMaster.getGeo_cord_y());
						
						dtMaster.setUpdate(userName);
						dtMaster.setUpdatedate(new Timestamp(currtime));
						Timestamp mtrChangeTime=null;
						Timestamp mfChangeTime=null;		
								
						DtDetailsHistoryEntity dthst=new DtDetailsHistoryEntity();
						dthst.setDtname(dtMaster.getDtname());
						dthst.setDttype(dtMaster.getDttype());
						dthst.setTpparentid(dtMaster.getTpparentid());
						dthst.setMeterno(dtMaster.getMeterno());
						dthst.setCrossdt(dtMaster.getCrossdt());
						dthst.setSubdivision(dtMaster.getSubdivision());
						dthst.setOfficeid(dtMaster.getOfficeid());
						dthst.setMetermanufacture(dtMaster.getMetermanufacture());
						dthst.setDt_id(dtMaster.getDt_id());
						dthst.setDttpid(dtMaster.getDttpid());
						dthst.setMf(dtMaster.getMf());
						dthst.setPhase(dtMaster.getPhase());
						dthst.setParent_feeder(dtMaster.getParent_feeder());
						dthst.setParent_substation(dtMaster.getParent_substation());
						dthst.setParentid(dtMaster.getParentid());
						dthst.setGeo_cord_x(dtMaster.getGeo_cord_x());
						dthst.setGeo_cord_y(dtMaster.getGeo_cord_y());
						dthst.setTp_town_code(dtMaster.getTp_town_code());
						dthst.setOlddtmtr(dtMaster.getOlddtmtr());
						if(dtMaster.getDtcapacity()!=null){
							dthst.setDtcapacity(dtMaster.getDtcapacity());
						}
						dthst.setEntryby(userName);
						dthst.setEntrydate(new Timestamp(System.currentTimeMillis()));
						
						if(dtMaster.getConsumptionperc()!=null){
						dthst.setConsumptionperc(dtMaster.getConsumptionperc());
						}				
						if(mfdatechng!=""){
							try {
							      DateFormat formatter;
							      formatter = new SimpleDateFormat("yyyy-MM-dd");
							      Date date;
								  date = (Date) formatter.parse(mfdatechng);
								  mfChangeTime = new Timestamp(date.getTime());
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
						if(mfChangeTime!=null){
							dtMaster.setMfchangedate(mfChangeTime);
							dtMaster.setMfflag(1);
							dthst.setMfchangedate(mfChangeTime);
							dthst.setMfflag(1);
						}
						
						
						MasterMainEntity master = masterMainService.getEntityByMtrNO(dtMaster.getMeterno());
						if (master != null) {
							master.setPhase(Editdtphase);
							master.setLatitude(latitude);
							master.setLongitude(longitude);
							master.setCustomer_name(dtnamee);
					
							try {
								masterMainService.update(master);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						try {	
							dtdetailsService.update(dtMaster);
							dtDetailsHistoryService.save(dthst);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						msg = " DT DETAILS UPDATED SUCCESFULLY";
			}
		}
		
		
//		DtDetailsEntity dt = dtdetailsService.find(Long.parseLong(dtide));
//		
//		//dthstory
//		DtDetailsHistoryEntity dthst=new DtDetailsHistoryEntity();
//		dthst.setDtname(dt.getDtname());
//		dthst.setDttype(dt.getDttype());
//		dthst.setTpparentid(dt.getTpparentid());
//		dthst.setMeterno(dt.getMeterno());
//		dthst.setCrossdt(dt.getCrossdt());
//		dthst.setSubdivision(dt.getSubdivision());
//		dthst.setOfficeid(dt.getOfficeid());
//		dthst.setMetermanufacture(dt.getMetermanufacture());
//		dthst.setDt_id(dt.getDt_id());
//		dthst.setDttpid(dt.getDttpid());
//		dthst.setMf(dt.getMf());
//		dthst.setPhase(dt.getPhase());
//		dthst.setParent_feeder(dt.getParent_feeder());
//		dthst.setParent_substation(dt.getParent_substation());
//		dthst.setParentid(dt.getParentid());
//		dthst.setGeo_cord_x(dt.getGeo_cord_x());
//		dthst.setGeo_cord_y(dt.getGeo_cord_y());
//		if(dt.getDtcapacity()!=null){
//			dthst.setDtcapacity(dt.getDtcapacity());
//		}
//		dthst.setEntryby(userName);
//		dthst.setEntrydate(new Timestamp(System.currentTimeMillis()));
//		
//		if(dt.getConsumptionperc()!=null){
//		dthst.setConsumptionperc(dt.getConsumptionperc());
//		}
//		
//		//main table modification
//		
//		
//		dt.setDtname(dtnamee);		
//		dt.setDttype(editdttype);		
//		Double editdtcapacity = null;
//		try {
//			if(Editdtcapacity!=null && Editdtcapacity!=""){
//			editdtcapacity=Double.parseDouble(Editdtcapacity);
//			dt.setDtcapacity(editdtcapacity);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Integer editdtphase=null;
//		try {
//			if(Editdtphase!=null && Editdtphase!=""){
//				editdtphase =Integer.parseInt(Editdtphase);
//				dt.setPhase(editdtphase);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		dt.setDttpid(edittpdtcode);		
//		dt.setTpparentid(edittpparentcode);		
//		dt.setMeterno(editmetersrno);		
//		dt.setMetermanufacture(editmetermf);		
//		Double editmf =null;
//		try {
//			if(Editmf!=null && Editmf!="" ){
//			editmf = Double.parseDouble(Editmf);
//			dt.setMf(editmf);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Double editConsumptionper=null;
//		try {
//			if(EditConsumptionper!=null && EditConsumptionper!=""){
//			 editConsumptionper = Double.parseDouble(EditConsumptionper);	
//			 dt.setConsumptionperc(editConsumptionper);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		dt.setUpdate(userName);
//		dt.setUpdatedate(new Timestamp(currtime));
//		Timestamp mtrChangeTime=null;
//		Timestamp mfChangeTime=null;
//		if(mtrChangeDate!=""){
//		try {
//		      DateFormat formatter;
//		      formatter = new SimpleDateFormat("yyyy-MM-dd");
//		      Date date;
//			  date = (Date) formatter.parse(mtrChangeDate);
//			  mtrChangeTime = new Timestamp(date.getTime());
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		if(mfdatechng!=""){
//			try {
//			      DateFormat formatter;
//			      formatter = new SimpleDateFormat("yyyy-MM-dd");
//			      Date date;
//				  date = (Date) formatter.parse(mfdatechng);
//				  mfChangeTime = new Timestamp(date.getTime());
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//			}
//		if(mfChangeTime!=null){
//			dt.setMfchangedate(mfChangeTime);
//			dt.setMfflag(1);
//			dthst.setMfchangedate(mfChangeTime);
//			dthst.setMfflag(1);
//		}
//		
//		if(mtrChangeTime!=null){
//			dt.setMeterchangedate(mtrChangeTime);
//			dt.setMeterchangeflag(1);
//			dthst.setMeterchangedate(mtrChangeTime);
//			dthst.setMeterchangeflag(1);
//			
//		}
//		
//		try {
//			if(!oldmeterno.equalsIgnoreCase(dt.getMeterno())){
//				meterInventoryService.updateMeterNoInstalled(dt.getMeterno(),request.getSession().getAttribute("username")+"");
//				//consumerMasterService.updateMasterMainMeterNo(consumerMasterId.getMeterno(), consumerMasterId.getMf(), mtrChangeTime);
//				//masterMainService.mtrReplaceUpdateNewMtrno(oldmeterno, dt.getMeterno(), mtrChangeTime+"", dt.getMf()+"");
//			}
//			
//		dtDetailsHistoryService.save(dthst);
//		dtdetailsService.update(dt);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//dtDetailsHistoryService.save(dthst);
//		//dtdetailsService.update(dt);
//		msg = " DT DETAILS UPDATED SUCCESFULLY";
		
		} catch (Exception e) {
			e.printStackTrace();
			msg = " DT DETAILS NOT UPDATED ";
			//msg=e.getMessage();
		}
		return "redirect:/DTdetails";
	}

	@RequestMapping(value = "/getdtname", method = { RequestMethod.POST,RequestMethod.GET })
	public @ResponseBody List getdtname(HttpServletRequest request, ModelMap model) {

		List<?> dtname = dtdetailsService.getdtname();
		return dtname;
	}
	@RequestMapping(value = "/Showdivision/{circle}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List Showdivision(@PathVariable String circle, HttpServletRequest request,ModelMap model) {
		
		List<?> Division = dtdetailsService.getShowdivision(circle);
		return Division;
	}
	@RequestMapping(value = "/getFeederNameForDt/{subStationName}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getFeederNameForDt(@PathVariable String subStationName,HttpServletRequest request, ModelMap model) {
		System.out.println("subStationName--" + subStationName);
		List<?> feedersName = dtdetailsService.getFeederNameForDt(subStationName);
		return feedersName;
	}

	@RequestMapping(value="/getDTDeTailsByMeterNo/{meterno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object retriveMrName(@PathVariable String meterno,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		System.out.println(meterno);
		return dtdetailsService.getDtDetailsBymetrno(meterno);
	}
	
	@RequestMapping(value="/deleteDtdetails",method={RequestMethod.POST,RequestMethod.GET})
   	public  String deleteDtdetails(HttpServletResponse response,HttpServletRequest request,ModelMap model) 
   	{
		String dtide = request.getParameter("dtId");
		/*if(dtide!=null){
			try {
				long deleteId=Long.parseLong(dtide);
				dtdetailsService.delete(deleteId);
				msg = " DT details deleted succsessfully";
			} catch (Exception e) {
				e.printStackTrace();
				msg = " DT details are Not Deleted";
			}
		}*/
   		msg=dtdetailsService.deletedt(dtide);
  	 
        return "redirect:/DTdetails";
   	}
	
	/*@RequestMapping(value="/deleteDtdetails/{id}",method= {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String deleteDtdetails(@PathVariable String id,String parentid,HttpServletRequest request,ModelMap model)
	{
		String qry = null ;
		qry="select *from meter_data.consumermaster where dtcode = '"+id+"' ";
		List resultList = null;
		try {
			
        	resultList =  dtdetailsService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			System.out.println(resultList.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
         
       if(resultList.size()==0) {
    	  
    	  String qry1="update meter_data.dtdetails set deleted=1 where dt_id='"+id+"' "; 
    	  System.out.println(qry1);
    	  List list = null;
    	  int i=dtdetailsService.deletedt(qry1);
    	  try {
			
        	int i =  dtdetailsService.getCustomEntityManager("postgresMdas").createNativeQuery(qry1).executeUpdate();
			System.out.println(resultList.size());
            
			} catch (Exception e) {
				e.printStackTrace();
			}
    	return "Deleted";
       	 }
       else {
      		return "Record Exist ";
            }  
        }	
	*/
	@RequestMapping(value="/checkDuplicatedtParent/{tpdtcode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object checkDuplicatetpdtcode(@PathVariable String tpdtcode,HttpServletResponse response,HttpServletRequest request,ModelMap model) throws JsonParseException, JsonMappingException, IOException
	{
		System.out.println(tpdtcode);
		String qry = null ; 
			qry="select  * from meter_data.dtdetails  where dttpid= '"+tpdtcode+"'";
		
			List resultList = null;
	        try {
	        	resultList =  dtdetailsService.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        if(resultList.size()>=1) {
	          	 return "Code Exist";
	          	 }
	          else {
	         		return "Code not Exist ";
	               }  
	}
	
	@RequestMapping(value = "/getParentFeederSubstationName/{officeid}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getParentFeederSubstationName(@PathVariable String officeid,HttpServletRequest request, ModelMap model) {
		System.out.println("subStationName--" + officeid);
		List<?> ssname = dtdetailsService.getsubstation(officeid);
		return ssname;
	}
	@RequestMapping(value = "/getDTdataByfeederNames/{fdrid}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getDTdataByfeederNames(@PathVariable String fdrid,HttpServletRequest request, ModelMap model) {
		System.out.println("subStationName--" + fdrid);
		List<?> dtnames = dtdetailsService.getDTdataByfeederNames(fdrid);
		return dtnames;
	}
	 @RequestMapping(value = "/showDTNameBySubdivAndSSname", method = { RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody Object getfdridnamedetails(HttpServletRequest request) 
		{

		 

		 String parentid=request.getParameter("parentid");
		 String sdoCode=request.getParameter("sdoCode");

		 // System.out.println("This is the response " + dtdetailsService.getDTIdNamenameBySubdiv(parentid));
		    return dtdetailsService.getDTIdNamenameBySubdiv(parentid,sdoCode);
		}


	 
	 @RequestMapping(value = "/showDTNameBySubdivAndSSnameNew", method = { RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody Object showDTNameBySubdivAndSSnameNew(HttpServletRequest request) 
		{
		 String parentid=request.getParameter("parentid");
		 String officeId=request.getParameter("officeId");
		  System.out.println("This is the response " + dtdetailsService.getDTIdNamenameBySubdivandSubs(parentid, officeId));
		    return dtdetailsService.getDTIdNamenameBySubdivandSubs(parentid, officeId);
		}
	 
	 @RequestMapping(value = "/getAlldetailsData", method = { RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody Object getAlldetailsData(HttpServletRequest request) 
		{
//		    String ssid = request.getParameter("ssid").trim();
		 	String region = request.getParameter("region").trim();
			String tp_towncode = request.getParameter("town").trim();
			String frdId = request.getParameter("frdId").trim();
			String circle = request.getParameter("circle").trim();
			return  dtdetailsService.getdtdetailsBytownCode(region,frdId, tp_towncode,circle);
		}
	  
		
	 @RequestMapping(value="/dtdetailspdf",method={RequestMethod.GET,RequestMethod.POST})
	 public @ResponseBody void dtdetailspdf(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
       {
		 String circle=request.getParameter("circle");
		// String town=request.getParameter("town");
		 String ssid=request.getParameter("ssid");

		String tp_towncode = request.getParameter("town").trim();
		String fdrID = request.getParameter("fdrID").trim();
		
		if (circle.equalsIgnoreCase("ALL")){
			circle="%";
		}
		if (tp_towncode.equalsIgnoreCase("ALL")){
			tp_towncode="%";
		}
		if (fdrID.equalsIgnoreCase("ALL")){
			fdrID="%";
		}
		 
		 
//		 dtdetailsService.getDtDetailsPdf(request, response, circle, town, ssid);
		dtdetailsService.getDtDetailsPdf(request, response, circle, tp_towncode, fdrID);
       }
	 
	 @Transactional(propagation = Propagation.REQUIRED)
		@RequestMapping(value = "/uploadDtFile", method = { RequestMethod.GET, RequestMethod.POST })
		public String uploadDtFile(HttpServletRequest request, HttpServletResponse response, ModelMap model,
				HttpSession session) {
		 String extension = null;
		 String excel = "xlsx";
		 try {

				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile myFile = multipartRequest.getFile("fileUpload");

				// File temp_file = new File(myFile.getOriginalFilename());
				String fileName = myFile.getOriginalFilename();

				if (myFile != null && !myFile.isEmpty() && myFile.getOriginalFilename() != " ") {

					extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

					if (!extension.equals(excel)) {
						uploadFlag = 1;
						msg = "Please choose only .xlsx file to upload.!!!!!";				
						model.put("msg", msg);
//						model.addAttribute("alert_type", "success");
//						model.addAttribute("results", "Please choose only .xlsx file to upload.!!!!!");
					} else {

						InputStream file = myFile.getInputStream();
						// Get the workbook instance for XLSX file
						XSSFWorkbook workbook = new XSSFWorkbook(file);
						// Get first sheet from the workbook
						XSSFSheet sheet = workbook.getSheetAt(0);
						// Get iterator to all the rows in current sheet

						List<String> values;
						int noOfSheets = workbook.getNumberOfSheets();
						int lastRows = 0;
						int lastColumn = 0;
						String SheetName = "";
						//System.out.println(sheet.getLastRowNum());
						if (sheet.getLastRowNum() != 0) {

							for (int i = 0; i < noOfSheets; i++) {
								try {

									SheetName = workbook.getSheetName(i);
									lastRows = workbook.getSheetAt(i).getLastRowNum();
									//System.out.println("LastRow="+lastRows);
									values = new ArrayList<String>();

									String[] DT_Code = new String[lastRows + 1];
									String[] DT_Type = new String[lastRows + 1];
									String[] DT_Name = new String[lastRows + 1];
									String[] DT_Capacity = new String[lastRows + 1];
									String[] Phase = new String[lastRows + 1];
									String[] Feeder_Code = new String[lastRows + 1];
									String[] Meter_Sr_Number = new String[lastRows + 1];
									//String[] Meter_Manufacturer = new String[lastRows + 1];
									//String[] MF = new String[lastRows + 1];
									String[] Town_Code = new String[lastRows + 1];
									String[] Latitude = new String[lastRows + 1];
									String[] Longitude = new String[lastRows + 1];

									if (workbook.getSheetAt(i).getRow(0) != null) {

										lastColumn = workbook.getSheetAt(i).getRow(0).getLastCellNum();

										String cellGotValue = "";

										int DT_Codecol = 0;
										int DT_Typecol = 0;
										int DT_Namecol = 0;
										int DT_Capacitycol = 0;
										int Phasecol = 0;
										int Feeder_Codecol = 0;
										int Meter_Sr_Numbercol = 0;
										//int Meter_Manufacturercol = 0;
										//int MFcol = 0;
										int Town_Codecol = 0;
										int Latitudecol = 0;
										int Longitudecol = 0;

										if (lastRows == 0) {
											uploadFlag = 2;
											msg = "Records are not avaliable in excel to upload";
											model.put("msg", "Records are not avaliable in excel to upload");
										}

										for (int j = 0; j <= lastRows; j++) {

											//
											if (j == 0)// To get Column Names First row in Excel
											{
												for (int k = j; k < lastColumn; k++) {
													XSSFCell cellNull = workbook.getSheetAt(i).getRow(j).getCell(k);
													if (cellNull != null) {
														cellGotValue = cellNull.getStringCellValue();
														cellGotValue = cellGotValue.trim();

													}
													if (cellGotValue.equalsIgnoreCase("DT_Code")) {
														DT_Codecol = k;

													}
													if (cellGotValue.equalsIgnoreCase("DT_Type")) {
														DT_Typecol = k;

													}
													if (cellGotValue.equalsIgnoreCase("DT_Name")) {
														DT_Namecol = k;

													}
													if (cellGotValue.equalsIgnoreCase("DT_Capacity")) {
														DT_Capacitycol = k;

													}
													if (cellGotValue.equalsIgnoreCase("Phase")) {
														Phasecol = k;

													}
													if (cellGotValue.equalsIgnoreCase("Feeder_Code")) {
														Feeder_Codecol = k;

													}
													if (cellGotValue.equalsIgnoreCase("Meter_Sr_Number")) {
														Meter_Sr_Numbercol = k;

													}
//													if (cellGotValue.equalsIgnoreCase("Meter_Manufacturer")) {
//														Meter_Manufacturercol = k;
//
//													}
//													if (cellGotValue.equalsIgnoreCase("MF")) {
//														MFcol = k;
//
//													}
													if (cellGotValue.equalsIgnoreCase("Town_Code")) {
														Town_Codecol = k;

													}
													if (cellGotValue.equalsIgnoreCase("Latitude")) {
														Latitudecol = k;

													}
													if (cellGotValue.equalsIgnoreCase("Longitude")) {
														Longitudecol = k;

													}
												}
											}

											else {
												String nextLine[] = null;

												for (int k = 0; k < lastColumn; k++) {

													XSSFCell cellNull = workbook.getSheetAt(i).getRow(j).getCell(k);

													if (cellNull != null) {

														if (cellNull.getCellType() == Cell.CELL_TYPE_NUMERIC) {
															if (DateUtil.isCellDateFormatted(cellNull)) {
																Date date = cellNull.getDateCellValue();
																SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
																cellGotValue = sdf.format(date);
																cellGotValue = cellGotValue.trim();
															} else {
																if (cellNull.getCellType() == Cell.CELL_TYPE_NUMERIC) {
																	cellNull.setCellType(Cell.CELL_TYPE_STRING);
																}
																cellGotValue = cellNull.getStringCellValue();

															}
														}

														if (cellNull.getCellType() == Cell.CELL_TYPE_FORMULA) {

															switch (cellNull.getCachedFormulaResultType()) {
															case Cell.CELL_TYPE_NUMERIC:

																break;
															case Cell.CELL_TYPE_STRING:

																break;
															}
														} else {

															DataFormatter formatter = new DataFormatter();
															Cell cell = cellNull;
															cellGotValue = formatter.formatCellValue(cell);
															cellGotValue = cellGotValue.trim();

														}

													} else {
														cellGotValue = "";
													}

													if (k == DT_Codecol) {
														DT_Code[j - 1] = cellGotValue.trim();
													}
													if (k == DT_Typecol) {
														DT_Type[j - 1] = cellGotValue.trim();
													}
													if (k == DT_Namecol) {
														DT_Name[j - 1] = cellGotValue.trim();
													}
													if (k == DT_Capacitycol) {
														DT_Capacity[j - 1] = cellGotValue.trim();
													}
													if (k == Phasecol) {
														Phase[j - 1] = cellGotValue.trim();

													}
													if (k == Feeder_Codecol) {
														Feeder_Code[j - 1] = cellGotValue.trim();
													}
													if (k == Meter_Sr_Numbercol) {
														Meter_Sr_Number[j - 1] = cellGotValue.trim();
													}
//													if (k == Meter_Manufacturercol) {
//														Meter_Manufacturer[j - 1] = cellGotValue.trim();
//
//													}
//													if (k == MFcol) {
//														MF[j - 1] = cellGotValue.trim();
//													}
													if (k == Town_Codecol) {
														Town_Code[j - 1] = cellGotValue.trim();
													}
													if (k == Latitudecol) {
														Latitude[j - 1] = cellGotValue.trim();
													}
													if (k == Longitudecol) {
														Longitude[j - 1] = cellGotValue.trim();
													}

												}

											}
										}

										for (int n = 0; n < lastRows; n++) {

											try {

												//DtDetailsEntity dtdetailsEntity = dtdetailsService.getDtByFdrcodeMrtId(DT_Code[n].trim(), Feeder_Code[n].trim(), Meter_Sr_Number[n].trim(), Town_Code[n].trim());
												
												List<DtDetailsEntity> dtdetailsEntity = dtdetailsService.getDtDetailsByTownFdrDtId(DT_Code[n].trim(), Town_Code[n].trim(),Feeder_Code[n].trim());
												if (!dtdetailsEntity.isEmpty()) {
													for (int d = 0; d < dtdetailsEntity.size(); d++) {
														DtDetailsEntity dtMaster = (DtDetailsEntity) dtdetailsEntity.get(d);
														if (dtMaster != null) {
															dtMaster.setDttype(DT_Type[n].trim());
															dtMaster.setDtname(DT_Name[n].trim());
															if(!(DT_Capacity[n].isEmpty())) {
															if(!DT_Capacity[n].trim().equalsIgnoreCase(""))
															dtMaster.setDtcapacity(Double.valueOf((DT_Capacity[n].split(","))[0]));
															}
															//dtMaster.setDtcapacity(Double.valueOf(DT_Capacity[n].trim()));
															if(!(Phase[n].isEmpty())) {
															dtMaster.setPhase(Integer.valueOf(Phase[n].trim()));
															}
															//dtMaster.setMetermanufacture(Meter_Manufacturer[n].trim());
															if(!(Latitude[n].isEmpty())) {
															dtMaster.setGeo_cord_x(Double.valueOf(Latitude[n].trim()));
															}
															if(!(Longitude[n].isEmpty())) {
															dtMaster.setGeo_cord_y(Double.valueOf(Longitude[n].trim()));
															}
															MasterMainEntity master = masterMainService.getEntityByMtrNOandTcode(Town_Code[n].trim(),dtMaster.getMeterno());
															if (master != null) {
																master.setPhase(Phase[n].trim());
																//master.setMtrmake(Meter_Manufacturer[n].trim());
																master.setCustomer_name(DT_Name[n].trim());
																master.setLatitude(Latitude[n].trim());
																master.setLongitude(Longitude[n].trim());
																

																try {
																	masterMainService.update(master);

																} catch (Exception e) {
																	e.printStackTrace();
																}
															}
		
															try {
																dtdetailsService.update(dtMaster);

															} catch (Exception e) {
																e.printStackTrace();
															}
														}
													}
												}
												msg = "Data Uploaded Successfully";
												model.put("msg", "Data Uploaded Successfully");
					
											} catch (Exception e) {
												uploadFlag = 3;
												msg = "OOPS! Something went wrong!!";
												model.put("msg", "OOPS! Something went wrong!!");
												e.printStackTrace();
											}

										}

									}

								} catch (Exception e) {
									uploadFlag = 3;
									msg = "OOPS! Something went wrong!!";
									model.put("msg", "OOPS! Something went wrong!!");
									e.printStackTrace();
								}

							}
						} 

					}

				}

			
			 
		} catch (Exception e) {		
			uploadFlag = 3;	
			msg = "OOPS! Something went wrong!!";
			model.put("msg", "OOPS! Something went wrong!!");
			e.printStackTrace();
		}
		 return "redirect:/DTdetails";
	 }
	 
	@RequestMapping(value="/dtdetailssampleExcel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object dtdetailssampleExcel(HttpServletRequest request,HttpServletResponse response,ModelMap model)
		{
		 try {

//				String circle=request.getParameter("circle");
//				String ssid=request.getParameter("ssid");
//				String tp_towncode = request.getParameter("town").trim();
			 	String region = request.getParameter("region").trim();
				String circle=request.getParameter("circle");
				//String ssid=request.getParameter("ssid");
				String tp_towncode = request.getParameter("town").trim();
				String fdrID = request.getParameter("fdrID").trim();
				
				if (region.equalsIgnoreCase("ALL")){
					region="%";
				}
				
				if (circle.equalsIgnoreCase("ALL")){
					circle="%";
				}
				if (tp_towncode.equalsIgnoreCase("ALL")){
					tp_towncode="%";
				}
				if (fdrID.equalsIgnoreCase("ALL")){
					fdrID="%";
				}
			
		    
			    String fileName = "DTSampleDetails";
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
	 
					header.createCell(0).setCellValue("DT_Code");
					header.createCell(1).setCellValue("DT_Type");
					header.createCell(2).setCellValue("DT_Name");
					header.createCell(3).setCellValue("DT_Capacity");
					header.createCell(4).setCellValue("Phase");
					header.createCell(5).setCellValue("Feeder_Code");
					header.createCell(6).setCellValue("Meter_Sr_Number");
					//header.createCell(7).setCellValue("Meter_Manufacturer");
					//header.createCell(8).setCellValue("MF");
					header.createCell(7).setCellValue("Town_Code");
					header.createCell(8).setCellValue("Latitude");
					header.createCell(9).setCellValue("Longitude");

					
					
				   List<?> dtData=null;
				     dtData =dtdetailsService.getdtdetailsBytownCode(region,fdrID, tp_towncode,circle);
					
					int count =1;
					//int cellNO=0;
		            for(Iterator<?> iterator=dtData.iterator();iterator.hasNext();){
		      	    final Object[] values=(Object[]) iterator.next();
		      		
		      		XSSFRow row = sheet.createRow(count);
		      		//cellNO++;
		      		//row.createCell(0).setCellValue(String.valueOf(cellNO+""));
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
		      		
		      		if(values[4]==null)
		      		{
		      			row.createCell(4).setCellValue(String.valueOf(""));
		      		}else
		      		{
		      			row.createCell(4).setCellValue(String.valueOf(values[4]));
		      		}
		      		if(values[5]==null)
		      		{
		      			row.createCell(5).setCellValue(String.valueOf(""));
		      		}else
		      		{
		      			row.createCell(5).setCellValue(String.valueOf(values[5]));
		      		}
		      		if(values[6]==null)
		      		{
		      			row.createCell(6).setCellValue(String.valueOf(""));
		      		}else
		      		{
		      			row.createCell(6).setCellValue(String.valueOf(values[6]));
		      		}
//		      		if(values[7]==null)
//		      		{
//		      			row.createCell(7).setCellValue(String.valueOf(""));
//		      		}else
//		      		{
//		      			row.createCell(7).setCellValue(String.valueOf(values[7]));
//		      		}
//		      		if(values[8]==null)
//		      		{
//		      			row.createCell(8).setCellValue(String.valueOf(""));
//		      		}else
//		      		{
//		      			row.createCell(8).setCellValue(String.valueOf(values[8]).split("\\.")[0]);
//		      		}
		      		if(values[9]==null)
		      		{
		      			row.createCell(7).setCellValue(String.valueOf(""));
		      		}else
		      		{
		      			row.createCell(7).setCellValue(String.valueOf(values[9]));
		      		}
		      		if(values[12]==null)
		      		{
		      			row.createCell(8).setCellValue(String.valueOf(""));
		      		}else
		      		{
		      			row.createCell(8).setCellValue(String.valueOf(values[12]));
		      		}
		      		if(values[13]==null)
		      		{
		      			row.createCell(9).setCellValue(String.valueOf(""));
		      		}else
		      		{
		      			row.createCell(9).setCellValue(String.valueOf(values[13]));
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
					response.setHeader("Content-Disposition", "inline;filename=\"SampleDtDetails.xlsx"+"\"");
					FileInputStream input = new FileInputStream(fileName);
					IOUtils.copy(input, servletOutputStream);
					servletOutputStream.flush();
					servletOutputStream.close();
					
					return null;	
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		}
	
	@RequestMapping(value = "/DtDetailsExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object DtDetailsExcel(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		try {
			String region = request.getParameter("region");
			String circle = request.getParameter("circle");
			//String ssid = request.getParameter("ssid");
			String tp_towncode = request.getParameter("town").trim();
			String fdrID = request.getParameter("fdrID").trim();
//			System.out.println("circle---" + circle + "ssid---" + ssid + "towncode---" + tp_towncode + "fdrid---"
//					+ fdrID + "Region---" + Region);

			//String regn = "", circlee = "", towncode = "", fdrid = "";
			if (region.equalsIgnoreCase("ALL")){
				region="%";
			}
			
			if (circle.equalsIgnoreCase("ALL")){
				circle="%";
			}
			if (tp_towncode.equalsIgnoreCase("ALL")){
				tp_towncode="%";
			}
			if (fdrID.equalsIgnoreCase("ALL")){
				fdrID="%";
			}

			String fileName = "DTDetails";
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(fileName);
			XSSFRow header = sheet.createRow(0);

			CellStyle lockedCellStyle = wb.createCellStyle();
			lockedCellStyle.setLocked(true);
			CellStyle unlockedCellStyle = wb.createCellStyle();
			unlockedCellStyle.setLocked(false);

			XSSFCellStyle style = wb.createCellStyle();
			style.setWrapText(true);
			sheet.setColumnWidth(0, 1000);
			XSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short) 10);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);

		
			header.createCell(0).setCellValue("Circle");
			header.createCell(1).setCellValue("Town");
			header.createCell(2).setCellValue("Feeder_Code");
			header.createCell(3).setCellValue("Feeder_Name");
			
			header.createCell(4).setCellValue("DT_Code");
			header.createCell(5).setCellValue("DT_Type");
			header.createCell(6).setCellValue("DT_Name");
			header.createCell(7).setCellValue("DT_Capacity");
			header.createCell(8).setCellValue("Phase");
			header.createCell(9).setCellValue("Meter_Sr_Number");
			header.createCell(10).setCellValue("Meter_Manufacturer");
			header.createCell(11).setCellValue("Latitude");
			header.createCell(12).setCellValue("Longitude");
			header.createCell(13).setCellValue("Region");
			// header.createCell(8).setCellValue("MF");
			// header.createCell(9).setCellValue("Town_Code");

			List<?> dtData = null;
			dtData = dtdetailsService.getdtdetailsBytownCode(region,fdrID, tp_towncode, circle);

			int count = 1;
			// int cellNO=0;
			for (Iterator<?> iterator = dtData.iterator(); iterator.hasNext();) {
				final Object[] values = (Object[]) iterator.next();

				XSSFRow row = sheet.createRow(count);
				// cellNO++;
				// row.createCell(0).setCellValue(String.valueOf(cellNO+""));

//				if (values[11] == null) {
//					row.createCell(0).setCellValue(String.valueOf(""));
//				} else {
//					row.createCell(0).setCellValue(String.valueOf(values[11]));
//				}
				
				
				if (values[10] == null) {
					row.createCell(0).setCellValue(String.valueOf(""));
				} else {
					row.createCell(0).setCellValue(String.valueOf(values[10]));
				}

				if (values[11] == null) {
					row.createCell(1).setCellValue(String.valueOf(""));
				} else {
					row.createCell(1).setCellValue(String.valueOf(values[9]) + "-" + String.valueOf(values[11]));
				}

				
				if (values[14] == null) {
					row.createCell(2).setCellValue(String.valueOf(""));
				} else {
					row.createCell(2).setCellValue(String.valueOf(values[14]));
				}
				
				
				if (values[5] == null) {
					row.createCell(3).setCellValue(String.valueOf(""));
				} else {
					row.createCell(3).setCellValue(String.valueOf(values[5]));
				}

				if (values[0] == null) {
					row.createCell(4).setCellValue(String.valueOf(""));
				} else {
					row.createCell(4).setCellValue(String.valueOf(values[0]));
				}

				if (values[1] == null) {
					row.createCell(5).setCellValue(String.valueOf(""));
				} else {
					row.createCell(5).setCellValue(String.valueOf(values[1]));
				}
				if (values[2] == null) {
					row.createCell(6).setCellValue(String.valueOf(""));
				} else {
					row.createCell(6).setCellValue(String.valueOf(values[2]));
				}
				if (values[3] == null) {
					row.createCell(7).setCellValue(String.valueOf(""));
				} else {
					row.createCell(7).setCellValue(String.valueOf(values[3]));
				}
				if (values[4] == null) {
					row.createCell(8).setCellValue(String.valueOf(""));
				} else {
					row.createCell(8).setCellValue(String.valueOf(values[4]));
				}
				if (values[6] == null) {
					row.createCell(9).setCellValue(String.valueOf(""));
				} else {
					row.createCell(9).setCellValue(String.valueOf(values[6]));
				}
				if (values[7] == null) {
					row.createCell(10).setCellValue(String.valueOf(""));
				} else {
					row.createCell(10).setCellValue(String.valueOf(values[7]));
				}
				if (values[12] == null) {
					row.createCell(11).setCellValue(String.valueOf(""));
				} else {
					row.createCell(11).setCellValue(String.valueOf(values[12]));
				}

				if (values[13] == null) {
					row.createCell(12).setCellValue(String.valueOf(""));
				} else {
					row.createCell(12).setCellValue(String.valueOf(values[13]));
				}
				
				if (values[19] == null) {
					row.createCell(13).setCellValue(String.valueOf(""));
				} else {
					row.createCell(13).setCellValue(String.valueOf(values[19]));
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
			response.setHeader("Content-Disposition", "inline;filename=\"DtDetails.xlsx" + "\"");
			FileInputStream input = new FileInputStream(fileName);
			IOUtils.copy(input, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();

			return null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
