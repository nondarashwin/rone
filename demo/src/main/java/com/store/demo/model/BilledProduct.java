package com.store.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "BillingProduct")
@IdClass(BilledProduct.class)
public class BilledProduct implements Serializable {
    @Id
    //@PrimaryKeyJoinColumn
    //@JsonView(Views.Internal.class)
    @JsonIgnore
    private int billId;
    @Id
    // @PrimaryKeyJoinColumn
    //@JsonView(Views.Public.class)
    private int productId;
    //@JsonView(Views.Public.class)
    private int cost;
    private int quantity;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BilledProduct)) return false;
        BilledProduct that = (BilledProduct) o;
        return getBillId() == that.getBillId() &&
                getProductId() == that.getProductId() &&
                getCost() == that.getCost() &&
                getQuantity() == that.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBillId(), getProductId(), getCost(), getQuantity());
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BilledProduct(int billId, int productId, int cost, int quantity) {
        this.billId = billId;
        this.productId = productId;
        this.cost = cost;
        this.quantity = quantity;
    }

    public BilledProduct() {
    }

    @Override
    public String toString() {
        return "BilledProduct{" +
                "billId=" + billId +
                ", productId=" + productId +
                ", cost=" + cost +
                ", quantity=" + quantity +
                '}';
    }
}
