package ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
public interface ReadService<T, U> {

    Optional<T> get(@NotNull U id);

    Page<T> getAll(@NotNull Pageable pageable);

    long count();

}
