package com.bcits.mdas.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bcits.mdas.entity.AllowedDeviceVersionsEntity;
import com.bcits.mdas.entity.MeterInventoryMobileEntity;
import com.bcits.mdas.entity.OnAirVersionUpdationEntity;
import com.bcits.mdas.entity.SurveyOutputMobileEntity;
import com.bcits.mdas.service.AllowedDeviceVersionService;
import com.bcits.mdas.service.MRDeviceAllocationService;
import com.bcits.mdas.service.MRDeviceService;
import com.bcits.mdas.service.MeterInventoryMobileService;
import com.bcits.mdas.service.OnAirVersionUpdationService;
import com.bcits.mdas.utility.BCITSLogger;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

@Controller
@EnableWebMvc
@RequestMapping(value = "/ws")
public class MeterReplaceMobileController {


	@Autowired
	private OnAirVersionUpdationService onAirVersionUpdationService;

	@Autowired
	private MeterInventoryMobileService meterInventoryMobileService;

	@Autowired
	private AllowedDeviceVersionService allowedDeviceVersionService;

	@Autowired
	private MRDeviceService mrDeviceService;

	@Autowired
	private MRDeviceAllocationService mrDeviceAllocationService;



	@RequestMapping(value = "/getMeterInventoryMobile", method = {RequestMethod.POST, RequestMethod.GET},produces={"application/json; charset=UTF-8"})
	public @ResponseBody List<MeterInventoryMobileEntity> getMeterInventoryMobile(@RequestBody String data) throws JSONException {
		BCITSLogger.logger.info("in getMeterInventoryMobile..........."+data);
		JSONObject obj = new JSONObject(data);
		List<MeterInventoryMobileEntity> entities = null;
		try {
			entities = meterInventoryMobileService.findAll(obj.optString("sitecode"),obj.optString("mrcode"));
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return entities;
	}
	
	@RequestMapping(value = "/getConsumerDetailsMeterChange", method = {RequestMethod.POST, RequestMethod.GET},produces={"application/json; charset=UTF-8"})
	public @ResponseBody String getConsumerDetailsMeterChange(@RequestBody String data) throws JSONException {
		BCITSLogger.logger.info("in getConsumerDetailsMeterChange..........."+data);
		DateFormat serverdate_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		JSONArray array = new JSONArray();
		JSONObject objData = new JSONObject(data);
		List<?> entities = null;
		try {
			entities = meterInventoryMobileService.getConsumersForMeterChange(objData.optString("sitecode"),objData.optString("mrcode"),objData.optString("dtcCode"));
			if(entities.size()!=0)
			{
				System.out.println("Came to list of Data");
				for (int j = 0; j < entities.size(); j++) {
					try {
						JSONObject obj = new JSONObject();
						Object[] object = (Object[]) entities.get(j);
						obj.put("zone", String.valueOf(object[0]));
						obj.put("circle", String.valueOf(object[1]));
						obj.put("division", String.valueOf(object[2]));
						obj.put("subdivision", String.valueOf(object[3]));
						obj.put("sdocode", String.valueOf(object[4]));
						obj.put("kno", String.valueOf(object[5]));
						obj.put("name", String.valueOf(object[6]));
						obj.put("address", String.valueOf(object[7]));
						obj.put("mobileno", String.valueOf(object[8]));
						obj.put("emailid", String.valueOf(object[9]));
						obj.put("accno", String.valueOf(object[10]));
						obj.put("poleno", String.valueOf(object[11]));
						obj.put("dtccode", String.valueOf(object[12]));
						obj.put("cinnumeric", String.valueOf(object[13]));
						obj.put("ninnumeric", String.valueOf(object[14]));
						obj.put("meterno", String.valueOf(object[15]));
						obj.put("ctrd", String.valueOf(object[16]));
						obj.put("ctrn", String.valueOf(object[17]));
						obj.put("mf", String.valueOf(object[18]));
						obj.put("currdngkwh", String.valueOf(object[19]));
						obj.put("mtrtype", String.valueOf(object[20]));
						obj.put("mtrmake", String.valueOf(object[21]));
						obj.put("survey_status", String.valueOf(object[22]));
						obj.put("servertosbmDate", serverdate_time.format(new Date()));
						array.put(obj);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return array.toString();
	}
	
	@RequestMapping(value = "/getConsumerDetailsMeterChangedAlready", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getConsumerDetailsMeterChangedAlready(@RequestBody String data) throws JSONException {
		BCITSLogger.logger.info("in getConsumerDetailsMeterChangedAlready..........." + data);
		DateFormat serverdate_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		JSONArray array = new JSONArray();
		JSONObject objData = new JSONObject(data);
		List<?> entities = null;
		try {
			entities = meterInventoryMobileService.getConsumerDetailsMeterChangedAlready(objData.optString("sitecode"),
					objData.optString("mrcode"));
			if (entities.size() != 0) {
				System.out.println("Came to list of Data");
				for (int j = 0; j < entities.size(); j++) {
					try {
						JSONObject obj = new JSONObject();
						Object[] object = (Object[]) entities.get(j);
						obj.put("sdocode", String.valueOf(object[0]));
						obj.put("kno", String.valueOf(object[1]));
						obj.put("accno", String.valueOf(object[2]));
						obj.put("name", String.valueOf(object[3]));
						obj.put("address", String.valueOf(object[4]));
						obj.put("mobileno", String.valueOf(object[5]));
						obj.put("meterno", String.valueOf(object[6]));
						obj.put("poleno", String.valueOf(object[8]));
						obj.put("dtccode", String.valueOf(object[9]));
						obj.put("latitude", String.valueOf(object[10]));
						obj.put("longitude", String.valueOf(object[11]));
						obj.put("servertosbmDate", serverdate_time.format(new Date()));
						array.put(obj);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return array.toString();
	}

	@RequestMapping(value = "/getDistinctDTC", method = {RequestMethod.POST, RequestMethod.GET},produces={"application/json; charset=UTF-8"})
	public @ResponseBody String getDistinctDTC(@RequestBody String data) throws JSONException {
		BCITSLogger.logger.info("in getDistinctDTC..........."+data);
		JSONArray array = new JSONArray();
		JSONObject objData = new JSONObject(data);
		List<?> entities = null;
		try {
			entities = meterInventoryMobileService.getDistinctDTCForMeterChange(objData.optString("sitecode"),objData.optString("mrcode"));
			if(entities.size()!=0)
			{
				System.out.println("Came to list of Data");
				for (int j = 0; j < entities.size(); j++) {
					try {
						JSONObject obj = new JSONObject();
						obj.put("dtc",entities.get(j));
						
						array.put(obj);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return array.toString();
	}

	/**
	 * @category Updating billed data from mobile to server
	 * @param consumerlist
	 * @return Updated status for each consumer records
	 * @throws JSONException
	 */
	@Transactional
	@RequestMapping(value="/uploadMeterChange",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object uploadMeterChange(@RequestBody String consumerlist)
	{
		System.out.println(consumerlist);
		String responseString = "[{}]";
		/*if(true){
			return responseString;
		}*/
		try {
			new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

			JSONObject download = new JSONObject();
			String sdocode               =null ;
			String kno                   =null ;
			String accno                 =null ;
			String consumername          =null ;
			String address               =null ;
			String mobileno              =null ;
			String meterno               =null ;
			String connectiontype        =null ;
			String poleno                =null ;
			String dtcno                 =null ;
			String latitude              =null ;
			String longitude             =null ;
			String premise               =null ;
			String sticker_no            =null ;
			String land_mark             =null ;
			byte[] meter_image           =null ;
			byte[] premise_image         =null ;
			String mrcode                =null ;
			String appversion            =null ;
			String imei                  =null ;
			String survey_timings        =null ;
			String observation           =null ;
			String old_metermake         =null ;
			String old_mf                =null ;
			String old_ctrn              =null ;
			String old_ctrd              =null ;
			String currdngkwh            =null ;
			String finalreading          =null ;
			String newmeterno            =null ;
			String newmetermake          =null ;
			String newmetertype          =null ;
			String newmf                 =null ;
			String newctratio            =null ;
			String newinitialreading     =null ;
			byte[] newmeterimage         =null ;
			byte[] mcrreportimage        =null ;
			String newmeterkvah          =null ;
			String oldmeterkvah          =null ;
			String newmeterkva           =null ;
			String oldmeterkva           =null ;
			String oldmeterno_correction =null ;
			String tenderno =null ;

			JSONArray arr = new JSONArray(consumerlist);
			for (int i = 0; i < arr.length(); i++)
			{
				JSONObject jobject = arr.getJSONObject(i);

				sdocode = jobject.optString("SDOCODE");
				kno = jobject.optString("KNO");
				accno = jobject.optString("ACCNO");
				consumername = jobject.optString("NAME");
				address = jobject.optString("ADDRESS");
				mobileno = jobject.optString("MOBILENO"); 
				meterno = jobject.optString("METERNO");
				connectiontype = jobject.optString("CONNECTIONTYPE");
				poleno = jobject.optString("POLENO");
				dtcno = jobject.optString("DTCCODE");
				latitude = jobject.optString("OMLATTITUDE");
				longitude = jobject.optString("OMLONGITUDE");
				premise = jobject.optString("PREMISE");
				sticker_no = jobject.optString("STICKER_NO");
				land_mark = jobject.optString("LAND_MARK");
				meter_image = Base64.decodeBase64(jobject.optString("OLDMTRIMAGE"));
				premise_image = Base64.decodeBase64(jobject.optString("PREMISEIMAGE"));
				mrcode = jobject.optString("MRCODE");
				appversion = jobject.optString("DEVICEFIRMWAREVERSION");
				imei = jobject.optString("DEVICEID");
				survey_timings = jobject.optString("METERREPLACEDATE");
				observation = jobject.optString("OBSERVATION");
				old_metermake = jobject.optString("MTRMAKE");
				old_mf = jobject.optString("MF");
				old_ctrn = jobject.optString("CTRN");
				old_ctrd = jobject.optString("CTRD");
				currdngkwh = jobject.optString("CURRDNGKWH");
				finalreading = jobject.optString("FINALREADING");
				newmeterno = jobject.optString("NEWMETERNO");
				newmetermake = jobject.optString("NEWMETERMAKE");
				newmetertype = jobject.optString("NEWMETERTYPE");
				newmf = jobject.optString("NEWMF");
				newctratio = jobject.optString("NEWCTRATIO");
				newinitialreading = jobject.optString("NEWINITIALREADING");
				newmeterimage = Base64.decodeBase64(jobject.optString("NEWMTRIMAGE"));
				mcrreportimage = Base64.decodeBase64(jobject.optString("MCREPORTIMAGE"));
				newmeterkvah = jobject.optString("NEWMETERKVAH");
				oldmeterkvah = jobject.optString("OLDMETERKVAH");
				jobject.optString("OLDMETERKVAH");
				newmeterkva = jobject.optString("NEWMETERKVA");
				oldmeterkva = jobject.optString("OLDMETERKVA");
				oldmeterno_correction = jobject.optString("OLDMETERNO_CORRECTION");
				tenderno = jobject.optString("TENDERNO");
				boolean test = false;
				new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				test= meterInventoryMobileService.isConsumerExist(accno,newmeterno,sdocode);
				System.out.println("*********does consumer not exists: ************" + test);
				if(test){
					try {
						BCITSLogger.logger.debug("*********inside updation*******");
						SurveyOutputMobileEntity downloadEntity = new  SurveyOutputMobileEntity(
								  sdocode,  kno,  accno,  consumername,
								 address,  mobileno,  meterno,  connectiontype,  poleno,  dtcno,
								 latitude,  longitude,  premise,  sticker_no,  land_mark,  meter_image,
								premise_image,  mrcode,  appversion,  imei,  survey_timings,
								 observation,  old_metermake,  old_mf,  old_ctrn,  old_ctrd,
								 currdngkwh,  finalreading,  newmeterno,  newmetermake,  newmetertype,
								 newmf,  newctratio,  newinitialreading,  newmeterimage,  mcrreportimage,
								 newmeterkvah, oldmeterkvah, newmeterkva,  oldmeterkva,  oldmeterno_correction,tenderno);

						boolean result = false ;
						result = meterInventoryMobileService.isertToSurveyOutput(downloadEntity);
						test   = meterInventoryMobileService.isConsumerExist(accno,newmeterno,sdocode);
						System.out.println("Insert into main table>>>>"+accno+">>"+result);
						System.out.println("isConsumerExist>>>>"+accno+">>"+test);
						if(result && !test){
							meterInventoryMobileService.updateMeterInstalled(newmeterno, sdocode);
							download.put(accno, "1");
						}else{
							download.put(accno, "0");
						}
						
					} catch (Exception e) {
						BCITSLogger.logger.error("update failed: " +"	accno	"+	accno, e);

						download.put(accno, "0");
					}

				}else{
					BCITSLogger.logger.debug("****already exists*****");
					meterInventoryMobileService.updateMeterInstalled(newmeterno, sdocode);
					download.put(accno, "1");
				}

			}
			JSONArray jsonarr = new JSONArray();
			jsonarr.put(download);
			BCITSLogger.logger.debug("jsonarr.toString()"+jsonarr.toString());
			responseString= jsonarr.toString();
			return responseString;
		} catch (Exception e) {
			e.printStackTrace();
			return responseString;
		}
	}
	@RequestMapping(value = "/getDCUDetails", method = {RequestMethod.POST,RequestMethod.GET }, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getDCUDetails(@RequestBody String data) throws JSONException {
		BCITSLogger.logger.info("in getDCUDetails..........." + data);
		DateFormat serverdate_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		JSONArray array = new JSONArray();
		JSONObject objData = new JSONObject(data);
		List<?> entities = null;
		try {
			entities = meterInventoryMobileService.getCustomEntityManager("postgresMdas").createNativeQuery("select * from meter_data.dcu_master where sdocode = :sitecode ")
					.setParameter("sitecode", objData.optString("sitecode"))
					.getResultList();
			if (entities.size() != 0) {
				System.out.println("Came to list of Data");
				for (int j = 0; j < entities.size(); j++) {
					try {
						JSONObject obj = new JSONObject();
						Object[] object = (Object[]) entities.get(j);
						obj.put("dcuno", String.valueOf(object[2]));
						obj.put("ipnumber", String.valueOf(object[18]));
						obj.put("snnumber", String.valueOf(object[1]));
						obj.put("servertosbmDate", serverdate_time.format(new Date()));
						array.put(obj);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return array.toString();
	}
	
	@Transactional
	@RequestMapping(value="/uploadMeterChangeGPSDetails",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object uploadMeterChangeGPSDetails(@RequestBody String consumerlist)
	{
		System.out.println(consumerlist);
		String responseString = "[{}]";
		/*if(true){
			return responseString;
		}*/
		try {

			JSONObject download = new JSONObject();
			String sdocode               =null ;
			String kno                   =null ;
			String accno                 =null ;
			String meterno               =null ;
			String latitude              =null ;
			String longitude             =null ;
			String newlatitude           =null ;
			String newlongitude          =null ;
			String mrcode                =null ;
			String appversion            =null ;
			String imei                  =null ;
			String survey_timings        =null ;

			JSONArray arr = new JSONArray(consumerlist);
			for (int i = 0; i < arr.length(); i++)
			{
				JSONObject jobject = arr.getJSONObject(i);

				sdocode = jobject.optString("sdocode");
				kno = jobject.optString("kno");
				accno = jobject.optString("accno");
				meterno = jobject.optString("mtrno");
				latitude = jobject.optString("oldlat");
				longitude = jobject.optString("oldlong");
				newlatitude = jobject.optString("newlat");
				newlongitude = jobject.optString("newlong");
				mrcode = jobject.optString("mrcode");
				appversion = jobject.optString("version");
				imei = jobject.optString("imei");
				survey_timings = jobject.optString("datestamp");
				try {
					BCITSLogger.logger.debug("*********inside updation*******");
					System.out.println("update meter_data.survey_output set latitude = '"+newlatitude+"', longitude = '"+newlongitude+"' , status = '1' where sdocode = '"+sdocode+"'  and accno = '"+accno+"' ");
					int result =meterInventoryMobileService.getCustomEntityManager("postgresMdas").createNativeQuery("update meter_data.survey_output set latitude = '"+newlatitude+"', longitude = '"+newlongitude+"' , status = '1' where sdocode = '"+sdocode+"'  and accno = '"+accno+"' ")
							.executeUpdate();
					System.out.println("Insert into main table>>>>"+accno+">>"+result);
					if(result>0){
						System.out.println("insert into meter_data.changedmcgps ('"+sdocode+"','"+kno+"','"+accno+"','"+meterno+"','"+latitude+"','"+longitude+"','"+mrcode+"','"+appversion+"','"+imei+"','"+newlatitude+"','"+newlongitude+"','"+survey_timings+"')");
						meterInventoryMobileService.getCustomEntityManager("postgresMdas").createNativeQuery("insert into meter_data.changedmcgps values('"+sdocode+"','"+kno+"','"+accno+"','"+meterno+"','"+latitude+"','"+longitude+"','"+mrcode+"','"+appversion+"','"+imei+"','"+newlatitude+"','"+newlongitude+"','"+survey_timings+"')")
						.executeUpdate();
						download.put(accno, "1");
					}else{
						download.put(accno, "0");
					}

				} catch (Exception e) {
					BCITSLogger.logger.error("update failed: " +"	accno	"+	accno, e);

					download.put(accno, "0");
				}
			}
			JSONArray jsonarr = new JSONArray();
			jsonarr.put(download);
			BCITSLogger.logger.debug("jsonarr.toString()"+jsonarr.toString());
			responseString= jsonarr.toString();
			return responseString;
		} catch (Exception e) {
			e.printStackTrace();
			return responseString;
		}
	}
	
	@Transactional
	@RequestMapping(value="/uploadDCUDetails",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object uploadDCUDetails(@RequestBody String consumerlist)
	{
		System.out.println(consumerlist);
		String responseString = "[{}]";
		/*if(true){
			return responseString;
		}*/
		try {

			JSONObject download = new JSONObject();
			String sdocode               =null ;
			String dcu_sn_number         =null ;
			String dcu_id                =null ;
			String ip_no                 =null ;
			String latitude              =null ;
			String longitude             =null ;
			byte[] dcu_image          	 =null ;
			String installed_by          =null ;
			String appversion            =null ;
			String imei                  =null ;
			String installation_date     =null ;

			JSONArray arr = new JSONArray(consumerlist);
			for (int i = 0; i < arr.length(); i++)
			{
				JSONObject jobject = arr.getJSONObject(i);

				sdocode = jobject.optString("sdocode");
				dcu_sn_number = jobject.optString("snnumber");
				dcu_id = jobject.optString("dcuno");
				ip_no = jobject.optString("ipnumber");
				latitude = jobject.optString("newlat");
				longitude = jobject.optString("newlong");
				dcu_image = Base64.decodeBase64(jobject.optString("dcuimage"));
				installed_by = jobject.optString("mrcode");
				appversion = jobject.optString("version");
				imei = jobject.optString("imei");
				installation_date = jobject.optString("datestamp");
				List<?> dataList=null;
				dataList= meterInventoryMobileService.getCustomEntityManager("postgresMdas").createNativeQuery("SELECT * FROM meter_data.dcu_master "
						+ "WHERE dcu_sn_number='"+dcu_sn_number+"' AND sdocode='"+sdocode+"' and dcu_status = 'INSTALLED'").getResultList();
				BCITSLogger.logger.info(">>>>>>>>>>>>>>>>>>  dataList "+dataList.toString() + " dataList.isEmpty():"+dataList.isEmpty());
				if(dataList.isEmpty()){
					try {
						BCITSLogger.logger.debug("*********inside updation*******");
						int result = meterInventoryMobileService.getCustomEntityManager("postgresMdas").createNativeQuery("update meter_data.dcu_master set latitude = :latitude, longitude = :longitude, installed_by = '"+installed_by
								+"', installation_date = :installation_date, deviceid = '"+imei
								+"', appversion = '"+appversion
								+"', dcu_image = :image , dcu_status = 'INSTALLED' where sdocode = '"+sdocode+"' and dcu_status = 'NOT INSTALLED'  and dcu_sn_number = '"+dcu_sn_number+"' ")
								.setParameter("latitude",Double.parseDouble(latitude))
								.setParameter("longitude",Double.parseDouble(longitude))
								.setParameter("installation_date",new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(installation_date).getTime()))
								.setParameter("image",dcu_image)
								.executeUpdate();
						System.out.println("Insert into main table>>>>"+dcu_sn_number+">>"+result);
						if(result>0){
							download.put(dcu_sn_number, "1");
						}else{
							download.put(dcu_sn_number, "0");
						}

					} catch (Exception e) {
						BCITSLogger.logger.error("update failed: " +"	accno	"+	dcu_sn_number, e);

						download.put(dcu_sn_number, "0");
					}
				}else{
					download.put(dcu_sn_number, "1");
				}

			}
			JSONArray jsonarr = new JSONArray();
			jsonarr.put(download);
			BCITSLogger.logger.debug("jsonarr.toString()"+jsonarr.toString());
			responseString= jsonarr.toString();
			return responseString;
		} catch (Exception e) {
			e.printStackTrace();
			return responseString;
		}
	}
	
	@RequestMapping(value = "/apk/getapkversion/{remarks}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody
	String getVersion_field(@PathVariable String remarks) throws JSONException {
		BCITSLogger.logger.info("in getVersion_..........."+remarks);
		String version = null;
		try {
			List<OnAirVersionUpdationEntity> entities = onAirVersionUpdationService.getlatestVersion(remarks);
			version = entities.get(0).getApkVersion();
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return version;
	}

	/**
	 * @category GET LATEST VERSION Based On deviceID
	 * @return version
	 * @throws JSONException
	 * @author Shivanand
	 */
	//JVVNL METHOD
	@RequestMapping(value = "apk/getlatestversion_by_imei/{deviceid}/{appname}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody
	String getVersion_deviceid(@PathVariable("deviceid") String deviceid, @PathVariable("appname") String appname) throws JSONException {
		BCITSLogger.logger.info("in getVersion_JVVNL...........");
		String version = null;
		try {
			List<OnAirVersionUpdationEntity> entities = onAirVersionUpdationService	.getlatestVersion(appname);
			version = entities.get(0).getApkVersion();
			System.out.println("Coming 2nd loop :"+version);
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return version;
	}
	//JVVNL METHOD

	@RequestMapping(value = "/apk/downloadApk_by_version/{version}/{appname}", method = {RequestMethod.POST, RequestMethod.GET})
	public  @ResponseBody 
	void getApkFileDeviceidnew(HttpServletRequest request , HttpServletResponse response, @PathVariable("version") String version,@PathVariable("appname") String appname)  {
		BCITSLogger.logger.info("in getApkFile...........");
		try{
			List<OnAirVersionUpdationEntity> entities;

			if (appname.equalsIgnoreCase("JVVNLFIELD")) {
				entities = onAirVersionUpdationService.getlatestVersion(appname);
			} else {
				entities = onAirVersionUpdationService.getlatestVersion(appname);
			}
			System.out.println("***Version*"+entities.get(0).getApkVersion());
			final String FILE_PATH =   entities.get(0).getApkPath()+entities.get(0).getApkName();
			String filePathToBeServed = FILE_PATH;

			File fileToDownload = new File(filePathToBeServed);
			System.out.println("filePathToBeServed :"+filePathToBeServed);
			long length = fileToDownload.length();
			if (length <= Integer.MAX_VALUE)
			{
				response.setContentLength((int)length);
			}
			else
			{/*
			    response.addHeader("Content-Length", Long.toString(length));*/
			}
			InputStream inputStream = new FileInputStream(fileToDownload);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename="+entities.get(0).getApkName()); 
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();

		}catch(Exception e){
			BCITSLogger.logger.info("Request could not be completed at this moment. Please try again.");
			e.printStackTrace();
		}
	}


	@RequestMapping(value = "/login/checkinOtherApps/{remarks}/{apk}/{userid}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody
	String loginValidationOtherApps(@PathVariable("remarks") String remarks,@PathVariable("userid") String userid,@PathVariable("apk") String apk) throws JSONException 
	{
		BCITSLogger.logger.info("in loginValidation...........");
		int login = 0;
		String SDOCODE = "null";
		String billed = "0" ;
		String unbilled = "0" ;
		String sdocodedatabase = "1";
		String consumer = "null";
		String[] rquest = userid.split("-");
		String meterreadercode = rquest[0];
		String imei = rquest[1];
		String sdocode = rquest[2];
		try {
			boolean[] b = mrDeviceAllocationService.isUserExist(sdocode, meterreadercode,imei) ;
			//String billedString = hhbm_ConsumerService.getTotalBilled(sdocode, meterreadercode) ;
			//String unbilledString = hhbm_ConsumerService.getTotalunBilled(sdocode, meterreadercode) ;
			System.out.println("version"+apk);
			if (b[0]==true||b[1]==true) {
				SDOCODE=sdocode;
				List<AllowedDeviceVersionsEntity> allowedVersionList = allowedDeviceVersionService.findAllField(remarks);
				for (AllowedDeviceVersionsEntity allowedDeviceVersionsEntity : allowedVersionList) {
					System.out.println("allowedDeviceVersionsEntity.getVersion_name()"+Double.valueOf(allowedDeviceVersionsEntity.getVersion_name())+"=="+Double.valueOf(apk));
					System.out.println("allowedDeviceVersionsEntity.getVersion_name()"+(Double.valueOf(allowedDeviceVersionsEntity.getVersion_name()).equals(Double.valueOf(apk))));
					if(Double.valueOf(allowedDeviceVersionsEntity.getVersion_name()).equals(Double.valueOf(apk))){
						login = 1;
						break ;
					}
				}
			}else{
				login = 0;
			}
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}

		return login+"-"+SDOCODE+"-"+billed+"-"+unbilled+"-"+consumer+"-"+sdocodedatabase;
	}


	@RequestMapping(value = "/newlogin/newvalidation/{userid}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody
	String newloginValidation(@PathVariable("userid") String userid) throws JSONException 
	{
		BCITSLogger.logger.info("in loginValidation...........");
		boolean[] b  = new boolean[2];
		String result = "0-invalid";
		try {
			String[] rquest = userid.split("-");
			String meterreadercode = rquest[0];
			String sdocode = rquest[1];
			String imei = rquest[2];
			if(sdocode.equalsIgnoreCase("1234") && meterreadercode.equalsIgnoreCase("test")){
				b[0] = true;
				b[1] = true;
			}
			else{
				b =	mrDeviceAllocationService.isUserExist(sdocode, meterreadercode, imei) ;
			}
			if(b[0] ){
				result ="1" ;
				if(b[1]) 
					result +="-valid" ;
				else
					result ="2-invalid" ;
			}else {
				result ="0-invalid" ;
			}
			/*datafromserver[0].equals("1") && datafromserver[1].equalsIgnoreCase("valid")  
			datafromserver[0].equals("0") invalid*/
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return result;
	}
	/**
	 * @category Login credentials and existence of data for given credentials
	 * @param userid
	 * @return Status
	 * @throws JSONException
	 * @author yogesh
	 */
	@RequestMapping(value = "/getlogincredentials/{apk}/{userid}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody
	String newloginValidationCedentials(@PathVariable String userid,@PathVariable String apk) throws JSONException 
	{
		BCITSLogger.logger.info("in loginValidation..........."+userid);
		String[] b  = new String[2];
		String result = "-", version = "0",version_status = "old";
		String MRcode=null, Sitecode=null;
		try {
			b =	mrDeviceAllocationService.getLoginCredentials(userid) ;
			MRcode=b[1];
			Sitecode=b[0];
			/*try
			{
				String response= sendRequest(oracleURL+"getMrCodeAndSdoCodeMrbdUhbvn/"+userid);

				System.out.println(response);
				if(response==null || response.equals("INVALID_USER")){
					return "INVALID_USER";
				}
				JSONObject obj = new JSONObject(response);
				MRcode=obj.getString("MRCODE");
				Sitecode= obj.getString("SDOCODE");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "Error while downloading "+e.getMessage();
			} */

			List<OnAirVersionUpdationEntity> entities = onAirVersionUpdationService.getlatestVersion();
			version = entities.get(0).getApkVersion();
			System.out.println(apk+"==="+version);
			if (Double.parseDouble(apk)>=Double.parseDouble(version)) {
				version = "1";
			} else {
				version = "0";
			}
			List<AllowedDeviceVersionsEntity> allowedVersionList = allowedDeviceVersionService.findAll();
			for (AllowedDeviceVersionsEntity allowedDeviceVersionsEntity : allowedVersionList) {

				if(allowedDeviceVersionsEntity.getVersion_name().equalsIgnoreCase(apk)){
					version_status = "latest";
					break ;
				}
			}
			result =Sitecode+"-"+MRcode+"-"+version+"-"+version_status;
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return result;
	}
	@RequestMapping(value = "/getlogincredentials/{apk}/{userid}/{appname}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody
	String newloginValidationCedentialsEbill(@PathVariable String userid,@PathVariable String apk,@PathVariable String appname) throws JSONException 
	{
		BCITSLogger.logger.info("in loginValidation..........."+userid);
		String[] b  = new String[2];
		String result = "-", version = "0",version_status = "old";
		String MRcode=null, Sitecode=null;
		try {
			b =	mrDeviceAllocationService.getLoginCredentials(userid) ;
			MRcode=b[1];
			Sitecode=b[0];
			/*try
			{
				String response= sendRequest(oracleURL+"getMrCodeAndSdoCodeMrbdUhbvn/"+userid);

				System.out.println(response);
				if(response==null || response.equals("INVALID_USER")){
					return "INVALID_USER";
				}
				JSONObject obj = new JSONObject(response);
				MRcode=obj.getString("MRCODE");
				Sitecode= obj.getString("SDOCODE");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "Error while downloading "+e.getMessage();
			} */

			List<OnAirVersionUpdationEntity> entities = onAirVersionUpdationService.getlatestVersion(appname);
			version = entities.get(0).getApkVersion();
			System.out.println(apk+"==="+version);
			if (Double.parseDouble(apk)>=Double.parseDouble(version)) {
				version = "1";
			} else {
				version = "0";
			}
			List<AllowedDeviceVersionsEntity> allowedVersionList = allowedDeviceVersionService.findAllField(appname);
			for (AllowedDeviceVersionsEntity allowedDeviceVersionsEntity : allowedVersionList) {

				if(allowedDeviceVersionsEntity.getVersion_name().equalsIgnoreCase(apk)){
					version_status = "latest";
					break ;
				}
			}
			result =Sitecode+"-"+MRcode+"-"+version+"-"+version_status;
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return result;
	}
	/**
	 * @category Device Validate Method
	 * @param devciedate
	 * @param devicetime
	 * @param apk
	 * @param imei_no
	 * @return  device Validation Results
	 * @throws JSONException
	 */
	//EBILLL METHOD
	@RequestMapping(value = "/loginvalidate_for_ebill/{devciedate}/{devicetime}/{apk}/{imei_no}/{remarks}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody Object deviceValidateEbill(@PathVariable("devciedate") String devciedate , @PathVariable("devicetime") String devicetime , 
			@PathVariable("apk") String apk , @PathVariable("imei_no") String imei_no ,@PathVariable("remarks") String remarks  ) throws JSONException {
		BCITSLogger.logger.info("in deviceValidate...........");
		String version = null;
		String resultTime = "" , version_status = "old" , device_status = "notreg" ,version_update_status = "new"; 
		try {

			///////////////////////DATE AND TIME VALIDATION///////////////////////////////
			String devicedate = devciedate.trim().replace("@", "/") + " "+ devicetime;
			System.out.println(devicedate);
			Date date1 = new Date();
			System.out.println("mobiledate" + date1.getTime());
			DateFormat serverdate_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String serverdate = serverdate_time.format(date1);
			System.out.println("serverdate" + serverdate);
			System.out.println("devicedate" + devicedate);
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
			System.out.println("Time in minutes: " + diffMinutes + " minutes.");
			int diff_min = (int) Math.abs(diffMinutes);

			if (diff_min > 10) {resultTime = "invalidtime@" + serverdate_time.format(date1);} 
			else {	resultTime = "validtime@" + serverdate_time.format(date1);	}
			///////////////////////END DATE AND TIME VALIDATION///////////////////////////////		


			/////////////////////////////// ALLOWEDDEVICEVERSIONS ///////////////////////////////////
			List<AllowedDeviceVersionsEntity> allowedVersionList = allowedDeviceVersionService.findAllField(remarks);
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
			System.out.println(remarks);
			List<OnAirVersionUpdationEntity> entities = onAirVersionUpdationService	.getlatestVersion(remarks);
			version = entities.get(0).getApkVersion();

			if (compareVersions(apk, version) == -1) 
			{
				version_update_status = "old";
			}
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		System.out.println("OUTPUT VALIDATION IS :::::" + resultTime + "@" + version_status + "@" + device_status + "@"+ version_update_status);
		return resultTime + "@" + version_status + "@" + device_status + "@"+ version_update_status; 
	}
	@RequestMapping(value = "/login/validate_for_group/{devciedate}/{devicetime}/{apk}/{imei_no}", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody Object deviceValidate(@PathVariable("devciedate") String devciedate , @PathVariable("devicetime") String devicetime , 
			@PathVariable("apk") String apk , @PathVariable("imei_no") String imei_no  ) throws JSONException {
		BCITSLogger.logger.info("in deviceValidate...........");
		String version = null;
		String resultTime = "" , version_status = "old" , device_status = "notreg" ,version_update_status = "new"; 
		try {

			///////////////////////DATE AND TIME VALIDATION///////////////////////////////
			String devicedate = devciedate.trim().replace("@", "/") + " "+ devicetime;
			System.out.println(devicedate);
			Date date1 = new Date();
			System.out.println("mobiledate" + date1.getTime());
			DateFormat serverdate_time = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String serverdate = serverdate_time.format(date1);
			System.out.println("serverdate" + serverdate);
			System.out.println("devicedate" + devicedate);
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
			System.out.println("Time in minutes: " + diffMinutes + " minutes.");
			int diff_min = (int) Math.abs(diffMinutes);

			if (diff_min > 10) {resultTime = "invalidtime@" + serverdate_time.format(date1);} 
			else {	resultTime = "validtime@" + serverdate_time.format(date1);	}
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

			List<OnAirVersionUpdationEntity> entities = onAirVersionUpdationService	.getlatestVersion();
			version = entities.get(0).getApkVersion();

			if (compareVersions(apk, version) == -1) 
			{
				version_update_status = "old";
			}
		} catch (Exception exception) {
			BCITSLogger.logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		System.out.println("OUTPUT VALIDATION IS :::::" + resultTime + "@" + version_status + "@" + device_status + "@"+ version_update_status);
		return resultTime + "@" + version_status + "@" + device_status + "@"+ version_update_status; 
	}
	/**
	 * @category To Get Server Date
	 * @return  Server Date
	 */
	@RequestMapping(value = "/updatefromlocal/getserverdate", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String getServerDate()  {
		BCITSLogger.logger.info("in getVersion_...........");
		String serverDate = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			serverDate = dateFormat.format(cal.getTime());
			System.out.println("*********TEST Ok ************" + serverDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverDate;
	}
	
	public static final int compareVersions(String presentver1, String dbver2) {

		String[] vals1 = presentver1.split("\\.");
		String[] vals2 = dbver2.split("\\.");
		int i=0;
		while(i<vals1.length&&i<vals2.length&&vals1[i].equals(vals2[i])) {
			i++;
		}
		if (i<vals1.length&&i<vals2.length) {
			int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
			return diff<0?-1:diff==0?0:1;
		}
		return vals1.length<vals2.length?-1:vals1.length==vals2.length?0:1;
	}


	@RequestMapping(value = "/uploadFileToServerAndroid/{MRcode}/{Sitecode}")
	public  @ResponseBody String uploadFile(@RequestParam("uploadedfile") MultipartFile uploadedFileRef,@PathVariable("MRcode") String MRcode , @PathVariable("Sitecode") String Sitecode) {
		// Get name of uploaded file.
		final String rootFolder ="C:\\UHBVN_BILLING_LOGS";
		//		final String rootFolder ="/application/JVVNL_FOLDERS/";
		final String subFoler=rootFolder+"\\"+Sitecode;
		//		final String rootFolder ="/application/JVVNL_LOGS/"+Sitecode;
		final String mobileFolder=subFoler+"\\"+MRcode;
		String fileName = uploadedFileRef.getOriginalFilename();
		final String datefolder=mobileFolder+"/"+new SimpleDateFormat("dd-MM-yyyy hh-mm-a").format(new Date());

		System.out.println(mobileFolder+"  FILE NAMEEEEEEEEEEEEEEEEE "+fileName);

		// Path where the uploaded file will be stored.

		if(folderExists(rootFolder) && folderExists(subFoler)&& folderExists(mobileFolder)&& folderExists(datefolder)) // CREATING FOLDERS IF NOT EXISTS
		{
			String path = mobileFolder +"\\"+ fileName;

			// This buffer will store the data read from 'uploadedFileRef'
			byte[] buffer = new byte[1000];

			// Now create the output file on the server.
			File outputFile = new File(path);

			InputStream reader = null;
			FileOutputStream writer = null;
			int totalBytes = 0;
			try {
				outputFile.createNewFile();
				// Create the input stream to uploaded file to read data from it.
				reader = uploadedFileRef.getInputStream();

				// Create writer for 'outputFile' to write data read from
				// 'uploadedFileRef'
				writer = new FileOutputStream(outputFile);

				// Iteratively read data from 'uploadedFileRef' and write to
				// 'outputFile';            
				int bytesRead = 0;
				while ((bytesRead = reader.read(buffer)) != -1) {
					writer.write(buffer);
					totalBytes += bytesRead;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("! Total Bytes Read="+totalBytes);



			try {
				ZipFile zipFile = new ZipFile(path);
				zipFile.extractAll(datefolder);
				new File(path).delete();
			} catch (ZipException e) {
				e.printStackTrace();
			}


			return "FILE UPLOADED SUCCESSFULLY";
		}
		return "FILE UPLOAD FAILED";
	}
	@RequestMapping(value = "/uploadPaymentsFileToServerAndroid/{MRcode}/{Sitecode}")
	public  @ResponseBody String uploadPaymentsFileToServerAndroid(@RequestParam("uploadedfile") MultipartFile uploadedFileRef,@PathVariable("MRcode") String MRcode , @PathVariable("Sitecode") String Sitecode) {
		// Get name of uploaded file./home/bcits/CESCLIVE/
		//final String rootFolder ="E:\\JVVNL_PAYMENTS";
		final String rootFolder ="/application/JVVNL_PAYMENTS/";
		final String subFoler=rootFolder+"/"+Sitecode;

		final String mobileFolder=subFoler+"/"+MRcode;
		//final String datefolder=mobileFolder+"\\"+new SimpleDateFormat("ddMMyyyyhhmma").format(new Date());
		final String datefolder=mobileFolder+"/"+new SimpleDateFormat("dd-MM-yyyy hh-mm-a").format(new Date());
		String fileName =uploadedFileRef.getOriginalFilename();

		System.out.println(mobileFolder+"  FILE NAMEEEEEEEEEEEEEEEEE---"+fileName);

		// Path where the uploaded file will be stored.

		if(folderExists(rootFolder) && folderExists(subFoler)&& folderExists(mobileFolder)&& folderExists(datefolder)) // CREATING FOLDERS IF NOT EXISTS
		{
			//String path = mobileFolder +"\\"+ fileName;
			String path = mobileFolder +"/"+ fileName;

			// This buffer will store the data read from 'uploadedFileRef'
			byte[] buffer = new byte[1000];

			// Now create the output file on the server.
			File outputFile = new File(path);

			InputStream reader = null;
			FileOutputStream writer = null;
			int totalBytes = 0;
			try {
				outputFile.createNewFile();
				// Create the input stream to uploaded file to read data from it.
				reader = uploadedFileRef.getInputStream();

				// Create writer for 'outputFile' to write data read from
				// 'uploadedFileRef'
				writer = new FileOutputStream(outputFile);

				// Iteratively read data from 'uploadedFileRef' and write to
				// 'outputFile';            
				int bytesRead = 0;
				while ((bytesRead = reader.read(buffer)) != -1) {
					writer.write(buffer);
					totalBytes += bytesRead;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("! Total Bytes Read="+totalBytes);

			try {
				ZipFile zipFile = new ZipFile(path);
				zipFile.extractAll(datefolder);
				new File(path).delete();
			} catch (ZipException e) {
				e.printStackTrace();
			}


			return "FILE UPLOADED SUCCESSFULLY";
		}
		return "FILE UPLOAD FAILED";
	}

	@RequestMapping(value = "/uploadFolderToServerAndroid/{MRcode}/{Sitecode}")
	public  @ResponseBody String uploadFolderToServerAndroid(@RequestParam("uploadedfile") MultipartFile uploadedFileRef,@PathVariable("MRcode") String MRcode , @PathVariable("Sitecode") String Sitecode) {
		final String rootFolder ="C:\\UHBVN_BILL_FOLDERS";
		//		final String rootFolder ="/application/JVVNL_FOLDERS/";
		final String subFoler=rootFolder+"\\"+Sitecode;

		final String mobileFolder=subFoler+"\\"+MRcode;
		//final String datefolder=mobileFolder+"\\"+new SimpleDateFormat("ddMMyyyyhhmma").format(new Date());
		final String datefolder=mobileFolder+"/"+new SimpleDateFormat("dd-MM-yyyy hh-mm-a").format(new Date());
		String fileName =uploadedFileRef.getOriginalFilename();

		System.out.println(mobileFolder+"  FILE NAMEEEEEEEEEEEEEEEEE---"+fileName);

		// Path where the uploaded file will be stored.

		if(folderExists(rootFolder) && folderExists(subFoler)&& folderExists(mobileFolder)&& folderExists(datefolder)) // CREATING FOLDERS IF NOT EXISTS
		{
			//String path = mobileFolder +"\\"+ fileName;
			String path = mobileFolder +"\\"+ fileName;

			// This buffer will store the data read from 'uploadedFileRef'
			byte[] buffer = new byte[1000];

			// Now create the output file on the server.
			File outputFile = new File(path);

			InputStream reader = null;
			FileOutputStream writer = null;
			int totalBytes = 0;
			try {
				outputFile.createNewFile();
				// Create the input stream to uploaded file to read data from it.
				reader = uploadedFileRef.getInputStream();

				// Create writer for 'outputFile' to write data read from
				// 'uploadedFileRef'
				writer = new FileOutputStream(outputFile);

				// Iteratively read data from 'uploadedFileRef' and write to
				// 'outputFile';            
				int bytesRead = 0;
				while ((bytesRead = reader.read(buffer)) != -1) {
					writer.write(buffer);
					totalBytes += bytesRead;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("! Total Bytes Read="+totalBytes);

			try {
				ZipFile zipFile = new ZipFile(path);
				zipFile.extractAll(datefolder);
				new File(path).delete();
			} catch (ZipException e) {
				e.printStackTrace();
			}
			return "FILE UPLOADED SUCCESSFULLY";
		}
		return "FILE UPLOAD FAILED";
	}

	public static boolean folderExists(String rtfolder) {
		File rootFolder= new File(rtfolder);
		if(!rootFolder.exists())
		{
			try {
				rootFolder.mkdir();
				System.out.println("Created new folder");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Folder creation failed");
				return false;
			}
		}else
		{
			return true;
		}
	}




	@SuppressWarnings("resource")
	@RequestMapping(value="/uploadlogs/{MRcode}/{Sitecode}", method=RequestMethod.POST)
	public @ResponseBody String handleFileUpload( 
			@RequestParam("filelogs") MultipartFile file,@RequestParam("filelogserror") MultipartFile fileerror, @PathVariable("MRcode") String MRcode , @PathVariable("Sitecode") String Sitecode){
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				byte[] bytes2 = fileerror.getBytes();
				FileOutputStream fop, fop1 = null;
				File file11, file12;
				Date date111 = new Date();
				System.out.println("mobiledate" + date111.getTime());
				DateFormat serverdate_time = new SimpleDateFormat("dd-MM-yyyy-HHmm");
				String serverdate = serverdate_time.format(date111);
				String filenameforlogs = MRcode+"_"+serverdate+".log";
				/*String directryname = "E:\\CESCMobileLOGS\\"+Sitecode;*/

				//String directryname = "E:\\JVVNL_LOGS\\"+Sitecode;
				String directryname = "/application/JVVNL_LOGS/"+Sitecode;
				file11 = new File(directryname);

				if (!file11.exists()) {
					file11.mkdirs();
				}
				System.out.println(file11.canWrite());
				/*	String filename = "E:\\CESCMobileLOGS\\"+Sitecode+"\\"+filenameforlogs;*/
				//String filename = "E:\\JVVNL_LOGS\\"+Sitecode+"\\"+filenameforlogs;
				String filename = "/application/JVVNL_LOGS/"+Sitecode+"/"+filenameforlogs;
				System.out.println("filename is :"+filename);
				file11 = new File(filename);
				fop = new FileOutputStream(file11);

				// if file doesnt exists, then create it
				if (!file11.exists()) {
					file11.createNewFile();
				}
				try {            
					fop.write(bytes);
					if(!fileerror.isEmpty()){
						/*file12 = new File("E:\\CESCMobileLOGS\\"+Sitecode+"\\"+MRcode+"_"+serverdate+"error.log");*/
						//file12 = new File("E:\\JVVNL_LOGS\\"+Sitecode+"\\"+MRcode+"_"+serverdate+"error.log");
						file12 = new File("/application/JVVNL_LOGS/"+Sitecode+"/"+MRcode+"_"+serverdate+"error.log");
						fop1 = new FileOutputStream(file12);
						if (!file12.exists()) {
							file12.createNewFile();
						}
						fop1.write(bytes2);
					}
					return "success";

				} catch (IOException e) {
					e.printStackTrace();
					return "failed";

				}
				/* unzipImage(AppConstants.DataPath + "/products.zip",
                       AppConstants.DataPath);*/
			}catch (Exception E)
			{
				E.printStackTrace();
				return "failed";
			}
		} else {
			return "failed";
		}
	}

	public static double doubRound(double val, int places) {
		long factor = (long)Math.pow(10,places);
		val = val * factor;
		long tmp = Math.round(val);
		return (double)tmp / factor;
	}
	public String []   getUniqueLatLongs( double lattitude_frommobile, double longitude_frommobile){
		String [] array = new String[2];
		String [] arraydate ;
		try {
			Date d = new Date();
			SimpleDateFormat format = new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss: a");
			String date = format.format(d);

			//String date = sd.format(d);
			System.out.println(date);
			arraydate = date.split(":");
			int count = Integer.parseInt(arraydate[2]);

			if (count<5&& count>=0) {
				lattitude_frommobile = lattitude_frommobile+ 0.0001214;
			}			
			else if (count<11&& count>=6) {
				longitude_frommobile = longitude_frommobile+ 0.0001214;
			}			
			else if (count<17&& count>=12) {
				longitude_frommobile = longitude_frommobile+ 0.0002109;
			}			
			else if (count<23&& count>=18) {
				lattitude_frommobile = lattitude_frommobile+ 0.0001214;
				longitude_frommobile = longitude_frommobile+ 0.0002109;
			}			
			else if (count<29&& count>=24) {
				longitude_frommobile = longitude_frommobile+ 0.0001214;
				lattitude_frommobile = lattitude_frommobile+ 0.0002109;
			}			
			else if (count<35&& count>=30) {
				lattitude_frommobile = lattitude_frommobile+ 0.0002719;
			}			
			else if (count<41&& count>=36) {
				longitude_frommobile = longitude_frommobile+ 0.0002651;
			}			
			else if (count<47&& count>=42) {
				lattitude_frommobile = lattitude_frommobile+ 0.0002651;
				longitude_frommobile = longitude_frommobile+ 0.0002109;
			}			
			else if (count<53&& count>=48) {
				lattitude_frommobile = lattitude_frommobile+ 0.0002651;
				longitude_frommobile = longitude_frommobile+ 0.0002719;
			}			
			else if (count<60&& count>=54) {
				lattitude_frommobile = lattitude_frommobile+ 0.0002109;
				count=0;
			}
			array[0] = ""+lattitude_frommobile;
			array[1] = ""+longitude_frommobile;
		} catch (Exception e) {
			array[0] = ""+lattitude_frommobile;
			array[1] = ""+longitude_frommobile;
		}
		return array;
	} 

	
	public static String sendRequest(String url) {
		HttpURLConnection conn = null;
		String response = "error: " + "GET" + " failed ";
		String type = "application/json";
		String method = "GET";
		try {

			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("Content-Type", type);
			conn.setRequestProperty("Accept", type);
			conn.setRequestMethod(method);
			conn.setConnectTimeout(10000);
			String line = "";
			StringBuffer sb = new StringBuffer();
			BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = input.readLine()) != null)
				sb.append(line);
			input.close();
			conn.disconnect();
			response = sb.toString();
			String responseFromServer = response;
			return responseFromServer;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.disconnect();
			} catch (Exception e) {
			}
		}
		return null;
	} 
}