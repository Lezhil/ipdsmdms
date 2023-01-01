package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.entity.D2Data;
import com.bcits.mdas.service.AmrInstantaneousService;
import com.bcits.service.D2DataService;
import com.bcits.service.PfAngleService;
import com.bcits.utility.MDMLogger;
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
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Repository
public class D2DataServiceImpl extends GenericServiceImpl<D2Data> implements D2DataService 
{
	@Autowired
	private PfAngleService pfAngleService;
	
	@Autowired
	private AmrInstantaneousService amrInstantaneousService;
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<D2Data> findAll(String meterNo,String billMonth,ModelMap model)
	{	
		List<D2Data> list= postgresMdas.createNamedQuery("D2Data.AllDetails").setParameter("meterNo", meterNo).setParameter("billMonth", Integer.parseInt(billMonth)).getResultList();
		String rPfValue,yPfValue,bPfValue ;
		String rPhaseIa="",yPhaseIb="",bPhaseIc="";
		model.put("instantanousData", list);
		model.put("meterNo", meterNo);
		model.put("selectedMonth", billMonth);
		
		model.put("viewCategory", "instantaneous");
		
		if(list.size()==0)
		{
			model.put("msg", "No data found for entered Meter number and selected yearMonth");
		}
		else
		{
			/*List<PfAngle> rList = pfAngleService.getLeadAngle(list.get(0).getrPhasePfVal());
			rPfValue = rList.get(0).getPfAngle();
			List<PfAngle> yList = pfAngleService.getLeadAngle(list.get(0).getyPhasePfVal());
			yPfValue = yList.get(0).getPfAngle();
			List<PfAngle> bList = pfAngleService.getLeadAngle(list.get(0).getbPhasePfVal());
			bPfValue = bList.get(0).getPfAngle();
			model.put("rPhasePfValue", rPfValue);
			model.put("yPhasePfValue", yPfValue);
			model.put("bPhasePfValue", bPfValue);*/
			
			model.put("rPhaseAngle", list.get(0).getiRAngle());
			model.put("yPhaseAngle", list.get(0).getiYAngle());
			model.put("bPhaseAngle", list.get(0).getiBAngle());
		}
		return list;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<D2Data> download360ViewPdf(String meterno, String month,ModelMap model,HttpServletResponse response)
	{	
		String circle="",division="",subDivision="",consumerName="",address="",cd="",sanLoad="",msf="";
		List<Object[]> data=null;
		List<Object[]> masterData=null;
		List<Object[]> dataHeader=null;
		 List<D2Data> d2data=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try 
		{
			String sql="SELECT MA.CIRCLE,MA.DIVISION,MA.SDONAME,MA.NAME,MA.ADDRESS1,MM.METRNO, MA.CONTRACTDEMAND||'',MA.SANLOAD||'',MM.MF||'' FROM BSMARTMDM.MASTER MA,BSMARTMDM.METERMASTER "
					+ "MM WHERE MA.ACCNO=MM.ACCNO AND MM.RDNGMONTH='"+month+"' AND MM.METRNO like '"+meterno+"'";
			
			System.out.println("inside download360ViewPdf==>"+sql);
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
		        pstart.add(new Phrase("BCITS Private Ltd",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		        cell1.setBorder(Rectangle.NO_BORDER);
		        cell1.addElement(pstart);
		        pdf2.addCell(cell1);
		        pstart.add(new Chunk(glue));
		        pstart.add(new Phrase("Reading Month : "+new SimpleDateFormat("MMM-YYYY").format(new SimpleDateFormat("yyyyMM").parse(month)),new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		        
		        document.add(pdf2);
		        PdfPCell cell2 = new PdfPCell();
		        Paragraph p1 = new Paragraph();
		        p1.add(new Phrase("INSTANTANEOUS PARAMETER : "+meterno,new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD)));
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
		             
		             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		             header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
		             header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
		             document.add(header);
		             
		             PdfPTable table = new PdfPTable(3);
		             table.setWidths(new int[]{2,2,2});
		             table.setWidthPercentage(100);
		             PdfPCell cell;
		             cell = new PdfPCell(new Phrase("SR NO.",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		             cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		             cell.setFixedHeight(26f);
		             table.addCell(cell);
		             
		             cell = new PdfPCell(new Phrase("Parameter",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		             cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		             cell.setFixedHeight(26f);
		             table.addCell(cell);
		             
		             cell = new PdfPCell(new Phrase("Value",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		             cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		             cell.setFixedHeight(26f);
		             table.addCell(cell);
		             String mtrSerialNo="",mtrDateTime="",mtrType="",mtrClass="",manufacturerName="",phaseSequence="";
		             d2data=findAll(meterno, month, model);
		             for (D2Data element : d2data)
		             {
		            	 mtrSerialNo=element.getCdfData().getMeterNo();
		            	 mtrDateTime=element.getCdfData().getD1data().getMeterDate();
		            	 mtrType=element.getCdfData().getD1data().getMeterType();
		            	 mtrClass=element.getCdfData().getD1data().getMeterClass();
		            	 manufacturerName=element.getCdfData().getD1data().getManufacturerName();
		            	 phaseSequence=element.getPhaseSequence();
					 }
		             cell=new PdfPCell(new Phrase("1",new Font(Font.FontFamily.HELVETICA  ,13)));
		             cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase("Meter Serial No.",new Font(Font.FontFamily.HELVETICA  ,13)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase(mtrSerialNo,new Font(Font.FontFamily.HELVETICA  ,13)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 
	                 cell=new PdfPCell(new Phrase("2",new Font(Font.FontFamily.HELVETICA  ,13)));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase("Meter Date Time",new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase(mtrDateTime,new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 
	                 cell=new PdfPCell(new Phrase("3",new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase("Meter Type.",new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase(mtrType,new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 
	                 cell=new PdfPCell(new Phrase("4",new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase("Meter Class",new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase(mtrClass,new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 
	                 cell=new PdfPCell(new Phrase("5",new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase("Meter Manufacturer Name",new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase(manufacturerName,new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase("6",new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase("Phase Sequence",new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 cell=new PdfPCell(new Phrase(phaseSequence,new Font(Font.FontFamily.HELVETICA  ,13 )));
	                 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	                 cell.setFixedHeight(20f);
	                 table.addCell(cell);
	                 
	                 List<Object[]> listParameter=new ArrayList<Object[]>();
	                 for (int i = 0; i < d2data.size(); i++) 
	                 {
	                	 Object[] voltageArr=new Object[4];
						D2Data dataValue=d2data.get(i);
						voltageArr[0]="Voltages";
						voltageArr[1]=dataValue.getrPhaseVal()+"V";
						voltageArr[2]=dataValue.getyPhaseVal()+"V";
						voltageArr[3]=dataValue.getbPhaseVal()+"V";
						listParameter.add(voltageArr);
						
						 Object[] lineCurrentArr=new Object[4];
						 lineCurrentArr[0]="Line Current";
						 lineCurrentArr[1]=dataValue.getrPhaseLineVal()+"A";
						 lineCurrentArr[2]=dataValue.getyPhaseLineVal()+"A";
						 lineCurrentArr[3]=dataValue.getbPhaseLineVal()+"A";
						 listParameter.add(lineCurrentArr);
						 
						 Object[] activeCurrentArr=new Object[4];
						 activeCurrentArr[0]="Active Current";
						 activeCurrentArr[1]=dataValue.getrPhaseActiveVal()+"A";
						 activeCurrentArr[2]=dataValue.getyPhaseActiveVal()+"A";
						 activeCurrentArr[3]=dataValue.getbPhaseActiveVal()+"A";
						 listParameter.add(activeCurrentArr);
						 
						 Object[] powerFactorArr=new Object[4];
						 powerFactorArr[0]="Power Factor";
						 powerFactorArr[1]=dataValue.getrPhasePfVal();
						 powerFactorArr[2]=dataValue.getyPhasePfVal();
						 powerFactorArr[3]=dataValue.getbPhasePfVal();
						 listParameter.add(powerFactorArr);
						 
						 Object[] currentAngleFactorArr=new Object[4];
						 currentAngleFactorArr[0]="Current Angle Factor";
						 currentAngleFactorArr[1]=dataValue.getrPhaseCuurentAngle()==null?"":dataValue.getrPhaseCuurentAngle();
						 currentAngleFactorArr[2]=dataValue.getyPhaseCuurentAngle()==null?"":dataValue.getyPhaseCuurentAngle();
						 currentAngleFactorArr[3]=dataValue.getbPhaseCuurentAngle()==null?"":dataValue.getbPhaseCuurentAngle();
						 listParameter.add(currentAngleFactorArr);
					 }
	                 
		             document.add(table);
		             
		             PdfPTable header2 = new PdfPTable(4);
		             header2.setWidthPercentage(100);
		             header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		             header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		             header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		             header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		             header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		             header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		             header2.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
		             header2.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		             document.add(header2);
		             
		             PdfPTable header3 = new PdfPTable(1);
		             header3.setWidthPercentage(100); // percentage
		             header3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		             Chunk underline = new Chunk("LineVoltage,Current and PF Parameters:-",new Font(Font.FontFamily.HELVETICA  ,16, Font.BOLD));
		             underline.setUnderline(0.1f, -2f);
		             PdfPCell cell3 = new PdfPCell();
				        Paragraph pstart3 = new Paragraph();
				        pstart3.add(underline);
				        cell3.setBorder(Rectangle.NO_BORDER);
				        cell3.addElement(pstart3);
				        header3.addCell(cell3);
				        document.add(header3);
		             
				        PdfPTable header4 = new PdfPTable(4);
				        header4.setWidthPercentage(100);
				        header4.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
				        header4.addCell(getCell("", PdfPCell.ALIGN_LEFT));
				        header4.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
				        header4.addCell(getCell("", PdfPCell.ALIGN_LEFT));
			             document.add(header4);
				        
	                 PdfPTable parameterTable = new PdfPTable(4);
	                 parameterTable.setWidths(new int[]{2,2,2,2});
	                 parameterTable.setWidthPercentage(100);
		             PdfPCell parameterCell;
		             parameterCell = new PdfPCell(new Phrase("SI NO.",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		             parameterCell.setFixedHeight(25f);
		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		             parameterTable.addCell(parameterCell);
		             
		             parameterCell = new PdfPCell(new Phrase("L1/Element1",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		             parameterCell.setFixedHeight(25f);
		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		             parameterTable.addCell(parameterCell);
		             
		             parameterCell = new PdfPCell(new Phrase("L2/Element2",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		             parameterCell.setFixedHeight(25f);
		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		             parameterTable.addCell(parameterCell);
		             
		             parameterCell = new PdfPCell(new Phrase("L3/Element3",new Font(Font.FontFamily.HELVETICA  ,13, Font.BOLD)));
		             parameterCell.setFixedHeight(25f);
		             parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		             parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		             parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		             parameterTable.addCell(parameterCell);
	                 
	                for (int i = 0; i < listParameter.size(); i++) 
	                {
	                	Object[] obj=listParameter.get(i);
	                	for (int j = 0; j < obj.length; j++) 
	                	{
							if(j==0)
							{
								MDMLogger.logger.info("==================>obj"+obj[j]);
								 parameterCell = new PdfPCell(new Phrase(obj[0]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								 parameterCell.setFixedHeight(20f);
								 parameterTable.addCell(parameterCell);
								 parameterCell = new PdfPCell(new Phrase(obj[1]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								 parameterCell.setFixedHeight(20f);
								 parameterTable.addCell(parameterCell);
								 parameterCell = new PdfPCell(new Phrase(obj[2]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								 parameterCell.setFixedHeight(20f);
								 parameterTable.addCell(parameterCell);
								 parameterCell = new PdfPCell(new Phrase(obj[3]+"",new Font(Font.FontFamily.HELVETICA  ,13 )));
								 parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
								 parameterCell.setFixedHeight(20f);
								 parameterTable.addCell(parameterCell);
					             
							}
						}
					} 
	                document.add(parameterTable);
			document.close();

			response.setHeader("Content-disposition", "attachment; filename=InstantaneousParameters_"+meterno+"-"+month+".pdf");
			response.setContentType("application/pdf");
			ServletOutputStream outstream = response.getOutputStream();
			baos.writeTo(outstream);
			outstream.flush();
			outstream.close();

			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<D2Data> getInstansData(String mtrNo) 
	{
		String sql = "";
		List<D2Data> list=null;
		try 
		{
			sql="SELECT * FROM ((SELECT cast('R' as text) as Phase,r_phase_val as Voltage_KV,r_phase_line_val as Current_Amps,r_phase_pf_val as Power_Factor,read_time  \n" +
					"FROM meter_data.d2_data WHERE meter_number='"+mtrNo+"')\n" +
					"UNION\n" +
					"(SELECT cast('Y' as text) as Phase,y_phase_val as Voltage_KV,y_phase_line_val as Current_Amps,y_phase_pf_val as Power_Factor,read_time  \n" +
					"FROM meter_data.d2_data WHERE meter_number='"+mtrNo+"')\n" +
					"UNION\n" +
					"(SELECT cast('B' as text) as Phase,b_phase_val as Voltage_KV,b_phase_line_val as Current_Amps,b_phase_pf_val as Power_Factor,read_time  \n" +
					"FROM meter_data.d2_data WHERE meter_number='"+mtrNo+"'))AA WHERE AA.read_time=(SELECT  max(read_time) FROM meter_data.d2_data WHERE meter_number='"+mtrNo+"')";
			
			System.out.println("in event sql==>"+sql);
			list = postgresMdas.createNativeQuery(sql).getResultList();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	
	
	
}
