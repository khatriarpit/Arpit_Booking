package com.emxcel.dms.core.business.services.payment.impl;

import javax.inject.Inject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.payment.BillPaymentModelRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.payment.BillPaymentModelService;
import com.emxcel.dms.core.model.payment.BillPaymentModel;

@Service("billPaymentModelService")
public class BillPaymentModelServiceImpl extends DMSEntityServiceImpl<Long,BillPaymentModel> implements BillPaymentModelService{

	
	private BillPaymentModelRepository billPaymentModelRepository;	
	
	@Inject
	public BillPaymentModelServiceImpl (BillPaymentModelRepository billPaymentModelRepository) {
		super(billPaymentModelRepository);
		this.billPaymentModelRepository=billPaymentModelRepository;
		// TODO Auto-generated constructor stub
	}

	@Override
	public BillPaymentModel saveBillPaymentMode(String amount, String accessKey, String returnUrl, String txnID,
			String hmac,String tripID) throws ServiceException {
		BillPaymentModel billPaymentModel=new BillPaymentModel();
		billPaymentModel.setAmount(amount);
		billPaymentModel.setRequestSignature(hmac);
		billPaymentModel.setMerchantAccessKey(accessKey);
		billPaymentModel.setReturnUrl(returnUrl);
		billPaymentModel.setMerchantTxnId(txnID);
		billPaymentModel.setTripID(tripID);
		super.save(billPaymentModel);
		return billPaymentModel;
		
	}

}
