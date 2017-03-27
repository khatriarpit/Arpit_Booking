package com.emxcel.dms.core.business.repositories.trip;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.trip.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryCustom {

	/**
	 * @return
	 */
	@Query("select e from Expense e where date_format(createdDate, '%Y-%m-%d') = date_format(:date, '%Y-%m-%d') order by createdDate asc")
	List<Expense> getTotalCharges(@Param("date") Timestamp date);
}