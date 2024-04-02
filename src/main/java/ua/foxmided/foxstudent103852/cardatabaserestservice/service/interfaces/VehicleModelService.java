package ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;

@Validated
public interface VehicleModelService extends CrudService<VehicleModel, Long> {

    Page<VehicleModel> findAllByMake(@NotNull VehicleMake make, @NotNull Pageable pageable);

    VehicleModel modifyYears(@NotNull Long modelId, @NotNull @Valid ModelYear modelYear, boolean isRemove);

}
