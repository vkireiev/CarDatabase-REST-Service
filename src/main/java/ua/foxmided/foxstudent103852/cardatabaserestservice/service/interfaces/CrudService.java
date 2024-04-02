package ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
public interface CrudService<T, U> extends ReadService<T, U> {

    T add(@NotNull @Valid T t);

    T update(@NotNull @Valid T t);

    boolean delete(@NotNull T t);

    boolean deleteById(@NotNull U id);

}
