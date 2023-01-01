package com.bcits.mdas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pfc_d5_rpt" , schema="meter_data")
public class Pfcd5ReportEntity {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	@Column(name ="rec_id")
	private int id;
	
	@Column(name = "month_year")
	private String monthYear;
	
	@Column(name = "town")
	private String town;
}
