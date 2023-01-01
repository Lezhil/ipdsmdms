package com.bcits.mdas.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.DtDetailsEntity;
import com.bcits.mdas.entity.AllowedDeviceVersionsEntity;
import com.bcits.mdas.entity.AmrBillsEntity;
import com.bcits.mdas.entity.AmrEventsEntity;
import com.bcits.mdas.entity.AmrInstantaneousEntity;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.FeederOutputEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.entity.ModemInstallationEntity;
import com.bcits.mdas.entity.ModemMasterEntity;
import com.bcits.mdas.entity.NewConsumersEntity;
import com.bcits.mdas.ftp.XmlHelper;
import com.bcits.mdas.service.AllowedDeviceVersionService;
import com.bcits.mdas.service.AmrBillsService;
import com.bcits.mdas.service.AmrEventsService;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.FeederMRDeviceService;
import com.bcits.mdas.service.FeederMRMasterService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.FeederOutputService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.ModemInstallationService;
import com.bcits.mdas.service.ModemMasterServiceMDAS;
import com.bcits.mdas.service.NewConsumerService;
import com.bcits.mdas.utility.BCITSLogger;

@Controller
public class FeederSurveyController 
{

	@Autowired
	private FeederMasterService feederService;

	@Autowired
	private MasterMainService masterMainService;

	@Autowired
	private FeederMRMasterService mrMasterService;



	@Autowired
	private AmrEventsService amrEventsService;

	@Autowired
	private AmrInstantaneousService amrInstantaneousService;

	@Autowired
	private AmrLoadService amrLoadService;

	@Autowired
	private AmrBillsService amrBillsService;

	@Autowired
	private FeederOutputService feederOutputService;

	@Autowired
	private NewConsumerService newConsumerService;

	@Autowired
	AllowedDeviceVersionService allowedDeviceVersionService;

	@Autowired
	FeederMRDeviceService mrDeviceService;

	@Autowired
	private ModemMasterServiceMDAS modemMasterService;

	@Autowired
	private ModemInstallationService modemInstallationService;

	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager postgresMdas;


