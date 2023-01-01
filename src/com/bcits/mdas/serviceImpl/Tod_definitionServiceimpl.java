package com.bcits.mdas.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.entity.TodDefinitionEntity;
import com.bcits.service.Tod_definitionService;
import com.bcits.serviceImpl.GenericServiceImpl;

@Repository
public class Tod_definitionServiceimpl extends GenericServiceImpl<TodDefinitionEntity> implements Tod_definitionService{

	@Override
	public List getPreviuosEndTime() {
		String qry="";
		String qry1="";
		List result=new ArrayList<>();
		List<?> todResult=new ArrayList<>();
		List<?> todid=new ArrayList<>();
		try {
			qry="Select * from meter_data.tod_definition order by ID DESC";
			qry1="SELECT B. rule||(case WHEN length (B.id)=1 then ''||B.id else B.id  END) as todid from \n" +
					"( \n" +
					"SELECT substr(A.todno, 0,4) as rule,CAST(CAST(substr(A.todno, 4,length(A.todno))as INTEGER)+1 as TEXT) as id FROM \n" +
					"( \n" +
					"SELECT COALESCE(max(tod_no),'TOD0') as todno FROM meter_data.tod_definition \n" +
					")A \n" +
					")B ";
			todResult=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
			todid=getCustomEntityManager("postgresMdas").createNativeQuery(qry1).getResultList();
			result.add(todResult);
			result.add(todid);
		} catch (Exception e) {
		e.printStackTrace();
		}
		return result;
	}

	@Override
	public List checkTodNameExist(String tod_name) {
		String qry="";
		List<TodDefinitionEntity> todResult=new ArrayList<>();
		try {
			qry="Select * from meter_data.tod_definition where"
					+ " tod_no='"+tod_name+"'";
			todResult=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return todResult;
	}

	/*@Override
	public List getAllTOD() {
		String qry="";
		List<TodDefinitionEntity> todResult=new ArrayList<>();
		try {
			qry="Select * from meter_data.tod_definition ";
			System.out.println(qry);
			todResult=getCustomEntityManager("postgresMdas").createNativeQuery(qry).getResultList();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return todResult;
	}*/
	@Override
	public List<TodDefinitionEntity> getAllTODSlots() {
		List<TodDefinitionEntity> todResult=new ArrayList<>();
		try {
			
			todResult=postgresMdas.createNamedQuery("TodDefinitionEntity.getAllTODSlots").getResultList();
			return todResult;
		} catch (Exception e) {
		e.printStackTrace();
		}
		return todResult;
	}

	@Override
	public int deleteTod(long id) {
		String qry="";
		int count=0;
		try {
			qry="DELETE FROM meter_data.tod_definition WHERE id='"+id+"'";
			count=postgresMdas.createNativeQuery(qry).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public String getTODName() {
		String qry1="";
		String todname="";
		try {
			qry1="SELECT B. rule||(case WHEN length (B.id)=1 then ''||B.id else B.id  END) as todid from \n" +
					"( \n" +
					"SELECT substr(A.todno, 0,4) as rule,CAST(CAST(substr(A.todno, 4,length(A.todno))as INTEGER)+1 as TEXT) as id FROM \n" +
					"( \n" +
					"SELECT COALESCE(max(tod_no),'TOD0') as todno FROM meter_data.tod_definition \n" +
					")A \n" +
					")B ";
			todname=(String) getCustomEntityManager("postgresMdas").createNativeQuery(qry1).getSingleResult();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return todname;
	}

}
