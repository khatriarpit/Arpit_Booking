package com.emxcel.dms.core.business.repositories.superadmin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.superadmin.PackageModel;

public interface PackageRepository extends JpaRepository<PackageModel, Long> {

	@Query("select pm from PackageModel pm where pm.status=0")
	List<PackageModel> getlistByStatus();

	@Query("select count(p.id) from PackageModel p where p.name=:packageName and p.id != :id and p.status=0")
	int checkPackageName(@Param("packageName") String packageName, @Param("id") Long id);

	@Query("select count(p.id) from PackageModel p where p.name=:packageName and p.status=0")
	int checkPackageNameNoWithOutCompanyId(@Param("packageName") String packageName);

	@Query("select pm from PackageModel pm where pm.status=0 and pm.id != :id")
	List<PackageModel> getlistByStatus(@Param("id") Long id);

	@Query("select p from PackageModel p where p.name=:packageName and p.status=0")
	PackageModel checkPackageName(@Param("packageName") String packageName);

	@Query("select p from PackageModel p where p.name=:packageName")
	PackageModel checkPackageNameWithOutStatusCheck(@Param("packageName") String packageName);

	@Query("select p from PackageModel p where p.name=:packageName and p.id != :packageID")
	PackageModel checkPackageNameWithOutStatusCheck(@Param("packageName") String packageName,
			@Param("packageID") Long packageID);
}