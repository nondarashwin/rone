package com.store.demo.repository;

import java.util.List;
import com.store.demo.model.Bill;
import com.store.demo.model.TopSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillsRepository extends JpaRepository<Bill, Integer> {
    @Query(value = "select Stores.name,Stores.id,sum(Billing.totalAmount) from Billing inner join Stores on Stores.id=Billing.storeId GROUP BY Stores.id;",nativeQuery = true)
    List findByCondition();
}
