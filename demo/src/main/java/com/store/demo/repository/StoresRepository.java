package com.store.demo.repository;

import com.store.demo.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoresRepository extends JpaRepository<Store, Integer> {
    Store findByName(String name);
    @Query(value = "select Product.name,Product.id,sum(BillingProduct.quantity) From Product inner join BillingProduct on Product.id=BillingProduct.productId  inner join Billing on BillingProduct.billId = Billing.id  GROUP BY Product.id,Billing.storeId having Billing.storeId=?1",nativeQuery = true)
    List findByCondition(int id);
}
