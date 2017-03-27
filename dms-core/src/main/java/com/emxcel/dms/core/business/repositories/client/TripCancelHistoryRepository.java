package com.emxcel.dms.core.business.repositories.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.model.client.TripCancelHistory;


public interface TripCancelHistoryRepository extends JpaRepository<TripCancelHistory, Long>{

}
