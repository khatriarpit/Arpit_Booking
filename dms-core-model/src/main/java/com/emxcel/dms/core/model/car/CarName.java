package com.emxcel.dms.core.model.car;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

@Entity
@Table(name = "car_name", schema = SchemaConstant.DMS_SCHEMA)
public class CarName extends DMSEntity<Long, CarName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5496239026796704505L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CAR_NAME_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "car_name",length = 50,nullable = false)
	private String carName;

	@Column(name = "car_type_id",nullable = false)
	private Long carTypeId;

	@Transient
	private CarType carType;

	public Long getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(Long carTypeId) {
		this.carTypeId = carTypeId;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
