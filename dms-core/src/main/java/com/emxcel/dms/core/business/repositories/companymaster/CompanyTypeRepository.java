package com.emxcel.dms.core.business.repositories.companymaster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.companymaster.CompanyType;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> ,CompanyTypeRepositoryCustom{

	@Query("select c from CompanyType c where c.companyType= ? and c.id != ?")
	List<CompanyType> checkcompanytype(String companyType, Long id);

	@Query("select c from CompanyType c where c.companyStatus=?")
	List<CompanyType> getcompanyListByStatus(boolean b);

	@Query("select c from CompanyType c where c.companyType= ? ")
	List<CompanyType> checkcompanytypeby(String companyType);

}
