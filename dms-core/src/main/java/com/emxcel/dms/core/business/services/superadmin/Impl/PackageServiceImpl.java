package com.emxcel.dms.core.business.services.superadmin.Impl;

import java.util.List;

import javax.inject.Inject;

import com.emxcel.dms.core.business.constants.Constants;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.superadmin.PackageRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.PackageService;
import com.emxcel.dms.core.model.superadmin.PackageModel;

/**
 * Created by root on 1/3/17.
 */
@Service("PackageService")
public class PackageServiceImpl extends DMSEntityServiceImpl<Long,PackageModel> implements PackageService {

    private PackageRepository packageRepository;

    @Inject
    public PackageServiceImpl(PackageRepository  packageRepository) {
        super(packageRepository);
        this.packageRepository = packageRepository;
    }

    @Override
    public List<PackageModel> getlistByStatus() {
        return packageRepository.getlistByStatus();
    }

	@Override
	public int checkPackageName(String name, Long id) {
		if (id != null) {
            return packageRepository.checkPackageName(name, id);
        } else {
            return packageRepository.checkPackageNameNoWithOutCompanyId(name);
        }
	}

	@Override
	public List<PackageModel> getlistByStatus(Long id) {
		return packageRepository.getlistByStatus(id);
	}

	@Override
	public PackageModel checkPackageName(String name) {
		return packageRepository.checkPackageName(name);
	}

	@Override
	public PackageModel checkPackageNameWithOutStatusCheck(String name, Long packageID) {
		if(packageID != null) {
			return packageRepository.checkPackageNameWithOutStatusCheck(name, packageID);			
		} else {
			return packageRepository.checkPackageNameWithOutStatusCheck(name);	
		}
	}

	@Override
	public void saveStatus(PackageModel packageModel) throws Exception {
		packageModel.setStatus(Constants.ACTIVE);
		super.save(packageModel);
	}

	@Override
	public void setStatusActive(PackageModel packageModel) throws Exception {
		packageModel.setStatus(Constants.ACTIVE);
		super.update(packageModel);
	}

	@Override
	public void setStatusInactive(PackageModel packageModel) throws Exception {
		packageModel.setStatus(Constants.DEACTIVE);
		super.update(packageModel);
	}
}
