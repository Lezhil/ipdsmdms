package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/*select pg_get_constraintdef(oid,true)
from pg_constraint 
where conrelid =
    (select oid 
    from pg_class 
    where relname='users' and conname='userlevel_check');*/
@Entity
@Table(name="PG_CONSTRAINT")

public class Pg_Constraint_Entity 
{
  
	@Column(name="pg_get_constraintdef")
	private String pg_get_constraintdef;
	
	@Column(name="consrc")
	private String consrc;
	
	@Column(name="conrelid")
	private String conrelid;
	
	@Column(name="conname")
	private String conname;
	
	@Id
	@Column(name="oid")
	private int oid;


	public String getPg_get_constraintdef() {
		return pg_get_constraintdef;
	}

	public void setPg_get_constraintdef(String pg_get_constraintdef) {
		this.pg_get_constraintdef = pg_get_constraintdef;
	}

	public String getConrelid() {
		return conrelid;
	}

	public void setConrelid(String conrelid) {
		this.conrelid = conrelid;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getConsrc() {
		return consrc;
	}

	public void setConsrc(String consrc) {
		this.consrc = consrc;
	}

	public String getConname() {
		return conname;
	}

	public void setConname(String conname) {
		this.conname = conname;
	}
}
