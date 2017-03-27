package com.emxcel.dms.core.business.repositories.city;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.city.City;

public interface CityRepository extends JpaRepository<City, Long>, CityRepositoryCustom {

	@Query("select c from City c where c.stateID=?")
	List<City> listCity(Long stateID);

	@Query("select c from City c where c.cityName=?")
	City getCityByCityName(String city);
}
