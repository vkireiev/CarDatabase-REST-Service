package ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;

@Validated
public interface VehicleService extends CrudService<Vehicle, Long> {

    boolean existsByModelAndYear(@NotNull @Valid VehicleModel model, @NotNull @Valid ModelYear year);

    Page<Vehicle> findAll(@NotNull Specification<Vehicle> specification, @NotNull Pageable pageable);

}
