
package com.bcits.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.resource.Finder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.entity.ConsumerMasterEntity;
import com.bcits.entity.ConsumerMasterHistoryEntity;
import com.bcits.entity.Master;
import com.bcits.entity.MeterInventoryEntity;
import com.bcits.entity.MeterLifeCycleEntity;
import com.bcits.entity.MeterMaster;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.mdas.service.MeterInventoryService;
import com.bcits.service.AssessmentsService;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.ConsumermasterHstService;
import com.bcits.service.MeterLifeCycleService;
import com.bcits.service.MeterMasterService;
import com.bcits.utility.MDMLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class ConsumerMaster {

	@Autowired
	private ConsumerMasterService consumerMasterService;

	@Autowired
	private MeterMasterService meterMasterService;

	@Autowired
	private AssessmentsService assessmentsService;

	@Autowired
	private MeterLifeCycleService meterLifeCycleService;

	@Autowired
	private MasterMainService masterMainService;

	@Autowired
	private MeterInventoryService meterInventoryService;

	@Autowired
	private FeederDetailsService feederDetailsService;

	@Autowired
	private ConsumermasterHstService consumermasterHstService;

	String result = "";

	// circleByZone
	@RequestMapping(value = "/getCircleByZone", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getCirData(HttpServletRequest request, ModelMap model, HttpSession session) {
		
		
		// MDMLogger.logger.info("In get circle data for accno " );
		/*String officeName = (String) session.getAttribute("officeName");
		 if(officeName.equalsIgnoreCase("region")) {
			 String zone = (String) session.getAttribute("newRegionName");
			consumerMasterService.getCircleByZone(zone);
		 }*/
		
		String zone = request.getParameter("zone");
		// System.out.println("zone-->"+zone);
		List<?> circleList = consumerMasterService.getCircleByZone(zone);
		System.err.println("CIRCLe-----"+circleList);
		//System.out.println(circle);
		return circleList;

	}
	
	@RequestMapping(value = "/getCircleByZoneForFdr", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getCircleByZoneForFdr(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get circle data for accno " );
		String zone = request.getParameter("zoneForFdr");
		// System.out.println("zone-->"+zone);
		List<Master> circle = consumerMasterService.getCircleByZone(zone);
		//System.err.println("CIRCLe-----"+circle);
		//System.out.println(circle);
		return circle;

	}
	
	@RequestMapping(value = "/getCircleByZoneForFdrbyReg", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getCircleByZoneForFdrbyReg(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get circle data for accno " );
		String zone = request.getParameter("zoneForFdrbyReg");
		 System.out.println("zone-->"+zone);
		List<Master> circleList = consumerMasterService.getCircleByZonebyReg(zone);
		//System.err.println("CIRCLe-----"+circle);
		//System.out.println(circle);
		return circleList;

	}
	
	
	@RequestMapping(value = "/getCircleByZoneForFdrntse", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getCircleByZoneForFdrntse(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get circle data for accno " );
		String zone = request.getParameter("zoneForntse");
		// System.out.println("zone-->"+zone);
		List<Master> circle = consumerMasterService.getCircleByZone(zone);
		//System.err.println("CIRCLe-----"+circle);
		//System.out.println(circle);
		return circle;

	}

	// division
	@RequestMapping(value = "/getDivByCircle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getDivByCircle(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get Division data for accno " );
		String circle = request.getParameter("circle");
		String zone = request.getParameter("zone");
		// System.out.println("circle-->"+circle);
		List<Master> division = consumerMasterService.getDivByCircle(zone, circle);
		return division;
	}

	// subdivison
	@RequestMapping(value = "/getSubdivByDiv", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getSubdivByDiv(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get Division data for accno " );
		String division = request.getParameter("division");
		String circle = request.getParameter("circle");
		String zone = request.getParameter("zone");
		// System.out.println("division-->"+division);
		List<Master> Subdivision = consumerMasterService.getSubdivByDivision(zone, circle, division);
		return Subdivision;
	}
	
	//town based on circle and region
	@RequestMapping(value = "/getTownsBaseOnCircle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getTownbyCircle(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get Division data for accno " );
		String circle = request.getParameter("circle");
		String zone = request.getParameter("zone");
		 System.out.println("parameters-->"+zone+circle);
		List<Master> townlist = consumerMasterService.getTownsBaseOnCircle(zone, circle);
		return townlist;
	}
	

	// to get sitecode and subdivison
	@RequestMapping(value = "/getSubdivandSitecodeByDiv", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getSubdivandSitecodeByDiv(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get Division data for accno " );
		String division = request.getParameter("division");
		String circle = request.getParameter("circle");
		String zone = request.getParameter("zone");
		// System.out.println("division-->"+division);
		List<Master> Subdivision = consumerMasterService.getSubdivandSitecodeByDivision(zone, circle, division);
		return Subdivision;
	}

	// to get sitecode and subdivison
	@RequestMapping(value = "/getTownNameandCodeBySubDiv", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getTownNameandCodeBySubDiv(HttpServletRequest request, ModelMap model) {
		
		
		String siteCode = request.getParameter("sitecode");

		List<?> Town = consumerMasterService.getTownNameandCode(siteCode);
		return Town;
	}
	
	
	// to get town name and town code based on circle drop down
	@RequestMapping(value = "/getTownNameandCodebyCircle", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getTownNameandCodebyCircle(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get circle data for accno " );
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		// System.out.println("zone-->"+zone);
		List<Master> circleList = consumerMasterService.getTownNameandCodebyCircleandzone(zone,circle);
		return circleList;

	}
	
	@RequestMapping(value = "/getTownNameandCodebyCircleForFdr", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getTownNameandCodebyCircleForFdr(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get circle data for accno " );
		String zone = request.getParameter("zoneForFdr");
		String circle = request.getParameter("circleForFdr");
		 System.out.println("zone-->"+zone);
		 System.out.println("circle-->"+circle);

		List<Master> circleList = consumerMasterService.getTownNameandCodebyCircleandzone(zone,circle);
		return circleList;

	}
	
	@RequestMapping(value = "/getTownNameandCodebyCircleForFdrbyReg", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getTownNameandCodebyCircleForFdrbyReg(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get circle data for accno " );
		String zone = request.getParameter("zoneForFdrbyReg");
		String circle = request.getParameter("circleForFdrbyReg");
		 System.out.println("zone-->"+zone);
		 System.out.println("circle-->"+circle);

		List<Master> circleList = consumerMasterService.getTownNameandCodebyCircleandzonebyReg(zone,circle);
		return circleList;

	}
	
	
	@RequestMapping(value = "/getTownNameandCodebyCircleForntse", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getTownNameandCodebyCircleForntse(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get circle data for accno " );
		String zone = request.getParameter("zoneForntse");
		String circle = request.getParameter("circleForntse");
		 System.out.println("zone-->"+zone);
		 System.out.println("circle-->"+circle);

		List<Master> circleList = consumerMasterService.getTownNameandCodebyCircleandzone(zone,circle);
		return circleList;

	}
	
	
	//to get meter on basis of town
	
	@RequestMapping(value = "/showmeteronBasisofTown", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List showMeteronTown(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get circle data for accno " );
		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		String town = request.getParameter("town");
		// System.out.println("zone-->"+zone);
		List<Master> circleList = consumerMasterService.showmeteronBasisofTown(zone,circle,town);
		return circleList;

	}
	
	
	
	
	// to get feeder_tp_id and feedername based on town selection 
		@RequestMapping(value = "/getFeederTpandName", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List getFeederTpandName(HttpServletRequest request, ModelMap model) {
			// MDMLogger.logger.info("In get Division data for accno " );
			String townCode = request.getParameter("townCode");
			
			// System.out.println("division-->"+division);
			List<Master> feederList = consumerMasterService.getFeederTpIdandFeederName(townCode);
			return feederList;
		}

		// to get dttpid and dtrname based on town selection 
		@RequestMapping(value = "/getDTtpcodeAndName", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List getDTtpcodeAndName(HttpServletRequest request, ModelMap model) {
			// MDMLogger.logger.info("In get Division data for accno " );
			String feederCode = request.getParameter("feederCode");
			
			// System.out.println("division-->"+division);
			List<Master> dtList = consumerMasterService.getDTTpIdandDTName(feederCode);
			return dtList;
		}
		
		
	@RequestMapping(value = "/newConnectionAMR", method = { RequestMethod.GET, RequestMethod.POST })
	public String newConnectionAMR(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,
			HttpServletRequest request, HttpServletResponse resp, ModelMap model) {
		// System.out.println("come inside new Consumer Connection");
		List<Master> zone = consumerMasterService.getZone();
		newConnectionMeterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		model.addAttribute("zones", zone);
		int RdngMonth = meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));
		return "newConnection";
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/addNewConnectionAMR", method = { RequestMethod.GET, RequestMethod.POST })
	public String addNewConnection(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,
			HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In Add new connection Data ");
		meterMasterService.addNewConnectionData(newConnectionMeterMaster, request, model);
		List<MeterLifeCycleEntity> list1 = meterLifeCycleService
				.searchDuplicatemeter(newConnectionMeterMaster.getMetrno(), newConnectionMeterMaster.getAccno());
		// System.out.println("lifecycle size-"+list1.size());
		if (list1.size() == 0) {
			// System.err.println("save-----");
			MeterLifeCycleEntity lifeCycleEntity = new MeterLifeCycleEntity();
			lifeCycleEntity.setMeter_no(newConnectionMeterMaster.getMetrno());
			lifeCycleEntity.setConsumer_no(newConnectionMeterMaster.getAccno());
			lifeCycleEntity.setInstalled_date(null);
			lifeCycleEntity.setInitial_reading(newConnectionMeterMaster.getPrkwh());
			lifeCycleEntity.setMeter_status("ACTIVE");
			lifeCycleEntity.setMonth_year(new SimpleDateFormat("yyyyMMM").format(new Date()));
			lifeCycleEntity.setInstalled_date(new Date());
			meterLifeCycleService.save(lifeCycleEntity);

			/*
			 * meterLifeCycleService.customsaveBySchema(lifeCycleEntity, "entityManager");
			 * MasterMainEntity mastermain=new MasterMainEntity();
			 * System.out.println("newConnectionMeterMaster.getAccno()------->"+
			 * newConnectionMeterMaster.getAccno());
			 * mastermain.setAccno(newConnectionMeterMaster.getAccno());
			 * mastermain.setMtrno(newConnectionMeterMaster.getMetrno());
			 * mastermain.setModem_sl_no(newConnectionMeterMaster.getModem_no());
			 * mastermain.setZone(newConnectionMeterMaster.getMaster().getZone());
			 * mastermain.setCircle(newConnectionMeterMaster.getMaster().getCircle());
			 * mastermain.setDivision(newConnectionMeterMaster.getMaster().getDivision());
			 * mastermain.setSubdivision(newConnectionMeterMaster.getMaster().getSubdiv());
			 * //System.out.println("consumer name---->"+newConnectionMeterMaster.
			 * getConsumername());
			 * mastermain.setCustomer_name(newConnectionMeterMaster.getConsumername());
			 * //System.out.println("addressssss---->"+newConnectionMeterMaster.getAddress()
			 * ); mastermain.setCustomer_address(newConnectionMeterMaster.getAddress());
			 * System.out.println(newConnectionMeterMaster.getMaster().getPhoneno2());
			 * if(newConnectionMeterMaster.getMaster().getPhoneno2()!=null ) {
			 * mastermain.setCustomer_mobile(newConnectionMeterMaster.getMaster().
			 * getPhoneno2().toString()); }
			 * if(newConnectionMeterMaster.getMaster().getContractdemand()!=null) {
			 * mastermain.setContractdemand(newConnectionMeterMaster.getMaster().
			 * getContractdemand().toString()); }
			 * 
			 * mastermain.setKworhp(newConnectionMeterMaster.getMaster().getKworhp());
			 * mastermain.setMtrmake(newConnectionMeterMaster.getMtrmake());
			 * //mastermain.setId(1000000); try{
			 * //mastermainservice.getCustomEntityManager("postgresMdas").createNamedQuery(
			 * "").getSingleResult(); masterMainService.customsaveBySchema(mastermain,
			 * "postgresMdas"); } catch (Exception e) { e.printStackTrace(); }
			 */
		}

		int RdngMonth = meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));

		return "newConnection";
	}

	@RequestMapping(value = "/dataModificationAMR", method = { RequestMethod.GET, RequestMethod.POST })
	public String dataModification(@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,
			HttpServletRequest request, HttpServletResponse resp, ModelMap model) {
		// System.out.println("come inside new Consumer Connection");
		List<Master> zone = consumerMasterService.getZone();
		newConnectionMeterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		model.addAttribute("zones", zone);
		int RdngMonth = meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));

		return "dataModification";
	}

	@RequestMapping(value = "/getConnectionDataAMR", method = { RequestMethod.GET, RequestMethod.POST })
	public String getAmrDataModification(
			@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,
			HttpServletRequest request, ModelMap model) {
		// System.out.println("dataModification method");
		consumerMasterService.searchByAMRAccNo(request, newConnectionMeterMaster, model);

		List<Master> zone = consumerMasterService.getZone();
		model.addAttribute("zones", zone);
		int RdngMonth = meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));
		return "dataModification";

	}

	@RequestMapping(value = "/updateAMRConnectionData", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateAMRConnectionData(
			@ModelAttribute("newConnectionMeterMaster") MeterMaster newConnectionMeterMaster,
			HttpServletRequest request, ModelMap model) {
		// System.out.println("In update AMR connection data");
		consumerMasterService.updateAMRConnectionData(newConnectionMeterMaster, request, model);
		int RdngMonth = meterMasterService.getMaxRdgMonthYear(request);
		model.put("mtrmakeList", meterMasterService.getDistinctMake(RdngMonth));
		return "dataModification";
	}

	// Consumer connection
	@RequestMapping(value = "/addNewConsumer", method = { RequestMethod.GET, RequestMethod.POST })
	public String addConsumer(@ModelAttribute("consumerMasterId") ConsumerMasterEntity consumerMasterId,
			HttpServletRequest request, ModelMap model, HttpSession session) {
		// System.out.println("In consumer master");

		String officeType = (String) session.getAttribute("officeType");
		String officeCode = (String) session.getAttribute("officeCode");
		// List<?>
		// circleList=consumerMasterService.getAllcirclesByofficecode(officeCode);
		List<?> circleList = consumerMasterService.getAllLocationData(officeType, officeCode);
		model.put("circleList", circleList);
		List consumerData = consumerMasterService.getALLConsumerData();
		model.put("InstockMeters", meterInventoryService.getALLInstockMeters());
		model.put("consumerDetails", consumerData);
		model.put("result", result);
		result = "";
		return "addNewConsumer";

	}

	// add consumer details
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/addNewConsumerData", method = { RequestMethod.GET, RequestMethod.POST })
	public String getNewConsumerData(@ModelAttribute("consumerMasterId") ConsumerMasterEntity consumerMasterId,
			HttpServletRequest request, ModelMap model, HttpSession session) {
		// System.out.println(consumerMasterId.toString());

		String dtcode = request.getParameter("dtId");
		String fdrcode = request.getParameter("feederId");
		String circle = request.getParameter("circleId");
		String division = request.getParameter("divisionId");
		String subdivision = request.getParameter("sdonameId");
		// System.out.println("c-"+circle+"=="+division+"==="+subdivision);
		System.out.println("f-" + fdrcode + "==" + dtcode);

		HashMap<String, String> hh = new HashMap<>();
		try {
			hh = consumerMasterService.getZoneSitecodeBySubdiv(circle, subdivision);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long currtime = System.currentTimeMillis();
		String officeCode = (String) session.getAttribute("officeCode");
		ConsumerMasterEntity consumer = new ConsumerMasterEntity();
		consumer.setAccno(consumerMasterId.getAccno());
		consumer.setKno(consumerMasterId.getKno());
		consumer.setMeterno(consumerMasterId.getMeterno());
		consumer.setName(consumerMasterId.getName());
		consumer.setAddress1(consumerMasterId.getAddress1());
		consumer.setPhoneno(consumerMasterId.getPhoneno());
		consumer.setEmail(consumerMasterId.getEmail());
		consumer.setCd(consumerMasterId.getCd());
		consumer.setKworhp(consumerMasterId.getKworhp());
		consumer.setSanload(consumerMasterId.getSanload());
		consumer.setMf(consumerMasterId.getMf());
		consumer.setSupplyvoltage(consumerMasterId.getSupplyvoltage());
		consumer.setTadesc(consumerMasterId.getTadesc());
		consumer.setTariffcode(consumerMasterId.getTariffcode());
		consumer.setConsumerstatus(consumerMasterId.getConsumerstatus());
		consumer.setLatitude(consumerMasterId.getLatitude());
		consumer.setLongitude(consumerMasterId.getLongitude());
		consumer.setPrepaid(consumerMasterId.getPrepaid());
		consumer.setTod(consumerMasterId.getTod());
		consumer.setTou(consumerMasterId.getTou());
		consumer.setDtcode(dtcode);
		consumer.setFeedercode(fdrcode);
		consumer.setConload(consumerMasterId.getConload());
		consumer.setSdocode(hh.get("SITECODE"));

		if (!consumerMasterId.getTpdtcode().equalsIgnoreCase("")) {
			consumer.setTpdtcode(consumerMasterId.getTpdtcode());
		}
		if (!consumerMasterId.getTpfeedercode().equalsIgnoreCase("")) {
			consumer.setTpfeedercode(consumerMasterId.getTpfeedercode());
		}
		try {
			if (!consumerMasterId.getBillperiodstartdate().equals("")
					|| !consumerMasterId.getBillperiodstartdate().equals(null)) {
				consumer.setBillperiodstartdate(consumerMasterId.getBillperiodstartdate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		consumer.setEntryby(request.getSession().getAttribute("username") + "");
		// consumer.setEntrydate(new java.sql.Date(currtime));
		consumer.setEntrydate(new Timestamp(System.currentTimeMillis()));

		// System.out.println(consumer.toString());

		consumerMasterService.save(consumer);
		MasterMainEntity master = new MasterMainEntity();
		master.setMtrno(consumerMasterId.getMeterno());
		master.setFdrcategory(consumerMasterId.getTadesc());
		master.setZone(hh.get("ZONE"));
		master.setCircle(circle);
		master.setDivision(division);
		master.setSubdivision(subdivision);
		master.setSdocode(hh.get("SITECODE"));
		master.setAccno(consumerMasterId.getAccno());
		master.setKno(consumerMasterId.getKno());
		master.setMf(consumerMasterId.getMf() + "");
		masterMainService.save(master);
		meterInventoryService.updateMeterNoInstalled(consumerMasterId.getMeterno(),
				request.getSession().getAttribute("username") + "");
		result = "Data Saved Successfully";
		model.addAttribute("result", result);
		model.addAttribute("consumerMasterId", new ConsumerMasterEntity());

		return "redirect:/addNewConsumer";

	}

	// check for meter exists
	@RequestMapping(value = "/checkMtrnoExistInConsuMaster/{meterno}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody String checkMtrnoExistInConsuMaster(@PathVariable String meterno, HttpServletRequest request,
			ModelMap model) {
		String res = "";
		List<MeterInventoryEntity> result = meterInventoryService.meterNoExistOrNot(meterno);
		// System.out.println(result);
		if (result.size() > 0) {
			String mtrStatus = "";
			for (int i = 0; i < result.size(); i++) {
				mtrStatus = result.get(i).getMeter_status();
			}
			if (mtrStatus.equalsIgnoreCase("INSTALLED")) {
				res = "MeterNo " + meterno + " Already Installed";
			}

		} else {
			res = "MeterNo  " + meterno + " NOT IN STOCK";
		}
		return res;
	}

	// check for ACCno exists
	@RequestMapping(value = "/checkAccnoExistInConsuMaster/{accno}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String checkAccnoExistInConsuMaster(@PathVariable String accno, HttpServletRequest request,
			ModelMap model) {
		String res = "";
		List<ConsumerMasterEntity> result = consumerMasterService.checkAccnoExist(accno);
		if (result.size() > 0) {
			res = "AccNo Already Exist ...";
		}
		return res;
	}

	// check kno Exist
	@RequestMapping(value = "/checkFOrKnoExist/{kno}", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String checkFOrKnoExist(@PathVariable String kno, HttpServletRequest request, ModelMap model) {
		String res = "";
		List<ConsumerMasterEntity> result = consumerMasterService.checkKnoExist(kno);
		if (result.size() > 0) {
			res = "KNO Already Exist ...";
		}
		System.out.println(res);
		return res;

	}

	// consumerDataModification
	// Consumer connection
	@RequestMapping(value = "/consumerDataModification", method = { RequestMethod.GET, RequestMethod.POST })
	public String consumerDataModification(@ModelAttribute("consumerMasterId") ConsumerMasterEntity consumerMasterId,
			HttpServletRequest request, ModelMap model) {
		return "consumerModification";
	}

	// getConsumerDatetoModify
	@RequestMapping(value = "/getConsumerDatetoModify", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<ConsumerMasterEntity> getConsumerDatetoModify(HttpServletRequest request,
			ModelMap model) {
		String flag = request.getParameter("flag");
		List<ConsumerMasterEntity> result = new ArrayList<ConsumerMasterEntity>();
		if (flag.equalsIgnoreCase("1")) {
			result = consumerMasterService.checkAccnoExist(request.getParameter("accno"));
		}
		if (flag.equalsIgnoreCase("2")) {
			result = consumerMasterService.checkMtrnoExist(request.getParameter("accno"));
		}
		if (flag.equalsIgnoreCase("3")) {
			result = consumerMasterService.checkKnoExist(request.getParameter("accno"));
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/modifyConsumerData", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyConsumerData(@ModelAttribute("consumerMasterId") ConsumerMasterEntity consumerMasterId,
			HttpServletRequest request, ModelMap model) throws java.text.ParseException {
		System.out.println("In consumer master Data Modification--");

		String dtcode = request.getParameter("dtId");
		String fdrcode = request.getParameter("feederId");
		String circle = request.getParameter("circleId");
		String division = request.getParameter("divisionId");
		String subdivision = request.getParameter("sdonameId");
		String mfdatechng = request.getParameter("mfdatechngId");
		String mtrChangeDate = request.getParameter("mtrdatechngId");
		System.out.println("mfdatechng=:" + mfdatechng);
		System.out.println("mtrdatechng=:" + mtrChangeDate);
		String oldmeterno = "";
		Timestamp mtrChangeTime = null;
		Timestamp mfChangeTime = null;
		if (mtrChangeDate != "") {
			try {
				DateFormat formatter;
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date;
				date = (Date) formatter.parse(mtrChangeDate);
				mtrChangeTime = new Timestamp(date.getTime());
				System.out.println(mtrChangeTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (mfdatechng != "") {
			try {
				DateFormat formatter;
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date;
				date = (Date) formatter.parse(mfdatechng);
				mfChangeTime = new Timestamp(date.getTime());
				System.out.println(mfChangeTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		long currtime = System.currentTimeMillis();
		ConsumerMasterEntity consumer = consumerMasterService.find(consumerMasterId.getId());

		/*
		 * Gson gson=new GsonBuilder().create();
		 * System.out.println("consumer "+gson.toJson(consumer));
		 * 
		 * ConsumerMasterHistoryEntity chst= gson.fromJson(gson.toJson(consumer),
		 * ConsumerMasterHistoryEntity.class);
		 * System.out.println("ConsumerMasterHistoryEntity "+gson.toJson(chst));
		 */
		ConsumerMasterHistoryEntity chst = new ConsumerMasterHistoryEntity();
		System.out.println("mtrChangeTime--" + mtrChangeTime + " mfChangeTime-" + mfChangeTime);
		oldmeterno = consumer.getMeterno();
		chst.setAccno(consumer.getAccno());

		chst.setKno(consumer.getKno());
		chst.setMeterno(consumer.getMeterno());
		chst.setName(consumer.getName());
		chst.setAddress1(consumer.getAddress1());
		chst.setPhoneno(consumer.getPhoneno());
		chst.setEmail(consumer.getEmail());
		chst.setCd(consumer.getCd());
		chst.setKworhp(consumer.getKworhp());
		chst.setSanload(consumer.getSanload());
		chst.setMf(consumer.getMf());
		chst.setSupplyvoltage(consumer.getSupplyvoltage());
		chst.setTadesc(consumer.getTadesc());
		chst.setTariffcode(consumer.getTariffcode());
		chst.setConsumerstatus(consumer.getConsumerstatus());
		chst.setLatitude(consumer.getLatitude());
		chst.setLongitude(consumer.getLongitude());
		chst.setPrepaid(consumer.getPrepaid());
		chst.setTod(consumer.getTod());
		chst.setTou(consumer.getTou());
		chst.setDtcode(dtcode);
		chst.setFeedercode(fdrcode);
		chst.setConload(consumer.getConload());
		chst.setTpdtcode(consumer.getTpdtcode());
		chst.setTpfeedercode(consumer.getTpfeedercode());

		try {
			if (consumer.getBillperiodstartdate() != null) {
				chst.setBillperiodstartdate(consumer.getBillperiodstartdate());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		chst.setEntryby(request.getSession().getAttribute("username") + "");
		chst.setEntry_date(new Timestamp(System.currentTimeMillis()));

		if (mtrChangeTime != null) {
			chst.setMeterchangedate(mtrChangeTime);
			chst.setMeterchangeflag(1);
			consumer.setMeterchangedate(mfChangeTime);
			consumer.setMeterchangeflag(1);
		}
		if (mfChangeTime != null) {
			chst.setMfchangedate(mfChangeTime);
			chst.setMfflag(1);
			consumer.setMfchangedate(mfChangeTime);
			consumer.setMfflag(1);
		}

		consumer.setAccno(consumerMasterId.getAccno());
		consumer.setKno(consumerMasterId.getKno());
		consumer.setMeterno(consumerMasterId.getMeterno());
		consumer.setName(consumerMasterId.getName());
		consumer.setAddress1(consumerMasterId.getAddress1());
		consumer.setPhoneno(consumerMasterId.getPhoneno());
		consumer.setEmail(consumerMasterId.getEmail());
		consumer.setCd(consumerMasterId.getCd());
		consumer.setKworhp(consumerMasterId.getKworhp());
		consumer.setSanload(consumerMasterId.getSanload());
		consumer.setMf(consumerMasterId.getMf());
		consumer.setSupplyvoltage(consumerMasterId.getSupplyvoltage());
		consumer.setTadesc(consumerMasterId.getTadesc());
		consumer.setTariffcode(consumerMasterId.getTariffcode());
		consumer.setConsumerstatus(consumerMasterId.getConsumerstatus());
		consumer.setLatitude(consumerMasterId.getLatitude());
		consumer.setLongitude(consumerMasterId.getLongitude());
		consumer.setPrepaid(consumerMasterId.getPrepaid());
		consumer.setTod(consumerMasterId.getTod());
		consumer.setTou(consumerMasterId.getTou());
		consumer.setDtcode(dtcode);
		consumer.setFeedercode(fdrcode);
		consumer.setConload(consumerMasterId.getConload());
		if (!consumerMasterId.getTpdtcode().equalsIgnoreCase("")) {
			consumer.setTpdtcode(consumerMasterId.getTpdtcode());
		}
		if (!consumerMasterId.getTpfeedercode().equalsIgnoreCase("")) {
			consumer.setTpfeedercode(consumerMasterId.getTpfeedercode());
		}

		// consumer.setBillperiodstartdate(consumerMasterId.getBillperiodstartdate());
		// System.out.println("--->"+consumerMasterId.getBillperiodstartdate());
		try {
			if (!consumerMasterId.getBillperiodstartdate().equals("")
					|| !consumerMasterId.getBillperiodstartdate().equals(null)) {
				consumer.setBillperiodstartdate(consumerMasterId.getBillperiodstartdate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		consumer.setUpdateby(request.getSession().getAttribute("username") + "");
		consumer.setUpdatedate(new Timestamp(System.currentTimeMillis()));
		// System.out.println(consumer.toString());
		try {
			if (!oldmeterno.equalsIgnoreCase(consumerMasterId.getMeterno())) {
				meterInventoryService.updateMeterNoInstalled(consumerMasterId.getMeterno(),
						request.getSession().getAttribute("username") + "");
				// consumerMasterService.updateMasterMainMeterNo(consumerMasterId.getMeterno(),
				// consumerMasterId.getMf(), mtrChangeTime);
				masterMainService.mtrReplaceUpdateNewMtrno(oldmeterno, consumerMasterId.getMeterno(),
						mtrChangeTime + "", consumerMasterId.getMf() + "");
			}

			consumermasterHstService.save(chst);
			consumerMasterService.update(consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = "Data Updated Successfully";
		model.addAttribute("result", result);
		model.addAttribute("consumerMasterId", new ConsumerMasterEntity());
		return "redirect:/addNewConsumer";
	}

	// getAllDataBased On Id
	@RequestMapping(value = "/getConsumerDataById/{operation}", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object retriveMrName(@PathVariable Long operation, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		MDMLogger.logger.info("In ::::::::::::::::::::::::In Edit Consumer Details   ");
		Object obj1 = new Object();
		ConsumerMasterEntity result = new ConsumerMasterEntity();
		try {
			result = consumerMasterService.find(operation);
			/*
			 * if(result!=null){ String meterno=result.getMeterno(); Object[] obj=(Object[])
			 * consumerMasterService.getSubFdrDT(meterno); HashMap<String, String> map=new
			 * HashMap<>(); map.put("circle", obj[0]+""); map.put("division", obj[1]+"");
			 * map.put("subdiv", obj[2]+""); map.put("substation", obj[3]+"--"+obj[4]);
			 * map.put("fdrcode", obj[3]+"--"+obj[4]); map.put("dtcode",
			 * obj[3]+"--"+obj[4]);
			 * 
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	// check for getAllLocationData
	@RequestMapping(value = "/getAllLocationData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getAllLocationData(HttpServletRequest request, ModelMap model) {
		// System.out.println("calling location--");
		String officeCode = request.getParameter("officeCOde");
		String officeType = request.getParameter("officeType");
		List<?> result = consumerMasterService.getAllLocationData(officeType, officeCode);

		return result;
	}

	// get Substation
	@RequestMapping(value = "/getSubStations", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getSubStations(HttpServletRequest request, ModelMap model) {
		String subdivision = request.getParameter("subdivision");
		// System.out.println("subdivision-->"+subdivision);
		List substation = consumerMasterService.getSubstationsBySubdiv(subdivision);
		return substation;
	}

	@RequestMapping(value = "/getFeeders", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getFeeders(HttpServletRequest request, ModelMap model) {
		String ssid = request.getParameter("ssid");
		// System.out.println("ssid-->"+ssid);
		List feeder = consumerMasterService.getFeederBysubstation(ssid);
		return feeder;
	}

	@RequestMapping(value = "/getDtcByFeeders", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getDtcByFeeders(HttpServletRequest request, ModelMap model) {
		String fdrid = request.getParameter("fdrid");
		// System.out.println("fdrid-->"+fdrid);
		List dtc = consumerMasterService.getDtcByFeeders(fdrid);
		return dtc;
	}

	@RequestMapping(value = "/getAlldtLocation", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getAlldtLocation(HttpServletRequest request, ModelMap model) {
		String meterno = request.getParameter("meterno");
		// System.out.println("fdrid-->"+meterno);
		Object result = consumerMasterService.getSubFdrDT(meterno);
		return result;
	}

	// @Scheduled(cron="0 25 15 * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertConsmerReadingData() {
		consumerMasterService.insertConsumerreading();
	}

	// get Towns Based on subdiv name
	@RequestMapping(value = "/getTownsBaseOnSubDiv", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getTownsBaseOnSubDiv(HttpServletRequest request, ModelMap model) {

		String subdivision = request.getParameter("subdivision");
		// System.out.println("subdivision-->"+subdivision);
		List towns = consumerMasterService.getTownsBaseOnSubDiv(subdivision);
		return towns;
	}

	// get Substations Based on Town Code
	@RequestMapping(value = "/getSubStationsByTownCode", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getSubStationsByTownCode(HttpServletRequest request, ModelMap model) {
		String towncode = request.getParameter("towncode");
		// System.out.println("subdivision-->"+subdivision);
		List towns = consumerMasterService.getSubstationsByTownCode(towncode);
		return towns;
	}

	@RequestMapping(value = "/getSSTPCode", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getSSTPCode(HttpServletRequest request, ModelMap model) {
		String ssid = request.getParameter("ssid");
		// System.out.println("subdivision-->"+subdivision);
		String towns = consumerMasterService.getSSTPCode(ssid);
		return towns;
	}

	@RequestMapping(value = "/getDTTPcodeByFdrid", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object getDTTPcodeByFdrid(HttpServletRequest request, ModelMap model) {
		String fdrid = request.getParameter("fdrid");
		// System.out.println("subdivision-->"+subdivision);
		String towns = consumerMasterService.getDTTpParentCode(fdrid);
		return towns;
	}

	@RequestMapping(value = "/getTownsBaseOnSubdivision", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getTownsBaseOnSubdivision(HttpServletRequest request, ModelMap model) {

		String zone = request.getParameter("zone");
		String circle = request.getParameter("circle");
		//String division = request.getParameter("division");
	//	String subdivision = request.getParameter("subdivision");
		// System.out.println("subdivision-->"+subdivision);
		List towns = consumerMasterService.getTownsBaseOnSubdivision(circle,zone);
		return towns;
	}
	
	@RequestMapping(value = "/getAllLocationHiarchary", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List getAllLocationHiarchary(HttpServletRequest request, ModelMap model) {
		// System.out.println("calling location--");
		String officeCode = request.getParameter("officeCode");
		String officeType = request.getParameter("officeType");
		List<?> result = consumerMasterService.getAllLocationHiarchary(officeType, officeCode);

		return result;
	}
	
	@RequestMapping(value = "/getFeederByTown", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getFeederByTown(HttpServletRequest request, ModelMap model) {
		// MDMLogger.logger.info("In get Division data for accno " );
		String townCode = request.getParameter("townCode");
		
		String circle = request.getParameter("circle");
		// System.out.println("division-->"+division);
		List<Master> feederList = consumerMasterService.getFeederByTown(townCode,circle);
		return feederList;
	}
	
	@RequestMapping(value = "/getFeederBySelection", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List getFeederBySelection(HttpServletRequest request, ModelMap model) {
		String townCode = request.getParameter("townCode");
		
		String circle = request.getParameter("circle");
		String zone = request.getParameter("region");
		
		List<?> feederList = consumerMasterService.getFeederBySelection(townCode,circle,zone);
		return feederList;
	}
	
	//CircleByRegionForFeederOutage
		@RequestMapping(value = "/getCircleByRegionForFeederOutage", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody List getCircleByRegionForFeederOutage(HttpServletRequest request, ModelMap model, HttpSession session) {
			String zone = request.getParameter("zone");
			// System.out.println("zone-->"+zone);
			List<?> circleList = consumerMasterService.getCircleByRegionForFeederOutage(zone);
			System.err.println("CIRCLe-----"+circleList);
			//System.out.println(circle);
			return circleList;

		}
	
	

}
