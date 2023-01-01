package com.bcits.serviceImpl;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.ConsumerMasterEntity;
import com.bcits.entity.ConsumerReadingEntity;
import com.bcits.entity.Master;
import com.bcits.entity.MeterInventoryEntity;
import com.bcits.entity.MeterMaster;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.service.ConsumerMasterService;
import com.bcits.service.MeterMasterService;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

@Repository
public class ConsumerMasterServiceImpl extends GenericServiceImpl<ConsumerMasterEntity> implements ConsumerMasterService{
	
	@Autowired
	private MeterMasterService meterMasterService;
	

	/*@Override
	public void addNewConsumrData(Master master, HttpServletRequest request,
			ModelMap model) {
		try {
		MDMLogger.logger.info("inside addNewConsumerData method");
		System.out.println("name--"+master.getName()+"master.getBuilding_name();=="+master.getBuilding_name());
		master.setAccno(master.getAccno());
		master.setBuilding_name(master.getBuilding_name());
		save(master);
		model.put("result", "Consumer data added succesfully");
		} catch (Exception e) {
			model.put("result", "Failure while Adding ConsumerData");
			e.printStackTrace();
		}
		Master master1=new Master();
		model.put("newConsConnection", master1);
	}*/

	@Override
	public List<Master> getZone() {
		List<Master> zones=null;
		String qry1="";
		try {
			qry1="SELECT DISTINCT ZONE FROM meter_data.MASTER WHERE ZONE IS NOT NULL ORDER BY ZONE";
			//System.out.println("get division-->"+qry1);
			zones=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zones;
	}

	@Override
	public List<Master> getCircleByZone(String zone) {
		List<Master> circle=null;
		String qry1="";
		try {
			qry1="SELECT DISTINCT CIRCLE FROM meter_data.amilocation WHERE ZONE LIKE '"+zone+"' AND CIRCLE IS NOT NULL ORDER BY CIRCLE";
			System.err.println(qry1);
			circle=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return circle;
	}
	
	@Override
	public List<Master> getCircleByZonebyReg(String zone) {
		List<Master> circle=null;
		String qry1="";
		try {
			qry1="SELECT DISTINCT CIRCLE FROM meter_data.amilocation WHERE ZONE LIKE 'ERODE' AND CIRCLE IS NOT NULL ORDER BY CIRCLE";
			System.err.println(qry1);
			circle=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return circle;
	}

	@Override
	public List<Master> getDivByCircle(String circle) {
		List<Master> division=null;
		String qry1="";
		try {
			qry1="SELECT DISTINCT DIVISION FROM meter_data.amilocation WHERE CIRCLE LIKE '"+circle+"' AND DIVISION IS NOT NULL ORDER BY DIVISION";
			//System.out.println("get division-->"+qry1);
			division=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return division;
	}

	@Override
	public List<Master> getSubdivByDivision(String division) {
		List<Master> Subdivision=null;
		String qry1="";
		try {
			qry1="SELECT DISTINCT subdivision FROM meter_data.amilocation WHERE DIVISION LIKE '"+division+"' AND subdivision IS NOT NULL ORDER BY subdivision";
			Subdivision=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Subdivision;
	}

	@Override
	public void searchByAMRAccNo(HttpServletRequest request,MeterMaster meterMaster, ModelMap model) {
		List<MeterMaster> list = null;
				try {
			list = postgresMdas.createNamedQuery("MeterMaster.searchByAccNo").setParameter("accNo", meterMaster.getAccno()).setParameter("rdgMonth", meterMaster.getRdngmonth()).getResultList();
		//System.out.println("lisss size==>"+list.size());
		if(list.size()>0)
		{
			 List circle=getCircleByZone(list.get(0).getMaster().getZone());
			 List division=getDivByCircle(list.get(0).getMaster().getCircle());
			 List sdoname=getSubdivByDivision(list.get(0).getMaster().getDivision());
			// System.out.println(circle.toString());
			// System.out.println(division.toString());
			// System.out.println(sdoname.toString());
			 model.put("circle",circle);
			 model.put("division",division);
			 model.put("sdoname",sdoname);
			 model.put("newConnectionMeterMaster", list.get(0));
		}
		else{
			meterMaster=new MeterMaster();
			model.put("result1", "No data found for this accno");
			model.put("newConnectionMeterMaster", meterMaster);
			meterMaster.setRdngmonth(meterMasterService.getMaxRdgMonthYear(request));
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateAMRConnectionData(MeterMaster newConnectionMeterMaster,HttpServletRequest request, ModelMap model) 
	{
		//System.out.println("in amr update method");
		//System.out.println("accno-->"+newConnectionMeterMaster.getAccno());
		int metermasterRes=0;
		int masterUpdate=0;
		try {
		
		metermasterRes = postgresMdas
				.createNamedQuery("MeterMaster.updateAMRConnection")
				.setParameter("Rdngmonth",newConnectionMeterMaster.getRdngmonth())
				.setParameter("accno", newConnectionMeterMaster.getAccno())
				.setParameter("modem_no",newConnectionMeterMaster.getModem_no())
				.setParameter("meterno", newConnectionMeterMaster.getMetrno())
				.setParameter("mf", newConnectionMeterMaster.getMf())
				.setParameter("ctrd", newConnectionMeterMaster.getCtrd())
				.setParameter("ctrn", newConnectionMeterMaster.getCtrn())
				.setParameter("prkwh", newConnectionMeterMaster.getPrkwh())
				.setParameter("mtrmake", newConnectionMeterMaster.getMtrmake())
				.executeUpdate();
		
		if(metermasterRes>0)
		{
			masterUpdate=postgresMdas.createNamedQuery("Master.UpdateAMRDetails")
					.setParameter("accno", newConnectionMeterMaster.getAccno())
					.setParameter("mobileno", newConnectionMeterMaster.getMaster().getPhoneno2())
					.setParameter("zone", newConnectionMeterMaster.getMaster().getZone())
					.setParameter("circle", newConnectionMeterMaster.getMaster().getCircle())
					.setParameter("division", newConnectionMeterMaster.getMaster().getDivision())
					.setParameter("subdivision", newConnectionMeterMaster.getMaster().getSubdiv())
					.setParameter("address", newConnectionMeterMaster.getMaster().getAddress1())
					.setParameter("status", newConnectionMeterMaster.getMaster().getStatus())
					.setParameter("cd", newConnectionMeterMaster.getMaster().getContractdemand())
					.setParameter("sanload", newConnectionMeterMaster.getMaster().getSanload())
					.setParameter("supplytype", newConnectionMeterMaster.getMaster().getSupplytype())
					.setParameter("suuplyVoltage", newConnectionMeterMaster.getMaster().getSupplyvoltage())
					.setParameter("industryType", newConnectionMeterMaster.getMaster().getIndustrytype())
					.setParameter("tarrifcode", newConnectionMeterMaster.getMaster().getTariffcode())
					.setParameter("consumerStatus", newConnectionMeterMaster.getMaster().getConsumerstatus())
					.setParameter("remarks", newConnectionMeterMaster.getMaster().getRemarks()).executeUpdate();
		}
		if (metermasterRes > 0 && masterUpdate > 0)
		{
			model.put("result", "Data modification done successfully");
			model.put("newConnectionMeterMaster", new MeterMaster());
		} else 
		{
			model.put("result","Data Modification Not Done");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ConsumerMasterEntity> checkMtrnoExist(String meterno) {
		List<ConsumerMasterEntity> result=new ArrayList<ConsumerMasterEntity>();
		try {
		result=postgresMdas.createNamedQuery("CONSUMERMASTER.getdataBymeterno").setParameter("meterno", meterno).getResultList();
		} catch (Exception e) {
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMasterEntity> checkAccnoExist(String accno) {
		List<ConsumerMasterEntity> result=new ArrayList<ConsumerMasterEntity>();
		try {
		result=postgresMdas.createNamedQuery("CONSUMERMASTER.getdataByAccno").setParameter("accno", accno).getResultList();
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public List<ConsumerMasterEntity> checkKnoExist(String kno) {
		List<ConsumerMasterEntity> result=new ArrayList<ConsumerMasterEntity>();
		try {
		String qry="SELECT * FROM meter_data.consumermaster where kno='"+kno+"'";
		//System.out.println(qry);
		List result1=postgresMdas.createNativeQuery(qry).getResultList();
		//System.out.println(result1.size());
		result=postgresMdas.createNamedQuery("CONSUMERMASTER.getdataByKno",ConsumerMasterEntity.class).setParameter("kno", kno).getResultList();
		//System.out.println("--"+result.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//update consumer data
	/*@Override
	public List<ConsumerMasterEntity> updateConsumerData(
			ConsumerMasterEntity consumer, HttpServletRequest request,
			ModelMap model) {
		List<String> exceReport=new ArrayList<>();
		int metermasterRes =0;
		try {
			long currtime=System.currentTimeMillis();
			consumer.setEntryby(request.getSession().getAttribute("username")+"");
			consumer.setEntrydate(new java.sql.Date(currtime));
				metermasterRes = postgresMdas
				.createNamedQuery("Consumer.updateModifyData")
				.setParameter("accno",consumer.getAccno())
				.setParameter("kno",consumer.getKno())
				.setParameter("meterno",consumer.getMeterno())
				.setParameter("name", consumer.getName())
				.setParameter("address1", consumer.getAddress1())
				.setParameter("phoneno", consumer.getPhoneno())
				.setParameter("email", consumer.getEmail())
				.setParameter("cd", consumer.getCd())
				.setParameter("kworhp", consumer.getKworhp())
				.setParameter("sanload", consumer.getSanload())
				.setParameter("mf", consumer.getMf())
				.setParameter("supplyvoltage", consumer.getSupplyvoltage())
				.setParameter("tadesc", consumer.getTadesc())
				.setParameter("tariffcode", consumer.getTariffcode())
				.setParameter("consumerstatus", consumer.getConsumerstatus())
				.setParameter("lat", consumer.getLatitude())
				.setParameter("long", consumer.getLongitude())
				.setParameter("prepaid", consumer.getPrepaid())
				.setParameter("tod", consumer.getTod())
				.setParameter("tou", consumer.getTou())
				.setParameter("billdate", consumer.getBillperiodstartdate())
				.setParameter("updateTime", new java.sql.Date(currtime))
				.setParameter("updateBy",request.getSession().getAttribute("username")+"")
				.setParameter("id", consumer.getId())
				.executeUpdate();
		
		} catch (Exception e) {
			exceReport.add(e.getMessage());
		}
		if(metermasterRes>0)
		{
			model.addAttribute("consumerMasterId",new ConsumerMasterEntity());
			model.addAttribute("result", "Data Updated Sucessfully");
		}
		else
		{
			model.addAttribute("result", "Error while Updating data");
		}
		return null;
	}*/

	@Override
	public List<ConsumerReadingEntity> getReadingData() {
		String kno="210413032532";
		String rdngmonth="201903";
		List<ConsumerReadingEntity> result=new ArrayList<ConsumerReadingEntity>();
		try {
			result=postgresMdas.createNamedQuery("CONSUMERREADINGS.getReadingData").setParameter("kno", kno).setParameter("rdngmonth", rdngmonth).getResultList();
			System.out.println("consumer reading size-"+result.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 
	 * implemented in jsp pages with only circle
	 * (non-Javadoc)
	 * @see com.bcits.service.ConsumerMasterService#getCircle()
	 */
	@Override
	public List<?> getCircle()
	{
		//System.out.println("get-circle-details....");
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT circle from meter_data.amilocation where CIRCLE IS NOT NULL ORDER BY CIRCLE";
			//System.out.println("sql");
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<?> getDivisionByCircle(String circle)
	{
		if(circle.equalsIgnoreCase("All")){
			circle="%";
		}
		//System.out.println("get-division-details....");
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT division from meter_data.amilocation where CIRCLE like '"+circle+"' and division IS NOT NULL ORDER BY division";
			//System.out.println("sql");
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<?> getSubdivByDiv(String division)
	{
		if(division.equalsIgnoreCase("All")){
			division="%";
		}
		System.out.println("get-subdivision-details....");
				List<?> list = null;
				try {
					String sql = "SELECT DISTINCT subdivision from meter_data.amilocation where division like '"+division+"' and subdivision IS NOT NULL ORDER BY subdivision";
					//System.out.println("sql");
					list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
	}

	@Override
	public List<?> getAllOldDismantle(String circle, String divsion,
			String subdivision) {
		if(circle.equalsIgnoreCase("All")){
			circle="%";
		}
		if(divsion.equalsIgnoreCase("All")){
			divsion="%";
		}
		if(subdivision.equalsIgnoreCase("All")){
			subdivision="%";
		}
	String qry="";
	List result=new ArrayList<>();
	try {
		qry="SELECT sm.subdivision,sm.accno,sm.kno,so.consumername,latitude,longitude,premise,\n" +
				"connectiontype,old_metermake,newmetermake,sm.meterno AS oldmeterno,so.meterno as newmeterno,survey_timings\n" +
				"FROM meter_data.survey_output so,meter_data.survey_master sm WHERE so.kno=sm.kno AND sm.circle LIKE '"+circle+"' AND \n" +
				"sm.division LIKE '"+divsion+"' AND sm.subdivision LIKE '"+subdivision+"'";
	//System.out.println("dismental--qry:"+qry);
	result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
	} catch (Exception e) {
		e.printStackTrace();
	}
		return result;
	}

	@Override
	public List<ConsumerMasterEntity> getALLConsumerData() {
		List<ConsumerMasterEntity> result=new ArrayList<ConsumerMasterEntity>(); 
		
		try {
			result=postgresMdas.createNamedQuery("CONSUMERMASTER.getAllConsumerData").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List getAllcirclesByofficecode(String officecode) {
		
		//System.out.println("get-division-details....");
				List<?> list = null;
				try {
					String sql = "SELECT DISTINCT circle FROM meter_data.amilocation WHERE discom_code = '"+officecode+"' ORDER BY circle;";
					System.out.println("sql");
					list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
	}

	@Override
	public List getAlldivisionByofficecode(String circle) {
		
		//System.out.println("get-division-details....");
				List<?> list = null;
				try {
					String sql = "SELECT DISTINCT division FROM meter_data.amilocation WHERE circle LIKE '"+circle+"'; ORDER BY circle;";
					System.out.println("sql");
					list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
	}

	@Override
	public List getAllsubdivisionofficecode(String division) {
		
		//System.out.println("get-division-details....");
				List<?> list = null;
				try {
					String sql = "SELECT DISTINCT subdivision FROM meter_data.amilocation WHERE division LIKE '"+division+"' order by division";
					System.out.println("sql");
					list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
	}
	
	@Override
	public List getAllLocationData(String officeType,String officeCode) {
		
		//System.out.println("get-division-details....");
				List<?> list = new ArrayList<>();
				String qry="";
				try {if (officeType.equalsIgnoreCase("circle")) {
					qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a"
							+ " WHERE circle_code = '"+ officeCode + "' ORDER BY CIRCLE";
				} else if (officeType.equalsIgnoreCase("division")) {
					qry = "SELECT DISTINCT circle,division,subdivision FROM meter_data.amilocation a "
							+ "WHERE division_code = '"+ officeCode + "'  ORDER BY circle,division,subdivision";
				} else if (officeType.equalsIgnoreCase("subdivision")) {
					qry = "SELECT DISTINCT circle,division,subdivision FROM meter_data.amilocation a "
							+ "WHERE sitecode = '"+ officeCode + "'  ORDER BY circle,division,subdivision";
				} else if (officeType.equalsIgnoreCase("discom")){
				qry = "SELECT distinct CIRCLE FROM meter_data.amilocation a"
						+ " WHERE  discom_code = '"+ officeCode+"' ORDER BY CIRCLE";
				}} catch (Exception e) {
					e.printStackTrace();
				}
				
				//System.out.println("consumer location qry--"+qry);
				list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
				return list;
	}

	@Override
	public List getSubstationsBySubdiv(String sdoname) {
		
		System.out.println("getSubstationsBySubdiv--"+sdoname);
				List<?> list = null;
				try {
					/*String sql = "select ss_id,ss_name  from meter_data.substation_details s,meter_data.feederdetails f \n" +
								 "WHERE cast(s.ss_id AS varchar) =f.parentid and parent_subdivision='"+sdoname+"' GROUP BY ss_name,ss_id order by ss_name asc";
					System.out.println("sql--"+sql);*/
					
//					String sql = "select ss_id,ss_name  from meter_data.substation_details s,meter_data.feederdetails f \n" +
//								 "WHERE cast(s.ss_id AS varchar) =f.parentid  GROUP BY ss_name,ss_id order by ss_name asc";
					
					String sql ="select ss_id,ss_name  from meter_data.substation_details s, meter_data.amilocation a where \r\n" + 
							"a.sitecode=s.office_id and a.subdivision like '"+sdoname+"' GROUP BY ss_name,ss_id \r\n" + 
							"order by ss_name asc";
					
					System.out.println("sql--"+sql);
					list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
	}

	@Override
	public List getFeederBysubstation(String ssid) {
		
		//System.out.println("getFeederBysubstation--"+ssid);
				List<?> list = null;
				try {
					String sql = "select  f.fdr_id,f.feedername FROM meter_data.substation_details s,meter_data.feederdetails f\n" +
								"WHERE sstp_id='"+ssid+"' AND f.parentid=CAST(s.ss_id AS VARCHAR) AND f.crossfdr='0' AND f.feedername is NOT NULL AND f.feedername \n" +
								" NOT in('') GROUP BY f.fdr_id,f.feedername ORDER BY fdr_id";
					//System.out.println(sql);
					list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
	}

	@Override
	public List getDtcByFeeders(String fdrid) {
		
		//System.out.println("getDtcByFeeders--"+fdrid);
				List<?> list = null;
				try {
					String sql = "SELECT dt_id,dtname FROM meter_data.dtdetails WHERE parentid='"+fdrid+"' GROUP BY dt_id,dtname ORDER BY dtname";
					//System.out.println(sql);
					list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
	}

	@Override
	public Object getSubFdrDT(String meterno) {

		
		//System.out.println("getDtcByFeeders--"+meterno);
				Object obj=new Object();
				try {
					String sql = "SELECT a.circle,a.division,a.subdivision,f.parentid AS ssid,d.parent_substation AS substationname,d.parentid AS fdrid,\n" +
							"d.parent_feeder AS fdrname,d.dt_id ,d.dtname FROM \n" +
							"meter_data.dtdetails d,meter_data.feederdetails f,meter_data.substation_details s, meter_data.consumermaster c,meter_data.amilocation a\n" +
							"WHERE c.meterno='"+meterno+"' AND s.ss_id=f.parentid AND d.parentid=f.fdr_id AND c.dtcode=d.dt_id AND c.sdocode=cast(a.sitecode AS VARCHAR)\n" +
							"GROUP BY  a.circle,a.division,a.subdivision,f.parentid,d.parent_substation,d.parent_feeder,d.parentid,d.dtname,d.dt_id";
					//System.out.println("get All date===="+sql);
					obj = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getSingleResult();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return obj;
	
	}

	@Override
	public HashMap<String, String> getZoneSitecodeBySubdiv(String circle, String subdiv) {
		HashMap<String, String> h=new HashMap<>();
		String qry = "";
		try {
			
			qry = "SELECT zone,circle,division,subdivision,sitecode FROM meter_data.amilocation WHERE circle='"+circle+"' AND subdivision='"+subdiv+"' ";
		//	System.out.println(qry);
			Object[] list = (Object[]) getCustomEntityManager("postgresMdas")
					.createNativeQuery(qry).getSingleResult();
			
				h.put("ZONE", list[0]+"");
				h.put("CIRCLE", list[1]+"");
				h.put("DIVISION", list[2]+"");
				h.put("SUBDIVISION", list[3]+"");
				h.put("SITECODE", list[4]+"");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return h;

	
	}

	@Override
	public List<?> getCirleConsumerCount(Object circle) {
		List<?> list = null;
		try {
			String sql="select count(name) from meter_data.consumermaster A WHERE sdocode in\n" +
					"(\n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"'\n" +
					")   ";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	
}

	@Override
	public List<?> gettotalConsumerCount() {
		List<?> list = null;
		try {
			String sql="select count(name) from meter_data.consumermaster";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;		
	}

	@Override
	public List<?> getCustomerDetails() {
		List<?> list = null;
		try {
			String sql="select * from meter_data.consumermaster";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getcircleCustomerDetails(List circle,String month) {
		List<?> list = null;
		try {
			String sql="select * from meter_data.consumermaster a,meter_data.consumerreadings b where  a.sdocode in\n" +
					"(\n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"'\n" +
					") and b.billmonth='"+month+"' ";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
}


	@Override
	public List<?> getTotalReadingAvailCons(String month) {
		List<?> list = null;
		try {
			String sql="select count(DISTINCT a.meterno) from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth in(\n" +
					"SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE billmonth='"+month+"') and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getTotalConsumerAvail(String month) {
		List<?> list = null;
		try {
			String sql="SELECT count(DISTINCT a.meterno) FROM\n" +
					"meter_data.consumermaster a\n" +
					", meter_data.consumerreadings b where b.billmonth='"+month+"'";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getTotalNotReadingAvailCons(String month) {
		List<?> list = null;
		try {
			String sql="\n" +
					"SELECT \n" +
					"(SELECT count(DISTINCT meterno) as total_cons FROM meter_data.consumermaster)-\n" +
					"(SELECT count(DISTINCT a.meterno) as reading_avail  FROM  meter_data.consumermaster a, meter_data.consumerreadings b \n" +
					"where a.meterno=b.meterno and b.billmonth='"+month+"'\n" +
					")as reading_not_avail";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void insertConsumerreading() {
		String rdngmonth=new SimpleDateFormat("yyyyMM").format(new Date());
		
		String qry="";
		
		
		try {
			qry="INSERT INTO meter_data.consumerreadings( id,accno,kno,meterno,billmonth,rdate,kwh,kvah,kva,timestamp,flag) \n" +
				"(SELECT nextval('meter_data.consumerreading_seq'),\n" +
				"A.accno,A.kno,A.meter_number,A.billmonth,A.billing_date,A.kwh,A.kvah,A.kva,(now()) as time,0 as flag FROM\n" +
				"(SELECT CASE WHEN c.kno IS NULL THEN c.accno ELSE c.kno end AS kno,c.accno,b.meter_number,to_char(b.billing_date,'yyyyMM') as billmonth,b.billing_date,kwh/1000 as kwh ,kvah/1000 as kvah,kva/1000 as kva \n" +
				"FROM meter_data.bill_history b,meter_data.consumermaster c WHERE\n" +
				" to_char(b.billing_date,'HH24:MI:SS')='00:00:00' AND to_char(b.billing_date,'yyyyMM')='"+rdngmonth+"' AND b.meter_number=c.meterno AND \n" +
				" b.meter_number NOT IN (SELECT DISTINCT meterno FROM meter_data.consumerreadings WHERE billmonth='"+rdngmonth+"') AND c.accno IS NOT NULL )A);";
				//System.out.println("consumer reading qry--"+qry);
		int count=postgresMdas.createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<?> getTotalCircleReadingAvailCons(List circle,String month) {
		List<?> list = null;
		try {
			String sql="select count(DISTINCT a.meterno) from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth in( SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE billmonth='"+month+"') and a.sdocode in( SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"' \n" +
					") and   a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getTotalCircleConsumerAvail(List circle,String month) {
		List<?> list = null;
		try {
			String sql="SELECT count(DISTINCT a.meterno) FROM  meter_data.consumermaster a where  a.sdocode in   \n" +
					"(   \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"'   \n" +
					") ";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getTotalCircleNotReadingAvailCons(List circle,String month) {
		List<?> list = null;
		try {
			String sql=" SELECT(\n" +
					"SELECT count(DISTINCT a.meterno) as total_hours FROM meter_data.consumermaster a,meter_data.consumerreadings b where b.billmonth in( SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE billmonth='"+month+"') and  a.sdocode in (SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"')\n" +
					")-  \n" +
					"(\n" +
					"SELECT count(DISTINCT a.meterno) as reading_avail  FROM  meter_data.consumermaster a,meter_data.								consumerreadings b where  b.billmonth in( SELECT cast(billmonth as text) from meter_data.consumerreadings \n" +
					"WHERE billmonth='"+month+"') and  a.sdocode in(SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"') and a.meterno=b.meterno\n" +
					")as reading_not_avail ; \n" +
					" ";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List getReadingCustomerDetails(String month) {
		List<?> list = null;
		System.err.println("getReadingCustomerDetails"+month);
		try {
			String sql="select * from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth in(\n" +
					"SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE billmonth='"+month+"') and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List getNotReadingCustomerDetails(String month) {
		List<?> list = null;
		try {
			String sql="SELECT  a.meterno,a.kno,a.accno FROM meter_data.consumermaster a where a.meterno  not in (select b.meterno from meter_data.consumerreadings b where b.billmonth='"+month+"')";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getRMSUpload(String month) {
		List<?> list = null;
		try {
			String sql="select \"count\"(CASE WHEN b.\"flag\"=1 THEN 1 END) as rms_synced\n" +
					"from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth='"+month+"' and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getRMSpending(String month) {
		List<?> list = null;
		try {
			String sql="select \"count\"(CASE WHEN b.\"flag\"=0 THEN 1 END) as rms_synced\n" +
					"from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth='"+month+"' and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List getRMSUploadDetails(String monthAlt) {
		List<?> list = null;
		try {
			String sql="SELECT * from meter_data.consumerreadings a,meter_data.consumermaster b  WHERE flag='1' and a.billmonth='"+monthAlt+"' and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;		
	}

	@Override
	public List getRMSPendingDetails(String monthAlt) {
		List<?> list = null;
		try {
			String sql="SELECT * from meter_data.consumerreadings a,meter_data.consumermaster b  WHERE flag='0' and a.billmonth='"+monthAlt+"' and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();

			//System.out.println(sql);


		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Master> getDivByCircle(String zone, String circle) {
		List<Master> division=null;
		String qry1="";
		try {
			qry1="SELECT DISTINCT DIVISION FROM meter_data.amilocation WHERE ZONE LIKE '"+zone+"' AND CIRCLE LIKE '"+circle+"' AND DIVISION IS NOT NULL ORDER BY DIVISION";
			division=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return division;
	}

	@Override
	public List<Master> getSubdivByDivision(String zone, String circle,String division) {
		List<Master> Subdivision=null;
		String qry1="";
		try {
			qry1="SELECT DISTINCT subdivision FROM meter_data.amilocation WHERE ZONE LIKE '"+zone+"' AND CIRCLE LIKE '"+circle+"' AND DIVISION LIKE '"+division+"' AND subdivision IS NOT NULL ORDER BY subdivision";
			Subdivision=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Subdivision;
	}
	
	@Override
	public List<Master> getSubdivandSitecodeByDivision(String zone, String circle,String division) {
		List<Master> Subdivision=null;
		String qry1="";
		try {
			qry1="SELECT DISTINCT subdivision,sitecode FROM meter_data.amilocation WHERE ZONE LIKE '"+zone+"' AND CIRCLE LIKE '"+circle+"' AND DIVISION LIKE '"+division+"' AND subdivision IS NOT NULL ORDER BY subdivision";
			
			Subdivision=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Subdivision;
	}
	
	@Override
	public List<?> getDistinctCircle() {
		List<Master> circles=null;
		String qry="";
		try {
			qry="SELECT DISTINCT CIRCLE FROM meter_data.amilocation WHERE CIRCLE IS NOT NULL ORDER BY CIRCLE";
			circles=postgresMdas.createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return circles;
	}

	
	@Override
	
	public int updateMasterMainMeterNo(String meterno,Double mf,Timestamp mtrchngtime) {
//		System.out.println("inside updateMasterMain meterno");
//		System.out.println("meterno--"+meterno+" mf--"+mf+" mtrchngtime--"+mtrchngtime);
		int count=0;
		//String sql ="update meter_data.meter_inventory SET meter_status='INSTALLED',updateby='"+userName+"' WHERE meterno='"+meterno+"'";
		//System.out.println(sql);
		try {
		//li = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return 0;
	
	}

	


	@Override
	public List<?> getCircleRMSUpload(List circle, String month) {
		List<?> list = null;
		try {
			String sql="select count(CASE WHEN b.flag=1 THEN 1 END) as rms_synced from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth='"+month+"' and a.sdocode in   \n" +
					"(   \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"'   \n" +
					") and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getCircleRMSpending(List circle,String month) {
		List<?> list = null;
		try {
			String sql="select count(CASE WHEN b.flag=0 THEN 1 END) as rms_synced from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth='"+month+"' and a.sdocode in   \n" +
					"(   \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"'   \n" +
					") and  a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getcircleReadingDetails(List circle, String month) {
		List<?> list = null;
		try {
			String sql="select * from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth in(\n" +
					"SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE billmonth='"+month+"') and a.sdocode in   \n" +
					"(   \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"'   \n" +
					") and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getCircleNotReadingCustomerDetails(List circle, String month) {
		List<?> list = null;
		try {
			String sql="SELECT  a.meterno,a.kno,a.accno FROM meter_data.consumermaster a where a.meterno  not in (select b.meterno from meter_data.consumerreadings b where b.billmonth='"+month+"') and a.sdocode in   \n" +
					"(   \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"'   \n" +
					")";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getCircleRMSUploadDetails(List circle, String month) {
		List<?> list = null;
		try {
			String sql="SELECT * from meter_data.consumerreadings a,meter_data.consumermaster b  WHERE flag='1' and a.billmonth='"+month+"' and b.sdocode in   \n" +
					"(   \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"'   \n" +
					")and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;		
	}

	@Override
	public List<?> getCircleRMSPendingDetails(List circle, String month) {
		List<?> list = null;
		try {
			String sql="SELECT * from meter_data.consumerreadings a,meter_data.consumermaster b  WHERE flag='0' and a.billmonth='"+month+"'and b.sdocode in   \n" +
					"(   \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"'   \n" +
					") and a.meterno=b.meterno;";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getTotalDivisionConsumerAvail(String circle, String division, String month) {
		List<?> list = null;
		try {
			String sql="SELECT count(DISTINCT a.meterno) FROM  meter_data.consumermaster a where  a.sdocode in \n" +
					"( \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+circle+"' and division='"+division+"'\n" +
					")";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getTotalDivisionReadingAvailCons(String cicle, String divisionlist, String month) {
		List<?> list = null;
		try {
			String sql="select count(DISTINCT a.meterno) from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth in( SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE billmonth='"+month+"') and a.sdocode in( SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"'\n" +
					") and   a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getTotalDivisionNotReadingAvailCons(String cicle, String divisionlist, String month) {
		List<?> list = null;
		try {
			String sql=" SELECT(\n" +
					"SELECT count(DISTINCT a.meterno) as total_hours FROM meter_data.consumermaster a,meter_data.consumerreadings b where b.billmonth in( SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE billmonth='"+month+"') and  a.sdocode in (SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"')\n" +
					")-  \n" +
					"(\n" +
					"SELECT count(DISTINCT a.meterno) as reading_avail  FROM  meter_data.consumermaster a,meter_data.consumerreadings b where  b.billmonth in( SELECT cast(billmonth as text) from meter_data.consumerreadings \n" +
					"WHERE billmonth='"+month+"') and  a.sdocode in(SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"') and a.meterno=b.meterno\n" +
					")as reading_not_avail ";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getDivisionRMSUpload(String cicle, String divisionlist, String month) {
		List<?> list = null;
		try {
			String sql="	select count(CASE WHEN b.flag=1 THEN 1 END) as rms_synced from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth='"+month+"' and a.sdocode in  \n" +
					"(  \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"'  and division='"+divisionlist+"'\n" +
					") and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getDivisionRMSpending(String cicle, String divisionlist, String month) {
		List<?> list = null;
		try {
			String sql="select count(CASE WHEN b.flag=0 THEN 1 END) as rms_synced from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth='"+month+"' and a.sdocode in  \n" +
					"(  \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"'\n" +
					") and  a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getDivisionCustomerDetails(String cicle, String divisionlist, String month) {
		List<?> list = null;
		try {
			String sql="select * from meter_data.consumermaster a,meter_data.consumerreadings b where  a.sdocode in\n" +
					"(\n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"'\n" +
					") and b.billmonth='"+month+"' ";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getDivisionReadingDetails(String cicle, String divisionlist, String month) {
		List<?> list = null;
		try {
			String sql="select * from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth in(\n" +
					"SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE billmonth='"+month+"') and a.sdocode in   \n" +
					"( \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"' \n" +
					") and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getDivivsionNotReadingCustomerDetails(String cicle, String divisionlist, String month) {
		List<?> list = null;
		try {
			String sql="\n" +
					"SELECT  a.meterno,a.kno,a.accno FROM meter_data.consumermaster a where a.meterno  not in (select b.meterno from meter_data.consumerreadings b where b.billmonth='"+month+"') and a.sdocode in \n" +
					"(   \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"' \n" +
					")";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getDivisionRMSUploadDetails(String cicle, String divisionlist, String month) {
		List<?> list = null;
		try {
			String sql="SELECT * from meter_data.consumerreadings a,meter_data.consumermaster b  WHERE flag='1' and a.billmonth='"+month+"' and b.sdocode in   \n" +
					"( \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"' \n" +
					")and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getDivisionRMSPendingDetails(String cicle, String divisionlist, String month) {
		List<?> list = null;
		try {
			String sql="SELECT * from meter_data.consumerreadings a,meter_data.consumermaster b  WHERE flag='0' and a.billmonth='"+month+"' and b.sdocode in  \n" +
					"(  \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"' \n" +
					") and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getTotalSubdivConsumerAvail(String cicle, String divisionlist, String subdivlist, String month) {
		List<?> list = null;
		try {
			String sql="SELECT count(DISTINCT a.meterno) FROM  meter_data.consumermaster a where  a.sdocode in \n" +
					"( \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"' and subdivision='"+subdivlist+"'\n" +
					") ";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getTotalSubdivReadingAvailCons(String cicle, String divisionlist, String subdivlist, String month) {
		List<?> list = null;
		try {
			String sql="select count(DISTINCT a.meterno) from meter_data.consumermaster a, meter_data.consumerreadings b "
					+ "where b.billmonth in( SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE "
					+ "billmonth='"+month+"') and a.sdocode in( SELECT cast(sdocode as text) from meter_data.amilocation B "
					+ "WHERE circle='"+cicle+"' and division='"+divisionlist+"'  and subdivision='"+subdivlist+"'\n" +
					") and   a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getTotalSubdivNotReadingAvailCons(String cicle, String divisionlist, String subdivlist,
			String month) {
		List<?> list = null;
		try {
			String sql="SELECT(\n" +
					"SELECT count(DISTINCT a.meterno) as total_hours FROM meter_data.consumermaster a,"
					+ "meter_data.consumerreadings b where b.billmonth in( SELECT cast(billmonth as text) "
					+ "from meter_data.consumerreadings WHERE billmonth='"+month+"') and  a.sdocode in (SELECT cast(sdocode as text)"
					+ " from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"' and "
					+ "subdivision='"+subdivlist+"')\n" +
					")-  \n" +
					"(\n" +
					"SELECT count(DISTINCT a.meterno) as reading_avail  FROM  meter_data.consumermaster a,"
					+ "meter_data.consumerreadings b where  b.billmonth in( SELECT cast(billmonth as text) from "
					+ "meter_data.consumerreadings \n" +
					"WHERE billmonth='"+month+"') and  a.sdocode in(SELECT cast(sdocode as text) from meter_data.amilocation B "
					+ "WHERE circle='"+cicle+"' and division='"+divisionlist+"' and subdivision='"+subdivlist+"') and a.meterno=b.meterno\n" +
					")as reading_not_avail ";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getSubdivRMSUpload(String cicle, String divisionlist, String subdivlist, String month) {
		List<?> list = null;
		try {
			String sql="select count(CASE WHEN b.flag=1 THEN 1 END) as rms_synced from meter_data.consumermaster a, "
					+ "meter_data.consumerreadings b where b.billmonth='"+month+"' and a.sdocode in \n" +
					"(  \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"'  and "
					+ "division='"+divisionlist+"' and subdivision='"+subdivlist+"'\n" +
					") and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getSubdivRMSpending(String cicle, String divisionlist, String subdivlist, String month) {
		List<?> list = null;
		try {
			String sql="select count(CASE WHEN b.flag=0 THEN 1 END) as rms_synced from meter_data.consumermaster a, "
					+ "meter_data.consumerreadings b where b.billmonth='"+month+"' and a.sdocode in \n" +
					"( \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and"
					+ " division='"+divisionlist+"'  and subdivision='"+subdivlist+"'\n" +
					") and  a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getSubdivCustomerDetails(String cicle, String divisionlist, String subdivlist, String month) {
		List<?> list = null;
		try {
			String sql="select * from meter_data.consumermaster a,meter_data.consumerreadings b where  a.sdocode in\n" +
					"(SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and "
					+ "division='"+divisionlist+"' and subdivision='"+subdivlist+"') and b.billmonth='"+month+"' ";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getSubdivReadingDetails(String cicle, String divisionlist, String subdivlist, String month) {
		List<?> list = null;
		try {
			String sql="select * from meter_data.consumermaster a, meter_data.consumerreadings b where b.billmonth in(\n" +
					"SELECT cast(billmonth as text) from meter_data.consumerreadings WHERE billmonth='"+month+"') and a.sdocode in \n" +
					"( \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and division='"+divisionlist+"'"
					+ " and subdivision='"+subdivlist+"') and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getSubdivNotReadingCustomerDetails(String cicle, String divisionlist, String subdivlist,
			String month) {
		List<?> list = null;
		try {
			String sql="SELECT  a.meterno,a.kno,a.accno FROM meter_data.consumermaster a where a.meterno  "
					+ "not in (select b.meterno from meter_data.consumerreadings b where b.billmonth='"+month+"') and"
					+ " a.sdocode in \n" +
					"(   \n" +
					"SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and "
					+ "division='"+divisionlist+"' and subdivision='"+subdivlist+"'\n" +
					")";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getSubdivRMSUploadDetails(String cicle, String divisionlist, String subdivlist, String month) {
		List<?> list = null;
		try {
			String sql="SELECT * from meter_data.consumerreadings a,meter_data.consumermaster b  WHERE flag='1' "
					+ "and a.billmonth='"+month+"' and b.sdocode in  \n" +
					"(SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' and "
					+ "division='"+divisionlist+"' and subdivision='"+subdivlist+"')and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getSubdivRMSPendingDetails(String cicle, String divisionlist, String subdivlist, String month) {
		List<?> list = null;
		try {
			String sql="SELECT * from meter_data.consumerreadings a,meter_data.consumermaster b  WHERE flag='0' and "
					+ "a.billmonth='"+month+"' and b.sdocode in  \n" +
					"( SELECT cast(sdocode as text) from meter_data.amilocation B WHERE circle='"+cicle+"' "
					+ "and division='"+divisionlist+"' and subdivision='"+subdivlist+"' ) and a.meterno=b.meterno";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public List getTownsBaseOnSubDiv(String subdivname) {
		
		System.out.println("get-town-details...."+subdivname);
				List<?> list = new ArrayList<>();
				try {
					String sql = "select tp_towncode,town_ipds from meter_data.amilocation WHERE subdivision like '"+subdivname+"'\n" +
									"GROUP BY tp_towncode,town_ipds ORDER BY town_ipds;";
					
					//System.out.println("sql---"+sql);
					list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
	}

	@Override
	public List getSubstationsByTownCode(String towncode) {
		
		System.out.println("getSubstationsByTownCode--"+towncode);
				List<?> list = null;
				try {
					
					String sql ="select sstp_id,ss_name  from meter_data.substation_details s, meter_data.amilocation a where \n" +
								"a.sitecode=s.office_id and a.tp_towncode like '"+towncode+"' and ss_name is not null AND s.deleted IS NULL GROUP BY ss_name,sstp_id\n" +
								"order by ss_name asc;";
					
					//System.out.println("sql--"+sql);
					list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return list;
	}

	
	

	@Override
	public List<?> getTownNameandCode(String siteCode) {
		List<Master> Town=null;
		String qry1="";
		try {
			qry1="select DISTINCT tp_towncode,town_ipds from meter_data.amilocation where subdivision  like '"+siteCode+"' ";
			Town=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Town;
		
	}

	@Override
	public String getSSTPCode(String ssid) {
		String sql="SELECT DISTINCT sstp_id FROM meter_data.substation_details WHERE ss_id='"+ssid+"';";
		String town=String.valueOf(postgresMdas.createNativeQuery(sql).getResultList().get(0));
		return town;
	}

	@Override
	public String getDTTpParentCode(String fdrid) {
		String sql="select distinct tp_fdr_id from meter_data.feederdetails WHERE fdr_id='"+fdrid+"'";
		System.out.println("qi="+sql);
		try {
			String tpcode=String.valueOf(postgresMdas.createNativeQuery(sql).getResultList().get(0));
			return tpcode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List getTownsBaseOnSubdivision( String circle,String zone) {
	//	System.out.println("get-town-details...."+);
		List<?> list = new ArrayList<>();
		try {
			String sql = "select tp_towncode,town_ipds from meter_data.amilocation WHERE circle like '"+circle+"'and zone like '"+zone+"' and town_ipds is not null \n" +
							"GROUP BY tp_towncode,town_ipds ORDER BY town_ipds;";
			
			System.out.println("sql---"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
			
	@Override
	public List getTownsBaseOnCircle(String zone, String circle) {
//		System.out.println("get-town-details...."+subdivision);
		List<?> list = new ArrayList<>();
		try {
			String sql = "select tp_towncode,town_ipds from meter_data.amilocation WHERE zone like '"+zone+"' and circle like '"+circle+"' \n" +
							"GROUP BY tp_towncode,town_ipds ORDER BY town_ipds;";
			
			//System.out.println("sql---"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public List getAllLocationHiarchary(String officeType,String officeCode) {
		
		//System.out.println("get-division-details....");
				List<?> list = new ArrayList<>();
				String qry="";
				try {
					if (officeType.equalsIgnoreCase("circle")) {
					qry = "SELECT DISTINCT CIRCLE  FROM meter_data.amilocation a"
							+ " WHERE circle_code = '"+ officeCode + "' ORDER BY CIRCLE";
				} else if (officeType.equalsIgnoreCase("corporate")){
				qry = "SELECT distinct zone FROM meter_data.amilocation a"
						+ " WHERE  discom_code = '"+ officeCode+"' ORDER BY zone";
				System.out.println(qry);
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//System.out.println("consumer location qry--"+qry);
				list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
				return list;
	}


	@Override
	public List getTownNameandCodebyCircleandzone(String zone, String circle) {
//		System.out.println("get-town-details...."+subdivision);
		List<?> list = new ArrayList<>();
		try {
			String sql = "select distinct tp_towncode,town_ipds from meter_data.amilocation WHERE zone like '"+zone+"' and circle like '"+circle+"' and town_ipds is not null\n" +
							"GROUP BY tp_towncode,town_ipds ORDER BY town_ipds;";
			
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			System.out.println("sql---"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List getTownNameandCodebyCircleandzonebyReg(String zone, String circle) {
//		System.out.println("get-town-details...."+subdivision);
		List<?> list = new ArrayList<>();
		try {
			String sql = "select distinct tp_towncode,town_ipds from meter_data.amilocation WHERE zone like '"+zone+"' and circle like '"+circle+"' and town_ipds is not null\n" +
							"GROUP BY tp_towncode,town_ipds ORDER BY town_ipds;";
			
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			//System.out.println("sql---"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@Override
	public List showmeteronBasisofTown(String zone,String circle,String town) {
//		System.out.println("get-town-details...."+subdivision);
		List<?> list = new ArrayList<>();
		try {
			String sql = "Select  distinct mtrno  from meter_data.master_main WHERE zone like '"+zone+"' and circle like '"+circle+"' and town_code like '"+town+"' ;";
			System.out.println(sql);
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			//System.out.println("sql---"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getTownNameandCodebySubdiv(String circle, String division,String subdiv) {
		List<?> list = new ArrayList<>();
		try {
			String sql = "select tp_towncode,town_ipds from meter_data.amilocation WHERE  circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdiv+"' \n" +
							"GROUP BY tp_towncode,town_ipds ORDER BY town_ipds;";
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			//System.out.println("towncode qry is----"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public List<?> getdtdata(String towncode) {
		List<?> list = new ArrayList<>();
		try {
			String sql ="select distinct dttpid from meter_data.dtdetails WHERE tp_town_code LIKE '"+towncode+"' and trim(dttpid)  not in('NULL','') and crossdt=0 and meterno not in('NULL','') ORDER BY dttpid ASC";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}



	
	@Override
	public List getFeederTpIdandFeederName(String townCode) {
		// TODO Auto-generated method stub
		List<?> list = new ArrayList<>();
		try {
			//String sql = "Select distinct tp_fdr_id, feedername from meter_data.feederdetails where tp_town_code = '"+townCode+"'  ";
			
			String sql="SELECT DISTINCT ON(tpparentid)tpparentid,parent_feeder FROM meter_data.dtdetails where tp_town_code like '"+townCode+"' and parent_feeder is not NULL";
			
			//System.out.println("sql---"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List getFeederByTown(String townCode, String circle) {
		// TODO Auto-generated method stub
		List<?> list = new ArrayList<>();
		try {
			String sql;
			if (townCode.equalsIgnoreCase("%")) {
				sql = "Select distinct tp_fdr_id, feedername,meterno,crossfdr from meter_data.feederdetails where tp_town_code in (select distinct tp_towncode from meter_data.amilocation where circle like '"+circle+"')  and crossfdr='0'";
			}else {
				sql = "Select distinct tp_fdr_id, feedername from meter_data.feederdetails where tp_town_code like '"+townCode+"'  and crossfdr='0' ";
			}
			  
			
			System.out.println("sql---"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}	

	@Override
	public List getDTTpIdandDTName(String feederCode) {
		// TODO Auto-generated method stub
		List<?> list = new ArrayList<>();
		try {
			String sql = "Select DISTINCT dttpid , dtname from meter_data.dtdetails where tpparentid = '"+feederCode+"'  and meterno is not null ";
			
			System.out.println("sql---"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List getFeederBySelection(String townCode, String circle, String zone) {
		// TODO Auto-generated method stub
		List<?> list = new ArrayList<>();
		try {
			String sql;
			/*if (townCode.equalsIgnoreCase("%")) {
				//sql = "Select distinct tp_fdr_id, feedername,meterno,crossfdr from meter_data.feederdetails where tp_town_code in (select distinct tp_towncode from meter_data.amilocation where circle like '"+circle+"' and zone like '"+zone+"')  and crossfdr='0'";
				
				sql ="SELECT DISTINCT ON(tpparentid)tpparentid,parent_feeder,meterno FROM meter_data.dtdetails where tp_town_code IN(select distinct tp_towncode from meter_data.amilocation where circle like '"+circle+"' and zone like '"+zone+"') and parent_feeder is not NULL and meterno is not NULL ";
			}else {
				//sql = "Select distinct tp_fdr_id, feedername from meter_data.feederdetails where tp_town_code like '"+townCode+"'  and crossfdr='0' ";
				
				sql="SELECT DISTINCT ON(tpparentid)tpparentid,parent_feeder FROM meter_data.dtdetails where tp_town_code like '"+townCode+"'  and parent_feeder is not NULL and meterno is not NULL ";
			}*/
			  
			sql ="SELECT DISTINCT ON(tpparentid)tpparentid,parent_feeder,meterno FROM meter_data.dtdetails where tp_town_code IN(select distinct tp_towncode from meter_data.amilocation where circle like '"+circle+"' and zone like '"+zone+"' and tp_towncode like '"+townCode+"') and parent_feeder is not NULL and meterno is not NULL ";
			
			System.out.println("sql---"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Master> getCircleByRegionForFeederOutage(String zone) {
		List<Master> circle=null;
		String qry1="";
		try {
			qry1="SELECT DISTINCT CIRCLE,tp_circlecode FROM meter_data.amilocation WHERE ZONE LIKE '"+zone+"' AND CIRCLE IS NOT NULL ORDER BY CIRCLE";
			System.err.println(qry1);
			circle=postgresMdas.createNativeQuery(qry1).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return circle;
	}
}
