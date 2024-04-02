package ua.foxmided.foxstudent103852.cardatabaserestservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityAddDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityNotFoundException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityUpdateDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiDataResponse;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.VehicleServiceImpl;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicle;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest extends KeycloakTestcontainersConfigurer {
    private static final Vehicle GET_ENTITY = ConstantsTestVehicle.VEHICLE_VALID_1;
    private static final Vehicle NEW_ENTITY = ConstantsTestVehicle.newValidVehicle();
    private static final Vehicle UPD_ENTITY = ConstantsTestVehicle.VEHICLE_VALID_1;
    private static final Vehicle DEL_ENTITY = ConstantsTestVehicle.VEHICLE_VALID_2;

    private static ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
            .registerModule(new JavaTimeModule());

    @Autowired
    MockMvc mvc;

    @MockBean
    VehicleServiceImpl vehicleServiceMock;

    @ParameterizedTest
    @MethodSource("provideUnauthorizedScenario")
    void WhenCalledWithUnauthorizedScenario_ShouldReturn401Code(
            RequestBuilder parameterizedUrlAndHttpMethod) throws Exception {

        mvc.perform(parameterizedUrlAndHttpMethod)
                .andExpect(status().is(401))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideForbiddenScenario")
    void WhenCalledWithForbiddenScenario_ShouldReturn403Code(
            RequestBuilder parameterizedUrlAndHttpMethod) throws Exception {

        mvc.perform(parameterizedUrlAndHttpMethod)
                .andExpect(status().is(403))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.message").value("FORBIDDEN"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArgumentInUrlPath")
    void WhenCalledWithInvalidArgument_ShouldReturn400Code(
            RequestBuilder parameterizedUrlAndHttpMethod) throws Exception {

        mvc.perform(parameterizedUrlAndHttpMethod)
                .andExpect(status().is(400))
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidJsonBody")
    void WhenCalledWithInvalidJsonBody_ShouldReturn400Code(
            RequestBuilder parameterizedUrlAndHttpMethod) throws Exception {

        mvc.perform(parameterizedUrlAndHttpMethod)
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void getEntity_WhenCalledAndThrowsDataProcessingException_ShouldReturn500Code() throws Exception {

        Mockito.when(vehicleServiceMock
                .get(GET_ENTITY.getId()))
                .thenThrow(new DataProcessingException("DataProcessingException"));

        mvc.perform(get("/api/v1/vehicles/{vehicleId}", GET_ENTITY.getId()))
                .andExpect(status().is(500))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataProcessingException))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleServiceMock, Mockito.times(1)).get(GET_ENTITY.getId());
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void getEntity_WhenCalledAndNotExistVehicleWithSuchId_ShouldReturn404Code() throws Exception {
        Vehicle resultMock = ConstantsTestVehicle.VEHICLE_VALID_1;

        Mockito.when(vehicleServiceMock.get(resultMock.getId()))
                .thenReturn(Optional.empty());

        mvc.perform(get("/api/v1/vehicles/{vehicleId}", resultMock.getId()))
                .andExpect(status().is(404))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage())
                        .containsIgnoringCase("Vehicle")
                        .containsIgnoringCase(String.valueOf(GET_ENTITY.getId()))
                        .containsIgnoringCase("not found"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("NOT_FOUND"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleServiceMock, Mockito.times(1)).get(resultMock.getId());
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void getEntity_WhenCalledAndExistVehicleWithSuchId_ShouldReturn200CodeAndEntity()
            throws Exception {
        Vehicle resultMock = ConstantsTestVehicle.VEHICLE_VALID_1;

        Mockito.when(vehicleServiceMock
                .get(resultMock.getId()))
                .thenReturn(Optional.of(resultMock));

        MvcResult resultController = mvc.perform(get("/api/v1/vehicles/{vehicleId}", resultMock.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andReturn();

        checkDataFieldInControllerResults(resultController, resultMock);

        Mockito.verify(vehicleServiceMock, Mockito.times(1)).get(resultMock.getId());
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void findAllEntities_WhenCalledWithInvalidSortArgument_ShouldReturn400Code() throws Exception {

        mvc.perform(get("/api/v1/vehicles?sort=invalid,asc"))
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void findAllEntities_WhenCalledAndThrowsDataProcessingException_ShouldReturn500Code() throws Exception {
        int page = 0;
        int size = 5;
        String sort = "id,desc";
        Year searchYear = ConstantsTestModelYear.YEAR_VALID_3.getYear();

        Mockito.when(vehicleServiceMock
                .findAll(
                        Mockito.any(Specification.class),
                        Mockito.any(Pageable.class)))
                .thenThrow(new DataProcessingException("DataProcessingException"));

        mvc.perform(get("/api/v1/vehicles?year={year}&page={page}&size={size}&sort={sort}",
                searchYear, page, size, sort))
                .andExpect(status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleServiceMock, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void findAllEntities_WhenCalledAndNotExistsVehicles_ShouldReturn200CodeAndEmptyListOfVehiclesWithPaginationInfo()
            throws Exception {
        int page = 0;
        int size = 5;
        String sort = "id,desc";
        Year searchYear = ConstantsTestModelYear.YEAR_VALID_3.getYear();
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));

        Mockito.when(vehicleServiceMock
                .findAll(
                        Mockito.any(Specification.class),
                        Mockito.any(Pageable.class)))
                .thenReturn(Page.empty(pageable));

        mvc.perform(get("/api/v1/vehicles?year={year}&page={page}&size={size}&sort={sort}",
                searchYear, page, size, sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())

                .andExpect(jsonPath("$.data.content").isEmpty())
                .andExpect(jsonPath("$.data.empty").value(true))
                .andExpect(jsonPath("$.data.pageable").isNotEmpty())
                .andExpect(jsonPath("$.data.pageable.paged").value(true))

                .andExpect(jsonPath("$.data.sort").isNotEmpty())
                .andExpect(jsonPath("$.data.sort.sorted").value(true))

                .andExpect(jsonPath("$.data.totalElements").value(0))
                .andExpect(jsonPath("$.data.numberOfElements").value(0))
                .andExpect(jsonPath("$.data.totalPages").value(0));

        Mockito.verify(vehicleServiceMock, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void findAllEntities_WhenCalledAndExistsVehicles_ShouldReturn200CodeAndPagedAndSortedListOfVehiclesWithPaginationInfo()
            throws Exception {
        int page = 1;
        int size = 3;
        String sort = "id,desc";
        Year searchYear = ConstantsTestModelYear.YEAR_VALID_3.getYear();
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));
        List<Vehicle> filteredEntities = ConstantsTestVehicle.getAllTestVehicles().stream()
                .filter(vehicle -> vehicle.getYear().getYear().equals(searchYear))
                .sorted((entity1, entity2) -> entity2.getId().compareTo(entity1.getId()))
                .collect(Collectors.toList());
        assertTrue(filteredEntities.size() > (size * page));
        assertEquals(16L, filteredEntities.size());

        List<Vehicle> resultMockito = filteredEntities.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        assertEquals(size, resultMockito.size());

        Mockito.when(vehicleServiceMock
                .findAll(
                        Mockito.any(Specification.class),
                        Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(resultMockito, pageable, filteredEntities.size()));

        MvcResult resultController = mvc.perform(get("/api/v1/vehicles?year={year}&page={page}&size={size}&sort={sort}",
                searchYear, page, size, sort))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())

                .andExpect(jsonPath("$.data.content").isNotEmpty())
                .andExpect(jsonPath("$.data.empty").value(false))
                .andExpect(jsonPath("$.data.pageable").isNotEmpty())
                .andExpect(jsonPath("$.data.pageable.paged").value(true))

                .andExpect(jsonPath("$.data.sort").isNotEmpty())
                .andExpect(jsonPath("$.data.sort.sorted").value(true))

                .andExpect(jsonPath("$.data.totalElements").value(16))
                .andExpect(jsonPath("$.data.numberOfElements").value(3))
                .andExpect(jsonPath("$.data.totalPages").value(6))
                .andReturn();

        checkDataFieldInControllerResults(resultController, "content", resultMockito);

        Mockito.verify(vehicleServiceMock, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForAddEntityMethodWhenThrowsException")
    void addEntity_WhenCalledAndThrowsException_ShouldReturnProperCode(
            Throwable parameterizedException, int parameterizedCode, String parameterizedMessage) throws Exception {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        String newEntityAsJson = mapper.writeValueAsString(newEntity);
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(vehicleServiceMock.add(newEntity))
                .thenThrow(parameterizedException);

        mvc.perform(post("/api/v1/vehicles")
                .header("Authorization", validTokenWithAdminRole)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEntityAsJson))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleServiceMock, Mockito.times(1)).add(newEntity);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void addEntity_WhenCalledWithHappyPath_ShouldReturn201Code() throws Exception {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        String newEntityAsJson = mapper.writeValueAsString(newEntity);
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(vehicleServiceMock.add(newEntity))
                .thenReturn(newEntity);

        mvc.perform(post("/api/v1/vehicles")
                .header("Authorization", validTokenWithAdminRole)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEntityAsJson))
                .andExpect(status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleServiceMock, Mockito.times(1)).add(newEntity);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForUpdateEntityMethodWhenThrowsException")
    void updateEntity_WhenCalledAndThrowsException_ShouldReturnProperCode(
            Throwable parameterizedException, int parameterizedCode, String parameterizedMessage) throws Exception {
        Vehicle resultMock = ConstantsTestVehicle.VEHICLE_VALID_1;
        String updateEntityAsJson = mapper.writeValueAsString(resultMock);
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(vehicleServiceMock
                .update(resultMock))
                .thenThrow(parameterizedException);

        mvc.perform(put("/api/v1/vehicles/{vehicleId}", resultMock.getId())
                .header("Authorization", validTokenWithAdminRole)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEntityAsJson))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleServiceMock, Mockito.times(1)).update(resultMock);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void updateEntity_WhenCalledWithHappyPath_ShouldReturn200CodeAndEntity() throws Exception {
        Vehicle resultMock = ConstantsTestVehicle.VEHICLE_VALID_1;
        String updateEntityAsJson = mapper.writeValueAsString(resultMock);
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(vehicleServiceMock
                .update(resultMock))
                .thenReturn(resultMock);

        MvcResult resultController = mvc.perform(put("/api/v1/vehicles/{vehicleId}", resultMock.getId())
                .header("Authorization", validTokenWithAdminRole)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEntityAsJson))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andReturn();

        checkDataFieldInControllerResults(resultController, resultMock);

        Mockito.verify(vehicleServiceMock, Mockito.times(1)).update(resultMock);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForDeleteEntityMethodWhenThrowsException")
    void deleteEntity_WhenCalledAndThrowsException_ShouldReturnProperCode(
            Throwable parameterizedException, int parameterizedCode, String parameterizedMessage) throws Exception {
        Vehicle resultMock = ConstantsTestVehicle.VEHICLE_VALID_1;
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(vehicleServiceMock
                .deleteById(resultMock.getId()))
                .thenThrow(parameterizedException);

        mvc.perform(delete("/api/v1/vehicles/{vehicleId}", resultMock.getId())
                .header("Authorization", validTokenWithAdminRole))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleServiceMock, Mockito.times(1)).deleteById(resultMock.getId());
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @CsvSource({
            "false, 400, BAD_REQUEST",
            "true, 200, OK"
    })
    void deleteEntity_WhenCalledAndEntityNotDeletedOrDeleted_ShouldReturnProperCode(
            boolean parameterizedServiceResult, int parameterizedCode, String parameterizedMessage) throws Exception {
        Vehicle resultMock = ConstantsTestVehicle.VEHICLE_VALID_1;
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(vehicleServiceMock
                .deleteById(resultMock.getId()))
                .thenReturn(parameterizedServiceResult);

        mvc.perform(delete("/api/v1/vehicles/{vehicleId}", resultMock.getId())
                .header("Authorization", validTokenWithAdminRole))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleServiceMock, Mockito.times(1))
                .deleteById(resultMock.getId());
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    private void checkDataFieldInControllerResults(MvcResult resultController, Vehicle item)
            throws Exception {
        String jsonStringResponse = resultController.getResponse().getContentAsString();
        RestApiDataResponse objectResponse = mapper.readValue(jsonStringResponse, RestApiDataResponse.class);

        assertThat(convertMapToEntity((Map<String, Object>) objectResponse.getData(), Vehicle.class))
                .usingRecursiveComparison()
                .isEqualTo(item);
    }

    private void checkDataFieldInControllerResults(MvcResult resultController, String key, List<Vehicle> items)
            throws Exception {
        String jsonStringResponse = resultController.getResponse().getContentAsString();
        RestApiDataResponse objectResponse = mapper.readValue(jsonStringResponse, RestApiDataResponse.class);

        Map<String, Object> data = (Map<String, Object>) objectResponse.getData();
        assertTrue(data.containsKey(key));
        List<Vehicle> itemsParsed = new ArrayList<>();
        for (Object rawVehicleModel : (List<Object>) data.get(key)) {
            itemsParsed.add(convertMapToEntity((Map<String, Object>) rawVehicleModel, Vehicle.class));
        }

        assertThat(itemsParsed)
                .usingRecursiveComparison()
                .isEqualTo(items);
    }

    private <X> X convertMapToEntity(Map<String, Object> map, Class<X> entityClass) {
        return mapper.convertValue(map, entityClass);
    }

    private static Stream<Arguments> provideUnauthorizedScenario() throws Exception {
        return Stream.of(
                Arguments.of(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(NEW_ENTITY))),

                Arguments.of(put("/api/v1/vehicles/{id}", UPD_ENTITY.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(UPD_ENTITY))),

                Arguments.of(delete("/api/v1/vehicles/{id}", DEL_ENTITY)));
    }

    private static Stream<Arguments> provideForbiddenScenario() throws Exception {
        String validTokenWithoutAdminRole = getBearerToken(VALID_USER, VALID_USER_PASSWORD);

        return Stream.of(
                Arguments.of(post("/api/v1/vehicles")
                        .header("Authorization", validTokenWithoutAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(NEW_ENTITY))),

                Arguments.of(put("/api/v1/vehicles/{id}", UPD_ENTITY.getId())
                        .header("Authorization", validTokenWithoutAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(UPD_ENTITY))),

                Arguments.of(delete("/api/v1/vehicles/{id}", DEL_ENTITY)
                        .header("Authorization", validTokenWithoutAdminRole)));
    }

    private static Stream<Arguments> provideInvalidArgumentInUrlPath() throws Exception {
        String tokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        return Stream.of(
                Arguments.of(get("/api/v1/vehicles/{vehicleId}", GET_ENTITY.getObjectId())),
                Arguments.of(delete("/api/v1/vehicles/{vehicleId}", DEL_ENTITY.getObjectId())
                        .header("Authorization", tokenWithAdminRole)));
    }

    private static Stream<Arguments> provideInvalidJsonBody() throws Exception {
        String tokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        return Stream.of(
                Arguments.of(post("/api/v1/vehicles")
                        .header("Authorization", tokenWithAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ImmutableMap.builder()
                                .put("invalid_field", UPD_ENTITY.getObjectId())
                                .build()))),
                Arguments.of(put("/api/v1/vehicles/{vehicleId}", UPD_ENTITY.getId())
                        .header("Authorization", tokenWithAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ImmutableMap.builder()
                                .put("invalid_field", UPD_ENTITY.getObjectId())
                                .build()))));
    }

    private static Stream<Arguments> provideArgumentsForAddEntityMethodWhenThrowsException() throws Exception {
        return Stream.of(
                Arguments.of(new DataProcessingException("INTERNAL_SERVER_ERROR"), 500, "INTERNAL_SERVER_ERROR"),
                Arguments.of(new EntityAddDataIntegrityViolationException("BAD_REQUEST"), 400, "BAD_REQUEST"));
    }

    private static Stream<Arguments> provideArgumentsForUpdateEntityMethodWhenThrowsException() throws Exception {
        return Stream.of(
                Arguments.of(new EntityNotFoundException("NOT_FOUND"), 404, "NOT_FOUND"),
                Arguments.of(new DataProcessingException("INTERNAL_SERVER_ERROR"), 500, "INTERNAL_SERVER_ERROR"),
                Arguments.of(new EntityUpdateDataIntegrityViolationException("BAD_REQUEST"), 400, "BAD_REQUEST"));
    }

    private static Stream<Arguments> provideArgumentsForDeleteEntityMethodWhenThrowsException() throws Exception {
        return Stream.of(
                Arguments.of(new DataProcessingException("INTERNAL_SERVER_ERROR"), 500, "INTERNAL_SERVER_ERROR"),
                Arguments.of(new EntityDataIntegrityViolationException("BAD_REQUEST"), 400, "BAD_REQUEST"));
    }

}
