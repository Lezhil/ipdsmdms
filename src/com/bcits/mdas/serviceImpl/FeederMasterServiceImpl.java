package com.bcits.mdas.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.Last30DaysEntity;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.service.FeederMasterService;
import com.bcits.mdas.utility.Colours;
import com.bcits.serviceImpl.GenericServiceImpl;
import com.ibm.icu.text.SimpleDateFormat;
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
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Repository
public class FeederMasterServiceImpl extends GenericServiceImpl<FeederMasterEntity> implements FeederMasterService {
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Object getAllFeedersMobile(String imeiNo) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getAllFeederDataMobile")
				.setParameter("imeiNo", imeiNo).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getDistinctCircle() {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT circle FROM dholpur.feedermaster ";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getDivisionByCircle(String circle, ModelMap model) {
		String sql = "";
		List<Object[]> list = null;
		try {
			// sql="SELECT DISTINCT division FROM dholpur.feedermaster WHERE
			// circle='"+circle+"'";
			sql = "SELECT DISTINCT division FROM dholpur.feedermaster WHERE circle=:circle";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).setParameter("circle", circle).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getSubdivByDivisionByCircle(String circle, String division, ModelMap model) {
		String sql = "";
		List<Object[]> list = null;
		try {
			sql = "SELECT DISTINCT office_code,office_name FROM dholpur.feedermaster WHERE circle='" + circle
					+ "' and division='" + division + "' order by office_name";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getFeederBySubByDiv(String circle, String division, String subdiv, ModelMap model) {
		String sql = "";
		List<Object[]> list = null;
		try {
			sql = "SELECT DISTINCT unin_feeder,feeder_name FROM dholpur.feedermaster WHERE circle='" + circle
					+ "' and division='" + division + "' and office_code='" + subdiv + "' ORDER BY unin_feeder ";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> findAll(String district) {
		List<FeederMasterEntity> list = null;
		try {
			list = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.findAll").setParameter("district", district)
					.getResultList();
			// model.addAttribute("feederMasterList",list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> findDistricts() {
		List<FeederMasterEntity> list = null;
		try {
			list = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getDistinctDistrict").getResultList();
			// model.addAttribute("feederMasterList",list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getDistinctFeederName(String sdocode, ModelMap model, HttpServletRequest request) {
		String sql = "";
		List<Object[]> list = null;
		try {
			sql = "SELECT DISTINCT feeder_name FROM dholpur.feedermaster WHERE office_code='" + sdocode + "'";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> getDistinctFeederCode(String sdocode, String feederName, ModelMap model,
			HttpServletRequest request) {
		String sql = "";
		List<Object[]> list = null;
		try {
			sql = "SELECT DISTINCT unin_feeder FROM dholpur.feedermaster WHERE office_code='" + sdocode
					+ "' and feeder_name='" + feederName + "'";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	// @Transactional(propagation=Propagation.REQUIRED)
	public int updateMrImeiByFeederNo(String sdoNo, String fdrName, String fdrNo, String mrNo, String imeiNo,
			HttpServletRequest request) {
		int i = 0;

		String sql = "UPDATE dholpur.feedermaster SET imeino='" + imeiNo + "',mrcode='" + mrNo
				+ "' WHERE office_code like '" + sdoNo + "' " + "and feeder_name like '" + fdrName
				+ "' and unin_feeder like '" + fdrNo + "'";
		System.out.println("inside updateMrImeiByFeederNo Sql Query==>" + sql);
		try {
			i = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	// @Transactional(propagation=Propagation.REQUIRED)
	public int updateNullMrImeiByFeederNo(String deviceidPk, HttpServletRequest request) {
		int i = 0;

		String sql = "UPDATE dholpur.feedermaster SET imeino='',mrcode='' WHERE imeino like '" + deviceidPk + "'";
		try {
			i = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getFeederData(String mtrNo) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getFeederData").setParameter("mtrNo", mtrNo).getResultList();
	}

	@Override
	public List<Object[]> getMeterDetailsForXml() {
		return getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getMeterDetailsForXml").getResultList();
	}

	@Override
	public List<?> getCorporateDetailCount() {

		System.out.println("=============Feerder Zone Counts==============="
				+ getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getCircleDetailCount")
						.unwrap(org.hibernate.Query.class).getQueryString());

		return getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getCorporateDetailCount").getResultList();

	}

	@Override
	public List<?> getZoneDetailCount(String zone) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getZoneDetailCount").setParameter("zone", zone)
				.getResultList();
	}

	@Override
	public List<?> getCircleDetailCount(String circle) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getCircleDetailCount").setParameter("circle", circle)
				.getResultList();
	}

	@Override
	public List<?> getDivisionDetailCount(String division) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getDivisionDetailCount")
				.setParameter("division", division).getResultList();
	}

	@Override
	public List<?> getSubDivisionDetailCount(String subdivision) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getSubDivisionDetailCount")
				.setParameter("subdivision", subdivision).getResultList();
	}

	@Override
	public List<?> getSubStationDetailCount(String subStation) {
		return getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getSubStationDetailCount")
				.setParameter("subStation", subStation).getResultList();
	}

	@Override
	public List<?> getZones() {
		// TODO Auto-generated method stub
		List<?> lis = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getZones").getResultList();
		return lis;
	}

	@Override
	public List<?> getCircles(String zone) {
		// TODO Auto-generated method stub
		List<?> lis = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getCircles").setParameter("zone", zone)
				.getResultList();
		return lis;
	}

	@Override
	public List<?> getDivisions(String circle) {
		// TODO Auto-generated method stub
		List<?> lis = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getDivision").setParameter("circle", circle)
				.getResultList();
		return lis;
	}

	@Override
	public List<?> getSubDivisions(String division) {
		// TODO Auto-generated method stub
		List<?> lis = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getSubDivision")
				.setParameter("division", division).getResultList();
		return lis;
	}

	@Override
	public List<?> getSubStations(String subdivision) {
		// TODO Auto-generated method stub
		List<?> lis = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getSubStation")
				.setParameter("subdivision", subdivision).getResultList();
		return lis;
	}

	@Override
	public int getModemCount() {
		// TODO Auto-generated method stub FeederMasterEntity.getModemCount
		List<Long> lis = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getModemCount").getResultList();
		return lis.get(0).intValue();
	}

	@Override
	public List<?> getFailUploadCount() {

		// String sql="select count(DISTINCT meter_number) from
		// meter_data.xml_failed_info where
		// date_part('day',age(current_date,date(time_stamp)))<=1";
		String sql = "SELECT count(distinct mm.zone)as zone, " + "count(distinct mm.mtrno)as total, " + "COUNT ( "
				+ "CASE " + "WHEN mstat.upload_status = 1 THEN " + "1 " + "END " + ") AS uploaded, " + "COUNT ( "
				+ "CASE " + "WHEN mstat.upload_status = 0 THEN " + "1 " + "END " + ") AS failed " + "FROM "
				+ "meter_data.xml_upload_status mstat , meter_data.master_main mm "
				+ "WHERE mstat.file_date= CURRENT_DATE-1 and mstat.meter_number=mm.mtrno";
		System.out.println("--------------upload fail----------------" + sql);
		// List<BigInteger> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).g();
		List<Object[]> li = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		/*
		 * for ( Iterator<?> iterator=li.iterator();iterator.hasNext();){ final Object[]
		 * values = (Object[]) iterator.next();
		 * System.out.println(values[0]+"--------upload file test--------"); }
		 */

		for (Object[] object : li) {
			System.out.println(object[0] + "--------upload file test--------");
			System.out.println(object[1] + "--------upload file test--------");
			System.out.println(object[2] + "--------upload file test--------");
			System.out.println(object[3] + "--------upload file test--------");

		}

		return li;
	}

	@Override
	public List<?> getFailUploadCount(String type1, String value1) {

		// String sql="select count(DISTINCT meter_number) from
		// meter_data.xml_failed_info where
		// date_part('day',age(current_date,date(time_stamp)))<=1";
		String sql = "SELECT :type1, " + "count(distinct mm.mtrno)as total, " + "COUNT ( " + "CASE "
				+ "WHEN mstat.upload_status = 1 THEN " + "1 " + "END " + ") AS uploaded, " + "COUNT ( " + "CASE "
				+ "WHEN mstat.upload_status = 0 THEN " + "1 " + "END " + ") AS failed " + "FROM "
				+ "meter_data.xml_upload_status mstat , meter_data.master_main mm "
				+ "WHERE mstat.file_date= CURRENT_DATE-1 and mstat.meter_number=mm.mtrno and mm." + type1
				+ "=:value1 group by  mm." + type1;
		System.out.println("--------------upload fail----------------" + sql);
		// List<BigInteger> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		List<?> li = getCustomEntityManager("postgresMdas").createNativeQuery(sql).setParameter("type1", type1).setParameter("value1", value1)
				.getResultList();
		// return li.get(0).intValue();
		return li;
	}

	@Override
	public List<?> getFailUploadCountSublevel() {

		String sql = "SELECT " + "mm. ZONE, " + "count(distinct mm.mtrno)as total, " + "COUNT ( " + "CASE "
				+ "WHEN mstat.upload_status = 1 THEN " + "1 " + "END " + ") AS uploaded, " + "COUNT ( " + "CASE "
				+ "WHEN mstat.upload_status = 0 THEN " + "1 " + "END " + ") AS failed " + "FROM "
				+ "meter_data.xml_upload_status mstat, " + "meter_data.master_main mm " + "WHERE "
				+ "mm.mtrno = mstat.meter_number " + "AND mstat.file_date = (CURRENT_DATE - 1) " + "GROUP BY "
				+ "mm. ZONE";

		System.out.println("-----------zone upload count-------------" + sql);
		List<?> li = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

		return li;
	}

	@Override
	public List<?> getFailUploadCountSublevel(String type, String sub_type, String value) {
		System.out.println("getFailUploadCountSublevel======type======" + type);
		System.out.println("getFailUploadCountSublevel=====sub_type=======" + sub_type);
		System.out.println("getFailUploadCountSublevel======value======" + value);
		String sql = "SELECT " + "mm." + sub_type + ",count(distinct mm.mtrno)as total, " + "COUNT ( " + "CASE "
				+ "WHEN mstat.upload_status = 1 THEN " + "1 " + "END " + ") AS uploaded, " + "COUNT ( " + "CASE "
				+ "WHEN mstat.upload_status = 0 THEN " + "1 " + "END " + ") AS failed " + "FROM "
				+ "meter_data.xml_upload_status mstat, " + "meter_data.master_main mm " + "WHERE "
				+ "mm.mtrno = mstat.meter_number " + "AND mstat.file_date = (CURRENT_DATE - 1) " + "AND mm." + type
				+ "='" + value + "' GROUP BY " + "mm. ZONE,mm." + sub_type;

		System.out.println("-----------" + type + "=" + value + " upload count-------------" + sql);
		List<?> li = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

		return li;
	}

	@Override
	public int getUploadedCount() {
		String sql = "select " + "(select count(DISTINCT mtrno) from meter_data.master_main ) " + "-  "
				+ "(select count(DISTINCT meter_number) from meter_data.xml_failed_info where date_part('day',age(current_date,date(time_stamp)))<=1) AS Difference";
		System.out.println(sql);
		List<BigInteger> li = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

		return li.get(0).intValue();
	}

	@Override
	public int getTtlMtrCount() {
		// TODO Auto-generated method stub
		List<Long> lis = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getTtlMtrCount").getResultList();
		return lis.get(0).intValue();
	}

	@Override
	public List<?> getModemCountZone() {
		// TODO Auto-generated method stub
		List<?> lis = getCustomEntityManager("postgresMdas").createNamedQuery("FeederMasterEntity.getZonesModemCount").getResultList();
		return lis;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getFeederInfo(ModelMap model, HttpServletRequest request) {

		List<?> list = null;

		try {
			String subStation1 = "33 KV Mirzapur";
			if (subStation1 != null) {
				String query = "SELECT mm.fdrname,mm.fdrcode,mm.mtrno,mm.modem_sl_no FROM meter_data.master_main  mm where substation='33 KV Mirzapur'";
				list = getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
				model.put("results", "noFeederData");
				if (list.size() > 0) {
					model.put("feederInfoList", list);
				} else {
					model.put("results", "Data not available.");
				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public ModelMap getFdrMeterInfo(String FEEDERNAME, String FEEDERCODE) {
		ModelMap model = new ModelMap();
		// 0 1 2 3 4 5
		String query = "SELECT mm.mtrno,mm.mtrmake,mm.dlms,mm.year_of_man,mm.comm,mm.modem_sl_no FROM meter_data.master_main mm where mm.fdrname='"
				+ FEEDERNAME + "' and mm.fdrcode='" + FEEDERCODE + "' ";
		Query q = getCustomEntityManager("postgresMdas").createNativeQuery(query);
		System.out.println("q==" + q);
		List list = q.getResultList();

		Object[] val = (Object[]) list.get(0);
		if (val.length > 0) {
			if (val[0] == null) {
				model.put("mtrno", "NOData");
			} else {
				model.put("mtrno", val[0]);
			}
			if (val[1] == null) {
				model.put("mtrmake", "NOData");
			} else {
				model.put("mtrmake", val[1]);
			}
			if (val[2] == null) {
				model.put("dlms", "NOData");
			} else {
				model.put("dlms", val[2]);
			}
			if (val[3] == null) {
				System.out.println("year_of_man====NO DATA======");
				model.put("year_of_man", "NOData");
			} else {
				System.out.println("year_of_man==========" + val[3]);
				model.put("year_of_man", val[3]);
			}
			if (val[4] == null) {
				model.put("comm", "NOData");
			} else {
				model.put("comm", val[4]);
			}
			if (val[5] == null) {
				model.put("modem_sl_no", "NOData");
			} else {
				model.put("modem_sl_no", val[5]);
			}

		}
		System.out.println("List==" + list.size());

		return model;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getDistinctZone() {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT zone from meter_data.amilocation where zone is not null order by zone ";
			// String sql="SELECT DISTINCT zone from meter_data.master where zone not like
			// ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getCircleByZone(String zone, ModelMap model) {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT circle from meter_data.master_main WHERE zone like '" + zone + "'";
			// String sql="SELECT DISTINCT circle from meter_data.master WHERE zone like
			// '"+zone+"' and circle not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getDistinctCircle(HttpServletRequest request, ModelMap model) {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT circle from meter_data.master";
			// String sql="SELECT DISTINCT circle from meter_data.master WHERE zone like
			// '"+zone+"' and circle not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getDivisionByCircle(String zone, String circle, ModelMap model) {
		List<FeederMasterEntity> list = null;
		try {
			
			String sql = "SELECT DISTINCT division from meter_data.master_main WHERE zone like '" + zone
					+ "' and circle LIKE '" + circle + "'";
			// String sql="SELECT DISTINCT division from meter_data.master WHERE zone like
			// '"+zone+"' and "
			// + "circle LIKE '"+circle+"' and division not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getDivisionUnderCircle(String circle, ModelMap model)
	{
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT division from meter_data.master WHERE  circle LIKE '" + circle + "'";
			// String sql="SELECT DISTINCT division from meter_data.master WHERE zone like
			// '"+zone+"' and "
			// + "circle LIKE '"+circle+"' and division not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getSubdivByDivisionByCircle(String zone, String circle, String division,
			ModelMap model) {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT subdivision,sdocode from meter_data.master_main WHERE zone like '" + zone
					+ "' and circle LIKE '" + circle + "' and division like '" + division + "'";
			// String sql="SELECT DISTINCT subdivision from meter_data.master WHERE zone
			// like '"+zone+"' and circle LIKE '"+circle+"' "
			// + "and division like '"+division+"' and subdivision not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getSubdivUnderDivision(String circle, String division,
			ModelMap model) {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT subdiv from meter_data.master WHERE   circle LIKE '" + circle + "' and division like '" + division + "'";
			// String sql="SELECT DISTINCT subdivision from meter_data.master WHERE zone
			// like '"+zone+"' and circle LIKE '"+circle+"' "
			// + "and division like '"+division+"' and subdivision not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getSStationBySubByDiv(String zone, String circle, String division, String subdiv,
			ModelMap model) {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT substation from meter_data.master_main WHERE zone like '" + zone
					+ "' and circle LIKE '" + circle + "' and division like '" + division + "' and subdivision like '"
					+ subdiv + "'";
			// String sql="SELECT DISTINCT substation from meter_data.master WHERE zone like
			// '"+zone+"' and "
			// + "circle LIKE '"+circle+"' and division like '"+division+"' and subdivision
			// like '"+subdiv+"' and substation not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getDistinctSubDivision() {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT subdivision from meter_data.master_main";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getSStationBySub(String subdiv, ModelMap model) {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT substation from meter_data.master_main WHERE subdivision like '" + subdiv
					+ "'";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Object getD1GData(String mtrNo) {
		String query = "SELECT pt_ratio as G7,ct_ratio as G8,mtrmake as G22,mtr_firmware as G17,year_of_man as G1177,dlms as G1194 from meter_data.master_main where mtrno='"
				+ mtrNo + "' ";
		Query q = getCustomEntityManager("postgresMdas").createNativeQuery(query);
		System.out.println("q==" + q);
		List list = q.getResultList();
		try {
			return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// graph

	@Override
	public List<?> getDistinctSubStation(String queryWhere) {
		/*
		 * System.out.println("enter to srvimpl"); List<?>
		 * subStations=getCustomEntityManager("postgresMdas").createNamedQuery(
		 * "FeederMasterEntity.getDistinctSubstation").getResultList(); return
		 * subStations;
		 */
		System.out.println("getting loaded");

		List<?> list = null;
		// String qry = "SELECT id,stock_name from stockmarket.stock_table";
		String where = queryWhere.replace("WHERE", "AND");
		String query = "SELECT  DISTINCT f.substation FROM meter_data.MASTER_MAIN f WHERE f.substation is NOT NULL "
				+ where + " ORDER BY f.substation";
		list = getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
		System.out.println("list count==" + list.size());

		return (List<?>) list;

	}

	@Override
	public List<?> getFeederBySubstn(String substation) {
		List<?> list = null;
		String query = "SELECT f.fdrname FROM meter_data.MASTER_MAIN f WHERE f.substation='" + substation + "'";

		list = getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
		int count = list.size();
		System.out.println("count==" + count);

		return list;
	}

	@SuppressWarnings("unused")
	@Override
	public JSONArray getTimeAndPhases(String substation, String date, String zone, String circle, String division, String subdiv) throws ParseException {
		System.out.println("come to query");
		List<?> list = null;
		/*
		 * String
		 * query="SELECT read_time,meter_number,i_r,i_y,i_b FROM meter_data.load_survey WHERE to_char(read_time, 'YYYY-MM-DD HH24:MI:SS') like '2017-11-13%' AND meter_number='HRT64902' ORDER BY read_time DESC"
		 * ;
		 */
		JSONArray ojarray = new JSONArray();
		JSONObject ojobj = new JSONObject();
		JSONArray jarraySubStn = new JSONArray();
		JSONArray jarraySubStnDatas = new JSONArray();

		/*
		 * String[] strarry=new
		 * String[]{"00:00:00","00:30:00","01:00:00","01:30:00","02:00:00","02:30:00",
		 * "03:00:00","03:30:00"
		 * ,"04:00:00","04:30:00","05:00:00","05:30:00","06:00:00","06:30:00","07:00:00"
		 * ,"07:30:00",
		 * "08:00:00","08:30:00","09:00:00","09:30:00","10:00:00","10:30:00","11:00:00",
		 * "11:30:00"
		 * ,"12:00:00","12:30:00","13:00:00","13:30:00","14:00:00","14:30:00","15:00:00"
		 * ,"15:30:00",
		 * "16:00:00","16:30:00","17:00:00","17:30:00","18:00:00","18:30:00","19:00:00",
		 * "19:30:00",
		 * "20:00:00","20:30:00","21:00:00","21:30:00","22:00:00","22:30:00",,};
		 */

		String[] strarry = new String[] { "23:30:00", "23:00:00", "22:30:00", "22:00:00", "21:30:00", "21:00:00",
				"20:30:00", "20:00:00", "19:30:00", "19:00:00", "18:30:00", "18:00:00", "17:30:00", "17:00:00",
				"16:30:00", "16:00:00", "15:30:00", "15:00:00", "14:30:00", "14:00:00", "13:30:00", "13:00:00",
				"12:30:00", "12:00:00", "11:30:00", "11:00:00", "10:30:00", "10:00:00", "09:30:00", "09:00:00",
				"08:30:00", "08:00:00", "07:30:00", "07:00:00", "06:30:00", "06:00:00", "05:30:00", "05:00:00",
				"04:30:00", "04:00:00", "03:30:00", "03:00:00", "02:30:00", "02:00:00", "01:30:00", "01:00:00",
				"00:30:00", "00:00:00" };

		for (int i = 0; i < 48; i++) {
			JSONArray jarraySubStnData0 = new JSONArray();
			jarraySubStnDatas.put(jarraySubStnData0);
		}
		try {
			String querySubStn="";
			if("ALL".equalsIgnoreCase(substation)) {
				querySubStn = "SELECT fdrname,mtrno from meter_data.master_main WHERE "
						+ "ZONE='"+zone+"' AND CIRCLE='"+circle+"' AND DIVISION='"+division+"' AND SUBDIVISION='"+subdiv+"'";
			} else {
				querySubStn = "SELECT fdrname,mtrno from meter_data.master_main WHERE "
						+ "ZONE='"+zone+"' AND CIRCLE='"+circle+"' AND DIVISION='"+division+"' AND SUBDIVISION='"+subdiv+"'" 
						+ " AND substation='" + substation+ "'";
			}
			
//			System.err.println("---query feeder sun station---:" + querySubStn);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(querySubStn).getResultList();
			for (Iterator<?> iteratorSubStn = list.iterator(); iteratorSubStn.hasNext();) {
				final Object[] value = (Object[]) iteratorSubStn.next();
				jarraySubStn.put(value[0].toString());

				String query = "SELECT ls.read_time,ls.meter_number,ls.i_r,ls.i_y,ls.i_b,mm.fdrname FROM meter_data.load_survey ls,meter_data.master_main mm WHERE mm.mtrno=ls.meter_number  and to_char(ls.read_time, 'DD-MM-YYYY HH24:MI:SS') like '"
						+ date + "%' AND  ls.meter_number ='" + value[1].toString() + "' ORDER BY ls.read_time desc";
				list = getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
				System.out.println("query===========" + query);
				int count = list.size();
				System.out.println("count==" + count);

				String phase = null;
				String starttime = null;
				String fdr = null;
				int j = 0;// j index should point to the current time if we selected current date
				if (!list.isEmpty()) {
					for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

						final Object[] values = (Object[]) iterator.next();
						starttime = values[0].toString();
						String st1 = starttime.substring(11, 19);
//						System.err.println("no data------" + st1 + "------" + strarry[j]);
						while (!st1.equals(strarry[j])) {
//							System.err.println("no data------------" + strarry[j]);
							fdr = value[0].toString();
							phase = "NoData";
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("name", fdr + "_" + phase + "( startTime :" + strarry[j] + ")");
							jsonObject.put("color", "#ccc");
							jsonObject.put("y", 30);
							((JSONArray) jarraySubStnDatas.get(j)).put(jsonObject);
							j++;
						}

						if (!(values[2].toString().equalsIgnoreCase("0.00000"))
								&& !(values[3].toString().equalsIgnoreCase("0.00000"))
								&& !(values[3].toString().equalsIgnoreCase("0.00000"))) {
							phase = "threePhase";
						} else if ((values[2].toString().equalsIgnoreCase("0.00000"))
								&& (values[3].toString().equalsIgnoreCase("0.00000"))
								&& (values[3].toString().equalsIgnoreCase("0.00000"))) {
							phase = "poweroff";
						} else if ((values[2].toString().equalsIgnoreCase("0.00000"))
								&& !(values[3].toString().equalsIgnoreCase("0.00000"))
								&& !(values[3].toString().equalsIgnoreCase("0.00000"))
								|| (!values[2].toString().equalsIgnoreCase("0.00000"))
										&& (values[3].toString().equalsIgnoreCase("0.00000"))
										&& !(values[3].toString().equalsIgnoreCase("0.00000"))
								|| (!values[2].toString().equalsIgnoreCase("0.00000"))
										&& !(values[3].toString().equalsIgnoreCase("0.00000"))
										&& (values[3].toString().equalsIgnoreCase("0.00000"))) {
							phase = "singlePhase";
						} else if ((!values[2].toString().equalsIgnoreCase("0.00000"))
								&& (values[3].toString().equalsIgnoreCase("0.00000"))
								&& (values[3].toString().equalsIgnoreCase("0.00000"))
								|| (!values[2].toString().equalsIgnoreCase("0.00000"))
										&& (values[3].toString().equalsIgnoreCase("0.00000"))
										&& !(values[3].toString().equalsIgnoreCase("0.00000"))
								|| (values[2].toString().equalsIgnoreCase("0.00000"))
										&& !(values[3].toString().equalsIgnoreCase("0.00000"))
										&& !(values[3].toString().equalsIgnoreCase("0.00000"))) {
							phase = "twoPhase";
						}

						fdr = values[5].toString();

						JSONObject jsonObject = new JSONObject();
						jsonObject.put("name", fdr + "_" + phase + "( startTime :" + st1 + ")");
						if (phase.equalsIgnoreCase("singlePhase")) {
							jsonObject.put("color", Colours.YELLOW);
							// jsonObject.put("starttime", starttime);
						} else if (phase.equalsIgnoreCase("poweroff")) {
							jsonObject.put("color", Colours.RED);
							// jsonObject.put("starttime", starttime);
						} else if (phase.equalsIgnoreCase("threePhase")) {
							jsonObject.put("color", "#00A439");
							// jsonObject.put("starttime", starttime);
						} else if (phase.equalsIgnoreCase("twoPhase")) {
							jsonObject.put("color", Colours.ORANGE);
							// jsonObject.put("starttime", starttime);
						}
						/*
						 * JSONArray jarr=new JSONArray(); jarr.put(30);
						 */
						jsonObject.put("y", 30);
						((JSONArray) jarraySubStnDatas.get(j)).put(jsonObject);
						// jarraySubStnDatas.put(j,jsonObject);
						j++;
					}

					while (j <= 47) {

						fdr = value[0].toString();
						phase = "NoData";
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("name", fdr + "_" + phase + "( startTime :" + strarry[j] + ")");
						jsonObject.put("color", "#ccc");
						jsonObject.put("y", 30);
						((JSONArray) jarraySubStnDatas.get(j)).put(jsonObject);
						j++;
					}

				} else {
					for (int i = 0; i < strarry.length; i++) {
						fdr = value[0].toString();
						phase = "NoData";
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("name", fdr + "_" + phase + "( startTime :" + strarry[i] + ")");
						jsonObject.put("color", "#ccc");
						jsonObject.put("y", 30);
						((JSONArray) jarraySubStnDatas.get(j)).put(jsonObject);
						j++;
					}
				}
			}
			JSONArray outDataJArray = new JSONArray();
			for (int i = 0; i < jarraySubStnDatas.length(); i++) {
				JSONObject dataJObj = new JSONObject();
				dataJObj.put("data", jarraySubStnDatas.get(i));
				outDataJArray.put(dataJObj);
			}
			ojobj.put("fdrArray", jarraySubStn);
			ojobj.put("dataArray", outDataJArray);

			ojarray.put(ojobj);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ojarray;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getDistinctZoneByLogin(String userName, ModelMap model,
			HttpServletRequest request) {
		System.out.println("getDistinctZoneByLogin==>" + userName);
		if (userName.equalsIgnoreCase("DHBVN") || userName.equalsIgnoreCase("DHBVN")
				|| userName.equalsIgnoreCase("DHBVN")) {
			userName = userName;
		} else if (userName.equalsIgnoreCase("RECMDAS") || userName.equalsIgnoreCase("Admin")||userName.equalsIgnoreCase("RAJGURU")) {
			userName = "%";
		}
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT zone from meter_data.master_main where zone like '" + userName + "'";
			// String sql="SELECT DISTINCT zone from meter_data.master where zone not like
			// ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getPowerStatusReportData(String zone, String circle, String division,
			String subdiv, String from, String to) {
		
		List<Map<String, String>> result=new ArrayList();
		
		
		try {
		
			/*String querySubStn="SELECT substation,accno,customer_name,mtrno,modem_sl_no,zone,circle,division,subdivision from meter_data.master_main WHERE mtrno in( SELECT DISTINCT meter_number FROM meter_data.modem_communication) ";*/
					
			String querySubStn="SELECT\n" +
					"					 	M.accno, \n" +
					"					 	M.name, \n" +
					"					 	MTM.metrno, \n" +
					"					 	M.circle, \n" +
					"					 	M.division, \n" +
					"					 	M.subdiv\n" +
					"					 FROM\n" +
					"					 	meter_data.master M INNER JOIN meter_data.metermaster MTM ON MTM.accno=M.accno\n" +
					"					";
			
			/*if("ALL".equalsIgnoreCase(zone)) {
				
				querySubStn+="";
				
			} else if("ALL".equalsIgnoreCase(circle)) {
				querySubStn+="AND ZONE='"+zone+"'";
			} else if("ALL".equalsIgnoreCase(division)) {
				querySubStn+="AND ZONE='"+zone+"' AND CIRCLE='"+circle+"'";
			} else if("ALL".equalsIgnoreCase(subdiv)) {
				querySubStn+="AND ZONE='"+zone+"' AND CIRCLE='"+circle+"'  AND DIVISION='"+division+"'";
			} else {
				querySubStn+="AND ZONE='"+zone+"' AND CIRCLE='"+circle+"' AND DIVISION='"+division+"' AND SUBDIVISION='"+subdiv+"'";
			}*/
			
            if("ALL".equalsIgnoreCase(circle)) {
				
				querySubStn+="";
				
			} else if("ALL".equalsIgnoreCase(division)) {
				querySubStn+="AND M.CIRCLE='"+circle+"'";
			} else if("ALL".equalsIgnoreCase(subdiv)) {
				querySubStn+=" AND M.CIRCLE='"+circle+"' AND M.DIVISION='"+division+"'" ;
			}  else {
				querySubStn+="AND M.CIRCLE='"+circle+"' AND M.DIVISION='"+division+"' AND M.SUBDIV='"+subdiv+"'";
			}
			
			
			
			querySubStn+="";
		
		
//		System.err.println("---query feeder sun station---:" + querySubStn);
		
		List<?> list = getCustomEntityManager("postgresMdas").createNativeQuery(querySubStn).getResultList();
		for (Iterator<?> iteratorSubStn = list.iterator(); iteratorSubStn.hasNext();) {
			Object[] value = (Object[]) iteratorSubStn.next();
			
			//String substation=String.valueOf(value[0]);
			String accno=String.valueOf(value[0]);
			String name=String.valueOf(value[1]);
			String mtrno=String.valueOf(value[2]);
			//String imei=String.valueOf(value[4]);
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		    SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");		
		    Date toNew=sdf1.parse(to);
		    Calendar c=Calendar.getInstance();
		    c.setTime(toNew);
		    c.add(Calendar.DATE, 1);
		    String toNewS=sdf1.format(c.getTime());
		    
			String query="( SELECT event_time,(CASE WHEN i_r=0 AND i_y=0 AND i_b=0 THEN 'poweroff' WHEN i_r=0 "
					+ "AND i_y=0 AND i_b<>0 THEN '1phase' WHEN i_r=0 AND i_y<>0 AND i_b=0 THEN '1phase' WHEN i_r<>0 AND i_y=0 "
					+ "AND i_b=0 THEN '1phase' WHEN i_r=0 AND i_y<>0 AND i_b<>0 THEN '3Phase' WHEN i_r<>0 AND i_y=0 "
					+ "AND i_b<>0 THEN '3Phase' WHEN i_r<>0 AND i_y<>0 AND i_b=0 THEN '3Phase' WHEN i_r<>0 AND i_y<>0 "
					+ "AND i_b<>0 THEN '3Phase' end) as phase FROM meter_data.events WHERE meter_number='"+mtrno+"' "
					+ "AND event_time =( SELECT max(event_time) FROM meter_data.events WHERE event_time < to_date('"+from+"', 'YYYY-MM-DD') AND meter_number='"+mtrno+"' ) LIMIT 1 ) "
					+ "UNION ALL "
					+ "( SELECT event_time, (CASE WHEN i_r=0 AND i_y=0 AND i_b=0 THEN 'poweroff' WHEN i_r=0 AND i_y=0 "
					+ "AND i_b<>0 THEN '1phase' WHEN i_r=0 AND i_y<>0 AND i_b=0 THEN '1phase' WHEN i_r<>0 AND i_y=0 AND i_b=0 THEN '1phase' "
					+ "WHEN i_r=0 AND i_y<>0 AND i_b<>0 THEN '3Phase' WHEN i_r<>0 AND i_y=0 AND i_b<>0 THEN '3Phase' "
					+ "WHEN i_r<>0 AND i_y<>0 AND i_b=0 THEN '3Phase' WHEN i_r<>0 AND i_y<>0 AND i_b<>0 THEN '3Phase' end ) as phase "
					+ "FROM meter_data.events WHERE meter_number='"+mtrno+"' AND event_time >=to_date('"+from+"', 'YYYY-MM-DD') "
					+ "AND event_time <=to_date('"+toNewS+"', 'YYYY-MM-DD') ORDER BY event_time ) ";
			
//			System.err.println("---get event details--- : "+query);
			
			List<?> list1 = getCustomEntityManager("postgresMdas").createNativeQuery(query).getResultList();
			System.out.println("query===========" + query);
			int count = list1.size();
			System.out.println("count==" + count);
			
			Map<String, String> map=new HashMap<>();
			map.put("mtrNo", mtrno);
			//map.put("zone", String.valueOf(value[5]));
			map.put("circle", String.valueOf(value[3]));
			map.put("div", String.valueOf(value[4]));
			map.put("subdiv", String.valueOf(value[5]));
			//map.put("imei", imei);
			//map.put("substation", substation);
			map.put("accno", accno);
			map.put("name", name);
			map.put("date", from);
			
			long powerOn=0;
			long powerOff=0;
			long phase1=0;
			long phase3=0;
			
			if (!list1.isEmpty()) {
				
				//String fromTime=from+" 00:00:00";
				Date fromTime=sdf.parse(from+" 00:00:00");
				Date toTime=sdf.parse(toNewS+" 00:00:00");
				Date lastTime=null;
				String pStatus="";
				for (Iterator<?> iterator = list1.iterator(); iterator.hasNext();) {
					Object[] obj = (Object[]) iterator.next();
					
					String edate=String.valueOf(obj[0]);
					String status=String.valueOf(obj[1]);
					Date edate_D=sdf.parse(edate);
					
					
					if(fromTime.compareTo(edate_D)>0) {
						pStatus=String.valueOf(obj[1]);
						
					}else {
						long diff=0;
						if(lastTime!=null) {
							diff = edate_D.getTime() - lastTime.getTime();
						} else {
							diff = edate_D.getTime() - fromTime.getTime();
						}
						//diff=diff / 1000 / 60/60;
						lastTime=edate_D;
						
						if("poweroff".equalsIgnoreCase(pStatus)) {
							powerOff+=diff;
						} else if("3Phase".equalsIgnoreCase(pStatus)) {
							phase3+=diff;
						} else if("1phase".equalsIgnoreCase(pStatus)) {
							phase1+=diff;
						}
						pStatus=String.valueOf(obj[1]);
						
				        System.out.println ("Days: " + diff / 1000 / 60/60 );
						
						
					}
					
					
					System.out.println("String eDate : "+edate);
					System.out.println("Date eDate : "+edate_D);
					System.out.println("Status : "+status);
					
				}
				
				if(lastTime!=null) {
					double diff1 = toTime.getTime() - lastTime.getTime();
					//diff1=diff1 / 1000 / 60/60;
					if("poweroff".equalsIgnoreCase(pStatus)) {
						powerOff+=diff1;
					} else if("3Phase".equalsIgnoreCase(pStatus)) {
						phase3+=diff1;
					} else if("1phase".equalsIgnoreCase(pStatus)) {
						phase1+=diff1;
					}
				} else {
					double diff1 = toTime.getTime() - fromTime.getTime();
					//diff1=diff1 / 1000 / 60/60;
					if("poweroff".equalsIgnoreCase(pStatus)) {
						powerOff+=diff1;
					} else if("3Phase".equalsIgnoreCase(pStatus)) {
						phase3+=diff1;
					} else if("1phase".equalsIgnoreCase(pStatus)) {
						phase1+=diff1;
					}
				}
				
								
				
			}
			
			
			
			map.put("powerOn", getHourMinute(phase1+phase3));
			map.put("powerOff", getHourMinute(86400000-(phase1+phase3) ));
			map.put("phase1", getHourMinute(phase1));
			map.put("phase3", getHourMinute(phase3));
			result.add(map);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
			
		
	}

	public boolean daysBetween(String from,String  to, String test) throws java.text.ParseException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fromD=sdf.parse(from);
		Date toD=sdf.parse(to);
		Date testD=sdf.parse(test);

		
		long diff = testD.getTime() - fromD.getTime();
		long diff1 = toD.getTime() - testD.getTime();

		if((diff*diff1)>=0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public String getHourMinute(long diff) {
		
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		String s=diffHours+":"+diffMinutes+" Hrs";
		
		if(diffDays!=0) {
			s="23:59 Hrs";
		}
		
		
		System.out.println(s);
		return s;
		
	}

	@Override
	public List<FeederMasterEntity> showDistrictByCircle(String zone, String circle) {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT district from meter_data.master_main WHERE zone like '" + zone
					+ "' and circle LIKE '" + circle + "'";
			// String sql="SELECT DISTINCT division from meter_data.master WHERE zone like
			// '"+zone+"' and "
			// + "circle LIKE '"+circle+"' and division not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getDistinctMtrMake() {
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT mtrmake from meter_data.master_main";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getDistinctSubstations(String zone, String circle, String division, String subdiv) {
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT substation  from meter_data.master_main WHERE zone like '" + zone
					+ "' and circle LIKE '" + circle + "' and division like '" + division + "' and subdivision like '"+subdiv+"'";
			// String sql="SELECT DISTINCT subdivision from meter_data.master WHERE zone
			// like '"+zone+"' and circle LIKE '"+circle+"' "
			// + "and division like '"+division+"' and subdivision not like ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getDistinctFeeders(String zone, String circle, String division, String subdiv, String substn) {
		try {
			String sql = "SELECT DISTINCT fdrname  from meter_data.master_main WHERE zone like '" + zone
					+ "' and circle LIKE '" + circle + "' and division like '" + division + "' and subdivision like '"+subdiv+"' and substation='"+substn+"'";
			
			return getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getDistinctAmiZone() {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT zone from meter_data.amilocation where zone is not null order by zone";
			// String sql="SELECT DISTINCT zone from meter_data.master where zone not like
			// ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getCircleByZone() {
		String sql="";
		List<?> List=null;
		try {
			String qry="select distinct circle from meter_data.amilocation";
			List=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			System.out.println("circle...."+List);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List;
	}

	@Override
	public List<?> getDivision(String circle) {
		String sql="";
		List<?> List=null;
		try {
			System.out.println("entered into division....");
			String qry="select distinct division,division_code from meter_data.amilocation where circle='"+circle+"'";
			List=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			System.out.println("division...."+List);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List;
    }
	
	
	public List<?> getAtcLosses(String billmonth,String period,String townCode,HttpSession session) {
		System.out.println("atcloss..");
    	String officeType=(String)session.getAttribute("officeType");
		List<?> list=null;
		List<Map<String, Object>>  data=new ArrayList<>();
        String sql="";
        String TableName = "";
              try {
            	  
            	  if(period.equalsIgnoreCase("12")){
            		  TableName = "rpt_eamainfeeder_losses_12months";
            	  }
            	 if(period.equalsIgnoreCase("10")){
            		 TableName = "rpt_eamainfeeder_losses_10months";
            	  }  
            	 if(period.equalsIgnoreCase("08")){
            		 TableName = "rpt_eamainfeeder_losses_08months";
           	  } 
            	 if(period.equalsIgnoreCase("06")){
            		 TableName = "rpt_eamainfeeder_losses_06months";
           	  }
            	 if(period.equalsIgnoreCase("04")){
            		 TableName = "rpt_eamainfeeder_losses_04months";
           	  }
            	 if(period.equalsIgnoreCase("02")){
            		 TableName = "rpt_eamainfeeder_losses_02months";
           	  }
            	 if(period.equalsIgnoreCase("00")){
            		 TableName = "rpt_eamainfeeder_losses_00months";
              	  }  
            	  
            	  
            	  sql= "SELECT fdr_name, round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
            			  "	(Select fdr_name,\n" +
            			  "	round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
            			  "	round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
            			  "	round((unit_billed/unit_supply),4) AS ActualBilling_efficiency,\n" +
            			  "	round((amt_collected/amt_billed),4) AS Actualcollection_efficiency	from  meter_data."+TableName+" \n" +
            			  "	where month_year = '"+billmonth+"' and town_code = '"+townCode+"')A";
				   /*sql="SELECT DISTINCT B.division,round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as atc_loss_percent  FROM\n" +
								"(select round((unit_billed/unit_supply)*100,2) AS billing_efficiency,\n" +
								"round((amt_collected/amt_billed)*100,2) AS collection_efficiency,\n" +
								"round((unit_billed/unit_supply),2) AS ActualBilling_efficiency,\n" +
								"round((amt_collected/amt_billed),2) AS Actualcollection_efficiency,\n" +
								"round(((tech_loss/unit_billed)*100),2) as tech_loss_perc,office_id,month_year\n" +
								"FROM meter_data.rpt_ea_feeder_losses )A,\n" +
								"(select circle,division_code,sitecode,circle_code,division ,zone from meter_data.amilocation )B "
								+ "where A.office_id=B.sitecode and month_year BETWEEN '"+fmonth+"' and '"+tmonth+"' and B.circle ='"+circle+"'";*/
						System.out.println("sql query..."+sql);
						list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

						for(Iterator<?> iterator=list.iterator();iterator.hasNext();)
						{
				      	       final Object[] values=(Object[]) iterator.next();
				      	       Map<String, Object> map=new HashMap<>();
				      	       map.put("y", Double.parseDouble(values[1]+""));
				      	       map.put("label", values[0]+"");
				      	       data.add(map);
						}
              }catch(Exception e) {
            	  e.printStackTrace();
              }
					
		return data;
	}
	
	public List<?> getTdLosses(String billmonth,String period,String townCode){
		System.out.println("tdloss..");
		List<?> list=null;
		List<Map<String, Object>>  data=new ArrayList<>();
        String sql="";
        String TableName = "";
					try {
						/*sql="SELECT DISTINCT B.division,A.tech_loss_perc FROM\n" +
								"(select round(((unit_supply-unit_billed)*100)/unit_supply,2) as tech_loss_perc,office_id,month_year\n" +
								"FROM meter_data.rpt_ea_feeder_losses )A,\n" +
								"(select circle,division_code,circle_code,sitecode,division ,zone from meter_data.amilocation )B where A.office_id=B.sitecode\n" +
								"and month_year BETWEEN '"+fmonth+"' and '"+tmonth+"' and B.circle='"+circle+"'";*/
						  if(period.equalsIgnoreCase("12")){
		            		  TableName = "rpt_eamainfeeder_losses_12months";
		            	  }
		            	 if(period.equalsIgnoreCase("10")){
		            		 TableName = "rpt_eamainfeeder_losses_10months";
		            	  }  
		            	 if(period.equalsIgnoreCase("08")){
		            		 TableName = "rpt_eamainfeeder_losses_08months";
		           	  } 
		            	 if(period.equalsIgnoreCase("06")){
		            		 TableName = "rpt_eamainfeeder_losses_06months";
		           	  }
		            	 if(period.equalsIgnoreCase("04")){
		            		 TableName = "rpt_eamainfeeder_losses_04months";
		           	  }
		            	 if(period.equalsIgnoreCase("02")){
		            		 TableName = "rpt_eamainfeeder_losses_02months";
		           	  }
		            	 if(period.equalsIgnoreCase("00")){
		            		 TableName = "rpt_eamainfeeder_losses_00months";
		              	  }  
						
						sql = "select fdr_name, tech_loss\n" +
								"FROM meter_data."+TableName+" where month_year = '"+billmonth+"' AND town_code = '"+townCode+"'";
						
						
						list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

						for(Iterator<?> iterator=list.iterator();iterator.hasNext();)
						{
				      	       final Object[] values=(Object[]) iterator.next();
				      	       Map<String, Object> map=new HashMap<>();
				      	       map.put("y", Double.parseDouble(values[1]+""));
				      	       map.put("label", values[0]+"");
				      	       data.add(map);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
		return data;
		
	}

	@Override
	public List<?> getBillingEffLosses(String billmonth,String period,String townCode) {
		System.out.println("Billing-Efficiency-loss..");
		List<?> list=null;
		List<Map<String, Object>>  data=new ArrayList<>();
        String sql="";
        String TableName = "";
					try {
						
						  if(period.equalsIgnoreCase("12")){
		            		  TableName = "rpt_eamainfeeder_losses_12months";
		            	  }
		            	 if(period.equalsIgnoreCase("10")){
		            		 TableName = "rpt_eamainfeeder_losses_10months";
		            	  }  
		            	 if(period.equalsIgnoreCase("08")){
		            		 TableName = "rpt_eamainfeeder_losses_08months";
		           	  } 
		            	 if(period.equalsIgnoreCase("06")){
		            		 TableName = "rpt_eamainfeeder_losses_06months";
		           	  }
		            	 if(period.equalsIgnoreCase("04")){
		            		 TableName = "rpt_eamainfeeder_losses_04months";
		           	  }
		            	 if(period.equalsIgnoreCase("02")){
		            		 TableName = "rpt_eamainfeeder_losses_02months";
		           	  }
		            	 if(period.equalsIgnoreCase("00")){
		            		 TableName = "rpt_eamainfeeder_losses_00months";
		              	  }
						
						
						
						/*sql="SELECT DISTINCT B.division,A.billing_efficiency FROM\n" +
								"(select round((unit_billed/unit_supply)*100,2) AS billing_efficiency,office_id,month_year\n" +
								"FROM meter_data.rpt_ea_feeder_losses )A,\n" +
								"(select circle,division_code,circle_code,division,sitecode ,zone from meter_data.amilocation )B where A.office_id=B.sitecode \n" +
								"and month_year BETWEEN '"+fmonth+"' and '"+tmonth+"' and B.circle ='"+circle+"'";*/
						
						sql = "select fdr_name, round((unit_billed/unit_supply)*100,2) AS billing_efficiency	FROM meter_data."+TableName+" \n" +
								"where month_year = '"+billmonth+"' AND  town_code = '"+townCode+"' ";
						
						
						
						list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

						for(Iterator<?> iterator=list.iterator();iterator.hasNext();)
						{
				      	       final Object[] values=(Object[]) iterator.next();
				      	       Map<String, Object> map=new HashMap<>();
				      	       map.put("y", Double.parseDouble(values[1]+""));
				      	       map.put("label", values[0]+"");
				      	       data.add(map);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
		return data;
	}

	@Override
	public List<?> getCollectionEffLosses(String billmonth,String period,String townCode) {
		System.out.println("Collection-Efficiency-loss..");
		List<?> list=null;
		List<Map<String, Object>>  data=new ArrayList<>();
        String sql="";
        String TableName = "";
					try {
								if(period.equalsIgnoreCase("12")){
									TableName = "rpt_eamainfeeder_losses_12months";
				              }
				            	 if(period.equalsIgnoreCase("10")){
				            		 TableName = "rpt_eamainfeeder_losses_10months";
				              }  
				            	 if(period.equalsIgnoreCase("08")){
				            		 TableName = "rpt_eamainfeeder_losses_08months";
				           	  } 
				            	 if(period.equalsIgnoreCase("06")){
				            		 TableName = "rpt_eamainfeeder_losses_06months";
				           	  }
				            	 if(period.equalsIgnoreCase("04")){
				            		 TableName = "rpt_eamainfeeder_losses_04months";
				           	  }
				            	 if(period.equalsIgnoreCase("02")){
				            		 TableName = "rpt_eamainfeeder_losses_02months";
				           	  }
				            	 if(period.equalsIgnoreCase("00")){
				            		 TableName = "rpt_eamainfeeder_losses_00months";
				              	  }
						
						
						/*sql="SELECT DISTINCT B.division,A.collection_efficiency FROM\n" +
								"(select round((amt_collected/amt_billed)*100,2) AS collection_efficiency,office_id,month_year\n" +
								"FROM meter_data.rpt_ea_feeder_losses )A,\n" +
								"(select circle,division_code,circle_code,division ,sitecode,zone from meter_data.amilocation )B where A.office_id=B.sitecode \n" +
								"and month_year BETWEEN '"+fmonth+"' and '"+tmonth+"' and B.circle ='"+circle+"'";*/
						
						
						sql = "select fdr_name, round((amt_collected/amt_billed)*100,2) AS collection_efficiency	FROM meter_data."+TableName+" \n" +
								"where month_year = '"+billmonth+"' AND  town_code = '"+townCode+"' ";
						
						list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

						for(Iterator<?> iterator=list.iterator();iterator.hasNext();)
						{
				      	       final Object[] values=(Object[]) iterator.next();
				      	       Map<String, Object> map=new HashMap<>();
				      	       map.put("y", Double.parseDouble(values[1]+""));
				      	       map.put("label", values[0]+"");
				      	       data.add(map);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
		return data;
	}

	@Override
	public List<?> getMeterDetails(String zone, String circle, String division, String subdivision, String meterType,
			String townCode) {

		String sql="";
		List<?> List=null;
		try {
			String qry="SELECT m.subdivision,ami.town_ipds, m.mtrno, (CASE  " + 
					"		WHEN m.fdrcategory='FEEDER METER' THEN fdrname  " + 
					"		WHEN m.fdrcategory='BOUNDARY METER' THEN  customer_name " + 
					"		WHEN m.fdrcategory='DT' THEN customer_name " + 
					"		END) as meter_name,  " + 
					"     (CASE " + 
					"		WHEN m.fdrcategory='FEEDER METER' THEN fdrcode " + 
					"		WHEN m.fdrcategory='BOUNDARY METER' THEN  accno " + 
					"		WHEN m.fdrcategory='DT' THEN accno " + 
					"	 END) as meter_code  , " + 
					"   m.phase, m.mtrmake,  m.mf, " + 
					"   m.sdocode" + 
					" FROM" + 
					"(select * from meter_data.master_main where zone like '"+zone+"' and circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"' and fdrcategory like '"+meterType+"' AND mtrno is not NULL AND mtrno<>'' and town_code='"+townCode+"') m," + 
					"(SELECT tp_towncode, town_ipds,CAST(sitecode as TEXT) FROM meter_data.amilocation where " + 
					"circle like '"+circle+"' and division like '"+division+"' and subdivision like '"+subdivision+"'  GROUP BY tp_towncode, town_ipds,sitecode) ami" + 
					"  WHERE ami.tp_towncode = m.town_code " + 
					" ORDER BY m.mtrno;";
			System.out.println("qry...."+qry);
			List=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			System.out.println("Details...."+List);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List;
	}

	@Override
	public List getOldMtrDataforMtrChange(String meterno) {
	
		List<?> result=new ArrayList<>();
		Date d1=new Date();
		String month=new SimpleDateFormat("yyyyMM").format(d1);
		/*String qry="SELECT * FROM\n" +
					"(select m.mtrno,m.mtrmake,m.mf,phase,m.contractdemand,  \n" +
					"(CASE  WHEN m.fdrcategory='FEEDER METER' THEN fdrname WHEN m.fdrcategory='BOUNDARY METER' THEN  customer_name WHEN m.fdrcategory='DT' THEN customer_name END) as loc_name, \n" +
					"(CASE 	WHEN m.fdrcategory='FEEDER METER' THEN fdrcode WHEN m.fdrcategory='BOUNDARY METER' THEN  accno 	WHEN m.fdrcategory='DT' THEN accno 	END) as loc_code\n" +
					"from meter_data.master_main m WHERE mtrno='"+meterno+"')A\n" +
					"LEFT JOIN\n" +
					"(SELECT kwh as lkwh,kvah,meter_number FROM meter_data.amiinstantaneous WHERE meter_number='"+meterno+"' \n" +
					"  ORDER BY read_time desc LIMIT 1)B\n" +
					"ON   A.mtrno=B.meter_number";*/
		
		String qry="SELECT C.*,D.maxdemand FROM\n" +
					"(SELECT A.*,B.lkwh,B.kvah FROM\n" +
					"(select m.mtrno,m.mtrmake,m.mf,phase,\n" +
					"(CASE  WHEN m.fdrcategory='FEEDER METER' THEN fdrname WHEN m.fdrcategory='BOUNDARY METER' THEN  customer_name WHEN m.fdrcategory='DT' THEN customer_name END) as loc_name, \n" +
					"location_id as loc_code\n" +
					"from meter_data.master_main m WHERE mtrno='"+meterno+"')A\n" +
					"LEFT JOIN\n" +
					"(SELECT cum_active_import_energy as lkwh,cum_apparent_import_energy as kvah,mtrno as meter_number  FROM meter_data.daily_load WHERE mtrno='"+meterno+"' \n" +
					" and rtc_date_time<= CURRENT_DATE ORDER BY rtc_date_time desc LIMIT 1)B\n" +
					"ON   A.mtrno=B.meter_number)C\n" +
					"LEFT JOIN\n" +
					"(select max(kw) as maxdemand,meter_number from meter_data.load_survey l\n" +
					"WHERE meter_number='"+meterno+"' AND to_char(read_time,'yyyyMM')='"+month+"' GROUP BY meter_number)D\n" +
					"ON C.mtrno=D.meter_number";
		
		System.out.println("getdata--"+qry);
		result= (List<?>) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		return result;
	}

	@Override
	public List getdtTpIDbyTowncode(String townCode) {
		List<?> result=new ArrayList<>();
		
		String qry=" SELECT DISTINCT dttpid FROM ( SELECT count(*),dttpid,tp_town_code FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!=''"
				+ " and meterno IS NOT NULL and meterno!='' AND  tp_town_code='"+townCode+"' "
				+ "GROUP BY dttpid,tp_town_code HAVING count(*)>1)b";		
		System.out.println("getdata--"+qry);
		result= (List<?>) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		return result;
	}

	@Override
	public List<?> getDTMeterChangeDetails(String townCode) {
		// TODO Auto-generated method stub
		List<?> result=new ArrayList<>();
		
		  String sql="select mm.town_code,mm.fdrname,mm.fdrcode,mm.accno as dtcode,mm.customer_name as dtname,mm.old_mtr_no,mct.oldmtrmake,\n"+
		  "mct.oldmf,mct.lastinstataneouskwh as lastreading,mm.mtrno,mct.newmtrmake,mct.newmf,mct.initialreading,\n"
		  + "mct.entryby,mct.entrydate,mct.reason\n" +
		  "from meter_data.master_main  mm, meter_data.meterchange_transhistory mct where mm.mtrno in(\n"+
		  "select distinct meterno from meter_data.dtdetails where  meterchangeflag=1 \n"
		  + "and tp_town_code='"+townCode+"' and meterno <> ''\n" +
		  ") and mm.mtrno=mct.newmeterno";
		 
		/*
		 * String sql="SELECT a.*,b.town_ipds FROM(\n" +
		 * "select mm.town_code,mm.fdrname,mm.fdrcode,mm.accno as dtcode,mm.customer_name as dtname,mm.old_mtr_no,mct.oldmtrmake,\n"
		 * +
		 * "				mct.oldmf,mct.lastinstataneouskwh as lastreading,mm.mtrno,mct.newmtrmake,mct.newmf,mct.initialreading,\n"
		 * + "				mct.entryby,mct.entrydate,mct.reason\n" +
		 * "				from meter_data.master_main  mm, meter_data.meterchange_transhistory mct where mm.mtrno in(\n"
		 * +
		 * "				select distinct meterno from meter_data.dtdetails where  meterchangeflag=1 \n"
		 * + "				and tp_town_code='"+townCode+"' and meterno <> ''\n" +
		 * "				) and mm.mtrno=mct.newmeterno)a,\n" +
		 * "				(select  town_ipds from meter_data.amilocation WHERE tp_towncode='"
		 * +townCode+"')b ";
		 */
		//System.out.println("meter change-----"+sql);
		result= (List<?>) getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return result;
	}

	@Override
	public List<?> getBoundaryMeterChangeDetails(String townCode) {
		List<?> result=new ArrayList<>();
		
		
		
		  String sql="select mm.town_code,mm.fdrname,mm.fdrcode,mm.accno as boundarycode,mm.customer_name as boundaryname,mm.old_mtr_no,mct.oldmtrmake,\n"
		  +"mct.oldmf,mct.lastinstataneouskwh as lastreading,mm.mtrno,mct.newmtrmake,mct.newmf,mct.initialreading,\n"
		  + "mct.entryby,mct.entrydate,mct.reason\n" +
		  "from meter_data.master_main  mm, meter_data.meterchange_transhistory mct where mm.mtrno in(\n"+
		  "select distinct meterno from meter_data.feederdetails where  meterchangeflag=1 and crossfdr='1' \n"
		  + "and tp_town_code='"+townCode+"' and meterno <> ''\n" +
		  ") and mm.mtrno=mct.newmeterno";
		 
		/*
		 * String sql="SELECT a.*,b.town_ipds FROM(\n" +
		 * "select mm.town_code,mm.fdrname,mm.fdrcode,mm.accno as boundarycode,mm.customer_name as boundaryname,mm.old_mtr_no,mct.oldmtrmake,\n"
		 * +
		 * "mct.oldmf,mct.lastinstataneouskwh as lastreading,mm.mtrno,mct.newmtrmake,mct.newmf,mct.initialreading,\n"
		 * + "mct.entryby,mct.entrydate,mct.reason\n" +
		 * "from meter_data.master_main  mm, meter_data.meterchange_transhistory mct where mm.mtrno in(\n"
		 * +
		 * "select distinct meterno from meter_data.feederdetails where  meterchangeflag=1 and crossfdr='1' \n"
		 * + "and tp_town_code='"+townCode+"' and meterno <> ''\n" +
		 * ") and mm.mtrno=mct.newmeterno)a,\n" +
		 * "(select  town_ipds from meter_data.amilocation WHERE tp_towncode='"+townCode
		 * +"')b";
		 */
		 //System.out.println("meter change-----"+sql);	
		result= (List<?>) getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return result;
	}

	@Override
	public List<?> getFeederMeterChangeDetails(String townCode) {
		List<?> result=new ArrayList<>();
		
		  String sql="select mm.town_code,mm.fdrname,mm.fdrcode,mm.old_mtr_no,mct.oldmtrmake,\n" +
		  "mct.oldmf,mct.lastinstataneouskwh as lastreading,mm.mtrno,mct.newmtrmake,mct.newmf,mct.initialreading,\n"
		  + "mct.entryby,mct.entrydate,mct.reason\n" +
		  "from meter_data.master_main  mm, meter_data.meterchange_transhistory mct where mm.mtrno in(\n"+
		  "select distinct meterno from meter_data.feederdetails where  meterchangeflag=1 and crossfdr='0' \n"
		  + "and tp_town_code='"+townCode+"' and meterno <> ''\n" +
		  ") and mm.mtrno=mct.newmeterno";
		 
		
		/*
		 * String sql="SELECT a.*,b.town_ipds FROM(\n" +
		 * "select mm.town_code,mm.fdrname,mm.fdrcode,mm.old_mtr_no,mct.oldmtrmake,\n" +
		 * "mct.oldmf,mct.lastinstataneouskwh as lastreading,mm.mtrno,mct.newmtrmake,mct.newmf,mct.initialreading,\n"
		 * + "mct.entryby,mct.entrydate,mct.reason\n" +
		 * "from meter_data.master_main  mm, meter_data.meterchange_transhistory mct where mm.mtrno in(\n"
		 * +
		 * "select distinct meterno from meter_data.feederdetails where  meterchangeflag=1 and crossfdr='0' \n"
		 * + "and tp_town_code='"+townCode+"' and meterno <> ''\n" +
		 * ") and mm.mtrno=mct.newmeterno)a,\n" +
		 * "(select  town_ipds from meter_data.amilocation WHERE tp_towncode='"
		 * +townCode+"')b ";
		 */
	//System.out.println("meter change-----"+sql);
		result= (List<?>) getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FeederMasterEntity> getZoneByLogin(String officeCode) {
		List<FeederMasterEntity> list = null;
		try {
			String sql = "SELECT DISTINCT zone from meter_data.amilocation"
					+ " WHERE circle_code = '"+ officeCode + "'"
					+ " and zone is not null order by zone ";
			// String sql="SELECT DISTINCT zone from meter_data.master where zone not like
			// ''";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void getChangeMeterPDF(HttpServletRequest request, HttpServletResponse response, String zne, String crcl,
			String dvn, String sdiv, String meterType, String townCode) {

		String zone="",circle="",division="",subdiv="";
		try {
			if(zne=="%")
			{
				zone="ALL";
			}else {
				zone=zne;
			}
			if(crcl=="%")
			{
				circle="ALL";
			}else {
				circle=crcl;
			}
			if(dvn=="%")
			{
				division="ALL";
			}else {
				division=dvn;
			}
			if(sdiv=="%")
			{
				subdiv="ALL";
			}else {
				subdiv=sdiv;
			}			
			
			Rectangle pageSize = new Rectangle(1250, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("Change Meter ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Zone :"+zone,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Circle :"+circle,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    
		    headerCell = new PdfPCell(new Phrase("Division :"+division,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Sub-Division :"+subdiv,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Town :"+townName,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
		    headerCell = new PdfPCell(new Phrase("Meter Type :"+meterType,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> ChangeMtrData=null;
			query="SELECT m.subdivision,ami.town_ipds,m.fdrcategory, m.mtrno, (CASE WHEN m.fdrcategory='FEEDER METER' THEN fdrname WHEN m.fdrcategory='BOUNDARY METER' THEN  customer_name"
					+ " WHEN m.fdrcategory='DT' THEN customer_name END) as meter_name,(CASE WHEN m.fdrcategory='FEEDER METER' THEN fdrcode WHEN m.fdrcategory='BOUNDARY METER' THEN  accno"
					+ " WHEN m.fdrcategory='DT' THEN accno END) as meter_code ,m.phase, m.mtrmake,m.mf,m.sdocode FROM(select * from meter_data.master_main where zone like '"+zne+"' "
					+ " and circle like '"+crcl+"' and division like '"+dvn+"' and subdivision like '"+sdiv+"' and fdrcategory like '"+meterType+"' AND mtrno is not NULL AND mtrno<>'' "
					+ " and town_code='"+townCode+"') m,(SELECT tp_towncode, town_ipds,CAST(sitecode as TEXT) FROM meter_data.amilocation where circle like '"+crcl+"' and division like '"+dvn+"' and "
					+ " subdivision like '"+sdiv+"'  GROUP BY tp_towncode, town_ipds,sitecode) ami  WHERE ami.tp_towncode = m.town_code  ORDER BY m.mtrno";
			ChangeMtrData=postgresMdas.createNativeQuery(query).getResultList();
			
			PdfPTable parameterTable = new PdfPTable(10);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("SL.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("SUB-DIV NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TOWN NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("METER TYPE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("METER NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("PHASE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("METER MAKE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("MF",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < ChangeMtrData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=ChangeMtrData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
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
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=ChangeMeter.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	@Override
	public void getFeederMtrPDF(HttpServletRequest request, HttpServletResponse response, String zne, String crcl,
			String dvn, String sdiv, String towncode, String townname, String metertype) {

		String zone="",circle="",division="",subdiv="";
		try {
			if(zne=="%")
			{
				zone="ALL";
			}else {
				zone=zne;
			}
			if(crcl=="%")
			{
				circle="ALL";
			}else {
				circle=crcl;
			}
			if(dvn=="%")
			{
				division="ALL";
			}else {
				division=dvn;
			}
			if(sdiv=="%")
			{
				subdiv="ALL";
			}else {
				subdiv=sdiv;
			}			
			
			Rectangle pageSize = new Rectangle(1650, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("Meter Change Report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Zone :"+zone,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Circle :"+circle,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Division :"+division,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Sub-Division :"+subdiv,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Meter Type :"+metertype,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> BoundaryMtrchangeData=null;
			query="select mm.subdivision,mm.town_code,mm.fdrname,mm.fdrcode,mm.old_mtr_no,mct.oldmtrmake,\n" +
					"				mct.oldmf,mct.lastinstataneouskwh as lastreading,mm.mtrno,mct.newmtrmake,mct.newmf,mct.initialreading,\n" +
					"				mct.entryby,mct.entrydate,mct.reason\n" +
					"				from meter_data.master_main  mm, meter_data.meterchange_transhistory mct where mm.mtrno in(\n" +
					"				select distinct meterno from meter_data.feederdetails where  meterchangeflag=1 and crossfdr='0' \n" +
					"				and tp_town_code='"+towncode+"' and meterno <> ''\n" +
					"				) and mm.mtrno=mct.newmeterno";
			BoundaryMtrchangeData=postgresMdas.createNativeQuery(query).getResultList();
			
			PdfPTable parameterTable = new PdfPTable(16);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("Sl.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("TownName",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TownCode",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FeederName",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FeederCode",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMeterNo",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMeterMake",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMF",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMeterLastReading",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMeterNo",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMeterMake",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMF",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMeterFirstReading",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("ChangedBy",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("ChangedDate",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Reason",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < BoundaryMtrchangeData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=BoundaryMtrchangeData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[12]==null?null:obj[12]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[13]==null?null:obj[13]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[14]==null?null:obj[14]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
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
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=FeederMeterChange.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}	
	
	}

	@Override
	public void getBoundaryMtrPDF(HttpServletRequest request, HttpServletResponse response, String zne, String crcl,
			String dvn, String sdiv, String towncode, String townname, String metertype) {
		
		String zone="",circle="",division="",subdiv="";
		try {
			if(zne=="%")
			{
				zone="ALL";
			}else {
				zone=zne;
			}
			if(crcl=="%")
			{
				circle="ALL";
			}else {
				circle=crcl;
			}
			if(dvn=="%")
			{
				division="ALL";
			}else {
				division=dvn;
			}
			if(sdiv=="%")
			{
				subdiv="ALL";
			}else {
				subdiv=sdiv;
			}			
			
			Rectangle pageSize = new Rectangle(1650, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("Meter Change Report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Zone :"+zone,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Circle :"+circle,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Division :"+division,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Sub-Division :"+subdiv,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Meter Type :"+metertype,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> DTMtrchangeData=null;
			query="select mm.subdivision,mm.town_code,mm.fdrname,mm.fdrcode,mm.accno as boundarycode,mm.customer_name as boundaryname,mm.old_mtr_no,mct.oldmtrmake,\n" +
					"mct.oldmf,mct.lastinstataneouskwh as lastreading,mm.mtrno,mct.newmtrmake,mct.newmf,mct.initialreading,mct.entryby,mct.entrydate,mct.reason\n" +
					"from meter_data.master_main  mm, meter_data.meterchange_transhistory mct where mm.mtrno in(\n" +
					"select distinct meterno from meter_data.feederdetails where  meterchangeflag=1 and crossfdr='1' \n" +
					"and tp_town_code='"+towncode+"' and meterno <> ''\n" +
					") and mm.mtrno=mct.newmeterno";
			DTMtrchangeData=postgresMdas.createNativeQuery(query).getResultList();
			
			   PdfPTable parameterTable = new PdfPTable(18);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("Sl.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("TownName",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TownCode",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FeederName",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FeederCode",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DTCode",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DTName",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMeterNo",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMeterMake",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMF",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMeterLastReading",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMeterNo",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMeterMake",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMF",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMeterFirstReading",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("ChangedBy",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("ChangedDate",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Reason",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < DTMtrchangeData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=DTMtrchangeData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			 parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[12]==null?null:obj[12]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[13]==null?null:obj[13]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[14]==null?null:obj[14]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[15]==null?null:obj[15]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[16]==null?null:obj[16]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
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
			         

			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=BoundaryMeterChange.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}	
	
	
	}

	@Override
	public void getDTMtrPDF(HttpServletRequest request, HttpServletResponse response, String zne, String crcl,
			String dvn, String sdiv, String towncode, String townname, String metertype) {

		String zone="",circle="",division="",subdiv="";
		try {
			if(zne=="%")
			{
				zone="ALL";
			}else {
				zone=zne;
			}
			if(crcl=="%")
			{
				circle="ALL";
			}else {
				circle=crcl;
			}
			if(dvn=="%")
			{
				division="ALL";
			}else {
				division=dvn;
			}
			if(sdiv=="%")
			{
				subdiv="ALL";
			}else {
				subdiv=sdiv;
			}			
			
			Rectangle pageSize = new Rectangle(1650, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("Meter Change Report",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(3);
		    header.setWidths(new int[]{1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Zone :"+zone,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Circle :"+circle,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Division :"+division,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
			/*
			 * headerCell = new PdfPCell(new Phrase("Sub-Division :"+subdiv,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */
		    
		    headerCell = new PdfPCell(new Phrase("Town :"+townname,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Meter Type :"+metertype,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> DTMtrchangeData=null;
			query="select mm.subdivision,mm.town_code,mm.fdrname,mm.fdrcode,mm.accno as dtcode,mm.customer_name as dtname,mm.old_mtr_no,mct.oldmtrmake,\n" +
					"mct.lastinstataneouskwh as lastreading,mm.mtrno,mct.newmtrmake,mct.initialreading,mct.entryby,mct.entrydate,mct.reason\n" +
					"from meter_data.master_main  mm, meter_data.meterchange_transhistory mct where mm.mtrno in(\n" +
					"select distinct meterno from meter_data.dtdetails where  meterchangeflag=1 \n" +
					"and tp_town_code='"+towncode+"' and meterno <> ''\n" +
					") and mm.mtrno=mct.newmeterno";
			
			DTMtrchangeData=postgresMdas.createNativeQuery(query).getResultList();
			
			   PdfPTable parameterTable = new PdfPTable(16);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("Sl.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("TownName",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TownCode",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FeederName",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FeederCode",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("BoundaryCode",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("BoundaryName",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMeterNo",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMeterMake",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("OldMeterLastReading",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMeterNo",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMeterMake",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("NewMeterFirstReading",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("ChangedBy",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("ChangedDate",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Reason",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < DTMtrchangeData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=DTMtrchangeData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			 parameterCell = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[8]==null?null:obj[8]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[9]==null?null:obj[9]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[11]==null?null:obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[12]==null?null:obj[12]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[13]==null?null:obj[13]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[14]==null?null:obj[14]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
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
			                
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=DTMeterChange.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}	
	
	}

	@Override
	public List<?> getCommFailList(String zone, String circle, String towncode) {
		String sql="";
		List<?> List=null;
		try {
			String qry="select mm.*,a.town_ipds as town,a.zone as zone,a.tp_zonecode as zonecode,a.circle as circle,a.tp_circlecode as circlecode from \n" +
					"(SELECT town_code, \"count\"(*),\n" +
					"\"count\"(CASE WHEN c.meter_number is not null then 1 END) as active,\n" +
					"\"count\"(CASE WHEN c.meter_number is  not null and c.difhr>=24 then 1 END) as inactive,\n" +
					"\"count\"(CASE WHEN c.difhr>24 AND c.ldate is not NULL AND c.difhr<=120 THEN 1 END) as inc24h,\n" +
					"\"count\"(CASE WHEN c.difhr>120 AND c.ldate is not NULL AND c.difhr<=240 THEN 1 END) as inc5d,\n" +
					"\"count\"(CASE WHEN c.difhr>240 AND c.ldate is not NULL AND c.difhr<=480 THEN 1 END) as inc10d,\n" +
					"\"count\"(CASE WHEN c.difhr>480 AND c.ldate is not NULL AND c.difhr<=720 THEN 1 END) as inc20d,\n" +
					"\"count\"(CASE WHEN c.difhr>720  THEN 1 END) as inc30d \n" +
					"from meter_data.master_main m LEFT JOIN\n" +
					"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number) c on m.mtrno=c.meter_number\n" +
					"GROUP BY town_code ORDER BY town_code)\n" +
					"mm,meter_data.amilocation a \n" +
					"where a.tp_towncode=mm.town_code and a.tp_towncode like '"+towncode+"' and zone like '"+zone+"' and circle like '"+circle+"' GROUP BY mm.town_code,mm.count,mm.active,mm.inactive,mm.inc24h,mm.inc5d,mm.inc10d,mm.inc20d,mm.inc30d,a.town_ipds,a.zone,a.tp_zonecode,a.circle,a.tp_circlecode ";
			List=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		System.out.println("consis--"+qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List;
	}

	@Override
	public List<?> getTotalConsumersFailData(String id, String towncode) {
		String qry="";
		List<?> list=null;
		
		if(id.equalsIgnoreCase("1")) {
			qry="select mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds as town,a.zone as zone,a.tp_zonecode as zonecode,a.circle as circle,a.tp_circlecode as circlecode  from \n" +
					"(SELECT town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno\n" +
					"from meter_data.master_main m \n" +
					"LEFT JOIN\n" +
					"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number) c \n" +
					"	\n" +
					"on m.mtrno=c.meter_number where mtrno is NOT NULL and m.town_code like '"+towncode+"'\n" +
					"GROUP BY town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno ORDER BY town_code)mm,meter_data.amilocation a \r\n" + 
					"where a.tp_towncode=mm.town_code and a.tp_towncode like '"+towncode+"' \r\n" + 
					"GROUP BY mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds ,a.zone,a.tp_zonecode,a.circle,a.tp_circlecode ";
			list=postgresMdas.createNativeQuery(qry).getResultList();	
		}else if (id.equalsIgnoreCase("2")) {
			qry="select mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds as town,a.zone as zone,a.tp_zonecode as zonecode,a.circle as circle,a.tp_circlecode as circlecode  from \n" +
					"(SELECT town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno\n" +
					"from meter_data.master_main m \n" +
					"LEFT JOIN\n" +
					"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number) c \n" +
					"	\n" +
					"on m.mtrno=c.meter_number where mtrno is NOT NULL and m.town_code like '"+towncode+"'\n" +
					"GROUP BY town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno ORDER BY town_code)mm,meter_data.amilocation a \r\n" + 
					"where a.tp_towncode=mm.town_code and a.tp_towncode like '"+towncode+"' \r\n" + 
					"GROUP BY mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds ,a.zone,a.tp_zonecode,a.circle,a.tp_circlecode ";
			list=postgresMdas.createNativeQuery(qry).getResultList();
		}else if (id.equalsIgnoreCase("3")) {
			qry="select mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds as town,a.zone as zone,a.tp_zonecode as zonecode,a.circle as circle,a.tp_circlecode as circlecode  from \n" +
					"(SELECT town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno\n" +
					"from meter_data.master_main m \n" +
					"LEFT JOIN\n" +
					"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number) c \n" +
					"	\n" +
					"on m.mtrno=c.meter_number where c.difhr>=24 and  c.ldate is not NULL AND meter_number is  not NULL and m.town_code like'"+towncode+"'\n" +
					"GROUP BY town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno ORDER BY town_code)mm,meter_data.amilocation a \r\n" + 
					"where a.tp_towncode=mm.town_code and a.tp_towncode like '"+towncode+"' \r\n" + 
					"GROUP BY mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds ,a.zone,a.tp_zonecode,a.circle,a.tp_circlecode";
			list=postgresMdas.createNativeQuery(qry).getResultList();
			System.out.println(qry);
		}else if (id.equalsIgnoreCase("4")) {
			qry="select mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds as town,a.zone as zone,a.tp_zonecode as zonecode,a.circle as circle,a.tp_circlecode as circlecode  from \n" +
					"(SELECT town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno\n" +
					"from meter_data.master_main m \n" +
					"LEFT JOIN\n" +
					"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number) c \n" +
					"	\n" +
					"on m.mtrno=c.meter_number where c.difhr>24 AND c.ldate is not NULL AND c.difhr<=120 and m.town_code like'"+towncode+"'\n" +
					"GROUP BY town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno ORDER BY town_code)mm,meter_data.amilocation a \r\n" + 
					"where a.tp_towncode=mm.town_code and a.tp_towncode like '"+towncode+"' \r\n" + 
					"GROUP BY mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds ,a.zone,a.tp_zonecode,a.circle,a.tp_circlecode ";
			list=postgresMdas.createNativeQuery(qry).getResultList();
		}else if (id.equalsIgnoreCase("5")) {
			qry="select mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds as town,a.zone as zone,a.tp_zonecode as zonecode,a.circle as circle,a.tp_circlecode as circlecode  from \n" +
					"(SELECT town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno\n" +
					"from meter_data.master_main m \n" +
					"LEFT JOIN\n" +
					"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number) c \n" +
					"	\n" +
					"on m.mtrno=c.meter_number where c.difhr>120 AND c.ldate is not NULL AND c.difhr<=240 and m.town_code like'"+towncode+"'\n" +
					"GROUP BY town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno ORDER BY town_code)mm,meter_data.amilocation a \r\n" + 
					"where a.tp_towncode=mm.town_code and a.tp_towncode like '"+towncode+"' \r\n" + 
					"GROUP BY mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds ,a.zone,a.tp_zonecode,a.circle,a.tp_circlecode";
					list=postgresMdas.createNativeQuery(qry).getResultList();		
		}else if (id.equalsIgnoreCase("6")) {
			qry="select mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds as town,a.zone as zone,a.tp_zonecode as zonecode,a.circle as circle,a.tp_circlecode as circlecode  from \n" +
					"(SELECT town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno\n" +
					"from meter_data.master_main m \n" +
					"LEFT JOIN\n" +
					"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number) c \n" +
					"	\n" +
					"on m.mtrno=c.meter_number where c.difhr>240 AND c.ldate is not NULL AND c.difhr<=480 and m.town_code like'"+towncode+"'\n" +
					"GROUP BY town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno ORDER BY town_code)mm ,meter_data.amilocation a \r\n" + 
					"where a.tp_towncode=mm.town_code and a.tp_towncode like '"+towncode+"' \r\n" + 
					"GROUP BY mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds ,a.zone,a.tp_zonecode,a.circle,a.tp_circlecode";
					list=postgresMdas.createNativeQuery(qry).getResultList();		
		}else if (id.equalsIgnoreCase("7")) {
			qry="select mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds as town,a.zone as zone,a.tp_zonecode as zonecode,a.circle as circle,a.tp_circlecode as circlecode  from \n" +
					"(SELECT town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno\n" +
					"from meter_data.master_main m \n" +
					"LEFT JOIN\n" +
					"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number) c \n" +
					"	\n" +
					"on m.mtrno=c.meter_number where c.difhr>480 AND c.ldate is not NULL AND c.difhr<=720 and m.town_code like'"+towncode+"'\n" +
					"GROUP BY town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno ORDER BY town_code)mm,meter_data.amilocation a \r\n" + 
					"where a.tp_towncode=mm.town_code and a.tp_towncode like '"+towncode+"' \r\n" + 
					"GROUP BY mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds ,a.zone,a.tp_zonecode,a.circle,a.tp_circlecode ";
					list=postgresMdas.createNativeQuery(qry).getResultList();		
		}else if (id.equalsIgnoreCase("8")) {
			qry="select mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds as town,a.zone as zone,a.tp_zonecode as zonecode,a.circle as circle,a.tp_circlecode as circlecode  from \n" +
					"(SELECT town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno\n" +
					"from meter_data.master_main m \n" +
					"LEFT JOIN\n" +
					"(SELECT meter_number, \"max\"(\"last_communication\") as ldate,\n" +
					"(DATE_PART('day', now() - \"max\"(\"last_communication\")) * 24 + DATE_PART('hour', now() - \"max\"(\"last_communication\"))) as difhr \n" +
					"  FROM meter_data.modem_communication GROUP BY meter_number) c \n" +
					"	\n" +
					"on m.mtrno=c.meter_number where c.difhr>720  and m.town_code like'"+towncode+"'\n" +
					"GROUP BY town_code,mtrno,fdrcategory,ldate,subdivision,fdrname,accno ORDER BY town_code)mm ,meter_data.amilocation a \r\n" + 
					"where a.tp_towncode=mm.town_code and a.tp_towncode like '"+towncode+"' \r\n" + 
					"GROUP BY mm.subdivision,mm.mtrno,mm.accno,mm.fdrname,mm.fdrcategory,mm.ldate,mm.town_code,a.town_ipds ,a.zone,a.tp_zonecode,a.circle,a.tp_circlecode";
					list=postgresMdas.createNativeQuery(qry).getResultList();		
		}
//		System.out.println("ID:- "+id+" Query="+qry);
		return list;
	}

	@Override
	public List<?> getWorstATClossesFeeder(String Billmonth) {
		// TODO Auto-generated method stub
		
		List<?> list=null;
		String worstFeederqry = "Select fdr_name,atc_loss_percent from (\n" +
				"SELECT  fdr_name,round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
				"(Select fdr_name ,round((unit_billed/NULLIF(unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
				"round((amt_collected/NULLIF(amt_billed,0)),2) AS Actualcollection_efficiency	\n" +
				"from  meter_data.rpt_eamainfeeder_losses_02months where month_year = '"+Billmonth+"' )A)x   where atc_loss_percent is not null  ORDER BY  atc_loss_percent DESC limit 2";

			try {
				list=postgresMdas.createNativeQuery(worstFeederqry).getResultList();
			} catch (Exception e) {
				// TODO: handle exception
			}	
					
				
		return list;
	}

	@Override
	public List<?> getBestATClossesFeeder(String Billmonth) {
		List<?> list=null;
		String BestFeederqry = "Select fdr_name,atc_loss_percent from (\n" +
				"SELECT  fdr_name,round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
				"(Select fdr_name ,round((unit_billed/NULLIF(unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
				"round((amt_collected/NULLIF(amt_billed,0)),2) AS Actualcollection_efficiency	\n" +
				"from  meter_data.rpt_eamainfeeder_losses_02months where month_year = '"+Billmonth+"' )A)x   where atc_loss_percent is not null  ORDER BY  atc_loss_percent ASC limit 2\n" +
				"";

			try {
				list=postgresMdas.createNativeQuery(BestFeederqry).getResultList();
			} catch (Exception e) {
				// TODO: handle exception
			}	
					
		return list;
		
	}

	@Override
	public List<?> getWorstATClossesDT(String Billmonth) {
		List<?> list=null;
		String worstDTqry = "	Select dt_name,atc_loss_percent from (\n" +
				"SELECT  dt_name,round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
				"(Select dt_name ,round((total_unit_billed/NULLIF(total_unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
				"round((total_amount_collected/NULLIF(total_amount_billed,0)),2) AS Actualcollection_efficiency	\n" +
				"from  meter_data.rpt_eadt_losses_02months where month_year = '"+Billmonth+"' )A)x   where atc_loss_percent is not null  ORDER BY  atc_loss_percent DESC limit 10";

			try {
				list=postgresMdas.createNativeQuery(worstDTqry).getResultList();
			} catch (Exception e) {
				
			}	
					
				
		return list;
	}

	@Override
	public List<?> getBestATClossesDT(String Billmonth) {
		List<?> list=null;
		String BestDTqry ="	Select dt_name,atc_loss_percent from (\n" +
				"SELECT  dt_name,round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
				"(Select dt_name ,round((total_unit_billed/NULLIF(total_unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
				"round((total_amount_collected/NULLIF(total_amount_billed,0)),2) AS Actualcollection_efficiency	\n" +
				"from  meter_data.rpt_eadt_losses_02months where month_year = '"+Billmonth+"' )A)x   where atc_loss_percent is not null  ORDER BY  atc_loss_percent ASC limit 10";

			try {
				list=postgresMdas.createNativeQuery(BestDTqry).getResultList();
			} catch (Exception e) {
				// TODO: handle exception
			}	
					
				
		return list;	}
	
	
	
	
	
	
	
	
	
	

	@Override
	public List<?> getFeederLess15AtcData(String billmonth) {
		List<?> list=null;
		String BestDTqry ="Select count(*) from (\n" +
				"SELECT fdr_name, round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
				"(Select fdr_name,round((unit_billed/unit_supply),2) AS ActualBilling_efficiency,\n" +
				"round((amt_collected/amt_billed),2) AS Actualcollection_efficiency	\n" +
				"from  meter_data.rpt_eamainfeeder_losses_02months where month_year = '201911' )A)x where atc_loss_percent > 15";

			try {
				list=postgresMdas.createNativeQuery(BestDTqry).getResultList();
			} catch (Exception e) {
				// TODO: handle exception
			}	
			return list;
			}

	@Override
	public List<?> getFeederGreater15AtcData(String billmonth) {
		List<?> list=null;
		String BestDTqry ="Select count(*) from (\n" +
				"SELECT fdr_name, round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
				"(Select fdr_name,round((unit_billed/unit_supply),2) AS ActualBilling_efficiency,\n" +
				"round((amt_collected/amt_billed),2) AS Actualcollection_efficiency	\n" +
				"from  meter_data.rpt_eamainfeeder_losses_02months where month_year = '201911' )A)x where atc_loss_percent > 15";

			try {
				list=postgresMdas.createNativeQuery(BestDTqry).getResultList();
			} catch (Exception e) {
				// TODO: handle exception
			}	
			return list;
			}
	@Override
	public List<?> getDTLess15AtcData(String billmonth) {
		List<?> list=null;
		String BestDTqry ="Select count(*) from (\n" +
				"SELECT fdr_name, round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
				"(Select fdr_name,round((unit_billed/unit_supply),2) AS ActualBilling_efficiency,\n" +
				"round((amt_collected/amt_billed),2) AS Actualcollection_efficiency	\n" +
				"from  meter_data.rpt_eamainfeeder_losses_02months where month_year = '201911' )A)x where atc_loss_percent > 15";

			try {
				list=postgresMdas.createNativeQuery(BestDTqry).getResultList();
			} catch (Exception e) {
				// TODO: handle exception
			}	
			return list;	}
	@Override
	public List<?> getDTGreater15AtcData(String billmonth) {
		List<?> list=null;
		String BestDTqry ="Select count(*) from (\n" +
				"SELECT fdr_name, round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from\n" +
				"(Select fdr_name,round((unit_billed/unit_supply),2) AS ActualBilling_efficiency,\n" +
				"round((amt_collected/amt_billed),2) AS Actualcollection_efficiency	\n" +
				"from  meter_data.rpt_eamainfeeder_losses_02months where month_year = '201911' )A)x where atc_loss_percent > 15";

			try {
				list=postgresMdas.createNativeQuery(BestDTqry).getResultList();
			} catch (Exception e) {
				// TODO: handle exception
			}	
			return list;	}
	
	
	
	@Override
	public List<?> getmultipleMonthatclossFeederdata(String feederTpId, String fromMonth, String toMonth,String period) {
		List<?> list =null;
		String tableName = null ;
		if(period.equalsIgnoreCase("00")){
			tableName = "meter_data.rpt_eamainfeeder_losses_00months";}
		if(period.equalsIgnoreCase("02")){
			tableName = "meter_data.rpt_eamainfeeder_losses_02months";}
		if(period.equalsIgnoreCase("04")){
			tableName = "meter_data.rpt_eamainfeeder_losses_04months";}
		if(period.equalsIgnoreCase("06")){
			tableName = "meter_data.rpt_eamainfeeder_losses_06months";}
		if(period.equalsIgnoreCase("08")){
			tableName = "meter_data.rpt_eamainfeeder_losses_08months";}
		if(period.equalsIgnoreCase("10")){
			tableName = "meter_data.rpt_eamainfeeder_losses_10months";}
		if(period.equalsIgnoreCase("12")){
			tableName = "meter_data.rpt_eamainfeeder_losses_12months";}
		
		
		try {
			String sql="Select A.*,round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from (\n" +
					"Select month_year,fdr_name,tp_fdr_id ,round((unit_billed/unit_supply)*100,2) AS billing_efficiency, round((amt_collected/amt_billed)*100,2) AS collection_efficiency,"
					+ "round((unit_billed/NULLIF(unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
					"round((amt_collected/NULLIF(amt_billed,0)),2) AS Actualcollection_efficiency from "+tableName+" where  tp_fdr_id = '"+feederTpId +"' "
					+ "	and month_year BETWEEN '"+fromMonth +"'  and '"+toMonth+"')A order by month_year ";
			
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	} 
	
	
	
	@Override
	public List<?> getmultipleMonthatclossDtdata(String dtTpId, String fromMonth, String toMonth, String period) {
		List<?> list =null;
		String tableName = null ;
		if(period.equalsIgnoreCase("00")){
			tableName = "meter_data.rpt_eadt_losses_00months";}
		if(period.equalsIgnoreCase("02")){
			tableName = "meter_data.rpt_eadt_losses_02months";}
		if(period.equalsIgnoreCase("04")){
			tableName = "meter_data.rpt_eadt_losses_04months";}
		if(period.equalsIgnoreCase("06")){
			tableName = "meter_data.rpt_eadt_losses_06months";}
		if(period.equalsIgnoreCase("08")){
			tableName = "meter_data.rpt_eadt_losses_08months";}
		if(period.equalsIgnoreCase("10")){
			tableName = "meter_data.rpt_eadt_losses_10months";}
		if(period.equalsIgnoreCase("12")){
			tableName = "meter_data.rpt_eadt_losses_12months";}
		
			
		String sql="Select A.*,round((1-(A.ActualBilling_efficiency*A.Actualcollection_efficiency))*100,2) as  atc_loss_percent from (\n" +
				"Select month_year,dt_name,tpdt_id ,round((total_unit_billed/NULLIF(total_unit_supply,0))*100,2) AS billing_efficiency, "
				+ "round((total_amount_collected/NULLIF(total_amount_billed,0))*100,2) AS collection_efficiency,"
				+ "round((total_unit_billed/NULLIF(total_unit_supply,0)),2) AS ActualBilling_efficiency,\n" +
				"round((total_amount_collected/NULLIF(total_amount_billed,0)),2) AS Actualcollection_efficiency from "+tableName+" where  tpdt_id = '"+dtTpId +"' "
				+ "	and month_year BETWEEN '"+fromMonth +"'  and '"+toMonth +"')A order by month_year ";
		try {
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void unmappedmeterspdf(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD);
		    Font font2 = new Font(Font.FontFamily.HELVETICA  ,14, Font.BOLD);
		    PdfPTable pdf1 = new PdfPTable(1);
		    pdf1.setWidthPercentage(100); 
		    pdf1.getDefaultCell().setPadding(3);
		    pdf1.getDefaultCell().setBorderWidth(0);
		    pdf1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		    
		    PdfPTable pdf2 = new PdfPTable(1);
		    pdf2.setWidthPercentage(100);
		    pdf2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		    Chunk glue = new Chunk(new VerticalPositionMark());
		    PdfPCell cell1 = new PdfPCell();
		    Paragraph pstart = new Paragraph();
		    pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		    cell1.setBorder(Rectangle.NO_BORDER);
		    cell1.addElement(pstart);
		    
		    document.add(pdf2);
		    PdfPCell cell2 = new PdfPCell();
		    Paragraph p1 = new Paragraph();
		    p1.add(new Phrase("Unmapped Meters Details ",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		   
		    
		    String query="";
			List<Object[]> DtloadSummData=null;
			query="select A.meter_number,case when np.manufacturer_name is null then '' else np.manufacturer_name end ,A.first_comm,A.LAST_comm from (SELECT meter_number,min(last_communication) AS first_comm ,max(last_communication) as LAST_comm  FROM meter_data.modem_communication WHERE DATE(last_communication)>=CURRENT_DATE-30 and meter_number NOT IN (SELECT mtrno FROM meter_data.master_main) GROUP BY meter_number) A LEFT JOIN meter_data.name_plate np on np.meter_serial_number=a.meter_number";
			System.out.println("query--->"+query);
			DtloadSummData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable1 = new PdfPTable(4);
	           parameterTable1.setWidths(new int[]{2, 2, 2, 2});
	           parameterTable1.setWidthPercentage(100);
	           PdfPCell parameterCell1;
	           
	           parameterCell1 = new PdfPCell(new Phrase("Meter SL No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Manufacturer",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("First Communication",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
	           
	           parameterCell1 = new PdfPCell(new Phrase("Last Communication",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell1.setFixedHeight(25f);
	           parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable1.addCell(parameterCell1);
		    
	           
	           for (int i = 0; i < DtloadSummData.size(); i++) 
	            {
	        	   
						
						 
	            	Object[] obj=DtloadSummData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			
							 
	            			parameterCell1 = new PdfPCell(new Phrase(obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
	            			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            			 parameterCell1.setFixedHeight(30f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell1.setFixedHeight(30f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell1.setFixedHeight(30f);
							 parameterTable1.addCell(parameterCell1);
							 
							 parameterCell1 = new PdfPCell(new Phrase(obj[3]==null?null:obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell1.setFixedHeight(30f);
							 parameterTable1.addCell(parameterCell1);
							
							 
	            		}
	            	}
	            }
	           
	                      document.add(parameterTable1);

	           
	                document.add(new Phrase("\n"));
			        LineSeparator separator = new LineSeparator();
			        separator.setPercentage(98);
			        separator.setLineColor(BaseColor.WHITE);
			        Chunk linebreak = new Chunk(separator);
			        document.add(linebreak);
			         
		       
	           
	           
			        document.close();
			        response.setHeader("Content-disposition", "attachment; filename=Unmapped Meters Details.pdf");
			        response.setContentType("application/pdf");
			        ServletOutputStream outstream = response.getOutputStream();
			        baos.writeTo(outstream);
			        outstream.flush();
			        outstream.close();
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		}

	
		
	} 
	
	
	
	
	
	
	
	
	
	

