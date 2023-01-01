package com.bcits.mdas.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.DtDetailsEntity;
import com.bcits.entity.TownEntity;
import com.bcits.mdas.entity.FeederMasterEntity;
import com.bcits.mdas.entity.SiteLocationEntity;
import com.bcits.mdas.entity.SubstationDetailsEntity;
import com.bcits.mdas.service.substationdetailsservice;
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

@Component
@Repository
public class substationdetailsserviceImpl extends GenericServiceImpl<SubstationDetailsEntity>
		implements substationdetailsservice {

	@Override
	@Transactional
	public List<?> getSubstationDetails() {
		String qry = "select ss_name,ss_capacity,office_id,parent_id,sstp_id,tp_parent_id,entry_by,entry_date,update_by,update_date,parent_subdivision,parent_feeder_voltage,parent_feeder,id,ss_id,"
				+ " (SELECT DISTINCT tp_towncode||'-'||town_ipds FROM meter_data.amilocation WHERE tp_towncode=parent_town) as town "
				+ " FROM meter_data.substation_details where deleted IS NULL or deleted='0' order by ss_id ";
		System.out.println("get sub sss--" + qry);
		return postgresMdas.createNativeQuery(qry).getResultList();

	}

	public List<?> getChangedDetails(int id) {
		String qry = "select id,ss_name,ss_capacity,sstp_id,parent_id,office_id,tp_parent_id,parent_subdivision,parent_feeder_voltage,parent_feeder,ss_id FROM meter_data.substation_details where id ="
				+ id + "";
		return postgresMdas.createNativeQuery(qry).getResultList();

	}

	public int getModifyDetails(String substaidd, String substanamee, String substacapp, String substacodee,
			String tpparcodee, String userName, String latitude, String longitude, String dcuno,
			String editSubstationCapacityinMVA) {

		long currtime = System.currentTimeMillis();
		/* String userName = (String) session.getAttribute("username"); */
		Timestamp uppdatetime = new Timestamp(currtime);
		int i = 0;
		if (substacapp.isEmpty()) {
			substacapp = null;
		}
		if (latitude.isEmpty()) {
			latitude = null;
		}
		if (longitude.isEmpty()) {
			longitude = null;
		}

		if (editSubstationCapacityinMVA.isEmpty()) {
			editSubstationCapacityinMVA = null;
		}

		// String sql ="update meter_data.substation_details set
		// ss_name='"+substanamee+"',
		// ss_capacity="+substacapp+",sstp_id='"+substacodee+"',tp_parent_id='"+tpparcodee+"',update_by='"+userName+"',update_date='"+uppdatetime+"'
		// where ss_id ='"+substaidd+"'";

		String sql = "update meter_data.substation_details set ss_name='" + substanamee + "', ss_capacity=" + substacapp
				+ ",update_by='" + userName + "',update_date='" + uppdatetime + "',latitude=" + latitude + ",longitude="
				+ longitude + ",dcuno='" + dcuno + "',substation_mva=" + editSubstationCapacityinMVA
				+ " where sstp_id ='" + substaidd + "'";
		// System.out.println(sql);
		try {
			i = postgresMdas.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getDistinctZone() {
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT zone from meter_data.amilocation";

			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getCircleByZone(String zone, ModelMap model) {
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT circle from meter_data.amilocation WHERE zone like '" + zone + "'";

			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getDivisionByCircle(String circle, ModelMap model) {
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT division from meter_data.amilocation WHERE circle like '" + circle + "'";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<?> getSubdivisionByDivision(String division, ModelMap model) {
		List<?> list = null;
		try {
			String sql = "SELECT DISTINCT subdivision from meter_data.amilocation WHERE division like '" + division
					+ "'";
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<?> getSubdivVolBySubdiv() {

		List<?> resultList = null;
		String qry = "select DISTINCT(voltagelevel) from meter_data.feederdetails";
		try {
			resultList = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getParentfeeder(Double parentfeedervol, String sitecode) {
		List<?> resultList = null;
		String qry = "select distinct feedername from meter_data.feederdetails where officeId = '" + sitecode
				+ "' and voltagelevel='" + parentfeedervol + "'";
		try {
			resultList = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public String getSsid() {
		String resultList = null;
		String qry = "";
		/*
		 * String
		 * qry="SELECT B. rule||(case WHEN length (B.id)=1 then '0'||B.id else B.id  END) from \n"
		 * + "( \n" +
		 * "SELECT substr(A.ssid, 0,3) as rule,CAST(CAST(substr(A.ssid, 3,length(A.ssid))as INTEGER)+1 as TEXT) as id FROM \n"
		 * + "( \n" +
		 * "SELECT COALESCE(max(ss_id),'SS00') as ssid FROM meter_data.substation_details \n"
		 * + ")A \n" + ")B ";
		 */

		qry = "SELECT B. rule||(case WHEN length(B.id)=1 THEN '00000'||B.id WHEN length(B.id)=2 THEN '0000'||B.id WHEN length(B.id)=3 THEN '000'||B.id WHEN length(B.id)=4 THEN '00'||B.id WHEN length(B.id)=5 THEN '0'||B.id ELSE B.id END) from ( SELECT substr(A.ssid, 0,3) as rule,CAST(CAST(substr(A.ssid, 3,length(A.ssid))as INTEGER)+1 as TEXT) as id FROM ( SELECT COALESCE(max(ss_id),'SS000000') as ssid FROM meter_data.substation_details)A )B ;";
		try {
			System.out.println("qry--sid:" + qry);
			resultList = (String) getCustomEntityManager("postgresMdas").createNativeQuery(qry).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;

	}

	@Override
	public int subStationDel(String str) {
		return postgresMdas.createNativeQuery(str).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getTownList(String zone, String circle) {
		List<?> resultList = null;

		String sql = "select distinct tp_towncode,town_ipds,string_agg(distinct circle, ',') as circle,string_agg(distinct tp_circlecode, ',') as circlecode,t.createdby,t.createddate,t.sld_file,t.technical_loss,t.golivedate,t.filename,t.updatedby,t.updateddate,t.baseline_loss,string_agg(distinct tp_zonecode, ',') as tp_zonecode,string_agg(distinct zone, ',') as zone  FROM meter_data.amilocation\r\n"
				+ " a ,meter_data.town_master t where zone like '" + zone + "' and circle like '" + circle
				+ "' and t.towncode=a.tp_towncode \r\n"
				+ "group by tp_towncode,town_ipds,t.createdby,t.createddate,t.sld_file,t.technical_loss,t.golivedate,t.filename,t.updatedby,t.baseline_loss,t.updateddate \r\n"
				+ "order by tp_towncode";

		try {
			System.out.println(sql);
			resultList = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public List<?> getSubstationDetailsNew() {
//		String qry="SELECT ss_name,max(ss_capacity) as ss_capacity,sstp_id, \n" +
//				"count(DISTINCT tp_parent_id) as parent_count, \n" +
//				"count( DISTINCT parent_town) as town_count,\n" +
//				"max(entry_by) as  entry_by, max(entry_date) as entry_date,\n" +
//				"max(update_by) as update_by, max(update_date) as update_date,latitude,longitude,dcuno,substation_mva,filename\n" +
//				"FROM  meter_data.substation_details where deleted IS NULL or deleted='0'\n" +
//				"GROUP BY ss_name,sstp_id,latitude,longitude,dcuno,substation_mva,filename\n" +
//				"ORDER BY ss_name,sstp_id";

		
		  String qry ="SELECT sd.ss_name,max(ss_capacity) as ss_capacity,sstp_id, \r\n"
		  + "count(DISTINCT fd.tp_fdr_id) as feeder_count, \r\n" +
		  "count( DISTINCT sd.parent_town) as town_count,\r\n" +
		  "max(sd.entry_by) as  entry_by, max(sd.entry_date) as entry_date,\r\n" +
		  "max(sd.update_by) as update_by, max(sd.update_date) as update_date,sd.latitude,sd.longitude,sd.dcuno,sd.substation_mva,sd.filename\r\n"
		  +
		  "FROM  meter_data.substation_details sd Left JOIN meter_data.feederdetails fd ON(sd.sstp_id=fd.tpparentid) where sd.deleted IS NULL or sd.deleted='0' and meterno IN (select mtrno from meter_data.master_main WHERE fdrcategory='FEEDER METER' and fdrtype='IPDS')\r\n"
		  +
		  "GROUP BY ss_name,sstp_id,latitude,longitude,dcuno,substation_mva,filename\r\n"
		  + "ORDER BY ss_name,sstp_id";
		

		System.out.println(qry);
		return postgresMdas.createNativeQuery(qry).getResultList();
	}

	@Override
	public List<?> getmasterSubstationDetailsNew(String zone, String circle, String town_code) {

		String sql = "\r\n"
				+ "SELECT DISTINCT sd.ss_name as substation_name,max(ss_capacity) as ss_capacity,sd.sstp_id, \r\n"
				+ "count(DISTINCT sd.parent_town) as town_count,\r\n"
				+ "count(DISTINCT fd.tp_fdr_id) as feeder_count,  \r\n"
				+ "max(sd.entry_by) as  entry_by, max(sd.entry_date) as entry_date,\r\n"
				+ "max(sd.update_by) as update_by, max(sd.update_date) as update_date,sd.latitude,sd.longitude,sd.dcuno,sd.substation_mva,mm.zone as region,mm.circle,ac.town_ipds  \r\n"
				+ "FROM  meter_data.substation_details sd LEFT JOIN meter_data.master_main mm ON(sd.parent_town = mm.town_code) LEFT JOIN meter_data.feederdetails  fd ON(sd.sstp_id=fd.tpparentid) LEFT JOIN meter_data.amilocation ac ON(mm.town_code=ac.tp_towncode) Where mm.zone like '"
				+ zone + "' and mm.circle like '" + circle + "' and mm.town_code LIKE '" + town_code
				+ "'  GROUP BY sd.ss_name,mm.zone,mm.circle,sd.latitude,sd.longitude,sd.dcuno,sd.substation_mva,sd.sstp_id,ac.town_ipds";

		System.out.println(sql);
		return postgresMdas.createNativeQuery(sql).getResultList();
	}

	@Override
	public void getTownDetailsPdf(HttpServletRequest request, HttpServletResponse response, String region,
			String circle) {

		String regionn = "", circlee = "";
		try {
			if (region == "%") {
				regionn = "ALL";
			} else {
				regionn = region;
			}
			if (circle == "%") {
				circlee = "ALL";
			} else {
				circlee = circle;
			}

			Rectangle pageSize = new Rectangle(1450, 720);
			Document document = new Document(pageSize);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// PdfWriter.getInstance(document, baos);
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
			p1.add(new Phrase("Town Details ", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
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
					new Phrase("Region :" + regionn, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			headerCell = new PdfPCell(
					new Phrase("Circle :" + circlee, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			String query = "";
			List<Object[]> TownData1 = null;
			query = "select distinct string_agg(distinct tp_circlecode, ',') as circlecode,string_agg(distinct circle, ',') as circle,tp_towncode,town_ipds,t.technical_loss,t.baseline_loss,t.golivedate,\n"
					+ "t.createdby,to_char(t.createddate,'YYYY-MM-DD HH24:MI:ss')as createddate,t.updatedby,to_char(t.updateddate,'YYYY-MM-DD HH24:MI:ss')as updateddate,t.sld_file,t.filename,string_agg(distinct tp_zonecode, ',') as tp_zonecode,string_agg(distinct zone, ',') as zone FROM meter_data.amilocation a ,meter_data.town_master t where zone like '"
					+ region + "' and circle like '" + circle + "' and t.towncode=a.tp_towncode  \n"
					+ "group by tp_towncode,town_ipds,t.createdby,t.createddate,t.sld_file,t.technical_loss,t.golivedate,t.filename,t.updatedby,t.baseline_loss,t.updateddate order by tp_towncode";

			System.out.println(query);
			TownData1 = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(14);
			parameterTable.setWidths(new int[] { 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.NO", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("REGION CODE", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("REGION", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("CIRCLE CODE", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("CIRCLE NAME", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
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

			parameterCell = new PdfPCell(new Phrase("TOWN NAME", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("TECHNICALLOSS(%)", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("BASELINELOSS(%)", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("GOLIVE DATE", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("ENTRY BY", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("ENTRY DATE", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("UPDATED BY", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("UPDATED DATE", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < TownData1.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Object[] obj = TownData1.get(i);
				for (int j = 0; j < obj.length; j++) {
					if (j == 0) {
						String value1 = obj[0] + "";

						parameterCell = new PdfPCell(new Phrase(obj[13] == null ? null : obj[13] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[14] == null ? null : obj[14] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

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

						parameterCell = new PdfPCell(new Phrase(obj[10] == null ? null : obj[10] + "",
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
			response.setHeader("Content-disposition", "attachment; filename=TownDetails.pdf");
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
	public void getSubstationDtlspdf(HttpServletRequest request, HttpServletResponse response, String zone,
			String circle, String town) {

		List<Object[]> subData = null;
		String zonename = "", cirname = "", townname = "";
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
			LocalDateTime sysdatetime = LocalDateTime.now();

			/*
			 * if (zone.equalsIgnoreCase("%") && circle.equalsIgnoreCase("%") &&
			 * town.equalsIgnoreCase("%")){
			 * 
			 * zonename="ALL"; townname="ALL"; cirname="ALL";
			 * 
			 * } else {
			 * 
			 * String qry=""; }
			 */

			if (zone.equalsIgnoreCase("%")) {
				zonename = "ALL";
			} else {
				zonename = zone;
			}

			if (circle.equalsIgnoreCase("%")) {
				cirname = "ALL";
			} else {
				cirname = circle;
			}
			if (town.equalsIgnoreCase("%")) {
				townname = "ALL";
			} else {
				townname = town;
			}

			Rectangle pageSize = new Rectangle(1350, 720);
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
			p1.add(new Phrase("Sub-Station Details", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.BOTTOM);
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
			List<Object[]> SubstationDetailsData = null;
//			query="SELECT ss_name,max(ss_capacity) as ss_capacity,sstp_id,count(DISTINCT tp_parent_id) as parent_count, count( DISTINCT parent_town) as town_count,\n" +
//					"max(entry_by) as  entry_by, max(entry_date) as entry_date,max(update_by) as update_by, max(update_date) as update_date,latitude,longitude,dcuno,substation_mva\n" +
//					"FROM  meter_data.substation_details where deleted IS NULL or deleted='0' GROUP BY ss_name,sstp_id,latitude,longitude,dcuno,substation_mva ORDER BY ss_name,sstp_id";

			query = "SELECT DISTINCT sd.ss_name as substation_name,max(ss_capacity) as ss_capacity,sd.sstp_id, \r\n"
					+ "count(DISTINCT sd.parent_town) as town_count,\r\n"
					+ "count(DISTINCT fd.tp_fdr_id) as feeder_count,  \r\n"
					+ "max(sd.entry_by) as  entry_by, max(sd.entry_date) as entry_date,\r\n"
					+ "max(sd.update_by) as update_by, max(sd.update_date) as update_date,sd.latitude,sd.longitude,sd.dcuno,sd.substation_mva,mm.zone as region,mm.circle,ac.town_ipds  \r\n"
					+ "FROM  meter_data.substation_details sd LEFT JOIN meter_data.master_main mm ON(sd.parent_town = mm.town_code) LEFT JOIN meter_data.feederdetails  fd ON(sd.sstp_id=fd.tpparentid) LEFT JOIN meter_data.amilocation ac ON(mm.town_code=ac.tp_towncode) Where mm.zone like '"
					+ zone + "' and mm.circle like '" + circle + "' and mm.town_code LIKE '" + town
					+ "'  GROUP BY sd.ss_name,mm.zone,mm.circle,sd.latitude,sd.longitude,sd.dcuno,sd.substation_mva,sd.sstp_id,ac.town_ipds\r\n"
					+ " ";
			System.out.println(query);
			SubstationDetailsData = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(17);
			parameterTable.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("Sl.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Region", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("circle", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
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

			parameterCell = new PdfPCell(
					new Phrase("Substation Name", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Voltage level", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Substation Code", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Subdivision Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Town Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Latitude", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Longitude", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("DCUNo", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Capacity(in MVA)", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Entry By", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Entry Date ", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Update By", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("Update Date", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(25f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < SubstationDetailsData.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Object[] obj = SubstationDetailsData.get(i);
				for (int j = 0; j < obj.length; j++) {
					if (j == 0) {
						String value1 = obj[0] + "";

						parameterCell = new PdfPCell(new Phrase(obj[13] == null ? null : obj[13] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[14] == null ? null : obj[14] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[15] == null ? null : obj[15] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

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

						parameterCell = new PdfPCell(new Phrase(obj[9] == null ? null : obj[9] + "",
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
			response.setHeader("Content-disposition", "attachment; filename=SubstationDetails.pdf");
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
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> getExcelTownList(String zone, String circle) {
		List<?> resultList = null;

		String sql = "select distinct tp_towncode,town_ipds,string_agg(distinct circle, ',') as circle,string_agg(distinct tp_circlecode, ',') as circlecode,t.createdby,to_char(t.createddate,'YYYY-MM-DD HH24:MI:ss')as createddate,t.sld_file,t.technical_loss,t.golivedate,t.filename,t.updatedby,to_char(t.updateddate,'YYYY-MM-DD HH24:MI:ss')as updateddate,t.baseline_loss,string_agg(distinct tp_zonecode, ',') as tp_zonecode,string_agg(distinct zone, ',') as zone FROM meter_data.amilocation\r\n"
				+ " a ,meter_data.town_master t where zone like '" + zone + "' and circle like '" + circle
				+ "' and t.towncode=a.tp_towncode \r\n"
				+ "group by tp_towncode,town_ipds,t.createdby,t.createddate,t.sld_file,t.technical_loss,t.golivedate,t.filename,t.updatedby,t.baseline_loss,t.updateddate \r\n"
				+ "order by tp_towncode";

		System.out.println(sql);

		try {
			resultList = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public List<SubstationDetailsEntity> getSubstationBySSTpCode(String sscode) {
		// System.out.println("hi---");
		List<SubstationDetailsEntity> result = new ArrayList<SubstationDetailsEntity>();
		try {
			// System.out.println("in serviceimpl");
			result = postgresMdas.createNamedQuery("SubstationDetailsEntity.getSubstationBySSTpCode")
					.setParameter("sstpid", sscode).getResultList();
			System.out.println("result is---" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public SubstationDetailsEntity getSubstationByCode(String sscode) {
		try {
			return getCustomEntityManager("postgresMdas")
					.createNamedQuery("SubstationDetailsEntity.getSubstationBySSTpCode", SubstationDetailsEntity.class)
					.setParameter("sstpid", sscode).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
