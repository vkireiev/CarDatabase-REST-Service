package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import org.springframework.jdbc.BadSqlGrammarException;

import jakarta.validation.ValidationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityAddDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityNotFoundException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityUpdateDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.VehicleMakeRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTest;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleMake;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class VehicleMakeServiceImplTest {

    @MockBean
    VehicleMakeRepository testRepositoryMock;

    @Autowired
    VehicleMakeServiceImpl testService;

    private final long entityForTestId = ConstantsTestVehicleMake.MAKE_ID_VALID_1;
    private VehicleMake entityForTest;

    @BeforeEach
    void beforeEach() {
        entityForTest = ConstantsTestVehicleMake.getTestVehicleMake(entityForTestId);
    }

    @Test
    void add_WhenCalledWithNullAsVehicleMake_ThenException() {
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

        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleMake.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void add_WhenCalledWithDuplicatedValidVehicleMake_ThenException() {
        entityForTest.setId(null);

        Mockito.when(testRepositoryMock.save(entityForTest)).thenThrow(DataIntegrityViolationException.class);

        EntityAddDataIntegrityViolationException exception = assertThrows(
                EntityAddDataIntegrityViolationException.class,
                () -> testService.add(entityForTest),
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase("VehicleMake with such name already exists");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleMake.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void add_WhenCalledWithValidVehicleMake_ThenAddAndReturnAddedEntity() {
        entityForTest.setId(null);
        VehicleMake resultMockito = ConstantsTestVehicleMake.getTestVehicleMake(entityForTestId);

        Mockito.when(testRepositoryMock.save(entityForTest)).thenReturn(resultMockito);

        VehicleMake resultService = testService.add(entityForTest);
        assertNotNull(resultService.getId());
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleMake.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void get_WhenCalledWithNullAsVehicleMakeId_ThenException() {
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
    void get_WhenCalledAndNotExistVehicleMakeWithSuchId_ThenReturnOptionalEmpty() {
        Optional<VehicleMake> resultMockito = Optional.empty();

        Mockito.when(testRepositoryMock.findById(entityForTestId))
                .thenReturn(resultMockito);

        assertThat(testService.get(entityForTest.getId())).isEmpty();

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void get_WhenCalledAndExistVehicleMakeWithSuchId_ThenReturnOptionalVehicleMake() {
        Optional<VehicleMake> resultMockito = Optional
                .of(ConstantsTestVehicleMake.getTestVehicleMake(entityForTestId));

        Mockito.when(testRepositoryMock.findById(entityForTestId))
                .thenReturn(resultMockito);

        Optional<VehicleMake> resultService = testService.get(entityForTestId);
        assertThat(resultService).isNotEmpty();
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void getAll_WhenCalledWithNullAsPageable_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.getAll(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
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
    }

    @Test
    void getAll_WhenCalledAndNotExistsVehicleMakes_ThenReturnEmptyPage() {
        Pageable pageable = Pageable.ofSize(Integer.MAX_VALUE);

        Mockito.when(testRepositoryMock.findAll(pageable))
                .thenReturn(Page.empty());

        assertThat(testService.getAll(pageable)).isEmpty();

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void getAll_WhenCalledAndExistsVehicleMakes_ThenReturnSortedAndPagedListOfVehicleMakes() {
        int page = 1;
        int pageSize = 5;
        List<VehicleMake> sortedEntities = ConstantsTestVehicleMake.getAllTestVehicleMakes().stream()
                .sorted((make1, make2) -> make2.getName().compareToIgnoreCase(make1.getName()))
                .collect(Collectors.toList());
        assertTrue(sortedEntities.size() > (pageSize * page));
        List<VehicleMake> resultMockito = sortedEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(List.of(Order.desc("name").ignoreCase())));
        Mockito.when(testRepositoryMock.findAll(pageable))
                .thenReturn(new PageImpl<>(resultMockito, pageable, sortedEntities.size()));

        Page<VehicleMake> resultService = testService.getAll(pageable);
        assertThat(resultService).isNotEmpty();
        assertThat(resultService.getContent())
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);
        assertTrue(resultService.getPageable().isPaged());
        assertTrue(resultService.getSort().isSorted());

        Mockito.verify(testRepositoryMock, Mockito.times(1)).findAll(pageable);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledWithNullAsVehicleMake_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.update(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledWithNullAsVehicleMakeId_ThenException() {
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
    void update_WhenCalledWithNotExistVehicleMake_ThenException() {
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase(entityForTest.getId().toString())
                .containsIgnoringCase("VehicleMake")
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
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleMake.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledWithDuplicatedValidVehicleMake_ThenException() {
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(true);
        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenThrow(DataIntegrityViolationException.class);

        EntityUpdateDataIntegrityViolationException exception = assertThrows(
                EntityUpdateDataIntegrityViolationException.class,
                () -> testService.update(entityForTest),
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
        assertThat(exception.getMessage())
                .containsIgnoringCase("VehicleMake with such name already exists");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleMake.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void update_WhenCalledWithExistVehicleMakeAndHappyPath_ThenUpdateAndReturnUpdatedEntity() {
        VehicleMake resultMockito = ConstantsTestVehicleMake.getTestVehicleMake(entityForTest.getId());

        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(true);
        Mockito.when(testRepositoryMock.save(entityForTest))
                .thenReturn(resultMockito);

        VehicleMake resultService = testService.update(entityForTest);
        assertThat(resultService)
                .usingRecursiveComparison()
                .isEqualTo(resultMockito);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(Mockito.anyLong());
        Mockito.verify(testRepositoryMock, Mockito.times(1)).save(Mockito.any(VehicleMake.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void delete_WhenCalledWithNullAsVehicleMake_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.delete(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
    }

    @Test
    void delete_WhenCalledWithNullAsVehicleMakeId_ThenException() {
        entityForTest.setId(null);

        Mockito.doNothing().when(testRepositoryMock).delete(entityForTest);
        Mockito.when(testRepositoryMock.existsById(null))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(DataProcessingException.class,
                () -> testService.delete(entityForTest),
                ConstantsTest.DATA_PROCESSING_EXCEPTION_MESSAGE);

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(VehicleMake.class));
        Mockito.verify(testRepositoryMock, Mockito.times(1)).existsById(null);
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void delete_WhenCalledWithNotExistVehicleMake_ThenNothingToDeletedAndReturnTrue() {
        Mockito.doNothing().when(testRepositoryMock).delete(entityForTest);
        Mockito.when(testRepositoryMock.existsById(entityForTest.getId()))
                .thenReturn(false);

        assertTrue(testService.delete(entityForTest));

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(VehicleMake.class));
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

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(VehicleMake.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void delete_WhenCalledWithExistVehicleMakeWithWeavedVehicleModel_ThenException() {
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
                .containsIgnoringCase("Some VehicleModels still refer to this VehicleMake");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).delete(Mockito.any(VehicleMake.class));
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void delete_WhenCalledWithExistVehicleMakeAndDeleteOrNotDelete_ThenReturnTrueOrFalse(
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
    void deleteById_WhenCalledWithNullAsVehicleMakeId_ThenException() {
        assertThrows(ValidationException.class,
                () -> testService.deleteById(null),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);

        Mockito.verifyNoInteractions(testRepositoryMock);
    }

    @Test
    void deleteById_WhenCalledWithNotExistVehicleMake_ThenNothingToDeletedAndReturnTrue() {
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

    @Test
    void deleteById_WhenCalledWithExistVehicleMakeWithWeavedVehicleModel_ThenException() {
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
                .containsIgnoringCase("Some VehicleModels still refer to this VehicleMake");

        Mockito.verify(testRepositoryMock, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    void deleteById_WhenCalledWithExistVehicleMakeAndDeleteOrNotDelete_ThenReturnTrueOrFalse(
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
    void count_WhenCalledAndNotExistsVehicleMakes_ThenReturnZero() {
        Mockito.when(testRepositoryMock.count())
                .thenReturn(0L);

        assertEquals(0L, testService.count());

        Mockito.verify(testRepositoryMock, Mockito.times(1)).count();
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

    @Test
    void count_WhenCalledAndExistsVehicleMakes_ThenReturnNumberOfVehicleMakes() {
        long resultMockito = ConstantsTestVehicleMake.getAllTestVehicleMakes().size();
        assertNotEquals(0L, resultMockito);

        Mockito.when(testRepositoryMock.count())
                .thenReturn(resultMockito);

        assertEquals(resultMockito, testService.count());

        Mockito.verify(testRepositoryMock, Mockito.times(1)).count();
        Mockito.verifyNoMoreInteractions(testRepositoryMock);
    }

}
