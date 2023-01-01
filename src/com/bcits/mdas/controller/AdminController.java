package com.bcits.mdas.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.UserMDAS;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.XmlUploadStatusService;
/*import com.bcits.mdas.utility.Encryption;*/
import com.bcits.mdas.utility.FilterUnit;
/*import com.bcits.mdas.service.MRDeviceAllocationService;
import com.bcits.mdas.service.MRDeviceService;
import com.bcits.mdas.service.SiteLocationService;
import com.bcits.mdas.service.UserLogsService;
import com.bcits.mdas.service.UserService;
import com.bcits.mdas.service.XmlUploadStatusService;*/
/*import com.bcits.mdas.utility.Link;*/

@Controller
public class AdminController {
	
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager entityManager;
	
/*	@Autowired
	private UserService userService;

	@Autowired
	private SiteLocationService siteLocationService;

	@Autowired
	private Encryption encryption;
	
	@Autowired
	private UserLogsService userLogsService;
	
	@Autowired
	private MRDeviceService mrDeviceService;
	
	@Autowired
	private MRDeviceAllocationService mrDeviceAllocationService;
	
	@Autowired
	private AmrInstantaneousService amrInstantaneousService;
	*/
	@Autowired
	private FeederMasterService feederService;
	
	@Autowired(required=false)
	private XmlUploadStatusService xmlUploadStatusService;
	
/*	@Link(label="Home", family="UserController", parent = "" )
	@RequestMapping(value="/dashBoardMDAS",method=RequestMethod.GET)
	public String fmsDashBoard(ModelMap model, HttpServletRequest request)
	{
		amrInstantaneousService.getModemsStats(model,request);
		model.addAttribute("results", "notDisplay");
		return "fmsDashBoardMDAS";
	} 
	
	@RequestMapping(value="/showTotalModemsMDAS",method=RequestMethod.GET)
	public String showTotalModems(ModelMap model, HttpServletRequest request)
	{
		try
		{
			amrInstantaneousService.getModemsStats(model,request);
			List<?> list=amrInstantaneousService.getTotalModems(model,request);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "fmsDashBoardMDAS";
	} 
	
	@RequestMapping(value="/showWorkingModemsMDAS",method=RequestMethod.GET)
	public String showWorkingsModems(ModelMap model, HttpServletRequest request)
	{
		try
		{
			amrInstantaneousService.getModemsStats(model,request);
			List<?> list=amrInstantaneousService.getWorkingsModems(model,request);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		return "fmsDashBoardMDAS";
	} 
	
	@RequestMapping(value="/showNotWorkingModemsMDAS",method=RequestMethod.GET)
	public String showNotWorkingsModems(ModelMap model, HttpServletRequest request)
	{
		try
		{
			amrInstantaneousService.getModemsStats(model,request);
			List<?> list=amrInstantaneousService.getNotWorkingsModems(model,request);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "fmsDashBoardMDAS";
	}
	
	@RequestMapping(value = "/getProfilePicMDAS", method = RequestMethod.GET)
	public void getprofilePic(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		BCITSLogger.logger.info("In profile pic ");
		userService.displayProfilePic(request, response);
	}
	
	@RequestMapping(value = "/userAccessManagmentMDAS", method = RequestMethod.GET)
	public String userAccessManagment(@ModelAttribute("users") User user,ModelMap model, BindingResult bindingResult,HttpServletRequest request) 
	{
		BCITSLogger.logger.info("In User DashBoard-User Access Mangement");
		userService.getRecentPath(request.getRequestURI().substring(4,request.getRequestURI().length()), request);
		userService.getUserData(model, request);
		model.addAttribute("results", "notDisplay");

		return "userAccessManagment";
	}
	
	@RequestMapping(value = "/getProfileImageMDAS/getImage/{userId}", method = RequestMethod.GET)
	public void getUseImage(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @PathVariable int userId)
			throws IOException {
		BCITSLogger.logger.info("In profile photo for user Accessment");
		userService.getImage(request, response, userId);

	}
	
	@RequestMapping(value = "/updateUserMDAS", method = RequestMethod.POST)
	public String updateUserData(@ModelAttribute("users") User user,
			BindingResult bindingResult, ModelMap model,
			HttpServletRequest request) {
		userService.updateuserDetails(request, user, new Date(), model);
		userService.getUserData(model, request);
		model.put("users", new User());
		return "userAccessManagmentMDAS";
	}
	
	@RequestMapping(value = "/editUserMDAS/{operation}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object editUser(@PathVariable int operation, HttpServletResponse response,HttpServletRequest request, ModelMap model)throws JsonParseException, JsonMappingException, IOException 
	{
		User user = userService.find(operation);
		user.setUserPassword(encryption.decrypt(user.getUserPassword()));
		return user;
	}
	
	@RequestMapping(value = "/mrDeviceAccessManagmentMDAS", method = RequestMethod.GET)
	public String mrDeviceAccessManagment(@ModelAttribute("mrDevices") MRDeviceEntity mrDevices,HttpServletRequest request, ModelMap model,BindingResult bindingResult) 
	{
		StringBuffer a=request.getRequestURL();
		String moduleActivity=a.substring(34);
		BCITSLogger.logger.info("method name====>"+a+"====="+moduleActivity);
		
		model.put("mrdList", mrDeviceService.findAllMRDevices(request));
		model.put("makeConstrts", mrDeviceService.getCheckConstraints("devicemaster", "make_check", request));
		model.put("sdoCodes", siteLocationService.getAllSiteCodes());
		model.put("results", "notDisplay");
		
		return "mrDeviceAccessManagment";
	}
	
	@RequestMapping(value = "/checkDeviceAvailabilityMDAS", method = RequestMethod.POST)
	public @ResponseBody
	Boolean checkDeviceAvailability(@RequestParam("deviceid") String deviceid,
			@ModelAttribute("mrDevices") MRDeviceEntity mrDevices,
			HttpServletRequest request, ModelMap model) {
		int pk = mrDeviceService.findByPk(deviceid);
		BCITSLogger.logger.info("==============>COMINFG pk----------" + pk);
		if (mrDeviceService.find(pk) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping(value = "/editMRDeviceMDAS/{id}", method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody MRDeviceEntity editMRDevice(@ModelAttribute("mrDevices") MRDeviceEntity mrDevices,@PathVariable int id, HttpServletRequest request, ModelMap model,BindingResult bindingResult) 
	{
		System.out.println("ID dev " + id);
		MRDeviceEntity dev = mrDeviceService.find(id);
		// System.out.println("obj=== "+dev.getApprovalStatus());
		return dev;
	}
	
	@RequestMapping(value = "/updateMRDeviceMDAS", method = RequestMethod.POST)
	public String updateMRDevice(@ModelAttribute("mrDevices") MRDeviceEntity mrDevices,HttpServletRequest request, ModelMap model,BindingResult bindingResult) 
	{
		String numbers = "(.*[0-9].*)";
		String splChars = "(.*[,~,!,@,#,^,&,*,(,),-,_,+,{,},|,;,:,?].*$)";
		boolean valid = true;
		if (request.getParameter("sdoCode").equalsIgnoreCase("0"))
        {
     	   	model.put("results","Please select SdoCode");
            valid = false;
            model.put("mrdList", mrDeviceService.findAllMRDevices(request));
			model.put("makeConstrts", mrDeviceService.getCheckConstraints(
					"devicemaster", "make_check", request));
			model.put("mrDevices", new MRDeviceEntity());
			model.put("sdoCodes", siteLocationService.getAllSiteCodes());
        }
		else if (request.getParameter("simslot").equalsIgnoreCase("0"))
        {
     	   	model.put("results","Please select Sim-Slot");
            valid = false;
            model.put("mrdList", mrDeviceService.findAllMRDevices(request));
			model.put("makeConstrts", mrDeviceService.getCheckConstraints(
					"devicemaster", "make_check", request));
			model.put("mrDevices", new MRDeviceEntity());
			model.put("sdoCodes", siteLocationService.getAllSiteCodes());
        }
		else if (request.getParameter("deviceid").equalsIgnoreCase(""))
        {
			model.put("results","Please enter Imei No.");
            valid = false;
            model.put("mrdList", mrDeviceService.findAllMRDevices(request));
			model.put("makeConstrts", mrDeviceService.getCheckConstraints(
					"devicemaster", "make_check", request));
			model.put("mrDevices", new MRDeviceEntity());
			model.put("sdoCodes", siteLocationService.getAllSiteCodes());
        }
		else if (request.getParameter("deviceid").matches(splChars))
		{
			model.put("results","Please enter Valid Imei No.");
			valid = false;
			model.put("mrdList", mrDeviceService.findAllMRDevices(request));
			model.put("makeConstrts", mrDeviceService.getCheckConstraints(
					"devicemaster", "make_check", request));
			model.put("mrDevices", new MRDeviceEntity());
			model.put("sdoCodes", siteLocationService.getAllSiteCodes());
		}
		else if (!request.getParameter("deviceid").matches(numbers ))
        {
     	   	model.put("results","Imei No should be in Numeric.");
            valid = false;
            model.put("mrdList", mrDeviceService.findAllMRDevices(request));
			model.put("makeConstrts", mrDeviceService.getCheckConstraints(
					"devicemaster", "make_check", request));
			model.put("mrDevices", new MRDeviceEntity());
			model.put("sdoCodes", siteLocationService.getAllSiteCodes());
        }
		else if (request.getParameter("deviceid").length()<15)
        {
     	   	model.put("results","Imei No should be 15 digits");
            valid = false;
            model.put("mrdList", mrDeviceService.findAllMRDevices(request));
			model.put("makeConstrts", mrDeviceService.getCheckConstraints(
					"devicemaster", "make_check", request));
			model.put("mrDevices", new MRDeviceEntity());
			model.put("sdoCodes", siteLocationService.getAllSiteCodes());
        }
		else if (request.getParameter("make").equalsIgnoreCase("0"))
        {
     	   	model.put("results","Please select Model Type");
            valid = false;
            model.put("mrdList", mrDeviceService.findAllMRDevices(request));
			model.put("makeConstrts", mrDeviceService.getCheckConstraints(
					"devicemaster", "make_check", request));
			model.put("mrDevices", new MRDeviceEntity());
			model.put("sdoCodes", siteLocationService.getAllSiteCodes());
        }
		else if (valid)
		{
				try {
				mrDevices.setUsername((String) request.getSession().getAttribute("username"));
				mrDevices.setTimestamp(new Timestamp(new Date().getTime()));
				
				
				MRDeviceEntity mrDev = mrDeviceService.find(mrDevices.getId());
				if (mrDev != null) 
				{
					if (!mrDevices.getDeviceid().equalsIgnoreCase(mrDev.getDeviceid())) 
					{
						String value = mrDeviceService.findByDeviceId(mrDevices.getDeviceid());
						if (value.equalsIgnoreCase("DataExists"))
						{
							model.put("results", "Device id " + mrDevices.getDeviceid()+ " already registered");
						} 
						else 
						{
							mrDevices.setApprovalStatus("NOT APPROVED");
							mrDevices.setGprsSimNum("0");
							mrDeviceService.update(mrDevices);
							model.put("results", "Device updated Successfully");
						}
						model.put("mrdList", mrDeviceService.findAllMRDevices(request));
						model.put("makeConstrts", mrDeviceService.getCheckConstraints(
								"devicemaster", "make_check", request));
						model.put("mrDevices", new MRDeviceEntity());
						model.put("sdoCodes", siteLocationService.getAllSiteCodes());
							return "mrDeviceAccessManagmentMDAS";
					}
				}
				
					BCITSLogger.logger.info("================>" + mrDevices.getId());
					if (mrDevices.getId() == 0) {
						mrDevices.setAllocatedflag("NOT ALLOCATED");
						mrDevices.setApprovalStatus("NOT APPROVED");
						mrDevices.setGprsSimNum("0");
						mrDevices.setStatus("WORKING");
						mrDevices.setDeviceType("MOBILE");
						String value = mrDeviceService.findByDeviceId(mrDevices.getDeviceid());
						if (value.equalsIgnoreCase("DataExists"))
						{
							model.put("results", "Device id " + mrDevices.getDeviceid()
									+ " already registered");
						} else {
							mrDeviceService.update(mrDevices);
							model.put("results", "Device saved Successfully");
						}
						
					} else {
						mrDeviceService.update(mrDevices);
						model.put("results", "Device updated Successfully");
					}
					model.put("mrdList", mrDeviceService.findAllMRDevices(request));
					model.put("makeConstrts", mrDeviceService.getCheckConstraints(
							"devicemaster", "make_check", request));
					model.put("mrDevices", new MRDeviceEntity());
					model.put("sdoCodes", siteLocationService.getAllSiteCodes());
				} catch (Exception e) {
					e.printStackTrace();
					model.put("results", "error while processing");
				}
		}
		return "mrDeviceAccessManagmentMDAS";
	}
	
	@RequestMapping(value = "/approveDeviceMDAS", method = RequestMethod.GET)
	public String approveDevice(@ModelAttribute("mrDevices") MRDeviceEntity mrDevices,HttpServletRequest request, @RequestParam String deviceid,ModelMap model, BindingResult bindingResult) 
	{
		mrDeviceService.approveDevice(deviceid, request, model);
		model.put("sdoCodes", siteLocationService.getAllSiteCodes());
		return "mrDeviceAccessManagmentMDAS";
		
	}
	
	@RequestMapping(value = "/mrDeviceAllocationManagmentMDAS", method = {RequestMethod.GET, RequestMethod.POST })
	public String mrDeviceAllocationManagment(@ModelAttribute("mrDevAllocs") MRDeviceAllocationEntity mrDevAllocs,HttpServletRequest request, ModelMap model,BindingResult bindingResult) 
	{
		StringBuffer a=request.getRequestURL();
		String moduleActivity=a.substring(34);
		BCITSLogger.logger.info("method name====>"+a+"===moduleActivity=="+moduleActivity);
		
		model.put("mrdaList", mrDeviceAllocationService.findAllMRDeviceAllocations(request, model));
		model.put("sdoCodes", siteLocationService.getAllSiteCodes());
		return "mrDeviceAllocationManagmentMDAS";
	}

	@RequestMapping(value = "/allocateNewDeviceMDAS", method = { RequestMethod.POST })
	public String allocateNewDevice(@ModelAttribute("mrDevAllocs") MRDeviceAllocationEntity mrDevAllocs,HttpServletRequest request, ModelMap model) 
	{
		mrDevAllocs.setUsername((String) request.getSession().getAttribute("username"));
		mrDevAllocs.setTimestamp(new Timestamp(new Date().getTime()));
		String mrcode = mrDevAllocs.getMrCode().trim().replaceAll("\\s", "");
		mrDevAllocs.setMrCode(mrcode);
		try {
			List<MRDeviceAllocationEntity> list = mrDeviceAllocationService.findByDeviceId(mrDevAllocs.getDeviceid());
			if (list.size() > 0) {
				model.put("results", "Device Id " + mrDevAllocs.getDeviceid()+ " already Allocated");
				model.put("mrDevAllocs", new MRDeviceAllocationEntity());
				model.put("sdoCodes", siteLocationService.getAllSiteCodes());
				model.put("mrdaList", mrDeviceAllocationService.findAllMRDeviceAllocations(request, model));
				return "mrDeviceAllocationManagmentMDAS";
			}
			mrDeviceAllocationService.save(mrDevAllocs);
			int updatestatus = mrDeviceService.updateDeviceSdocode(	mrDevAllocs.getDeviceid(), mrDevAllocs.getSdoCode(),"ALLOCATED");
			if (updatestatus > 0) {
				model.put("results", "Device Allocated Successfully");
			} else {
				model.put("results", "Device not Allocated");
			}

		} catch (Exception e) {
			model.put("results", "Device not Allocated");
			e.printStackTrace();
		}
		model.put("mrdaList", mrDeviceAllocationService.findAllMRDeviceAllocations(request, model));
		model.put("sdoCodes", siteLocationService.getAllSiteCodes());
		model.put("mrDevAllocs", new MRDeviceAllocationEntity());
		return "mrDeviceAllocationManagmentMDAS";
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/deAllocateDeviceMDAS", method = {RequestMethod.GET,RequestMethod.POST })
	public String deAllocateDevice(@ModelAttribute("mrDevAllocs") MRDeviceAllocationEntity mrDevAllocs,@RequestParam String deviceidPk, HttpServletRequest request,ModelMap model) 
	{
		try {
			BCITSLogger.logger.info("=======================>iterable_element"+ deviceidPk);
			int val = mrDeviceAllocationService.deleteDevice(deviceidPk);
			int val1 = mrDeviceService.updateDeviceMaster(deviceidPk + "");
			if (val > 0 && val1 > 0) {
				model.put("results", "Device DeAllocated successfully");
			} else {
				model.put("results", "Device not DeAllocated");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		model.put("mrdaList", mrDeviceAllocationService.findAllMRDeviceAllocations(request, model));
		model.put("sdoCodes", siteLocationService.getAllSiteCodes());
		return "mrDeviceAllocationManagmentMDAS";
	}*/

