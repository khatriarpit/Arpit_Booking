package com.emxcel.dms.core.business.repositories.superadmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.seller.SellerModel;


public interface SellerRepository extends JpaRepository<SellerModel,Long>{

	@Query("select s from SellerModel s where s.tanentID=:tanentID and s.status=:status")
	SellerModel getIdByTenantID(@Param("tanentID") Long tenantIDVal, @Param("status") Integer status);
}
