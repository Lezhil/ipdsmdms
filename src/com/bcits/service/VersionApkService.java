package com.bcits.service;

import com.bcits.entity.VersionApkEntity;

public interface VersionApkService extends GenericService<VersionApkEntity>
{
	
	
	public String findApkMaxVersion();
	public String findApkMaxVersionAmr();
	public String FindApkDetails(String maxVersion);
	public String FindApkDetailsAmr(String maxVersion);

}
