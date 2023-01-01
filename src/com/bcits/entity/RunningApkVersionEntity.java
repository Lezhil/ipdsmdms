package com.bcits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="RUNNING_APK_VERSION")
@NamedQueries({
	@NamedQuery(name="RunningApkVersionEntity.GetApkVersion",
			
			query="select MAX(m.version_name) FROM RunningApkVersionEntity m")
})
 

public class RunningApkVersionEntity {
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="VERSION_NAME")
	private String version_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	
	

}
