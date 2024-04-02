package ua.foxmided.foxstudent103852.cardatabaserestservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.validation.ValidationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTest;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleModel;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleMakeRepositoryTest {

    @Autowired
    private VehicleMakeRepository testRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void save_WhenAddVehicleMakeWithNullAsVehicleMake_ThenException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> testRepository.save(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @CsvSource({
            ConstantsTestVehicleMake.MAKE_NAME_INVALID_1,
            ConstantsTestVehicleMake.MAKE_NAME_INVALID_2
    })
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void save_WhenAddVehicleMakeWithInvalidName_ThenException(String parameterizedName) {
        VehicleMake newEntity = ConstantsTestVehicleMake.newValidVehicleMake();
        newEntity.setName(parameterizedName);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void save_WhenAddValidVehicleMake_ThenShouldAddAndReturnAddedEntity() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleMake newEntity = ConstantsTestVehicleMake.newValidVehicleMake();
        newEntity.setName(ConstantsTestVehicleMake.MAKE_NAME_VALID_FOR_ADD);
        assertNull(this.testEntityManager.getId(newEntity));
        VehicleMake returnedEntity = testRepository.save(newEntity);
        assertNotNull(returnedEntity.getId());
        assertNotNull(this.testEntityManager.getId(newEntity));
        long countEntitiesAfterAttempt = testRepository.count();
        assertTrue(countEntitiesBeforeAttempt < countEntitiesAfterAttempt);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void save_WhenAddDuplicatedValidVehicleMake_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long existEntityId = ConstantsTestVehicleMake.MAKE_ID_VALID_1;
        Optional<VehicleMake> existEntity = testRepository.findById(existEntityId);
        assertTrue(existEntity.isPresent());
        VehicleMake newEntity = ConstantsTestVehicleMake.newValidVehicleMake();
        newEntity.setName(existEntity.get().getName());
        /*
         * unique constraint by VehicleMake.name
         */
        assertThrows(DataAccessException.class,
                () -> {
                    testRepository.save(newEntity);
                    testEntityManager.flush();
                },
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void existsById_WhenIdIsNull_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.existsById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void existsById_WhenNotExistVehicleMake_ThenReturnFalse() {
        assertFalse(testRepository.existsById(ConstantsTestVehicleMake.MAKE_ID_NOT_EXIST));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void existsById_WhenExistVehicleMake_ThenReturnTrue() {
        assertTrue(testRepository.existsById(ConstantsTestVehicleMake.MAKE_ID_VALID_1));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findById_WhenIdIsNull_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.findById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void findById_WhenNotExistVehicleMake_ThenReturnOptionalNull() {
        assertFalse(testRepository.findById(ConstantsTestVehicleMake.MAKE_ID_NOT_EXIST).isPresent());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void findById_WhenExistVehicleMake_ThenReturnOptionalVehicleMake() {
        long entityId = ConstantsTestVehicleMake.MAKE_ID_VALID_1;
        Optional<VehicleMake> expectedEntity = Optional
                .of(ConstantsTestVehicleMake.getTestVehicleMake(entityId));

        Optional<VehicleMake> returnedEntity = testRepository.findById(entityId);
        assertTrue(returnedEntity.isPresent());
        assertThat(returnedEntity.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedEntity.get());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findAll_WhenNotExistsVehicleMakes_ThenReturnEmptyPagedListOfVehicleMakes() {
        assertEquals(0, testRepository.count());

        Page<VehicleMake> returnedResult = testRepository
                .findAll(Pageable.ofSize(Integer.MAX_VALUE));
        assertThat(returnedResult).isEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void findAll_WhenExistsVehicleMakes_ThenReturnSortedAndPagedListOfVehicleMakes() {
        List<VehicleMake> sortedEntities = ConstantsTestVehicleMake.getAllTestVehicleMakes().stream()
                .sorted((make1, make2) -> make2.getName().compareToIgnoreCase(make1.getName()))
                .collect(Collectors.toList());
        assertThat(sortedEntities).isNotEmpty();
        int page = 1;
        int pageSize = 3;
        assertTrue(sortedEntities.size() > (pageSize * page));
        List<VehicleMake> expectedResult = sortedEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<VehicleMake> returnedResult = testRepository
                .findAll(PageRequest.of(page, pageSize, Sort.by(List.of(Order.desc("name").ignoreCase()))));
        assertThat(returnedResult).isNotEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
        assertTrue(returnedResult.getSort().isSorted());
        assertEquals(pageSize, returnedResult.getPageable().getPageSize());
        assertEquals(sortedEntities.size(), returnedResult.getTotalElements());
        assertThat(returnedResult.getContent())
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @CsvSource({
            ConstantsTestVehicleMake.MAKE_NAME_INVALID_1,
            ConstantsTestVehicleMake.MAKE_NAME_INVALID_2
    })
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void save_WhenUpdateExistVehicleMakeWithInvalidName_ThenException(String parameterizedName) {
        long entityForUpdateId = ConstantsTestVehicleMake.MAKE_ID_VALID_1;
        Optional<VehicleMake> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        entityForUpdate.get().setName(parameterizedName);
        assertThrows(ValidationException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    testEntityManager.flush();
                },
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void save_WhenUpdateExistVehicleMakeOnAnotherExistVehicleMake_ThenException() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long existEntityId = ConstantsTestVehicleMake.MAKE_ID_VALID_1;
        long entityForUpdateId = ConstantsTestVehicleMake.MAKE_ID_VALID_2;
        Optional<VehicleMake> existEntity = transactionTemplate.execute(status -> {
            Optional<VehicleMake> vehicleMake = testRepository.findById(existEntityId);
            assertTrue(vehicleMake.isPresent());
            return vehicleMake;
        });
        Optional<VehicleMake> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleMake> vehicleMake = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleMake.isPresent());
            return vehicleMake;
        });
        Optional<VehicleMake> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<VehicleMake> vehicleMake = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleMake.isPresent());
            return vehicleMake;
        });
        assertNotEquals(existEntity.get(), entityForUpdate.get());
        entityForUpdate.get().setName(existEntity.get().getName());
        assertThrows(
                DataAccessException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    this.testEntityManager.flush();
                },
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
        Optional<VehicleMake> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleMake> vehicleMake = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleMake.isPresent());
            return vehicleMake;
        });
        assertEquals(entityBeforeAttempt.get(), entityAfterAttempt.get());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void save_WhenUpdateExistVehicleMakeWithValidName_ThenShouldUpdate() {
        long entityForUpdateId = ConstantsTestVehicleMake.MAKE_ID_VALID_2;
        Optional<VehicleMake> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        String nameEntityForUpdate = entityForUpdate.get().getName();
        assertNotEquals(ConstantsTestVehicleMake.MAKE_NAME_VALID_FOR_UPDATE, entityForUpdate.get().getName());
        entityForUpdate.get().setName(ConstantsTestVehicleMake.MAKE_NAME_VALID_FOR_UPDATE);
        testRepository.save(entityForUpdate.get());
        Optional<VehicleMake> entityAfterUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityAfterUpdate.isPresent());
        assertNotEquals(nameEntityForUpdate, entityAfterUpdate.get().getName());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void delete_WhenDeleteNotExistVehicleMake_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleMake entityForDelete = ConstantsTestVehicleMake.NOT_EXIST_MAKE;
        assertFalse(testRepository.findById(entityForDelete.getId()).isPresent());
        testRepository.delete(entityForDelete);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void delete_WhenDeleteExistVehicleMakeWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicleMake.MAKE_ID_VALID_2;
        Optional<VehicleMake> entityForDelete = testRepository.findById(entityForDeleteId);
        assertTrue(entityForDelete.isPresent());
        testRepository.delete(entityForDelete.get());
        testEntityManager.flush();
        assertFalse(testRepository.findById(entityForDeleteId).isPresent());
        long countEntitiesAfterAttempt = testRepository.count();
        assertTrue(countEntitiesBeforeAttempt > countEntitiesAfterAttempt);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void delete_WhenDeleteExistVehicleMakeWithWeavedVehicleModel_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleModel vehicleModel = ConstantsTestVehicleModel.MODEL_VALID_1;
        assertFalse(vehicleModel.getYears().isEmpty());
        long entityForDeleteId = vehicleModel.getYears().stream()
                .findAny().get()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<VehicleMake> entityForDelete = transactionTemplate.execute(status -> {
            Optional<VehicleMake> entity = testRepository.findById(entityForDeleteId);
            assertTrue(entity.isPresent());
            return entity;
        });

        assertThrows(DataIntegrityViolationException.class,
                () -> testRepository.delete(entityForDelete.get()),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);

        assertTrue(testRepository.existsById(entityForDeleteId));
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void count_WhenNotExistsVehicleMakes_ThenReturnZero() {
        assertEquals(0L, testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void count_WhenExistsVehicleMakes_ThenReturnCountVehicleMakes() {
        assertEquals(ConstantsTestVehicleMake.getAllTestVehicleMakes().size(), testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void deleteById_WhenDeleteWithNullAsVehicleMakeId_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.deleteById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void deleteById_WhenDeleteNotExistVehicleMake_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicleMake.MAKE_ID_NOT_EXIST;
        assertFalse(testRepository.existsById(entityForDeleteId));
        testRepository.deleteById(entityForDeleteId);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleMakes_in_makes.sql" })
    void deleteById_WhenDeleteExistVehicleMakeWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicleMake.MAKE_ID_VALID_2;
        assertTrue(testRepository.existsById(entityForDeleteId));
        testRepository.deleteById(entityForDeleteId);
        testEntityManager.flush();
        assertFalse(testRepository.existsById(entityForDeleteId));
        long countEntitiesAfterAttempt = testRepository.count();
        assertTrue(countEntitiesBeforeAttempt > countEntitiesAfterAttempt);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void deleteById_WhenDeleteExistVehicleMakeWithWeavedVehicleModel_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleModel vehicleModel = ConstantsTestVehicleModel.MODEL_VALID_1;
        assertFalse(vehicleModel.getYears().isEmpty());
        long entityForDeleteId = vehicleModel.getYears().stream()
                .findAny().get()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<VehicleMake> entityForDelete = transactionTemplate.execute(status -> {
            Optional<VehicleMake> entity = testRepository.findById(entityForDeleteId);
            assertTrue(entity.isPresent());
            return entity;
        });

        assertThrows(DataIntegrityViolationException.class,
                () -> testRepository.deleteById(entityForDelete.get().getId()),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);

        assertTrue(testRepository.existsById(entityForDeleteId));
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

}
