package com.emxcel.dms.core.model.car;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.generic.DMSEntity;

import javax.persistence.*;

/**
 * Created by root on 1/2/17.
 */
@Entity
@Table(name="Car_Driver_Mapping",schema = SchemaConstant.DMS_SCHEMA)
public class CarDriverMapping extends DMSEntity<Long, CarDriverMapping> {


    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CAR_DRIVER_MAPPING_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name="car_id",nullable = false)
    private Long carId;

    @Column
    private Long driId;

    @Transient
    private Driver driver;

    @Transient
    private Car car;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public Long getDriId() {
        return driId;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setDriId(Long driId) {
        this.driId = driId;
    }

    public Car getCar() {
        return car;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

}