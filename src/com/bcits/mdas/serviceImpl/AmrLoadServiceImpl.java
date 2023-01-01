package com.bcits.mdas.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.mdas.entity.AmrLoadEntity;
import com.bcits.mdas.entity.MasterMainEntity;
import com.bcits.mdas.service.AmrLoadService;
import com.bcits.mdas.service.MasterMainService;
import com.bcits.serviceImpl.GenericServiceImpl;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Repository
public class AmrLoadServiceImpl extends GenericServiceImpl<AmrLoadEntity> implements AmrLoadService {
	@Autowired
	private MasterMainService masterMainService;

	@SuppressWarnings("unchecked")
	@Override
	public List<AmrLoadEntity> getLoadSurveyData(String mtrNo, String frmDate, String tDate, String radioValue) {
		String sql = "";
		List<AmrLoadEntity> list = null;
		try {
			// TO REDUCE 30 MINUTES IN UI
			if ("meterno".equals(radioValue)) {
				sql = "SELECT meter_number,read_time,v_r,v_y,v_b,i_r,i_y,i_b,kwh,kvarh_lag,kvarh_lead,kvah,frequency,average_voltage, average_current, neutral_current,power_factor FROM meter_data.load_survey WHERE meter_number='"
						+mtrNo+"' AND read_time >= to_date('" + frmDate
						+ "', 'YYYY-MM-DD') +  INTERVAL  '15 minutes' and read_time <= to_date('" + tDate
					+ "', 'YYYY-MM-DD') +interval '24 hours' ORDER BY read_time DESC;";
				
				
				 System.out.println("in load survey sql==>"+sql);

			} else {
				sql = "SELECT meter_number,read_time,v_r,v_y,v_b,i_r,i_y,i_b,kwh,kvarh_lag,kvarh_lead,kvah,frequency,average_voltage, average_current, neutral_current,power_factor FROM meter_data.load_survey WHERE meter_number in (SELECT distinct mtrno from meter_data.master_main where mtrno='"
						+mtrNo+"') AND read_time >= to_date('" + frmDate
						+ "', 'YYYY-MM-DD') +  INTERVAL  '15 minutes'" + " and read_time <= to_date('" + tDate
					+ "', 'YYYY-MM-DD') +interval '24 hours' ORDER BY read_time DESC";
				
				
				 System.out.println("in load survey sql==>"+sql);
				// sql="SELECT
				// meter_number,read_time,v_r,v_y,v_b,i_r,i_y,i_b,kwh,kvarh_lag,kvarh_lead,kvah,frequency
				// FROM meter_data.load_survey WHERE meter_number=(select mtrno from
				// meter_data.master_main where accno='"+mtrNo+"') AND read_time >=
				// to_date('"+frmDate+"', 'YYYY-MM-DD') + INTERVAL '30 minutes' and read_time <=
				// to_date('"+tDate+"', 'YYYY-MM-DD') +interval '24 hours' ORDER BY read_time;";
			}
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

	@Override
	public List<AmrLoadEntity> getLoadSurveyDataInfo(String mtrNo) {
		String sql = "";
		List<AmrLoadEntity> list = null;
		try {
			
			
			if("meterno"!=null || "meterno"==null ) {
				sql="SELECT distinct on(ls.meter_number)ls.meter_number,max(\r\n" + 
						"						                CASE \r\n" + 
						"						                    WHEN (c.dtcapacity = (100)) THEN 100 \r\n" + 
						"						                    WHEN (c.dtcapacity = (250)) THEN 250 \r\n" + 
						"						                    WHEN (c.dtcapacity = (500)) THEN 500 \r\n" + 
						"						                    WHEN (c.dtcapacity = (25)) THEN 25 \r\n" + 
						"						                    WHEN (c.dtcapacity = (40)) THEN 40 \r\n" + 
						"						                    WHEN (c.dtcapacity = (63)) THEN 63 \r\n" + 
						"						                    WHEN (c.dtcapacity = (315)) THEN 315 \r\n" + 
						"						                    WHEN (c.dtcapacity = (300)) THEN 300 \r\n" + 
						"						                    WHEN (c.dtcapacity = (200)) THEN 200 \r\n" + 
						"						                    WHEN (c.dtcapacity = (175)) THEN 175 \r\n" + 
						"						                    WHEN (c.dtcapacity = (160)) THEN 160 \r\n" + 
						"						                    WHEN (c.dtcapacity = (150)) THEN 150 \r\n" + 
						"						                    WHEN (c.dtcapacity = (75)) THEN 75 \r\n" + 
						"						                    WHEN (c.dtcapacity = (50)) THEN 50 \r\n" + 
						"						                    WHEN (c.dtcapacity = (16)) THEN 16 \r\n" + 
						"						                    WHEN (c.dtcapacity IS NULL) THEN 100 \r\n" + 
						"						                    ELSE NULL  \r\n" + 
						"						                END) AS dt_capacity,mm.fdrname,ami.section,c.dtname FROM meter_data.load_survey ls LEFT JOIN meter_data.dtdetails c ON(ls.meter_number=c.meterno) LEFT JOIN meter_data.master_main mm  ON(ls.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation ami ON(ami.tp_towncode=mm.town_code) WHERE meter_number='"+mtrNo+"'  GROUP BY ls.meter_number,mm.fdrname,ami.section,c.dtname;\r\n" + 
						" ";
			}
				
				 System.out.println("in load survey sql==>"+sql);
				
					/*
					 * else {
					 * 
					 * sql="SELECT distinct ls.meter_number,max( \r\n" +
					 * "						                CASE \r\n" +
					 * "						                    WHEN (c.dtcapacity = (100)) THEN 100 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (250)) THEN 250 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (500)) THEN 500 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (25)) THEN 25 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (40)) THEN 40 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (63)) THEN 63 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (315)) THEN 315 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (300)) THEN 300 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (200)) THEN 200 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (175)) THEN 175 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (160)) THEN 160 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (150)) THEN 150 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (75)) THEN 75 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (50)) THEN 50 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity = (16)) THEN 16 \r\n"
					 * +
					 * "						                    WHEN (c.dtcapacity IS NULL) THEN 100 \r\n"
					 * + "						                    ELSE NULL  \r\n" +
					 * "						                END) AS dt_capacity,mm.fdrname,ami.section FROM meter_data.load_survey ls LEFT JOIN meter_data.dtdetails c ON(ls.meter_number=c.meterno) LEFT JOIN meter_data.master_main mm  ON(ls.meter_number=mm.mtrno) LEFT JOIN meter_data.amilocation ami ON(ami.tp_towncode=mm.town_code) WHERE meter_number='"
					 * +mtrNo+"' AND ls.read_time >= to_date('"
					 * +frmDate+"', 'YYYY-MM-DD') +  INTERVAL  '15 minutes' and ls.read_time <= to_date('"
					 * +tDate+"', 'YYYY-MM-DD') +interval '24 hours' GROUP BY ls.meter_number,mm.fdrname,ami.section ORDER BY read_time DESC;\r\n"
					 * + " ";
					 * 
					 * System.out.println("in load survey sql==>"+sql);
					 * 
					 * 
					 * }
					 */
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	
		return list;
	}

	

	@Override
	public List<?> getDailyLoadSurveyData(String mtrNo, String frmDate, String tDate, String radioValue) {
		String sql = "";
		List<?> list = null;
		try {
			if ("meterno".equals(radioValue)) {

				/*
				 * sql="SELECT DISTINCT to_char(read_time,'yyyy-MM-dd HH:mm:ss') as modem_time,"
				 * +
				 * "max(v_r) as v_r,max(v_y) as v_y,max(v_b) as v_b,max(i_r) as i_r,max(i_y) as i_y,max(i_b) as i_b,sum(kwh) as kwh,"
				 * +
				 * "sum(kvarh_lag) as kvarh_lag,sum(kvarh_lead) as kvarh_lead,sum(kvah) as kvah "
				 * + "FROM meter_data.load_survey WHERE meter_number='"
				 * +mtrNo+"' and to_char(read_time,'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"
				 * +tDate+"' "+
				 * "GROUP BY to_char(read_time,'yyyy-MM-dd HH:mm:ss') ORDER BY modem_time";
				 */
				/*
				 * sql = "SELECT DISTINCT date(read_time) AS modem_time," +
				 * "max(v_r) as v_r,max(v_y) as v_y,max(v_b) as v_b,max(i_r) as i_r,max(i_y) as i_y,max(i_b) as i_b,sum(kwh) as kwh,"
				 * +
				 * "sum(kvarh_lag) as kvarh_lag,sum(kvarh_lead) as kvarh_lead,sum(kvah) as kvah, "
				 * +
				 * " sum(CAST(average_voltage as NUMERIC))/count(*) as col1, sum(CAST( average_current as NUMERIC))/count(*) col2, sum(CAST(neutral_current as NUMERIC))/count(*) as col3, "
				 * +
				 * " sum(kwh_exp) as kwhexp,sum(kvah_exp) as kvahexp,sum(kwh_imp) as cum_kwh,sum(kvah_imp) as cum_kvah "
				 * + "FROM meter_data.load_survey WHERE meter_number='"
				 * +mtrNo+"' and date(read_time) BETWEEN '" + frmDate + "' AND '" + tDate + "' "
				 * + "GROUP BY date(read_time) ORDER BY modem_time"; } else { sql =
				 * "SELECT DISTINCT date(read_time) AS modem_time," +
				 * "max(v_r) as v_r,max(v_y) as v_y,max(v_b) as v_b,max(i_r) as i_r,max(i_y) as i_y,max(i_b) as i_b,sum(kwh) as kwh,sum(kvarh_lag) as kvarh_lag,sum(kvarh_lead) as kvarh_lead,"
				 * +
				 * "sum(kvah) as kvah,  sum(CAST(average_voltage as NUMERIC))/count(*) as col1, "
				 * +
				 * "sum(CAST( average_current as NUMERIC))/count(*) col2, sum(CAST(neutral_current as NUMERIC))/count(*) as col3,  "
				 * + "sum(kwh_exp) as kwhexp,sum(kvah_exp) as kvahexp,sum(kwh_imp) as cum_kwh,"
				 * +
				 * "sum(kvah_imp) as cum_kvah FROM meter_data.load_survey WHERE meter_number in "
				 * + "(SELECT distinct mtrno from meter_data.master_main where kno='"
				 * +mtrNo+"') and date(read_time) BETWEEN '" + frmDate + "' AND '" + tDate +
				 * "' GROUP BY date(read_time) ORDER BY modem_time";
				 */
				
				
				/*
				 * sql=" SELECT * FROM  meter_data.daily_load WHERE mtrno='"
				 * +mtrNo+"' and date(rtc_date_time) BETWEEN '"+frmDate+"' AND '"+tDate+"' \n" +
				 * " ORDER BY rtc_date_time desc";
				 */
				 sql="SELECT distinct dl.*,mm.fdrname,ami.section,max( \r\n" + 
				 		"						                CASE \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (100)) THEN 100 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (250)) THEN 250 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (500)) THEN 500 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (25)) THEN 25 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (40)) THEN 40 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (63)) THEN 63 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (315)) THEN 315 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (300)) THEN 300 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (200)) THEN 200 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (175)) THEN 175 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (160)) THEN 160 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (150)) THEN 150 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (75)) THEN 75 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (50)) THEN 50 \r\n" + 
				 		"						                    WHEN (c.dtcapacity = (16)) THEN 16 \r\n" + 
				 		"						                    WHEN (c.dtcapacity IS NULL) THEN 100 \r\n" + 
				 		"						                    ELSE NULL  \r\n" + 
				 		"						                END) AS dt_capacity   FROM  meter_data.daily_load dl LEFT JOIN meter_data.dtdetails c ON(dl.mtrno=c.meterno) LEFT JOIN meter_data.master_main mm  ON(dl.mtrno=mm.mtrno) LEFT JOIN meter_data.amilocation ami ON(ami.tp_towncode=mm.town_code) WHERE dl.mtrno='"+mtrNo+"' and date(dl.rtc_date_time) BETWEEN '"+frmDate+"' AND '"+tDate+"' GROUP BY  dl.id,mm.fdrname,ami.section\r\n" + 
				 		"						 ORDER BY dl.rtc_date_time desc ";
				 
				 
				}else{
					sql=" SELECT * FROM  meter_data.daily_load WHERE mtrno in  (SELECT distinct mtrno from meter_data.master_main where kno='"+mtrNo+"') and and date(rtc_date_time) BETWEEN '"+frmDate+"' AND '"+tDate+"' \n" +
							" ORDER BY rtc_date_time desc";

			}

			 System.out.println("in load daily survey sql==>"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			/*
			 * } else{
			 * sql="SELECT DISTINCT to_char(modem_time,'yyyy-MM-dd HH:mm:ss') as modem_time,"
			 * +
			 * "max(v_r) as v_r,max(v_y) as v_y,max(v_b) as v_b,max(i_r) as i_r,max(i_y) as i_y,max(i_b) as i_b,sum(kwh) as kwh,"
			 * +
			 * "sum(kvarh_lag) as kvarh_lag,sum(kvarh_lead) as kvarh_lead,sum(kvah) as kvah "
			 * +
			 * "FROM meter_data.load_survey WHERE meter_number=(select mtrno from meter_data.master_main where accno='"
			 * +mtrNo+"') and to_char(read_time,'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"
			 * +tDate+"' "+
			 * "GROUP BY to_char(modem_time,'yyyy-MM-dd HH:mm:ss') ORDER BY modem_time";
			 * list =
			 * getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList()
			 * ; }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	

	@Override
	public List<?> getDailyLoadSurveyDataInfo(String mtrNo) {
		String sql = "";
		List<?> list = null;
		try {
			if ( "meterno"!=null || "meterno"==null) {

				sql="SELECT distinct on(dd.mtrno)dd.mtrno,mm.fdrname,ami.section,max(CASE WHEN dt.DTCAPACITY=100 THEN 133 \r\n" + 
						"		WHEN dt.DTCAPACITY=250 THEN 333\r\n" + 
						"		WHEN dt.DTCAPACITY=500 THEN 666\r\n" + 
						"		WHEN dt.DTCAPACITY=25 THEN 33\r\n" + 
						"		WHEN dt.DTCAPACITY=40 THEN 53\r\n" + 
						"		WHEN dt.DTCAPACITY=63 THEN 83\r\n" + 
						"		WHEN dt.DTCAPACITY=315 THEN 420\r\n" + 
						"		WHEN dt.DTCAPACITY=300 THEN 400\r\n" + 
						"		WHEN dt.DTCAPACITY=200 THEN 266\r\n" + 
						"		WHEN dt.DTCAPACITY=175 THEN 233\r\n" + 
						"		WHEN dt.DTCAPACITY=160 THEN 213\r\n" + 
						"		WHEN dt.DTCAPACITY=150 THEN 200\r\n" + 
						"		WHEN dt.DTCAPACITY=75 THEN 100\r\n" + 
						"		WHEN dt.DTCAPACITY=50 THEN 66\r\n" + 
						"		WHEN dt.DTCAPACITY=16 THEN 21\r\n" + 
						"		WHEN dt.DTCAPACITY is null THEN 133 \r\n" + 
						"\r\n" + 
						"END) DT_CURR_RATING,dt.dtname  FROM  meter_data.daily_load dd LEFT JOIN meter_data.dtdetails dt ON(dd.mtrno=dt.meterno) LEFT JOIN meter_data.master_main mm ON (mm.mtrno=dd.mtrno) LEFT JOIN meter_data.amilocation ami ON(ami.tp_towncode=mm.town_code)  WHERE dd.mtrno='"+mtrNo+"'  GROUP BY dd.mtrno,mm.fdrname,ami.section,dt.dtname";
				}

			 System.out.println("in load daily survey sql==>"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			/*
			 * } else{
			 * sql="SELECT DISTINCT to_char(modem_time,'yyyy-MM-dd HH:mm:ss') as modem_time,"
			 * +
			 * "max(v_r) as v_r,max(v_y) as v_y,max(v_b) as v_b,max(i_r) as i_r,max(i_y) as i_y,max(i_b) as i_b,sum(kwh) as kwh,"
			 * +
			 * "sum(kvarh_lag) as kvarh_lag,sum(kvarh_lead) as kvarh_lead,sum(kvah) as kvah "
			 * +
			 * "FROM meter_data.load_survey WHERE meter_number=(select mtrno from meter_data.master_main where accno='"
			 * +mtrNo+"') and to_char(read_time,'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"
			 * +tDate+"' "+
			 * "GROUP BY to_char(modem_time,'yyyy-MM-dd HH:mm:ss') ORDER BY modem_time";
			 * list =
			 * getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList()
			 * ; }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@Override
	public List<AmrLoadEntity> getRecords(String meterno, String fileDate) throws ParseException {
		List<AmrLoadEntity> abb = null;
		SimpleDateFormat inPut = new SimpleDateFormat("yyyy-MM-dd");
		Date newInDate = inPut.parse(fileDate);

		Date dateBefore = new Date(newInDate.getTime() - 4 * 24 * 3600 * 1000l);

		Date onePluseDate = new Date(newInDate.getTime() + 1 * 24 * 3600 * 1000l);
		// to convert the date to a formate yyyy-mm-dd
		SimpleDateFormat formatOutput = new SimpleDateFormat("yyyy-MM-dd");

		String beforeDate = formatOutput.format(dateBefore);

		String onePluseDateString = formatOutput.format(onePluseDate);

		try {

			/*
			 * abb= getCustomEntityManager("postgresMdas").createNamedQuery(
			 * "AmrLoadEntity.getRecords",AmrLoadEntity.class).setParameter("meterno",
			 * meterno).setParameter("fileDate",fileDate).getResultList();
			 */
			// abb=
			// getCustomEntityManager("postgresMdas").createNamedQuery("AmrLoadEntity.getRecords",AmrLoadEntity.class).setParameter("meterno",meterno).setParameter("fileDate",fileDate).setParameter("beforeDate",
			// beforeDate).getResultList();
			abb = getCustomEntityManager("postgresMdas")
					.createNamedQuery("AmrLoadEntity.getRecords", AmrLoadEntity.class).setParameter("meterno", meterno)
					.setParameter("onePluseDateString", onePluseDateString).setParameter("beforeDate", beforeDate)
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return abb;

	}

	@Override
	public AmrLoadEntity findEntityById(String colid) {
		return getCustomEntityManager("postgresMdas")
				.createNamedQuery("AmrLoadEntity.findEntityById", AmrLoadEntity.class)
				.setParameter("id", Long.parseLong(colid)).getSingleResult();
	}

	@Override
	public List<?> getNamePlateDetailsBymeterNo(String mtrNo) {
		List<?> list = null;
		try {

			String sql = "SELECT meter_serial_number,manufacturer_name,firmware_version,meter_type,year_of_manufacture,hardware_version,created_time,updated_time,ct_ratio,pt_ratio FROM meter_data.name_plate WHERE meter_serial_number='"
					+ mtrNo + "'";
			// list=getCustomEntityManager("postgresMdas").createNamedQuery("NamePlate.getPlateDetails",NamePlate.class).setParameter("mtrNo",
			// mtrNo).getResultList();
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getNamePlateDetailsByKno(String mtrNo) {
		List<?> list = null;
		try {
			String sql = "SELECT meter_serial_number,manufacturer_name,firmware_version,meter_type,year_of_manufacture,hardware_version,created_time,updated_time,ct_ratio,pt_ratio FROM meter_data.name_plate WHERE "
					+ "meter_serial_number IN (SELECT distinct mtrno from meter_data.master_main where kno='" + mtrNo
					+ "')";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * @Override public List<?> getDailyMinMaxByMtrNo(String mtrNo,String
	 * frmDate,String tDate) { List<?> list=null; String
	 * sql="SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,min(i_r) as ir,min(i_y) as iy,min(i_b) as ib,min(kvarh_lag) as kvarh_lags,\n"
	 * +
	 * "min(kvarh_lead) as kvarh_leads,min(kvah) as kvah,min(block_energy_kwh_exp) as block_kwh\n"
	 * + " FROM meter_data.load_survey WHERE meter_number='3152532'\n" +
	 * "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '2019-01-18' and '2019-03-01' GROUP BY meter_number,time"
	 * ; String
	 * sql="SELECT meter_number,to_char(read_time, 'yyyy-MM-dd') as time, max(kwh) as maxcurrent, min(kwh) as min_current,min(kva) as min_voltage,max(kva) as max_voltage, AVG(kva) as avg_voltage,\n"
	 * + " AVG(kwh) as avg_current from meter_data.load_survey WHERE meter_number='"
	 * +mtrNo+"'\n" +
	 * " AND to_char(read_time, 'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"
	 * +tDate+"' GROUP BY meter_number,time";
	 * //"SELECT meter_number, max(kwh) as maxcurrent, min(kwh) as min_current,min(kvah) as min_voltage,max(kvah) as max_voltage, AVG(kvah) as avg_voltage,\n"
	 * +
	 * //" AVG(kwh) as avg_current from meter_data.load_survey WHERE meter_number = '"
	 * +mtrNo+"'\n" +
	 * //" AND to_char(read_time, 'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"
	 * +tDate+"' GROUP BY meter_number"; try{
	 * list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).
	 * getResultList();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return list;
	 * 
	 * }
	 * 
	 * @Override public List<?> getDailyMinMaxByKnoNo(String kNo, String frmDate,
	 * String tDate) { List<?> list=null; String
	 * sql="SELECT meter_number,to_char(read_time, 'yyyy-MM-dd') as time, max(kwh) as maxcurrent, min(kwh) as min_current,min(kva) as min_voltage,max(kva) as max_voltage, AVG(kva) as avg_voltage,\n"
	 * +
	 * " AVG(kwh) as avg_current from meter_data.load_survey WHERE meter_number in (SELECT distinct metrno from meter_data.metermaster where kno='"
	 * +kNo+"')\n" +
	 * " AND to_char(read_time, 'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"
	 * +tDate+"' GROUP BY meter_number,time";
	 * 
	 * //"SELECT meter_number, max(kwh) as maxcurrent, min(kwh) as min_current,min(kvah) as min_voltage,max(kvah) as max_voltage, AVG(kvah) as avg_voltage,\n"
	 * +
	 * //" AVG(kwh) as avg_current from meter_data.load_survey WHERE meter_number in (SELECT distinct metrno from meter_data.metermaster where kno='"
	 * +kNo+"')\n" +
	 * //" AND to_char(read_time, 'yyyy-MM-dd') BETWEEN '"+frmDate+"' AND '"
	 * +tDate+"' GROUP BY meter_number"; try{
	 * list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).
	 * getResultList();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return list; }
	 */
	@Override
	public List<?> getTransactionData(String mtrno, String frmDate, String tDate) {
		List<?> l = null;
		String sql = "select B.event_time,A.event_code,A.event from \n"
				+ "(select event,event_code from meter_data.event_master)A,\n"
				+ "(SELECT CAST(event_code as int),event_time from meter_data.events where event_code in ('251','151','152','153','154','155')\n"
				+ "  and meter_number='" + mtrno + "' AND to_char(event_time,'yyyy-MM-dd') BETWEEN '" + frmDate
				+ "' and '" + tDate + "')B\n" + "WHERE A.event_code=B.event_code ORDER BY event_time";
		try {
			l = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;

	}

	@Override
	public List<List<?>> getDailyMinMaxByMtrNo(String metrNo, String fromDate, String toDate) {
		List<List<?>> dailyParameters = new ArrayList<>();
		List<?> minParameters = new ArrayList<>();
		List<?> avgParameters = new ArrayList<>();
		List<?> maxParameters = new ArrayList<>();

		// min query
		String sql = "SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,\n"
				+ "min(i_r) as ir,min(i_y) as iy,min(i_b) as ib, \n" + "min(v_r) as vr,min(v_y) as vy,min(v_b) as vb,\n"
				+ "min(kvarh_lag) as kvarh_lags,min(kvarh_lead) as kvarh_leads,min(kvah) as kvah,min(kwh) as block_kwh,(min(i_r)+min(i_y)+min(i_b)) as TotalIr,"
				+ " round((min(v_r)+min(v_y)+min(v_b))/3,3) as AvgVr FROM "
				+ "meter_data.load_survey WHERE meter_number='" + metrNo
				+ "'AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fromDate + "' and '" + toDate + "' "
				+ " and i_r>0 and i_y>0 and i_b>0 and v_r>0 and v_y>0 and v_b>0 and kvah>0 and kwh>0  GROUP BY meter_number,time";
		// avg query
		String sql1 = "SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time, round(AVG(i_r),3) as ir,round(AVG(i_y),3) as iy,round(AVG(i_b),3) as ib,\r\n"
				+ "	round(AVG(v_r),3) as vr,round(AVG(v_y),3) as vy,round(AVG(v_b),3) as vb,\r\n"
				+ "	round(AVG(kvarh_lag),3) as kvarh_lags,round(AVG(kvarh_lead),3) as kvarh_leads,\r\n"
				+ "	round(AVG(kvah),3) as kvah,round(AVG(kwh),3) as block_kwh,\r\n"
				+ "	(round(AVG(i_r),3)+round(AVG(i_y),3)+round(AVG(i_b),3)) as TotalIr,\r\n"
				+ "	round((round(AVG(v_r),3) +round(AVG(v_y),3) +round(AVG(v_b),3))/3,3) as avgVr\r\n"
				+ "	FROM meter_data.load_survey WHERE meter_number='" + metrNo
				+ "'AND  to_char(read_time,'yyyy-MM-dd') \r\n" + "	BETWEEN '" + fromDate + "' and '" + toDate
				+ "'   GROUP BY meter_number,time";
		// max query
		String sql2 = "SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,max(i_r) as ir,max(i_y) as iy,max(i_b) as ib,\n"
				+ "max(v_r) as vr,max(v_y) as vy,max(v_b) as vb,max(kvarh_lag) as kvarh_lags,\n"
				+ "max(kvarh_lead) as kvarh_leads,max(kvah) as kvah,max(kwh) as block_kwh,(max(i_r)+max(i_y)+max(i_b)) as TotalIr,round((max(v_r)+max(v_y)+max(v_b))/3,3) as AvgVr\n"
				+ " FROM meter_data.load_survey WHERE meter_number='" + metrNo + "'\n"
				+ "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fromDate + "' and '" + toDate
				+ "' GROUP BY meter_number,time";
		try {
			// System.out.println("sql avg--1:"+sql1);
			// System.out.println("sql min--1:"+sql);
			// System.out.println("sql max--1:"+sql2);
			minParameters = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			avgParameters = getCustomEntityManager("postgresMdas").createNativeQuery(sql1).getResultList();
			maxParameters = getCustomEntityManager("postgresMdas").createNativeQuery(sql2).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		dailyParameters.add(minParameters);
		dailyParameters.add(maxParameters);
		dailyParameters.add(avgParameters);
		return dailyParameters;
	}

	@Override
	public List<List<?>> getdailyParametersByKno(String kNo, String fromDate, String toDate) {
		List<List<?>> dailyParameters = new ArrayList<>();
		List<?> minParameters = new ArrayList<>();
		List<?> avgParameters = new ArrayList<>();
		List<?> maxParameters = new ArrayList<>();
		// min query
		String sql = "SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,min(i_r) as ir,min(i_y) as iy,min(i_b) as ib,\n"
				+ "min(v_r) as vr,min(v_y) as vy,min(v_b) as vb,min(kvarh_lag) as kvarh_lags,\n"
				+ "min(kvarh_lead) as kvarh_leads,min(kvah) as kvah,min(kwh) as block_kwh\n"
				+ " FROM meter_data.load_survey WHERE meter_number in (SELECT distinct mtrno from meter_data.master_main where kno='"
				+ kNo + "')\n" + "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fromDate + "' and '" + toDate
				+ "'  GROUP BY meter_number,time;";
		// avg query
		String sql1 = "SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time, round(AVG(i_r),3) as ir,round(AVG(i_y),3) as iy,round(AVG(i_b),3) as ib,\n"
				+ "round(AVG(v_r),3) as vr,round(AVG(v_y),3) as vy,round(AVG(v_b),3) as vb,round(AVG(kvarh_lag),3) as kvarh_lags,round(AVG(kvarh_lead),3) as kvarh_leads,round(AVG(kvah),3) as kvah,\n"
				+ "round(AVG(kwh),3) as block_kwh\n"
				+ " FROM meter_data.load_survey WHERE meter_number in (SELECT distinct mtrno from meter_data.master_main where kno='"
				+ kNo + "')\n" + "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fromDate + "' and '" + toDate
				+ "' GROUP BY meter_number,time";

		String sql2 = "SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,max(i_r) as ir,max(i_y) as iy,max(i_b) as ib,\n"
				+ "max(v_r) as vr,max(v_y) as vy,max(v_b) as vb,max(kvarh_lag) as kvarh_lags,\n"
				+ "max(kvarh_lead) as kvarh_leads,max(kvah) as kvah,max(kwh) as block_kwh\n"
				+ " FROM meter_data.load_survey WHERE meter_number IN (SELECT distinct mtrno from meter_data.master_main where kno='"
				+ kNo + "')\n" + "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fromDate + "' and '" + toDate
				+ "' GROUP BY meter_number,time;";
		try {

			minParameters = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			avgParameters = getCustomEntityManager("postgresMdas").createNativeQuery(sql1).getResultList();
			maxParameters = getCustomEntityManager("postgresMdas").createNativeQuery(sql2).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		dailyParameters.add(minParameters);
		dailyParameters.add(maxParameters);
		dailyParameters.add(avgParameters);
		return dailyParameters;
	}

	@Override
	public List<List<?>> getDailyMinMaxByMtrNoMf(String meterNum, String frmDate, String tDate) {
		List<List<?>> dailyParameters = new ArrayList<>();
		List<?> minParameters = new ArrayList<>();
		List<?> avgParameters = new ArrayList<>();
		List<?> maxParameters = new ArrayList<>();

		List<MasterMainEntity> masterdata = null;
		try {
			masterdata = masterMainService.getFeederData(meterNum);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String table = "";
		String fdrcategory = masterdata.get(0).getFdrcategory();
		
		if (fdrcategory != null) {
			if ("DT".equalsIgnoreCase(fdrcategory)) {
				table = "dtdetails";
			} else {
				table = "feederdetails";
			}
		}

		String minSql = "SELECT a.meter_number,a.time,a.ir*b.current_mf as ir,a.iy*b.current_mf as iy,a.ib*b.current_mf as ib,a.vr*b.volt_mf as vr,a.vy*b.volt_mf as vy,a.vb*b.volt_mf as vb,a.kvarh_lags*b.mf as kvarh_lags,a.kvarh_leads*b.mf as kvarh_lead,a.kvah*b.mf as kvah,a.block_kwh*b.mf as block_kwh,\n"
				+ "(a.ir*b.current_mf+a.iy*b.current_mf+a.ib*b.current_mf) as TotalIr,\n"
				+ "round((a.vr*b.volt_mf +a.vy*b.volt_mf+a.vb*b.volt_mf)/3,3) as AvgVr\n"
				+ "FROM(SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,round(min(i_r),3) as ir,round(min(i_y),3) as iy,round(min(i_b),3) as ib,round(min(v_r),3) as vr,round(min(v_y),3) as vy,round(min(v_b),3) as vb,round(min(kvarh_lag),3) as kvarh_lags,round(min(kvarh_lead),3) as kvarh_leads,round(min(kvah),3) as kvah,round(min(kwh),3) as block_kwh FROM meter_data.load_survey WHERE meter_number='"
				+ meterNum + "'AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + frmDate + "' and '" + tDate
				+ "'  and i_r>0 and i_y>0 and i_b>0 and v_r>0 and v_y>0 and v_b>0  and kvah>0 and kwh>0 GROUP BY meter_number,time)a,(SELECT DISTINCT cast(mf as NUMERIC),cast(volt_mf as NUMERIC),cast(current_mf as NUMERIC),meterno FROM meter_data."
				+ table + "  WHERE meterno='" + meterNum + "')b WHERE a.meter_number=b.meterno";

		String maxSql = "SELECT a.meter_number,a.time,a.ir*b.current_mf as ir,a.iy*b.current_mf as iy,a.ib*b.current_mf as ib,a.vr*b.volt_mf as vr,a.vy*b.volt_mf as vy,a.vb*b.volt_mf as vb,a.kvarh_lags*b.mf as kvarh_lags,a.kvarh_leads*b.mf as kvarh_lead,a.kvah*b.mf as kvah,a.block_kwh*b.mf as block_kwh,\n"
				+ "(a.ir*b.current_mf+a.iy*b.current_mf+a.ib*b.current_mf) as TotalIr,\n"
				+ "round((a.vr*b.volt_mf +a.vy*b.volt_mf+a.vb*b.volt_mf)/3,3) as AvgVr\n"
				+ "FROM(SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,round(max(i_r),3) as ir,round(max(i_y),3) as iy,round(max(i_b),3) as ib,round(max(v_r),3) as vr,round(max(v_y),3) as vy,round(max(v_b),3) as vb,round(max(kvarh_lag),3) as kvarh_lags,round(max(kvarh_lead),3) as kvarh_leads,round(max(kvah),3) as kvah,max(kwh) as block_kwh FROM meter_data.load_survey WHERE meter_number='"
				+ meterNum + "'AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + frmDate + "' and '" + tDate
				+ "'  and i_r>0 and i_y>0 and i_b>0 and v_r>0 and v_y>0 and v_b>0  and kvah>0 and kwh>0 GROUP BY meter_number,time)a,(SELECT DISTINCT cast(mf as NUMERIC),cast(volt_mf as NUMERIC),cast(current_mf as NUMERIC),meterno FROM meter_data."
				+ table + "  WHERE meterno='" + meterNum + "')b WHERE a.meter_number=b.meterno";

		String avgSql = "SELECT a.meter_number,a.time,a.ir*b.current_mf as ir,a.iy*b.current_mf as iy,a.ib*b.current_mf as ib,a.vr*b.volt_mf as vr,a.vy*b.volt_mf as vy,a.vb*b.volt_mf as vb,a.kvarh_lags*b.mf as kvarh_lags,a.kvarh_leads*b.mf as kvarh_lead,a.kvah*b.mf as kvah,a.block_kwh*b.mf as block_kwh,\n"
				+ "(a.ir*b.current_mf+a.iy*b.current_mf+a.ib*b.current_mf) as TotalIr,\n"
				+ "round((a.vr*b.volt_mf +a.vy*b.volt_mf+a.vb*b.volt_mf)/3,3) as AvgVr\n"
				+ "FROM(SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,round(AVG(i_r),3) as ir,round(AVG(i_y),3) as iy,round(AVG(i_b),3) as ib,round(AVG(v_r),3) as vr,round(AVG(v_y),3) as vy,round(AVG(v_b),3) as vb,round(AVG(kvarh_lag),3) as kvarh_lags,round(AVG(kvarh_lead),3) as kvarh_leads,round(AVG(kvah),3) as kvah,round(AVG(kwh),3) as block_kwh FROM meter_data.load_survey WHERE meter_number='"
				+ meterNum + "'AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + frmDate + "' and '" + tDate
				+ "'  and i_r>0 and i_y>0 and i_b>0 and v_r>0 and v_y>0 and v_b>0  and kvah>0 and kwh>0 GROUP BY meter_number,time)a,(SELECT DISTINCT cast(mf as NUMERIC),cast(volt_mf as NUMERIC),cast(current_mf as NUMERIC),meterno FROM meter_data."
				+ table + "  WHERE meterno='" + meterNum + "')b WHERE a.meter_number=b.meterno";
		try {

			minParameters = getCustomEntityManager("postgresMdas").createNativeQuery(minSql).getResultList();
			avgParameters = getCustomEntityManager("postgresMdas").createNativeQuery(avgSql).getResultList();
			maxParameters = getCustomEntityManager("postgresMdas").createNativeQuery(maxSql).getResultList();

//System.out.println(minSql);
//System.out.println(maxSql);

		} catch (Exception e) {
			e.printStackTrace();
		}

		dailyParameters.add(minParameters);
		dailyParameters.add(maxParameters);
		dailyParameters.add(avgParameters);
		return dailyParameters;
	}

	@Override
	public List<List<?>> getdailyParametersByKnoandMf(String meterNum, String frmDate, String tDate) {
		List<List<?>> dailyParameters = new ArrayList<>();
		List<?> minParameters = new ArrayList<>();
		List<?> avgParameters = new ArrayList<>();
		List<?> maxParameters = new ArrayList<>();
		String maxSql = "SELECT a.meter_number,a.time,a.ir*b.mf as ir,a.iy*b.mf as iy,a.ib*b.mf as ib,a.vr*b.mf as vr,a.vy*b.mf as vy,a.vb*b.mf as vb,a.kvarh_lags*b.mf as kvarh_lags,\n"
				+ "a.kvarh_leads*b.mf as kvarh_lead,a.kvah*b.mf as kvah,a.block_kwh*b.mf as block_kwh \n" + "FROM\n"
				+ "(SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,max(i_r) as ir,max(i_y) as iy,max(i_b) as ib,\n"
				+ "max(v_r) as vr,max(v_y) as vy,max(v_b) as vb,max(kvarh_lag) as kvarh_lags,\n"
				+ "max(kvarh_lead) as kvarh_leads,max(kvah) as kvah,max(kwh) as block_kwh\n"
				+ " FROM meter_data.load_survey WHERE meter_number in (select DISTINCT mtrno from meter_data.master_main where kno='"
				+ meterNum + "'\n" + ")\n" + "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + frmDate + "' and '"
				+ tDate + "' GROUP BY meter_number,time)a,\n"
				+ "(SELECT Distinct cast(mf as NUMERIC),mtrno FROM meter_data.master_main WHERE mtrno in (select DISTINCT mtrno from meter_data.master_main where kno='"
				+ meterNum + "'\n" + "))b\n" + "WHERE a.meter_number=b.mtrno";
		String minSql = "SELECT a.meter_number,a.time,a.ir*b.mf as ir,a.iy*b.mf as iy,a.ib*b.mf as ib,a.vr*b.mf as vr,a.vy*b.mf as vy,a.vb*b.mf as vb,a.kvarh_lags*b.mf as kvarh_lags,\n"
				+ "a.kvarh_leads*b.mf as kvarh_lead,a.kvah*b.mf as kvah,a.block_kwh*b.mf as block_kwh \n" + "FROM\n"
				+ "(SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,min(i_r) as ir,min(i_y) as iy,min(i_b) as ib,\n"
				+ "min(v_r) as vr,min(v_y) as vy,min(v_b) as vb,min(kvarh_lag) as kvarh_lags,\n"
				+ "min(kvarh_lead) as kvarh_leads,min(kvah) as kvah,min(kwh) as block_kwh\n"
				+ " FROM meter_data.load_survey WHERE meter_number in (select DISTINCT mtrno from meter_data.master_main where kno='"
				+ meterNum + "')\n" + "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + frmDate + "' and '" + tDate
				+ "' GROUP BY meter_number,time)a,\n"
				+ "(SELECT DISTINCT cast(mf as NUMERIC),mtrno FROM meter_data.master_main WHERE mtrno in (select DISTINCT mtrno from meter_data.master_main where kno='"
				+ meterNum + "'))b\n" + "WHERE a.meter_number=b.mtrno";
		String avgSql = "SELECT a.meter_number,a.time,a.ir*b.mf as ir,a.iy*b.mf as iy,a.ib*b.mf as ib,a.vr*b.mf as vr,a.vy*b.mf as vy,a.vb*b.mf as vb,a.kvarh_lags*b.mf as kvarh_lags,\n"
				+ "a.kvarh_leads*b.mf as kvarh_lead,a.kvah*b.mf as kvah,a.block_kwh*b.mf as block_kwh \n" + "FROM\n"
				+ "(SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,round(AVG(i_r),3) as ir,round(AVG(i_y),3) as iy,round(AVG(i_b),3) as ib,\n"
				+ "round(AVG(v_r),3) as vr,round(AVG(v_y),3) as vy,round(AVG(v_b),3) as vb,round(AVG(kvarh_lag),3) as kvarh_lags,\n"
				+ "round(AVG(kvarh_lead),3) as kvarh_leads,round(AVG(kvah),3) as kvah,round(AVG(kwh),3) as block_kwh\n"
				+ " FROM meter_data.load_survey WHERE meter_number in (select DISTINCT mtrno from meter_data.master_main where kno='"
				+ meterNum + "')\n" + "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + frmDate + "' and '" + tDate
				+ "' GROUP BY meter_number,time)a,\n"
				+ "(SELECT DISTINCT cast(mf as NUMERIC),mtrno FROM meter_data.master_main WHERE mtrno  in (select DISTINCT mtrno from meter_data.master_main where kno='"
				+ meterNum + "'))b\n" + "WHERE a.meter_number=b.mtrno;";
		try {

			minParameters = getCustomEntityManager("postgresMdas").createNativeQuery(minSql).getResultList();
			avgParameters = getCustomEntityManager("postgresMdas").createNativeQuery(avgSql).getResultList();
			maxParameters = getCustomEntityManager("postgresMdas").createNativeQuery(maxSql).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		dailyParameters.add(minParameters);
		dailyParameters.add(maxParameters);
		dailyParameters.add(avgParameters);
		return dailyParameters;
	}

	/*
	 * @Override public List<?> getMaximumParameters(String metrNo, String fromDate,
	 * String toDate) { List<?> maxParameters=null; String
	 * sql="SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,max(i_r) as ir,max(i_y) as iy,max(i_b) as ib,\n"
	 * +
	 * "max(v_r) as vr,max(v_y) as vy,max(v_b) as vb,max(kvarh_lag) as kvarh_lags,\n"
	 * +
	 * "max(kvarh_lead) as kvarh_leads,max(kvah) as kvah,max(block_energy_kwh_exp) as block_kwh\n"
	 * + " FROM meter_data.load_survey WHERE meter_number='"+metrNo+"'\n" +
	 * "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '"+fromDate+"' and '"
	 * +toDate+"' GROUP BY meter_number,time"; try{
	 * maxParameters=getCustomEntityManager("postgresMdas").createNativeQuery(sql).
	 * getResultList();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return maxParameters;
	 * 
	 * }
	 */

	@Override
	public AmrLoadEntity findEntityByUniId(String mtrno, Timestamp readTime) {
		try {
			return getCustomEntityManager("postgresMdas")
					.createNamedQuery("AmrLoadEntity.findEntityByUniId", AmrLoadEntity.class)
					.setParameter("meterNumber", mtrno).setParameter("readTime", readTime).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer findEntityByLoadId(String mtrno, Timestamp readTime) {
		Integer idd = 0;
		try {
			@SuppressWarnings("unchecked")
			Integer l = (Integer) postgresMdas.createNamedQuery("AmrLoadEntity.findEntityByLId")
					.setParameter("meterNumber", mtrno).setParameter("readTime", readTime).getSingleResult();
			ObjectMapper mapper = new ObjectMapper();
             
			/*
			 * System.out.println(mapper.writeValueAsString(l)); System.out.println(l);
			 */
			
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<?> getmMtrnoByTcode(String zone, String circle, String tname) {
		String qry = null;
		List<?> mtrnum = null;
		try {
			qry = "select distinct mtrno from meter_data.master_main where town_code in (select tp_towncode from meter_data.amilocation where tp_towncode LIKE '"
					+ tname + "') and zone LIKE '" + zone + "' and circle LIKE '" + circle + "' ";
				System.out.println(qry);
			mtrnum = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			System.out.println(qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtrnum;
	}
	
	@Override
	public List<?> getmMtrnoByInfo(String zone){
		String qry = null;
		List<?> mtrnum = null;
		try {
			
			qry="select distinct zone from meter_data.master_main where zone='"+zone+"' ";
		
			mtrnum = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			System.out.println(qry);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mtrnum;
		
	}
	@Override
	public List<?> getmMtrnoByZonCirTwn(String zone, String circle, String tname) {
		String qry = null;
		List<?> mtrnum = null;
		try {
			qry = "select distinct mtrno from meter_data.master_main where town_code in (select tp_towncode from meter_data.amilocation where tp_towncode like '"
					+ tname + "'  and zone like '" + zone + "' and circle like '" + circle + "') and zone like '" + zone + "' and circle like '" + circle + "'";
			System.err.println(qry);

			mtrnum = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtrnum;
	}

	@SuppressWarnings("unused")
	private ServletOutputStream doMerge(List<FileInputStream> list, ServletOutputStream servletOutputStream)
			throws DocumentException, IOException {
		Rectangle pageSize = new Rectangle(1050, 720);
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, servletOutputStream);
		document.open();
		PdfContentByte cb = writer.getDirectContent();

		for (FileInputStream in : list) {
			PdfReader reader = new PdfReader(in);
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				document.newPage();
				// import the page from source pdf
				PdfImportedPage page = writer.getImportedPage(reader, i);
				// add the page to the destination pdf
				cb.addTemplate(page, 0, 0);
			}
		}

		servletOutputStream.flush();
		document.close();
		servletOutputStream.close();
		return servletOutputStream;
	}

	@Override
	public void getEnergyHistoryPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno,
			String fdate, String tdate, ArrayList<String> strlist) {
		String s = "";
		for (int i = 0; i < strlist.size(); i++) {
			String t = strlist.get(i);

			s += t;
			if (i < (strlist.size() - 1)) {
				s += ",";
			}
		}

		String[] str = s.split(",");

		try {

			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();

			Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
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
			pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			// cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("Energy History", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.OUT_BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(3);
			header.setWidths(new int[] { 1, 1, 1 });
			header.setWidthPercentage(100);

			PdfPCell headerCell = null;
			headerCell = new PdfPCell(
					new Phrase("Region :" + zone, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("Circle :" + circle, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(new Phrase("Town :" + town, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("MeterNo :" + mtrno, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("FromDate :" + fdate, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("ToDate :" + tdate, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			List<Object[]> EgyHisData = null;
			String query = "select to_char(AA.billing_date,'yyyy-mm-dd HH24:MI:SS')as billing_date,AA.kwh,AA.kvah,AA.kva,AA.date_kva,\n"
					+ "AA.kwh-(case when AA.PreviousKwh is null then AA.kwh else AA.PreviousKwh end) as kwh_consumption,\n"
					+ "AA.kvah-( case when AA.Previouskvah is null then AA.kvah else AA.Previouskvah end) as kvah_consumption,\n"
					+ "AA.kwh_tz1,AA.kwh_tz2,AA.kwh_tz3,AA.kwh_tz4,AA.kwh_tz5,AA.kwh_tz6,AA.kwh_tz7,AA.kwh_tz8,AA.\n"
					+ "kvah_tz1,AA.kvah_tz2,AA.kvah_tz3,AA.kvah_tz4,AA.kvah_tz5,AA.kvah_tz6,AA.kvah_tz7,AA.kvah_tz8,AA.\n"
					+ "demand_kw,AA.occ_date_kw,AA.kvarh_lag,AA.kvarh_lead,AA.bill_kwh_export,AA.bill_kvah_export,AA.\n"
					+ "PreviousKwh,AA.PreviousKvah,AA.PEV_billing_date FROM (\n" + "SELECT \n"
					+ "billing_date,kwh,kvah,kva,date_kva,\n"
					+ "kwh_tz1,kwh_tz2,kwh_tz3,kwh_tz4,kwh_tz5,kwh_tz6,kwh_tz7,kwh_tz8,\n"
					+ "kvah_tz1,kvah_tz2,kvah_tz3,kvah_tz4,kvah_tz5,kvah_tz6,kvah_tz7,kvah_tz8,\n"
					+ "demand_kw,occ_date_kw,kvarh_lag,kvarh_lead,bill_kwh_export,bill_kvah_export,\n"
					+ "LAG(kwh) OVER ( ORDER BY billing_date ) AS PreviousKwh,  \n"
					+ "LAG(kvah) OVER (ORDER BY billing_date ) AS PreviousKvah,\n"
					+ "LAG(billing_date) OVER ( ORDER BY billing_date ) AS PEV_billing_date\n"
					+ "FROM 	meter_data.bill_history WHERE meter_number ='" + mtrno + "'\n"
					+ "AND  to_char(billing_date,'yyyy-mm-dd HH24:MI:SS') in ('" + str[0] + "','" + str[1] + "','"
					+ str[2] + "','" + str[3] + "','" + str[4] + "')\n"
					+ "order by billing_date) AA  ORDER BY AA.billing_date ";
			// System.out.println("query----is---"+query);
			EgyHisData = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(10);
			parameterTable.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Date", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kwh", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kvah", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kva(MD)", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kw", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("MDDateTime", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KwOccDate", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KwhConsum", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KvahConsum", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			// SECOND TABLE
			PdfPTable parameterTable1 = new PdfPTable(10);
			parameterTable1.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable1.setWidthPercentage(100);
			PdfPCell parameterCell1;

			parameterCell1 = new PdfPCell(new Phrase("KwhTOD1", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KwhTOD2", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KwhTOD3", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KwhTOD4", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KwhTOD5", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KwhTOD6", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KwhTOD7", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KwhTOD8", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KvahTOD1", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KvahTOD2", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			// Third Table
			PdfPTable parameterTable2 = new PdfPTable(8);
			parameterTable2.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable2.setWidthPercentage(100);
			PdfPCell parameterCell2;

			parameterCell2 = new PdfPCell(new Phrase("KvahTOD3", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("KvahTOD4", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("KvahTOD5", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("KvahTOD6", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("KvahTOD7", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("KvahTOD8", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("KvarhLag", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("KvarhLead", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			Date date1 = null;
			for (int i = 0; i < EgyHisData.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Object[] obj = EgyHisData.get(i);

				for (int j = 0; j < obj.length; j++) {
					if (j == 0) {
						String value1 = obj[0] + "";
						parameterCell = new PdfPCell(
								new Phrase(obj[0] == null ? "" : obj[0] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						/// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[1] == null ? "" : obj[1] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[2] == null ? "" : obj[2] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[3] == null ? "" : obj[3] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[23] == null ? "" : obj[23] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[4] == null ? "" : obj[4] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[24] == null ? "" : obj[24] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[5] == null ? "" : obj[5] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[6] == null ? "" : obj[6] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						// second
						parameterCell1 = new PdfPCell(
								new Phrase(obj[7] == null ? "" : obj[7] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(
								new Phrase(obj[8] == null ? "" : obj[8] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(
								new Phrase(obj[9] == null ? "" : obj[9] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[10] == null ? "" : obj[10] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[11] == null ? "" : obj[11] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[12] == null ? "" : obj[12] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[13] == null ? "" : obj[13] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[14] == null ? "" : obj[14] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[15] == null ? "" : obj[15] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);
						// h
						parameterCell1 = new PdfPCell(new Phrase(obj[16] == null ? "" : obj[16] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						// Third
						parameterCell2 = new PdfPCell(new Phrase(obj[17] == null ? "" : obj[17] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[18] == null ? "" : obj[18] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[19] == null ? "" : obj[19] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[20] == null ? "" : obj[20] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[21] == null ? "" : obj[21] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[22] == null ? "" : obj[22] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[25] == null ? "" : obj[25] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[26] == null ? "" : obj[26] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

					}
				}
			}
			document.add(parameterTable);
			document.add(parameterTable1);
			document.add(parameterTable2);

			document.add(new Phrase("\n"));
			LineSeparator separator = new LineSeparator();
			separator.setPercentage(98);
			separator.setLineColor(BaseColor.WHITE);
			Chunk linebreak = new Chunk(separator);
			document.add(linebreak);

			document.close();
			response.setHeader("Content-disposition", "attachment; filename=Instantaneous.pdf");
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
	public void getLoadSurveyPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno,
			String fdate, String tdate) {

		try {

			Rectangle pageSize = new Rectangle(1350, 720);
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();

			Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
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
			pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			// cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("Load Survey", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.OUT_BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(2);
			header.setWidths(new int[] { 1, 1 });
			header.setWidthPercentage(100);

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			String query = "";
			List<Object[]> list = null;

			query = "SELECT meter_number,read_time,v_r,v_y,v_b,i_r,i_y,i_b,kwh,kvarh_lag,kvarh_lead,kvah,frequency,average_voltage, average_current, neutral_current,power_factor FROM meter_data.load_survey WHERE meter_number='"
					+ mtrno + "' AND read_time >= to_date('" + fdate
					+ "', 'YYYY-MM-DD') +  INTERVAL  '15 minutes' and read_time <= to_date('" + tdate
					+ "', 'YYYY-MM-DD') +interval '24 hours' ORDER BY read_time DESC;";

			// System.out.println("query----"+query);
			list = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(13);
			parameterTable.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("ReadTime", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vr", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vb", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ir", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Iy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ib", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("BlockKwh", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KvarhLag", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KvarhLead", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kvah", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("PowerFactor", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < list.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Date date1 = null;
				Object[] obj = list.get(i);
				for (int j = 0; j < obj.length; j++) {

					if (j == 0) {
						String value1 = obj[0] + "";
						parameterCell = new PdfPCell(
								new Phrase(obj[0] == null ? "" : obj[0] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[1] == null ? "" : obj[1] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[2] == null ? "" : obj[2] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[3] == null ? "" : obj[3] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[4] == null ? "" : obj[4] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[5] == null ? "" : obj[5] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[6] == null ? "" : obj[6] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[7] == null ? "" : obj[7] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[8] == null ? "" : obj[8] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[9] == null ? "" : obj[9] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[9] == null ? "" : obj[9] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[10] == null ? "" : obj[10] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
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
			response.setHeader("Content-disposition", "attachment; filename=LoadSurvey.pdf");
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
	public void getEventDetailsPDF(HttpServletResponse response, String zne, String cir, String twn, String mtrno,
			String fdate, String tdate) {

		try {

			Rectangle pageSize = new Rectangle(1350, 720);
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();

			Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
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
			pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			// cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("Event Details", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.OUT_BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(2);
			header.setWidths(new int[] { 1, 1 });
			header.setWidthPercentage(100);

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			String query = "";
			List<Object[]> list = null;

			query = "SELECT event_time,event_code, ( case when (to_number(event_code, '999')-lag(to_number(event_code, '999'), 1) OVER (ORDER BY event_time))=1 then \n"
					+ "concat(event_time,',',lag(event_time, 1) OVER (ORDER BY event_time))  END) as duration,	v_r,	v_y,	v_b,	i_r,	i_y,	i_b,	pf_r,	pf_y,	pf_b,	kwh \n"
					+ "FROM	meter_data.events WHERE	meter_number = '" + mtrno
					+ "' AND to_char(event_time, 'yyyy-MM-dd') BETWEEN '" + fdate + "' AND '" + tdate
					+ "' ORDER BY 	event_time DESC";

			// System.out.println("query----"+query);
			list = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(14);
			parameterTable.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("EventTime", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("EventCode", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("EventDesc", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Duration", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vr", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vb", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ir", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Iy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ib", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("PFr", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("PFy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("PFb", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KWh", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < list.size(); i++) {

				Object[] obj = list.get(i);

				String fulldiff = "";
				String eDesc = "";
				if (obj[2] == null) {
					fulldiff = "";
				} else {
					String dates = obj[2] + "";
					String[] duration = dates.split(",");
					// System.out.println("duration---"+duration);
					String dateStop = duration[0];
					String dateStart = duration[1];

					// System.err.println("dateStop--"+dateStop+"dateStart---"+dateStart);

					SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

					Date d1 = null;
					Date d2 = null;

					d1 = format.parse(dateStart);
					d2 = format.parse(dateStop);

					long diff = d2.getTime() - d1.getTime();

					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000) % 24;
					long diffDays = diff / (24 * 60 * 60 * 1000);

					fulldiff = " " + diffDays + "days " + diffHours + ":" + diffMinutes + ":" + diffSeconds + "";
					// System.err.println("fulldiff--"+fulldiff);
				}

				if (((String) obj[1]).equalsIgnoreCase("101")) {
					eDesc = "Power faliure - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("102")) {
					eDesc = "Power faliure - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("1")) {
					eDesc = "R-Phase PT link Missing (Missing Potential) - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("2")) {
					eDesc = "R-Phase PT link Missing (Missing Potential) - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("3")) {
					eDesc = "Y-Phase PT link Missing (Missing Potential) - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("5")) {
					eDesc = "B-Phase PT link Missing (Missing Potential) - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("4")) {
					eDesc = "Y-Phase PT link Missing (Missing Potential) - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("8")) {
					eDesc = "Over Voltage in any Phase - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("9")) {
					eDesc = "Low Voltage in any Phase - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("6")) {
					eDesc = "B-Phase PT link Missing (Missing Potential) - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("7")) {
					eDesc = "Over Voltage in any Phase - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("10")) {
					eDesc = "Low Voltage in any Phase - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("11")) {
					eDesc = "Voltage Unbalance - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("12")) {
					eDesc = "Voltage Unbalance - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("51")) {
					eDesc = "Phase  R CT reverse - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("52")) {
					eDesc = "Phase  R CT reverse - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("53")) {
					eDesc = "Phase  Y CT reverse - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("54")) {
					eDesc = "Phase  Y CT reverse - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("55")) {
					eDesc = "Phase  B CT reverse - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("56")) {
					eDesc = "Phase  B CT reverse - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("57")) {
					eDesc = "Phase  R CT Open - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("58")) {
					eDesc = "Phase  R CT Open - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("59")) {
					eDesc = "Phase  Y CT Open - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("60")) {
					eDesc = "Phase  Y CT Open - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("61")) {
					eDesc = "Phase  B CT Open - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("62")) {
					eDesc = "Phase  B CT Open - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("63")) {
					eDesc = "Current Unbalance - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("64")) {
					eDesc = "Current Unbalance - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("65")) {
					eDesc = "CT Bypass - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("66")) {
					eDesc = "CT Bypass - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("67")) {
					eDesc = "Over Current in any Phase - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("68")) {
					eDesc = "Over Current in any Phase - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("151")) {
					eDesc = "Real Time Clock  Date and Time";
				} else if (((String) obj[1]).equalsIgnoreCase("152")) {
					eDesc = "Demand Integration Period";
				} else if (((String) obj[1]).equalsIgnoreCase("153")) {
					eDesc = "Profile Capture Period";
				} else if (((String) obj[1]).equalsIgnoreCase("154")) {
					eDesc = "Single-action Schedule for Billing Dates";
				} else if (((String) obj[1]).equalsIgnoreCase("155")) {
					eDesc = "Activity Calendar for Time Zones etc.";
				} else if (((String) obj[1]).equalsIgnoreCase("201")) {
					eDesc = "Influence of Permanent Magnet or AC/ DC Electromagnet - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("202")) {
					eDesc = "Influence of Permanent Magnet or AC/ DC Electromagnet - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("203")) {
					eDesc = "Neutral Disturbance - HF AND DC - Occurence";
				} else if (((String) obj[1]).equalsIgnoreCase("204")) {
					eDesc = "Neutral Disturbance - HF AND DC - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("205")) {
					eDesc = "Very Low PF - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("206")) {
					eDesc = "Very Low PF - Restoration";
				} else if (((String) obj[1]).equalsIgnoreCase("251")) {
					eDesc = "Meter Cover Opening - Occurrence";
				} else if (((String) obj[1]).equalsIgnoreCase("301")) {
					eDesc = "Meter Disconnected";
				} else if (((String) obj[1]).equalsIgnoreCase("302")) {
					eDesc = "Meter Connected";
				}
				Object[] obj1 = list.get(i);
				for (int j = 0; j < obj1.length; j++) {
					if (j == 0) {

						parameterCell = new PdfPCell(new Phrase(obj1[0] == null ? "" : obj[0] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[1] == null ? "" : obj1[1] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(16f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(eDesc == null ? "" : eDesc + "", new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(16f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(fulldiff == null ? "" : fulldiff + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(16f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[3] == null ? "0" : obj1[3] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(16f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[4] == null ? "0" : obj1[4] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(16f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[5] == null ? "0" : obj1[5] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(16f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[6] == null ? "0" : obj1[6] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(16f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[7] == null ? "0" : obj1[7] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[8] == null ? "0" : obj1[8] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[9] == null ? "0" : obj1[9] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[10] == null ? "0" : obj1[10] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[11] == null ? "0" : obj1[11] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj1[12] == null ? "0" : obj1[12] + "",
								new Font(Font.FontFamily.HELVETICA, 13)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
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
			response.setHeader("Content-disposition", "attachment; filename=EventDetails.pdf");
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
	public void getDailyParamPDF(HttpServletResponse response, String zne, String cir, String twn, String mtrno,
			String fdate, String tdate) {
		try {

			Rectangle pageSize = new Rectangle(1350, 720);
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();

			Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
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
			pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			// cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("Load Survey", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.OUT_BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(2);
			header.setWidths(new int[] { 1, 1 });
			header.setWidthPercentage(100);

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			String query = "";
			List<Object[]> list = null;

			query = "SELECT DISTINCT date(read_time) AS modem_time,"
					+ "max(v_r) as v_r,max(v_y) as v_y,max(v_b) as v_b,max(i_r) as i_r,max(i_y) as i_y,max(i_b) as i_b,sum(kwh) as kwh,"
					+ "sum(kvarh_lag) as kvarh_lag,sum(kvarh_lead) as kvarh_lead,sum(kvah) as kvah, "
					+ " sum(CAST(average_voltage as NUMERIC))/count(*) as col1, sum(CAST( average_current as NUMERIC))/count(*) col2, sum(CAST(neutral_current as NUMERIC))/count(*) as col3, "
					+ " sum(kwh_exp) as kwhexp,sum(kvah_exp) as kvahexp,sum(kwh_imp) as cum_kwh,sum(kvah_imp) as cum_kvah "
					+ "FROM meter_data.load_survey WHERE meter_number='" + mtrno + "' and date(read_time) BETWEEN '"
					+ fdate + "' AND '" + tdate + "' " + "GROUP BY date(read_time) ORDER BY modem_time";

			// System.out.println("query----"+query);
			list = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(12);
			parameterTable.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Date", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vr", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vb", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ir", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Iy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ib", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kwh", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KvarhLag", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KvarhLead", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kvah", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < list.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Object[] obj = list.get(i);
				for (int j = 0; j < obj.length; j++) {

					if (j == 0) {
						String value1 = obj[0] + "";
						parameterCell = new PdfPCell(new Phrase(obj[0] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[1] == null ? "" : obj[1] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[2] == null ? "" : obj[2] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[3] == null ? "" : obj[3] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[4] == null ? "" : obj[4] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[5] == null ? "" : obj[5] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[6] == null ? "" : obj[6] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[7] == null ? "" : obj[7] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[8] == null ? "" : obj[8] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[9] == null ? "" : obj[9] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[10] == null ? "" : obj[10] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
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
			response.setHeader("Content-disposition", "attachment; filename=DailyParameters.pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<AmrLoadEntity> getLoadSurveyDataExcel(String mtrno, String fdate, String tdate) {
		String sql = "";
		List<AmrLoadEntity> list = null;
		try {
			sql = "SELECT to_char(read_time,'YYYY-MM-DD HH24:MI:SS')as readtime,v_r,v_y,v_b,i_r,i_y,i_b,kwh,kvarh_lag,kvarh_lead,kvah,power_factor,frequency,average_voltage, average_current, neutral_current FROM meter_data.load_survey WHERE meter_number='"
					+ mtrno + "' AND read_time >= to_date('" + fdate
					+ "', 'YYYY-MM-DD') +  INTERVAL  '15 minutes' and read_time <= to_date('" + tdate
					+ "', 'YYYY-MM-DD') +interval '24 hours' ORDER BY read_time DESC;";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void getInstantaneous2PDF(HttpServletResponse response, String zne, String cir, String twn, String mtrno,
			String fdate, String tdate) {
		try {

			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();

			Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
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
			pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			// cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("Instantaneous", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.OUT_BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(3);
			header.setWidths(new int[] { 1, 1, 1 });
			header.setWidthPercentage(100);

			PdfPCell headerCell = null;
			headerCell = new PdfPCell(new Phrase("Region :" + zne, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(new Phrase("Circle :" + cir, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(new Phrase("Town :" + twn, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("MeterNo :" + mtrno, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("FromDate :" + fdate, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("ToDate :" + tdate, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			List<Object[]> Insta2Data = null;
			String query = "SELECT * FROM meter_data.amiinstantaneous WHERE meter_number='" + mtrno
					+ "' AND read_time<(SELECT  max(read_time)"
					+ " FROM meter_data.amiinstantaneous WHERE meter_number='" + mtrno
					+ "' AND date(read_time) BETWEEN '" + fdate + "' AND '" + tdate + "') "
					+ "AND date(read_time) BETWEEN '" + fdate + "' AND '" + tdate + "' ORDER BY read_time DESC";
			System.out.println("query--->" + query);
			Insta2Data = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(11);
			parameterTable.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("MeterNo", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("ReadTime", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kwh", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kvah", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kva", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ir", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Iy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ib", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vr", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			// SECOND TABLE
			PdfPTable parameterTable1 = new PdfPTable(11);
			parameterTable1.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable1.setWidthPercentage(100);
			PdfPCell parameterCell1;

			parameterCell1 = new PdfPCell(new Phrase("Vb", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("PFr", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("PFy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("PFb", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("PF", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("Frequency", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("Kwh(Exp)", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("Kvah(Exp)", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("Power(KW)", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KvarhLag", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			parameterCell1 = new PdfPCell(new Phrase("KvarhLead", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell1.setFixedHeight(25f);
			parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable1.addCell(parameterCell1);

			// Third Table
			PdfPTable parameterTable2 = new PdfPTable(10);
			parameterTable2.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable2.setWidthPercentage(100);
			PdfPCell parameterCell2;

			parameterCell2 = new PdfPCell(
					new Phrase("PowerOffCount", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(
					new Phrase("PowerOffDuration", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(
					new Phrase("TamperCount", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(
					new Phrase("PhaseSequence", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("IrAngle", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("IyAngle", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("IbAngle", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("VrAngle", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("VyAngle", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			parameterCell2 = new PdfPCell(new Phrase("VbAngle", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			// parameterCell2.setFixedHeight(25f);
			parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable2.addCell(parameterCell2);

			Date date1 = null;
			for (int i = 0; i < Insta2Data.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Object[] obj = Insta2Data.get(i);

				double Ir, Iy, Ib;
				Ir = Float(obj[19]) * (180 / Math.PI);
				Iy = Float(obj[20]) * (180 / Math.PI);
				Ib = Float(obj[21]) * (180 / Math.PI);

				for (int j = 0; j < obj.length; j++) {
					if (j == 0) {
						String value1 = obj[0] + "";
						parameterCell = new PdfPCell(new Phrase(obj[4] == null ? null : obj[4] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						/// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[3] == null ? null : obj[3] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[7] == null ? null : obj[7] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[8] == null ? null : obj[8] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[9] == null ? null : obj[9] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[13] == null ? null : obj[13] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[14] == null ? null : obj[14] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[15] == null ? null : obj[15] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[16] == null ? null : obj[16] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[17] == null ? null : obj[17] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable.addCell(parameterCell);

						// second
						parameterCell1 = new PdfPCell(new Phrase(obj[18] == null ? null : obj[18] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[19] == null ? null : obj[19] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[20] == null ? null : obj[20] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[21] == null ? null : obj[21] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[22] == null ? null : obj[22] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[23] == null ? null : obj[23] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[24] == null ? null : obj[24] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[26] == null ? null : obj[26] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[28] == null ? null : obj[28] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);
						// h
						parameterCell1 = new PdfPCell(new Phrase(obj[30] == null ? null : obj[30] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						parameterCell1 = new PdfPCell(new Phrase(obj[31] == null ? null : obj[31] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable1.addCell(parameterCell1);

						// Third
						parameterCell2 = new PdfPCell(new Phrase(obj[32] == null ? null : obj[32] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[33] == null ? null : obj[33] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[36] == null ? null : obj[36] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[54] == null ? null : "FORWARD" + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(Ir + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(Iy + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(Ib + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[55] == null ? "120" : obj[55] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[56] == null ? "120" : obj[56] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

						parameterCell2 = new PdfPCell(new Phrase(obj[57] == null ? "120" : obj[57] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						// parameterCell1.setFixedHeight(25f);
						parameterTable2.addCell(parameterCell2);

					}
				}
			}
			document.add(parameterTable);
			document.add(parameterTable1);
			document.add(parameterTable2);

			document.add(new Phrase("\n"));
			LineSeparator separator = new LineSeparator();
			separator.setPercentage(98);
			separator.setLineColor(BaseColor.WHITE);
			Chunk linebreak = new Chunk(separator);
			document.add(linebreak);

			document.close();
			response.setHeader("Content-disposition", "attachment; filename=Instantaneous.pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private double Float(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	private double Double(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getDailyMinPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno,
			String fdate, String tdate, String minmf) {
		try {

			Rectangle pageSize = new Rectangle(1550, 720);
			Document document = new Document(PageSize.A2);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();

			Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
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
			pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("Minimum Parameter Values", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.OUT_BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(6);
			header.setWidthPercentage(100);
			PdfPCell headerCell = null;

			headerCell = new PdfPCell(new Phrase("Region :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Circle :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Town :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(town, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("From Date :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(fdate, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("To Date :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			header.addCell(getCell(tdate, PdfPCell.ALIGN_LEFT));

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);
			List<MasterMainEntity> masterdata = null;
			try {
				masterdata = masterMainService.getFeederData(mtrno);
			} catch (Exception e) {
				// TODO: handle exception
			}

			String table = "";
			String fdrcategory = masterdata.get(0).getFdrcategory();
			//System.err.println(fdrcategory);
			if (fdrcategory != null) {
				if ("DT".equalsIgnoreCase(fdrcategory)) {
					table = "dtdetails";
				} else {
					table = "feederdetails";
				}
			}

			String query = "";
			List<Object[]> list = null;

			if (minmf.equalsIgnoreCase("false")) {

				query = "SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,\n"
						+ "min(i_r) as ir,min(i_y) as iy,min(i_b) as ib, \n"
						+ "min(v_r) as vr,min(v_y) as vy,min(v_b) as vb,\n"
						+ "min(kvarh_lag) as kvarh_lags,min(kvarh_lead) as kvarh_leads,min(kvah) as kvah,min(kwh) as block_kwh,(min(i_r)+min(i_y)+min(i_b)) as TotalIr,"
						+ " round((min(v_r)+min(v_y)+min(v_b))/3,3) as AvgVr FROM "
						+ "meter_data.load_survey WHERE meter_number='" + mtrno
						+ "'AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fdate + "' and '" + tdate + "' "
						+ " and i_r>0 and i_y>0 and i_b>0 and v_r>0 and v_y>0 and v_b>0 and kvah>0 and kwh>0  GROUP BY meter_number,time";
			} else {
				query = "SELECT a.meter_number,a.time,a.ir*b.current_mf as ir,a.iy*b.current_mf as iy,a.ib*b.current_mf as ib,a.vr*b.volt_mf as vr,a.vy*b.volt_mf as vy,a.vb*b.volt_mf as vb,a.kvarh_lags*b.mf as kvarh_lags,a.kvarh_leads*b.mf as kvarh_lead,a.kvah*b.mf as kvah,a.block_kwh*b.mf as block_kwh,\n"
						+ "(a.ir*b.current_mf+a.iy*b.current_mf+a.ib*b.current_mf) as TotalIr,\n"
						+ "round((a.vr*b.volt_mf +a.vy*b.volt_mf+a.vb*b.volt_mf)/3,3) as AvgVr\n"
						+ "FROM(SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,round(min(i_r),3) as ir,round(min(i_y),3) as iy,round(min(i_b),3) as ib,round(min(v_r),3) as vr,round(min(v_y),3) as vy,round(min(v_b),3) as vb,round(min(kvarh_lag),3) as kvarh_lags,round(min(kvarh_lead),3) as kvarh_leads,round(min(kvah),3) as kvah,round(min(kwh),3) as block_kwh FROM meter_data.load_survey WHERE meter_number='"
						+ mtrno + "'AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fdate + "' and '" + tdate
						+ "'  and i_r>0 and i_y>0 and i_b>0 and v_r>0 and v_y>0 and v_b>0  and kvah>0 and kwh>0 GROUP BY meter_number,time)a,(SELECT DISTINCT cast(mf as NUMERIC),cast(volt_mf as NUMERIC),cast(current_mf as NUMERIC),meterno FROM meter_data."
						+ table + "  WHERE meterno='" + mtrno + "')b WHERE a.meter_number=b.meterno";
			}

			list = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(13);
			parameterTable.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);
			parameterCell = new PdfPCell(new Phrase("meterNo", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Date", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ir", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Iy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ib", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vr", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vb", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kvarh Lag", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KvarhLead", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kvah", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Block Kwh", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < list.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Date date1 = null;
				Object[] obj = list.get(i);
				for (int j = 0; j < obj.length; j++) {

					if (j == 0) {
						String value1 = obj[0] + "";
						parameterCell = new PdfPCell(
								new Phrase(obj[0] == null ? "" : obj[0] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[1] == null ? "" : obj[1] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[2] == null ? "" : obj[2] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[3] == null ? "" : obj[3] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[4] == null ? "" : obj[4] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[5] == null ? "" : obj[5] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[6] == null ? "" : obj[6] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[7] == null ? "" : obj[7] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[8] == null ? "" : obj[8] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[9] == null ? "" : obj[9] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[10] == null ? "" : obj[10] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[11] == null ? "" : obj[11] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
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
			response.setHeader("Content-disposition", "attachment; filename=DailyMinParameters.pdf");
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
	public void getDailyMaxPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno,
			String fdate, String tdate, String maxmf) {
		try {

			Rectangle pageSize = new Rectangle(1550, 720);
			Document document = new Document(PageSize.A2);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();

			Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
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
			pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));

			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("Maximum Parameter Values", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.OUT_BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(6);
			header.setWidthPercentage(100);
			PdfPCell headerCell = null;

			headerCell = new PdfPCell(new Phrase("Region :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Circle :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Town :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(town, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("From Date :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(fdate, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("To Date :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(tdate, PdfPCell.ALIGN_LEFT));

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			String query = "";

			List<MasterMainEntity> masterdata = null;
			try {
				masterdata = masterMainService.getFeederData(mtrno);
			} catch (Exception e) {
				// TODO: handle exception
			}

			String table = "";
			String fdrcategory = masterdata.get(0).getFdrcategory();
			
			if (fdrcategory != null) {
				if ("DT".equalsIgnoreCase(fdrcategory)) {
					table = "dtdetails";
				} else {
					table = "feederdetails";
				}
			}

			List<Object[]> list = null;
			if (maxmf.equalsIgnoreCase("false")) {

				query = "SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,max(i_r) as ir,max(i_y) as iy,max(i_b) as ib,\n"
						+ "max(v_r) as vr,max(v_y) as vy,max(v_b) as vb,max(kvarh_lag) as kvarh_lags,\n"
						+ "max(kvarh_lead) as kvarh_leads,max(kvah) as kvah,max(kwh) as block_kwh,(max(i_r)+max(i_y)+max(i_b)) as TotalIr,round((max(v_r)+max(v_y)+max(v_b))/3,3) as AvgVr\n"
						+ " FROM meter_data.load_survey WHERE meter_number='" + mtrno + "'\n"
						+ "AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fdate + "' and '" + tdate
						+ "' GROUP BY meter_number,time";
			} else {
				query = "SELECT a.meter_number,a.time,a.ir*b.current_mf as ir,a.iy*b.current_mf as iy,a.ib*b.current_mf as ib,a.vr*b.volt_mf as vr,a.vy*b.volt_mf as vy,a.vb*b.volt_mf as vb,a.kvarh_lags*b.mf as kvarh_lags,a.kvarh_leads*b.mf as kvarh_lead,a.kvah*b.mf as kvah,a.block_kwh*b.mf as block_kwh,\n"
						+ "(a.ir*b.current_mf+a.iy*b.current_mf+a.ib*b.current_mf) as TotalIr,\n"
						+ "round((a.vr*b.volt_mf +a.vy*b.volt_mf+a.vb*b.volt_mf)/3,3) as AvgVr\n"
						+ "FROM(SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,round(max(i_r),3) as ir,round(max(i_y),3) as iy,round(max(i_b),3) as ib,round(max(v_r),3) as vr,round(max(v_y),3) as vy,round(max(v_b),3) as vb,round(max(kvarh_lag),3) as kvarh_lags,round(max(kvarh_lead),3) as kvarh_leads,round(max(kvah),3) as kvah,max(kwh) as block_kwh FROM meter_data.load_survey WHERE meter_number='"
						+ mtrno + "'AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fdate + "' and '" + tdate
						+ "'  and i_r>0 and i_y>0 and i_b>0 and v_r>0 and v_y>0 and v_b>0  and kvah>0 and kwh>0 GROUP BY meter_number,time)a,(SELECT DISTINCT cast(mf as NUMERIC),cast(volt_mf as NUMERIC),cast(current_mf as NUMERIC),meterno FROM meter_data."
						+ table + "  WHERE meterno='" + mtrno + "')b WHERE a.meter_number=b.meterno";

			}
			list = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(13);
			parameterTable.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);
			parameterCell = new PdfPCell(new Phrase("meterNo", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Date", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ir", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Iy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ib", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vr", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vb", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kvarh Lag", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KvarhLead", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kvah", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);
			parameterCell = new PdfPCell(new Phrase("Block Kwh", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < list.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Date date1 = null;
				Object[] obj = list.get(i);
				for (int j = 0; j < obj.length; j++) {

					if (j == 0) {
						String value1 = obj[0] + "";
						parameterCell = new PdfPCell(
								new Phrase(obj[0] == null ? "" : obj[0] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[1] == null ? "" : obj[1] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[2] == null ? "" : obj[2] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[3] == null ? "" : obj[3] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[4] == null ? "" : obj[4] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[5] == null ? "" : obj[5] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[6] == null ? "" : obj[6] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[7] == null ? "" : obj[7] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[8] == null ? "" : obj[8] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[9] == null ? "" : obj[9] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[10] == null ? "" : obj[10] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[11] == null ? "" : obj[11] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
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
			response.setHeader("Content-disposition", "attachment; filename=DailyMaxParameters.pdf");
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
	public void getDailyAvgPDF(HttpServletResponse response, String zone, String circle, String town, String mtrno,
			String fdate, String tdate, String avgmf) {
		try {

			Rectangle pageSize = new Rectangle(1550, 720);
			Document document = new Document(PageSize.A2);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
			document.open();

			Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
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
			pstart.add(new Phrase("BCITS Private Ltd", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));

			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("Average Parameter Values", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.OUT_BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(6);
			header.setWidthPercentage(100);
			PdfPCell headerCell = null;

			headerCell = new PdfPCell(new Phrase("Region :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Circle :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Town :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(town, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("From Date :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(fdate, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("To Date :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			header.addCell(getCell(tdate, PdfPCell.ALIGN_LEFT));

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			List<MasterMainEntity> masterdata = null;
			try {
				masterdata = masterMainService.getFeederData(mtrno);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			String table = "";
			String fdrcategory = masterdata.get(0).getFdrcategory();
			
			if (fdrcategory != null) {
				if ("DT".equalsIgnoreCase(fdrcategory)) {
					table = "dtdetails";
				} else {
					table = "feederdetails";
				}
			}

			String query = "";
			List<Object[]> list = null;
			if (avgmf.equalsIgnoreCase("false")) {

				query = "SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time, round(AVG(i_r),3) as ir,round(AVG(i_y),3) as iy,round(AVG(i_b),3) as ib,\r\n"
						+ "	round(AVG(v_r),3) as vr,round(AVG(v_y),3) as vy,round(AVG(v_b),3) as vb,\r\n"
						+ "	round(AVG(kvarh_lag),3) as kvarh_lags,round(AVG(kvarh_lead),3) as kvarh_leads,\r\n"
						+ "	round(AVG(kvah),3) as kvah,round(AVG(kwh),3) as block_kwh,\r\n"
						+ "	(round(AVG(i_r),3)+round(AVG(i_y),3)+round(AVG(i_b),3)) as TotalIr,\r\n"
						+ "	round((round(AVG(v_r),3) +round(AVG(v_y),3) +round(AVG(v_b),3))/3,3) as avgVr\r\n"
						+ "	FROM meter_data.load_survey WHERE meter_number='" + mtrno
						+ "'AND  to_char(read_time,'yyyy-MM-dd') \r\n" + "	BETWEEN '" + fdate + "' and '" + tdate
						+ "'   GROUP BY meter_number,time";
			} else {
				query = "SELECT a.meter_number,a.time,a.ir*b.current_mf as ir,a.iy*b.current_mf as iy,a.ib*b.current_mf as ib,a.vr*b.volt_mf as vr,a.vy*b.volt_mf as vy,a.vb*b.volt_mf as vb,a.kvarh_lags*b.mf as kvarh_lags,a.kvarh_leads*b.mf as kvarh_lead,a.kvah*b.mf as kvah,a.block_kwh*b.mf as block_kwh,\n"
						+ "(a.ir*b.current_mf+a.iy*b.current_mf+a.ib*b.current_mf) as TotalIr,\n"
						+ "round((a.vr*b.volt_mf +a.vy*b.volt_mf+a.vb*b.volt_mf)/3,3) as AvgVr\n"
						+ "FROM(SELECT meter_number,to_char(read_time,'YYYY-MM-dd') as time,round(AVG(i_r),3) as ir,round(AVG(i_y),3) as iy,round(AVG(i_b),3) as ib,round(AVG(v_r),3) as vr,round(AVG(v_y),3) as vy,round(AVG(v_b),3) as vb,round(AVG(kvarh_lag),3) as kvarh_lags,round(AVG(kvarh_lead),3) as kvarh_leads,round(AVG(kvah),3) as kvah,round(AVG(kwh),3) as block_kwh FROM meter_data.load_survey WHERE meter_number='"
						+ mtrno + "'AND  to_char(read_time,'yyyy-MM-dd') BETWEEN '" + fdate + "' and '" + tdate
						+ "'  and i_r>0 and i_y>0 and i_b>0 and v_r>0 and v_y>0 and v_b>0  and kvah>0 and kwh>0 GROUP BY meter_number,time)a,(SELECT DISTINCT cast(mf as NUMERIC),cast(volt_mf as NUMERIC),cast(current_mf as NUMERIC),meterno FROM meter_data."
						+ table + "  WHERE meterno='" + mtrno + "')b WHERE a.meter_number=b.meterno";

			}
			list = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(13);
			parameterTable.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Mtrno", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Date", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ir", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Iy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Ib", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vr", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vy", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Vb", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KvarhLag", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KvarhLead", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Kvah", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);
			parameterCell = new PdfPCell(new Phrase("BlockKwh", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < list.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Date date1 = null;
				Object[] obj = list.get(i);
				for (int j = 0; j < obj.length; j++) {

					if (j == 0) {
						String value1 = obj[0] + "";
						parameterCell = new PdfPCell(
								new Phrase(obj[0] == null ? "" : obj[0] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[1] == null ? "" : obj[1] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[2] == null ? "" : obj[2] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[3] == null ? "" : obj[3] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[4] == null ? "" : obj[4] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[5] == null ? "" : obj[5] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[6] == null ? "" : obj[6] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[7] == null ? "" : obj[7] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[8] == null ? "" : obj[8] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[9] == null ? "" : obj[9] + "", new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[10] == null ? "" : obj[10] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[11] == null ? "" : obj[11] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
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
			response.setHeader("Content-disposition", "attachment; filename=DailyAvgParameters.pdf");
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
