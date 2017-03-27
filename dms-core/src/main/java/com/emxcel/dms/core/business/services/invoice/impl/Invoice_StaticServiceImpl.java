package com.emxcel.dms.core.business.services.invoice.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.invoice.Invoice_StaticRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.invoice.Invoice_StaticService;
import com.emxcel.dms.core.model.invoice.Invoice_Static;


/**
 * @author emxcelsolution
 *
 */
@Service("invoice_staticService")
public class Invoice_StaticServiceImpl extends DMSEntityServiceImpl<Long, Invoice_Static> implements Invoice_StaticService {

	/**
	 * invoice_StaticRepository
	 */
	@Inject
	private Invoice_StaticRepository invoice_StaticRepository;
	
	/**
	 * @param invoice_StaticRepository
	 */
	@Inject
	public Invoice_StaticServiceImpl(Invoice_StaticRepository invoice_StaticRepository) {
		super(invoice_StaticRepository);
		 this.invoice_StaticRepository = invoice_StaticRepository;
	}

	@Override
	public Invoice_Static getByTanentID(Long tanentID) {
		return invoice_StaticRepository.getByTanentID(tanentID);
	}


}