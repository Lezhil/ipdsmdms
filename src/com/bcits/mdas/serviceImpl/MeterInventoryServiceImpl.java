package com.bcits.mdas.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterInventoryEntity;
import com.bcits.mdas.service.MeterInventoryService;
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

@Repository
public class MeterInventoryServiceImpl extends GenericServiceImpl<MeterInventoryEntity> implements MeterInventoryService {

	@SuppressWarnings("unchecked")
	@Override
	public List<MeterInventoryEntity> getALLMeterDetails() {
		List<MeterInventoryEntity> result=new ArrayList<MeterInventoryEntity>();
		try {
			result=postgresMdas.createNamedQuery("METER_INVENTORY.getMeterDetailsData").getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<MeterInventoryEntity> getALLMeterDetailsById(Long id) {
		List<MeterInventoryEntity> result=new ArrayList<MeterInventoryEntity>();
		try {
			//result=postgresMdas.createNamedQuery("METER_INVENTORY.getMeterDetailsDataById").setParameter("id", id).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MeterInventoryEntity> meterNoExistOrNot(String meterno) {
		try {
			return postgresMdas.createNamedQuery("METER_INVENTORY.checkMeterNoExist").setParameter("meterno", meterno).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<MeterInventoryEntity> saveBatchInventory(List<MeterInventoryEntity> list) {
		
		for (int i = 0; i < list.size(); i++) {
			
			save(list.get(i));			
		}
		return list;
	}

	@Override
	public List totalInstalledMeters(ModelMap model) {
		//List<BigInteger> li =new ArrayList<>(); 
		List<?> li =new ArrayList<>();
		/*String sql ="SELECT count(*) from meter_data.meter_inventory WHERE meterno IN(\n" +
					"SELECT mtrno FROM meter_data.master_main)";*/
		String sql ="SELECT meter_status,count(*) from meter_data.meter_inventory"
					+ " GROUP BY meter_status HAVING count(*)>0 ORDER BY meter_status asc";
		try {
		li = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		if(li.size()>0){
			for (int i = 0; i < li.size(); i++) {
				Object[] str=(Object[]) li.get(i);
				if(str[0].equals("DAMAGED"))
	    		 {
					model.put("DAMAGED", str[1]);
	    		 }
				else if(str[0].equals("INSTALLED"))
				{
					model.put("INSTALLED", str[1]);
				}
				if(str[0].equals("INSTOCK"))
				{
					model.put("INSTOCK", str[1]);
				}
				if(str[0].equals("ISSUED"))
				{
					model.put("ISSUED", str[1]);
				}
				
			}
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		return li;
	
	}
	
	@Override
	public List totalInstalledMetersForCirclelvl(ModelMap model,String officeType,String circle,String region) {
		//List<BigInteger> li =new ArrayList<>(); 
		List<?> li =new ArrayList<>();
		String sqls="";
		String status = "INSTALLED";
		
		
		if(officeType.equalsIgnoreCase("circle")) {
			 sqls ="select * from (\r\n" + 
			 		"(SELECT meter_status,count(*) from meter_data.meter_inventory  \r\n" + 
			 		"where meter_status not in ('"+status+"')  GROUP BY meter_status \r\n" + 
			 		"ORDER BY meter_status asc )\r\n" + 
			 		"UNION\r\n" + 
			 		"(select '"+status+"' as meter_status1,count(*) from meter_data.master_main where circle like '"+circle+"'))c";
		
		}else if(officeType.equalsIgnoreCase("region")) {
			sqls ="select * from (\r\n" + 
			 		"(SELECT meter_status,count(*) from meter_data.meter_inventory  \r\n" + 
			 		"where meter_status not in ('"+status+"')  GROUP BY meter_status \r\n" + 
			 		"ORDER BY meter_status asc )\r\n" + 
			 		"UNION\r\n" + 
			 		"(select '"+status+"' as meter_status1,count(*) from meter_data.master_main where zone like '"+region+"'))c";
				
		}
		
		try {
		li = getCustomEntityManager("postgresMdas").createNativeQuery(sqls).getResultList();
		if(li.size()>0){
			for (int i = 0; i < li.size(); i++) {
				Object[] str=(Object[]) li.get(i);
				if(str[0].equals("DAMAGED"))
	    		 {
					model.put("DAMAGED", str[1]);
	    		 }
				else if(str[0].equals("INSTALLED"))
				{
					model.put("INSTALLED", str[1]);
				}
				if(str[0].equals("INSTOCK"))
				{
					model.put("INSTOCK", str[1]);
				}
				if(str[0].equals("ISSUED"))
				{
					model.put("ISSUED", str[1]);
				}
				
			}
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		return li;
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MeterInventoryEntity> getALLInstockMeters(String strLoc,String iortype) {
		String s=null;
		if(iortype.equalsIgnoreCase("ISSUED")){
			s="INSTOCK";
		}
		else if(iortype.equalsIgnoreCase("INSTOCK")){
			s="ISSUED";
		}
		List<MeterInventoryEntity> result=new ArrayList<MeterInventoryEntity>();
		try {
			result=postgresMdas.createNamedQuery("METER_INVENTORY.getLocInstockMeters").setParameter("meter_status", s).setParameter("strLoc", strLoc).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public List<MeterInventoryEntity> getALLInstockMeters() {
		List<MeterInventoryEntity> result=new ArrayList<MeterInventoryEntity>();
		try {
			result=postgresMdas.createNamedQuery("METER_INVENTORY.getALLInstockMeters").setParameter("meter_status", "INSTOCK").getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<MeterInventoryEntity> updateMeterNoInstalled(String meterno,String userName) {

		int li =0;
		String sql ="update meter_data.meter_inventory SET meter_status='INSTALLED',updateby='"+userName+"' WHERE meterno='"+meterno+"'";
		////System.out.println(sql);
		try {
		li = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return null;
		
	
	
	}

	@Override
	public List<MeterInventoryEntity> checkMeterNoIsInSTOCK(String meterno) {
		List li=new ArrayList<>();
		String sql ="SELECT meter_status FROM meter_data.meter_inventory m WHERE m.meterno='"+meterno+"'  AND meter_status='INSTOCK'";
		////System.out.println(sql);
		try {
		li = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return li;
	
	}

	@Override
	public List<MeterInventoryEntity> checkMeterNoInstalled(String meterno) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    public String sequenceMeterResponse(String ssn,String esn) {

		String s1 = ssn;
		String s2 = esn;
		
		String c1ss = s1.substring(0, s1.length() - 3);
		String c2ss = s2.substring(0, s2.length() - 3);
		if (!c1ss.equalsIgnoreCase(c2ss)) {
			return "Wrong Sequence Inputs";
		}
		String n1ss = s1.substring(s1.length() - 3, s1.length());
		String n2ss = s2.substring(s2.length() - 3, s2.length());
		int n1;
		int v1 = 0;
		int v2 = 0;
		int i1=Integer.parseInt(n1ss);
		int i2=Integer.parseInt(n2ss);
		
		try {
			 v1 = Integer.toString(Integer.parseInt(n1ss)).length();
			 v2=Integer.toString(Integer.parseInt(n2ss)).length();
			
		}
		catch (Exception e) {
			return "Wrong Sequence Inputs";
		}
		
		
			
				if(i2>i1) {
					if((i2-i1)>100) {
						return "Application allow maximum 100 meters";
					}
					else {
				List<String> ml=sequenceMeters(c1ss,i1,i2);
				String meterList=meterList(ml); 
				return 	meterList;
					}
				}
				else {
					 return "Wrong Sequence Inputs";
				}

			
		

	
    }
    public  List<String> sequenceMeters(String prefix,int i1,int i2){
		List<String> ml=new ArrayList<>();
		
		for(int i=i1;i<=i2;i++) {
			if(Integer.toString(i).length()==1) {
				ml.add(prefix+"00"+Integer.toString(i));
			}
			else if(Integer.toString(i).length()==2) {
				ml.add(prefix+"0"+Integer.toString(i));
			}
			else if(Integer.toString(i).length()==3) {
				ml.add(prefix+Integer.toString(i));
			}
		}
		return ml;
	}
	
	
	  public  String meterList(List<String> s) {
		  String sqls="";
	    for (String string : s) {
			sqls+="'"+string+"',";
		}
		 sqls=sqls.substring(0, sqls.length()-1) ;
		// ////System.out.println(sqls);
		 return sqls;
		 
	  }

	@Override
	public List<Object[]> getMetersBasedOnStatus(String status) {
		List<Object[]> li =new ArrayList<>();
		String result="";
		/*if(status.equalsIgnoreCase("ISSUED"))
		{
			String qry="select m.meterno,m.meter_status,m.meter_make,connection_type,s.surveyorname,s.entrydate\n" +
						" from meter_data.meter_inventory m ,meter_data.surveyordetails s\n" +
						" where m.mrflag=s.suid and m.meter_status='"+status+"' and \n" +
						"to_char(s.entrydate,'dd-MM-yyyy') =to_char(CURRENT_DATE,'dd-MM-yyyy') order by s.entrydate";
		////System.out.println("qry--"+qry);
			Object obj=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		return (List<MeterInventoryEntity>) obj;
		}
		else{*/
		
		
			if(status.equalsIgnoreCase("INSTOCK")) {
				result="select meterno,meter_status,meter_make,meter_model,ct_ratio,pt_ratio,connection_type,entrydate from meter_data.meter_inventory where meter_status='"+status+"'";
				
			}else if(status.equalsIgnoreCase("INSTALLED")) {
				result="select mtrno,customer_name,location_id,substation, subdivision, division, circle, last_communicated_date  from meter_data.master_main ";
				//"select meterno,meter_status,meter_make,meter_model,ct_ratio,pt_ratio,connection_type,entrydate from meter_data.meter_inventory where meter_status='"+status+"'";

			}
			li = getCustomEntityManager("postgresMdas").createNativeQuery(result).getResultList();

		//}
		return li;
		
	}
	
	@Override
		public List<Object[]> getMetersBasedOnStatusandLoginlvl(String status,String officeType,String region,String circle) {
		
		List<Object[]> li =new ArrayList<>();
		
		String sqls="";
		
		if(officeType.equalsIgnoreCase("circle")) {
			if(status.equalsIgnoreCase("INSTOCK")) {
				sqls="select meterno,meter_status,meter_make,meter_model,ct_ratio,pt_ratio,connection_type,entrydate from meter_data.meter_inventory where meter_status='"+status+"'";
				
			}else if(status.equalsIgnoreCase("INSTALLED")) {
				sqls="select mtrno,customer_name,location_id,substation, subdivision, division, circle, last_communicated_date  from meter_data.master_main ";
						//"select ll.meterno,ll.meter_status,ll.meter_make,ll.meter_model,ll.ct_ratio,ll.pt_ratio,ll.connection_type,ll.entrydate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.circle='"+circle+"'and ll.meter_status='"+status+"'";
			}
			
		}else if(officeType.equalsIgnoreCase("region")) {
			if(status.equalsIgnoreCase("INSTOCK")) {
				sqls="select meterno,meter_status,meter_make,meter_model,ct_ratio,pt_ratio,connection_type,entrydate from meter_data.meter_inventory where meter_status='"+status+"'";
				}else if(status.equalsIgnoreCase("INSTALLED")) {
				sqls="select mtrno,customer_name,location_id,substation, subdivision, division, circle, last_communicated_date  from meter_data.master_main ";
						//"select ll.meterno,ll.meter_status,ll.meter_make,ll.meter_model,ll.ct_ratio,ll.pt_ratio,ll.connection_type,ll.entrydate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.zone='"+region+"'and ll.meter_status='"+status+"'";
			}
		}
		
		li = getCustomEntityManager("postgresMdas").createNativeQuery(sqls).getResultList();

		return li;
		
	}

	@Override
	public Object getissuedAndInstalledMetrdata(String status,String fromDate,String toDate) {
		
		Object result=new ArrayList<MeterInventoryEntity>();
		try {

			String qry=" SELECT a.meterno,c.meter_status,c.meter_make,c.connection_type,b.surveyorname,a.insert_time,c.mrflag from \n" +
						"(SELECT suid,trans_type,meter_status,insert_time,meterno FROM meter_data.meter_issue_or_return_transaction where date(insert_time) BETWEEN '"+fromDate+"' and  '"+toDate+"'  order by insert_time)a,\n" +
						"(select suid,surveyorname from meter_data.surveyordetails)b,\n" +
						"(select meterno,meter_make,connection_type,mrflag,meter_status from meter_data.meter_inventory where meter_status='"+status+"')c\n" +
						"where a.suid=b.suid and c.mrflag=a.suid and b.suid=c.mrflag and c.meterno=a.meterno order by a.insert_time";
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getMeterInventoryBasedOnData(String data,String parameter) {
		List<Object[]> result =new ArrayList<>();
		
		String sqls="";
		
		try {
			if(parameter.equalsIgnoreCase("meterslno")){
				sqls="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant, ct_ratio,tender_no,meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby, entrydate,updateby,updatedate,id from meter_data.meter_inventory where meterno='"+data+"'";
			}
			if(parameter.equalsIgnoreCase("metermake")){
				sqls="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant, ct_ratio,tender_no,meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby, entrydate,updateby,updatedate,id from meter_data.meter_inventory where meter_make='"+data+"'";
			}
			if(parameter.equalsIgnoreCase("meterstatus")){
				sqls="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant, ct_ratio,tender_no,meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby, entrydate,updateby,updatedate,id from meter_data.meter_inventory where meter_status='"+data+"'";
			}
			if(parameter.equalsIgnoreCase("manufactureyear")){
				sqls="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant, ct_ratio,tender_no,meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby, entrydate,updateby,updatedate,id from meter_data.meter_inventory where manufacture_year_month='"+data+"'";
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println("sql ra" + sqls);
		result = getCustomEntityManager("postgresMdas").createNativeQuery(sqls).getResultList();

		
		return result;
	}
	
	@Override
	public List<Object[]> getMeterInventoryBasedOnDataByLoginLvl(String data, String parameter, String circle,
			String region,String officeType) {
		
		List<Object[]> result =new ArrayList<>();
		
		String sqls="";
		
		if (officeType.equalsIgnoreCase("circle")) {
			if (parameter.equalsIgnoreCase("meterslno")) {
				sqls = "select ll.meterno,ll.connection_type,ll.meter_make,ll.meter_commisioning,ll.meter_model,ll.meter_ip_period,cc.pt_ratio,ll.meter_constant, cc.ct_ratio,ll.tender_no,ll.meter_accuracy_class,ll.manufacture_year_month,ll.meter_current_rating,ll.warrenty_years,ll.meter_voltage_rating,ll.meter_status,ll.entryby, ll.entrydate,ll.updateby,ll.updatedate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.circle='"
						+ circle + "'and ll.meterno='" + data + "'";

			} else if (parameter.equalsIgnoreCase("metermake")) {
				sqls = "select ll.meterno,ll.connection_type,ll.meter_make,ll.meter_commisioning,ll.meter_model,ll.meter_ip_period,cc.pt_ratio,ll.meter_constant, cc.ct_ratio,ll.tender_no,ll.meter_accuracy_class,ll.manufacture_year_month,ll.meter_current_rating,ll.warrenty_years,ll.meter_voltage_rating,ll.meter_status,ll.entryby, ll.entrydate,ll.updateby,ll.updatedate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.circle='"
						+ circle + "'and ll.meter_make='" + data + "'";

			} else if (parameter.equalsIgnoreCase("meterstatus")) {
				sqls = "select ll.meterno,ll.connection_type,ll.meter_make,ll.meter_commisioning,ll.meter_model,ll.meter_ip_period,cc.pt_ratio,ll.meter_constant, cc.ct_ratio,ll.tender_no,ll.meter_accuracy_class,ll.manufacture_year_month,ll.meter_current_rating,ll.warrenty_years,ll.meter_voltage_rating,ll.meter_status,ll.entryby, ll.entrydate,ll.updateby,ll.updatedate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.circle='"
						+ circle + "'and ll.meter_status='" + data + "'";

			} else if (parameter.equalsIgnoreCase("manufactureyear")) {
				sqls = "select ll.meterno,ll.connection_type,ll.meter_make,ll.meter_commisioning,ll.meter_model,ll.meter_ip_period,cc.pt_ratio,ll.meter_constant, cc.ct_ratio,ll.tender_no,ll.meter_accuracy_class,ll.manufacture_year_month,ll.meter_current_rating,ll.warrenty_years,ll.meter_voltage_rating,ll.meter_status,ll.entryby, ll.entrydate,ll.updateby,ll.updatedate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.circle='"
						+ circle + "'and ll.manufactureyear='" + data + "'";

			}

		} else if (officeType.equalsIgnoreCase("region")) {
			if (parameter.equalsIgnoreCase("meterslno")) {
				sqls = "select ll.meterno,ll.connection_type,ll.meter_make,ll.meter_commisioning,ll.meter_model,ll.meter_ip_period,cc.pt_ratio,ll.meter_constant, cc.ct_ratio,ll.tender_no,ll.meter_accuracy_class,ll.manufacture_year_month,ll.meter_current_rating,ll.warrenty_years,ll.meter_voltage_rating,ll.meter_status,ll.entryby, ll.entrydate,ll.updateby,ll.updatedate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.zone='"
						+ region + "'and ll.meterno='" + data + "'";

			} else if (parameter.equalsIgnoreCase("metermake")) {
				sqls = "select ll.meterno,ll.connection_type,ll.meter_make,ll.meter_commisioning,ll.meter_model,ll.meter_ip_period,cc.pt_ratio,ll.meter_constant, cc.ct_ratio,ll.tender_no,ll.meter_accuracy_class,ll.manufacture_year_month,ll.meter_current_rating,ll.warrenty_years,ll.meter_voltage_rating,ll.meter_status,ll.entryby, ll.entrydate,ll.updateby,ll.updatedate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.zone='"
						+ region + "'and ll.metermake='" + data + "'";

			} else if (parameter.equalsIgnoreCase("meterstatus")) {
				sqls = "select ll.meterno,ll.connection_type,ll.meter_make,ll.meter_commisioning,ll.meter_model,ll.meter_ip_period,cc.pt_ratio,ll.meter_constant, cc.ct_ratio,ll.tender_no,ll.meter_accuracy_class,ll.manufacture_year_month,ll.meter_current_rating,ll.warrenty_years,ll.meter_voltage_rating,ll.meter_status,ll.entryby, ll.entrydate,ll.updateby,ll.updatedate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.zone='"
						+ region + "'and ll.meter_status='" + data + "'";

			} else if (parameter.equalsIgnoreCase("manufactureyear")) {
				sqls = "select ll.meterno,ll.connection_type,ll.meter_make,ll.meter_commisioning,ll.meter_model,ll.meter_ip_period,cc.pt_ratio,ll.meter_constant, cc.ct_ratio,ll.tender_no,ll.meter_accuracy_class,ll.manufacture_year_month,ll.meter_current_rating,ll.warrenty_years,ll.meter_voltage_rating,ll.meter_status,ll.entryby, ll.entrydate,ll.updateby,ll.updatedate from meter_data.meter_inventory ll,meter_data.master_main cc where cc.mtrno=ll.meterno and cc.zone='"
						+ region + "'and ll.manufactureyear='" + data + "'";

			}
		}

		result = getCustomEntityManager("postgresMdas").createNativeQuery(sqls).getResultList();

		return result;
	}

	@Override
	public Object getCtRatioPtRatio(String meterno) {
		
		Object result=new ArrayList<MeterInventoryEntity>();
		try {

			String qry="select ct_ratio,pt_ratio from meter_data.meter_inventory WHERE meterno='"+meterno+"';";
			result=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList().get(0);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public MeterInventoryEntity getMeterInventoryEntity(String meterno) {
		//List<MeterInventoryEntity> result=new ArrayList<MeterInventoryEntity>();
		try {
			 return getCustomEntityManager("postgresMdas").createNamedQuery("METER_INVENTORY.getMeterInventoryEntity",MeterInventoryEntity.class)
					 .setParameter("meterno", meterno)
					 .getSingleResult();
			
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateMeterStatus(String meterno,String meter_status) {
		int i = 0;
		String sql = "update meter_data.meter_inventory SET meter_status='"+meter_status+"' WHERE meterno='"+meterno+"'";
		try 
		{
			i = getCustomEntityManager("postgresMdas").createNativeQuery(sql).executeUpdate();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public void getMeterDetailsPdf(HttpServletRequest request, HttpServletResponse response, String param,
			String data) {

		try {
			
			Rectangle pageSize = new Rectangle(1550, 720);
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
		    p1.add(new Phrase("Meter Details",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		    p1.setAlignment(Element.ALIGN_CENTER);
		    cell2.addElement(p1);
		    cell2.setBorder(Rectangle.BOTTOM);
		    pdf1.addCell(cell2);
		    document.add(pdf1);
		    
		    PdfPTable header = new PdfPTable(2);
		    header.setWidths(new int[]{1,1});
		    header.setWidthPercentage(100);
		    
		    PdfPCell headerCell=null;
		    headerCell = new PdfPCell(new Phrase("Parameter :"+param,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    headerCell = new PdfPCell(new Phrase("Data :"+data,new Font(Font.FontFamily.HELVETICA  ,10, Font.BOLD)));
		    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    headerCell.setFixedHeight(20f);
		    headerCell.setBorder(PdfPCell.NO_BORDER);
		    header.addCell(headerCell);
		    
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		    header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		    
		    document.add(header);
		    
		    String query="";
			List<Object[]> MeterDetailsData=null;
			if(param.equalsIgnoreCase("meterslno")) {
			query="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant,ct_ratio,tender_no,\n" +
					"meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby,to_char(entrydate,'yyyy-mm-dd'),updateby,\n" +
					"to_char(updatedate,'yyyy-mm-dd') from meter_data.meter_inventory where meterno='"+data+"' ";
			} 
			if(param.equalsIgnoreCase("metermake")) {
			query="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant,ct_ratio,tender_no,\n" +
					"meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby,to_char(entrydate,'yyyy-mm-dd'),updateby,\n" +
					"to_char(updatedate,'yyyy-mm-dd') from meter_data.meter_inventory where meter_make='"+data+"' ";
			} 
			if(param.equalsIgnoreCase("meterstatus")) {
			query="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant,ct_ratio,tender_no,\n" +
					"meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby,to_char(entrydate,'yyyy-mm-dd'),updateby,\n" +
					"to_char(updatedate,'yyyy-mm-dd') from meter_data.meter_inventory where meter_status='"+data+"' ";	
			}
			if(param.equalsIgnoreCase("manufactureyear")) {
			query=	"select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant,ct_ratio,tender_no,\n" +
					"meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby,to_char(entrydate,'yyyy-mm-dd'),updateby,\n" +
					"to_char(updatedate,'yyyy-mm-dd') from meter_data.meter_inventory where manufacture_year_month='"+data+"' ";
			}
			MeterDetailsData=postgresMdas.createNativeQuery(query).getResultList();

			
			PdfPTable parameterTable = new PdfPTable(21);
	           parameterTable.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
	           parameterTable.setWidthPercentage(100);
	           PdfPCell parameterCell;
	           
	           parameterCell = new PdfPCell(new Phrase("S.NO",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
		    
	           parameterCell = new PdfPCell(new Phrase("MeterNo",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("ConnectionType",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter Make",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Commissioning",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Model",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("IP Period",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("PT Ratio",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Meter Constant",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("CT Ratio",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Tender No",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Accuracy Class",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Manufacture YearMonth",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Current Rating",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Warranty Period",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Voltage Rating",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("MeterStatus",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Entry By",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Entry Date",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Updated By",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           parameterCell = new PdfPCell(new Phrase("Updated Date",new Font(Font.FontFamily.HELVETICA  ,11, Font.BOLD)));
	           parameterCell.setFixedHeight(25f);
	           parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	           parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	           parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	           parameterTable.addCell(parameterCell);
	           
	           
	           for (int i = 0; i < MeterDetailsData.size(); i++) 
	            {
	        	   parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
						 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						 parameterTable.addCell(parameterCell);
						
						 
	            	Object[] obj=MeterDetailsData.get(i);
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
							 
							 parameterCell = new PdfPCell(new Phrase( obj[17]==null?null:obj[17]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[18]==null?null:obj[18]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
							 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
							 parameterCell.setFixedHeight(30f);
							 parameterTable.addCell(parameterCell);
							 
							 parameterCell = new PdfPCell(new Phrase( obj[19]==null?null:obj[19]+"",new Font(Font.FontFamily.HELVETICA  ,11 )));
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
			        response.setHeader("Content-disposition", "attachment; filename=Meter_Details.pdf");
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
	public List<?> getMeterDetailsExcel(String data, String parameter) {
		
		  String query="";
			List<Object[]> MeterDetailsData=null;
			if(parameter.equalsIgnoreCase("meterslno")) {
			query="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant,ct_ratio,tender_no,\n" +
					"meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby,to_char(entrydate,'yyyy-mm-dd'),updateby,\n" +
					"to_char(updatedate,'yyyy-mm-dd') from meter_data.meter_inventory where meterno='"+data+"' ";
			} 
			if(parameter.equalsIgnoreCase("metermake")) {
			query="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant,ct_ratio,tender_no,\n" +
					"meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby,to_char(entrydate,'yyyy-mm-dd'),updateby,\n" +
					"to_char(updatedate,'yyyy-mm-dd') from meter_data.meter_inventory where meter_make='"+data+"' ";
			} 
			if(parameter.equalsIgnoreCase("meterstatus")) {
			query="select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant,ct_ratio,tender_no,\n" +
					"meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby,to_char(entrydate,'yyyy-mm-dd'),updateby,\n" +
					"to_char(updatedate,'yyyy-mm-dd') from meter_data.meter_inventory where meter_status='"+data+"' ";	
			}
			if(parameter.equalsIgnoreCase("manufactureyear")) {
			query=	"select meterno,connection_type,meter_make,meter_commisioning,meter_model,meter_ip_period,pt_ratio,meter_constant,ct_ratio,tender_no,\n" +
					"meter_accuracy_class,manufacture_year_month,meter_current_rating,warrenty_years,meter_voltage_rating,meter_status,entryby,to_char(entrydate,'yyyy-mm-dd'),updateby,\n" +
					"to_char(updatedate,'yyyy-mm-dd') from meter_data.meter_inventory where manufacture_year_month='"+data+"' ";
			}
			try {
				MeterDetailsData=postgresMdas.createNativeQuery(query).getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return MeterDetailsData;
	}

	@Override
	public int updateMtrStatus(int id) {
		int i = 0;
		String sql = "update meter_data.meter_inventory SET meter_status='INSTOCK' WHERE meterno in (select meterno from meter_data.feederdetails where id='"+id+"')";
		try 
		{
			i=postgresMdas.createNativeQuery(sql).executeUpdate();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public List<Object[]> getUnmappedMetersData() {
		List<Object[]> li =new ArrayList<>();
		String result="";
         result="select * from meter_data.unmapped";
		li = getCustomEntityManager("postgresMdas").createNativeQuery(result).getResultList();
        return li;
	}

	


	
}
