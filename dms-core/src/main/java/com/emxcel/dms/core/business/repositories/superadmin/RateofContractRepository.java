package com.emxcel.dms.core.business.repositories.superadmin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.superadmin.RateOfContract;

public interface RateofContractRepository extends JpaRepository<RateOfContract, Long> {
/*jjj*/
	@Query("select rt from RateOfContract rt where rt.rateOfContract=? and rt.id!=?")
	List<RateOfContract> getRateOfContractDBStatus(String rateOfContract, Long id);

}
