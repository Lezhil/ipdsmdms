package com.bcits.bsmartwater.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.IOException;
import java.net.MalformedURLException;


public class HeaderFooterPageEvent extends PdfPageEventHelper {
	 private PdfTemplate t;
	 
		@Override
	    public void onEndPage(PdfWriter writer, Document document) {
	        addHeader(writer);
	        addFooter(writer);
	    }
		public void onOpenDocument(PdfWriter writer, Document document) {
	        t = writer.getDirectContent().createTemplate(30, 20);
	       
	    }


	    private void addHeader(PdfWriter writer){
	        PdfPTable header = new PdfPTable(2);
	        try {
	            // set defaults
	            header.setWidths(new int[]{2, 30});
	            header.setTotalWidth(550);
	            header.setLockedWidth(true);
	            header.getDefaultCell().setFixedHeight(35);
	            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
	            header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

	            // add image
	           /* Image logo = Image.getInstance("C:/bsmartenergy.png");
	            header.addCell(logo);*/
	           
	            // add text
	            PdfPCell text = new PdfPCell();
	            text.setPaddingBottom(20);
	            text.setPaddingLeft(10);
	            //text.setBorder(Rectangle.BOTTOM);
	            //text.setBorderColor(BaseColor.LIGHT_GRAY);
	            //text.addElement(new Phrase("SubDivision:"+subdivision+"           billmonth:"+billmonth +"           binder: "+binder, new Font(Font.FontFamily.HELVETICA, 10)));
	            //text.addElement(new Phrase("billmonth", new Font(Font.FontFamily.HELVETICA, 10)));
	            //text.addElement(new Phrase("billmonth", new Font(Font.FontFamily.HELVETICA, 10)));
	            
	            header.addCell(text);

	            // write content
	            header.writeSelectedRows(0, -1, 36, 810, writer.getDirectContent());
	        } catch(DocumentException de) {
	            throw new ExceptionConverter(de);
	        } 
	    }

	    private void addFooter(PdfWriter writer){
	        PdfPTable footer = new PdfPTable(3);
	        try {
	            // set defaults
	            footer.setWidths(new int[]{24, 5, 4});
	            footer.setTotalWidth(600);
	            footer.setLockedWidth(true);
	            footer.getDefaultCell().setFixedHeight(25);
	            footer.getDefaultCell().setBorder(Rectangle.TOP);
	            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

	            // add copyright
	           footer.addCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));

	            // add current page count
	            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	            footer.addCell(new Phrase(String.format("PageNo:%d", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 13)));

	            // add placeholder for total page count
	            PdfPCell totalPageCount = new PdfPCell();
	            totalPageCount.setBorder(Rectangle.TOP);
	            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
	            footer.addCell(totalPageCount);

	            PdfContentByte canvas = writer.getDirectContent();
		           canvas.beginText();
		            footer.writeSelectedRows(0, -1, 36, 60, canvas);
		            canvas.endText();
	        } catch(DocumentException de) {
	            throw new ExceptionConverter(de);
	        }
	    }

	    public void onCloseDocument(PdfWriter writer, Document document) {
	        int totalLength = String.valueOf(writer.getPageNumber()).length();
	        int totalWidth = totalLength * 6;
	        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
	                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 10)),
	                totalWidth, 6, 0);
	    }
}
