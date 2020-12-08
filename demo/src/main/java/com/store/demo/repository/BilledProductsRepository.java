package com.store.demo.repository;

import com.store.demo.model.BilledProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BilledProductsRepository extends JpaRepository<BilledProduct, Integer> {
    List<BilledProduct> findByBillId(int id);
}
