package com.emxcel.dms.core.business.repositories.country;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.model.country.Country;

public interface CountryRepository extends JpaRepository<Country, Long>{
	

}
