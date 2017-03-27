package com.emxcel.dms.core.business.services.payment;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.payment.PaymentModel;

public interface PaymentModeService extends DMSEntityService<Long, PaymentModel> {

	PaymentModel savePaymentMode(String txId, String txStatus, String pgTxnNo, String issuerRefNo, String authIdCode,
			String firstName, String lastName, String pgRespCode, String addressZip, String amount,
			String requestSignature)throws Exception;


	PaymentModel savePaymentModePayUMoney(String txId, String txStatus, String productInfo, String email,
			String phone, String firstName, String lastName, String service_provider, String addressZip,
			String amount, String hashcode, String pgType, String paymentMode) throws Exception;

}
