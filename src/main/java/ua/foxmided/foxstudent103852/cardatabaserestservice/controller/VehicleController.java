package ua.foxmided.foxstudent103852.cardatabaserestservice.controller;

import java.time.Year;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.ResponseEntityHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiSimpleResponse;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleService;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.SortHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.constants.VehicleConstants;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.specification.SearchCriteria;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.specification.VehicleSearchSpecification;

@RestController
@RequestMapping(path = "/api/v1/vehicles")
@Tag(name = "vehicles", description = "Vehicle API")
public class VehicleController
        extends DefaultIdentifiableEntityRestApiController<Vehicle, Long> {
    private static final Set<String> ENTITY_SORT_FIELDS = Set.of("id", "year", "model", "make");

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        super(vehicleService, Vehicle.class);
        this.vehicleService = vehicleService;
    }

    @Operation(summary = "get a Vehicle by its {vehicleId}", description = "Returns a single Vehicle with {vehicleId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = Vehicle.class))
            }, examples = @ExampleObject(value = VehicleConstants.GET_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid {vehicleId} supplied"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @GetMapping("/{id}")
    public HttpEntity<RestApiSimpleResponse> getEntity(
            @Parameter(description = "{vehicleId} of Vehicle to return", example = "7", required = true) @PathVariable(name = "id") @NotNull Long id) {

        return super.getEntityByIdImpl(id);
    }

    @Operation(summary = "get all Vehicles according to the search criteria", description = "Returns page with Vehicles according to the search criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = PageImpl.class))
            }, examples = @ExampleObject(value = VehicleConstants.FINDALL_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied")
    })
    @GetMapping
    public HttpEntity<RestApiSimpleResponse> findAllEntities(
            @ParameterObject @PageableDefault(page = 0, size = 5) @SortDefault(sort = "id", direction = Direction.ASC, caseSensitive = false) @NotNull Pageable pageable,
            @Parameter(description = "{make} of Vehicle to return", example = "Audi", required = false) @RequestParam(name = "make", required = false, defaultValue = "") String make,
            @Parameter(description = "{model} of Vehicle to return", example = "A5", required = false) @RequestParam(name = "model", required = false, defaultValue = "") String model,
            @Parameter(description = "{year} of Vehicle to return", example = "2020", required = false) @RequestParam(name = "year", required = false) Integer year,
            @Parameter(description = "{minYear} of Vehicle to return", example = "2019", required = false) @RequestParam(name = "minYear", required = false) Integer minYear,
            @Parameter(description = "{maxYear} of Vehicle to return", example = "2022", required = false) @RequestParam(name = "maxYear", required = false) Integer maxYear) {

        if (!SortHelper.isValidSortProperties(pageable.getSort(), ENTITY_SORT_FIELDS)) {
            return ResponseEntityHelper.of(HttpStatus.BAD_REQUEST);
        }
        List<Specification<Vehicle>> conditions = new LinkedList<>();
        conditions.addAll(parseSortParametersToSpecification(pageable.getSort().toList()));

        if (!make.isBlank()) {
            conditions.add(new VehicleSearchSpecification(
                    new SearchCriteria("make", "==", make)));
        }
        if (!model.isBlank()) {
            conditions.add(new VehicleSearchSpecification(
                    new SearchCriteria("model", "==", model)));
        }
        if (year != null) {
            conditions.add(new VehicleSearchSpecification(
                    new SearchCriteria("year", "==", Year.of(year))));
        }
        if (minYear != null) {
            conditions.add(new VehicleSearchSpecification(
                    new SearchCriteria("year", ">=", Year.of(minYear))));
        }
        if (maxYear != null) {
            conditions.add(new VehicleSearchSpecification(
                    new SearchCriteria("year", "<=", Year.of(maxYear))));
        }

        return ResponseEntityHelper.of(HttpStatus.OK,
                vehicleService.findAll(
                        Specification.allOf(conditions),
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())));
    }

    @Operation(summary = "add new Vehicle", description = "Add new Vehicle. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class), examples = @ExampleObject(value = VehicleConstants.POST_REQUEST_BODY_JSON_EXAMPLE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public HttpEntity<RestApiSimpleResponse> addEntity(
            @RequestBody @NotNull @Valid Vehicle newEntity) {

        return super.addEntityImpl(newEntity);
    }

    @Operation(summary = "update Vehicle by its {vehicleId}", description = "Returns the updated Vehicle with {vehicleId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class), examples = @ExampleObject(value = VehicleConstants.PUT_REQUEST_BODY_JSON_EXAMPLE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = Vehicle.class))
            }, examples = @ExampleObject(value = VehicleConstants.PUT_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PutMapping("/{id}")
    public HttpEntity<RestApiSimpleResponse> updateEntity(
            @Parameter(description = "{vehicleId} of Vehicle to update", example = "7", required = true) @PathVariable(name = "id") @NotNull Long id,
            @RequestBody @NotNull @Valid Vehicle updateEntity) {

        return super.updateEntityByIdImpl(id, updateEntity);
    }

    @Operation(summary = "delete Vehicle by its {vehicleId}", description = "Delete Vehicle with {vehicleId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(ref = "#/components/schemas/200ResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public HttpEntity<RestApiSimpleResponse> deleteEntity(
            @Parameter(description = "{vehicleId} of Vehicle to delete", example = "7", required = true) @PathVariable(name = "id") @NotNull Long id) {

        return super.deleteEntityByIdImpl(id);
    }

    private Collection<? extends Specification<Vehicle>> parseSortParametersToSpecification(List<Order> listOrders) {
        return listOrders.stream()
                .map(order -> new VehicleSearchSpecification(
                        new SearchCriteria(
                                order.getProperty(),
                                order.isAscending())))
                .toList();
    }

}
