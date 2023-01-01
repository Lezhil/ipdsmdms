package com.bcits.serviceImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.entity.VersionApkEntity;
import com.bcits.service.VersionApkService;

@Repository
public class VersionApkServiceImpl extends GenericServiceImpl<VersionApkEntity> implements VersionApkService {
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String findApkMaxVersion() {
		
		String result = null;
		Object o =    postgresMdas.createNamedQuery("VersionApkEntity.GetApkMaxVersion").getSingleResult();
		String versionName = o.toString();
		
		return versionName;
		
	}

	@Override
	public String FindApkDetails(String maxVersion) {
		// TODO Auto-generated method stub
		
		
		VersionApkEntity o =    (VersionApkEntity) postgresMdas.createNamedQuery("VersionApkEntity.GetApkDetails")
				.setParameter("version", maxVersion)
				.getSingleResult();
		
		
		String name = o.getApkname();
		/*System.out.println(name);*/
		
		String path = o.getApk_path();
		/*System.out.println(path);*/
		
		
		
		 /*String filePathToBeServed = "E:\\MIPHT_LIVEAPK\\MIP_J3.0.apk";*/
		
		String filePathToBeServed = path+name;
		
		
		return filePathToBeServed;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String findApkMaxVersionAmr() {
		
		String result = null;
		Object o =    postgresMdas.createNamedQuery("VersionApkEntity.GetApkMaxVersionAmr").getSingleResult();
		String versionName = o.toString();
		
		return versionName;
		
	}

	@Override
	public String FindApkDetailsAmr(String maxVersion) {
		// TODO Auto-generated method stub
		
		
		VersionApkEntity o =    (VersionApkEntity) postgresMdas.createNamedQuery("VersionApkEntity.GetApkDetailsAmr")
				.setParameter("version", maxVersion)
				.getSingleResult();
		
		
		String name = o.getApkname();
		/*System.out.println(name);*/
		
		String path = o.getApk_path();
		/*System.out.println(path);*/
		
		
		
		 /*String filePathToBeServed = "E:\\MIPHT_LIVEAPK\\MIP_J3.0.apk";*/
		
		String filePathToBeServed = path+name;
		
		
		return filePathToBeServed;
	}
	
	

}
