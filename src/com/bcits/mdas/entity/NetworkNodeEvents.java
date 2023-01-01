package com.bcits.mdas.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="network_node_events" ,schema="METER_DATA")
public class NetworkNodeEvents {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="time_stamp")
	private Timestamp timestamp;
	@Column(name="createtime")
	private Timestamp createtime;
	@Column(name="seq_id")
	private int seqId;
	@Column(name="nodeid")
	private String nodeId;
	@Column(name="sequencenumber")
	private int sequenceNumber;
	@Column(name="status")
	private String status;
	@Column(name="server_timestamp")
	private Timestamp serverTimestamp;
	@Column(name="description")
	private String description;
	@Column(name="eventtime")
	private Timestamp eventTime;
	@Column(name="numericcode")
	private double numericCode;
	@Column(name="textcode")
	private String textCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getServerTimestamp() {
		return serverTimestamp;
	}
	public void setServerTimestamp(Timestamp serverTimestamp) {
		this.serverTimestamp = serverTimestamp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getEventTime() {
		return eventTime;
	}
	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}
	
	public double getNumericCode() {
		return numericCode;
	}
	public void setNumericCode(double numericCode) {
		this.numericCode = numericCode;
	}
	public String getTextCode() {
		return textCode;
	}
	public void setTextCode(String textCode) {
		this.textCode = textCode;
	}
	
	
	
	 
	   

}
