package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.FeederEntity;
import com.bcits.entity.MeterInventoryEntity;
import com.bcits.service.DtDetailsService;
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

@Repository
public class DtDetailsServiceImpl extends GenericServiceImpl<DtDetailsEntity> implements DtDetailsService {
	/*
	 * @Override public List getsubdivncodes() { List list=null; List<?> list =
	 * null; String qry = ""; try { qry =
	 * "select distinct subdiv from meter_data.master"; list =
	 * getCustomEntityManager("postgresMdas")
	 * .createNativeQuery(qry).getResultList(); } catch (Exception e) {
	 * e.printStackTrace(); } return list; }
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getdtdetails() {
		List<?> list = null;
		String qry = "";
		try {
			qry = "select id,dttype,dtname,dtcapacity,phase,parentid,dttpid,\n"
					+ "tpparentid,meterno,metermanufacture,consumptionperc,dt_id from meter_data.dtdetails where deleted !=1 ";

			/*
			 * list=getCustomEntityManager("postgresMdas").createNativeQuery(qry)
			 * .getResultList();
			 */
			System.err.println("qry--" + qry);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getChangedDetails(int id) {
		String qry = "select id,dttype,dtname,dtcapacity,phase,dttpid,\n"
				+ "tpparentid,meterno,metermanufacture,consumptionperc,mf,geo_cord_x,geo_cord_y from meter_data.dtdetails where dttpid ='"
				+ id + "'";
		System.out.println("Hiii"+qry);
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public int getmodifydtdetails(String dtide, String dtnamee, String editdttype, double editdtcapacity,
			int editdtphase, String edittpdtcode, String edittpparentcode, String editmetersrno, String editmetermf,
			double editmf, double editConsumptionper) {

		int i = 0;
		String sql = "update meter_data.dtdetails set dtname='" + dtnamee + "',dtcapacity=" + editdtcapacity + ",phase="
				+ editdtphase + ",dttype='" + editdttype + "',dttpid='" + edittpdtcode + "',tpparentid='"
				+ edittpparentcode + "',meterno='" + editmetersrno + "',\n" + "metermanufacture='" + editmetermf
				+ "',mf=" + editmf + ",consumptionperc=" + editConsumptionper + "\n" + "where id=" + dtide + "";

		try {
			i = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
			/* System.out.println(sql); */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;

	}

	@Override
	public List<?> getsubstation(String officeid) {
		List<?> list = new ArrayList<>();
		String qry = "";
		try {
			/*
			 * qry =
			 * "select distinct ss_name  from meter_data.substation_details s,meter_data.feederdetails f \n"
			 * + "WHERE cast(s.ss_id AS varchar) =f.parentid and s.office_id='"
			 * +officeid+"' order by ss_name asc" ;
			 */
			qry = "select ss_id,ss_name  from meter_data.substation_details s,meter_data.feederdetails f \n"
					+ "WHERE cast(s.ss_id AS varchar) =f.parentid and s.office_id='" + officeid
					+ "' GROUP BY ss_id,ss_name order by ss_id asc";
			// System.out.println("get ssname--"+qry);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<?> getdtname() {
		List<?> list = null;
		String qry = "";
		try {
			qry = "select distinct dtname from meter_data.dtdetails";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getShowdivision(String circle) {
		List<?> list = null;
		String qry = "";
		try {
			qry = "select distinct division from meter_data.amilocation where circle ='" + circle + "'";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/*
	 * @Override public List<?> getmeterManuFacture() { List<?> list = null; String
	 * qry = ""; try { qry =
	 * "select  distinct meter_make from meter_data.meter_inventory;"; list =
	 * getCustomEntityManager("postgresMdas")
	 * .createNativeQuery(qry).getResultList(); } catch (Exception e) {
	 * e.printStackTrace(); } return list; }
	 */

	@Override
	public List<?> getFeederNameForDt(String subStationName) {
		List<?> list = null;
		String qry = "";
		try {
			/*
			 * qry =
			 * "select  f.feedername,f.id FROM meter_data.substation_details s,meter_data.feederdetails f \n"
			 * + "WHERE ss_name='" + subStationName +
			 * "' AND f.parentid=CAST(s.id AS VARCHAR) AND f.feedername iS NOT NULL";
			 */
			qry = "select  f.feedername,f.fdr_id FROM meter_data.substation_details s,meter_data.feederdetails f \n"
					+ "WHERE ss_name='" + subStationName
					+ "' AND f.parentid=CAST(s.ss_id AS VARCHAR) AND f.feedername iS NOT NULL AND f.feedername  NOT in('') ORDER BY fdr_id";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<DtDetailsEntity> getDtDetailsBymetrno(String meterno) {
		List<DtDetailsEntity> result = new ArrayList<DtDetailsEntity>();
		try {
			result = getCustomEntityManager("postgresMdas").createNamedQuery("DtDetailsEntity_getData").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getDtid() {
		String qry = "";
		String result = null;
		try {
			/*
			 * qry="SELECT B. rule||(case WHEN length (B.id)=1 then '00'||B.id WHEN length (B.id)=2 then '0'||B.id else B.id  END) from \n"
			 * + "( \n" +
			 * "SELECT substr(A.dtid, 0,3) as rule,CAST(CAST(substr(A.dtid, 3,length(A.dtid))as INTEGER)+1 as TEXT) as id FROM \n"
			 * + "( \n" +
			 * "SELECT COALESCE(max(dt_id),'DT000') as dtid FROM meter_data.dtdetails \n" +
			 * ")A \n" + ")B ";
			 */
			/*
			 * qry="SELECT B. rule||(case WHEN length (B.id)=1 then '0'||B.id else B.id  END) from \n"
			 * + "( \n" +
			 * "SELECT substr(A.dtid, 0,7) as rule,CAST(CAST(substr(A.dtid, 7,length(A.dtid))as INTEGER)+1 as TEXT) as id FROM \n"
			 * + "( \n" +
			 * "SELECT COALESCE(max(dt_id),'D0000000') as dtid FROM meter_data.dtdetails \n"
			 * + ")A \n" + ")B ";
			 */

			/*
			 * qry="SELECT B. rule||(case WHEN length (B.id)=1 then '0'||B.id WHEN length (B.id)=2 then '0'||B.id else B.id  END) from   \n"
			 * +
			 * "(SELECT substr(A.dtid, 0,3) as rule,CAST(CAST(substr(A.dtid, 3,length(A.dtid))as INTEGER)+1 as TEXT) as id \n"
			 * +
			 * "FROM (SELECT COALESCE(max(dt_id),'DT00') as dtid FROM meter_data.dtdetails)A)B"
			 * ;
			 */
			qry = "SELECT  B.RULE || ( CASE\n" + "WHEN LENGTH ( B.ID ) = 7 THEN '0' || B.ID   \n"
					+ "WHEN LENGTH ( B.ID ) = 6 THEN '00' || B.ID   \n"
					+ "WHEN LENGTH ( B.ID ) = 5 THEN '000' || B.ID   \n"
					+ "WHEN LENGTH ( B.ID ) = 4 THEN '0000' || B.ID   \n"
					+ "WHEN LENGTH ( B.ID ) = 3 THEN '00000' || B.ID   \n"
					+ "WHEN LENGTH ( B.ID ) = 2 THEN '000000' || B.ID   \n"
					+ "WHEN LENGTH ( B.ID ) = 1 THEN '0000000' || B.ID   \n" + "ELSE B.ID END )   \n" + "FROM  \n"
					+ "(SELECT substr( A.dtid, 0, 3 ) AS RULE,CAST(CAST(substr( A.dtid,3,LENGTH(A.dtid))AS INTEGER )+ 1 AS TEXT) AS ID   \n"
					+ "	FROM( SELECT COALESCE ( MAX(dt_id), 'DT00000000' ) AS dtid FROM meter_data.dtdetails   \n"
					+ "		) A   \n" + ") B;";
			result = (String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String deletedt(String dtide) {
		// System.out.println("dtid is---"+dtide);
		String qry = null;
		qry = "select *from meter_data.consumermaster where dtcode = '" + dtide + "' ";
		List resultList = null;
		try {

			resultList = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resultList.size() == 0) {

			String qry1 = "update meter_data.dtdetails set deleted=1 where dttpid='" + dtide + "' ";
			String qry2 = "DELETE from meter_data.master_main WHERE mtrno IN\n"
					+ "(select DISTINCT meterno from meter_data.dtdetails  where dttpid='" + dtide + "')";

			String qry3 = "UPDATE meter_data.meter_inventory  SET meter_status='DELETED' where  meterno IN\n"
					+ "(select DISTINCT meterno from meter_data.dtdetails  where dttpid='" + dtide + "');";

			// System.out.println(qry1);
			List list = null;
			try {

				int i = getCustomEntityManager("postgresMdas").createNativeQuery(qry1).executeUpdate();
				int j = getCustomEntityManager("postgresMdas").createNativeQuery(qry2).executeUpdate();
				int k = getCustomEntityManager("postgresMdas").createNativeQuery(qry3).executeUpdate();
				// System.out.println(i);

			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
			return "Deleted Successfully";
		} else {
			return "This DT Meter Containing Consumers ";
		}
	}

	@Override
	public List<?> getDistinctCircle() {
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT circle from meter_data.master_main";
			list = postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getDTdataByfeederNames(String fdrname) {
		List<?> list = new ArrayList<>();
		String qry = "";
		try {

			qry = "SELECT DISTINCT dtname FROM meter_data.dtdetails WHERE parentid='" + fdrname + "' ORDER BY dtname";
			// System.out.println(qry);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<?> getDivByCircle(String circle, ModelMap model) {
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT division from meter_data.master_main WHERE circle LIKE '" + circle + "'";
			// System.err.println(sql);
			list = postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getSubdivByDivByCircle(String circle, String division, ModelMap model) {
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT subdivision from meter_data.master_main WHERE circle LIKE '" + circle
					+ "' and division like '" + division + "'";
			// System.err.println(sql);
			list = postgresMdas.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public HashMap<String, String> getALLLocationData(String sitecode) {
		HashMap<String, String> h = new HashMap<>();
		String qry = "";
		try {

			qry = "SELECT zone,circle,division,subdivision FROM meter_data.amilocation WHERE sitecode='" + sitecode
					+ "' GROUP BY zone,circle,division,subdivision";
			System.out.println(qry);
			Object[] list = (Object[]) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();

			h.put("ZONE", list[0] + "");
			h.put("CIRCLE", list[1] + "");
			h.put("DIVISION", list[2] + "");
			h.put("SUBDIVISION", list[3] + "");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return h;
	}

	@Override
	public Object getDTIdNamenameBySubdiv(String parentid, String sdoCode) {
		List<?> list = new ArrayList();
		try {
			// String sql = "SELECT DISTINCT dt_id||'-'||dtname from meter_data.dtdetails
			// where parent_substation= '"+parentid+"' and deleted = '0' ";
			String sql = "SELECT DISTINCT dt_id || '-' || dtname FROM meter_data.dtdetails WHERE parent_substation in( SELECT sstp_id FROM meter_data.substation_details WHERE ss_id like '"
					+ parentid
					+ "' AND office_id in ( SELECT sitecode FROM meter_data.amilocation WHERE subdivision like '"
					+ sdoCode + "') ) AND (deleted = '0' or deleted is null)";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Object getDTIdNamenameBySubdivandSubs(String parentid, String officeId) {
		List<?> list = new ArrayList();
		try {
			String sql = "SELECT DISTINCT dt_id||'-'||dtname from meter_data.dtdetails where parent_substation= '"
					+ parentid + "' officeid ='" + officeId + "'  and deleted = '0'  ";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int updateConsumption(String dtid, String consumptionperc) {
		int count = 0;
		try {

			String qry = "UPDATE meter_data.dtdetails SET consumptionperc='" + consumptionperc + "' WHERE dt_id='"
					+ dtid + "' ";
			count = getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

	@Override
	public List<?> getDtHealthRep(String zone, String monthAlt, String circle, String town) {
		List<?> list = null;
		try {
			/*
			 * "select a.*,b.* from (\n" +
			 * "SELECT count(dt.*) as totalcount,ami.subdivision,ami.town_ipds,dt.officeid from meter_data.dtdetails dt, meter_data.amilocation ami,meter_data.substation_details c\n"
			 * + "WHERE ami.circle LIKE '"+circle+"' and ami.division like '"
			 * +division+"' and ami.subdivision like '"+subdiv+"'  and ami.town_ipds like '"
			 * +town+"'  and (dt.crossdt IS NULL OR dt.crossdt =0) and dt.officeid=ami.sitecode AND dt.parent_substation= c.sstp_id\n"
			 * + "GROUP BY ami.subdivision, dt.officeid,ami.town_ipds \n" + ")a,\n" +
			 * "(SELECT COUNT(CASE WHEN overload ='t' THEN 1 END) as overload ,\n" +
			 * "COUNT(CASE WHEN underload ='t' THEN 1 END) as underload ,\n" +
			 * "COUNT(CASE WHEN unbalance ='t' THEN 1 END) as unbalance ,month_year,\n" +
			 * "cast(office_id as text )  FROM meter_data.dt_health_rpt B WHERE cast(office_id as text ) in(SELECT cast(sitecode as TEXT) FROM meter_data.amilocation WHERE circle LIKE '"
			 * +circle+"' and division like '"+division+"' and subdivision like '"
			 * +subdiv+"'  and town_ipds like '"+town+"' )  and month_year='"
			 * +monthAlt+"' GROUP BY office_id,month_year)b \n" +
			 * "where cast(a.officeid as text )=b.office_id";
			 */
//			String sql = "select a.*,b.* from (\r\n" + 
//					"					SELECT ami.circle,ami.town_ipds,dt.officeid,ami.zone from meter_data.dtdetails dt, meter_data.amilocation ami,meter_data.master_main mm\r\n" + 
//					"					WHERE ami.zone LIKE '"+zone+"' and ami.circle LIKE '"+circle+"'\r\n" + 
//					"					 and ami.tp_towncode like '"+town+"'  and (dt.crossdt IS NULL OR dt.crossdt =0) and dt.officeid=ami.sitecode  and dt.meterno=mm.mtrno and  ami.tp_towncode=mm.town_code\r\n" + 
//					"					GROUP BY ami.circle, dt.officeid,ami.town_ipds,ami.zone )a,\r\n" + 
//					"					(SELECT COUNT(CASE WHEN overload ='t' THEN 1 END) as overload ,\r\n" + 
//					"					COUNT(CASE WHEN underload ='t' THEN 1 END) as underload ,Count(tp_dt_id) as dtCount,\r\n" + 
//					"					COUNT(CASE WHEN unbalance ='t' THEN 1 END) as unbalance ,month_year,\r\n" + 
//					"					cast(office_id as text ),tp_town_code  FROM meter_data.dt_health_rpt B WHERE cast(office_id as text ) in(SELECT cast(sitecode as TEXT) FROM meter_data.amilocation WHERE zone LIKE '"+zone+"' and circle LIKE '"+circle+"' and tp_towncode like '"+town+"' )  and month_year='"+monthAlt+"' GROUP BY office_id,month_year,tp_town_code)b \r\n" + 
//					"					where cast(a.officeid as text )=b.office_id";
			
//			String sql = "select DISTINCT ON (b.tp_town_code) a.*,b.* from (\r\n" + 
//					"					SELECT DISTINCT ON (ami.town_ipds) ami.circle,ami.town_ipds,dt.officeid,ami.zone from meter_data.dtdetails dt, meter_data.amilocation ami,meter_data.master_main mm\r\n" + 
//					"					WHERE ami.zone LIKE '"+zone+"' and ami.circle LIKE '"+circle+"'\r\n" + 
//					"					 and ami.tp_towncode like '"+town+"'  and (dt.crossdt IS NULL OR dt.crossdt =0) and dt.officeid=ami.sitecode  and dt.meterno=mm.mtrno and  ami.tp_towncode=mm.town_code\r\n" + 
//					"					GROUP BY ami.circle, dt.officeid,ami.town_ipds,ami.zone )a,\r\n" + 
//					"					(SELECT DISTINCT ON (tp_town_code) COUNT(CASE WHEN overload ='t' THEN 1 END) as overload ,\r\n" + 
//					"					COUNT(CASE WHEN underload ='t' THEN 1 END) as underload ,Count(tp_dt_id) as dtCount,\r\n" + 
//					"					COUNT(CASE WHEN unbalance ='t' THEN 1 END) as unbalance ,month_year,\r\n" + 
//					"					cast(office_id as text ),tp_town_code  FROM meter_data.dt_health_rpt B WHERE cast(office_id as text ) in(SELECT cast(sitecode as TEXT) FROM meter_data.amilocation WHERE zone LIKE '"+zone+"' and circle LIKE '"+circle+"' and tp_towncode like '"+town+"' )  and month_year='"+monthAlt+"' GROUP BY office_id,month_year,tp_town_code)b \r\n" + 
//					"					where cast(a.officeid as text )=b.office_id\r\n" + 
//					"";
			
			String sql = "select distinct on (tp_town_code)a.circle,a.town_ipds,a.officeid,a.zone,b.* from (\r\n" + 
					"					SELECT ami.circle,ami.town_ipds,dt.officeid,ami.zone,ami.tp_towncode from meter_data.dtdetails dt, meter_data.amilocation ami,meter_data.master_main mm\r\n" + 
					"					WHERE ami.zone LIKE '"+zone+"' and ami.circle LIKE '"+circle+"'\r\n" + 
					"					 and ami.tp_towncode like '"+town+"'  and (dt.crossdt IS NULL OR dt.crossdt =0)  and dt.meterno=mm.mtrno and  ami.tp_towncode=mm.town_code\r\n" + 
					"					GROUP BY ami.circle, dt.officeid,ami.town_ipds,ami.zone,ami.tp_towncode )a,\r\n" + 
					"					(SELECT COUNT(CASE WHEN overload ='t' THEN 1 END) as overload ,\r\n" + 
					"					COUNT(CASE WHEN underload ='t' THEN 1 END) as underload ,Count(tp_dt_id) as dtCount,\r\n" + 
					"					COUNT(CASE WHEN unbalance ='t' THEN 1 END) as unbalance ,month_year,tp_town_code  FROM meter_data.dt_health_rpt B WHERE tp_town_code in(SELECT tp_towncode FROM meter_data.amilocation WHERE zone LIKE '"+zone+"' and circle LIKE '"+circle+"' and tp_towncode like '"+town+"' )  and month_year='"+monthAlt+"' GROUP BY month_year,tp_town_code)b where a.tp_towncode=b.tp_town_code ";
			
			System.err.println(sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Object getMaxRdngmnth() {
		Object res = null;
		String sql = " select max(month_year) from meter_data.dt_health_rpt";
		res = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getSingleResult();
		return res;
	}

	@Override
	public List<?> getNtwrkDetails(String ofcid, String subdiv, String month, ModelMap model) {
		List<?> list = null;
		try {

			String sql = "SELECT A.subdivision,A.parent_substation,A.parent_feeder,A.dtname,A.dt_id,A.dtcapacity,A.meterno,A.crossdt, B.* FROM meter_data.dtdetails A join  meter_data.dt_health_rpt B on A.meterno=B.meter_sr_number where A.officeid=B.office_id and \n"
					+ "office_id in \n" + "(\n" + "SELECT officeid FROM meter_data.dtdetails WHERE  subdivision='"
					+ subdiv + "' \n" + ")  and month_year='" + month + "' and office_id='" + ofcid
					+ "'  GROUP BY office_id,officeid,crossdt,subdivision,dtname,dtcapacity,parent_substation,parent_feeder,meterno,A.dt_id";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			// System.out.println("qry is---"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<?> getoverloadedDetails(String officeid, String subdiv, String monthAlt, ModelMap model, String town,String towncode,String zone,String circle) {
		List<?> list = null;
		try {

			/*
			 * String
			 * sql="SELECT B.officeid,B.dtname,B.dttpid,B.dtcapacity,B.meterno,B.crossdt FROM\n"
			 * +
			 * "(SELECT * FROM meter_data.dt_health_rpt WHERE overload='t' AND office_id='"
			 * +officeid+"' AND month_year='"+monthAlt+"' )A,\n" +
			 * "(SELECT * FROM meter_data.dtdetails)B WHERE A.office_id=cast(B.officeid as VARCHAR) AND officeid='"
			 * +officeid+"' AND A.tp_dt_id=B.dttpid";
			 */

			String sql = " select b.tp_dt_id,a.circle,a.division,a.town_name,\r\n" + 
					"string_agg(distinct fdrname,',' ORDER BY fdrname) as fdrname,\r\n" + 
					"string_agg(distinct mtrno,',' ORDER BY mtrno)as metrno,\r\n" + 
					"string_agg(distinct dtcapacity ,',' ORDER BY dtcapacity) as dtcapacity,a.kva\r\n" + 
					"from (\r\n" + 
					"					SELECT distinct dt.dttpid,mm.circle,mm.division,mm.fdrname,mm.mtrno,mm.location_id, ami.town_name,dt.officeid,CAST (round(dt.dtcapacity,0) AS VARCHAR) as dtcapacity,max(kva) as kva from meter_data.dtdetails dt, meter_data.town_master ami,meter_data.master_main mm,meter_data.load_survey_dt cc\r\n" + 
					"					WHERE mm.zone LIKE '"+zone+"' and mm.circle LIKE '"+circle+"'\r\n" + 
					"					 and ami.towncode like '"+towncode+"'  and (dt.crossdt IS NULL OR dt.crossdt =0)  and dt.meterno=mm.mtrno and  ami.towncode=mm.town_code and cc.dttpid=dt.dttpid and cc.billmonth = '"+monthAlt+"'\r\n" + 
					"					GROUP BY mm.circle,mm.division,mm.fdrname, dt.officeid,ami.town_name,mm.mtrno,mm.location_id,dt.dtcapacity,dt.dttpid)a right jOIN\r\n" + 
					"					(SELECT tp_dt_id,(CASE WHEN overload ='t' THEN 1 else 0 END) as overload\r\n" + 
					"  FROM meter_data.dt_health_rpt B WHERE tp_dt_id in(SELECT location_id FROM meter_data.master_main WHERE zone LIKE '"+zone+"' and circle LIKE '"+circle+"' and town_code like '"+towncode+"' )  and month_year='"+monthAlt+"' and overload='1' GROUP BY overload,tp_dt_id)b on (a.dttpid=b.tp_dt_id) \r\n" + 
					"GROUP BY b.tp_dt_id,a.circle,a.division,a.town_name,a.kva";

			/*
			 * "Select distinct (firstqry.*),max(kva),min(kva) from \n" +
			 * "(SELECT B.officeid, string_agg(dtname,',' ORDER BY B.dtname) dtname ,\n" +
			 * "B.dttpid,\n" +
			 * "string_agg(CAST(dtcapacity AS VARCHAR),',' ORDER BY B.dtcapacity) dtcapacity,\n"
			 * + "string_agg(meterno,',' ORDER BY B.meterno) meterno FROM\n" +
			 * "(SELECT * FROM meter_data.dt_health_rpt WHERE overload='t' AND office_id='"
			 * +officeid+"' AND month_year='"+monthAlt+"' )A,\n" +
			 * "(SELECT * FROM meter_data.dtdetails)B WHERE A.office_id=cast(B.officeid as VARCHAR) AND officeid='"
			 * +officeid+"' AND A.tp_dt_id=B.dttpid group by officeid,dttpid)firstqry " +
			 * "left join meter_data.load_survey_dt ldt on ldt.dttpid = firstqry.dttpid GROUP BY officeid,dtname,firstqry.dttpid,dtcapacity,meterno order by dttpid"
			 * ;
			 */
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			 System.out.println("qry is---"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<?> getunderloadedDetails(String officeid, String subdiv, String monthAlt, ModelMap model, String town,String zone,String circle,String towncode) {
		List<?> list = null;
		
		try {
			String sql = "select b.tp_dt_id,a.circle,a.division,a.town_name,\r\n" + 
					"string_agg(distinct fdrname,',' ORDER BY fdrname) as fdrname,\r\n" + 
					"string_agg(distinct mtrno,',' ORDER BY mtrno)as metrno,\r\n" + 
					"string_agg(distinct dtcapacity ,',' ORDER BY dtcapacity) as dtcapacity,a.kva\r\n" + 
					"from (\r\n" + 
					"					SELECT distinct dt.dttpid,mm.circle,mm.division,mm.fdrname,mm.mtrno,mm.location_id, ami.town_name,dt.officeid,CAST (round(dt.dtcapacity,0) AS VARCHAR) as dtcapacity,max(kva) as kva from meter_data.dtdetails dt, meter_data.town_master ami,meter_data.master_main mm, meter_data.load_survey_dt cc\r\n" + 
					"					WHERE mm.zone LIKE '"+circle+"' and mm.circle LIKE '"+zone+"'\r\n" + 
					"					 and ami.towncode like '"+towncode+"'  and (dt.crossdt IS NULL OR dt.crossdt =0)  and dt.meterno=mm.mtrno and  ami.towncode=mm.town_code and cc.dttpid=dt.dttpid and cc.billmonth = '"+monthAlt+"'\r\n" + 
					"					GROUP BY mm.circle,mm.division,mm.fdrname, dt.officeid,ami.town_name,mm.mtrno,mm.location_id,dt.dtcapacity,dt.dttpid)a right jOIN\r\n" + 
					"					(SELECT tp_dt_id,(CASE WHEN underload ='t' THEN 1 END) as underload\r\n" + 
					"  FROM meter_data.dt_health_rpt B WHERE tp_dt_id in(SELECT location_id FROM meter_data.master_main WHERE zone LIKE '"+circle+"' and circle LIKE '"+zone+"' and town_code like '"+towncode+"' )  and month_year='"+monthAlt+"' and underload='1' GROUP BY underload,tp_dt_id)b on (a.dttpid=b.tp_dt_id) \r\n" + 
					"GROUP BY b.tp_dt_id,a.circle,a.division,a.town_name,a.kva";

			/*
			 * String sql="Select distinct (firstqry.*),max(kva),min(kva) from \n" +
			 * "(SELECT B.officeid, string_agg(dtname,',' ORDER BY B.dtname) dtname ,\n" +
			 * "B.dttpid,\n" +
			 * "string_agg(CAST(dtcapacity AS VARCHAR),',' ORDER BY B.dtcapacity) dtcapacity,\n"
			 * + "string_agg(meterno,',' ORDER BY B.meterno) meterno FROM\n" +
			 * "(SELECT * FROM meter_data.dt_health_rpt WHERE underload='t' AND office_id='"
			 * +officeid+"' AND month_year='"+monthAlt+"' )A,\n" +
			 * "(SELECT * FROM meter_data.dtdetails)B WHERE A.office_id=cast(B.officeid as VARCHAR) AND officeid='"
			 * +officeid+"' AND A.tp_dt_id=B.dttpid group by officeid,dttpid)firstqry " +
			 * "left join meter_data.load_survey_dt ldt on ldt.dttpid = firstqry.dttpid GROUP BY officeid,dtname,firstqry.dttpid,dtcapacity,meterno order by dttpid"
			 * ;
			 */

			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			System.out.println("qry is---"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<?> getunbalancedDetails(String officeid, String subdiv, String monthAlt, ModelMap model, String town,String zone,String circle,String towncode) {
		List<?> list = null;
		try {
			String sql = " select b.tp_dt_id,a.circle,a.division,a.town_name,\r\n" + 
					"string_agg(distinct fdrname,',' ORDER BY fdrname) as fdrname,\r\n" + 
					"string_agg(distinct mtrno,',' ORDER BY mtrno)as metrno,\r\n" + 
					"string_agg(distinct dtcapacity ,',' ORDER BY dtcapacity) as dtcapacity,a.kva\r\n" + 
					"from (\r\n" + 
					"					SELECT distinct dt.dttpid,mm.circle,mm.division,mm.fdrname,mm.mtrno,mm.location_id, ami.town_name,dt.officeid,CAST (round(dt.dtcapacity,0) AS VARCHAR) as dtcapacity,max(kva) as kva from meter_data.dtdetails dt, meter_data.town_master ami,meter_data.master_main mm, meter_data.load_survey_dt cc \r\n" + 
					"					WHERE mm.zone LIKE '"+zone+"' and mm.circle LIKE '"+circle+"'\r\n" + 
					"					 and ami.towncode like '"+towncode+"'  and (dt.crossdt IS NULL OR dt.crossdt =0)  and dt.meterno=mm.mtrno and  ami.towncode=mm.town_code and cc.dttpid=dt.dttpid\r\n" + 
					"					GROUP BY mm.circle,mm.division,mm.fdrname, dt.officeid,ami.town_name,mm.mtrno,mm.location_id,dt.dtcapacity,dt.dttpid)a right jOIN\r\n" + 
					"					(SELECT tp_dt_id,(CASE WHEN unbalance ='t' THEN 1 END) as unbalance\r\n" + 
					"  FROM meter_data.dt_health_rpt B WHERE tp_dt_id in(SELECT location_id FROM meter_data.master_main WHERE zone LIKE '"+zone+"' and circle LIKE '"+circle+"' and town_code like '"+towncode+"' )  and month_year='"+monthAlt+"' and unbalance='1' GROUP BY unbalance,tp_dt_id)b on (a.dttpid=b.tp_dt_id) \r\n" + 
					"GROUP BY b.tp_dt_id,a.circle,a.division,a.town_name,a.kva\r\n" + 
					"	";

			/*
			 * String
			 * sql="SELECT B.officeid,B.dtname,B.dttpid,B.dtcapacity,B.meterno,B.crossdt FROM\n"
			 * +
			 * "(SELECT * FROM meter_data.dt_health_rpt WHERE unbalance='t' AND office_id='"
			 * +officeid+"' AND month_year='"+monthAlt+"' )A,\n" +
			 * "(SELECT * FROM meter_data.dtdetails)B WHERE A.office_id=cast(B.officeid as VARCHAR) AND officeid='"
			 * +officeid+"' AND A.tp_dt_id=B.dttpid";
			 */

			/*
			 * String sql="Select distinct (firstqry.*),max(kva),min(kva) from \n" +
			 * "(SELECT B.officeid, string_agg(dtname,',' ORDER BY B.dtname) dtname ,\n" +
			 * "B.dttpid,\n" +
			 * "string_agg(CAST(dtcapacity AS VARCHAR),',' ORDER BY B.dtcapacity) dtcapacity,\n"
			 * + "string_agg(meterno,',' ORDER BY B.meterno) meterno FROM\n" +
			 * "(SELECT * FROM meter_data.dt_health_rpt WHERE unbalance='t' AND office_id='"
			 * +officeid+"' AND month_year='"+monthAlt+"' )A,\n" +
			 * "(SELECT * FROM meter_data.dtdetails)B WHERE A.office_id=cast(B.officeid as VARCHAR) AND officeid='"
			 * +officeid+"' AND A.tp_dt_id=B.dttpid group by officeid,dttpid)firstqry " +
			 * "left join meter_data.load_survey_dt ldt on ldt.dttpid = firstqry.dttpid GROUP BY officeid,dtname,firstqry.dttpid,dtcapacity,meterno order by dttpid"
			 * ;
			 */

			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			System.out.println("qry is---"+sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> gettotalDTDetails(String officeid, String subdiv, String monthAlt, ModelMap model, String town,
			String otown,String zone,String circle,String towncode,String circlecode,String zonecode) {
		List<?> list = null;
		
	//	System.out.println("towncode----------"+towncode);
		try {
			String sql = "select distinct  z.* from\r\n" + 
					"(select a.location_id,a.circle,a.division,a.town_name,\r\n" + 
					"string_agg(distinct fdrname,',' ORDER BY fdrname) as fdrname,\r\n" + 
					"string_agg(distinct mtrno,',' ORDER BY mtrno)as metrno,\r\n" + 
					"string_agg(distinct dtcapacity ,',' ORDER BY dtcapacity) as dtcapacity,a.kva\r\n" + 
					"from (\r\n" + 
					"					SELECT distinct mm.circle,mm.division,mm.fdrname,mm.mtrno,mm.location_id, ami.town_name,dt.officeid,CAST (round(dt.dtcapacity,0) AS VARCHAR) as dtcapacity,max(kva) as kva from meter_data.dtdetails dt, meter_data.town_master ami,meter_data.master_main mm,meter_data.load_survey_dt cc\r\n" + 
					"					WHERE mm.zone LIKE '"+zone+"' and mm.circle LIKE '"+circlecode+"'\r\n" + 
					"					 and ami.towncode like '"+towncode+"'  and (dt.crossdt IS NULL OR dt.crossdt =0)  and dt.meterno=mm.mtrno and  ami.towncode=mm.town_code and cc.dttpid=dt.dttpid\r\n" + 
					"					GROUP BY mm.circle,mm.division,mm.fdrname, dt.officeid,ami.town_name,mm.mtrno,mm.location_id,dt.dtcapacity)a right join \r\n" + 
					"					(SELECT \r\n" + 
					"					(tp_dt_id) as dtCount\r\n" + 
					"  FROM meter_data.dt_health_rpt B WHERE tp_dt_id in(SELECT location_id FROM meter_data.master_main WHERE zone LIKE '"+zone+"' and circle LIKE '"+circlecode+"' and town_code like '"+towncode+"' )  and month_year='"+monthAlt+"' GROUP BY month_year,tp_dt_id)b on b.dtCount=a.location_id\r\n" + 
					"GROUP BY a.location_id,a.circle,a.division,a.town_name,a.kva)z where location_id is not null and location_id != ''";
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

			System.out.println("qry is---"+sql);

			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getdtdetailsBytownCode(String region,String frdId, String townCode, String circle) {
		List<?> list = null;
		String qry = "";
		try {
//				qry = "select id,dttype,dtname,dtcapacity,phase,parentid,dttpid,\n" +
//						"tpparentid,meterno,metermanufacture,consumptionperc,dt_id from meter_data.dtdetails \n" +
//						"where deleted !=1 and parent_substation LIKE '"+ssid+"' and tp_town_code LIKE '"+townCode+"' and meterno is not NULL;";

//			qry = "SELECT DISTINCT on (d.dttpid)d.dttpid as dttpid,d.dttype, d.dtname,"
//					+ "string_agg(CAST(round(d.dtcapacity,0) as varchar), ',') as dtcapacity,\n" + "d.phase,d.tpparentid,\n"
//					+ "string_agg(d.meterno, ',') as meterno,\n"
//					+ "string_agg(d.metermanufacture, ',') as metermanufacture,d.mf,d.tp_town_code, m.circle,t.town_name,m.latitude,m.longitude,m.fdrname,d.dt_hv_voltage,d.dt_hv_amp,d.dt_lv_voltage,d.dt_lv_amp,m.zone\n"
//					+ "from meter_data.dtdetails d, meter_data.master_main m,meter_data.town_master t \n"
//					+ "where d.meterno=m.mtrno\r\n"
//					+ "and t.towncode=d.tp_town_code and d.deleted !=1 and m.zone LIKE '"+region+"' and d.tpparentid LIKE '" + frdId
//					+ "' and d.tp_town_code LIKE '" + townCode + "' and circle like '" + circle
//					+ "' and d.meterno is not NULL and d.meterno <> '' \n"
//					+ "GROUP BY d.dttpid,d.dttype,d.phase,d.tpparentid,d.metermanufacture,d.mf,d.tp_town_code, m.circle,t.town_name,m.latitude,m.longitude,m.fdrname,d.dtname,d.dt_hv_voltage,d.dt_hv_amp,d.dt_lv_voltage,d.dt_lv_amp,m.zone";
			
			
			
		
			
			qry="SELECT distinct ON(a.dttpid)a.dttpid as dttpid,b.dttype,b.dtname,b.dtcapacity,b.phase,b.tpparentid,split_part(a.meterno, ',', 1) as meterno1,split_part(a.meterno, ',', 2) as meterno2,a.metermanufacture,b.mf,b.tp_town_code,b.circle,b.town_name,b.latitude,b.longitude,b.fdrname,b.dt_hv_voltage,b.dt_hv_amp,b.dt_lv_voltage,b.dt_lv_amp,b.zone FROM\r\n" + 
					"(SELECT DISTINCT d.dttpid,\r\n" + 
					"string_agg(DISTINCT d.meterno, ',') as meterno,string_agg(DISTINCT d.metermanufacture, ',') as metermanufacture\r\n" + 
					"\r\n" + 
					" FROM  meter_data.dtdetails d LEFT JOIN meter_data.amilocation am ON(d.tp_town_code=am.tp_towncode) LEFT JOIN meter_data.master_main m ON(d.meterno=m.mtrno)  WHERE  m.zone LIKE '"+region+"' and d.tpparentid LIKE '"+frdId+"' and d.tp_town_code LIKE '"+townCode+"' and m.circle like '"+circle+"' and d.meterno is not NULL and d.meterno <> '' GROUP BY 1)a LEFT JOIN \r\n" + 
					" \r\n" + 
					" (select distinct string_agg(d.meterno, ',') as meterno,d.dttpid,d.dttype,d.dtname,string_agg(CAST(round(d.dtcapacity,0) as varchar), ',') as dtcapacity,d.mf,d.tp_town_code,\r\n" + 
					"d.phase,d.tpparentid,m.circle,t.town_name,m.latitude,m.longitude,m.fdrname,d.dt_hv_voltage,d.dt_hv_amp,d.dt_lv_voltage,d.dt_lv_amp,m.zone from meter_data.dtdetails d LEFT JOIN meter_data.master_main m ON(d.meterno=m.mtrno) LEFT JOIN meter_data.town_master t ON(t.towncode=d.tp_town_code)  WHERE  m.zone LIKE '"+region+"' and d.tpparentid LIKE '"+frdId+"' and d.tp_town_code LIKE '"+townCode+"' and m.circle like '"+circle+"' and d.meterno is not NULL and d.meterno <> ''  GROUP BY d.dttype,d.dttpid,d.dtname,d.phase,d.tpparentid,d.mf,d.tp_town_code,m.circle,m.latitude,m.longitude,m.fdrname,d.dt_hv_voltage,d.dt_hv_amp,d.dt_lv_voltage,d.dt_lv_amp,m.zone,t.town_name)b ON(a.dttpid=b.dttpid)\r\n" + 
					"";
			System.out.println("Query= "+qry);
			// list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			list = postgresMdas.createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DtDetailsEntity> getDtDetailsByDttpid(String dttpid, String tp_towncode) {
		List<DtDetailsEntity> result = new ArrayList<DtDetailsEntity>();
		try {
			result = getCustomEntityManager("postgresMdas").createNamedQuery("dtdetails.getDtDetailsByDttpid")
					.setParameter("dttpid", dttpid).setParameter("tp_towncode", tp_towncode).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DtDetailsEntity> getDtDetailsByTownFdrDtId(String dttpid, String tp_towncode, String frdId) {
		List<DtDetailsEntity> result = new ArrayList<DtDetailsEntity>();
		try {
			result = getCustomEntityManager("postgresMdas")
					.createNamedQuery("DtDetailsEntity.getDtDetailsByTownFdrDtId").setParameter("dttpid", dttpid)
					.setParameter("tpparentid", frdId).setParameter("tp_town_code", tp_towncode).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<DtDetailsEntity> getDtDetailsByDttpid(String dttpid) {
		List<DtDetailsEntity> result = new ArrayList<DtDetailsEntity>();
		try {
			result = getCustomEntityManager("postgresMdas").createNamedQuery("dtdetails.getDtDetailsBydt_tpid")
					.setParameter("dttpid", dttpid).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public DtDetailsEntity getDtDetailsByMeter(String meterid) {
		DtDetailsEntity result = new DtDetailsEntity();
		try {
			result = getCustomEntityManager("postgresMdas")
					.createNamedQuery("DtDetailsEntity.getDtByMeterno", DtDetailsEntity.class)
					.setParameter("meterno", meterid).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DtDetailsEntity> getDtDetailsByMeterno(String meterno) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("DtDetailsEntity.getDtByMeterno")
					.setParameter("meterno", meterno).getResultList();
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void getDtDetailsPdf(HttpServletRequest request, HttpServletResponse response, String circle, String town,
			String feeder) {
		List<Object[]> DTData = null;
		String cirname = "", townname = "", townname1 = "", substa = "", fdr = "", fdr1 = "";

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
		LocalDateTime sysdatetime = LocalDateTime.now();

		try {
//			String qry="select distinct ami.circle,ami.town_ipds,ssd.ss_name from meter_data.feederdetails fdr,meter_data.amilocation ami,meter_data.substation_details ssd \n" +
//					"where ssd.parent_town=ami.tp_towncode and fdr.tpparentid = ssd.sstp_id and ami.circle ='"+circle+"' and fdr.tp_town_code='"+town+"' and fdr.tpparentid='"+substation+"'";
//			DTData=postgresMdas.createNativeQuery(qry).getResultList();
//			
//			if(DTData.size()>0){
//				cirname =(String)DTData.get(0)[0];
//				townname=(String)DTData.get(0)[1];
//				substa=(String)DTData.get(0)[2];		
//			}
//			else{
//				cirname="";
//				townname="";
//				substa="";
//			}

			if (town.equalsIgnoreCase("%") && feeder.equalsIgnoreCase("%")) {
				fdr = "ALL";
				townname = "ALL";
			} else {
				String qry = "select distinct tw.town_name,fdr.feedername from meter_data.town_master tw,meter_data.feederdetails fdr \r\n"
						+ "where tw.towncode like '" + town + "' and fdr.tp_fdr_id like '" + feeder
						+ "' and tw.towncode=fdr.tp_town_code";
				// System.out.println(qry);
				DTData = postgresMdas.createNativeQuery(qry).getResultList();
				if (DTData.size() > 0) {
					townname1 = (String) DTData.get(0)[0];
					fdr1 = (String) DTData.get(0)[1];
				}
			}

			if (circle.equalsIgnoreCase("%")) {
				cirname = "ALL";
			} else {
				cirname = circle;
			}
			if (town.equalsIgnoreCase("%")) {
				townname = "ALL";
			} else {
				townname = town + "-" + townname1;
			}
			if (feeder.equalsIgnoreCase("%")) {
				fdr = "ALL";
			} else {
				fdr = fdr1;
			}

			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
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
			pstart.add(new Phrase("BCITS PRIVATE LTD", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("DT DETAILS ", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(3);
			header.setWidths(new int[] { 1, 1, 1 });
			header.setWidthPercentage(100);

			PdfPCell headerCell = null;
			headerCell = new PdfPCell(
					new Phrase("CIRCLE :" + cirname, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("TOWN :" + townname, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(new Phrase("FEEDER :" + fdr, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("CurrentDate&Time :" + sysdatetime, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));

			document.add(header);

			String query = "";
			List<Object[]> DtData1 = null;
//			query="SELECT DISTINCT dttpid,dttype,string_agg(dtname, ',') as dtname,string_agg(CAST(dtcapacity as varchar), ',') as dtcapacity,phase,tpparentid,\n" +
//					"string_agg(meterno, ',') as meterno,string_agg(metermanufacture, ',') as metermanufacture,round(mf,0)as mf,tp_town_code\n" +
//					"from meter_data.dtdetails \n" +
//					"where deleted !=1 and parent_substation LIKE '"+substation+"' and tp_town_code LIKE '"+town+"' and meterno is not NULL\n" +
//					"GROUP BY dttpid,dttype,phase,tpparentid,metermanufacture,mf,tp_town_code";

//			query = "SELECT DISTINCT d.dttpid,d.dttype,\n" + "string_agg(d.dtname, ',') as dtname,\n"
//					+ "string_agg(CAST(d.dtcapacity as varchar), ',') as dtcapacity,\n" + "d.phase,d.tpparentid,\n"
//					+ "string_agg(d.meterno, ',') as meterno,\n"
//					+ "string_agg(d.metermanufacture, ',') as metermanufacture,d.mf,d.tp_town_code, m.circle,t.town_name,m.latitude,m.longitude\n"
//					+ "from meter_data.dtdetails d, meter_data.master_main m,meter_data.town_master t \n"
//					+ "where d.meterno=m.mtrno\n"
//					+ "and t.towncode=d.tp_town_code and d.deleted !=1 and d.tpparentid LIKE '" + feeder
//					+ "' and d.tp_town_code LIKE '" + town + "' and d.meterno is not NULL and d.meterno <> '' \n"
//					+ "GROUP BY d.dttpid,d.dttype,d.phase,d.tpparentid,d.metermanufacture,d.mf,d.tp_town_code, m.circle,t.town_name,m.latitude,m.longitude";

			
			query = "SELECT DISTINCT d.dttpid,d.dttype,\n" + "string_agg(d.dtname, ',') as dtname,\n"
					+ "string_agg(CAST(d.dtcapacity as varchar), ',') as dtcapacity,\n" + "d.phase,d.tpparentid,\n"
					+ "string_agg(d.meterno, ',') as meterno,\n"
					+ "string_agg(d.metermanufacture, ',') as metermanufacture,d.mf,d.tp_town_code,m.circle,t.town_name,m.latitude,m.longitude,m.fdrname,m.zone\n"
					+ "from meter_data.dtdetails d, meter_data.master_main m,meter_data.town_master t \n"
					+ "where d.meterno=m.mtrno\r\n"
					+ "and t.towncode=d.tp_town_code and d.deleted !=1 and d.tpparentid LIKE '" + feeder
					+ "' and d.tp_town_code LIKE '" + town + "' and circle like '" + circle
					+ "' and d.meterno is not NULL and d.meterno <> '' \n"
					+ "GROUP BY d.dttpid,d.dttype,d.phase,d.tpparentid,d.metermanufacture,d.mf,d.tp_town_code, m.circle,t.town_name,m.latitude,m.longitude,m.fdrname,m.zone";

				System.out.println(query);
			

			DtData1=postgresMdas.createNativeQuery(query).getResultList();
			
			   PdfPTable parameterTable = new PdfPTable(13);
	           parameterTable.setWidths(new int[]{1,2,2,1,1,1,4,1,1,1,2,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("REGION",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("CIRCLE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("TOWN",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FEEDER NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("FEEDER CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("DT CODE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT TYPE",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT NAME",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setRowspan(1);
	          // parameterCell.setColspan(2);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("DT CAPACITY",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
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
	           
	           
	           parameterCell = new PdfPCell(new Phrase("METER NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("MANUFACTURER",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	         
			
			
	           for (int i = 0; i < DtData1.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=DtData1.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			
	            			 parameterCell = new PdfPCell(new Phrase(obj[15]==null?null:obj[15]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
	            			 parameterCell = new PdfPCell(new Phrase(obj[10]==null?null:obj[10]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[9]==null?null:obj[9]+"-"+obj[11]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
							 parameterCell = new PdfPCell(new Phrase(obj[14]==null?null:obj[14]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 
							 parameterCell = new PdfPCell(new Phrase(obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[0]==null?null:obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=DtDetails.pdf");
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
	public List<?> getIndividualDtConsumData1(String circle, String division, String subdivision, String towncode,
			String townname, String dt, String period, String fromdate, String todate) {
		List<?> list = new ArrayList<>();
		try {
//			String sql ="SELECT Y.circle, Y.division, Y.subdivision, X.* \n" +
//					"FROM\n" +
//					"	(\n" +
//					"	SELECT A.tp_town_code, A.dttpid, b.yearmonth,\n" +
//					"	SUM ( b.kwh ) AS kwh,	SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\n" +
//					"	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 4 ) pf,\n" +
//					"	round (AVG( i_r) ) AS ir,round (AVG( i_y) ) AS iy,round (AVG( i_b) ) AS ib,\n" +
//					"	round(AVG ( v_r ),3) AS vr,round(AVG ( v_y ),3) AS vy,round(AVG ( v_b ),3) AS vb,\n" +
//					"	SUM ( frequency ) frqncy \n" +
//					"FROM\n" +
//					"	(\n" +
//					"	SELECT dttpid,meterno,tp_town_code \n" +
//					"FROM meter_data.dtdetails  WHERE\n" +
//					"	dttpid IN \n" +
//					"	( SELECT dttpid \n" +
//					" FROM\n" +
//					"	( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND \n" +
//					"	  meterno IS NOT NULL AND meterno != '' ) b \n" +
//					"	)) A,\n" +
//					"	(\n" +
//					"  SELECT meter_number, to_char( read_time, 'yyyy-MM-dd ' ) AS yearmonth,\n" +
//					"	kwh, kvah, kw, i_r,i_y,i_b,\n" +
//					"	 v_r, v_y,v_b,frequency,\n" +
//					"	(kwh * 2 ) AS kva \n" +
//					"FROM meter_data.load_survey \n" +
//					"WHERE read_time >='"+fromdate+"' and read_time <= '"+todate+"' \n" +
//					"GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd ' ), kwh, frequency,\n" +
//					"	kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva \n" +
//					"	) b \n" +
//					"WHERE 	A.meterno = b.meter_number AND dttpid = '"+dt+"' \n" +
//					"GROUP BY tp_town_code, dttpid, yearmonth ) X,\n" +
//					"	(\n" +
//					"SELECT *  FROM meter_data.amilocation am \n" +
//					"WHERE LOWER ( am.circle ) LIKE LOWER ( '"+circle+"' )) Y \n" +
//					"WHERE Y.tp_towncode = X.tp_town_code";
			/*
			 * String sql = "SELECT Y.circle, Y.division, Y.subdivision, X.* \r\n" +
			 * "FROM\r\n" + "	(\r\n" +
			 * "	SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" +
			 * "	SUM ( b.kwh ) AS kwh,	SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n"
			 * +
			 * "	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 4 ) pf,\r\n"
			 * +
			 * "	round (sum( i_r)/48,3 ) AS ir,round (sum( i_y)/48,3 ) AS iy,round (sum( i_b)/48,3 ) AS ib,\r\n"
			 * +
			 * "	round(AVG ( v_r ),3) AS vr,round(AVG ( v_y ),3) AS vy,round(AVG ( v_b ),3) AS vb,\r\n"
			 * + "	SUM ( frequency ) frqncy \r\n" + "FROM\r\n" + "	(\r\n" +
			 * "	SELECT dttpid,meterno,tp_town_code \r\n" +
			 * "FROM meter_data.dtdetails  WHERE\r\n" + "	dttpid IN \r\n" +
			 * "	( SELECT dttpid \r\n" + " FROM\r\n" +
			 * "	( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND \r\n"
			 * + "	  meterno IS NOT NULL AND meterno != '' ) b \r\n" + "	)) A,\r\n" +
			 * "	(\r\n" +
			 * "  SELECT meter_number, to_char( read_time, 'yyyy-MM-dd ' ) AS yearmonth,\r\n"
			 * +
			 * "	kwh, kvah, kw,round(sum(i_r)/48,3) as i_r,round(sum(i_y)/48,3) as i_y,round(sum(i_b)/48,3) as i_b,\r\n"
			 * + "	 v_r, v_y,v_b,frequency,\r\n" + "	(kwh * 2 ) AS kva \r\n" +
			 * "FROM meter_data.load_survey \r\n" +
			 * "WHERE read_time >='"+fromdate+"' and read_time <= '"+todate+"' \r\n" +
			 * "GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd ' ), kwh, frequency,\r\n"
			 * + "	kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva \r\n" + "	) b \r\n" +
			 * "WHERE 	A.meterno = b.meter_number AND dttpid = '"+dt+"' \r\n" +
			 * "GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + "	(\r\n" +
			 * "SELECT *  FROM meter_data.amilocation am \r\n" +
			 * "WHERE LOWER ( am.circle ) LIKE LOWER ( '"+circle+"' )) Y \r\n" +
			 * "WHERE Y.tp_towncode = X.tp_town_code";
			 */
			
			
			String sql="SELECT Y.circle, Y.division, Y.subdivision, X.* \r\n" + 
					"FROM\r\n" + 
					"	(\r\n" + 
					"	SELECT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" + 
					"	SUM ( b.kwh ) AS kwh,	SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n" + 
					"	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 4 ) pf,\r\n" + 
					"	round (sum( i_r)/48,3 ) AS ir,round (sum( i_y)/48,3 ) AS iy,round (sum( i_b)/48,3 ) AS ib,\r\n" + 
					"	round(AVG ( v_r ),3) AS vr,round(AVG ( v_y ),3) AS vy,round(AVG ( v_b ),3) AS vb,\r\n" + 
					"	SUM ( frequency ) frqncy \r\n" + 
					"FROM\r\n" + 
					"	(\r\n" + 
					"	SELECT dttpid,meterno,tp_town_code \r\n" + 
					"FROM meter_data.dtdetails  WHERE\r\n" + 
					"	dttpid IN \r\n" + 
					"	( SELECT dttpid \r\n" + 
					" FROM\r\n" + 
					"	( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND \r\n" + 
					"	  meterno IS NOT NULL AND meterno != '' ) b \r\n" + 
					"	)) A,\r\n" + 
					"	(\r\n" + 
					"  SELECT distinct  ls.meter_number, to_char(ls.read_time, 'yyyy-MM-dd') AS yearmonth,\r\n" + 
					"	ls.kwh, ls.kvah,ls.kw,round(sum(ls.i_r)/48,3) as i_r,round(sum(ls.i_y)/48,3) as i_y,round(sum(ls.i_b)/48,3) as i_b,\r\n" + 
					"	 ls.v_r, ls.v_y,ls.v_b,ls.frequency,\r\n" + 
					"	(ls.kwh * 2 ) AS kva \r\n" + 
					"FROM meter_data.load_survey ls LEFT JOIN meter_data.master_main mm\r\n" + 
					"ON(ls.meter_number=mm.mtrno) WHERE to_char(ls.read_time,'yyyy-MM-dd') >='"+fromdate+"' and to_char(ls.read_time,'yyyy-MM-dd') <= '"+todate+"' and mm.town_code like '"+towncode+"'\r\n" + 
					"GROUP BY ls.meter_number, to_char(ls.read_time, 'yyyy-MM-dd'),ls.kwh, ls.frequency,\r\n" + 
					"	ls.kvah, ls.kw, ls.i_r, ls.i_b,ls.i_y, ls.v_r, ls.v_y, ls.v_b, ls.kva \r\n" + 
					"	) b \r\n" + 
					"WHERE 	A.meterno = b.meter_number AND dttpid = '"+dt+"' \r\n" + 
					"GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + 
					"	(\r\n" + 
					"SELECT *  FROM meter_data.amilocation am \r\n" + 
					"WHERE LOWER ( am.circle ) LIKE LOWER ( '"+circle+"' )) Y \r\n" + 
					"WHERE Y.tp_towncode = X.tp_town_code";

			System.out.println("datewise qry is----" + sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getIndividualDtConsumData2(String circle, String division, String subdivision, String towncode,
			String townname, String dt, String period, String frommonth, String tomonth) {
		List<?> list = new ArrayList<>();
		try {
			/*String sql ="Select * FROM\n" +
					"(SELECT Y.circle, Y.division, Y.subdivision, X.* \n" +
					"FROM\n" +
					"	(\n" +
					"	SELECT A.tp_town_code, A.dttpid, b.yearmonth,\n" +
					"	SUM ( b.kwh ) AS kwh,	SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\n" +
					"	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 4 ) pf,\n" +
					"	round(sum( i_r)/1440,3 ) AS ir,round (sum( i_y)/1440,3 ) AS iy,round (sum( i_b)/1440,3 ) AS ib,\n" +
					"	round(AVG ( v_r ),3) AS vr,round(AVG ( v_y ),3) AS vy,round(AVG ( v_b ),3) AS vb,\n" +
					"	SUM ( frequency ) frqncy \n" +
					"FROM\n" +
					"	(\n" +
					"	SELECT dttpid,meterno,tp_town_code \n" +
					"FROM meter_data.dtdetails  WHERE\n" +
					"	dttpid IN \n" +
					"	( SELECT dttpid \n" +
					" FROM\n" +
					"	( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND \n" +
					"	  meterno IS NOT NULL AND meterno != '' ) b \n" +
					"	)) A,\n" +
					"	( SELECT meter_number, to_char( read_time, 'yyyyMM' ) AS yearmonth,\n" +
					"	sum(kwh)as kwh, sum(kvah)as kvah, sum(kw)as kw, round(sum(i_r)/1440,3)as i_r,round(sum(i_y)/1440,3)as i_y,round(sum(i_b)/1440,3) as i_b,\n" +
					"	round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b, sum(frequency)as frequency,\n" +
					"	sum((( CASE WHEN kwh = 0 THEN 1 ELSE kwh END ) * 2 )) AS kva \n" +
					"FROM meter_data.load_survey \n" +
					"WHERE to_char(read_time,'yyyyMM') >='"+frommonth+"'and  to_char(read_time,'yyyyMM') <='"+tomonth+"' \n" +
					"GROUP BY meter_number, to_char( read_time, 'yyyyMM' )) b \n" +
					"WHERE 	A.meterno = b.meter_number AND dttpid = '"+dt+"' \n" +
					"GROUP BY tp_town_code, dttpid, yearmonth ) X,\n" +
					"	(\n" +
					"SELECT *  FROM meter_data.amilocation am \n" +
					"WHERE LOWER ( am.circle ) LIKE LOWER ( '"+circle+"' ) ) Y \n" +
					"WHERE Y.tp_towncode = X.tp_town_code)AA,\n" +
					"(\n" +
					"SELECT dttpid,(kwh*2)mdkva,read_time,billmonth from (\n" +
					"SELECT dttpid,kwh,read_time,billmonth,rank() over (partition by dttpid,billmonth ORDER BY kwh desc) as rank from (\n" +
					"SELECT a.dttpid,sum(b.kwh) as kwh,b.read_time,to_char(read_time,'yyyyMM') as billmonth  FROM \n" +
					"(SELECT dttpid,meterno from meter_data.dtdetails where dttpid IS NOT NULL AND dttpid != '' AND \n" +
					"	  meterno IS NOT NULL AND meterno != '' )a,\n" +
					"(SELECT  meter_number,kwh,read_time from meter_data.load_survey WHERE  \n" +
					"to_char(read_time,'yyyyMM') >='"+frommonth+"'\n" +
					"and  to_char(read_time,'yyyyMM') <='"+tomonth+"' )b \n" +
					"WHERE a.meterno=b.meter_number   GROUP  BY b.read_time,a.dttpid,to_char(read_time,'yyyyMM')  ) a )b where rank=1)BB\n" +
					"WHERE AA.yearmonth=BB.billmonth AND AA.dttpid=BB.dttpid";*/
			
			
			String sql="Select DISTINCT * FROM\r\n" + 
					"(SELECT Y.circle, Y.division, Y.subdivision, X.* \r\n" + 
					"FROM\r\n" + 
					"	(\r\n" + 
					"	SELECT DISTINCT A.tp_town_code, A.dttpid, b.yearmonth,\r\n" + 
					"	SUM ( b.kwh ) AS kwh,	SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\r\n" + 
					"	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 4 ) pf,\r\n" + 
					"	round(sum( i_r)/1440,3 ) AS ir,round (sum( i_y)/1440,3 ) AS iy,round (sum( i_b)/1440,3 ) AS ib,\r\n" + 
					"	round(AVG ( v_r ),3) AS vr,round(AVG ( v_y ),3) AS vy,round(AVG ( v_b ),3) AS vb,\r\n" + 
					"	SUM ( frequency ) frqncy \r\n" + 
					"FROM\r\n" + 
					"	(\r\n" + 
					"	SELECT dttpid,meterno,tp_town_code \r\n" + 
					"FROM meter_data.dtdetails  WHERE\r\n" + 
					"	dttpid IN \r\n" + 
					"	( SELECT DISTINCT dttpid \r\n" + 
					" FROM\r\n" + 
					"	( SELECT DISTINCT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND \r\n" + 
					"	  meterno IS NOT NULL AND meterno != '' ) b \r\n" + 
					"	)) A,\r\n" + 
					"	(SELECT DISTINCT  meter_number, to_char( read_time, 'yyyyMM' ) AS yearmonth,\r\n" + 
					"	sum(kwh)as kwh, sum(kvah)as kvah, sum(kw)as kw, round(sum(i_r)/1440,3)as i_r,round(sum(i_y)/1440,3)as i_y,round(sum(i_b)/1440,3) as i_b,\r\n" + 
					"	round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b, sum(frequency)as frequency,\r\n" + 
					"	sum((( CASE WHEN kwh = 0 THEN 1 ELSE kwh END ) * 2 )) AS kva \r\n" + 
					"FROM meter_data.load_survey  LEFT JOIN meter_data.master_main ON(load_survey.meter_number=master_main.mtrno)\r\n" + 
					"WHERE to_char(read_time,'yyyyMM') >='"+frommonth+"'and  to_char(read_time,'yyyyMM') <='"+tomonth+"' and town_code LIKE '"+towncode+"' \r\n" + 
					"GROUP BY meter_number, to_char( read_time, 'yyyyMM' )) b \r\n" + 
					"WHERE 	A.meterno = b.meter_number AND dttpid = '"+dt+"' \r\n" + 
					"GROUP BY tp_town_code, dttpid, yearmonth ) X,\r\n" + 
					"	(\r\n" + 
					"SELECT DISTINCT *  FROM meter_data.amilocation am \r\n" + 
					"WHERE LOWER ( am.circle ) LIKE LOWER ( '"+circle+"' ) ) Y \r\n" + 
					"WHERE Y.tp_towncode = X.tp_town_code)AA,\r\n" + 
					"(\r\n" + 
					"SELECT DISTINCT dttpid,(kwh*2)mdkva,read_time,billmonth from (\r\n" + 
					"SELECT dttpid,kwh,read_time,billmonth,rank() over (partition by dttpid,billmonth ORDER BY kwh desc) as rank from (\r\n" + 
					"SELECT a.dttpid,sum(b.kwh) as kwh,b.read_time,to_char(read_time,'yyyyMM') as billmonth  FROM \r\n" + 
					"(SELECT dttpid,meterno from meter_data.dtdetails where dttpid IS NOT NULL AND dttpid != '' AND \r\n" + 
					"	  meterno IS NOT NULL AND meterno != '' )a,\r\n" + 
					"(SELECT  DISTINCT ls.meter_number,ls.kwh,ls.read_time from meter_data.load_survey ls LEFT JOIN meter_data.master_main mm ON(ls.meter_number=mm.mtrno) WHERE  mm.town_code like '"+towncode+"' and  \r\n" + 
					"to_char(ls.read_time,'yyyyMM') >='"+frommonth+"'\r\n" + 
					"and  to_char(ls.read_time,'yyyyMM') <='"+tomonth+"') b \r\n" + 
					"WHERE a.meterno=b.meter_number   GROUP  BY b.read_time,a.dttpid,to_char(read_time,'yyyyMM')  ) a )b where rank=1)BB\r\n" + 
					"WHERE AA.yearmonth=BB.billmonth AND AA.dttpid=BB.dttpid";

			System.err.println("monthly--ALL SingleDT :" + sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getIndividualDtConsumData3(String circle, String division, String subdivision, String towncode,
			String townname, String dt, String period, String ipwisedate) {
		List<?> list = new ArrayList<>();
		try {
			String sql ="SELECT Y.circle, Y.division, Y.subdivision, X.* \n" +
					"FROM\n" +
					"	(\n" +
					"	SELECT A.tp_town_code, A.dttpid, b.yearmonth,\n" +
					"	SUM ( b.kwh ) AS kwh,	SUM ( b.kvah ) AS kvah, SUM ( b.kw ) AS kw, SUM ( b.kva ) AS kva,\n" +
					"	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 4 ) pf,\n" +
					"	round (sum( i_r),3 ) AS ir,round (sum( i_y),3 ) AS iy,round (sum( i_b),3 ) AS ib,\n" +
					"	AVG ( v_r ) AS vr,AVG ( v_y ) AS vy,AVG ( v_b ) AS vb,\n" +
					"	SUM ( frequency ) frqncy \n" +
					"FROM\n" +
					"	(\n" +
					"	SELECT dttpid,meterno,tp_town_code \n" +
					"FROM meter_data.dtdetails  WHERE\n" +
					"	dttpid IN \n" +
					"	( SELECT dttpid \n" +
					" FROM\n" +
					"	( SELECT dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL AND dttpid != '' AND \n" +
					"	  meterno IS NOT NULL AND meterno != '' ) b \n" +
					"	)) A,\n" +
					"	(\n" +
					"  SELECT meter_number, to_char( read_time, 'yyyy-MM-dd HH24:mi:ss' ) AS yearmonth,\n" +
					"	kwh, kvah, kw, i_r,i_y,i_b,\n" +
					"	round(avg( v_r),3) AS v_r, round(avg( v_y),3) AS v_y, round(avg( v_b),3) AS v_b, frequency,\n" +
					"	( kwh * 2 ) AS kva \n" +
					"FROM meter_data.load_survey LEFT JOIN meter_data.master_main ON(load_survey.meter_number=master_main.mtrno) \n" +
					"WHERE  read_time between '"+ipwisedate+" 00:00:00' and '"+ipwisedate+" 23:59:59' and town_code LIKE '"+towncode+"'  \n" +
					"GROUP BY meter_number, to_char( read_time, 'yyyy-MM-dd HH24:mi:ss' ), kwh, frequency,\n" +
					"	kvah, kw, i_r, i_b,i_y, v_r, v_y, v_b, kva \n" +
					"	) b \n" +
					"WHERE 	A.meterno = b.meter_number AND dttpid = '"+dt+"' \n" +
					"GROUP BY tp_town_code, dttpid, yearmonth ) X,\n" +
					"	(\n" +
					"SELECT *  FROM meter_data.amilocation am \n" +
					"WHERE LOWER ( am.circle ) LIKE LOWER ( '"+circle+"' )) Y \n" +
					"WHERE Y.tp_towncode = X.tp_town_code";

			System.err.println("Ipwise query--" + sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<?> getIndividualDtConsumData11(String towncode) {
		List<?> list = new ArrayList<>();
		try {
			String sql = "select circle,division,subdivision,town_ipds,tp_towncode from meter_data.amilocation where tp_towncode='"
					+ towncode + "'";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getNoOfMeterCount(String dttpcode) {
		List<?> list = new ArrayList<>();
		try {
			String sql = "select count(*),meterno,avg(dtcapacity) \n" + "from meter_data.dtdetails WHERE dttpid='"
					+ dttpcode + "' and trim(dttpid)  not in('NULL','') and crossdt=0\n"
					+ "and meterno not in('NULL','') GROUP BY meterno";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getdtconsumdata(String zone,String circle, String division, String subdivision, String towncode,String feederTpId, String period,
			String date, String month) {

		List<?> list = new ArrayList<>();
		String sql = "";
		try {
			if ("Date".equals(period)) {

//				sql = "select A.*,X.yearmonth,X.kwh,X.kvah,X.kva,X.pf,X.ir,X.iy,X.ib,X.vr,X.vy,X.vb,X.frqncy,X.dtcap from\n"
//						+ "(\n" + "select am.circle,am.division,am.subdivision,am.town_ipds, dt.* from\n" + "(\n"
//						+ "select tp_town_code,feedername,tp_fdr_id,dtname,dttpid,count(meterno) \n" + "from\n" + "(\n"
//						+ "select d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\n"
//						+ "FROM meter_data.dtdetails d,meter_data.feederdetails f\n"
//						+ "WHERE  d.tpparentid=f.tp_fdr_id \n"
//						+ "and  trim(d.dttpid)  not in('NULL','') and d.crossdt=0\n"
//						+ "and d.meterno not in('NULL','')\n"
//						+ "GROUP BY  d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\n"
//						+ ")x GROUP BY tp_town_code,feedername,tp_fdr_id,dtname,dttpid\n" + ")dt, \n"
//						+ "meter_data.amilocation am \n"
//						+ "WHERE am.tp_towncode=dt.tp_town_code and LOWER(am.circle) LIKE LOWER('" + circle
//						+ "')  AND am.zone like '"+zone+"' and am.tp_towncode LIKE '" + towncode + "'\n" + ")A,\n" + "(\n"
//						+ "SELECT dttpid,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,\n"
//						+ "	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 2 ) pf, \n"
//						+ "avg(b.i_r) as ir,avg(i_y) as iy,avg(i_b) as ib,avg(b.v_r) as vr,avg(v_y) as vy,avg(v_b) as vb,\n"
//						+ "SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap\n" + "FROM \n" + "(\n"
//						+ "SELECT dttpid,meterno,officeid,tp_town_code,dtcapacity FROM meter_data.dtdetails WHERE \n"
//						+ " dttpid in (SELECT dttpid FROM (\n"
//						+ "SELECT count(*),dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\n"
//						+ " GROUP BY dttpid )b) ORDER BY dttpid\n" + ")a,\n"
//						+ "(select meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\n"
//						+ "sum(COALESCE(kw,'0') )as kw,\n"
//						+ "round(avg(i_r),3) as i_r,round(avg(i_y),3) as i_y,round(avg(i_b),3) as i_b,\n"
//						+ "round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\n"
//						+ "sum(kwh * 2) AS kva  \n"
//						+ "from meter_data.load_survey WHERE to_char(read_time,'yyyy-MM-dd')='" + date
//						+ "'  GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd')\n" + ")b\n"
//						+ "WHERE a.meterno=b.meter_number  GROUP BY dttpid,yearmonth ORDER BY dttpid\n" + ")X\n"
//						+ "WHERE A.dttpid=X.dttpid";
//				 System.out.println(sql);
				
				/*sql = "select A.*,X.yearmonth,X.kwh,X.kvah,X.kva,X.pf,X.ir,X.iy,X.ib,X.vr,X.vy,X.vb,X.frqncy,X.dtcap from\r\n" + 
						"(\r\n" + 
						"select am.circle,am.division,am.subdivision,am.town_ipds, dt.* from\r\n" + 
						"(\r\n" + 
						"select distinct on (tp_fdr_id)tp_town_code,feedername,tp_fdr_id,dtname,dttpid,count(meterno) \r\n" + 
						"from\r\n" + 
						"(\r\n" + 
						"select d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\r\n" + 
						"FROM meter_data.dtdetails d,meter_data.feederdetails f\r\n" + 
						"WHERE  d.tpparentid=f.tp_fdr_id \r\n" + 
						"and  trim(d.dttpid)  not in('NULL','') and d.crossdt=0\r\n" + 
						"and d.meterno not in('NULL','')\r\n" + 
						"GROUP BY  d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\r\n" + 
						")x GROUP BY tp_town_code,feedername,tp_fdr_id,dtname,dttpid\r\n" + 
						")dt, \r\n" + 
						"meter_data.amilocation am \r\n" + 
						"WHERE am.tp_towncode=dt.tp_town_code and LOWER(am.circle) LIKE LOWER('"+circle+"')  AND am.zone like '"+zone+"' and am.tp_towncode LIKE '"+towncode+"'\r\n" + 
						")A,\r\n" + 
						"(\r\n" + 
						"SELECT dttpid,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,\r\n" + 
						"	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 2 ) pf, \r\n" + 
						"round(sum(b.i_r)/48,3) as ir,round(sum(i_y)/48,3) as iy,round(sum(i_b)/48,3) as ib,avg(b.v_r) as vr,avg(v_y) as vy,avg(v_b) as vb,\r\n" + 
						"SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap\r\n" + 
						"FROM \r\n" + 
						"(\r\n" + 
						"SELECT dttpid,meterno,officeid,tp_town_code,dtcapacity FROM meter_data.dtdetails WHERE \r\n" + 
						" dttpid in (SELECT dttpid FROM (\r\n" + 
						"SELECT count(*),dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\r\n" + 
						" GROUP BY dttpid )b) ORDER BY dttpid\r\n" + 
						")a,\r\n" + 
						"(select distinct meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\r\n" + 
						"sum(COALESCE(kw,'0') )as kw,\r\n" + 
						"round(sum(i_r)/48,3) as i_r,round(sum(i_y)/48,3) as i_y,round(sum(i_b)/48,3) as i_b,\r\n" + 
						"round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\r\n" + 
						"sum(kwh * 2) AS kva  \r\n" + 
						"from meter_data.load_survey WHERE read_time between '"+date+" 00:00:00' and '"+date+" 23:59:59' and meter_number IN \r\n" + 
						"(select distinct dt.meterno  from meter_data.dtdetails dt LEFT JOIN meter_data.master_main mm ON(dt.meterno=mm.mtrno)where dt.tp_town_code ='"+towncode+"' and dt.tpparentid='"+feederTpId+"')  GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd')\r\n" + 
						")b\r\n" + 
						"WHERE a.meterno=b.meter_number  GROUP BY dttpid,yearmonth ORDER BY dttpid\r\n" + 
						")X\r\n" + 
						"WHERE A.dttpid=X.dttpid\r\n" + 
						"";*/
				
				/*sql="select distinct b.circle,b.division,b.subdivision,b.town_ipds,a.tp_town_code,a.feedername,a.tp_fdr_id,a.dtname,a.dttpid,c.count,a.dtcapacity,a.yearmonth,a.meter_number,sum(a.kwh) as kwh ,sum(a.kvah) as kvah,sum(a.kw) as kw,sum(a.kva) as kva,\r\n" + 
						"	round(( SUM ( a.kwh ) / SUM ((case WHEN a.kvah =0 THEN 1 else a.kvah END ))), 2 ) pf, \r\n" + 
						"i_r,i_y,i_b,avg(a.v_r) as vr,avg(a.v_y) as vy,avg(a.v_b) as vb,\r\n" + 
						"SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap,a.mdkva,a.read_time from \r\n" + 
						"(select distinct  dtdetails.tp_town_code,feederdetails.tp_fdr_id,feederdetails.feedername,dtdetails.dttpid,dtdetails.dtname,dtdetails.dtcapacity,meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\r\n" + 
						"sum(COALESCE(kw,'0') )as kw,\r\n" + 
						"round(sum(i_r/48),3) as i_r,round(sum(i_y/48),3) as i_y,round(sum(i_b/48),3) as i_b,\r\n" + 
						"round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\r\n" + 
						"sum(kwh * 2) AS kva,max(kva) as mdkva,to_char(read_time,'yyyy-MM-dd') as read_time  \r\n" + 
						"from meter_data.load_survey LEFT JOIN meter_data.dtdetails ON(load_survey.meter_number=dtdetails.meterno) LEFT JOIN meter_data.feederdetails ON(feederdetails.tp_fdr_id=dtdetails.tpparentid)WHERE read_time between '"+date+" 00:00:00' and '"+date+" 23:59:59'  and meter_number in\r\n" + 
						"(select distinct dt.meterno  from meter_data.dtdetails dt LEFT JOIN meter_data.master_main mm ON(dt.meterno=mm.mtrno)where dt.tp_town_code ='"+towncode+"' and dt.tpparentid='"+feederTpId+"') and dtdetails.dttpid IS NOT NULL and dtdetails.dttpid!='' and dtdetails.meterno IS NOT NULL and dtdetails.meterno!='' GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd'),dtdetails.dttpid,dtdetails.tp_town_code,dtdetails.dtname,feederdetails.tp_fdr_id,feederdetails.feedername,dtdetails.dtcapacity\r\n" + 
						")a LEFT JOIN \r\n" + 
						"\r\n" + 
						"(select circle,division,subdivision,town_ipds,tp_towncode from meter_data.amilocation)b ON(a.tp_town_code=b.tp_towncode)  LEFT JOIN \r\n" + 
						"\r\n" + 
						"(\r\n" + 
						"SELECT count(meterno) as count,dttpid,meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\r\n" + 
						" GROUP BY dttpid,meterno)c ON(a.meter_number=c.meterno) GROUP BY b.circle,b.division,b.subdivision,b.town_ipds, a.tp_town_code,a.tp_fdr_id,a.feedername,a.dttpid,a.dtname,a.dtcapacity,a.meter_number,a.yearmonth,a.mdkva,a.read_time,a.kwh,a.kvah,a.kw,a.i_r,a.i_y,a.i_b,\r\n" + 
						"a.v_r,a.v_y,a.v_b,a.frequency,\r\n" + 
						"a.kva,a.mdkva,a.read_time,c.count";*/
				
				
				
				sql="select distinct b.circle,b.division,b.subdivision,b.town_ipds,a.tp_town_code,a.feedername,a.tp_fdr_id,a.dtname,a.dttpid,a.meter_number,a.dtcapacity,a.yearmonth,sum(a.kwh) as kwh ,sum(a.kvah) as kvah,sum(a.kw) as kw,sum(a.kva) as kva,\r\n" + 
						"	round(( SUM ( a.kwh ) / SUM ((case WHEN a.kvah =0 THEN 1 else a.kvah END ))), 2 ) pf, \r\n" + 
						"i_r,i_y,i_b,avg(a.v_r) as vr,avg(a.v_y) as vy,avg(a.v_b) as vb,\r\n" + 
						"SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap,a.mdkva,a.read_time from \r\n" + 
						"\r\n" + 
						"(select distinct  dtdetails.tp_town_code,feederdetails.tp_fdr_id,feederdetails.feedername, String_agg(DISTINCT dtdetails.dttpid,',') as dttpid ,dtdetails.dtname,dtdetails.dtcapacity,count(DISTINCT meter_number) as meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\r\n" + 
						"sum(COALESCE(kw,'0') )as kw,\r\n" + 
						"round(sum(i_r/48),3) as i_r,round(sum(i_y/48),3) as i_y,round(sum(i_b/48),3) as i_b,\r\n" + 
						"round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\r\n" + 
						"sum(kwh * 2) AS kva,max(kva) as mdkva,to_char(read_time,'yyyy-MM-dd') as read_time  \r\n" + 
						"from meter_data.load_survey LEFT JOIN meter_data.dtdetails ON(load_survey.meter_number=dtdetails.meterno) LEFT JOIN meter_data.feederdetails ON(feederdetails.tp_fdr_id=dtdetails.tpparentid)WHERE read_time between '"+date+" 00:00:00' and '"+date+" 23:59:59'  and meter_number in\r\n" + 
						"(select distinct dt.meterno  from meter_data.dtdetails dt LEFT JOIN meter_data.master_main mm ON(dt.meterno=mm.mtrno)where dt.tp_town_code ='"+towncode+"' and dt.tpparentid='"+feederTpId+"') and dtdetails.dttpid IS NOT NULL and dtdetails.dttpid!='' and dtdetails.meterno IS NOT NULL and dtdetails.meterno!='' GROUP BY to_char(read_time,'yyyy-MM-dd'),dtdetails.dttpid,dtdetails.tp_town_code,dtdetails.dtname,feederdetails.tp_fdr_id,feederdetails.feedername,dtdetails.dtcapacity\r\n" + 
						")a LEFT JOIN \r\n" + 
						"\r\n" + 
						"(select circle,division,subdivision,town_ipds,tp_towncode from meter_data.amilocation)b ON(a.tp_town_code=b.tp_towncode) GROUP BY b.circle,b.division,b.subdivision,b.town_ipds, a.tp_town_code,a.tp_fdr_id,a.feedername,a.dttpid,a.dtname,a.dtcapacity,a.meter_number,a.yearmonth,a.mdkva,a.read_time,a.kwh,a.kvah,a.kw,a.i_r,a.i_y,a.i_b,\r\n" + 
						"a.v_r,a.v_y,a.v_b,a.frequency,\r\n" + 
						"a.kva,a.mdkva,a.read_time\r\n" + 
						"";

			} else if ("Month".equals(period)) {
				// System.err.println("all dt consumption month");
//				sql = "select A.*,X.yearmonth,X.kwh,X.kvah,X.kva,X.pf,X.ir,X.iy,X.ib,X.vr,X.vy,X.vb,X.frqncy,X.dtcap from\n"
//						+ "(\n" + "select am.circle,am.division,am.subdivision,am.town_ipds, dt.* from\n" + "(\n"
//						+ "select tp_town_code,feedername,tp_fdr_id,dtname,dttpid,count(meterno) \n" + "from\n" + "(\n"
//						+ "select d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\n"
//						+ "FROM meter_data.dtdetails d,meter_data.feederdetails f\n"
//						+ "WHERE  d.tpparentid=f.tp_fdr_id \n"
//						+ "and  trim(d.dttpid)  not in('NULL','') and d.crossdt=0\n"
//						+ "and d.meterno not in('NULL','')\n"
//						+ "GROUP BY  d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\n"
//						+ ")x GROUP BY tp_town_code,feedername,tp_fdr_id,dtname,dttpid\n" + ")dt, \n"
//						+ "meter_data.amilocation am \n"
//						+ "WHERE am.tp_towncode=dt.tp_town_code and LOWER(am.circle) LIKE LOWER('"+circle+"') AND am.zone like '"+zone+"' AND am.tp_towncode LIKE '"+towncode+"'\n"
//						+ ")A,\n" + "(\n"
//						+ "SELECT dttpid,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,\n"
//						+ "	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 2 ) pf, \n"
//						+ "avg(b.i_r) as ir,avg(i_y) as iy,avg(i_b) as ib,avg(b.v_r) as vr,avg(v_y) as vy,avg(v_b) as vb,\n"
//						+ "SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap\n" + "FROM \n" + "(\n"
//						+ "SELECT dttpid,meterno,officeid,tp_town_code,dtcapacity FROM meter_data.dtdetails WHERE \n"
//						+ " dttpid in (SELECT dttpid FROM (\n"
//						+ "SELECT count(*),dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\n"
//						+ " GROUP BY dttpid )b) ORDER BY dttpid\n" + ")a,\n"
//						+ "(select meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\n"
//						+ "sum(COALESCE(kw,'0') )as kw,\n"
//						+ "round(avg(i_r),3) as i_r,round(avg(i_y),3) as i_y,round(avg(i_b),3) as i_b,\n"
//						+ "round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\n"
//						+ "sum(kwh * 2) AS kva  \n" + "from meter_data.load_survey WHERE to_char(read_time,'yyyyMM')='"
//						+ month + "'  GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd')\n" + ")b\n"
//						+ "WHERE a.meterno=b.meter_number  GROUP BY dttpid,yearmonth ORDER BY dttpid\n" + ")X\n"
//						+ "WHERE A.dttpid=X.dttpid";
//				 System.out.println(sql);
				
			/*	sql = "select A.*,X.yearmonth,X.kwh,X.kvah,X.kva,X.pf,X.ir,X.iy,X.ib,X.vr,X.vy,X.vb,X.frqncy,X.dtcap,X.mdkva,X.read_time from\r\n" + 
						"(\r\n" + 
						"select am.circle,am.division,am.subdivision,am.town_ipds, dt.* from\r\n" + 
						"(\r\n" + 
						"select distinct on (tp_fdr_id)tp_town_code,feedername,tp_fdr_id,dtname,dttpid,count(meterno) \r\n" + 
						"from\r\n" + 
						"(\r\n" + 
						"select d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\r\n" + 
						"FROM meter_data.dtdetails d,meter_data.feederdetails f\r\n" + 
						"WHERE  d.tpparentid=f.tp_fdr_id \r\n" + 
						"and  trim(d.dttpid)  not in('NULL','') and d.crossdt=0\r\n" + 
						"and d.meterno not in('NULL','')\r\n" + 
						"GROUP BY  d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\r\n" + 
						")x GROUP BY tp_town_code,feedername,tp_fdr_id,dtname,dttpid\r\n" + 
						")dt, \r\n" + 
						"meter_data.amilocation am \r\n" + 
						"WHERE am.tp_towncode=dt.tp_town_code and LOWER(am.circle) LIKE LOWER('"+circle+"') AND am.zone like '"+zone+"' AND am.tp_towncode LIKE '"+towncode+"'\r\n" + 
						")A,\r\n" + 
						"(\r\n" + 
						"SELECT dttpid,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,\r\n" + 
						"	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 2 ) pf, \r\n" + 
						"round(sum(b.i_r)/1440,3) as ir,round(sum(i_y)/1440,3) as iy,round(sum(i_b)/1440,3) as ib,avg(b.v_r) as vr,avg(v_y) as vy,avg(v_b) as vb,\r\n" + 
						"SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap,b.mdkva,b.read_time\r\n" + 
						"FROM \r\n" + 
						"(\r\n" + 
						"SELECT dttpid,meterno,officeid,tp_town_code,dtcapacity FROM meter_data.dtdetails WHERE \r\n" + 
						" dttpid in (SELECT dttpid FROM (\r\n" + 
						"SELECT count(*),dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\r\n" + 
						" GROUP BY dttpid )b) ORDER BY dttpid\r\n" + 
						")a,\r\n" + 
						"(select meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\r\n" + 
						"sum(COALESCE(kw,'0') )as kw,\r\n" + 
						"round(sum(i_r)/1440,3) as i_r,round(sum(i_y)/1440,3) as i_y,round(sum(i_b)/1440,3) as i_b,\r\n" + 
						"round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\r\n" + 
						"sum(kwh * 2) AS kva,max(kva) as mdkva,to_char(read_time,'yyyy-MM-dd') as read_time  \r\n" + 
						"from meter_data.load_survey WHERE to_char(read_time,'yyyyMM')='"+month+"' and meter_number in\r\n" + 
						"(select distinct dt.meterno  from meter_data.dtdetails dt LEFT JOIN meter_data.master_main mm ON(dt.meterno=mm.mtrno)where dt.tp_town_code ='"+towncode+"' and dt.tpparentid='"+feederTpId+"') GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd')\r\n" + 
						")b\r\n" + 
						"WHERE a.meterno=b.meter_number  GROUP BY dttpid,yearmonth,mdkva,read_time ORDER BY dttpid\r\n" + 
						")X\r\n" + 
						"WHERE A.dttpid=X.dttpid\r\n" + 
						""; */
				
				
					/*sql="select distinct b.circle,b.division,b.subdivision,b.town_ipds,a.tp_town_code,a.feedername,a.tp_fdr_id,a.dtname,a.dttpid,c.count,a.dtcapacity,a.yearmonth,a.meter_number,sum(a.kwh) as kwh ,sum(a.kvah) as kvah,sum(a.kw) as kw,sum(a.kva) as kva,\r\n" + 
							"	round(( SUM ( a.kwh ) / SUM ((case WHEN a.kvah =0 THEN 1 else a.kvah END ))), 2 ) pf, \r\n" + 
							"i_r,i_y,i_b,avg(a.v_r) as vr,avg(a.v_y) as vy,avg(a.v_b) as vb,\r\n" + 
							"SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap,a.mdkva,a.read_time from \r\n" + 
							"(select distinct  dtdetails.tp_town_code,feederdetails.tp_fdr_id,feederdetails.feedername,dtdetails.dttpid,dtdetails.dtname,dtdetails.dtcapacity,meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\r\n" + 
							"sum(COALESCE(kw,'0') )as kw,\r\n" + 
							"round(sum(i_r/1440),3) as i_r,round(sum(i_y/1440),3) as i_y,round(sum(i_b/1440),3) as i_b,\r\n" + 
							"round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\r\n" + 
							"sum(kwh * 2) AS kva,max(kva) as mdkva,to_char(read_time,'yyyy-MM-dd') as read_time  \r\n" + 
							"from meter_data.load_survey LEFT JOIN meter_data.dtdetails ON(load_survey.meter_number=dtdetails.meterno) LEFT JOIN meter_data.feederdetails ON(feederdetails.tp_fdr_id=dtdetails.tpparentid)WHERE to_char(read_time,'yyyyMM')='"+month+"' and meter_number in\r\n" + 
							"(select distinct dt.meterno  from meter_data.dtdetails dt LEFT JOIN meter_data.master_main mm ON(dt.meterno=mm.mtrno)where dt.tp_town_code ='"+towncode+"' and dt.tpparentid='"+feederTpId+"') and dtdetails.dttpid IS NOT NULL and dtdetails.dttpid!='' and dtdetails.meterno IS NOT NULL and dtdetails.meterno!='' GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd'),dtdetails.dttpid,dtdetails.tp_town_code,dtdetails.dtname,feederdetails.tp_fdr_id,feederdetails.feedername,dtdetails.dtcapacity\r\n" + 
							")a LEFT JOIN \r\n" + 
							"\r\n" + 
							"(select circle,division,subdivision,town_ipds,tp_towncode from meter_data.amilocation)b ON(a.tp_town_code=b.tp_towncode)  LEFT JOIN \r\n" + 
							"\r\n" + 
							"(\r\n" + 
							"SELECT count(meterno) as count,dttpid,meterno FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\r\n" + 
							" GROUP BY dttpid,meterno)c ON(a.meter_number=c.meterno) GROUP BY b.circle,b.division,b.subdivision,b.town_ipds, a.tp_town_code,a.tp_fdr_id,a.feedername,a.dttpid,a.dtname,a.dtcapacity,a.meter_number,a.yearmonth,a.mdkva,a.read_time,a.kwh,a.kvah,a.kw,a.i_r,a.i_y,a.i_b,\r\n" + 
							"a.v_r,a.v_y,a.v_b,a.frequency,\r\n" + 
							"a.kva,a.mdkva,a.read_time,c.count";*/
				
				
				sql="\r\n" + 
						"select distinct b.circle,b.division,b.subdivision,b.town_ipds,a.tp_town_code,a.feedername,a.tp_fdr_id,a.dtname,a.dttpid,a.meter_number,a.dtcapacity,a.yearmonth,sum(a.kwh) as kwh ,sum(a.kvah) as kvah,sum(a.kw) as kw,sum(a.kva) as kva,\r\n" + 
						"	round(( SUM ( a.kwh ) / SUM ((case WHEN a.kvah =0 THEN 1 else a.kvah END ))), 2 ) pf, \r\n" + 
						"i_r,i_y,i_b,avg(a.v_r) as vr,avg(a.v_y) as vy,avg(a.v_b) as vb,\r\n" + 
						"SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap,a.mdkva,a.read_time from \r\n" + 
						"\r\n" + 
						"(select distinct  dtdetails.tp_town_code,feederdetails.tp_fdr_id,feederdetails.feedername, String_agg(DISTINCT dtdetails.dttpid,',') as dttpid ,dtdetails.dtname,dtdetails.dtcapacity,count(DISTINCT meter_number) as meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\r\n" + 
						"sum(COALESCE(kw,'0') )as kw,\r\n" + 
						"round(sum(i_r/48),3) as i_r,round(sum(i_y/48),3) as i_y,round(sum(i_b/48),3) as i_b,\r\n" + 
						"round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\r\n" + 
						"sum(kwh * 2) AS kva,max(kva) as mdkva,to_char(read_time,'yyyy-MM-dd') as read_time  \r\n" + 
						"from meter_data.load_survey LEFT JOIN meter_data.dtdetails ON(load_survey.meter_number=dtdetails.meterno) LEFT JOIN meter_data.feederdetails ON(feederdetails.tp_fdr_id=dtdetails.tpparentid)WHERE  to_char(read_time,'yyyyMM')='"+month+"'  and meter_number in\r\n" + 
						"(select distinct dt.meterno  from meter_data.dtdetails dt LEFT JOIN meter_data.master_main mm ON(dt.meterno=mm.mtrno)where dt.tp_town_code ='"+towncode+"' and dt.tpparentid='"+feederTpId+"') and dtdetails.dttpid IS NOT NULL and dtdetails.dttpid!='' and dtdetails.meterno IS NOT NULL and dtdetails.meterno!='' GROUP BY to_char(read_time,'yyyy-MM-dd'),dtdetails.dttpid,dtdetails.tp_town_code,dtdetails.dtname,feederdetails.tp_fdr_id,feederdetails.feedername,dtdetails.dtcapacity\r\n" + 
						")a LEFT JOIN \r\n" + 
						"\r\n" + 
						"(select circle,division,subdivision,town_ipds,tp_towncode from meter_data.amilocation)b ON(a.tp_town_code=b.tp_towncode) GROUP BY b.circle,b.division,b.subdivision,b.town_ipds, a.tp_town_code,a.tp_fdr_id,a.feedername,a.dttpid,a.dtname,a.dtcapacity,a.meter_number,a.yearmonth,a.mdkva,a.read_time,a.kwh,a.kvah,a.kw,a.i_r,a.i_y,a.i_b,\r\n" + 
						"a.v_r,a.v_y,a.v_b,a.frequency,\r\n" + 
						"a.kva,a.mdkva,a.read_time";
			}
			 System.out.println(period+" dt all consumption--"+sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

//pdf
	@Override
	public void dtConsumptionpdf(HttpServletRequest request, HttpServletResponse response, String circle,
			String towncode, String period, String date, String month, String zone,String frommonthh,String tomonthh,String dtt) {

		List<Object[]> DTData2 = null;
		String sql = "";
		try {

			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
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
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("DT Loading Summary ", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(2);
			header.setWidths(new int[] { 1, 1 });
			header.setWidthPercentage(100);

			PdfPCell headerCell = null;
			headerCell = new PdfPCell(
					new Phrase("Circle :" + circle, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
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
			 * 
			 * headerCell = new PdfPCell(new Phrase("Sub-Division :"+subdiv,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */

			headerCell = new PdfPCell(
					new Phrase("Town :" + circle, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("Month Year :" + month, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			try {
				if ("Date".equals(period)) {

//					sql = "select A.*,X.yearmonth,X.kwh,X.kvah,X.kva,X.pf,X.ir,X.iy,X.ib,X.vr,X.vy,X.vb,X.frqncy,X.dtcap from\n"
//							+ "(\n" + "select am.circle,am.division,am.subdivision,am.town_ipds, dt.* from\n" + "(\n"
//							+ "select tp_town_code,feedername,tp_fdr_id,dtname,dttpid,count(meterno) \n" + "from\n"
//							+ "(\n" + "select d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\n"
//							+ "FROM meter_data.dtdetails d,meter_data.feederdetails f\n"
//							+ "WHERE  d.tpparentid=f.tp_fdr_id \n"
//							+ "and  trim(d.dttpid)  not in('NULL','') and d.crossdt=0\n"
//							+ "and d.meterno not in('NULL','')\n"
//							+ "GROUP BY  d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\n"
//							+ ")x GROUP BY tp_town_code,feedername,tp_fdr_id,dtname,dttpid\n" + ")dt, \n"
//							+ "meter_data.amilocation am \n"
//							+ "WHERE am.tp_towncode=dt.tp_town_code and LOWER(am.circle) LIKE LOWER('CHENGALPATTU')  AND am.tp_towncode LIKE '013'\n"
//							+ ")A,\n" + "(\n"
//							+ "SELECT dttpid,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,\n"
//							+ "	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 2 ) pf, \n"
//							+ "avg(b.i_r) as ir,avg(i_y) as iy,avg(i_b) as ib,avg(b.v_r) as vr,avg(v_y) as vy,avg(v_b) as vb,\n"
//							+ "SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap\n" + "FROM \n" + "(\n"
//							+ "SELECT dttpid,meterno,officeid,tp_town_code,dtcapacity FROM meter_data.dtdetails WHERE \n"
//							+ " dttpid in (SELECT dttpid FROM (\n"
//							+ "SELECT count(*),dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\n"
//							+ " GROUP BY dttpid )b) ORDER BY dttpid\n" + ")a,\n"
//							+ "(select meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\n"
//							+ "sum(COALESCE(kw,'0') )as kw,\n"
//							+ "round(avg(i_r),3) as i_r,round(avg(i_y),3) as i_y,round(avg(i_b),3) as i_b,\n"
//							+ "round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\n"
//							+ "sum(kwh * 2) AS kva  \n"
//							+ "from meter_data.load_survey WHERE to_char(read_time,'yyyy-MM-dd')='" + date
//							+ "'  GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd')\n" + ")b\n"
//							+ "WHERE a.meterno=b.meter_number  GROUP BY dttpid,yearmonth ORDER BY dttpid\n" + ")X\n"
//							+ "WHERE A.dttpid=X.dttpid";
					
					
					sql = "select A.*,X.yearmonth,X.kwh,X.kvah,X.kva,X.pf,X.ir,X.iy,X.ib,X.vr,X.vy,X.vb,X.frqncy,X.dtcap from\r\n" + 
							"(\r\n" + 
							"select am.circle,am.division,am.subdivision,am.town_ipds, dt.* from\r\n" + 
							"(\r\n" + 
							"select tp_town_code,feedername,tp_fdr_id,dtname,dttpid,count(meterno) \r\n" + 
							"from\r\n" + 
							"(\r\n" + 
							"select d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\r\n" + 
							"FROM meter_data.dtdetails d,meter_data.feederdetails f\r\n" + 
							"WHERE  d.tpparentid=f.tp_fdr_id \r\n" + 
							"and  trim(d.dttpid)  not in('NULL','') and d.crossdt=0\r\n" + 
							"and d.meterno not in('NULL','')\r\n" + 
							"GROUP BY  d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\r\n" + 
							")x GROUP BY tp_town_code,feedername,tp_fdr_id,dtname,dttpid\r\n" + 
							")dt, \r\n" + 
							"meter_data.amilocation am \r\n" + 
							"WHERE am.tp_towncode=dt.tp_town_code and LOWER(am.circle) LIKE LOWER('"+circle+"')  AND am.zone like '"+zone+"' and am.tp_towncode LIKE '"+towncode+"'\r\n" + 
							")A,\r\n" + 
							"(\r\n" + 
							"SELECT dttpid,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,\r\n" + 
							"	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 2 ) pf, \r\n" + 
							"round(sum(b.i_r/48),3) as ir,round(sum(i_y/48),3) as iy,round(sum(i_b/48),3) as ib,avg(b.v_r) as vr,avg(v_y) as vy,avg(v_b) as vb,\r\n" + 
							"SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap\r\n" + 
							"FROM \r\n" + 
							"(\r\n" + 
							"SELECT dttpid,meterno,officeid,tp_town_code,dtcapacity FROM meter_data.dtdetails WHERE \r\n" + 
							" dttpid in (SELECT dttpid FROM (\r\n" + 
							"SELECT count(*),dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\r\n" + 
							" GROUP BY dttpid )b) ORDER BY dttpid\r\n" + 
							")a,\r\n" + 
							"(select meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\r\n" + 
							"sum(COALESCE(kw,'0') )as kw,\r\n" + 
							"round(sum(i_r/48),3) as i_r,round(sum(i_y/48),3) as i_y,round(sum(i_b/48),3) as i_b,\r\n" + 
							"round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\r\n" + 
							"sum(kwh * 2) AS kva  \r\n" + 
							"from meter_data.load_survey WHERE to_char(read_time,'yyyy-MM-dd')='"+date+"'  GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd')\r\n" + 
							")b\r\n" + 
							"WHERE a.meterno=b.meter_number  GROUP BY dttpid,yearmonth ORDER BY dttpid\r\n" + 
							")X\r\n" + 
							"WHERE A.dttpid=X.dttpid\r\n" + 
							"";
					// System.out.println(sql);

				} else if ("Month".equals(period)) {
					// System.err.println("all dt consumption month");
//					sql = "select A.*,X.yearmonth,X.kwh,X.kvah,X.kva,X.pf,X.ir,X.iy,X.ib,X.vr,X.vy,X.vb,X.frqncy,X.dtcap from\n"
//							+ "(\n" + "select am.circle,am.division,am.subdivision,am.town_ipds, dt.* from\n" + "(\n"
//							+ "select tp_town_code,feedername,tp_fdr_id,dtname,dttpid,count(meterno) \n" + "from\n"
//							+ "(\n" + "select d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\n"
//							+ "FROM meter_data.dtdetails d,meter_data.feederdetails f\n"
//							+ "WHERE  d.tpparentid=f.tp_fdr_id \n"
//							+ "and  trim(d.dttpid)  not in('NULL','') and d.crossdt=0\n"
//							+ "and d.meterno not in('NULL','')\n"
//							+ "GROUP BY  d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\n"
//							+ ")x GROUP BY tp_town_code,feedername,tp_fdr_id,dtname,dttpid\n" + ")dt, \n"
//							+ "meter_data.amilocation am \n"
//							+ "WHERE am.tp_towncode=dt.tp_town_code and LOWER(am.circle) LIKE LOWER('CHENGALPATTU') AND am.tp_towncode LIKE '013'\n"
//							+ ")A,\n" + "(\n"
//							+ "SELECT dttpid,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,\n"
//							+ "	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 2 ) pf, \n"
//							+ "avg(b.i_r) as ir,avg(i_y) as iy,avg(i_b) as ib,avg(b.v_r) as vr,avg(v_y) as vy,avg(v_b) as vb,\n"
//							+ "SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap\n" + "FROM \n" + "(\n"
//							+ "SELECT dttpid,meterno,officeid,tp_town_code,dtcapacity FROM meter_data.dtdetails WHERE \n"
//							+ " dttpid in (SELECT dttpid FROM (\n"
//							+ "SELECT count(*),dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\n"
//							+ " GROUP BY dttpid )b) ORDER BY dttpid\n" + ")a,\n"
//							+ "(select meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\n"
//							+ "sum(COALESCE(kw,'0') )as kw,\n"
//							+ "round(avg(i_r),3) as i_r,round(avg(i_y),3) as i_y,round(avg(i_b),3) as i_b,\n"
//							+ "round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\n"
//							+ "sum(kwh * 2) AS kva  \n"
//							+ "from meter_data.load_survey WHERE to_char(read_time,'yyyyMM')='" + month
//							+ "'  GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd')\n" + ")b\n"
//							+ "WHERE a.meterno=b.meter_number  GROUP BY dttpid,yearmonth ORDER BY dttpid\n" + ")X\n"
//							+ "WHERE A.dttpid=X.dttpid";
					// System.out.println(sql);

					sql = "select A.*,X.yearmonth,X.kwh,X.kvah,X.kva,X.pf,X.ir,X.iy,X.ib,X.vr,X.vy,X.vb,X.frqncy,X.dtcap from\r\n" + 
							"(\r\n" + 
							"select am.circle,am.division,am.subdivision,am.town_ipds, dt.* from\r\n" + 
							"(\r\n" + 
							"select tp_town_code,feedername,tp_fdr_id,dtname,dttpid,count(meterno) \r\n" + 
							"from\r\n" + 
							"(\r\n" + 
							"select d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\r\n" + 
							"FROM meter_data.dtdetails d,meter_data.feederdetails f\r\n" + 
							"WHERE  d.tpparentid=f.tp_fdr_id \r\n" + 
							"and  trim(d.dttpid)  not in('NULL','') and d.crossdt=0\r\n" + 
							"and d.meterno not in('NULL','')\r\n" + 
							"GROUP BY  d.tp_town_code,f.feedername,f.tp_fdr_id,d.dtname,d.dttpid,d.meterno\r\n" + 
							")x GROUP BY tp_town_code,feedername,tp_fdr_id,dtname,dttpid\r\n" + 
							")dt, \r\n" + 
							"meter_data.amilocation am \r\n" + 
							"WHERE am.tp_towncode=dt.tp_town_code and LOWER(am.circle) LIKE LOWER('"+circle+"') AND am.zone like '"+zone+"' AND am.tp_towncode LIKE '"+towncode+"'\r\n" + 
							")A,\r\n" + 
							"(\r\n" + 
							"SELECT dttpid,yearmonth,sum(b.kwh) as kwh ,sum(b.kvah) as kvah,sum(b.kw) as kw,sum(b.kva) as kva,\r\n" + 
							"	round(( SUM ( b.kwh ) / SUM ((case WHEN b.kvah =0 THEN 1 else b.kvah END ))), 2 ) pf, \r\n" + 
							"round(sum(b.i_r)/1440,3) as ir,round(sum(i_y)/1440,3) as iy,round(sum(i_b)/1440,3) as ib,avg(b.v_r) as vr,avg(v_y) as vy,avg(v_b) as vb,\r\n" + 
							"SUM ( frequency ) frqncy,AVG(dtcapacity)  dtcap\r\n" + 
							"FROM \r\n" + 
							"(\r\n" + 
							"SELECT dttpid,meterno,officeid,tp_town_code,dtcapacity FROM meter_data.dtdetails WHERE \r\n" + 
							" dttpid in (SELECT dttpid FROM (\r\n" + 
							"SELECT count(*),dttpid FROM meter_data.dtdetails WHERE dttpid IS NOT NULL and dttpid!='' and meterno IS NOT NULL and meterno!=''\r\n" + 
							" GROUP BY dttpid )b) ORDER BY dttpid\r\n" + 
							")a,\r\n" + 
							"(select meter_number,to_char(read_time,'yyyy-MM-dd') as yearmonth,sum(COALESCE(kwh,'0')) as kwh,sum(COALESCE(kvah,'0')) as kvah,\r\n" + 
							"sum(COALESCE(kw,'0') )as kw,\r\n" + 
							"round(sum(i_r)/1440,3) as i_r,round(sum(i_y)/1440,3) as i_y,round(sum(i_b)/1440,3) as i_b,\r\n" + 
							"round(avg(v_r),3) as v_r,round(avg(v_y),3) as v_y,round(avg(v_b),3) as v_b,sum(frequency)as frequency,\r\n" + 
							"sum(kwh * 2) AS kva  \r\n" + 
							"from meter_data.load_survey WHERE to_char(read_time,'yyyyMM')='"+month+"'  GROUP BY meter_number,to_char(read_time,'yyyy-MM-dd')\r\n" + 
							")b\r\n" + 
							"WHERE a.meterno=b.meter_number  GROUP BY dttpid,yearmonth ORDER BY dttpid\r\n" + 
							")X\r\n" + 
							"WHERE A.dttpid=X.dttpid\r\n" + 
							"";
					
				}
				// System.out.println(period+" dt all consumption--"+sql);
				DTData2 = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

			} catch (Exception e) {
				e.printStackTrace();
			}

			// return list;
			System.out.println(DTData2.size());
			PdfPCell parameterCell;
			PdfPTable parameterTable=null;
		if (period.equalsIgnoreCase("Month"))
			
			{
			 parameterTable = new PdfPTable(22);
			parameterTable.setWidths(new int[] { 1, 2, 2, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
			parameterTable.setWidthPercentage(100);
			
			}
		else {
			 parameterTable = new PdfPTable(20);
			parameterTable.setWidths(new int[] { 1, 2, 2, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
			parameterTable.setWidthPercentage(100);
			//PdfPCell parameterCell;
			
		}

			parameterCell = new PdfPCell(new Phrase("S.NO", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("TOWN", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("TOWN CODE", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("FEEDER ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("FEEDER CODE", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("DT NAME", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("DT CODE", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setRowspan(1);
			// parameterCell.setColspan(2);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("NO. OF METERS COONECTED", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("DT CAPACITY", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("MONTH ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KWH", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KVAH", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("KVA", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("IR", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("IY", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("IB", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("VR", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("VY", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("VB", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("PF", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

//			parameterCell = new PdfPCell(new Phrase("FREQUENCY", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
//			parameterCell.setFixedHeight(25f);
//			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//			parameterTable.addCell(parameterCell);

			if (period.equalsIgnoreCase("Month"))
			{ parameterCell = new PdfPCell(new Phrase("KVA",new
			  Font(Font.FontFamily.HELVETICA ,11, Font.BOLD)));
			  parameterCell.setFixedHeight(25f);
			  parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			  parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			  parameterTable.addCell(parameterCell);
			  
			  parameterCell = new PdfPCell(new Phrase("MD TIME",new
			  Font(Font.FontFamily.HELVETICA ,11, Font.BOLD)));
			  parameterCell.setFixedHeight(25f);
			  parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			  parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			  parameterTable.addCell(parameterCell);
			}

			for (int i = 0; i < DTData2.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Object[] obj = DTData2.get(i);
				for (int j = 0; j < obj.length; j++) {
					if (j == 0) {
						String value1 = obj[0] + "";
						parameterCell = new PdfPCell(new Phrase(obj[3] == null ? null : obj[3] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[4] == null ? null : obj[4] + "-" + obj[11] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[5] == null ? null : obj[5] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[6] == null ? null : obj[6] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[7] == null ? null : obj[7] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[8] == null ? null : obj[8] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[9] == null ? null : obj[9] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[22] == null ? null : obj[22] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[10] == null ? null : obj[10] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[11] == null ? null : obj[11] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[12] == null ? null : obj[12] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[13] == null ? null : obj[13] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[15] == null ? null : obj[15] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[16] == null ? null : obj[16] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[17] == null ? null : obj[17] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[18] == null ? null : obj[18] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[19] == null ? null : obj[19] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[20] == null ? null : obj[20] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[14] == null ? null : obj[14] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

//						parameterCell = new PdfPCell(new Phrase(obj[21] == null ? null : obj[21] + "",
//								new Font(Font.FontFamily.HELVETICA, 11)));
//						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//						parameterCell.setFixedHeight(30f);
//					parameterTable.addCell(parameterCell);

					
						if (period.equalsIgnoreCase("Month"))
						{	  parameterCell = new PdfPCell(new Phrase( obj[21]==null?null:obj[22]+"",new
						  Font(Font.FontFamily.HELVETICA ,11 )));
						  parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						  parameterCell.setFixedHeight(30f); parameterTable.addCell(parameterCell);
						  
						  parameterCell = new PdfPCell(new Phrase( obj[21]==null?null:obj[23]+"",new
						  Font(Font.FontFamily.HELVETICA ,11 )));
						  parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						  parameterCell.setFixedHeight(30f); parameterTable.addCell(parameterCell);
						}
						 

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
			response.setHeader("Content-disposition", "attachment; filename=DtConsumption.pdf");
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
	public void getdtLoadSummpdf(HttpServletRequest request, HttpServletResponse response, String zone1, String crcl,
			String twn, String month,String townname1) {
System.out.println("townname1--->"+townname1);
		String zone = "", circle = "", town = "", townname = "";
		try {
			if (zone1 == "%") {
				zone = "ALL";
			} else {
				zone = zone1;
			}
			if (crcl == "%") {
				circle = "ALL";
			} else {
				circle = crcl;
			}
			if(twn=="%")
			{
				town="ALL";
			}else {
				town=twn;
			}
			if(townname1=="%")
			{
				townname="ALL";
			}else {
				townname=townname1;
			}

			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
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
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(pstart);

			document.add(pdf2);
			PdfPCell cell2 = new PdfPCell();
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase("DT Loading Summary ", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			PdfPTable header = new PdfPTable(2);
			header.setWidths(new int[] {1,1});
			header.setWidthPercentage(100);

			PdfPCell headerCell = null;
			headerCell = new PdfPCell(
					new Phrase("Region :" +zone, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			
			headerCell = new PdfPCell(
					new Phrase("Circle :" +circle, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
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
			 * 
			 * headerCell = new PdfPCell(new Phrase("Sub-Division :"+subdiv,new
			 * Font(Font.FontFamily.HELVETICA ,10, Font.BOLD)));
			 * headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			 * headerCell.setFixedHeight(20f); headerCell.setBorder(PdfPCell.NO_BORDER);
			 * header.addCell(headerCell);
			 */

			headerCell = new PdfPCell(new Phrase("Town :" +townname1, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("Month Year :" +month, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));


			document.add(header);

			String query = "";
			List<Object[]> DtloadSummData = null;
			/*
			 * query =
			 * "select b.month_year,a.subdivision,a.town_ipds,a.totalcount,b.overload,b.underload,b.unbalance from (\n"
			 * +
			 * "SELECT count(dt.*) as totalcount,ami.subdivision,ami.town_ipds,dt.officeid from meter_data.dtdetails dt, meter_data.amilocation ami,meter_data.master_main mm\n"
			 * + "WHERE ami.zone LIKE '" + zone1 + "' and ami.circle LIKE '" + crcl +
			 * "' and ami.tp_towncode like '" + twn +
			 * "'  and (dt.crossdt IS NULL OR dt.crossdt =0) and dt.officeid=ami.sitecode  and dt.meterno=mm.mtrno and  ami.tp_towncode=mm.town_code\n"
			 * + "GROUP BY ami.subdivision, dt.officeid,ami.town_ipds\n" + ")a,\n" +
			 * "(SELECT COUNT(CASE WHEN overload ='t' THEN 1 END) as overload ,\n" +
			 * "COUNT(CASE WHEN underload ='t' THEN 1 END) as underload ,\n" +
			 * "COUNT(CASE WHEN unbalance ='t' THEN 1 END) as unbalance ,month_year,\n" +
			 * "cast(office_id as text )  FROM meter_data.dt_health_rpt B WHERE cast(office_id as text ) in(SELECT cast(sitecode as TEXT) FROM meter_data.amilocation WHERE zone like '"
			 * + zone1 + "' and circle LIKE '" + crcl + "' and tp_towncode like '" + twn +
			 * "' )  and month_year='" + month + "' GROUP BY office_id,month_year)b \n" +
			 * "where cast(a.officeid as text )=b.office_id";
			 */
			
			query="select b.month_year,a.subdivision,a.town_ipds,b.dtCount,b.overload,b.underload,b.unbalance from (\n" +
					"SELECT ami.subdivision,ami.town_ipds,dt.officeid from meter_data.dtdetails dt, meter_data.amilocation ami,meter_data.master_main mm\n" +
					"WHERE ami.zone LIKE '"+zone1+"' and ami.circle LIKE '"+crcl+"' and ami.tp_towncode like '"+twn+"'  and (dt.crossdt IS NULL OR dt.crossdt =0) and dt.officeid=ami.sitecode  and dt.meterno=mm.mtrno and  ami.tp_towncode=mm.town_code\n" +
					"GROUP BY ami.subdivision, dt.officeid,ami.town_ipds\n" +
					")a,\n" +
					"(SELECT COUNT(CASE WHEN overload ='t' THEN 1 END) as overload ,\n" +
					"COUNT(CASE WHEN underload ='t' THEN 1 END) as underload ,\n" +
					"Count(tp_dt_id) as dtCount,\n" +
					"COUNT(CASE WHEN unbalance ='t' THEN 1 END) as unbalance ,month_year,\n" +
					"cast(office_id as text )  FROM meter_data.dt_health_rpt B WHERE cast(office_id as text ) in(SELECT cast(sitecode as TEXT) FROM meter_data.amilocation WHERE zone LIKE '"+zone1+"' and circle LIKE '"+crcl+"' and tp_towncode like '"+twn+"' )  and month_year='"+month+"' GROUP BY office_id,month_year)b \n" +
					"where cast(a.officeid as text )=b.office_id";
			System.out.println("query--->"+query);
			DtloadSummData = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(8);
			parameterTable.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.NO", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Month Year", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Sub Division", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Town", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Total DT", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Overloaded DT", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Underloaded DT", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Unbalanced DT", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < DtloadSummData.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Object[] obj = DtloadSummData.get(i);
				for (int j = 0; j < obj.length; j++) {
					if (j == 0) {
						String value1 = obj[0] + "";
						parameterCell = new PdfPCell(new Phrase(obj[0] == null ? null : obj[0] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[1] == null ? null : obj[1] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[2] == null ? null : obj[2] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(
								new Phrase(obj[3] == null ? null : obj[3] == null ? null : obj[3] + "",
										new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[4] == null ? null : obj[4] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[5] == null ? null : obj[5] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[6] == null ? null : obj[6] + "",
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
			response.setHeader("Content-disposition", "attachment; filename=DTLoadingSummary.pdf");
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
	public DtDetailsEntity getDtByFdrcodeMrtId(String dtcode, String feedercode, String meterno, String towncode) {
		DtDetailsEntity result = null;
		try {
			result = getCustomEntityManager("postgresMdas")
					.createNamedQuery("DtDetailsEntity.getDtByFdrcodeMrtId", DtDetailsEntity.class)
					.setParameter("dttpid", dtcode).setParameter("tpparentid", feedercode)
					.setParameter("meterno", meterno).setParameter("tp_town_code", towncode).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	

}
