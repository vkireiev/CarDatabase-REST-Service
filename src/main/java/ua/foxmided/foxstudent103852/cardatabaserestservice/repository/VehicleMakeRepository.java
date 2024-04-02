package ua.foxmided.foxstudent103852.cardatabaserestservice.repository;

import org.springframework.stereotype.Repository;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;

@Repository
public interface VehicleMakeRepository extends CrudEntityRepository<VehicleMake, Long> {

}
