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
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.ResponseEntityHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiSimpleResponse;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleMakeService;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.SortHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.constants.VehicleMakeConstants;

@RestController
@RequestMapping(path = "/api/v1/makes")
@Tag(name = "makes", description = "VehicleMake API")
public class VehicleMakeController
        extends DefaultIdentifiableEntityRestApiController<VehicleMake, Long> {
    private static final Set<String> ENTITY_SORT_FIELDS = Set.of("id", "name");

    private final VehicleMakeService makeService;

    public VehicleMakeController(VehicleMakeService makeService) {
        super(makeService, VehicleMake.class);
        this.makeService = makeService;
    }

    @Operation(summary = "get a VehicleMake by its {makeId}", description = "Returns a single VehicleMake with {makeId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = VehicleMake.class))
            }, examples = @ExampleObject(value = VehicleMakeConstants.GET_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid {makeId} supplied"),
            @ApiResponse(responseCode = "404", description = "VehicleMake not found")
    })
    @GetMapping("/{id}")
    public HttpEntity<RestApiSimpleResponse> getEntity(
            @Parameter(description = "{makeId} of VehicleMake to return", example = "3", required = true) @PathVariable(name = "id") @NotNull Long id) {

        return super.getEntityByIdImpl(id);
    }

    @Operation(summary = "get all VehicleMakes", description = "Returns page with VehicleMakes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = PageImpl.class))
            }, examples = @ExampleObject(value = VehicleMakeConstants.GETALL_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied")
    })
    @GetMapping
    public HttpEntity<RestApiSimpleResponse> getAllEntities(
            @ParameterObject @PageableDefault(page = 0, size = 5) @SortDefault(sort = "id", direction = Direction.ASC, caseSensitive = false) @NotNull Pageable pageable) {

        if (!SortHelper.isValidSortProperties(pageable.getSort(), ENTITY_SORT_FIELDS)) {
            return ResponseEntityHelper.of(HttpStatus.BAD_REQUEST);
        }

        return super.getAllEntitiesImpl(pageable);
    }

    @Operation(summary = "add new VehicleMake", description = "Add new VehicleMake. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VehicleMake.class), examples = @ExampleObject(value = VehicleMakeConstants.POST_REQUEST_JSON_BODY_EXAMPLE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public HttpEntity<RestApiSimpleResponse> addEntity(
            @RequestBody @NotNull @Valid VehicleMake newEntity) {

        return super.addEntityImpl(newEntity);
    }

    @Operation(summary = "update VehicleMake by its {makeId}", description = "Returns the updated VehicleMake with {makeId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VehicleMake.class), examples = @ExampleObject(value = VehicleMakeConstants.PUT_REQUEST_JSON_BODY_EXAMPLE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = VehicleMake.class))
            }, examples = @ExampleObject(value = VehicleMakeConstants.PUT_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "VehicleMake not found")
    })
    @PutMapping("/{id}")
    public HttpEntity<RestApiSimpleResponse> updateEntity(
            @Parameter(description = "{makeId} of VehicleMake to update", example = "3", required = true) @PathVariable(name = "id") @NotNull Long id,
            @RequestBody @NotNull @Valid VehicleMake updateEntity) {

        return super.updateEntityByIdImpl(id, updateEntity);
    }

    @Operation(summary = "delete VehicleMake by its {makeId}", description = "Delete VehicleMake with {makeId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(ref = "#/components/schemas/200ResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public HttpEntity<RestApiSimpleResponse> deleteEntity(
            @Parameter(description = "{makeId} of VehicleMake to delete", example = "3", required = true) @PathVariable(name = "id") @NotNull Long id) {

        return super.deleteEntityByIdImpl(id);
    }

}
