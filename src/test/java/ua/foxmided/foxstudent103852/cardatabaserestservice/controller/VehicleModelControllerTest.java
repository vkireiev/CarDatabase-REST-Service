package ua.foxmided.foxstudent103852.cardatabaserestservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
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
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiDataResponse;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.ModelYearServiceImpl;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.VehicleMakeServiceImpl;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.VehicleModelServiceImpl;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleModel;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleModelControllerTest extends KeycloakTestcontainersConfigurer {
    private static final VehicleModel GET_ENTITY = ConstantsTestVehicleModel.MODEL_VALID_1;
    private static final VehicleModel NEW_ENTITY = ConstantsTestVehicleModel.newValidVehicleModel();
    private static final VehicleModel UPD_ENTITY = ConstantsTestVehicleModel.MODEL_VALID_1;
    private static final VehicleModel DEL_ENTITY = ConstantsTestVehicleModel.MODEL_VALID_2;

    private static ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
            .registerModule(new JavaTimeModule());

    @Autowired
    MockMvc mvc;

    @MockBean
    VehicleModelServiceImpl modelServiceMock;

    @MockBean
    VehicleMakeServiceImpl vehicleMakeServiceMock;

    @MockBean
    ModelYearServiceImpl modelYearServiceMock;

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

        Mockito.verifyNoInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
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

        Mockito.verifyNoInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
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

        Mockito.verifyNoInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
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

        Mockito.verifyNoInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForNotExistVehicleMakeScenario")
    void WhenCalledWithNotExistVehicleMake_ShouldReturn404Code(
            RequestBuilder parameterizedUrlAndHttpMethod, VehicleMake parameterizedMake) throws Exception {

        Mockito.when(vehicleMakeServiceMock.get(parameterizedMake.getId()))
                .thenReturn(Optional.empty());

        mvc.perform(parameterizedUrlAndHttpMethod)
                .andExpect(status().is(404))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage())
                        .containsIgnoringCase("VehicleMake")
                        .containsIgnoringCase(parameterizedMake.getId().toString())
                        .containsIgnoringCase("not found"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("NOT_FOUND"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleMakeServiceMock, Mockito.times(1)).get(parameterizedMake.getId());
        Mockito.verifyNoMoreInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForNotExistVehicleModelScenario")
    void WhenCalledWithNotExistVehicleModel_ShouldReturn404Code(
            RequestBuilder parameterizedUrlAndHttpMethod, Long modelId)
            throws Exception {

        Mockito.when(modelServiceMock
                .get(modelId))
                .thenReturn(Optional.empty());

        mvc.perform(parameterizedUrlAndHttpMethod)
                .andExpect(status().is(404))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage())
                        .containsIgnoringCase("VehicleModel")
                        .containsIgnoringCase(modelId.toString())
                        .containsIgnoringCase("not found"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("NOT_FOUND"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(modelServiceMock, Mockito.times(1)).get(modelId);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void getEntity_WhenCalledAndThrowsDataProcessingException_ShouldReturn500Code() throws Exception {

        Mockito.when(modelServiceMock
                .get(GET_ENTITY.getId()))
                .thenThrow(new DataProcessingException("DataProcessingException"));

        mvc.perform(get("/api/v1/models/{modelId}", GET_ENTITY.getId()))
                .andExpect(status().is(500))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataProcessingException))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(modelServiceMock, Mockito.times(1)).get(GET_ENTITY.getId());
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void getEntity_WhenCalledAndExistVehicleModelWithSuchId_ShouldReturn200CodeAndEntity()
            throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel.MODEL_VALID_1;

        Mockito.when(modelServiceMock
                .get(GET_ENTITY.getId()))
                .thenReturn(Optional.of(resultMock));

        MvcResult resultController = mvc.perform(get("/api/v1/models/{modelId}", GET_ENTITY.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andReturn();

        checkDataFieldInControllerResults(resultController, resultMock);

        Mockito.verify(modelServiceMock, Mockito.times(1)).get(GET_ENTITY.getId());
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void getAllEntities_WhenCalledWithInvalidSortArgument_ShouldReturn400Code() throws Exception {

        mvc.perform(get("/api/v1/models?sort=invalid,asc"))
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verifyNoInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void getAllEntities_WhenCalledAndThrowsDataProcessingException_ShouldReturn500Code() throws Exception {
        int page = 0;
        int size = 5;
        String sort = "id,desc";
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));

        Mockito.when(modelServiceMock.getAll(pageable))
                .thenThrow(new DataProcessingException("DataProcessingException"));

        mvc.perform(get("/api/v1/models?page={page}&size={size}&sort={sort}",
                page, size, sort))
                .andExpect(status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(modelServiceMock, Mockito.times(1)).getAll(pageable);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void getAllEntities_WhenCalledAndNotExistsVehicleModels_ShouldReturn200CodeAndEmptyListOfVehicleModelsWithPaginationInfo()
            throws Exception {
        int page = 0;
        int size = 5;
        String sort = "id,desc";
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));

        Mockito.when(modelServiceMock.getAll(pageable))
                .thenReturn(Page.empty(pageable));

        mvc.perform(get("/api/v1/models?page={page}&size={size}&sort={sort}",
                page, size, sort))
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

        Mockito.verify(modelServiceMock, Mockito.times(1)).getAll(pageable);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void getAllEntities_WhenCalledAndExistsVehicleModels_ShouldReturn200CodeAndPagedAndSortedListOfVehicleModelsWithPaginationInfo()
            throws Exception {
        int page = 1;
        int size = 5;
        String sort = "id,desc";
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));
        List<VehicleModel> sortedEntities = ConstantsTestVehicleModel.getAllTestVehicleModels().stream()
                .sorted((entity1, entity2) -> entity2.getId().compareTo(entity1.getId()))
                .collect(Collectors.toList());
        assertTrue(sortedEntities.size() > (size * page));
        assertEquals(59L, sortedEntities.size());

        List<VehicleModel> resultMockito = sortedEntities.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        assertEquals(size, resultMockito.size());

        Mockito.when(modelServiceMock.getAll(pageable))
                .thenReturn(new PageImpl<>(resultMockito, pageable, sortedEntities.size()));

        MvcResult resultController = mvc.perform(get("/api/v1/models?page={page}&size={size}&sort={sort}",
                page, size, sort))
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

                .andExpect(jsonPath("$.data.totalElements").value(59))
                .andExpect(jsonPath("$.data.numberOfElements").value(5))
                .andExpect(jsonPath("$.data.totalPages").value(12))
                .andReturn();

        checkDataFieldInControllerResults(resultController, "content", resultMockito);

        Mockito.verify(modelServiceMock, Mockito.times(1)).getAll(pageable);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void findAllByMakeEntities_WhenCalledWithInvalidSortArgument_ShouldReturn400Code() throws Exception {

        Mockito.when(vehicleMakeServiceMock.get(GET_ENTITY.getMake().getId()))
                .thenReturn(Optional.of(GET_ENTITY.getMake()));

        mvc.perform(get("/api/v1/makes/{makeId}/models?sort=invalid,asc", GET_ENTITY.getMake().getId()))
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleMakeServiceMock, Mockito.times(1)).get(GET_ENTITY.getMake().getId());
        Mockito.verifyNoInteractions(modelServiceMock);
        Mockito.verifyNoMoreInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void findAllByMakeEntities_WhenCalledAndThrowsDataProcessingException_ShouldReturn500Code() throws Exception {
        int page = 0;
        int size = 5;
        String sort = "id,desc";
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));

        Mockito.when(vehicleMakeServiceMock.get(GET_ENTITY.getMake().getId()))
                .thenReturn(Optional.of(GET_ENTITY.getMake()));
        Mockito.when(modelServiceMock.findAllByMake(GET_ENTITY.getMake(), pageable))
                .thenThrow(new DataProcessingException("DataProcessingException"));

        mvc.perform(get("/api/v1/makes/{makeId}/models?page={page}&size={size}&sort={sort}",
                GET_ENTITY.getMake().getId(), page, size, sort))
                .andExpect(status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleMakeServiceMock, Mockito.times(1)).get(GET_ENTITY.getMake().getId());
        Mockito.verifyNoMoreInteractions(vehicleMakeServiceMock);
        Mockito.verify(modelServiceMock, Mockito.times(1)).findAllByMake(GET_ENTITY.getMake(), pageable);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void findAllByMakeEntities_WhenCalledAndNotExistsVehicleModelsWithSuchMakeId_ShouldReturn200CodeAndEmptyListOfVehicleModelsWithPaginationInfo()
            throws Exception {
        int page = 0;
        int size = 5;
        String sort = "id,desc";
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));

        Mockito.when(vehicleMakeServiceMock.get(GET_ENTITY.getMake().getId()))
                .thenReturn(Optional.of(GET_ENTITY.getMake()));
        Mockito.when(modelServiceMock.findAllByMake(GET_ENTITY.getMake(), pageable))
                .thenReturn(Page.empty(pageable));

        mvc.perform(get("/api/v1/makes/{makeId}/models?page={page}&size={size}&sort={sort}",
                GET_ENTITY.getMake().getId(), page, size, sort))
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

        Mockito.verify(vehicleMakeServiceMock, Mockito.times(1)).get(GET_ENTITY.getMake().getId());
        Mockito.verifyNoMoreInteractions(vehicleMakeServiceMock);
        Mockito.verify(modelServiceMock, Mockito.times(1)).findAllByMake(GET_ENTITY.getMake(), pageable);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void findAllByMakeEntities_WhenCalledAndExistsVehicleModelsWithSuchMakeId_ShouldReturn200CodeAndPagedAndSortedListOfVehicleModelsWithPaginationInfo()
            throws Exception {
        int page = 1;
        int size = 2;
        String sort = "id,desc";
        VehicleMake searchMake = ConstantsTestVehicleMake.MAKE_VALID_2;
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));
        List<VehicleModel> filteredEntities = ConstantsTestVehicleModel.getAllTestVehicleModels().stream()
                .filter(model -> model.getMake().equals(searchMake))
                .sorted((entity1, entity2) -> entity2.getId().compareTo(entity1.getId()))
                .collect(Collectors.toList());
        assertTrue(filteredEntities.size() > (size * page));
        assertEquals(5L, filteredEntities.size());

        List<VehicleModel> resultMockito = filteredEntities.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        assertEquals(size, resultMockito.size());

        Mockito.when(vehicleMakeServiceMock.get(searchMake.getId()))
                .thenReturn(Optional.of(searchMake));
        Mockito.when(modelServiceMock.findAllByMake(searchMake, pageable))
                .thenReturn(new PageImpl<>(resultMockito, pageable, filteredEntities.size()));

        MvcResult resultController = mvc.perform(
                get("/api/v1/makes/{makeId}/models?page={page}&size={size}&sort={sort}",
                        searchMake.getId(), page, size, sort))
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

                .andExpect(jsonPath("$.data.totalElements").value(5))
                .andExpect(jsonPath("$.data.numberOfElements").value(2))
                .andExpect(jsonPath("$.data.totalPages").value(3))
                .andReturn();

        checkDataFieldInControllerResults(resultController, "content", resultMockito);

        Mockito.verify(vehicleMakeServiceMock, Mockito.times(1)).get(searchMake.getId());
        Mockito.verifyNoMoreInteractions(vehicleMakeServiceMock);
        Mockito.verify(modelServiceMock, Mockito.times(1)).findAllByMake(searchMake, pageable);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForAddEntityMethodWhenThrowsException")
    void addEntity_WhenCalledAndThrowsException_ShouldReturnProperCode(
            Throwable parameterizedException, int parameterizedCode, String parameterizedMessage) throws Exception {
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        String newEntityAsJson = mapper.writeValueAsString(newEntity);
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(vehicleMakeServiceMock.get(newEntity.getMake().getId()))
                .thenReturn(Optional.of(newEntity.getMake()));
        Mockito.when(modelServiceMock.add(newEntity))
                .thenThrow(parameterizedException);

        mvc.perform(post("/api/v1/makes/{makeId}/models", newEntity.getMake().getId())
                .header("Authorization", validTokenWithAdminRole)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEntityAsJson))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleMakeServiceMock, Mockito.times(1)).get(newEntity.getMake().getId());
        Mockito.verifyNoMoreInteractions(vehicleMakeServiceMock);
        Mockito.verify(modelServiceMock, Mockito.times(1)).add(newEntity);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void addEntity_WhenCalledWithHappyPath_ShouldReturn201Code() throws Exception {
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        String newEntityAsJson = mapper.writeValueAsString(newEntity);
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(vehicleMakeServiceMock.get(newEntity.getMake().getId()))
                .thenReturn(Optional.of(newEntity.getMake()));
        Mockito.when(modelServiceMock.add(newEntity))
                .thenReturn(newEntity);

        mvc.perform(post("/api/v1/makes/{makeId}/models", newEntity.getMake().getId())
                .header("Authorization", validTokenWithAdminRole)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEntityAsJson))
                .andExpect(status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(vehicleMakeServiceMock, Mockito.times(1)).get(newEntity.getMake().getId());
        Mockito.verifyNoMoreInteractions(vehicleMakeServiceMock);
        Mockito.verify(modelServiceMock, Mockito.times(1)).add(newEntity);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForUpdateEntityMethodWhenThrowsException")
    void updateEntity_WhenCalledAndThrowsException_ShouldReturnProperCode(
            Throwable parameterizedException, int parameterizedCode, String parameterizedMessage) throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel.MODEL_VALID_1;
        String updateEntityAsJson = mapper.writeValueAsString(resultMock);
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(modelServiceMock
                .update(resultMock))
                .thenThrow(parameterizedException);

        mvc.perform(put("/api/v1/models/{modelId}", resultMock.getId())
                .header("Authorization", validTokenWithAdminRole)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEntityAsJson))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(modelServiceMock, Mockito.times(1)).update(resultMock);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @Test
    void updateEntity_WhenCalledWithHappyPath_ShouldReturn200CodeAndEntity() throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel.MODEL_VALID_1;
        String updateEntityAsJson = mapper.writeValueAsString(resultMock);
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(modelServiceMock
                .update(resultMock))
                .thenReturn(resultMock);

        MvcResult resultController = mvc.perform(put("/api/v1/models/{modelId}", resultMock.getId())
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

        Mockito.verify(modelServiceMock, Mockito.times(1)).update(resultMock);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForDeleteEntityMethodWhenThrowsException")
    void deleteEntity_WhenCalledAndThrowsException_ShouldReturnProperCode(
            Throwable parameterizedException, int parameterizedCode, String parameterizedMessage) throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel.MODEL_VALID_1;
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(modelServiceMock
                .deleteById(resultMock.getId()))
                .thenThrow(parameterizedException);

        mvc.perform(delete("/api/v1/models/{modelId}", resultMock.getId())
                .header("Authorization", validTokenWithAdminRole))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(modelServiceMock, Mockito.times(1)).deleteById(resultMock.getId());
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @ParameterizedTest
    @CsvSource({
            "false, 400, BAD_REQUEST",
            "true, 200, OK"
    })
    void deleteEntity_WhenCalledAndEntityNotDeletedOrDeleted_ShouldReturnProperCode(
            boolean parameterizedServiceResult, int parameterizedCode, String parameterizedMessage) throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel.MODEL_VALID_1;
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(modelServiceMock
                .deleteById(resultMock.getId()))
                .thenReturn(parameterizedServiceResult);

        mvc.perform(delete("/api/v1/models/{modelId}", resultMock.getId())
                .header("Authorization", validTokenWithAdminRole))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(modelServiceMock, Mockito.times(1))
                .deleteById(resultMock.getId());
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void getEntityAllYears_WhenCalledAndExistVehicleModelWithSuchId_ShouldReturn200CodeAndListOfModelYears(
            boolean isEmptyResult)
            throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel
                .getTestVehicleModel(ConstantsTestVehicleModel.MODEL_ID_VALID_2);
        if (isEmptyResult) {
            resultMock.getYears().clear();
        }
        assertEquals(isEmptyResult, resultMock.getYears().isEmpty());

        Mockito.when(modelServiceMock
                .get(resultMock.getId()))
                .thenReturn(Optional.of(resultMock));

        MvcResult resultController = mvc.perform(get("/api/v1/models/{modelId}/years", resultMock.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                .andReturn();

        checkDataFieldInControllerResults(resultController, resultMock.getYears(), ModelYear.class);

        Mockito.verify(modelServiceMock, Mockito.times(1)).get(resultMock.getId());
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
        Mockito.verifyNoInteractions(modelYearServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForAddModelYearToEntityYearsMethodWhenThrowsException")
    void addModelYearToEntityYears_WhenCalledAndThrowsException_ShouldReturnProperCode(
            Throwable parameterizedException, int parameterizedCode, String parameterizedMessage) throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel
                .getTestVehicleModel(ConstantsTestVehicleModel.MODEL_ID_VALID_1);
        ModelYear addModelYear = ConstantsTestModelYear.YEAR_VALID_3;
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(modelYearServiceMock
                .get(addModelYear.getId()))
                .thenReturn(Optional.of(addModelYear));
        Mockito.when(modelServiceMock
                .modifyYears(resultMock.getId(), addModelYear, false))
                .thenThrow(parameterizedException);

        mvc.perform(post("/api/v1/models/{modelId}/years/{yearId}", resultMock.getId(), addModelYear.getId())
                .header("Authorization", validTokenWithAdminRole))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(modelYearServiceMock, Mockito.times(1)).get(addModelYear.getId());
        Mockito.verifyNoMoreInteractions(modelYearServiceMock);
        Mockito.verify(modelServiceMock, Mockito.times(1))
                .modifyYears(resultMock.getId(), addModelYear, false);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
    }

    @Test
    void addModelYearToEntityYears_WhenCalledWithHappyPath_ShouldReturn200CodeAndListOfModelYears() throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel
                .getTestVehicleModel(ConstantsTestVehicleModel.MODEL_ID_VALID_1);
        ModelYear addModelYear = ConstantsTestModelYear.YEAR_VALID_3;
        assertFalse(resultMock.getYears().contains(addModelYear));
        resultMock.getYears().add(addModelYear);
        assertTrue(resultMock.getYears().contains(addModelYear));
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(modelYearServiceMock
                .get(addModelYear.getId()))
                .thenReturn(Optional.of(addModelYear));
        Mockito.when(modelServiceMock
                .modifyYears(resultMock.getId(), addModelYear, false))
                .thenReturn(resultMock);

        MvcResult resultController = mvc.perform(post("/api/v1/models/{modelId}/years/{yearId}",
                resultMock.getId(), addModelYear.getId())
                .header("Authorization", validTokenWithAdminRole))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                .andReturn();

        checkDataFieldInControllerResults(resultController, resultMock.getYears(), ModelYear.class);

        Mockito.verify(modelYearServiceMock, Mockito.times(1)).get(addModelYear.getId());
        Mockito.verifyNoMoreInteractions(modelYearServiceMock);
        Mockito.verify(modelServiceMock, Mockito.times(1))
                .modifyYears(resultMock.getId(), addModelYear, false);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForRemoveModelYearFromEntityYearsMethodWhenThrowsException")
    void removeModelYearFromEntityYears_WhenCalledAndThrowsException_ShouldReturnProperCode(
            Throwable parameterizedException, int parameterizedCode, String parameterizedMessage) throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel
                .getTestVehicleModel(ConstantsTestVehicleModel.MODEL_ID_VALID_1);
        ModelYear removeModelYear = ConstantsTestModelYear.YEAR_VALID_3;
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(modelYearServiceMock
                .get(removeModelYear.getId()))
                .thenReturn(Optional.of(removeModelYear));
        Mockito.when(modelServiceMock
                .modifyYears(resultMock.getId(), removeModelYear, true))
                .thenThrow(parameterizedException);

        mvc.perform(delete("/api/v1/models/{modelId}/years/{yearId}", resultMock.getId(), removeModelYear.getId())
                .header("Authorization", validTokenWithAdminRole))
                .andExpect(status().is(parameterizedCode))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(parameterizedCode))
                .andExpect(jsonPath("$.message").value(parameterizedMessage))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(modelYearServiceMock, Mockito.times(1)).get(removeModelYear.getId());
        Mockito.verifyNoMoreInteractions(modelYearServiceMock);
        Mockito.verify(modelServiceMock, Mockito.times(1))
                .modifyYears(resultMock.getId(), removeModelYear, true);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
    }

    @Test
    void removeModelYearFromEntityYears_WhenCalledWithHappyPath_ShouldReturn200CodeAndListOfModelYears()
            throws Exception {
        VehicleModel resultMock = ConstantsTestVehicleModel
                .getTestVehicleModel(ConstantsTestVehicleModel.MODEL_ID_VALID_2);
        ModelYear removeModelYear = ConstantsTestModelYear.YEAR_VALID_3;
        assertTrue(resultMock.getYears().contains(removeModelYear));
        resultMock.getYears().remove(removeModelYear);
        assertFalse(resultMock.getYears().contains(removeModelYear));
        String validTokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        Mockito.when(modelYearServiceMock
                .get(removeModelYear.getId()))
                .thenReturn(Optional.of(removeModelYear));
        Mockito.when(modelServiceMock
                .modifyYears(resultMock.getId(), removeModelYear, true))
                .thenReturn(resultMock);

        MvcResult resultController = mvc.perform(delete("/api/v1/models/{modelId}/years/{yearId}",
                resultMock.getId(), removeModelYear.getId())
                .header("Authorization", validTokenWithAdminRole))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                .andReturn();

        checkDataFieldInControllerResults(resultController, resultMock.getYears(), ModelYear.class);

        Mockito.verify(modelYearServiceMock, Mockito.times(1)).get(removeModelYear.getId());
        Mockito.verifyNoMoreInteractions(modelYearServiceMock);
        Mockito.verify(modelServiceMock, Mockito.times(1))
                .modifyYears(resultMock.getId(), removeModelYear, true);
        Mockito.verifyNoMoreInteractions(modelServiceMock);
        Mockito.verifyNoInteractions(vehicleMakeServiceMock);
    }

    private void checkDataFieldInControllerResults(MvcResult resultController, VehicleModel item)
            throws Exception {
        String jsonStringResponse = resultController.getResponse().getContentAsString();
        RestApiDataResponse objectResponse = mapper.readValue(jsonStringResponse, RestApiDataResponse.class);

        assertThat(convertMapToEntity((Map<String, Object>) objectResponse.getData(), VehicleModel.class))
                .usingRecursiveComparison()
                .isEqualTo(item);
    }

    private void checkDataFieldInControllerResults(MvcResult resultController, String key, List<VehicleModel> items)
            throws Exception {
        String jsonStringResponse = resultController.getResponse().getContentAsString();
        RestApiDataResponse objectResponse = mapper.readValue(jsonStringResponse, RestApiDataResponse.class);

        Map<String, Object> data = (Map<String, Object>) objectResponse.getData();
        assertTrue(data.containsKey(key));
        List<VehicleModel> itemsParsed = new ArrayList<>();
        for (Object rawVehicleModel : (List<Object>) data.get(key)) {
            itemsParsed.add(convertMapToEntity((Map<String, Object>) rawVehicleModel, VehicleModel.class));
        }

        assertThat(itemsParsed)
                .usingRecursiveComparison()
                .isEqualTo(items);
    }

    private <X> void checkDataFieldInControllerResults(MvcResult resultController,
            Collection<X> items, Class<X> entityClass) throws Exception {
        String jsonStringResponse = resultController.getResponse().getContentAsString();
        RestApiDataResponse objectResponse = mapper.readValue(jsonStringResponse, RestApiDataResponse.class);

        List<X> itemsParsed = new ArrayList<>();
        for (Object rawMap : (List<Object>) objectResponse.getData()) {
            itemsParsed.add(convertMapToEntity((Map<String, Object>) rawMap, entityClass));
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
                Arguments.of(post("/api/v1/makes/{makeId}/models", NEW_ENTITY.getMake().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(NEW_ENTITY))),
                Arguments.of(put("/api/v1/models/{modelId}", UPD_ENTITY.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(UPD_ENTITY))),
                Arguments.of(delete("/api/v1/models/{modelId}", DEL_ENTITY.getId())),

                Arguments.of(post("/api/v1/models/{modelId}/years/{yearId}",
                        UPD_ENTITY.getId(), ConstantsTestModelYear.YEAR_ID_VALID_1)),

                Arguments.of(delete("/api/v1/models/{modelId}/years/{yearId}",
                        UPD_ENTITY.getId(), ConstantsTestModelYear.YEAR_ID_VALID_1)));
    }

    private static Stream<Arguments> provideForbiddenScenario() throws Exception {
        String validTokenWithoutAdminRole = getBearerToken(VALID_USER, VALID_USER_PASSWORD);

        return Stream.of(
                Arguments.of(post("/api/v1/makes/{makeId}/models", NEW_ENTITY.getMake().getId())
                        .header("Authorization", validTokenWithoutAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(NEW_ENTITY))),
                Arguments.of(put("/api/v1/models/{modelId}", UPD_ENTITY.getId())
                        .header("Authorization", validTokenWithoutAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(UPD_ENTITY))),
                Arguments.of(delete("/api/v1/models/{modelId}", DEL_ENTITY.getId())
                        .header("Authorization", validTokenWithoutAdminRole)),

                Arguments.of(post("/api/v1/models/{modelId}/years/{yearId}",
                        UPD_ENTITY.getId(), ConstantsTestModelYear.YEAR_ID_VALID_1)
                        .header("Authorization", validTokenWithoutAdminRole)),
                Arguments.of(delete("/api/v1/models/{modelId}/years/{yearId}",
                        UPD_ENTITY.getId(), ConstantsTestModelYear.YEAR_ID_VALID_1)
                        .header("Authorization", validTokenWithoutAdminRole)));
    }

    private static Stream<Arguments> provideInvalidArgumentInUrlPath() throws Exception {
        String tokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        return Stream.of(
                Arguments.of(post("/api/v1/makes/{makeId}/models", NEW_ENTITY.getMake().getName())
                        .header("Authorization", tokenWithAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(NEW_ENTITY))),
                Arguments.of(get("/api/v1/makes/{makeId}/models", GET_ENTITY.getMake().getName())),
                Arguments.of(get("/api/v1/models/{modelId}", GET_ENTITY.getName())),
                Arguments.of(put("/api/v1/models/{modelId}", UPD_ENTITY.getName())
                        .header("Authorization", tokenWithAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(UPD_ENTITY))),
                Arguments.of(delete("/api/v1/models/{modelId}", DEL_ENTITY.getName())
                        .header("Authorization", tokenWithAdminRole)),

                Arguments.of(get("/api/v1/models/{modelId}/years", GET_ENTITY.getName())),
                Arguments.of(post("/api/v1/models/{modelId}/years/{yearId}",
                        UPD_ENTITY.getName(), ConstantsTestModelYear.YEAR_VALID_1.getId())
                        .header("Authorization", tokenWithAdminRole)),
                Arguments.of(post("/api/v1/models/{modelId}/years/{yearId}",
                        UPD_ENTITY.getId(), UPD_ENTITY.getName())
                        .header("Authorization", tokenWithAdminRole)),
                Arguments.of(delete("/api/v1/models/{modelId}/years/{yearId}",
                        UPD_ENTITY.getName(), ConstantsTestModelYear.YEAR_VALID_1.getId())
                        .header("Authorization", tokenWithAdminRole)),
                Arguments.of(delete("/api/v1/models/{modelId}/years/{yearId}",
                        UPD_ENTITY.getId(), UPD_ENTITY.getName())
                        .header("Authorization", tokenWithAdminRole)));
    }

    private static Stream<Arguments> provideInvalidJsonBody() throws Exception {
        String tokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        return Stream.of(
                Arguments.of(post("/api/v1/makes/{makeId}/models", UPD_ENTITY.getMake().getId())
                        .header("Authorization", tokenWithAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ImmutableMap.builder()
                                .put("invalid_field", UPD_ENTITY.getMake().getName())
                                .build()))),
                Arguments.of(put("/api/v1/models/{modelId}", UPD_ENTITY.getId())
                        .header("Authorization", tokenWithAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ImmutableMap.builder()
                                .put("invalid_field", UPD_ENTITY.getMake().getName())
                                .build()))));
    }

    private static Stream<Arguments> provideArgumentsForNotExistVehicleMakeScenario() throws Exception {
        String tokenWithAdminRole = getBearerToken(VALID_ADMIN, VALID_ADMIN_PASSWORD);

        return Stream.of(
                Arguments.of(
                        post("/api/v1/makes/{makeId}/models", NEW_ENTITY.getMake().getId())
                                .header("Authorization", tokenWithAdminRole)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(NEW_ENTITY)),
                        NEW_ENTITY.getMake()),

                Arguments.of(
                        get("/api/v1/makes/{makeId}/models", GET_ENTITY.getMake().getId()),
                        GET_ENTITY.getMake()));
    }

    private static Stream<Arguments> provideArgumentsForNotExistVehicleModelScenario() throws Exception {
        return Stream.of(
                Arguments.of(
                        get("/api/v1/models/{modelId}", GET_ENTITY.getId()),
                        GET_ENTITY.getId()),
                Arguments.of(
                        get("/api/v1/models/{modelId}/years", GET_ENTITY.getId()),
                        GET_ENTITY.getId()));
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

    private static Stream<Arguments> provideArgumentsForAddModelYearToEntityYearsMethodWhenThrowsException()
            throws Exception {
        return Stream.of(
                Arguments.of(new EntityNotFoundException("NOT_FOUND"), 404, "NOT_FOUND"),
                Arguments.of(new DataProcessingException("INTERNAL_SERVER_ERROR"), 500, "INTERNAL_SERVER_ERROR"),
                Arguments.of(new EntityUpdateDataIntegrityViolationException("BAD_REQUEST"), 400, "BAD_REQUEST"));
    }

    private static Stream<Arguments> provideArgumentsForRemoveModelYearFromEntityYearsMethodWhenThrowsException()
            throws Exception {
        return Stream.of(
                Arguments.of(new DataProcessingException("INTERNAL_SERVER_ERROR"), 500, "INTERNAL_SERVER_ERROR"),
                Arguments.of(new EntityUpdateDataIntegrityViolationException("BAD_REQUEST"), 400, "BAD_REQUEST"));
    }

}
