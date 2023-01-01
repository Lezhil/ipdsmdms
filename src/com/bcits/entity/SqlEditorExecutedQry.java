package com.bcits.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="sql_editor_query",schema="meter_data")
@NamedQueries({
	@NamedQuery(name="SqlEditorExecutedQry.getSqlEditorExecutedQueryDetails",query="select c from SqlEditorExecutedQry c order by c.id DESC"),
	
	@NamedQuery(name="SqlEditorExecutedQry.findByReportName",query="select c.report_name from SqlEditorExecutedQry c where c.report_name=:report_name"),
	
	@NamedQuery(name="SqlEditorExecutedQry.findAllSavedQuery",query="select c from SqlEditorExecutedQry c where c.executed_by=:executed_by order by c.id desc"),
	
	@NamedQuery(name="SqlEditorExecutedQry.findSavedQuerybyRpt_name",query="select c.sql_qry from SqlEditorExecutedQry c where c.report_name=:report_name"),
	
	@NamedQuery(name="SqlEditorExecutedQry.findSavedQuerybyEntry_name",query="select c.executed_by from SqlEditorExecutedQry c"),

})
public class SqlEditorExecutedQry
{

	

	
	/*
	 * @GeneratedValue(strategy =
	 * GenerationType.SEQUENCE,generator="query_builder_seq")
	 * 
	 * @SequenceGenerator(name="query_builder_seq",
	 * sequenceName="query_builder_seq", allocationSize=100)
	 */
	
	@Id 
	/*
	 * @SequenceGenerator(name="pk_sequence",sequenceName="query_builder_seq",
	 * allocationSize=1)
	 * 
	 * @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	 */
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="sql_qry")
	private String sql_qry;
	
	@Column(name="executed_by")
	private String executed_by;
	
	@Column(name="executed_date")
	private Timestamp executed_date;
	
	@Column(name="schema_name")
	private String schema_name;
	
	@Column(name="report_name")
	private String report_name;
	
	@Column(name="report_desc")
	private String report_description;

	public String getSchema_name() {
		return schema_name;
	}

	public void setSchema_name(String schema_name) {
		this.schema_name = schema_name;
	}

	public String getReport_name() {
		return report_name;
	}

	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}

	public String getReport_description() {
		return report_description;
	}

	public void setReport_description(String report_description) {
		this.report_description = report_description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSql_qry() {
		return sql_qry;
	}

	public void setSql_qry(String sql_qry) {
		this.sql_qry = sql_qry;
	}

	public String getExecuted_by() {
		return executed_by;
	}

	public void setExecuted_by(String executed_by) {
		this.executed_by = executed_by;
	}

	public Timestamp getExecuted_date() {
		return executed_date;
	}

	public void setExecuted_date(Timestamp executed_date) {
		this.executed_date = executed_date;
	}
	


	
	

}
