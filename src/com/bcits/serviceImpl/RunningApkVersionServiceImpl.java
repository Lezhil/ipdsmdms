package com.bcits.serviceImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.RunningApkVersionEntity;
import com.bcits.service.RunningApkVersionService;


@Repository
public class RunningApkVersionServiceImpl extends GenericServiceImpl<RunningApkVersionEntity> implements RunningApkVersionService{

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String findApkVersion(double apk) {
		
		
		String result = null;
		Object o =    postgresMdas.createNamedQuery("RunningApkVersionEntity.GetApkVersion").getSingleResult();
		String versionName = o.toString();
        double serverApk = Double.parseDouble(versionName);
        
        System.out.println("apk:"+apk);
        System.out.println("serverApk :"+serverApk);
        
		
        if(apk == serverApk){
        	
        	result ="latest";
        	
        }
        else{
        	result ="old";
        }
		
		
		return result;
	}

}
