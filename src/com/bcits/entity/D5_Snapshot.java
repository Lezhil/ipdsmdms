package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="D5_SNAPSHOTS")
public class D5_Snapshot {

	
	@Id
	@SequenceGenerator(name="snapShotsId",sequenceName="D5_Snapshot_Id")
	@GeneratedValue(generator="snapShotsId")
	@Column(name="D5_SNAPSHOT_ID")
	private long id;
	
	@Column(name="D5_ID")
	private int d5Id;
	
	@Column(name = "R_PHASE_VAL")
	private float rPhaseVal;
	
	@Column(name = "B_PHASE_VAL")
	private float bPhaseVal;

	@Column(name = "Y_PHASE_VAL")
	private float yPhaseVal;
	
	@Column(name = "R_PHASE_LINE_VAL")
	private float rPhaseLineVal;
	
	@Column(name = "Y_PHASE_LINE_VAL")
	private float yPhaseLineVal;
	
	@Column(name = "B_PHASE_LINE_VAL")
	private float bPhaseLineVal;
	
	@Column(name = "R_PHASE_ACTIVE_VAL")
	private float rPhaseActiveVal;
	
	@Column(name = "Y_PHASE_ACTIVE_VAL")
	private float yPhaseActiveVal;
	
	@Column(name = "B_PHASE_ACTIVE_VAL")
	private float bPhaseActiveVal;
	
	@Column(name = "R_PHASE_PF_VAL")
	private float rPhasePfVal;
	
	@Column(name = "Y_PHASE_PF_VAL")
	private float yPhasePfVal;
	
	@Column(name = "B_PHASE_PF_VAL")
	private float bPhasePfVal;
	
	@Column(name = "AVG_PF_VAL")
	private float avgPfVal;
	
	@Column(name = "ACTIVE_POWER_VAL")
	private float activePowerVal;
	
	@Column(name = "MF")
	private double mf;
	
	@Column(name = "PHASE_SEQUENCE")
	private String phaseSequence;
	
	

	public D5_Snapshot()
	{
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getD5Id() {
		return d5Id;
	}

	public void setD5Id(int d5Id) {
		this.d5Id = d5Id;
	}

	public float getrPhaseVal() {
		return rPhaseVal;
	}

	public void setrPhaseVal(float rPhaseVal) {
		this.rPhaseVal = rPhaseVal;
	}

	public float getbPhaseVal() {
		return bPhaseVal;
	}

	public void setbPhaseVal(float bPhaseVal) {
		this.bPhaseVal = bPhaseVal;
	}

	public float getyPhaseVal() {
		return yPhaseVal;
	}

	public void setyPhaseVal(float yPhaseVal) {
		this.yPhaseVal = yPhaseVal;
	}

	public float getrPhaseLineVal() {
		return rPhaseLineVal;
	}

	public void setrPhaseLineVal(float rPhaseLineVal) {
		this.rPhaseLineVal = rPhaseLineVal;
	}

	public float getyPhaseLineVal() {
		return yPhaseLineVal;
	}

	public void setyPhaseLineVal(float yPhaseLineVal) {
		this.yPhaseLineVal = yPhaseLineVal;
	}

	public float getbPhaseLineVal() {
		return bPhaseLineVal;
	}

	public void setbPhaseLineVal(float bPhaseLineVal) {
		this.bPhaseLineVal = bPhaseLineVal;
	}

	public float getrPhaseActiveVal() {
		return rPhaseActiveVal;
	}

	public void setrPhaseActiveVal(float rPhaseActiveVal) {
		this.rPhaseActiveVal = rPhaseActiveVal;
	}

	public float getyPhaseActiveVal() {
		return yPhaseActiveVal;
	}

	public void setyPhaseActiveVal(float yPhaseActiveVal) {
		this.yPhaseActiveVal = yPhaseActiveVal;
	}

	public float getbPhaseActiveVal() {
		return bPhaseActiveVal;
	}

	public void setbPhaseActiveVal(float bPhaseActiveVal) {
		this.bPhaseActiveVal = bPhaseActiveVal;
	}

	public float getrPhasePfVal() {
		return rPhasePfVal;
	}

	public void setrPhasePfVal(float rPhasePfVal) {
		this.rPhasePfVal = rPhasePfVal;
	}

	public float getyPhasePfVal() {
		return yPhasePfVal;
	}

	public void setyPhasePfVal(float yPhasePfVal) {
		this.yPhasePfVal = yPhasePfVal;
	}

	public float getbPhasePfVal() {
		return bPhasePfVal;
	}

	public void setbPhasePfVal(float bPhasePfVal) {
		this.bPhasePfVal = bPhasePfVal;
	}

	public float getAvgPfVal() {
		return avgPfVal;
	}

	public void setAvgPfVal(float avgPfVal) {
		this.avgPfVal = avgPfVal;
	}

	public float getActivePowerVal() {
		return activePowerVal;
	}

	public void setActivePowerVal(float activePowerVal) {
		this.activePowerVal = activePowerVal;
	}

	public double getMf() {
		return mf;
	}

	public void setMf(double mf) {
		this.mf = mf;
	}

	public String getPhaseSequence() {
		return phaseSequence;
	}

	public void setPhaseSequence(String phaseSequence) {
		this.phaseSequence = phaseSequence;
	}

	
	
}
