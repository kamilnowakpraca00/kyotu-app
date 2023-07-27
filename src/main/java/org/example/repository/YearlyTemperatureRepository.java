package org.example.repository;

import java.util.List;
import org.example.entity.YearlyTemperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyTemperatureRepository extends JpaRepository<YearlyTemperature, Long> {
    List<YearlyTemperature> findByCity(String city);
}
