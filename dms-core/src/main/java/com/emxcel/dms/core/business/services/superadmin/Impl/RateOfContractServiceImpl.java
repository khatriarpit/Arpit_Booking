package com.emxcel.dms.core.business.services.superadmin.Impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.business.repositories.superadmin.RateofContractRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.RateOfContractService;
import com.emxcel.dms.core.model.superadmin.RateOfContract;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("rateOfContractService")
public class RateOfContractServiceImpl extends DMSEntityServiceImpl<Long, RateOfContract> implements RateOfContractService {

	/*JJJ*/
	private RateofContractRepository  rateofContractRepository;

	@Inject
	public RateOfContractServiceImpl(RateofContractRepository rateofContractRepository) {
		super(rateofContractRepository);
		this.rateofContractRepository=rateofContractRepository;
	}

	@Override
	public List<RateOfContract> getRateOfContractDBStatus(String rateOfContract, Long id) {
		return rateofContractRepository.getRateOfContractDBStatus(rateOfContract,id);
	}



}
