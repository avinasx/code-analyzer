package com.example.app.repository;

import com.example.app.model.entity.CustomerEntity;
import com.example.app.repository.custom.CustomCustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>, CustomCustomerRepository {
}
