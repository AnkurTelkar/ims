package com.ims.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ims.inventory.entity.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {

	@Query("SELECT coalesce(MAX(i.itemId) + 1, 1) FROM ItemEntity i")
	int getNextCode();
	
}