	@RequestMapping(value = "/getAllFeederDataMobile", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object getAllFeederData(@RequestBody String district) throws JSONException {
		JSONArray array = new JSONArray(district);
		String dis =array.getJSONObject(0).getString("tc");
		System.out.println("REQUEST FOR FEEDERS ====="+dis);
		return feederService.findAll(dis);
	}

	@RequestMapping(value = "/getAllModemMasterData", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object getAllModemMasterData() throws JSONException {
		System.out.println("REQUEST FOR getAllModemMasterData =====");
		return modemMasterService.findAll();
	}

	@RequestMapping(value = "/getDistinctDistrict", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object getDistinctDistrict() {
		System.out.println("REQUEST FOR DISTRCITS =====");
		return feederService.findDistricts();
	}

	@RequestMapping(value = "/getDistinctZone", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object getDistinctZone() {
		System.out.println("REQUEST FOR getDistinctZone =====");
		return masterMainService.findDistinctZones();
	}

	@RequestMapping(value = "/getDistinctCircle", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object getDistinctCircle(@RequestBody String data) {
		String zoneString = "";
		try {
			JSONArray array = new JSONArray(data);
			zoneString =array.getJSONObject(0).getString("zone");
			System.out.println("REQUEST FOR getDistinctCircle ====="+zoneString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return masterMainService.findDistinctCircle(zoneString);

	}

	@RequestMapping(value = "/getDistinctDivision", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object getDistinctDivision(@RequestBody String data) {
		String zoneString = "",circleString="";
		try {
			JSONArray array = new JSONArray(data);
			zoneString =array.getJSONObject(0).getString("zone");
			circleString =array.getJSONObject(0).getString("circle");
			System.out.println("REQUEST FOR getDistinctDivision ====="+zoneString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return masterMainService.findDistinctDivision(zoneString, circleString);

	}

	@RequestMapping(value = "/getDistinctSubDivision", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object getDistinctSubDivision(@RequestBody String data) {
		String zoneString = "",circleString="",divisionString="";
		try {
			JSONArray array = new JSONArray(data);
			zoneString =array.getJSONObject(0).getString("zone");
			circleString =array.getJSONObject(0).getString("circle");
			divisionString =array.getJSONObject(0).getString("division");
			System.out.println("REQUEST FOR getDistinctSubDivision ====="+zoneString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return masterMainService.findDistinctSubDivision(zoneString, circleString,divisionString);

	}

	@RequestMapping(value = "/getDistinctSubstation", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object getDistinctSubstation(@RequestBody String data) {
		String zoneString = "",circleString="",divisionString="", subdivisionString = "";
		try {
			JSONArray array = new JSONArray(data);
			zoneString =array.getJSONObject(0).getString("zone");
			circleString =array.getJSONObject(0).getString("circle");
			divisionString =array.getJSONObject(0).getString("division");
			subdivisionString =array.getJSONObject(0).getString("subdivision");

			System.out.println("REQUEST FOR getDistinctSubstation ====="+zoneString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return masterMainService.findDistinctSubstation(zoneString, circleString,divisionString,subdivisionString);

	}

	@RequestMapping(value = "/getAllConsumersforModem", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object getAllConsumersforModem(@RequestBody String data) {
		String zoneString = "",circleString="",divisionString="", subdivisionString = "",substationString="";
		try {
			JSONArray array = new JSONArray(data);
			zoneString =array.getJSONObject(0).getString("zone");
			circleString =array.getJSONObject(0).getString("circle");
			divisionString =array.getJSONObject(0).getString("division");
			subdivisionString =array.getJSONObject(0).getString("subdivision");

			System.out.println("REQUEST FOR getAllFeedersforModem ====="+zoneString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return masterMainService.findAllFeedersforModem(zoneString, circleString,divisionString,subdivisionString,"");

	}


	@RequestMapping(value = "login/validate_for_group/{devciedate}/{devicetime}/{apk}/{imei_no}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody
	Object deviceValidate(@PathVariable("devciedate") String devciedate , @PathVariable("devicetime") String devicetime , @PathVariable("apk") String apk
			, @PathVariable("imei_no") String imei_no  ) throws JSONException {
		BCITSLogger.logger.info("in deviceValidate...........");
		String version = null;
		String resultTime = "" , version_status = "old" , device_status = "notreg" ,version_update_status = "new";
		try {

			///////////////////////DATE AND TIME VALIDATION///////////////////////////////
			String devicedate = devciedate.trim().replace("@", "/") + " "+ devicetime;
			Date date1 = new Date();
			DateFormat serverdate_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String serverdate = serverdate_time.format(date1);
			SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");

			Date d1 = null;
			Date d2 = null;
			try
			{
				d1 = format.parse(devicedate);
				d2 = format.parse(serverdate);
			} catch (ParseException e)
			{
				e.printStackTrace();
			}

			long diff = d2.getTime() - d1.getTime();
			long diffMinutes = diff / (60 * 1000);
			int diff_min = (int) Math.abs(diffMinutes);

			if (diff_min > 10) {resultTime = "invalidtime@" + serverdate_time.format(date1);}
			else {    resultTime = "validtime@" + serverdate_time.format(date1);    }
			///////////////////////END DATE AND TIME VALIDATION///////////////////////////////


			/////////////////////////////// ALLOWEDDEVICEVERSIONS ///////////////////////////////////
			List<AllowedDeviceVersionsEntity> allowedVersionList = allowedDeviceVersionService.findAll();
			for (AllowedDeviceVersionsEntity allowedDeviceVersionsEntity : allowedVersionList) {

				if(allowedDeviceVersionsEntity.getVersion_name().equalsIgnoreCase(apk)){
					version_status = "latest";
					break ;
				}
			}
			/////////////////////////////// ALLOWEDDEVICEVERSIONS END///////////////////////////////////

			/////////////////////////////// DEVICEMASTER ///////////////////////////////////////////////
			String dev =   mrDeviceService.findByDevice(imei_no);
			if (imei_no.equalsIgnoreCase(dev))
				device_status = "reg";
			/////////////////////////////// ONAIRVERSIONUPDATION ///////////////////////////////////////

			/*List<OnAirVersionUpdationEntity> entities = onAirVersionUpdationService    .getlatestVersion();
            version = entities.get(0).getApkVersion();



            if (compareVersions(apk, version) == -1)
            {*/
			version_update_status = "new";
			/* }*/



		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return resultTime + "@" + version_status + "@" + device_status + "@"+ version_update_status;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/updateFeederData", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object updateFeederData(@RequestBody String feeders) 
	{
		System.out.println("UPDATING Feeder DATA ====="+feeders);
		JSONArray response = new JSONArray();
		try {
			JSONArray array = new JSONArray(feeders);

			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				String mrcode=obj.optString("mrcode").trim();
				String sitecode=obj.optString("sitecode").trim();
				String fdrcode=obj.optString("fdrcode").trim();
				String fdrname=obj.optString("fdrname").trim().replace("'", "''");
				String mtrno=obj.optString("mtrno").trim();
				String mtrmake=obj.optString("mtrmake").trim();
				String mtryear=obj.optString("mtryear").trim();
				String dlms=obj.optString("dlms").trim();
				String port=obj.optString("port").trim();
				String substation=obj.optString("substation").trim().replace("'", "''");
				String district=obj.optString("district").trim();
				String panelspace=obj.optString("panelspace").trim();
				String powersupply=obj.optString("powersupply").trim();
				String enclosed=obj.optString("enclosed").trim();
				String cable=obj.optString("cable").trim();
				String network=obj.optString("network").trim();
				String ctratio=obj.optString("ctratio").trim();
				String ptratio=obj.optString("ptratio").trim();
				String mf=obj.optString("mf").trim();
				String timetaken=obj.optString("timetaken").trim();
				String accuracy=obj.optString("accuracy").trim();
				String lat=obj.optString("lat").trim();
				String longi=obj.optString("long").trim();
				String imei=obj.optString("imei").trim();
				String version=obj.optString("version").trim();
				String extra1=obj.optString("extra1").trim();
				String extra2=obj.optString("extra2").trim();
				String extra3=obj.optString("extra3").trim();
				String extra4=obj.optString("extra4").trim();
				String extra5=obj.optString("extra5").trim();
				String extra6=obj.optString("extra6").trim();
				//System.out.println("obj.optString(frontimage)"+obj.optString("frontimage"));

				byte[] frontImage = null,rightImage= null,leftImage=null,ttbImage=null,portImage=null;
				try {

					if (obj.optString("frontimage").isEmpty() || obj.optString("frontimage") == null) {

						frontImage="".getBytes();

					}else{
						frontImage=Base64.decodeBase64(obj.optString("frontimage"));
					}

					leftImage=Base64.decodeBase64(obj.optString("leftimage"));
					rightImage=Base64.decodeBase64(obj.optString("rightimage"));
					ttbImage=Base64.decodeBase64(obj.optString("ttbimage"));
					portImage=Base64.decodeBase64(obj.optString("portimage"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				boolean test;  //hhbm_DownloadService.isConsumerExist(CONSUMER_SC_NO, SITECODE,METERREADER_CODE, BILL_MONTH);
				List<FeederOutputEntity> dataList=null;

				String rawquery = "SELECT * FROM VCLOUDENGINE.FEEDER_OUTPUT WHERE feeder_name='"+fdrname+"' AND substation='"+substation+"'";
				System.out.println(rawquery);
				dataList = postgresMdas.createNativeQuery(rawquery).getResultList();
				BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  feeder_name "+dataList.toString()+"*****"+fdrname + " dataList.isEmpty():"+dataList.isEmpty());
				if(dataList.isEmpty()){
					test = true ;
				}else{
					test = false ;
				}

				if (test) {
					FeederOutputEntity foe = new FeederOutputEntity(sitecode, mrcode, fdrcode, fdrname, mtrno, mtrmake, mtryear, dlms, port, substation, district,
							panelspace, powersupply, enclosed, cable, network, ctratio, ptratio, mf, timetaken, accuracy, lat, longi, frontImage, leftImage, 
							rightImage, imei, version, ttbImage, portImage,extra1,extra2,"","","",extra6,extra3,extra4,extra5);
					try {
						feederOutputService.customsavemdas(foe);
						//String query = "update vcloudengine.master set feedsurvey = '1' where fdrcode = '"+fdrcode+"'";
						String query = "update meter_data.master set feedsurvey = '1' where fdrname = :fdrname";
						int value = postgresMdas.createNativeQuery(query).setParameter("fdrname", fdrname).executeUpdate();
						response.put(new JSONObject().put("fdrname", fdrname.replace("''", "'")));
						//if (value>0) {
						//}
					} catch (Exception e) {
						//response.put(new JSONObject().put("fdrname", fdrname));
						response.put("Not Updated");
						e.printStackTrace();
					}
				} else {
					BCITSLogger.logger.info("feeder_name-"+fdrname + ">>Already Exists in DB");
					response.put(new JSONObject().put("fdrname", fdrname.replace("''", "'")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  response.toString();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/updateSubData", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object updateSubData(@RequestBody String substation) 
	{
		//System.out.println("UPDATING substation DATA ====="+substation);
		JSONArray response = new JSONArray();
		try {
			JSONArray array = new JSONArray(substation);

			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				String mrcode=obj.optString("mrcode").trim();
				String sitecode=obj.optString("sitecode").trim();
				String subname=obj.optString("subname").trim().replace("'", "''");
				String subaddr=obj.optString("subaddr").trim();
				String subdistrict=obj.optString("subdistrict").trim();
				String timetaken=obj.optString("timetaken").trim();
				String accuracy=obj.optString("accuracy").trim();
				String lat=obj.optString("lat").trim();
				String longi=obj.optString("long").trim();
				String imei=obj.optString("imei").trim();
				String version=obj.optString("version").trim();
				String extra1=obj.optString("extra1").trim();
				String extra2=obj.optString("extra2").trim();
				String extra3=obj.optString("extra3").trim();
				String extra4=obj.optString("extra4").trim();
				String extra5=obj.optString("extra5").trim();
				String extra6=obj.optString("extra6").trim();

				byte[] firstImage = null,secondImage= null,thirdImage=null,fourthImage = null,fifthImage=null,
						sixthImage=null,seventhImage=null,EighthImage=null,ninthImage=null,tenthImage= null;
				try {
					firstImage=Base64.decodeBase64(obj.optString("firstImage"));
					secondImage=Base64.decodeBase64(obj.optString("secondImage"));
					thirdImage=Base64.decodeBase64(obj.optString("thirdImage"));
					fourthImage=Base64.decodeBase64(obj.optString("fourthImage"));
					fifthImage=Base64.decodeBase64(obj.optString("fifthImage"));
					sixthImage=Base64.decodeBase64(obj.optString("sixthImage"));
					seventhImage=Base64.decodeBase64(obj.optString("seventhImage"));
					EighthImage=Base64.decodeBase64(obj.optString("EighthImage"));
					ninthImage=Base64.decodeBase64(obj.optString("ninthImage"));
					tenthImage=Base64.decodeBase64(obj.optString("tenthImage"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				List<FeederOutputEntity> dataList=null;

				String rawquery = "SELECT * FROM VCLOUDENGINE.SUBSTATION_OUTPUT WHERE substation_name='"+subname+"'AND substation_district='"+subdistrict+"'";

				dataList = postgresMdas.createNativeQuery(rawquery).getResultList();
				BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  substation "+dataList.toString()+"*****"+subname + " dataList.isEmpty():"+dataList.isEmpty());
				boolean test = false;
				if(dataList.isEmpty()){
					test  = true ;
				}else{
					test = false ;
				}
				if (test) {
					String sql = "insert into vcloudengine.substation_output values('"
							+ sitecode
							+ "','"
							+ mrcode
							+ "','"
							+ subname
							+ "','"
							+ subaddr
							+ "','"
							+ subdistrict
							+ "','"
							+ timetaken + "','"
							+ accuracy + "','"
							+ lat + "','"
							+ longi+ "',:firstImage,:secondImage,:thirdImage,:fourthImage,:fifthImage,:sixthImage,:seventhImage,:EighthImage,:ninthImage,:tenthImage,'"
							+ imei + "','"
							+ version + "','"
							+extra1+"','"
							+extra2+"','"
							+extra3+"','"
							+extra4+"','"
							+extra5+"','"
							+extra6+"')";
					int count=postgresMdas.createNativeQuery(sql)
							.setParameter("firstImage", firstImage)
							.setParameter("secondImage", secondImage)
							.setParameter("thirdImage", thirdImage)
							.setParameter("fourthImage", fourthImage)
							.setParameter("fifthImage", fifthImage)
							.setParameter("sixthImage", sixthImage)
							.setParameter("seventhImage", seventhImage)
							.setParameter("EighthImage", EighthImage)
							.setParameter("ninthImage", ninthImage)
							.setParameter("tenthImage", tenthImage)
							.executeUpdate();
					BCITSLogger.logger.info("===update substation sql==>>"+sql+"==count==>"+count);
					if(count>0)
					{
						response.put(new JSONObject().put("subname", subname.replace("'", "''")));
					}
					else
					{
						response.put("Not Updated");
					}
				} else {
					BCITSLogger.logger.info("substation_name-"+subname + ">>Already Exists in DB");
					response.put(new JSONObject().put("subname", subname.replace("'", "''")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  response.toString();
	}

	@RequestMapping(value="/getFeederData/{mtrNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getFeederData(@PathVariable String mtrNo,HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		System.out.println("inside Feeder Data==>"+mtrNo);
		model.addAttribute("mtrNo",mtrNo);
		List<FeederMasterEntity> feederData=feederService.getFeederData(mtrNo);
		return feederData;
	}	

	@RequestMapping(value="/getEventData/{mtrNo}/{frmDate}/{tDate}/{radioValue}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getEventData(@PathVariable String mtrNo,@PathVariable String frmDate,@PathVariable String tDate,@PathVariable String radioValue, HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<AmrEventsEntity> eventData=amrEventsService.getEventData(mtrNo,frmDate,tDate,radioValue);
		return eventData;
	}
	
	@RequestMapping(value="/getEventDataInfo/{mtrNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getEventDataInfo(@PathVariable String mtrNo, HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<AmrEventsEntity> eventDataInfo=amrEventsService.getEventDataInfo(mtrNo);
		return eventDataInfo;
	}

	@RequestMapping(value="/getInstansData/{mtrNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getInstansData(@PathVariable String mtrNo,HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<AmrInstantaneousEntity> instansData=amrInstantaneousService.getInstansData(mtrNo);
		return instansData;
	}
	
	
	@RequestMapping(value="/getInstansDetails/{mtrNo}/{radioValue}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getInstansDetails(@PathVariable String mtrNo,@PathVariable String radioValue,HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		String frmDate=request.getParameter("frmDate");
		String tDate=request.getParameter("tDate");
		//List<AmrInstantaneousEntity> instansData1=amrInstantaneousService.getCompleteInstansData(mtrNo,radioValue);
		List<AmrInstantaneousEntity> instansData1=amrInstantaneousService.getCompleteInstansDataNew(mtrNo, frmDate, tDate, radioValue);
		return instansData1;
	}
	@RequestMapping(value="/getALLInstansDetails/{mtrNo}/{radioValue}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getALLInstansDetails(@PathVariable String mtrNo,@PathVariable String radioValue, HttpServletRequest request)
	{
		
		String frmDate=request.getParameter("frmDate");
		String tDate=request.getParameter("tDate");
		
		List<?> list=amrInstantaneousService.getAllInstansData(mtrNo, frmDate, tDate, radioValue);
		/*String qry="SELECT * FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"' AND read_time<(SELECT  max(read_time)"
				+ " FROM meter_data.amiinstantaneous WHERE meter_number='"+mtrNo+"') "
				+ "AND date(read_time) BETWEEN '"+frmDate+"' AND '"+tDate+"' ORDER BY read_time DESC;";
		List<?> list = postgresMdas.createNativeQuery(qry).getResultList();
		System.out.println("in inst det"+qry);*/
		return list;
	}
	
	//XmlHelper helper;
	
	
	@RequestMapping(value="/getLoadSurveyDataInfo/{mtrNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getLoadSurveyDataInfo(@PathVariable String mtrNo, HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<AmrLoadEntity> eventData=amrLoadService.getLoadSurveyDataInfo(mtrNo);
		return eventData;
	}
	
	
	
	
	@RequestMapping(value="/getLoadSurveyData/{mtrNo}/{frmDate}/{tDate}/{radioValue}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getLoadSurveyData(@PathVariable String mtrNo,@PathVariable String frmDate,@PathVariable String tDate,@PathVariable String radioValue, HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<AmrLoadEntity> eventData=amrLoadService.getLoadSurveyData(mtrNo,frmDate,tDate,radioValue);
		return eventData;
	}
	
	
	
	@RequestMapping(value="/getNamePlateDetails/{mtrNo}/{radioValue}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getnamePlateDetails(@PathVariable String mtrNo,@PathVariable String radioValue, HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<?> namePlateData=null;
		//String meterNum=request.getParameter("meterNum");

		if("meterno".equals(radioValue)) {
			namePlateData=amrLoadService.getNamePlateDetailsBymeterNo(mtrNo);
		} else {
			namePlateData=amrLoadService.getNamePlateDetailsByKno(mtrNo);
		}
		//List<?> namePlateData=amrLoadService.getNamePlateDetails(mtrNo);
		return namePlateData;
	}
	@RequestMapping(value="/getMinMaxData", method={RequestMethod.POST,RequestMethod.GET})
	public String getDailyMinMaxData(HttpServletRequest request,ModelMap modelMap){
		String mtrno=request.getParameter("mtrno");
		String zone=request.getParameter("zone");
		String circle=request.getParameter("circle");
		String town=request.getParameter("town");
		String from=request.getParameter("from");
		String to=request.getParameter("to");
		
		System.out.println("zone ----"+zone);
		System.out.println("circle ----"+circle);
		System.out.println("town ----"+town);
		System.out.println("mtrno----"+mtrno);

		modelMap.put("zone", zone);
		modelMap.put("circle", circle);
		modelMap.put("town", town);
		modelMap.put("mtrno", mtrno);
		modelMap.put("from", from);
		modelMap.put("to", to);
		//System.out.println(zone+""+circle);
		
		return "dailyMinMax";
	}
	@RequestMapping(value="/getMinMaxAvgDataByMtrNo/{meterNum}/{frmDate}/{tDate}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getDailyMinMaxDatabyMtrNum(@PathVariable String meterNum,@PathVariable String frmDate,@PathVariable String tDate)
	{
		String a="";
		//System.out.println("in controller");
		List<List<?>> dailyParameters=new ArrayList<>();
		
		dailyParameters=amrLoadService.getDailyMinMaxByMtrNo(meterNum,frmDate,tDate);
		
		return dailyParameters;
		
	}
	@RequestMapping(value="/getMinMaxAvgDataByMtrNoandMf/{meterNum}/{frmDate}/{tDate}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getDailyMinMaxDatabyMtrNumMf(@PathVariable String meterNum,@PathVariable String frmDate,@PathVariable String tDate)
	{
		
		List<List<?>> dailyParameters=new ArrayList<>();
		
		dailyParameters=amrLoadService.getDailyMinMaxByMtrNoMf(meterNum,frmDate,tDate);
		
		return dailyParameters;
		
	}
	@RequestMapping(value="/getMinMaxAvgDataByKNo/{meterNum}/{frmDate}/{tDate}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getDailyMinMaxDatabyKNum(@PathVariable String meterNum,@PathVariable String frmDate,@PathVariable String tDate)
	{
		
		List<List<?>> dailyParameters=new ArrayList<>();
		
		dailyParameters=amrLoadService.getdailyParametersByKno(meterNum,frmDate,tDate);
	
		return dailyParameters;
		
	}
	@RequestMapping(value="/getMinMaxAvgDataByKNoandMf/{meterNum}/{frmDate}/{tDate}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getDailyMinMaxDatabyKNumMf(@PathVariable String meterNum,@PathVariable String frmDate,@PathVariable String tDate)
	{
		
		List<List<?>> dailyParameters=new ArrayList<>();
		
		dailyParameters=amrLoadService.getdailyParametersByKnoandMf(meterNum,frmDate,tDate);
	
		return dailyParameters;
		
	}
	@RequestMapping(value="/getConsumerData/{meterNum}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getConsumerDetails(@PathVariable String meterNum)
	{
		List <MasterMainEntity> consumerDetails=new ArrayList<>();
		consumerDetails=masterMainService.getFeederData(meterNum);
		
		return consumerDetails;
	}
	@RequestMapping(value="/getConsumerDatabyKno/{meterNum}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getConsumerDetailsbyKno(@PathVariable String meterNum)
	{
		List <MasterMainEntity> consumerDetails=new ArrayList<>();
		consumerDetails=masterMainService.getMeterDataByKno(meterNum);
		
		return consumerDetails;
	}
	
		@RequestMapping(value="/transactiondata/{mtrNo}/{frmDate}/{tDate}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getTransactionData(@PathVariable String mtrNo,@PathVariable String frmDate,@PathVariable String tDate){
		List<?> transactionData=null;
		transactionData=amrLoadService.getTransactionData(mtrNo, frmDate, tDate);
		return transactionData;
	
	}
	
	@RequestMapping(value="/getDailyLoadSurveyData/{mtrNo}/{frmDate}/{tDate}/{radioValue}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getDailyLoadSurveyData(@PathVariable String mtrNo,@PathVariable String frmDate,@PathVariable String tDate,@PathVariable String radioValue,HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<?> eventData=amrLoadService.getDailyLoadSurveyData(mtrNo,frmDate,tDate,radioValue);
		return eventData;
	}
	
	
	@RequestMapping(value="/getDailyLoadSurveyDataInfo/{mtrNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getDailyLoadSurveyDataInfo(@PathVariable String mtrNo,HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<?> eventData=amrLoadService.getDailyLoadSurveyDataInfo(mtrNo);
		return eventData;
	}
	

	@RequestMapping(value="/getbillHistoryDetails/{mtrNo}/{frmDate}/{tDate}/{radioValue}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getbillHistoryDetails(@PathVariable String mtrNo,@PathVariable String frmDate,@PathVariable String tDate,@PathVariable String radioValue, HttpServletResponse response,HttpServletRequest request,ModelMap model) throws ParseException
	{
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-01 00:00:00");
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MONTH, 0);
			
			Calendar now1 = Calendar.getInstance();
			now1.add(Calendar.MONTH, -1);
			
			Calendar now2 = Calendar.getInstance();
			now2.add(Calendar.MONTH, -2);
			
			Calendar now3 = Calendar.getInstance();
			now3.add(Calendar.MONTH, -3);
			
			Calendar now4 = Calendar.getInstance();
			now4.add(Calendar.MONTH, -4);
			
			Calendar now5 = Calendar.getInstance();
			now5.add(Calendar.MONTH, -5);

			
			//now.add(Calendar.MONTH, -1);
		   // String presentDate = dateFormat.format(Calendar.getInstance().getTime());
		    String yesterDay = dateFormat.format(now.getTime());
		    //System.err.println("the bill months are====>"+dateFormat.format(now.getTime()));
		    ArrayList<String> strlist=new ArrayList<>();
		    strlist.add(dateFormat.format(now.getTime()));
		    strlist.add(dateFormat.format(now1.getTime()));
		    strlist.add(dateFormat.format(now2.getTime()));
		    strlist.add(dateFormat.format(now3.getTime()));
		    strlist.add(dateFormat.format(now4.getTime()));
		    strlist.add(dateFormat.format(now5.getTime()));
		//List<AmrBillsEntity> bills=amrBillsService.getbillHistoryDetails(mtrNo,frmDate,tDate); //OLD LOGIC FOR BILL HISTORY
		List<AmrBillsEntity> months6Bills=amrBillsService.getBillHistory6months(mtrNo,strlist,radioValue);//NEW LOGIC FOR BILL HISTORY
		return months6Bills;
	}
	

	@RequestMapping(value="/getbillHistoryDetailsInfo/{mtrNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getbillHistoryDetailsInfo(@PathVariable String mtrNo, HttpServletResponse response,HttpServletRequest request,ModelMap model) throws ParseException
	{
		
		
		List<AmrBillsEntity> bills=amrBillsService.getbillHistoryDetailsInfo(mtrNo);
		
		return bills;
		
		
	}
	
	
	
	
	
	@Transactional(propagation=Propagation.REQUIRES_NEW,isolation=Isolation.DEFAULT)
	@RequestMapping(value = "/updateFeederModemData", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object updateFeederModemData(@RequestBody String feeders) 
	{
		System.out.println("UPDATING updateFeederModemData DATA ====="+feeders);
		JSONArray response = new JSONArray();
		try {
			JSONArray array = new JSONArray(feeders);

			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				String sitecode=obj.optString("sitecode").trim();
				String mrcode=obj.optString("mrcode").trim();
				String name=obj.optString("name").trim();
				String accno=obj.optString("accno").trim();
				String mtrno=obj.optString("mtrno").trim();
				String mtrmake=obj.optString("mtrmake").trim();
				String mtryr=obj.optString("mtryr").trim();
				String dlms=obj.optString("dlms").trim();
				String port=obj.optString("port").trim();
				String substation=obj.optString("substation").trim();
				String district=obj.optString("district").trim();
				String ctratio=obj.optString("ctratio").trim();
				String ptratio=obj.optString("ptratio").trim();
				String modemslno=obj.optString("modemslno").trim();
				String imeino=obj.optString("imeino").trim();
				String simimsi=obj.optString("simimsi").trim();
				String simccid=obj.optString("simccid").trim();
				String newmtrno=obj.optString("newmtrno").trim();
				String newmtrmake=obj.optString("newmtrmake").trim();
				String newmtryr=obj.optString("newmtryr").trim();
				String newdlms=obj.optString("newdlms").trim();
				String newctratio=obj.optString("newctratio").trim();
				String newptratio=obj.optString("newptratio").trim();
				String servertosbmdate=obj.optString("time").trim();
				String division=obj.optString("division").trim();
				String subdivision=obj.optString("subdivision").trim();
				String state=obj.optString("state").trim();
				String zone=obj.optString("zone").trim();
				String circle=obj.optString("circle").trim();
				String deviceid=obj.optString("deviceid").trim();
				String appversion=obj.optString("appversion").trim();
				String timestaken=obj.optString("extra1").trim();
				String oldsealone=obj.optString("extra2").trim();
				String oldsealtwo=obj.optString("extra3").trim();
				String oldsealthree=obj.optString("extra4").trim();
				String newsealone=obj.optString("extra5").trim();
				String newsealtwo=obj.optString("extra6").trim();
				String newsealthree=obj.optString("extra7").trim();
				String REMARKS=obj.optString("REMARKS").trim();
				String oldsealfour=obj.optString("oldsealfour").trim();
				String oldsealfive=obj.optString("oldsealfive").trim();
				String newsealfour=obj.optString("newsealfour").trim();
				String newsealfive=obj.optString("newsealfive").trim();
				String extra11=obj.optString("extra11").trim();
				String extra22=obj.optString("extra22").trim();

				byte[] Image = null;
				try {

					if (obj.optString("image").isEmpty() || obj.optString("image") == null) {
						Image="".getBytes();
					}else{
						Image=Base64.decodeBase64(obj.optString("image"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				boolean test;  //hhbm_DownloadService.isConsumerExist(CONSUMER_SC_NO, SITECODE,METERREADER_CODE, BILL_MONTH);
				List<ModemInstallationEntity> dataList=null;

				String rawquery = "SELECT * FROM VCLOUDENGINE.MODEM_INSTALLATION WHERE accno='"+accno+"'AND substation='"+substation+"'";
				dataList = postgresMdas.createNativeQuery(rawquery).getResultList();
				BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  accno "+dataList.toString()+"*****"+accno + " dataList.isEmpty():"+dataList.isEmpty());
				if(dataList.isEmpty()){
					test = true ;
				}else{
					test = false ;
				}
				servertosbmdate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
				if (test) {
					ModemInstallationEntity foe = new ModemInstallationEntity(state,
							zone, 
							circle, 
							district, 
							division,
							subdivision, 
							substation, 
							accno, 
							name, 
							mtrno, 
							mtrmake, 
							mtryr, 
							dlms,  
							ctratio,  
							ptratio, 
							modemslno, 
							imeino, 
							simimsi, 
							simccid, 
							newmtrno, 
							newmtrmake, 
							newmtryr, 
							newdlms, 
							newctratio, 
							newptratio,
							servertosbmdate,
							deviceid, 
							appversion, 
							Image, 
							timestaken, 
							oldsealone, 
							oldsealtwo, 
							oldsealthree, 
							newsealone, 
							newsealtwo,
							newsealthree,
							REMARKS,
							oldsealfour,
							oldsealfive,
							newsealfour,
							newsealfive,
							extra11,
							extra22);
					try {
						modemInstallationService.customsavemdas(foe);
						//String query = "update vcloudengine.master set feedsurvey = '1' where fdrcode = '"+fdrcode+"'";
						ModemMasterEntity modemMaster=modemMasterService.getEntityByImei(imeino);
						if(modemMaster!=null) {
							modemMaster.setInstalled("1");
							modemMasterService.customupdatemdas(modemMaster);
						}
						MasterMainEntity masterMain=masterMainService.getEntityByAccnoSubstation(accno, subdivision);
						if(masterMain!=null) {
							masterMain.setInstallation("1");
							masterMainService.customupdatemdas(masterMain);
						}
						/*String query = "update meter_data.modem_master set installed = '1' where modem_sl_no = :modem_sl_no";
						int value = postgresMdas.createNativeQuery(query).setParameter("modem_sl_no", modemslno).executeUpdate();
						String query2 = "update meter_data.master_main set installation = '1' WHERE accno='"+accno+"'AND substation='"+substation+"'";
						int value2 = postgresMdas.createNativeQuery(query2).executeUpdate();*/
						/*System.out.println("modem_master flag update ---"+value);
						System.out.println("feeder_output flag update ---"+value2);*/
						response.put(new JSONObject().put("accno", accno));
					} catch (Exception e) {
						response.put("Not Updated");
						e.printStackTrace();
					}
				} else {
					//BCITSLogger.logger.info("feeder_name-"+fdrname + ">>Already Exists in DB");
					/*String query = "update meter_data.modem_master set installed = '1' where modem_sl_no = :modem_sl_no";
					int value = postgresMdas.createNativeQuery(query).setParameter("modem_sl_no", modemslno).executeUpdate();
					String query2 = "update meter_data.master_main set installation = '1' WHERE accno='"+accno+"'AND substation='"+substation+"'";
					int value2 = postgresMdas.createNativeQuery(query2).executeUpdate();*/
					
					//String query = "update vcloudengine.master set feedsurvey = '1' where fdrcode = '"+fdrcode+"'";
					ModemMasterEntity modemMaster=modemMasterService.getEntityByImei(imeino);
					if(modemMaster!=null) {
						modemMaster.setInstalled("1");
						modemMasterService.customupdatemdas(modemMaster);
					}
					MasterMainEntity masterMain=masterMainService.getEntityByAccnoSubstation(accno, subdivision);
					if(masterMain!=null) {
						masterMain.setInstallation("1");
						masterMainService.customupdatemdas(masterMain);
					}
					response.put(new JSONObject().put("accno", accno));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			updateMasterMain();
		} catch (Exception e) {
			e.printStackTrace();		
		}*/
		return  response.toString();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value="/updateMastermain",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String updateMastermain(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{ 
		return updateMasterMain();
	}	


	public String updateMasterMain(){ 
		String rawquery = "INSERT INTO meter_data.master_main (zone	,circle	,division	,district	,subdivision	,substation	,fdrname	,fdrcode		,mtrno	, mtrmake	,"
				+" dlms	,year_of_man	,ct_ratio	,pt_ratio	,mf	,comm	,longitude	,latitude, modem_sl_no	)  "
				+" ( SELECT * from ( select fo.zone	,fo.circle	,fo.division	,fo.district	,fo.subdivision	,fo.substation	,fo.feeder_name	,fo.feeder_code	, "
				+" ( case when mi.new_meter_no='' then mi.meter_no else mi.new_meter_no end ) as meterNumber	,"
				+" ( case when mi.new_meter_make='' then mi.meter_make else mi.new_meter_make end ) as metrMake, "
				+" ( case when mi.new_dlms='' then mi.dlms else mi.new_dlms end ) as dlmss	, "
				+" ( case when mi.new_meter_year='' then mi.meter_year else mi.new_meter_year end ) as metryear	, "
				+" ( case when mi.new_ct_ratio='' then mi.ct_ratio else mi.new_ct_ratio end ) as ctratios	, "
				+" ( case when mi.new_pt_ratio='' then mi.pt_ratio else mi.new_pt_ratio end ) as ptratios	,"
				+" fo.mf	,fo.port_configuration	,fo.longitude	,fo.latitude, mi.imei_no	 "
				+" from  vcloudengine.feeder_output fo , vcloudengine.modem_installation mi where fo.feeder_name = mi.feeder_name and fo.substation=mi.substation  and fo.district=mi.district) "
				+" aa where aa.meterNumber not in (select mtrno from meter_data.master_main))";

		System.out.println("SYNC MASTER MAIN  "+rawquery);

		int dataList = postgresMdas.createNativeQuery(rawquery).executeUpdate();
		System.out.println(dataList);
		System.out.println("DATA COUNT "+dataList);

		return dataList +" Data Synced";

	}

	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value = "/updateConsumerData", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
	public @ResponseBody Object updateConsumerData(@RequestBody String feeders) 
	{
		System.out.println("UPDATING Consumers DATA ====="+feeders);
		JSONArray response = new JSONArray();
		try {
			JSONArray array = new JSONArray(feeders);

			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				String sitecode =obj.optString("sitecode");
				String mrcode =obj.optString("mrcode");
				String zone =obj.optString("zone");
				String circle =obj.optString("circle");
				String division =obj.optString("division");
				String district =obj.optString("district");
				String subdivision =obj.optString("subdivision");
				String substation =obj.optString("substation");
				String addrsub =obj.optString("addrsub");
				String village =obj.optString("village");
				String fdrname =obj.optString("fdrname");
				String fdrcode =obj.optString("fdrcode");
				String mtrno =obj.optString("mtrno");
				String mtrmake =obj.optString("mtrmake");
				String dlms =obj.optString("dlms");
				String mtrfirmare =obj.optString("mtrfirmare");
				String yearofman =obj.optString("yearofman");
				String ct =obj.optString("ct");
				String pt =obj.optString("pt");
				String mf =obj.optString("mf");
				String comm =obj.optString("comm");
				String longi =obj.optString("longi");
				String lati =obj.optString("lati");
				String modelno =obj.optString("modelno");
				String voltage =obj.optString("voltage");
				String name =obj.optString("name");
				String mobile =obj.optString("mobile");
				String add =obj.optString("add");
				String constatus =obj.optString("constatus");
				String tariff =obj.optString("tariff");
				String kwhp =obj.optString("kwhp");
				String sanload =obj.optString("sanload");
				String contract =obj.optString("contract");
				String mrname =obj.optString("mrname");
				String kno =obj.optString("kno");
				String discom =obj.optString("discom");
				String accno =obj.optString("accno");
				String modemslno =obj.optString("modemslno");
				String imeino =obj.optString("imeino");
				String simimsi =obj.optString("simimsi");
				String simccid =obj.optString("simccid");
				String newmtrno =obj.optString("newmtrno");
				String newmtrmake =obj.optString("newmtrmake");
				String newmtryr =obj.optString("newmtryr");
				String newdlms =obj.optString("newdlms");
				String newctratio =obj.optString("newctratio");
				String newptratio =obj.optString("newptratio");
				String time =obj.optString("time");
				String deviceid =obj.optString("deviceid");
				String appversion =obj.optString("appversion");
				String extra1 =obj.optString("extra1");
				String extra2 =obj.optString("extra2");
				String extra3 =obj.optString("extra3");
				String extra4 =obj.optString("extra4");
				String extra5 =obj.optString("extra5");
				String extra6 =obj.optString("extra6");
				String extra7 =obj.optString("extra7");
				String panelSpace =obj.optString("panelSpace");
				String powerSupply =obj.optString("powerSupply");
				String enclosed=obj.optString("enclosed");
				String cablelength=obj.optString("cablelength");
				String simOneNetwork =obj.optString("simOneNetwork");
				String simTwoNetwork =obj.optString("simTwoNetwork");
				String simOneSignal=obj.optString("simOneSignal");
				String simTwoSignal=obj.optString("simTwoSignal");
				byte[] Image= null,frontImage = null,rightImage= null,leftImage=null,ttbImage=null,portImage=null;
				try {
					Image=Base64.decodeBase64(obj.optString("image"));
					frontImage=Base64.decodeBase64(obj.optString("imageOne"));
					leftImage=Base64.decodeBase64(obj.optString("imageTwo"));
					rightImage=Base64.decodeBase64(obj.optString("imageThree"));
					ttbImage=Base64.decodeBase64(obj.optString("imageFour"));
					portImage=Base64.decodeBase64(obj.optString("imageFive"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				boolean test;  //hhbm_DownloadService.isConsumerExist(CONSUMER_SC_NO, SITECODE,METERREADER_CODE, BILL_MONTH);
				List<FeederOutputEntity> dataList=null;

				String rawquery = "SELECT * FROM VCLOUDENGINE.NEW_CONSUMERS WHERE accno='"+accno+"' AND subdivision='"+subdivision+"'";
				System.out.println(rawquery);
				dataList = postgresMdas.createNativeQuery(rawquery).getResultList();
				BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  feeder_name "+dataList.toString()+"*****"+fdrname + " dataList.isEmpty():"+dataList.isEmpty());
				if(dataList.isEmpty()){
					test = true ;
				}else{
					test = false ;
				}

				if (test) {
					String servertosbmdate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

					NewConsumersEntity foe = new NewConsumersEntity( sitecode,  mrcode,  zone,
							circle,  division,  district,
							subdivision,  substation,  addrsub,
							village,  fdrname,  fdrcode,  mtrno,
							mtrmake,  dlms,  mtrfirmare,
							yearofman,  ct,  pt,  mf,
							comm,  longi,  lati,  modelno,
							voltage,  name,  mobile,
							add,  accno,  constatus,
							tariff,  kwhp,  sanload,
							contract,  mrname,  kno,  discom,
							Image,  modemslno,  imeino,  simimsi,
							simccid,  newmtrno,  newmtrmake,
							newmtryr,  newdlms,  newctratio,
							newptratio,  servertosbmdate,  "1",
							"1",  deviceid,  appversion,
							extra1,  extra2,  extra3,  extra4,
							extra5,  extra6,  extra7,  frontImage ,
							rightImage,leftImage,ttbImage,portImage,
							panelSpace,  powerSupply,
							enclosed,  cablelength,  simOneNetwork,
							simTwoNetwork,  simOneSignal,  simTwoSignal,
							servertosbmdate);
					try {
						newConsumerService.customsavemdas(foe);
						response.put(new JSONObject().put("fdrname", accno.replace("''", "'")));
					} catch (Exception e) {
						response.put("Not Updated");
						e.printStackTrace();
					}
				} else {
					BCITSLogger.logger.info("feeder_name-"+fdrname + ">>Already Exists in DB");
					response.put(new JSONObject().put("fdrname", accno.replace("''", "'")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  response.toString();
	}
	@RequestMapping(value="/getConsumerLocationData",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getConsumerLocationDetails(HttpServletRequest request)
	{
		String mtrNum=request.getParameter("meterNum").trim();
		String accNum=request.getParameter("accountNumber").trim();
		//System.out.println(accNum);
		return masterMainService.getConsumerLocData(mtrNum,accNum);
		
	}
	
	@RequestMapping(value="/getFeederLocationData",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getFeederConsumerLocationDetails(HttpServletRequest request)
	{
		List<Object[]> feederLocDetails=new ArrayList<>();
		String mtrNum=request.getParameter("meterNum").trim();
		//String fdrId=request.getParameter("assetId").trim();
		//System.out.println(fdrId);

		feederLocDetails=masterMainService.getFeederLocData(mtrNum);
		
		return feederLocDetails;
	}
	@RequestMapping(value="/getDtLocationData",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object gettDLocationDetails(HttpServletRequest request)
	{
		List <Object[]> dtLocDetails=new ArrayList<>();
		String mtrNum=request.getParameter("meterNum").trim();
		String dtId="";
				//request.getParameter("assetId").trim();
		dtLocDetails=masterMainService.getDtLocData(mtrNum,dtId);
		//System.out.println(dtId);
		return dtLocDetails;
	}
	
	@RequestMapping(value="/getMtrnobyTowncode",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getMtrnobyTowncode(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<?> mtrDetails=new ArrayList<>();
		String zne=request.getParameter("zone");
		String cir=request.getParameter("circle");
		String tname=request.getParameter("town");
	
		//System.out.println(tname);
		/*String twnname="";
		 * if(tname.equalsIgnoreCase("ALL")) { twnname="%"; }else { twnname=tname; }
		 */
		//System.out.println("tname--"+twnname);
	
		mtrDetails=amrLoadService.getmMtrnoByTcode(zne,cir,tname);
		
		return mtrDetails;
	}
	@RequestMapping(value="/getviewInfo",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getviewInfo(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<?> mtrDetails=new ArrayList<>();
		
		String zne=request.getParameter("zone");
		
		mtrDetails=amrLoadService.getmMtrnoByInfo(zne);
		
		return mtrDetails;
	}
	@RequestMapping(value="/getmMtrnoByZonCirTwn",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getmMtrnoByZonCirTwn(HttpServletResponse response,HttpServletRequest request,ModelMap model)
	{
		List<?> mtrDetails=new ArrayList<>();
		String zne=request.getParameter("zone");
		String cir=request.getParameter("circle");
		String tname=request.getParameter("twncode");
		/*String twnname="";
		 * if(tname.equalsIgnoreCase("ALL")) { twnname="%"; }else { twnname=tname; }
		 */
		//System.out.println("tname--"+twnname);
		mtrDetails=amrLoadService.getmMtrnoByZonCirTwn(zne,cir,tname);
		return mtrDetails;
	}
	
	@RequestMapping(value="/ViewEnergyHistoryPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void ViewEnergyHistoryPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("twn");
		String mtrno=request.getParameter("mtrno");
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 0);
		
		Calendar now1 = Calendar.getInstance();
		now1.add(Calendar.MONTH, -1);
		
		Calendar now2 = Calendar.getInstance();
		now2.add(Calendar.MONTH, -2);
		
		Calendar now3 = Calendar.getInstance();
		now3.add(Calendar.MONTH, -3);
		
		Calendar now4 = Calendar.getInstance();
		now4.add(Calendar.MONTH, -4);
		
		Calendar now5 = Calendar.getInstance();
		now5.add(Calendar.MONTH, -5);

		
	    String yesterDay = dateFormat.format(now.getTime());
	    System.err.println("the bill months are====>"+dateFormat.format(now.getTime()));
	    ArrayList<String> strlist=new ArrayList<>();
	    strlist.add(dateFormat.format(now.getTime()));
	    strlist.add(dateFormat.format(now1.getTime()));
	    strlist.add(dateFormat.format(now2.getTime()));
	    strlist.add(dateFormat.format(now3.getTime()));
	    strlist.add(dateFormat.format(now4.getTime()));
	    strlist.add(dateFormat.format(now5.getTime()));
		
		String zne="",cir="",twn="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="%";
		}else {
			cir=circle;
		}
		if(town.equalsIgnoreCase("ALL"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
		amrLoadService.getEnergyHistoryPDF(response, zne, cir, twn, mtrno, fdate, tdate, strlist);
		
	}

	@RequestMapping(value="/ViewLoadSurveyPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void ViewLoadSurveyPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("twn");
		String mtrno=request.getParameter("mtrno");
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		
		String zne="",cir="",twn="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="%";
		}else {
			cir=circle;
		}
		if(town.equalsIgnoreCase("ALL"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
		amrLoadService.getLoadSurveyPDF(response, zne, cir, twn, mtrno, fdate, tdate);
		
	}

	@RequestMapping(value="/ViewEventDetailsPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void ViewEventDetailsPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("twn");
		String mtrno=request.getParameter("mtrno");
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		
		String zne="",cir="",twn="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="%";
		}else {
			cir=circle;
		}
		if(town.equalsIgnoreCase("ALL"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
		amrLoadService.getEventDetailsPDF(response, zne, cir, twn, mtrno, fdate, tdate);
		
	}
		
	
	@RequestMapping(value="/ViewDailyParamPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void ViewDailyParamPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("twn");
		String mtrno=request.getParameter("mtrno");
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		
		String zne="",cir="",twn="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="%";
		}else {
			cir=circle;
		}
		if(town.equalsIgnoreCase("ALL"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
		amrLoadService.getDailyParamPDF(response, zne, cir, twn, mtrno, fdate, tdate);
		
	}
	
	@RequestMapping(value="/ViewInstantaneous2PDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void ViewInstantaneous2PDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("twn");
		String mtrno=request.getParameter("mtrno");
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		
		String zne="",cir="",twn="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="%";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="%";
		}else {
			cir=circle;
		}
		if(town.equalsIgnoreCase("ALL"))
		{
			twn="%";
		}else {
			twn=town;
		}
		
		amrLoadService.getInstantaneous2PDF(response, zne, cir, twn, mtrno, fdate, tdate);
		
	}

	@RequestMapping(value="/ViewLoadSurveyExcel",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object ViewLoadSurveyExcel(HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		
		try {
			String zone=request.getParameter("zne");
			String circle=request.getParameter("cir");
			String town=request.getParameter("twn");
			String mtrno=request.getParameter("mtrno");
			String fdate=request.getParameter("fdate");
			String tdate=request.getParameter("tdate");
			
			String zne="",cir="",twn="";
			if(zone.equalsIgnoreCase("ALL"))
			{
				zne="%";
			}else {
				zne=zone;
			}
			if(circle.equalsIgnoreCase("ALL"))
			{
				cir="%";
			}else {
				cir=circle;
			}
			if(town.equalsIgnoreCase("ALL"))
			{
				twn="%";
			}else {
				twn=town;
			}
			
			  String fileName = "LoadSurvey";
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
	 
					header.createCell(0).setCellValue("ReadTime");
					header.createCell(1).setCellValue("Vr");
					header.createCell(2).setCellValue("Vy");
					header.createCell(3).setCellValue("Vb");
					header.createCell(4).setCellValue("Ir");
					header.createCell(5).setCellValue("Iy");
					header.createCell(6).setCellValue("Ib");
					header.createCell(7).setCellValue("BlockKwh");
					header.createCell(8).setCellValue("KvarhLag");
					header.createCell(9).setCellValue("KvarhLead");
					header.createCell(10).setCellValue("Kvah");
					header.createCell(11).setCellValue("PowerFactor");

					
					List<AmrLoadEntity> loaddata=null;
					 loaddata=amrLoadService.getLoadSurveyDataExcel(mtrno,fdate,tdate);

					 int count =1;
						//int cellNO=0;
			            for(Iterator<?> iterator=loaddata.iterator();iterator.hasNext();){
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
			      		if(values[7]==null)
			      		{
			      			row.createCell(7).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      			row.createCell(7).setCellValue(String.valueOf(values[7]));
			      		}
			      		if(values[8]==null)
			      		{
			      			row.createCell(8).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      			row.createCell(8).setCellValue(String.valueOf(values[8]));
			      		}
			      		if(values[9]==null)
			      		{
			      			row.createCell(9).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      			row.createCell(9).setCellValue(String.valueOf(values[9]));
			      		}
			      		if(values[10]==null)
			      		{
			      			row.createCell(10).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      			row.createCell(10).setCellValue(String.valueOf(values[10]));
			      		}
			      		if(values[11]==null)
			      		{
			      			row.createCell(11).setCellValue(String.valueOf(""));
			      		}else
			      		{
			      			row.createCell(11).setCellValue(String.valueOf(values[11]));
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
						response.setHeader("Content-Disposition", "inline;filename=\"LoadSurvey.xlsx"+"\"");
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

	@RequestMapping(value="/DailyMinMaxPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void getDailyMinMaxPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("twn");
		String mtrno=request.getParameter("mtrno");
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		String minmf=request.getParameter("minmf");
		String zne="",cir="",twn="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="ALL";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="ALL";
		}else {
			cir=circle;
		}
		if(town.equalsIgnoreCase("ALL"))
		{
			twn="ALL";
		}else {
			twn=town;
		}
		
		amrLoadService.getDailyMinPDF(response, zne, cir, twn, mtrno, fdate, tdate,minmf);
		
		
	}
	
	@RequestMapping(value="/DailyMaxPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void getDailyMaxPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("twn");
		String mtrno=request.getParameter("mtrno");
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		String maxmf=request.getParameter("maxmf");
		String zne="",cir="",twn="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="ALL";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="ALL";
		}else {
			cir=circle;
		}
		if(town.equalsIgnoreCase("ALL"))
		{
			twn="ALL";
		}else {
			twn=town;
		}
		
	
		amrLoadService.getDailyMaxPDF(response, zne, cir, twn, mtrno, fdate, tdate,maxmf);
		
	}
	
	@RequestMapping(value="/DailyAvgPDF",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void getDailyAvgPDF(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
	{
		String zone=request.getParameter("zne");
		String circle=request.getParameter("cir");
		String town=request.getParameter("twn");
		String mtrno=request.getParameter("mtrno");
		String fdate=request.getParameter("fdate");
		String tdate=request.getParameter("tdate");
		String avgmf=request.getParameter("avgmf");
		
		String zne="",cir="",twn="";
		if(zone.equalsIgnoreCase("ALL"))
		{
			zne="ALL";
		}else {
			zne=zone;
		}
		if(circle.equalsIgnoreCase("ALL"))
		{
			cir="ALL";
		}else {
			cir=circle;
		}
		if(town.equalsIgnoreCase("ALL"))
		{
			twn="ALL";
		}else {
			twn=town;
		}
		
		
		amrLoadService.getDailyAvgPDF(response, zne, cir, twn, mtrno, fdate, tdate,avgmf);
		
	}	
	
		
}
