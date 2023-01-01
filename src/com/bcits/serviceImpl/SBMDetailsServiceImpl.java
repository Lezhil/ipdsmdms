package com.bcits.serviceImpl;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.bcits.entity.SBMDetailsEntity;
import com.bcits.service.SBMDetailsService;
/*Author: Ved Prakash Mishra*/
@Repository
public class SBMDetailsServiceImpl extends GenericServiceImpl<SBMDetailsEntity> implements SBMDetailsService
{

	@Override
	public List<SBMDetailsEntity> getAllDetails()
	{
		List<SBMDetailsEntity> list=null;
		list=postgresMdas.createNamedQuery("SBMDetailsEntity.GetAllData").getResultList();
		return list;
	}

	@Override
	public Boolean findSbmNum(ModelMap model, String sbmnumber) {
		boolean flag=false;

		if(sbmnumber!= null)
		{
		   List l= postgresMdas.createNamedQuery("SBMDetailsEntity.Getsbmnumber").setParameter("sbmnumber",sbmnumber).getResultList();
		   if(l.size()>0)
		      return flag=true;
		}
		
		return flag;
	}

	@Override
	public List<SBMDetailsEntity> getSbmNumber() {
		
		List<SBMDetailsEntity> list=null;
		list=postgresMdas.createNamedQuery("SBMDetailsEntity.GetSbmData").getResultList();
		System.out.println("DDDDDEEEEEEEE+++++++++++"+list.size());
		return list;
	}
	}

	

