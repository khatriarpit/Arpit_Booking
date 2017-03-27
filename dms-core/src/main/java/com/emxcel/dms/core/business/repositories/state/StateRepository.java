package com.emxcel.dms.core.business.repositories.state;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.state.State;

public interface StateRepository extends JpaRepository<State, Long>{
	
	@Query("select s from State s where s.countryId=?")
	List<State> getstate(Long countryId);
}