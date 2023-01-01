package com.bcits.mdas.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.AllowedDeviceVersionsEntity;
import com.bcits.mdas.entity.OnAirVersionUpdationEntity;
import com.bcits.mdas.service.AllowedDeviceVersionService;
import com.bcits.mdas.service.FeederMRDeviceService;
import com.bcits.mdas.service.OnAirVersionUpdationService;
import com.bcits.mdas.utility.BCITSLogger;

@Controller
public class ApkUpdaterController {
	@Autowired
	AllowedDeviceVersionService allowedDeviceVersionService;
	
	@Autowired
	FeederMRDeviceService mrDeviceService;
	
	@Autowired
	private OnAirVersionUpdationService onAirVersionUpdationService;
	
	//private static final Log logger = LogFactory.getLog(DTCMasterController.class);
	/**
	 * @category GET LATEST VERSION 
	 * @return version
	 * @throws JSONException
	 */
	@RequestMapping(value ="/apk/getapkversion", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String getVersion_() throws JSONException {
		System.out.println("inside getApkversion");
	String version = null;
		try {
			List<OnAirVersionUpdationEntity> entities = onAirVersionUpdationService	.getlatestVersion();
			version = entities.get(0).getApkVersion();
		} catch (Exception exception) {
			//logger.info("Error while saving JSON object to DB");
			exception.printStackTrace();
		}
		return version;
	}
	

	/**
	 * @category DOWNLOAD APK FILE 
	 * @param request
	 * @param response
	 * @return APK FILE
	 */
	@RequestMapping(value ="/apk/downloadApk", method = {RequestMethod.POST, RequestMethod.GET})
	public  @ResponseBody 
	void getApkFile(HttpServletRequest request , HttpServletResponse response)  {
		//logger.info("in getApkFile...........");
		 try{
			 
			 
			 List<OnAirVersionUpdationEntity> entities = onAirVersionUpdationService.getlatestVersion();
			// String filePathToBeServed = "D:\\JVVNLAPKFILES\\JVVNL_V3.44.apk";
			final String FILE_PATH =   entities.get(0).getApkPath()+entities.get(0).getApkName();
			
			//final String FILE_PATH =   entities.get(0).getApkPath()+entities.get(0).getApkName()+"_"+entities.get(0).getApkVersion()+".apk";
			
			System.out.println("FILE_PATH " +FILE_PATH);
			
	            String filePathToBeServed = FILE_PATH;
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
	                    response.setHeader("Content-Disposition", "attachment; filename="+entities.get(0).getApkName()); 
	                    IOUtils.copy(inputStream, response.getOutputStream());
	                      response.flushBuffer();
	                      

	        }catch(Exception e){
	        	//logger.info("--------------- Reading DTC Details  ---------------------");
	        	//logger.info("Request could not be completed at this moment. Please try again.");
				e.printStackTrace();
	        }
	}
	
	@RequestMapping(value = "logingroup/validate_for_group/{devciedate}/{devicetime}/{apk}/{imei_no}", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Object deviceValidategroup(@PathVariable("devciedate") String devciedate , @PathVariable("devicetime") String devicetime , @PathVariable("apk") String apk
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
}
