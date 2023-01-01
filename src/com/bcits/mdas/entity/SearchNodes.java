package com.bcits.mdas.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.net.ntp.TimeStamp;
@Entity
@Table(name="search_nodes",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="SearchNodes.dculist",query="select distinct s.nodeId from SearchNodes s where nodeType='GATEWAY'")
})
public class SearchNodes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="time_stamp")
	private Timestamp timestamp;
	@Column(name="address")
	private String address;
	@Column(name="create_time")
	private Timestamp createTime;
	@Column(name="current_firmware_version")
	private String currentFirmwareVersion;
	@Column(name="gateway_node_id")
	private String gatewayNodeId;
	@Column(name="hardwareversion")
	private String hardwareVersion;
	@Column(name="seq_id")
	private int seqId;
	@Column(name="latitude")
	private double latitude;
	@Column(name="longitude")
	private double longitude;
	@Column(name="node_id")
	private String nodeId;
	@Column(name="node_type")
	private String nodeType;
	@Column(name="parent_node_id")
	private String parentNodeId;
	@Column(name="port")
	private int port;
	@Column(name="server_name")
	private String serverName;
	@Column(name="status")
	private String status;
	@Column(name="update_time")
	private Timestamp updateTime;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCurrentFirmwareVersion() {
		return currentFirmwareVersion;
	}
	public void setCurrentFirmwareVersion(String currentFirmwareVersion) {
		this.currentFirmwareVersion = currentFirmwareVersion;
	}
	public String getGatewayNodeId() {
		return gatewayNodeId;
	}
	public void setGatewayNodeId(String gatewayNodeId) {
		this.gatewayNodeId = gatewayNodeId;
	}
	public String getHardwareVersion() {
		return hardwareVersion;
	}
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	   

}
