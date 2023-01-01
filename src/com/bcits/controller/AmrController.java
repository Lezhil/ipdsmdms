package com.bcits.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bcits.entity.AmrMeterData;
import com.bcits.entity.AmrMeterData.CustomKey;
import com.bcits.service.AmrMeterDataService;
import com.bcits.service.VersionApkService;
import com.bcits.utility.amr.AmrMethods;
import com.bcits.utility.amr.XmlCdfCreator;

@Controller
public class AmrController {
	
	 
	@Autowired
	private VersionApkService versionApkService;
	
	@Autowired
	private AmrMeterDataService amrMeterData;
	
	private SimpleDateFormat formatMobile = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
	private SimpleDateFormat txtNameFormat = new SimpleDateFormat("yyyyMMddHHmmss",Locale.ENGLISH);
	
	private static String rootFolder ="C:\\AMR_BSMART";
	public static String meterFileFolder=rootFolder+"\\METER_FILES"+"\\JVVNL";
	private final String mobileFolder=rootFolder+"\\MOBILE_FILES";
	 
	
	
	//TODO GETTING FILE CONTENT FROM ANDROID
		@RequestMapping(value = "/uploadDlmsJsonFile", method={RequestMethod.POST,RequestMethod.GET}, produces={"application/json; charset=UTF-8"})
		public @ResponseBody String uploadDlmsJsonFile(@RequestBody String value) {
			JSONArray response = new JSONArray();
			try {
				
			if(AmrMethods.folderExists(rootFolder) && AmrMethods.folderExists(meterFileFolder)) // CREATING FOLDERS IF NOT EXISTS
			{
				JSONArray array = new JSONArray(value);
				for (int i = 0; i < array.length(); i++)
				{
					JSONObject meterData= array.getJSONObject(i);
					
					try {//ADDING TO TABLE
						new AddToTable(meterData).start();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String fileName= meterData.getString("METER_NUMBER")+"_"+txtNameFormat.format(formatMobile.parse(meterData.getString("CREATED_TIME")));
				
					String xmlFileName =meterFileFolder+"\\"+fileName+".xml";
					
					XmlCdfCreator cdfCreator = new XmlCdfCreator();
					
					if(cdfCreator.createXML(meterData,xmlFileName))
					{
					response.put(new JSONObject().put("METER_NUMBER", meterData.getString("METER_NUMBER")).put("CREATED_TIME", meterData.getString("CREATED_TIME")));
					}
				}
				System.out.println(response);
			}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return response.toString();
		}
		
		
	private class AddToTable extends Thread {
		
		JSONObject meterData;

		AddToTable(JSONObject meterData) {
			this.meterData = meterData;
		}

		@Override
		public void run() {
			super.run();
			
		                
		try {
			AmrMeterData e = new AmrMeterData();
			e.setBillingDate(meterData.optString("BILLING_DATE"));
			try {
				e.setCreatedTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(meterData.optString("CREATED_TIME")));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.setCumulativeBillingCount(meterData.optString("CUMULATIVE_BILLING_COUNT"));
			e.setCumulativeEnergyKvarhLag(meterData.optString("CUMULATIVE_ENERGY_KVARH_LAG"));
			e.setCumulativeEnergyKvarhLead(meterData.optString("CUMULATIVE_ENERGY_KVARH_LEAD"));
			e.setCumulativeProgrammingCount(meterData.optString("CUMULATIVE_PROGRAMMING_COUNT"));
			e.setCumulativeTamperCount(meterData.optString("CUMULATIVE_TAMPER_COUNT"));
			e.setDemandIntegrationPeriod(meterData.optString("DEMAND_INTEGRATION_PERIOD"));
			e.setFirmwareVersion(meterData.optString("FIRMWARE_VERSION"));
			e.setFrequency(meterData.optString("FREQUENCY"));
			e.setInternalCtRatio(meterData.optString("INTERNAL_CT_RATIO"));
			e.setInternalPtRatio(meterData.optString("INTERNAL_PT_RATIO"));
			e.setKva(meterData.optString("KVA"));
			e.setKvah(meterData.optString("KVAH"));
			e.setKwh(meterData.optString("KWH"));
			e.setLineCurrent1(meterData.optString("LINE_CURRENT_1"));
			e.setLineCurrent2(meterData.optString("LINE_CURRENT_2"));
			e.setLineCurrent3(meterData.optString("LINE_CURRENT_3"));
			e.setLogicalName(meterData.optString("LOGICAL_NAME"));
			e.setManufacturer(meterData.optString("MANUFACTURER"));
			e.setMeterDate(meterData.optString("METER_DATE"));
			e.setMeterType(meterData.optString("METER_TYPE"));
			e.setMyKey(new CustomKey(meterData.optString("METER_NUMBER"), meterData.optString("READING_MONTH")));
			e.setNoOfPowerFailurs(meterData.optString("NO_OF_POWER_FAILURS"));
			e.setPf3Phase(meterData.optString("PF_3_PHASE"));
			e.setPowerFacor1(meterData.optString("POWER_FACOR_1"));
			e.setPowerFacor2(meterData.optString("POWER_FACOR_2"));
			e.setPowerFacor3(meterData.optString("POWER_FACOR_3"));
			e.setPowerFailDuration(meterData.optString("POWER_FAIL_DURATION"));
			e.setSignedActivePower(meterData.optString("SIGNED_ACTIVE_POWER"));
			e.setSignedReactivePower(meterData.optString("SIGNED_REACTIVE_POWER"));
			e.setTakenTime(meterData.optString("TAKEN_TIME")); 
			e.setVoltage1(meterData.optString("VOLTAGE_1"));
			e.setVoltage2(meterData.optString("VOLTAGE_2"));
			e.setVoltage3(meterData.optString("VOLTAGE_3"));
			e.setYearOfManufacture(meterData.optString("YEAR_OF_MANUFACTURE"));
			e.setLoadSurveyData(meterData.optString("LOAD_SURVEY_DATA"));
			e.setEventHistory(meterData.optString("EVENT_HISTORY"));		
			e.setColumnStructure(meterData.optString("COLUMN_STRUCTURE"));
			e.setBillingHistory(meterData.optString("BILLING_HISTORY"));
				
			if(amrMeterData.update(e) instanceof AmrMeterData){
				System.out.println("Updated Meter Data==============");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
		
		//AUTO UPDATE
		 @RequestMapping(value = "/apk1/downloadApkAmr", method = {RequestMethod.POST, RequestMethod.GET})
		 public  @ResponseBody 
		 void getApkFile(HttpServletRequest request , HttpServletResponse response)  {
		   try{
			   System.out.println("BEFORE DOWNLOAD APK");
			   String maxVersion = versionApkService.findApkMaxVersionAmr();
			   String filePathToBeServed = versionApkService.FindApkDetailsAmr(maxVersion);
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
		                     response.setHeader("Content-Disposition", "attachment; filename="+"MIP_J3.0.apk");
		                     IOUtils.copy(inputStream, response.getOutputStream());
		                     response.flushBuffer();

		         }catch(Exception e)
		   {
		    e.printStackTrace();
		         }
		 }
		 
		 @RequestMapping(value="/apk/getapkversionAmr",method={RequestMethod.POST,RequestMethod.GET})
			public @ResponseBody String getApkversion() 
			{
			 System.out.println("GETTING APK VERSION");
				return versionApkService.findApkMaxVersionAmr();
			}

}
