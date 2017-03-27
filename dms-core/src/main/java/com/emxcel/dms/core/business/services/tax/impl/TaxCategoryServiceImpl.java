package com.emxcel.dms.core.business.services.tax.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.tax.TaxCategoryRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.tax.TaxCategoryService;
import com.emxcel.dms.core.model.tax.TaxCategory;

/**
 * @author Jimit Patel
 *
 */
@Service("taxCategoryService")
public class TaxCategoryServiceImpl extends DMSEntityServiceImpl<Long, TaxCategory>  implements TaxCategoryService{

	/**
	 * 
	 */
	private TaxCategoryRepository taxCategoryRepository;

	/**
	 * @param taxCategoryRepository
	 */
	@Inject
	public TaxCategoryServiceImpl(
			final TaxCategoryRepository taxCategoryRepository) {
		super(taxCategoryRepository);
		this.taxCategoryRepository = taxCategoryRepository;
	}



	
}