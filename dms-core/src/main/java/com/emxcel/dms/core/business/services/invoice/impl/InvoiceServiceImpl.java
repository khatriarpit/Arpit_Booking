package com.emxcel.dms.core.business.services.invoice.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.invoice.InvoiceRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.invoice.InvoiceService;
import com.emxcel.dms.core.model.invoice.Invoice;


/**
 * @author emxcelsolution
 *
 */
@Service("invoiceService")
public class InvoiceServiceImpl extends DMSEntityServiceImpl<Long, Invoice> implements InvoiceService {

	/**
	 * 
	 */
	@Inject
	private InvoiceRepository invoiceRepository;
	
	/**
	 * @param invoiceRepository
	 */
	@Inject
	public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
		super(invoiceRepository);
		 this.invoiceRepository = invoiceRepository;
	}

	

	/* (non-Javadoc)
	 * @see com.emxcel.dms.core.business.services.invoice.InvoiceService#getTripByTripId(java.lang.String)
	 */
	@Override
	public Invoice getTripByTripId(String tripid) {
		return invoiceRepository.getTripByTripId(tripid);
	}

	/* (non-Javadoc)
	 * @see com.emxcel.dms.core.business.services.invoice.InvoiceService#getlastInvoiceNo()
	 */
	@Override
	public Invoice getlastInvoiceNo() {
		return invoiceRepository.getlastInvoiceNo();
	}



	@Override
	public Invoice getByTanentID(Long tanentID) {
		return invoiceRepository.getByTanentID(tanentID);
	}
}