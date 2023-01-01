package com.bcits.serviceImpl;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bcits.entity.CDFData;
import com.bcits.entity.D1_data;
import com.bcits.entity.D2Data;
import com.bcits.entity.D3Data;
import com.bcits.entity.D4CdfData;
import com.bcits.entity.D4Data;
import com.bcits.entity.D5Data;
import com.bcits.entity.D9Data;
import com.bcits.entity.JvvnlUsersEntity;
import com.bcits.entity.User;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.utility.FilterUnit;
import com.bcits.service.CdfDataService;
import com.bcits.service.D1DataService;
import com.bcits.service.D2DataService;
import com.bcits.service.D3DataService;
import com.bcits.service.D4DtataService;
import com.bcits.service.D4LoadDataService;
import com.bcits.service.D5DataService;
import com.bcits.service.D5SnashotService;
import com.bcits.service.D9DataService;
import com.bcits.service.MasterService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.UserAccessTypeService;
import com.bcits.service.UserService;
import com.bcits.service.XmlImportService;
import com.bcits.utility.ParamCodeValidator;

@Repository
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService
{
	@Autowired
	private CdfDataService cdfDataService;
	
	@Autowired
	private D1DataService d1DataService;
	
	@Autowired
	private D2DataService d2DataService;
	
	@Autowired
	private D3DataService d3DataService;
	
	@Autowired
	private D4DtataService d4DtataService;
	
	@Autowired
	private D5DataService d5DataService;
	
	@Autowired
	private D5SnashotService d5SnashotService;
	
	@Autowired
	private D9DataService d9DataService;
	
	@Autowired
	private D4LoadDataService d4LoadDataService;
	
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private MeterMasterService meterMasterService;
	
	@Autowired
	private XmlImportService xmlImportService;
	
	@Autowired
	private UserAccessTypeService userAccessTypeService;
	
	@Autowired
	AmiLocationService als;

	public static String Status="CMRI";
	
	//@Value("project.name")
	//public static String projectName;
	//@Autowired
	//private FilterUnit filter;
	

	/* (non-Javadoc)
	 * @see com.bcits.serviceImpl.UserService#findAll(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<User> findAll(String userMailId,String userPassword)
	{
	
		return postgresMdas.createNamedQuery("User.findAll").setParameter("username", userMailId).setParameter("userPassword", userPassword).getResultList();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<JvvnlUsersEntity> findAllJvvnlUsers(String userMailId, String userPassword)
	{
		return postgresMdas.createNamedQuery("JvvnlUsersEntity.findAllJvvnlUsers").setParameter("username", userMailId).setParameter("userPassword", userPassword).getResultList();
	}
	
	/*@Transactional(propagation=Propagation.SUPPORTS)
	public String afterLogin(String userMailId,String userPassword,HttpSession session,ModelMap model)
	{
	    	String pageName="userPage1";
	    
	    SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		List<User> list = findAll(userMailId, userPassword);
		if(list.size() > 0)
		{
			session.setAttribute("userType", list.get(0).getUserType());
			session.setAttribute("username",list.get(0).getUsername());
			session.setAttribute("designation", list.get(0).getDesignation());
		
			if((list.get(0).getUserType()).equalsIgnoreCase("ADMIN"))
			{
				pageName= "adminPage";
			}
		    //long totalConsumers = masterService.FindTotalConsumerCount();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			String billmonth = sdfBillDate.format(cal.getTime());
			cal.add(Calendar.MONTH, 1);
			String billmonth1 = sdfBillDate.format(cal.getTime());
			//List listTamper = d9DataService.tamperEventData(billmonth1, model);
			//model.addAttribute("total", totalConsumers);
			String accessRights = userAccessTypeService.getSideMenu(list.get(0).getDesignation());
			//model.put("d5DataList",d5DataService.showEventDetails(billmonth1,model));
			//masterService.FindMakewiseConsumerCount(billmonth1, model);
			session.setAttribute("accessRights", accessRights);
		}
		else{
			model.addAttribute("msg", "Invalid Credentials...");
			pageName = "login";
		}
		return pageName; 
	}
	*/
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public String afterLogin(String userMailId,String userPassword,HttpSession session,ModelMap model)
	{

	    //String pageName="home";
		String pageName="redirect:/homeNew";

	    

	    SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		List<User> list = findAll(userMailId, userPassword);
		if(list.size() > 0)
		{
			session.setAttribute("userType", list.get(0).getUserType());
			session.setAttribute("username",list.get(0).getUsername());
			session.setAttribute("designation", list.get(0).getDesignation());
			session.setAttribute("name", list.get(0).getName());
		//System.out.println("project name====="+FilterUnit.projectName);
			
			session.setAttribute("officeType",  list.get(0).getOfficeType());
			session.setAttribute("officeCode",  list.get(0).getOffice());
			session.setAttribute("contactNo", list.get(0).getMobileNo());
			session.setAttribute("email", list.get(0).getEmailId());
		
			session.setAttribute("projectName",FilterUnit.projectName);
			
			session.setAttribute("editRights", list.get(0).getEditAccess());
			
			if(list.get(0).getOfficeType().equalsIgnoreCase("circle")){
				
				String circleName=als.circleName(list.get(0).getOffice());
				System.err.println("After circle login :- "+circleName);
				String newRegionName =als.getRegionNameByCircle(list.get(0).getOffice());
				List<?> townList = als.getTownByCircleCode(list.get(0).getOffice());
				session.setAttribute("officeType",  "circle");
				session.setAttribute("officeName", circleName+"");
				session.setAttribute("newRegionName", newRegionName);
				session.setAttribute("townList", townList);
				pageName="redirect:/dashBoard2MDAS?type=circle&value="+circleName+"";
				
			}			
			else if(list.get(0).getOfficeType().equalsIgnoreCase("corporate")){
				List<?>  allRegions = als.getAllRegions();
				session.setAttribute("officeName", "Level");
				//session.setAttribute("officeType",  list.get(0).getOfficeType());
				session.setAttribute("allRegions", allRegions);
				pageName="redirect:/dashBoard2MDAS?type=corporate&value=Level";
			}
			else if(list.get(0).getOfficeType().equalsIgnoreCase("region")){
				List<?> circleList=als.getCircleListByZoneCode(list.get(0).getOffice());
				String newRegionName =als.getRegionNameByRegionCode(list.get(0).getOffice());
				session.setAttribute("newRegionName", newRegionName);
				session.setAttribute("officeName", newRegionName);
				session.setAttribute("circleList", circleList);	
				pageName="redirect:/dashBoard2MDAS?type=region&value="+newRegionName+"";
				//session.setAttribute("officeType",  list.get(0).getOfficeType());
				}
			
			
			
			
			/*else if(list.get(0).getOfficeType().equalsIgnoreCase("division")){
				String divisonName=als.divisionName(list.get(0).getOffice());
				session.setAttribute("officeName", divisonName);
			}
			else if(list.get(0).getOfficeType().equalsIgnoreCase("subdivision")){
				String subdivisonName=als.subDivisionName(list.get(0).getOffice());
				session.setAttribute("officeName", subdivisonName);			}
			*/
			
			
			/*if((list.get(0).getUserType()).equalsIgnoreCase("ADMIN"))
			{
				pageName= "adminPage";
			}*/
				          //long totalConsumers = masterService.FindTotalConsumerCount();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			String billmonth = sdfBillDate.format(cal.getTime());
			/*List list1 = meterMasterService.getMeterMakeWiseData(Integer.parseInt(billmonth));*/
			cal.add(Calendar.MONTH, 1);
			String billmonth1 = sdfBillDate.format(cal.getTime());
			//List listTamper = d9DataService.tamperEventData(billmonth1, model);
			//model.addAttribute("meterMakeData", list1);
			//model.addAttribute("total", totalConsumers);
			try {
				String accessRights = userAccessTypeService.getSideMenu(list.get(0).getUserType());
				System.out.println("accessRights-->"+accessRights);
				session.setAttribute("accessRights", accessRights);
				String editRights = userAccessTypeService.getEditRights(list.get(0).getUserType());
				//String viewRights = userAccessTypeService.getViewRights(list.get(0).getUserType());
				
				/*session.setAttribute("viewRights", viewRights);*/
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//model.addAttribute("accessRights",accessRights);
			//model.put("d5DataList",d5DataService.showEventDetails(billmonth1,model));
			//masterService.FindMakewiseConsumerCount(billmonth1, model);
			//model.put("accessRights", accessRights);
			//pageName="redirect:/dashBoard2MDAS?type=Corporate&value=Level";
		}
		else{
			model.addAttribute("msg", "Invalid Credentials...");
			pageName = "login";
		}
		return pageName; 
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public String jvvnlUserLogin(String userMailId, String userPassword,
			HttpSession session, ModelMap model) {
		System.out.println("come to jvvnl users login method-->"+userMailId+".."+userPassword);
		String pageName="home";
		
		SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		List<JvvnlUsersEntity> list = findAllJvvnlUsers(userMailId, userPassword);
		if(list.size() > 0)
		{
			session.setAttribute("userType","jvvnluser");
			session.setAttribute("username",list.get(0).getUser_login_name());
			session.setAttribute("designation", "JVVNLUSERS");
		
			/*if((list.get(0).getUserType()).equalsIgnoreCase("ADMIN"))
			{
				pageName= "adminPage";
			}*/
				          //long totalConsumers = masterService.FindTotalConsumerCount();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			String billmonth = sdfBillDate.format(cal.getTime());
			/*List list1 = meterMasterService.getMeterMakeWiseData(Integer.parseInt(billmonth));*/
			cal.add(Calendar.MONTH, 1);
			String billmonth1 = sdfBillDate.format(cal.getTime());
			//List listTamper = d9DataService.tamperEventData(billmonth1, model);
			//model.addAttribute("meterMakeData", list1);
			//model.addAttribute("total", totalConsumers);
			String accessRights = userAccessTypeService.getSideMenu("JVVNLUSERS");
			//model.addAttribute("accessRights",accessRights);
			//model.put("d5DataList",d5DataService.showEventDetails(billmonth1,model));
			//masterService.FindMakewiseConsumerCount(billmonth1, model);
			session.setAttribute("accessRights", accessRights);
		}
			else{
				model.addAttribute("msg", "Invalid Credentials...");
				pageName = "login";
			}
		return pageName;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public String parseTheFile(Document doc,String billmonthParam,ModelMap model,String unZipFIlePath,String filename)
	{
		System.out.println("enter to parse method service impl");
		
		
	 String   meterNumber = "";
	 String mainStatus = "";
	 int cdfId ;
	 String manufacturerCode = "",manufacturerName = "";
	 Double d2frequency=0.0;
	 int billmonth = Integer.parseInt(billmonthParam);
	 Date d = new Date();
	 
	 //FOR CHECKING 
	 Date meter_date=null;
	 Date device_reading_date=null;
	 String meterClass = "", meterType = "", kwhValue = "", kvahValue = "", kvaValue = "", kvaValueB6 = "", pfValue = "";
	//D2 variables
	 float rPhaseVal = 0, yPhaseVal = 0, bPhaseVal = 0;
	 float rPhaseLineVal = 0, yPhaseLineVal = 0, bPhaseLineVal = 0;
	 float rPhaseActiveVal = 0, yPhaseActiveVal = 0, bPhaseActiveVal = 0;
	 float rPhasePFVal = 0, yPhasePFVal = 0, bPhasePFVal = 0;
	 float avgPFVal = 0, activePowerVal = 0;
	 String phaseSequence = "";
	 
		 Double kwh=0.0;
		 Double kvah=0.0;
		 Double kva=0.0;
		 Double iRAngle=0.0;
		 Double iYAngle=0.0;
		 Double iBAngle=0.0;
		 Double iR=0.0;	
		 Double iY=0.0;
		 Double iB=0.0;
		 Double vR=0.0;
		 Double vY=0.0;
		 Double vB=0.0;	
		 Double pfR=0.0;
		 Double pfY=0.0;
		 Double pfB=0.0;
		 Double pfThreephase=0.0;
		 Double frequency=0.0;
		 Double kwhExp =0.0;
		 Double kwhImp=0.0;	
		 Double kvahExp=0.0;	
		 Double kvahImp=0.0;	
		 Double powerKw=0.0;	
		 Double kvar=0.0;	
		 Double kvarhLag=0.0;	
		 Double kvarhLead=0.0;	
		 Integer powerOffCount=0;	
		 Integer powerOffDuration=0;	
		 Integer mdResetCount=0;	
		 Integer programmingCount=0;	
		 Integer tamperCount=0;	
		 String mdResetDate="";	
		 Double mdKw=0.0;	
		 String dateMdKw="";	
		 Double mdKva=0.0;	
		 String dateMdKva="";
	 
	 

	//D5 Snapshot variables
	 float rPhaseVal1 = 0, yPhaseVal1 = 0, bPhaseVal1 = 0;
	 float rPhaseLineVal1 = 0, yPhaseLineVal1 = 0, bPhaseLineVal1 = 0;
	 float rPhaseActiveVal1 = 0, yPhaseActiveVal1 = 0, bPhaseActiveVal1 = 0;
	 float rPhasePFVal1 = 0, yPhasePFVal1 = 0, bPhasePFVal1 = 0;
	 float avgPFVal1 = 0, activePowerVal1 = 0;
	 String phaseSequence1 = "";
	 float d5_kwh = 0;
	 float d2_kwh=0;
	 
	 String kwhValue2 = "", kvahValue2 = "", kvahValue3 = "", kvaValue2 = "", kvaValue3 = "";
	 String d3_01_dateTime = "", d3_02_dateTime = "", d3_03_dateTime = "";
	 
	 //
	
	 double maxKva = 0, minKva = 0, sumKwh = 0, sumPf = 0,sumKva = 0;
	 double ctrn = 0, ctrd = 0, mf = 0, cd = 0;
	 double cd_20 = 0, cd_40 = 0, cd_60 = 0;
	 int pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0, dayProfileCount = 0, pfNoLoad=0, pfBlackOut = 0;
	 

	 int kva1_lt_cd20 = 0, kva1_lt_cd40 = 0, kva1_lt_cd60 = 0, kva1_gt_cd60 = 0;
	 int kva2_lt_cd20 = 0, kva2_lt_cd40 = 0, kva2_lt_cd60 = 0, kva2_gt_cd60 = 0;
	 int kva3_lt_cd20 = 0, kva3_lt_cd40 = 0, kva3_lt_cd60 = 0, kva3_gt_cd60 = 0;
	 int kva4_lt_cd20 = 0, kva4_lt_cd40 = 0, kva4_lt_cd60 = 0, kva4_gt_cd60 = 0;
	//for D3 dates
		
	 String d3_01_month = billmonth+"";
	 String d3_02_month = "";
	 String d3_03_month = "";
	 String d3_01_energy = "0", d3_02_energy = "0", d3_03_energy = "0";
	 String d1DateForCheckin = "";
	 Date d3BillingDate=null;
	 
	 Double sysPfBilling=0.0, kwhTz1 =0.0, kwhTz2 =0.0,kwhTz3 =0.0,kwhTz4 =0.0,kwhTz5 =0.0,kwhTz6 =0.0,kwhTz7 =0.0,kwhTz8 =0.0,
			 d3_kwh=0.0,d3_kvah =0.0,d3_kvarhLag =0.0,d3_kvarhLead =0.0,kvahTz1 =0.0,kvahTz2 =0.0,kvahTz3=0.0,kvahTz4 =0.0,kvahTz5 =0.0,kvahTz6 =0.0,
					 kvahTz7 =0.0,kvahTz8 =0.0,demandKw =0.0,kwTz1 =0.0,kwTz2 =0.0,kwTz3 =0.0,kwTz4 =0.0,kwTz5 =0.0,kwTz6 =0.0,kwTz7 =0.0,kwTz8 =0.0,
							 d3_kva =0.0,kvaTz1 =0.0,kvaTz2 =0.0,kvaTz3 =0.0,kvaTz4 =0.0,kvaTz5 =0.0,kvaTz6 =0.0,kvaTz7 =0.0,kvaTz8 =0.0;

	 String occDateKw = "",dateKwTz1 = "",dateKwTz2 = "",dateKwTz3 = "",dateKwTz4 = "",dateKwTz5 = "",dateKwTz6 = "",dateKwTz7 = "",dateKwTz8 = "",
			 dateKva="",transId="",meterId="",dateKvaTz1="",dateKvaTz2="",dateKvaTz3="",dateKvaTz4="",dateKvaTz5="",dateKvaTz6="",dateKvaTz7="",dateKvaTz8="";

	 
	 Date G2dateVal=null;	
	 
	 
	 //D4 Variables
	 String intervalPeriod = "0";
	 String d4KvaValue = "",d4KwhValue = "",d4PfValue = "";
	 String d4vrValue = "",d4vyValue = "",d4vbValue = "",d4arValue= "",d4ayValue="",d4abValue="";
	 
	 Double d4loadkwh=0.0,d4loadkvarh_lag=0.0,
					 d4loadkvarh_lead=0.0,
							 d4loadkvah=0.0,
									 d4loadkw=0.0,
											 d4loadkva=0.0;
		
	 //All SimplaDateFormats
	 SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
	 SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy hh:mm");
	 SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
	 SimpleDateFormat sdf4 = new SimpleDateFormat("dd/MM/yyyy");
	 SimpleDateFormat sdf5 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	 SimpleDateFormat yearCheck = new SimpleDateFormat("yyyy");
	 
	
	 //added columns
	// String meterNumber = "";
		String imeiNo = "";
		Date G2 = null, G3 = null, G4 = null;
		String mtrMake = "";
		String mfCode = "";
		String intervalperiod="";
	 
	
		try{
			boolean prevMonthFlag = false;
			 Calendar cal = Calendar.getInstance();
				
			 String yearSearch = cal.get(Calendar.YEAR)+"";
			 cal.setTime(sdfBillDate.parse(billmonth+""));
			 cal.add(Calendar.MONTH, -1);
			 d3_02_month = sdfBillDate.format(cal.getTime());
			 cal.add(Calendar.MONTH, -1);
			 d3_03_month = sdfBillDate.format(cal.getTime());
			 
			 NodeList nodeListForMetrNo1 = doc.getElementsByTagName("CDF");
				System.out.println("nodeListForMetrNo11111=="+nodeListForMetrNo1.getLength());
				for(int count = 0; count < nodeListForMetrNo1.getLength();count++)
				{
					Node tempNodeForMetrNo = nodeListForMetrNo1.item(count);//CDF Node
					if(tempNodeForMetrNo.getNodeType() == Node.ELEMENT_NODE)
					{
						if(tempNodeForMetrNo.hasChildNodes())
						{
							NodeList subnodeListForMetrNo = tempNodeForMetrNo.getChildNodes();
							for(int subCount = 0 ; subCount < subnodeListForMetrNo.getLength();subCount++)
							{
								//System.out.println("subnodeListForMetrNo=="+subnodeListForMetrNo.getLength());
								 Node subChildNode = subnodeListForMetrNo.item(subCount);//Utility Node
								 if(subChildNode.getNodeType() == Node.ELEMENT_NODE)
								 {
									 if(subChildNode.hasChildNodes())
									 { 
										 NodeList dataNodeList = subChildNode.getChildNodes();
										 for(int dataSubCount = 0 ; dataSubCount < dataNodeList.getLength();dataSubCount++)
										 {
											 //System.out.println("dataNodeList=="+dataNodeList.getLength());
											 
											 Node d1DataNode = dataNodeList.item(dataSubCount);
											 if(d1DataNode.getNodeType() == Node.ELEMENT_NODE)
											 {
												 if(d1DataNode.hasChildNodes())
												 {
													 if(d1DataNode.getNodeName().equalsIgnoreCase("D1"))//Check the D1 Tag
													 {
														 NodeList d1SubChildNodeList = d1DataNode.getChildNodes();
														 
														// System.out.println("d1SubChildNodeList=="+d1SubChildNodeList.getLength());
														 
														 for(int subd1 = 0; subd1 < d1SubChildNodeList.getLength();subd1++)
														 {
															
															 
															Node d1ChildNode =  d1SubChildNodeList.item(subd1);
															if(d1ChildNode.getNodeType() == Node.ELEMENT_NODE)
															{
																String nodeNameForMetrNo = d1ChildNode.getNodeName();
																String nodeValueForMetrNo = d1ChildNode.getTextContent();
																
																if (nodeNameForMetrNo.equalsIgnoreCase("G2")) {
																	 
																	System.err.println("METER date========>"+nodeValueForMetrNo);
																	meter_date=new Timestamp(sdf5.parse(nodeValueForMetrNo).getTime());
																
																	
																}
																if (nodeNameForMetrNo.equalsIgnoreCase("G3")) {
																	System.err.println("DEVICE reading date========>"+nodeValueForMetrNo);
																	device_reading_date=new Timestamp(sdf5.parse(nodeValueForMetrNo).getTime());
																	
																}
															}
														 }
													 }
												 }
											 }
										 }
									 }
								 }
							 }
						 }
					 }
				 }
				
			 
			 
			 
				System.err.println("METER date after parsing========>"+meter_date); 
				System.err.println("DEVICE reading date after parsing========>"+device_reading_date);
			NodeList nodeListForMetrNo = doc.getElementsByTagName("CDF");
			System.out.println("nodeListForMetrNo=="+nodeListForMetrNo.getLength());
			for(int count = 0; count < nodeListForMetrNo.getLength();count++)
			{
				
				Node tempNodeForMetrNo = nodeListForMetrNo.item(count);//CDF Node
				if(tempNodeForMetrNo.getNodeType() == Node.ELEMENT_NODE)
				{
					if(tempNodeForMetrNo.hasChildNodes())
					{
						NodeList subnodeListForMetrNo = tempNodeForMetrNo.getChildNodes();
						for(int subCount = 0 ; subCount < subnodeListForMetrNo.getLength();subCount++)
						{
							//System.out.println("subnodeListForMetrNo=="+subnodeListForMetrNo.getLength());
							 Node subChildNode = subnodeListForMetrNo.item(subCount);//Utility Node
							 if(subChildNode.getNodeType() == Node.ELEMENT_NODE)
							 {
								 
								 
								 
								 if(subChildNode.hasChildNodes())
								 { 
									 NodeList dataNodeList = subChildNode.getChildNodes();
									 for(int dataSubCount = 0 ; dataSubCount < dataNodeList.getLength();dataSubCount++)
									 {
										 //System.out.println("dataNodeList=="+dataNodeList.getLength());
										 
										 Node d1DataNode = dataNodeList.item(dataSubCount);
										 if(d1DataNode.getNodeType() == Node.ELEMENT_NODE)
										 {
											 if(d1DataNode.hasChildNodes())
											 {
												 if(d1DataNode.getNodeName().equalsIgnoreCase("D1"))//Check the D1 Tag
												 {
													 NodeList d1SubChildNodeList = d1DataNode.getChildNodes();
													 
													// System.out.println("d1SubChildNodeList=="+d1SubChildNodeList.getLength());
													 
													 for(int subd1 = 0; subd1 < d1SubChildNodeList.getLength();subd1++)
													 {
														
														 
														Node d1ChildNode =  d1SubChildNodeList.item(subd1);
														if(d1ChildNode.getNodeType() == Node.ELEMENT_NODE)
														{
															String nodeNameForMetrNo = d1ChildNode.getNodeName();
															String nodeValueForMetrNo = d1ChildNode.getTextContent();
															
															
															//NEED TO GET ALL PARAMETERS OF IMEI AND METER AND EVENT TIME DETAILS HERE

															//TODO
										/*					if (node.getNodeName().equalsIgnoreCase("CODE")) {
																mfCode=nodeValueForMetrNo;
																map.put(node.getNodeName(), node.getNodeValue());
															} else if (node.getNodeName().equalsIgnoreCase("NAME")) {
																list.add((node.getNodeValue().split(" "))[0]);
																map.put(node.getNodeName(), node.getNodeValue());
															}
															if (nodeNameForMetrNo.equalsIgnoreCase("G1")) {
																map.put(nodeName, nodeValue);
																// meterNumber=nodeValue;
															}

															if (nodeNameForMetrNo.equalsIgnoreCase("G2")) {
																map.put(nodeName, nodeValue);
															}
															if (nodeNameForMetrNo.equalsIgnoreCase("G3")) {
																G3=nodeValueForMetrNo;
															}
															if (nodeNameForMetrNo.equalsIgnoreCase("G4")) {
																map.put(nodeName, nodeValue);
															}*/
															
															/*if (nodeNameForMetrNo.equalsIgnoreCase("G2")) {
																 
																System.err.println("METER date========>"+nodeValueForMetrNo);
																meter_date=new Timestamp(sdf5.parse(nodeValueForMetrNo).getTime());
																System.err.println("METER date after parsing========>"+meter_date);
																
															}
															if (nodeNameForMetrNo.equalsIgnoreCase("G3")) {
																System.err.println("DEVICE reading date========>"+nodeValueForMetrNo);
																device_reading_date=sdf5.parse(nodeValueForMetrNo);
																System.err.println("DEVICE reading date after parsing========>"+device_reading_date);
															}*/
															
															
															
															if(nodeNameForMetrNo.equalsIgnoreCase("G1"))
															{
																 meterNumber = nodeValueForMetrNo;
																	
																 System.out.println(" G1 : meter number "+meterNumber.trim());	
																 /*List<CDFData> countData = cdfDataService.findAll(meterNumber, billmonth);*/
																 List<CDFData> countData=cdfDataService.checkFileExistanceForDay(meterNumber, device_reading_date);
																 System.out.println("cdf count data=="+countData.size());
																 if(countData.size() == 0)
																 {
																	 List masterList = masterService.getMeterDataInformation(meterNumber, Integer.toString(billmonth));
																	 
																	System.out.println(masterList.size()+"  --"+masterList.toString());
													
																	 if(masterList.size() > 0)
																	 {
																		 System.out.println("inside masterlist");
																		 System.out.println("inside mmmmmm");
																		
																		 String accno="";
																		 
																		 for(Iterator<?> iterator1=  masterList.iterator(); iterator1.hasNext();)
									    			    			 		{
																			 System.out.println("insss for");
																			 Object[] obj=(Object[]) iterator1.next();
																			  accno = (String)obj[0];
																			  if(obj[1]!=null )
																			  {
																				  
																				ctrn = (Double)obj[1];
																			  }
																			  if(obj[2]!=null )
																			  {
																				  
																				  ctrd = (Double)obj[2];
																			  }
																			  if(obj[3]!=null )
																			  {
																				  
																				  cd = (Double)obj[3];
																			  }
																			  if(obj[4]!=null )
																			  {
																				  
																				  mf = (Double)obj[4];
																			  }
																				
									    			    			 		}
									    			    			 		
																		 /*Object[] obj = (Object[]) masterList.get(0);
																			String accno = (String)obj[0];
																			ctrn = (Double)obj[1];
																			ctrd = (Double)obj[2];
																			cd = (Double)obj[3];
																			mf = (Double)obj[4];*/
																			
																			
																			if(mf > 0)
																			{
																				cd_20 = ((((cd/mf)*20)/100)/2);
																				cd_40 = ((((cd/mf)*40)/100)/2);
																				cd_60 = ((((cd/mf)*60)/100)/2);
																				
																				
																			}
																	 
																	
																		
																	int prevdata = cdfDataService.findPrevDataD4(meterNumber);
																	if(prevdata > 0)
																	{
																		 prevMonthFlag = true;
																	}
																	
																	//manually selecting the max id from cdf 
																	//Integer mxId=cdfDataService.getMaxCdfId();
																	
																	System.out.println("inside before saving cdf");
																	CDFData cdf = new CDFData();
																	cdf.setMeterNo(meterNumber);
																	System.out.println("accno : "+accno);
																	cdf.setAccountNo(accno.toString());
																	cdf.setBillmonth(billmonth);
																	System.err
																			.println("device_reading_date----- "+device_reading_date);
																	cdf.setReadDate(new Timestamp(device_reading_date.getTime()));
																	cdf.setDbDate(d);
																	cdf.setMeter_date(new Timestamp(meter_date.getTime()));
																	cdf.setDiscom("UHBVN");
																	/*cdf.setId(mxId+7);*/
																	//cdf.setId(mxId+30);
																	//System.err.println("++++++++++++++++++>>"+(mxId+30));
																	cdfDataService.save(cdf);
																	//cdfId = cdfDataService.getRecentCdfId(meterNumber, billmonth);
																	cdfId = cdfDataService.getRecentChangedCdfId(meterNumber,device_reading_date);
																	
																	System.err.println("LINKING CDF ID====>"+cdfId);
																	
																	
																	//System.out.println("-----------account Number : "+ obj[0]);
																	
																	NodeList nodeList = doc.getElementsByTagName("CDF");
																	for(int count1 = 0; count1 < nodeList.getLength();count1++)
																	{
																		 System.out.println("inside CDF");
																		Node tempNodeFor = nodeList.item(count1);//CDF Node
																		if(tempNodeFor.getNodeType() == Node.ELEMENT_NODE)
																		{
																			if(tempNodeFor.hasChildNodes())
																			{
																				NodeList subnodeList = tempNodeFor.getChildNodes();
																				for(int subCount1 = 0 ; subCount1 < subnodeListForMetrNo.getLength();subCount1++)
																				{
																					 Node subChildNodeForData = subnodeList.item(subCount1);//Utility Node
																					 if(subChildNodeForData.getNodeType() == Node.ELEMENT_NODE)
																					 {
																						 if(subChildNodeForData.hasChildNodes())
																						 { 
																							 NodeList dataNodeListFordb = subChildNodeForData.getChildNodes();
																							 for(int dataSubCount1 = 0 ; dataSubCount1 < dataNodeListFordb.getLength();dataSubCount1++)
																							 {
																								 Node d1DataNodeData = dataNodeListFordb.item(dataSubCount1);
																								 if(d1DataNodeData.getNodeType() == Node.ELEMENT_NODE)
																								 {
																									 if(d1DataNodeData.hasChildNodes())
																									 {
																										 System.out.println("inside d1 childnode");
																										 if(d1DataNodeData.getNodeName().equalsIgnoreCase("D1"))//Check The D1 Tag
																										 {
																											 
																											 System.out.println("enter to d1 node");
																													
																											 String g2Date = "";
																											 NodeList d1SubChildNodeListdb = d1DataNodeData.getChildNodes();
																											 System.out.println("d1SubChildNodeListdb.getLength()===="+d1SubChildNodeListdb.getLength());
																											 for(int subd11 = 0; subd11 < d1SubChildNodeListdb.getLength();subd11++)
																											 {
																												 Node d1Maindata = d1SubChildNodeListdb.item(subd11);
																												 if(d1Maindata.getNodeType() == Node.ELEMENT_NODE)
																												 {
																													 String nodeName = d1Maindata.getNodeName();
																													 String nodeValue = d1Maindata.getTextContent();
																													 	if(nodeName.equalsIgnoreCase("G2"))
																														{
																													 		g2Date = nodeValue;
																													 		if(nodeValue.contains("."))
																														 	{
																													 			SimpleDateFormat sdf21 = new SimpleDateFormat("dd-MM-yyyy hh.mm");
																														 		d1DateForCheckin = sdfBillDate.format(sdf21.parse(nodeValue));
																														 		G2dateVal= sdf21.parse(nodeValue);
																														 	}
																														 	else
																														 	{
																														 		d1DateForCheckin = sdfBillDate.format(sdf2.parse(nodeValue));
																														 		G2dateVal= sdf2.parse(nodeValue);
																														 		
																														 	}
																														}
																													 	
																														if(nodeName.equalsIgnoreCase("G13"))
																														{
																															meterClass = nodeValue;
																														}
																														if(nodeName.equalsIgnoreCase("G15"))
																														{
																															meterType = nodeValue;
																														}
																														if(nodeName.equalsIgnoreCase("G22"))
																														{
																															if (d1Maindata.hasAttributes()) 
																															{
																																NamedNodeMap nodeMap = d1Maindata.getAttributes();
																																//String manufacturerCode = "",manufacturerName = "";
																																
																																for (int i = 0; i < nodeMap.getLength(); i++) 
																																{
																																	String d1AttrId = "", value = "";
																																	Node node = nodeMap.item(i);
																																	//System.out.println(" Parameter attr name : " + node.getNodeName()+" attr value : " + node.getNodeValue());
																																	
																																	if(node.getNodeName().equalsIgnoreCase("CODE")) 
																																	{
																																		manufacturerCode = node.getNodeValue();
																																	}
																																	else if(node.getNodeName().equalsIgnoreCase("NAME")) 
																																	{
																																		manufacturerName = node.getNodeValue();
																																	}															    						
																																																																					
																																}
																																
																															}//End has Attribute
																														}
																													 
																												 }
																											 }//End For loop
																											 
																											 D1_data d1 = new D1_data();
																											 d1.setCdfId(cdfId);d1.setManufacturerCode(manufacturerCode);
																											 d1.setManufacturerName(manufacturerName);
																											 d1.setMeterClass(meterClass);d1.setMeterType(meterType);
																											 d1.setMeterDate(g2Date);
																											 d1DataService.save(d1);
																											 System.out.println("Insert into D1 completed");
																										 }// Ends the D1 Tag
																										 
																										 else if(d1DataNodeData.getNodeName().equalsIgnoreCase("D2"))//Check The D2 Tag
																										 {
																											 System.out.println("enter in to d2");
																											NodeList d2SubChildNodeList = d1DataNodeData.getChildNodes();
																											System.out.println("d2SubChildNodeList.getLength()"+d2SubChildNodeList.getLength());
																											for(int d2sub = 0;d2sub<d2SubChildNodeList.getLength();d2sub++)
																											{
																												 Node d2tempNode123 = d2SubChildNodeList.item(d2sub);
																												 if (d2tempNode123.getNodeType() == Node.ELEMENT_NODE) 
																												 {
																													 String nodeName = d2tempNode123.getNodeName();
																													 String nodeValue = d2tempNode123.getTextContent();
																													 
																													 if(d2tempNode123.hasAttributes())
																													 {
																														 NamedNodeMap nodeMap = d2tempNode123.getAttributes();
																										    				String code = "",value = "", unit = "";
																										    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																										    				{
																										    					Node node = nodeMap.item(nodeMapIndex);
																										    					//System.out.println("attr name : " + node.getNodeName());
																										    					//System.out.println("attr value : " + node.getNodeValue());
																										    					if(node.getNodeName().equalsIgnoreCase("CODE"))
																										    						code = node.getNodeValue();
																										    					else if(node.getNodeName().equalsIgnoreCase("VALUE")) 
																										    						value = node.getNodeValue();
																										    					else if(node.getNodeName().equalsIgnoreCase("UNIT")) 
																										    						unit = node.getNodeValue();													    																	    					
																										    				}
																										    				
																										    				if(value.equalsIgnoreCase(""))
																										    				{
																										    					value = "0";
																										    				}
																										    				if(code.equalsIgnoreCase("P1-2-1-1-0") && unit.equalsIgnoreCase("V"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					rPhaseVal = temp;
																										    					/*if(temp > rPhaseVal)
																										    					{
																										    						rPhaseVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P1-2-2-1-0") && unit.equalsIgnoreCase("V"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					yPhaseVal = temp;
																										    					
																										    					/*if(temp > yPhaseVal)
																										    					{
																										    						yPhaseVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P1-2-3-1-0") && unit.equalsIgnoreCase("V"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					bPhaseVal = temp;
																										    					
																										    					/*if(temp > bPhaseVal)
																										    					{
																										    						bPhaseVal = temp;
																										    					}*/
																										    				}
																										    				
																										    				else if(code.equalsIgnoreCase("P2-1-1-1-0") && unit.equalsIgnoreCase("A"))
																										    				{
																										    					
																										    					float temp = Float.parseFloat(value);
																										    					rPhaseLineVal = temp;
																										    					System.out.println("come to P2-1-1-1-0 value=="+rPhaseLineVal);
																										    					/*if(temp > rPhaseLineVal)
																										    					{
																										    						rPhaseLineVal = temp;
																										    					}*/
																										    					/*else if(temp < rPhaseLineVal)
																										    					{
																										    						temp=temp-(temp+temp);
																										    						rPhaseLineVal=temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P2-1-2-1-0") && unit.equalsIgnoreCase("A"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					yPhaseLineVal = temp;
																										    					/*if(temp > yPhaseLineVal)
																										    					{
																										    						yPhaseLineVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P2-1-3-1-0") && unit.equalsIgnoreCase("A"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					bPhaseLineVal = temp;
																										    					/*if(temp > bPhaseLineVal)
																										    					{
																										    						bPhaseLineVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P2-2-1-1-0") && unit.equalsIgnoreCase("A"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					rPhaseActiveVal = temp;
																										    					/*if(temp > rPhaseActiveVal)
																										    					{
																										    						rPhaseActiveVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P2-2-2-1-0") && unit.equalsIgnoreCase("A"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					yPhaseActiveVal = temp;
																										    					/*if(temp > yPhaseActiveVal)
																										    					{
																										    						yPhaseActiveVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P2-2-3-1-0") && unit.equalsIgnoreCase("A"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					bPhaseActiveVal = temp;
																										    					
																										    					/*if(temp > bPhaseActiveVal)
																										    					{
																										    						bPhaseActiveVal = temp;
																										    					}*/
																										    				}
																										    				
																										    				else if(code.equalsIgnoreCase("P4-1-1-0-0"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					rPhasePFVal = temp;
																										    					/*if(temp > rPhasePFVal)
																										    					 * 
																										    					{
																										    						rPhasePFVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P4-2-1-0-0"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					yPhasePFVal = temp;
																										    					/*if(temp > yPhasePFVal)
																										    					{
																										    						yPhasePFVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P4-3-1-0-0"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					
																										    					bPhasePFVal = temp;
																										    					/*if(temp > bPhasePFVal)
																										    					{
																										    						bPhasePFVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P4-4-1-0-0"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					avgPFVal = temp;
																										    					/*if(temp > avgPFVal)
																										    					{
																										    						avgPFVal = temp;
																										    					}*/
																										    				}
																										    				else if((code.equalsIgnoreCase("P3-1-4-1-0") || code.equalsIgnoreCase("P3-2-4-1-0")) && unit.equalsIgnoreCase("K"))
																										    				{
																										    					float temp = Float.parseFloat(value);
																										    					/*seena*/
																										    					activePowerVal = temp;
																										    					
																										    					
																										    					/*if(temp > activePowerVal)
																										    					{
																										    						activePowerVal = temp;
																										    					}*/
																										    				}
																										    				else if(code.equalsIgnoreCase("P8-1-0-0-0"))
																										    					
																										    				{
																										    					phaseSequence = value;
																										    				}
																										    				else if(code.equalsIgnoreCase("P9-1-0-0-0"))
																										    				{
																										    					d2frequency = Double.parseDouble(value);
																										    				}
																										    				
																										    				
																										    				
																										    				/* reading D2_KWH value*/
																										    				
																																
																										    				
																										    			else  if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-18-0-0")||code.equalsIgnoreCase("P7-1-18-2-0"))
																											    				{
																										    						float temp = Float.parseFloat(value);
																										    						d2_kwh=temp;
																										    						//System.out.println("==inside 1 ===>"+code+"==>"+temp+"==>"+d2_kwh);
																											    				}
																										    					
																										    				
																										    				
																										    					else if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-18-0-0")||
																										    							code.equalsIgnoreCase("P7-1-5-2-0") || code.equalsIgnoreCase("P7-1-13-2-0"))
																												    				{
																										    						float temp = Float.parseFloat(value);
																										    						d2_kwh=temp;
																										    						//System.out.println("==inside 2 ===>"+code+"==>"+temp+"==>"+d2_kwh);
																												    				}
																										    				
																										    				
																										    					else if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-18-0-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
																												    				{
																										    						//System.out.println("Inside P7-1-5-2-0 kwh value");
																																			
																										    						
																										    						float temp=Float.parseFloat(value);
																										    						d2_kwh=temp;
																										    						//System.out.println("==inside 3 ===>"+code+"==>"+temp+"==>"+d2_kwh);
																												    				}
																										    				
																										    				
																										    				else if(code.equalsIgnoreCase("P7-1-13-2-0")||code.equalsIgnoreCase("P7-1-13-1-0") ||code.equalsIgnoreCase("P7-1-18-2-0"))
																												    				{
																										    						float temp = Float.parseFloat(value);
																										    						d2_kwh=temp;
																										    						//System.out.println("==inside 4 ===>"+code+"==>"+temp+"==>"+d2_kwh);
																												    				}
																										    				
																										    				
																										    				
																										    				//ADD NEW COLUMNS
																										    				if(value.equalsIgnoreCase(""))
																										    				{
																										    					value = "0";
																										    				}
																										    				if(code.equalsIgnoreCase("P1-2-1-1-0") && unit.equalsIgnoreCase("V"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					vR=temp;
																										    					//d2.setvR(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P1-2-2-1-0") && unit.equalsIgnoreCase("V"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					vY=temp; 
																										    					//d2.setvY(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P1-2-3-1-0") && unit.equalsIgnoreCase("V"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					vB=temp; 
																										    					//amrInstantaneousEntity.setvB(temp);
																										    				}
																										    				
																										    				else if(code.equalsIgnoreCase("P2-1-1-1-0") && unit.equalsIgnoreCase("A"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					pfR=temp;
																										    					//amrInstantaneousEntity.setPfR(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P2-1-2-1-0") && unit.equalsIgnoreCase("A"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					pfY=temp;
																										    					//amrInstantaneousEntity.setPfY(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P2-1-3-1-0") && unit.equalsIgnoreCase("A"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					pfB=temp;
																										    					//amrInstantaneousEntity.setPfB(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P9-1-0-0-0") && unit.equalsIgnoreCase("HZ"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					pfB=temp;
																										    					//amrInstantaneousEntity.setPfB(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P3-4-4-1-0") && unit.equalsIgnoreCase("K"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					  iRAngle=temp; 
																										    					//amrInstantaneousEntity.setiRAngle(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P3-2-4-1-0") && unit.equalsIgnoreCase("K"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					 iYAngle=temp;
																										    					 // Double iBAngle=0.0;
																										    					//amrInstantaneousEntity.setiYAngle(temp);
																										    				}
																										    				
																										    				else if(code.equalsIgnoreCase("P4-1-1-0-0"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					pfR=temp;
																										    					//amrInstantaneousEntity.setPfR(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P4-2-1-0-0"))
																										    				{
																										    					double temp = Float.parseFloat(value); 
																										    					pfY=temp;
																										    					//amrInstantaneousEntity.setPfY(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P4-3-1-0-0"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					pfB=temp;
																										    					//amrInstantaneousEntity.setPfB(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P4-4-1-0-0"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					pfThreephase=temp;
																										    					//amrInstantaneousEntity.setPfThreephase(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P3-3-4-1-0") && unit.equalsIgnoreCase("K"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					iBAngle=temp;
																										    					//amrInstantaneousEntity.setiBAngle(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P7-1-5-2-0") && unit.equalsIgnoreCase("K"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					kwh=temp;
																										    					//amrInstantaneousEntity.setKwh(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P7-2-1-2-0") && unit.equalsIgnoreCase("K"))
																										    				{
																										    					double temp = Float.parseFloat(value); 
																										    					kvarhLag=temp;
																										    					//amrInstantaneousEntity.setKvarhLag(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P7-2-4-2-0") && unit.equalsIgnoreCase("K"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					kvarhLead=temp;
																										    					//amrInstantaneousEntity.setKvarhLead(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P7-3-5-2-0") && unit.equalsIgnoreCase("K"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					kvah=temp;
																										    					//amrInstantaneousEntity.setKvah(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P11-1-2-0-0"))
																										    				{
																										    					int temp = Integer.parseInt(value);
																										    					powerOffCount=temp;
																										    					//amrInstantaneousEntity.setPowerOffCount(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P11-4-0-0-0") && unit.equalsIgnoreCase("min"))
																										    				{
																										    					int temp = Integer.parseInt(value);
																										    					powerOffDuration=temp;
																										    					//amrInstantaneousEntity.setPowerOffDuration(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("1200"))
																										    				{
																										    					int temp = Integer.parseInt(value);
																										    					tamperCount=temp; 
																										    					//amrInstantaneousEntity.setTamperCount(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("1199"))
																										    				{
																										    					int temp = Integer.parseInt(value);
																										    					mdResetCount=temp; 
																										    					//amrInstantaneousEntity.setMdResetCount(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("4"))
																										    				{
																										    					String temp = value;
																										    					mdResetDate=temp;
																										    					//amrInstantaneousEntity.setMdResetDate(value);
																										    				}
																										    				
																										    				
																										    				
																										    				
																										    				/*else if(code.equalsIgnoreCase("P7-4-5-2-4") && unit.equalsIgnoreCase("K"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					amrInstantaneousEntity.setMdKw(temp);
																										    				}
																										    				else if(code.equalsIgnoreCase("P7-6-5-2-4") && unit.equalsIgnoreCase("K"))
																										    				{
																										    					double temp = Float.parseFloat(value);
																										    					amrInstantaneousEntity.setMdKva(temp);
																										    				}
																										    				if(G2!=null) {
																										    					amrInstantaneousEntity.setReadTime(new Timestamp(G2.getTime()));
																										    				}
																										    				if(G3!=null) {
																										    					amrInstantaneousEntity.setModemTime(new Timestamp(G3.getTime()));
																										    				}
																										    				amrInstantaneousEntity.setTimeStamp(new Timestamp(G2.getTime()));*/
																										    				
																										    					
																													 }
																												 }
																											}//End ForLoop
																											D2Data d2 = new D2Data();
																											d2.setFrequency(d2frequency);
																											d2.setCdfId(cdfId);d2.setbPhaseVal(bPhaseVal);d2.setrPhaseVal(rPhaseVal);d2.setyPhaseVal(yPhaseVal);
																											d2.setrPhaseLineVal(rPhaseLineVal);d2.setyPhaseLineVal(yPhaseLineVal);d2.setbPhaseLineVal(bPhaseLineVal);
																											d2.setrPhaseActiveVal(rPhaseActiveVal);d2.setyPhaseActiveVal(yPhaseActiveVal);d2.setbPhaseActiveVal(bPhaseActiveVal);
																											d2.setrPhasePfVal(rPhasePFVal);d2.setyPhasePfVal(yPhasePFVal);d2.setbPhasePfVal(bPhasePFVal);
																											d2.setAvgPfVal(avgPFVal);d2.setActivePowerVal(activePowerVal);d2.setPhaseSequence(phaseSequence);
																											d2.setMf(mf);
																											d2.setD2_kwh(d2_kwh);
																											 d2.setReadTime(new Timestamp(G2dateVal.getTime()));
																											//need to set the newly added data also....
																											//d2.setvR(vR);//d2.setvY(vY);//d2.setvB(vB);//d2.setPfR(pfR);//d2.setPfB(pfB);
																											d2.setiRAngle(iRAngle);d2.setiYAngle(iYAngle);
																											//d2.setPfThreephase(pfThreephase); d2.setKwh(kwh)
																											d2.setiBAngle(iBAngle);d2.setKvarhLag(kvarhLag);d2.setKvarhLead(kvarhLead);d2.setKvah(kvah);d2.setPowerOffCount(powerOffCount);d2.setPowerOffDuration(powerOffDuration);
																											d2.setTamperCount(tamperCount);d2.setMdResetCount(mdResetCount);d2.setMdResetDate(mdResetDate);d2.setMeterNumber(meterNumber);
																											d2DataService.save(d2);
																											System.out.println("Insert into D2 completed");
																										 }// Ends the D2 Tag
																										 
																										 else if(d1DataNodeData.getNodeName().equalsIgnoreCase("D3"))//Check The D3 Tag
																										 {
																											 System.out.println("enter in to D3 node");
																										 
																										 String flag = "0";
																										 String flag1 = "0";
																										 String flag2 = "0";
																										 NodeList d3SubNodeList = d1DataNodeData.getChildNodes();
																										 String d3_01_temp = "", d3_02_temp = "", d3_03_temp = "";
																										 
																										 ParamCodeValidator validator=new ParamCodeValidator(mtrMake, mfCode);
																										 
																										 System.out.println("d3SubNodeList.getLength()=="+d3SubNodeList.getLength());
																										 for(int countD3 = 0; countD3 < d3SubNodeList.getLength();countD3++)
																										 {
																											 String d3TagCount = "", dateTime = "", mechanism = "",  tagAttrId = "";
																											 String d3Id = "", d3AttrId = "", attrValue = "";
																											 Node d3subNode = d3SubNodeList.item(countD3);
																											 if(d3subNode.getNodeType() == Node.ELEMENT_NODE)
																											 {
																												 
																												 d3TagCount = d3subNode.getNodeName();
																												 if(d3subNode.hasAttributes())
																												 {
																													 NamedNodeMap nodeMap = d3subNode.getAttributes();
																									    			
																									    	 			for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																									    				{
																									    					Node node = nodeMap.item(nodeMapIndex);
																									    					
																									    					if(node.getNodeName().equalsIgnoreCase("DATETIME"))
																									    					{
																									    						dateTime = node.getNodeValue();
																										    					d3BillingDate =  sdf2.parse( node.getNodeValue());
																									    					}
																									    						
																									    					else if(node.getNodeName().equalsIgnoreCase("MECHANISM"))
																									    						mechanism = node.getNodeValue();
																									    				}
																									    	 			if(d3TagCount.equalsIgnoreCase("D3-01"))
																								    					{
																									    					d3_01_dateTime = dateTime;
																									    					
																									    					System.out.println("Timeeeeeeee Length "+d3_01_dateTime.length());
																									    					if(d3_01_dateTime.length()==0)
																									    					{
																									    						d3_01_temp = "000000";
																									    						
																									    					}
																									    					else if(d3_01_dateTime.length() >= 14)
																									    					{
																									    					
																									    						try{
																									    								d3_01_temp =  sdfBillDate.format(sdf2.parse(d3_01_dateTime));
																									    						}
																									    						catch (Exception e) {
																																	// TODO: handle exception
																									    							if((sdfBillDate.format(sdf4.parse(d3_01_dateTime))).startsWith("0"))
																									    							{
																									    								
																									    								String append = sdfBillDate.format(sdf4.parse(d3_01_dateTime));
																									    								append = append.substring(2);
																									    								append = "20"+append;
																									    								System.out.println("append String is : "+append);
																									    								d3_01_temp = append;	
																									    							}
																									    							
																									    							else
																									    							{
																									    								d3_01_temp =  sdfBillDate.format(sdf4.parse(d3_01_dateTime));
																									    							}
																																}
																									    					}
																									    					
																									    					else
																									    					{
																									    						d3_01_temp =  sdfBillDate.format(sdf3.parse(d3_01_dateTime));
																									    					}
																									    					
																									    					
																								    					}
																									    	 			else if(d3TagCount.equalsIgnoreCase("D3-02"))
																								    					{
																									    					d3_02_dateTime = dateTime;
																									    					if(d3_02_dateTime.length()==0)
																									    					{
																									    						d3_02_temp = "000000";
																									    						
																									    					}
																									    					else if(d3_02_dateTime.length() >= 14)
																									    					{
																									    						
																									    						try{
																									    							
																									    							d3_02_temp =  sdfBillDate.format(sdf2.parse(d3_02_dateTime));
																									    						}
																									    						catch (Exception e) {
																																	// TODO: handle exception
																									    							if((sdfBillDate.format(sdf4.parse(d3_02_dateTime))).startsWith("0"))
																									    							{
																									    								
																									    								String append = sdfBillDate.format(sdf4.parse(d3_02_dateTime));
																									    								append = append.substring(2);
																									    								append = "20"+append;
																									    								System.out.println("append String is : "+append);
																									    								d3_02_temp = append;	
																									    							}
																									    							
																									    							else
																									    							{
																									    								d3_02_temp =  sdfBillDate.format(sdf4.parse(d3_02_dateTime));
																									    							}
																																}
																									    					
																									    					}
																									    					else
																									    					{
																									    						d3_02_temp =  sdfBillDate.format(sdf3.parse(d3_02_dateTime));
																									    					}
																									    					
																									    					
																								    					}
																									    	 			else if(d3TagCount.equalsIgnoreCase("D3-03"))
																								    					{
																									    					d3_03_dateTime = dateTime;
																									    					
																									    					if(d3_03_dateTime.length() == 0)
																									    					{
																									    						d3_03_temp = "000000";
																									    						
																									    					}
																									    					
																									    					else if(d3_03_dateTime.length() >= 14)
																									    					{
																									    				
																										    				try
																										    				{
																								    							
																								    							d3_03_temp =  sdfBillDate.format(sdf2.parse(d3_03_dateTime));
																								    						}
																								    						catch (Exception e) {
																																// TODO: handle exception
																								    							if((sdfBillDate.format(sdf4.parse(d3_03_dateTime))).startsWith("0"))
																								    							{
																								    								
																								    								String append = sdfBillDate.format(sdf4.parse(d3_03_dateTime));
																								    								append = append.substring(2);
																								    								append = "20"+append;
																								    								System.out.println("append String is : "+append);
																								    								d3_03_temp = append;
																								    							}
																								    							
																								    							else
																								    							{
																								    								d3_03_temp =  sdfBillDate.format(sdf4.parse(d3_03_dateTime));
																								    							}
																															}
																								    					
																								    					
																									    					}
																									    					else
																									    					{
																									    						d3_03_temp =  sdfBillDate.format(sdf3.parse(d3_03_dateTime));
																									    					}
																								    					}
																												 }//ends hasAttributes
																												 NodeList subTempNodeListD3 = d3subNode.getChildNodes();
																												 for(int subCountD3 = 0; subCountD3 < subTempNodeListD3.getLength(); subCountD3++)
																												 {
																													 Node subTempNode123 = subTempNodeListD3.item(subCountD3);
																													 if (subTempNode123.getNodeType() == Node.ELEMENT_NODE) 
																													 {
																														String subNodeName = subTempNode123.getNodeName();//B1, B2 ..
																														String subNodeValue = subTempNode123.getTextContent();
																														String code = "", value = "", unit = "", tod = "",occ="";
																														if(subTempNode123.hasAttributes()) 
																										    			{
																															NamedNodeMap nodeMap = subTempNode123.getAttributes();
																										    				String attributeId = "", attributeValue = "";
																										    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																										    				{
																										    					Node node = nodeMap.item(nodeMapIndex);
																										    					if((d3TagCount.equalsIgnoreCase("D3-00")) || (d3TagCount.equalsIgnoreCase("D3-01")) || (d3TagCount.equalsIgnoreCase("D3-02")||(d3TagCount.equalsIgnoreCase("D3-03"))))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																												    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																												    						code = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																												    						value = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																												    						unit = node.getNodeValue();	
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																												    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																												    						code = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																												    						value = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																												    						unit = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("TOD"))
																												    						tod = node.getNodeValue();
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																												    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																												    						code = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																												    						value = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																												    						unit = node.getNodeValue();	
																												    					else if(node.getNodeName().equalsIgnoreCase("TOD"))
																												    						tod = node.getNodeValue();
																										    						}
																										    						
																										    						if(subNodeName.equalsIgnoreCase("B4"))
																										    						{
																										    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																												    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																												    						code = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																												    						value = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																												    						unit = node.getNodeValue();	
																												    					else if(node.getNodeName().equalsIgnoreCase("TOD"))
																												    						tod = node.getNodeValue();
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B6"))
																										    						{
																										    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																												    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																												    						code = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																												    						value = node.getNodeValue();
																												    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																												    						unit = node.getNodeValue();	
																												    					else if(node.getNodeName().equalsIgnoreCase("TOD"))
																												    						tod = node.getNodeValue();
																												    					else if (node.getNodeName().equalsIgnoreCase("OCCDATE"))
																																			occ = node.getNodeValue();
																										    						}
																										    					}
																										    				}
																										    				
																										    				if(manufacturerCode.equalsIgnoreCase("1")||manufacturerName.equalsIgnoreCase("SECURE METERS LTD."))
																										    				{   
																										    					//System.out.println("comes to manufacturerCode=="+manufacturerCode);
																																	
																										    					//values for updating MeterMaster
																										    					if(d3TagCount.equalsIgnoreCase("D3-01"))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if(code.equalsIgnoreCase("P7-1-5-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
																										    							{
																										    								kwhValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-1-18-0-0")||code.equalsIgnoreCase("P7-1-18-1-0"))
																										    							{
																										    								kwhValue2 = value;
																										    								System.out.println("kwhValue2==="+kwhValue2);
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-13-1-0"))
																										    							{
																										    								kvahValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-18-0-0"))
																										    							{
																										    								kvahValue2 = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-5-0-0")||code.equalsIgnoreCase("P7-3-5-2-0"))
																										    							{
																										    								kvahValue3 = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P7-6-13-1-0")||code.equalsIgnoreCase("P7-6-18-2-0"))
																										    							{
																										    								kvaValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-6-18-0-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
																										    							{
																										    								kvaValue2 = value;
																										    							}
																																		if(code.equalsIgnoreCase("P7-4-18-0-0"))
																										    							{
																										    								kvaValue3 = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P4-4-4-1-0")|| code.equalsIgnoreCase("P4-4-4-0-0"))
																										    							{
																										    								pfValue = value;
																										    							}
																										    						}
																										    						
																										    						
																										    					}//end of values for updating MeterMaster
																										    					
																										    					if((d3TagCount.equalsIgnoreCase("D3-01")) && (d3_01_month.equalsIgnoreCase(d3_01_temp)))
																										    					{
																										    						//System.out.println("comes to manufacturerCode=="+manufacturerCode);
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							
																										    							if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0"))||(code.equalsIgnoreCase("P7-1-18-1-0")||code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							{
																										    								d3_01_energy = value;
																										    								System.out.println("d3_01_energy-------1=="+d3_01_energy);
																																					
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-02")) && (d3_02_month.equalsIgnoreCase(d3_02_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							System.out.println("D3-02 B3 values - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0"))||(code.equalsIgnoreCase("P7-1-18-1-0")||code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							{
																										    								d3_02_energy = value;
																										    								System.out.println("d3_02_energy=="+d3_02_energy);
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-03")) && (d3_03_month.equalsIgnoreCase(d3_03_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							System.out.println("D3-03 B3 values - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0"))||(code.equalsIgnoreCase("P7-1-18-1-0")||code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							{
																										    								d3_03_energy = value;
																										    								System.out.println("d3_03_energy=="+d3_03_energy);
																										    							}
																										    						}
																										    					}
																										    				}//End Secure 
																										    				else if(manufacturerCode.equalsIgnoreCase("3")||manufacturerName.equalsIgnoreCase("LARSEN AND TOUBRO LIMITED"))
																										    				{
																										    					//System.out.println("comes to manufacturerCode=="+manufacturerCode);
																										    					//values for updating MeterMaster
																										    					if(d3TagCount.equalsIgnoreCase("D3-01"))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
													
																										    						
																										    						{
																										    							if(code.equalsIgnoreCase("P7-1-5-1-0")|| code.equalsIgnoreCase("P7-1-5-2-0"))
																										    							{
																										    								kwhValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-5-1-0")||code.equalsIgnoreCase("P7-3-5-2-0"))
																										    							{
																										    								kvahValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P7-6-5-1-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
																										    							{
																										    								kvaValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P4-4-4-1-0")||code.equalsIgnoreCase("P4-4-4-0-0"))
																										    							{
																										    								pfValue = value;
																										    							}
																										    						}
																										    					}//end of values for updating MeterMaster
																										    					
																										    					//for L&T 
																										    					if((d3TagCount.equalsIgnoreCase("D3-01")) && (d3_01_month.equalsIgnoreCase(d3_01_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-01 B3 //for L&T  values - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0")||(code.equalsIgnoreCase("P7-1-18-1-0"))))
																										    							{
																										    								d3_01_energy = value;
																										    								System.out.println("2====================d3_01_energy "+d3_01_energy);
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-02")) && (d3_02_month.equalsIgnoreCase(d3_02_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-02 B3 values - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0"))||(code.equalsIgnoreCase("P7-1-18-1-0")))
																										    							{
																										    								d3_02_energy = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-03")) && (d3_03_month.equalsIgnoreCase(d3_03_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-03 B3 values - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0"))||(code.equalsIgnoreCase("P7-1-18-1-0")))
																										    							{
																										    								d3_03_energy = value;
																										    							}
																										    						}
																										    					}
																										    					
																										    				}//End LNt 
																										    				else if(manufacturerCode.equalsIgnoreCase("4"))
																										    				{
																										    					//System.out.println("comes to manufacturerCode=="+manufacturerCode);
																										    					//values for updating MeterMaster
																										    					if(d3TagCount.equalsIgnoreCase("D3-01"))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if(code.equalsIgnoreCase("P7-1-5-1-0") || code.equalsIgnoreCase("P7-1-13-2-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
																										    							{
																										    								kwhValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-5-1-0") || code.equalsIgnoreCase("P7-3-13-2-0")||code.equalsIgnoreCase("P7-3-5-2-0"))
																										    							{
																										    								kvahValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							//System.out.println("D3-01 lnt B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P7-6-5-1-0") || code.equalsIgnoreCase("P7-6-5-2-4")||code.equalsIgnoreCase("P7-6-5-2-0"))
																										    							{
																										    								kvaValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
																										    							{
																										    								pfValue = value;
																										    							}
																										    							else
																										    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
																										    							{
																										    								pfValue = value;
																										    							}
																										    						}
																										    					}//end of values for updating MeterMaster
																										    					
																										    					//for genus
																										    					
																										    					if((d3TagCount.equalsIgnoreCase("D3-01")) && (d3_01_month.equalsIgnoreCase(d3_01_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-01 B3 values -ssssss"+code+" - "+value+" - "+unit);
																										    							System.out.println("code=="+code);
																																				
																										    							if(code.equalsIgnoreCase("P7-1-5-1-0")&& flag.equalsIgnoreCase("0") ) // || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0"))
																										    							{
																										    								d3_01_energy = value;
																										    								flag = value;
																										    							}else if(code.equalsIgnoreCase("P7-1-18-0-0")||(code.equalsIgnoreCase("P7-1-18-1-0")) && flag.equalsIgnoreCase("0"))
																										    							{
																										    								d3_01_energy = value;
																										    								flag = value;
																										    							}else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag.equalsIgnoreCase("0"))
																										    							{
																										    								d3_01_energy = value;
																										    								flag = value;
																										    							}
																										    							else if (code.equalsIgnoreCase("P7-1-13-2-0") && flag.equalsIgnoreCase("0"))
																										    							{
																										    								d3_01_energy = value;
																										    								flag = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-02")) && (d3_02_month.equalsIgnoreCase(d3_02_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3") && flag1.equalsIgnoreCase("0"))
																										    						{
																										    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P7-1-5-1-0") && flag1.equalsIgnoreCase("0")) //|| (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							{
																										    								d3_02_energy = value;
																										    								flag1 = value;
																										    							}else if(code.equalsIgnoreCase("P7-1-18-0-0") ||(code.equalsIgnoreCase("P7-1-18-1-0"))&& flag1.equalsIgnoreCase("0"))
																										    							{
																										    								d3_02_energy = value;
																										    								flag1 = value;
																										    							}else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1.equalsIgnoreCase("0"))
																										    							{
																										    								d3_02_energy = value;
																										    								flag1 = value;
																										    							}
																										    							else if (code.equalsIgnoreCase("P7-1-13-2-0") && flag1.equalsIgnoreCase("0"))
																										    							{
																										    								d3_02_energy = value;
																										    								flag1 = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-03")) && (d3_03_month.equalsIgnoreCase(d3_03_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-03 B3 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P7-1-5-1-0")  && flag2.equalsIgnoreCase("0"))// || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							{
																										    								d3_03_energy = value;
																										    								flag2 = value;
																										    							}
																										    							else if(code.equalsIgnoreCase("P7-1-18-0-0")||(code.equalsIgnoreCase("P7-1-18-1-0")) && flag2.equalsIgnoreCase("0"))
																										    							{
																										    								d3_03_energy = value;
																										    								flag2 = value;
																										    							}else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag2.equalsIgnoreCase("0"))
																										    							{
																										    								d3_03_energy = value;
																										    								flag2 = value;
																										    							}
																										    							else if (code.equalsIgnoreCase("P7-1-13-2-0") && flag2.equalsIgnoreCase("0"))
																										    							{
																										    								d3_03_energy = value;
																										    								flag2 = value;
																										    							}
																										    						}
																										    					}
																										    					
																										    				}//End Genus or DLMS
																										    				
																										    				//IF ELSE LADDER COMPLETED HERE
																										    				
																										    				else if(manufacturerCode.equalsIgnoreCase("101"))
																										    				{
																										    					//System.out.println("comes to manufacturerCode=="+manufacturerCode);
																										    					if(d3TagCount.equalsIgnoreCase("D3-01"))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if(code.equalsIgnoreCase("P7-1-18-2-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
																										    							{
																										    								kwhValue = value;
																										    							}
																										    							
																										    							if(code.equalsIgnoreCase("P7-3-13-0-0")||code.equalsIgnoreCase("P7-3-5-2-0"))
																										    							{
																										    								kvahValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							if(code.equalsIgnoreCase("P7-6-13-0-0")||code.equalsIgnoreCase("P7-6-5-2-0"))
																										    							{
																										    								kvaValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							if(code.equalsIgnoreCase("P4-4-4-1-0")|| code.equalsIgnoreCase("P4-4-4-0-0"))
																										    							{
																										    								pfValue = value;
																										    							}
																										    						}
																										    						
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-01")) && (d3_01_month.equalsIgnoreCase(d3_01_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if((code.equalsIgnoreCase("P7-1-18-2-0")))
																										    							{
																										    								d3_01_energy = value;
																										    								System.out.println("d3TagCount.equalsIgnoreCase");
																																					
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-02")) && (d3_02_month.equalsIgnoreCase(d3_02_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if((code.equalsIgnoreCase("P7-1-18-2-0")))
																										    							{
																										    								d3_02_energy = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-03")) && (d3_03_month.equalsIgnoreCase(d3_03_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if((code.equalsIgnoreCase("P7-1-18-2-0")))
																										    							{
																										    								d3_03_energy = value;
																										    								System.out.println("d3_01_energy=="+d3_01_energy);
																										    							}
																										    						}
																										    					}
																										    					
																										    				}//End of LNG
																										    				else
																										    				{
																										    					//values for updating MeterMaster
																										    					/*if(d3TagCount.equalsIgnoreCase("D3-01"))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if(code.equalsIgnoreCase("P7-1-13-2-0")||code.equalsIgnoreCase("P7-1-13-1-0"))
																										    							{
																										    								kwhValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-13-2-0")||code.equalsIgnoreCase("P7-3-13-1-0"))
																										    							{
																										    								kvahValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P7-6-13-2-0")||code.equalsIgnoreCase("P7-6-13-1-0") ||code.equalsIgnoreCase("P7-6-18-2-0"))
																										    							{
																										    								kvaValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
																										    							{
																										    								pfValue = value;
																										    							}
																										    						}
																										    						
																										    					}//end of values for updating MeterMaster
																										    					//for HPL
																										    					if((d3TagCount.equalsIgnoreCase("D3-01")) && (d3_01_month.equalsIgnoreCase(d3_01_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    						System.out.println("D3-01 B3 valuesqqqqqqq - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-13-2-0"))||(code.equalsIgnoreCase("P7-1-13-1-0")))
																										    							{
																										    								d3_01_energy = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-02")) && (d3_02_month.equalsIgnoreCase(d3_02_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																										    							//if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							if((code.equalsIgnoreCase("P7-1-13-2-0"))||(code.equalsIgnoreCase("P7-1-13-1-0")))
																										    							{
																										    								d3_02_energy = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-03")) && (d3_03_month.equalsIgnoreCase(d3_03_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																										    							//if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							if((code.equalsIgnoreCase("P7-1-13-2-0"))||(code.equalsIgnoreCase("P7-1-13-1-0")))
																										    							{
																										    								d3_03_energy = value;
																										    							}
																										    						}
																										    					}*/
																										    					
																										    					
																										    					if(d3TagCount.equalsIgnoreCase("D3-00"))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							if(code.equalsIgnoreCase("P7-1-13-2-0")||code.equalsIgnoreCase("P7-1-13-1-0")||code.equalsIgnoreCase("P7-1-5-2-0"))
																										    							{
																										    								kwhValue = value;
																										    							}
																										    							if(code.equalsIgnoreCase("P7-3-13-2-0")||code.equalsIgnoreCase("P7-3-13-1-0"))
																										    							{
																										    								kvahValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B5"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P7-6-13-2-0")||code.equalsIgnoreCase("P7-6-13-1-0") ||code.equalsIgnoreCase("P7-6-18-2-0") || code.equalsIgnoreCase("P7-6-5-2-0"))
																										    							{
																										    								kvaValue = value;
																										    							}
																										    						}
																										    						if(subNodeName.equalsIgnoreCase("B9"))
																										    						{
																										    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
																										    							if(code.equalsIgnoreCase("P4-4-4-0-0"))
																										    							{
																										    								pfValue = value;
																										    							}
																										    						}
																										    						
																										    					}//end of values for updating MeterMaster
																										    					//for HPL
																										    					if((d3TagCount.equalsIgnoreCase("D3-01")) && (d3_01_month.equalsIgnoreCase(d3_01_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    						System.out.println("D3-01 B3 valuesqqqqqqq - "+code+" - "+value+" - "+unit);
																										    							if((code.equalsIgnoreCase("P7-1-13-2-0"))||(code.equalsIgnoreCase("P7-1-13-1-0")))
																										    							{
																										    								d3_01_energy = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-02")) && (d3_02_month.equalsIgnoreCase(d3_02_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																										    							//if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							if((code.equalsIgnoreCase("P7-1-13-2-0"))||(code.equalsIgnoreCase("P7-1-13-1-0")))
																										    							{
																										    								d3_02_energy = value;
																										    							}
																										    						}
																										    					}
																										    					if((d3TagCount.equalsIgnoreCase("D3-03")) && (d3_03_month.equalsIgnoreCase(d3_03_temp)))
																										    					{
																										    						if(subNodeName.equalsIgnoreCase("B3"))
																										    						{
																										    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
																										    							//if((code.equalsIgnoreCase("P7-1-5-1-0")) || (code.equalsIgnoreCase("P7-1-18-0-0")) || (code.equalsIgnoreCase("P7-1-5-2-0")))
																										    							if((code.equalsIgnoreCase("P7-1-13-2-0"))||(code.equalsIgnoreCase("P7-1-13-1-0")))
																										    							{
																										    								d3_03_energy = value;
																										    							}
																										    						}
																										    					}
																										    					
																																		
																																		
																																		//NEWLY ADDED FIELDS
																																		if((d3TagCount.equalsIgnoreCase("D3-00")))
																												    					{
																												    						if(subNodeName.equalsIgnoreCase("B3"))
																												    						{
																												    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																														    					/*if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																														    						code = node.getNodeValue();
																														    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																														    						value = node.getNodeValue();
																														    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																														    						unit = node.getNodeValue();	*/
																												    							
																												    							//code=validator.getD3RecCode(code);
																														    					
																														    					if ("P7-3-5-2-0".equalsIgnoreCase(code)) {
																																					if ("k".equalsIgnoreCase(unit)) {
																																						d3_kvah=Double.parseDouble(value);
																																						//billsEntity.setKvah(Double.parseDouble(value));
																																					} else if("M".equalsIgnoreCase(unit)) {
																																						d3_kvah=Double.parseDouble(value);
																																						//billsEntity.setKvah((Double.parseDouble(value)*1000));
																																					}
																																				}
																														    					
																														    					if ("P7-1-5-2-0".equalsIgnoreCase(code)) {
																																					if ("k".equalsIgnoreCase(unit)) {
																																						d3_kwh=Double.parseDouble(value);
																																						//billsEntity.setKwh(Double.parseDouble(value));
																																					} else if("M".equalsIgnoreCase(unit)) {
																																						d3_kwh=Double.parseDouble(value);
																																						//billsEntity.setKwh((Double.parseDouble(value))*1000);
																																					}
																																				}
																														    						if ("P7-2-1-2-0".equalsIgnoreCase(code)) {
																														    							
																														    							//System.err.println("inside--->P7-2-1-2-0--"+value);
																																						//d3_kvarhLead=Double.parseDouble(value);
																																						if ("k".equalsIgnoreCase(unit)) {
																																							System.err.println("inside--->P7-2-1-2-0--"+value);
																																							d3_kvarhLead=Double.parseDouble(value);
																																							//billsEntity.setKvarhLead(Double.parseDouble(value));
																																						} else if("M".equalsIgnoreCase(unit)) {
																																							d3_kvarhLead=Double.parseDouble(value);
																																							//billsEntity.setKvarhLead((Double.parseDouble(value))*1000);
																																						}
																																					}
																																				
																																				if ("P7-2-4-2-0".equalsIgnoreCase(code)) {
																																					if ("k".equalsIgnoreCase(unit)) {
																																						d3_kvarhLag=Double.parseDouble(value);
																																						//billsEntity.setKvarhLag(Double.parseDouble(value));
																																					} else if("M".equalsIgnoreCase(unit)) {
																																						d3_kvarhLag=Double.parseDouble(value);
																																						//billsEntity.setKvarhLag((Double.parseDouble(value))*1000);
																																					}
																																				}
																																				
																												    						}if(subNodeName.equalsIgnoreCase("B4"))
																												    						{
																														    					//code=validator.getD3RecCode(code);
																																				
																																				if ("P7-3-5-2-0".equalsIgnoreCase(code)) {
																																					if ("k".equalsIgnoreCase(unit)) {
																																						
																																					} else if("M".equalsIgnoreCase(unit)) {
																																						value=(Double.parseDouble(value)*1000)+"";
																																					}
																																					
																																					switch (tod) {
																																					case "1":
																																						kvahTz1=Double.parseDouble(value);
																																						
																																						//billsEntity.setKvahTz1(Double.parseDouble(value));
																																						break;
																																					case "2":
																																						kvahTz2=Double.parseDouble(value);
																																						//billsEntity.setKvahTz2(Double.parseDouble(value));
																																						break;
																																					case "3":
																																						kvahTz3=Double.parseDouble(value);
																																						//billsEntity.setKvahTz3(Double.parseDouble(value));
																																						break;
																																					case "4":
																																						kvahTz4=Double.parseDouble(value);
																																						//billsEntity.setKvahTz4(Double.parseDouble(value));
																																						break;
																																					case "5":
																																						kvahTz5=Double.parseDouble(value);
																																						//billsEntity.setKvahTz5(Double.parseDouble(value));
																																						break;
																																					case "6":
																																						kvahTz6=Double.parseDouble(value);
																																						//billsEntity.setKvahTz6(Double.parseDouble(value));
																																						break;
																																					case "7":
																																						kvahTz7=Double.parseDouble(value);
																																						//billsEntity.setKvahTz7(Double.parseDouble(value));
																																						break;
																																					case "8":
																																						kvahTz8=Double.parseDouble(value);
																																						//billsEntity.setKvahTz8(Double.parseDouble(value));
																																						break;
																													
																																					default:
																																						break;
																																					}
																																				}else if ("P7-1-5-2-0".equalsIgnoreCase(code)) {
																																					
																																					if ("k".equalsIgnoreCase(unit)) {
																																						value=Double.parseDouble(value)+"";
																																					}
																																					else if("M".equalsIgnoreCase(unit)) {
																																						value=(Double.parseDouble(value)*1000)+"";
																																					}
																																					
																																					switch (tod) {
																																					case "1":
																																						kwhTz1=Double.parseDouble(value);
																																						System.err
																																						.println("kwTz1====>"+kwTz1);
																																						//billsEntity.setKwhTz1(Double.parseDouble(value));
																																						break;
																																					case "2":
																																						kwhTz2=Double.parseDouble(value);
																																						//billsEntity.setKwhTz2(Double.parseDouble(value));
																																						break;
																																					case "3":
																																						kwhTz3=Double.parseDouble(value);
																																						//billsEntity.setKwhTz3(Double.parseDouble(value));
																																						break;
																																					case "4":
																																						kwhTz4=Double.parseDouble(value);
																																						//billsEntity.setKwhTz4(Double.parseDouble(value));
																																						break;
																																					case "5":
																																						kwhTz5=Double.parseDouble(value);
																																						//billsEntity.setKwhTz5(Double.parseDouble(value));
																																						break;
																																					case "6":
																																						kwhTz6=Double.parseDouble(value);
																																						//billsEntity.setKwhTz6(Double.parseDouble(value));
																																						break;
																																					case "7":
																																						kwhTz7=Double.parseDouble(value);
																																						//billsEntity.setKwhTz7(Double.parseDouble(value));
																																						break;
																																					case "8":
																																						kwhTz8=Double.parseDouble(value);
																																						//billsEntity.setKwhTz8(Double.parseDouble(value));
																																						break;
																													
																																					default:
																																						break;
																																					}
																																				}
																												    						}
																												    						if(subNodeName.equalsIgnoreCase("B5"))
																												    						{
																												    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																														    					/*if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																														    						code = node.getNodeValue();
																														    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																														    						value = node.getNodeValue();
																														    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																														    						unit = node.getNodeValue();
																														    					else if(node.getNodeName().equalsIgnoreCase("TOD"))
																														    						unit = node.getNodeValue();*/
																												    							
																												    							//code=validator.getD3RecCode(code);
																																				
																																				if ("P7-6-5-2-0".equalsIgnoreCase(code)) {
																																					if ("k".equalsIgnoreCase(unit)) {
																																						//billsEntity.setKva(Double.parseDouble(value));
																																						//billsEntity.setDateKva(occ);.
																																						d3_kva=Double.parseDouble(value);
																																						dateKva=occ;
																																					} else if("M".equalsIgnoreCase(unit)) {
																																						d3_kva=Double.parseDouble(value);
																																						dateKva=occ;
																																					}
																																				} 
																																				
																																				if ("P7-4-5-2-0".equalsIgnoreCase(code)) {
																																					if ("k".equalsIgnoreCase(unit)) {
																																						demandKw=Double.parseDouble(value);
																																						occDateKw=occ;
																																						//billsEntity.setDemandKw(Double.parseDouble(value));
																																						//billsEntity.setOccDateKw(occ);
																																					} else if("M".equalsIgnoreCase(unit)) {
																																						demandKw=Double.parseDouble(value);
																																						occDateKw=occ;
																																					}
																																				}
																												    						}
																												    						if(subNodeName.equalsIgnoreCase("B6"))
																												    						{
																												    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																														    					
																														    					
																														    					
																														    					//code=validator.getD3RecCode(code);
																																				if ("P7-6-5-2-0".equalsIgnoreCase(code)) {
																																					
																																					if ("k".equalsIgnoreCase(unit)) {
																																						
																																					} else if("M".equalsIgnoreCase(unit)) {
																																						value=(Double.parseDouble(value)*1000)+"";
																																					}
																																					
																																					
																																					switch (tod) {
																																					case "1":
																																						kvaTz1=Double.parseDouble(value);
																																						dateKvaTz1=occ;
																																						//billsEntity.setKvaTz1(Double.parseDouble(value));
																																						//billsEntity.setDateKvaTz1(occ);
																																						break;
																																					case "2":
																																						kvaTz2=Double.parseDouble(value);
																																						dateKvaTz2=occ;
																																						//billsEntity.setKvaTz2(Double.parseDouble(value));
																																						//billsEntity.setDateKvaTz2(occ);
																																						break;
																																					case "3":
																																						kvaTz3=Double.parseDouble(value);
																																						dateKvaTz3=occ;
																																						//billsEntity.setKvaTz3(Double.parseDouble(value));
																																						//billsEntity.setDateKvaTz3(occ);
																																						break;
																																					case "4":
																																						kvaTz4=Double.parseDouble(value);
																																						dateKvaTz4=occ;
																																						//billsEntity.setKvaTz4(Double.parseDouble(value));
																																						//billsEntity.setDateKvaTz4(occ);
																																						break;
																																					case "5":
																																						kvaTz5=Double.parseDouble(value);
																																						dateKvaTz5=occ;
																																						//billsEntity.setKvaTz5(Double.parseDouble(value));
																																						//billsEntity.setDateKvaTz5(occ);
																																						break;
																																					case "6":
																																						kvaTz6=Double.parseDouble(value);
																																						dateKvaTz6=occ;
																																						//billsEntity.setKvaTz6(Double.parseDouble(value));
																																						//billsEntity.setDateKvaTz6(occ);
																																						break;
																																					case "7":
																																						kvaTz7=Double.parseDouble(value);
																																						dateKvaTz7=occ;
																																						//billsEntity.setKvaTz7(Double.parseDouble(value));
																																						//billsEntity.setDateKvaTz7(occ);
																																						break;
																																					case "8":
																																						kvaTz8=Double.parseDouble(value);
																																						dateKvaTz8=occ;
																																						//billsEntity.setKvaTz8(Double.parseDouble(value));
																																						//billsEntity.setDateKvaTz8(occ);
																																						break;

																																					default:
																																						break;
																																					}
																																							
																																						
																																				}
																																					
																																					if ("P7-4-5-2-0".equalsIgnoreCase(code)) {
																																						
																																						if ("k".equalsIgnoreCase(unit)) {
																																							
																																						}
																																						else if("M".equalsIgnoreCase(unit)) {
																																							value=(Double.parseDouble(value)*1000)+"";
																																						}
																																						switch (tod) {
																																						case "1":
																																							kwTz1=Double.parseDouble(value);
																																							dateKwTz1=occ;
																																							/*billsEntity.setKwTz1(Double.parseDouble(value));
																																							billsEntity.setDateKwTz1(occ);*/
																																							break;
																																						case "2":
																																							kwTz2=Double.parseDouble(value);
																																							dateKwTz2=occ;
																																							//billsEntity.setKwTz2(Double.parseDouble(value));
																																							//billsEntity.setDateKwTz2(occ);
																																							break;
																																						case "3":
																																							kwTz3=Double.parseDouble(value);
																																							dateKwTz3=occ;
																																							//billsEntity.setKwTz3(Double.parseDouble(value));
																																							//billsEntity.setDateKwTz3(occ);
																																							break;
																																						case "4":
																																							kwTz4=Double.parseDouble(value);
																																							dateKwTz4=occ;
																																							//billsEntity.setKwTz4(Double.parseDouble(value));
																																							//billsEntity.setDateKwTz4(occ);
																																							break;
																																						case "5":
																																							kwTz5=Double.parseDouble(value);
																																							dateKwTz5=occ;
																																							//billsEntity.setKwTz5(Double.parseDouble(value));
																																							//billsEntity.setDateKwTz5(occ);
																																							break;
																																						case "6":
																																							kwTz6=Double.parseDouble(value);
																																							dateKwTz6=occ;
																																							//billsEntity.setKwTz6(Double.parseDouble(value));
																																							//billsEntity.setDateKwTz6(occ);
																																							break;
																																						case "7":
																																							kwTz7=Double.parseDouble(value);
																																							dateKwTz7=occ;
																																							//billsEntity.setKwTz7(Double.parseDouble(value));
																																							//billsEntity.setDateKwTz7(occ);
																																							break;
																																						case "8":
																																							kwTz8=Double.parseDouble(value);
																																							dateKwTz8=occ;
																																							//billsEntity.setKwTz8(Double.parseDouble(value));
																																							//billsEntity.setDateKwTz8(occ);
																																							break;

																																						default:
																																							break;
																																						}
																														    					
																												    						}
																												    						
																												    					}
																												    						if(subNodeName.equalsIgnoreCase("B9"))
																												    						{
																												    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																												    							if ("P4-4-4-1-0".equalsIgnoreCase(code)) {
																																					sysPfBilling=Double.parseDouble(value);
																																					//billsEntity.setSysPfBilling(Double.parseDouble(value));
																																				}

																																			}
																																		}
																																	}
																																}
																															}
																														}
																													}
																											 }
																										 D3Data d3 = new D3Data();
																										 d3.setCdfId(cdfId);
																										 System.out.println("d3_01_energy=="+d3_01_energy);
																										 System.out.println("d3_02_energy=="+d3_02_energy);
																										 System.out.println("d3_03_energy=="+d3_03_energy);
																												
																										 if(d3_01_energy.equalsIgnoreCase("") || d3_01_energy.equalsIgnoreCase(null))
																										 {
																											 d3_01_energy = "0";
																										 }
																										 if(d3_02_energy.equalsIgnoreCase("") || d3_02_energy.equalsIgnoreCase(null))
																										 {
																											 d3_02_energy = "0";
																										 }
																										 if(d3_03_energy.equalsIgnoreCase("") || d3_03_energy.equalsIgnoreCase(null))
																										 {
																											 d3_03_energy = "0";
																										 }
																										 d3.setD3_01_Energy(Float.parseFloat(d3_01_energy));
																										 d3.setD3_02_Energy(Float.parseFloat(d3_02_energy));
																										 d3.setD3_03_Energy(Float.parseFloat(d3_03_energy));
																										 d3.setKvah(d3_kvah);
																										 d3.setKwh(d3_kwh);
																										 d3.setKvarhLead(d3_kvarhLead);
																										 d3.setKvarhLag(d3_kvarhLag);
																										 //d3.setKvarhLead(kvarhLead);   
																										// d3.setKvarhLag(kvarhLag);
																										 d3.setKvahTz1(kvahTz1);
																										 d3.setKvahTz2(kvahTz2);
																										 d3.setKvahTz3(kvahTz3);
																										 d3.setKvahTz4(kvahTz4);
																										 d3.setKvahTz5(kvahTz5);
																										 d3.setKvahTz6(kvahTz6);
																										 d3.setKvahTz7(kvahTz7);
																										 d3.setKvahTz8(kvahTz8);
																										 d3.setKwhTz1(kwhTz1);
																										 d3.setKwhTz2(kwhTz2);
																										 d3.setKwhTz3(kwhTz3);
																										 d3.setKwhTz4(kwhTz4);
																										 d3.setKwhTz5(kwhTz5);
																										 d3.setKwhTz6(kwhTz6);
																										 d3.setKwhTz7(kwhTz7);
																										 d3.setKwhTz8(kwhTz8);
																										// d3.setKva(kva); 
																										 d3.setKva(d3_kva);
																										 d3.setDateKva(dateKva);
																										 d3.setDemandKw(demandKw);
																										 d3.setOccDateKw(occDateKw);
																										 d3.setKvaTz1(kvaTz1);
																										 d3.setDateKvaTz1(dateKvaTz1);
																										 d3.setKvaTz2(kvaTz2);
																										 d3.setDateKvaTz2(dateKvaTz2);
																										 d3.setKvaTz3(kvaTz3);
																										 d3.setDateKvaTz3(dateKvaTz3);
																										 d3.setKvaTz4(kvaTz4);
																										 d3.setDateKvaTz4(dateKvaTz4);
																										 d3.setKvaTz5(kvaTz5);
																										 d3.setDateKvaTz5(dateKvaTz5);
																										 d3.setKvaTz6(kvaTz6);
																										 d3.setDateKvaTz6(dateKvaTz6);
																										 d3.setKvaTz7(kvaTz7);
																										 d3.setDateKvaTz7(dateKvaTz7);
																										 d3.setKvaTz8(kvaTz8);
																										 d3.setDateKvaTz8(dateKvaTz8);
																										 d3.setKwTz1(kwTz1);
																										 d3.setDateKwTz1(dateKwTz1);
																										 d3.setKwTz2(kwTz2);
																										 d3.setDateKwTz2(dateKwTz2);
																										 d3.setKwTz3(kwTz3);
																										 d3.setDateKwTz3(dateKwTz3);
																										 d3.setKwTz4(kwTz4);
																										 d3.setDateKwTz4(dateKwTz4);
																										 d3.setKwTz5(kwTz5);
																										 d3.setDateKwTz5(dateKwTz5);
																										 d3.setKwTz6(kwTz6);
																										 d3.setDateKwTz6(dateKwTz6);
																										 d3.setKwTz7(kwTz7);
																										 d3.setDateKwTz7(dateKwTz7);
																										 d3.setKwTz8(kwTz8);
																										 d3.setDateKwTz8(dateKwTz8);
																										 d3.setSysPfBilling(sysPfBilling);
																										 d3.setTimeStamp(new Timestamp(G2dateVal.getTime()));
																										 d3.setBilling_date(new Timestamp(G2dateVal.getTime())); //new Timestamp(d3BillingDate.getTime())
																										 d3DataService.save(d3);
																										 System.out.println("Insert into D3 completed");
																										 }// Ends the D3 Tag
																										 else if(d1DataNodeData.getNodeName().equalsIgnoreCase("D4"))//Check The D4 Tag
																										 {
																											 
																											 System.out.println("enter in to D4 node cmriiii");
																											 
																											 String checkDatePattern = "";
																											 NodeList subnodeListD4 = d1DataNodeData.getChildNodes();
																											 if (d1DataNodeData.hasAttributes()) 
																								    			{
																								    	 			NamedNodeMap nodeMap = d1DataNodeData.getAttributes();
																								    				//String code = "",value = "", unit = "";
																								    	 			System.out.println("nodeMap.getLength()=="+nodeMap.getLength());
																								    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																								    				{
																								    					Node node = nodeMap.item(nodeMapIndex);
																								    					intervalPeriod = node.getNodeValue();
																								    					System.out.println("attr name : " + node.getNodeName()+" attr value : " + intervalPeriod);
																								    				}
																								    			}
																											 
																											 for (int countD4 = 0; countD4 < subnodeListD4.getLength(); countD4++) 
																												{
																												 System.err
																														.println("inside for loop");
																												    String dayProfileDate = "";
																													Node tempNodeD4 = subnodeListD4.item(countD4);
																													 if (tempNodeD4.getNodeType() == Node.ELEMENT_NODE) 
																													 {
																														 Calendar calendar = Calendar.getInstance();
																														 String nodeName = tempNodeD4.getNodeName();
																														 String nodeValue = tempNodeD4.getTextContent();
																														 if (tempNodeD4.hasAttributes()) 
																											    			{
																											    	 			NamedNodeMap nodeMap = tempNodeD4.getAttributes();
																											    	 			
																											    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																											    				{
																											    					Node node = nodeMap.item(nodeMapIndex);
																											    					dayProfileDate = node.getNodeValue();
																											    					//System.out.println(" profile date attr name : " + node.getNodeName()+" attr value : " + dayProfileDate);
																											    																		    																	    																	    					
																											    				}													    				
																											    			}
																														 System.err.println("check---- "+meterNumber+"--"+dayProfileDate);
																														List<?> d1= d4LoadDataService.getDuplicateData(meterNumber,dayProfileDate);
																														System.err.println("d1--- "+d1.size());
																														 if(d1.size()==0){
																														 if(dayProfileDate.length() > 2)
																															{
																																checkDatePattern = dayProfileDate.substring(2);
																																if(checkDatePattern.startsWith("-"))
																																{
																																	dayProfileDate = sdf3.format(sdf3.parse(dayProfileDate));
																																}
																																else if(checkDatePattern.startsWith("/"))
																																{
																																	dayProfileDate = sdf4.format(sdf4.parse(dayProfileDate));
																																	
																																}
																																else
																																{
																																	 dayProfileDate = "";
																																}
																															}
																															else
																															{
																																 dayProfileDate = "";
																															}
																														 if(prevMonthFlag)
																														 {
//																															 System.out.println("previous data their ");
																															 Calendar cal1 = Calendar.getInstance();
																															 if(dayProfileDate.length() > 2)
																																{
																																 String checkForDatePatter = dayProfileDate.substring(2);
																																 if(checkForDatePatter.startsWith("-"))
																																	{
																																	 cal1.setTime(sdf3.parse(dayProfileDate));
																																	}
																																	else if(checkForDatePatter.startsWith("/"))
																																	{
																																		 cal1.setTime(sdf4.parse(dayProfileDate));
																																	}
																																	else
																																	{
																																		 dayProfileDate = "";
																																	}
																																}
																															 
																															cal1.add(Calendar.MONTH, 1);
																															 String profileDateYearMonth = sdfBillDate.format(cal1.getTime());
																															 if(d1DateForCheckin.equalsIgnoreCase(billmonth+""))//Checking Profile Date is Equal to this Month
																															 {
																																 int kwhFlag = 0;
																																 NodeList subTempNodeListD4 = tempNodeD4.getChildNodes();
																																 Date datetime=null;
																																 if(dayProfileDate.length() > 2)
																																	{
																																	 String checkForDatePatter1 = dayProfileDate.substring(2);
																																	 if(checkForDatePatter1.startsWith("-"))
																																	 {
																																		// d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																		 datetime=sdf3.parse(dayProfileDate);
																																		 calendar.setTime(datetime);
																																		// calendar.add(Calendar.MINUTE, 30);
																																		}
																																		else if(checkForDatePatter1.startsWith("/"))
																																		{
																																			//d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																			 datetime=sdf4.parse(dayProfileDate);
																																			 calendar.setTime(datetime);
																																			// calendar.add(Calendar.MINUTE, 30);
																																		}
																																		else
																																		{
																																			 dayProfileDate = "";
																																		}
																																	}
																																 for (int subCountD4 = 0; subCountD4 < subTempNodeListD4.getLength(); subCountD4++) 
																																	{
																																	
																																	 	String ipInterval = "0";
																																		int ipIntervalNum = 0;
																																		 Node subTempNode = subTempNodeListD4.item(subCountD4);
																																		 if (subTempNode.getNodeType() == Node.ELEMENT_NODE) 
																																		 {
																																			 String subNodeName = subTempNode.getNodeName();//IP ..
																																			 String subNodeValue = subTempNode.getTextContent();
																																			 if(subTempNode.hasAttributes())
																																			 {
																																				 NamedNodeMap nodeMap = subTempNode.getAttributes();
																																    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																    				{
																																    					Node node = nodeMap.item(nodeMapIndex);
																																    					//System.out.println("ipinterval befor node.getNodeValue==>"+ipInterval);
																																    					ipInterval = node.getNodeValue();
																																    					//System.out.println("ipinterval after node.getNodeValue==>"+ipInterval);
																																    				}
																																			 }
																																		 }
																																		 ipIntervalNum = Integer.parseInt(ipInterval);
																																		 boolean sumKwhFlag = true;
																																		 boolean sumKvaFlag = true;
																																		 NodeList subTempNodeListIP = subTempNode.getChildNodes();
																																		 D4CdfData d4 = new D4CdfData();
																																		 for (int subCountIP = 0; subCountIP < subTempNodeListIP.getLength(); subCountIP++)
																																		 {
																																			 
																																			 String paramCode = "", paramValue = "";
																																			 Node subTempNodeIP = subTempNodeListIP.item(subCountIP);
																																			 if (subTempNodeIP.getNodeType() == Node.ELEMENT_NODE) 
																																			 {
																																				String subNodeName = subTempNodeIP.getNodeName();//IFLAG, PARAMETER
																																				String subNodeValue = subTempNodeIP.getTextContent();
																																				if(subTempNodeIP.hasAttributes()) 
																																    			{
																																					NamedNodeMap nodeMap = subTempNodeIP.getAttributes();
																																    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																    				{
																																    					
																																    					Node node = nodeMap.item(nodeMapIndex);
																																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																																    					{
																																    						paramCode = node.getNodeValue();
																																    					}
																																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																																    					{
																																    						paramValue = node.getNodeValue();
																																    					}
																																    				}
																																    			}
																																			 }	
																																			 if(manufacturerCode.equalsIgnoreCase("1")||manufacturerName.equalsIgnoreCase("SECURE METERS LTD."))
																																			 {

																																					//KVA
																																					 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-0-0")||paramCode.equalsIgnoreCase("P7-4-18-0-0")||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																					 {
																																						 //System.out.println("====== "+Float.parseFloat(paramValue));
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4KvaValue = paramValue;
																																						if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																							d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																							
																																									
																																						float tempKva = Float.parseFloat(paramValue);
																																						 
																																						 if(sumKvaFlag)
																																						 {
																																							 sumKva = sumKva + tempKva;
																																							 sumKvaFlag = false;
																																						 }
																																						 if(tempKva > maxKva)
																																						 {
																																							 maxKva = tempKva;
																																						 }
																																						 
																																						 if(minKva == 0)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 if(tempKva < minKva)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 
																																					 }
																																					 
																																					//'P7-1-5-1-0','P7-1-18-0-0'
																																					//KWH(Pradeep changes)
																																					 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-18-0-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																					 {
																																						 System.out.println("====== "+Float.parseFloat(paramValue));
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						 d4KwhValue = paramValue;
																																						 float tempKwh = Float.parseFloat(paramValue);
																																						 if(sumKwhFlag)
																																						 {
																																							 sumKwh = sumKwh + tempKwh;
																																							 sumKwhFlag = false;
																																						 }
																																						 kwhFlag = 1;
																																						/* if(tempKwh > maxKwh)
																																						 {
																																							 maxKwh = tempKwh;
																																						 }*/
																																						 
																																					 }
																																					 
																																					 //D4VRphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4vrValue = paramValue;
																																					 }
																																					 //D4VYphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vyValue= paramValue;
																																					 }
																																					 //D4VBphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vbValue= paramValue;
																																					 }
																																					//D4ARphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4arValue = paramValue;
																																					 }
																																					 //D4AYphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4ayValue= paramValue;
																																					 }
																																					 //D4ABphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4abValue= paramValue;
																																					 }
																																					
																																				 
																																			 }
																																			 else  if(manufacturerCode.equalsIgnoreCase("3")||manufacturerName.equalsIgnoreCase("LARSEN AND TOUBRO LIMITED"))
																																			 {
																																				//FOR L&T
																																					//KVA
																																					 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")|| paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-1-0") || paramCode.equalsIgnoreCase("P7-4-5-2-0")||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																					 {
																																						 System.out.println("====== "+Float.parseFloat(paramValue));
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						 //*2
																																						 d4KvaValue = paramValue;
																																						 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																								d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																						 float tempKva = Float.parseFloat(paramValue);
																																						 
																																						 if(sumKvaFlag)
																																						 {
																																							 sumKva = sumKva + tempKva;
																																							 sumKvaFlag = false;
																																						 }
																																						 
																																						 if(tempKva > maxKva)
																																						 {
																																							 maxKva = tempKva;
																																						 }
																																						 
																																						 if(minKva == 0)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 if(tempKva < minKva)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 
																																					 }
																																					//'P7-1-5-1-0','P7-1-18-0-0'
																																						//KWH
																																						 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-18-0-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																						 {
																																							 //System.out.println("====== "+Float.parseFloat(paramValue));
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							 float tempKwh = Float.parseFloat(paramValue);
																																							 d4KwhValue = paramValue;
																																							 if(sumKwhFlag)
																																							 {
																																								 sumKwh = sumKwh + tempKwh;
																																								 sumKwhFlag = false;
																																							 }
																																							 kwhFlag = 1;
																																							 /*if(tempKwh > maxKwh)
																																							 {
																																								 maxKwh = tempKwh;
																																							 }*/
																																							 
																																						 }
																																						//D4VRphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																						 {
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							d4vrValue = paramValue;
																																						 }
																																						 //D4VYphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4vyValue= paramValue;
																																						 }
																																						 //D4VBphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4vbValue= paramValue;
																																						 }
																																						//D4ARphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																						 {
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							d4arValue = paramValue;
																																						 }
																																						 //D4AYphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4ayValue= paramValue;
																																						 }
																																						 //D4ABphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4abValue= paramValue;
																																						 }
																																			 }
																																			 else  if(manufacturerCode.equalsIgnoreCase("4"))
																																			 {
																																				//FOR Genus
																																					//KVA
																																					 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-1-0") || paramCode.equalsIgnoreCase("P7-4-5-2-0") ||
																																							 paramCode.equalsIgnoreCase("P7-6-5-2-4") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																					 {
																																						 //System.out.println("====== "+Float.parseFloat(paramValue));
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						 //*2
																																						 d4KvaValue = paramValue;
																																						 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																								d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																						 float tempKva = Float.parseFloat(paramValue);
																																						 
																																						 
																																						 if(sumKvaFlag)
																																						 {
																																							 sumKva = sumKva + tempKva;
																																							 sumKvaFlag = false;
																																						 }
																																						 
																																						 if(tempKva > maxKva)
																																						 {
																																							 maxKva = tempKva;
																																						 }
																																						 
																																						 if(minKva == 0)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 if(tempKva < minKva)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 
																																					 }
																																					 
																																					//'P7-1-5-1-0','P7-1-18-0-0'
																																						//KWH
																																						 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-18-0-0")||
																																								 paramCode.equalsIgnoreCase("P7-1-5-2-0") || paramCode.equalsIgnoreCase("P7-1-13-2-0")||
																																								 paramCode.equalsIgnoreCase("P7-1-5-2-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																						 {
																																							 //System.out.println("====== "+Float.parseFloat(paramValue));
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							 float tempKwh = Float.parseFloat(paramValue);
																																							 d4KwhValue = paramValue;
																																							 if(sumKwhFlag)
																																							 {
																																								 sumKwh = sumKwh + tempKwh;
																																								 sumKwhFlag = false;
																																							 }
																																							 kwhFlag = 1;
																																							 /*if(tempKwh > maxKwh)
																																							 {
																																								 maxKwh = tempKwh;
																																							 }*/
																																							 
																																						 }
																																						//D4VRphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																						 {
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							d4vrValue = paramValue;
																																						 }
																																						 //D4VYphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4vyValue= paramValue;
																																						 }
																																						 //D4VBphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4vbValue= paramValue;
																																						 }
																																						//D4ARphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																						 {
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							d4arValue = paramValue;
																																						 }
																																						 //D4AYphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4ayValue= paramValue;
																																						 }
																																						 //D4ABphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4abValue= paramValue;
																																						 }
																																						 
																																			 }
																																			 if(manufacturerCode.equalsIgnoreCase("101"))
																																			 {

																																					//KVA
																																					 if(paramCode.equalsIgnoreCase("P7-6-13-0-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4KvaValue = paramValue;
																																						float tempKva = Float.parseFloat(paramValue);
																																						 
																																						 if(sumKvaFlag)
																																						 {
																																							 sumKva = sumKva + tempKva;
																																							 sumKvaFlag = false;
																																						 }
																																						 if(tempKva > maxKva)
																																						 {
																																							 maxKva = tempKva;
																																						 }
																																						 
																																						 if(minKva == 0)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 if(tempKva < minKva)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 
																																					 }
																																					//KWH
																																					 if(paramCode.equalsIgnoreCase("P7-1-18-2-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						 d4KwhValue = paramValue;
																																						 float tempKwh = Float.parseFloat(paramValue);
																																						 if(sumKwhFlag)
																																						 {
																																							 sumKwh = sumKwh + tempKwh;
																																							 sumKwhFlag = false;
																																						 }
																																						 kwhFlag = 1;
																																						/* if(tempKwh > maxKwh)
																																						 {
																																							 maxKwh = tempKwh;
																																						 }*/
																																						 
																																					 }
																																					//D4VRphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4vrValue = paramValue;
																																					 }
																																					 //D4VYphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vyValue= paramValue;
																																					 }
																																					 //D4VBphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vbValue= paramValue;
																																					 }
																																					//D4ARphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4arValue = paramValue;
																																					 }
																																					 //D4AYphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4ayValue= paramValue;
																																					 }
																																					 //D4ABphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4abValue= paramValue;
																																					 }
																																			 
																																			 }
																																				else
																																				{
																																							//FOR HPL
																																								//KVA
																																								 if(paramCode.equalsIgnoreCase("P7-6-13-2-0")||paramCode.equalsIgnoreCase("P7-6-13-1-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0")||
																																										paramCode.equalsIgnoreCase("P7-3-5-2-0"))
																																								 {
																																									 //System.out.println("====== "+Float.parseFloat(paramValue));
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									 //*2
																																									 d4KvaValue = paramValue;
																																									 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																											d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																									 float tempKva = Float.parseFloat(paramValue);
																																									
																																									 if(sumKvaFlag)
																																									 {
																																										 sumKva = sumKva + tempKva;
																																										 sumKvaFlag = false;
																																									 }
																																									 
																																									 if(tempKva > maxKva)
																																									 {
																																										 maxKva = tempKva;
																																									 }
																																									 
																																									 if(minKva == 0)
																																									 {
																																										 minKva = tempKva;
																																									 }
																																									 if(tempKva < minKva)
																																									 {
																																										 minKva = tempKva;
																																									 }
																																									 
																																								 }
																																								 
																																								//'P7-1-5-1-0','P7-1-18-0-0'
																																									//KWH
																																									 if(paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-1-13-1-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																									 {
																																										 //System.out.println("====== "+Float.parseFloat(paramValue));
																																										 if( paramValue == "")
																																										 {
																																											 paramValue = "0";
																																										 }
																																										 float tempKwh = Float.parseFloat(paramValue);
																																										 d4KwhValue = paramValue;
																																										 if(sumKwhFlag)
																																										 {
																																											 sumKwh = sumKwh + tempKwh;
																																											 sumKwhFlag = false;
																																										 }
																																										 kwhFlag = 1;
																																										 /*if(tempKwh > maxKwh)
																																										 {
																																											 maxKwh = tempKwh;
																																										 }*/
																																										 
																																									 }
																																									//D4VRphase value
																																									 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																									 {
																																										 if( paramValue == "")
																																										 {
																																											 paramValue = "0";
																																										 }
																																										d4vrValue = paramValue;
																																									 }
																																									 //D4VYphase value
																																									 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																									 {
																																										 if(paramValue == "")
																																										 {
																																											 paramValue="0";
																																										 }
																																										 d4vyValue= paramValue;
																																									 }
																																									 //D4VBphase value
																																									 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																									 {
																																										 if(paramValue == "")
																																										 {
																																											 paramValue="0";
																																										 }
																																										 d4vbValue= paramValue;
																																									 }
																																									//D4ARphase value
																																									 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																									 {
																																										 if( paramValue == "")
																																										 {
																																											 paramValue = "0";
																																										 }
																																										d4arValue = paramValue;
																																									 }
																																									 //D4AYphase value
																																									 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																									 {
																																										 if(paramValue == "")
																																										 {
																																											 paramValue="0";
																																										 }
																																										 d4ayValue= paramValue;
																																									 }
																																									 //D4ABphase value
																																									 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																									 {
																																										 if(paramValue == "")
																																										 {
																																											 paramValue="0";
																																										 }
																																										 d4abValue= paramValue;
																																									 }
																																							 
																																						 }//End HPL
																																						 
																																						//Load Utilization values
																																						 /*if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																								 paramCode.equalsIgnoreCase("P7-3-5-1-0"))*/
																																						 //HPL
																																						 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																								 paramCode.equalsIgnoreCase("P7-3-5-1-0")||paramCode.equalsIgnoreCase("P7-6-13-2-0")
																																								 ||paramCode.equalsIgnoreCase("P7-6-13-1-0") || paramCode.equalsIgnoreCase("P7-6-5-2-4") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																						 {
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							 
																																							 float tempKva = Float.parseFloat(paramValue);
																																							 if(Integer.parseInt(intervalPeriod) > 15)
																																							 {
																																								 if((ipIntervalNum >= 18) && (ipIntervalNum <= 33))
																																								 {
																																									 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																									 if(tempKva <= cd_20)
																																									 {
																																										 kva1_lt_cd20++;
																																									 }
																																									 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																									 {
																																										 kva1_lt_cd40++;
																																									 }
																																									 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																									 {
																																										 kva1_lt_cd60++;
																																									 }
																																									 else if(tempKva > cd_60)
																																									 {
																																										 kva1_gt_cd60++;
																																									 }
																																								 }
																																								 else if((ipIntervalNum < 18) || (ipIntervalNum >= 34))
																																								 {
																																									 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																									 if(tempKva <= cd_20)
																																									 {
																																										 kva2_lt_cd20++;
																																									 }
																																									 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																									 {
																																										 kva2_lt_cd40++;
																																									 }
																																									 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																									 {
																																										 kva2_lt_cd60++;
																																									 }
																																									 else if(tempKva > cd_60)
																																									 {
																																										 kva2_gt_cd60++;
																																									 }
																																								 }
																																							 }
																																							 else{
																																								 if((ipIntervalNum >= 36) && (ipIntervalNum <= 68))
																																								 {

																																									 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																									 if(tempKva <= cd_20)
																																									 {
																																										 kva1_lt_cd20++;
																																									 }
																																									 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																									 {
																																										 kva1_lt_cd40++;
																																									 }
																																									 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																									 {
																																										 kva1_lt_cd60++;
																																									 }
																																									 else if(tempKva > cd_60)
																																									 {
																																										 kva1_gt_cd60++;
																																									 }
																																								 
																																								 }
																																								 else if((ipIntervalNum <= 35) || (ipIntervalNum >= 69))
																																								 {

																																									 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																									 if(tempKva <= cd_20)
																																									 {
																																										 kva2_lt_cd20++;
																																									 }
																																									 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																									 {
																																										 kva2_lt_cd40++;
																																									 }
																																									 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																									 {
																																										 kva2_lt_cd60++;
																																									 }
																																									 else if(tempKva > cd_60)
																																									 {
																																										 kva2_gt_cd60++;
																																									 }
																																								 
																																								 }
																																							 }
																																						 }
																																						 if(paramCode.equalsIgnoreCase("P4-4-4-1-0")||paramCode.equalsIgnoreCase("P4-4-4-0-0"))
																																						 {
																																							 //System.out.println("====== "+Float.parseFloat(paramValue));
																																							if(paramValue.equalsIgnoreCase("") )
																																							{
																																								paramValue = "0";
																																							}
																																							
																																							 float tempPf = Float.parseFloat(paramValue);
																																								
																																							 sumPf = sumPf + tempPf;
																																							 
																																							 //pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0;
																																							 d4PfValue = paramValue;
																																							 if(tempPf == 0)
																																							 {
																																								pfNoLoad++; 
																																							 }
																																							 if(tempPf == -1)
																																							 {
																																								pfBlackOut++; 
																																							 }
																																							 if((tempPf != 0) && (tempPf != -1))
																																							 {
																																								 if(tempPf <= 0.5F)
																																								 {
																																									 pfLt05++;
																																								 }
																																								 else if((tempPf > 0.5F) && (tempPf < 0.7F))
																																								 {
																																									 pf05To07++;
																																									// pfVal1 = pfVal1 + tempPf + ",";
																																								 }
																																								 else if((tempPf >= 0.7F) && (tempPf <= 0.9F))
																																								 {
																																									 pf07To09++;
																																									 //pfVal2 = pfVal2 + tempPf + ",";
																																								 }
																																								 else if(tempPf >0.9F)
																																								 {
																																									 pfGt09++;
																																								 }
																																							 }
																																							 /*if(tempPf > maxPf)
																																							 {
																																								 maxPf = tempPf;
																																							 }*/
																																							
																																						 }
																																						 
																																						 if(paramCode.equalsIgnoreCase("P7-2-4-2-0"))
																																						 {
																																							 if(paramValue.equalsIgnoreCase("") )
																																								{
																																									paramValue = "0";
																																								}
																																							 else{
																																								 d4loadkvarh_lead= Double.parseDouble(paramValue);
																																							 }
																																						 }
																																						 //System.err.println("paramCodecccccc--------------"+paramCode);
																																						 if(paramCode.equalsIgnoreCase("P7-2-1-2-0"))
																																						 {
																																							 if(paramValue.equalsIgnoreCase("") )
																																								{
																																									paramValue = "0";
																																								}
																																							 else{
																																								 d4loadkvarh_lag = Double.parseDouble(paramValue);
																																							 }
																																						 }
																																						 if(paramCode.equalsIgnoreCase("P7-1-5-2-0"))
																																						 {
																																							 if(paramValue.equalsIgnoreCase("") )
																																								{
																																									paramValue = "0";
																																								}else if(paramValue==null||paramValue=="null")
																																								{
																																									d4loadkwh=0.0;
																																								}
																																							 else{
																																								  d4loadkwh= Double.parseDouble(paramValue);
																																							 }
																																						 }
																																						 
																																						 if(paramCode.equalsIgnoreCase("P7-3-5-2-0"))
																																						 {
																																							 if(paramValue.equalsIgnoreCase("") )
																																								{
																																									paramValue = "0";
																																								}
																																							 else{
																																								  d4loadkvah = Double.parseDouble(paramValue);
																																							 }
																																						 }
																																						 if(paramCode.equalsIgnoreCase("P7-4-5-2-0"))
																																						 {
																																							 if(paramValue.equalsIgnoreCase("") )
																																								{
																																									paramValue = "0";
																																								}
																																							 else{
																																								  d4loadkw = Double.parseDouble(paramValue);
																																							 }
																																						 }
																																						 if(paramCode.equalsIgnoreCase("P7-6-5-2-0"))
																																						 {
																																							 if(paramValue.equalsIgnoreCase("") )
																																								{
																																									paramValue = "0";
																																								}
																																							else{
																																								  d4loadkva = Double.parseDouble(paramValue);
																																							 }
																																						 }
																																						 Double val=paramValue==null?0:Double.parseDouble(paramValue);
																														                                    ParamCodeValidator validator=new ParamCodeValidator();

																																						 if(paramCode!=null) {
																															                                    
																															                                    String code=validator.readD4tags(paramCode);
																															                                   
																															                                    if("i_r".equals(code)) {
																															                                    	d4.setiR(val);
																															                                    }
																															                                    else if ("i_y".equals(code)) {
																															                                    	d4.setiY(val);
																															                                    }
																															                                    else if ("i_b".equals(code)) {
																															                                    	d4.setiB(val);
																															                                    }
																															                                    else if("v_r".equals(code)) {
																															                                    	d4.setvR(val);
																															                                    }
																															                                    else if ("v_y".equals(code)) {
																															                                    	d4.setvY(val);
																															                                    }
																															                                    else if ("v_b".equals(code)) {
																															                                    	d4.setvB(val);
																															                                    }
																															                                    else if ("kvarh_lag".equals(code)) {
																															                                    	d4.setKvarhLag(val);
																															                                    }
																															                                    else if ("kvarh_lead".equals(code)) {
																															                                    	d4.setKvarhLead(val);
																															                                    }
																															                                    else if ("kwh".equals(code)) {
																															                                    	d4.setKwh(val);
																															                                    }
																															                                    else if ("kvah".equals(code)) {
																															                                    	d4.setKvah(val);
																															                                    }
																															                                    else if ("frequency".equals(code)) {
																															                                    	d4.setFrequency(val);
																															                                    }
																															                                    
																															                                    }
																																						 
																																			 }
																																		 
																																		
																																		 
																																		 if(Integer.parseInt(ipInterval)!=0){
																																		 d4.setBillmonth(billmonth+"");d4.setCdfId(cdfId);d4.setMeterNo(meterNumber);
																																		 d4.setIpInterval(Integer.parseInt(ipInterval));
																																		
																																		 //calendar.add(Calendar.MINUTE, 30);
																																		  d4.setDayProfileDate(new Timestamp((calendar.getTime()).getTime()));
																																		 d4.setKvarhLag(d4loadkvarh_lag);
																										                                	d4.setKvarhLead(d4loadkvarh_lead);
																										                                	d4.setKwh(d4loadkwh);
																										                                	d4.setKvah(d4loadkvah);
																										                                	if(d4KwhValue!=""||d4KwhValue!=null)
																										                                	{
																										                                		System.err.println("d4KwhValue=================>"+d4KwhValue);
																										                                		d4.setKwhValue(d4KwhValue);
																										                                	}else
																										                                	{
																										                                		System.err.println("else=================>"+d4loadkwh.toString());
																										                                		d4.setKwhValue(d4loadkwh.toString());
																										                                	}
																																		 d4.setKvaValue(d4KvaValue);d4.setPfValue(d4PfValue);d4.setKwhValue(d4loadkwh.toString());
																																		 d4.setVrValue(d4vrValue);d4.setVyValue(d4vyValue);d4.setVbValue(d4vbValue);
																																		 d4.setArValue(d4arValue);d4.setAyValue(d4ayValue);d4.setAbValue(d4abValue);
																																		 d4.setIntervalPeriod(intervalPeriod);
																																		 d4LoadDataService.save(d4);
																																		 
																																		 }
																																		 calendar.add(Calendar.MINUTE, 30);
																																		 d4KvaValue = "";
																																		 d4KwhValue = "";
																																		 d4PfValue = "";
																																		 ipInterval = "";
																																		 d4vrValue="";
																																		 d4vyValue="";d4vbValue="";d4arValue="";d4ayValue="";d4abValue="";
																																		
																																	}//IP ForLoop
																																//assign all variables to zero
																																
																																	//Power Factor Report
																																	int intervalVal = Integer.parseInt(intervalPeriod);
																																	
																																	maxKva = Math.round(maxKva * 1000.0)/1000.0;
																																	minKva = Math.round(minKva * 1000.0)/1000.0;
																																	sumKwh = Math.round(sumKwh * 1000.0)/1000.0;
																																	sumPf = Math.round(sumPf * 1000.0)/1000.0;
																																	sumKva = Math.round(sumKva * 1000.0)/1000.0;
																																	
																																	D4Data d4 = new D4Data();
																																	
																																	d4.setCdfId(cdfId);d4.setIntervalPeriod(intervalVal);
																																	if(dayProfileDate.length() > 2)
																																	{
																																	 String checkForDatePatter1 = dayProfileDate.substring(2);
																																	 if(checkForDatePatter1.startsWith("-"))
																																		{
																																		 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																		 
																																		}
																																		else if(checkForDatePatter1.startsWith("/"))
																																		{
																																			d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																			 
																																		}
																																		else
																																		{
																																			 dayProfileDate = "";
																																		}
																																	}
																																    
																																    d4.setMinKva(minKva);d4.setMaxKva(maxKva);d4.setSumKwh(sumKwh);
																															        d4.setKwhFlag(kwhFlag);d4.setSumPf(sumPf);d4.setPf05(pfLt05);
																															        d4.setPf0507(pf05To07);d4.setPf0709(pf07To09);d4.setPf09(pfGt09);
																															        d4.setPfNoload(pfNoLoad);d4.setPfBlackout(pfBlackOut);
																															        d4.setIpGs20(kva1_lt_cd20);d4.setIpGs2040(kva1_lt_cd40);
																															        d4.setIpGs4060(kva1_lt_cd60);d4.setIpGs60(kva1_gt_cd60);
																															        d4.setIpOutGs20(kva2_lt_cd20);d4.setIpOutGs2040(kva2_lt_cd40);
																															        d4.setIpOutGs4060(kva2_lt_cd60);d4.setIpOutGs60(kva2_gt_cd60);
																															        d4.setMf(mf);d4.setCd(cd);d4.setSumKva(sumKva);
																															       // d4DtataService.save(d4);
																															        kwhFlag = 0;
																																	maxKva = 0; minKva = 0; sumKwh = 0; sumPf = 0;sumKva = 0;
																																	pfLt05 = 0; pf05To07 = 0; pf07To09 = 0; pfGt09 = 0;  pfNoLoad = 0; pfBlackOut = 0;
																																	
																																	kva1_lt_cd20 = 0;kva1_lt_cd40 = 0;kva1_lt_cd60 = 0;kva1_gt_cd60 = 0;
																																	kva2_lt_cd20 = 0;kva2_lt_cd40 = 0;kva2_lt_cd60 = 0;kva2_gt_cd60 = 0;
																																	kva3_lt_cd20 = 0;kva3_lt_cd40 = 0;kva3_lt_cd60 = 0;kva3_gt_cd60 = 0;
																																	kva4_lt_cd20 = 0;kva4_lt_cd40 = 0;kva4_lt_cd60 = 0;kva4_gt_cd60 = 0;
																															 }
																														 }//If Previous Data Der
																														 else
																														 {
																															    int kwhFlag = 0;
																																NodeList subTempNodeListD4 = tempNodeD4.getChildNodes();
																																 Date datetime=null;
																																if(dayProfileDate.length() > 2)
																																	{
																																	 String checkForDatePatter1 = dayProfileDate.substring(2);
																																	 if(checkForDatePatter1.startsWith("-"))
																																	 {
																																		// d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																		 datetime=sdf3.parse(dayProfileDate);
																																		 calendar.setTime(datetime);
																																		 //calendar.add(Calendar.MINUTE, 30);
																																		}
																																		else if(checkForDatePatter1.startsWith("/"))
																																		{
																																		//	d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																			 datetime=sdf4.parse(dayProfileDate);
																																			 calendar.setTime(datetime);
																																		//	 calendar.add(Calendar.MINUTE, 30);
																																		}
																																		else
																																		{
																																			 dayProfileDate = "";
																																		}
																																	}
																																for (int subCountD4 = 0; subCountD4 < subTempNodeListD4.getLength(); subCountD4++) 
																																{
																																	String ipInterval = "0";
																																	int ipIntervalNum = 0;
																																	 Node subTempNode = subTempNodeListD4.item(subCountD4);
																																	 if (subTempNode.getNodeType() == Node.ELEMENT_NODE) 
																																	 {
																																		 if(subTempNode.hasAttributes()) 
																															    			{
																															    	 			NamedNodeMap nodeMap = subTempNode.getAttributes();
																															    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																															    				{
																															    					Node node = nodeMap.item(nodeMapIndex);
																															    					System.out.println("ipinterval befor node.getNodeValue==>"+ipInterval);
																															    					ipInterval = node.getNodeValue();
																															    					System.out.println("ipinterval after node.getNodeValue==>"+ipInterval);
																															    					System.out.println(" IP interval attr name : " + node.getNodeName()+" attr value : " + ipInterval);
																															    																				    					
																															    				}
																															    			}
																																		 
																																	 }
																																	 ipIntervalNum = Integer.parseInt(ipInterval);
																																	 NodeList subTempNodeListIP = subTempNode.getChildNodes();
																																	 boolean sumKwhFlag = true;
																																	 boolean sumKvaFlag = true;
																																	 D4CdfData d4 = new D4CdfData();
																																	 for (int subCountIP = 0; subCountIP < subTempNodeListIP.getLength(); subCountIP++) 
																																		{
																																		    String tagAttrId = "";
																																			String paramCode = "", paramValue = "";
																																			Node subTempNodeIP = subTempNodeListIP.item(subCountIP);
																																			 if (subTempNodeIP.getNodeType() == Node.ELEMENT_NODE) 
																																			 {
																																				 if(subTempNodeIP.hasAttributes()) 
																																	    			{
																																					 NamedNodeMap nodeMap = subTempNodeIP.getAttributes();
																																	    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																	    				{
																																	    					Node node = nodeMap.item(nodeMapIndex);
																																	    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																																	    					{
																																	    						paramCode = node.getNodeValue();
																																	    					}
																																	    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																																	    					{
																																	    						paramValue = node.getNodeValue();
																																	    					}
																																	    				}
																																					 
																																	    			}
																																				 
																																			 }
																																			 
																																			 if(manufacturerCode.equalsIgnoreCase("1")||manufacturerName.equalsIgnoreCase("SECURE METERS LTD."))
																																			 {

																																					//KVA
																																					 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-0-0")||paramCode.equalsIgnoreCase("P7-4-18-0-0") ||
																																							 paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																					 {
																																						 //System.out.println("====== "+Float.parseFloat(paramValue));
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						//*2
																																						 d4KvaValue = paramValue;
																																						 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																								d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																						
																																						float tempKva = Float.parseFloat(paramValue);
																																						 
																																						 if(sumKvaFlag)
																																						 {
																																							 sumKva = sumKva + tempKva;
																																							 sumKvaFlag = false;
																																						 }
																																						 if(tempKva > maxKva)
																																						 {
																																							 maxKva = tempKva;
																																						 }
																																						 
																																						 if(minKva == 0)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 if(tempKva < minKva)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 
																																					 }
																																					 
																																					//'P7-1-5-1-0','P7-1-18-0-0'
																																					//KWH
																																					 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-18-0-0")||
																																							 paramCode.equalsIgnoreCase("P7-1-18-1-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0"))
																																					 {
																																						 //System.out.println("====== "+Float.parseFloat(paramValue));
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						 d4KwhValue = paramValue;
																																						  float tempKwh = Float.parseFloat(paramValue);
																																						 if(sumKwhFlag)
																																						 {
																																							 sumKwh = sumKwh + tempKwh;
																																							 sumKwhFlag = false;
																																						 }
																																						 kwhFlag = 1;
																																						/* if(tempKwh > maxKwh)
																																						 {
																																							 maxKwh = tempKwh;
																																						 }*/
																																						 
																																					 }
																																					//D4VRphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4vrValue = paramValue;
																																					 }
																																					 //D4VYphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vyValue= paramValue;
																																					 }
																																					 //D4VBphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vbValue= paramValue;
																																					 }
																																					//D4ARphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4arValue = paramValue;
																																					 }
																																					 //D4AYphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4ayValue= paramValue;
																																					 }
																																					 //D4ABphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4abValue= paramValue;
																																					 }
																																				}
																																			 else  if(manufacturerCode.equalsIgnoreCase("3")||manufacturerName.equalsIgnoreCase("LARSEN AND TOUBRO LIMITED"))
																																			 {
																																				//FOR L&T
																																					//KVA
																																					 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-1-0") || paramCode.equalsIgnoreCase("P7-4-5-2-0") ||
																																							 paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																					 {
																																						 //System.out.println("====== "+Float.parseFloat(paramValue));
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }d4KvaValue = paramValue;
																																						 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																								d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																						 
																																						 float tempKva = Float.parseFloat(paramValue);
																																						 
																																						 if(sumKvaFlag)
																																						 {
																																							 sumKva = sumKva + tempKva;
																																							 sumKvaFlag = false;
																																						 }
																																						 
																																						 if(tempKva > maxKva)
																																						 {
																																							 maxKva = tempKva;
																																						 }
																																						 
																																						 if(minKva == 0)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 if(tempKva < minKva)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 
																																					 }
																																					//'P7-1-5-1-0','P7-1-18-0-0'
																																						//KWH
																																						 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-18-0-0")||
																																								 paramCode.equalsIgnoreCase("P7-1-5-2-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																						 {
																																							 //System.out.println("====== "+Float.parseFloat(paramValue));
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							 float tempKwh = Float.parseFloat(paramValue);
																																							 d4KwhValue = paramValue;
																																							 if(sumKwhFlag)
																																							 {
																																								 sumKwh = sumKwh + tempKwh;
																																								 sumKwhFlag = false;
																																							 }
																																							 kwhFlag = 1;
																																							 /*if(tempKwh > maxKwh)
																																							 {
																																								 maxKwh = tempKwh;
																																							 }*/
																																							 
																																						 }
																																						//D4VRphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																						 {
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							d4vrValue = paramValue;
																																						 }
																																						 //D4VYphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4vyValue= paramValue;
																																						 }
																																						 //D4VBphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4vbValue= paramValue;
																																						 }
																																						//D4ARphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																						 {
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							d4arValue = paramValue;
																																						 }
																																						 //D4AYphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4ayValue= paramValue;
																																						 }
																																						 //D4ABphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4abValue= paramValue;
																																						 }
																																						 
																																			 }
																																			 else  if(manufacturerCode.equalsIgnoreCase("4"))
																																			 {
																																				//FOR Genus
																																					//KVA
																																					 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-1-0") || paramCode.equalsIgnoreCase("P7-4-5-2-0") ||
																																							 paramCode.equalsIgnoreCase("P7-6-5-2-4")  ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																					 {
																																						 //System.out.println("====== "+Float.parseFloat(paramValue));
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }d4KvaValue = paramValue;
																																						 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																								d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																						 float tempKva = Float.parseFloat(paramValue);
																																						 
																																						 if(sumKvaFlag)
																																						 {
																																							 sumKva = sumKva + tempKva;
																																							 sumKvaFlag = false;
																																						 }
																																						 
																																						 if(tempKva > maxKva)
																																						 {
																																							 maxKva = tempKva;
																																						 }
																																						 
																																						 if(minKva == 0)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 if(tempKva < minKva)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 
																																					 }
																																					 
																																					//'P7-1-5-1-0','P7-1-18-0-0'
																																						//KWH
																																						 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-18-0-0")||
																																								 paramCode.equalsIgnoreCase("P7-1-5-2-0") || paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																						 {
																																							 //System.out.println("====== "+Float.parseFloat(paramValue));
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							 float tempKwh = Float.parseFloat(paramValue);
																																							 d4KwhValue = paramValue;
																																							 if(sumKwhFlag)
																																							 {
																																								 sumKwh = sumKwh + tempKwh;
																																								 sumKwhFlag = false;
																																							 }
																																							 kwhFlag = 1;
																																							 /*if(tempKwh > maxKwh)
																																							 {
																																								 maxKwh = tempKwh;
																																							 }*/
																																							 
																																						 }
																																						//D4VRphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																						 {
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							d4vrValue = paramValue;
																																						 }
																																						 //D4VYphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4vyValue= paramValue;
																																						 }
																																						 //D4VBphase value
																																						 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4vbValue= paramValue;
																																							 //System.out.println("d4vbValue-->"+d4vbValue);
																																						 }
																																						//D4ARphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																						 {
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							d4arValue = paramValue;
																																						 }
																																						 //D4AYphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4ayValue= paramValue;
																																						 }
																																						 //D4ABphase value
																																						 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																						 {
																																							 if(paramValue == "")
																																							 {
																																								 paramValue="0";
																																							 }
																																							 d4abValue= paramValue;
																																						 }
																																			}
																																			 
																																			 if(manufacturerCode.equalsIgnoreCase("101"))
																																			 {

																																					//KVA
																																					 if(paramCode.equalsIgnoreCase("P7-6-13-0-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4KvaValue = paramValue;
																																						float tempKva = Float.parseFloat(paramValue);
																																						 
																																						 if(sumKvaFlag)
																																						 {
																																							 sumKva = sumKva + tempKva;
																																							 sumKvaFlag = false;
																																						 }
																																						 if(tempKva > maxKva)
																																						 {
																																							 maxKva = tempKva;
																																						 }
																																						 
																																						 if(minKva == 0)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 if(tempKva < minKva)
																																						 {
																																							 minKva = tempKva;
																																						 }
																																						 
																																					 }
																																					//KWH
																																					 if(paramCode.equalsIgnoreCase("P7-1-18-2-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						 d4KwhValue = paramValue;
																																						 float tempKwh = Float.parseFloat(paramValue);
																																						 if(sumKwhFlag)
																																						 {
																																							 sumKwh = sumKwh + tempKwh;
																																							 sumKwhFlag = false;
																																						 }
																																						 kwhFlag = 1;
																																						/* if(tempKwh > maxKwh)
																																						 {
																																							 maxKwh = tempKwh;
																																						 }*/
																																						 
																																					 }
																																					//D4VRphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4vrValue = paramValue;
																																					 }
																																					 //D4VYphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vyValue= paramValue;
																																					 }
																																					 //D4VBphase value
																																					 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4vbValue= paramValue;
																																					 }
																																					//D4ARphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						d4arValue = paramValue;
																																					 }
																																					 //D4AYphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4ayValue= paramValue;
																																					 }
																																					 //D4ABphase value
																																					 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																					 {
																																						 if(paramValue == "")
																																						 {
																																							 paramValue="0";
																																						 }
																																						 d4abValue= paramValue;
																																					 }
																																			 
																																			 }
																																			 
																																			 else{

																																					//FOR HPL
																																						//KVA
																																				 System.out
																																						.println("comeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
																																						 if(paramCode.equalsIgnoreCase("P7-6-13-2-0")||paramCode.equalsIgnoreCase("P7-6-13-1-0") ||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																								 paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																						 {
																																							 //System.out.println("====== "+Float.parseFloat(paramValue));
																																							 if( paramValue == "")
																																							 {
																																								 paramValue = "0";
																																							 }
																																							 d4KvaValue = paramValue;
																																							// System.out.println("d4KvaValue===="+d4KvaValue);
																																							 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																									d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																							 //System.out.println("d4KvaValue===="+d4KvaValue);
																																									
																																							 float tempKva = Float.parseFloat(paramValue);
																																							 
																																							 if(sumKvaFlag)
																																							 {
																																								 sumKva = sumKva + tempKva;
																																								 sumKvaFlag = false;
																																							 }
																																							 
																																							 if(tempKva > maxKva)
																																							 {
																																								 maxKva = tempKva;
																																							 }
																																							 
																																							 if(minKva == 0)
																																							 {
																																								 minKva = tempKva;
																																							 }
																																							 if(tempKva < minKva)
																																							 {
																																								 minKva = tempKva;
																																							 }
																																							 
																																						 }
																																						 
																																						//'P7-1-5-1-0','P7-1-18-0-0'
																																							//KWH
																																							 if(paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-1-13-1-0")||
																																									 paramCode.equalsIgnoreCase("P7-1-18-1-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 float tempKwh = Float.parseFloat(paramValue);
																																								 d4KwhValue = paramValue;
																																								 if(sumKwhFlag)
																																								 {
																																									 sumKwh = sumKwh + tempKwh;
																																									 sumKwhFlag = false;
																																								 }
																																								 kwhFlag = 1;
																																								 /*if(tempKwh > maxKwh)
																																								 {
																																									 maxKwh = tempKwh;
																																								 }*/
																																								 
																																							 }
																																							//D4VRphase value
																																							 if(paramCode.equalsIgnoreCase("P1-2-1-1-0")||paramCode.equalsIgnoreCase("P1-2-1-4-0"))
																																							 {
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								d4vrValue = paramValue;
																																							 }
																																							 //D4VYphase value
																																							 if(paramCode.equalsIgnoreCase("P1-2-2-1-0")||paramCode.equalsIgnoreCase("P1-2-2-4-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4vyValue= paramValue;
																																							 }
																																							 //D4VBphase value
																																							 if(paramCode.equalsIgnoreCase("P1-2-3-1-0")||paramCode.equalsIgnoreCase("P1-2-3-4-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4vbValue= paramValue;
																																							 }
																																							//D4ARphase value
																																							 if(paramCode.equalsIgnoreCase("P2-1-1-1-0")||paramCode.equalsIgnoreCase("P2-1-1-4-0"))
																																							 {
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								d4arValue = paramValue;
																																							 }
																																							 //D4AYphase value
																																							 if(paramCode.equalsIgnoreCase("P2-1-2-1-0")||paramCode.equalsIgnoreCase("P2-1-2-4-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4ayValue= paramValue;
																																							 }
																																							 //D4ABphase value
																																							 if(paramCode.equalsIgnoreCase("P2-1-3-1-0")||paramCode.equalsIgnoreCase("P2-1-3-4-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4abValue= paramValue;
																																							 }
																																				}
																																			//Load Utilization values
																																			 /*if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																					 paramCode.equalsIgnoreCase("P7-3-5-1-0"))*/
																																			 //HPL
																																			 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																					 paramCode.equalsIgnoreCase("P7-3-5-1-0")||paramCode.equalsIgnoreCase("P7-6-13-2-0")
																																					 ||paramCode.equalsIgnoreCase("P7-6-13-1-0") || paramCode.equalsIgnoreCase("P7-6-5-2-4") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																			 {
																																				 if( paramValue == "")
																																				 {
																																					 paramValue = "0";
																																				 }
																																				 float tempKva = Float.parseFloat(paramValue);
																																				 if(Integer.parseInt(intervalPeriod) > 15)
																																				 {
																																					 if((ipIntervalNum >= 18) && (ipIntervalNum <= 33))
																																					 {
																																						 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																						 if(tempKva <= cd_20)
																																						 {
																																							 kva1_lt_cd20++;
																																						 }
																																						 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																						 {
																																							 kva1_lt_cd40++;
																																						 }
																																						 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																						 {
																																							 kva1_lt_cd60++;
																																						 }
																																						 else if(tempKva > cd_60)
																																						 {
																																							 kva1_gt_cd60++;
																																						 }
																																					 }
																																					 else if((ipIntervalNum < 18) || (ipIntervalNum >= 34))
																																					 {
																																						 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																						 if(tempKva <= cd_20)
																																						 {
																																							 kva2_lt_cd20++;
																																						 }
																																						 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																						 {
																																							 kva2_lt_cd40++;
																																						 }
																																						 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																						 {
																																							 kva2_lt_cd60++;
																																						 }
																																						 else if(tempKva > cd_60)
																																						 {
																																							 kva2_gt_cd60++;
																																						 }
																																					 }
																																				 }
																																				 else{
																																					 if((ipIntervalNum >= 36) && (ipIntervalNum <= 68))
																																					 {

																																						 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																						 if(tempKva <= cd_20)
																																						 {
																																							 kva1_lt_cd20++;
																																						 }
																																						 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																						 {
																																							 kva1_lt_cd40++;
																																						 }
																																						 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																						 {
																																							 kva1_lt_cd60++;
																																						 }
																																						 else if(tempKva > cd_60)
																																						 {
																																							 kva1_gt_cd60++;
																																						 }
																																					 
																																					 }
																																					 else if((ipIntervalNum <= 35) || (ipIntervalNum >= 69))
																																					 {

																																						 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																						 if(tempKva <= cd_20)
																																						 {
																																							 kva2_lt_cd20++;
																																						 }
																																						 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																						 {
																																							 kva2_lt_cd40++;
																																						 }
																																						 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																						 {
																																							 kva2_lt_cd60++;
																																						 }
																																						 else if(tempKva > cd_60)
																																						 {
																																							 kva2_gt_cd60++;
																																						 }
																																					 
																																					 }
																																				 }
																																			 }
																																			 Double val=paramValue==null?0:Double.parseDouble(paramValue);
																											                                    ParamCodeValidator validator=new ParamCodeValidator();

																																			 if(paramCode.equalsIgnoreCase("P4-4-4-1-0")||paramCode.equalsIgnoreCase("P4-4-4-0-0"))
																																			 {
																																				 //System.out.println("====== "+Float.parseFloat(paramValue));
																																				if(paramValue.equalsIgnoreCase("") )
																																				{
																																					paramValue = "0";
																																				}
																																				
																																				 float tempPf = Float.parseFloat(paramValue);
																																					
																																				 sumPf = sumPf + tempPf;
																																				 d4PfValue = paramValue;
																																				 //pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0;
																																				 
																																				 if(tempPf == 0)
																																				 {
																																					pfNoLoad++; 
																																				 }
																																				 if(tempPf == -1)
																																				 {
																																					pfBlackOut++; 
																																				 }
																																				 if((tempPf != 0) && (tempPf != -1))
																																				 {
																																					 if(tempPf <= 0.5F)
																																					 {
																																						 pfLt05++;
																																					 }
																																					 else if((tempPf > 0.5F) && (tempPf < 0.7F))
																																					 {
																																						 pf05To07++;
																																						// pfVal1 = pfVal1 + tempPf + ",";
																																					 }
																																					 else if((tempPf >= 0.7F) && (tempPf <= 0.9F))
																																					 {
																																						 pf07To09++;
																																						 //pfVal2 = pfVal2 + tempPf + ",";
																																					 }
																																					 else if(tempPf >0.9F)
																																					 {
																																						 pfGt09++;
																																					 }
																																				 }
																																				 /*if(tempPf > maxPf)
																																				 {
																																					 maxPf = tempPf;
																																				 }*/
																																				
																																			 }
																																			 System.err.println("paramCode--- "+paramCode);
																																					
																																			 if(paramCode.equalsIgnoreCase("P7-2-4-2-0"))
																																			 {
																																				 if(paramValue.equalsIgnoreCase("") )
																																					{
																																						paramValue = "0";
																																					}
																																				 else{
																																					 d4loadkvarh_lead = Double.parseDouble(paramValue);
																																				 }
																																			 }
																																			 if(paramCode.equalsIgnoreCase("P7-2-1-2-0"))
																																			 {
																																				 if(paramValue.equalsIgnoreCase("") )
																																					{
																																						paramValue = "0";
																																					}
																																				 else{
																																					 d4loadkvarh_lag = Double.parseDouble(paramValue);
																																				 }
																																			 }
																																			 if(paramCode.equalsIgnoreCase("P7-1-5-2-0"))
																																			 {
																																				 if(paramValue.equalsIgnoreCase("") )
																																					{
																																						paramValue = "0";
																																					}
																																				 else{
																																					  d4loadkwh= Double.parseDouble(paramValue);
																																				 }
																																			 }
																																			 
																																			 if(paramCode.equalsIgnoreCase("P7-3-5-2-0"))
																																			 {
																																				 if(paramValue.equalsIgnoreCase("") )
																																					{
																																						paramValue = "0";
																																					}
																																				 else{
																																					  d4loadkvah = Double.parseDouble(paramValue);
																																				 }
																																			 }
																																			 if(paramCode.equalsIgnoreCase("P7-4-5-2-0"))
																																			 {
																																				 if(paramValue.equalsIgnoreCase("") )
																																					{
																																						paramValue = "0";
																																					}
																																				 else{
																																					  d4loadkw = Double.parseDouble(paramValue);
																																				 }
																																			 }
																																			 if(paramCode.equalsIgnoreCase("P7-6-5-2-0"))
																																			 {
																																				 if(paramValue.equalsIgnoreCase("") )
																																					{
																																						paramValue = "0";
																																					}
																																				 else{
																																					  d4loadkva = Double.parseDouble(paramValue);
																																				 }
																																			 }
																																			 
																								
																																	        if(paramCode!=null) {
																												                                    
																												                                    String code=validator.readD4tags(paramCode);
																												                                   
																												                                    if("i_r".equals(code)) {
																												                                    	d4.setiR(val);
																												                                    }
																												                                    else if ("i_y".equals(code)) {
																												                                    	d4.setiY(val);
																												                                    }
																												                                    else if ("i_b".equals(code)) {
																												                                    	d4.setiB(val);
																												                                    }
																												                                    else if("v_r".equals(code)) {
																												                                    	d4.setvR(val);
																												                                    }
																												                                    else if ("v_y".equals(code)) {
																												                                    	d4.setvY(val);
																												                                    }
																												                                    else if ("v_b".equals(code)) {
																												                                    	d4.setvB(val);
																												                                    }
																												                                    else if ("kvarh_lag".equals(code)) {
																												                                    	d4.setKvarhLag(val);
																												                                    }
																												                                    else if ("kvarh_lead".equals(code)) {
																												                                    	d4.setKvarhLead(val);
																												                                    }
																												                                    else if ("kwh".equals(code)) {
																												                                    	d4.setKwh(val);
																												                                    }
																												                                    else if ("kvah".equals(code)) {
																												                                    	d4.setKvah(val);
																												                                    }
																												                                    else if ("frequency".equals(code)) {
																												                                    	d4.setFrequency(val);
																												                                    }
																												                                    
																												                                    }
																																		}
																																	 //System.out.println("ip interval ----->"+ipInterval);
																																	 if(Integer.parseInt(ipInterval)!=0){
																																		// System.out.println("INSIDE SAVE !=0");
																																	 d4.setBillmonth(billmonth+"");d4.setCdfId(cdfId);d4.setMeterNo(meterNumber);
																																	 d4.setIpInterval(Integer.parseInt(ipInterval));
																																	 if(d4KwhValue!=""||d4KwhValue!=null)
																									                                	{
																									                                		d4.setKwhValue(d4KwhValue);
																									                                	}else
																									                                	{
																									                                		d4.setKwhValue(d4loadkwh.toString());
																									                                	}
																																	// d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																	 d4.setKvaValue(d4KvaValue);d4.setPfValue(d4PfValue);d4.setKwhValue(d4loadkwh.toString());
																																	 d4.setVrValue(d4vrValue);d4.setVyValue(d4vyValue);d4.setVbValue(d4vbValue);
																																	 d4.setArValue(d4arValue);d4.setAyValue(d4ayValue);d4.setAbValue(d4abValue);
																																	 d4.setIntervalPeriod(intervalPeriod);
																																	 d4.setKvarhLag(d4loadkvarh_lag);
																									                                	d4.setKvarhLead(d4loadkvarh_lead);
																									                                	d4.setKwh(d4loadkwh);
																									                                	d4.setKvah(d4loadkvah);
																									                                	
																																	// d4.setImei(imeiNo);
																										                                d4.setTimeStamp(new Timestamp(G2dateVal.getTime()));
																										                            //    d4.setReadTime(new Timestamp(G2dateVal.getTime()));
																										                                d4.setDayProfileDate(new Timestamp((calendar.getTime()).getTime()));
																										                                d4.setKvarhQ1(0.0);
																										                                d4.setKvarhQ2(0.0);
																										                                d4.setKvarhQ3(0.0);
																										                                d4.setKvarhQ4(0.0);
																										                                d4.setNetKwh(0.0);
																										                                d4.setTransId("0000000000000");
																										                                d4.setKwhImp(0.0);
																										                                d4.setKwhExp(0.0);
																										                                d4.setStructureSize(0);
																																	 d4LoadDataService.save(d4);
																																	 }
																																	 calendar.add(Calendar.MINUTE, 30);
																																	 d4KvaValue = "";
																																	 d4KwhValue = "";
																																	 d4PfValue = "";
																																	 ipInterval = "";
																																	 d4vrValue="";
																																	 d4vyValue="";d4vbValue="";d4arValue="";d4ayValue="";d4abValue="";
																																}
																																//assign all variables to zero
																																
																																//Power Factor Report
																																int intervalVal = Integer.parseInt(intervalPeriod);
																																
																																maxKva = Math.round(maxKva * 1000.0)/1000.0;
																																minKva = Math.round(minKva * 1000.0)/1000.0;
																																sumKwh = Math.round(sumKwh * 1000.0)/1000.0;
																																sumPf = Math.round(sumPf * 1000.0)/1000.0;
																																sumKva = Math.round(sumKva * 1000.0)/1000.0;
																																
																																D4Data d4 = new D4Data();
																																d4.setCdfId(cdfId);d4.setIntervalPeriod(intervalVal);
																																if(dayProfileDate.length() > 2)
																																{
																																 String checkForDatePatter1 = dayProfileDate.substring(2);
																																 if(checkForDatePatter1.startsWith("-"))
																																	{
																																	 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																	 
																																	}
																																	else if(checkForDatePatter1.startsWith("/"))
																																	{
																																		d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																		 
																																	}
																																	else
																																	{
																																		 dayProfileDate = "";
																																	}
																																}
																															    d4.setMinKva(minKva);d4.setMaxKva(maxKva);d4.setSumKwh(sumKwh);
																														        d4.setKwhFlag(kwhFlag);d4.setSumPf(sumPf);d4.setPf05(pfLt05);
																														        d4.setPf0507(pf05To07);d4.setPf0709(pf07To09);d4.setPf09(pfGt09);
																														        d4.setPfNoload(pfNoLoad);d4.setPfBlackout(pfBlackOut);
																														        d4.setIpGs20(kva1_lt_cd20);d4.setIpGs2040(kva1_lt_cd40);
																														        d4.setIpGs4060(kva1_lt_cd60);d4.setIpGs60(kva1_gt_cd60);
																														        d4.setIpOutGs20(kva2_lt_cd20);d4.setIpOutGs2040(kva2_lt_cd40);
																														        d4.setIpOutGs4060(kva2_lt_cd60);d4.setIpOutGs60(kva2_gt_cd60);
																														        d4.setMf(mf);d4.setCd(cd);d4.setSumKva(sumKva);
																														        
																														        d4DtataService.save(d4);
																														        kwhFlag = 0;
																																maxKva = 0; minKva = 0; sumKwh = 0; sumPf = 0;sumKva = 0;
																																pfLt05 = 0; pf05To07 = 0; pf07To09 = 0; pfGt09 = 0;  pfNoLoad = 0; pfBlackOut = 0;
																																
																																kva1_lt_cd20 = 0;kva1_lt_cd40 = 0;kva1_lt_cd60 = 0;kva1_gt_cd60 = 0;
																																kva2_lt_cd20 = 0;kva2_lt_cd40 = 0;kva2_lt_cd60 = 0;kva2_gt_cd60 = 0;
																																kva3_lt_cd20 = 0;kva3_lt_cd40 = 0;kva3_lt_cd60 = 0;kva3_gt_cd60 = 0;
																																kva4_lt_cd20 = 0;kva4_lt_cd40 = 0;kva4_lt_cd60 = 0;kva4_gt_cd60 = 0;
																															 
																														 }}
																													}
																												}//End For Loop
																											 System.out.println("Insert into D4 completed");
																										 }// Ends the  D4 Tag
																										 else if(d1DataNodeData.getNodeName().equalsIgnoreCase("D5"))//Check The D5 Tag
																										 {
																											 System.out.println("enter to d5 node");
																											 NodeList subnodeListD5 = d1DataNodeData.getChildNodes();
																											 
																											 if(d1DateForCheckin.equalsIgnoreCase(billmonth+""))  //   "201807"
																												{
																												 System.out.println("subnodeListD5.getLength()=="+subnodeListD5.getLength());
																													for (int countD5 = 0; countD5 < subnodeListD5.getLength(); countD5++) 
																													{
																														String checkDatePattern = "";
																														String d5ReadTagAttrId = "0",d5Id = "0";
																														 Node tempNodeD5 = subnodeListD5.item(countD5);
																														 if(tempNodeD5.getNodeType()==Node.ELEMENT_NODE)
																														 {
																															 String nodeName = tempNodeD5.getNodeName();
																															 String code = "", time = "",status = "",duration="",oldtime="";
																															 if(nodeName.equalsIgnoreCase("EVENT"))
																										    					{
																																 
																																 if (tempNodeD5.hasAttributes()) 
																													    			{
																																	  NamedNodeMap nodeMap = tempNodeD5.getAttributes();
																																	  for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																													    				{
																																		    String d5AttrReadAttributeId = "0",value = "";
																													    					Node node = nodeMap.item(nodeMapIndex);
																													    					value = node.getNodeValue();
																													    					if(node.getNodeName().equalsIgnoreCase("CODE"))
																													    					{
																													    						code = node.getNodeValue();
																													    					}
																													    					else if(node.getNodeName().equalsIgnoreCase("TIME"))
																													    					{
																													    						time = node.getNodeValue();
																													    					}
																													    					else if(node.getNodeName().equalsIgnoreCase("STATUS"))
																													    					{
																													    						status = node.getNodeValue();
																													    					}
																													    					else if(node.getNodeName().equalsIgnoreCase("DURATION"))
																													    					{
																													    						duration=node.getNodeValue();
																													    					}
																													    				}
																													    			}
																																 if(status.equalsIgnoreCase("0"))
																											    					{
																																	   //oldtime=time;
																																	   model.addAttribute("oldtime",time);
																											    					}
																																 
																																    if(status.equalsIgnoreCase("1") && time=="")
																																    {
																																	    oldtime= (String) model.get("oldtime");
																																		String oldtimeFormat=oldtime;
																																		String timeduration=duration; 
																																		String time11=oldtimeFormat.substring(11);
																																		String time2=timeduration.substring(4);
																																		String s3=oldtimeFormat.substring(0,11);
																																		
																																		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
																																		timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
																																		
																																		Date date1 = timeFormat.parse(time11);
																																		Date date2 = timeFormat.parse(time2);
																																		
																																		long sum = date1.getTime() + date2.getTime();
																																		
																																		String date3 = timeFormat.format(new Date(sum));
																																		time=s3+date3;
																																		System.out.println("status & time----"+status+"   "+time);
																																    }
																																 	String query = "";
																																	String time1 = "";
																																	int d5Status = 0;
																																	D5Data d5 = new D5Data();
																																	if(time.length() > 2)
																																	{
																																		checkDatePattern = time.substring(2);
																																		if(checkDatePattern.startsWith("-"))
																																		{
																																		    //time1 = sdf2.format(sdf2.parse(time));
																																			Date event_time;
																																			try {
																																				event_time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(time);
																																				//mykey.setEventTime(new Timestamp(event_time.getTime()));
																																				d5.setEventTime(new Timestamp(event_time.getTime()));
																																				System.out.println("event time=====>"+event_time.getTime());
																																			} catch (ParseException e) {
																																				e.printStackTrace();
																																				event_time=device_reading_date;
																																			}
																																			
																																		}
																																		else if(checkDatePattern.startsWith("/"))
																																		{
																																			time1 = sdf4.format(sdf4.parse(time));
																																		}
																																		else{
																																			time1= "";
																																		}
																																	}
																																	else
																																	{
																																		time1= "";
																																	}
																																	
																																		if(code.equalsIgnoreCase("") || code.equalsIgnoreCase(null))
																																		{
																																			code = "0";
																																		}
																						
																																	
																																	d5.setCdfId(cdfId);d5.setEventCode(Integer.parseInt(code));d5.setEventStatus(status);
																																	/*if(time1 != "" || time1 != null)
																																	{
																																		
																																	}*/
																																	
																																	
																																	
																																	//Inserting SnapShots for Each Event 
																																	if(tempNodeD5.hasChildNodes())
																																	{
																																		
																																		NodeList d5SnapshotList = tempNodeD5.getChildNodes();
																																		for(int d5sub = 0;d5sub<d5SnapshotList.getLength();d5sub++)
																																		{
																																			
																																			 Node d5tempNode123 = d5SnapshotList.item(d5sub);
																																			 if (d5tempNode123.getNodeType() == Node.ELEMENT_NODE) 
																																			 {
																																				 String subNodeName = d5tempNode123.getNodeName();
																																				 String subNodeValue = d5tempNode123.getTextContent();
																																				 
																																				 if(d5tempNode123.hasAttributes())
																																				 {
																																					 NamedNodeMap nodeMap = d5tempNode123.getAttributes();
																																	    				String snapCode = "",snapValue = "", snapUnit = "";
																																	    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																	    				{
																																	    					Node node = nodeMap.item(nodeMapIndex);
																																	    					//System.out.println("attr name : " + node.getNodeName());
																																	    					//System.out.println("attr value : " + node.getNodeValue());
																																	    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																																	    						snapCode = node.getNodeValue();
																																	    					else if(node.getNodeName().equalsIgnoreCase("VALUE")) 
																																	    						snapValue = node.getNodeValue();
																																	    					else if(node.getNodeName().equalsIgnoreCase("UNIT")) 
																																	    						snapUnit = node.getNodeValue();													    																	    					
																																	    				}
																																	    				//System.out.println("==inside snapCode  ===>"+snapCode+"==>"+snapValue+"==>"+snapUnit);
																																	    				//System.out.println("==inside manufacturerName  ===>"+manufacturerCode+"==>"+manufacturerName);
																																	    				if(snapValue.equalsIgnoreCase(""))
																																	    				{
																																	    					snapValue = "0";
																																	    				}
																																	    				if(snapCode.equalsIgnoreCase("P1-2-1-1-0") && snapUnit.equalsIgnoreCase("V"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					rPhaseVal1 = temp;
																																	    					/*if(temp > rPhaseVal1)
																																	    					{
																																	    						rPhaseVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P1-2-2-1-0") && snapUnit.equalsIgnoreCase("V"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					yPhaseVal1 = temp;
																																	    					/*if(temp > yPhaseVal1)
																																	    					{
																																	    						yPhaseVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P1-2-3-1-0") && snapUnit.equalsIgnoreCase("V"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					bPhaseVal1 = temp;
																																	    					/*if(temp > bPhaseVal1)
																																	    					{
																																	    						bPhaseVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				
																																	    				else if(snapCode.equalsIgnoreCase("P2-1-1-1-0") && (snapUnit.equalsIgnoreCase("K") || snapUnit.equalsIgnoreCase("A")))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					
																																	    					rPhaseLineVal1 = temp;
																																	    					/*if(temp > rPhaseLineVal1)
																																	    					{
																																	    						rPhaseLineVal1 = temp;
																																	    						//System.out.println("==inside rPhaseLineVal1 1 ===>"+snapValue+"==>"+temp+"==>"+rPhaseLineVal1);
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P2-1-2-1-0") && (snapUnit.equalsIgnoreCase("K") || snapUnit.equalsIgnoreCase("A")))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					yPhaseLineVal1 = temp;
																																	    					
																																	    					/*if(temp > yPhaseLineVal1)
																																	    					{
																																	    						yPhaseLineVal1 = temp;
																																	    						//System.out.println("==inside yPhaseLineVal1 2 ===>"+snapValue+"==>"+temp+"==>"+yPhaseLineVal1);
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P2-1-3-1-0") && (snapUnit.equalsIgnoreCase("K") || snapUnit.equalsIgnoreCase("A")))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					bPhaseLineVal1 = temp;
																																	    					/*if(temp > bPhaseLineVal1)
																																	    					{
																																	    						bPhaseLineVal1 = temp;
																																	    						//System.out.println("==inside bPhaseLineVal1 3 ===>"+snapValue+"==>"+temp+"==>"+bPhaseLineVal1);
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P2-2-1-1-0") && snapUnit.equalsIgnoreCase("A"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					
																																	    					rPhaseActiveVal1 = temp;
																																	    					/*if(temp > rPhaseActiveVal1)
																																	    					{
																																	    						rPhaseActiveVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P2-2-2-1-0") && snapUnit.equalsIgnoreCase("A"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					
																																	    					yPhaseActiveVal1 = temp;
																																	    					/*if(temp > yPhaseActiveVal1)
																																	    					{
																																	    						yPhaseActiveVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P2-2-3-1-0") && snapUnit.equalsIgnoreCase("A"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					bPhaseActiveVal1 = temp;
																																	    					/*if(temp > bPhaseActiveVal1)
																																	    					{
																																	    						bPhaseActiveVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				
																																	    				else if(snapCode.equalsIgnoreCase("P4-1-1-0-0") || snapCode.equalsIgnoreCase("P4-1-4-0-0"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					
																																	    					rPhasePFVal1 = temp;
																																	    					/*if(temp > rPhasePFVal1)
																																	    					{
																																	    						rPhasePFVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P4-2-1-0-0") || snapCode.equalsIgnoreCase("P4-2-4-0-0"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					yPhasePFVal1 = temp;
																																	    					/*if(temp > yPhasePFVal1)
																																	    					{
																																	    						yPhasePFVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P4-3-1-0-0") || snapCode.equalsIgnoreCase("P4-3-4-0-0"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					bPhasePFVal1 = temp;
																																	    					/*if(temp > bPhasePFVal1)
																																	    					{
																																	    						bPhasePFVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P4-4-1-0-0") || snapCode.equalsIgnoreCase("P4-4-4-0-0"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					avgPFVal1 = temp;
																																	    					/*if(temp > avgPFVal1)
																																	    					{
																																	    						avgPFVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				else if((snapCode.equalsIgnoreCase("P3-1-4-1-0") || snapCode.equalsIgnoreCase("P3-2-4-1-0")) && snapCode.equalsIgnoreCase("K"))
																																	    				{
																																	    					float temp = Float.parseFloat(snapValue);
																																	    					activePowerVal1 = temp;
																																	    					/*if(temp > activePowerVal1)
																																	    					{
																																	    						activePowerVal1 = temp;
																																	    					}*/
																																	    				}
																																	    				else if(snapCode.equalsIgnoreCase("P8-1-0-0-0"))
																																	    				{
																																	    					phaseSequence1 = snapValue;
																																	    				}
																																	    				
																																	    				if(manufacturerCode.equalsIgnoreCase("1"))
																																	    				{
																																	    					if(snapCode.equalsIgnoreCase("P7-1-5-1-0")||snapCode.equalsIgnoreCase("P7-1-18-0-0")||snapCode.equalsIgnoreCase("P7-1-18-2-0"))
																																		    				{
																																	    						float temp = Float.parseFloat(snapValue);
																																	    						d5_kwh=temp;
																																	    						//System.out.println("==inside 1 ===>"+snapValue+"==>"+temp+"==>"+d5_kwh);
																																		    				}
																																	    					
																																	    				}
																																	    				else if(manufacturerCode.equalsIgnoreCase("4"))
																																	    				{
																																	    					if(snapCode.equalsIgnoreCase("P7-1-5-1-0")||snapCode.equalsIgnoreCase("P7-1-18-0-0")||
																																	    					   snapCode.equalsIgnoreCase("P7-1-5-2-0") || snapCode.equalsIgnoreCase("P7-1-13-2-0"))
																																			    				{
																																	    						float temp = Float.parseFloat(snapValue);
																																	    						d5_kwh=temp;
																																	    						//System.out.println("==inside 2 ===>"+snapValue+"==>"+temp+"==>"+d5_kwh);
																																			    				}
																																	    				}
																																	    				else if(manufacturerCode.equalsIgnoreCase("3")||manufacturerName.contains("LARSEN")|| manufacturerName.contains("L&T")||manufacturerName.equalsIgnoreCase("LARSEN AND TOUBRO LIMITED"))
																																	    				{
																																	    					if(snapCode.equalsIgnoreCase("P7-1-5-1-0")||snapCode.equalsIgnoreCase("P7-1-18-0-0")||snapCode.equalsIgnoreCase("P7-1-5-2-0"))
																																			    				{
																																	    						//System.out.println("Inside P7-1-5-2-0 kwh value");
																																										
																																	    						float temp = Float.parseFloat(snapValue);
																																	    						d5_kwh=temp;
																																	    						//System.out.println("==inside 3 ===>"+snapValue+"==>"+temp+"==>"+d5_kwh);
																																			    				}
																																	    				}
																																	    				else 
																																	    				{
																																	    					if(snapCode.equalsIgnoreCase("P7-1-13-2-0")||snapCode.equalsIgnoreCase("P7-1-13-1-0") ||snapCode.equalsIgnoreCase("P7-1-18-2-0"))
																																			    				{
																																	    						float temp = Float.parseFloat(snapValue);
																																	    						d5_kwh=temp;
																																	    						//System.out.println("==inside dgdf4 ===>"+snapValue+"==>"+temp+"==>"+d5_kwh);
																																			    				}
																																	    				}

																																	    				Double val=Double.parseDouble(snapValue);
																														                                ParamCodeValidator validator=new ParamCodeValidator();
																														                                if(snapCode!=null) {
																															                               String code1=validator.readD4tags(snapCode);
																															                                if("i_r".equals(code1)) {
																															                                	d5.setiR(val);
																															                                }
																															                                else if ("i_y".equals(code1)) {
																															                                	d5.setiY(val);
																															                                }
																															                                else if ("i_b".equals(code1)) {
																															                                	d5.setiB(val);
																															                                }
																															                                else if("v_r".equals(code1)) {
																															                                	d5.setvR(val);
																															                                }
																															                                else if ("v_y".equals(code1)) {
																															                                	d5.setvY(val);
																															                                }
																															                                else if ("v_b".equals(code1)) {
																															                                	d5.setvB(val);
																															                                }
																															                                else if("pf_r".equals(code1)) {
																															                                	d5.setPfR(val);
																															                                }
																															                                else if ("pf_y".equals(code1)) {
																															                                	d5.setPfY(val);
																															                                }
																															                                else if ("pf_b".equals(code1)) {
																															                                	d5.setPfB(val);
																															                                }
																															                                else if ("kwh".equals(code1)) {
																															                                	d5.setKwh(val);
																															                                }
																															                                else if ("kvah".equals(code1)) {
																															                                	d5.setKvah(val);
																															                                }
																														                                }
																																	    					
																																				 }
																																			 }
																																			 
																																		}//End ForLoop
																																		
																																		//System.out.println("==inside ryb phase val ===>"+bPhaseVal1+"==>"+rPhaseVal1+"==>"+yPhaseVal1);
																																		d5.setbPhaseVal(bPhaseVal1);
																																		d5.setrPhaseVal(rPhaseVal1);
																																		d5.setyPhaseVal(yPhaseVal1);
																																		//System.out.println("==inside ryb phase line ===>"+rPhaseLineVal1+"==>"+yPhaseLineVal1+"==>"+bPhaseLineVal1);
																																		d5.setrPhaseLineVal(rPhaseLineVal1);
																																		d5.setyPhaseLineVal(yPhaseLineVal1);
																																		d5.setbPhaseLineVal(bPhaseLineVal1);
																																		//System.out.println("==inside ryb phase active ===>"+rPhaseActiveVal1+"==>"+yPhaseActiveVal1+"==>"+bPhaseActiveVal1);
																																		d5.setrPhaseActiveVal(rPhaseActiveVal1);
																																		d5.setyPhaseActiveVal(yPhaseActiveVal1);
																																		d5.setbPhaseActiveVal(bPhaseActiveVal1);
																																		//System.out.println("==inside ryb phase pf ===>"+rPhasePFVal1+"==>"+yPhasePFVal1+"==>"+bPhasePFVal1);
																																		d5.setrPhasePfVal(rPhasePFVal1);
																																		d5.setyPhasePfVal(yPhasePFVal1);
																																		d5.setbPhasePfVal(bPhasePFVal1);
																																		d5.setAvgPfVal(avgPFVal1);
																																		d5.setActivePowerVal(activePowerVal1);
																																		d5.setPhaseSequence(phaseSequence1);
																																		d5.setMf(mf);
																																		//System.out.println("==inside d5_kwh ===>"+d5_kwh);
																																		d5.setD5_kwh(d5_kwh);
																																		//d5SnashotService.save(d5Snap);
																																	}
																																	
																																	D5Data DD=d5DataService.save(d5);
																																	d5DataService.flush();
																																	System.err.println("d5 data saved successfully With Id==>"+DD.getD5Id());
																																			
																																	//D5 Snapshot variables
																																	  rPhaseVal1 = 0; yPhaseVal1 = 0; bPhaseVal1 = 0;
																																	  rPhaseLineVal1 = 0; yPhaseLineVal1 = 0; bPhaseLineVal1 = 0;
																																	  rPhaseActiveVal1 = 0; yPhaseActiveVal1 = 0; bPhaseActiveVal1 = 0;
																																	  rPhasePFVal1 = 0; yPhasePFVal1 = 0; bPhasePFVal1 = 0;
																																	  avgPFVal1 = 0; activePowerVal1 = 0;
																																	  phaseSequence1 = "";
																																	  d5_kwh=0;
																										    					}
																															 
																														 }
																														
																													}// End ForLoop
																													
																												}
																											 System.out.println("Insert into D5 completed");
																										 }

																										 else if(d1DataNodeData.getNodeName().equalsIgnoreCase("D9"))//Check The D9 Tag
																										 {
																											 String checkDatePattern = "";
																											 NodeList subnodeListD9 = d1DataNodeData.getChildNodes();
																											 if(d1DateForCheckin.equalsIgnoreCase("201807"))    //billmonth+""
																												{
																												 for (int countD9 = 0; countD9 < subnodeListD9.getLength(); countD9++)
																												 {
																													 Node tempNodeD9 = subnodeListD9.item(countD9);
																													 if(tempNodeD9.getNodeType()== Node.ELEMENT_NODE)
																													 {
																														 String nodeName = tempNodeD9.getNodeName();
																														 if (tempNodeD9.hasAttributes()) 
																											    			{
																															 
																															 	NamedNodeMap nodeMap = tempNodeD9.getAttributes();
																											    	 			String transactionCode = "", dateTime = "";
																											    	 			for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																											    	 			{
																											    	 				Node node = nodeMap.item(nodeMapIndex);
																											    	 				if(node.getNodeName().equalsIgnoreCase("CODE"))
																											    						transactionCode = node.getNodeValue();
																											    					else if(node.getNodeName().equalsIgnoreCase("DATETIME"))
																											    						dateTime = node.getNodeValue();
																											    	 				
																											    	 			}
																											    	 			
																											    	 			String time1 = "";
																											    	 			D9Data d9 = new D9Data();
																											    				if(dateTime.length() > 2)
																																{
																																	checkDatePattern = dateTime.substring(2);
																																	if(checkDatePattern.startsWith("-"))
																																	{
																																		time1 =  time1 = sdf2.format(sdf2.parse(dateTime));
																																		d9.setTransactionDate(sdf2.parse(time1));
																																	}
																																	else if(checkDatePattern.startsWith("/"))
																																	{
																																		time1 = sdf4.format(sdf4.parse(dateTime));
																																	}
																																	else{
																																		time1= "";
																																	}
																																}
																																else
																																{
																																	time1= "";
																																}
																											    				
																											    				
																											    				d9.setCdfId(cdfId);d9.setTransactionCode(transactionCode);
																											    				
																											    				//d9DataService.save(d9);
																											    			}
																													 }
																													 
																												 }//End ForLoop
																												}
																											 
																											 System.out.println("Insert into D9 completed"); 
																										 }// Ends the  D9 Tag
																										 
																										 
																									 }
																								 }
																							 }
																							 
																							 
																						 }
																							 
																							 
																						 
																					 }
																				}
																				
																				
																			}//End Child Node
																			
																			if(manufacturerCode.equalsIgnoreCase("1"))
														    				{
																				if(kwhValue.equalsIgnoreCase(""))
																				{
																					kwhValue = kwhValue2;
																				}
																				if(kvahValue.equalsIgnoreCase(""))
																				{
																					kvahValue = kvahValue2;
																					if(kvahValue2.equalsIgnoreCase(""))
																					{
																						kvahValue = kvahValue3;
																					}
																				}
																				if(kvaValue.equalsIgnoreCase(""))
																				{
																					kvaValue = kvaValue2;
																					if(kvaValue2.equalsIgnoreCase(""))
																					{
																						kvaValue = kvaValue3;
																					}
																				}
														    				}
																			
																			if(kwhValue.equalsIgnoreCase("") || kwhValue.equalsIgnoreCase(null))
																			{
																				kwhValue = "0";
																			}
																			if(kvahValue.equalsIgnoreCase("") || kvahValue.equalsIgnoreCase(null))
																			{
																				kvahValue = "0";
																			}
																			if(kvaValue.equalsIgnoreCase("") || kvaValue.equalsIgnoreCase(null))
																			{
																				kvaValue = "0";
																			}
																			if(pfValue.equalsIgnoreCase("") || pfValue.equalsIgnoreCase(null))
																			{
																				pfValue = "0";
																			}
																			//int n = entityManager.createNamedQuery("MeterMaster.updateMeterMasterData").setParameter("currdngkwh", Double.parseDouble(kwhValue)).setParameter("currrdngkvah",Double.parseDouble(kvahValue)).setParameter("currdngkva",Double.parseDouble(kvaValue)).setParameter("mtrclass", meterClass).setParameter("pf", Double.parseDouble(pfValue)).setParameter("accno", accno).setParameter("metrno", meterNumber).setParameter("rdngmonth", billmonth).executeUpdate();
																			/*if(n >0)
																			{*/
																				//System.out.println("MeterMaster updated succussfully");
																				//File Deletion Part
																				mainStatus = "parsed";
																				
																				 String source = unZipFIlePath + "/"+filename;
																				 File sourceFile = new File(source);
																				 //System.out.println("2950 Source file delete : "+sourceFile.delete());
																			/*}
																			else{
																				System.out.println("MeterMaster updation failed");
																				mainStatus = "parsed";
																				 String source = unZipFIlePath + "/"+filename;
																				 File sourceFile = new File(source);
																				 System.out.println("Source file delete : "+sourceFile.delete());
																				// File Deletion Part
																			}*/
																			
																		}
																		
																	}
																	model.addAttribute("result","File Upload Successfully");
																	
																	
																  }
																	 else{
																		 
																		 System.out
																				.println("MeterDoesnotExist --------");
																		 //Meter Doesnot Exist
																		 mainStatus = "meterDoesNotExist";
																		 System.out.println("metrnooo->"+meterNumber);
																		 String source = unZipFIlePath + "/"+filename;
																		 File sourceFile = new File(source);
																		// System.out.println("2976 Source file delete : "+sourceFile.delete());
																	 }
																 }//End Main If
																 
																 else{
																	 model.addAttribute("result","Meter Already Exist...");
																	 cdfId = countData.get(0).getId();
																	 System.err
																			.println("already exists and CDFID Is====>"+cdfId);
																	 long dataFound = (Long) postgresMdas.createQuery("SELECT COUNT(d.cdfId) FROM D4Data d WHERE d.cdfId = '"+cdfId+"' ").getSingleResult();
																	 if(dataFound == 0)
																	 {
																		 System.out.println("Import data for d4 tag");
																		 System.err.println("prevMonthFlag before checking -->"+prevMonthFlag);
																		int prevdata = cdfDataService.findPrevDataD4(meterNumber);
																		System.err.println("prev data after-->"+prevdata);
																		if(prevdata > 0)
																		{
																			 List masterList = masterService.getMeterDataInformation(meterNumber, billmonth+"");
																				/*Object[] obj = (Object[]) masterList.get(0);
																				String accno = (String)obj[0];
																				ctrn = (Double)obj[1];
																				ctrd = (Double)obj[2];
																				cd = (Double)obj[3];
																				mf = (Double)obj[4];*/
																			 String accno="";
																			 for(Iterator<?> iterator1=  masterList.iterator(); iterator1.hasNext();)
										    			    			 		{
																				 System.out.println("insss for");
																				 Object[] obj=(Object[]) iterator1.next();
																				  accno = (String)obj[0];
																				  if(obj[1]!=null )
																				  {
																					  
																					ctrn = (Double)obj[1];
																				  }
																				  if(obj[2]!=null )
																				  {
																					  
																					  ctrd = (Double)obj[2];
																				  }
																				  if(obj[3]!=null )
																				  {
																					  
																					  cd = (Double)obj[3];
																				  }
																				  if(obj[4]!=null )
																				  {
																					  
																					  mf = (Double)obj[4];
																				  }
																					
										    			    			 		}
										    			    			 		
																				if(mf > 0)
																				{
																					cd_20 = ((((cd/mf)*20)/100)/2);
																					cd_40 = ((((cd/mf)*40)/100)/2);
																					cd_60 = ((((cd/mf)*60)/100)/2);
																				}
																			 prevMonthFlag = true;
																		}
																		System.err.println("prevMonthFlag after checking -->"+prevMonthFlag);
																		
																		try{
																			
																			NodeList nodeList = doc.getElementsByTagName("CDF");
																			for(int count1 = 0; count1 < nodeList.getLength();count1++)
																			{
																				Node tempNodeFor = nodeList.item(count1);//CDF Node
																				if(tempNodeFor.getNodeType() == Node.ELEMENT_NODE)
																				{
																					if(tempNodeFor.hasChildNodes())
																					{
																						NodeList subnodeList = tempNodeFor.getChildNodes();
																						for(int subCount1 = 0 ; subCount1 < subnodeListForMetrNo.getLength();subCount1++)
																						{
																							Node subChildNodeForData = subnodeList.item(subCount1);//Utility Node
																							 if(subChildNodeForData.getNodeType() == Node.ELEMENT_NODE)
																							 {
																								 if(subChildNodeForData.hasChildNodes())
																								 { 
																									 NodeList dataNodeListFordb = subChildNodeForData.getChildNodes();
																									 for(int dataSubCount1 = 0 ; dataSubCount1 < dataNodeListFordb.getLength();dataSubCount1++)
																									 {
																										 Node d1DataNodeData = dataNodeListFordb.item(dataSubCount1);
																										 if(d1DataNodeData.getNodeType() == Node.ELEMENT_NODE)
																										 {
																											 if(d1DataNodeData.hasChildNodes())
																											 {
																												 if(d1DataNodeData.getNodeName().equalsIgnoreCase("D4"))//Check The D1 Tag
																												 {
																													 String checkDatePattern = "";
																													 NodeList subnodeListD4 = d1DataNodeData.getChildNodes();
																													 if (d1DataNodeData.hasAttributes()) 
																										    			{
																										    	 			NamedNodeMap nodeMap = d1DataNodeData.getAttributes();
																										    				//String code = "",value = "", unit = "";
																										    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																										    				{
																										    					Node node = nodeMap.item(nodeMapIndex);
																										    					intervalPeriod = node.getNodeValue();
																										    					//System.out.println("attr name : " + node.getNodeName()+" attr value : " + intervalPeriod);
																										    				}
																										    			}
																													 
																													 for (int countD4 = 0; countD4 < subnodeListD4.getLength(); countD4++) 
																														{
																														    String dayProfileDate = "";
																															Node tempNodeD4 = subnodeListD4.item(countD4);
																															 if (tempNodeD4.getNodeType() == Node.ELEMENT_NODE) 
																															 {
																																 String nodeName = tempNodeD4.getNodeName();
																																 String nodeValue = tempNodeD4.getTextContent();
																																 if (tempNodeD4.hasAttributes()) 
																													    			{
																													    	 			NamedNodeMap nodeMap = tempNodeD4.getAttributes();
																													    	 			
																													    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																													    				{
																													    					Node node = nodeMap.item(nodeMapIndex);
																													    					dayProfileDate = node.getNodeValue();
																													    					//System.out.println(" profile date attr name : " + node.getNodeName()+" attr value : " + dayProfileDate);
																													    																		    																	    																	    					
																													    				}													    				
																													    			}
																																 System.err.println("check---- "+meterNumber+"--"+dayProfileDate);
																																	List<?> d1= d4LoadDataService.getDuplicateData(meterNumber,dayProfileDate);
																																	System.err.println("d1--- "+d1.size());
																																	 if(d1.size()==0){ 
																																 if(dayProfileDate.length() > 2)
																																	{
																																		checkDatePattern = dayProfileDate.substring(2);
																																		if(checkDatePattern.startsWith("-"))
																																		{
																																			dayProfileDate = sdf3.format(sdf3.parse(dayProfileDate));
																																		}
																																		else if(checkDatePattern.startsWith("/"))
																																		{
																																			dayProfileDate = sdf4.format(sdf4.parse(dayProfileDate));
																																			
																																		}
																																		else
																																		{
																																			 dayProfileDate = "";
																																		}
																																	}
																																	else
																																	{
																																		 dayProfileDate = "";
																																	}
																																if(prevMonthFlag)
																																 {
																																	 System.err.println("inside prevMonthFlag is true");
																																	 Calendar cal1 = Calendar.getInstance();
																																	 if(dayProfileDate.length() > 2)
																																		{
																																		 String checkForDatePatter = dayProfileDate.substring(2);
																																		 if(checkForDatePatter.startsWith("-"))
																																			{
																																			 cal1.setTime(sdf3.parse(dayProfileDate));
																																			}
																																			else if(checkForDatePatter.startsWith("/"))
																																			{
																																				 cal1.setTime(sdf4.parse(dayProfileDate));
																																			}
																																			else
																																			{
																																				 dayProfileDate = "";
																																			}
																																		}
																																	 cal1.add(Calendar.MONTH, 1);
																																	 String profileDateYearMonth = sdfBillDate.format(cal1.getTime());
																																	 if(profileDateYearMonth.equalsIgnoreCase(billmonth+""))//Checking Profile Date is Equal to this Month
																																	 {
																																		 int kwhFlag = 0;
																																		 NodeList subTempNodeListD4 = tempNodeD4.getChildNodes();
																																		 Calendar calendar = Calendar.getInstance();
																																		 Date datetime=null;
																																		 if(dayProfileDate.length() > 2)
																																			{
																																			 String checkForDatePatter1 = dayProfileDate.substring(2);
																																			 if(checkForDatePatter1.startsWith("-"))
																																				{
																																				// d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																				 datetime=sdf3.parse(dayProfileDate);
																																				 calendar.setTime(datetime);
																																				}
																																				else if(checkForDatePatter1.startsWith("/"))
																																				{
																																				//	d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																					 datetime=sdf4.parse(dayProfileDate);
																																					 calendar.setTime(datetime);
																																				}
																																				else
																																				{
																																					 dayProfileDate = "";
																																				}
																																			}
																																		 for (int subCountD4 = 0; subCountD4 < subTempNodeListD4.getLength(); subCountD4++) 
																																			{
																																			 	String ipInterval = "0";
																																				int ipIntervalNum = 0;
																																				 Node subTempNode = subTempNodeListD4.item(subCountD4);
																																				 if (subTempNode.getNodeType() == Node.ELEMENT_NODE) 
																																				 {
																																					 String subNodeName = subTempNode.getNodeName();//IP ..
																																					 String subNodeValue = subTempNode.getTextContent();
																																					 if(subTempNode.hasAttributes())
																																					 {
																																						 NamedNodeMap nodeMap = subTempNode.getAttributes();
																																		    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																		    				{
																																		    					Node node = nodeMap.item(nodeMapIndex);
																																		    					ipInterval = node.getNodeValue();
																																		    				}
																																						 
																																					 }
																																					 
																																					 
																																				 }
																																				 ipIntervalNum = Integer.parseInt(ipInterval);
																																				 boolean sumKwhFlag = true;
																																				 boolean sumKvaFlag = true;
																																				 NodeList subTempNodeListIP = subTempNode.getChildNodes();
																																				 D4CdfData d4 = new D4CdfData();
																																				
																																				 for (int subCountIP = 0; subCountIP < subTempNodeListIP.getLength(); subCountIP++)
																																				 {
																																					 
																																					 String paramCode = "", paramValue = "";
																																					 Node subTempNodeIP = subTempNodeListIP.item(subCountIP);
																																					 if (subTempNodeIP.getNodeType() == Node.ELEMENT_NODE) 
																																					 {
																																						
																																						String subNodeName = subTempNodeIP.getNodeName();//IFLAG, PARAMETER
																																						String subNodeValue = subTempNodeIP.getTextContent();
																																						if(subTempNodeIP.hasAttributes()) 
																																		    			{
																																							NamedNodeMap nodeMap = subTempNodeIP.getAttributes();
																																		    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																		    				{
																																		    					
																																		    					Node node = nodeMap.item(nodeMapIndex);
																																		    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																																		    					{
																																		    						paramCode = node.getNodeValue();
																																		    					}
																																		    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																																		    					{
																																		    						paramValue = node.getNodeValue();
																																		    					}
																																		    					
																																		    					
																																		    				}
																																		    				
																																		    			}
																																						
																																					 }	
																																					 if(manufacturerCode.equalsIgnoreCase("1")||manufacturerName.equalsIgnoreCase("SECURE METERS LTD."))
																																					 {

																																							//KVA
																																							 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																									 paramCode.equalsIgnoreCase("P7-3-5-0-0")||paramCode.equalsIgnoreCase("P7-4-18-0-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								d4KvaValue = paramValue;
																																								 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																										d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																								float tempKva = Float.parseFloat(paramValue);
																																								 
																																								 if(sumKvaFlag)
																																								 {
																																									 sumKva = sumKva + tempKva;
																																									 sumKvaFlag = false;
																																								 }
																																								 if(tempKva > maxKva)
																																								 {
																																									 maxKva = tempKva;
																																								 }
																																								 
																																								 if(minKva == 0)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 if(tempKva < minKva)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 
																																							 }
																																							 
																																							//'P7-1-5-1-0','P7-1-18-0-0'
																																							//KWH
																																							 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0")||
																																									 paramCode.equalsIgnoreCase("P7-1-18-0-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 d4KwhValue = paramValue;
																																								 float tempKwh = Float.parseFloat(paramValue);
																																								 if(sumKwhFlag)
																																								 {
																																									 sumKwh = sumKwh + tempKwh;
																																									 sumKwhFlag = false;
																																								 }
																																								 kwhFlag = 1;
																																								/* if(tempKwh > maxKwh)
																																								 {
																																									 maxKwh = tempKwh;
																																								 }*/
																																								 
																																							 }
																																							 //D4VRphase value
																																							 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																							 {
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								d4vrValue = paramValue;
																																							 }
																																							 //D4VYphase value
																																							 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4vyValue= paramValue;
																																							 }
																																							 //D4VBphase value
																																							 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4vbValue= paramValue;
																																							 }
																																							//D4ARphase value
																																							 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																							 {
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								d4arValue = paramValue;
																																							 }
																																							 //D4AYphase value
																																							 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4ayValue= paramValue;
																																							 }
																																							 //D4ABphase value
																																							 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4abValue= paramValue;
																																							 }
																																							
																																						 
																																					 }
																																					 else  if(manufacturerCode.equalsIgnoreCase("3")||manufacturerName.equalsIgnoreCase("LARSEN AND TOUBRO LIMITED"))
																																					 {
																																						//FOR L&T
																																							//KVA
																																							 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																									 paramCode.equalsIgnoreCase("P7-3-5-1-0") || paramCode.equalsIgnoreCase("P7-4-5-2-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 d4KvaValue = paramValue;
																																								 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																										d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																								 float tempKva = Float.parseFloat(paramValue);
																																								 
																																								 if(sumKvaFlag)
																																								 {
																																									 sumKva = sumKva + tempKva;
																																									 sumKvaFlag = false;
																																								 }
																																								 
																																								 if(tempKva > maxKva)
																																								 {
																																									 maxKva = tempKva;
																																								 }
																																								 
																																								 if(minKva == 0)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 if(tempKva < minKva)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 
																																							 }
																																							//'P7-1-5-1-0','P7-1-18-0-0'
																																								//KWH
																																								 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0")||
																																										 paramCode.equalsIgnoreCase("P7-1-18-0-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																								 {
																																									 //System.out.println("====== "+Float.parseFloat(paramValue));
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									 d4KwhValue = paramValue;
																																									 float tempKwh = Float.parseFloat(paramValue);
																																									 if(sumKwhFlag)
																																									 {
																																										 sumKwh = sumKwh + tempKwh;
																																										 sumKwhFlag = false;
																																									 }
																																									 kwhFlag = 1;
																																									 /*if(tempKwh > maxKwh)
																																									 {
																																										 maxKwh = tempKwh;
																																									 }*/
																																									 
																																								 }
																																								 //D4VRphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4vrValue = paramValue;
																																								 }
																																								 //D4VYphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vyValue= paramValue;
																																								 }
																																								 //D4VBphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vbValue= paramValue;
																																								 }
																																								//D4ARphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4arValue = paramValue;
																																								 }
																																								 //D4AYphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4ayValue= paramValue;
																																								 }
																																								 //D4ABphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4abValue= paramValue;
																																								 }
																																								 
																																					 }
																																					 else  if(manufacturerCode.equalsIgnoreCase("4"))
																																					 {
																																						//FOR Genus
																																							//KVA
																																							 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																									 paramCode.equalsIgnoreCase("P7-3-5-1-0") || paramCode.equalsIgnoreCase("P7-4-5-2-0") ||
																																									 paramCode.equalsIgnoreCase("P7-6-5-2-4")  ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 d4KvaValue = paramValue;
																																								 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																										d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																								 float tempKva = Float.parseFloat(paramValue);
																																								 
																																								 if(sumKvaFlag)
																																								 {
																																									 sumKva = sumKva + tempKva;
																																									 sumKvaFlag = false;
																																								 }
																																								 
																																								 if(tempKva > maxKva)
																																								 {
																																									 maxKva = tempKva;
																																								 }
																																								 
																																								 if(minKva == 0)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 if(tempKva < minKva)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 
																																							 }
																																							 
																																							 
																																							//'P7-1-5-1-0','P7-1-18-0-0'
																																								//KWH
																																								 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-18-0-0")||
																																										 paramCode.equalsIgnoreCase("P7-1-5-2-0") || paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																								 {
																																									 //System.out.println("====== "+Float.parseFloat(paramValue));
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									 d4KwhValue = paramValue;
																																									 float tempKwh = Float.parseFloat(paramValue);
																																									 if(sumKwhFlag)
																																									 {
																																										 sumKwh = sumKwh + tempKwh;
																																										 sumKwhFlag = false;
																																									 }
																																									 kwhFlag = 1;
																																									 /*if(tempKwh > maxKwh)
																																									 {
																																										 maxKwh = tempKwh;
																																									 }*/
																																									 
																																								 }
																																								 //D4VRphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4vrValue = paramValue;
																																								 }
																																								 //D4VYphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vyValue= paramValue;
																																								 }
																																								 //D4VBphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vbValue= paramValue;
																																								 }
																																								//D4ARphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4arValue = paramValue;
																																								 }
																																								 //D4AYphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4ayValue= paramValue;
																																								 }
																																								 //D4ABphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4abValue= paramValue;
																																								 }
																																								 
																																					 }
																																						else
																																						{
																																									//FOR HPL
																																										//KVA
																																										 if(paramCode.equalsIgnoreCase("P7-6-13-2-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																												 paramCode.equalsIgnoreCase("P7-6-13-1-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																										 {
																																											 //System.out.println("====== "+Float.parseFloat(paramValue));
																																											 if( paramValue == "")
																																											 {
																																												 paramValue = "0";
																																											 }
																																											 
																																											 d4KvaValue = paramValue;
																																											 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																													d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																											 float tempKva = Float.parseFloat(paramValue);
																																											 if(sumKvaFlag)
																																											 {
																																												 sumKva = sumKva + tempKva;
																																												 sumKvaFlag = false;
																																											 }
																																											 
																																											 if(tempKva > maxKva)
																																											 {
																																												 maxKva = tempKva;
																																											 }
																																											 
																																											 if(minKva == 0)
																																											 {
																																												 minKva = tempKva;
																																											 }
																																											 if(tempKva < minKva)
																																											 {
																																												 minKva = tempKva;
																																											 }
																																											 
																																										 }
																																										 
																																										//'P7-1-5-1-0','P7-1-18-0-0'
																																											//KWH
																																											 if(paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																													 paramCode.equalsIgnoreCase("P7-1-13-1-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																											 {
																																												 //System.out.println("====== "+Float.parseFloat(paramValue));
																																												 if( paramValue == "")
																																												 {
																																													 paramValue = "0";
																																												 }
																																												 d4KwhValue = paramValue;
																																												 float tempKwh = Float.parseFloat(paramValue);
																																												 if(sumKwhFlag)
																																												 {
																																													 sumKwh = sumKwh + tempKwh;
																																													 sumKwhFlag = false;
																																												 }
																																												 kwhFlag = 1;
																																												 /*if(tempKwh > maxKwh)
																																												 {
																																													 maxKwh = tempKwh;
																																												 }*/
																																												 
																																											 }
																																											 //D4VRphase value
																																											 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																											 {
																																												 if( paramValue == "")
																																												 {
																																													 paramValue = "0";
																																												 }
																																												d4vrValue = paramValue;
																																											 }
																																											 //D4VYphase value
																																											 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																											 {
																																												 if(paramValue == "")
																																												 {
																																													 paramValue="0";
																																												 }
																																												 d4vyValue= paramValue;
																																											 }
																																											 //D4VBphase value
																																											 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																											 {
																																												 if(paramValue == "")
																																												 {
																																													 paramValue="0";
																																												 }
																																												 d4vbValue= paramValue;
																																											 }
																																											//D4ARphase value
																																											 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																											 {
																																												 if( paramValue == "")
																																												 {
																																													 paramValue = "0";
																																												 }
																																												d4arValue = paramValue;
																																											 }
																																											 //D4AYphase value
																																											 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																											 {
																																												 if(paramValue == "")
																																												 {
																																													 paramValue="0";
																																												 }
																																												 d4ayValue= paramValue;
																																											 }
																																											 //D4ABphase value
																																											 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																											 {
																																												 if(paramValue == "")
																																												 {
																																													 paramValue="0";
																																												 }
																																												 d4abValue= paramValue;
																																											 }
																																									 
																																								 }//End HPL
																																								 
																																								//Load Utilization values
																																								 /*if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																										 paramCode.equalsIgnoreCase("P7-3-5-1-0"))*/
																																								 //HPL
																																								 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																										 paramCode.equalsIgnoreCase("P7-3-5-1-0")||paramCode.equalsIgnoreCase("P7-6-13-2-0")
																																										 ||paramCode.equalsIgnoreCase("P7-6-13-1-0") || paramCode.equalsIgnoreCase("P7-6-5-2-4") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									 float tempKva = Float.parseFloat(paramValue);
																																									 if(Integer.parseInt(intervalPeriod) > 15)
																																									 {
																																										 if((ipIntervalNum >= 18) && (ipIntervalNum <= 33))
																																										 {
																																											 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																											 if(tempKva <= cd_20)
																																											 {
																																												 kva1_lt_cd20++;
																																											 }
																																											 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																											 {
																																												 kva1_lt_cd40++;
																																											 }
																																											 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																											 {
																																												 kva1_lt_cd60++;
																																											 }
																																											 else if(tempKva > cd_60)
																																											 {
																																												 kva1_gt_cd60++;
																																											 }
																																										 }
																																										 else if((ipIntervalNum < 18) || (ipIntervalNum >= 34))
																																										 {
																																											 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																											 if(tempKva <= cd_20)
																																											 {
																																												 kva2_lt_cd20++;
																																											 }
																																											 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																											 {
																																												 kva2_lt_cd40++;
																																											 }
																																											 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																											 {
																																												 kva2_lt_cd60++;
																																											 }
																																											 else if(tempKva > cd_60)
																																											 {
																																												 kva2_gt_cd60++;
																																											 }
																																										 }
																																									 }
																																									 else{
																																										 if((ipIntervalNum >= 36) && (ipIntervalNum <= 68))
																																										 {

																																											 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																											 if(tempKva <= cd_20)
																																											 {
																																												 kva1_lt_cd20++;
																																											 }
																																											 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																											 {
																																												 kva1_lt_cd40++;
																																											 }
																																											 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																											 {
																																												 kva1_lt_cd60++;
																																											 }
																																											 else if(tempKva > cd_60)
																																											 {
																																												 kva1_gt_cd60++;
																																											 }
																																										 
																																										 }
																																										 else if((ipIntervalNum <= 35) || (ipIntervalNum >= 69))
																																										 {

																																											 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																											 if(tempKva <= cd_20)
																																											 {
																																												 kva2_lt_cd20++;
																																											 }
																																											 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																											 {
																																												 kva2_lt_cd40++;
																																											 }
																																											 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																											 {
																																												 kva2_lt_cd60++;
																																											 }
																																											 else if(tempKva > cd_60)
																																											 {
																																												 kva2_gt_cd60++;
																																											 }
																																										 
																																										 }
																																									 }
																																								 }
																																								 if(paramCode.equalsIgnoreCase("P4-4-4-1-0")||paramCode.equalsIgnoreCase("P4-4-4-0-0"))
																																								 {
																																									 //System.out.println("====== "+Float.parseFloat(paramValue));
																																									if(paramValue.equalsIgnoreCase("") )
																																									{
																																										paramValue = "0";
																																									}
																																									d4PfValue = paramValue;
																																									 float tempPf = Float.parseFloat(paramValue);
																																										
																																									 sumPf = sumPf + tempPf;
																																									 
																																									 //pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0;
																																									 
																																									 if(tempPf == 0)
																																									 {
																																										pfNoLoad++; 
																																									 }
																																									 if(tempPf == -1)
																																									 {
																																										pfBlackOut++; 
																																									 }
																																									 if((tempPf != 0) && (tempPf != -1))
																																									 {
																																										 if(tempPf <= 0.5F)
																																										 {
																																											 pfLt05++;
																																										 }
																																										 else if((tempPf > 0.5F) && (tempPf < 0.7F))
																																										 {
																																											 pf05To07++;
																																											// pfVal1 = pfVal1 + tempPf + ",";
																																										 }
																																										 else if((tempPf >= 0.7F) && (tempPf <= 0.9F))
																																										 {
																																											 pf07To09++;
																																											 //pfVal2 = pfVal2 + tempPf + ",";
																																										 }
																																										 else if(tempPf >0.9F)
																																										 {
																																											 pfGt09++;
																																										 }
																																									 }
																																									 /*if(tempPf > maxPf)
																																									 {
																																										 maxPf = tempPf;
																																									 }*/
																																									
																																								 }
																																								 
																																								 
																																								//newly added
																																								 System.err
																																										.println("paramcode-===>"+paramCode);
																																								 if(paramCode.equalsIgnoreCase("P7-2-4-2-0"))
																																								 {
																																									 if(paramValue.equalsIgnoreCase("") )
																																										{
																																											paramValue = "0.0";
																																										}
																																									 else{
																																										 d4loadkvarh_lead = Double.parseDouble(paramValue);
																																									 }
																																								 }
																																								 if(paramCode.equalsIgnoreCase("P7-2-1-2-0"))
																																								 {
																																									 if(paramValue.equalsIgnoreCase("") )
																																										{
																																											paramValue = "0";
																																										}
																																									 else{
																																										 d4loadkvarh_lag = Double.parseDouble(paramValue);
																																									 }
																																								 }
																																								 if(paramCode.equalsIgnoreCase("P7-1-5-2-0"))
																																								 {
																																									 if(paramValue.equalsIgnoreCase("") )
																																										{
																																											paramValue = "0";
																																										}
																																									 else{
																																										  d4loadkwh= Double.parseDouble(paramValue);
																																									 }
																																								 }
																																								 
																																								 if(paramCode.equalsIgnoreCase("P7-3-5-2-0"))
																																								 {
																																									 if(paramValue.equalsIgnoreCase("") )
																																										{
																																											paramValue = "0";
																																										}
																																									 else{
																																										  d4loadkvah = Double.parseDouble(paramValue);
																																									 }
																																								 }
																																								 if(paramCode.equalsIgnoreCase("P7-4-5-2-0"))
																																								 {
																																									 if(paramValue.equalsIgnoreCase("") )
																																										{
																																											paramValue = "0";
																																										}
																																									 else{
																																										  d4loadkw = Double.parseDouble(paramValue);
																																									 }
																																								 }
																																								 if(paramCode.equalsIgnoreCase("P7-6-5-2-0"))
																																								 {
																																									 if(paramValue.equalsIgnoreCase("") )
																																										{
																																											paramValue = "0";
																																										}
																																									 else{
																																										  d4loadkva = Double.parseDouble(paramValue);
																																									 }
																																								 }
																																								 
																																								 
																																								 
																																								 Double val=paramValue==null?0:Double.parseDouble(paramValue);
																																                                    ParamCodeValidator validator=new ParamCodeValidator();

																																								 if(paramCode!=null) {
																																	                                    
																																	                                    String code=validator.readD4tags(paramCode);
																																	                                    if("i_r".equals(code)) {
																																	                                    	d4.setiR(val);
																																	                                    }
																																	                                    else if ("i_y".equals(code)) {
																																	                                    	d4.setiY(val);
																																	                                    }
																																	                                    else if ("i_b".equals(code)) {
																																	                                    	d4.setiB(val);
																																	                                    }
																																	                                    else if("v_r".equals(code)) {
																																	                                    	d4.setvR(val);
																																	                                    }
																																	                                    else if ("v_y".equals(code)) {
																																	                                    	d4.setvY(val);
																																	                                    }
																																	                                    else if ("v_b".equals(code)) {
																																	                                    	d4.setvB(val);
																																	                                    }
																																	                                    else if ("kvarh_lag".equals(code)) {
																																	                                    	d4.setKvarhLag(val);
																																	                                    }
																																	                                    else if ("kvarh_lead".equals(code)) {
																																	                                    	d4.setKvarhLead(val);
																																	                                    }
																																	                                    else if ("kwh".equals(code)) {
																																	                                    	d4.setKwh(val);
																																	                                    }
																																	                                    else if ("kvah".equals(code)) {
																																	                                    	d4.setKvah(val);
																																	                                    }
																																	                                    else if ("frequency".equals(code)) {
																																	                                    	d4.setFrequency(val);
																																	                                    }
																																	                                    
																																	                                	
																																	                                    
																																	                                    }
																																					 }
																																				
																																				 if(Integer.parseInt(ipInterval)!=0){
																																				 d4.setBillmonth(billmonth+"");d4.setCdfId(cdfId);d4.setMeterNo(meterNumber);
																																				 d4.setIpInterval(Integer.parseInt(ipInterval));
																																				 
																																				 if(d4KwhValue!=""||d4KwhValue!=null)
																												                                	{
																												                                		d4.setKwhValue(d4KwhValue);
																												                                	}else
																												                                	{
																												                                		d4.setKwhValue(d4loadkwh.toString());
																												                                	}
																																				 //d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																				 d4.setKvaValue(d4KvaValue);d4.setPfValue(d4PfValue);d4.setKwhValue(d4loadkwh.toString());
																																				 d4.setVrValue(d4vrValue);d4.setVyValue(d4vyValue);d4.setVbValue(d4vbValue);
																																				 d4.setArValue(d4arValue);d4.setAyValue(d4ayValue);d4.setAbValue(d4abValue);
																																				 d4.setIntervalPeriod(intervalPeriod);
																																				 d4.setKvarhLag(d4loadkvarh_lag);
																												                                	d4.setKvarhLead(d4loadkvarh_lead);
																												                                	d4.setKwh(d4loadkwh);
																												                                	d4.setKvah(d4loadkvah);
																												                                	//d4.setKw(d4loadkw);
																																				// d4.setImei(imeiNo);
																													                                d4.setTimeStamp(new Timestamp(G2dateVal.getTime()));
																													                                d4.setKvarhQ1(0.0);
																													                                d4.setKvarhQ2(0.0);
																													                                d4.setKvarhQ3(0.0);
																													                                d4.setKvarhQ4(0.0);
																													                                d4.setNetKwh(0.0);
																													                                d4.setDayProfileDate(new Timestamp((calendar.getTime()).getTime()));
																													                             //   d4.setModemTime(new Timestamp(G4.getTime()));
																													                               /* KeyLoad myKey=new KeyLoad();
																													                                myKey.setMeterNumber(meterNumber);
																													                                myKey.setReadTime(new Timestamp((calendar.getTime()).getTime())); /////
																													                                d4.setMyKey(myKey);*/
																													                                d4.setTransId("0000000000000");
																													                                d4.setKwhImp(0.0);
																													                                d4.setKwhExp(0.0);
																													                                d4.setStructureSize(0);
																																				 d4LoadDataService.save(d4);
																																				 }
																																				 calendar.add(Calendar.MINUTE, 30);
																																				 d4KvaValue = "";
																																				 d4KwhValue = "";
																																				 d4PfValue = "";
																																				 ipInterval = "";
																																				 d4vrValue="";
																																				 d4vyValue="";d4vbValue="";d4arValue="";d4ayValue="";d4abValue="";
																																				 
																																			}//IP ForLoop
																																		//assign all variables to zero
																																			
																																			//Power Factor Report
																																			int intervalVal = Integer.parseInt(intervalPeriod);
																																			
																																			maxKva = Math.round(maxKva * 1000.0)/1000.0;
																																			minKva = Math.round(minKva * 1000.0)/1000.0;
																																			sumKwh = Math.round(sumKwh * 1000.0)/1000.0;
																																			sumPf = Math.round(sumPf * 1000.0)/1000.0;
																																			sumKva = Math.round(sumKva * 1000.0)/1000.0;
																																			
																																			D4Data d4 = new D4Data();
																																			d4.setCdfId(cdfId);d4.setIntervalPeriod(intervalVal);
																																			if(dayProfileDate.length() > 2)
																																			{
																																			 String checkForDatePatter1 = dayProfileDate.substring(2);
																																			 if(checkForDatePatter1.startsWith("-"))
																																				{
																																				 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																				 
																																				}
																																				else if(checkForDatePatter1.startsWith("/"))
																																				{
																																					d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																					 
																																				}
																																				else
																																				{
																																					 dayProfileDate = "";
																																				}
																																			}
																																		    d4.setMinKva(minKva);d4.setMaxKva(maxKva);d4.setSumKwh(sumKwh);
																																	        d4.setKwhFlag(kwhFlag);d4.setSumPf(sumPf);d4.setPf05(pfLt05);
																																	        d4.setPf0507(pf05To07);d4.setPf0709(pf07To09);d4.setPf09(pfGt09);
																																	        d4.setPfNoload(pfNoLoad);d4.setPfBlackout(pfBlackOut);
																																	        d4.setIpGs20(kva1_lt_cd20);d4.setIpGs2040(kva1_lt_cd40);
																																	        d4.setIpGs4060(kva1_lt_cd60);d4.setIpGs60(kva1_gt_cd60);
																																	        d4.setIpOutGs20(kva2_lt_cd20);d4.setIpOutGs2040(kva2_lt_cd40);
																																	        d4.setIpOutGs4060(kva2_lt_cd60);d4.setIpOutGs60(kva2_gt_cd60);
																																	        d4.setMf(mf);d4.setCd(cd);d4.setSumKva(sumKva);
																																	        d4DtataService.save(d4);
																																	        kwhFlag = 0;
																																			maxKva = 0; minKva = 0; sumKwh = 0; sumPf = 0;sumKva = 0;
																																			pfLt05 = 0; pf05To07 = 0; pf07To09 = 0; pfGt09 = 0;  pfNoLoad = 0; pfBlackOut = 0;
																																			
																																			kva1_lt_cd20 = 0;kva1_lt_cd40 = 0;kva1_lt_cd60 = 0;kva1_gt_cd60 = 0;
																																			kva2_lt_cd20 = 0;kva2_lt_cd40 = 0;kva2_lt_cd60 = 0;kva2_gt_cd60 = 0;
																																			kva3_lt_cd20 = 0;kva3_lt_cd40 = 0;kva3_lt_cd60 = 0;kva3_gt_cd60 = 0;
																																			kva4_lt_cd20 = 0;kva4_lt_cd40 = 0;kva4_lt_cd60 = 0;kva4_gt_cd60 = 0;
																																	 }
																																 }//If Previous Data Der
																																 
																																 else{
																																	 
																																	    int kwhFlag = 0;
																																		NodeList subTempNodeListD4 = tempNodeD4.getChildNodes();
																																		 Calendar calendar = Calendar.getInstance();
																																		 Date datetime=null;
																																		 if(dayProfileDate.length() > 2)
																																			{
																																			 String checkForDatePatter1 = dayProfileDate.substring(2);
																																			 if(checkForDatePatter1.startsWith("-"))
																																			 {
																																				 datetime=sdf3.parse(dayProfileDate);
																																				 calendar.setTime(datetime);
																																			}
																																			else if(checkForDatePatter1.startsWith("/"))
																																			{
																																				 datetime=sdf4.parse(dayProfileDate);
																																				 calendar.setTime(datetime);
																																			}
																																				else
																																				{
																																					 dayProfileDate = "";
																																				}
																																			}
																																		for (int subCountD4 = 0; subCountD4 < subTempNodeListD4.getLength(); subCountD4++) 
																																		{
																																			String ipInterval = "0";
																																			int ipIntervalNum = 0;
																																			 Node subTempNode = subTempNodeListD4.item(subCountD4);
																																			 if (subTempNode.getNodeType() == Node.ELEMENT_NODE) 
																																			 {
																																				 if(subTempNode.hasAttributes()) 
																																	    			{
																																	    	 			NamedNodeMap nodeMap = subTempNode.getAttributes();
																																	    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																	    				{
																																	    					Node node = nodeMap.item(nodeMapIndex);
																																	    					ipInterval = node.getNodeValue();
																																	    					//System.out.println(" IP interval attr name : " + node.getNodeName()+" attr value : " + ipInterval);
																																	    																				    					
																																	    				}
																																	    			}
																																				 
																																			 }
																																			 ipIntervalNum = Integer.parseInt(ipInterval);
																																			 NodeList subTempNodeListIP = subTempNode.getChildNodes();
																																			 boolean sumKwhFlag = true;
																																			 boolean sumKvaFlag = true;
																																			 D4CdfData d4 = new D4CdfData();
																																			 for (int subCountIP = 0; subCountIP < subTempNodeListIP.getLength(); subCountIP++) 
																																				{
																																				    String tagAttrId = "";
																																					String paramCode = "", paramValue = "";
																																					Node subTempNodeIP = subTempNodeListIP.item(subCountIP);
																																					 if (subTempNodeIP.getNodeType() == Node.ELEMENT_NODE) 
																																					 {
																																						 if(subTempNodeIP.hasAttributes()) 
																																			    			{
																																							 NamedNodeMap nodeMap = subTempNodeIP.getAttributes();
																																			    				for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																																			    				{
																																			    					Node node = nodeMap.item(nodeMapIndex);
																																			    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																																			    					{
																																			    						paramCode = node.getNodeValue();
																																			    					}
																																			    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																																			    					{
																																			    						paramValue = node.getNodeValue();
																																			    					}
																																			    				}
																																							 
																																			    			}
																																						 
																																					 }
																																					 
																																					 if(manufacturerCode.equalsIgnoreCase("1")||manufacturerName.equalsIgnoreCase("SECURE METERS LTD."))
																																					 {

																																							//KVA
																																							 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																									 paramCode.equalsIgnoreCase("P7-3-5-0-0")||paramCode.equalsIgnoreCase("P7-4-18-0-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								d4KvaValue = paramValue;
																																								if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																									d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																								float tempKva = Float.parseFloat(paramValue);
																																								 
																																								 if(sumKvaFlag)
																																								 {
																																									 sumKva = sumKva + tempKva;
																																									 sumKvaFlag = false;
																																								 }
																																								 if(tempKva > maxKva)
																																								 {
																																									 maxKva = tempKva;
																																								 }
																																								 
																																								 if(minKva == 0)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 if(tempKva < minKva)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 
																																							 }
																																							 
																																							//'P7-1-5-1-0','P7-1-18-0-0'
																																							//KWH
																																							 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0")||
																																									 paramCode.equalsIgnoreCase("P7-1-18-0-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 d4KwhValue = paramValue;
																																								  float tempKwh = Float.parseFloat(paramValue);
																																								 if(sumKwhFlag)
																																								 {
																																									 sumKwh = sumKwh + tempKwh;
																																									 sumKwhFlag = false;
																																								 }
																																								 kwhFlag = 1;
																																								/* if(tempKwh > maxKwh)
																																								 {
																																									 maxKwh = tempKwh;
																																								 }*/
																																								 
																																							 }
																																							//D4VRphase value
																																							 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																							 {
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								d4vrValue = paramValue;
																																							 }
																																							 //D4VYphase value
																																							 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4vyValue= paramValue;
																																							 }
																																							 //D4VBphase value
																																							 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4vbValue= paramValue;
																																							 }
																																							//D4ARphase value
																																							 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																							 {
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								d4arValue = paramValue;
																																							 }
																																							 //D4AYphase value
																																							 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4ayValue= paramValue;
																																							 }
																																							 //D4ABphase value
																																							 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																							 {
																																								 if(paramValue == "")
																																								 {
																																									 paramValue="0";
																																								 }
																																								 d4abValue= paramValue;
																																							 }
																																							 
																																						}
																																					 else  if(manufacturerCode.equalsIgnoreCase("3")||manufacturerName.equalsIgnoreCase("LARSEN AND TOUBRO LIMITED"))
																																					 {
																																						//FOR L&T
																																							//KVA
																																							 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-3-5-2-0")||
																																									 paramCode.equalsIgnoreCase("P7-3-5-1-0") || paramCode.equalsIgnoreCase("P7-4-5-2-0") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 d4KvaValue = paramValue;
																																								 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																										d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																								 float tempKva = Float.parseFloat(paramValue);
																																								 
																																								 if(sumKvaFlag)
																																								 {
																																									 sumKva = sumKva + tempKva;
																																									 sumKvaFlag = false;
																																								 }
																																								 
																																								 if(tempKva > maxKva)
																																								 {
																																									 maxKva = tempKva;
																																								 }
																																								 
																																								 if(minKva == 0)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 if(tempKva < minKva)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 
																																							 }
																																							//'P7-1-5-1-0','P7-1-18-0-0'
																																								//KWH
																																								 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-18-0-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																								 {
																																									 //System.out.println("====== "+Float.parseFloat(paramValue));
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									 d4KwhValue = paramValue;
																																									 float tempKwh = Float.parseFloat(paramValue);
																																									 if(sumKwhFlag)
																																									 {
																																										 sumKwh = sumKwh + tempKwh;
																																										 sumKwhFlag = false;
																																									 }
																																									 kwhFlag = 1;
																																									 /*if(tempKwh > maxKwh)
																																									 {
																																										 maxKwh = tempKwh;
																																									 }*/
																																									 
																																								 }
																																								//D4VRphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4vrValue = paramValue;
																																								 }
																																								 //D4VYphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vyValue= paramValue;
																																								 }
																																								 //D4VBphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vbValue= paramValue;
																																								 }
																																								//D4ARphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4arValue = paramValue;
																																								 }
																																								 //D4AYphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4ayValue= paramValue;
																																								 }
																																								 //D4ABphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4abValue= paramValue;
																																								 }
																																								 
																																					 }
																																					 else  if(manufacturerCode.equalsIgnoreCase("4"))
																																					 {
																																						//FOR Genus
																																							//KVA
																																							 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||paramCode.equalsIgnoreCase("P7-6-5-2-0")||
																																									 paramCode.equalsIgnoreCase("P7-3-5-1-0") || paramCode.equalsIgnoreCase("P7-4-5-2-0") ||
																																									 paramCode.equalsIgnoreCase("P7-6-5-2-4") || paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																							 {
																																								 //System.out.println("====== "+Float.parseFloat(paramValue));
																																								 if( paramValue == "")
																																								 {
																																									 paramValue = "0";
																																								 }
																																								 d4KvaValue = paramValue;
																																								 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																										d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																								 float tempKva = Float.parseFloat(paramValue);
																																								 
																																								 if(sumKvaFlag)
																																								 {
																																									 sumKva = sumKva + tempKva;
																																									 sumKvaFlag = false;
																																								 }
																																								 
																																								 if(tempKva > maxKva)
																																								 {
																																									 maxKva = tempKva;
																																								 }
																																								 
																																								 if(minKva == 0)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 if(tempKva < minKva)
																																								 {
																																									 minKva = tempKva;
																																								 }
																																								 
																																							 }
																																							 
																																							//'P7-1-5-1-0','P7-1-18-0-0'
																																								//KWH
																																								 if(paramCode.equalsIgnoreCase("P7-1-5-1-0")||paramCode.equalsIgnoreCase("P7-1-18-0-0")||
																																										 paramCode.equalsIgnoreCase("P7-1-5-2-0") || paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																								 {
																																									 //System.out.println("====== "+Float.parseFloat(paramValue));
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									 d4KwhValue = paramValue;
																																									 float tempKwh = Float.parseFloat(paramValue);
																																									 if(sumKwhFlag)
																																									 {
																																										 sumKwh = sumKwh + tempKwh;
																																										 sumKwhFlag = false;
																																									 }
																																									 kwhFlag = 1;
																																									 /*if(tempKwh > maxKwh)
																																									 {
																																										 maxKwh = tempKwh;
																																									 }*/
																																									 
																																								 }
																																								//D4VRphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4vrValue = paramValue;
																																								 }
																																								 //D4VYphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vyValue= paramValue;
																																								 }
																																								 //D4VBphase value
																																								 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4vbValue= paramValue;
																																								 }
																																								//D4ARphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																								 {
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									d4arValue = paramValue;
																																								 }
																																								 //D4AYphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4ayValue= paramValue;
																																								 }
																																								 //D4ABphase value
																																								 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																								 {
																																									 if(paramValue == "")
																																									 {
																																										 paramValue="0";
																																									 }
																																									 d4abValue= paramValue;
																																								 }
																																					}
																																					 else{

																																							//FOR HPL
																																								//KVA
																																								 if(paramCode.equalsIgnoreCase("P7-6-13-2-0")||paramCode.equalsIgnoreCase("P7-6-13-1-0")||paramCode.equalsIgnoreCase("P7-6-5-2-0")
																																										 ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																								 {
																																									 //System.out.println("====== "+Float.parseFloat(paramValue));
																																									 if( paramValue == "")
																																									 {
																																										 paramValue = "0";
																																									 }
																																									 d4KvaValue = paramValue;
																																									 if(paramCode.equalsIgnoreCase("P7-3-18-2-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0"))
																																											d4KvaValue = Double.toString(Double.parseDouble(paramValue)*2);
																																									 float tempKva = Float.parseFloat(paramValue);
																																									 
																																									 if(sumKvaFlag)
																																									 {
																																										 sumKva = sumKva + tempKva;
																																										 sumKvaFlag = false;
																																									 }
																																									 
																																									 if(tempKva > maxKva)
																																									 {
																																										 maxKva = tempKva;
																																									 }
																																									 
																																									 if(minKva == 0)
																																									 {
																																										 minKva = tempKva;
																																									 }
																																									 if(tempKva < minKva)
																																									 {
																																										 minKva = tempKva;
																																									 }
																																									 
																																								 }
																																								 
																																								//'P7-1-5-1-0','P7-1-18-0-0'
																																									//KWH
																																									 if(paramCode.equalsIgnoreCase("P7-1-13-2-0")||paramCode.equalsIgnoreCase("P7-1-5-2-0")
																																											 ||paramCode.equalsIgnoreCase("P7-1-13-1-0")||paramCode.equalsIgnoreCase("P7-1-18-1-0"))
																																									 {
																																										 //System.out.println("====== "+Float.parseFloat(paramValue));
																																										 if( paramValue == "")
																																										 {
																																											 paramValue = "0";
																																										 }
																																										 d4KwhValue = paramValue;
																																										 float tempKwh = Float.parseFloat(paramValue);
																																										 if(sumKwhFlag)
																																										 {
																																											 sumKwh = sumKwh + tempKwh;
																																											 sumKwhFlag = false;
																																										 }
																																										 kwhFlag = 1;
																																										 /*if(tempKwh > maxKwh)
																																										 {
																																											 maxKwh = tempKwh;
																																										 }*/
																																										 
																																									 }
																																									//D4VRphase value
																																									 if(paramCode.equalsIgnoreCase("P1-2-1-1-0"))
																																									 {
																																										 if( paramValue == "")
																																										 {
																																											 paramValue = "0";
																																										 }
																																										d4vrValue = paramValue;
																																									 }
																																									 //D4VYphase value
																																									 if(paramCode.equalsIgnoreCase("P1-2-2-1-0"))
																																									 {
																																										 if(paramValue == "")
																																										 {
																																											 paramValue="0";
																																										 }
																																										 d4vyValue= paramValue;
																																									 }
																																									 //D4VBphase value
																																									 if(paramCode.equalsIgnoreCase("P1-2-3-1-0"))
																																									 {
																																										 if(paramValue == "")
																																										 {
																																											 paramValue="0";
																																										 }
																																										 d4vbValue= paramValue;
																																									 }
																																									//D4ARphase value
																																									 if(paramCode.equalsIgnoreCase("P2-1-1-1-0"))
																																									 {
																																										 if( paramValue == "")
																																										 {
																																											 paramValue = "0";
																																										 }
																																										d4arValue = paramValue;
																																									 }
																																									 //D4AYphase value
																																									 if(paramCode.equalsIgnoreCase("P2-1-2-1-0"))
																																									 {
																																										 if(paramValue == "")
																																										 {
																																											 paramValue="0";
																																										 }
																																										 d4ayValue= paramValue;
																																									 }
																																									 //D4ABphase value
																																									 if(paramCode.equalsIgnoreCase("P2-1-3-1-0"))
																																									 {
																																										 if(paramValue == "")
																																										 {
																																											 paramValue="0";
																																										 }
																																										 d4abValue= paramValue;
																																									 }
																																						}
																																					//Load Utilization values
																																					 /*if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-1-0"))*/
																																					 //HPL
																																					 if(paramCode.equalsIgnoreCase("P7-3-18-0-0")||paramCode.equalsIgnoreCase("P7-3-13-1-0")||
																																							 paramCode.equalsIgnoreCase("P7-3-5-1-0")||paramCode.equalsIgnoreCase("P7-6-13-2-0")
																																							 ||paramCode.equalsIgnoreCase("P7-6-13-1-0") || paramCode.equalsIgnoreCase("P7-6-5-2-4") ||paramCode.equalsIgnoreCase("P7-3-18-2-0"))
																																					 {
																																						 if( paramValue == "")
																																						 {
																																							 paramValue = "0";
																																						 }
																																						 float tempKva = Float.parseFloat(paramValue);
																																						 if(Integer.parseInt(intervalPeriod) > 15)
																																						 {
																																							 if((ipIntervalNum >= 18) && (ipIntervalNum <= 33))
																																							 {
																																								 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																								 if(tempKva <= cd_20)
																																								 {
																																									 kva1_lt_cd20++;
																																								 }
																																								 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																								 {
																																									 kva1_lt_cd40++;
																																								 }
																																								 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																								 {
																																									 kva1_lt_cd60++;
																																								 }
																																								 else if(tempKva > cd_60)
																																								 {
																																									 kva1_gt_cd60++;
																																								 }
																																							 }
																																							 else if((ipIntervalNum < 18) || (ipIntervalNum >= 34))
																																							 {
																																								 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																								 if(tempKva <= cd_20)
																																								 {
																																									 kva2_lt_cd20++;
																																								 }
																																								 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																								 {
																																									 kva2_lt_cd40++;
																																								 }
																																								 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																								 {
																																									 kva2_lt_cd60++;
																																								 }
																																								 else if(tempKva > cd_60)
																																								 {
																																									 kva2_gt_cd60++;
																																								 }
																																							 }
																																						 }
																																						 else{
																																							 if((ipIntervalNum >= 36) && (ipIntervalNum <= 68))
																																							 {

																																								 //System.out.println("(ipIntervalNum >= 18) && (ipIntervalNum <= 34) -- "+ipIntervalNum);
																																								 if(tempKva <= cd_20)
																																								 {
																																									 kva1_lt_cd20++;
																																								 }
																																								 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																								 {
																																									 kva1_lt_cd40++;
																																								 }
																																								 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																								 {
																																									 kva1_lt_cd60++;
																																								 }
																																								 else if(tempKva > cd_60)
																																								 {
																																									 kva1_gt_cd60++;
																																								 }
																																							 
																																							 }
																																							 else if((ipIntervalNum <= 35) || (ipIntervalNum >= 69))
																																							 {

																																								 //System.out.println("(ipIntervalNum < 18) || (ipIntervalNum >= 35) -- "+ipIntervalNum);
																																								 if(tempKva <= cd_20)
																																								 {
																																									 kva2_lt_cd20++;
																																								 }
																																								 else if((tempKva > cd_20)&&(tempKva <= cd_40))
																																								 {
																																									 kva2_lt_cd40++;
																																								 }
																																								 else if((tempKva > cd_40)&&(tempKva <= cd_60))
																																								 {
																																									 kva2_lt_cd60++;
																																								 }
																																								 else if(tempKva > cd_60)
																																								 {
																																									 kva2_gt_cd60++;
																																								 }
																																							 
																																							 }
																																						 }
																																					 }
																																					 
																																					 if(paramCode.equalsIgnoreCase("P4-4-4-1-0")||paramCode.equalsIgnoreCase("P4-4-4-0-0"))
																																					 {
																																						 //System.out.println("====== "+Float.parseFloat(paramValue));
																																						if(paramValue.equalsIgnoreCase("") )
																																						{
																																							paramValue = "0";
																																						}
																																						
																																						 float tempPf = Float.parseFloat(paramValue);
																																							
																																						 sumPf = sumPf + tempPf;
																																						 d4PfValue = paramValue;
																																						 //pfLt05=0, pf05To07=0, pf07To09=0, pfGt09=0;
																																						 
																																						 if(tempPf == 0)
																																						 {
																																							pfNoLoad++; 
																																						 }
																																						 if(tempPf == -1)
																																						 {
																																							pfBlackOut++; 
																																						 }
																																						 if((tempPf != 0) && (tempPf != -1))
																																						 {
																																							 if(tempPf <= 0.5F)
																																							 {
																																								 pfLt05++;
																																							 }
																																							 else if((tempPf > 0.5F) && (tempPf < 0.7F))
																																							 {
																																								 pf05To07++;
																																								// pfVal1 = pfVal1 + tempPf + ",";
																																							 }
																																							 else if((tempPf >= 0.7F) && (tempPf <= 0.9F))
																																							 {
																																								 pf07To09++;
																																								 //pfVal2 = pfVal2 + tempPf + ",";
																																							 }
																																							 else if(tempPf >0.9F)
																																							 {
																																								 pfGt09++;
																																							 }
																																						 }
																																						 /*if(tempPf > maxPf)
																																						 {
																																							 maxPf = tempPf;
																																						 }*/
																																						
																																					 }
																																					 if(paramCode.equalsIgnoreCase("P7-2-4-2-0"))
																																					 {
																																						 if(paramValue.equalsIgnoreCase("") )
																																							{
																																								paramValue = "0";
																																							}
																																						 else{
																																							 d4loadkvarh_lead = Double.parseDouble(paramValue);
																																						 }
																																					 }
																																					 if(paramCode.equalsIgnoreCase("P7-2-1-2-0"))
																																					 {
																																						 if(paramValue.equalsIgnoreCase("") )
																																							{
																																								paramValue = "0";
																																							}
																																						 else{
																																							 d4loadkvarh_lag = Double.parseDouble(paramValue);
																																						 }
																																					 }
																																					 if(paramCode.equalsIgnoreCase("P7-1-5-2-0"))
																																					 {
																																						 if(paramValue.equalsIgnoreCase("") )
																																							{
																																								paramValue = "0";
																																							}
																																						 else{
																																							  d4loadkwh= Double.parseDouble(paramValue);
																																						 }
																																					 }
																																					 
																																					 if(paramCode.equalsIgnoreCase("P7-3-5-2-0"))
																																					 {
																																						 if(paramValue.equalsIgnoreCase("") )
																																							{
																																								paramValue = "0";
																																							}
																																						 else{
																																							  d4loadkvah = Double.parseDouble(paramValue);
																																						 }
																																					 }
																																					 if(paramCode.equalsIgnoreCase("P7-4-5-2-0"))
																																					 {
																																						 if(paramValue.equalsIgnoreCase("") )
																																							{
																																								paramValue = "0";
																																							}
																																						 else{
																																							  d4loadkw = Double.parseDouble(paramValue);
																																						 }
																																					 }
																																					 if(paramCode.equalsIgnoreCase("P7-6-5-2-0"))
																																					 {
																																						 if(paramValue.equalsIgnoreCase("") )
																																							{
																																								paramValue = "0";
																																							}
																																						 else{
																																							  d4loadkva = Double.parseDouble(paramValue);
																																						 }
																																					 }
																																					 
																																					 
																																					 Double val=paramValue==null?0:Double.parseDouble(paramValue);
																													                                    ParamCodeValidator validator=new ParamCodeValidator();

																																					 if(paramCode!=null) {
																														                                    
																														                                    String code=validator.readD4tags(paramCode);
																														                                    if("i_r".equals(code)) {
																														                                    	d4.setiR(val);
																														                                    	d4.setiR(val);
																														                                    }
																														                                    else if ("i_y".equals(code)) {
																														                                    	d4.setiY(val);
																														                                    }
																														                                    else if ("i_b".equals(code)) {
																														                                    	d4.setiB(val);
																														                                    }
																														                                    else if("v_r".equals(code)) {
																														                                    	d4.setvR(val);
																														                                    }
																														                                    else if ("v_y".equals(code)) {
																														                                    	d4.setvY(val);
																														                                    }
																														                                    else if ("v_b".equals(code)) {
																														                                    	d4.setvB(val);
																														                                    }
																														                                   
																														                                    else if ("kvarh_lag".equals(code)) {
																														                                    	d4.setKvarhLag(val);
																														                                    }
																														                                    else if ("kvarh_lead".equals(code)) {
																														                                    	d4.setKvarhLead(val);
																														                                    }
																														                                    else if ("kwh".equals(code)) {
																														                                    	d4.setKwh(val);
																														                                    }
																														                                    else if ("kvah".equals(code)) {
																														                                    	d4.setKvah(val);
																														                                    }
																														                                    else if ("frequency".equals(code)) {
																														                                    	d4.setFrequency(val);
																														                                    }
																														                                  }
																																				}
																																			 
																																			
																																			 if(Integer.parseInt(ipInterval)!=0){
																																			 d4.setBillmonth(billmonth+"");d4.setCdfId(cdfId);d4.setMeterNo(meterNumber);
																																			 d4.setIpInterval(Integer.parseInt(ipInterval));
																																			 //d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																			 if(d4KwhValue!=""||d4KwhValue!=null)
																											                                	{
																											                                		d4.setKwhValue(d4KwhValue);
																											                                	}else
																											                                	{
																											                                		d4.setKwhValue(d4loadkwh.toString());
																											                                	}
																																			 d4.setKvaValue(d4KvaValue);d4.setPfValue(d4PfValue);d4.setKwhValue(d4loadkwh.toString());
																																			 d4.setVrValue(d4vrValue);d4.setVyValue(d4vyValue);d4.setVbValue(d4vbValue);
																																			 d4.setArValue(d4arValue);d4.setAyValue(d4ayValue);d4.setAbValue(d4abValue);
																																			 d4.setIntervalPeriod(intervalPeriod);
																																			 d4.setKvarhLag(d4loadkvarh_lag);
																											                                	d4.setKvarhLead(d4loadkvarh_lead);
																											                                	d4.setKwh(d4loadkwh);
																											                                	d4.setKvah(d4loadkvah);
																											                                	//d4.setKw(d4loadkw);
																											                                	  d4.setDayProfileDate(new Timestamp((calendar.getTime()).getTime()));
																																			 d4LoadDataService.save(d4);
																																			 }
																																			 calendar.add(Calendar.MINUTE, 30);
																																			 d4KvaValue = "";
																																			 d4KwhValue = "";
																																			 d4PfValue = "";
																																			 ipInterval = "";
																																			 d4vrValue="";
																																			 d4vyValue="";d4vbValue="";d4arValue="";d4ayValue="";d4abValue="";
																																		}
																																		//assign all variables to zero
																																		
																																		//Power Factor Report
																																		int intervalVal = Integer.parseInt(intervalPeriod);
																																		
																																		maxKva = Math.round(maxKva * 1000.0)/1000.0;
																																		minKva = Math.round(minKva * 1000.0)/1000.0;
																																		sumKwh = Math.round(sumKwh * 1000.0)/1000.0;
																																		sumPf = Math.round(sumPf * 1000.0)/1000.0;
																																		sumKva = Math.round(sumKva * 1000.0)/1000.0;
																																		
																																		D4Data d4 = new D4Data();
																																		d4.setCdfId(cdfId);d4.setIntervalPeriod(intervalVal);
																																		if(dayProfileDate.length() > 2)
																																		{
																																		 String checkForDatePatter1 = dayProfileDate.substring(2);
																																		 if(checkForDatePatter1.startsWith("-"))
																																			{
																																			 d4.setDayProfileDate(sdf3.parse(dayProfileDate));
																																			 
																																			}
																																			else if(checkForDatePatter1.startsWith("/"))
																																			{
																																				d4.setDayProfileDate(sdf4.parse(dayProfileDate));
																																				 
																																			}
																																			else
																																			{
																																				 dayProfileDate = "";
																																			}
																																		}
																																	    d4.setMinKva(minKva);d4.setMaxKva(maxKva);d4.setSumKwh(sumKwh);
																																        d4.setKwhFlag(kwhFlag);d4.setSumPf(sumPf);d4.setPf05(pfLt05);
																																        d4.setPf0507(pf05To07);d4.setPf0709(pf07To09);d4.setPf09(pfGt09);
																																        d4.setPfNoload(pfNoLoad);d4.setPfBlackout(pfBlackOut);
																																        d4.setIpGs20(kva1_lt_cd20);d4.setIpGs2040(kva1_lt_cd40);
																																        d4.setIpGs4060(kva1_lt_cd60);d4.setIpGs60(kva1_gt_cd60);
																																        d4.setIpOutGs20(kva2_lt_cd20);d4.setIpOutGs2040(kva2_lt_cd40);
																																        d4.setIpOutGs4060(kva2_lt_cd60);d4.setIpOutGs60(kva2_gt_cd60);
																																        d4.setMf(mf);d4.setCd(cd);d4.setSumKva(sumKva);
																																        d4DtataService.save(d4);
																																        
																																        kwhFlag = 0;
																																		maxKva = 0; minKva = 0; sumKwh = 0; sumPf = 0;sumKva = 0;
																																		pfLt05 = 0; pf05To07 = 0; pf07To09 = 0; pfGt09 = 0;  pfNoLoad = 0; pfBlackOut = 0;
																																		
																																		kva1_lt_cd20 = 0;kva1_lt_cd40 = 0;kva1_lt_cd60 = 0;kva1_gt_cd60 = 0;
																																		kva2_lt_cd20 = 0;kva2_lt_cd40 = 0;kva2_lt_cd60 = 0;kva2_gt_cd60 = 0;
																																		kva3_lt_cd20 = 0;kva3_lt_cd40 = 0;kva3_lt_cd60 = 0;kva3_gt_cd60 = 0;
																																		kva4_lt_cd20 = 0;kva4_lt_cd40 = 0;kva4_lt_cd60 = 0;kva4_gt_cd60 = 0;
																																	 
																																 }}
																															}
																														 
																														 
																														}//End For Loop
																													 
																													 
																													 System.out.println("Insert into D444 completed");
																												 }
																											 }
																										 }
																									 }
																								 }
																							 }
																						}
																					}
																					
																				}
																			}
																		}
																		catch(Exception e)
																		{
																			e.printStackTrace();
																		}
																		 
																	 }
																	 else
																	 {
																		 
																		 System.out.println("------D4 Data Present ---------");
																	 }
																	 //Duplicate Files Deleted
																	 mainStatus = "duplicate";
																	 System.out.println("metrnooo->"+meterNumber);
																	 System.out.println("-----------Meter Already Exist--------");
																	 String source = unZipFIlePath + "/"+filename;
																	 File sourceFile = new File(source);
																	 //System.out.println("4228 Source file delete : "+sourceFile.delete());
																 }
															}
														}
													 }
												 }//End D1 Tag
											 }
										 }
										 
									 }
								 }
							 }
						}
					}
					
				}
				
			}
			
		}
		
		catch(Exception e)
		{
			mainStatus="corrupted";
			System.out.println("metrnooo->"+meterNumber);
			e.printStackTrace();
			//return mainStatus;
		}
		if(meterNumber.equalsIgnoreCase(""))
		{
			meterNumber="empty";
		}
		return mainStatus+"/"+meterNumber;
	}

	
	/*@Transactional(propagation=Propagation.REQUIRED)
	public void importBillingParameters(Document docForMetrNo)
	{
		
		try{
		String manufacturerCode = "",manufacturerName = "";
		String meterno="",rdate="",kwh="0",kwhunit="0",kvh="0",kvhunit="0",kva="0",kvaunit="",arb="",pf="0",kwharb="",kvaarb="",kvharb="",pfarb="",flag1="0",flag2="0",flag3="0",flag4="0",flag5="0",flag6="0";
		String g2Value = "", g3Value = "";
		String d1_OccDate = "",d2_OccDate = "",d3_OccDate = "",d4_OccDate = "",d5_OccDate = "",d6_OccDate="";
		int status=0;
		
		String rdate_d2="",kwh_d2="0",kwhunit_d2="0",kvh_d2="0",kvhunit_d2="0",kva_d2="0",kvaunit_d2="0",arb_d2="",pf_d2="0",kwharb_d2="",kvaarb_d2="",kvharb_d2="",pfarb_d2="",flag1_d2="0",flag2_d2="0",flag3_d2="0",flag4_d2="0",flag5_d2="0",flag6_d2="0";
		String rdate_d3="",kwh_d3="0",kwhunit_d3="0",kvh_d3="0",kvhunit_d3="0",kva_d3="0",kvaunit_d3="0",arb_d3="",pf_d3="0",kwharb_d3="",kvaarb_d3="",kvharb_d3="",pfarb_d3="",flag1_d3="0",flag2_d3="0",flag3_d3="0",flag4_d3="0",flag5_d3="0",flag6_d3="0";
		String rdate_d4="",kwh_d4="0",kwhunit_d4="0",kvh_d4="0",kvhunit_d4="0",kva_d4="0",kvaunit_d4="0",arb_d4="",pf_d4="0",kwharb_d4="",kvaarb_d4="",kvharb_d4="",pfarb_d4="",flag1_d4="0",flag2_d4="0",flag3_d4="0",flag4_d4="0",flag5_d4="0",flag6_d4="0";
		String rdate_d5="",kwh_d5="0",kwhunit_d5="0",kvh_d5="0",kvhunit_d5="0",kva_d5="0",kvaunit_d5="0",arb_d5="",pf_d5="0",kwharb_d5="",kvaarb_d5="",kvharb_d5="",pfarb_d5="",flag1_d5="0",flag2_d5="0",flag3_d5="0",flag4_d5="0",flag5_d5="0",flag6_d5="0";
		String rdate_d6="",kwh_d6="0",kwhunit_d6="0",kvh_d6="0",kvhunit_d6="0",kva_d6="0",kvaunit_d6="0",arb_d6="",pf_d6="0",kwharb_d6="",kvaarb_d6="",kvharb_d6="",pfarb_d6="",flag1_d6="0",flag2_d6="0",flag3_d6="0",flag4_d6="0",flag5_d6="0",flag6_d6="0";
		boolean d3_01_flag=false, d3_02_flag=false, d3_03_flag=false, d3_04_flag=false, d3_05_flag=false, d3_06_flag=false, d3_exist = false;

		String dateCount1 = "0", dateCount2 = "0", dateCount3 = "0", dateCount4 = "0", dateCount5 = "0", dateCount6 = "0";

		//for billMonth
		Date billDate = new Date();
		SimpleDateFormat sdfBillDate = new SimpleDateFormat("yyyyMM");
		String billMonth = sdfBillDate.format(billDate);
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
		 SimpleDateFormat sdf4 = new SimpleDateFormat("dd/MM/yyyy");
		//billMonth = "201306";
		int d4DayProfileCount = 0;
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(billDate); 
	    cal.add(Calendar.MONTH,-1);
		String dataMonth = sdfBillDate.format(cal.getTime());
		try
		{
			NodeList nodeList = docForMetrNo.getElementsByTagName("CDF");
			String meterclass="";
			for (int count = 0; count < nodeList.getLength(); count++) 
			{
				Node tempNode = nodeList.item(count);
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					if (tempNode.hasChildNodes())
					{
						NodeList subnodeList = tempNode.getChildNodes();
						for (int count1 = 0; count1 < subnodeList.getLength(); count1++) 
						{
							Node tempNode1 = subnodeList.item(count1);
							if (tempNode1.getNodeType() == Node.ELEMENT_NODE) 
							{
								if (tempNode1.hasChildNodes())
								{
									NodeList subnodeList1 = tempNode1.getChildNodes();
									for (int count12 = 0; count12 < subnodeList1.getLength(); count12++) 
									{
										Node tempNode12 = subnodeList1.item(count12);
										if (tempNode12.getNodeType() == Node.ELEMENT_NODE) 
										{
											if (tempNode12.hasChildNodes())
											{
												String dataType = "";
												 
												
												if(tempNode12.getNodeName().equalsIgnoreCase("D1"))
												{
													//System.out.println("Node name : "+tempNode12.getNodeName());
													NodeList subnodeListD1 = tempNode12.getChildNodes();
												
													for (int countD1 = 0; countD1 < subnodeListD1.getLength(); countD1++) 
													{
														 Node tempNode123 = subnodeListD1.item(countD1);
													 	if (tempNode123.getNodeType() == Node.ELEMENT_NODE) 
														{
															String nodeName = tempNode123.getNodeName();
															String nodeValue = tempNode123.getTextContent();
															//System.out.println("\nNode Name =" + nodeName );
															//System.out.println("Node Value =" + nodeValue);
															String tagId = "";
															if(nodeName.equalsIgnoreCase("G1"))
															{
																meterno=nodeValue;
																//System.out.println("meter no===========================>"+meterno);
															}
															
															if(nodeName.equalsIgnoreCase("G2"))
															{
																g2Value = nodeValue;
																//System.out.println("G2 value ===========================>"+g2Value);
															}

															if(nodeName.equalsIgnoreCase("G3"))
															{
																g3Value = nodeValue;
																//System.out.println("G3 value ===========================>"+g3Value);
															}

															if(nodeName.equalsIgnoreCase("G13"))
															{
																meterclass = nodeValue;
																//System.out.println("meter not no===========================>"+meterclass);
															}
															if(nodeName.equalsIgnoreCase("G22"))
															{
																if (tempNode123.hasAttributes()) 
																{
																	NamedNodeMap nodeMap = tempNode123.getAttributes();
																	//String manufacturerCode = "",manufacturerName = "";
																	
																	for (int i = 0; i < nodeMap.getLength(); i++) 
																	{
																		String d1AttrId = "", value = "";
																		Node node = nodeMap.item(i);
																		//System.out.println(" Parameter attr name : " + node.getNodeName()+" attr value : " + node.getNodeValue());
																		
																		if(node.getNodeName().equalsIgnoreCase("CODE")) 
																		{
																			manufacturerCode = node.getNodeValue();
																		}
																		else if(node.getNodeName().equalsIgnoreCase("NAME")) 
																		{
																			manufacturerName = node.getNodeValue();
																		}															    						
																																																						
																	}
																	
																}//End has Attribute
															}
															
													 		
														}
													}
												}// End Of D1
												else if(tempNode12.getNodeName().equalsIgnoreCase("D3"))
												{
													d3_exist = true;
													NodeList subnodeListD3 = tempNode12.getChildNodes();
													for (int countD3 = 0; countD3 < subnodeListD3.getLength(); countD3++) 
													{
														String d3TagCount = "", dateTime = "", mechanism = "", tagId = "", tagAttrId = "";
														String d3Id = "", d3AttrId = "", attrValue = "";
														Node tempNode123 = subnodeListD3.item(countD3);
														 if (tempNode123.getNodeType() == Node.ELEMENT_NODE) 
														 {
															 d3TagCount = tempNode123.getNodeName();
															 if (tempNode123.hasAttributes()) 
												    			{
												    	 			NamedNodeMap nodeMap = tempNode123.getAttributes();
												    				//String code = "",value = "", unit = "";
												    	 			
												    				for (int i = 0; i < nodeMap.getLength(); i++) 
												    				{
												    					Node node = nodeMap.item(i);
												    					//System.out.println("attr name : " + node.getNodeName());
												    					//System.out.println("attr value : " + node.getNodeValue());
												    					if(node.getNodeName().equalsIgnoreCase("DATETIME"))
												    						dateTime = node.getNodeValue();
												    					else if(node.getNodeName().equalsIgnoreCase("MECHANISM"))
												    						mechanism = node.getNodeValue();
												    																	    																	    					
												    				}
												    				String d3_01_dateTime="";
												    				if(d3TagCount.equalsIgnoreCase("D3-01"))
											    					{
												    					d3_01_dateTime = dateTime;	
												    					rdate=dateTime;	
																		d3_01_flag = true;
												    					//System.out.println("values are dates"+dateTime);
											    					}
												    				else if(d3TagCount.equalsIgnoreCase("D3-02"))
											    					{
												    					d3_01_dateTime = dateTime;	
												    					rdate_d2=dateTime;	
																		d3_02_flag = true;
												    					//System.out.println("values are dates"+dateTime);
											    					}
												    				
												    				else if(d3TagCount.equalsIgnoreCase("D3-03"))
											    					{
												    					d3_01_dateTime = dateTime;	
												    					rdate_d3=dateTime;	
																		d3_03_flag = true;
												    					//System.out.println("values are dates"+dateTime);
											    					}
												    				
												    				else if(d3TagCount.equalsIgnoreCase("D3-04"))
											    					{
												    					d3_01_dateTime = dateTime;	
												    					rdate_d4=dateTime;	
																		d3_04_flag = true;
												    					//System.out.println("values are dates"+dateTime);
											    					}
												    				else if(d3TagCount.equalsIgnoreCase("D3-05"))
											    					{
												    					d3_01_dateTime = dateTime;	
												    					rdate_d5=dateTime;	
																		d3_05_flag = true;
												    					//System.out.println("values are dates"+dateTime);
											    					}
												    				else if(d3TagCount.equalsIgnoreCase("D3-06"))
											    					{
												    					d3_01_dateTime = dateTime;	
												    					rdate_d6=dateTime;	
																		d3_06_flag = true;
												    					//System.out.println("values are dates"+dateTime);
											    					}
												    				
												    				//System.out.println("code : "+code+" unit : "+unit+" value : "+value);
												    				
												    			}
															 
															 NodeList subTempNodeListD3 = tempNode123.getChildNodes();
															 for (int subCountD3 = 0; subCountD3 < subTempNodeListD3.getLength(); subCountD3++) 
																{
																 	Node subTempNode123 = subTempNodeListD3.item(subCountD3);
																 	if (subTempNode123.getNodeType() == Node.ELEMENT_NODE) 
																 	{
																 		String subNodeName = subTempNode123.getNodeName();//B1, B2 ..
																		String subNodeValue = subTempNode123.getTextContent();
																		String code = "", value = "", unit = "",tod = "",occDate = "";
																		if(subTempNode123.hasAttributes()) 
														    			{
																			NamedNodeMap nodeMap = subTempNode123.getAttributes();
														    				String attributeId = "", attributeValue = "";
														    				
														    				for (int i = 0; i < nodeMap.getLength(); i++) 
														    				{
														    					Node node = nodeMap.item(i);
														    					if(d3TagCount.equalsIgnoreCase("D3-01"))
														    					{
														    						
														    						//System.out.println("am coming here"+tempindex++);
														    						
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																    						code = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																    						value = node.getNodeValue();
																    					 
																    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																    						unit = node.getNodeValue();	
																    					   
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																    						code = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																    						value = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																    						unit = node.getNodeValue();	
																						else if(node.getNodeName().equalsIgnoreCase("OCCDATE"))
																    						occDate = node.getNodeValue();	
																    					
														    						}
														    						if(subNodeName.equalsIgnoreCase("B6"))
														    						{
														    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																    						code = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																    						value = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																    						unit = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("TOD"))
																    						tod = node.getNodeValue();
														    						}
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																    						code = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																    						value = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																    						unit = node.getNodeValue();	
														    						}
														    					}
														    					
														    					//for d3-02
														    					
														    					if(d3TagCount.equalsIgnoreCase("D3-02") || d3TagCount.equalsIgnoreCase("D3-03") || d3TagCount.equalsIgnoreCase("D3-04") || d3TagCount.equalsIgnoreCase("D3-05") || d3TagCount.equalsIgnoreCase("D3-05") || d3TagCount.equalsIgnoreCase("D3-06"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																    						code = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																    						value = node.getNodeValue();
																    					 
																    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																    						unit = node.getNodeValue();	
																    					    //System.out.println("  code===============>"+code);  
																    					    //System.out.println("  value===============>"+value);
																    					   // System.out.println("unit value===============>"+unit);
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																    						code = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																    						value = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																    						unit = node.getNodeValue();
																						else if(node.getNodeName().equalsIgnoreCase("OCCDATE"))
																    						occDate = node.getNodeValue();	
																    					
														    						}
														    						if(subNodeName.equalsIgnoreCase("B6"))
														    						{
														    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																    						code = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																    						value = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																    						unit = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("TOD"))
																    						tod = node.getNodeValue();
														    						}
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//PARAMCODE="P7-1-18-0-0" VALUE="74860.9" UNIT
																    					if(node.getNodeName().equalsIgnoreCase("PARAMCODE"))
																    						code = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("VALUE"))
																    						value = node.getNodeValue();
																    					else if(node.getNodeName().equalsIgnoreCase("UNIT"))
																    						unit = node.getNodeValue();	
														    						}
														    					}
														    					
														    					//End of d3-02
														    					
														    				}
														    				
														    				//d3TagCount subNodeName
														    				String kwhValue="",kvahValue="",kvaValueB6="",pfValue="",kvaValue="";
														    				if(manufacturerCode.equalsIgnoreCase("1"))
														    				{
														    					if(d3TagCount.equalsIgnoreCase("D3-01"))
														    					{
															    					if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								flag1=value;
														    								kwh= value;
														    								kwharb=code;
														    								//System.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh= value;
														    								kwhunit=unit;
														    								kwharb=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								flag2=value;
														    								kwharb=code;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								flag3=value;
														    								kvharb=code;
														    								
														    								kvhunit=unit;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2.equalsIgnoreCase("0") &&  flag3.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvharb=code;
														    								kvhunit=unit;
																							flag6=value;

														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    						 	else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag2.equalsIgnoreCase("0") &&  flag3.equalsIgnoreCase("0") && flag6.equalsIgnoreCase("0") )
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvharb=code;
														    								kvhunit=unit;
														    								//System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    						}
															    					if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    								System.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								kvaunit=unit;
																							flag5=value;
																							d1_OccDate = occDate;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4.equalsIgnoreCase("0")&& flag5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
																							//flag5=value;
														    							}
														    						}
															    					if(subNodeName.equalsIgnoreCase("B9"))
														    						{

														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf=value;
														    								pfarb=code;
														    								//System.out.println("pfValue value==================>"+pfValue);
														    							}
														    						
														    						}
														    					}
															    				else
															    					if(d3TagCount.equalsIgnoreCase("D3-02"))
															    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d2=unit;
															    								flag1_d2=value;
															    								kwh_d2= value;
															    								kwharb_d2=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d2= value;
															    								kwhunit_d2=unit;
															    								kwharb_d2=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvhunit_d2=unit;
															    								flag2_d2=value;
															    								kwharb_d2=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								flag3_d2=value;
															    								kvharb_d2=code;
															    								
															    								kvhunit_d2=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d2.equalsIgnoreCase("0") &&  flag3_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvharb_d2=code;
															    								kvhunit_d2=unit;
																								flag6_d2=value;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag2_d2.equalsIgnoreCase("0") &&  flag3_d2.equalsIgnoreCase("0") &&    flag6_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d2= value;
															    								kvharb_d2=code;
															    								kvhunit_d2=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								flag4_d2=value;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								flag5_d2=value;
																								d2_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d2.equalsIgnoreCase("0")&& flag5_d2.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d2=value;
															    								kvaarb_d2=code;
															    								kvaunit_d2=unit;
																								d2_OccDate = occDate;
																								//flag5_d2=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d2=value;
															    								pfarb_d2=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    						
															    					}
															    					else if(d3TagCount.equalsIgnoreCase("D3-03"))
																    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d3=unit;
															    								flag1_d3=value;
															    								kwh_d3= value;
															    								kwharb_d3=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d3= value;
															    								kwhunit_d3=unit;
															    								kwharb_d3=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvhunit_d3=unit;
															    								flag2_d3=value;
															    								kwharb_d3=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								flag3_d3=value;
															    								kvharb_d3=code;
															    								
															    								kvhunit_d3=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d3.equalsIgnoreCase("0") &&  flag3_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvharb_d3=code;
															    								kvhunit_d3=unit;
																								flag6_d3=value;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag2_d3.equalsIgnoreCase("0") &&  flag3_d3.equalsIgnoreCase("0") && flag6_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d3= value;
															    								kvharb_d3=code;
															    								kvhunit_d3=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								flag4_d3=value;
															    								kvaunit_d3=unit;
																								d3_OccDate=occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								kvaunit_d3=unit;
																								flag5_d3=value;
																								d3_OccDate=occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d3.equalsIgnoreCase("0")&& flag5_d3.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d3=value;
															    								kvaarb_d3=code;
															    								kvaunit_d3=unit;
																								d3_OccDate=occDate;
																								//flag5_d3=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d3=value;
															    								pfarb_d3=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    						
															    					}
															    					else if(d3TagCount.equalsIgnoreCase("D3-04"))
																    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d4=unit;
															    								flag1_d4=value;
															    								kwh_d4= value;
															    								kwharb_d4=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d4= value;
															    								kwhunit_d4=unit;
															    								kwharb_d4=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								kvhunit_d4=unit;
															    								flag2_d4=value;
															    								kwharb_d4=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								flag3_d4=value;
															    								kvharb_d4=code;
															    								
															    								kvhunit_d4=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d4.equalsIgnoreCase("0") &&  flag3_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								kvharb_d4=code;
															    								kvhunit_d4=unit;
																								flag3_d4=value;
																								flag6_d4=value;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag2_d4.equalsIgnoreCase("0") &&  flag3_d4.equalsIgnoreCase("0")  &&  flag6_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d4= value;
															    								kvharb_d4=code;
															    								kvhunit_d4=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								flag4_d4=value;
															    								kvaunit_d4=unit;
																								d4_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								kvaunit_d4=unit;
																								flag5_d4=value;
																								d4_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d4.equalsIgnoreCase("0")&& flag5_d4.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d4=value;
															    								kvaarb_d4=code;
															    								kvaunit_d4=unit;
																								d4_OccDate = occDate;
																								//flag5_d4=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d4=value;
															    								pfarb_d4=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    						
															    					}
															    					else if(d3TagCount.equalsIgnoreCase("D3-05"))
																    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d5=unit;
															    								flag1_d5=value;
															    								kwh_d5= value;
															    								kwharb_d5=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d5= value;
															    								kwhunit_d5=unit;
															    								kwharb_d5=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								kvhunit_d5=unit;
															    								flag2_d5=value;
															    								kwharb_d5=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								flag3_d5=value;
															    								kvharb_d5=code;
															    								
															    								kvhunit_d5=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d5.equalsIgnoreCase("0") &&  flag3_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								kvharb_d5=code;
																								flag6_d5=value;
															    								kvhunit_d5=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag2_d5.equalsIgnoreCase("0") &&  flag3_d5.equalsIgnoreCase("0")&&  flag6_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d5= value;
															    								kvharb_d5=code;
															    								kvhunit_d5=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d5=value;
															    								kvaarb_d5=code;
															    								flag4_d5=value;
															    								kvaunit_d5=unit;
																								d5_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d5=value;
															    								kvaarb_d5=code;
															    								kvaunit_d5=unit;
																								flag5_d5=value;
																								d5_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d5.equalsIgnoreCase("0")&& flag5_d5.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d5=value;
															    								kvaarb_d5=code;
															    								kvaunit_d5=unit;
																								d5_OccDate = occDate;
																								//flag5_d5=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d5=value;
															    								pfarb_d5=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    						
															    					}
															    					else if(d3TagCount.equalsIgnoreCase("D3-06"))
																    					{
															    						if(subNodeName.equalsIgnoreCase("B3"))
															    						{
															    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
															    							{
															    								arb=code;
															    								kwhValue = value;
															    								kwhunit_d6=unit;
															    								flag1_d6=value;
															    								kwh_d6= value;
															    								kwharb_d6=code;
															    								//System.out.println("kwhValue value==================>"+kwhValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-1-18-0-0") && flag1_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kwh_d6= value;
															    								kwhunit_d6=unit;
															    								kwharb_d6=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								kvhunit_d6=unit;
															    								flag2_d6=value;
															    								kwharb_d6=code;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    							else if(code.equalsIgnoreCase("P7-3-18-0-0") && flag2_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								flag3_d6=value;
															    								kvharb_d6=code;
															    								
															    								kvhunit_d6=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d6.equalsIgnoreCase("0") &&  flag3_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								kvharb_d6=code;
																								flag6_d6=value;
															    								kvhunit_d6=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    						 	else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag2_d6.equalsIgnoreCase("0") &&  flag3_d6.equalsIgnoreCase("0") &&  flag6_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvahValue = value;
															    								kvh_d6= value;
															    								kvharb_d6=code;
															    								kvhunit_d6=unit;
															    								//System.out.println("kvahValue value==================>"+kvahValue);
															    							}
															    							
															    						}
															    						if(subNodeName.equalsIgnoreCase("B5"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								flag4_d6=value;
															    								kvaunit_d6=unit;
																								d6_OccDate = occDate;
															    								//System.out.println("kva value==================>"+kvaValue);
															    							}
															    							else if(code.equalsIgnoreCase("P7-6-18-0-0")&& flag4_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								kvaunit_d6=unit;
																								flag5_d6=value;
																								d6_OccDate = occDate;
															    							}
																							else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d6.equalsIgnoreCase("0")&& flag5_d6.equalsIgnoreCase("0"))
															    							{
															    								arb=code;
															    								kvaValue = value;
															    								kva_d6=value;
															    								kvaarb_d6=code;
															    								kvaunit_d6=unit;
																								d6_OccDate = occDate;
																								//flag5_d6=value;
															    							}
															    						}
															    						 
															    						if(subNodeName.equalsIgnoreCase("B9"))
															    						{
															    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
															    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
															    							{
															    								arb=code;
															    								pfValue = value;
															    								
															    								pf_d6=value;
															    								pfarb_d6=code;
															    								//System.out.println("pfValue value==================>"+pfValue);
															    							}
															    						}
															    						
															    					}
														    				}
														    				else if (manufacturerCode.equalsIgnoreCase("3")||manufacturerName.equalsIgnoreCase("LARSEN AND TOUBRO LIMITED"))
														    				{
														    					if(d3TagCount.equalsIgnoreCase("D3-01"))
														    					{

														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								flag1=value;
														    								kwh= value;
														    								kwharb=code;
														    								//systemout.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								//flag1=value;
														    								kwh= value;
														    								kwharb=code;
														    								//systemout.println("kwhValue value==================>"+kwhValue);
														    							}
														    						
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								flag2=value;
														    								kvharb=code;
														    								//systemout.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								//flag2=value;
														    								kvharb=code;
														    								//systemout.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    								//systemout.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								//flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    								//systemout.println("kva value==================>"+kvaValue);
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf=value;
														    								pfarb=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    						
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-02"))
															    				{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d2=unit;
														    								flag1_d2=value;
														    								kwh_d2= value;
														    								kwharb_d2=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d2.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d2=unit;
														    								//flag1_d2=value;
														    								kwh_d2= value;
														    								kwharb_d2=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								kvhunit_d2=unit;
														    								flag2_d2=value;
														    								kvharb_d2=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d2.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								kvhunit_d2=unit;
														    								//flag2_d2=value;
														    								kvharb_d2=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}
														    							
														    							
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								flag4_d2=value;
														    								kvaunit_d2=unit;
																							d2_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d2.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								//flag4_d2=value;
														    								kvaunit_d2=unit;
																							d2_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    						
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d2=value;
														    								pfarb_d2=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-03"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d3=unit;
														    								flag1_d3=value;
														    								kwh_d3= value;
														    								kwharb_d3=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d3.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d3=unit;
														    								//flag1_d3=value;
														    								kwh_d3= value;
														    								kwharb_d3=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d3= value;
														    								kvhunit_d3=unit;
														    								flag2_d3=value;
														    								kvharb_d3=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d3.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d3= value;
														    								kvhunit_d3=unit;
														    								//flag2_d3=value;
														    								kvharb_d3=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}
														    																					    							
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d3=value;
														    								kvaarb_d3=code;
														    								flag4_d3=value;
														    								kvaunit_d3=unit;
																							d3_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d3.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d3=value;
														    								kvaarb_d3=code;
														    								//flag4_d3=value;
														    								kvaunit_d3=unit;
																							d3_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    						
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d3=value;
														    								pfarb_d3=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    						
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-04"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d4=unit;
														    								flag1_d4=value;
														    								kwh_d4= value;
														    								kwharb_d4=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d4.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d4=unit;
														    								//flag1_d4=value;
														    								kwh_d4= value;
														    								kwharb_d4=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d4= value;
														    								kvhunit_d4=unit;
														    								flag2_d4=value;
														    								kvharb_d4=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d4.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d4= value;
														    								kvhunit_d4=unit;
														    								//flag2_d4=value;
														    								kvharb_d4=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}
														    							
														    						
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d4=value;
														    								kvaarb_d4=code;
														    								flag4_d4=value;
														    								kvaunit_d4=unit;
																							d4_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d4.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d4=value;
														    								kvaarb_d4=code;
														    								//flag4_d4=value;
														    								kvaunit_d4=unit;
																							d4_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    							
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d4=value;
														    								pfarb_d4=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    						
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-05"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d5=unit;
														    								flag1_d5=value;
														    								kwh_d5= value;
														    								kwharb_d5=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d5.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d5=unit;
														    								//flag1_d5=value;
														    								kwh_d5= value;
														    								kwharb_d5=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d5= value;
														    								kvhunit_d5=unit;
														    								flag2_d5=value;
														    								kvharb_d5=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d5.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d5= value;
														    								kvhunit_d5=unit;
														    								//flag2_d5=value;
														    								kvharb_d5=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}
														    							
														    							
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d5=value;
														    								kvaarb_d5=code;
														    								flag4_d5=value;
														    								kvaunit_d5=unit;
																							d5_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d5.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d5=value;
														    								kvaarb_d5=code;
														    								//flag4_d5=value;
														    								kvaunit_d5=unit;
																							d5_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    							
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d5=value;
														    								pfarb_d5=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    						
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-06"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d6=unit;
														    								flag1_d6=value;
														    								kwh_d6= value;
														    								kwharb_d6=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d6.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d6=unit;
														    								//flag1_d6=value;
														    								kwh_d6= value;
														    								kwharb_d6=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d6= value;
														    								kvhunit_d6=unit;
														    								flag2_d6=value;
														    								kvharb_d6=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d6.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d6= value;
														    								kvhunit_d6=unit;
														    								//flag2_d6=value;
														    								kvharb_d6=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}															    							
														    						
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d6=value;
														    								kvaarb_d6=code;
														    								flag4_d6=value;
														    								kvaunit_d6=unit;
																							d6_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d6.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d6=value;
														    								kvaarb_d6=code;
														    								//flag4_d6=value;
														    								kvaunit_d6=unit;
																							d6_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    							
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d6=value;
														    								pfarb_d6=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    				}
														    				else if(manufacturerCode.equalsIgnoreCase("4"))
														    				{
														    					if(d3TagCount.equalsIgnoreCase("D3-01"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								if(value == "")
																							{
																								value = "0";
																							}
																							float temp = Float.parseFloat(value);
																							flag1 = temp+"";
																							if(!flag1.equalsIgnoreCase("0.0") )
																							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								//flag1=value;
														    								kwh= value;
														    								kwharb=code;
																							}
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								//flag1=value;
														    								kwh= value;
														    								kwharb=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    						
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								flag2=value;
														    								kvharb=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								//flag2=value;
														    								kvharb=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								//flag4=value;
														    								kvaunit=unit;
																							d1_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf=value;
														    								pfarb=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    						
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-02"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								if(value == "")
																							{
																								value = "0";
																							}
																							float temp = Float.parseFloat(value);
																							flag1_d2 = temp+"";
																							if(!flag1_d2.equalsIgnoreCase("0.0") )
																							{
																						
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d2=unit;
														    								//flag1_d2=value;
														    								kwh_d2= value;
														    								kwharb_d2=code;
																							}
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d2.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d2=unit;
														    								//flag1_d2=value;
														    								kwh_d2= value;
														    								kwharb_d2=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								kvhunit_d2=unit;
														    								flag2_d2=value;
														    								kvharb_d2=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d2.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								kvhunit_d2=unit;
														    								//flag2_d2=value;
														    								kvharb_d2=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}
														    							
														    							
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								flag4_d2=value;
														    								kvaunit_d2=unit;
																							d2_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d2.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								//flag4_d2=value;
														    								kvaunit_d2=unit;
																							d2_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    						
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d2=value;
														    								pfarb_d2=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    						
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-03"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								if(value == "")
																							{
																								value = "0";
																							}
																							float temp = Float.parseFloat(value);
																							flag1_d3 = temp+"";
																							if(!flag1_d3.equalsIgnoreCase("0.0") )
																							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d3=unit;
														    								//flag1_d3=value;
														    								kwh_d3= value;
														    								kwharb_d3=code;
																							}
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d3.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d3=unit;
														    								//flag1_d3=value;
														    								kwh_d3= value;
														    								kwharb_d3=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d3= value;
														    								kvhunit_d3=unit;
														    								flag2_d3=value;
														    								kvharb_d3=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d3.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d3= value;
														    								kvhunit_d3=unit;
														    								//flag2_d3=value;
														    								kvharb_d3=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}
														    																					    							
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d3=value;
														    								kvaarb_d3=code;
														    								flag4_d3=value;
														    								kvaunit_d3=unit;
																							d3_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d3.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d3=value;
														    								kvaarb_d3=code;
														    								//flag4_d3=value;
														    								kvaunit_d3=unit;
																							d3_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    						
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d3=value;
														    								pfarb_d3=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-04"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								if(value == "")
																							{
																								value = "0";
																							}
																							float temp = Float.parseFloat(value);
																							flag1_d4 = temp+"";
																							if(!flag1_d4.equalsIgnoreCase("0.0") )
																							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d4=unit;
														    								//flag1_d4=value;
														    								kwh_d4= value;
														    								kwharb_d4=code;
																							}
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d4.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d4=unit;
														    								//flag1_d4=value;
														    								kwh_d4= value;
														    								kwharb_d4=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d4= value;
														    								kvhunit_d4=unit;
														    								flag2_d4=value;
														    								kvharb_d4=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d4.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d4= value;
														    								kvhunit_d4=unit;
														    								//flag2_d4=value;
														    								kvharb_d4=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}
														    							
														    						
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d4=value;
														    								kvaarb_d4=code;
														    								flag4_d4=value;
														    								kvaunit_d4=unit;
																							d4_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d4.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d4=value;
														    								kvaarb_d4=code;
														    								//flag4_d4=value;
														    								kvaunit_d4=unit;
																							d4_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    							
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d4=value;
														    								pfarb_d4=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-05"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								if(value == "")
																							{
																								value = "0";
																							}
																							float temp = Float.parseFloat(value);
																							flag1_d5 = temp+"";
																							if(!flag1_d5.equalsIgnoreCase("0.0") )
																							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d5=unit;
														    								//flag1_d5=value;
														    								kwh_d5= value;
														    								kwharb_d5=code;
																							}
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d5.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d5=unit;
														    								//flag1_d5=value;
														    								kwh_d5= value;
														    								kwharb_d5=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d5= value;
														    								kvhunit_d5=unit;
														    								flag2_d5=value;
														    								kvharb_d5=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d5.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d5= value;
														    								kvhunit_d5=unit;
														    								//flag2_d5=value;
														    								kvharb_d5=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}
														    							
														    							
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d5=value;
														    								kvaarb_d5=code;
														    								flag4_d5=value;
														    								kvaunit_d5=unit;
																							d5_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d5.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d5=value;
														    								kvaarb_d5=code;
														    								//flag4_d5=value;
														    								kvaunit_d5=unit;
																							d5_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    							
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d5=value;
														    								pfarb_d5=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-06"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-1-5-1-0"))
														    							{
														    								if(value == "")
																							{
																								value = "0";
																							}
																							float temp = Float.parseFloat(value);
																							flag1_d6 = temp+"";
																							if(!flag1_d6.equalsIgnoreCase("0.0") )
																							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d6=unit;
														    								//flag1_d6=value;
														    								kwh_d6= value;
														    								kwharb_d6=code;
																							}
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-1-5-2-0") && flag1_d6.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kwhValue = value;
														    								kwhunit_d6=unit;
														    								//flag1_d6=value;
														    								kwh_d6= value;
														    								kwharb_d6=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
																						}
														    							
														    							
														    					        if(code.equalsIgnoreCase("P7-3-5-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d6= value;
														    								kvhunit_d6=unit;
														    								flag2_d6=value;
														    								kvharb_d6=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-3-5-2-0") && flag2_d6.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvahValue = value;
														    								kvh_d6= value;
														    								kvhunit_d6=unit;
														    								//flag2_d6=value;
														    								kvharb_d6=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
																						}															    							
														    						
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-5-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d6=value;
														    								kvaarb_d6=code;
														    								flag4_d6=value;
														    								kvaunit_d6=unit;
																							d6_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
																						else if(code.equalsIgnoreCase("P7-4-5-2-0") && flag4_d6.equalsIgnoreCase("0"))
																						{
																							arb=code;
														    								kvaValue = value;
														    								kva_d6=value;
														    								kvaarb_d6=code;
														    								//flag4_d6=value;
														    								kvaunit_d6=unit;
																							d6_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
																						}
														    							
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d6=value;
														    								pfarb_d6=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    				}
														    				else if (manufacturerCode.equalsIgnoreCase("6"))
														    				{
														    					if(d3TagCount.equalsIgnoreCase("D3-01"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || (code.equalsIgnoreCase("P7-1-13-1-0")) )
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit=unit;
														    								flag1=value;
														    								kwh= value;
														    								kwharb=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							else if((code.equalsIgnoreCase("P7-3-13-2-0"))  && flag1.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh= value;
														    								kwhunit=unit;
														    								kwharb=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								kvhunit=unit;
														    								flag2=value;
														    								kwharb=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh= value;
														    								flag3=value;
														    								kvharb=code;
														    								
														    								kvhunit=unit;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								flag4=value;
														    								kvaunit=unit;
														    								d1_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								kvaunit=unit;
																							flag5=value;
																							d1_OccDate = occDate;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4.equalsIgnoreCase("0")&& flag5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva=value;
														    								kvaarb=code;
														    								kvaunit=unit;
																							//flag5=value;
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf=value;
														    								pfarb=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    						
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-02"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || (code.equalsIgnoreCase("P7-1-13-1-0")))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d2=unit;
														    								flag1_d2=value;
														    								kwh_d2= value;
														    								kwharb_d2=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							else if((code.equalsIgnoreCase("P7-3-13-2-0"))  && flag1_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh_d2= value;
														    								kwhunit_d2=unit;
														    								kwharb_d2=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								kvhunit_d2=unit;
														    								flag2_d2=value;
														    								kwharb_d2=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								flag3_d2=value;
														    								kvharb_d2=code;
														    								
														    								kvhunit_d2=unit;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d2.equalsIgnoreCase("0") &&  flag3_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d2= value;
														    								kvharb_d2=code;
														    								kvhunit_d2=unit;
														    								System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								flag4_d2=value;
														    								kvaunit_d2=unit;
														    								d2_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								kvaunit_d2=unit;
																							flag5_d2=value;
																							d2_OccDate = occDate;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d2.equalsIgnoreCase("0")&& flag5_d2.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d2=value;
														    								kvaarb_d2=code;
														    								kvaunit_d2=unit;
																							//flag5_d2=value;
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d2=value;
														    								pfarb_d2=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-03"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || (code.equalsIgnoreCase("P7-1-13-1-0")))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d3=unit;
														    								flag1_d3=value;
														    								kwh_d3= value;
														    								kwharb_d3=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							else if((code.equalsIgnoreCase("P7-3-13-2-0"))  && flag1_d3.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh_d3= value;
														    								kwhunit_d3=unit;
														    								kwharb_d3=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d3= value;
														    								kvhunit_d3=unit;
														    								flag2_d3=value;
														    								kwharb_d3=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d3.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d3= value;
														    								flag3_d3=value;
														    								kvharb_d3=code;
														    								
														    								kvhunit_d3=unit;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d3.equalsIgnoreCase("0") &&  flag3_d3.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d3= value;
														    								kvharb_d3=code;
														    								kvhunit_d3=unit;
														    								System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d3=value;
														    								kvaarb_d3=code;
														    								flag4_d3=value;
														    								kvaunit_d3=unit;
														    								d3_OccDate=occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d3.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d3=value;
														    								kvaarb_d3=code;
														    								kvaunit_d3=unit;
																							flag5_d3=value;
																							d3_OccDate=occDate;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d3.equalsIgnoreCase("0")&& flag5_d3.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d3=value;
														    								kvaarb_d3=code;
														    								kvaunit_d3=unit;
																							//flag5_d3=value;
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d3=value;
														    								pfarb_d3=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-04"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || (code.equalsIgnoreCase("P7-1-13-1-0")))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d4=unit;
														    								flag1_d4=value;
														    								kwh_d4= value;
														    								kwharb_d4=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag1_d4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh_d4= value;
														    								kwhunit_d4=unit;
														    								kwharb_d4=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d4= value;
														    								kvhunit_d4=unit;
														    								flag2_d4=value;
														    								kwharb_d4=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d4= value;
														    								flag3_d4=value;
														    								kvharb_d4=code;
														    								
														    								kvhunit_d4=unit;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d4.equalsIgnoreCase("0") &&  flag3_d4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d4= value;
														    								kvharb_d4=code;
														    								kvhunit_d4=unit;
														    								System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d4=value;
														    								kvaarb_d4=code;
														    								flag4_d4=value;
														    								kvaunit_d4=unit;
														    								d4_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d4=value;
														    								kvaarb_d4=code;
														    								kvaunit_d4=unit;
																							flag5_d4=value;
																							d4_OccDate = occDate;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d4.equalsIgnoreCase("0")&& flag5_d4.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d4=value;
														    								kvaarb_d4=code;
														    								kvaunit_d4=unit;
																							//flag5_d4=value;
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d4=value;
														    								pfarb_d4=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-05"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || (code.equalsIgnoreCase("P7-1-13-1-0")))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d5=unit;
														    								flag1_d5=value;
														    								kwh_d5= value;
														    								kwharb_d5=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag1_d5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh_d5= value;
														    								kwhunit_d5=unit;
														    								kwharb_d5=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d5= value;
														    								kvhunit_d5=unit;
														    								flag2_d5=value;
														    								kwharb_d5=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d5= value;
														    								flag3_d5=value;
														    								kvharb_d5=code;
														    								
														    								kvhunit_d5=unit;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d5.equalsIgnoreCase("0") &&  flag3_d5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d5= value;
														    								kvharb_d5=code;
														    								kvhunit_d5=unit;
														    								System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d5=value;
														    								kvaarb_d5=code;
														    								flag4_d5=value;
														    								kvaunit_d5=unit;
														    								d5_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d5=value;
														    								kvaarb_d5=code;
														    								kvaunit_d5=unit;
																							flag5_d5=value;
																							d5_OccDate = occDate;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d5.equalsIgnoreCase("0")&& flag5_d5.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d5=value;
														    								kvaarb_d5=code;
														    								kvaunit_d5=unit;
																							//flag5_d5=value;
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d5=value;
														    								pfarb_d5=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    					else if(d3TagCount.equalsIgnoreCase("D3-06"))
														    					{
														    						if(subNodeName.equalsIgnoreCase("B3"))
														    						{
														    							//System.out.println("D3-01 B3 values - "+code+" - "+value+" - "+unit);
														    							if((code.equalsIgnoreCase("P7-1-13-2-0")) || (code.equalsIgnoreCase("P7-1-13-1-0")))
														    							{
														    								arb=code;
														    								kwhValue = value;
														    								kwhunit_d6=unit;
														    								flag1_d6=value;
														    								kwh_d6= value;
														    								kwharb_d6=code;
														    								//system.out.println("kwhValue value==================>"+kwhValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag1_d6.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kwh_d6= value;
														    								kwhunit_d6=unit;
														    								kwharb_d6=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    					        if(code.equalsIgnoreCase("P7-3-13-1-0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d6= value;
														    								kvhunit_d6=unit;
														    								flag2_d6=value;
														    								kwharb_d6=code;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							
														    							else if(code.equalsIgnoreCase("P7-3-13-2-0") && flag2_d6.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d6= value;
														    								flag3_d6=value;
														    								kvharb_d6=code;
														    								
														    								kvhunit_d6=unit;
														    								//system.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-3-5-0-0") && flag2_d6.equalsIgnoreCase("0") &&  flag3_d6.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvahValue = value;
														    								kvh_d6= value;
														    								kvharb_d6=code;
														    								kvhunit_d6=unit;
														    								System.out.println("kvahValue value==================>"+kvahValue);
														    							}
														    						 	
														    							
														    						}
														    						if(subNodeName.equalsIgnoreCase("B5"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P7-6-13-1-0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d6=value;
														    								kvaarb_d6=code;
														    								flag4_d6=value;
														    								kvaunit_d6=unit;
														    								d6_OccDate = occDate;
														    								//system.out.println("kva value==================>"+kvaValue);
														    							}
														    							else if(code.equalsIgnoreCase("P7-6-13-2-0")&& flag4_d6.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d6=value;
														    								kvaarb_d6=code;
														    								kvaunit_d6=unit;
																							flag5_d6=value;
														    							}
																						else if(code.equalsIgnoreCase("P7-4-18-0-0")&& flag4_d6.equalsIgnoreCase("0")&& flag5_d6.equalsIgnoreCase("0"))
														    							{
														    								arb=code;
														    								kvaValue = value;
														    								kva_d6=value;
														    								kvaarb_d6=code;
														    								kvaunit_d6=unit;
																							//flag5_d6=value;
														    							}
														    						}
														    						 
														    						if(subNodeName.equalsIgnoreCase("B9"))
														    						{
														    							//System.out.println("D3-01 B5 values - "+code+" - "+value+" - "+unit);
														    							if(code.equalsIgnoreCase("P4-4-4-1-0"))
														    							{
														    								arb=code;
														    								pfValue = value;
														    								
														    								pf_d6=value;
														    								pfarb_d6=code;
														    								//system.out.println("pfValue value==================>"+pfValue);
														    							}
														    						}
														    					}
														    					
														    				}
														    				
														    			}
																 	}
																}
														 }
													}
												}// End of D3
												else if(tempNode12.getNodeName().equalsIgnoreCase("D4"))
												{
													String checkDatePattern = "";
													NodeList subnodeListD4 = tempNode12.getChildNodes();
													for (int countD4 = 0; countD4 < subnodeListD4.getLength(); countD4++) 
													{
														String dayProfileDate = "";
														Node tempNodeD4 = subnodeListD4.item(countD4);
														if (tempNodeD4.getNodeType() == Node.ELEMENT_NODE) 
														 {
															String nodeName = tempNodeD4.getNodeName();
															String nodeValue = tempNodeD4.getTextContent();
															if (tempNodeD4.hasAttributes()) 
															{
																NamedNodeMap nodeMap = tempNodeD4.getAttributes();
																for (int nodeMapIndex = 0; nodeMapIndex < nodeMap.getLength(); nodeMapIndex++) 
																{
																	Node node = nodeMap.item(nodeMapIndex);
																	dayProfileDate = node.getNodeValue();
																	Calendar cal1 = Calendar.getInstance();
																	if(dayProfileDate.length() > 2)
																	{
																		checkDatePattern = dayProfileDate.substring(2);
																		if(checkDatePattern.startsWith("-"))
																		{
																			dayProfileDate = sdf3.format(sdf3.parse(dayProfileDate));
																			cal1.setTime(sdf3.parse(dayProfileDate));
																		}
																		else if(checkDatePattern.startsWith("/"))
																		{
																			dayProfileDate = sdf4.format(sdf4.parse(dayProfileDate));
																			cal1.setTime(sdf4.parse(dayProfileDate));
																		}
																		else
																		{
																			 dayProfileDate = "";
																		}
																	}
																	else
																	{
																		 dayProfileDate = "";
																	}
																	
																	cal1.add(Calendar.MONTH, 1);
																	 String profileDateYearMonth = sdfBillDate.format(cal1.getTime());
																	 if(billMonth.equalsIgnoreCase(profileDateYearMonth))
																		{
																			d4DayProfileCount++;
																			//System.out.println("Day profile : " + dayProfileDate);
																		}
																	 
																}
															}
														 }
													}
												}//End of D4
												
											}
										}
									}
								}
								
							}
						}
					}
					
					// temporary 
					int month=0;
					String DATE_FORMAT = "dd-MM-yyyy H:mm";
				    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
				    
				    String DATE_FORMAT1 = "dd-MMM-yyyy";
				    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat(DATE_FORMAT1);
				    
				  //current Date
					Date currentDate = new Date();
		    		String current_date =  sdf1.format(currentDate);
		    		if(!d3_exist)
					{
		    			System.out.println("D3 tag doesnt exist"+meterno);
		    			String checkTheData = xmlImportService.ChechForBillingParameters(meterno, current_date);
		    			if(checkTheData.equalsIgnoreCase("0"))
		    			{
		    				XmlImport xml = new XmlImport();
		    				xml.setMeterno(meterno);xml.setDatestamp(current_date);
		    				xml.setG2value(g2Value);xml.setG3value(g3Value);
		    				xmlImportService.save(xml);
		    				System.out.println("G2 and G3 values inserted successfully");
		    			}
		    			else
						{
							System.out.println("G2 and G3 values already exist");
						}
					}
		    		
		    		Calendar c1 = Calendar.getInstance();
					String monthyear1="", monthyear2="", monthyear3="", monthyear4="", monthyear5="", monthyear6="";
					String yyyyMM1 = "",yyyyMM2 = "",yyyyMM3 = "",yyyyMM4 = "",yyyyMM5 = "",yyyyMM6 = "";
					if(d3_01_flag && (rdate != ""))
					{
						Date date = null;//(Date)sdf.parse(rdate);
						
						String checkDatePattern = "";
						
						if(rdate.length() > 2)
						{
							checkDatePattern = rdate.substring(2);
							if(checkDatePattern.startsWith("-"))
							{
								date = sdf3.parse(rdate);
								
							}
							else if(checkDatePattern.startsWith("/"))
							{
								date = sdf4.parse(rdate);
								
							}
							else
							{
								 date = null;
							}
						}
						else
						{
							date = null;
						}
						yyyyMM1 = sdfBillDate.format(date); 
						//system.out.println("rDate yyyyMM : "+yyyyMM1);
						if(dataMonth.equalsIgnoreCase(yyyyMM1))
						{
							dateCount1 = d4DayProfileCount + "";
						}
						String res = xmlImportService.ChechRdateData(meterno, date);
						if(res.equalsIgnoreCase("0"))
						{
							XmlImport xml = new XmlImport();
							xml.setMeterno(meterno);xml.setRdate(date);
							if(kwh.equalsIgnoreCase("") || kwh.equalsIgnoreCase(null))
								kwh = "0";
							xml.setKwh(Double.parseDouble(kwh));xml.setKwhunit(kwhunit);
							if(kvh.equalsIgnoreCase("") || kvh.equalsIgnoreCase(null))
								kvh = "0";
							xml.setKvh(Double.parseDouble(kvh));xml.setKvhunit(kvhunit);
							if(kva.equalsIgnoreCase("") || kva.equalsIgnoreCase(null))
								kva = "0";
							xml.setKva(Double.parseDouble(kva));xml.setKvaunit(kvaunit);xml.setKwharb(kwharb);
							if(pf.equalsIgnoreCase("") || pf.equalsIgnoreCase(null))
								pf = "0";
							
							xml.setPf(Double.parseDouble(pf));xml.setKvaarb(kvaarb);xml.setKvharb(kvharb);
							xml.setPfarb(pfarb);xml.setMonth(Integer.parseInt(yyyyMM1));xml.setDatestamp(sdf3.format(date));
							xml.setLoadSurveyCount(Long.parseLong(dateCount1));xml.setKvaOccDate(d1_OccDate);
							xml.setG2value(g2Value);xml.setG3value(g3Value);
							xmlImportService.save(xml);
						}
						
					}
					//Date for d3-02
					if(d3_02_flag && (rdate_d2 != ""))
					{
						Date date2 = null;//(Date)sdf.parse(rdate_d2);
						String checkDatePattern = "";
						
						if(rdate_d2.length() > 2)
						{
							checkDatePattern = rdate_d2.substring(2);
							if(checkDatePattern.startsWith("-"))
							{
								date2 = sdf3.parse(rdate_d2);
								
							}
							else if(checkDatePattern.startsWith("/"))
							{
								date2 = sdf4.parse(rdate_d2);
								
							}
							else
							{
								date2 = null;
							}
						}
						else
						{
							date2 = null;
						}
						
						yyyyMM2 = sdfBillDate.format(date2);
						//system.out.println("rDate2 yyyyMM : "+yyyyMM2);
						if(dataMonth.equalsIgnoreCase(yyyyMM2))
						{
							dateCount2 = d4DayProfileCount + "";
						}
						String res = xmlImportService.ChechRdateData(meterno, date2);
						if(res.equalsIgnoreCase("0"))
						{
							XmlImport xml = new XmlImport();
							xml.setMeterno(meterno);xml.setRdate(date2);
							if(kwh_d2.equalsIgnoreCase("") || kwh_d2.equalsIgnoreCase(null))
								kwh_d2 = "0";
							xml.setKwh(Double.parseDouble(kwh_d2));xml.setKwhunit(kwhunit_d2);
							if(kvh_d2.equalsIgnoreCase("") || kvh_d2.equalsIgnoreCase(null))
								kvh_d2 = "0";
							xml.setKvh(Double.parseDouble(kvh_d2));xml.setKvhunit(kvhunit_d2);
							if(kva_d2.equalsIgnoreCase("") || kva_d2.equalsIgnoreCase(null))
								kva_d2 = "0";
							xml.setKva(Double.parseDouble(kva_d2));xml.setKvaunit(kvaunit_d2);xml.setKwharb(kwharb_d2);
							if(pf_d2.equalsIgnoreCase("") || pf_d2.equalsIgnoreCase(null))
								pf_d2 = "0";
							xml.setPf(Double.parseDouble(pf_d2));xml.setKvaarb(kvaarb_d2);xml.setKvharb(kvharb_d2);
							xml.setPfarb(pfarb_d2);xml.setMonth(Integer.parseInt(yyyyMM2));xml.setDatestamp(sdf3.format(date2));
							xml.setLoadSurveyCount(Long.parseLong(dateCount2));xml.setKvaOccDate(d2_OccDate);
							xml.setG2value(g2Value);xml.setG3value(g3Value);
							xmlImportService.save(xml);
							
						}
					}
					//Date for d3-03
					if(d3_03_flag && (rdate_d3 != ""))
					{
						Date date3 = null;//(Date)sdf.parse(rdate_d3);
						String checkDatePattern = "";
						if(rdate_d3.length() > 2)
						{
							checkDatePattern = rdate_d3.substring(2);
							if(checkDatePattern.startsWith("-"))
							{
								date3 = sdf3.parse(rdate_d3);
								
							}
							else if(checkDatePattern.startsWith("/"))
							{
								date3 = sdf4.parse(rdate_d3);
								
							}
							else
							{
								date3 = null;
							}
						}
						else
						{
							date3 = null;
						}
						yyyyMM3 = sdfBillDate.format(date3);
						//system.out.println("rDate3 yyyyMM : "+yyyyMM3);
						if(dataMonth.equalsIgnoreCase(yyyyMM3))
						{
								dateCount3 = d4DayProfileCount + "";
						}
						String res = xmlImportService.ChechRdateData(meterno, date3);
						if(res.equalsIgnoreCase("0"))
						{
							XmlImport xml = new XmlImport();
							xml.setMeterno(meterno);xml.setRdate(date3);
							if(kwh_d3.equalsIgnoreCase("") || kwh_d3.equalsIgnoreCase(null))
								kwh_d3 = "0";
							xml.setKwh(Double.parseDouble(kwh_d3));xml.setKwhunit(kwhunit_d3);
							if(kvh_d3.equalsIgnoreCase("") || kvh_d3.equalsIgnoreCase(null))
								kvh_d3 = "0";
							xml.setKvh(Double.parseDouble(kvh_d3));xml.setKvhunit(kvhunit_d3);
							if(kva_d3.equalsIgnoreCase("") || kva_d3.equalsIgnoreCase(null))
								kva_d3 = "0";
							xml.setKva(Double.parseDouble(kva_d3));xml.setKvaunit(kvaunit_d3);xml.setKwharb(kwharb_d3);
							if(pf_d3.equalsIgnoreCase("") || pf_d3.equalsIgnoreCase(null))
								pf_d3 = "0";
							xml.setPf(Double.parseDouble(pf_d3));xml.setKvaarb(kvaarb_d3);xml.setKvharb(kvharb_d3);
							xml.setPfarb(pfarb_d3);xml.setMonth(Integer.parseInt(yyyyMM3));xml.setDatestamp(sdf3.format(date3));
							xml.setLoadSurveyCount(Long.parseLong(dateCount3));xml.setKvaOccDate(d3_OccDate);
							xml.setG2value(g2Value);xml.setG3value(g3Value);
							xmlImportService.save(xml);
							
						}
					}
					//Date for d3-04
					if(d3_04_flag && (rdate_d4 != ""))
					{
						Date date4 = null;//Date)sdf.parse(rdate_d4);
						String checkDatePattern = "";
						if(rdate_d4.length() > 2)
						{
							checkDatePattern = rdate_d4.substring(2);
							if(checkDatePattern.startsWith("-"))
							{
								date4 = sdf3.parse(rdate_d4);
								
							}
							else if(checkDatePattern.startsWith("/"))
							{
								date4 = sdf4.parse(rdate_d4);
								
							}
							else
							{
								date4 = null;
							}
						}
						else
						{
							date4 = null;
						}
						yyyyMM4 = sdfBillDate.format(date4);
						//system.out.println("rDate4 yyyyMM : "+yyyyMM4);
						if(dataMonth.equalsIgnoreCase(yyyyMM4))
						{
							dateCount4 = d4DayProfileCount + "";
						}
						String res = xmlImportService.ChechRdateData(meterno, date4);
						if(res.equalsIgnoreCase("0"))
						{
							XmlImport xml = new XmlImport();
							xml.setMeterno(meterno);xml.setRdate(date4);
							if(kwh_d4.equalsIgnoreCase("") || kwh_d4.equalsIgnoreCase(null))
								kwh_d4 = "0";
							xml.setKwh(Double.parseDouble(kwh_d4));xml.setKwhunit(kwhunit_d4);
							if(kvh_d4.equalsIgnoreCase("") || kvh_d4.equalsIgnoreCase(null))
								kvh_d4 = "0";
							xml.setKvh(Double.parseDouble(kvh_d4));xml.setKvhunit(kvhunit_d4);
							if(kva_d4.equalsIgnoreCase("") || kva_d4.equalsIgnoreCase(null))
								kva_d4 = "0";
							xml.setKva(Double.parseDouble(kva_d4));xml.setKvaunit(kvaunit_d4);xml.setKwharb(kwharb_d4);
							if(pf_d4.equalsIgnoreCase("") || pf_d4.equalsIgnoreCase(null))
								pf_d4 = "0";
							xml.setPf(Double.parseDouble(pf_d4));xml.setKvaarb(kvaarb_d4);xml.setKvharb(kvharb_d4);
							xml.setPfarb(pfarb_d4);xml.setMonth(Integer.parseInt(yyyyMM4));xml.setDatestamp(sdf3.format(date4));
							xml.setLoadSurveyCount(Long.parseLong(dateCount4));xml.setKvaOccDate(d4_OccDate);
							xml.setG2value(g2Value);xml.setG3value(g3Value);
							xmlImportService.save(xml);
							
						}
					}
					//Date for d3-05
					if(d3_05_flag && (rdate_d5 != ""))
					{
						Date date5 = null;//(Date)sdf.parse(rdate_d5);
						String checkDatePattern = "";
						if(rdate_d5.length() > 2)
						{
							checkDatePattern = rdate_d5.substring(2);
							if(checkDatePattern.startsWith("-"))
							{
								date5 = sdf3.parse(rdate_d5);
								
							}
							else if(checkDatePattern.startsWith("/"))
							{
								date5 = sdf4.parse(rdate_d5);
								
							}
							else
							{
								date5 = null;
							}
						}
						else
						{
							date5 = null;
						}
						yyyyMM5 = sdfBillDate.format(date5);
						//system.out.println("rDate5 yyyyMM : "+yyyyMM5);
						if(dataMonth.equalsIgnoreCase(yyyyMM5))
						{
							dateCount5 = d4DayProfileCount + "";
						}
						
						String res = xmlImportService.ChechRdateData(meterno, date5);
						if(res.equalsIgnoreCase("0"))
						{
							XmlImport xml = new XmlImport();
							xml.setMeterno(meterno);xml.setRdate(date5);
							if(kwh_d5.equalsIgnoreCase("") || kwh_d5.equalsIgnoreCase(null))
								kwh_d5 = "0";
							xml.setKwh(Double.parseDouble(kwh_d5));xml.setKwhunit(kwhunit_d5);
							if(kvh_d5.equalsIgnoreCase("") || kvh_d5.equalsIgnoreCase(null))
								kvh_d5 = "0";
							xml.setKvh(Double.parseDouble(kvh_d5));xml.setKvhunit(kvhunit_d5);
							if(kva_d5.equalsIgnoreCase("") || kva_d5.equalsIgnoreCase(null))
								kva_d5 = "0";
							xml.setKva(Double.parseDouble(kva_d5));xml.setKvaunit(kvaunit_d5);xml.setKwharb(kwharb_d5);
							if(pf_d5.equalsIgnoreCase("") || pf_d5.equalsIgnoreCase(null))
								pf_d5 = "0";
							xml.setPf(Double.parseDouble(pf_d5));xml.setKvaarb(kvaarb_d5);xml.setKvharb(kvharb_d5);
							xml.setPfarb(pfarb_d5);xml.setMonth(Integer.parseInt(yyyyMM5));xml.setDatestamp(sdf3.format(date5));
							xml.setLoadSurveyCount(Long.parseLong(dateCount5));xml.setKvaOccDate(d5_OccDate);
							xml.setG2value(g2Value);xml.setG3value(g3Value);
							xmlImportService.save(xml);
							
						}
					}
					//Date for d3-06
					if(d3_06_flag && (rdate_d6 != ""))
					{
						Date date6 = null;//(Date)sdf.parse(rdate_d6);
						String checkDatePattern = "";
						if(rdate_d6.length() > 2)
						{
							checkDatePattern = rdate_d6.substring(2);
							if(checkDatePattern.startsWith("-"))
							{
								date6 = sdf3.parse(rdate_d6);
								
							}
							else if(checkDatePattern.startsWith("/"))
							{
								date6 = sdf4.parse(rdate_d6);
								
							}
							else
							{
								date6 = null;
							}
						}
						else
						{
							date6 = null;
						}
						yyyyMM6 = sdfBillDate.format(date6);
						//system.out.println("rDate6 yyyyMM : "+yyyyMM6);
						if(dataMonth.equalsIgnoreCase(yyyyMM6))
						{
							dateCount6 = d4DayProfileCount + "";
						}
						
						String res = xmlImportService.ChechRdateData(meterno, date6);
						if(res.equalsIgnoreCase("0"))
						{
							XmlImport xml = new XmlImport();
							xml.setMeterno(meterno);xml.setRdate(date6);
							if(kwh_d6.equalsIgnoreCase("") || kwh_d6.equalsIgnoreCase(null))
								kwh_d6 = "0";
							xml.setKwh(Double.parseDouble(kwh_d6));xml.setKwhunit(kwhunit_d6);
							if(kvh_d6.equalsIgnoreCase("") || kvh_d6.equalsIgnoreCase(null))
								kvh_d6 = "0";
							xml.setKvh(Double.parseDouble(kvh_d6));xml.setKvhunit(kvhunit_d6);
							if(kva_d6.equalsIgnoreCase("") || kva_d6.equalsIgnoreCase(null))
								kva_d6 = "0";
							xml.setKva(Double.parseDouble(kva_d6));xml.setKvaunit(kvaunit_d6);xml.setKwharb(kwharb_d6);
							if(pf_d6.equalsIgnoreCase("") || pf_d6.equalsIgnoreCase(null))
								pf_d6 = "0";
							xml.setPf(Double.parseDouble(pf_d6));xml.setKvaarb(kvaarb_d6);xml.setKvharb(kvharb_d6);
							xml.setPfarb(pfarb_d6);xml.setMonth(Integer.parseInt(yyyyMM6));xml.setDatestamp(sdf3.format(date6));
							xml.setLoadSurveyCount(Long.parseLong(dateCount6));xml.setKvaOccDate(d6_OccDate);
							xml.setG2value(g2Value);xml.setG3value(g3Value);
							xmlImportService.save(xml);
							
						}
					}
					//system.out.println("dateCount : "+dateCount1+" - "+dateCount2+" - "+dateCount3+" - "+dateCount4+" - "+dateCount5+" - "+dateCount6 + " --- "+rdate);
		    		
				}
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}*/
	
	@Override
	public List<User> findAllUser() 
	{
	
		return postgresMdas.createNamedQuery("User.findAllUser").getResultList();
	}

	@Override
	public Boolean findDuplicate(User user, ModelMap models) 
	{
	
		boolean flag=false;
		String usermail=user.getUsername();
		if(usermail!= null)
		{
		   List l= postgresMdas.createNamedQuery("User.findDuplicate").setParameter("username",usermail).getResultList();
		   if(l.size()>0)
		      return flag=true;
		}
		
		return flag;
	}

	@Override
	public Integer getUserID(String username)
	{
		Integer id;
		try
		{
		id=(Integer)postgresMdas.createNamedQuery("User.findUserID").setParameter("username",username).getSingleResult();
		if(id!=null)
			return id; 
		}
		catch(EntityNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<String[]> getReport()
	{
		List<String[]> list1=null;
		System.out.println("controller");
		try{
				/*String qry="SELECT circle,subdivision, count(*) as total_meters," +
							"count(CASE WHEN dlms='DLMS' then 1 END) as dlms_meters," +
							"count(CASE WHEN modem_sl_no is NOT NULL THEN 1 end) as modem_installted," +
							"count(CASE WHEN to_char(last_communicated_date, 'YYYY-MM-DD')=to_char(CURRENT_DATE, 'YYYY-MM-DD') THEN 1 END) as communicating_today," +
							"count(CASE WHEN dlms='Non-DLMS' then 1 END) as non_dlms_meters," +
							"count(CASE WHEN dlms NOT in ('Non-DLMS','DLMS') then 1 END) as unknown_meter_type" +
								" from meter_data.master_main GROUP BY circle,subdivision ORDER BY circle,subdivision";*/
			
			String qry="SELECT  row_number() over (ORDER BY circle) as slno,circle,subdivision, count(*) as total_meters,\n" +
					"count(CASE WHEN dlms='DLMS' then 1 END) as dlms_meters,\n" +
					"count(CASE WHEN modem_sl_no is NOT NULL THEN 1 end) as modem_installted,\n" +
					"count(CASE WHEN to_char(last_communicated_date, 'YYYY-MM-DD')=to_char(CURRENT_DATE, 'YYYY-MM-DD') THEN 1 END) as communicating_today,\n" +
					"count(CASE WHEN dlms='Non-DLMS' then 1 END) as non_dlms_meters,\n" +
					"count(CASE WHEN dlms NOT in ('Non-DLMS','DLMS') then 1 END) as unknown_meter_type\n" +
					" from meter_data.master_main GROUP BY circle,subdivision ORDER BY circle ASC;";
			
			
		System.err.println(qry);
		list1= getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		System.out.println(list1.size());
		return list1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public List getSubdivList(String officeCode, String officeType) {
		List<User> result=new ArrayList<User>();
		try {
			String qry="";
			
			if (officeType.equalsIgnoreCase("circle")) {
				qry = "SELECT s.office_id,SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f"
						+ " WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND circle_code = '"+ officeCode + "' GROUP BY s.office_id ,subdivision ORDER BY subdivision";
			} 
			else if (officeType.equalsIgnoreCase("division")) {
				qry = "SELECT s.office_id,SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f "
						+ "WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND  division_code = '"+ officeCode + "' GROUP BY s.office_id ,subdivision ORDER BY subdivision";
			} 
			else if (officeType.equalsIgnoreCase("subdivision")) {
				qry = "SELECT s.office_id,SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f "
						+ "WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND  sitecode = '"+ officeCode + "' GROUP BY s.office_id ,subdivision ORDER BY subdivision";
			} 
			else if (officeType.equalsIgnoreCase("discom")){
				qry = "SELECT s.office_id, SUBDIVISION FROM meter_data.amilocation a,meter_data.substation_details s,meter_data.feederdetails f"
					+ " WHERE a.sitecode=s.office_id and  f.officeid=s.office_id AND discom_code = '"+ officeCode+"' AND "
					+ "subdivision is NOT NULL GROUP BY s.office_id ,subdivision ORDER BY subdivision" ;
			}
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public User getDataById(String userid) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("User.getById", User.class).setParameter("id", Integer.parseInt(userid)).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public User getDataByEmailId(String emailId) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("User.getByEmailId", User.class).setParameter("emailId", emailId).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
