package com.store.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="StorePassword")
public class StorePassword {
@Id
private int storeId;
private String mailId;
private String password;

    public StorePassword(int storeId, String mailId, String password) {
        this.storeId = storeId;
        this.mailId = mailId;
        this.password = password;
    }

    public StorePassword() {

    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "StorePassword{" +
                "storeId=" + storeId +
                ", mailId='" + mailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StorePassword)) return false;
        StorePassword that = (StorePassword) o;
        return getStoreId() == that.getStoreId() &&
                getMailId().equals(that.getMailId()) &&
                getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStoreId(), getMailId(), getPassword());
    }
}
