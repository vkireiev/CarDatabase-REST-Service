package ua.foxmided.foxstudent103852.cardatabaserestservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;

@Repository
public interface VehicleModelRepository extends CrudEntityRepository<VehicleModel, Long> {

    Page<VehicleModel> findAllByMake(VehicleMake make, Pageable pageable);

}
