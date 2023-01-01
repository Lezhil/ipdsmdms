package com.bcits.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "METERMASTER_EXTRAS", uniqueConstraints = { @UniqueConstraint(columnNames = { "ACCNO", "RDNGMONTH" }) })

@NamedQueries({
/*@NamedQuery(name="MeterMasterExtra.findOnlyImage", query="select m.CustomKey.accno from MeterMasterExtra m where m.CustomKey.readingMonth=:readingMonth and m.CustomKey.accno like :accno "),
*/
})
public class MeterMasterExtra {

	@EmbeddedId // FOR MAKING UNIQUE KEY
	private CustomKey myKey; // ACCNO, RDNGMONTH

	@Column(name = "METRNO")
	private String metrno;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "ACCURACY")
	private String accuracy;

	@Column(name = "IMAGE_ONE")
	private byte[] imageOne;

	@Column(name = "IMAGE_TWO")
	private byte[] imageTwo;

	@Column(name = "IMAGE_THREE")
	private byte[] imageThree;

	public CustomKey getMyKey() {
		return myKey;
	}

	public void setMyKey(CustomKey myKey) {
		this.myKey = myKey;
	}

	public String getMetrno() {
		return metrno;
	}

	public void setMetrno(String metrno) {
		this.metrno = metrno;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public byte[] getImageOne() {
		return imageOne;
	}

	public void setImageOne(byte[] imageOne) {
		this.imageOne = imageOne;
	}

	public byte[] getImageTwo() {
		return imageTwo;
	}

	public void setImageTwo(byte[] imageTwo) {
		this.imageTwo = imageTwo;
	}

	public byte[] getImageThree() {
		return imageThree;
	}

	public void setImageThree(byte[] imageThree) {
		this.imageThree = imageThree;
	}
	
	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}



	@Embeddable
	public static class CustomKey implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "ACCNO")
		private String accno;

		@Column(name = "RDNGMONTH")
		private Long readingMonth;

		public String getAccno() {
			return accno;
		}

		public void setAccno(String accno) {
			this.accno = accno;
		}

		public Long getReadingMonth() {
			return readingMonth;
		}

		public void setReadingMonth(Long readingMonth) {
			this.readingMonth = readingMonth;
		}

		public CustomKey(String accno, Long readingMonth) {
			super();
			this.accno = accno;
			this.readingMonth = readingMonth;
		}

		public CustomKey() {

		}

	}

}
