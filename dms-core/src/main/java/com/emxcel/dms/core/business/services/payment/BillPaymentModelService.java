package com.emxcel.dms.core.business.services.payment;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.payment.BillPaymentModel;

public interface BillPaymentModelService extends DMSEntityService<Long, BillPaymentModel> {

	BillPaymentModel saveBillPaymentMode(String amount, String accessKey, String returnUrl, String txnID,
			String hmac, String tripID)throws ServiceException;

}
