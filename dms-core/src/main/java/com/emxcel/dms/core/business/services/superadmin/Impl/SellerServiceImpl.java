package com.emxcel.dms.core.business.services.superadmin.Impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.repositories.city.CityRepository;
import com.emxcel.dms.core.business.repositories.country.CountryRepository;
import com.emxcel.dms.core.business.repositories.state.StateRepository;
import com.emxcel.dms.core.business.repositories.superadmin.SellerRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.SellerService;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.country.Country;
import com.emxcel.dms.core.model.seller.SellerModel;
import com.emxcel.dms.core.model.state.State;

@Service("sellerService")
public class SellerServiceImpl extends DMSEntityServiceImpl<Long,SellerModel> implements SellerService{



	private SellerRepository sellerRepository;

	@Inject
	private CountryRepository countryRepository;

	@Inject
	private CityRepository cityRepository;

	@Inject
	private StateRepository stateRepository;

	@PersistenceContext
	private EntityManager manager;

	@Inject
	public SellerServiceImpl(final SellerRepository sellerRepository) {
		super(sellerRepository);
		this.sellerRepository = sellerRepository;
	}


	@Override
	public void saveSellerDetail(SellerModel sellerModel, Long tenantID) {
		try {
			Country country = countryRepository.findOne(sellerModel.getCountry().getId());
			sellerModel.setCountry(country);
			if (sellerModel.getId() == null) {
				sellerModel.setStatus(Constants.ACTIVE);
			}
			State state = stateRepository.findOne(sellerModel.getState().getId());
			sellerModel.setState(state);
			sellerModel.setTanentID(tenantID);
			City city = cityRepository.findOne(sellerModel.getCity().getId());
			sellerModel.setCity(city);

			super.save(sellerModel);
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public boolean getIdByTenantID(Long tenantIDVal, Integer status) {
		SellerModel sellerModel = sellerRepository.getIdByTenantID(tenantIDVal, status);
		return sellerModel == null;
	}
}
