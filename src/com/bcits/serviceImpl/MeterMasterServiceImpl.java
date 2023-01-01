package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.entity.ChangesEntity;
import com.bcits.entity.D2Data;
import com.bcits.entity.Master;
import com.bcits.entity.MeterMaster;
import com.bcits.entity.MeterMasterExtra;
import com.bcits.entity.MeterMasterExtra.CustomKey;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.entity.UploadedFile;
import com.bcits.service.ChangesService;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.MasterService;
import com.bcits.service.MeterMasterExtraService;
import com.bcits.service.MeterMasterService;
import com.bcits.service.MrnameService;
import com.bcits.service.SdoJccService;
import com.bcits.service.sealService;
import com.bcits.utility.CalenderClass;
import com.bcits.utility.MDMLogger;
import com.bcits.utility.Resultupdated;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;


/**
 * @author Manjunath Kotagi
 * 
 *         Meter Master Service Implementation
 * 
 */
@Repository
public class MeterMasterServiceImpl extends GenericServiceImpl<MeterMaster>
		implements MeterMasterService {

	@Autowired
	private CalenderClass calenderClass;

	@Autowired
	private MasterService masterService;

	@Autowired
	private MrnameService mrnameService;

	@Autowired
	private ChangesService changesService;
	
	@Autowired
	sealService sealService;
	@Autowired
	private SdoJccService sdoJccService;

	@Autowired
	MeterMasterExtraService extraService;
	
	@Autowired
	private ConsumerMasterService consumerMasterService;
	
	@PersistenceContext(unitName="POSTGREDataSource")
	protected EntityManager postgresMdasL;
	
	@Autowired
	private MasterMainService masterMainService;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MeterMaster> findAll() {
		return postgresMdas.createNamedQuery("MeterMaster.getData")
				.setParameter("month", calenderClass.getPresentMonth())
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MeterMaster> findmonthData(String parameter) {
		return postgresMdas.createNamedQuery("MeterMaster.getData")
				.setParameter("month", Integer.parseInt(parameter))
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MeterMaster> findmeterandmonthData(String metrno) {
		return postgresMdas
				.createNamedQuery("MeterMaster.getDatabasedonmonthmeternumber")
				.setParameter("metrno", metrno)
				.setParameter("rdngmonth", calenderClass.getPresentMonth())
				.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List getMeterMakeWiseData(int rdngmonth) {

		return postgresMdas
				.createNamedQuery("MeterMaster.getMeterMakeWiseData")
				.setParameter("rdngmonth", rdngmonth).getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public String checkMeterAccNoExistOrNot(
			MeterMaster newConnectionMeterMaster, HttpServletRequest request) {
		Master materData = masterService.find(newConnectionMeterMaster
				.getAccno());// check AccNo present in Master
		int res = 0;
		if (materData != null) {
			res = 1;// for message purpos in ajax call
		}
		List list = postgresMdas.createNamedQuery("MeterMaster.checkExist")
				.setParameter("metrno", newConnectionMeterMaster.getMetrno())
				.getResultList();
		MDMLogger.logger.info("sizess " + list.size());
		if (list.size() > 0) {
			if (res == 1) {
				res = 3;// for message purpos in ajax call
			} else {
				res = 2;// for message purpos in ajax call
			}
		}
		return res + "";
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public String checkMeterExist(MeterMaster newConnectionMeterMaster,
			HttpServletRequest request) {
		List list = postgresMdas
				.createNamedQuery("MeterMaster.checkMeterExistOnCurrentMonth")
				.setParameter("metrno", newConnectionMeterMaster.getMetrno())
				.setParameter("accno", newConnectionMeterMaster.getAccno())
				.setParameter("rdngmonth", newConnectionMeterMaster.getRdngmonth())
				.getResultList();
		String res = "0";
		if (list.size() > 0) {
			res = "1";
		}
		
		System.out.println("res==="+res);
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewConnectionData(MeterMaster newConnectionMeterMaster,
			HttpServletRequest request, ModelMap model) {
		try {
			System.out.println("Accno-->"+	newConnectionMeterMaster.getAccno());
			newConnectionMeterMaster.getMaster().setAccno(newConnectionMeterMaster.getAccno());
			newConnectionMeterMaster.setMetrno(newConnectionMeterMaster.getMetrno());
			newConnectionMeterMaster.getMaster().setKno(newConnectionMeterMaster.getMaster().getKno());
			System.out.println("ddddd-->"+newConnectionMeterMaster.getMaster().getCircle()+"--->"+newConnectionMeterMaster.getMaster().getDivision()+"---->"+newConnectionMeterMaster.getMaster().getSdoname());
			newConnectionMeterMaster.getMaster().setZone(newConnectionMeterMaster.getMaster().getZone());
			newConnectionMeterMaster.getMaster().setCircle(newConnectionMeterMaster.getMaster().getCircle());
			newConnectionMeterMaster.getMaster().setDivision(newConnectionMeterMaster.getMaster().getDivision());
			newConnectionMeterMaster.getMaster().setSubdiv(newConnectionMeterMaster.getMaster().getSdoname());
			newConnectionMeterMaster.setConsumername(newConnectionMeterMaster.getMaster().getName());
			newConnectionMeterMaster.setAddress(newConnectionMeterMaster.getMaster().getAddress1());
			newConnectionMeterMaster.getMaster().setPhoneno2(newConnectionMeterMaster.getMaster().getPhoneno2());
			newConnectionMeterMaster.getMaster().setContractdemand(newConnectionMeterMaster.getMaster().getContractdemand());
			newConnectionMeterMaster.getMaster().setKworhp(newConnectionMeterMaster.getMaster().getKworhp());
			newConnectionMeterMaster.getMaster().setSupplytype(newConnectionMeterMaster.getMaster().getSupplytype());
			newConnectionMeterMaster.getMaster().setSanload(newConnectionMeterMaster.getMaster().getSanload());
			newConnectionMeterMaster.setMf(newConnectionMeterMaster.getMf());
			newConnectionMeterMaster.setCtrd(newConnectionMeterMaster.getCtrd());
			newConnectionMeterMaster.setCtrn(newConnectionMeterMaster.getCtrn());
			newConnectionMeterMaster.getMaster().setTariffcode(newConnectionMeterMaster.getMaster().getTariffcode());
			newConnectionMeterMaster.getMaster().setSupplyvoltage(newConnectionMeterMaster.getMaster().getSupplyvoltage());
			newConnectionMeterMaster.setMtrmake("GENUS POWER INFRASTRUCTURES LTD");
			//newConnectionMeterMaster.setUnitskwh(newConnectionMeterMaster.getUnitskwh());
			//newConnectionMeterMaster.setUnitskvah(newConnectionMeterMaster.getUnitskvah());
			newConnectionMeterMaster.getMaster().setIndustrytype(newConnectionMeterMaster.getMaster().getIndustrytype());
			newConnectionMeterMaster.getMaster().setConndate(new Date());
			//System.out.println(newConnectionMeterMaster.getMaster().getStatus());
			newConnectionMeterMaster.getMaster().setStatus(newConnectionMeterMaster.getMaster().getStatus());
			newConnectionMeterMaster.getMaster().setConsumerstatus("R");
			newConnectionMeterMaster.setCircle(newConnectionMeterMaster.getMaster().getCircle());
			newConnectionMeterMaster.setSubdiv(newConnectionMeterMaster.getMaster().getSdoname());
			newConnectionMeterMaster.setDivision(newConnectionMeterMaster.getMaster().getDivision());
			newConnectionMeterMaster.setkno(newConnectionMeterMaster.getMaster().getKno());
			//newConnectionMeterMaster.setMeterstatus(meterstatus);
			//newConnectionMeterMaster.setId((long) 1000000);
			customsaveBySchema(newConnectionMeterMaster, "postgresMdas");
			
			//adding to master main
			MasterMainEntity mastermain=new MasterMainEntity();
			mastermain.setAccno(newConnectionMeterMaster.getMaster().getAccno());
			mastermain.setMtrno(newConnectionMeterMaster.getMetrno());
			mastermain.setZone(newConnectionMeterMaster.getMaster().getZone());
			mastermain.setCircle(newConnectionMeterMaster.getMaster().getCircle());
			mastermain.setDivision(newConnectionMeterMaster.getMaster().getDivision());
			mastermain.setSubdivision(newConnectionMeterMaster.getMaster().getSubdiv());
			mastermain.setCustomer_name(newConnectionMeterMaster.getMaster().getName());
			mastermain.setCustomer_address(newConnectionMeterMaster.getMaster().getAddress1());
			mastermain.setConsumerstatus(newConnectionMeterMaster.getMaster().getConsumerstatus());
			mastermain.setTariffcode(newConnectionMeterMaster.getMaster().getTariffcode());
			if(newConnectionMeterMaster.getMaster().getPhoneno2()!=null)
			{
				mastermain.setCustomer_mobile(newConnectionMeterMaster.getMaster().getPhoneno2().toString());	
			}
			 if(newConnectionMeterMaster.getMaster().getContractdemand()!=null)
			 {
				 mastermain.setContractdemand(newConnectionMeterMaster.getMaster().getContractdemand().toString());	 
			 }
			 if(newConnectionMeterMaster.getMaster().getSanload()!=null)
			 {
				 mastermain.setSanload(newConnectionMeterMaster.getMaster().getSanload().toString());
			 }
			if(newConnectionMeterMaster.getMaster().getKno()!=null)
			{
				mastermain.setSdocode(newConnectionMeterMaster.getMaster().getKno().substring(0, 7));
			}
			mastermain.setKworhp(newConnectionMeterMaster.getMaster().getKworhp());
			mastermain.setMtrmake("GENUS POWER INFRASTRUCTURES LTD");
			mastermain.setInstallation_date(new Date());
			//mastermain.setId(1000000);
			try{
				//mastermainservice.getCustomEntityManager("postgresMdas").createNamedQuery("").getSingleResult();
				masterMainService.customsaveBySchema(mastermain, "postgresMdas");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
			model.put("result", "Account No Added Successfully");
			
		} catch (Exception e) {
			model.put("result", "Error while adding Account number");
			e.printStackTrace();
		}
		MeterMaster mtrMaster = new MeterMaster();
		List<Master> zone=consumerMasterService.getZone();
		model.addAttribute("zones", zone);
		model.put("circle", getAllCirclesInAmiLocation());
		model.put("division", getAllDivisionsInAmiLocation());
		model.put("sdoname", getAllSubDivisionsInAmiLocation());
		mtrMaster.setRdngmonth(getMaxRdgMonthYear(request));
		model.put("newConnectionMeterMaster", mtrMaster);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateConnectionDataValues(
			MeterMaster newConnectionMeterMaster, HttpServletRequest request,
			ModelMap model) {
		try {
			
			List<MeterMaster> list = postgresMdas.createNamedQuery("MeterMaster.searchByAccNo").setParameter("rdgMonth",newConnectionMeterMaster.getRdngmonth()).setParameter("accNo", newConnectionMeterMaster.getAccno()).getResultList();
			if (list.size() > 0) {
				MeterMaster entity = list.get(0);
				//changesService.InsertTransaction(entity, "OLD", request);// for
																			// old
																			// transaction
																			// (changes
																			// table)
			}
					String mnpValue=(String) model.get("mnpMaster");
			
			int metermasterRes = postgresMdas
					.createNamedQuery("MeterMaster.updateConnectionDetails")
					.setParameter("rdngmonth",
							newConnectionMeterMaster.getRdngmonth())
					.setParameter("accno", newConnectionMeterMaster.getAccno())
					.setParameter("meterNo",
							newConnectionMeterMaster.getMetrno())
					.setParameter("mtrtype",
							newConnectionMeterMaster.getMtrtype())
					.setParameter("mtrMake",
							newConnectionMeterMaster.getMtrmake())
					.setParameter("ctrn", newConnectionMeterMaster.getCtrn())
					.setParameter("ctrd", newConnectionMeterMaster.getCtrd())
					.setParameter("mf", newConnectionMeterMaster.getMf())
					.setParameter("initialRdg",
							newConnectionMeterMaster.getPrkwh())
					.setParameter("pStatus",
							newConnectionMeterMaster.getPrevmeterstatus())
					.setParameter("cStatus",newConnectionMeterMaster.getMaster()
							.getConsumerstatus())
					.setParameter("dlms", newConnectionMeterMaster.getDlms())
							.executeUpdate();
			int meterRes = 0;
			System.out.println("metermasterRes-->"+metermasterRes);
			if (metermasterRes > 0) {
				meterRes = postgresMdas
						.createNamedQuery("Master.updateConnectionDetails")
						.setParameter("accno",
								newConnectionMeterMaster.getAccno())
						.setParameter("name",
								newConnectionMeterMaster.getMaster().getName())
						.setParameter(
								"address",
								newConnectionMeterMaster.getMaster()
										.getAddress1())
						.setParameter(
								"phoneNo",
								newConnectionMeterMaster.getMaster()
										.getPhoneno())
						.setParameter(
								"phoneNo2",
								newConnectionMeterMaster.getMaster()
										.getPhoneno2())
						.setParameter(
								"cd",
								newConnectionMeterMaster.getMaster()
										.getContractdemand())
						.setParameter(
								"remarks",
								newConnectionMeterMaster.getMaster()
										.getRemarks())
						.setParameter(
								"sanLoad",
								newConnectionMeterMaster.getMaster()
										.getSanload())
						.setParameter(
								"industryType",
								newConnectionMeterMaster.getMaster()
										.getIndustrytype())
						.setParameter(
								"kwHp",
								newConnectionMeterMaster.getMaster()
										.getKworhp())
						.setParameter(
								"supplyType",
								newConnectionMeterMaster.getMaster()
										.getSupplytype())
						.setParameter(
								"cStatus",
								newConnectionMeterMaster.getMaster()
										.getConsumerstatus())
						.setParameter(
								"tariff",
								newConnectionMeterMaster.getMaster()
										.getTariffcode())
						.setParameter("taDesc",newConnectionMeterMaster.getMaster().getTadesc())
						.setParameter("supplyvoltage",newConnectionMeterMaster.getMaster().getSupplyvoltage())
						.setParameter("mrname",newConnectionMeterMaster.getMrname())
						.setParameter("tn", newConnectionMeterMaster.getMaster().getTn())
						.setParameter("mnp", mnpValue)
						.setParameter("kno", newConnectionMeterMaster.getMaster().getKno())
						.setParameter("kno", newConnectionMeterMaster.getMaster().getKno())
						.setParameter("sdoname", newConnectionMeterMaster.getMaster().getSdoname())
						.setParameter("division", newConnectionMeterMaster.getMaster().getDivision())
						.setParameter("circle", newConnectionMeterMaster.getMaster().getCircle())
						.executeUpdate();
			}

			if (metermasterRes > 0 && meterRes > 0) {
				changesService.InsertTransaction(newConnectionMeterMaster,
						"NEW", request);// for new transaction (changes table)
				model.put("result", "Data modification done successfully");
			} else {
				model.put("result",
						"Data Modification Not Done");
			}

		} catch (Exception e) {
			model.put("result", "Error while modifying data");
			e.printStackTrace();
		}
		MeterMaster mtrMaster = new MeterMaster();
		mtrMaster.setRdngmonth(getMaxRdgMonthYear(request));
		model.put("newConnectionMeterMaster", mtrMaster);
		model.put("mrNames", mrnameService.findAll());

	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public int getMaxRdgMonthYear(HttpServletRequest request) {
		int monthYear = 0;
		try {
			monthYear = (int) getCustomEntityManager("postgresMdas").createNamedQuery(
					"MeterMaster.getMaxMonthYear").getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return monthYear;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void searchByAccNo(HttpServletRequest request,MeterMaster meterMaster, ModelMap model) 
	{
		List<MeterMaster> list = null;
		String mrname = "";
		System.out.println("--inside searchByAccNo serviceImpl-----");
		try {
			System.out.println("Accno--" +meterMaster.getAccno()+"rdgMonth--" +meterMaster.getRdngmonth());
			list = postgresMdas.createNamedQuery("MeterMaster.searchByAccNo").setParameter("accNo", meterMaster.getAccno()).setParameter("rdgMonth", meterMaster.getRdngmonth()).getResultList();
			System.out.println("list size-->"+list.size());
			/*mrname = (String)postgresMdas.createNamedQuery("Master.FindMrName").setParameter("accno", meterMaster.getAccno()).getSingleResult();
			System.out.println("---------------mrname------------ : "+list.get(0).getMrname());*/
		
			if (list.size() == 0) 
			{
				
				model.put("result", "Data not found for entered account number");
				System.out.println("inside accnoService Impl list==0");
				int rdgMonth = meterMaster.getRdngmonth();
				meterMaster = new MeterMaster();
				meterMaster.setRdngmonth(rdgMonth);
				model.put("mnpValue", "noVal");
				model.put("newConnectionMeterMaster", meterMaster);
			}
			
			if (list.size() > 0) {
				try{
					System.out.println("Sub Division===="+list.get(0).getMaster().getSdoname());
				System.out.println("MNP--"+list.get(0).getMaster().getMnp());
				
				if(list.get(0).getMaster().getMnp()==null)
				{
					model.put("mnpValue", "noVal");
				}
				else
				{
					model.put("mnpValue", list.get(0).getMaster().getMnp());
				}
				mrname = (String)postgresMdas.createNamedQuery("Master.FindMrName").setParameter("accno", meterMaster.getAccno()).getSingleResult();
				System.out.println("---mrname size====> : "+mrname.length()+"  mrname==>"+mrname);
				System.out.println("---------------mrname------------ : "+list.get(0).getMrname());
				if(mrname!=null)
				{
					  list.get(0).setMrname(mrname);
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				model.put("newConnectionMeterMaster", list.get(0));
				System.out.println(" hoi " + list.get(0).getAccno() + "   "+ list.get(0).getMaster());
				System.out.println(list.get(0).getMaster().getCircle());
				/*model.put("mrNames", masterService.getALLMrNameByCIR(list.get(0).getMaster().getCircle());*/
				System.out.println("hello division liast circle===+++"+list.get(0).getMaster().getCircle());
				//model.put("mrNames", mrnameService.findAll());
				model.put("SDONames",sdoJccService.getSubDivisionList(list.get(0).getMaster().getDivision()));
				//model.put("mrNames", masterService.findMr());
				model.put("divisions", sdoJccService.getALLDivisionByCIR(list.get(0).getMaster().getCircle()));
				model.put("MNP",masterService.getALLMNPByCIR(list.get(0).getMaster().getCircle()));
				//model.addAttribute("MNP",masterService.getAllMNP());
				model.put("mrNames", masterService.getALLMrNameByCIR(list.get(0).getMaster().getCircle()));
			}
		} catch (Exception e) 
		{

			e.printStackTrace();
		}
		
		

	}

	/* From here Ved Prakash */

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Object getAccountNumber(String rrdmonth, String metrno,
			HttpServletRequest request, ModelMap model) {
        System.out.println("MeterNo"+metrno);
		Integer rdngmonth = Integer.parseInt(rrdmonth);
		String accno;
		try {
			accno = (String) postgresMdas
					.createNamedQuery("MeterMaster.FindAccountNumber")
					.setParameter("metrno", metrno)
					.setParameter("rdngmonth", rdngmonth).getSingleResult();
			System.out.println("Account No"+accno);
			return accno;
		} catch (javax.persistence.NoResultException nre) {
			return null;
		}

		
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Object getAccountNumber1(String rrdmonth, String oldaccno,
			HttpServletRequest request, ModelMap model) {

		System.out.println("MMMMMMMMMMMMMMM" + rrdmonth + "" + oldaccno);

		Integer rdngmonth = Integer.parseInt(rrdmonth);
		String meter;
		try {
			meter = (String) postgresMdas.createNamedQuery("MeterMaster.FindMeterNumber").setParameter("oldaccno", oldaccno)
					.setParameter("rdngmonth", rdngmonth).getSingleResult();
			System.out.println("RRRRRRRRRRRRRRRRRR" + meter);
			return meter;
			
		} catch (javax.persistence.NoResultException nre) {
			return null;
		}

		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateNewAccount(MeterMaster meterMaster,
			HttpServletRequest request, ModelMap model) {
		String metrno = meterMaster.getMetrno();
		Integer rdngmonth = meterMaster.getRdngmonth();
		String accno = meterMaster.getAccno();
		Integer sdocode=meterMaster.getSdocode();
		System.out.println(metrno + "ppppp" + rdngmonth + "pppppp" + accno);
		int success = 0;

		Integer a = postgresMdas
				.createNamedQuery("MeterMaster.UpdateAccountNumber")
				.setParameter("accno", accno).setParameter("metrno", metrno).setParameter("sdocode", sdocode).executeUpdate();
		if (a > 0) {
			// model.put("msg","Account updated successfully");
			return success = 1;
		}

		return success;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String getMeterMasterData(HttpServletRequest request,
			MeterMaster meterMaster, ModelMap model) {
		Integer rdngmonth = meterMaster.getRdngmonth();
		System.out.println("Accountno" + meterMaster.getAccno() + " MeterNo "
				+ meterMaster.getMetrno() + "Rdngmonth " + rdngmonth);
		List<MeterMaster> list = null;
		if (meterMaster.getAccno() != null) {
			list = postgresMdas.createNamedQuery("MeterMaster.FindAllData2")
					.setParameter("accno", meterMaster.getAccno())
					.setParameter("rdngmonth", rdngmonth).getResultList();
		}
		if (list.size() == 0) {
			model.put("msg", "Data not found for entered values ");
			model.put("getManulReadingData", meterMaster);
			return null;
		}
		if (list.size() > 0) {
			model.put("getManulReadingData", list.get(0));
			model.put("masterdata",masterService.find(list.get(0).getAccno()));
			System.out.println(" hoi " + list.get(0).getAccno() + "   "
					+ list.get(0).getMaster());
			request.setAttribute("sdocode", list.get(0).getSdocode());

		}

		return list.toString();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getPreviousRemark(String accno,int readingmonth)
	{
		List<MeterMaster> list = postgresMdas.createNamedQuery("MeterMaster.FindAllData2").setParameter("accno", accno).setParameter("rdngmonth", readingmonth).getResultList();
		String remark ="";
		if(list.size() > 0)
		{
			 remark = list.get(0).getReadingremark();
		}
		
		
		
		return remark;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String getMeterMasterData2(HttpServletRequest request,MeterMaster meterMaster, ModelMap model) {
		
		Integer rdngmonth = meterMaster.getRdngmonth();
		System.out.println("Old Accountno" + meterMaster.getMaster().getOldaccno() + " MeterNo "+ meterMaster.getMetrno() + "Rdngmonth " + rdngmonth);
		List<MeterMaster> list = null;
		if (meterMaster.getAccno() != null) {
			list = postgresMdas.createNamedQuery("MeterMaster.FindAllData2").setParameter("accno",meterMaster.getMaster().getOldaccno()).setParameter("rdngmonth", rdngmonth).getResultList();
		}
		if (list.size() == 0) {
			model.put("msg", "Data not found for entered values ");
			model.put("meterMaster", meterMaster);
			return null;
		}
		if (list.size() > 0) {
			model.put("getManulReadingData", list.get(0));
			model.put("meterMaster", list.get(0));
			
			model.put("subaccno",list.get(0).getAccno().substring(0,4));
			System.out.println(" hoi " + list.get(0).getAccno() + "   "+ list.get(0).getMaster());
			request.setAttribute("sdocode", list.get(0).getSdocode());
		}
		return list.toString();
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public String getMeterMasterData1(HttpServletRequest request,MeterMaster meterMaster, ModelMap model) {
		Integer rdngmonth = meterMaster.getRdngmonth();
		System.out.println("Accountno" + meterMaster.getAccno() + " MeterNo "+ meterMaster.getMetrno() + "Rdngmonth " + rdngmonth);
		List<MeterMaster> list = null;
		if (meterMaster.getMetrno() != null) {

			list = postgresMdas.createNamedQuery("MeterMaster.FindAllData1").setParameter("metrno", meterMaster.getMetrno().trim()).setParameter("rdngmonth", rdngmonth).getResultList();

		}

		if (list.size() == 0) {
			model.put("msg", "Data not found for entered values ");
			model.put("getManulReadingData", meterMaster);
			model.put("meterMaster", meterMaster);
			return null;
		}
		if (list.size() > 0) {
			System.out.println("accno==>"+list.get(0).getAccno());
			model.put("getManulReadingData", list.get(0));
			model.put("meterMaster", list.get(0));
			model.put("masterdata",masterService.find(list.get(0).getAccno()));
			model.put("subaccno",list.get(0).getAccno().substring(0,4));
			System.out.println(" hoi " + list.get(0).getAccno() + "   "
					+ list.get(0).getMaster());
			request.setAttribute("sdocode", list.get(0).getSdocode());

		}

		return list.toString();
	}

	@Override
	@Transactional
	public int updateManualReadingData(HttpServletRequest request,
			MeterMaster meterMaster, String oldseal1, ModelMap model,String oldxcurrdngkwh) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		java.util.Date xmldate=meterMaster.getXmldate();
		int flag = 0;
		String oldseal = oldseal1;
		System.out.println("***************************** : "+oldseal);
		System.out.println("***************************** : "+meterMaster.getReadingdate());
		System.out.println("***************************** : "+meterMaster.getMrino());
		Double newxcurrdngkwh1=meterMaster.getXcurrdngkwh();
	
		if(!oldxcurrdngkwh.isEmpty())
		{
			if(newxcurrdngkwh1!=null)
			{
					if(Double.parseDouble(oldxcurrdngkwh)==newxcurrdngkwh1)
					{
					}
					else
					{
						xmldate=new Date();
					}
			}
			else
			{
			}
		}
		else
		{
			if(newxcurrdngkwh1!=null)
			{
				xmldate=new Date();
			}
		}
	     System.out.println("final xmldate==="+xmldate);
		if (oldseal.equals(meterMaster.getNewseal())) 
		{
		/*	Query query = postgresMdas.createQuery("UPDATE meter_data.test tr  SET tr.test1 = '1234567' ");
				query.executeUpdate();*/
				/*
				try{
				      
				      Query query = postgresMdas.createQuery("UPDATE meter_data.test SET test1 = '12345678' ");
				      
				      int records = query.executeUpdate();
				      System.out.println(records +" record (s) 				has been deleted");
				     
				    }
				    finally{
				    	postgresMdasL.close();
				    }*/
			
			/* Query query = postgresMdas.createQuery("UPDATE meter_data.test SET test1 = '12345678' ");
			int i=customExecuteUpdate(query);*/
			flag = postgresMdas.createNamedQuery("MeterMaster.UpdateManualReadingAccount").setParameter("prevmeterstatus",meterMaster.getPrevmeterstatus())
					.setParameter("unitskva", meterMaster.getUnitskva())
					.setParameter("unitskvah", meterMaster.getUnitskvah())
					.setParameter("unitskwh", meterMaster.getUnitskwh())
					.setParameter("xcurrdngkva", meterMaster.getXcurrdngkva())
					.setParameter("xcurrdngkwh", meterMaster.getXcurrdngkwh())
					.setParameter("xcurrrdngkvah",meterMaster.getXcurrrdngkvah())
					.setParameter("newseal", meterMaster.getNewseal())
					.setParameter("oldseal", meterMaster.getOldseal())
					.setParameter("xpf", meterMaster.getXpf()).setParameter("pf",meterMaster.getPf())
					.setParameter("accno", meterMaster.getAccno())
					.setParameter("metrno", meterMaster.getMetrno())
					.setParameter("rdngmonth", meterMaster.getRdngmonth())
					.setParameter("mrname", meterMaster.getMrname())
					.setParameter("mrino", meterMaster.getMrino())
					.setParameter("readingdate", meterMaster.getReadingdate())
					.setParameter("mrdstatus", meterMaster.getMrdstatus())
					.setParameter("readingremark",meterMaster.getReadingremark())
					.setParameter("currdngkva", meterMaster.getCurrdngkva())
					.setParameter("currdngkwh", meterMaster.getCurrdngkwh())
					.setParameter("currrdngkvah", meterMaster.getCurrrdngkvah())
					.setParameter("prkva", meterMaster.getPrkva())
					.setParameter("prkvah", meterMaster.getPrkvah())
					.setParameter("prkwh", meterMaster.getPrkwh())
					.setParameter("remark", meterMaster.getRemark())
					.setParameter("xmldate",xmldate )
					.setParameter("dname", request.getSession().getAttribute("username"))
					.setParameter("t1kwh", meterMaster.getT1kwh())
					.setParameter("t2kwh", meterMaster.getT2kwh())
					.setParameter("t3kwh", meterMaster.getT3kwh())
					.setParameter("t4kwh", meterMaster.getT4kwh())
					.setParameter("t5kwh", meterMaster.getT5kwh())
					.setParameter("t6kwh", meterMaster.getT6kwh())
					.setParameter("t7kwh", meterMaster.getT7kwh())
					.setParameter("t8kwh", meterMaster.getT8kwh())
					.setParameter("t1kvah", meterMaster.getT1kvah())
					.setParameter("t2kvah", meterMaster.getT2kvah())
					.setParameter("t3kvah", meterMaster.getT3kvah())
					.setParameter("t4kvah", meterMaster.getT4kvah())
					.setParameter("t5kvah", meterMaster.getT5kvah())
					.setParameter("t6kvah", meterMaster.getT6kvah())
					.setParameter("t7kvah", meterMaster.getT7kvah())
					.setParameter("t8kvah", meterMaster.getT8kvah())
					.executeUpdate();
			
			postgresMdas.createNamedQuery("Master.updateMrname").setParameter("mrname",meterMaster.getMrname()).setParameter("accno",meterMaster.getAccno()).executeUpdate();
		  } 
		else 
		{
			flag = getCustomEntityManager("postgresMdas").createNamedQuery("MeterMaster.UpdateManualReadingAccount").setParameter("prevmeterstatus",meterMaster.getPrevmeterstatus())
					.setParameter("unitskva", meterMaster.getUnitskva())
					.setParameter("unitskvah", meterMaster.getUnitskvah())
					.setParameter("unitskwh", meterMaster.getUnitskwh())
					.setParameter("xcurrdngkva", meterMaster.getXcurrdngkva())
					.setParameter("xcurrdngkwh", meterMaster.getXcurrdngkwh())
					.setParameter("xcurrrdngkvah",meterMaster.getXcurrrdngkvah())
					.setParameter("newseal",meterMaster.getNewseal())
					.setParameter("oldseal",meterMaster.getOldseal())
					.setParameter("xpf", meterMaster.getXpf()).setParameter("pf",meterMaster.getPf())
					.setParameter("accno", meterMaster.getAccno())
					.setParameter("metrno", meterMaster.getMetrno())
					.setParameter("rdngmonth", meterMaster.getRdngmonth())
					.setParameter("mrname", meterMaster.getMrname())
					.setParameter("mrino", meterMaster.getMrino()).setParameter("readingdate", meterMaster.getReadingdate())
					.setParameter("mrdstatus", meterMaster.getMrdstatus())
					.setParameter("readingremark",meterMaster.getReadingremark())
					.setParameter("currdngkva", meterMaster.getCurrdngkva())
					.setParameter("currdngkwh", meterMaster.getCurrdngkwh())
					.setParameter("currrdngkvah", meterMaster.getCurrrdngkvah())
					.setParameter("prkva", meterMaster.getPrkva())
					.setParameter("prkvah", meterMaster.getPrkvah())
					.setParameter("prkwh", meterMaster.getPrkwh())
					.setParameter("remark", meterMaster.getRemark())
					.setParameter("xmldate", xmldate)
					.setParameter("dname", request.getSession().getAttribute("username"))
					.setParameter("t1kwh", meterMaster.getT1kwh())
					.setParameter("t2kwh", meterMaster.getT2kwh())
					.setParameter("t3kwh", meterMaster.getT3kwh())
					.setParameter("t4kwh", meterMaster.getT4kwh())
					.setParameter("t5kwh", meterMaster.getT5kwh())
					.setParameter("t6kwh", meterMaster.getT6kwh())
					.setParameter("t7kwh", meterMaster.getT7kwh())
					.setParameter("t8kwh", meterMaster.getT8kwh())
					.setParameter("t1kvah", meterMaster.getT1kvah())
					.setParameter("t2kvah", meterMaster.getT2kvah())
					.setParameter("t3kvah", meterMaster.getT3kvah())
					.setParameter("t4kvah", meterMaster.getT4kvah())
					.setParameter("t5kvah", meterMaster.getT5kvah())
					.setParameter("t6kvah", meterMaster.getT6kvah())
					.setParameter("t7kvah", meterMaster.getT7kvah())
					.executeUpdate();
			getCustomEntityManager("postgresMdas").createNamedQuery("Master.updateMrname").setParameter("mrname",meterMaster.getMrname()).setParameter("accno",meterMaster.getAccno()).executeUpdate();
		}
		if (flag == 1) {
			return flag = 1;
		}
		return flag;
	}

	@Override
	public List<MeterMaster> findaccnomonthData(MeterMaster meterMaster,ModelMap model) {
		
		MeterMaster mm=new MeterMaster();
		 List<MeterMaster> l=null;
		 System.out.println("Accno number ====================="+meterMaster.getAccno());
		 System.out.println("rdng number ====================="+meterMaster.getRdngmonth());
		 //List<MeterMaster> mtrnum = postgresMdas.createNamedQuery("MeterMaster.getDatabaseMeterMasterData").setParameter("accno", meterMaster.getAccno()).setParameter("rdngmonth",meterMaster.getRdngmonth()) .getResultList();
    String qry="select * from meter_data.metermaster m WHERE accno='"+meterMaster.getAccno()+"' and rdngmonth <='"+meterMaster.getRdngmonth()+"' ORDER BY  rdngmonth DESC";

    List<?> mtrnum =getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
    		
    for (Iterator iterator1 = mtrnum.iterator(); iterator1.hasNext();)
    {
    	Object[] obj = (Object[]) iterator1.next();
		 if (mtrnum.size() > 0) 
		 {
			System.out.println("---------"+mtrnum.get(0));
			 model.put("metrno",obj[2] );
			 model.put("data", obj[20]);
			 model.put("mtrmake",obj[48]);
			 model.put("mtrtype",obj[33]);
			 model.put("newseal",obj[23]);
		} 
		 else {
				model.put("msg", "No data found on this account ");
				return null;
			}
		 
		 }
    return l;

	}
	
	public List<MeterMaster> findaccnomonthData1(MeterMaster meterMaster,ModelMap model) {
		 System.out.println("Meter no=============="+meterMaster.getMetrno());
		 
		List<MeterMaster> l=null;
		System.out.println("rdngmonth---->"+meterMaster.getRdngmonth());
		System.out.println("metrno---->"+meterMaster.getMetrno());
		try{
		l = getCustomEntityManager("postgresMdas").createNamedQuery("MeterMaster.getDatabaseMeterMasterData1").setParameter("rdngmonth",meterMaster.getRdngmonth()).setParameter("metrno", meterMaster.getMetrno()).getResultList();
		
		 List<MeterMaster> mtrnum=l;
		 String flag="";
		 
		//System.out.println("+++++++++++++++++++++++++++++++++ : "+mtrnum.get(0).getAccno()+ " : "+meterMaster.getRdngmonth());
		if (l.size() > 0) 
		{
			for(int i=0;i<l.size();i++)
			{
			Long count=masterService.getCount(l.get(i).getAccno());
			System.out.println("account no==="+i+"   "+l.get(i).getAccno());
			System.out.println("count====>"+count);
			if(count!=0)
			{
		     System.out.println("entering==="+i);
			 System.out.println("hello the list size=="+l.size());
			 System.out.println("AAAAAAAcccccccc========"+l.get(i).getAccno());
			 model.put("masterdata", masterService.customfind(l.get(i).getAccno()));
			 model.put("accno", mtrnum.get(i).getAccno());
			 model.put("data", mtrnum.get(i).getMf());
			 model.put("mtrmake",mtrnum.get(i).getMtrmake());
			 model.put("mtrtype",mtrnum.get(i).getMtrtype());
			 model.put("newseal",mtrnum.get(i).getNewseal());
			 flag="yes";
			 return l;
			}
			}
			if(!flag.equalsIgnoreCase("yes"))
			{
				model.put("msg", "No data found on this metrnumber ");
				return null;
				
			}
		}
		else
		{
			model.put("msg", "No data found on this metrnumber ");
			return null;
		}
		return null;
	}
		catch (Exception e) 
		{
			
			System.out.println("getting exception");
			e.printStackTrace();
			return null;
		}
		}

	public  List<MeterMaster> findaccnomonthData2(MeterMaster meterMaster,ModelMap model)
	{
		 System.out.println("Meter no ============== "+meterMaster.getNewseal());
			List<MeterMaster> l=null;
			l = postgresMdas.createNamedQuery("MeterMaster.getDatabaseMeterMasterData2").setParameter("newseal", meterMaster.getNewseal()).getResultList();
		
			 List<MeterMaster> mtrnum=l;
			 
		//	 System.out.println("+++++++++++++++++++++++++++++++++ : "+mtrnum.get(0).getAccno()+ " : "+meterMaster.getRdngmonth());
			 if (l.size() > 0) {
			
				 System.out.println("AAAAAAAcccccccc========"+l.get(0).getAccno());
				 model.put("masterdata", masterService.find(l.get(0).getAccno()));
				 model.put("accno", mtrnum.get(0).getAccno());
				 model.put("metrno", mtrnum.get(0).getMetrno());
				 model.put("data", mtrnum.get(0).getMf());
				 model.put("mtrmake",mtrnum.get(0).getMtrmake());
				 model.put("mtrtype",mtrnum.get(0).getMtrtype());
				 model.put("newseal",mtrnum.get(0).getNewseal());
				 return l;
			}
			else
			{
				model.put("msg", "No data found on this newseal ");
				return null;
			}
		
	}
	
	
	
	

	/*@Override
	public List<MeterMaster> findDistinct(MeterMaster meterMaster, ModelMap model) 
	{
		List<MeterMaster> mfno=null;
		
		mfno = postgresMdas.createNamedQuery("MeterMaster.getDistinctMeterMasterData").setParameter("accno", meterMaster.getAccno()).setParameter("rdngmonth", calenderClass.getPresentMonth()).getResultList();
			for (Iterator iterator1 = mfno.iterator(); iterator1.hasNext();) 
			{
				MeterMaster mtrmster = (MeterMaster) iterator1.next();
				System.out.print("MF: " + mtrmster.getMf());
				model.put("data",mtrmster.getMf());
		
			}
	return mfno;
	}*/
	
	/*@Override
	public List<MeterMaster>  findDistinct1(MeterMaster meterMaster, ModelMap model) 
	{
		
            List<MeterMaster> mfno=null;
		
		mfno = postgresMdas.createNamedQuery("MeterMaster.getDistinctMeterMasterData1").setParameter("metrno", meterMaster.getMetrno()).setParameter("rdngmonth", calenderClass.getPresentMonth()).getResultList();
			for (Iterator iterator1 = mfno.iterator(); iterator1.hasNext();) 
			{
				MeterMaster mtrmster = (MeterMaster) iterator1.next();
				System.out.print("MF: " + mtrmster.getMf());
				model.put("data",mtrmster.getMf());
		
			}
	return mfno;
	}	
		*/
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getMeterMasterDetailsForManualReadingData(String readingMonth,String accno)
	{
		List list = null;
		final String queryString = "SELECT M.accno,MM.metrno,MM.prevmeterstatus,MM.readingremark,"+
		"MM.remark,MM.sdocode,M.sdoname,M.tariffcode,MM.prkwh,MM.currdngkwh,MM.unitskwh,MM.xcurrdngkwh,"+
				"MM.prkvah,MM.currrdngkvah,MM.unitskvah,MM.xcurrrdngkvah,MM.prkva,MM.currdngkva,MM.unitskva,"
				+ "MM.xcurrdngkva,MM.pf,MM.xpf,MM.oldseal,MM.newseal,MM.mrdstatus,M.mrname,"
                 +" MM.mrino,MM.readingdate,M.circle,MM.xmldate,MM.t1kwh,MM.t2kwh,MM.t3kwh,MM.t4kwh,MM.t5kwh,MM.t6kwh,MM.t7kwh,MM.t8kwh,MM.t1kvah,MM.t2kvah,MM.t3kvah,MM.t4kvah,MM.t5kvah,MM.t6kvah,MM.t7kvah,MM.t8kvah FROM Master M,MeterMaster MM WHERE M.accno=MM.accno AND MM.rdngmonth='"+readingMonth+"' AND M.accno='"+accno+"'";
		Query query = getCustomEntityManager("postgresMdas").createQuery(queryString);
		 list=query.getResultList();
		/* List<MeterMaster> mtrnum=list;
		 if(list.size()>0)
		 {
			 //model.put("xkwh", mtrnum.get(0).);
		 }*/
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getMeterMasterDetailsForManualReadingDataMeter(String readingMonth,String metrno)
	{
		List list = null;
		
		final String queryString = "SELECT M.accno,MM.metrno,MM.prevmeterstatus,MM.readingremark,"+
		"MM.remark,MM.sdocode,M.sdoname,M.tariffcode,MM.prkwh,MM.currdngkwh,MM.unitskwh,MM.xcurrdngkwh,"+
				"MM.prkvah,MM.currrdngkvah,MM.unitskvah,MM.xcurrrdngkvah,MM.prkva,MM.currdngkva,MM.unitskva,"
				+ "MM.xcurrdngkva,MM.pf,MM.xpf,MM.oldseal,MM.newseal,MM.mrdstatus,M.mrname,"
                 +" MM.mrino,MM.readingdate,M.circle,MM.xmldate FROM Master M,MeterMaster MM WHERE M.accno=MM.accno AND MM.rdngmonth='"+readingMonth+"' AND MM.metrno='"+metrno+"'";
		 Query query = postgresMdas.createQuery(queryString);
		 list=query.getResultList();
		 
		return list;
	}
	
	

	@Override
	public List<MeterMaster> searchByAccNoSec(HttpServletRequest request,
			MeterMaster meterMaster,String accno, ModelMap model) {
		try {
			return postgresMdas.createNamedQuery("MeterMaster.searchByAccNo").setParameter("accNo",accno).setParameter("rdgMonth", meterMaster.getRdngmonth()).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	//Added by Shivanand
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ArrayList<Resultupdated> updateMobileDataToMeterMaster(MeterMaster newConnectionMeterMaster, HttpServletRequest request, JSONArray array) {

		ArrayList<Resultupdated> list = new ArrayList<Resultupdated>();

		try {
			String connectionNumber =null;

			for (int i = 0; i < array.length(); i++)
			{
				Resultupdated res = new Resultupdated();
				int metermasterRes = 0;
//				int updated = 0;
				int rdndmthInteger = 0;

				try{

					JSONObject json =array.getJSONObject(i);
					MeterMaster meterMasterEntity = new MeterMaster();

					meterMasterEntity.setAccno(json.getString("CONSUMERID"));
					connectionNumber = meterMasterEntity.getAccno();

					double ckwhDouble = Double.parseDouble(json.getString("CKWH"));

					meterMasterEntity.setCurrdngkwh(ckwhDouble);

					double ckvahDouble = Double.parseDouble(json.getString("CKVAH"));

					meterMasterEntity.setCurrrdngkvah(ckvahDouble);

					double ckvaDouble = Double.parseDouble(json.getString("CKVA"));
					meterMasterEntity.setCurrdngkva(ckvaDouble);

					double cpfDouble = Double.parseDouble(json.getString("CPF"));
					meterMasterEntity.setPf(cpfDouble);

					meterMasterEntity.setMrname(json.getString("MRNAME"));
					meterMasterEntity.setNewseal(json.getString("CURRENTSEAL"));

					rdndmthInteger = Integer.parseInt(json.getString("BILLMONTH"));
					meterMasterEntity.setRdngmonth(rdndmthInteger);

					meterMasterEntity.setReadingremark(json.getString("METERREMARKS"));
					meterMasterEntity.setRemark(json.getString("OTHERREMARKS"));
					meterMasterEntity.setOldseal(json.getString("OLDSEAL"));
					meterMasterEntity.setMrino(json.getString("CMRID"));

					meterMasterEntity.setDemandType(json.getString("DEMANDTYPE"));

					double prKwh = Double.parseDouble(json.getString("PKWH"));

					meterMasterEntity.setPrkwh(prKwh);
					meterMasterEntity.setUnitskwh(meterMasterEntity.getCurrdngkwh() - meterMasterEntity.getPrkwh());

					if(meterMasterEntity.getUnitskwh() < 0){
						meterMasterEntity.setUnitskwh(meterMasterEntity.getCurrdngkwh());
					}

					double prkvah = Double.parseDouble(json.getString("PKVAH"));
					meterMasterEntity.setPrkvah(prkvah);

					if(meterMasterEntity.getCurrrdngkvah() == 99999999){
						meterMasterEntity.setUnitskvah(0.0);
					}
					else{
						meterMasterEntity.setUnitskvah(meterMasterEntity.getCurrrdngkvah() - meterMasterEntity.getPrkvah());
						if(meterMasterEntity.getUnitskvah() < 0){
							meterMasterEntity.setUnitskvah(meterMasterEntity.getCurrrdngkvah());
						}
					}

					String submitDate = json.getString("SUBMITDATETIMESTAMP");

					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					java.util.Date submitDateFormat = format.parse(submitDate);
					java.sql.Date submitDateSql = new java.sql.Date(submitDateFormat.getTime());
					meterMasterEntity.setReadingdate(submitDateSql);

					java.util.Date dateStamp = new java.util.Date();
					java.sql.Date dateStampSql = new java.sql.Date(dateStamp.getTime());
					meterMasterEntity.setDatestamp(dateStampSql);

					meterMasterEntity.setMm(1);

					meterMasterEntity.setCmri(json.optString("CMRI_SUCCESS"));
					
					metermasterRes = postgresMdas.createNamedQuery("MeterMaster.updateMobileDataToMeterMaster").setParameter("rdngmonth",meterMasterEntity.getRdngmonth())
							.setParameter("accno", meterMasterEntity.getAccno())
							.setParameter("currdngkwh", meterMasterEntity.getCurrdngkwh())
							.setParameter("currrdngkvah", meterMasterEntity.getCurrrdngkvah())
							.setParameter("currdngkva", meterMasterEntity.getCurrdngkva())
							.setParameter("pf",meterMasterEntity.getPf())
							.setParameter("newseal",meterMasterEntity.getNewseal())
							.setParameter("mrname",meterMasterEntity.getMrname())
							.setParameter("readingremark",meterMasterEntity.getReadingremark())
							.setParameter("remark",meterMasterEntity.getRemark())
							.setParameter("unitskwh",meterMasterEntity.getUnitskwh())
							.setParameter("unitskvah",meterMasterEntity.getUnitskvah())
							.setParameter("readingdate",meterMasterEntity.getReadingdate())
							.setParameter("datestamp",meterMasterEntity.getDatestamp())
							.setParameter("oldseal",meterMasterEntity.getOldseal())
							.setParameter("mm",meterMasterEntity.getMm())
							.setParameter("mrino", meterMasterEntity.getMrino())
							.setParameter("demandType", meterMasterEntity.getDemandType())
							.setParameter("cmri", meterMasterEntity.getCmri())
							.setParameter("uploadstatus", 1)
							.executeUpdate();


					int sealResult = 1;
					boolean updateImgAndGps=false;
					
					
					if(metermasterRes > 0){
						//UPDATE UPLOAD STATUS IN METER MASTER
//						updated = 	postgresMdas.createNamedQuery("MeterMaster.updateUploadStatus").setParameter("uploadstatus", 1) .setParameter("consumerid", meterMasterEntity.getAccno()) .setParameter("billmonth", meterMasterEntity.getRdngmonth()).executeUpdate();

						String phNumber = json.getString("PHONE");
						String industryType = json.getString("INDUSTRYTYPE");
						//UPDATE PHONE NUMBER IN MASTER
						masterService.updatePhoneno(meterMasterEntity.getAccno(), phNumber, industryType);//UPDATE PHONE NUMBER TO MASTER

						if(!(meterMasterEntity.getNewseal().trim().equals("") || meterMasterEntity.getNewseal().trim().equals(" ") || meterMasterEntity.getNewseal() == null)){
							sealResult = 0;
							String sealRemark = "SEALUSED";
							String meterNo = json.getString("METERNO");
							
							System.out.println("NEW SEAL: "+meterMasterEntity.getNewseal().trim()+"  SEAL REMARK: "+sealRemark+"  METERNO: "+meterNo+"  MRNAME: "+meterMasterEntity.getMrname()+"  RDNGMONTH: "+meterMasterEntity.getRdngmonth()+"  ACCNO: "+meterMasterEntity.getAccno());

							//INSERT SEAL NUMBER TO SEAL TABLE
							sealResult = sealService.updateSealTable(meterMasterEntity.getNewseal().trim(), sealRemark, meterNo, meterMasterEntity.getMrname(), meterMasterEntity.getRdngmonth(), meterMasterEntity.getAccno());
						}
						
					
						try {	//INSERT IMAGE AND GPS COORDINATES TO METERMASTER_EXTRA TABLE
							MeterMasterExtra extra = new MeterMasterExtra();
							extra.setMyKey(new CustomKey(json.optString("CONSUMERID"),Long.parseLong(json.optString("BILLMONTH"))));
							
							byte[] image1=Base64.decodeBase64(json.getString("IMAGE1"));
							byte[] image2=Base64.decodeBase64(json.getString("IMAGE2"));
							byte[] image3=Base64.decodeBase64(json.getString("IMAGE3"));
							
							extra.setImageOne(image1);
							extra.setImageTwo(image2);
							extra.setImageThree(image3);
							extra.setLatitude(json.optString("LATITUDE"));
							extra.setLongitude(json.optString("LONGITUDE"));
							extra.setAccuracy(json.optString("ACURACY"));
							extra.setMetrno(json.optString("METERNO"));
							updateImgAndGps= extraService.update(extra) instanceof MeterMasterExtra;
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
					

					System.out.println("UPLOAD STATUS ++++ "+meterMasterEntity.getAccno()+"   "+meterMasterEntity.getRdngmonth()+"   "+metermasterRes+"   "+/*updated+"   "+*/sealResult+"  "+updateImgAndGps);
					
					if(metermasterRes>0 /*&& updated>0*/  /*&& sealResult>0 */&& updateImgAndGps){
						res.status="UPDATED";
						res.connectionNo = connectionNumber;
					}
					else{
						res.status="UPDATEFAILED";
						res.connectionNo = connectionNumber;
					}

				}					
				catch(Exception e){
					e.printStackTrace();
					res.status="UPDATEFAILED";
					res.connectionNo = connectionNumber;

				}

				list.add(res);
			} 

		}catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}
	//Added by Shivanand
	
	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public double getMFforTheGivenAccnumber(String accno, int readingmth) {
		// TODO Auto-generated method stub
		
		double mf1 =0.0;
		@SuppressWarnings("unchecked")
		List<MeterMaster> mf =postgresMdas.createNamedQuery("MeterMaster.getMFforTheGivenAccnumber").setParameter("accno", accno)
				.setParameter("rdngmonth", readingmth).getResultList();
		for (int i = 0; i < mf.size(); i++)
		{
			mf1 =mf.get(i).getMf();
		}
		
		return mf1;
	}

	
	
	
	//Added by Shivanand
	
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public JSONArray getNotMRDdata(String mrname, HttpServletRequest request) 
	{
		int billmonthint = this.getMaxRdgMonthYear(request);
		int uploadstatus = 0;
		System.out.println("----------------- : "+mrname);
		List<String> list = null;
				

				
				
				JSONArray approvalMap = new JSONArray();
				
				
			/*	
				//just for testing
				
				if((new Date().getMonth()+1) == 10){
					
					
					billmonthint = 201610;
					
				}
				*/
				
				
				
				String	query = "select RDNGMONTH,m.mrname,m.tadesc,mm.accno,mm.metrno,m.name,mm.readingremark,mrino,readingdate, m.MNP, m.ADDRESS1  from metermaster mm,master m where m.accno=mm.accno and mcst like 'R' and rdngmonth="+billmonthint+" and mm.NOTMRD = 0 and m.mrname like '"+mrname+"'  and rtc='0' and readingremark not in ('BOX LOCK','CNP','DC','COUNTER METER','DISPLAY OUT / CE','METER BURNT','METER DEFECTIVE','METER STOP','READING NOT POSSIBLE','RNP','PDC')   and mtrmake not in ('ABB','AVON','BHEL','DATAPRO','DUKE','ELYMER','LnTOLD','SYNERGY','TTL') and mtrtype not in ('CM','IRDA')  and readingdate is not null order by mtrmake";
				
				
				
				
				
				
				
				
						
				
				/*System.out.println("No MRD query :"+query);*/

				
				
				list = postgresMdas.createNativeQuery(query).getResultList();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					final Object[] objects = (Object[]) iterator.next();
					approvalMap.put(new HashMap<String, Object>() {
						
						
						{
							put("rdngmonth", (objects[0] != null) ? objects[0].toString() : "NA");
							put("mrname", (objects[1] != null) ? objects[1].toString() : "NA");
							put("tadesc", (objects[2] != null) ? objects[2].toString() : "NA");
							put("accno", (objects[3] != null) ? objects[3].toString() : "NA");
							put("metrno", (objects[4] != null) ? objects[4].toString() : "NA");
							put("name", (objects[5] != null) ? objects[5].toString() : "NA");
							put("readingremark", (objects[6] != null) ? objects[6].toString() : "NA");
							put("mrino", (objects[7] != null) ? objects[7].toString() : "NA");
							put("readingdate", (objects[8] != null) ? objects[8].toString() : "NA");
							put("mnp", (objects[9] != null) ? objects[9].toString() : "NA");
							put("address", (objects[10] != null) ? objects[10].toString() : "NA");
							
							
							
						}
					});
				}
				
				
				/*System.out.println(approvalMap);*/
				
				
				
				return approvalMap;
				
	}
	
	
	
	
	
	//Added by Shivanand
	
	
			@Transactional(propagation=Propagation.SUPPORTS)
			public int UpdateNoMRDflag(String accno, int billmonth, String mrname) 
			{
						String query1= "UPDATE METERMASTER SET NOTMRD = 1 WHERE ACCNO LIKE '"+accno+"' AND MRNAME LIKE '"+mrname+"' AND RDNGMONTH = "+billmonth;
						int result = postgresMdas.createNativeQuery(query1).executeUpdate();
						return result;
			}
			
	
	
	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public double getCurrentKWH(String accno, int readingmth) {
		// TODO Auto-generated method stub
		
		double ckwh =0.0;
		@SuppressWarnings("unchecked")
		List<MeterMaster> mf =postgresMdas.createNamedQuery("MeterMaster.getMFforTheGivenAccnumber").setParameter("accno", accno)
				.setParameter("rdngmonth", readingmth).getResultList();
		
		System.out.println("mf.size() :"+mf.size());
		
		/*for(MeterMaster mm:mf){
			System.out.println(mm.getCurrdngkwh());
		}*/
		for (int i = 0; i < mf.size(); i++)
		{
			System.out.println("hoiiii");
			/*ckwh =mf.get(i).getCurrdngkwh();*/
			System.out.println(mf.get(i).getReadingremark()+" helooo");
			
		}
		
		return ckwh;
	}
		
	
	@Override
	public long checkMeterExist(Integer rdngmonth, String metrno)
	{
		return (long)postgresMdas.createNamedQuery("MeterMaster.checkMeterExists").setParameter("rdngmonth", rdngmonth).setParameter("metrno", metrno).getSingleResult();
		
	}

	@Override
	public long countPending(MeterMaster meterMaster)
	{
		
		Long countTotal=null;
		try
		{
			countTotal=(long)getCustomEntityManager("postgresMdas").createNamedQuery("MeterMaster.findTotalPending").setParameter("rdngmonth",meterMaster.getRdngmonth()).getSingleResult();
			if(countTotal>0)
				return countTotal;
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
		return countTotal;
	}

	@Override
	public long countNOI(MeterMaster meterMaster)
	{
		
		Long countTotal=null;
		try
		{
			countTotal=(long)getCustomEntityManager("postgresMdas").createNamedQuery("MeterMaster.findTotalNOI").setParameter("rdngmonth",meterMaster.getRdngmonth()).getSingleResult();
			if(countTotal>0)
				return countTotal;
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
		return countTotal;
	}

	public List getMrDate(MeterMaster meterMaster,HttpServletRequest request, ModelMap model) {

		 List<Object[]> searchDetailsTwo=null;
		try
		{
			    Query query1=postgresMdas.createNativeQuery("select DISTINCT a.mrname from mdm_test.master a , mdm_test.metermaster b where  a.consumerstatus like 'R'   and    CAST(b.rdngmonth as INTEGER) = ? and b.mcst like 'R'   and a.accno=b.accno  order by a.mrname");
			    query1.setParameter(1,meterMaster.getRdngmonth()); 
			    searchDetailsTwo = query1.getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return searchDetailsTwo;
	}
	
	
	
	@Override
	public List getMrWiseReprot(MeterMaster meterMaster,HttpServletRequest request, ModelMap model)
	{
	
		 List<Object[]> searchDetailsOne=null;
		try
		{
			String qry=" select a.mrname,count(*) from meter_data.master a , meter_data.metermaster b where  a.consumerstatus like 'R'  and "
					+ " cast(b.rdngmonth as INTEGER) = '"+meterMaster.getRdngmonth()+"' and b.mcst like 'R'   and a.accno=b.accno group by a.mrname order by a.mrname";
				System.err.println(qry);
			searchDetailsOne = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return searchDetailsOne;
	}

	@Override
	public List getMrWiseReprotOne(MeterMaster meterMaster,HttpServletRequest request, ModelMap model,String circle) {

		 List<Object[]> searchDetailsOne=null;
		 Set<String> dateSet=new HashSet<String>();
		 double val=0.0;
		 List<String[]> sumList=new ArrayList<String[]>();
		 String[] sumArray=new String[2];
		 String datVal="";
		try
		{
			Query query=postgresMdas.createNativeQuery("select a.mrname,to_char(b.readingdate ,'dd-MM-yyyy'),count(*),a.sdoname from master a , metermaster b where  a.consumerstatus like 'R'   and    b. rdngmonth like ? and a.circle like ? and a.accno=b.accno and b.readingdate IS NOT NULL group by a.mrname,b.readingdate,a.sdoname order by a.mrname ");
		    query.setParameter(1,meterMaster.getRdngmonth()); 
		    query.setParameter(2,circle);  
		    searchDetailsOne = query.getResultList();
		    for (int i = 0; i < searchDetailsOne.size(); i++) 
		    {
		    	Object[] obj=searchDetailsOne.get(i);
		    	for (int j = 0; j < obj.length; j++)
		    	{
					if(j==1)
					{
						dateSet.add(obj[j].toString());
					}
				}
			}
		    for (int i = 0; i < searchDetailsOne.size(); i++) 
		    {
		    	Object[] obj=searchDetailsOne.get(i);
		    	for (int j = 0; j < obj.length; j++)
		    	{
		    		if(j==1)
		    		{
		    			/*for (String date : dateSet) 
						{*/
							if(obj[1].toString().equalsIgnoreCase("01-05-2016"))
							{
								val=val+Double.parseDouble(obj[2].toString());
							}
						/*}*/
		    		}
				}
		    	 
			}
		    MDMLogger.logger.info("====val===>"+val);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return searchDetailsOne;
	}
	
	
	/*public List getMrDay(MeterMaster meterMaster,HttpServletRequest request, ModelMap model) {

		 List<Object[]> searchDetailsOne=null;
		try
		{
			Query query=postgresMdas.createNativeQuery("select b.readingdate,count(*) from master a , metermaster b where  a.consumerstatus like 'R'   and    b. rdngmonth like ? and b.mcst like 'R'   and a.accno=b.accno and b.readingdate IS NOT NULL  group by b.readingdate  ORDER BY b.readingdate");
		    query.setParameter(1,meterMaster.getRdngmonth()); 
		    searchDetailsOne = query.getResultList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return searchDetailsOne;
	}*/
	

	@Override
	public List getDays(MeterMaster meterMaster, HttpServletRequest request,ModelMap model,String circle) 
	{
		  System.out.println("getDays  function");
		 List<Object[]> searchDetailsOne=null;
		 List<Object[]> searchDetailsTwo=getMrDate(meterMaster,request,model);
		 List<Object[]> daysItems=null;
		 List<Object[]> previtem=getMrWiseReprot(meterMaster,request,model);
		
		 List finalDay=null ;
		 
			try  
			{
				Query query=postgresMdas.createNativeQuery("select to_char(b.readingdate,'dd-MON-yyyy'),count(*) from meter_data.master a , meter_data.metermaster b where  a.consumerstatus like 'R'   and    CAST(b.rdngmonth as INTEGER) = ? and a.circle like ?  and a.accno=b.accno and b.readingdate IS NOT NULL  group by b.readingdate  ORDER BY b.readingdate");
			    query.setParameter(1,meterMaster.getRdngmonth()); 
			    query.setParameter(2,circle); 
			    searchDetailsOne = query.getResultList();
			    System.out.println("List===="+searchDetailsOne.size());


			

			}
			
		
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return searchDetailsOne;
	}

	@Override
	public List getMrWiseBilledDetails(String mrname,String rdngmonth,String sdoName, HttpServletRequest request, ModelMap model) {
		System.out.println("Mr Name Reading Month SDO Name======================"+mrname+" "+rdngmonth+" "+sdoName);
		List billedDetails=null;
		try
		{
			String qry="select a.subdiv,a.accno,b.metrno,a.name,a.address1,a.PHONENO,a.mrname from meter_data.master a,meter_data.metermaster b where a.accno=b.accno "
					+ "and cast(b.rdngmonth as text) like '"+rdngmonth+"' and a.mrname like '"+mrname+"' and a.subdiv like '"+sdoName+"' and a.consumerstatus like 'R' order by accno";
			System.err.println(qry);
			billedDetails=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("Size of deyails ===="+billedDetails.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return billedDetails;
	}

	@Override
	public List getMrWisePendingDetails(String mrname,String sdoname,String rdngmonth,HttpServletRequest request, ModelMap model) {
		System.out.println("mrname rdngmonth sdoname ======================"+mrname+" "+rdngmonth+" "+sdoname);
		List pendingDetails=null;
		try
		{
			String qry="select a.subdiv,a.accno,b.metrno,a.name,a.address1,a.PHONENO,a.mrname from meter_data.master a,"
					+ "meter_data.metermaster b where a.accno=b.accno and cast(b.rdngmonth as text) like '"+rdngmonth+"' "
					+ "and a.mrname like '"+mrname+"' and a.subdiv like '"+sdoname+"' and a.consumerstatus like 'R' and b.readingdate is null order by accno";
			System.out.println(qry);
			pendingDetails=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("Size of deyails ===="+pendingDetails.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return pendingDetails;
	}

	
	
	 
	 
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<?> getOtherExternalReportData(String condition,String month)
	{
		String query = "";
		if(condition.equalsIgnoreCase("1"))
		{
			query = "select a.accno,b.metrno,a.name,a.address1,b.mf,a.kworhp,a.sanload,"
					+ "a.contractdemand,b.readingremark,b.rtc,b.mrname from meter_data.master a,meter_data.metermaster b "
					+ "where a.accno=b.accno and b.rdngmonth='"+month+"' "
							+ "and b.readingremark in ('METER BURNT','DISPLAY NOT VISIBLE','DISPLAY OUT / CE','METER STOP')  order by accno";
		}
		else if(condition.equalsIgnoreCase("2"))
		{
			/*query = "select a.accno,b.metrno,a.name,a.address1,b.mf,a.kworhp,"
					+ "a.sanload,a.contractdemand,b.CURRDNGKVA ,b.XCURRDNGKVA ,"
					+ "b.readingremark from master a,metermaster b "
					+ "where a.accno=b.accno and b.rdngmonth='"+month+"' and a.contractdemand='0' and b.CURRDNGKVA>'50'  "
					+ "and CURRDNGKVA not like '99999999'   order by accno";*/
			query = "SELECT M.ACCNO,MM.METRNO,M.NAME,M.ADDRESS1,MM.MF,M.KWORHP,M.SANLOAD,M.CONTRACTDEMAND ,"+
						"MM.CURRDNGKVA,MM.XCURRDNGKVA,MM.READINGREMARK,"+
						"(MM.CURRDNGKVA*MM.MF) AS CD1,"+
						"((M.CONTRACTDEMAND*105)/100) AS CD2,((M.CONTRACTDEMAND*110)/100) AS CD3 "+
						"FROM meter_data.MASTER M,meter_data.METERMASTER MM WHERE M.ACCNO=MM.ACCNO AND M.CONSUMERSTATUS NOT IN ('P','D','A')"+
						"AND MM.RDNGMONTH ='"+month+"' ORDER BY M.ACCNO";
		}
		
		else if(condition.equalsIgnoreCase("3"))
		{
			query = "select a.accno,b.metrno,a.name,a.address1,b.mf,a.kworhp,a.sanload,a.contractdemand,"
					+ "b.readingremark,b.unitskwh,b.mrname from meter_data.master a,meter_data.metermaster b "
					+ "where a.accno=b.accno and b.rdngmonth='"+month+"' and a.consumerstatus like 'R' and unitskwh='0' and b.readingdate is not null order by accno";
		}
		else if(condition.equalsIgnoreCase("4"))
		{
			String lastmonth = "";
			SimpleDateFormat sdf= new SimpleDateFormat("YYYYMM");
			java.util.Date d = new java.util.Date();
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(month.substring(0,4)), Integer.parseInt(month.substring(4,6)),1);
			String[] lastMonths = new String[7];
			for(int i=0;i<=6;i++)
			{
				cal.add(Calendar.MONTH, -1);
				java.util.Date newdate = cal.getTime();
				lastMonths[i] = sdf.format(newdate);
			}
			
			query = "SELECT M.ACCNO,MM.METRNO,M.NAME,M.ADDRESS1,M.TARIFFCODE,M.CONTRACTDEMAND,MM.CURRDNGKWH,"+ 
					"(SELECT ROUND((AVG(UNITSKWH)/6))   FROM meter_data.METERMASTER MM1 WHERE MM1.RDNGMONTH IN ("+lastMonths[1]+","+lastMonths[2]+","+lastMonths[3]+","+lastMonths[4]+","+lastMonths[5]+","+lastMonths[6]+") "+
					"AND MM1.ACCNO=M.ACCNO) AS AVRG,(SELECT MM2.UNITSKWH  FROM meter_data.METERMASTER MM2 WHERE MM2.RDNGMONTH='"+lastMonths[1]+"' AND MM2.ACCNO=M.ACCNO) AS PREVKWH,"+
					"MM.UNITSKWH  FROM meter_data.MASTER M,meter_data.METERMASTER MM WHERE M.ACCNO = MM.ACCNO AND MM.RDNGMONTH='"+month+"' AND "+
					" MM.UNITSKWH < (((SELECT MM2.UNITSKWH  FROM meter_data.METERMASTER MM2 WHERE MM2.RDNGMONTH='"+lastMonths[1]+"' AND MM2.ACCNO=M.ACCNO  LIMIT 1)/100)*30) ORDER BY M.ACCNO";
			
			/*query = "SELECT M.ACCNO,MM.METRNO,M.NAME,M.ADDRESS1,M.TARIFFCODE,M.CONTRACTDEMAND,MM.CURRDNGKWH,"+
					"(SELECT ROUND((AVG(UNITSKWH)/6))   FROM METERMASTER MM1 WHERE MM1.RDNGMONTH IN ("+lastMonths[1]+","+lastMonths[2]+","+lastMonths[3]+","+lastMonths[4]+","+lastMonths[5]+","+lastMonths[6]+") "+
					"AND MM1.ACCNO=M.ACCNO) AS AVRG,(SELECT MM2.UNITSKWH  FROM METERMASTER MM2 WHERE MM2.RDNGMONTH="+lastMonths[1]+" AND MM2.ACCNO=M.ACCNO) AS PREVKWH,"+
					"MM.UNITSKWH  FROM MASTER M,METERMASTER MM WHERE M.ACCNO = MM.ACCNO AND MM.RDNGMONTH='"+month+"' ORDER BY M.ACCNO";*/
		}
		else if(condition.equalsIgnoreCase("5"))
		{
			String lastmonth = "";
			SimpleDateFormat sdf= new SimpleDateFormat("YYYYMM");
			java.util.Date d = new java.util.Date();
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(month.substring(0,4)), Integer.parseInt(month.substring(4,6)),1);
			String[] lastMonths = new String[7];
			for(int i=0;i<=6;i++)
			{
				cal.add(Calendar.MONTH, -1);
				java.util.Date newdate = cal.getTime();
				lastMonths[i] = sdf.format(newdate);
			}
			
			query = "SELECT M.ACCNO,MM.METRNO,M.NAME,M.ADDRESS1,M.TARIFFCODE,M.CONTRACTDEMAND,MM.CURRDNGKWH,"+ 
					"(SELECT ROUND((AVG(UNITSKWH)/6))   FROM meter_data.METERMASTER MM1 WHERE MM1.RDNGMONTH IN ("+lastMonths[1]+","+lastMonths[2]+","+lastMonths[3]+","+lastMonths[4]+","+lastMonths[5]+","+lastMonths[6]+") "+
					"AND MM1.ACCNO=M.ACCNO) AS AVRG,(SELECT MM2.UNITSKWH  FROM meter_data.METERMASTER MM2 WHERE MM2.RDNGMONTH='"+lastMonths[1]+"' AND MM2.ACCNO=M.ACCNO) AS PREVKWH,"+
					"MM.UNITSKWH  FROM meter_data.MASTER M,meter_data.METERMASTER MM WHERE M.ACCNO = MM.ACCNO AND MM.RDNGMONTH='"+month+"' AND "+
					" MM.UNITSKWH > (((SELECT MM2.UNITSKWH  FROM meter_data.METERMASTER MM2 WHERE MM2.RDNGMONTH='"+lastMonths[1]+"' AND MM2.ACCNO=M.ACCNO  LIMIT 1)/100)*30) ORDER BY M.ACCNO";
			
			
		}
		
		Query q = postgresMdas.createNativeQuery(query);
		List list = q.getResultList();
		System.out.println("--------the list Size is : "+list.size());
		
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> showVariationOfEnergy(String month, ModelMap model,String category,String sdocode)
	{
		String query="";
		List<Object[]> list=null;
		try 
		{
			if(category.equalsIgnoreCase("increased"))
			{
				query="SELECT ACCNO,NAME,ADDRESS1,METRNO,AVGSIX,AVGYEAR,ROUND(((UNITSKWH-AVGSIX)/AVGSIX)*100,2) AS SIXMONTHS,ROUND(((UNITSKWH-AVGYEAR)/AVGYEAR)*100,2) AS TWELVEMONTHS,UNITSKWH,INDUSTRYTYPE,SANLOAD,SDOCODE "
						+ "FROM"
						+ " (SELECT  ACCNO,NAME,ADDRESS1,METRNO,AVGSIX,AVGYEAR,ROUND(((UNITSKWH-AVGSIX)/AVGSIX)*100,2) AS SIXMONTHS,ROUND(((UNITSKWH-AVGYEAR)/AVGYEAR)*100,2) AS TWELVEMONTHS,UNITSKWH,INDUSTRYTYPE,SANLOAD,SDOCODE "
						+ "FROM "
						+ "(SELECT MS.ACCNO, MS.NAME,MS.ADDRESS1, MMS.METRNO, MS.AVGSIX, MS.AVGYEAR, MMS.UNITSKWH,MS.INDUSTRYTYPE,MS.SANLOAD,MS.SDOCODE FROM "
						+ "MASTER MS, METERMASTER MMS WHERE MS.SDOCODE="+sdocode+" AND MS.ACCNO=MMS.ACCNO AND (MMS.PREVMETERSTATUS NOT IN('MI','MC','RC') "
						+ "OR MMS.PREVMETERSTATUS IS NULL) AND MS.CONSUMERSTATUS LIKE 'R' AND MMS.UNITSKWH>0 AND MS.AVGSIX>0 AND "
						+ "MS.AVGYEAR>0 AND MMS.RDNGMONTH="+month+")) WHERE SIXMONTHS BETWEEN 10 AND 75 AND  TWELVEMONTHS BETWEEN 10 AND 75";
				System.out.println("query increased-->"+query);
				list = postgresMdas.createNativeQuery(query).getResultList();
				
				model.put("gridData", "Increased Consumption");
			}
			else if(category.equalsIgnoreCase("downfall"))
			{
				query="SELECT ACCNO,NAME,ADDRESS1,METRNO,AVGSIX,AVGYEAR,ROUND(((UNITSKWH-AVGSIX)/AVGSIX)*100,2) AS SIXMONTHS,ROUND(((UNITSKWH-AVGYEAR)/AVGYEAR)*100,2) AS TWELVEMONTHS,UNITSKWH,INDUSTRYTYPE,SANLOAD,SDOCODE "
						+ "FROM "
						+ "(SELECT  ACCNO,NAME,ADDRESS1,METRNO,AVGSIX,AVGYEAR,ROUND(((UNITSKWH-AVGSIX)/AVGSIX)*100,2) AS SIXMONTHS,ROUND(((UNITSKWH-AVGYEAR)/AVGYEAR)*100,2) AS TWELVEMONTHS,UNITSKWH,INDUSTRYTYPE,SANLOAD,SDOCODE "
						+ "FROM(SELECT MS.ACCNO, MS.NAME,MS.ADDRESS1, MMS.METRNO, MS.AVGSIX, MS.AVGYEAR, MMS.UNITSKWH,MS.INDUSTRYTYPE,MS.SANLOAD,MS.SDOCODE "
						+ "FROM MASTER MS, METERMASTER MMS WHERE MS.SDOCODE="+sdocode+" AND MS.ACCNO=MMS.ACCNO AND (MMS.PREVMETERSTATUS NOT IN('MI','MC','RC') OR MMS.PREVMETERSTATUS IS NULL) AND MS.CONSUMERSTATUS LIKE 'R' AND MMS.UNITSKWH>0 AND MS.AVGSIX>0 AND MS.AVGYEAR>0 AND MMS.RDNGMONTH="+month+")) WHERE SIXMONTHS BETWEEN -75 AND -10 AND  TWELVEMONTHS BETWEEN -75 AND -10";
				list = postgresMdas.createNativeQuery(query).getResultList();
				model.put("gridData", "Downfall Consumption");
			}
			MDMLogger.logger.info("=============>query"+query);
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<Object[]> getDistinctMake(int RdngMonth)
	{
		System.out.println("RdngMonth-->"+RdngMonth);
		String query="";
		List<Object[]> list=null;
		try 
		{

			query="SELECT DISTINCT MTRMAKE FROM meter_data.mm WHERE RDNGMONTH='"+RdngMonth+"' AND cst like 'R' ORDER BY MTRMAKE ASC";

			//query="select DISTINCT mtrmake from mm where rdngmonth=201806 and cst like 'R'"

			list=getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
		
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> showMrDaywiseReport(String billmonth,ModelMap model,HttpServletRequest request)
	{
		List<Object[]> list=null;
		List<Object> dateStringList=new ArrayList<Object>();
		List<Object[]> totalDaySumList=new ArrayList<Object[]>();
		BigDecimal totaNoi=null;
		BigDecimal totalEntered=null;
		BigDecimal totalPending=null;
		try
		{
			String dateSql="SELECT DISTINCT TO_CHAR(READINGDATE,'dd-MM-yyyy') FROM mdm_test.METERMASTER WHERE RDNGMONTH="+billmonth+" AND TO_CHAR(READINGDATE,'yyyyMM') LIKE '"+billmonth+"'  ORDER BY TO_CHAR(READINGDATE,'dd-MM-yyyy')";
			MDMLogger.logger.info("+++++++++++++++++++++++++++++>"+dateSql);
			List<String> dateList=postgresMdas.createNativeQuery(dateSql).getResultList();//=getAllDatesInMonth(Integer.parseInt(billmonth.substring(4, 6))-1,Integer.parseInt(billmonth.substring(0, 4)));
			String sql="";
			 String sql1="";
			 String sumString1="SUM(";
			 String sumString="";
			 String groupString="";
			 if(dateList.size()>0)
			 {
				 for (int k = 0; k <dateList.size(); k++) 
					{
					 dateStringList.add(dateList.get(k).substring(0, 2));
						//sql="(SELECT count(DISTINCT consumer_sc_no) FROM  photobilling.hhbm_download WHERE sdo_code='"+siteCode+"' AND bill_month='"+billmonth+"' AND substr(billdate,0,11)='"+dateList.get(k)+"' AND bmd_reading=mrcode)as day"+(k+1)+"";
					sql="(SELECT COUNT(MM.ACCNO)  FROM mdm_test.METERMASTER MM, mdm_test.MASTER MA WHERE MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+billmonth+"' AND MA.MRNAME LIKE AA.MRNAME AND MA.CONSUMERSTATUS LIKE 'R' AND MM.MCST LIKE 'R' AND TO_CHAR(MM.READINGDATE,'dd-MM-yyyy')='"+dateList.get(k)+"')AS DAY"+dateList.get(k).substring(0, 2)+"";	
					 sumString="BB"+"."+"DAY"+dateList.get(k).substring(0, 2);
						if(k!=dateList.size()-1)
		               {
							sql1=sql1+sql+",";
							sumString1=sumString1+sumString+"+";
							groupString=groupString+sumString+",";
		               }
		               else
		               {
		               	sql1=sql1+sql;
		               	sumString1=sumString1+sumString+")";
		               	groupString=groupString+sumString;
		               }
					}
				 MDMLogger.logger.info("=====sql===========>"+sql);
				 model.put("daysOfMonth",dateStringList);
				 sumString1=sumString1+"AS TOTALENTERED";
				
				 String query1="SELECT ABC.*,(ABC.TOTALNOI-ABC.TOTALENTERED)AS TOTALPENDING FROM(SELECT  BB.*,"+sumString1+" FROM(SELECT AA.MRNAME,(SELECT COUNT(MM.ACCNO)  FROM mdm_test.METERMASTER MM, mdm_test.MASTER MA WHERE MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+billmonth+"' AND MA.MRNAME LIKE AA.MRNAME AND MM.MCST LIKE 'R' AND MA.CONSUMERSTATUS LIKE 'R')AS TOTALNOI,"+sql1+" FROM (SELECT DISTINCT MA.MRNAME  FROM mdm_test.METERMASTER MM ,mdm_test.MASTER MA WHERE MA.ACCNO=MM.ACCNO AND  MM.RDNGMONTH='"+billmonth+"' AND MA.MRNAME IS NOT NULL )AA"
				 		+ ")BB WHERE BB.MRNAME NOT LIKE '0' GROUP BY BB.MRNAME,BB.TOTALNOI,"+groupString+" ORDER BY BB.MRNAME)ABC";
				 MDMLogger.logger.info("================>query1"+query1);
				 list=postgresMdas.createNativeQuery(query1).getResultList();
				 
				 for (int i = 0; i < list.size(); i++) 
				 {
					if(i==0)
					{
						Object[] arr=list.get(i);
						model.put("arrayLength", arr.length);
					}
				}
				 String totalDaySum="SELECT AA.*,(SELECT COUNT(ACCNO)  FROM mdm_test.METERMASTER WHERE RDNGMONTH='"+billmonth+"'"+" " 
	+"AND TO_CHAR(READINGDATE,'dd-MM-yyyy') LIKE AA.RDATE ) FROM("+" "
	+"SELECT DISTINCT TO_CHAR(READINGDATE,'dd-MM-yyyy') AS RDATE FROM mdm_test.METERMASTER WHERE RDNGMONTH='"+billmonth+"' AND TO_CHAR(READINGDATE,'yyyyMM') LIKE '"+billmonth+"'  ORDER BY TO_CHAR(READINGDATE,'dd-MM-yyyy')"+" "
	+")AA ORDER BY AA.RDATE";
				 MDMLogger.logger.info("======================>totalDaySum"+totalDaySum);
				 totalDaySumList=postgresMdas.createNativeQuery(totalDaySum).getResultList();
				 String totalNoi="SELECT COUNT(MA.ACCNO) AS TOTALNOI FROM mdm_test.METERMASTER MM, mdm_test.MASTER MA WHERE   MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+billmonth+"' AND MM.MCST LIKE 'R' AND MA.CONSUMERSTATUS LIKE 'R'";
				 String totalentered="SELECT COUNT(MA.ACCNO) AS TOTALNOI FROM mdm_test.METERMASTER MM, mdm_test.MASTER MA WHERE   MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+billmonth+"' AND MM.MCST LIKE 'R' AND MA.CONSUMERSTATUS LIKE 'R' AND MM.READINGDATE IS NOT NULL";
				 String totalpending="SELECT COUNT(MA.ACCNO) AS TOTALNOI FROM mdm_test.METERMASTER MM, mdm_test.MASTER MA WHERE   MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+billmonth+"' AND MM.MCST LIKE 'R' AND MA.CONSUMERSTATUS LIKE 'R' AND MM.READINGDATE IS NULL";
				 
				 totaNoi=(BigDecimal) postgresMdas.createNativeQuery(totalNoi).getSingleResult();
				 totalEntered= (BigDecimal) postgresMdas.createNativeQuery(totalentered).getSingleResult();
				 totalPending= (BigDecimal) postgresMdas.createNativeQuery(totalpending).getSingleResult();
				 model.put("totaNoi", totaNoi);
				 model.put("totalEntered", totalEntered);
				 model.put("totalPending", totalPending);
				 model.put("totalDaySumList", totalDaySumList);
				 model.put("results", "notDisplay");
			 }
			 else
			 {
				 model.put("results", "No Record Available.");
			 }
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getAllPendingList(String month,HttpServletRequest request, ModelMap model)
	{
		String sql="";
		List<Object[]> list=null;
		try 
		{
			sql="SELECT MA.MRNAME, MA.SDOCODE,MA.ACCNO,MM.METRNO FROM MASTER MA,METERMASTER MM WHERE MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+month+"' AND MM.READINGDATE IS NULL AND MA.CONSUMERSTATUS LIKE 'R' AND MM.MCST LIKE 'R'  ORDER BY MA.MRNAME, MA.SDOCODE,MA.ACCNO,MM.METRNO";
			System.out.println("==getAllPendingList sql===>"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getPendingList(String mrname,String month,HttpServletRequest request, ModelMap model)
	{
		String sql="";
		List<Object[]> list=null;
		try 
		{
			sql="SELECT MA.MRNAME, MA.SDOCODE,MA.ACCNO,MM.METRNO FROM MASTER MA,METERMASTER MM WHERE MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+month+"' AND  UPPER(MA.MRNAME) LIKE '"+mrname.toUpperCase()+"' AND MM.READINGDATE IS NULL AND MA.CONSUMERSTATUS LIKE 'R' AND MM.MCST LIKE 'R' ORDER BY MA.MRNAME, MA.SDOCODE,MA.ACCNO,MM.METRNO";
			System.out.println("==getPendingList sql===>"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	HashSet<String> setValue=new HashSet<String>();
	
	@Override
	public String meterNoUpload(UploadedFile meterNoUpload,String billmonth, ModelMap model,HttpServletRequest request)
	{
		MultipartFile multipartFile=meterNoUpload.getFile();
		String fileName="";
		if(multipartFile!=null)
        {
  			fileName = multipartFile.getOriginalFilename();
  			try 
  			{
  				InputStream stream = multipartFile.getInputStream();
  				XSSFWorkbook workbook = new XSSFWorkbook(stream); 
  				XSSFSheet sheet = workbook.getSheetAt(0); 
  				int noOfSheets=workbook.getNumberOfSheets();
  			    int notRead=0;
  				int alreadyUploaded=0;
  				int uploaded=0;
  				String SheetNameNotUpdate="";
  				String SheetNameAlreadyUpdated="";
  				int sheetUpdated=0;
  				String SheetName="";
  				int record=0;
  				int lastRows=0;
  				int lastColumn=0;
  				int  notInserted=0;
  				String cluster="",clientid="";
  				int len=0;
  				String notreadColumnsDetails ="";
  				
  				for(int i=0;i<noOfSheets;i++)
  				{
  					if(i==0)
  					{
  					
  						try 
  						{
  								SheetName=workbook.getSheetName(i);	  	
  								lastRows=workbook.getSheetAt(i).getLastRowNum();
  								
  								if(lastRows==0)
  								{
  									model.put("results", "Records are not avaliable in excel to upload");
  									return "error";
  								}
  								String[] meterNo=new String[lastRows];
  								
  								if(workbook.getSheetAt(i).getRow(0)!=null )
  								{
	  								lastColumn=workbook.getSheetAt(i).getRow(0).getLastCellNum();
	  								
	  								
	  								int cell_type1=0;
	  								String cellGotValue="";
	  								XSSFCell cell_1=null;	  										
	  										
	  								int          meterNoCol=0;
  								for (int j=0;j<=lastRows;j++)
  								{  
  									if(j==0)// To get Column Names First row in Excel
  									{
  										for (int k=j;k<lastColumn;k++)
		  								{	  								         	
  											XSSFCell cellNull= workbook.getSheetAt(i).getRow(j).getCell(k);
  											if(cellNull!=null)
	  										{
  												cellGotValue=cellNull.getStringCellValue();											
  												cellGotValue=cellGotValue.trim();
	  										}
		  										int check=0;									
		  										
		  										if(cellGotValue.equalsIgnoreCase("METERNO"))
		  										{
		  											meterNoCol=k;	  	
		  											check++;
		  										}
		  							     }
  									}
  									else
  									{
  										
  									 for (int k=0;k<lastColumn;k++)
			  						 {
  										
  										    XSSFCell cellNull= workbook.getSheetAt(i).getRow(j).getCell(k);
													
			  									if(cellNull!=null)
			  										{	
			  										    
				  										if(cellNull.getCellType()==XSSFCell.CELL_TYPE_STRING)
			  						                    {
			  												cellGotValue=cellNull.getStringCellValue();
			  												cellGotValue=cellGotValue.trim();	  
			  											}
			  										}
			  											
				  									if(k==meterNoCol)
			  										{
				  										meterNo[j-1]=cellGotValue.trim();
			  										}
				  									setValue.add(meterNo[j-1].trim());
			  						 }
  							}

  								}
  						}
  					}
  						catch (Exception e) 
  						{
  							if(lastRows!=0)
  							{
  								model.put("results", "Error while uploading the file");
  								notRead++;
  								SheetNameNotUpdate=SheetNameNotUpdate+" "+SheetName;	
  							}
  								e.printStackTrace();
  							continue;
  						}
  						String returnValue=updateElements(setValue,billmonth);
  						model.put("results", returnValue);
  			} 
  				}
  		}
  			catch (IOException e) 
  			{
  				e.printStackTrace();
  			}
  			catch (Exception e) 
  			{
  				e.printStackTrace();
  			} 
        }
		return "Success";
	}
	
	public String updateElements(HashSet<String> setValue,String billmonth)
	{
		   HashSet<String> backUpSet=setValue;
		    int count=1;
			String meterNoList="(";
			
			for (Iterator iterator = setValue.iterator(); iterator .hasNext();)
			{
				String value = (String) iterator.next();
				if(count<=999)
				{
					meterNoList=meterNoList+"'"+value+"' ,";
					iterator.remove();
					count++;
				}
		    }
				if(meterNoList.endsWith(","))
				{
					meterNoList = meterNoList.substring(0,meterNoList.length() - 1);
				}
				 meterNoList=meterNoList+""+")";
				 String query="Update METERMASTER SET RTC='1' WHERE TRIM(METERNO) IN "+meterNoList+" AND RDNGMONTH='"+billmonth+"'";
		         int updateValue;
				try 
				{
					updateValue = postgresMdas.createNativeQuery(query).executeUpdate();
			        	 if(setValue.size()>0)
						  {
							  updateElements(setValue,billmonth);
						  } 
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return "Error While Updating";
				}
		         
		        	 
				
			return "Rtc Updated Successfully";
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void searchByMtrNo(HttpServletRequest request,MeterMaster meterMaster, ModelMap model) 
	{
		System.out.println("--inside searchByMtrNo-----");
		List<MeterMaster> list = null;
		String mrname = "";
		String sql = "";
		try {
			System.out.println("mtrNo--" +meterMaster.getMetrno()+"rdgMonth--" +meterMaster.getRdngmonth());
			list = postgresMdas.createNamedQuery("MeterMaster.searchByMtrNo").setParameter("mtrNo", meterMaster.getMetrno()).setParameter("rdgMonth", meterMaster.getRdngmonth()).getResultList();
			//mrname = (String)postgresMdas.createNamedQuery("Master.FindMrNameByMtrNo").setParameter("accno", meterMaster.getAccno()).getSingleResult();
			sql="SELECT DISTINCT m.MRNAME FROM MASTER m,METERMASTER mm WHERE m.ACCNO=MM.ACCNO and mm.METRNO='"+meterMaster.getMetrno()+"'";
			System.out.println("------mrname--sql-- : "+sql+"==>"+meterMaster.getMetrno());
			mrname = (String)postgresMdas.createNativeQuery(sql).getSingleResult();
			System.out.println("------mrname--=>"+mrname);
			System.out.println("list.size()"+list.size());
			if (list.size() == 0) 
			{
				model.put("result", "Data not found for entered METER number");
				System.out.println("inside accnoService Impl list==0");
				int rdgMonth = meterMaster.getRdngmonth();
				meterMaster = new MeterMaster();
				meterMaster.setRdngmonth(rdgMonth);
				model.put("mnpValue", "noVal");
				model.put("newConnectionMeterMaster", meterMaster);
			}
			if (list.size() > 0) {
				model.put("mnpValue", list.get(0).getMaster().getMnp());
				if(mrname!=null)
					  list.get(0).setMrname(mrname);
				model.put("newConnectionMeterMaster", list.get(0));
				System.out.println(" hoi " + list.get(0).getAccno() + "   "
						+ list.get(0).getMaster());
			}
		} catch (Exception e) 
		{

			e.printStackTrace();
		}
		model.put("mrNames", mrnameService.findAll());
	}
	
	

	@Override
	public List<?> getExportData(String mrNameEx, String sdoCodeEx,String tadescEx)
			 {
		List list=null;
			String query = "";
			try{
				query = "SELECT a.rdngmonth,b.tadesc,b.subdiv,a.accno,a.metrno ,\n" +
						"b.name,b.address1,b.consumerstatus as cst,a.mcst,a.CURRDNGKWH,\n" +
						"a.CURRRDNGKVAH,a.CURRDNGKVA,a.PF,a.readingremark,a.remark,a.xmldate,a.XCURRDNGKWH ,  \n" +
						"  a.XCURRRDNGKVAH, a.XCURRDNGKVA,a.XPF,a.UNITSKWH,a.UNITSKVAH, a.UNITSKVA,a.MRDSTATUS,\n" +
						"a.MTRMAKE,a.MRNAME,a.DNAME,a.READINGDATE,a.OLDSEAL,a.NEWSEAL,b.tariffcode,b.kworhp,b.sanload,\n" +
						"a.prevmeterstatus,a.RTC,a.MTRTYPE,a.PRKWH,a.PRKVAH,a.PRKVA,b.CONTRACTDEMAND  as CD,a.ctrn,\n" +
						"a.ctrd,a.MF,b.mrname as mmrname,a.mrino,b.tn,b.SUPPLYVOLTAGE as svoltage,b.mrcode,a.mrd,\n" +
						"a.username,b.avgsix,b.oldaccno,b.mnp,b.INDUSTRYTYPE,b.PHONENO,a.DEMANDTYPE,b.kno,a.mm,sdoname \n" +
						"from meter_data.metermaster a,meter_data.master b  WHERE b.MRNAME like '"+mrNameEx+"' AND a.ACCNO=b.ACCNO AND b.subdiv like '"+sdoCodeEx+"'";
				System.err.println(query);
				list= getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
			}
			catch(Exception e)
			{
				return null;
			}
		System.out.println("--------the list Size is : "+list.size());
		return list;
	}

	
	
	
	@Override
	public int delByAccNo(String accno) {
		System.out.println("accno-------- "+accno);
		int id5=0;
		try {
			String qry="DELETE FROM METERMASTER WHERE   accno='"+accno+"' and RDNGMONTH='201808'";
			System.out.println(">>>>>>>>>>>>>>>>>>>"+qry);
			
			int id4=postgresMdas.createNativeQuery(qry).executeUpdate();
			System.out.println("id4--->"+id4);
			if(id4>0)
			{
				String qry2="DELETE FROM MASTER WHERE  accno='"+accno+"'";
				id5=postgresMdas.createNativeQuery(qry2).executeUpdate();
			}
			return id5;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id5;
	
	}


	
	//Added By Vijayalaxmi
	@Override
	public List<?> getMrWiseReprotForReportReading(MeterMaster meterMaster,HttpServletRequest request, ModelMap model, String circle) {
		 List<Object[]> searchDetailsOne1=null;
			try
			{

				Query query=getCustomEntityManager("postgresMdas").createNativeQuery("select a.mrname,COALESCE(count(*),0),a.subdiv from meter_data.master a , meter_data.metermaster b where  a.consumerstatus like 'R'   and    CAST(b.rdngmonth as INTEGER) = ? and a.circle like ? and a.accno=b.accno group by a.mrname,a.subdiv order by a.mrname ");



			//	Query query=postgresMdas.createNativeQuery("select a.mrname,nvl(count(*),0),a.sdoname from master a , metermaster b where  a.consumerstatus like 'R'   and    b. rdngmonth like ? and a.circle like ?    and a.accno=b.accno group by a.mrname,a.sdoname order by a.mrname ");

			    

				query.setParameter(1,meterMaster.getRdngmonth()); 
			    query.setParameter(2,circle); 
			    System.out.println(query);
			    searchDetailsOne1 = query.getResultList();
			    
			    System.out.println("Reading Report query======"+searchDetailsOne1.size());
			
			   
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return searchDetailsOne1;
	}


	@Override
	public List<Object[]> getRNAdata(String billmonth, String circle) 
	{
		String qry="SELECT m.CIRCLE,mm.SDOCODE,m.subdiv,MM.MRNAME,COUNT(*),RDNGMONTH FROM meter_data.METERMASTER mm,meter_data.MASTER m WHERE MM.RDNGMONTH='"+billmonth+"' AND MM.RTC='0' AND m.CIRCLE LIKE '"+circle+"'\n" +
				"AND MM.READINGREMARK  not in ('OK','0','CE','CNP','COUNTER METER','IR PORT','METER CHANGE','METER ROUND','OTHER','PUSH BUTTON PROBLEM')\n" +
				"AND m.CONSUMERSTATUS LIKE 'R' AND m.subdiv=MM.subdiv GROUP BY m.CIRCLE,mm.SDOCODE,m.subdiv,mm.MRNAME,RDNGMONTH ORDER BY m.CIRCLE,mm.SDOCODE,m.subdiv,MM.MRNAME";
		return postgresMdas.createNativeQuery(qry).getResultList();
		
	}

	
	@Override
	public Object getGrandTotalForReadingReport(MeterMaster meterMaster,HttpServletRequest request, ModelMap model, String circle) 
	{
		 List<Object[]> grandTotal=null;
			try
			{
				Query query=postgresMdas.createNativeQuery("SELECT sum(B.total) from(select a.mrname,count(*) as total,a.sdoname from meter_data.master a , meter_data.metermaster b where  a.consumerstatus like 'R'   and    CAST(b.rdngmonth as INTEGER) = ? and a.circle like ? and b.mcst like 'R'   and a.accno=b.accno group by a.mrname,a.sdoname order by a.mrname )B");
			    
				query.setParameter(1,meterMaster.getRdngmonth()); 
			    query.setParameter(2,circle); 
			    System.out.println(query);
			    grandTotal = query.getResultList();
			    
			    System.out.println("grandTotal.size======"+grandTotal);
			
			   
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return grandTotal;
	}



	@Override
	public Object getrnaReportBasedonMr(String circle, String billmonth,String sdocode, String sdoname, String mrname, ModelMap model,HttpServletRequest request,HttpServletResponse response) 
	{
		String qry="SELECT m.CIRCLE,mm.SDOCODE,m.subdiv,m.TADESC,MM.ACCNO,MM.METRNO,MM.READINGREMARK,MM.MRNAME,MM.UNITSKWH,MM.UNITSKVAH,MM.READINGDATE,\n" +
				"m.NAME,m.ADDRESS1 FROM meter_data.METERMASTER mm,meter_data.MASTER m WHERE MM.RDNGMONTH='"+billmonth+"' AND MM.RTC='0' AND m.CIRCLE LIKE '"+circle+"'\n" +
				"AND MM.READINGREMARK  not in ('OK','0','CE','CNP','COUNTER METER','IR PORT','METER CHANGE','METER ROUND','OTHER','PUSH BUTTON PROBLEM')\n" +
				"AND m.CONSUMERSTATUS LIKE 'R' AND MM.MRNAME LIKE '"+mrname+"' AND m.subdiv=MM.subdiv  AND M.subdiv LIKE '"+sdoname+"'";
		System.out.println(qry);
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public List<?> getMrWiseReprotOnebySdoname(MeterMaster meterMaster,HttpServletRequest request, ModelMap model, String circle,String mrname, String sdoname)
	{
		 List<?> searchDetailsOne=new ArrayList<>();
		 
		
		try
		{
			Query query=postgresMdas.createNativeQuery("select a.mrname,to_char(b.readingdate ,'dd-MM-yyyy'),count(*),a.sdoname from meter_data.master a , meter_data.metermaster b where  a.consumerstatus like 'R'   and    CAST(b.rdngmonth as INTEGER) = ? and a.circle like ? and a.mrname like ? and  a.sdoname like ? and a.accno=b.accno and b.readingdate IS NOT NULL group by a.mrname,b.readingdate,a.sdoname order by a.mrname ");
		    query.setParameter(1,meterMaster.getRdngmonth()); 
		    query.setParameter(2,circle);  
		    query.setParameter(3,mrname);  
		    query.setParameter(4,sdoname);  
		    searchDetailsOne = query.getResultList();
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
            return searchDetailsOne;
	
	}

	
	// Added By Vijayalaxmi
	@Override
	public int updateHtMualAndRRemark(String billMonth, String rdngRemark,String accNo,double currdngkwh,double currdngkvah,double currdngkva)
	{
		int result=0;
		try {
			String qry="Update mdm_test.METERMASTER SET READINGREMARK='"+rdngRemark+"', HTMANUAL = 1,CURRDNGKWH='"+currdngkwh+"',CURRRDNGKVAH='"+currdngkvah+"',CURRDNGKVA='"+currdngkva+"'  WHERE ACCNO LIKE '"+accNo+"' AND RDNGMONTH = '"+billMonth+"' ";
			 System.out.println("HT Manual Update query======"+qry);
			result =postgresMdas.createNativeQuery(qry).executeUpdate();
		    } catch (Exception e) 
			{
			   e.printStackTrace();
			}
		return result; 
	}

	
	@Override
	public List<?> gethtReadingDetails(String rdngMonth, String circle1) 
	{
		List<?> htData=null;
		try {
			String qry="select rdngmonth,circle,sdoname,tadesc,accno,"
					+ "(case when KNO is null then accno else kno END ) as KNO,"
					+ "metrno,round(XCURRDNGKWH,2) as KWH,round(XCURRRDNGKVAH,2) as KVAH,"
					+ "round(XCURRDNGKVA,2) as KVA,name  from mm where rdngmonth='"+rdngMonth+"' and "
					+ "XCURRDNGKWH is not null and length(accno)='12' "
					+ "and circle like '"+circle1+"' ORDER BY ACCNO";
			System.out.println("htdata qry--"+qry);
			htData=postgresMdas.createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return htData;
	}

	
	@Override
	public List<?> gethtReadingDetailsForExcel(String rdngMonth, String circle) {
		List<?> htListData=null;
		try {
			String qry="select CIRCLE,division,subdiv,accno,consumername,address,category,metrno,RDATE, \n" +
					"XCURRDNGKWH,XCURRRDNGKVAH ,XCURRDNGKVA , KW , KWHE,KVHE,KVAE,T1KWH  ,T2KWH,  T3KWH,  \n" +
					"T4KWH,  T5KWH  ,T6KWH  ,T7KWH,  T8KWH , T1KVAH  ,T2KVAH , T3KVAH , T4KVAH  ,T5KVAH,  \n" +
					"T6KVAH,  T7KVAH  ,T8KVAH  ,T1KVAV  ,T2KVAV,  T3KVAV , T4KVAV,  T5KVAV,  T6KVAV,\n" +
					"T7KVAV ,T8KVAV,GROUP_VALUE,BILLING_CATEGORY,READINGREMARK,REMARK  \n" +
					"from  meter_data.metermaster where   rtc>=0  and rdngmonth="+rdngMonth+"  AND CIRCLE LIKE '"+circle+"' \n" +
					"ORDER BY circle ASC";
			System.out.println("ht excel qry==>"+qry);
			htListData=postgresMdas.createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return htListData;
	}




	@Override
	public int getAMRORCMR(int rdngmonth,String meterno)
	{
		
		String qry="";
		List result=null;
		int amr=0;
		try {
			qry="select AMR from meter_data.BATCHSTATUS where rdngmonth='"+rdngmonth+"' and METERNO='"+meterno+"' and XMLIMPORT='1'";
			System.out.println("qryyyyy---->"+qry);
			
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			
			 int acount=0;
			 int ccount=0;
		for(Iterator<?> iterator1=  result.iterator(); iterator1.hasNext();)
 		{
		 java.math.BigDecimal obj= (BigDecimal) iterator1.next();
		 int temp=obj.intValue();
		 System.out.println("acount-->"+acount+"ccount-->"+ccount);
		 if(temp==1)
		 {
			amr=temp; 
			acount++;
		 }
		 if(temp==0)
		 {
			 ccount++;
		 }
		
 		}
		 System.out.println("acount-->"+acount+"ccount-->"+ccount);
		if(acount>0 && ccount>0)
		{
			amr=5;
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amr;
	}

	
	@Override
	public String getmeterno(int rdngmonth,String accno)
	{
		System.out.println("geting meterno-->"+rdngmonth+"--"+accno);
		String metrno="";
		try {
			String qry="SELECT metrno FROM meter_data.METERMASTER WHERE RDNGMONTH='"+rdngmonth+"' AND accno='"+accno+"'";
			metrno=(String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
			System.out.println(qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return metrno;
		
	}
	
	//circle wise report

	@Override
	public List<?> getCirWiseReport(int rdngMonth) {
		List<?> cirResult=null;
		System.out.println("calling circle data");
		try {
			String qry="select circle,count(*) as Read_By_Btop from meter_data.mm where rdngmonth='"+rdngMonth+"' and cst like 'R' and metrno in (select meterno from meter_data.batchstatus where rdngmonth='"+rdngMonth+"' and amr='1') group by circle order by circle";
			cirResult=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println(qry);
			System.out.println("cirResult size-->"+cirResult.size());
	} catch (Exception e) {
		e.printStackTrace();
	}
		return cirResult;
	} 
	
	
	@Override
	public List getCircleWiseMeters(String circle,String mrname,int rdngmonth) {
		System.out.println("circle======================"+circle+" mrname-->"+mrname);
		List billedDetails=null;
		try
		{
   String qry="SELECT  SDONAME,ACCNO,TADESC,METRNO,NAME,MMRNAME FROM MM WHERE circle='"+circle+"' AND MMRNAME='"+mrname+"' AND RDNGMONTH='"+rdngmonth+"' AND METRNO is NOT NULL"
				+ " AND METRNO IN(select meterno from batchstatus where rdngmonth='"+rdngmonth+"' and amr='1') ";
			billedDetails=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("Size of deyails ===="+billedDetails.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return billedDetails; 
	}

	@Override
	public List getCircleWiseSecondBtopData(String circle, int rdngmonth) {
		List secondBtop=null;
		try
		{
			String qry="SELECT m.circle,m.mmrname,count(*) FROM MM m WHERE circle='"+circle+"' AND RDNGMONTH='"+rdngmonth+"' AND METRNO is NOT NULL AND cst like 'R' "
					+ " AND METRNO IN(select meterno from batchstatus where rdngmonth='"+rdngmonth+"' and amr='1')group by m.circle,m.mmrname ";
			secondBtop=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("2ND QUERY-->"+qry);
			System.out.println("Size of secondBtop ===="+secondBtop.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return secondBtop;
	}
	
	public  List<MeterMaster> findaccnomonthDataKno(MeterMaster meterMaster,ModelMap model)
	{
		 System.out.println("Kno in MeterMasterSerImpl============== "+meterMaster.getkno());
			List<MeterMaster> l=null;
			//l = postgresMdas.createNamedQuery("MeterMaster.getDatabaseMeterMasterForKno").setParameter("kno", meterMaster.getkno()).getResultList();
		
			 List<MeterMaster> mtrnum=l;
			 
		
			 
			 String qry="select * from mdm_test.metermaster m WHERE accno='"+meterMaster.getAccno()+"' and rdngmonth <='"+meterMaster.getRdngmonth()+"' ORDER BY  rdngmonth DESC";

			    List<?> mtrnum1 =postgresMdas.createNativeQuery(qry).getResultList();
			    		
			    for (Iterator iterator1 = mtrnum1.iterator(); iterator1.hasNext();)
			    {
			    	Object[] obj = (Object[]) iterator1.next();
				
			   
					 
					 if (mtrnum1.size() > 0) 
					 {
						System.out.println("---------"+mtrnum.get(0));
						 model.put("metrno",obj[2] );
						 model.put("data", obj[20]);
						 model.put("mtrmake",obj[48]);
						 model.put("mtrtype",obj[33]);
						 model.put("newseal",obj[23]);
						
					} 
					 else {
							model.put("msg", "No data found on this account ");
							return null;
						}
					 
					 }
			    return l;
	}
//Added By Vijayalaxmi
	@Override
	public List getdayWiseMrDetailsForUserRpt(String mrname, String sdoname,String readingdate, String circle, String rdng,
			HttpServletRequest request, ModelMap model) {
		List<?> days=null;
		try {
			String qry="SELECT a.SDONAME,a.TADESC,a.accno,b.metrno,a.name,a.address1,a.mrname, to_char(b.readingdate ,'dd-MM-yyyy') as readingDate,a.PHONENO "
					+ " from master a , metermaster b where  a.consumerstatus like 'R'   and    b. rdngmonth like '"+rdng+"' and "
					+ " a.circle like '"+circle+"' and a.mrname like '"+mrname+"' and  a.sdoname like '"+sdoname+"' and a.accno=b.accno"
					+ " and b.readingdate IS NOT NULL and b.readingdate=to_date('"+readingdate+"','dd-MM-yyyy') order by a.mrname ";
			days=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}
	
	
	
	/*------------------------------  Below Table query (Vijju)--------------------------------------*/
	
	
	@SuppressWarnings("unchecked")
	public List<MeterMaster> getBillingDataMM(String billMonth,String meterno,HttpServletRequest request,ModelMap model)
	{
		List<MeterMaster> list= postgresMdas.createNamedQuery("MeterMaster.FindAllData1").setParameter("metrno", meterno).setParameter("rdngmonth", Integer.parseInt(billMonth)).getResultList();
		
		model.put("billingData", list);	
		model.put("portletTitle", "Billing Details");
		model.put("meterNo", meterno);
		model.put("selectedMonth", billMonth);
		/*if(list.size()==0)
		{
			model.put("msg", "No data found for entered meter number and selected yearMonth");
		}*/
		String query="";
		//List BPdata=null;
		// its like Export To PDF Query
		
	/*query="SELECT BB.*,BB.kwh-BB.pre_month_kwh AS consumption,BB.kwhe-BB.pkwhe as Export_Units  from  "
			+ " (select AA.*,(select KWH from mdm_test.XMLIMPORT where month=to_CHAR(add_months(to_date(AA.month,'YYYYMM'),-1),'YYYYMM') "
			+ " and meterno=AA.METERNO) AS pre_month_kwh ,(select KWHE from mdm_test.XMLIMPORT where "
			+ " month=to_CHAR(add_months(to_date(AA.month,'YYYYMM'),-1),'YYYYMM') and meterno=AA.METERNO)  AS pkwhe from  "
			+ " (SELECT MONTH,METERNO,KVAOCCDATE, COALESCE(KWH,0) as kwh,COALESCE(KVH,0) as kvah, COALESCE(KVA,0) as kva,"
			+ " COALESCE(KWH,0) as CURRDNGKWH,kwhe,kvhe,kvae,pfe,pf FROM mdm_test.XMLIMPORT  WHERE  METERNO='"+meterno+"' "
			+ " AND MONTH BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE('"+billMonth+"','YYYYMM'),-5),'YYYYMM') AND '"+billMonth+"' ORDER BY MONTH DESC )AA)BB ";
	        */
		
		query="SELECT BB.*,BB.kwh-BB.pre_month_kwh AS consumption,BB.kwhe-BB.pkwhe as Export_Units  from  "
				+ " (select AA.*,(select KWH from mdm_test.XMLIMPORT where month=AA.MONTH-1 "
				+ " and meterno=AA.METERNO) AS pre_month_kwh ,(select KWHE from mdm_test.XMLIMPORT where "
				+ " month=AA.MONTH-1 and meterno=AA.METERNO)  AS pkwhe from  "
				+ " (SELECT MONTH,METERNO,KVAOCCDATE, COALESCE(KWH,0) as kwh,COALESCE(KVH,0) as kvah, COALESCE(KVA,0) as kva,"
				+ " COALESCE(KWH,0) as CURRDNGKWH,kwhe,kvhe,kvae,pfe,pf FROM mdm_test.XMLIMPORT  WHERE  METERNO='"+meterno+"' "
				+ " AND MONTH BETWEEN to_number('"+billMonth+"', '999999')-5 "
				+"	AND to_number('"+billMonth+"','999999') ORDER BY MONTH DESC )AA)BB ";
		
		// Preivious below query
	/*	
		query="SELECT BB.*,BB.KWH-BB.PRE_MONTH_KWH AS CONSUMPTION\n" +
				"FROM\n" +
				"( SELECT AA.*,(SELECT KWH FROM BP\n" +
				" WHERE MONTH=TO_CHAR(ADD_MONTHS(TO_DATE(AA.MONTH,'YYYYMM'),-1),'YYYYMM')\n" +
				"AND METERNO=AA.METERNO) AS PRE_MONTH_KWH FROM\n" +
				"( SELECT MONTH,CIRCLE,DIVISION,SDONAME,\n" +
				" ACCNO,METERNO,MF,CD, COALESCE(KWH,0) as kwh,COALESCE(KVH,0) as kvah,\n" +
				"COALESCE(KVA,0) as kva, COALESCE(KWH,0) as CURRDNGKWH FROM BP\n" +
				"WHERE  METERNO='"+meterno+"' AND MONTH BETWEEN\n" +
				" TO_CHAR(ADD_MONTHS(TO_DATE('"+billMonth+"','YYYYMM'),-5),'YYYYMM') AND '"+billMonth+"' ORDER BY MONTH DESC )AA )BB";
		*/
		
		
		//BPdata=postgresMdas.createNativeQuery(query).getResultList();
		
		//System.out.println("query from BP result size===>"+BPdata.size());
		
		
//System.err.println("query--=-=-=-==-=-=->"+query);		
		String[] consuptionArr=null;
	    String[] monthArr=null;
		MDMLogger.logger.info("==============="+query);
		List<Object[]> data=postgresMdas.createNativeQuery(query).getResultList();
		if(data.size()>0)
		{
			System.out.println("data->"+data.size());
			System.err.println("data.get(0).length-->"+data.get(0).length);
			model.put("dataArrLength", data.get(0).length);
			model.put("billedDataList", data);
			model.put("viewCategory", "BilledData");
			consuptionArr=new String[data.size()-1];
		    monthArr=new String[data.size()-1];
		    for (int i = 0; i < data.size(); i++)
			{
				Object[] value=data.get(i);
			//	System.out.println("value--length->"+value.length);
				for (int j = 0; j < value.length; j++)
				{
					if(j==14)
					{
						if(i<data.size()-1)
						{
								consuptionArr[i]=value[j]+"";
						}
					}
					if(j==0)
					{
						if(i<data.size()-1)
						{
							try {
								String value1=value[j]+"";
	                			String value11=new SimpleDateFormat("MMM-YYYY").format(new SimpleDateFormat("yyyyMM").parse(value1));
								monthArr[i]=value11;
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		    request.setAttribute("monthArr", monthArr);
			request.setAttribute("consuptionArr", consuptionArr);
		}
		else if(data.size()==0){
			model.put("msg", "No data found for entered meter number and selected yearMonth");
		}
		
	    /*consuptionArr=new String[data.size()-1];
	    monthArr=new String[data.size()-1];*/
		
		
		return list;
	}
	
	
	
	/*------------------------Export TO PDF 360---------------------------*/
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public void downloadBilledDataPdf(String meterno, String month,ModelMap model, HttpServletResponse response)
	{	
		String circle="",division="",subDivision="",consumerName="",address="",cd="",sanLoad="",msf="",kno="",accno="";
		List<Object[]> data=null;
		List<Object[]> masterData=null;
		List<Object[]> dataHeader=null;
		List<D2Data> d2data=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try 
		{
			String sql="SELECT MA.CIRCLE,MA.DIVISION,MA.SDONAME,MA.NAME,MA.ADDRESS1,MM.METRNO, MA.CONTRACTDEMAND||'',MA.SANLOAD||'',MM.MF||'' ,MM.KNO, MA.ACCNO FROM BSMARTMDM.MASTER MA,BSMARTMDM.METERMASTER MM WHERE MA.ACCNO=MM.ACCNO"
					+ " AND MM.RDNGMONTH='"+month+"' AND MM.METRNO like '"+meterno+"'";
			
			System.out.println("inside downloadBilledDataPdf==>"+sql);
			masterData=postgresMdas.createNativeQuery(sql).getResultList();
			
			
			if(masterData.size()>0)
			{
			circle=(String) masterData.get(0)[0];
			division=(String) masterData.get(0)[1];
			subDivision=(String) masterData.get(0)[2];
			consumerName=(String) masterData.get(0)[3];
			address=(String) masterData.get(0)[4];
			cd=(String) masterData.get(0)[6];
			sanLoad=(String) masterData.get(0)[7];
			msf=(String) masterData.get(0)[8];
			kno=(String) masterData.get(0)[9];
			accno=(String) masterData.get(0)[10];
			}
			else{
				circle="";
				division="";
				subDivision="";
				consumerName="";
				address="";
				cd="";
				sanLoad="";
				msf="";
				kno="";
				accno="";
			}
					
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			
		        Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		        Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		        PdfPTable pdf1 = new PdfPTable(1);
		        pdf1.setWidthPercentage(100); // percentage
		        pdf1.getDefaultCell().setPadding(3);
		        pdf1.getDefaultCell().setBorderWidth(0);
		        pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		      
		        PdfPTable pdf2 = new PdfPTable(1);
		        pdf2.setWidthPercentage(100); // percentage
		        pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		        Chunk glue = new Chunk(new VerticalPositionMark());
		        PdfPCell cell1 = new PdfPCell();
		        Paragraph pstart = new Paragraph();
		        pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		        cell1.setBorder(Rectangle.NO_BORDER);
		        cell1.addElement(pstart);
		        pdf2.addCell(cell1);
		        pstart.add(new Chunk(glue));
		        pstart.add(new Phrase("Reading Month : "+new SimpleDateFormat("MMM-YYYY").format(new SimpleDateFormat("yyyyMM").parse(month)),new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		        
		        document.add(pdf2);
		        PdfPCell cell2 = new PdfPCell();
		        Paragraph p1 = new Paragraph();
		        p1.add(new Phrase("BILLING PARAMETERS : "+meterno,new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		        p1.setAlignment(Element.ALIGN_CENTER);
		        cell2.addElement(p1);
		        cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		        pdf1.addCell(cell2);
		        document.add(pdf1);
		        
		        PdfPTable header = new PdfPTable(6);
	             header.setWidthPercentage(100);
	            /* header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));*/

	             PdfPCell headerCell=null;
	             headerCell = new PdfPCell(new Phrase("Circle :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));
	             	 
	             headerCell = new PdfPCell(new Phrase("ACCNO :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	           //header.addCell(getCell("Division :", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(accno, PdfPCell.ALIGN_LEFT));
	             
	             headerCell = new PdfPCell(new Phrase("Consumer Name :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             header.addCell(getCell(consumerName, PdfPCell.ALIGN_LEFT));
	             
	            
	             
	             headerCell = new PdfPCell(new Phrase("KNO :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             //header.addCell(getCell("Address :", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(kno, PdfPCell.ALIGN_LEFT));
	             
	            headerCell = new PdfPCell(new Phrase("Division :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	           //header.addCell(getCell("Division :", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(division, PdfPCell.ALIGN_LEFT));
	             
	             headerCell = new PdfPCell(new Phrase("Address :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             //header.addCell(getCell("Address :", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(address, PdfPCell.ALIGN_LEFT));
	             
	             headerCell = new PdfPCell(new Phrase("Sub-Division :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             //header.addCell(getCell("Sub-Division :", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(subDivision, PdfPCell.ALIGN_LEFT));
	             	
	             headerCell = new PdfPCell(new Phrase("Meter No :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             //header.addCell(getCell("Meter No :", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(meterno, PdfPCell.ALIGN_LEFT));
	             
	             headerCell = new PdfPCell(new Phrase("CD :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             //header.addCell(getCell("CD :", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(cd, PdfPCell.ALIGN_LEFT));
	             
	             headerCell = new PdfPCell(new Phrase("MF :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             //header.addCell(getCell("MF :", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(msf, PdfPCell.ALIGN_LEFT));
	             
	            
	             
	             
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	            header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	            header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	            header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             document.add(header);
	             String query="";
	             List<Object[]> billedData=null;
		        /*String query="SELECT BB.*,BB.CURRDNGKWH-BB.PRE_MONTH_KWH AS CONSUMPTION FROM"+" "
		        		+"("+" "
		        		+"SELECT AA.*,(SELECT CURRDNGKWH FROM METERMASTER WHERE RDNGMONTH=TO_CHAR(ADD_MONTHS(TO_DATE(AA.RDNGMONTH,'YYYYMM'),-1),'YYYYMM') AND ACCNO=AA.ACCNO)"+" "
		        		+"AS PRE_MONTH_KWH FROM"+" "
		        		+"("+" "
		        		+"SELECT MM.RDNGMONTH,MA.CIRCLE,MA.DIVISION,MA.SDONAME, MM.ACCNO,MM.METRNO,MM.MF,MA.CONTRACTDEMAND, MM.XCURRDNGKWH,MM.XCURRRDNGKVAH,MM.XCURRDNGKVA,MM.CURRDNGKWH"+" "
		        		+"FROM BSMARTMDM.MASTER MA,BSMARTMDM.METERMASTER MM WHERE MA.ACCNO=MM.ACCNO AND MM.METRNO='"+meterno+"' AND MM.RDNGMONTH BETWEEN "+" "
		        		+"TO_CHAR(ADD_MONTHS(TO_DATE('"+month+"','YYYYMM'),-5),'YYYYMM') AND '"+month+"' ORDER BY RDNGMONTH DESC"+" "
		        		+")AA"+" "
		        		+")BB";*/
	             
	            /* query="SELECT BB.*,BB.KWH-BB.PRE_MONTH_KWH AS CONSUMPTION\n" +
	            		 "FROM\n" +
	            		 "( SELECT AA.*,(SELECT KWH FROM BP\n" +
	            		 " WHERE MONTH=TO_CHAR(ADD_MONTHS(TO_DATE(AA.MONTH,'YYYYMM'),-1),'YYYYMM')\n" +
	            		 "AND METERNO=AA.METERNO) AS PRE_MONTH_KWH FROM\n" +
	            		 "( SELECT MONTH,CIRCLE,DIVISION,SDONAME,\n" +
	            		 " ACCNO,METERNO,MF,CD, COALESCE(KWH,0) as kwh,COALESCE(KVH,0) as kvah,\n" +
	            		 "COALESCE(KVA,0) as kva, COALESCE(KWH,0) as CURRDNGKWH FROM BP\n" +
	            		 "WHERE  METERNO='"+meterno+"' AND MONTH BETWEEN\n" +
	            		 " TO_CHAR(ADD_MONTHS(TO_DATE('"+month+"','YYYYMM'),-5),'YYYYMM') AND '"+month+"' ORDER BY MONTH DESC )AA )BB";*/
	             
	             query="select BB.*,BB.kwh-BB.pre_month_kwh AS consumption,BB.kwhe-BB.pkwhe as Export_Units\n" +
	            		 "from\n" +
	            		 "(select AA.*,(select KWH from XMLIMPORT where month=to_CHAR(add_months(to_date(AA.month,'YYYYMM'),-1),'YYYYMM') and meterno=AA.METERNO) AS pre_month_kwh ,\n" +
	            		 "(select KWHE from XMLIMPORT where month=to_CHAR(add_months(to_date(AA.month,'YYYYMM'),-1),'YYYYMM') and meterno=AA.METERNO) AS pkwhe from\n" +
	            		 "(SELECT MONTH,METERNO,KVAOCCDATE, COALESCE(KWH,0) as kwh,COALESCE(KVH,0) as kvah,\n" +
	            		 "COALESCE(KVA,0) as kva, COALESCE(KWH,0) as CURRDNGKWH,kwhe,kvhe,kvae,pfe,pf FROM XMLIMPORT\n" +
	            		 "WHERE  METERNO='"+meterno+"' AND MONTH BETWEEN\n" +
	            		 " TO_CHAR(ADD_MONTHS(TO_DATE('"+month+"','YYYYMM'),-5),'YYYYMM') AND '"+month+"' ORDER BY MONTH DESC )AA)BB";
	             
		        				MDMLogger.logger.info("queryqueryqueryqueryquery"+query);
		        			 billedData=postgresMdas.createNativeQuery(query).getResultList();
		        				System.out.println("billedData-->"+billedData.size());
		        				
		        			
		        				
		        				
		        			PdfPTable parameterTable = new PdfPTable(8);
		   	                 parameterTable.setWidths(new int[]{2,2,2,2,2,2,2,2});
		   	                 parameterTable.setWidthPercentage(100);
		   		             PdfPCell parameterCell;
		   		             parameterCell = new PdfPCell(new Phrase("SR NO.",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		   		             parameterTable.addCell(parameterCell);
		   		             
		   		          parameterCell = new PdfPCell(new Phrase("READING_MONTH",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		   		             parameterTable.addCell(parameterCell);
		   		            
		   		          parameterCell = new PdfPCell(new Phrase("KWH",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		          parameterTable.addCell(parameterCell);
		   		             
		   		          parameterCell = new PdfPCell(new Phrase("KVAH",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		             parameterTable.addCell(parameterCell);
		   		             
		   		          parameterCell = new PdfPCell(new Phrase("KVA",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		             parameterTable.addCell(parameterCell);
		   		             
		   		          parameterCell = new PdfPCell(new Phrase("KVA_DATE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		   		             parameterTable.addCell(parameterCell);
		   		             
		   		          parameterCell = new PdfPCell(new Phrase("PF",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		   		             parameterTable.addCell(parameterCell);
		   		             
		   		          parameterCell = new PdfPCell(new Phrase("Consumption",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		             parameterTable.addCell(parameterCell);
		        			
		   		             // new table
		   		          PdfPTable parameterTable1 = new PdfPTable(6);
		   	                 parameterTable1.setWidths(new int[]{2,2,2,2,2,2});
		   	                 parameterTable1.setWidthPercentage(100);
		   		             PdfPCell parameterCell2;
		   		         
		   		          parameterCell2 = new PdfPCell(new Phrase("RDNG_MONTH",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		   		             parameterCell2.setFixedHeight(25f);
		   		             parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		          parameterTable1.addCell(parameterCell2);
		   		             
		   		          parameterCell2 = new PdfPCell(new Phrase("KWHE",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		   		             parameterCell2.setFixedHeight(25f);
		   		             parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		             parameterTable1.addCell(parameterCell2);
		   		             
		   		          parameterCell2 = new PdfPCell(new Phrase("KVHE",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		   		             parameterCell2.setFixedHeight(25f);
		   		             parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		             parameterTable1.addCell(parameterCell2);
		   		             
		   		          parameterCell2 = new PdfPCell(new Phrase("KVAE",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		   		             parameterCell2.setFixedHeight(25f);
		   		             parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		             parameterTable1.addCell(parameterCell2);
		   		             
		   		             
		   		             parameterCell2 = new PdfPCell(new Phrase("EXPORT UNITS",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		   		             parameterCell2.setFixedHeight(25f);
		   		             parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		   		             parameterTable1.addCell(parameterCell2);
		   		             
		   		          parameterCell2 = new PdfPCell(new Phrase("PFE",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		   		             parameterCell2.setFixedHeight(25f);
		   		             parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		             parameterTable1.addCell(parameterCell2);
		   		             
		        			
		   		             
		        				for (int i = 0; i < billedData.size(); i++) 
		    	                {
		        					parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
	   								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	   								 parameterTable.addCell(parameterCell);
	   								
	   								
	   								 
		    	                	Object[] obj=billedData.get(i);
		    	                	for (int j = 0; j < obj.length; j++) 
		    	                	{
		    	                		if(j==0)
		    	                		{
		    	                			String value1=obj[0]+"";
		    	                			String value=new SimpleDateFormat("MMM-YYYY").format(new SimpleDateFormat("yyyyMM").parse(value1));
		    								MDMLogger.logger.info("========================value>"+value+""+value1);
		    	                			parameterCell = new PdfPCell(new Phrase(value,new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								
		    								/* parameterCell = new PdfPCell(new Phrase(obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterTable.addCell(parameterCell);*/
		    								 
		    								
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj[3]==null?"":obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 parameterCell = new PdfPCell(new Phrase(obj[4]==null?"":obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 parameterCell = new PdfPCell(new Phrase(obj[5]==null?"":obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 
		    								 
		    							
		    								 
		    								 String value2=obj[2]+"";
			    	                			/*String value3=new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("dd-MM-yyyy HH:MM:SS").parse(value2));
			    								MDMLogger.logger.info("========================value>"+value2+"--"+value3);
			    	                			*/
		    								/* parameterCell = new PdfPCell(new Phrase(value2==null?"":value2+"",new Font(Font.FontFamily.HELVETICA  ,13 )));*/
			    	                			 parameterCell = new PdfPCell(new Phrase(obj[2]==null?"":obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
			    	                			 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								 
			    								 parameterCell = new PdfPCell(new Phrase(obj[11]==null?"":obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
			    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			    								 parameterCell.setFixedHeight(25f);
			    								 parameterTable.addCell(parameterCell);
			    								 
			    								 
			    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj[14]==null?"":obj[14]==null?"":obj[14]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 
		    								
		    								 
		    								 parameterCell2 = new PdfPCell(new Phrase(value,new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 System.out.println("value-->"+value);
		    								 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell2.setFixedHeight(25f);
		    								 parameterTable1.addCell(parameterCell2);
		    								 
		    								 parameterCell2 = new PdfPCell(new Phrase(obj[7]==null?"":obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell2.setFixedHeight(25f);
		    								 parameterTable1.addCell(parameterCell2);
		    								 
		    								 parameterCell2 = new PdfPCell(new Phrase(obj[8]==null?"":obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell2.setFixedHeight(25f);
		    								 parameterTable1.addCell(parameterCell2);
		    								 
		    								 parameterCell2 = new PdfPCell(new Phrase(obj[9]==null?"":obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell2.setFixedHeight(25f);
		    								 parameterTable1.addCell(parameterCell2);
		    								 
		    								 parameterCell2 = new PdfPCell(new Phrase(obj[15]==null?"":obj[15]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell2.setFixedHeight(25f);
		    								 parameterTable1.addCell(parameterCell2);
		    								 
		    								 parameterCell2 = new PdfPCell(new Phrase(obj[10]==null?"":obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
		    								 parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell2.setFixedHeight(25f);
		    								 parameterTable1.addCell(parameterCell2);
		    								 
		    	                		}
		    							
		    						}
		    					} 
		        				
		        				 
		    	               document.add(parameterTable);
		    	               
		    	               document.add(new Phrase("\n"));
				   		        LineSeparator separator = new LineSeparator();
				   		        separator.setPercentage(98);
				   		        separator.setLineColor(BaseColor.WHITE);
				   		        Chunk linebreak = new Chunk(separator);
				   		        document.add(linebreak);
				   		         
				   		   
						      
				   		        
						        Paragraph p3 = new Paragraph();
						        p3.add(new Phrase("Export_Reading : "+meterno,new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
						        p3.setAlignment(Element.ALIGN_LEFT);
						        document.add(p3);
						        
						        document.add(parameterTable1);
		       
			document.close();

			response.setHeader("Content-disposition", "attachment; filename=BillingParameters_"+meterno+"-"+month+".pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();

			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void findAccDATA(String accno, String rdngmonth,HttpServletRequest request) {
		
		int month=Integer.parseInt(rdngmonth);
		try {
		List <MeterMaster> meterMaster=postgresMdas.createNamedQuery("MeterMaster.findAccDATA").setParameter("accno", accno).setParameter("rdngmonth", month).getResultList();
		List<Master> master=postgresMdas.createNamedQuery("Master.getAllData").setParameter("accno", accno).getResultList();
		
		String ipAddress= request.getRemoteAddr();
		System.out.println();
		ChangesEntity changes=new ChangesEntity();
		
		if(meterMaster.size()>0)
		{
			if(master.size()>0)
			{
				changes.setAccno(master.get(0).getAccno());
				changes.setCd(master.get(0).getContractdemand()+ "");
				changes.setCst(master.get(0).getConsumerstatus());
				changes.setCtrd(meterMaster.get(0).getCtrd());
				changes.setCtrn(meterMaster.get(0).getCtrn());
				changes.setDateStamp(new Date());
				changes.setKwOrHp(master.get(0).getKworhp());
				changes.setMeterno(meterMaster.get(0).getMetrno());
				changes.setMf(meterMaster.get(0).getMf());
				changes.setMrname(meterMaster.get(0).getMrname());
				changes.setMtrmake(meterMaster.get(0).getMtrmake());
				changes.setMtrType(meterMaster.get(0).getMtrtype());
				changes.setName(master.get(0).getName());
				changes.setOldNew("DELETED");
				changes.setPrevMtrStatus(meterMaster.get(0).getPrevmeterstatus());
				changes.setRdgMonth(meterMaster.get(0).getRdngmonth());
				changes.setSanload(master.get(0).getSanload());
				changes.setSupplyType(master.get(0).getSupplytype());
				changes.setTadesc(master.get(0).getTadesc());
				changes.setTariffCode(master.get(0).getTariffcode());
				changes.setKno(master.get(0).getKno());
				changes.setAddress1(ipAddress);
				changes.setUsername(request.getSession().getAttribute("username") + "");
				changesService.save(changes);
			}
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static String rootFolder="ASCII_REPORTS";
	@Override
	public String gethtReadingDataAscii(String rdngMonth, String circle1,int acclength,HttpServletResponse response1) {
		System.out.println("inside gethtReadingDataAscii--");
		
		try {
			File folder1 = new File(rootFolder);
			if(!folder1.exists())
			{
				System.out.println("creating folder");
				folder1.mkdir();
			}
			
			PrintWriter writer = new PrintWriter(rootFolder+"\\"+"SAMPLE.txt", "UTF-8");
			
			List<Object[]> list=null;
			String sql="";
			String asciiValue="";
			String finalRes=""; 	
			String kno="",meterno="",xcurrdngkwh="",xcurrdngkvah="",xcurrdngkva="";
			
			String qry="select rdngmonth,circle,sdoname,tadesc,accno,(case when KNO is null then accno else kno END ) as KNO,metrno,\n" +
					"round(XCURRDNGKWH,2) as KWH,round(XCURRRDNGKVAH,2) as KVAH,round(XCURRDNGKVA,2) as KVA,name  from mdm_test.mm where rdngmonth="+rdngMonth+" and\n" +
					"XCURRDNGKWH is not null and XCURRDNGKVA is not NULL and XCURRRDNGKVAH is NOT NULL and substr(accno,5,1)='9' and length(accno)="+acclength+" and circle like '"+circle1+"' ORDER BY ACCNO";
			System.out.println("htdata qry--"+qry);
			list=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println("ascci report list size---"+list.size());
			
			System.out.println("inside ascii for loop");	
			for(int i=0;i<list.size();i++)
			{
				Object[] str=list.get(i);
				
				
				for(int j=0;j<str.length;j++)
				{
					if(j==5)
					{
						if(str[j]!=null)
						{
						kno=str[j].toString();
//						System.out.println("kno==>"+kno);
						}
						asciiValue=asciiValue+kno+",";
					}
					if(j==6)
					{
						if(str[j]!=null)
						{
						meterno=str[j].toString();
						}
//						System.out.println("meterno==>"+meterno);
						asciiValue=asciiValue+meterno+",";
					}
					if(j==7)
					{
						if(str[j]!=null)
						{
						xcurrdngkwh=str[j].toString();
						}
//						System.out.println("xcurrdngkwh==>"+xcurrdngkwh);
						asciiValue=asciiValue+xcurrdngkwh+",";
					}
					if(j==8)
					{
						if(str[j]!=null)
						{
						xcurrdngkvah=str[j].toString();
						}
//						System.out.println("xcurrdngkwh==>"+xcurrdngkvah);
						asciiValue=asciiValue+xcurrdngkvah+",";
					}
					if(j==9)
					{
						if(str[j]!=null)
						{
						xcurrdngkva=str[j].toString();
						}
//						System.out.println("xcurrdngkwh==>"+xcurrdngkva);
						asciiValue=asciiValue+xcurrdngkva+",";
					}
					
					
				}
				asciiValue=asciiValue+"OK,0,0";
				finalRes=finalRes+asciiValue+"@";
				
				asciiValue="";
				kno="";meterno="";xcurrdngkwh="";xcurrdngkvah="";xcurrdngkva="";
				
			}
			String[] arr=finalRes.split("@");
			
			for(int i=0;i<arr.length;i++)
			{
				writer.print(arr[i]);
				writer.print("\r\n");
			}
			writer.flush();
			writer.close();
			
			SimpleDateFormat sdf=new SimpleDateFormat("MMM-yy");
			System.out.println("rdngMonth-->"+rdngMonth);
			Date date1=new SimpleDateFormat("yyyyMM").parse(rdngMonth);  
			System.out.println(date1);
			System.out.println("d2=="+sdf.format(date1));
			
			String name2=circle1+"_HT_Consumers";
			String path=rootFolder+"\\SAMPLE.txt"; //for 10.250
			File downloadFile = new File(path);
			FileInputStream inputStream=null;
			ServletOutputStream outStream=null;
			try {
				 inputStream = new FileInputStream(downloadFile);
				response1.setContentType("text/plain");
				response1.setHeader("Content-Disposition",
			                     "attachment;filename="+sdf.format(date1)+"_"+name2);

				 // get output stream of the response
		         outStream = response1.getOutputStream();
		 
		        byte[] buffer = new byte[1024];
		        int bytesRead = -1;
		 
		             // write bytes read from the input stream into the output stream
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		        outStream.flush();
		        outStream.close();
			} 
			
			catch (Exception e) {

				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}

	@Override
	public List<?> htSummary(String rdngmonth) 
	{
		List<?> htData=null;
		try {
			String qry="select circle,count(case when  length(accno)='12' and XCURRDNGKWH is not null then 1 END )\n" +
					" as \"Main_Meters\",count(case when  length(accno)='13' and XCURRDNGKWH is not null then 1 END )\n" +
					" as \"Second_Meters\",count(case when  XCURRDNGKWH is not null then 1  END ) as \"Total_Completed\"  from mm where rdngmonth="+rdngmonth+" and\n" +
					" substr(accno,5,1)='9' and cst like 'R'  group by circle order by circle";
			
			htData=postgresMdas.createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return htData;
	}

	@Override
	public List<MeterMaster> getMeterData(String meterno, String rdngmonth,ModelMap model)
	{
		 
		List<MeterMaster> l=null;
		try
		{
			 String qry="select * from mdm_test.metermaster m WHERE metrno='"+meterno+"' and rdngmonth ='"+rdngmonth+"' ORDER BY  rdngmonth DESC";
			 System.err.println(qry);
			    l =postgresMdas.createNativeQuery(qry).getResultList();
			    		
			    for (Iterator iterator1 = l.iterator(); iterator1.hasNext();)
			    {
			    	Object[] obj = (Object[]) iterator1.next();
					 if (l.size() > 0) 
					 {
						System.out.println("---------"+l.get(0));
						 model.put("metrno",obj[2] );
						 model.put("data", obj[20]);
						 model.put("mtrmake",obj[48]);
						 model.put("mtrtype",obj[33]);
						 model.put("newseal",obj[23]);
						 model.put("zone",obj[102]);
						 model.put("circle",obj[58]);
						 model.put("division",obj[59]);
						 model.put("subdiv",obj[60]);
						 model.put("name",obj[87]);
						 model.put("address",obj[88]);
						 model.put("mtrstatus",obj[4]);
						 model.put("accno",obj[1]);
					} 
					 else 
					 {
						model.put("msg", "No data found on this account ");
						return null;
					}
					 
				}
		
		}
		catch (Exception e) 
		{
			
			System.out.println("getting exception");
			e.printStackTrace();
			return null;
		}
		return l;
	}
	
	 @Override
	 @Transactional(propagation=Propagation.SUPPORTS)
	 public List<Object[]> exportReadingDataDetails(String circle, String subdivision,String month,HttpServletRequest request)
	 {
		String subdiv="";
		 if(subdivision.equalsIgnoreCase("noVal"))
			{
			 subdiv="%";
			}
			else
			{
				subdiv=subdivision;
			}
		
		 String sql="";
		
		 sql="SELECT DIVISION,SUBDIV,ACCNO,CONSUMERNAME,ADDRESS,CATEGORY,METRNO,RDATE,READINGDATE,CURRDNGKWH,CURRRDNGKVAH,CURRDNGKVA,XCURRDNGKWH,XCURRRDNGKVAH,XCURRDNGKVA,T1KWH,T2KWH,T3KWH,T4KWH,T5KWH,T6KWH,T7KWH,T8KWH,T1KVAH,T2KVAH,T3KVAH,T4KVAH,T5KVAH,T6KVAH,T7KVAH,T8KVAH,T1KVAV,T2KVAV,T3KVAV,T4KVAV,T5KVAV,T6KVAV,T7KVAV,T8KVAV,READINGREMARK,REMARK  FROM  meter_data.METERMASTER WHERE CIRCLE LIKE '"+circle+"' AND SUBDIV LIKE '"+subdiv+"' AND  RTC=1  AND RDNGMONTH='"+month+"'";//AND DISCOM='UHBVN'
		 System.err.println("excel----"+sql);
		 List<Object[]> list=null;
		
		 try 
		 {
			 list=postgresMdas.createNativeQuery(sql).getResultList();
			
		} 
		 catch (Exception e) 
		{
			e.printStackTrace();
		}
		 return list;
	 }
	 
		@Override
		public List<Object[]> getAdministrativeDetails(String billmonth,ModelMap model,HttpServletRequest request)
		{
			System.err.println("billmonth----- "+billmonth);
			List<Object[]> list=null;
			try
			{
				String qry="select ROW_NUMBER() OVER (PARTITION BY 1 ORDER BY circle)  as srno,circle,count(case when rtc='1'  and category like 'HTI' then 1 end) as HTI_CMRI,\n" +
						"count(case when rtc='1' and category like 'HT' then 1 end) as HT_CMRI,count(case when rtc='1'  \n" +
						"and category like 'LT' then 1 end) as LT_CMRI,count(case when rtc='1' and category  in ('HTI','HT','LT') then 1 end) as CMRI_Total \n" +
						"FROM meter_data.METERMASTER where rdngmonth='"+billmonth+"' and rtc in ('1','0') and SUBDIV not like 'NA' group by circle order by circle "; //and discom='UHBVN'
				 System.err.println("assesment---- "+qry);
				list=postgresMdas.createNativeQuery(qry).getResultList();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return list;
		}
		
		@Override
		public List<Object[]> viewCirclewiseDetails(String circle, String month,ModelMap model, HttpServletRequest request) {
			List<Object[]> list=null;
			try
			{
				String qry="select ROW_NUMBER()\n" +
						"   OVER (PARTITION BY 1 ORDER BY circle,division,subdiv)  as srno,circle,division,subdiv as Subdivision,\n" +
						"count(case when rtc='1'  and category like 'HTI' then 1 end) as HTI_CMRI,count(case when rtc='1'  and category like 'HT' then 1 end) as HT_CMRI,count(case when rtc='1'  and category like 'LT' then 1 end) as LT_CMRI,count(case when rtc='1'  and category  in ('HTI','HT','LT') then 1 end) as CMRI_Total,\n" +
						"count(case when rtc='0' and XCURRDNGKWH is not null and CAST(XCURRDNGKWH AS TEXT)  not like '0'  and category like 'HTI' then 1 end) as HTI_Manual,count(case when rtc='0' and XCURRDNGKWH is not null and cast(XCURRDNGKWH AS TEXT)  not like '0'  and category like 'HT' then 1 end) as HT_Manual,count(case when rtc='0' and XCURRDNGKWH is not null and cast(XCURRDNGKWH AS TEXT)  not like '0'  and category like 'LT' then 1 end) as LT_Manual,count(case when category  in ('HTI','HT','LT')  and rtc='0' and XCURRDNGKWH is not null and cast(XCURRDNGKWH AS TEXT)  not like '0'  then 1 end) as Manual_Total,\n" +
						"count(case when rtc='0'   and xmldate is null  and METERSTATUS like 'R' and upper(readingremark) not like '%DISPLAY%' and upper(readingremark) not like '%BURNT%' and upper(readingremark) not like '%METER%D%F%'  and upper(readingremark) not like '%DEAD%'  and upper(readingremark) not like 'METER%B%T%' and upper(readingremark) not like '%METER%FAUL%'  and upper(readingremark) not like '%STOP%' and readingremark is  not null  and READINGREMARK not like '0' and xmldate is null  and  (XCURRDNGKWH is null or XCURRDNGKWH='0')  and category like 'HTI' then 1 end) as HTI_RNC,count(case when rtc='0' and  xmldate is null  and METERSTATUS like 'R' and upper(readingremark) not like '%DISPLAY%' and upper(readingremark) not like '%BURNT%' and upper(readingremark) not like '%METER%D%F%'  and upper(readingremark) not like '%DEAD%'  and upper(readingremark) not like 'METER%B%T%' and upper(readingremark) not like '%METER%FAUL%'  and upper(readingremark) not like '%STOP%' and readingremark is  not null  and READINGREMARK not like '0' and xmldate is null  and  (XCURRDNGKWH is null or XCURRDNGKWH='0')  and category like 'HT' then 1 end) as HT_RNC,count(case when rtc='0'  and xmldate is null  and METERSTATUS like 'R' and upper(readingremark) not like '%DISPLAY%' and upper(readingremark) not like '%BURNT%' and upper(readingremark) not like '%METER%D%F%'  and upper(readingremark) not like '%DEAD%'  and upper(readingremark) not like 'METER%B%T%' and upper(readingremark) not like '%METER%FAUL%'  and upper(readingremark) not like '%STOP%' and readingremark is  not null  and READINGREMARK not like '0' and xmldate is null  and  (XCURRDNGKWH is null or XCURRDNGKWH='0')  and category like 'LT' then 1 end) as LT_RNC,\n" +
						"count(case when rtc='0' and xmldate is null  and METERSTATUS like 'R' and (upper(readingremark) like '%DISPLAY%' or upper(readingremark) like '%BURNT%' or upper(readingremark) like '%METER%D%F%'  or upper(readingremark) like '%DEAD%'  or upper(readingremark) like 'METER%B%T%' or upper(readingremark) like '%METER%FAUL%'  or upper(readingremark) like '%STOP%') then 1 end) as Defective,\n" +
						"count(case when rtc in ('1','0')  and category like 'HTI' then 1 end) as HTI_Total,count(case when  rtc in ('1','0')  and category like 'HT' then 1 end) as HT_Total,count(case when  rtc in ('1','0') and category like 'LT' then 1 end) as LT_Total,count(case when  rtc in ('1','0') and category  in ('HTI','HT','LT')  then 1 end) as Grand_Total\n" +
						"    FROM meter_data.METERMASTER where rdngmonth='"+month+"' and rtc in ('1','0')  and SUBDIV not like 'NA' AND CIRCLE='"+circle+"' group by circle,division,subdiv order by circle,division,subdiv";   //and discom='UHBVN'
				System.err.println("sub-- "+qry);
				list=postgresMdas.createNativeQuery(qry).getResultList();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return list;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public ArrayList<Map<String, Object>> findmonthData1(String rdngmonth,HttpServletRequest request,ModelMap data1) {
			 List<MeterMaster> list =new ArrayList<MeterMaster>();
			 ArrayList<Map<String, Object>> list1=new ArrayList<>();
			try
			{
				/*return postgresMdas.createNamedQuery("MeterMaster.getDataa").setParameter("month", Integer.parseInt(parameter)).
			setParameter("discom", "UHBVN").getResultList();*/
				
				
				String qry="select * from meter_data.consumerreadings m WHERE billmonth ='"+rdngmonth+"' ORDER BY  meterno ASC";
				 System.out.println(qry);
				 list =postgresMdas.createNativeQuery(qry).getResultList();

				// java.text.DecimalFormat df=new DecimalFormat(".##");
				 DecimalFormat df=new DecimalFormat(".##");
				    for (Iterator iterator1 = list.iterator(); iterator1.hasNext();)
				    {
				    	Map<String, Object> data=new HashMap<>();
				    	Object[] obj = (Object[]) iterator1.next();
						 if (list.size() > 0) 
						 {
							 
							 data.put("metrno", obj[0]);
							 data.put("data", obj[1]);
							 data.put("mtrmake",obj[2]);
							 data.put("mtrtype",obj[3]);
							 data.put("newseal",obj[4]);
							 data.put("zone",obj[5]);
							 data.put("circle",obj[5]);
							 data.put("division",obj[6]);
							 data.put("subdiv",obj[1]);
							 data.put("name",obj[87]);
							 data.put("address",obj[88]);
							 data.put("mtrstatus",obj[4]);
							 data.put("accno",obj[1]);
							 data.put("phase",obj[9]);
							 data.put("ctrn",obj[10]);
							 data.put("ctrd",obj[11]);
							 data.put("amprating",obj[12]);
							 
							 
							/* data.put("currdngkwh",df.format(obj[13]));
							 data.put("currrdngkvah",df.format(obj[14]));
							 data.put("currdngkva",df.format(obj[15]));*/
							 String kwh="";
							 String kvah="";
							 String kva="";
							 
							 if(obj[7]!=null )
							 {
								Double wh=Double.parseDouble(String.valueOf(obj[7]));
								double dkwh= wh;
								kwh=new DecimalFormat("#.##").format(dkwh);
							 } 
							 
							 if(obj[8]!=null)
							 {
								 	Double wh=Double.parseDouble(String.valueOf(obj[8]));
									double dkwh= wh;
									kvah=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[9]!=null )
							 {
								 	Double wh=Double.parseDouble(String.valueOf(obj[9]));
									double dkwh= wh;
									kva=new DecimalFormat("#.##").format(dkwh);
							 }
							 data.put("currdngkwh",kwh);
							 data.put("currrdngkvah",kvah);
							 data.put("currdngkva",kva);
							 
							/* String t1kwh="";
							 String t2kwh="";
							 String t3kwh="";
							 String t4kwh="";
							 String t5kwh="";
							 String t6kwh="";
							 String t7kwh="";
							 String t1kvah="";
							 String t2kvah="";
							 String t3kvah="";
							 String t4kvah="";
							 String t5kvah="";
							 String t6kvah="";
							 String t7kvah="";
							
							
							 if(obj[62]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[62]));
									double dkwh= wh;
									t1kwh=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[63]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[63]));
									double dkwh= wh/1000;
									t2kwh=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[64]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[64]));
									double dkwh= wh/1000;
									t3kwh=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[65]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[65]));
									double dkwh= wh/1000;
									t4kwh=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[66]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[66]));
									double dkwh= wh/1000;
									t5kwh=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[67]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[67]));
									double dkwh= wh/1000;
									t6kwh=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[68]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[68]));
									double dkwh= wh/1000;
									t7kwh=new DecimalFormat("#.##").format(dkwh);
							 }
							 
							 data.put("t1kwh",t1kwh);
							 data.put("t2kwh",t2kwh);
							 data.put("t3kwh",t3kwh);
							 data.put("t4kwh",t4kwh);
							 data.put("t5kwh",t5kwh);
							 data.put("t6kwh",t6kwh);
							 data.put("t7kwh",t7kwh);
							 
							 if(obj[70]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[70]));
									double dkwh= wh/1000;
									t1kvah=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[71]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[71]));
									double dkwh= wh/1000;
									t2kvah=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[72]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[72]));
									double dkwh= wh/1000;
									t3kvah=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[73]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[73]));
									double dkwh= wh/1000;
									t4kvah=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[74]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[74]));
									double dkwh= wh/1000;
									t5kvah=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[75]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[75]));
									double dkwh= wh/1000;
									t6kvah=new DecimalFormat("#.##").format(dkwh);
							 }
							 if(obj[76]!=null)
							 {
								 Double wh=Double.parseDouble(String.valueOf(obj[76]));
									double dkwh= wh/1000;
									t7kvah=new DecimalFormat("#.##").format(dkwh);
							 }
							 
							 
							 data.put("t1kvah",t1kvah);
							 data.put("t2kvah",t2kvah);
							 data.put("t3kvah",t3kvah);
							 data.put("t4kvah",t4kvah);
							 data.put("t5kvah",t5kvah);
							 data.put("t6kvah",t6kvah);
							 data.put("t7kvah",t7kvah);*/
							 
							 list1.add(data);
						} 
						 else 
						 {
							data1.put("msg", "No data found on this account ");
							return null;
						}
						 
					}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return list1;
		}


		@Override
		public Object meterdetails(String circle, String subdivision,String division,String date, HttpServletRequest request) {
			String subdiv="";
			 if(subdivision.equalsIgnoreCase("noVal"))
				{
				 subdiv="%";
				}
				else
				{
					subdiv=subdivision;
				}
			
			 String sql="";
			
			 sql="SELECT DIVISION,SUBDIV,ACCNO,CONSUMERNAME,ADDRESS,CATEGORY,METRNO,RDATE,READINGDATE,CURRDNGKWH,CURRRDNGKVAH,CURRDNGKVA,XCURRDNGKWH,XCURRRDNGKVAH,XCURRDNGKVA,T1KWH,T2KWH,T3KWH,T4KWH,T5KWH,T6KWH,T7KWH,T8KWH,T1KVAH,T2KVAH,T3KVAH,T4KVAH,T5KVAH,T6KVAH,T7KVAH,T8KVAH,T1KVAV,T2KVAV,T3KVAV,T4KVAV,T5KVAV,T6KVAV,T7KVAV,T8KVAV,READINGREMARK,REMARK,STATUS  FROM  meter_data.METERMASTER WHERE CIRCLE LIKE '"+circle+"' AND SUBDIV LIKE '"+subdiv+"' AND  RTC=1  AND division='"+division+"' AND rdngmonth='"+date+"' AND DISCOM='UHBVN'";
			 System.err.println("conn----"+sql);
			 List<Object[]> list=null;
			
			 try 
			 {
				 list=postgresMdas.createNativeQuery(sql).getResultList();
				
			} 
			 catch (Exception e) 
			{
				e.printStackTrace();
			}
			 return list;
		 }
		
		@Override
		public Object modemdetails(String circle, String subdivision,String division,String mtrno, HttpServletRequest request) {
			String subdiv="";
			 if(subdivision.equalsIgnoreCase("noVal"))
				{
				 subdiv="%";
				}
				else
				{
					subdiv=subdivision;
				}
			
			 String sql="";
			 
			 Date d=new Date();
			 SimpleDateFormat sdf1=new SimpleDateFormat("YYYYMM");
			 
			 String date=sdf1.format(d);
			 System.out.println("date is==>"+date);
			 System.out.println("division-------->"+division);
			 System.out.println("mtrno------>"+mtrno);
			 sql="SELECT A.accno,A.substation,MM.mtrmake FROM (SELECT * FROM meter_data.master M  WHERE circle like '"+circle+"' AND division like '"+division+"' AND subdiv like '"+subdivision+"')A INNER JOIN  meter_data.metermaster MM ON  A.accno=MM.accno\n" +
					 "AND MM.metrno ='"+mtrno+"' and mm.rdngmonth='"+date+"'";
			 System.err.println("conn----"+sql);
			 List<Object[]> list=null;
			
			 try 
			 {
				 list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				
			} 
			 catch (Exception e) 
			{
				e.printStackTrace();
			}
			 return list;
		 }

		@Override
		public int updatestatus(String accno, String status,String date) { int count=0;
		try
		{
		String query = "update meter_data.metermaster set status = '"+status+"' where accno='"+accno+"' and rdngmonth='"+date+"'";
	/*	getCustomEntityManager("postgresMdas").joinTransaction();*/
		  count = getCustomEntityManager("postgresMdas").createNativeQuery(query).executeUpdate();
		  
		 
		}
		
		
		catch(Exception e){e.printStackTrace();}
		return count;
				
	}
		 @Override
			@Transactional(propagation=Propagation.SUPPORTS)
		public List<Object[]> getCategorywiseData(String billMonth,HttpServletRequest request)
			{
			 List<Object[]> list=null;
		
				  String sql="SELECT DISTINCT MM.SUBDIV,"+" "
					+"COUNT(CASE WHEN MM.CATEGORY='LT' THEN 0 END)AS LT_TOT,"+" "
					+"COUNT(CASE WHEN MM.CATEGORY='LT' AND MM.READINGDATE IS NOT NULL THEN 0 END)AS LT_ENTERED,"+" "
					+"COUNT(CASE WHEN MM.CATEGORY='LT' AND MM.READINGDATE IS NULL THEN 0 END)AS LT_PENDING,"+" "
					+"COUNT(CASE WHEN MM.CATEGORY='HT' THEN 0 END) AS HT_TOT,"+" "
					+"COUNT(CASE WHEN MM.CATEGORY='HT' AND MM.READINGDATE IS NOT NULL THEN 0 END)AS HT_ENTERED,"+" "
					+"COUNT(CASE WHEN MM.CATEGORY='HT' AND MM.READINGDATE IS NULL THEN 0 END)AS HT_PENDING,"+" "
					+"COUNT(CASE WHEN MM.CATEGORY='HTI' THEN 0 END)AS HTI_TOT,"+" "
					+"COUNT(CASE WHEN MM.CATEGORY='HTI' AND MM.READINGDATE IS NOT NULL THEN 0 END)AS HTI_ENTERED,"+" "
					+"COUNT(CASE WHEN MM.CATEGORY='HTI' AND MM.READINGDATE IS NULL THEN 0 END)AS HTI_PENDING "+" "
					+"FROM mdm_test.METERMASTER MM,mdm_test.MASTER MA WHERE MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+billMonth+"' AND MM.DISCOM='UHBVN' "+" "
					+"AND MM.SUBDIV IS NOT NULL AND MA.CONSUMERSTATUS LIKE 'R' AND MM.CATEGORY IS NOT NULL AND "+" "
					+"MM.CATEGORY IN('HT','HTI','LT') AND MM.SUBDIV  NOT LIKE 'NA' GROUP BY MM.SUBDIV"+" "
					+"ORDER BY MM.SUBDIV";
							   MDMLogger.logger.info("========queryquery====>"+sql);
					 try
					 {
						 list=postgresMdas.createNativeQuery(sql).getResultList();
					 } 
					 catch (Exception e)
					{
						e.printStackTrace();
					}
			
				return list;
			 
			}
		
		
		@Override
		public List<Object[]> getActieMeters(String subdivision, String month,
				HttpServletRequest request) {
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-YYYY");
			Date d1=new Date();
			String today=sdf.format(d1);
			 List<Object[]> list=null;
				
			  String sql="SELECT mm.metrno,m.division,m.sdoname,m.accno,name,mm.status FROM meter_data.master M INNER JOIN meter_data.metermaster MM ON M.accno = MM.accno\n" +
					  "		    			 WHERE    M.division='"+subdivision+"' AND  MM.rdngmonth='"+month+"' AND MM.metrno IN( \n" +
					  "		    			  SELECT DISTINCT meter_number FROM meter_data.load_survey WHERE to_char(read_time,'dd-MM-YYYY')='"+today+"')";
						 //  MDMLogger.logger.info("========queryquery====>"+sql);
				 try
				 {
					 list=postgresMdas.createNativeQuery(sql).getResultList();
				 } 
				 catch (Exception e)
				{
					e.printStackTrace();
				}
		
			return list;
		 
		}
		
		 @Override
			@Transactional(propagation=Propagation.SUPPORTS)
		 public List<Object[]> getInstallationDetails(String billmonth1,String discom, ModelMap model,HttpServletRequest request)
			{
			 SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
				Date d1=new Date();
				String today=sdf.format(d1);
			// System.out.println("DHBVN dashboard billmonth---"+billmonth1);
			 List<Object[]> list=null;
			
					 /*String sql="SELECT AA.RDNGMONTH, AA.SUBDIV,AA.TOTAL_INSTLLATIONS,AA.TOTAL_ENTERED,(AA.TOTAL_INSTLLATIONS-AA.TOTAL_ENTERED)AS TOTAL_PENDING  FROM"+" "
								+"("+" "
								+"SELECT DISTINCT MM.SUBDIV,"+" "
								+"COUNT(*) AS TOTAL_INSTLLATIONS,"+" "
								+"COUNT(CASE WHEN READINGDATE IS NOT NULL THEN 0 END)AS TOTAL_ENTERED,MM.RDNGMONTH"+" "
								+"FROM mdm_test.METERMASTER MM,mdm_test.MASTER MA WHERE MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+billmonth1+"' AND MM.DISCOM='"+discom+"' AND MM.SUBDIV IS NOT NULL AND MA.CONSUMERSTATUS LIKE 'R' AND MA.CATEGORY IN ('HT','LT','HTI') GROUP BY MM.SUBDIV,  MM.RDNGMONTH"+" "
								+"ORDER BY MM.SUBDIV)AA";*/
			 /*String sql1="SELECT AA.RDNGMONTH,AA.SUBDIV,AA.NONR,AA.AMR,AA.TOTAL_INSTLLATIONS,AA.TOTAL_ENTERED,(AA.TOTAL_INSTLLATIONS - AA.TOTAL_ENTERED) AS TOTAL_PENDING\n" +
					 "FROM(SELECT DISTINCT MM.SUBDIV,COUNT (*) AS TOTAL_INSTLLATIONS,COUNT (CASE WHEN billing_category='NONR'  THEN 0 END ) AS NONR,\n" +
					 "COUNT (CASE WHEN billing_category='AMR' THEN 0 END) AS AMR,COUNT (CASE WHEN currdngkwh IS NOT NULL THEN 0 END) AS TOTAL_ENTERED,\n" +
					 "MM.RDNGMONTH FROM meter_data.METERMASTER MM,meter_data.MASTER MA WHERE MA.ACCNO = MM.ACCNO AND MM.RDNGMONTH = '"+billmonth1+"'\n" +
					 "AND MM.DISCOM = 'UHBVN' AND MM.SUBDIV IS NOT NULL AND MA.CONSUMERSTATUS LIKE 'R' GROUP BY MM.SUBDIV,MM.RDNGMONTH ORDER BY MM.SUBDIV) AA";
				
			 
			 String sql="SELECT AA.RDNGMONTH,AA.SUBDIV,AA.TOTAL_INSTLLATIONS\n" +
					 "FROM(SELECT DISTINCT MM.SUBDIV,COUNT (*) AS TOTAL_INSTLLATIONS,\n" +
					 "MM.RDNGMONTH FROM meter_data.METERMASTER MM,meter_data.MASTER MA WHERE MA.ACCNO = MM.ACCNO AND MM.RDNGMONTH = '"+billmonth1+"'\n" +
					 " AND MM.SUBDIV IS NOT NULL AND MA.CONSUMERSTATUS LIKE 'R' GROUP BY MM.SUBDIV,MM.RDNGMONTH ORDER BY MM.SUBDIV) AA";*/
			 
			 String sql2="SELECT A.SUBDIV AS sdoname,A.SUBDIV, coalesce (A.TOTAL,0)TOTAL,coalesce (B.ACTIVE,0)ACTIVE,(coalesce( A.TOTAL,0)-coalesce (B.ACTIVE,0)) AS BALANCE  FROM\n" +
					 "(select subdiv,count(subdiv) AS TOTAL from meter_data.metermaster where rdngmonth='"+billmonth1+"' GROUP BY subdiv)a\n" +
					 "left join \n" +
					 "(select subdiv,count(subdiv)AS ACTIVE from meter_data.metermaster where metrno in(\n" +
					 "select DISTINCT meter_number from meter_data.load_survey where to_char(read_time, 'YYYY-MM-dd')='"+today+"') and rdngmonth='"+billmonth1+"' GROUP BY subdiv)b\n" +
					 "ON A.SUBDIV=B.SUBDIV  ";
			 		System.out.println("installation details--"+sql2);
						 try
						 {
							 list=postgresMdas.createNativeQuery(sql2).getResultList();
							 for (int i = 0; i < list.size(); i++) 
							 {
								if(i==0)
								{
									Object[] arr=list.get(i);
									model.put("instArrayLength", arr.length);
								}
							}
						 } 
						 catch (Exception e)
						{
							e.printStackTrace();
						}
			 			return list;
					}

		@Override
		public List<?> getBillingDataMM(String month,
				String meterNo, ModelMap model, HttpServletRequest request)
		{
			String sql="SELECT EE.meterno,EE.PRESENT_BILLMONTH,EE.PREV_KWH,EE.PRESENT_KWH,(EE.PRESENT_KWH-EE.PREV_KWH) AS CONSUMPTION FROM (SELECT * FROM (SELECT * FROM(\n" +
					"SELECT * FROM (SELECT   C.meterno AS PREV_METERNO,C.cdf_id , C.readdate AS PREV_MONTH_READING_DATE,C.billmonth AS PREV_BILLMONTH,COALESCE(CASE WHEN  D.kwh =null\n" +
					"	 THEN 1 ELSE D.kwh END,0) as PREV_KWH\n" +
					" FROM mdm_test.cdf_data C \n" +
					"INNER JOIN mdm_test.d3_data D ON C.cdf_id=D.cdf_id\n" +
					"WHERE cast(C.billmonth as varchar) =to_char(to_date('"+month+"', 'yyyyMM')- interval '1 month','yyyyMM') AND  C.meterno ='"+meterNo+"')A  WHERE A.PREV_MONTH_READING_DATE IN (SELECT MAX(readdate) FROM mdm_test.cdf_data C  WHERE cast(C.billmonth as varchar) =to_char(to_date('"+month+"', 'yyyyMM')- interval '1 month','yyyyMM') AND  C.meterno ='"+meterNo+"' ))B)BB\n" +
					"INNER JOIN (\n" +
					"SELECT * FROM (SELECT   C.meterno,C.cdf_id, C.readdate AS PRESENT_READING_DATE,C.billmonth AS PRESENT_BILLMONTH,COALESCE(CASE WHEN  D.kwh =null\n" +
					"	 THEN 1 ELSE D.kwh END,0) as PRESENT_KWH\n" +
					" FROM mdm_test.cdf_data C \n" +
					"INNER JOIN mdm_test.d3_data D ON C.cdf_id=D.cdf_id\n" +
					"WHERE C.billmonth ='"+month+"' AND  C.meterno ='"+meterNo+"')A  WHERE A.PRESENT_READING_DATE IN (SELECT MAX(readdate) FROM mdm_test.cdf_data C  WHERE C.billmonth ='"+month+"' AND  C.meterno ='"+meterNo+"' ))D ON BB.PREV_METERNO=D.meterno)EE";
			
			System.err.println("billing sql===>"+sql);
			List<?> list=postgresMdas.createNativeQuery(sql).getResultList();
			return list;
		}
 @Override
			public List<Object[]> getServiceOrder() {
				String sql="select m.metrno,m.accno,m.circle,m.division,m.subdiv,m.consumername,m.address,ms.service_order_type,ms.status from meter_data.metermaster m,meter_data.meter_service_order ms where metrno like ms.meter_no GROUP BY metrno,accno,circle,division,subdiv,consumername,address,ms.service_order_type,ms.status ORDER BY metrno";
				
				List<Object[]> l=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				return l;
			}
			@Override
			public List<MeterMaster> getMeterData() 
			{
				List<MeterMaster> list=null;
				try{
					list=postgresMdas.createNamedQuery("Master.getAllDataMeter").getResultList();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				return list;
			}
			
			@Override
			public List<String> getDistinctSubdivision(String circle,String division, HttpServletRequest request) 
			{
				MDMLogger.logger.info("================>"+circle+"==="+division);
				List<String> list=null;
				try 
				{
					list=postgresMdas.createNamedQuery("MeterMaster.findSubdByCircleDiv").setParameter("circle", circle.toUpperCase()).setParameter("division", division.toUpperCase()).getResultList();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				return list;
			}
			@Override
			@Transactional(propagation=Propagation.SUPPORTS)
			public void generateMdiExceedRpt(String month, String subdiv,String categoryVal,ModelMap model,HttpServletResponse response)
			{
				
				MDMLogger.logger.info("===========generateMdiExceedRpt====categoryVal======>"+categoryVal);
				String query="SELECT AA.* FROM"+" "
								+"("+" "
								+"SELECT M.CIRCLE,M.DIVISION,M.SUBDIV,MM.CATEGORY,MM.ACCNO,MM.METRNO,MM.CONSUMERNAME,MM.ADDRESS,"
								+ "M.CONTRACTDEMAND,MM.XCURRDNGKVA,MM.MF,"+" "
								+"(MM.XCURRDNGKVA*MM.MF) as KVAMF,(M.CONTRACTDEMAND*1.05) as CD105"+" "
								+"FROM meter_data.master M,"+" " 
								+"meter_data.METERMASTER MM"+" "
								+"WHERE M.accno=MM.ACCNO AND MM.RDNGMONTH="+month+" AND MM.SUBDIV LIKE '"+subdiv+"' AND MM.CATEGORY LIKE '"+categoryVal+"' "
								+ "AND M.CONTRACTDEMAND>0"+" "
								+")AA WHERE AA.KVAMF>AA.CD105";
				MDMLogger.logger.info("===========generateMdiExceedRpt==========>"+query);
				List<Object[]> list=null;
				try 
				{
					 list=postgresMdas.createNativeQuery(query).getResultList();
					 /*XSSFWorkbook workbook=null;
						XSSFSheet spreadsheet=null;
						workbook = new XSSFWorkbook(); 
						//Create a blank spreadsheet
						 spreadsheet = workbook.createSheet("SEAL CARD REPORT");
						 XSSFRow row;*/
					 XSSFWorkbook workbook = new XSSFWorkbook();
						XSSFSheet sheet = workbook.createSheet("MDI Exceed Report") ;
					
						XSSFCellStyle style = workbook.createCellStyle();
						XSSFFont font = workbook.createFont();
						font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
						font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
						font.setFontHeightInPoints((short)12);
						style.setFont(font);
		 				style.setAlignment(CellStyle.ALIGN_CENTER);
		 				
		 				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		 				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		 				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		 				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		 				
		 				XSSFCellStyle style1 = workbook.createCellStyle();
						XSSFFont font1 = workbook.createFont();
						font1.setFontName(XSSFFont.DEFAULT_FONT_NAME);
						font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
						font1.setFontHeightInPoints((short)10);
						style1.setFont(font1);
		 				style1.setAlignment(CellStyle.ALIGN_CENTER);
		 				style1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		 				style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 				
		 				style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		 				style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		 				style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		 				style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
						
						XSSFRow row;
						 Map < String, Object[] > sealInfo = new TreeMap < String, Object[] >();
						 row = sheet.createRow(0);
						                 
			 				    Cell cell1 = row.createCell((short)0);
					            cell1.setCellValue("CIRCLE");
					            cell1.setCellStyle(style1);
					            Cell cell2 = row.createCell((short)1);
					            cell2.setCellValue("DIVISION");
					            cell2.setCellStyle(style1);
					            Cell cell3 = row.createCell((short)2);
					            cell3.setCellValue("SUBDIV");
					            cell3.setCellStyle(style1);
					            Cell cell4 = row.createCell((short)3);
					            cell4.setCellValue("CATEGORY");
					            cell4.setCellStyle(style1);
					            Cell cell5 = row.createCell((short)4);
					            cell5.setCellValue("ACCNO");
					            cell5.setCellStyle(style1);
					            Cell cell6 = row.createCell((short)5);
					            cell6.setCellValue("METER NO.");
					            cell6.setCellStyle(style1);
					            Cell cell7 = row.createCell((short)6);
					            cell7.setCellValue("NAME");
					            cell7.setCellStyle(style1);
					            Cell cell8 = row.createCell((short)7);
					            cell8.setCellValue("ADDRESS");
					            cell8.setCellStyle(style1);
					            Cell cell9 = row.createCell((short)8);
					            cell9.setCellValue("CD");
					            cell9.setCellStyle(style1);
					            Cell cell10 = row.createCell((short)9);
					            cell10.setCellValue("XCURRNGKVA");
					            cell10.setCellStyle(style1);
					            Cell cell11 = row.createCell((short)10);
					            cell11.setCellValue("MF");
					            cell11.setCellStyle(style1);
					            Cell cell12 = row.createCell((short)11);
					            cell12.setCellValue("KVA*MF");
					            cell12.setCellStyle(style1);
					            Cell cell13 = row.createCell((short)12);
					            cell13.setCellValue("CD*1.05");
					            cell13.setCellStyle(style1);
					            
								
					 for (int i = 0; i < list.size(); i++) 
					 {
						 Object[] object=list.get(i);
						 sealInfo.put( "row"+(i+1), object);
						 Set < String > keyid = sealInfo.keySet();
					      int rowid = 1;
					      for (String key : keyid)
					      {
					    	  int cellid = 0;
					         row = sheet.createRow(rowid++);
					         Object [] objectArr = sealInfo.get(key);
					         for (Object obj : objectArr)
					         {
					        		 Cell cell = row.createCell(cellid++);
					        		   cell.setCellValue(obj+"");
							            cell.setCellStyle(style);
					         }
					      }
					 }
						
						
						try 
						{
							ServletOutputStream servletOutputStream;
							
							servletOutputStream = response.getOutputStream();
							response.setContentType("application/vnd.ms-excel");
							response.setHeader("Content-Disposition", "inline;filename=\"MDI Exceed Report_"+subdiv+"_"+categoryVal+".xlsx"+"\"");
						
							OutputStream out = response.getOutputStream();
							workbook.write(out);
							out.flush();
						
							servletOutputStream.flush();
							servletOutputStream.close();
							
						} catch (Exception e) 
						{
							e.printStackTrace();
						}
				
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		
		@Override
		public List findALLZeroConc(String month) {
			System.out.println("zeroooo   month-->"+month);
			List list=null;
			try {
				/*String qry="SELECT mm.accno,mm.metrno,mm.rdngmonth,mm.unitskwh AS currdngkwh,unitskwh AS prevconsumption,ma.circle,ma.subdiv,ma.name"
						+ " FROM meter_data.metermaster mm, meter_data.master ma WHERE  "
						+ "ma.accno=mm.accno and rdngmonth='"+month+"' AND unitskwh is NOT NULL AND unitskwh>100 LIMIT 10";*/
				
				String qry="SELECT * FROM(\n" +
						"(SELECT B.accno,B.metrno,B.rdngmonth,B.currnet_reading ,A.pre_reading ,round(B.currnet_reading-A.pre_reading,2) as consumption,B.mf,B.category FROM\n" +
						"(SELECT accno,COALESCE(xcurrdngkwh,0) as currnet_reading,rdngmonth,metrno,mf,category FROM meter_data.metermaster WHERE rdngmonth='201812' AND circle LIKE '%' AND division LIKE '%'AND metrno NOT in( 'NA','1') AND metrno is not null)B\n" +
						"LEFT JOIN \n" +
						"(SELECT rdngmonth,accno,COALESCE(xcurrdngkwh,0) as pre_reading  FROM meter_data.metermaster WHERE cast(rdngmonth as TEXT)=to_char(to_date('201812', 'yyyyMM') -INTERVAL '1 month','yyyyMM')  AND circle LIKE '%' AND division like '%' "
						+ "AND metrno NOT in( 'NA','1') AND metrno is not null)A ON B.accno=A.accno)AA\n" +
						"LEFT JOIN \n" +
						"(SELECT accno,circle,division,subdiv,name,supplyvoltage ,contractdemand,sanload FROM meter_data.master WHERE circle LIKE '%' AND division like '%')BB\n" +
						"ON AA.accno=BB.accno )CC  WHERE CC.consumption=0";
						
						
				System.out.println("zeroo query=="+qry);
			list=postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return list;
		}
		
		@Override
		public List findALLDmndExceed(String month) {
			System.out.println("exceed   month-->"+month);
			List list=null;
			try {
				String qry="SELECT * FROM \n" +
						"(SELECT ma.circle,ma.subdiv,ma.substation,ma.name,mm.accno,mm.metrno,mm.rdngmonth,mm.currdngkva as KVA,mm.mf AS MF,\n" +
						"(cast(mm.xcurrdngkva AS NUMERIC) * cast(mm.mf AS NUMERIC)) AS kvaMF ,\n" +
						" ma.contractdemand,\n" +
						"case when ((cast(mm.currdngkva AS NUMERIC) * cast(mm.mf AS NUMERIC))-CAST(ma.contractdemand AS NUMERIC))>0 then (cast(mm.currdngkva AS NUMERIC) * cast(mm.mf AS NUMERIC))-CAST(ma.contractdemand AS NUMERIC) else '0' end as dexceed\n" +
						"FROM meter_data.metermaster mm,meter_data.master ma WHERE\n" +
						" ma.contractdemand>100 AND mm.accno=ma.accno and mm.rdngmonth='"+month+"')cc\n" +
						"WHERE cc.dexceed>0 order by cc.circle,cc.subdiv";
				System.out.println("zeroo query=="+qry);
			list=postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return list;
		
		}
		
		
		@Override
		@Transactional
		public List<MeterMaster> getMeterDataByMeterNo(String meterno) 
		{
			return postgresMdas.createNamedQuery("MeterMaster.CheckMeterNo").setParameter("metrno", meterno).getResultList();
		}
		@Transactional
		@Override
		public int updateBillDataTOMM(String month, String meterno,
				String kwh, String kvh, String kva, String pf,
				String todayDate, String t1kwh, String t2kwh, String t3kwh,
				String t4kwh, String t5kwh, String t6kwh, String t7kwh,
				String t8kwh, String t1kvah, String t2kvah, String t3kvah,
				String t4kvah, String t5kvah, String t6kvah, String t7kvah,
				String t8kvah, String t1kva, String t2kva, String t3kva,
				String t4kva, String t5kva, String t6kva, String t7kva,
				String t8kva, String kw, String flag,String billDatechk) {
			int count=0;
			Connection conn = null;
			Statement statement=null;
			 try {
		String qry ="UPDATE meter_data.metermaster SET XCURRDNGKWH = '"+kwh+"',XCURRRDNGKVAH = '"+kvh+"',XCURRDNGKVA = '"+kva+"',XPF = '"+pf+"',RTC = 1,XMLDATE = TO_DATE ('"+todayDate+"', 'dd-MM-yyyy'),\n" +
				"T1KWH = '"+t1kwh+"',T2KWH = '"+t2kwh+"',T3KWH = '"+t3kwh+"',T4KWH = '"+t4kwh+"',T5KWH = '"+t5kwh+"',T6KWH = '"+t6kwh+"',T7KWH = '"+t7kwh+"',\n" +
				"T8KWH = '"+t8kwh+"',T1KVAH = '"+t1kvah+"',T2KVAH = '"+t2kvah+"',T3KVAH = '"+t3kvah+"',T4KVAH = '"+t4kvah+"',T5KVAH = '"+t5kvah+"',T6KVAH = '"+t6kvah+"',T7KVAH = '"+t7kvah+"',T8KVAH = '"+t8kvah+"',T1KVAV = '"+t1kva+"',T2KVAV = '"+t2kva+"',T3KVAV = '"+t3kva+"',"
				+ "T4KVAV = '"+t4kva+"',T5KVAV = '"+t5kva+"',T6KVAV = '"+t6kva+"',T7KVAV = '"+t7kva+"',T8KVAV = '"+t8kva+"',FLAG = '"+flag+"',XT1KWH = '"+t1kwh+"',XT2KWH = '"+t2kwh+"',XT3KWH = '"+t3kwh+"',XT4KWH = '"+t4kwh+"',XT5KWH = '"+t5kwh+"',XT6KWH = '"+t6kwh+"',"
				+ "XT7KWH = '"+t7kwh+"',XT8KWH = '"+t8kwh+"',XT1KVAH = '"+t1kvah+"',XT2KVAH = '"+t2kvah+"',XT3KVAH = '"+t3kvah+"',XT4KVAH = '"+t4kvah+"',XT5KVAH = '"+t5kvah+"',XT6KVAH = '"+t6kvah+"',XT7KVAH = '"+t7kvah+"',XT8KVAH = '"+t8kvah+"'\n" +
				" , RDATE = TO_DATE ('"+billDatechk+"','YYYY-MM-DD') WHERE RDNGMONTH = '"+month+"' AND METRNO = '"+meterno+"'  and XCURRDNGKWH is NULL";
		System.out.println("UPdate qry-->"+qry);
		count=getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
			 }
			
		/*try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				try
				{
					conn=DriverManager.getConnection("jdbc:oracle:thin:@182.18.140.195:1521:orcl","DHBVN","bcits");
				}
				
				catch(Exception e)
				{
					e.printStackTrace();
				}
				 statement=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
				int rs=statement.executeUpdate(qry);
				System.out.println("Total count --->"+rs);
				//int count=postgresMdas.createNativeQuery(qry).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 } catch (Exception e) {
					e.printStackTrace();
					}
			finally
			{
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}*/
			return count;
		}

		@Override
		public void updateDataToMetrMaster() {
			
			Date d1=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMM");
			String billdate=sdf.format(d1)+"-01 00:00:00";
			String month=sdf1.format(d1);
			
			List metrsList=null;
			String qry="";
			try {
				qry="SELECT meter_number,kwh,kvah,kva,sys_pf_billing,kwh_tz1,kwh_tz2,kwh_tz3,kwh_tz4,kwh_tz5,kwh_tz6,kwh_tz7,kwh_tz8,\n" +
						"kvah_tz1,kvah_tz2,kvah_tz3,kvah_tz4,kvah_tz5,kvah_tz6,kvah_tz7,kvah_tz8,\n" +
						"kva_tz1,kva_tz2,kva_tz3,kva_tz4,kva_tz5,kva_tz6,kva_tz7,kva_tz8,demand_kw,billing_date\n" +
						" FROM meter_data.bill_history WHERE billing_date='"+billdate+"' AND  meter_number \n" +
						"in(SELECT metrno FROM meter_data.metermaster WHERE rdngmonth='"+month+"')";
						
				System.out.println("select qry-->"+qry);
				metrsList=postgresMdas.createNativeQuery(qry).getResultList();
				String meterno=null;String kwh=null;String kvh=null;String kva=null;String pf=null;String t1kwh=null;
				String t2kwh=null;String t3kwh=null;String t4kwh=null;String t5kwh=null;String t6kwh=null;String t7kwh=null;String t8kwh=null;String t1kvah=null;
				String t2kvah=null;String t3kvah=null;String t4kvah=null;String t5kvah=null;String t6kvah=null;String t7kvah=null;String t8kvah=null;String t1kva=null;
				String t2kva=null;String t3kva=null;
				String t4kva=null;String t5kva=null;String t6kva=null;String t7kva=null;String t8kva=null;String kw=null;String flag=null;String billDatechk=null;
				System.out.println("metrs size-->"+metrsList.size());
				for(int i=0;i<metrsList.size();i++)
				{
					Object[] data=(Object[]) metrsList.get(i);
					 meterno=(String)data[0];
					 if(data[1]!=null)
					 {
						 kwh=data[1].toString();
						 
					 }
					if(data[2]!=null)
					{
						kvh=   data[2].toString();
					}
					if(data[3]!=null)
					{
						 kva=    data[3].toString();
					}
					if(data[4]!=null)
					{
						 pf=      data[4].toString();
					}
					if(data[5]!=null)
					{
						 t1kwh= data[5].toString();
					}
					if(data[6]!=null)
					{
						t2kwh= data[6].toString();
					}
					if(data[7]!=null)
					{
						t3kwh= data[7].toString();
					}
					if(data[8]!=null)
					{
						 t4kwh= data[8].toString();
					}
					if(data[9]!=null)
					{
						 t5kwh= data[9].toString();
					}
					if(data[10]!=null)
					{
						 t6kwh= data[10].toString();
					} 
					if(data[11]!=null)
					{
						 t7kwh= data[11].toString();
					} 
					if(data[12]!=null)
					{
						 t8kwh= data[12].toString();
					} 
					if(data[13]!=null)
					{
						 t1kvah= data[13].toString();
					}
					if(data[14]!=null)
					{
						 t2kvah= data[14].toString();
					}
					if(data[15]!=null)
					{
						t3kvah= data[15].toString();
					}
					if(data[16]!=null)
					{
						 t4kvah= data[16].toString();
					}
					if(data[17]!=null)
					{
						 t5kvah= data[17].toString();
					}
					if(data[18]!=null)
					{
						 t6kvah= data[18].toString();
					}
					if(data[19]!=null)
					{
						 t7kvah= data[19].toString();
					}
					if(data[20]!=null)
					{
						t8kvah= data[20].toString();
					}
					if(data[21]!=null)
					{
						 t1kva= data[21].toString();
					}
					if(data[22]!=null)
					{
						 t2kva= data[22].toString();
					}
					if(data[23]!=null)
					{
						 t3kva= data[23].toString();
					}
					if(data[24]!=null)
					{
						 t4kva= data[24].toString();
					}
					if(data[25]!=null)
					{
						t5kva= data[25].toString();
					}
					if(data[26]!=null)
					{
						 t6kva= data[26].toString();
					}
					if(data[27]!=null)
					{
						 t7kva= data[27].toString();
					}
					if(data[28]!=null)
					{
						t8kva= data[28].toString();
					}
					if(data[29]!=null)
					{
						 kw= data[29].toString();
					}
					 flag="3";
					 billDatechk= data[30].toString();
					 
					SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy");
					SimpleDateFormat billtime=new SimpleDateFormat("yyyy-MM-dd");
					
					Date d2=billtime.parse(billDatechk);
					Date d3=new Date();
					String xmldate=sdf2.format(d3);
					String rdate=billtime.format(d2);
					
					try {
					
					int count=updateBillDataTOMM(month, meterno, kwh, kvh, kva, pf, xmldate, t1kwh, t2kwh, t3kwh, t4kwh, t5kwh, t6kwh, t7kwh, t8kwh, t1kvah, t2kvah, t3kvah, t4kvah, t5kvah, t6kvah, t7kvah, t8kvah, t1kva, t2kva, t3kva, t4kva, t5kva, t6kva, t7kva, t8kva, kw, flag, rdate);
					} catch (Exception e) {
						e.printStackTrace();
						
					}
					
					meterno=null; kwh=null; kvh=null; kva=null; pf=null; t1kwh=null;t2kwh=null; t3kwh=null; t4kwh=null; 
					t5kwh=null; t6kwh=null; t7kwh=null; t8kwh=null; t1kvah=null;t2kvah=null; t3kvah=null; t4kvah=null; 
					t5kvah=null; t6kvah=null; t7kvah=null; t8kvah=null; t1kva=null;t2kva=null; t3kva=null;t4kva=null; 
					t5kva=null; t6kva=null; t7kva=null; t8kva=null; kw=null; flag=null; billDatechk=null;
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}

		@Override
		public MeterMaster getMeterDataByAccno(HttpServletRequest req) 
		{
			try 
			{
				String accno=req.getParameter("accno");
			return	postgresMdas.createNamedQuery("MeterMaster.searchByAccNo",MeterMaster.class).setParameter("accNo", accno).setParameter("rdgMonth",201811).getSingleResult();
				
			} catch (Exception e) 
			{
				return null;
			}
			
		}

		@Override
		public JSONObject getBillDataByMeterNo(HttpServletRequest req) {

			System.out.println("insde bill queryy");
			JSONObject obj=new JSONObject();
			try 
			{
				String meterno=req.getParameter("meterno");
				String rdngmonth=req.getParameter("billmonth");
				String knoFromBijili=req.getParameter("kno");
				String kno="";
				System.out.println("meterno--"+meterno+" rdng--"+rdngmonth);
			int	rdngmonth1=Integer.parseInt(rdngmonth);
				List<MeterMaster> list=null;
				try {
		           list= postgresMdas.createNamedQuery("MeterMaster.searchByMtrNo").setParameter("mtrNo", meterno).setParameter("rdgMonth",rdngmonth1).getResultList();
			
				} catch (Exception e) {
					e.printStackTrace();
				}
			System.err.println(list.size());
			if (list.size() > 0) 
			{
				kno=list.get(0).getMaster().getKno();
				if(!(kno.equals(knoFromBijili)))
				{
					obj.put("missmatchdata", "missmatchdata");
				}
				else
				{
					obj.put("kno", list.get(0).getMaster().getKno());
					obj.put("meterno", list.get(0).getMetrno());
					obj.put("billmonth", list.get(0).getRdngmonth());
					obj.put("kwh", list.get(0).getXcurrdngkwh());
					obj.put("kva", list.get(0).getXcurrdngkva());
					obj.put("kvah", list.get(0).getXcurrrdngkvah());
				}
				
			}
			else
			{
				obj.put("noData", "noData");
			}
			
			} catch (Exception e) 
			{
				e.printStackTrace();
				
					try {
						return obj.put("noData", "noData");
					} catch (org.codehaus.jettison.json.JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
			return obj;
		}

		@Override
		public List getMetrConfigData(String metrno) {
			List list=null;
			try {
				//String qry="SELECT circle,division,address,accno,meterno,g2value,g3value \n" +
						//"FROM meter_data.rtc WHERE rdngmonth='"+month+"' GROUP BY circle,division,address,accno,meterno,g2value,g3value";
			//	String qry="SELECT m.circle,m.division,m.subdiv,m.address1,c.accno,c.meterno,c.readdate,c.meter_date  FROM meter_data.cdf_data c, meter_data.master m WHERE c.accno=m.accno and  c.billmonth='"+month+"' and c.accno!='4850011000' and c.readdate!=c.meter_date";
				
				
				String qry="SELECT * FROM meter_data.meter_lifecycle WHERE meter_no='"+metrno+"'";
				System.out.println("getMetrConfigData query=="+qry);
			list=postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return list;
		}
		
		@Override
		public List getMetrSyncData(int billmonth) {
			List<MeterMaster> list=null;
			ArrayList<Map<String, Object>> list1=new ArrayList();
			String rmsflag="1";
			try {
		           //list= postgresMdas.createNamedQuery("MeterMaster.getSyncDataAMI").setParameter("month",billmonth).setParameter("rmsflag", rmsflag).getResultList();
				
		           String qry="SELECT m.metrno,m.accno,m1.name,m.circle,m.division,m.subdiv,m.xcurrdngkwh,m.rdngmonth FROM meter_data.metermaster m, meter_data.master m1 WHERE m.accno=m1.accno AND m.rdngmonth='"+billmonth+"' and m.rmsflag='1'";
		          
		           System.out.println("bill--"+qry);
		           list=postgresMdas.createNativeQuery(qry).getResultList();
		           
		           for (Iterator iterator1 = list.iterator(); iterator1.hasNext();)
				    {
				    	Map<String, Object> data=new HashMap<>();
				    	Object[] obj = (Object[]) iterator1.next();
						 if (list.size() > 0) 
						 {
							 //Double kwh=null;
							 String kwh="";
							//System.out.println("kwh--"+obj[6]);
							 if(obj[6]!=null )
							 {
								Double wh=Double.parseDouble(String.valueOf(obj[6]));
								//double dkwh= wh/1000;
								kwh=new DecimalFormat("#.##").format(wh);
								//System.out.println("xck--"+kwh);
							 } 
							 
							 
							
							 data.put("METERNO", obj[0]);
							 data.put("ACCNO", obj[1]);
							 data.put("NAME", obj[2]);
							 data.put("CIRCLE", obj[3]);
							 data.put("DIVISION", obj[4]);
							 data.put("SDONAME", obj[5]);
							 data.put("KWH", kwh);
							 data.put("BILLMONTH", obj[7]);
							 list1.add(data);
						 }
				    }
			
			} catch (Exception e) {
					e.printStackTrace();
				}
				
			return list1;
		}

		@Override
		public List getMetrDetails(String meterno) {
			Date d1=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			String rdngmonth=sdf.format(d1);
			List list=null;
			try {
				//String qry="select * from meter_data.metermaster m WHERE rdngmonth ='"+rdngmonth+"' AND metrno='"+meterno+"'";
				/*String qry= "SELECT m.metrno,m.phase,n.manufacturer_name,n.firmware_version,n.meter_type,n.meter_catagory,n.device_id,\n" +
							" n.current_rating,n.name_plate2,n.name_plate3,n.year_of_manufacture\n" +
							" FROM meter_data.name_plate n,meter_data.metermaster m\n" +
							" WHERE m.metrno=n.meter_serial_number and rdngmonth='"+rdngmonth+"' AND m.metrno='"+meterno+"'";*/
				String qry="SELECT m.mtrno,n.hardware_version,n.manufacturer_name,n.firmware_version,n.meter_type,n.meter_catagory,n.node_id,\n" +
							" n.current_rating,n.node_id,n.node_id,n.year_of_manufacture\n" +
							" FROM meter_data.name_plate n,meter_data.master_main m\n" +
							" WHERE m.mtrno=n.meter_serial_number  AND m.mtrno='"+meterno+"'";
				
				//System.out.println("getMetrDetails query=="+qry);
			list=postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return list;
		}

		@Override
		public List getCirclesByZone(String zone) {
			List list = null;
			try {
				System.out.println("zone--"+zone);
				if(zone.equalsIgnoreCase("All"))
				{
					zone="%";
				}
				String sql = "SELECT DISTINCT circle from meter_data.master WHERE zone like '" + zone + "'";
				System.out.println("circle sql--"+sql);
				list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

		@Override
		public List getDivisionByCircleMDM(String zone,
				String circle) {
			List list = null;
			try {
				if(zone.equalsIgnoreCase("All"))
				{
					zone="%";
				}
				if(circle.equalsIgnoreCase("All"))
				{
					circle="%";
				}
				String sql = "SELECT DISTINCT division from meter_data.master WHERE zone like '" + zone
						+ "' and circle LIKE '" + circle + "'";
				//System.out.println("circle--MDM- : "+sql);
				list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				System.out.println(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

		@Override
		public List getSubdivByDivisionByCircleMDM(String zone, String circle,
				String division) {
			System.out.println("DIVISIONS---"+division);
			List list = null;
			String[] divs;
			divs=division.split(",");
			try {
				if(zone.equalsIgnoreCase("All"))
				{
					zone="%";
				}
				if(circle.equalsIgnoreCase("All"))
				{
					circle="%";
				}
				if(division.equalsIgnoreCase("All"))
				{
					division="%";
				}
			      if(divs.length==1){
			    	  String sql1="SELECT DISTINCT subdiv from meter_data.master WHERE zone like '" + zone
								+ "' and circle LIKE '" + circle + "' and division like '" + division + "'";
			    	  System.out.println(sql1);
						list = getCustomEntityManager("postgresMdas").createNativeQuery(sql1).getResultList();

			      }
			      else{
				//for(int i=0;i<divs.length;i++){
				String sql ="SELECT DISTINCT subdiv from meter_data.master WHERE zone like '%' and"
						+ " circle LIKE '"+circle+"' and division = '"+divs[0]+"' OR DIVISION='"+divs[1]+"'";
						
						/*"SELECT DISTINCT subdiv from meter_data.master WHERE zone like '" + zone
						+ "' and circle LIKE '" + circle + "' and division like '" + division + "'";*/
				System.err.println("sdoname--"+sql);
				list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			}
				
				//list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		@Override
		public List<String> getmeternoBySubDivByDivisionMDM(String zone, String circle, String division, String subdivision) {
			List<String> list = null;
			try {
				if(zone.equalsIgnoreCase("JAIPUR"))
				{
					zone="%";
				}
				
				if(circle.equalsIgnoreCase("All"))
				{
					circle="%";
				}
				if(division.equalsIgnoreCase("All"))
				{
					division="%";
				}
				
				if(subdivision.equalsIgnoreCase("All"))
				{
						
				   subdivision="%";
			    }
				
				String sql = "SELECT  mtrno from meter_data.master_main WHERE zone like '" + zone
						+ "' and circle LIKE '" + circle + "' and division like '" + division + "' and subdivision like '" + subdivision +"'";
				System.err.println("mtrno--"+sql);
				list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		

		//get meter list by sub division
		@Override
		public List getMeterListBySubDiv(String zone,String circle, String division,
				String subdivision) {
			List list = null;
			try {
				
				if(circle.equalsIgnoreCase("All"))
				{
					circle="%";
				}
				if(division.equalsIgnoreCase("All"))
				{
					division="%";
				}
				if(subdivision.equalsIgnoreCase("All"))
				{
					subdivision="%";
				}
				String sql = "select DISTINCT metrno from meter_data.metermaster WHERE circle like '" + circle +"' AND division like '" + division +"' AND subdiv like '" + subdivision + "'";
				//String sql = "SELECT DISTINCT subdiv from meter_data.master WHERE zone like '" + zone + "' and circle LIKE '" + circle + "' and division like '" + division + "'";
				System.err.println("sdoname--"+sql);
				list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		//updating log damage report
		@Override
		public int updateLogDamageIssue(String meterno, String dmgIssue) {
		
			System.out.println("inside damage report");
			Date d1=new Date();
			int count=0;
			long currtime = System.currentTimeMillis();
			 Timestamp uppdatetime = new Timestamp(currtime);
			String qry="";
			try {
				qry="UPDATE meter_data.master_main SET REMARKS='"+dmgIssue+"', remarks_date ='"+uppdatetime+"' WHERE mtrno='"+meterno+"'";
				count=getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}

		@Override
		public List findALLrtc(String selectedDateName, String circle,
				String division, String subdivision) {
			List list=null;
			try {
				//String qry="SELECT m.circle,m.division,m.subdiv,m.address1,c.accno,c.meterno,c.readdate,c.meter_date  FROM meter_data.cdf_data c, meter_data.master m WHERE c.accno=m.accno and  c.billmonth='"+month+"' and c.accno!='4850011000' and c.readdate!=c.meter_date";
				
				String qry="SELECT DISTINCT on (c.meter_number) c.meter_number,\n" +
							"	M .circle,\n" +
							"	M .division,\n" +
							"	M .subdiv,\n" +
							"	M .address,\n" +
							"	M .metrno,\n" +
							"	C .read_time,\n" +
							"	C .time_stamp\n" +
							"FROM\n" +
							"	meter_data.amiinstantaneous C,\n" +
							"	meter_data.metermaster M\n" +
							"WHERE\n" +
							"	M .metrno = c.meter_number AND\n" +
							"  M.circle='"+circle+"' AND M.division='"+division+"' AND M.subdiv='"+subdivision+"' AND to_char(read_time, 'yyyy-MM-dd')='"+selectedDateName+"'\n" +
							
							"AND C .read_time != C .time_stamp";
				
				System.out.println("rtc query=="+qry);
			list=postgresMdas.createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return list;
		}

		@Override
		public List getAddress(String meterno) {
		List list=null;
			System.out.println("inside getAddress ");
			Date d1=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			String billmonth=sdf.format(d1);
			String qry="";
			try {
				qry="SELECT name,address1,emailId,phoneno FROM meter_data.master m,meter_data.metermaster mt WHERE rdngmonth='"+billmonth+"' \n" +
						"AND m.accno=mt.accno AND mt.metrno='"+meterno+"'";
				System.out.println("getAddress---"+qry);
				list=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

		@Override
		public List getLocationBySdocode(String sdocode,String meterNo) {
			List LocData=new ArrayList<>();
			List MtrData=null;
			String Locqry="";
			String Mtrqry1="";
			try {
				Locqry="SELECT zone,circle,division,subdivision FROM meter_data.amilocation WHERE sitecode='"+sdocode+"'";
				Mtrqry1="SELECT mtrno FROM meter_data.master_main WHERE mtrno='"+meterNo+"'";
				System.out.println("meter query--"+Mtrqry1);
				System.out.println("location details query--"+Locqry);
				MtrData=(List) getCustomEntityManager("postgresMdas").createNativeQuery(Mtrqry1).getResultList();
				if(MtrData.size()==0)
				{
				LocData=getCustomEntityManager("postgresMdas").createNativeQuery(Locqry).getResultList();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return LocData;
		}

		@Override
		public List getAllZonesInAmiLocation() {
			List result=null;
			String qry="";
			try {
				qry="SELECT DISTINCT zone FROM meter_data.amilocation  WHERE zone IS NOT NULL ORDER BY zone";
				result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		

		@Override
		public List getAllCirclesInAmiLocation() {
			List result=null;
			String qry="";
			try {
				qry="SELECT DISTINCT circle FROM meter_data.amilocation  WHERE circle IS NOT NULL ORDER BY circle";
				result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		

		@Override
		public List getAllDivisionsInAmiLocation() {
			List result=null;
			String qry="";
			try {
				qry="SELECT DISTINCT division FROM meter_data.amilocation  WHERE division IS NOT NULL ORDER BY division";
				result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		

		@Override
		public List getAllSubDivisionsInAmiLocation() {
			List result=null;
			String qry="";
			try {
				qry="SELECT DISTINCT subdivision FROM meter_data.amilocation  WHERE subdivision IS NOT NULL ORDER BY subdivision";
				result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		public Object getCirclesByCircleCode(String circleCode) {
			List result=null;
			String qry="";
			try {

				qry="select DISTINCT circle from meter_data.amilocation WHERE circle_code='"+circleCode+"'";
				result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
			
		}

		@Override
		public List findmonthData2(String billmonth,
				HttpServletRequest request, ModelMap model) {
			List list=new ArrayList<>();
			
			try {
				
		           String qry="select * from meter_data.consumerreadings m WHERE billmonth ='"+billmonth+"' ORDER BY  meterno ASC";
		          
		           list=postgresMdas.createNativeQuery(qry).getResultList();
		           
		           
				    }
			
			 catch (Exception e) {
					e.printStackTrace();
					return list;
				}
				
			return list;
		}

		
		
}
