package com.store.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Billing")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int storeId;
    private int totalAmount;
    @Transient
    private String emailId;
    @Transient
    private List<BilledProduct> billedProducts=new ArrayList<>();

    public String getEmailId() {
        return emailId;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", totalAmount=" + totalAmount +
                ", emailId='" + emailId + '\'' +
                ", billedProducts=" + billedProducts +
                '}';
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill)) return false;
        Bill bill = (Bill) o;
        return getId() == bill.getId() &&
                getStoreId() == bill.getStoreId() &&
                getTotalAmount() == bill.getTotalAmount() &&
                emailId.equals(bill.emailId) &&
                getBilledProducts().equals(bill.getBilledProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStoreId(), getTotalAmount(), emailId, getBilledProducts());
    }

    public List<BilledProduct> getBilledProducts() {
        return billedProducts;
    }

    public void setBilledProducts(List<BilledProduct> billedProducts) {
        this.billedProducts = billedProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Bill(int id, int storeId, int totalAmount, String emailId, List<BilledProduct> billedProducts) {
        this.id = id;
        this.storeId = storeId;
        this.totalAmount = totalAmount;
        this.emailId = emailId;
        this.billedProducts = billedProducts;
    }

    public Bill() {
    }

}
