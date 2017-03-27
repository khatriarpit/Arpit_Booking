package com.emxcel.dms.core.business.services.invoice.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.invoice.TermsConditionsRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.invoice.TermsConditionsService;
import com.emxcel.dms.core.model.invoice.TermsConditions;

import java.util.List;


/**
 * @author emxcelsolution
 *
 */
@Service("termsConditionsService")
public class TermsConditionsServiceImpl extends DMSEntityServiceImpl<Long, TermsConditions> implements TermsConditionsService {

	/**
	 * 
	 */
	@Inject
	private TermsConditionsRepository termsConditionsRepository;
	
	/**
	 * @param termsConditionsRepository
	 */
	@Inject
	public TermsConditionsServiceImpl(TermsConditionsRepository termsConditionsRepository) {
		super(termsConditionsRepository);
		 this.termsConditionsRepository = termsConditionsRepository;
	}

	@Override
	public List<TermsConditions> getListOfTermConditionsByTenantID(Long invoiceId) {
		return termsConditionsRepository.getListOfTermConditionsByTenantID(invoiceId);
	}
}