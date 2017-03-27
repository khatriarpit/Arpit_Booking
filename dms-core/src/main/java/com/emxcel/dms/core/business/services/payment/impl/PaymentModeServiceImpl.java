package com.emxcel.dms.core.business.services.payment.impl;

import com.emxcel.dms.core.business.repositories.payment.PaymentModeRepositoty;
import com.emxcel.dms.core.business.services.payment.PaymentModeService;
import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.payment.PaymentModel;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("payMentModeService")
public class PaymentModeServiceImpl extends DMSEntityServiceImpl<Long, PaymentModel> implements PaymentModeService{

	private PaymentModeRepositoty paymentModeRepositoty;

	@Inject
	public PaymentModeServiceImpl(PaymentModeRepositoty paymentModeRepositoty) {
		super(paymentModeRepositoty);
		this.paymentModeRepositoty=paymentModeRepositoty;
	}


	@Override
	public PaymentModel savePaymentMode(String txId, String txStatus, String pgTxnNo, String issuerRefNo, String authIdCode,
			String firstName, String lastName, String pgRespCode, String addressZip, String amount,
			String requestSignature)throws Exception {
		PaymentModel paymentModel =new PaymentModel();
		paymentModel.setAddressZip(addressZip);
		paymentModel.setAmount(amount);
		paymentModel.setAuthIdCode(authIdCode);
		paymentModel.setIssuerRefNo(issuerRefNo);
		paymentModel.setPgRespCode(pgRespCode);
		paymentModel.setPgTxnNo(pgTxnNo);
		paymentModel.setRequestSignature(requestSignature);
		paymentModel.setIssuerRefNo(issuerRefNo);
		paymentModel.setFirstName(firstName);
		paymentModel.setLastName(lastName);
		paymentModel.setAuthIdCode(authIdCode);
		super.save(paymentModel);
		return paymentModel;
		
	}

	
	/* (non-Javadoc)
	 * @see com.emxcel.dms.core.business.services.payment.PaymentModeService#savePaymentModePayUMoney(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public PaymentModel savePaymentModePayUMoney(String txId, String txStatus, String productInfo, String email,
			String phone, String firstName, String lastName, String service_provider, String addressZip,
			String amount, String hashcode,String pgType, String paymentMode) throws Exception {
		// TODO Auto-generated method stub
		PaymentModel paymentModel = new PaymentModel();
//		paymentModel.setProductInfo(productInfo);
		paymentModel.setAddressZip(addressZip);
		paymentModel.setAmount(amount);
		paymentModel.setFirstName(firstName);
		paymentModel.setLastName(lastName);
//		paymentModel.setHashcode(hashcode);
		paymentModel.setAuthIdCode(phone);
/*		paymentModel.setEmail(email);
		paymentModel.setService_provider(service_provider);
		paymentModel.setHashcode(hashcode);*/
	//	paymentModel.setHashcode(hashcode);
	//	paymentModel.setPgType(pgType);
	//	paymentModel.setPaymentMode(paymentMode);
		super.save(paymentModel);
		return paymentModel;
	}

}
