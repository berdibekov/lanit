package com.berdibekov.repository;

import com.berdibekov.domain.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {
    Collection<Car> findAllByOwnerId(Long ownerId);

    @Query(value = "SELECT COUNT(DISTINCT vendor) FROM car GROUP BY vendor", nativeQuery = true)
    Optional<Long> countDistinctVendors();
}
