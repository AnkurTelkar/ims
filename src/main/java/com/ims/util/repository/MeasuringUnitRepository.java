package com.ims.util.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.util.entity.MeasuringUnitEntity;

@Repository
public interface MeasuringUnitRepository extends JpaRepository<MeasuringUnitEntity, Integer> {

	
}