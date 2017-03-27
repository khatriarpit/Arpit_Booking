package com.emxcel.dms.core.business.repositories.mapping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.car.CarDriverMapping;

public interface CarDriverMappingRepository extends JpaRepository<CarDriverMapping, Long> {
    /**
     * Created By- Ashka Thaker  Date-03/1/2017.
     * Used For Car No Checking
     *
     * @return carType
     */
    @Query("select c from CarDriverMapping c where c.tanentID=:tanentID")
    List<CarDriverMapping> findAllCarDriver(@Param("tanentID") Long tanentid);

    @Query("select c from CarDriverMapping c where c.tanentID=:tanentID and driId != null ")
    List<CarDriverMapping> carByDriverAssign(@Param("tanentID") Long tanentid);

    @Query("select c from CarDriverMapping c where c.tanentID=:tanentID and driId=:driID")
    CarDriverMapping checkRemoveCarDriver(@Param("tanentID") Long tanentID, @Param("driID") Long driId);

    //@Query("select c from CarDriverMapping c where c.tanentID=:tenantId and c.carId=:carId")
    @Query("select c from CarDriverMapping c where c.carId=:carId")
    CarDriverMapping getByCarIdAndTenant(@Param("carId") Long carId);

    @Query("select cm from CarDriverMapping cm where cm.carId=?")
	CarDriverMapping getCarDriverByCarId(Long carId);
}
