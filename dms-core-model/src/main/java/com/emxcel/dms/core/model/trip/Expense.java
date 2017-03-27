package com.emxcel.dms.core.model.trip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

/**
 * Created by emxcelsolution on 1/31/17.
 */
@Entity
@Table(name = "expense", schema = SchemaConstant.DMS_SCHEMA)
public class Expense extends DMSEntity<Long, Expense> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1526494749287966855L;

	@Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "EXPENSE_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

	@Column(name = "car_no")
    private String carno;

    @Column(name = "fuel_charge")
    private Double fuelCharge;

    @Column(name = "vehicle_charge")
    private Double vehicleCharge;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setCarno(String carno) {
		this.carno = carno;
	}

    public String getCarno() {
		return carno;
	}

    public void setFuelCharge(Double fuelCharge) {
        this.fuelCharge = fuelCharge;
    }

    public Double getFuelCharge() {
        return fuelCharge;
    }

    public void setVehicleCharge(Double vehicleCharge) {
        this.vehicleCharge = vehicleCharge;
    }

    public Double getVehicleCharge() {
        return vehicleCharge;
    }
}