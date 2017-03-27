package com.emxcel.dms.core.business.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.model.payment.BillPaymentModel;

public interface BillPaymentModelRepository extends JpaRepository<BillPaymentModel, Long> {

}
