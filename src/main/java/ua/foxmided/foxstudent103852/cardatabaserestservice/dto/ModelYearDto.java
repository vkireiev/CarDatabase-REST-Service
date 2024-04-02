package ua.foxmided.foxstudent103852.cardatabaserestservice.dto;

import java.time.Year;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
@ToString
@Schema(title = "ModelYear")
public class ModelYearDto {

    @NotNull
    private Long id;

    @NotNull
    private Year year;

    public static ModelYearDto from(@Valid @NotNull ModelYear modelYear) {
        return new ModelYearDto(
                modelYear.getId(),
                modelYear.getYear());
    }

    public static ModelYear to(@Valid @NotNull ModelYearDto modelYearDto) {
        return new ModelYear(
                modelYearDto.getId(),
                modelYearDto.getYear());
    }

}
