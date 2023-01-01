package com.bcits.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BATCHSTATUS")
@NamedQueries({
	@NamedQuery(name="BatchStatusEntity.getAllDetails",query="SELECT b FROM BatchStatusEntity b WHERE TO_CHAR(b.rdatestump,'dd-MM-yyyy')=:readingdate"),

	@NamedQuery(name="BatchStatusEntity.getCircle",query="select distinct (c.circle) from BatchStatusEntity c where c.circle is not null"),
	
	@NamedQuery(name="BatchStatusEntity.getSdocodeOnCircle",query="select distinct(c.sdocode) from BatchStatusEntity c where c.sdocode is not null and c.circle=:circle"),
	
	@NamedQuery(name="BatchStatusEntity.getReadingDateOnSdocodeAndcircle",query="select c.rdatestump from BatchStatusEntity c where c.sdocode=:sdocode and c.circle=:circle"),
	
	@NamedQuery(name="BatchStatusEntity.getReadingDateOnSdocodeAndcircleSdoAll",query="select c.rdatestump from BatchStatusEntity c where c.sdocode like '%' and c.circle=:circle"),
	

})
	
public class BatchStatusEntity {
	
		@Id
		@SequenceGenerator(name = "Id", sequenceName = "BATCH_STATUS_SUMMARY_SEQ")
		@GeneratedValue(generator = "Id")	

		@Column(name="ID")
		private int Id;
		
		@Column(name="RDATESTUMP")
		private Date rdatestump;
		
		@Column(name="RDNGMONTH")
		private String rdngmonth;
		
		@Column(name="METERNO")
		private String meterno;
		
		@Column(name="MM_TABLE")
		private int mm_table;
		
		@Column(name="XMLIMPORT" )
		private int xmlimport;

		@Column(name="FILE_NAME")
		private String file_name;

		@Column(name="PARSESTATUS")
		private String parsestatus;
		
		@Column(name="SDOCODE")
		private String sdocode;
		
		@Column(name="CIRCLE")
		private String circle;
		
		@Column(name="SDONAME")
		private String sdoname;
		
		@Column(name="AMR")
		private int amr;
		
		@Column(name="FILE_PATH")
		private String  file_path;

		public String getFile_path() {
			return file_path;
		}

		public void setFile_path(String file_path) {
			this.file_path = file_path;
		}

		public int getId() {
			return Id;
		}

		public void setId(int id) {
			Id = id;
		}

		public Date getRdatestump() {
			return rdatestump;
		}

		public void setRdatestump(Date rdatestump) {
			this.rdatestump = rdatestump;
		}

		public String getRdngmonth() {
			return rdngmonth;
		}

		public void setRdngmonth(String rdngmonth) {
			this.rdngmonth = rdngmonth;
		}

		public String getMeterno() {
			return meterno;
		}

		public void setMeterno(String meterno) {
			this.meterno = meterno;
		}

		public int getMm_table() {
			return mm_table;
		}

		public void setMm_table(int mm_table) {
			this.mm_table = mm_table;
		}

		public int getXmlimport() {
			return xmlimport;
		}

		public void setXmlimport(int xmlimport) {
			this.xmlimport = xmlimport;
		}

		public String getFile_name() {
			return file_name;
		}

		public void setFile_name(String file_name) {
			this.file_name = file_name;
		}
	
		public String getSdocode() {
			return sdocode;
		}

		public void setSdocode(String sdocode) {
			this.sdocode = sdocode;
		}

		public String getCircle() {
			return circle;
		}

		public void setCircle(String circle) {
			this.circle = circle;
		}

		public String getSdoname() {
			return sdoname;
		}

		public void setSdoname(String sdoname) {
			this.sdoname = sdoname;
		}

		public int getAmr() {
			return amr;
		}

		public void setAmr(int amr) {
			this.amr = amr;
		}
		
		
		public String getParsestatus() {
			return parsestatus;
		}

		public void setParsestatus(String parsestatus) {
			this.parsestatus = parsestatus;
		}
		

		

		
	
}
