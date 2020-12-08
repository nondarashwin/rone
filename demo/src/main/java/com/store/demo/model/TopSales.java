package com.store.demo.model;

import java.util.Objects;

public class TopSales {
    String name;
    long totalAmount;

    public TopSales(String name, long totalAmount) {
        this.name = name;
        this.totalAmount = totalAmount;
    }
    public TopSales(){

    }

    @Override
    public String toString() {
        return "TopSales{" +
                "Name='" + name + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopSales)) return false;
        TopSales topSales = (TopSales) o;
        return getTotalAmount() == topSales.getTotalAmount() &&
                getName().equals(topSales.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getTotalAmount());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
