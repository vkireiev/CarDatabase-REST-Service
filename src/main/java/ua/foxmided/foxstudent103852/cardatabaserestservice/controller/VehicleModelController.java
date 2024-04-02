package ua.foxmided.foxstudent103852.cardatabaserestservice.controller;

import java.util.Set;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ua.foxmided.foxstudent103852.cardatabaserestservice.dto.VehicleModelDto;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityAddDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityUpdateDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.ResponseEntityHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiSimpleResponse;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.ModelYearService;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleMakeService;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleModelService;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.SortHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.constants.VehicleModelConstants;

@RestController
@RequestMapping(path = "/api/v1")
@Tag(name = "models", description = "VehicleModel API")
public class VehicleModelController
        extends DefaultIdentifiableEntityRestApiController<VehicleModel, Long> {
    private static final Set<String> ENTITY_SORT_FIELDS = Set.of("id", "name");

    private final VehicleModelService vehicleModelService;
    private final VehicleMakeService vehicleMakeService;
    private final ModelYearService modelYearService;

    public VehicleModelController(VehicleModelService modelService, VehicleMakeService vehicleMakeService,
            ModelYearService modelYearService) {
        super(modelService, VehicleModel.class);
        this.vehicleModelService = modelService;
        this.vehicleMakeService = vehicleMakeService;
        this.modelYearService = modelYearService;
    }

    @Operation(summary = "get a VehicleModel by its {modelId}", description = "Returns a single VehicleModel with {modelId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = VehicleModel.class))
            }, examples = @ExampleObject(value = VehicleModelConstants.GET_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid {modelId} supplied"),
            @ApiResponse(responseCode = "404", description = "VehicleModel not found")
    })
    @GetMapping("/models/{modelId}")
    public HttpEntity<RestApiSimpleResponse> getEntity(
            @Parameter(description = "{modelId} of VehicleModel to return", example = "1", required = true) @PathVariable(name = "modelId") @NotNull Long modelId) {

        return super.getEntityByIdImpl(modelId);
    }

    @Operation(summary = "get all VehicleModels", description = "Returns page with VehicleModels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = PageImpl.class))
            }, examples = @ExampleObject(value = VehicleModelConstants.GETALL_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied")
    })
    @GetMapping("/models")
    public HttpEntity<RestApiSimpleResponse> getAllEntities(
            @ParameterObject @PageableDefault(page = 0, size = 5) @SortDefault(sort = "id", direction = Direction.ASC, caseSensitive = false) @NotNull Pageable pageable) {

        if (!SortHelper.isValidSortProperties(pageable.getSort(), ENTITY_SORT_FIELDS)) {
            return ResponseEntityHelper.of(HttpStatus.BAD_REQUEST);
        }

        return super.getAllEntitiesImpl(pageable);
    }

    @Operation(summary = "get all VehicleModels that have VehicleMake with {modelId}", description = "Returns page with VehicleModels that have VehicleMake with {modelId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = PageImpl.class))
            }, examples = @ExampleObject(value = VehicleModelConstants.GETALL_BY_MAKE_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied")
    })
    @GetMapping("/makes/{makeId}/models")
    public HttpEntity<RestApiSimpleResponse> findAllByMakeEntities(
            @Parameter(description = "{makeId} of VehicleMake", example = "5", required = true) @PathVariable(name = "makeId") @NotNull Long makeId,
            @ParameterObject @PageableDefault(page = 0, size = 5) @SortDefault(sort = "id", direction = Direction.ASC, caseSensitive = false) @NotNull Pageable pageable) {

        VehicleMake searchMake = returnEntityOrThrowEntityNotFoundException(
                vehicleMakeService.get(makeId),
                "VehicleMake (ID=" + makeId + ") not found");

        if (!SortHelper.isValidSortProperties(pageable.getSort(), ENTITY_SORT_FIELDS)) {
            return ResponseEntityHelper.of(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntityHelper.of(HttpStatus.OK,
                vehicleModelService.findAllByMake(searchMake, pageable));
    }

    @Operation(summary = "add new VehicleModel that have VehicleMake with {modelId}", description = "Add new VehicleModel that have VehicleMake with {modelId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VehicleModelDto.class), examples = @ExampleObject(value = VehicleModelConstants.POST_BY_MAKE_REQUEST_BODY_JSON_EXAMPLE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping("/makes/{makeId}/models")
    public HttpEntity<RestApiSimpleResponse> addEntity(
            @Parameter(description = "{makeId} of VehicleMake", example = "3", required = true) @PathVariable(name = "makeId") @NotNull Long makeId,
            @RequestBody @NotNull @Valid VehicleModelDto newEntityDto) {

        try {
            newEntityDto.setMake(
                    returnEntityOrThrowEntityNotFoundException(
                            vehicleMakeService.get(makeId),
                            "VehicleMake (ID=" + makeId + ") not found"));
            vehicleModelService.add(VehicleModelDto.to(newEntityDto));
        } catch (EntityAddDataIntegrityViolationException e) {
            throw new EntityDataIntegrityViolationException(e.getMessage(), e);
        }

        return ResponseEntityHelper.of(HttpStatus.CREATED);
    }

    @Operation(summary = "update VehicleModel by its {modelId}", description = "Returns the updated VehicleModel with {modelId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VehicleModel.class), examples = @ExampleObject(value = VehicleModelConstants.PUT_REQUEST_BODY_JSON_EXAMPLE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = VehicleModel.class))
            }, examples = @ExampleObject(value = VehicleModelConstants.PUT_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "VehicleModel not found")
    })
    @PutMapping("/models/{modelId}")
    public HttpEntity<RestApiSimpleResponse> updateEntity(
            @Parameter(description = "{modelId} of VehicleModel to return", example = "5", required = true) @PathVariable(name = "modelId") @NotNull Long modelId,
            @RequestBody @NotNull @Valid VehicleModel updateEntity) {

        return super.updateEntityByIdImpl(modelId, updateEntity);
    }

    @Operation(summary = "delete VehicleModel by its {modelId}", description = "Delete VehicleModel with {modelId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(ref = "#/components/schemas/200ResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/models/{modelId}")
    public HttpEntity<RestApiSimpleResponse> deleteEntity(
            @Parameter(description = "{modelId} of VehicleModel to delete", example = "1", required = true) @PathVariable(name = "modelId") @NotNull Long modelId) {

        return super.deleteEntityByIdImpl(modelId);
    }

    @Operation(summary = "get VehicleModel.years by its {modelId}", description = "Returns VehicleModel.years of VehicleModel with {modelId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", array = @ArraySchema(schema = @Schema(implementation = ModelYear.class)))
            }, examples = @ExampleObject(value = VehicleModelConstants.GETALL_YEARS_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid {modelId} supplied"),
            @ApiResponse(responseCode = "404", description = "VehicleModel not found")
    })
    @GetMapping("/models/{modelId}/years")
    public HttpEntity<RestApiSimpleResponse> getEntityAllYears(
            @Parameter(description = "{modelId} of VehicleModel", example = "1", required = true) @PathVariable(name = "modelId") @NotNull Long modelId) {

        VehicleModel vehicleModel = returnEntityOrThrowEntityNotFoundException(
                vehicleModelService.get(modelId),
                "VehicleModel (ID=" + modelId + ") not found");

        return ResponseEntityHelper.of(HttpStatus.OK,
                vehicleModel.getYears());
    }

    @Operation(summary = "add ModelYear with {yearId} to VehicleModel.years with {modelId}", description = "Add ModelYear with {yearId} to VehicleModel.years with {modelId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", array = @ArraySchema(schema = @Schema(implementation = ModelYear.class)))
            }, examples = @ExampleObject(value = VehicleModelConstants.POST_YEAR_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "VehicleModel not found")
    })
    @PostMapping("/models/{modelId}/years/{yearId}")
    public HttpEntity<RestApiSimpleResponse> addModelYearToEntityYears(
            @Parameter(description = "{modelId} of VehicleModel", example = "1", required = true) @PathVariable(name = "modelId") @NotNull Long modelId,
            @Parameter(description = "{yearId} of ModelYear", example = "3", required = true) @PathVariable(name = "yearId") @NotNull Long yearId) {

        ModelYear modelYear = returnEntityOrThrowEntityNotFoundException(
                modelYearService.get(yearId),
                "ModelYear (ID=" + yearId + ") not found");

        VehicleModel updatedEntity = new VehicleModel();
        try {
            updatedEntity = vehicleModelService.modifyYears(modelId, modelYear, false);
        } catch (EntityUpdateDataIntegrityViolationException e) {
            throw new EntityDataIntegrityViolationException(e.getMessage(), e);
        }

        return ResponseEntityHelper.of(HttpStatus.OK,
                updatedEntity.getYears());
    }

    @Operation(summary = "Remove ModelYear with {yearId} from VehicleModel.years with {modelId}", description = "Remove ModelYear with {yearId} to VehicleModel.years with {modelId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", array = @ArraySchema(schema = @Schema(implementation = ModelYear.class)))
            }, examples = @ExampleObject(value = VehicleModelConstants.DELETE_YEAR_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "VehicleModel not found")
    })
    @DeleteMapping("/models/{modelId}/years/{yearId}")
    public HttpEntity<RestApiSimpleResponse> removeModelYearFromEntityYears(
            @Parameter(description = "{modelId} of VehicleModel", example = "1", required = true) @PathVariable(name = "modelId") @NotNull Long modelId,
            @Parameter(description = "{yearId} of ModelYear", example = "3", required = true) @PathVariable(name = "yearId") @NotNull Long yearId) {

        ModelYear modelYear = returnEntityOrThrowEntityNotFoundException(
                modelYearService.get(yearId),
                "ModelYear (ID=" + yearId + ") not found");

        VehicleModel updatedEntity = new VehicleModel();
        try {
            updatedEntity = vehicleModelService.modifyYears(modelId, modelYear, true);
        } catch (EntityUpdateDataIntegrityViolationException e) {
            throw new EntityDataIntegrityViolationException(e.getMessage(), e);
        }

        return ResponseEntityHelper.of(HttpStatus.OK,
                updatedEntity.getYears());
    }

}
