package com.bcits.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.SBMDetailsEntity;
/*Author: Ved Prakash Mishra*/
public interface SBMDetailsService extends GenericService<SBMDetailsEntity> 
{
   public List<SBMDetailsEntity> getAllDetails();
   public Boolean findSbmNum(ModelMap model,String sbmnumber);
   public List<SBMDetailsEntity> getSbmNumber();
}
