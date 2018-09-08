package com.ims.inventory.sales.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ims.ApplicationConstants;
import com.ims.inventory.sales.entity.BillEntity;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {

	@Query("SELECT coalesce(MAX(b.billId) + 1, 1) FROM BillEntity b")
	int getNextCode();

	BillEntity findByBillNo( String billNo );

	@Query("SELECT b.billId, b.billNo, b.customer.customerId, b.status, b.total FROM BillEntity b WHERE ( DATE( b.createdAt ) BETWEEN CONCAT( DATE(?1), '%' ) AND CONCAT( DATE(?2), '%' ) ) "
			//+ "OR ( DATE( b.updatedAt ) BETWEEN CONCAT( DATE(?1), '%' ) AND CONCAT( DATE(?2), '%' ) ) "
			//+ "OR ( DATE( b.billDate ) BETWEEN CONCAT( DATE(?1), '%' ) AND CONCAT( DATE(?2), '%' ) )"
			)
	List<Object[]> findAllCreatedOrModified( Date start, Date end );

	List<BillEntity> findByCustomerCustomerId( int customerId );

	@Query( "SELECT I, ROUND(SUM(BI.quantity), 2) as qty, ROUND(SUM(BI.quantity*BI.discount), 2) as discount"
			+ "\n, ROUND(SUM(BI.quantity*BI.price), 2) as price" 
			+ "\n FROM BillItemEntity BI"
			+ "\n JOIN BI.sku I"
			+ "\n JOIN BI.bill B"
			+ "\n WHERE ( DATE( BI.createdAt ) BETWEEN CONCAT( DATE(?1), '%' ) AND CONCAT( DATE(?2), '%' ) ) "
			//+ "\n OR ( DATE( BI.updatedAt ) BETWEEN CONCAT( DATE(?1), '%' ) AND CONCAT( DATE(?2), '%' ) )"
			+ "\n AND B.status IN (" + ApplicationConstants.STATUS_DRAFT +", " + ApplicationConstants.STATUS_FINALIZE + ") "
			+ "\n GROUP BY I.skuId ORDER BY 2")
	List<Object[]> findBillItemSaleDetails( Date start, Date end );

	@Query(value = "SELECT customer_name, gst_no, COUNT(bill_id) 'count' "
			+ "\n, SUM(taxable) taxable, SUM(cgst) cgst, SUM(sgst) sgst, SUM(total) total FROM  "
			+ "\n(SELECT customer_id, customer_name, gst_no, bill_id "
			+ "\n, ROUND( SUM( IF(TYPE = 1, IF( b.status = " + ApplicationConstants.STATUS_REFUND + ", -amount, amount ), 0)), 2 ) taxable "
			+ "\n, ROUND( SUM( IF(TYPE = 4, IF( b.status = " + ApplicationConstants.STATUS_REFUND + ", -amount, amount ), 0)), 2 ) cgst "
			+ "\n, ROUND( SUM( IF(TYPE = 5, IF( b.status = " + ApplicationConstants.STATUS_REFUND + ", -amount, amount ), 0)), 2 ) sgst "
			+ "\n, ROUND( ( b.total))  total "
			+ "\n FROM inv_bills b "
			+ "\n JOIN customers c USING(customer_id) "
			+ "\n JOIN inv_bill_amount_details d USING(bill_id) "
			+ "\n WHERE customer_id > 0 AND LENGTH(gst_no) > 0 "
			+ "\n AND DATE(b.created_at) BETWEEN ?1 AND ?2 "
			+ "\n AND b.status IN (" + ApplicationConstants.STATUS_DRAFT + "," + ApplicationConstants.STATUS_FINALIZE + "," + ApplicationConstants.STATUS_REFUND + ")"
			+ "\n GROUP BY b.bill_id "
			+ "\n) T "
			+ "\nGROUP BY customer_id "
			+ "\nORDER BY gst_no ", nativeQuery = true)
	List<Object[]> findTaxReportData( String startDate, String endDate );
	
	@Query( value="SELECT 'GST SALE', ROUND(SUM( IF(LENGTH(gst_no) > 0, IF( b.status = " + ApplicationConstants.STATUS_REFUND + ", -total, total ), 0)), 2)  gst_sale "
			+ "\n, 'URD SALE', ROUND(SUM( IF(LENGTH(gst_no) <= 0 OR LENGTH(gst_no) IS NULL, IF( b.status = " + ApplicationConstants.STATUS_REFUND + ", -total, total ), 0)), 2)  urd_sale "
			+ "\n, 'BILL NO', CONCAT(MIN(bill_no), ' to ', MAX(bill_no)) bill_nos "
			+ "\nFROM inv_bills b  "
			+ "\nJOIN customers c USING(customer_id) "
			+ "\nWHERE customer_id > 0 " 
			+ "\nAND DATE(b.created_at) BETWEEN ?1 AND ?2 "
			+ "\n AND b.status IN (" + ApplicationConstants.STATUS_DRAFT + "," + ApplicationConstants.STATUS_FINALIZE + "," + ApplicationConstants.STATUS_REFUND + ")"
			+ "\nORDER BY gst_no ", nativeQuery=true )
	List<Object> findTaxReportSummaryData( String startDate, String endDate );
	
	@Query("SELECT COUNT(B) FROM BillEntity B WHERE B.customer.customerId=?1")
	int countByCustomerId(int customerId);
}