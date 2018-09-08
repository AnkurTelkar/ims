package com.ims.util.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DashboardRepository {

	@Query("SELECT coalesce(MAX(b.billId) + 1, 1) FROM BillEntity b")
	Object getDashboardData();
}