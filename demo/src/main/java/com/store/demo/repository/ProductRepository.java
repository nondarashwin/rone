package com.store.demo.repository;

import com.store.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String name);
    @Query(value = "select Product.name,Product.id,sum(BillingProduct.quantity) From Product inner join BillingProduct on Product.id=BillingProduct.productId GROUP BY Product.id;",nativeQuery = true)
    List findByCondition();
}
