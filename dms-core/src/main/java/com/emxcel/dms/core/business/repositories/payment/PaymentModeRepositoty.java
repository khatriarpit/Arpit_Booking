package com.emxcel.dms.core.business.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.model.payment.PaymentModel;

public interface PaymentModeRepositoty extends JpaRepository<PaymentModel, Long> {

}
