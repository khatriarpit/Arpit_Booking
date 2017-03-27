package com.emxcel.dms.core.business.services.superadmin.Impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.emxcel.dms.core.business.repositories.city.CityRepository;
import com.emxcel.dms.core.business.repositories.companymaster.CompanyTypeRepository;
import com.emxcel.dms.core.business.repositories.country.CountryRepository;
import com.emxcel.dms.core.business.repositories.state.StateRepository;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.companymaster.CompanyType;
import com.emxcel.dms.core.model.country.Country;
import com.emxcel.dms.core.model.state.State;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.superadmin.TenantRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.superadmin.Tenant;

/**
 * @author Emxcel Solutions
 *
 */
@Service("tenantServices")
public class TenantServiceImpl extends DMSEntityServiceImpl<Long, Tenant> implements TenantService {

    /**
     * **tenantRepository**.
     */
    private TenantRepository tenantRepository;

    @PersistenceContext
    private EntityManager manager;

   /* @Inject
    private TenantService tenantService;*/

    @Inject
    private CountryRepository countryRepository;

    @Inject
    private StateRepository stateRepository;

    @Inject
    private CityRepository cityRepository;

    @Inject
    private CompanyTypeRepository companyTypeRepository;


    /**
     * @param tenantRepository **tenantRepository**
     */
    @Inject
    public TenantServiceImpl(final TenantRepository tenantRepository) {
        super(tenantRepository);
        this.tenantRepository = tenantRepository;
    }

    /*
     * Created By - Johnson Chunara Date-26-01-2017
     * Used For-save company
     *
     * @see com.emxcel.dms.core.business.services.superadmin.CompanyService#
     * saveCompany(com.emxcel.dms.core.model.superadmin.Company)
     */
    @Override
    public Long saveTenant(Tenant tenant) {
        Tenant tenant1 = tenantRepository.save(tenant);
        return tenant1.getId();
    }

    /*Created By - Pro Naresh Banda Date-26-01-2017
     * Used For-getCompanyServiceNo
     * @see com.emxcel.dms.core.business.services.superadmin.CompanyService#
     * getCompanyServiceNo(java.lang.String)
     */
    @Override
    public int getCompanyServiceNo(final String serviceNo, final Long id) {
        if (id != null) {
            return tenantRepository.getCompanyServiceNo(serviceNo, id);
        } else {
            return tenantRepository.getCompanyServiceNoWithOutCompanyId(serviceNo);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.emxcel.dms.core.business.services.superadmin.CompanyService#
     * getCompanyPanNo(java.lang.String)
     */
    @Override
    public int getCompanyPanNo(String panNo, Long id) {
        if (id != null) {
            return tenantRepository.getCompanyPanNo(panNo, id);
        } else {
            return tenantRepository.getCompanyPanNoWithOutCompanyId(panNo);
        }
    }

    /*
     * Created By - Naresh Banda Date-26-01-2017
     * Used For-getCompanyEmailId
     * @see com.emxcel.dms.core.business.services.superadmin.CompanyService#
     * getCompanyEmailId(java.lang.String)
     */
    @Override
    public int getCompanyEmailId(final String emailId, final Long id) {
        if (id != null) {
            return tenantRepository.getCompanyEmailId(emailId, id);
        } else {
            return tenantRepository.getCompanyEmailIdWithOutCompanyId(emailId);
        }
    }

    /*
     * Created By - Naresh Banda Date-26-01-2017
     * Used For-getCompanyContactNo
     * @see com.emxcel.dms.core.business.services.superadmin.CompanyService#
     * getCompanyContactNo(java.lang.Long)
     */
    @Override
    public int getCompanyContactNo(final Long contactNo, final Long id) {
        if (id != null) {
            return tenantRepository.getCompanyContactNo(contactNo, id);
        } else {
            return tenantRepository.getCompanyContactNoWithOutCompanyId(contactNo);
        }
    }

    /* Created By - Naresh Banda Date-26-01-2017
     * Used For-checkCompanyName
     * @see com.emxcel.dms.core.business.services.superadmin.CompanyService#checkCompanyName(java.lang.String, java.lang.Long)
     */
    @Override
    public int checkCompanyName(final String companyname, final Long id) {
        if (id != null) {
            return tenantRepository.checkCompanyName(companyname, id);
        } else {
            return tenantRepository.checkCompanyContactNoWithOutCompanyId(companyname);
        }
    }

    @Override
    public List<Tenant> getTenantByCityId(Long cityId) {
        List<Tenant> listOfTenant=null;
        listOfTenant=manager.createNamedQuery("getTenantByCityId", Tenant.class)
                .setParameter("cityID", cityId).getResultList();
        return listOfTenant;
    }




    @Override
    public Tenant getTenantByCityTenant(String tenant, Long cityid) {
        Tenant tenantnew = manager.createNamedQuery("getTenantByCityTenant", Tenant.class).setParameter("cityID", cityid).setParameter("tenant",tenant).getSingleResult();
        return tenantnew;
    }

    @Override
    public Long savetenant(Tenant tenant,int status) throws Exception{
        Tenant tenant1 =setCountryStateCity(tenant);
        tenant1.setStatus(status);
        tenant1.setEmailFlag(true);
        Long totalTenant  = tenantRepository.count();
        tenant1.setTanentid(genarateTenantID(totalTenant).toUpperCase());
        Tenant tenant2=tenantRepository.save(tenant1);
        return tenant1.getId();

    }

    @Override
    public void updatTenant(Tenant tenant,int status) throws Exception{

        Tenant tenant1 =setCountryStateCity(tenant);
        tenant1.setStatus(status);
        super.update(tenant1);


    }

    public Tenant setCountryStateCity(Tenant tenant){

        Country country = countryRepository.findOne(tenant.getCountry().getId());
        tenant.setCountry(country);
        State state = stateRepository.findOne(tenant.getState().getId());
        tenant.setState(state);
        City city = cityRepository.findOne(tenant.getCity().getId());
        tenant.setCity(city);
        CompanyType companyType = companyTypeRepository.findOne(tenant.getCompanytypename().getId());
        tenant.setCompanytypename(companyType);
        return tenant;
    }

    @Override
    public void deleteTenant(Tenant tenant,int status) throws Exception{
        tenant.setStatus(status);
        super.update(tenant);

    }

    @Override
    public void updateTenantAsPerPackage(Tenant tenant,boolean emailFlag,int emailStatus,int status) throws Exception{
        tenant.setStatus(status);
        tenant.setEmailFlag(emailFlag);
        tenant.setEmailStatus(emailStatus);
        super.update(tenant);

    }

	@Override
	public int getCompanyTypeStatus(CompanyType companyType) {
		return tenantRepository.getCompanyTypeStatus(companyType);
	}

    public String genarateTenantID(Long totalTenants){
        String tenantID = "";
        int first = 97;
        int second = 97;
            int tempor = 0;
            int temp= totalTenants.intValue();
            tempor = temp +1;
            int r = tempor % 100;
            int q = tempor / 100;
            int r1 = q%26;
            int q1 = q/26;
            String suffix = "";
            if(r <= 9){
                suffix = "0" + String.valueOf(r);
            }
            else {
                suffix = String.valueOf(r);
            }

            if(q1>0) {
                first = first + q1;
            }
            second = second + r1;

            char s =(char) second;
            char f = (char) first;

            return String.valueOf(f)+ String.valueOf(s)+suffix;

    }


}