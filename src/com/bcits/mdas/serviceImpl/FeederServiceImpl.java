package com.bcits.mdas.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.FeederEntity;
import com.bcits.entity.MeterInventoryEntity;
import com.bcits.mdas.entity.AssignsEstimationRuleEntity;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.service.FeederDetailsService;
import com.bcits.serviceImpl.GenericServiceImpl;
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

@Service
public class FeederServiceImpl extends GenericServiceImpl<FeederEntity> implements FeederDetailsService {

	@Override
	public List<?> getDistinctsubdivision() {
		List<?> list = new ArrayList();
		try {
			String sql ="SELECT DISTINCT SUBDIVISION FROM METER_DATA.AMILOCATION WHERE SUBDIVISION  IS NOT NULL AND SUBDIVISION <>'' ORDER BY SUBDIVISION; ";
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("List==="+list);
		return list;
	}

	@Override
	public List<?> getcircle() {
		List<?> list = new ArrayList();
		try {
			String sql = "SELECT DISTINCT circle from meter_data.amilocation where zone='ERODE'";
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getfeederdetails(String Region,String Circle,String town,String feedertype) {
		List<?> list=null;
		String sql="";
		try {
				
			
		    
			/*
			 * String
			 * sql="SELECT 	fd.ID,	feedername,	voltagelevel,	am.circle,	am.subdivision,	fd.officeid,	parentid,	tp_fdr_id,	tpparentid,	COALESCE(meterno,'')AS meterno,"
			 * +
			 * "	manufacturer,	mf,	consumppercent,	fdr_id,	entryby,	entrydate,	updateby,	updatedate,	crossfdr ,feeder_type FROM	meter_data.feederdetails "
			 * +
			 * "fd, (SELECT circle, subdivision,sitecode  from	meter_data.amilocation GROUP BY circle, subdivision,sitecode) am WHERE fd.officeid = am.sitecode "
			 * +
			 * "and (fd.deleted IS NULL OR fd.deleted=0) and crossfdr='0' and am.subdivision='THIRUKALUKUNDRAM' order by fd.id"
			 * ;
			 */
			 if("OTHERS".equalsIgnoreCase(feedertype)) {
				 sql="SELECT   distinct on (fd.meterno)  fd.ID ,am.circle, feedername, voltagelevel, am.subdivision, fd.officeid,    parentid,    tp_fdr_id,    tpparentid, mm.substation,   COALESCE(meterno,'')AS meterno,    manufacturer,    mf,    consumppercent,    fdr_id,    entryby,to_char(entrydate,'yyyy-mm-dd HH24:MI:SS')as entrydate,    updateby,to_char(updatedate,'yyyy-mm-dd HH24:MI:SS')as updatedate,    crossfdr, fd.feeder_type,tp_town_code,town_ipds,fd.geo_cord_x,fd.geo_cord_y,am.zone from meter_data.feederdetails fd, (SELECT zone,circle, subdivision,sitecode,tp_towncode,town_ipds  from    meter_data.amilocation GROUP BY zone,circle, subdivision,sitecode,tp_towncode,town_ipds) am , (select fdrtype,fdrcode,longitude,latitude,substation from meter_data.master_main where fdrcategory='FEEDER METER'   GROUP BY  fdrtype,fdrcode,longitude,latitude,substation)mm WHERE  fd.fdr_id=mm.fdrcode  and fd.officeid = am.sitecode and (fd.deleted IS NULL OR fd.deleted=0) and crossfdr='0' and am.zone like '"+Region+"' and am.circle like '"+Circle+"' and am.subdivision like '%' and am.tp_towncode like '"+town+"' and fd.feeder_type NOT IN ('IPDS','NON-IPDS','GC','LV') \n" +
		                    "";
				 
			 }else {
				  sql="SELECT  distinct on (fd.meterno)  fd.ID ,am.circle, feedername, voltagelevel, am.subdivision, fd.officeid,    parentid,    tp_fdr_id,    tpparentid, mm.substation,   COALESCE(meterno,'')AS meterno,    manufacturer,    mf,    consumppercent,    fdr_id,    entryby,to_char(entrydate,'yyyy-mm-dd HH24:MI:SS')as entrydate,    updateby,to_char(updatedate,'yyyy-mm-dd HH24:MI:SS')as updatedate,    crossfdr, fd.feeder_type,tp_town_code,town_ipds,fd.geo_cord_x,fd.geo_cord_y,am.zone from meter_data.feederdetails fd, (SELECT zone,circle, subdivision,sitecode,tp_towncode,town_ipds  from    meter_data.amilocation GROUP BY zone,circle, subdivision,sitecode,tp_towncode,town_ipds) am , (select fdrtype,fdrcode,longitude,latitude,substation from meter_data.master_main where fdrcategory='FEEDER METER'   GROUP BY  fdrtype,fdrcode,longitude,latitude,substation)mm WHERE  fd.fdr_id=mm.fdrcode  and fd.officeid = am.sitecode and (fd.deleted IS NULL OR fd.deleted=0) and crossfdr='0' and am.zone like '"+Region+"' and am.circle like '"+Circle+"' and am.subdivision like '%' and am.tp_towncode like '"+town+"' and fd.feeder_type like '"+feedertype+"'\n" +""; 
			 }
			 
			 System.out.println(sql);
			 
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<?> getEditDetailsById(String id) {
		List<?> list=null;
		try {
			String sql="select feedername,voltagelevel,parentid,tp_fdr_id,tpparentid,meterno,mf,consumppercent,id,feeder_type,geo_cord_x,geo_cord_y,manufacturer from meter_data.feederdetails where id="+id+"";
			System.out.println(sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	

	

	@Override
	public Object getSSnameBySubdiv(String town) {
		
		List<?> list=null;
		try {
			String sql="select ss_id,ss_name from meter_data.substation_details where office_id in (select sitecode from meter_data.amilocation where  town_ipds like '"+town+"') "
					+ " and ( deleted is null or  deleted = 'No' )"; 
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Object getSSnameByofficeID(String officeId) {
		
		List<?> list=null;
		try {
			String sql="select ss_id,ss_name from meter_data.substation_details where office_id = '"+officeId+"'"; 
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
 
	@Override
	public String getfdrid() {
		String qry="";
		String result=null;
		try {
			
			qry = "SELECT B. rule||(case WHEN length (B.id)=1 then '0'||B.id else B.id  END) from  "
					+ "(SELECT substr(A.fdr_id, 0,8) as rule,CAST(CAST(substr(A.fdr_id, 8,length(A.fdr_id))as INTEGER)+1 as TEXT) as id"
					+ " FROM (SELECT COALESCE(max(fdr_id),'FDR000000') as fdr_id FROM meter_data.feederdetails)A )B ";
			
						
			/*qry=" SELECT B. rule||(case WHEN length (B.id)=1 then '0'||B.id else B.id  END) from  \n" +
	                "( \n" +
	                "SELECT substr(A.fdr_id, 0,4) as rule,CAST(CAST(substr(A.fdr_id, 4,length(A.fdr_id))as INTEGER)+1 as TEXT) as id FROM \n" +
	                "( \n" +
	                "SELECT COALESCE(max(fdr_id),'FDR00') as fdr_id FROM meter_data.feederdetails \n" +
	                ")A \n" +
	                 ")B ;";*/
			result=(String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	@Override
	public Object getFdrIdNamenameBySubdiv(String parentid, String sdoCode) {
		List<?> list = new ArrayList();
		try {
			//String sql = "SELECT DISTINCT fdr_id||'-'||feedername from meter_data.feederdetails where parentid= '"+parentid+"' and deleted ='0' and  crossfdr = '0' ";
			String sql = " SELECT DISTINCT fdr_id||'-'||feedername from meter_data.feederdetails where parentid= '"+parentid+"' AND(deleted = '0' or deleted is null) and crossfdr = '0' AND officeid in ( SELECT sitecode FROM meter_data.amilocation WHERE subdivision like '"+sdoCode+"')";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Object getFdrIdNamenameBySubdivandSubstat(String parentid, String officeId) {
		List<?> list = new ArrayList();
		try {
			String sql = "SELECT DISTINCT fdr_id||'-'||feedername from meter_data.feederdetails where parentid= '"+parentid+"' and officeid ='"+officeId+"' and   deleted IS NULL OR deleted=0 and  crossfdr = '0'  ";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public Object getsubdivByCircle(String circle) {
		List<?> list = new ArrayList();
		try {
			String sql ="select distinct subdivision from meter_data.amilocation where circle= '"+circle+"'";
			System.out.println(sql);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public Object isfeederAttached(int id) {
		List<?> list = new ArrayList();
		try {
			 String qry = "select *from meter_data.substation_details where parent_feeder in (select fdr_id from meter_data.feederdetails where id = '"+id+"') ";
			//System.out.println(qry);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public Object isDtAttached(int id) {
		List<?> list = new ArrayList();
		try {
			 String qry = "select *from meter_data.dtdetails where parentid in (select fdr_id from meter_data.feederdetails where id = '"+id+"') ";
			//System.out.println(qry);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Object isConsumerAttached(int id) {
		List<?> list = new ArrayList();
		try {
			 String qry = "select *from meter_data.consumermaster where feedercode in (select fdr_id from meter_data.feederdetails where id = '"+id+"') ";
			System.out.println(qry);
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Object deletemethod(int deleteId) {
		
		int result = 0 ;
		try {
			 String qry = "update meter_data.feederdetails set deleted = '1' where id ='"+deleteId+"'";
			//System.out.println(qry);
		result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
		
	}
	
	
	@Override
	public Object getFeederDetails(String feederId) {
		
		 String qry  = "select meterno, manufacturer, mf , consumppercent, voltagelevel,id\r\n" + 
			  		"from meter_data.feederdetails where fdr_id = '"+feederId +"' ";

		 List<?> result = null;
		 
		try {
		result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public Object isMeterNoAvailable(String meterNo) {
		
		 String qry = "select count(*) from meter_data.feederdetails where meterno = '"+meterNo+"' ";

		 List<?> result = null;
		 
		try {
		result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	@Override
	public Object validateMeterNO(String meterNo) {
		
		 String qry = "select meterno , meter_make,meter_status from meter_data.meter_inventory where meterno = '"+meterNo+"' and meter_status = 'INSTOCK' ";

		 List<?> result = null;
		 
		try {
		result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}


	@Override
	public List<?> getDistinctZone() {
		List<?> list = new ArrayList();
		try {
			String sql = "SELECT DISTINCT zone from meter_data.amilocation order by zone";
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	 



	@Override
	public int setMeterInventoflag(String meterNo, String userName){
		long currtime = System.currentTimeMillis();
		Timestamp updateDate =  new Timestamp(currtime);
		
		String qry = "update meter_data.meter_inventory SET meter_status='INSTALLED',updateby='"+userName+"',updatedate= '"+updateDate+"' WHERE meterno='"+meterNo+"'";
	
		int result = 0 ;
		try {
			result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;		
			
			

		
	}
	
	public HashMap<String, String> getlocationHireachy(String subdivsion){/*
		String qry = "Select zone, circle, division, subdivision from meter_data.amilocation where subdivision = '"+subdivsion+"'" ;
		List<?> result = null;
		try {
			result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	*/

		HashMap<String, String> h=new HashMap<>();
		String qry = "";
		try {
			
			qry = "Select distinct zone, circle, division, subdivision from meter_data.amilocation where lower(subdivision) = lower('"+subdivsion+"')";
			Object[] list = (Object[]) getCustomEntityManager("postgresMdas")
					.createNativeQuery(qry).getSingleResult();
			
				h.put("ZONE", list[0]+"");
				h.put("CIRCLE", list[1]+"");
				h.put("DIVISION", list[2]+"");
				h.put("SUBDIVISION", list[3]+"");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return h;
	}

	@Override
	public Object getSSnameAndFeederNameBySubdiv(String subdivision) {
		
		List<?> list=null;
		try {
			String sql="select ss_id,ss_name, from meter_data.substation_details where office_id in (select sitecode from meter_data.amilocation where subdivision ='"+subdivision+"')"; 
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<?> getMeterDetailsByFdrcode(String fdrcode) {
		try {
			
			String sql = "SELECT boundary_id,boundary_name,boundary_location,meterno,manufacturer,ct_ratio,pt_ratio,mf,meter_ratio,imp_exp,meter_installed from meter_data.feederdetails where tp_fdr_id='"+fdrcode+"' and crossfdr='1' and boundry_feeder=true and deleted=0;";
         //   System.out.println("-----------------List off Boundary meter based on feeder------------------"+sql);
			List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			return li;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	@Override
	public List<?> getBoundaryMeterDetailsByTown(String townCode,String ssid,String circle,String zone) {
		try {
			
			//System.out.println("Hi...........................");
			
			//String sql = "SELECT boundary_id,boundary_name,boundary_location,meterno,manufacturer,ct_ratio,pt_ratio,mf,meter_ratio,imp_exp,meter_installed from meter_data.feederdetails where tp_fdr_id='"+fdrcode+"' and crossfdr='1' and boundry_feeder=true and deleted=0;";
         //   System.out.println("-----------------List off Boundary meter based on feeder------------------"+sql);
			String sql="SELECT distinct on (f.meterno) f.tp_fdr_id,f.boundary_id,f.boundary_name,f.boundary_location,f.meterno,f.manufacturer,f.ct_ratio,f.pt_ratio,f.mf,f.meter_ratio,f.imp_exp,f.meter_installed,a.town_ipds,m.fdrname,m.latitude,m.longitude,a.zone,a.circle  from meter_data.feederdetails f, meter_data.master_main m,meter_data.amilocation a where  m.town_code=a.tp_towncode  and tp_town_code like '"+townCode+"'  and tpparentid like '"+ssid+"' and crossfdr='1' and m.circle like '"+circle+"'  and m.zone like '"+zone+"'  and boundry_feeder=true and deleted=0 and m.fdrcategory = 'BOUNDARY METER' and meterno is not null";
			System.out.println(sql);
			List<?> li=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			return li;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	@Override
	public FeederEntity getDetailsById(String bdrid) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("FeederEntity.getDetailsById", FeederEntity.class)
					.setParameter("boundary_id", bdrid)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object isFeederCodeAvailable(String fdrCode) {
		
		 String qry = "select count(*) from meter_data.feederdetails where tp_fdr_id = '"+fdrCode+"' ";

		 List<?> result = null;
		 
		try {
		result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> getDistinctCategory() {
		List<?> res=null;
		try
		{
			String sql="SELECT DISTINCT fdrcategory from meter_data.master_main WHERE fdrcategory IS NOT NULL";
			res=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public List<String> getFeederAllMeters() {
		

		 List<String> result = new ArrayList<>();
		 
		try {
		result = postgresMdas.createNamedQuery("FeederEntity.meterNumbers").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object getfdrIdByTpfeederId(String feedercode,String tp_towncode) {	
		
		Object result=new ArrayList<MeterInventoryEntity>();
		try {

			String qry="select feedername,fdr_id from meter_data.feederdetails where tp_fdr_id='"+feedercode+"' and crossfdr='0' AND (deleted = '0' or deleted is null) and tp_town_code='"+tp_towncode+"'";
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList().get(0);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FeederEntity> getFeederDetailsByFeederId(String feedercode, String towncode) {
		List<FeederEntity> result=new ArrayList<FeederEntity>();
		try {
		result=getCustomEntityManager("postgresMdas").createNamedQuery("FeederEntity.getFeederDetailsByFeederId")
				.setParameter("tpfdrid", feedercode)
				.setParameter("tpTownCode", towncode)
				.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getSSIdByTpsubstationId(String sstp_id, String towncode) {
		String qry="";
		String result=null;
		try {
			
			qry = "select ss_id from meter_data.substation_details where parent_town='"+towncode+"' and sstp_id='"+sstp_id+"'";
			System.out.println(qry);
			result=(String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FeederEntity getBoundaryDetailsByFeederId(String boundaryid, String feedercode, String towncode) {
		FeederEntity result=null;
		try {
		result= getCustomEntityManager("postgresMdas").createNamedQuery("FeederEntity.getBoundaryDetailsByFeederId",FeederEntity.class)
				.setParameter("tpfdrid", feedercode)
				.setParameter("tpTownCode", towncode)
				.setParameter("boundary_id", boundaryid)
				.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getFeederId(String feedercode,String sstp_id, String towncode) {
		String qry="";
		String result=null;
		try {
			
			qry = "select DISTINCT fdr_id from meter_data.feederdetails where tp_fdr_id= '"+feedercode+"' AND(deleted = '0' or deleted is null) and crossfdr = '0' AND tp_town_code   ='"+towncode+"' and tpparentid='"+sstp_id+"'";
			result=(String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public String getFeederIdbyTpfdrId(String feedercode) {
		String qry="";
		String result=null;
		try {
			
			qry = "select DISTINCT fdr_id from meter_data.feederdetails where tp_fdr_id= '"+feedercode+"' AND(deleted = '0' or deleted is null) and crossfdr = '0'";
			result=(String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<FeederEntity> getFeederByMeterno(String meterno) {
		try {
			return getCustomEntityManager("postgresMdas").createNamedQuery("FeederEntity.getFeederDetailsByMeterno")
					.setParameter("meterno", meterno)
					.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public FeederEntity getMtrExistOnFeeder(String feedercode) {
		
		FeederEntity result=null;
		try {
		result=getCustomEntityManager("postgresMdas").createNamedQuery("FeederEntity.getMtrExistOnFeeder",FeederEntity.class)
				.setParameter("tpfdrid", feedercode)
				.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<FeederEntity> checkMeterAttachedonFeeder(String feedercode, String meterid) {
		List<FeederEntity> result=new ArrayList<FeederEntity>();
		try {
		result=getCustomEntityManager("postgresMdas").createNamedQuery("FeederEntity.checkMeterAttachedonFeeder")
				.setParameter("tpfdrid", feedercode)
				.setParameter("meterno", meterid)
				.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void getFdrDtlspdf(HttpServletRequest request, HttpServletResponse response, String region, String circle, String town, String feederType,String townname) {
		System.out.println("region--->"+ region);
		System.out.println("circle--->"+ circle);
		System.out.println("town--->"+ town);
		try {
			String reg="",cir="",twn="",feeder="";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
			LocalDateTime sysdatetime = LocalDateTime.now();
			if (region.equalsIgnoreCase("%")) {
				reg = "ALL";
			} else {
				reg = region;
			}
			if (circle.equalsIgnoreCase("%")) {
				cir = "ALL";
			} else {
				cir = circle;
			}
			if (town.equalsIgnoreCase("%")) {
				twn = "ALL";
			} else {
				twn = town;
			}
			
			if (feederType.equalsIgnoreCase("%")) {
				feeder = "ALL";
			} else {
				feeder = feederType;
			}
			
			
			
			Rectangle pageSize = new Rectangle(1850, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);		
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
		    p1.add(new Phrase("Feeder Details",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(4);
		    header.setWidths(new int[]{1,1,1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Region :"+reg,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);		    
		    
		    headerCell = new PdfPCell(new Phrase("Circle :"+cir,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);		    		    
		    
		    headerCell = new PdfPCell(new Phrase("Town Name :"+townname,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Feeder Type :"+feeder,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Current Date&Time :"+sysdatetime,new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.RIGHT));
		    
		    document.add(header);
		    
		    String query="";
		    List<?> FeederDetailsData = null;
			FeederDetailsData=getfeederdetails(region, circle, town, feederType);
			
			//Old Query
//			query="SELECT  feedername,    voltagelevel,    am.circle,    am.subdivision,    fd.officeid,    parentid,    tp_fdr_id,    tpparentid,    COALESCE(meterno,'')AS meterno,    manufacturer,    mf,  fd.geo_cord_x, fd.geo_cord_y,   consumppercent,    fdr_id,    entryby,to_char(entrydate,'yyyy-mm-dd HH24:MI:SS')as entrydate,    updateby,to_char(updatedate,'yyyy-mm-dd HH24:MI:SS')as updatedate,    crossfdr, fd.feeder_type,tp_town_code,town_ipds from meter_data.feederdetails fd, (SELECT zone,circle, subdivision,sitecode,tp_towncode,town_ipds  from    meter_data.amilocation GROUP BY zone,circle, subdivision,sitecode,tp_towncode,town_ipds) am , (select fdrtype,fdrcode,longitude,latitude from meter_data.master_main where fdrcategory='FEEDER METER'   GROUP BY  fdrtype,fdrcode,longitude,latitude)mm WHERE  fd.fdr_id=mm.fdrcode  and fd.officeid = am.sitecode and (fd.deleted IS NULL OR fd.deleted=0) and crossfdr='0' and am.zone like '"+region+"' and am.circle like '"+circle+"' and am.subdivision like '%' and am.tp_towncode like '"+town+"'\n" +
//                    "order by fd.id";
			
			//query ="SELECT  feedername,    voltagelevel,    am.circle,   town_ipds,    tp_fdr_id,    tpparentid,    COALESCE(meterno,'')AS meterno,    manufacturer,    mf,  fd.geo_cord_x, fd.geo_cord_y,fd.feeder_type,entryby,to_char(entrydate,'yyyy-mm-dd HH24:MI:SS')as entrydate,updateby,to_char(updatedate,'yyyy-mm-dd HH24:MI:SS')as updatedate,tp_town_code from meter_data.feederdetails fd, (SELECT zone,circle, subdivision,sitecode,tp_towncode,town_ipds  from    meter_data.amilocation GROUP BY zone,circle, subdivision,sitecode,tp_towncode,town_ipds) am , (select fdrtype,fdrcode,longitude,latitude from meter_data.master_main where fdrcategory='FEEDER METER'   GROUP BY  fdrtype,fdrcode,longitude,latitude)mm WHERE  fd.fdr_id=mm.fdrcode  and fd.officeid = am.sitecode and (fd.deleted IS NULL OR fd.deleted=0) and crossfdr='0' and am.zone like '"+region+"' and am.circle like '"+circle+"' and am.subdivision like '%' and am.tp_towncode like '"+town+"' and fd.feeder_type like '"+feederType+"' AND fd.feeder_type IN ('IPDS','NONIPDS','GC','LV') \n" +
                    //"order by fd.id";
			//System.out.println(query);
			//FeederDetailsData=postgresMdas.createNativeQuery(query).getResultList();

			
			   PdfPTable parameterTable = new PdfPTable(17);
	           parameterTable.setWidths(new int[]{1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Circle Name",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);      //Feeder Name,Voltage Level,Circle Name
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Feeder Name",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Voltage Level",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Town Name",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder Code",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Sub Station Code",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter Sr Number",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter Manufacturer",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("MF",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Latitude",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Longitude",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder Type",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Entry By",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Entry Date ",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Update By",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Update Date",new Font(Font.FontFamily.HELVETICA  ,9, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           
	           
	           for (int i = 0; i < FeederDetailsData.size(); i++) 
	            {

	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=(Object[]) FeederDetailsData.get(i);
	            	for (int j = 0; j < obj.length; j++) 
	            	{
	            		if(j==0)
	            		{
	            			String value1=obj[0]+"";
	            			 parameterCell = new PdfPCell(new Phrase(obj[1]==null?null:obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[2]==null?null:obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[4]==null?null:obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							
							 
							 parameterCell = new PdfPCell(new Phrase(obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
							 
							 parameterCell = new PdfPCell(new Phrase( obj[22]==null?null:obj[22]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[23]==null?null:obj[23]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[19]==null?null:obj[19]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
							 
							 parameterCell = new PdfPCell(new Phrase( obj[17]==null?null:obj[17]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=FeederDetails.pdf");
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
	public FeederEntity getBoundaryByFeederMrtId(String boundaryid, String feedercode, String meterid) {
		FeederEntity result=null;
		try {
		result=getCustomEntityManager("postgresMdas").createNamedQuery("FeederEntity.getBoundaryByFeederMrtId",FeederEntity.class)
				.setParameter("tpfdrid", feedercode)
				.setParameter("boundary_id", boundaryid)
				.setParameter("meterno", meterid)
				.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public FeederEntity getFeederBySubstationMrtId(String towncode,String feedercode, String substationcode, String meterid) {
		FeederEntity result=null;
		try {
			result=getCustomEntityManager("postgresMdas").createNamedQuery("FeederEntity.getFeederBySubstationMrtId",FeederEntity.class)
					.setParameter("tpTownCode", towncode)
					.setParameter("tpfdrid", feedercode)
					.setParameter("tpparentid", substationcode)
					.setParameter("meterno", meterid)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<?> getAllipdsnonipdsFeederList(String region, String circle, String town, String category) {
		
		List<?> list=null;
	String sql="";
			
			 if(category.equalsIgnoreCase("ipds")) {
				 sql=" SELECT     fd.ID,    feedername,    voltagelevel,"
							+ "am.circle,    am.subdivision,    fd.officeid,    parentid,    tp_fdr_id,"
							+ "tpparentid,    COALESCE(meterno,'')AS meterno,    manufacturer,    mf,    consumppercent,"
							+ "fdr_id,    entryby,    entrydate,    updateby,    updatedate,    crossfdr, mm.fdrtype FROM"
							+ " meter_data.feederdetails fd, (SELECT zone,circle, subdivision,sitecode,tp_towncode  from    meter_data.amilocation GROUP BY zone,circle, subdivision,sitecode,tp_towncode) am,"
							+ "(select fdrtype,fdrcode from meter_data.master_main where fdrcategory='FEEDER METER' GROUP BY  fdrtype,fdrcode)mm WHERE  fd.fdr_id=mm.fdrcode  and fd.officeid = am.sitecode and (fd.deleted IS NULL OR fd.deleted=0)"
							+ "and crossfdr='0' and am.zone like '"+region+"'"
							+ "and am.circle like '"+circle+"' and am.subdivision like '%' and am.tp_towncode like '"+town+"' and fdrtype like 'IPDS' \n" +
							"order by fd.id";
				// System.out.println(sql);
			}
			
          else if(category.equalsIgnoreCase("nonipds")) {
        	  sql=" SELECT     fd.ID,    feedername,    voltagelevel,"
  					+ "am.circle,    am.subdivision,    fd.officeid,    parentid,    tp_fdr_id,"
  					+ "tpparentid,    COALESCE(meterno,'')AS meterno,    manufacturer,    mf,    consumppercent,"
  					+ "fdr_id,    entryby,    entrydate,    updateby,    updatedate,    crossfdr, mm.fdrtype FROM"
  					+ " meter_data.feederdetails fd, (SELECT zone,circle, subdivision,sitecode,tp_towncode  from    meter_data.amilocation GROUP BY zone,circle, subdivision,sitecode,tp_towncode) am,"
  					+ "(select fdrtype,fdrcode from meter_data.master_main where fdrcategory='FEEDER METER' GROUP BY  fdrtype,fdrcode)mm WHERE  fd.fdr_id=mm.fdrcode  and fd.officeid = am.sitecode and (fd.deleted IS NULL OR fd.deleted=0)"
  					+ "and crossfdr='0' and am.zone like '"+region+"'"
  					+ "and am.circle like '"+circle+"' and am.subdivision like '%' and am.tp_towncode like '"+town+"'  and fdrtype like 'NONIPDS'\n" +
  					"order by fd.id";
        	  //System.out.println(sql);
			}
			 
          else if(category.equalsIgnoreCase("ipds,nonipds")  )  {
        	  sql=" SELECT     fd.ID,    feedername,    voltagelevel,"
    					+ "am.circle,    am.subdivision,    fd.officeid,    parentid,    tp_fdr_id,"
    					+ "tpparentid,    COALESCE(meterno,'')AS meterno,    manufacturer,    mf,    consumppercent,"
    					+ "fdr_id,    entryby,    entrydate,    updateby,    updatedate,    crossfdr, mm.fdrtype FROM"
    					+ " meter_data.feederdetails fd, (SELECT zone,circle, subdivision,sitecode,tp_towncode  from    meter_data.amilocation GROUP BY zone,circle, subdivision,sitecode,tp_towncode) am,"
    					+ "(select fdrtype,fdrcode from meter_data.master_main where fdrcategory='FEEDER METER' GROUP BY  fdrtype,fdrcode)mm WHERE  fd.fdr_id=mm.fdrcode  and fd.officeid = am.sitecode and (fd.deleted IS NULL OR fd.deleted=0)"
    					+ "and crossfdr='0' and am.zone like '"+region+"'"
    					+ "and am.circle like '"+circle+"' and am.subdivision like '%' and am.tp_towncode like '"+town+"'\n" +
    					"order by fd.id";
        	  //System.out.println(sql);
          }
			try {
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}

	@Override
	public Object isFdrcodeAvail(String fdrcode) {
		String qry = "select count(*) from meter_data.feederdetails where tp_fdr_id = '"+fdrcode+"' ";

		 List<?> result = null;
		 
		try {
		result = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<?> getfeederOutageReport(String month,String feeder,String town,String circle) {
		List<?> list=null;
		try {
//			String sql="select eventmonth,circle,town,feedercode,feedername,meterid,totalevents,((split_part(cast(diff as varchar),':',1)||' hours ')||(split_part(cast(diff as varchar),':',2)||' mins ')||(split_part(cast(diff as varchar),':',3)||' secs ')) as eventduration from\n" +
//					"(SELECT to_char(date,'yyyyMM') as eventmonth,circle,town,feedercode,feedername,meterid, sum(totalnooccurence) as totalevents,justify_interval(make_interval(secs := sum(totalpowerfailureduration))) as diff FROM meter_data.power_onoff where feedercode like '"+feeder+"' and to_char(date, 'yyyyMM') ='"+month+"'\n" +
//					"GROUP BY eventmonth,circle,town,feedercode,feedername,meterid)X";
			String sql="SELECT to_char(date, 'yyyyMM') as eventmonth,circle,town,feedercode,feedername,meterid, sum(totalnooccurence) as totalevents,split_part(CAST(sum(totalpowerfailureduration) / (60*60*24) as VARCHAR),'.',1) || ' Days ' ||\n" +
					"split_part(CAST((sum(totalpowerfailureduration) % (60*60*24) / 3600) as VARCHAR),'.',1) || ' Hours ' ||\n" +
					"split_part(CAST(((sum(totalpowerfailureduration) % (60*60*24) % 3600) / 60) as VARCHAR),'.',1) || ' Minutes ' || CAST((sum(totalpowerfailureduration) % 60) as VARCHAR) || ' Second' AS event_duration\n" +
					"FROM meter_data.power_onoff where circlecode like'"+circle+"'and towncode like '"+town+"' and feedercode like '"+feeder+"' and to_char(date, 'yyyyMM') ='"+month+"'\n" +
					"GROUP BY eventmonth,circle,town,feedercode,feedername,meterid ";
			System.out.println(sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	//feederoutagepdf
	
	@Override
	public void getfeederOutagePdf(String month,String feeder,String town,String circle,HttpServletResponse response, HttpServletRequest request) {
	
		try {
			
			Rectangle pageSize = new Rectangle(1050, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//PdfWriter.getInstance(document, baos);
			PdfWriter writer =PdfWriter.getInstance(document, baos);
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);
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
		    p1.add(new Phrase("FeederOutageSummary",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> CirwiseMtrData=null;
			
			query="SELECT to_char(date, 'yyyyMM') as eventmonth,circle,town,feedercode,feedername,meterid, sum(totalnooccurence) as totalevents,split_part(CAST(sum(totalpowerfailureduration) / (60*60*24) as VARCHAR),'.',1) || ' Days ' ||\n" +
					"split_part(CAST((sum(totalpowerfailureduration) % (60*60*24) / 3600) as VARCHAR),'.',1) || ' Hours ' ||\n" +
					"split_part(CAST(((sum(totalpowerfailureduration) % (60*60*24) % 3600) / 60) as VARCHAR),'.',1) || ' Minutes ' || CAST((sum(totalpowerfailureduration) % 60) as VARCHAR) || ' Second' AS event_duration\n" +
					"FROM meter_data.power_onoff where circlecode like'"+circle+"'and towncode like '"+town+"' and feedercode like '"+feeder+"' and to_char(date, 'yyyyMM') ='"+month+"'\n" +
					"GROUP BY eventmonth,circle,town,feedercode,feedername,meterid ";
			//System.err.println(query);
			CirwiseMtrData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(9);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("MonthYear",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Circle",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Town",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	          
	           
	           parameterCell = new PdfPCell(new Phrase("Feeder Code",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("Feeder Name",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total No. Of Occurence",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Total Power Failure Duration",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < CirwiseMtrData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=CirwiseMtrData.get(i);
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
							 
							 
							 parameterCell = new PdfPCell(new Phrase(obj[3]==null?null:obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[4]==null?null:obj[4]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[5]==null?null:obj[5]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[6]==null?null:obj[6]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase(obj[7]==null?null:obj[7]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
						/*
						 * parameterCell = new PdfPCell(new Phrase(obj[8]==null?null:obj[8]+"",new
						 * Font(Font.FontFamily.HELVETICA ,11 )));
						 * parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 * parameterCell.setFixedHeight(30f); parameterTable.addCell(parameterCell);
						 * 
						 * parameterCell = new PdfPCell(new Phrase(obj[9]==null?null:obj[9]+"",new
						 * Font(Font.FontFamily.HELVETICA ,11 )));
						 * parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 * parameterCell.setFixedHeight(30f); parameterTable.addCell(parameterCell);
						 */
							 
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
			        response.setHeader("Content-disposition", "attachment; filename=FeederOutageSummary.pdf");
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
	public List<?> getMonthWisefeederOutageReport(String month,String feeder,String meterno) {
		List<?> list=null;
		try {
			String sql="SELECT circle,town,feedercode,feedername,meterid ,date as  eventdate, sum(totalnooccurence) as totalevents,split_part(CAST(sum(totalpowerfailureduration) / (60*60*24) as VARCHAR),'.',1) || ' Days ' ||\n" +
					"split_part(CAST((sum(totalpowerfailureduration) % (60*60*24) / 3600) as VARCHAR),'.',1) || ' Hours ' ||\n" +
					"split_part(CAST(((sum(totalpowerfailureduration) % (60*60*24) % 3600) / 60) as VARCHAR),'.',1) || ' Minutes ' || CAST((sum(totalpowerfailureduration) % 60) as VARCHAR) || ' Second' AS event_duration\n" +
					"FROM meter_data.power_onoff where feedercode like '"+feeder+"'and meterid like '"+meterno+"' and to_char(date, 'yyyyMM') ='"+month+"'\n" +
					"GROUP BY eventdate,circle,town,feedercode,feedername,meterid order by eventdate";
		//System.out.println(sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	@Override
	public List<?> getMonthDurationWisefeederOutageReport(String month,String feeder,String meterno) {
		List<?> list=null;
		try {
			String sql="select distinct  po.feedercode,po.feedername,po.meterid,occurence_time,restoration_time,round(extract(epoch from (restoration_time - occurence_time))/60) from meter_data.power_onoff_details pod , meter_data.power_onoff po   where  pod.meterid=po.meterid and po.feedercode like '"+feeder+"' and pod.date='"+month+"' and pod.meterid like '"+meterno+"'";
		System.out.println(sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<?> getFeederMaster(String zone, String subdiv, String circle,String town,
			String towncode) {
		List<?> list=null;
		try {
			String sql=" select DISTINCT ON(fd.tp_fdr_id)fd.tp_fdr_id,am.zone,am.circle,am.division,am.subdivision,am.section,fd.tp_town_code,am.town_ipds,fd.feedername,sd.sstp_id ,sd.ss_name from meter_data.feederdetails fd  LEFT JOIN meter_data.amilocation am ON(fd.tp_town_code=am.tp_towncode) LEFT JOIN meter_data.substation_details sd ON(am.tp_towncode=sd.parent_town) WHERE fd.tp_town_code LIKE '"+towncode+"' ";
		System.out.println(sql);
			list=getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
}








	
