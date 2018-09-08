package com.ims.vendor.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ims.vendor.entity.Vendor;

@Repository
public class VendorRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public String getVendorCode() {
		
		String qryString = "SELECT LPAD( IFNULL(MAX(id) + 1, 1), 5, 0 ) code FROM vendors";
        Query query = entityManager.createNativeQuery( qryString );
        String nextCode = ( String ) query.getSingleResult();
        return nextCode;
	}
	
	public Vendor getVendor( int id ) {
		return entityManager.find( Vendor.class, id );
	}
	
	public void saveOrUpdate( Vendor vendor ) {
		if( vendor.getId() <= 0 ) {
			entityManager.persist( vendor );
		} else {
			entityManager.merge( vendor );
		}
	}
	
	public List<Vendor> getAllVendors() {
		return entityManager.createQuery("SELECT v FROM Vendor v", Vendor.class).getResultList();
	}
	
	
	/*public List<Object[]> findTaxReportData( String startDate, String endDate ) {
		String queryString = " SELECT customer_name, gst_no, COUNT(bill_id) 'count' "
			+ "\n, SUM(taxable) taxable, SUM(cgst) cgst, SUM(sgst) sgst, SUM(total) total FROM  "
			+ "\n(SELECT customer_id, customer_name, gst_no, bill_id "
			+ "\n, ROUND( SUM( IF(TYPE = 1, amount, 0)), 2 ) taxable "
			+ "\n, ROUND( SUM( IF(TYPE = 4, amount, 0)), 2 ) cgst "
			+ "\n, ROUND( SUM( IF(TYPE = 5, amount, 0)), 2 ) sgst "
			+ "\n, ROUND( ( b.total), 2)  total "
			+ "\nFROM inv_bills b "
			+ "\nJOIN customers c USING(customer_id) "
			+ "\nJOIN inv_bill_amount_details d USING(bill_id) "
			+ "\nWHERE customer_id > 0 AND LENGTH(gst_no) > 0 "
			+ "\nAND DATE(b.created_at) BETWEEN ?1 AND ?2 "
			+ "\nGROUP BY b.bill_id "
			+ "\n) T "
			+ "\nGROUP BY customer_id "
			+ "\nORDER BY gst_no ";
		Query query = entityManager.createNativeQuery( queryString );
		query.setParameter(1, startDate="2017-01-01");
		query.setParameter(2, endDate);
		query.getResultList();
		return null;
	}*/
}
