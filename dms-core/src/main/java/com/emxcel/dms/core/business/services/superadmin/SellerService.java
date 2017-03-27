package com.emxcel.dms.core.business.services.superadmin;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.seller.SellerModel;

public interface SellerService extends DMSEntityService<Long, SellerModel>{

    void saveSellerDetail(SellerModel sellerModel, Long tenantID);

    boolean getIdByTenantID(Long tenantIDVal, Integer status);

}
