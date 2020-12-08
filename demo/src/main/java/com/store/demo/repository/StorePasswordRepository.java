package com.store.demo.repository;

import com.store.demo.model.StorePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorePasswordRepository  extends JpaRepository<StorePassword,Integer> {
}
