package com.example.app.repository;

import com.example.app.model.entity.StaffEntity;
import com.example.app.repository.custom.CustomStaffRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Integer>, CustomStaffRepository {
}
