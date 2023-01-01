package com.bcits.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import com.bcits.entity.SqlEditorExecutedQry;
import com.bcits.service.SqlEditorSaveService;

@Repository
public class SqlEditorSaveServiceImpl extends GenericServiceImpl<SqlEditorExecutedQry> implements SqlEditorSaveService {

	@Override
	public String findByReportName(String reportname) {
		
		try {
		return (String) postgresMdas.createNamedQuery("SqlEditorExecutedQry.findByReportName").setParameter("report_name", reportname).getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SqlEditorExecutedQry> findAllSavedQuery(String executed_by) {
		try {
			
			
			return postgresMdas.createNamedQuery("SqlEditorExecutedQry.findAllSavedQuery",SqlEditorExecutedQry.class).setParameter("executed_by", executed_by ).getResultList();
				
			
		}catch(Exception e) {
		return null;
		}
	}

	@Override
	public String findSavedQuerybyRpt_name(String parameter) {
		try {
			return (String) postgresMdas.createNamedQuery("SqlEditorExecutedQry.findSavedQuerybyRpt_name").setParameter("report_name", parameter).getSingleResult();
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	
}
