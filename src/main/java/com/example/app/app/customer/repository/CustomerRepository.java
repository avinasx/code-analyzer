package com.example.app.app.customer.repository;

import com.example.app.app.customer.domain.entity.CustomerEntity;
import com.example.app.app.customer.repository.custom.CustomCustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>, CustomCustomerRepository {
}
