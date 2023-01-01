package com.bcits.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.bcits.entity.SmsToRecipient;

public interface SmsToRecipientService extends GenericService<SmsToRecipient> {

	List<?> findAllRecipients(ModelMap model);

	List<SmsToRecipient> getDataBySubdiv(String division, String subdivision);


}
