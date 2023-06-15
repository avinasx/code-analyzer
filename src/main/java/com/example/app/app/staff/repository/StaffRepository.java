package com.example.app.app.staff.repository;

import com.example.app.app.staff.domain.entity.StaffEntity;
import com.example.app.app.staff.repository.custom.CustomStaffRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Integer>, CustomStaffRepository {
}
