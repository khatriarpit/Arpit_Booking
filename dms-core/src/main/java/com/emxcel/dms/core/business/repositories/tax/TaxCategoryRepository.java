package com.emxcel.dms.core.business.repositories.tax;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.tax.TaxCategory;

public interface TaxCategoryRepository extends JpaRepository<TaxCategory, Long>, TaxCategoryRepositoryCustom {


}