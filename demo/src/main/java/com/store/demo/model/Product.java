package com.store.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "\"Product\"")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    private String name;
    private String type;
    private String info;
    private int cost;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isContinuity() {
        return continuity;
    }

    public void setContinuity(boolean continuity) {
        this.continuity = continuity;
    }

    public Product(int id, String name, String type, String info, int cost, boolean continuity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.info = info;
        this.cost = cost;
        this.continuity = continuity;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", info='" + info + '\'' +
                ", cost=" + cost +
                ", continuity=" + continuity +
                '}';
    }
}
