package ua.foxmided.foxstudent103852.cardatabaserestservice.repository;

import org.springframework.stereotype.Repository;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;

@Repository
public interface VehicleRepository extends CrudEntityRepository<Vehicle, Long> {

    boolean existsByModelAndYear(VehicleModel model, ModelYear year);

}
