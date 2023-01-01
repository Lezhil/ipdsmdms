package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.D2Data;
import com.bcits.entity.D4Data;
import com.bcits.service.D4DtataService;
import com.bcits.utility.MDMLogger;
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
import com.itextpdf.text.pdf.draw.VerticalPositionMark;


@Repository
public class D4DtataServiceImpl extends GenericServiceImpl<D4Data> implements D4DtataService 
{
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<D4Data> getDetailsBasedOnMeterNo(String meterNo,String billMonth,String previousMonthForD4,ModelMap model)
	{
		List<D4Data> list=null;
		 list= postgresMdas.createNamedQuery("D4Data.getDetailsBasedOnId").setParameter("meterNo", meterNo).setParameter("billMonth", Integer.parseInt(billMonth)).getResultList();
			/*String qry="select meterno,day_profile_date as ls_date,IP_Interval,kvavalue as kva,kwhvalue as kwh,pfvalue as pf\n" +
					"from d4_load_data where meterno like '"+meterNo+"' and billmonth="+billMonth+" order by ls_date,ip_interval";
		list =postgresMdas.createNativeQuery(qry).getResultList();
		*/
		
		model.put("loadSurveyData", list);
		model.put("portletTitle", "Load Survey Details of "+billMonth);
		model.put("meterNo", meterNo);
		model.put("selectedMonth", billMonth);
		if(list.size()==0)
		{
			model.put("msg", "No data found for entered meter number and selected date");
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getLoadSurveyDatewise(String meterNo,String selectedDateName, ModelMap model)
	{
		List<Object[]> list=null;
		try 
		{
			/*String query="SELECT MM.CIRCLE,MM.DIVISION,MM.SDONAME,MM.MF,D4LOAD.IP_INTERVAL,TO_CHAR(D4LOAD.DAY_PROFILE_DATE,'dd-MM-yyyy')AS PROFILE_DATE,(D4LOAD.KWHVALUE)AS KWH,(D4LOAD.KVAVALUE)AS KVAV"+" "
						+"FROM BSMARTMDM.METERMASTER MM,BSMARTMDM.D4_LOAD_DATA D4LOAD WHERE  MM.METRNO=D4LOAD.METERNO AND MM.RDNGMONTH=D4LOAD.BILLMONTH"+" "
						+"AND MM.RDNGMONTH='"+selectedDateName+"' AND MM.METRNO like '"+meterNo+"' AND D4LOAD.BILLMONTH='"+selectedDateName+"' AND D4LOAD.METERNO='"+meterNo+"' AND D4LOAD.IP_INTERVAL>0 "+" "
						+"ORDER BY D4LOAD.DAY_PROFILE_DATE,D4LOAD.IP_INTERVAL";*/
			
			String query="SELECT M.CIRCLE,M.DIVISION,M.SDONAME,MM.MF,D4LOAD.IP_INTERVAL,\n" +
					"TO_CHAR(D4LOAD.DAY_PROFILE_DATE,'dd-MM-yyyy')AS PROFILE_DATE,\n" +
					"(D4LOAD.KWHVALUE)AS KWH,(D4LOAD.KVAVALUE)AS KVAV FROM BSMARTMDM.METERMASTER MM,\n" +
					"BSMARTMDM.D4_LOAD_DATA D4LOAD,BSMARTMDM.MASTER M WHERE MM.METRNO=D4LOAD.METERNO \n" +
					"AND MM.RDNGMONTH=D4LOAD.BILLMONTH AND MM.SDOCODE=M.SDOCODE AND MM.RDNGMONTH='"+selectedDateName+"' AND \n" +
					"MM.METRNO like '"+meterNo+"' AND D4LOAD.BILLMONTH='"+selectedDateName+"' AND D4LOAD.METERNO='"+meterNo+"' AND \n" +
					"D4LOAD.IP_INTERVAL>0 ORDER BY D4LOAD.DAY_PROFILE_DATE,D4LOAD.IP_INTERVAL";
			model.put("viewCategory", "LoadSurveyIpwise");
			MDMLogger.logger.info("====getLoadSurveyDatewise===>query"+query);
			System.out.println("====getLoadSurveyDatewise===>query"+query);
			list=postgresMdas.createNativeQuery(query).getResultList();
			if(list.size()>0)
			{
				model.put("loadSurveyArrLength", list.get(0).length);
				model.put("viewCategory", "LoadSurveyIpwise");
				model.put("meterNo", meterNo);
				model.put("selectedMonth", selectedDateName);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public void downloadLoadSurveyIpPdf(String meterno, String month,ModelMap model, HttpServletResponse response)
	{
		String circle="",division="",subDivision="",consumerName="",address="",cd="",sanLoad="",msf="";
		List<Object[]> data=null;
		List<Object[]> masterData=null;
		List<Object[]> dataHeader=null;
		 List<D2Data> d2data=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try 
		{
			/*String sql="SELECT MM.CIRCLE,MM.DIVISION,MM.SDONAME,MM.CONSUMERNAME,MM.ADDRESS,MM.METRNO, MA.CONTRACTDEMAND||'',MA.SANLOAD||'',MM.MF||'' FROM DHBVN.MASTER MA,DHBVN.METERMASTER "
					+ "MM WHERE MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+month+"' AND MM.METRNO like '"+meterno+"'";*/
			
			String sql="SELECT MA.CIRCLE,MA.DIVISION,MA.SDONAME,MA.NAME,MA.ADDRESS1,MM.METRNO, MA.CONTRACTDEMAND||'',MA.SANLOAD||'',MM.MF||'' FROM BSMARTMDM.MASTER MA,BSMARTMDM.METERMASTER "
					+ "MM WHERE MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+month+"' AND MM.METRNO like '"+meterno+"'";
			
			masterData=postgresMdas.createNativeQuery(sql).getResultList();
			circle=(String) masterData.get(0)[0];
			division=(String) masterData.get(0)[1];
			subDivision=(String) masterData.get(0)[2];
			consumerName=(String) masterData.get(0)[3];
			address=(String) masterData.get(0)[4];
			cd=(String) masterData.get(0)[6];
			sanLoad=(String) masterData.get(0)[7];
			msf=(String) masterData.get(0)[8];
			
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
		        pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		        cell1.setBorder(Rectangle.NO_BORDER);
		        cell1.addElement(pstart);
		        pdf2.addCell(cell1);
		        pstart.add(new Chunk(glue));
		        pstart.add(new Phrase("Reading Month : "+new SimpleDateFormat("MMM-YYYY").format(new SimpleDateFormat("yyyyMM").parse(month)),new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		        
		        document.add(pdf2);
		        PdfPCell cell2 = new PdfPCell();
		        Paragraph p1 = new Paragraph();
		        p1.add(new Phrase("LOAD SURVEY DETAILS: "+meterno,new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
		        p1.setAlignment(Element.ALIGN_CENTER);
		        cell2.addElement(p1);
		        cell2.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		        pdf1.addCell(cell2);
		        document.add(pdf1);
		        
		        PdfPTable header = new PdfPTable(4);
	             header.setWidthPercentage(100);
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));

	             PdfPCell headerCell=null;
	             headerCell = new PdfPCell(new Phrase("Circle :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));
	             	            
	             headerCell = new PdfPCell(new Phrase("Consumer Name :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             header.addCell(getCell(consumerName, PdfPCell.ALIGN_LEFT));
	             
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
	             
	             headerCell = new PdfPCell(new Phrase("SanLoad :",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
	             headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             headerCell.setFixedHeight(20f);
	             headerCell.setBorder(PdfPCell.NO_BORDER);
	             header.addCell(headerCell);
	             //header.addCell(getCell("MF :", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(sanLoad, PdfPCell.ALIGN_LEFT));
	             
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
	             header.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
	             header.addCell(getCell("", PdfPCell.ALIGN_LEFT));
	             document.add(header);

	             String query="SELECT IP_INTERVAL,TO_CHAR(DAY_PROFILE_DATE,'dd-MM-yyyy')AS PROFILE_DATE,(KWHVALUE)AS KWH,(KVAVALUE)AS KVAV"+" "
	            		 +"FROM D4_LOAD_DATA WHERE BILLMONTH='"+month+"' AND METERNO='"+meterno+"' AND IP_INTERVAL>0 "+" "
	            		 +"ORDER BY DAY_PROFILE_DATE,IP_INTERVAL";
	 			MDMLogger.logger.info("====================>query"+query);
	 			List<Object[]> loadDataDateList=postgresMdas.createNativeQuery(query).getResultList();
		        				
		        				PdfPTable parameterTable = new PdfPTable(5);
		   	                 parameterTable.setWidths(new int[]{2,2,2,2,2});
		   	                 parameterTable.setWidthPercentage(100);
		   		             PdfPCell parameterCell;
		   		             parameterCell = new PdfPCell(new Phrase("SR NO.",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		   		             parameterTable.addCell(parameterCell);
		   		             
		   		          parameterCell = new PdfPCell(new Phrase("IP Interval",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		          parameterTable.addCell(parameterCell);
		   		           
		   		          parameterCell = new PdfPCell(new Phrase("Day Profile Date",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		             parameterTable.addCell(parameterCell);
		   		             
		   		          parameterCell = new PdfPCell(new Phrase("KWH",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		             parameterTable.addCell(parameterCell);
		   		             
		   		          parameterCell = new PdfPCell(new Phrase("KVA",new Font(Font.FontFamily.HELVETICA  ,12, Font.BOLD)));
		   		             parameterCell.setFixedHeight(25f);
		   		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		   		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		   		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY); 
		   		          parameterTable.addCell(parameterCell);
		   		          
		        				for (int i = 0; i < loadDataDateList.size(); i++) 
		    	                {
		        					parameterCell = new PdfPCell(new Phrase((i+1)+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
	   								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	   								 parameterTable.addCell(parameterCell);
		    	                	Object[] obj=loadDataDateList.get(i);
		    	                	for (int j = 0; j < obj.length; j++) 
		    	                	{
		    	                		if(j==0)
		    	                		{
		    								 parameterCell = new PdfPCell(new Phrase(obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 parameterCell = new PdfPCell(new Phrase(obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 
		    								
		    								 
		    								 parameterCell = new PdfPCell(new Phrase(obj[2]==null?"":obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    								 
		    								
		    								
		    								 parameterCell = new PdfPCell(new Phrase(obj[3]==null?"":obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,12 )));
		    								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		    								 parameterCell.setFixedHeight(25f);
		    								 parameterTable.addCell(parameterCell);
		    	                		}
		    							
		    						}
		    					} 
		        				
		    	                document.add(parameterTable);
		       
			document.close();

			response.setHeader("Content-disposition", "attachment; filename=CompleteLoadSurveyDetails_"+meterno+"_"+month+".pdf");
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
}