	/*@RequestMapping(value = "/checkAllocatedMDAS/{operatorDevice}/{id}", method = RequestMethod.POST)
	public @ResponseBody Boolean checkAllocated(@PathVariable String operatorDevice,@PathVariable String id, HttpServletRequest request, ModelMap model) 
	{
		if (operatorDevice.equals("device"))// check for device allocated
		{
			return mrDeviceAllocationService.checkAllocated(request, "%", id);
		}
		if (operatorDevice.equals("operator"))// check for mr allocated
		{
			return mrDeviceAllocationService.checkAllocated(request, id, "%");
		}
		return true;
	}*/
	
	@RequestMapping(value="/downloadXMLDataMDAS", method = {RequestMethod.GET,RequestMethod.POST})
	public String downloadXMLData(UserMDAS user,ModelMap model, HttpServletRequest request,HttpSession session)
	{
		String userName=(String) session.getAttribute("username");
	//	System.out.println("username : "+session.getAttribute("username"));
	//	System.out.println("===========downloadXMLData info=============="+userName);
		/*List<FeederMasterEntity> zoneList=feederService.getDistinctZoneByLogin(userName,model,request);*/
		List<FeederMasterEntity> zoneList=feederService.getDistinctZone();
		String btnSLD=request.getParameter("viewModem");
		String zone=request.getParameter("zone"),circle=request.getParameter("circle"),division=request.getParameter("division"),
				subdiv=request.getParameter("sdoCode"),fDate=request.getParameter("frmDate");;
		model.put("zoneList", zoneList);
		model.put("circleList", feederService.getCircleByZone(zone,model));
		model.put("divisionList",feederService.getDivisionByCircle(zone,circle,model));
		model.put("subdivList", feederService.getSubdivByDivisionByCircle(zone,circle,division,model));
		
		model.addAttribute("zone",zone);
		model.addAttribute("circle",circle);
		model.addAttribute("division",division);
		model.addAttribute("subdiv",subdiv);
		model.addAttribute("fDate",fDate);
		model.addAttribute("results", "notDisplay");
		return "downloadXMLDataMDAS";
	}
	
