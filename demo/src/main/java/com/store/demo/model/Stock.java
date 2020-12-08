package com.store.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Stock")
@IdClass(Stock.class)
public class Stock implements Serializable {
    @Id
    @PrimaryKeyJoinColumn
    private int storeId;
    @Id
    @PrimaryKeyJoinColumn
    private int productId;
    private int stock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock stock1 = (Stock) o;
        return getStoreId() == stock1.getStoreId() &&
                getProductId() == stock1.getProductId() &&
                getStock() == stock1.getStock();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStoreId(), getProductId(), getStock());
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Stock(int storeId, int productId, int stock) {
        this.storeId = storeId;
        this.productId = productId;
        this.stock = stock;
    }

    public Stock() {
    }

    @Override
    public String toString() {
        return "Stock{" +
                "storeId=" + storeId +
                ", productId=" + productId +
                ", stock=" + stock +
                '}';
    }
}
