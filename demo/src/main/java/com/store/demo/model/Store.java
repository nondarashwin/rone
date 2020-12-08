package com.store.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "Stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String address;
    private boolean continuity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isContinuity() {
        return continuity;
    }

    public void setContinuity(boolean continuity) {
        this.continuity = continuity;
    }

    public Store(int id, String name, String address, boolean continuity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.continuity = continuity;
    }

    public Store() {
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", continuity=" + continuity +
                '}';
    }
}
