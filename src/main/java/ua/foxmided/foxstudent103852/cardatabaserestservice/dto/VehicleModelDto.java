package ua.foxmided.foxstudent103852.cardatabaserestservice.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
@ToString
@Schema(title = "VehicleModel")
public class VehicleModelDto {

    @Hidden
    private Long id;

    @NotBlank
    private String name;

    @Hidden
    private VehicleMake make;

    @NotNull
    private Set<@NotNull @Valid ModelYearDto> years = new HashSet<>();

    public static VehicleModelDto from(@Valid @NotNull VehicleModel vehicleModel) {
        return new VehicleModelDto(
                vehicleModel.getId(),
                vehicleModel.getName(),
                vehicleModel.getMake(),
                vehicleModel.getYears().stream()
                        .map(ModelYearDto::from)
                        .collect(Collectors.toSet()));
    }

    public static VehicleModel to(@Valid @NotNull VehicleModelDto vehicleModelDto) {
        return new VehicleModel(
                vehicleModelDto.getId(),
                vehicleModelDto.getName(),
                vehicleModelDto.getMake(),
                vehicleModelDto.getYears().stream()
                        .map(ModelYearDto::to)
                        .collect(Collectors.toSet()));
    }

}
