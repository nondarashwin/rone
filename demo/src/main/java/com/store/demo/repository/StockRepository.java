package com.store.demo.repository;

import com.store.demo.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findByStoreId(int id);

    List<Stock> findByProductId(int id);

    @Query("select stock from Stock stock where stock.stock<=(select avg(billedProduct.quantity) from BilledProduct billedProduct where billedProduct.productId=stock.productId)")
    List<Stock> findByStock();

    @Query("select stock from Stock stock where stock.productId=?1 and stock.storeId=?2")
    Stock findByCondition(int productId, int storeId);

}
