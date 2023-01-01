package com.bcits.serviceImpl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.ChangesEntity;
import com.bcits.entity.Master;
import com.bcits.entity.MeterMaster;
import com.bcits.service.ChangesService;
import com.bcits.service.MasterService;

@Repository
public class ChangesServiceImpl extends GenericServiceImpl<ChangesEntity>
		implements ChangesService {

	@Autowired
	private MasterService masterService;
	
	/*@Transactional(propagation = Propagation.REQUIRED)
	public void InsertTransaction(MeterMaster meterMaster,String oldNew, HttpServletRequest request) 
	{
        ChangesEntity entity = new ChangesEntity();
		entity.setAccno(meterMaster.getAccno());
	    entity.setCd(meterMaster.getMaster().getContractdemand()+ "");
		entity.setCst(meterMaster.getMaster().getConsumerstatus());
		entity.setCtrd(meterMaster.getCtrd());
		entity.setCtrn(meterMaster.getCtrn());
		entity.setDateStamp(new Date());
		entity.setKwOrHp(meterMaster.getMaster().getKworhp());
		entity.setMeterno(meterMaster.getMetrno());
		entity.setMf(meterMaster.getMf());
		entity.setMrname(meterMaster.getMrname());
		entity.setMtrmake(meterMaster.getMtrmake());
		entity.setMtrType(meterMaster.getMtrtype());
	    entity.setName(meterMaster.getMaster().getName());
		entity.setOldNew(oldNew);
	    entity.setPrevMtrStatus(meterMaster.getPrevmeterstatus());
		entity.setRdgMonth(meterMaster.getRdngmonth());
	    entity.setSanload(meterMaster.getMaster().getSanload());
		entity.setSupplyType(meterMaster.getMaster().getSupplytype());
		entity.setTadesc(meterMaster.getMaster().getTadesc());
		entity.setTariffCode(meterMaster.getMaster().getTariffcode());
		// entity.setTimeStamp(new Date()+"");
		entity.setKno(meterMaster.getMaster().getKno());
		entity.setUsername(request.getSession().getAttribute("username") + "");
      save(entity);
		}*/
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void InsertTransaction(MeterMaster meterMaster,String oldNew, HttpServletRequest request) 
	{
		System.out.println("oldNew-->"+oldNew);
		System.out.println(meterMaster.getCtrd());
		System.out.println(meterMaster.getCtrn());
		System.out.println(meterMaster.getMetrno());
		System.out.println(meterMaster.getMrname());
		System.out.println(meterMaster.getMtrmake());
		System.out.println(meterMaster.getMtrtype());
		System.out.println(meterMaster.getPrevmeterstatus());
		
        ChangesEntity entity = new ChangesEntity();
        Master master=masterService.find(meterMaster.getAccno());
		entity.setAccno(meterMaster.getAccno());
	    entity.setCd( master.getContractdemand()+ "");
		entity.setCst(master.getConsumerstatus());
		entity.setCtrd(meterMaster.getCtrd());
		entity.setCtrn(meterMaster.getCtrn());
		entity.setDateStamp(new Date());
		entity.setKwOrHp(master.getKworhp());
		entity.setMeterno(meterMaster.getMetrno());
		entity.setMf(meterMaster.getMf());
		entity.setMrname(meterMaster.getMrname());
		entity.setMtrmake(meterMaster.getMtrmake());
		entity.setMtrType(meterMaster.getMtrtype());
	    entity.setName(master.getName());
		entity.setOldNew(oldNew);
	    entity.setPrevMtrStatus(meterMaster.getPrevmeterstatus());
		entity.setRdgMonth(meterMaster.getRdngmonth());
	    entity.setSanload(master.getSanload());
		entity.setSupplyType(master.getSupplytype());
		entity.setTadesc(master.getTadesc());
		entity.setTariffCode(master.getTariffcode());
		// entity.setTimeStamp(new Date()+"");
		entity.setKno(master.getKno());
		entity.setUsername(request.getSession().getAttribute("username") + "");
		save(entity);
		}

	/*@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ChangesEntity InsertTransaction1(MeterMaster meterMaster,String oldNew, HttpServletRequest request) 
	{
	
	      ChangesEntity entity = new ChangesEntity();
	      
			//entity.setId(14555);
		    entity.setAccno(meterMaster.getAccno());
		    entity.setCd(meterMaster.getMaster().getContractdemand()+ "");
			entity.setCst(meterMaster.getMaster().getConsumerstatus());
			entity.setCtrd(meterMaster.getCtrd());
			entity.setCtrn(meterMaster.getCtrn());
			entity.setDateStamp(new Date());
			entity.setKwOrHp(meterMaster.getMaster().getKworhp());
			entity.setMeterno(meterMaster.getMetrno());
			entity.setMf(meterMaster.getMf());
			entity.setMrname(meterMaster.getMrname());
			entity.setMtrmake(meterMaster.getMtrmake());
			entity.setMtrType(meterMaster.getMtrtype());
		    entity.setName(meterMaster.getMaster().getName());
			entity.setOldNew(oldNew);
		    entity.setPrevMtrStatus(meterMaster.getPrevmeterstatus());
			entity.setRdgMonth(meterMaster.getRdngmonth());
		    entity.setSanload(meterMaster.getMaster().getSanload());
			entity.setSupplyType(meterMaster.getMaster().getSupplytype());
			entity.setTadesc(meterMaster.getMaster().getTadesc());
			entity.setTariffCode(meterMaster.getMaster().getTariffcode());
		  //  entity.setTimeStamp(new Date()+"");
			entity.setUsername(request.getSession().getAttribute("username") + "");
			entity.setSdocode(meterMaster.getSdocode());
			return entity;
	}*/
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ChangesEntity InsertTransaction1(MeterMaster meterMaster,String oldNew, HttpServletRequest request) 
	{
	
	      ChangesEntity entity = new ChangesEntity();
	      
	      Master master=masterService.find(meterMaster.getAccno());
	      
			
		    entity.setAccno(meterMaster.getAccno());
		    entity.setCd(master.getContractdemand()+ "");
			entity.setCst(master.getConsumerstatus());
			System.out.println(meterMaster.getCtrd());
			if(meterMaster.getCtrd()==null)
			{
				entity.setCtrd(0);
			}
			else{
				entity.setCtrd(meterMaster.getCtrd());
			}
			if(meterMaster.getCtrn()==null)
			{
				entity.setCtrd(0);
			}
			else{
				entity.setCtrn(meterMaster.getCtrn());
			}
			
			entity.setDateStamp(new Date());
			entity.setKwOrHp(master.getKworhp());
			entity.setMeterno(meterMaster.getMetrno());
			entity.setMf(meterMaster.getMf());
			entity.setMrname(meterMaster.getMrname());
			entity.setMtrmake(meterMaster.getMtrmake());
			entity.setMtrType(meterMaster.getMtrtype());
		    entity.setName(master.getName());
			entity.setOldNew(oldNew);
		    entity.setPrevMtrStatus(meterMaster.getPrevmeterstatus());
			entity.setRdgMonth(meterMaster.getRdngmonth());
		    entity.setSanload(master.getSanload());
			entity.setSupplyType(master.getSupplytype());
			entity.setTadesc(master.getTadesc());
			entity.setTariffCode(master.getTariffcode());
		  //  entity.setTimeStamp(new Date()+"");
			entity.setUsername(request.getSession().getAttribute("username") + "");
			entity.setSdocode(meterMaster.getSdocode());
			return entity;
	}
	

		}


