package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.BadSqlGrammarException;

import jakarta.validation.ValidationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityAddDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityNotFoundException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityUpdateDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.VehicleRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTest;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.specification.SearchCriteria;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.specification.SearchSpecification;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @MockBean
    VehicleRepository testRepositoryMock;

    @Autowired
    VehicleServiceImpl testService;

    private final long entityForTestId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
    private Vehicle entityForTest;

    @BeforeEach
    void beforeEach() {
        entityForTest = ConstantsTestVehicle.getTestVehicle(entityForTestId);
    }

    @Test
    void add_WhenCalledWithNullAsVehicle_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.add(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
    }

    @Test
    void add_WhenCalledAndThrowsDataAccessException_ThenException() {
        entityForTest.setId(null);

        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.add(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(Vehicle.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void add_WhenCalledWithDuplicatedValidVehicle_ThenException() {
        entityForTest.setId(null);

        Mockito.when(testRepositoryMock.save(entityForTest)).thenThrow(DataIntegrityViolationException.class);

        EntityAddDataIntegrityViolationException exception = assertThrows(
                EntityAddDataIntegrityViolationException.class,
                () -> testService.add(entityForTest),
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase("Vehicle with such objectId already exists");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(Vehicle.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void add_WhenCalledWithValidVehicle_ThenAddAndReturnAddedEntity() {
        entityForTest.setId(null);
        Vehicle resultMockito = ConstantsTestVehicle.getTestVehicle(entityForTestId);

        Mockito.when(testRepositoryMock.save(entityForTest)).thenReturn(resultMockito);

        Vehicle resultService = testService.add(entityForTest);
        assertNotNull(resultService.getId());
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(Vehicle.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void get_WhenCalledWithNullAsVehicleId_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.get(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
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
    }

    @Test
    void get_WhenCalledAndNotExistVehicleWithSuchId_ThenReturnOptionalEmpty() {
        Optional<Vehicle> resultMockito = Optional.empty();

        Mockito.when(testRepositoryMock.findById(entityForTestId))
                .thenReturn(resultMockito);

        assertThat(testService.get(entityForTest.getId())).isEmpty();

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void get_WhenCalledAndExistVehicleWithSuchId_ThenReturnOptionalVehicle() {
        Optional<Vehicle> resultMockito = Optional
                .of(ConstantsTestVehicle.getTestVehicle(entityForTestId));

        Mockito.when(testRepositoryMock.findById(entityForTestId))
                .thenReturn(resultMockito);

        Optional<Vehicle> resultService = testService.get(entityForTestId);
        assertThat(resultService).isNotEmpty();
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @ParameterizedTest
    @MethodSource("provideNullAsArgumentsForGetAllMethod")
    void findAll_WhenCalledWithNullAsArguments_ThenException(
            Specification<Vehicle> parameterizedSpecification, PageRequest parameterizedPageable) {
        assertThrows(ValidationException.class,
                () -> testService.findAll(parameterizedSpecification, parameterizedPageable),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
    }

    @Test
    void findAll_WhenCalledAndThrowsDataAccessException_ThenException() {
        Specification<Vehicle> specification = Specification.where(
                new SearchSpecification<Vehicle>(SearchCriteria.of("make", "==", entityForTest.getModel().getMake())));
        Pageable pageable = Pageable.ofSize(Integer.MAX_VALUE);

        Mockito.when(testRepositoryMock
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.findAll(specification, pageable),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void findAll_WhenCalledAndNotExistsSearchVehicles_ThenReturnEmptyPage() {
        Specification<Vehicle> specification = Specification.where(
                new SearchSpecification<Vehicle>(SearchCriteria.of("make", "==", entityForTest.getModel().getMake())));
        Pageable pageable = Pageable.ofSize(Integer.MAX_VALUE);

        Mockito.when(testRepositoryMock
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThat(testService.findAll(specification, pageable)).isEmpty();

        Mockito.verify(testRepositoryMock, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void findAll_WhenCalledAndExistsSearchVehicles_ThenReturnSortedAndPagedListOfVehicles() {
        int page = 2;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(List.of(Order.desc("id"))));
        VehicleMake searchMake = ConstantsTestVehicleMake.MAKE_VALID_2;
        List<Vehicle> filteredEntities = ConstantsTestVehicle.getAllTestVehicles().stream()
                .filter(vehicle -> vehicle.getModel().getMake().equals(searchMake))
                .sorted((vehicle1, vehicle2) -> vehicle2.getId().compareTo(vehicle1.getId()))
                .collect(Collectors.toList());
        assertEquals(10L, filteredEntities.size());
        assertTrue(filteredEntities.size() > (pageSize * page));
        List<Vehicle> resultMockito = filteredEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Specification<Vehicle> specification = Specification
                .where(new SearchSpecification<Vehicle>(
                        SearchCriteria.of("make", "==", searchMake.getName())));

        Mockito.when(testRepositoryMock
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(resultMockito, pageable, filteredEntities.size()));

        Page<Vehicle> resultService = testService.findAll(specification, pageable);
        assertThat(resultService).isNotEmpty();
        assertThat(resultService.getContent())
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);
        assertTrue(resultService.getPageable().isPaged());
        assertTrue(resultService.getSort().isSorted());

        Mockito.verify(testRepositoryMock, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledWithNullAsVehicle_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.update(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledWithNullAsVehicleId_ThenException() {
        entityForTest.setId(null);

        doThrow(IllegalArgumentException.class)
                .when(testRepositoryMock)
                .existsById(null);

        assertThrows(DataProcessingException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(null);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledWithNotExistVehicle_ThenException() {
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase(entityForTest.getId().toString())
                .containsIgnoringCase("Vehicle")
                .containsIgnoringCase("not found");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledAndThrowsDataAccessException_ThenException() {
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(true);
        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(Vehicle.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledWithDuplicatedValidVehicle_ThenException() {
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(true);
        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenThrow(DataIntegrityViolationException.class);

        EntityUpdateDataIntegrityViolationException exception = assertThrows(
                EntityUpdateDataIntegrityViolationException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase("Vehicle with such objectId already exists");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(Vehicle.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledWithExistVehicleAndHappyPath_ThenUpdateAndReturnUpdatedEntity() {
        Vehicle resultMockito = ConstantsTestVehicle.getTestVehicle(entityForTest.getId());

        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(true);
        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenReturn(resultMockito);

        Vehicle resultService = testService.update(entityForTest);
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(Vehicle.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void delete_WhenCalledWithNullAsVehicle_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.delete(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
    }

    @Test
    void delete_WhenCalledWithNullAsVehicleId_ThenException() {
        entityForTest.setId(null);

        Mockito.doNothing().when(testRepositoryMock).delete(entityForTest);
        Mockito.when(testRepositoryMock.existsById(null))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.delete(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(Vehicle.class));
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(null);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void delete_WhenCalledWithNotExistVehicle_ThenNothingToDeletedAndReturnTrue() {
        Mockito.doNothing().when(testRepositoryMock).delete(entityForTest);
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(false);

        assertTrue(testService.delete(entityForTest));

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(Vehicle.class));
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void delete_WhenCalledAndThrowsDataAccessException_ThenException() {
        doThrow(BadSqlGrammarException.class)
                .when(testRepositoryMock)
                .delete(entityForTest);

        assertThrows(DataProcessingException.class,
                () -> testService.delete(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(Vehicle.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void delete_WhenCalledWithExistVehicleAndAndDeleteOrNotDelete_ThenReturnTrueOrFalse(
            boolean isExistsByIdResult) {
        Mockito.doNothing().when(testRepositoryMock).delete(entityForTest);
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(isExistsByIdResult);

        assertEquals(!isExistsByIdResult, testService.delete(entityForTest));

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(entityForTest);
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(entityForTest.getId());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void deleteById_WhenCalledWithNullAsVehicleId_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.deleteById(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
    }

    @Test
    void deleteById_WhenCalledWithNotExistVehicle_ThenNothingToDeletedAndReturnTrue() {
        Mockito.doNothing().when(testRepositoryMock).deleteById(entityForTest.getId());
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(false);

        assertTrue(testService.deleteById(entityForTest.getId()));

        Mockito.verify(testRepositoryMock, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
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
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void deleteById_WhenCalledWithExistVehicleAndDeleteOrNotDelete_ThenReturnTrueOrFalse(
            boolean isExistsByIdResult) {
        Mockito.doNothing().when(testRepositoryMock).deleteById(entityForTest.getId());
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(isExistsByIdResult);

        assertEquals(!isExistsByIdResult, testService.deleteById(entityForTest.getId()));

        Mockito.verify(testRepositoryMock, Mockito.times(1)).deleteById(entityForTest.getId());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(entityForTest.getId());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
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
    }

    @Test
    void count_WhenCalledAndNotExistsVehicles_ThenReturnZero() {
        Mockito.when(testRepositoryMock.count())
                .thenReturn(0L);

        assertEquals(0L, testService.count());

        Mockito.verify(testRepositoryMock, Mockito.times(1)).count();
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void count_WhenCalledAndExistsVehicles_ThenReturnNumberOfVehicles() {
        long resultMockito = ConstantsTestVehicle.getAllTestVehicles().size();
        assertNotEquals(0L, resultMockito);

        Mockito.when(testRepositoryMock.count())
                .thenReturn(resultMockito);

        assertEquals(resultMockito, testService.count());

        Mockito.verify(testRepositoryMock, Mockito.times(1)).count();
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @ParameterizedTest
    @MethodSource("provideNullAsArgumentsForExistsByModelAndModelYearMethod")
    void existsByModelAndYear_WhenCalledWithNullAsVehicleModelAndModelYear_ThenException(
            VehicleModel parameterizedModel, ModelYear parameterizedModelYear) {
        assertThrows(ValidationException.class,
                () -> testService.existsByModelAndYear(parameterizedModel, parameterizedModelYear),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
    }

    @Test
    void existsByModelAndYear_WhenCalledAndThrowsDataAccessException_ThenException() {
        Mockito.when(testRepositoryMock
                .existsByModelAndYear(entityForTest.getModel(), entityForTest.getYear()))
                .thenThrow(BadSqlGrammarException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.existsByModelAndYear(entityForTest.getModel(), entityForTest.getYear()),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void existsByModelAndYear_WhenCalledAndNotExistVehicleWithSuchModelAndModelYear_ThenReturnFalse() {
        boolean resultMockito = false;

        Mockito.when(testRepositoryMock
                .existsByModelAndYear(ConstantsTestVehicleModel.NOT_EXIST_MODEL,
                        ConstantsTestModelYear.NOT_EXIST_YEAR))
                .thenReturn(resultMockito);

        assertFalse(testService.existsByModelAndYear(ConstantsTestVehicleModel.NOT_EXIST_MODEL,
                ConstantsTestModelYear.NOT_EXIST_YEAR));

        Mockito.verify(testRepositoryMock, Mockito.times(1))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void existsByModelAndYear_WhenCalledAndExistVehicleWithSuchModelAndModelYear_ThenReturnTrue() {
        boolean resultMockito = true;

        Mockito.when(testRepositoryMock
                .existsByModelAndYear(entityForTest.getModel(), entityForTest.getYear()))
                .thenReturn(resultMockito);

        assertTrue(testService.existsByModelAndYear(entityForTest.getModel(), entityForTest.getYear()));

        Mockito.verify(testRepositoryMock, Mockito.times(1))
                .existsByModelAndYear(Mockito.any(VehicleModel.class), Mockito.any(ModelYear.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    private static Stream<Arguments> provideNullAsArgumentsForGetAllMethod() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(Specification.allOf(), null),
                Arguments.of(null, PageRequest.of(0, Integer.MAX_VALUE)));
    }

    private static Stream<Arguments> provideNullAsArgumentsForExistsByModelAndModelYearMethod() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(ConstantsTestVehicleModel.NOT_EXIST_MODEL, null),
                Arguments.of(null, ConstantsTestModelYear.NOT_EXIST_YEAR));
    }

}
