package com.emxcel.dms.core.business.services.country.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.country.CountryRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.country.CountryService;
import com.emxcel.dms.core.model.country.Country;

@Service("countryService")
public class CountryServiceImpl extends DMSEntityServiceImpl<Long, Country> implements CountryService {

	@SuppressWarnings("unused")
	private CountryRepository countryRepository;

	@Inject
	public CountryServiceImpl(CountryRepository countryRepository) {
		super(countryRepository);
		this.countryRepository = countryRepository;
	}

}
