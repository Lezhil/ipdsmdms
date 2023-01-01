package com.bcits.service;



import java.util.List;

import com.bcits.entity.SqlEditorExecutedQry;

public interface SqlEditorSaveService extends GenericService<SqlEditorExecutedQry> {
	
	
	String findByReportName(String reportname);
	
	List<SqlEditorExecutedQry> findAllSavedQuery(String executed_by);

	String findSavedQuerybyRpt_name(String parameter);
		
}