	private String getFolderPath(String fileDate){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date date =dateFormat.parse(fileDate);
			String month=FilterUnit.ftpSourceFolder+"/"+new SimpleDateFormat("yyyyMM").format(date);
			String day=month+"/"+new SimpleDateFormat("yyyyMMdd").format(date);
			if(FilterUnit.folderExists(month)){
				if(FilterUnit.folderExists(day)){
					return day;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/downlodingFileMDAS/{zn}/{cir}/{div}/{subdiv}/{fDate}",method={RequestMethod.GET})
	public @ResponseBody List<String> downlodingFile(@PathVariable String zn,@PathVariable String cir,@PathVariable String div,@PathVariable String subdiv,@PathVariable String fDate,HttpSession session,HttpServletResponse response,ModelMap model) throws FileNotFoundException, ZipException 
	{
		//List<String> xmlList=xmlUploadStatusService.getXMLFilesByDate(zn,cir,div,subdiv,fDate,model);
		List<String> xmlList=xmlUploadStatusService.getMtrNoForDownload(zn,cir,div,subdiv,fDate);
		System.out.println("In downlodingFile==>"+zn+"==>"+cir+"==>"+div+"==>"+subdiv+"==>"+fDate+"==list size==>"+xmlList.size());
		try {
				String folderPAth=getFolderPath(fDate);
				String filePath=getFolderPath(fDate)+"/"+fDate+"_"+zn+".zip";
				
				File f = new File(filePath);
				if(f.exists() && !f.isDirectory()) { 
				    f.delete();
				}
	            ZipFile zipFile = new ZipFile(filePath);
	            ArrayList<File> filesToAdd = new ArrayList<File>();
	            //filesToAdd.add(new File("F:\\AMR_BSMART\\201711\\20171108\\HRT64853_2017-11-08.xml"));
	            //filesToAdd.add(new File("F:\\AMR_BSMART\\201711\\20171108\\HRT64902_2017-11-08.xml"));
	            //String ab="F:;AMR_BSMART;METER_FILES;HRT64853_2017-11-08.xml";
	            for(String s:xmlList){
	            	
	            	String path=folderPAth+"/"+s+"_"+fDate+".xml";
	            	System.out.println(path);
	            	File xf = new File(path);
	            	if(xf.exists()) {
	            		filesToAdd.add(new File(path));
	            		System.out.println("filesToAdd indiv==>"+filesToAdd);
	            	}else {
	            		System.out.println("filenotfound");
	            	}
	            }
	            
	            //System.out.println("filesToAdd==>"+filesToAdd);
	            ZipParameters parameters = new ZipParameters();
	            zipFile.addFiles(filesToAdd, parameters);
	            
	            File file = new File(filePath);
				 InputStream inputStream = new FileInputStream(file);
					System.out.println(file.getName());
					try {
						response.setHeader("Content-Disposition", "inline;filename=\""+zn+"_" + fDate + ".zip\"");
						response.setContentType("application/zip");
						OutputStream out = response.getOutputStream();
						IOUtils.copy(inputStream, out);
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
        }
        catch (ZipException e)
        {
            e.printStackTrace();
        }	
		return null;
	}
	
	@RequestMapping(value = "/checkMtrModemComm", method = RequestMethod.GET)
	public String checkMtrModemComm(ModelMap model,HttpServletRequest request) 
	{
		return "checkComm";
	}
	
	@RequestMapping(value = "/checkMtrModemCommMDAS", method = RequestMethod.GET)
	public String checkMtrModemCommlatest(ModelMap model,HttpServletRequest request) 
	{
		
		return "checkMtrModemCommMDASL";
	}
	@RequestMapping(value="/getCommunicationForInstallationMDAS",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getCommunicationForInstallation(HttpServletRequest request,ModelMap model)
	{
		String type=request.getParameter("type");
		String imei=request.getParameter("imeiVal");
		String qry="";
		if("IMEI".equals(type)) {
			qry="SELECT meter_number,imei,date,last_communication FROM meter_data.modem_communication WHERE imei='"+imei+"' ORDER BY last_communication DESC";
		} else if("METER".equals(type)) {
			qry="SELECT meter_number,imei,date,last_communication FROM meter_data.modem_communication WHERE meter_number='"+imei+"' ORDER BY last_communication DESC";
		} else {
			return null;
		}
		List<?> mlist=entityManager.createNativeQuery(qry).getResultList();
		return mlist;
	}
	
	@RequestMapping(value="/getAllCommunicationForInstallationMDAS",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getAllCommunicationForInstallation(HttpServletRequest request,ModelMap model)
	{
		String qry="";
		qry="SELECT meter_number, imei, date, last_communication  FROM meter_data.modem_communication WHERE date=CURRENT_DATE ORDER BY last_communication DESC;";
		List<?> mlist=entityManager.createNativeQuery(qry).getResultList();
		return mlist;
	}
	
}
