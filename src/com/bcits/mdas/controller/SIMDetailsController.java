package com.bcits.mdas.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.SimDetailsEntity;
import com.bcits.mdas.service.SimDetailsService;
import com.crystaldecisions.reports.queryengine.Session;


@Controller
public class SIMDetailsController {
	
	@Autowired
	SimDetailsService simdetailsservice; 
	String msg = "";

@RequestMapping(value="/simdetails",method= {RequestMethod.POST,RequestMethod.GET})
public String simdetails(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
  
	return "simdetails";
	
}

@RequestMapping(value="/getsimdetails",method= {RequestMethod.POST,RequestMethod.GET})
public @ResponseBody List<?> getsimdetails(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
	//System.out.println("entered into getsimdetails....");
	List<?> SimDetails=simdetailsservice.getSimDetails();
	//System.out.println("SimDetails..."+SimDetails);
	return SimDetails;
}

@RequestMapping(value="/addSimDetails",method= {RequestMethod.POST,RequestMethod.GET})
public @ResponseBody List<?> addSimDetails(ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession session){
	List<?> SimList=new ArrayList<>();
	long currtime = System.currentTimeMillis();
    String simsrno=request.getParameter("simsrno");
    String mdnum=request.getParameter("mdnum");
    String simip=request.getParameter("simip");
    String nsprov=request.getParameter("nsprov");
    String simsta=request.getParameter("simsta");
    String barcode=request.getParameter("barcode");
    String userName=(String) session.getAttribute("username");
    
    SimDetailsEntity sde=new SimDetailsEntity();
    sde.setSimsrno(simsrno);
    sde.setMdnumber(mdnum);
    sde.setSimstaticip(simip);
    sde.setNsprovider(nsprov);
    sde.setSimstatus(simsta);
    sde.setBarcode(barcode);
    sde.setEntryby(userName);
    sde.setEntrydate(new Timestamp(currtime));
    //sde.setUpdateby(userName);
    //sde.setUpdatedate(new Timestamp(new Date().getTime()));
    try {
    	simdetailsservice.save(sde);
    	SimList=simdetailsservice.addSimDetails();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return SimList;
}

@RequestMapping(value="/editSimDetails/{id}",method= {RequestMethod.POST,RequestMethod.GET})
public @ResponseBody List<?> editSimDetails(@PathVariable String id,ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession session){
	//System.out.println("entered into editsimdetails....");
	List<?> editSimList=simdetailsservice.getEditSimDetails(Integer.parseInt(id));
	//System.out.println("editSimList..."+editSimList);
	return editSimList;
}

@RequestMapping(value="/modifySimDetails",method= {RequestMethod.POST,RequestMethod.GET})
public @ResponseBody Object modifySimDetails(ModelMap model,HttpServletRequest request,HttpServletResponse response,HttpSession session){
	String userName = (String) session.getAttribute("username");
	long currtime = System.currentTimeMillis();
    String simid=request.getParameter("simid");
    String nsprovider=request.getParameter("nsproviderr");
    String mdnumber=request.getParameter("mdnumberr");
    String simstaip=request.getParameter("simstaticipp");
    String simstatus=request.getParameter("simstatuss");
    
    int modifySimList=simdetailsservice.getModifySimDetails(simid, mdnumber, simstaip, nsprovider, simstatus, userName);
    msg="SIM DETAILS UPDATED SUCCESFULLY";
	return modifySimList;
}

@RequestMapping(value="/checkSrnoAvail/{srno}",method= {RequestMethod.POST,RequestMethod.GET})
public @ResponseBody String checkSrnoAvail(@PathVariable String srno,ModelMap model,HttpServletRequest request,HttpServletResponse response){
	String qry=null;
	//System.out.println("entered into check srno avail in controller..");
	qry="select * from meter_data.sim_details where sim_sno='"+srno+"'";
	//System.out.println("qry.."+qry);
	List resultList=null;
	try {
		resultList=simdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
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

@RequestMapping(value="/checkMdnAvail/{mdno}",method= {RequestMethod.POST,RequestMethod.GET})
public @ResponseBody String checkMdnAvail(@PathVariable String mdno,ModelMap model,HttpServletRequest request,HttpServletResponse response){
	String qry=null;
	System.out.println("entered into check mbno avail in controller..");
	qry="select * from meter_data.sim_details where mob_dir_number='"+mdno+"'";
	System.out.println("qry.."+qry);
	List resultList=null;
	try {
		resultList=simdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
	  if(resultList.size()>=1) {
	       	return "Num Exist";
	      }
	 else {
	      	return "Num not Exist ";
	      } 
}

@RequestMapping(value="/checkStaticIpAvail/{simip}",method= {RequestMethod.POST,RequestMethod.GET})
public @ResponseBody String checkStaticIpAvail(@PathVariable String simip,ModelMap model,HttpServletRequest request,HttpServletResponse response){
	String qry=null;
	System.out.println("entered into check mbno avail in controller..");
	qry="select * from meter_data.sim_details where sim_static_ip='"+simip+"'";
	System.out.println("qry.."+qry);
	List resultList=null;
	try {
		resultList=simdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
	  if(resultList.size()>=1) {
	       	return "Ip Exist";
	      }
	 else {
	      	return "Ip not Exist ";
	      } 
}

@RequestMapping(value = "/getMeterByFdrcat/{mtrno}", method = { RequestMethod.GET,
		RequestMethod.POST })
public @ResponseBody Object getMeterByFdrcat(@PathVariable String mtrno, @RequestParam String fdate, @RequestParam String tdate) {
	List<?> meterData=simdetailsservice.getMeterData(mtrno, fdate, tdate);
   return meterData;
}

@RequestMapping(value = "/getMeterFdrwiseData",method= {RequestMethod.GET,RequestMethod.POST})
public @ResponseBody List<?> getMeterFdrwiseData(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
	String mtrno=request.getParameter("meterno");
	List<?> fdrcategory=simdetailsservice.getConsumerData(mtrno);
	String fdrcat=fdrcategory.get(0).toString();
	model.put("fdrcategory",fdrcat);
    String qry="";
	if(fdrcat.equals("HT")||fdrcat.equals("LT")) {
		 qry="select distinct ma.subdivision,ma.fdrcategory as Consumercategory,cm.name,cm.accno,cm.kno,ma.fdrcategory from meter_data.master_main ma,meter_data.consumermaster cm where mtrno='"+mtrno+"' AND ma.mtrno=cm.meterno";
		 List<?> list = simdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		 return list;
	}else if(fdrcat.equals("FEEDER METER")||fdrcat.equals("BORDER METER")) {
		qry="select ma.subdivision,mf.feedername,mf.crossfdr,mf.tp_fdr_id,ma.fdrcategory from meter_data.master_main ma,meter_data.feederdetails mf where mtrno='"+mtrno+"' and ma.mtrno=mf.meterno";
		 List<?> list = simdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	    return list;
	}else if(fdrcat.equals("DT")) {
		qry="select distinct ma.subdivision,dd.dtname,dd.dtcapacity,dd.crossdt,dd.dttpid,ma.fdrcategory from meter_data.master_main ma,meter_data.dtdetails dd where mtrno='"+mtrno+"' and ma.mtrno=dd.meterno";
		 List<?> list = simdetailsservice.getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	    return list;
	}
	else {
		return null;
	}
	
}

}