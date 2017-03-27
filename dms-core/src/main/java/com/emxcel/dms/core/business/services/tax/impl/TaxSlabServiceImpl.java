package com.emxcel.dms.core.business.services.tax.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.superadmin.InvoicePackageRepository;
import com.emxcel.dms.core.business.repositories.tax.TaxCategoryRepository;
import com.emxcel.dms.core.business.repositories.tax.TaxSlabRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.tax.TaxSlabService;
import com.emxcel.dms.core.model.superadmin.InvoicePackage;
import com.emxcel.dms.core.model.tax.TaxCategory;
import com.emxcel.dms.core.model.tax.TaxSlab;

import ch.qos.logback.core.net.SyslogOutputStream;

/**
 * @author swami
 *
 */
@Service("taxSlabService")
public class TaxSlabServiceImpl extends DMSEntityServiceImpl<Long, TaxSlab> implements TaxSlabService {

	/**
	 * this Repository for TaxSlabRepository.
	 */
	@Inject
	private TaxSlabRepository taxSlabRepository;
	
	/**
	 * this Repository for TaxCategoryRepository.
	 */
	@Inject
	private TaxCategoryRepository taxCateggoryRepository;
	
	/**
	 * this Repository for InvoicePackageRepository.
	 */
	@Inject
	private InvoicePackageRepository invoicePackageRepository;

	/**
	 * @param taxSlabRepository **taxSlabRepository**.
	 */
	@Inject
	public TaxSlabServiceImpl(final TaxSlabRepository taxSlabRepository) {
		super(taxSlabRepository);
		this.taxSlabRepository = taxSlabRepository;
	}

	/* (non-Javadoc)
	 * use : This is for list of Taxslab by tax id
	 * @see com.emxcel.dms.core.business.services.tax.TaxSlabService#getlistoftax(java.lang.Long)
	 */
	@Override
	public List<TaxSlab> getlistoftax(Long taxId) {
		return taxSlabRepository.getlistoftax(taxId);
	}
	/* (non-Javadoc)
	 * use: get list of Taxslab by  id
	 * @see com.emxcel.dms.core.business.services.tax.TaxSlabService#getTaxSlabById(java.lang.Long)
	 */
	@Override
	public List<TaxSlab> getTaxSlabById(Long id) {
		List<TaxSlab> listTaxslab = taxSlabRepository.getTaxSlabById(id);
		List<TaxSlab> listTaxSlabn = new ArrayList<TaxSlab>();
		for (TaxSlab taxSlab : listTaxslab) {
			TaxCategory taxCategory = taxCateggoryRepository.findOne(taxSlab.getTaxcategoryid());
			taxSlab.setTaxCategory(taxCategory);
			listTaxSlabn.add(taxSlab);
		}
		return listTaxSlabn;
	
	}
	
	/* (non-Javadoc)
	 * use:  This is for list of Taxslab set with TaxCategory. 
	 * @see com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl#list()
	 */
	@Override
	public List<TaxSlab> list() {
		List<TaxSlab> listTaxslab= taxSlabRepository.findAll();
		List<TaxSlab> listTaxSlabn = new ArrayList<TaxSlab>();
		for (TaxSlab taxSlab : listTaxslab) {
			TaxCategory taxCategory = taxCateggoryRepository.findOne(taxSlab.getTaxcategoryid());
			InvoicePackage invoicePackage = invoicePackageRepository.findOne(taxSlab.getInvoicecatid());
			taxSlab.setTaxCategory(taxCategory);
			taxSlab.setInvoicePackage(invoicePackage);
			listTaxSlabn.add(taxSlab);
		}
		return listTaxSlabn;
	}

}