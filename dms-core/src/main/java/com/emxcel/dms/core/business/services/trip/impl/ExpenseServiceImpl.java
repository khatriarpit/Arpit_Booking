package com.emxcel.dms.core.business.services.trip.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.trip.ExpenseRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.trip.ExpenseService;
import com.emxcel.dms.core.model.trip.Expense;

/**
 * @author emxcelsolution
 *
 */
@Service("expenseService")
public class ExpenseServiceImpl extends DMSEntityServiceImpl<Long, Expense>implements ExpenseService{

	/**
	 * expenseRepository
	 */
	private ExpenseRepository expenseRepository;

	@Inject
	public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
		super(expenseRepository);
		this.expenseRepository = expenseRepository;
	}

	/* (non-Javadoc)
	 * @see com.emxcel.dms.core.business.services.trip.ExpenseService#getTotalCharges()
	 */
	@Override
	public List<Expense> getTotalCharges(Timestamp date) {
		return expenseRepository.getTotalCharges(date);
	}
}
