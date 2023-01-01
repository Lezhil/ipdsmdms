package com.bcits.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.utils.HeaderFooterPageEvent;
import com.bcits.entity.DtDetailsEntity;
import com.bcits.service.ReliabilityIndicesService;
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
public class ReliabilityIndicesServiceImpl extends GenericServiceImpl<DtDetailsEntity>
		implements ReliabilityIndicesService {

	@Override
	public List<?> getReliabalitySingleFeederData(String fromDate, String toDate, String fdrNo, String subStationCode) {

		String qry = "SELECT\n"
				+ "	( SELECT subdivision AS SUBDIVNAME FROM meter_data.amilocation WHERE sitecode = fd.officeid ),\n"
				+ "	( SELECT ss_name AS substationName FROM meter_data.substation_details WHERE ss_id = fd.parentid ),\n"
				+ "		Z.fdr_id ,tp_fdr_id,	feedername,fd.meterno,fd.parentid,	Z.totalConsumers,	Z.SAIFI,	\n"
				+ "	ROUND( Z.SAIDI ) AS SAIDI,	ROUND(SAIDI/SAIFI) AS CAIDI,Z.MAIFI FROM\n"
				+ "	meter_data.feederdetails fd,\n"
				+ "	(SELECT	round(( cast(W.morethan10SAIFI as decimal) / cast(Y.totalConsumer as decimal)),2) AS SAIFI,	\n"
				+
				/* "	(SELECT	W.morethan10SAIFI / Y.totalConsumer AS SAIFI,	\n" + */
				"	Y.fdr_name,Y.fdr_id,Y.totalConsumer as totalConsumers,\n"
				+ "	round(( cast(W.morethanDuration10 as decimal) / cast(Y.totalConsumer as decimal)),2) AS SAIDI,\n"
				+ "	 round(( cast(W.lessthan10MAIFI as decimal) / cast(Y.totalConsumer as decimal)),2) AS MAIFI  \n"
				+ "	FROM	\n" + "	(SELECT \n"
				+ "	count(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 >= 10 then 1 end ) as morethan10SAIFI,\n"
				+ "	sum(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 >= 10 then EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 end ) as morethanDuration10,\n"
				+ "	count(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 < 10 then 1 end ) as lessthan10MAIFI ,\n"
				+ "	sum(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 < 10 then EXTRACT ( EPOCH FROM  ( event_duration ) ) / 60 end ) as lessthan10 \n"
				+ "		FROM	meter_data.event_details ed \n" + "	WHERE\n"
				+ "	TO_CHAR( event_occ_date, 'YYYY-MM' ) BETWEEN '" + fromDate + "' 	AND '" + toDate + "' \n"
				+ "	AND meter_sr_number in (select meterno from meter_data.feederdetails where fdr_id =  '" + fdrNo
				+ "' and parentid = '" + subStationCode + "')		) W,\n" + "	\n"
				+ "	( SELECT tot_consumers AS totalConsumer, fdr_name,fdr_id FROM meter_data.rpt_ea_feeder_losses rptFdrL WHERE fdr_id = '"
				+ fdrNo + "') Y \n" + "		) Z \n" + "	WHERE\n" + "	Z.fdr_id = fd.fdr_id and fd.parentid='"
				+ subStationCode + "' ";

		List<Object[]> list = null;
		try {
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<?> getReliabalityMultipleFeederData(String fromDate, String[] feederMultiple, String subStationCode) {

		String FinalString = null;
		ArrayList<String> ae = new ArrayList<>();
		String testSmaple2 = null;
		int size = feederMultiple.length;
		String test = null;
		String[] splittest = null;
		String firsttest = null;
		String finalString = null;

		for (int i = 0; i <= size - 1; i++) {
			int x = size - 1;
			String[] splittest1 = feederMultiple[i].split("-");
			if (i == 0) {
				firsttest = "('";
				test = splittest1[0];
				finalString = firsttest + test + "')";
			} else if (i != x) {
				test = test + "','" + splittest1[0];
			} else {
				test = test + "','" + splittest1[0] + "')";
				finalString = firsttest + test;
			}

		}

		String qry = "SELECT\n"
				+ "	( SELECT subdivision AS SUBDIVNAME FROM meter_data.amilocation WHERE sitecode = fd.officeid ),\n"
				+ "	( SELECT ss_name AS substationName FROM meter_data.substation_details WHERE ss_id = fd.parentid ),\n"
				+ "	Z.fdr_id,	tp_fdr_id,	feedername,	fd.meterno,	fd.parentid,Z.totalConsumers,	Z.SAIFI,	\n"
				+ "	ROUND( Z.SAIDI ) AS SAIDI,	ROUND(SAIDI/SAIFI) AS CAIDI,Z.MAIFI FROM\n"
				+ "	meter_data.feederdetails fd,\n"
				+ "	(SELECT	round(( cast(W.morethan10SAIFI as decimal) / cast(Y.totalConsumer as decimal)),2) AS SAIFI,	\n"
				+ "	Y.fdr_name,Y.fdr_id,	Y.totalConsumer as totalConsumers,\n"
				+ "	round(( cast(W.morethanDuration10 as decimal) / cast(Y.totalConsumer as decimal)),2) AS SAIDI,\n"
				+ "	 round(( cast(W.lessthan10MAIFI as decimal) / cast(Y.totalConsumer as decimal)),2) AS MAIFI  \n"
				+ "	FROM	\n" + "	(SELECT \n"
				+ "count(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 >10 then 1 end ) as morethan10SAIFI,\n"
				+ "sum(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 >10 then EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 end ) as morethanDuration10,\n"
				+ "count(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 <=10 then 1 end ) as lessthan10MAIFI ,\n"
				+ "sum(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 <=10 then EXTRACT ( EPOCH FROM  ( event_duration ) ) / 60 end ) as lessthan10 \n"
				+ "		FROM	meter_data.event_details ed \n" + "	WHERE\n"
				+ "	TO_CHAR( event_occ_date, 'YYYY-MM' ) ='" + fromDate + "' 	\n"
				+ "	AND meter_sr_number in (select meterno from meter_data.feederdetails where fdr_id in " + finalString
				+ "  and parentid = '" + subStationCode + "')		) W,\n" + "	\n"
				+ "( SELECT tot_consumers AS totalConsumer, fdr_name,fdr_id FROM meter_data.rpt_ea_feeder_losses rptFdrL WHERE fdr_id in "
				+ finalString + ") Y \n" + "		) Z \n" + "WHERE\n" + "Z.fdr_id = fd.fdr_id and fd.parentid='"
				+ subStationCode + "' ";

		System.out.println("Qyery " + qry);

		List<Object[]> list = null;
		try {
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getReliabalityMultipleDTData(String fromDate, String toDate, String[] dTNo, String substation) {

		String FinalString = null;
		ArrayList<String> ae = new ArrayList<>();
		String testSmaple2 = null;
		int size = dTNo.length;
		String test = null;
		String[] splittest = null;
		String firsttest = null;
		String finalString = null;

		for (int i = 0; i <= size - 1; i++) {
			int x = size - 1;
			String[] splittest1 = dTNo[i].split("-");
			if (i == 0) {
				firsttest = "('";
				test = splittest1[0];
				finalString = firsttest + test + "')";
			} else if (i != x) {
				test = test + "','" + splittest1[0];
			} else {
				test = test + "','" + splittest1[0] + "')";
				finalString = firsttest + test;
			}

		}

		String qry = "SELECT\n"
				+ "(SELECT subdivision AS SUBDIVNAME FROM meter_data.amilocation WHERE sitecode = dt.officeid ),\n"
				+ "	( SELECT ss_name AS substationName FROM meter_data.substation_details WHERE ss_name = dt.parent_substation),\n"
				+ "dt_id,	dttpid,	dtname,	dt.parentid,	dt.parent_feeder,	dt.meterno,	Z.totalConsumers,	round ((Z.SAIFI),2)as SAIFI ,	\n"
				+ "ROUND( Z.SAIDI ) AS SAIDI,	ROUND(SAIDI/SAIFI) AS CAIDI,round((Z.MAIFI),2) FROM\n"
				+ "meter_data.dtdetails dt,\n"
				+ "	(SELECT	round(( cast(W.morethan10SAIFI as decimal) / cast(Y.totalConsumer as decimal)),2) AS SAIFI,	\n"
				+ "	Y.dt_name,	Y.totalConsumer as totalConsumers,\n"
				+ "	round(( cast(W.morethanDuration10 as decimal) / cast(Y.totalConsumer as decimal)),2) AS SAIDI,\n"
				+ "	 round(( cast(W.lessthan10MAIFI as decimal) / cast(Y.totalConsumer as decimal)),2) AS MAIFI  \n"
				+ "	FROM	\n" + "	(SELECT \n"
				+ "count(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 >10 then 1 end ) as morethan10SAIFI,\n"
				+ "sum(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 >10 then EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 end ) as morethanDuration10,\n"
				+ "count(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 <=10 then 1 end ) as lessthan10MAIFI ,\n"
				+ "sum(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 <=10 then EXTRACT ( EPOCH FROM  ( event_duration ) ) / 60 end ) as lessthan10 \n"
				+ "		FROM	meter_data.event_details ed \n" + "	WHERE\n"
				+ "	TO_CHAR( event_occ_date, 'YYYY-MM' ) BETWEEN '" + fromDate + "' AND '" + toDate + "' \n"
				+ "	AND meter_sr_number in (select meterno from meter_data.dtdetails where dt_id in " + finalString
				+ "  and parent_substation = '" + substation + "')) W,\n" + "	\n"
				+ "( SELECT tot_consumers AS totalConsumer, dt_name FROM meter_data.rpt_eadt_losses rptFdrL WHERE dt_name in "
				+ finalString + ") Y \n" + "		) Z \n" + "WHERE\n"
				+ "Z.dt_name = dt.dt_id and dt.parent_substation='" + substation + "'   ";

		System.out.println("Qyery " + qry);
		List<Object[]> list = null;
		try {
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getReliabalitySingleDTData(String fromDate, String toDate, String dTNo, String substation) {

		String qry = "SELECT\n"
				+ "(SELECT subdivision AS SUBDIVNAME FROM meter_data.amilocation WHERE sitecode = dt.officeid ),\n"
				+ "	( SELECT ss_name AS substationName FROM meter_data.substation_details WHERE ss_name = dt.parent_substation),\n"
				+ "dt_id,	dttpid,	dtname,	dt.parentid,	dt.parent_feeder,	dt.meterno,	Z.totalConsumers,	round ((Z.SAIFI),2)as SAIFI ,	\n"
				+ "ROUND( Z.SAIDI ) AS SAIDI,	ROUND(SAIDI/SAIFI) AS CAIDI,round((Z.MAIFI),2) FROM\n"
				+ "meter_data.dtdetails dt,\n"
				+ "	(SELECT	round(( cast(W.morethan10SAIFI as decimal) / cast(Y.totalConsumer as decimal)),2) AS SAIFI,	\n"
				+ "	Y.dt_name,	Y.totalConsumer as totalConsumers,\n"
				+ "	round(( cast(W.morethanDuration10 as decimal) / cast(Y.totalConsumer as decimal)),2) AS SAIDI,\n"
				+ "	 round(( cast(W.lessthan10MAIFI as decimal) / cast(Y.totalConsumer as decimal)),2) AS MAIFI  \n"
				+ "	FROM	\n" + "	(SELECT \n"
				+ "count(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 >10 then 1 end ) as morethan10SAIFI,\n"
				+ "sum(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 >10 then EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 end ) as morethanDuration10,\n"
				+ "count(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 <=10 then 1 end ) as lessthan10MAIFI ,\n"
				+ "sum(case when  EXTRACT ( EPOCH FROM ( event_duration ) ) / 60 <=10 then EXTRACT ( EPOCH FROM  ( event_duration ) ) / 60 end ) as lessthan10 \n"
				+ "		FROM	meter_data.event_details ed \n" + "	WHERE\n"
				+ "	TO_CHAR( event_occ_date, 'YYYY-MM' ) BETWEEN '" + fromDate + "' AND '" + toDate + "' \n"
				+ "	AND meter_sr_number in (select meterno from meter_data.dtdetails where dt_id = '" + dTNo
				+ "'  and parent_substation = '" + substation + "')) W,\n" + "	\n"
				+ "( SELECT tot_consumers AS totalConsumer, dt_name FROM meter_data.rpt_eadt_losses rptFdrL WHERE dt_name = '"
				+ dTNo + "') Y \n" + "		) Z \n" + "WHERE\n" + "Z.dt_name = dt.dt_id and dt.parent_substation='"
				+ substation + "'   ";

		List<Object[]> list = null;
		try {
			list = getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> getSaifiSaidiDataDT(String townfeeder, String town, String monthyr, String dt) {

		String sql = null;

		if (townfeeder.equalsIgnoreCase("DT")) {
		sql = "Select mainx.*,to_char(to_date('" + monthyr
					+ "', 'YYYYMM') - INTERVAL '0 MONTH' , 'dd-mm-yyyy') as start_period,\n" + "to_char(to_date('"
					+ monthyr + "', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'dd-mm-yyyy') as end_period,\n"
					+ "round(((occcount)/ cast(total_dt_consumers as decimal)),2) as SAIFI ,\n"
					+ "round((cast(inmin as decimal)/ cast(total_dt_consumers as decimal)),2) as SAIDI from\n"
					+ "(Select xa.* ,\n" + "monthyear ,ht_industrial_consumer_count,\n"
					+ "ht_commercial_consumer_count,\n" + "lt_industrial_consumer_count,\n"
					+ "lt_commercial_consumer_count,\n" + "lt_domestic_consumer_count,\n" + "govt_consumer_count,\n"
					+ "agri_consumer_count,\n" + "others_consumer_count,\n" + "hut_consumer_count,\n"
					+ "(ht_industrial_consumer_count+\n" + "ht_commercial_consumer_count+\n"
					+ "lt_industrial_consumer_count+\n" + "lt_commercial_consumer_count+\n"
					+ "lt_domestic_consumer_count+\n" + "govt_consumer_count+\n" + "agri_consumer_count+\n"
					+ "others_consumer_count+\n" + "hut_consumer_count) as total_dt_consumers \n" + "from \n"
					+ "(SELECT meter_sr_number,dtname,dttpid,count(meter_sr_number) as occcount,\n"
					+ "Sum(EXTRACT(EPOCH FROM (event_restore_date-event_occ_date )) )as insec,\n"
					+ "Sum (EXTRACT(EPOCH FROM (event_restore_date-event_occ_date ))/60 )as inmin\n"
					+ "FROM meter_data.event_details_mv ed\n" + "LEFT JOIN \n"
					+ "meter_data.dtdetails dd on dd.meterno = ed.meter_sr_number where \n"
					+ "TO_CHAR( event_occ_date, 'YYYYMM' ) = '" + monthyr + "' and event_code = '101' and \n"
					+ "dttpid = '" + dt + "' GROUP BY meter_sr_number,dtname,dttpid) xa\n"
					+ " JOIN meter_data.npp_dt_rpt_monthly_calculation npp on xa.dttpid = npp.tpdtid and monthyear = '"
					+ monthyr + "'\n" + ")mainx";
			
			

		}
		
		else  {
			sql = "Select mainx.*,to_char(to_date('" + monthyr
					+ "', 'YYYYMM') - INTERVAL '0 MONTH' , 'dd-mm-yyyy') as start_period,\n" + "to_char(to_date('"
					+ monthyr + "', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'dd-mm-yyyy') as end_period,\n"
					+ " round(((occcount)/ cast(total_dt_consumers as decimal)),2) as SAIFI ,\n"
					+ "round((cast(inmin as decimal)/ cast(total_dt_consumers as decimal)),2) as SAIDI from\n"
					+ "(Select xa.* ,\n" + "monthyear ,ht_industrial_consumer_count,\n"
					+ "ht_commercial_consumer_count,\n" + "lt_industrial_consumer_count,\n"
					+ "lt_commercial_consumer_count,\n" + "lt_domestic_consumer_count,\n" + "govt_consumer_count,\n"
					+ "agri_consumer_count,\n" + "others_consumer_count,\n" + "hut_consumer_count,\n"
					+ "(ht_industrial_consumer_count+\n" + "ht_commercial_consumer_count+\n"
					+ "lt_industrial_consumer_count+\n" + "lt_commercial_consumer_count+\n"
					+ "lt_domestic_consumer_count+\n" + "govt_consumer_count+\n" + "agri_consumer_count+\n"
					+ "others_consumer_count+\n" + "hut_consumer_count) as total_dt_consumers \n" + "from \n"
					+ "(SELECT meter_sr_number,dtname,dttpid,count(meter_sr_number)as occcount,\n"
					+ "Sum(EXTRACT(EPOCH FROM (event_restore_date-event_occ_date )) )as insec,\n"
					+ "Sum (EXTRACT(EPOCH FROM (event_restore_date-event_occ_date ))/60 )as inmin\n"
					+ "FROM meter_data.event_details_mv ed\n" + "LEFT JOIN \n"
					+ "meter_data.dtdetails dd on dd.meterno = ed.meter_sr_number where \n"
					+ "TO_CHAR( event_occ_date, 'YYYYMM' ) = '" + monthyr + "' and event_code = '101'  and \n"
					+ "tp_town_code LIKE '" + town + "' GROUP BY meter_sr_number,dtname,dttpid) xa\n"
					+ " JOIN meter_data.npp_dt_rpt_monthly_calculation npp on xa.dttpid = npp.tpdtid and monthyear = '"
					+ monthyr + "'\n" + ")mainx";

		}
		
		System.out.println(sql);

		List<?> list = null;
		try {
			
			list = getCustomEntityManager("postgresMdas").createNativeQuery(sql).getResultList();
			System.out.println(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

//pdf	

	@Override
	public void getSaidiSaifiReportPDF(String townfeeder, String town, String zne, String crcl, String monthyr,
			String dt,String townname1,String feedername,HttpServletRequest request, HttpServletResponse response) {

		String zone = "", circle = "", town1 = "",townname="",feedername1="";
		try {
			if (zne == "%") {
				zone = "ALL";
			} else {
				zone = zne;
			}
			if (crcl == "%") {
				circle = "ALL";
			} else {
				circle = crcl;
			}
			if (town == "%") {
				town1 = "ALL";
			} else {
				town1 = town;
			}
			if(townname1=="%")
			{
				townname="ALL";
			}else {
				townname=townname1;
			}


			Rectangle pageSize = new Rectangle(2050, 720);
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
			p1.add(new Phrase("ReliabilityIndicesDTSummary", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			cell2.setBorder(Rectangle.BOTTOM);
			pdf1.addCell(cell2);
			document.add(pdf1);

			// PdfPTable header = new PdfPTable(6);
			// header.setWidths(new int[]{1,1});
			// header.setWidthPercentage(100);
			PdfPTable header = new PdfPTable(6);
			header.setWidthPercentage(100);
			PdfPCell headerCell = null;

			headerCell = new PdfPCell(new Phrase("Region :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(zone, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Circle :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(circle, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Town:", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(townname, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Month Year :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);
			header.addCell(getCell(monthyr, PdfPCell.ALIGN_LEFT));

			headerCell = new PdfPCell(new Phrase("Report Type :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
			headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			headerCell.setFixedHeight(20f);
			headerCell.setBorder(PdfPCell.NO_BORDER);
			header.addCell(headerCell);

			header.addCell(getCell(townfeeder + "wise", PdfPCell.ALIGN_LEFT));

			if ("DT".equals(townfeeder)) {

				headerCell = new PdfPCell(new Phrase("DT  :", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));
				headerCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				headerCell.setFixedHeight(20f);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				header.addCell(headerCell);
				header.addCell(getCell(feedername, PdfPCell.ALIGN_LEFT));
			}

			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
			header.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

			document.add(header);

			String query = "";
			List<Object[]> CirwiseMtrData = null;
			if (townfeeder.equalsIgnoreCase("DT")) {
				query = "Select mainx.*,to_char(to_date('" + monthyr
						+ "', 'YYYYMM') - INTERVAL '0 MONTH' , 'dd-mm-yyyy') as start_period,\n" + "to_char(to_date('"
						+ monthyr + "', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'dd-mm-yyyy') as end_period,\n"
						+ "round(((mainx.count)/ cast(total_dt_consumers as decimal)),2) as SAIFI ,\n"
						+ "round((cast(inmin as decimal)/ cast(total_dt_consumers as decimal)),2) as SAIDI from\n"
						+ "(Select xa.* ,\n" + "monthyear ,ht_industrial_consumer_count,\n"
						+ "ht_commercial_consumer_count,\n" + "lt_industrial_consumer_count,\n"
						+ "lt_commercial_consumer_count,\n" + "lt_domestic_consumer_count,\n" + "govt_consumer_count,\n"
						+ "agri_consumer_count,\n" + "others_consumer_count,\n" + "hut_consumer_count,\n"
						+ "(ht_industrial_consumer_count+\n" + "ht_commercial_consumer_count+\n"
						+ "lt_industrial_consumer_count+\n" + "lt_commercial_consumer_count+\n"
						+ "lt_domestic_consumer_count+\n" + "govt_consumer_count+\n" + "agri_consumer_count+\n"
						+ "others_consumer_count+\n" + "hut_consumer_count) as total_dt_consumers \n" + "from \n"
						+ "(SELECT meter_sr_number,dtname,dttpid,count(meter_sr_number),\n"
						+ "Sum(EXTRACT(EPOCH FROM (event_restore_date-event_occ_date )) )as insec,\n"
						+ "Sum (EXTRACT(EPOCH FROM (event_restore_date-event_occ_date ))/60 )as inmin\n"
						+ "FROM meter_data.event_details ed\n" + "LEFT JOIN \n"
						+ "meter_data.dtdetails dd on dd.meterno = ed.meter_sr_number where \n"
						+ "TO_CHAR( event_occ_date, 'YYYYMM' ) = '" + monthyr + "' and event_code = '101' and \n"
						+ "dttpid = '" + dt + "' GROUP BY meter_sr_number,dtname,dttpid) xa\n"
						+ " JOIN meter_data.npp_dt_rpt_monthly_calculation npp on xa.dttpid = npp.tpdtid and monthyear = '"
						+ monthyr + "'\n" + ")mainx";
			} else {
				query = "Select mainx.*,to_char(to_date('" + monthyr
						+ "', 'YYYYMM') - INTERVAL '0 MONTH' , 'dd-mm-yyyy') as start_period,\n" + "to_char(to_date('"
						+ monthyr + "', 'YYYYMM') + INTERVAL '1 MONTH - 1 DAY', 'dd-mm-yyyy') as end_period,\n"
						+ " round(((mainx.count)/ cast(total_dt_consumers as decimal)),2) as SAIFI  ,\n"
						+ "round((cast(inmin as decimal)/ cast(total_dt_consumers as decimal)),2) as SAIDI from\n"
						+ "(Select xa.* ,\n" + "monthyear ,ht_industrial_consumer_count,\n"
						+ "ht_commercial_consumer_count,\n" + "lt_industrial_consumer_count,\n"
						+ "lt_commercial_consumer_count,\n" + "lt_domestic_consumer_count,\n" + "govt_consumer_count,\n"
						+ "agri_consumer_count,\n" + "others_consumer_count,\n" + "hut_consumer_count,\n"
						+ "(ht_industrial_consumer_count+\n" + "ht_commercial_consumer_count+\n"
						+ "lt_industrial_consumer_count+\n" + "lt_commercial_consumer_count+\n"
						+ "lt_domestic_consumer_count+\n" + "govt_consumer_count+\n" + "agri_consumer_count+\n"
						+ "others_consumer_count+\n" + "hut_consumer_count) as total_dt_consumers \n" + "from \n"
						+ "(SELECT meter_sr_number,dtname,dttpid,count(meter_sr_number),\n"
						+ "Sum(EXTRACT(EPOCH FROM (event_restore_date-event_occ_date )) )as insec,\n"
						+ "Sum (EXTRACT(EPOCH FROM (event_restore_date-event_occ_date ))/60 )as inmin\n"
						+ "FROM meter_data.event_details ed\n" + "LEFT JOIN \n"
						+ "meter_data.dtdetails dd on dd.meterno = ed.meter_sr_number where \n"
						+ "TO_CHAR( event_occ_date, 'YYYYMM' ) = '" + monthyr + "' and event_code = '101'  and \n"
						+ "tp_town_code = '" + town + "' GROUP BY meter_sr_number,dtname,dttpid) xa\n"
						+ " JOIN meter_data.npp_dt_rpt_monthly_calculation npp on xa.dttpid = npp.tpdtid and monthyear = '"
						+ monthyr + "'\n" + ")mainx";
			}

			// System.out.println(query);
			CirwiseMtrData = postgresMdas.createNativeQuery(query).getResultList();

			PdfPTable parameterTable = new PdfPTable(19);
			parameterTable.setWidths(new int[] {  2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 4,  2, 2   });
			parameterTable.setWidthPercentage(100);
			PdfPCell parameterCell;

			parameterCell = new PdfPCell(new Phrase("S.No", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Meter Number", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("DT Number", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("DT Code", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("StartPeriod", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("EndPeriod", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Total Interuptions Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Total Interuptions Duration", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("HT Industrial Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("HT Commercial Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("LT Industrial Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("LT Commercial Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("LT Domestic Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("GOVT Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("AGRI Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("OTHERS Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(
					new Phrase("Total Consumer Count", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("SAIFI", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			parameterCell = new PdfPCell(new Phrase("SAIDI", new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD)));
			parameterCell.setFixedHeight(30f);
			parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			parameterCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			parameterCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			parameterTable.addCell(parameterCell);

			for (int i = 0; i < CirwiseMtrData.size(); i++) {
				parameterCell = new PdfPCell(new Phrase((i + 1) + "", new Font(Font.FontFamily.HELVETICA, 11)));
				parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				parameterTable.addCell(parameterCell);

				Object[] obj = CirwiseMtrData.get(i);
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

						parameterCell = new PdfPCell(new Phrase(obj[3] == null ? null : obj[3] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[4] == null ? null : obj[4] + "",
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

						parameterCell = new PdfPCell(new Phrase(obj[14] == null ? null : obj[14] + "",
								new Font(Font.FontFamily.HELVETICA, 11)));
						parameterCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
						parameterCell.setFixedHeight(30f);
						parameterTable.addCell(parameterCell);

						parameterCell = new PdfPCell(new Phrase(obj[16] == null ? null : obj[16] + "",
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
			response.setHeader("Content-disposition", "attachment; filename=ReliabilityIndicesDTSummary.pdf");
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
