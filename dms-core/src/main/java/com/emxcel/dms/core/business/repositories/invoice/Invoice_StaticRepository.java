package com.emxcel.dms.core.business.repositories.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.emxcel.dms.core.model.invoice.Invoice_Static;

/**
 * @author emxcelsolution
 *
 */
@Transactional
public interface Invoice_StaticRepository extends JpaRepository<Invoice_Static, Long> {

	@Query("select i from Invoice_Static i where i.tanentID=:tanentID")
	Invoice_Static getByTanentID(@Param("tanentID") Long tanentID);

}