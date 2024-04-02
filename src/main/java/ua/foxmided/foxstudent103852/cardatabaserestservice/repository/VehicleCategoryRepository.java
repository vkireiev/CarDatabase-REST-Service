package ua.foxmided.foxstudent103852.cardatabaserestservice.repository;

import org.springframework.stereotype.Repository;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleCategory;

@Repository
public interface VehicleCategoryRepository extends CrudEntityRepository<VehicleCategory, Long> {

}
