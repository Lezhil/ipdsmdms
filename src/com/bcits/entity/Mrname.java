package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="MRNAME",schema="mdm_test")
@NamedQueries({
@NamedQuery(name="Mrname.FindAll",query="SELECT m FROM Mrname m ORDER BY m.mrname asc"),
@NamedQuery(name="Mrname.FindDuplicate",query="SELECT m.mrname FROM Mrname m WHERE m.mrname=:mrname"),
@NamedQuery(name="Mrname.UpdaeMrName",query="UPDATE Mrname m SET m.mrname=:mrname WHERE m.mrname=:oldmrname"),
@NamedQuery(name="Mrname.FindMrName",query="SELECT m.mrname FROM Mrname m WHERE m.id=:id"),
@NamedQuery(name="Mrname.FindAllMrnames",query="SELECT m from Mrname m WHERE m.mrname is NOT NULL  ORDER BY m.mrname  ASC")
})
public class Mrname {


	@Id
	/*@SequenceGenerator(name = "mrSeq", sequenceName = "MRNAMEID")
	@GeneratedValue(generator = "mrSeq")*/	
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	@Column(name="SDO")
	private String sdo;
	
	@Column(name="MRNAME")
	private String mrname;
	
	@Column(name="NOI")
	private int noi;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public void setSdo(String sdo) {
		this.sdo = sdo;
	}
	
	public String getMrname() {
		return mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	public int getNoi() {
		return noi;
	}

	public void setNoi(int noi) {
		this.noi = noi;
	}
}
