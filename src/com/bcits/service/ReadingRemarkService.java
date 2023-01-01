package com.bcits.service;

import java.util.List;

import com.bcits.entity.ReadingRemark;
/*Author: Ved Prakash Mishra*/
public interface ReadingRemarkService  extends GenericService<ReadingRemark>
{
   public List<ReadingRemark> selectReadingRemark();

}
