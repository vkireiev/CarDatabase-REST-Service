package ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces;

import org.springframework.validation.annotation.Validated;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;

@Validated
public interface VehicleMakeService extends CrudService<VehicleMake, Long> {

}
