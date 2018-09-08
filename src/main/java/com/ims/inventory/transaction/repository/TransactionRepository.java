package com.ims.inventory.transaction.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ims.inventory.transaction.entity.TransactionDetailEntity;

public interface TransactionRepository extends JpaRepository<TransactionDetailEntity, Integer> {
	
@Query("SELECT t.skuId, COUNT(t.skuId) as counts FROM TransactionDetailEntity t WHERE t.refType <> 1 GROUP BY t.skuId")
List<Object[]> getTopSkus( Pageable pageable );
	
}