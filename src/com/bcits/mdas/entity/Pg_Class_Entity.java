package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PG_CLASS")
/*select pg_get_constraintdef(oid,true)
from pg_constraint 
where conrelid =
    (select oid 
    from pg_class 
    where relname='users' and conname='userlevel_check');*/
public class Pg_Class_Entity 
{
    @Id
	@Column(name="relname")
	private String relname;
	
	@Column(name="conname")
	private String conname;
	
	@Column(name="oid")
	private int oid;


	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getRelname() {
		return relname;
	}

	public void setRelname(String relname) {
		this.relname = relname;
	}

	public String getConname() {
		return conname;
	}

	public void setConname(String conname) {
		this.conname = conname;
	}

	
	
}
