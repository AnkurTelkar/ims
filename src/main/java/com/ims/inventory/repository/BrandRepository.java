package com.ims.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ims.inventory.entity.BrandEntity;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {

	@Query("SELECT coalesce(MAX(b.brandId) + 1, 1) FROM BrandEntity b")
	int getNextCode();
	
}