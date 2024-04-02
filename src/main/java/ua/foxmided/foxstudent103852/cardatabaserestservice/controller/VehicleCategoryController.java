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
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleCategory;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.ResponseEntityHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiSimpleResponse;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleCategoryService;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.SortHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.constants.VehicleCategoryConstants;

@RestController
@RequestMapping(path = "/api/v1/categories")
@Tag(name = "categories", description = "VehicleCategory API")
public class VehicleCategoryController
        extends DefaultIdentifiableEntityRestApiController<VehicleCategory, Long> {
    private static final Set<String> ENTITY_SORT_FIELDS = Set.of("id", "name");

    private final VehicleCategoryService categoryService;

    public VehicleCategoryController(VehicleCategoryService categoryService) {
        super(categoryService, VehicleCategory.class);
        this.categoryService = categoryService;
    }

    @Operation(summary = "get a VehicleCategory by its {categoryId}", description = "Returns a single VehicleCategory with {categoryId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = VehicleCategory.class))
            }, examples = @ExampleObject(value = VehicleCategoryConstants.GET_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid {categoryId} supplied"),
            @ApiResponse(responseCode = "404", description = "VehicleCategory not found")
    })
    @GetMapping("/{id}")
    public HttpEntity<RestApiSimpleResponse> getEntity(
            @Parameter(description = "{categoryId} of VehicleCategory to return", example = "2", required = true) @PathVariable(name = "id") @NotNull Long id) {

        return super.getEntityByIdImpl(id);
    }

    @Operation(summary = "get all VehicleCategories", description = "Returns page with VehicleCategories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = PageImpl.class))
            }, examples = @ExampleObject(value = VehicleCategoryConstants.GETALL_RESPONSE_JSON_EXAMPLE))),
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

    @Operation(summary = "add new VehicleCategory", description = "Add new VehicleCategory. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VehicleCategory.class), examples = @ExampleObject(value = VehicleCategoryConstants.POST_REQUEST_BODY_JSON_EXAMPLE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public HttpEntity<RestApiSimpleResponse> addEntity(
            @RequestBody @NotNull @Valid VehicleCategory newEntity) {

        return super.addEntityImpl(newEntity);
    }

    @Operation(summary = "update VehicleCategory by its {categoryId}", description = "Returns the updated VehicleCategory with {categoryId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VehicleCategory.class), examples = @ExampleObject(value = VehicleCategoryConstants.PUT_REQUEST_BODY_JSON_EXAMPLE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schemaProperties = {
                    @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                    @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "data", schema = @Schema(implementation = VehicleCategory.class))
            }, examples = @ExampleObject(value = VehicleCategoryConstants.PUT_RESPONSE_JSON_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "VehicleCategory not found")
    })
    @PutMapping("/{id}")
    public HttpEntity<RestApiSimpleResponse> updateEntity(
            @Parameter(description = "{categoryId} of VehicleCategory to update", example = "2", required = true) @PathVariable(name = "id") @NotNull Long id,
            @RequestBody @NotNull @Valid VehicleCategory updateEntity) {

        return super.updateEntityByIdImpl(id, updateEntity);
    }

    @Operation(summary = "delete VehicleCategory by its {categoryId}", description = "Delete VehicleCategory with {categoryId}. (This can only be done by the authenticated user with 'ADMIN' role)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(ref = "#/components/schemas/200ResponseSchema"))),
            @ApiResponse(responseCode = "400", description = "Invalid supplied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public HttpEntity<RestApiSimpleResponse> deleteEntity(
            @Parameter(description = "{categoryId} of VehicleCategory to delete", example = "2", required = true) @PathVariable(name = "id") @NotNull Long id) {

        return super.deleteEntityByIdImpl(id);
    }

}
