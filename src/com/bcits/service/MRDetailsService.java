package com.bcits.service;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.MeterReaderDetailsEntity;
/*Author: Ved Prakash Mishra*/
public interface MRDetailsService extends GenericService<MeterReaderDetailsEntity>
{
   public List<MeterReaderDetailsEntity> getAllMrDetails();
   public Boolean findDupliMRname(String mrname);
   public Boolean findDevice1(ModelMap model,String sbmno);
   public String findDevice2(ModelMap model,String sbmno);
   public String findMRname(String sbmno);
   
   
}
