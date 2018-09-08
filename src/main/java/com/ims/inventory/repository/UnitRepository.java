package com.ims.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.inventory.entity.UnitEntity;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Integer> {

	
	
}