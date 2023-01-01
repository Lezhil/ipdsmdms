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
@Table(name="CMRINO")
@NamedQueries({
	@NamedQuery(name="CmriNumber.FindAllCrmi", query="SELECT c FROM CmriNumber c ORDER BY c.cmri_no"),
	@NamedQuery(name="CmriNumber.FindDuplicate", query="SELECT c.cmri_no FROM CmriNumber c WHERE c.cmri_no=:cmri_no"),
	@NamedQuery(name="CmriNumber.UpdateCmriNo", query="UPDATE CmriNumber cm SET cm.cmri_no=:cmri_no WHERE cm.cmri_no=:old_cmri_no"),
	@NamedQuery(name = "CmriNumber.FindCm", query = "SELECT m FROM CmriNumber m"),
	@NamedQuery(name = "CmriNumber.CheckAvil", query = "SELECT m FROM CmriNumber m"),
	
})

/*Author: Ved Prakash Mishra*/
public class CmriNumber {
	@Id
	@SequenceGenerator(name = "cmriId", sequenceName = "CMRIID")
	@GeneratedValue(generator = "cmriId")	
	@Column(name="CMRI_ID")
	private Integer cmri_id;
	@Column(name="CMRI_NO")
	private String cmri_no;
	
	public CmriNumber()
	{
		
	}
	public String getCmri_no() {
		return cmri_no;
	}

	public void setCmri_no(String cmri_no) {
		this.cmri_no = cmri_no;
	}
	public Integer getCmri_id() {
		return cmri_id;
	}
	public void setCmri_id(Integer cmri_id) {
		this.cmri_id = cmri_id;
	}

	

	
}
