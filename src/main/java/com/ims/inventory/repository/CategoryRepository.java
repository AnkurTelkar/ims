package com.ims.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ims.inventory.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

	@Query("SELECT coalesce(MAX(c.categoryId) + 1, 1) FROM CategoryEntity c")
	int getNextCode();
	
}