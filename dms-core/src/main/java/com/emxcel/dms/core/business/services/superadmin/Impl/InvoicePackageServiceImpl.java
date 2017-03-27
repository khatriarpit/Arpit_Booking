package com.emxcel.dms.core.business.services.superadmin.Impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.superadmin.InvoicePackageRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.InvoicePackageService;
import com.emxcel.dms.core.model.car.CarType;
import com.emxcel.dms.core.model.superadmin.InvoicePackage;

/**
 * @author Emxcel Solutions
 *
 */
@Service("invoicePackageService")
public class InvoicePackageServiceImpl extends DMSEntityServiceImpl<Long, InvoicePackage> implements InvoicePackageService {

	/**
	 * **invoicePackageRepository**.
	 */
	private InvoicePackageRepository invoicePackageRepository;
	
	/**
	 *   Created By-Johnson Chunara Date-26-01-2016.
	 * Used For-Cunstructor
	 * @param invoicePackageRepository
	 */
	@Inject
	public InvoicePackageServiceImpl(final InvoicePackageRepository invoicePackageRepository) {
		super(invoicePackageRepository);
		this.invoicePackageRepository = invoicePackageRepository;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Created By-Johnson Chunara Date-26-01-2017.
	 * Used For-getInvoiceTypeDBStatus
	 * @param categoryname **categoryname**
	 * @param id **id**
	 * @return List 
	 */
	@Override
	public List<InvoicePackage> getInvoiceTypeDBStatus(final String categoryname,final Long id) {
		// TODO Auto-generated method stub
		return invoicePackageRepository.getInvoiceTypeDBStatus(categoryname,id);
	}

}
