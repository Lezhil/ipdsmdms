package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="READINGREMARK",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="ReadingRemark.SelectReadingRemark", query="SELECT r FROM ReadingRemark r ORDER BY r.readingremark"),

})
/*Author: Ved Prakash Mishra*/

public class ReadingRemark {

	@Id
	@SequenceGenerator(name = "readngid", sequenceName = "REDNGID")
	@GeneratedValue(generator = "readngid")	
	@Column(name="READINGREMARKID")
	private Integer readingremarkid;
	@Column(name="READINGREMARK")
	private String readingremark;
	public Integer getReadingremarkid() {
		return readingremarkid;
	}
	public void setReadingremarkid(Integer readingremarkid) {
		this.readingremarkid = readingremarkid;
	}
	public String getReadingremark() {
		return readingremark;
	}
	public void setReadingremark(String readingremark) {
		this.readingremark = readingremark;
	}
}
