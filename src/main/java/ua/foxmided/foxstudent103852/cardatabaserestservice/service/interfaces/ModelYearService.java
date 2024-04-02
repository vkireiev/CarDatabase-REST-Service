package ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces;

import org.springframework.validation.annotation.Validated;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;

@Validated
public interface ModelYearService extends CrudService<ModelYear, Long> {

}
