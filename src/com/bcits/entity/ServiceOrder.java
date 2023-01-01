package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="service_orders",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="ServiceOrder.findall",query="select s from ServiceOrder s order by s.transaction_time"),
	@NamedQuery(name="ServiceOrder.findalldata",query="select a from ServiceOrder a where a.circle=:circle AND a.division=:division  AND a.subdivision=:subdivision AND to_char(a.transaction_time,'yyyy-MM-dd')  BETWEEN  :fdate  AND :tdate")
	})
public class ServiceOrder {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)    
 private int id;
 private String accno; 
 private String meterno ;
 private String kno ;
 private Timestamp transaction_time ;
 private String circle;
 private String division ;
 private String  subdivision ;
 private String sdocode ;
 private String so_type ;
 private String request_type ;
 private String comments ;
 private String  status ;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getAccno() {
	return accno;
}
public void setAccno(String accno) {
	this.accno = accno;
}
public String getMeterno() {
	return meterno;
}
public void setMeterno(String meterno) {
	this.meterno = meterno;
}
public String getKno() {
	return kno;
}
public void setKno(String kno) {
	this.kno = kno;
}
public Timestamp getTransaction_time() {
	return transaction_time;
}
public void setTransaction_time(Timestamp transaction_time) {
	this.transaction_time = transaction_time;
}
public String getCircle() {
	return circle;
}
public void setCircle(String circle) {
	this.circle = circle;
}
public String getDivision() {
	return division;
}
public void setDivision(String division) {
	this.division = division;
}
public String getSubdivision() {
	return subdivision;
}
public void setSubdivision(String subdivision) {
	this.subdivision = subdivision;
}
public String getSdocode() {
	return sdocode;
}
public void setSdocode(String sdocode) {
	this.sdocode = sdocode;
}
public String getSo_type() {
	return so_type;
}
public void setSo_type(String so_type) {
	this.so_type = so_type;
}
public String getRequest_type() {
	return request_type;
}
public void setRequest_type(String request_type) {
	this.request_type = request_type;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

}
