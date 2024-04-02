package ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces;

import org.springframework.validation.annotation.Validated;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleCategory;

@Validated
public interface VehicleCategoryService extends CrudService<VehicleCategory, Long> {

}
