package com.bcits.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.MeterMaster;
import com.bcits.entity.MobileBillingDataEntity;
import com.bcits.entity.Seal;
import com.bcits.service.ConsumerOutputLiveMIPService;
import com.bcits.service.MRDetailsService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.NoMrdService;
import com.bcits.service.RunningApkVersionService;
import com.bcits.service.SBMService;
import com.bcits.service.VersionApkService;
import com.bcits.service.sealService;
import com.bcits.utility.Resultupdated;

@Controller
public class MobileDataDownloadAndUploadController {

	@Autowired
	ConsumerOutputLiveMIPService consumerOutputLiveMIPService;

	@Autowired
	MeterMasterService meterMasterService;

	@Autowired
	MRDetailsService mrDetailsService;

	@Autowired
	RunningApkVersionService runningApkVersionService;

	@Autowired
	SBMService sbmService;

	@Autowired
	sealService sealService;

	@Autowired
	VersionApkService versionApkService;


	@Autowired
	NoMrdService nomrdService;

	@RequestMapping(value = "/insertconsumeroutputlivemip", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Resultupdated>  getinsertDeviceInformation(@RequestBody String body,HttpServletRequest request) throws IOException, JSONException {

		ArrayList<Resultupdated> list = new ArrayList<Resultupdated>();
		System.out.println("UPDATE DATA ================ ");
		try{
			MeterMaster meterMasterEntity = new MeterMaster();

			JSONArray array =new JSONArray(body); 

			consumerOutputLiveMIPService.updateMobileDataToConsumerOutPutLive(request, array);
			list = meterMasterService.updateMobileDataToMeterMaster(meterMasterEntity, request, array);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}


	@RequestMapping(value = "/sealreturnupdate", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Resultupdated>  updateSealReturnDamage(@RequestBody String body,HttpServletRequest request) throws IOException, JSONException {
		System.out.println("sealreturnupdate");
		ArrayList<Resultupdated> list = new ArrayList<Resultupdated>();
		try{
			JSONArray array =new JSONArray(body); 
			list = sealService.updateSealsPending(request, array);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}


	@RequestMapping(value = "/nomrdupdatemobile", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Resultupdated>  nomrdupdateFromMobile(@RequestBody String body,HttpServletRequest request) throws IOException, JSONException {
		System.out.println("nomrdupdatemobile");
		ArrayList<Resultupdated> list = new ArrayList<Resultupdated>();
		try{
			JSONArray array =new JSONArray(body); 
			list = nomrdService.insertNoMRD(request, array);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}


	@RequestMapping(value="/getdatenew",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getServerDateToUpload() 
	{
		System.out.println("Coming to getdatenew method=======");
		String serverDate = null ;
		try
		{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			serverDate = dateFormat.format(cal.getTime());
		}
		catch (Exception e){}
		return serverDate;
	}

	@RequestMapping(value="/mobiledownloaddatavalidation/{deviceid}/{apk}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getMobileDownloadDataValidation(@PathVariable("deviceid") String  deviceid, @PathVariable("apk") String  apk, HttpServletRequest request) 
	{
		System.out.println("Download Validation ========= DEV ID "+deviceid +"    APK "+apk);
		String config =null;
		String apk_status = null;
		String Mrname = mrDetailsService.findMRname(deviceid);
		if(Mrname != null)
		{ 
			config ="SBMNOISConfigured";
		} 
		else 
		{
			config ="SBMNOISNOtConfigured";
		}
		apk = apk.replace("z", ".");
		double apkDouble = Double.parseDouble(apk);
		apk_status = runningApkVersionService.findApkVersion(apkDouble);
		String result = config + "#"+  apk_status;
		return result;
	}


	@RequestMapping(value = "/getconsumerdataformobile/{deviceid}", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<MobileBillingDataEntity> getCosnumerDataForMobile(@PathVariable("deviceid") String  deviceid,HttpServletRequest request) throws IOException {
		System.out.println("getconsumerdataformobile");
		List<MobileBillingDataEntity> list =null;

		try{
			String Mrname = mrDetailsService.findMRname(deviceid);
			list = sbmService.getMobileBillingData(Mrname, request);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}


	@RequestMapping(value = "/getsealsformobile/{deviceid}", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<Seal> getSealsForMobile(@PathVariable("deviceid") String  deviceid,HttpServletRequest request) throws IOException {
		System.out.println("getsealsformobile");
		List<Seal> list = null;

		try{
			String Mrname = mrDetailsService.findMRname(deviceid);
			list = sealService.getSealsForMobileMR(Mrname, request);
			//return list;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/getNOTMRDdataForMobile/{deviceid}", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getNOTMRDdataForMobile(@PathVariable("deviceid") String  deviceid,HttpServletRequest request) throws IOException {
		System.out.println("getNOTMRDdataForMobile");
		JSONArray jsonArray = null;

		try{
			String Mrname = mrDetailsService.findMRname(deviceid);
			jsonArray = meterMasterService.getNotMRDdata(Mrname, request);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return jsonArray.toString();
	}


	/*@RequestMapping(value="/apk/getapkversion",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getApkversion() 
	{
		System.out.println("/apk/getapkversion");
		return versionApkService.findApkMaxVersion();
	}*/


	@RequestMapping(value = "/apk1/downloadApk1", method = {RequestMethod.POST, RequestMethod.GET})
	public  @ResponseBody 
	void getApkFile(HttpServletRequest request , HttpServletResponse response)  {
		try{
			System.out.println("apk1/downloadApk1");
			String maxVersion = versionApkService.findApkMaxVersion();
			String filePathToBeServed = versionApkService.FindApkDetails(maxVersion);
			File fileToDownload = new File(filePathToBeServed);

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

			/* response.setHeader("Content-Disposition", "attachment; filename="+entities.get(0).getApkName());*/ 

			response.setHeader("Content-Disposition", "attachment; filename="+"MIP_J3.0.apk");
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
