package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.BadSqlGrammarException;

import jakarta.validation.ValidationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityAddDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityNotFoundException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityUpdateDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.VehicleModelRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleService;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTest;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleModel;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class VehicleModelServiceImplTest {

    @MockBean
    VehicleModelRepository testRepositoryMock;

    @MockBean
    VehicleService vehicleServiceMock;

    @Autowired
    VehicleModelServiceImpl testService;

    private final long entityForTestId = ConstantsTestVehicleModel.MODEL_ID_VALID_2;
    private VehicleModel entityForTest;

    @BeforeEach
    void beforeEach() {
        entityForTest = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);
    }

    @Test
    void add_WhenCalledWithNullAsVehicleModel_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.add(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void add_WhenCalledAndThrowsDataAccessException_ThenException() {
        entityForTest.setId(null);

        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.add(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleModel.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void add_WhenCalledWithDuplicatedValidVehicleModel_ThenException() {
        entityForTest.setId(null);

        Mockito.when(testRepositoryMock.save(entityForTest)).thenThrow(DataIntegrityViolationException.class);

        EntityAddDataIntegrityViolationException exception = assertThrows(
                EntityAddDataIntegrityViolationException.class,
                () -> testService.add(entityForTest),
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase("VehicleModel with such name already exists");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleModel.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void add_WhenCalledWithValidVehicleModel_ThenAddAndReturnAddedEntity() {
        entityForTest.setId(null);
        VehicleModel resultMockito = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);

        Mockito.when(testRepositoryMock.save(entityForTest)).thenReturn(resultMockito);

        VehicleModel resultService = testService.add(entityForTest);
        assertNotNull(resultService.getId());
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleModel.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void get_WhenCalledWithNullAsVehicleMakeId_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.get(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void get_WhenCalledAndThrowsDataAccessException_ThenException() {
        Mockito.when(testRepositoryMock.findById(entityForTestId))
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.get(entityForTestId),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void get_WhenCalledAndNotExistVehicleModelWithSuchId_ThenReturnOptionalEmpty() {
        Optional<VehicleModel> resultMockito = Optional.empty();

        Mockito.when(testRepositoryMock.findById(entityForTestId))
                .thenReturn(resultMockito);

        assertThat(testService.get(entityForTest.getId())).isEmpty();

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void get_WhenCalledAndExistVehicleModelWithSuchId_ThenReturnOptionalVehicleModel() {
        Optional<VehicleModel> resultMockito = Optional
                .of(ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId));

        Mockito.when(testRepositoryMock.findById(entityForTestId))
                .thenReturn(resultMockito);

        Optional<VehicleModel> resultService = testService.get(entityForTestId);
        assertThat(resultService).isNotEmpty();
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void getAll_WhenCalledWithNullAsPageable_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.getAll(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void getAll_WhenCalledAndThrowsDataAccessException_ThenException() {
        Pageable pageable = Pageable.ofSize(Integer.MAX_VALUE);

        Mockito.when(testRepositoryMock.findAll(pageable))
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.getAll(pageable),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findAll(pageable);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void getAll_WhenCalledAndNotExistsVehicleModels_ThenReturnEmptyPage() {
        Pageable pageable = Pageable.ofSize(Integer.MAX_VALUE);

        Mockito.when(testRepositoryMock.findAll(pageable))
                .thenReturn(Page.empty());

        assertThat(testService.getAll(pageable)).isEmpty();

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findAll(pageable);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void getAll_WhenCalledAndExistsVehicleModels_ThenReturnSortedAndPagedListOfVehicleModels() {
        int page = 1;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(List.of(Order.desc("name").ignoreCase())));
        List<VehicleModel> sortedEntities = ConstantsTestVehicleModel.getAllTestVehicleModels().stream()
                .sorted((model1, model2) -> model2.getName().compareToIgnoreCase(model1.getName()))
                .collect(Collectors.toList());
        assertTrue(sortedEntities.size() > (pageSize * page));
        List<VehicleModel> resultMockito = sortedEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Mockito.when(testRepositoryMock.findAll(pageable))
                .thenReturn(new PageImpl<>(resultMockito, pageable, sortedEntities.size()));

        Page<VehicleModel> resultService = testService.getAll(pageable);
        assertThat(resultService).isNotEmpty();
        assertThat(resultService.getContent())
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);
        assertTrue(resultService.getPageable().isPaged());
        assertTrue(resultService.getSort().isSorted());

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findAll(pageable);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void update_WhenCalledWithNullAsVehicleModel_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.update(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void update_WhenCalledWithNullAsVehicleModelId_ThenException() {
        entityForTest.setId(null);

        doThrow(IllegalArgumentException.class)
                .when(testRepositoryMock)
                .findById(null);

        assertThrows(DataProcessingException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(null);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void update_WhenCalledWithNotExistVehicleModel_ThenException() {
        Mockito.when(testRepositoryMock.findById(entityForTest.getId()))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase(entityForTest.getId().toString())
                .containsIgnoringCase("VehicleModel")
                .containsIgnoringCase("not found");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void update_WhenCalledAndThrowsDataAccessException_ThenException() {
        VehicleModel resultMockito = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);

        Mockito.when(testRepositoryMock.findById(entityForTest.getId()))
                .thenReturn(Optional.of(resultMockito));
        Mockito.when(vehicleServiceMock
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class)))
                .thenReturn(false);
        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(vehicleServiceMock, Mockito.atMost(entityForTest.getYears().size()))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleModel.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void update_WhenCalledWithDuplicatedValidVehicleModel_ThenException() {
        VehicleModel resultMockito = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);

        Mockito.when(testRepositoryMock.findById(entityForTest.getId()))
                .thenReturn(Optional.of(resultMockito));
        Mockito.when(vehicleServiceMock
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class)))
                .thenReturn(false);
        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenThrow(DataIntegrityViolationException.class);

        EntityUpdateDataIntegrityViolationException exception = assertThrows(
                EntityUpdateDataIntegrityViolationException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase("VehicleModel with such name already exists");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(vehicleServiceMock, Mockito.atMost(entityForTest.getYears().size()))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleModel.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void update_WhenCalledAndRemoveModelYearWeavedWithVehicle_ThenException() {
        long entityForTestId = ConstantsTestVehicleModel.MODEL_ID_VALID_2;
        VehicleModel entityForTest = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);
        VehicleModel resultMockito = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);
        assertTrue(entityForTest.getYears().size() > 1);
        Vehicle vehicle = ConstantsTestVehicle.VEHICLE_VALID_2;
        assertEquals(entityForTest, vehicle.getModel());
        assertTrue(entityForTest.getYears().contains(vehicle.getYear()));
        entityForTest.getYears().remove(vehicle.getYear());

        Mockito.when(testRepositoryMock.findById(entityForTest.getId()))
                .thenReturn(Optional.of(resultMockito));
        Mockito.when(vehicleServiceMock
                .existsByModelAndYear(entityForTest, vehicle.getYear()))
                .thenReturn(true);

        EntityUpdateDataIntegrityViolationException exception = assertThrows(
                EntityUpdateDataIntegrityViolationException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase(entityForTest.getId().toString())
                .containsIgnoringCase("Failed to update")
                .containsIgnoringCase("Some Vehicle.year still refer to this VehicleModel/ModelYear");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(vehicleServiceMock, Mockito.atMost(entityForTest.getYears().size()))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void update_WhenCalledWithExistVehicleModelAndHappyPath_ThenUpdateAndReturnUpdatedEntity() {
        VehicleModel resultMockito = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);

        Mockito.when(testRepositoryMock.findById(entityForTest.getId()))
                .thenReturn(Optional.of(resultMockito));
        Mockito.when(vehicleServiceMock
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class)))
                .thenReturn(false);
        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenReturn(resultMockito);

        VehicleModel resultService = testService.update(entityForTest);
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(vehicleServiceMock, Mockito.atMost(entityForTest.getYears().size()))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleModel.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @Test
    void delete_WhenCalledWithNullAsVehicleModel_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.delete(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void delete_WhenCalledWithNullAsVehicleModelId_ThenException() {
        entityForTest.setId(null);

        Mockito.doNothing().when(testRepositoryMock).delete(entityForTest);
        Mockito.when(testRepositoryMock.existsById(null))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.delete(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(VehicleModel.class));
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(null);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void delete_WhenCalledWithNotExistVehicleModel_ThenNothingToDeletedAndReturnTrue() {
        Mockito.doNothing().when(testRepositoryMock).delete(entityForTest);
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(false);

        assertTrue(testService.delete(entityForTest));

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(VehicleModel.class));
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void delete_WhenCalledAndThrowsDataAccessException_ThenException() {
        doThrow(BadSqlGrammarException.class)
                .when(testRepositoryMock)
                .delete(entityForTest);

        assertThrows(DataProcessingException.class,
                () -> testService.delete(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(VehicleModel.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void delete_WhenCalledWithExistVehicleModelWithWeavedVehicleOrModelYear_ThenException() {
        Mockito.doThrow(DataIntegrityViolationException.class)
                .when(testRepositoryMock)
                .delete(entityForTest);

        EntityDataIntegrityViolationException exception = assertThrows(
                EntityDataIntegrityViolationException.class,
                () -> testService.delete(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase(entityForTest.getId().toString())
                .containsIgnoringCase("Failed to delete")
                .containsIgnoringCase("Some Vehicles or ModelYears still refer to this VehicleModel");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(VehicleModel.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void delete_WhenCalledWithExistVehicleModelAndDeleteOrNotDelete_ThenReturnTrueOrFalse(
            boolean isExistsByIdResult) {
        Mockito.doNothing().when(testRepositoryMock).delete(entityForTest);
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(isExistsByIdResult);

        assertEquals(!isExistsByIdResult, testService.delete(entityForTest));

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(entityForTest);
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(entityForTest.getId());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void deleteById_WhenCalledWithNullAsVehicleModelId_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.deleteById(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void deleteById_WhenCalledWithNotExistVehicleModel_ThenNothingToDeletedAndReturnTrue() {
        Mockito.doNothing().when(testRepositoryMock).deleteById(entityForTest.getId());
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(false);

        assertTrue(testService.deleteById(entityForTest.getId()));

        Mockito.verify(testRepositoryMock, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void deleteById_WhenCalledAndThrowsDataAccessException_ThenException() {
        doThrow(BadSqlGrammarException.class)
                .when(testRepositoryMock)
                .deleteById(entityForTest.getId());

        assertThrows(DataProcessingException.class,
                () -> testService.deleteById(entityForTest.getId()),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void deleteById_WhenCalledWithExistVehicleModelWithWeavedVehicleOrModelYear_ThenException() {
        Mockito.doThrow(DataIntegrityViolationException.class)
                .when(testRepositoryMock)
                .deleteById(entityForTest.getId());

        EntityDataIntegrityViolationException exception = assertThrows(
                EntityDataIntegrityViolationException.class,
                () -> testService.deleteById(entityForTest.getId()),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase(entityForTest.getId().toString())
                .containsIgnoringCase("Failed to delete")
                .containsIgnoringCase("Some Vehicles or ModelYears still refer to this VehicleModel");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void deleteById_WhenCalledWithExistVehicleModelAndDeleteOrNotDelete_ThenReturnTrueOrFalse(
            boolean isExistsByIdResult) {
        Mockito.doNothing().when(testRepositoryMock).deleteById(entityForTest.getId());
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(isExistsByIdResult);

        assertEquals(!isExistsByIdResult, testService.deleteById(entityForTest.getId()));

        Mockito.verify(testRepositoryMock, Mockito.times(1)).deleteById(entityForTest.getId());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(entityForTest.getId());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void count_WhenCalledAndThrowsDataAccessException_ThenException() {
        Mockito.when(testRepositoryMock.count())
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.count(),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).count();
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void count_WhenCalledAndNotExistsVehicleModels_ThenReturnZero() {
        Mockito.when(testRepositoryMock.count())
                .thenReturn(0L);

        assertEquals(0L, testService.count());

        Mockito.verify(testRepositoryMock, Mockito.times(1)).count();
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void count_WhenCalledAndExistsVehicleModels_ThenReturnNumberOfVehicleModels() {
        long resultMockito = ConstantsTestVehicleModel.getAllTestVehicleModels().size();
        assertNotEquals(0L, resultMockito);

        Mockito.when(testRepositoryMock.count())
                .thenReturn(resultMockito);

        assertEquals(resultMockito, testService.count());

        Mockito.verify(testRepositoryMock, Mockito.times(1)).count();
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArgumentsForFindAllByMakeMethod")
    void findAllByMake_WhenCalledWithInvalidArguments_ThenException(
            VehicleMake parameterizedMake, Pageable parameterizedPageable) {

        assertThrows(ValidationException.class,
                () -> testService.findAllByMake(parameterizedMake, parameterizedPageable),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void findAllByMake_WhenCalledAndThrowsDataAccessException_ThenException() {
        VehicleMake searchMake = ConstantsTestVehicleMake.MAKE_VALID_1;
        Pageable pageable = Pageable.ofSize(Integer.MAX_VALUE);

        Mockito.when(testRepositoryMock
                .findAllByMake(searchMake, pageable))
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.findAllByMake(searchMake, pageable),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findAllByMake(searchMake, pageable);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void findAllByMake_WhenCalledAndNotExistsVehicleModelsWithSuchMake_ThenReturnEmptyPage() {
        VehicleMake searchMake = ConstantsTestVehicleMake.NOT_EXIST_MAKE;
        Pageable pageable = Pageable.ofSize(Integer.MAX_VALUE);

        Mockito.when(testRepositoryMock
                .findAllByMake(searchMake, pageable))
                .thenReturn(Page.empty());

        assertThat(testService.findAllByMake(searchMake, pageable)).isEmpty();

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findAllByMake(searchMake, pageable);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void findAllByMake_WhenCalledAndExistsVehicleModelsWithSuchMake_ThenReturnSortedAndPagedListOfVehicleModels() {
        VehicleMake searchMake = ConstantsTestVehicleMake.MAKE_VALID_2;
        int page = 1;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(List.of(Order.desc("name").ignoreCase())));
        List<VehicleModel> filteredEntities = ConstantsTestVehicleModel.getAllTestVehicleModels().stream()
                .filter(model -> model.getMake().equals(searchMake))
                .sorted((model1, model2) -> model2.getName().compareToIgnoreCase(model1.getName()))
                .collect(Collectors.toList());
        assertTrue(filteredEntities.size() > (pageSize * page));
        List<VehicleModel> resultMockito = filteredEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Mockito.when(testRepositoryMock
                .findAllByMake(searchMake, pageable))
                .thenReturn(new PageImpl<>(resultMockito, pageable, filteredEntities.size()));

        Page<VehicleModel> resultService = testService.findAllByMake(searchMake, pageable);
        assertThat(resultService).isNotEmpty();
        assertThat(resultService.getContent())
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);
        assertTrue(resultService.getPageable().isPaged());
        assertTrue(resultService.getSort().isSorted());

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findAllByMake(searchMake, pageable);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArgumentsForModifyYearsMethod")
    void modifyYears_WhenCalledWithInvalidArguments_ThenException(
            Long parameterizedModelId, ModelYear parameterizedModelYear, boolean parameterizedIsRemove) {

        assertThrows(ValidationException.class,
                () -> testService.modifyYears(parameterizedModelId, parameterizedModelYear, parameterizedIsRemove),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForModifyYearsMethodWhenThrowsDataAccessException")
    void modifyYears_WhenCalledAndThrowsDataAccessException_ThenException(
            Throwable parameterizedException, boolean parameterizedIsRemove) {
        ModelYear testModelYear = ConstantsTestModelYear.NOT_EXIST_YEAR;

        Mockito.when(testRepositoryMock.findById(entityForTest.getId()))
                .thenReturn(Optional.of(entityForTest));
        Mockito.when(vehicleServiceMock
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class)))
                .thenReturn(false);
        Mockito.when(testRepositoryMock
                .save(entityForTest))
                .thenThrow(parameterizedException);

        assertThrows(DataProcessingException.class,
                () -> testService.modifyYears(entityForTest.getId(),
                        testModelYear,
                        parameterizedIsRemove),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(entityForTest.getId());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleModel.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verify(vehicleServiceMock, Mockito.atMost(entityForTest.getYears().size()))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void modifyYears_WhenCalledAndNotExistVehicleModel_ThenException(
            boolean parameterizedIsRemove) {

        Mockito.when(testRepositoryMock.findById(entityForTest.getId()))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> testService.modifyYears(entityForTest.getId(),
                        ConstantsTestModelYear.YEAR_VALID_1,
                        parameterizedIsRemove),
                ConstantsTest.ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase("VehicleModel")
                .containsIgnoringCase(entityForTest.getId().toString())
                .containsIgnoringCase("not found");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(entityForTest.getId());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoInteractions(vehicleServiceMock);
    }

    @Test
    void modifyYears_WhenCalledAndRemoveModelYearWeavedWithVehicle_ThenException() {
        long entityForTestId = ConstantsTestVehicleModel.MODEL_ID_VALID_2;
        VehicleModel entityForTest = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);
        VehicleModel resultMockito = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);
        assertTrue(entityForTest.getYears().size() > 1);
        Vehicle vehicle = ConstantsTestVehicle.VEHICLE_VALID_2;
        ModelYear removeModelYear = vehicle.getYear();
        assertEquals(entityForTest, vehicle.getModel());
        assertTrue(entityForTest.getYears().contains(removeModelYear));

        Mockito.when(testRepositoryMock.findById(entityForTest.getId()))
                .thenReturn(Optional.of(resultMockito));
        Mockito.when(vehicleServiceMock
                .existsByModelAndYear(entityForTest, vehicle.getYear()))
                .thenReturn(true);

        EntityUpdateDataIntegrityViolationException exception = assertThrows(
                EntityUpdateDataIntegrityViolationException.class,
                () -> testService.modifyYears(
                        entityForTest.getId(),
                        removeModelYear,
                        true),
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase(entityForTest.getId().toString())
                .containsIgnoringCase("Failed to update")
                .containsIgnoringCase("Some Vehicle.year still refer to this VehicleModel/ModelYear");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(vehicleServiceMock, Mockito.atMost(entityForTest.getYears().size()))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForModifyYearsMethodWithHappyPath")
    void modifyYears_WhenCalledWithExistVehicleModelAndHappyPath_ThenUpdateAndReturnUpdatedEntity(
            ModelYear parameterizedModelYear, boolean parameterizedIsRemove) {
        VehicleModel entityForTest = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);
        VehicleModel resultMockitoBeforeUpdate = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);
        VehicleModel resultMockitoAfterUpdate = ConstantsTestVehicleModel.getTestVehicleModel(entityForTestId);
        if (parameterizedIsRemove) {
            assertTrue(resultMockitoAfterUpdate.getYears().contains(parameterizedModelYear));
            resultMockitoAfterUpdate.getYears().remove(parameterizedModelYear);
            assertFalse(resultMockitoAfterUpdate.getYears().contains(parameterizedModelYear));
        } else {
            assertFalse(resultMockitoAfterUpdate.getYears().contains(parameterizedModelYear));
            resultMockitoAfterUpdate.getYears().add(parameterizedModelYear);
            assertTrue(resultMockitoAfterUpdate.getYears().contains(parameterizedModelYear));
        }

        Mockito.when(testRepositoryMock.findById(entityForTest.getId()))
                .thenReturn(Optional.of(resultMockitoBeforeUpdate));
        Mockito.when(vehicleServiceMock
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class)))
                .thenReturn(false);
        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenReturn(resultMockitoAfterUpdate);

        VehicleModel resultService = testService
                .modifyYears(
                        entityForTest.getId(),
                        parameterizedModelYear,
                        parameterizedIsRemove);
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockitoAfterUpdate);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(entityForTest.getId());
        Mockito.verify(vehicleServiceMock, Mockito.atMost(entityForTest.getYears().size()))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(entityForTest);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
        Mockito.verifyNoMoreInteractions(vehicleServiceMock);
    }

    private static Stream<Arguments> provideInvalidArgumentsForFindAllByMakeMethod() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(ConstantsTestVehicleMake.MAKE_VALID_1, null),
                Arguments.of(null, PageRequest.of(0, Integer.MAX_VALUE)));
    }

    private static Stream<Arguments> provideInvalidArgumentsForModifyYearsMethod() {
        return Stream.of(
                Arguments.of(null, null, true),
                Arguments.of(null, ConstantsTestModelYear.YEAR_VALID_1, true),
                Arguments.of(null, null, false),
                Arguments.of(null, ConstantsTestModelYear.YEAR_VALID_1, false));
    }

    private static Stream<Arguments> provideArgumentsForModifyYearsMethodWhenThrowsDataAccessException() {
        return Stream.of(
                Arguments.of(new InvalidDataAccessResourceUsageException("Exception"), false),
                Arguments.of(new InvalidDataAccessResourceUsageException("Exception"), true),
                Arguments.of(new IllegalArgumentException("Exception"), false),
                Arguments.of(new IllegalArgumentException("Exception"), true));
    }

    private static Stream<Arguments> provideArgumentsForModifyYearsMethodWithHappyPath() {
        return Stream.of(
                Arguments.of(ConstantsTestModelYear.YEAR_VALID_1, false),
                Arguments.of(ConstantsTestModelYear.YEAR_VALID_3, true));
    }

}
