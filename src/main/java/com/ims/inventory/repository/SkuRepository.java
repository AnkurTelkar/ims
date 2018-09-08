package com.ims.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ims.inventory.entity.SkuEntity;

@Repository
public interface SkuRepository extends JpaRepository<SkuEntity, Integer> {

	@Query("SELECT coalesce(MAX(s.skuId) + 1, 1) FROM SkuEntity s")
	int getNextCode();
	
	List<SkuEntity> findByItemItemId( int itemId );
	
	@Query( "SELECT s.barcode, s.skuId, s.cost, s.gst, s.retailPrice, s.sellingPrice"
			+ ", \n CONCAT(i.itemName, ' ', t.unitName, ' - ', s.description) as skuDetails "
			+ ", \n CONCAT(s.skuCode, ' - ', s.description) as skuShortDetails "
			+ ", \n ROUND(s.sellingPrice / ( 1 + (s.gst/100) ), 2 ) as priceWithoutGst "
			+ "\n FROM  SkuEntity s "
			+ " \n JOIN s.item i  \nJOIN i.trackUnit t "
			+ "\n WHERE CONCAT(i.itemName, ' - ', t.unitName, ' - ', s.description) LIKE :input" )
	List<Object[]> findSkuDetails( @Param( "input" ) String input );
	
	SkuEntity findByBarcode( String barcode );
	@Query( "SELECT s FROM SkuEntity s WHERE s.quantity < s.threshold" )
	List<SkuEntity> findAllQuantityLessThanThreshold();
	
}