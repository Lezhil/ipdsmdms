package com.bcits.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.ProcessSchedular;
import com.bcits.service.ProcessSchedularService;

@Repository
public class ProcessSchedularServiceImpl extends GenericServiceImpl<ProcessSchedular> implements ProcessSchedularService {


	@Override
	public List<?> getValidationData() {
		List list=null;
		try {
			String sql="select v_rule_id,v_rule_name from meter_data.validation_rule_mst WHERE is_active='t'";
			//System.err.println("types are---"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List<?> getEstimationData() {
		List list=null;
		try {
			String sql="select e_rule_id,e_rule_name from meter_data.estimation_rule_mst WHERE is_active='t'";
			//System.err.println("types are---"+sql);
			list=postgresMdas.createNativeQuery(sql).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}

	@Override
	public List getSavedDataList() {
		
		  List list=null;
		  try { 
			  String sql="SELECT * from meter_data.process_schedular";
			  //System.err.println("saved data are---"+sql);
		  list=postgresMdas.createNativeQuery(sql).getResultList(); 
		  }catch (Exception e) { 
			  e.printStackTrace();
			  }
		  return list;
		 
		
	}

	@Override
	public List getEditedDataList(String id) {
		 List list=null;
		  try { 
			  String sql="SELECT process_type,process_name,process_id,occurance_type,occ_time,repeated_occ_time from meter_data.process_schedular WHERE id='"+id+"'";
			  //System.err.println("saved data are---"+sql);
		  list=postgresMdas.createNativeQuery(sql).getResultList(); 
		  }catch (Exception e) { 
			  e.printStackTrace();
			  }
		  return list;
	}

	@Override
	public ProcessSchedular getEntityById(int parseInt) {
		try {
			return postgresMdas.createNamedQuery("ProcessSchedular.findById", ProcessSchedular.class).setParameter("id", parseInt).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List getDataList(String id) {
		 List list=null;
		  try { 
			  String sql="SELECT * FROM meter_data.process_schedular WHERE id='"+id+"'";
			  //System.err.println("data are---"+sql);
		  list=postgresMdas.createNativeQuery(sql).getResultList(); 
		  }catch (Exception e) { 
			  e.printStackTrace();
			  }
		  return list;
	}


}
