package com.example.app.app.rental.repository.custom;

import com.example.app.app.rental.domain.entity.RentalEntity;

public interface CustomRentalRepository {
    RentalEntity findRentedDvdRentalId(Integer customerId, Integer inventoryId);
}
