package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="installation_review_history", schema="meter_data")
public class InstallationReview {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="consumer_no")
	private String consumerNo;
	@Column(name="old_mtr_no")
	private String oldMtrNo;
	@Column(name="new_mtr_no")
	private String newMtrNo;
	@Column(name="reviewed_by")
	private String reviewedBy;
	@Column(name="review_time")
	private Timestamp reviewTime;
	@Column(name="photos_quality")
	private String photoQua;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getConsumerNo() {
		return consumerNo;
	}
	public void setConsumerNo(String consumerNo) {
		this.consumerNo = consumerNo;
	}
	public String getOldMtrNo() {
		return oldMtrNo;
	}
	public void setOldMtrNo(String oldMtrNo) {
		this.oldMtrNo = oldMtrNo;
	}
	public String getNewMtrNo() {
		return newMtrNo;
	}
	public void setNewMtrNo(String newMtrNo) {
		this.newMtrNo = newMtrNo;
	}
	public String getReviewedBy() {
		return reviewedBy;
	}
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}
	public Timestamp getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}
	public String getPhotoQua() {
		return photoQua;
	}
	public void setPhotoQua(String photoQua) {
		this.photoQua = photoQua;
	}


}
