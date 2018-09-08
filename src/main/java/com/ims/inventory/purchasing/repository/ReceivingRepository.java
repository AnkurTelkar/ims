package com.ims.inventory.purchasing.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ims.inventory.purchasing.entity.ReceivingEntity;

@Repository
public interface ReceivingRepository extends JpaRepository<ReceivingEntity, Integer> {

	@Query("SELECT coalesce(MAX(r.receivingId) + 1, 1) FROM ReceivingEntity r")
	int getNextCode();
	
	@Query("SELECT b.receivingId, b.receivingNo, b.invoiceNo, b.vendor.id, b.status FROM ReceivingEntity b WHERE ( DATE( b.createdAt ) BETWEEN CONCAT( DATE(?1), '%' ) AND CONCAT( DATE(?2), '%' ) ) "
			+ "OR ( DATE( b.updatedAt ) BETWEEN CONCAT( DATE(?1), '%' ) AND CONCAT( DATE(?2), '%' ) ) " )
	List<Object[]> findAllCreatedOrModified( Date start, Date end );
}