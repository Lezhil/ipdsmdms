package com.bcits.mdas.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.VirtualLocation;
import com.bcits.mdas.service.AmiLocationService;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.service.ManageVirtualLocationService;

@Controller
public class MeterPointManagementController {
	@Autowired
	ManageVirtualLocationService mvls;
	@Autowired
	FeederMasterService feederService;
	@Autowired
	AmiLocationService als;
	@RequestMapping(value="/manageVirtualLoaction",method={RequestMethod.GET})
	public String virtualLocation(HttpServletRequest hsr, ModelMap model){
		List<VirtualLocation> vl= mvls.findall();
		List<FeederMasterEntity> zoneList=feederService.getDistinctAmiZone();
		model.put("VLlist", vl);
		model.put("ZoneList", zoneList);
		return "manageVirtualLocation";
	}
	@RequestMapping(value="/virtualLocationData",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object  virtualLocationData(ModelMap model){
		List<VirtualLocation> vl= mvls.findall();
		model.put("VLlist", vl);
		return vl;
	}
	@RequestMapping(value="/getVirtualLocation/{vlid}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object  getVirtualLocation(@PathVariable String vlid,HttpServletResponse response){
		VirtualLocation vl= new VirtualLocation();
		try
		{
			vl= (VirtualLocation) mvls.getVirtualLocation(vlid);
		}catch (Exception e) {
			e.printStackTrace();
			return vl;
		}
		
		return vl;
	}
	@RequestMapping(value="/VirtualLocationMeters",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object VirtualLocationMeters(@RequestParam String subdivision,
			@RequestParam String locationType,
			@RequestParam String mtrno,
			@RequestParam String acno,
			@RequestParam String dtcode,
			@RequestParam String fdcode ){
		String sc=als.subDivisionCode(subdivision);
		//String zone = request.getParameter("zone");
		Map<String, List<Object[]>> m=new HashMap<String, List<Object[]>>();
		if(locationType.equalsIgnoreCase("cons")){
			m.put("con", mvls.consumerlist(sc,mtrno,acno));
		}
		else if(locationType.equalsIgnoreCase("feeder")){
			m.put("feeder", mvls.feederlist(sc,mtrno,fdcode));
		}
       else if(locationType.equalsIgnoreCase("dt")){
	    m.put("dt", mvls.dtlist(sc,mtrno,dtcode));
	   }
		return m;
	}
	@RequestMapping(value="/VirtualLocationMetersUpdate",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object VirtualLocationMetersUpdate(@RequestParam String subdivision,@RequestParam String locationType, @RequestParam String vlid ){
		String sc=als.subDivisionCode(subdivision);
		Map<String, List<Object[]>> m=new HashMap<String, List<Object[]>>();
		if(locationType.equalsIgnoreCase("cons")){
			m.put("con", mvls.consumerlistupdate(sc, vlid));
		}
		else if(locationType.equalsIgnoreCase("feeder")){
			m.put("feeder", mvls.feederlistupdate(sc, vlid));
		}
else if(locationType.equalsIgnoreCase("dt")){
	m.put("dt", mvls.dtlistupdate(sc, vlid));
		}
		return m;
	}
	@RequestMapping(value="/locationDataSaved",method={RequestMethod.GET,RequestMethod.POST})
	@Transactional
	public @ResponseBody Object locationDataSaved(@RequestParam(value="conlist[]") List<String> conlist,@RequestParam String locflag,@RequestParam String vlname,@RequestParam String subdivision ,HttpSession session, ModelMap model){
		
		String lvid=mvls.sequenceVirtualID();
		
		int i = 0;
		if(locflag.equalsIgnoreCase("cons")){
		for (String string : conlist) {
			String qry="update meter_data.consumermaster set vl_id='"+lvid+"' where kno='"+string+"'";
			 mvls.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
			i++;
		}
		VirtualLocation v=new VirtualLocation();
		v.setVlID(lvid);
		v.setLocationType("Consumer");
		v.setLocationCode("Consumer_"+lvid);
		v.setSubdivision(subdivision);
		v.setVlName(vlname);
		v.setEntryBy(session.getAttribute("username").toString());
		v.setEntryDate(new Timestamp(System.currentTimeMillis()));
		mvls.save(v);
		}
		if(locflag.equalsIgnoreCase("feeder")){
			for (String string : conlist) {
				String qry="update meter_data.feederdetails set vl_id='"+lvid+"' where feedername='"+string+"'";
				 mvls.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
				i++;
			}
			VirtualLocation v=new VirtualLocation();
			v.setVlID(lvid);
			v.setLocationType("Feeder");
			v.setLocationCode("Feeder_"+lvid);
			v.setSubdivision(subdivision);
			v.setVlName(vlname);
			v.setEntryBy(session.getAttribute("username").toString());
			v.setEntryDate(new Timestamp(System.currentTimeMillis()));
			mvls.save(v);
			}
		if(locflag.equalsIgnoreCase("dt")){
			for (String string : conlist) {
				String qry="update meter_data.dtdetails set vl_id='"+lvid+"' where dtname='"+string+"'";
				 mvls.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
				i++;
			}
			VirtualLocation v=new VirtualLocation();
			v.setVlID(lvid);
			v.setLocationType("DT");
			v.setLocationCode("DT_"+lvid);
			v.setSubdivision(subdivision);
			v.setVlName(vlname);
			v.setEntryBy(session.getAttribute("username").toString());
			v.setEntryDate(new Timestamp(System.currentTimeMillis()));
			mvls.save(v);
			}
		return i;
	}
	@RequestMapping(value="/vlfind",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object vlfind(@RequestParam String vlname){
		boolean b=mvls.findVirtualLocation(vlname);
		return b;
	}
	@RequestMapping(value="/vlConsumData",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object vlConsumData(@RequestParam String ldata){
		
		return mvls.vlConsumData(ldata);
		
	}
	@RequestMapping(value="/locationDataUpdate",method={RequestMethod.GET,RequestMethod.POST})
	@Transactional
	public @ResponseBody Object locationDataUpdate(@RequestParam String lvid,@RequestParam(value="conlist[]") List<String> conlist,@RequestParam String locflag,@RequestParam String vlname,@RequestParam String subdivision ,HttpSession session, ModelMap model){
		int i = 0;
		if(locflag.equalsIgnoreCase("cons")){
			String cu="update meter_data.consumermaster set vl_id='' where vl_id='"+lvid+"'";
			 mvls.getCustomEntityManager("postgresMdas").createNativeQuery(cu).executeUpdate();
		for (String string : conlist) {
			String qry="update meter_data.consumermaster set vl_id='"+lvid+"' where kno='"+string+"'";
			 mvls.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
			i++;
		}
		VirtualLocation v = mvls.find(lvid);
		v.setVlName(vlname);
		v.setUpdateBy(session.getAttribute("username").toString());
		v.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		mvls.update(v);
		}
		if(locflag.equalsIgnoreCase("feeder")){
			String fd="update meter_data.feederdetails set vl_id='' where vl_id='"+lvid+"'";
			 mvls.getCustomEntityManager("postgresMdas").createNativeQuery(fd).executeUpdate();
			for (String string : conlist) {
				String qry="update meter_data.feederdetails set vl_id='"+lvid+"' where feedername='"+string+"'";
				 mvls.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
				i++;
			}
			VirtualLocation v = mvls.find(lvid);
			v.setVlName(vlname);
			v.setUpdateBy(session.getAttribute("username").toString());
			v.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			mvls.update(v);
			}
		if(locflag.equalsIgnoreCase("dt")){
			String dt="update meter_data.dtdetails set vl_id='' where vl_id='\"+lvid+\"'";
			 mvls.getCustomEntityManager("postgresMdas").createNativeQuery(dt).executeUpdate();
			for (String string : conlist) {
				String qry="update meter_data.dtdetails set vl_id='"+lvid+"' where dtname='"+string+"'";
				 mvls.getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
				i++;
			}
			VirtualLocation v = mvls.find(lvid);
			v.setVlName(vlname);
			v.setUpdateBy(session.getAttribute("username").toString());
			v.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			mvls.update(v);
			}
		return i;
		
	}
	@RequestMapping(value="/deleteVL",method={RequestMethod.POST})
	public String deleteStaff(@RequestParam("delVirtualLocation")String vl) 
	{	
		String[] s=vl.split("@");
		mvls.delete(s[0]);
		mvls.removeVL(vl);
		return "redirect:/manageVirtualLoaction";
	}

}
