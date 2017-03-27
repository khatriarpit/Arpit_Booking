package com.emxcel.dms.core.business.repositories.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.emxcel.dms.core.model.invoice.Invoice;

/**
 * @author emxcelsolution
 *
 */
@Transactional
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	/**
	 * @param tripid
	 * @return
	 */
	@Query("select i from Invoice i where i.tripId=?")
	Invoice getTripByTripId(String tripid);

	/**
	 * @return
	 */
	@Query("select i from Invoice i ORDER BY invoiceNo DESC")
	Invoice getlastInvoiceNo();

	@Query("select i from Invoice i where i.tanentID=:tanentID")
	Invoice getByTanentID(@Param("tanentID") Long tanentID);
}