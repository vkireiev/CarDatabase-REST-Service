package ua.foxmided.foxstudent103852.cardatabaserestservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
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
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTest;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleModel;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ModelYearRepositoryTest {

    @Autowired
    private ModelYearRepository testRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void save_WhenAddModelYearWithNullAsModelYear_ThenException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> testRepository.save(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void save_WhenAddModelYearWithNullAsYear_ThenException() {
        ModelYear newEntity = ConstantsTestModelYear.newValidModelYear();
        newEntity.setYear(null);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenAddValidModelYear_ThenShouldAddAndReturnAddedEntity() {
        long countEntitiesBeforeAttempt = testRepository.count();
        ModelYear newEntity = ConstantsTestModelYear.newValidModelYear();
        newEntity.setYear(ConstantsTestModelYear.YEAR_YEAR_VALID_FOR_ADD);
        assertNull(this.testEntityManager.getId(newEntity));
        ModelYear returnedEntity = testRepository.save(newEntity);
        assertNotNull(returnedEntity.getId());
        assertNotNull(this.testEntityManager.getId(newEntity));
        long countEntitiesAfterAttempt = testRepository.count();
        assertTrue(countEntitiesBeforeAttempt < countEntitiesAfterAttempt);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenAddDuplicatedValidModelYear_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long existEntityId = ConstantsTestModelYear.YEAR_ID_VALID_1;
        Optional<ModelYear> existEntity = testRepository.findById(existEntityId);
        assertTrue(existEntity.isPresent());
        ModelYear newEntity = ConstantsTestModelYear.newValidModelYear();
        newEntity.setYear(existEntity.get().getYear());
        /*
         * unique constraint by ModelYear.year
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
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void existsById_WhenNotExistModelYear_ThenReturnFalse() {
        assertFalse(testRepository.existsById(ConstantsTestModelYear.YEAR_ID_NOT_EXIST));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void existsById_WhenExistModelYear_ThenReturnTrue() {
        assertTrue(testRepository.existsById(ConstantsTestModelYear.YEAR_ID_VALID_1));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findById_WhenIdIsNull_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.findById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void findById_WhenNotExistModelYear_ThenReturnOptionalNull() {
        assertFalse(testRepository.findById(ConstantsTestModelYear.YEAR_ID_NOT_EXIST).isPresent());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void findById_WhenExistModelYear_ThenReturnOptionalModelYear() {
        long entityId = ConstantsTestModelYear.YEAR_ID_VALID_1;
        Optional<ModelYear> expectedEntity = Optional
                .of(ConstantsTestModelYear.getTestModelYear(entityId));

        Optional<ModelYear> returnedEntity = testRepository.findById(entityId);
        assertTrue(returnedEntity.isPresent());
        assertThat(returnedEntity.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedEntity.get());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findAll_WhenNotExistsModelYears_ThenReturnEmptyPagedListOfModelYears() {
        assertEquals(0, testRepository.count());

        Page<ModelYear> returnedResult = testRepository
                .findAll(Pageable.ofSize(Integer.MAX_VALUE));
        assertThat(returnedResult).isEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void findAll_WhenExistsModelYears_ThenReturnSortedAndPagedListOfModelYears() {
        List<ModelYear> sortedEntities = ConstantsTestModelYear.getAllTestModelYears().stream()
                .sorted((year1, year2) -> year2.getYear().compareTo(year1.getYear()))
                .collect(Collectors.toList());
        assertThat(sortedEntities).isNotEmpty();
        int page = 1;
        int pageSize = 3;
        assertTrue(sortedEntities.size() > (pageSize * page));
        List<ModelYear> expectedResult = sortedEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<ModelYear> returnedResult = testRepository
                .findAll(PageRequest.of(page, pageSize, Sort.by(List.of(Order.desc("year")))));
        assertThat(returnedResult).isNotEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
        assertTrue(returnedResult.getSort().isSorted());
        assertEquals(pageSize, returnedResult.getPageable().getPageSize());
        assertEquals(sortedEntities.size(), returnedResult.getTotalElements());
        assertThat(returnedResult.getContent())
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenUpdateExistModelYearWithNullAsYear_ThenException() {
        long entityForUpdateId = ConstantsTestModelYear.YEAR_ID_VALID_1;
        Optional<ModelYear> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        entityForUpdate.get().setYear(null);
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
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenUpdateExistModelYearOnAnotherExistModelYear_ThenException() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long existEntityId = ConstantsTestModelYear.YEAR_ID_VALID_1;
        long entityForUpdateId = ConstantsTestModelYear.YEAR_ID_VALID_2;
        Optional<ModelYear> existEntity = transactionTemplate.execute(status -> {
            Optional<ModelYear> modelYear = testRepository.findById(existEntityId);
            assertTrue(modelYear.isPresent());
            return modelYear;
        });
        Optional<ModelYear> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<ModelYear> modelYear = testRepository.findById(entityForUpdateId);
            assertTrue(modelYear.isPresent());
            return modelYear;
        });
        Optional<ModelYear> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<ModelYear> modelYear = testRepository.findById(entityForUpdateId);
            assertTrue(modelYear.isPresent());
            return modelYear;
        });
        assertNotEquals(existEntity.get(), entityForUpdate.get());
        entityForUpdate.get().setYear(existEntity.get().getYear());
        assertThrows(
                DataAccessException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    this.testEntityManager.flush();
                },
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
        Optional<ModelYear> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<ModelYear> modelYear = testRepository.findById(entityForUpdateId);
            assertTrue(modelYear.isPresent());
            return modelYear;
        });
        assertEquals(entityBeforeAttempt.get(), entityAfterAttempt.get());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenUpdateExistModelYearWithValidYear_ThenShouldUpdate() {
        long entityForUpdateId = ConstantsTestModelYear.YEAR_ID_VALID_2;
        Optional<ModelYear> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        Year yearEntityForUpdate = entityForUpdate.get().getYear();
        assertNotEquals(ConstantsTestModelYear.YEAR_YEAR_VALID_FOR_UPDATE, entityForUpdate.get().getYear());
        entityForUpdate.get().setYear(ConstantsTestModelYear.YEAR_YEAR_VALID_FOR_UPDATE);
        testRepository.save(entityForUpdate.get());
        Optional<ModelYear> entityAfterUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityAfterUpdate.isPresent());
        assertNotEquals(yearEntityForUpdate, entityAfterUpdate.get().getYear());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void delete_WhenDeleteNotExistModelYear_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        ModelYear entityForDelete = ConstantsTestModelYear.NOT_EXIST_YEAR;
        assertFalse(testRepository.findById(entityForDelete.getId()).isPresent());
        testRepository.delete(entityForDelete);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void delete_WhenDeleteExistModelYearWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestModelYear.YEAR_ID_VALID_2;
        Optional<ModelYear> entityForDelete = testRepository.findById(entityForDeleteId);
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
    void delete_WhenDeleteExistModelYearWithWeavedVehicleModel_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleModel vehicleModel = ConstantsTestVehicleModel.MODEL_VALID_1;
        assertFalse(vehicleModel.getYears().isEmpty());
        long entityForDeleteId = vehicleModel.getYears().stream()
                .findAny().get()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<ModelYear> entityForDelete = transactionTemplate.execute(status -> {
            Optional<ModelYear> entity = testRepository.findById(entityForDeleteId);
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
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void delete_WhenDeleteExistModelYearWithWeavedVehicle_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicle.VEHICLE_VALID_1
                .getYear()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<ModelYear> entityForDelete = transactionTemplate.execute(status -> {
            Optional<ModelYear> entity = testRepository.findById(entityForDeleteId);
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
    void count_WhenNotExistsModelYears_ThenReturnZero() {
        assertEquals(0L, testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void count_WhenExistsModelYears_ThenReturnCountModelYears() {
        assertEquals(ConstantsTestModelYear.getAllTestModelYears().size(), testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void deleteById_WhenDeleteWithNullAsModelYearId_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.deleteById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void deleteById_WhenDeleteNotExistModelYear_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestModelYear.YEAR_ID_NOT_EXIST;
        assertFalse(testRepository.existsById(entityForDeleteId));
        testRepository.deleteById(entityForDeleteId);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void deleteById_WhenDeleteExistModelYearWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestModelYear.YEAR_ID_VALID_2;
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
    void deleteById_WhenDeleteExistModelYearWithWeavedVehicleModel_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleModel vehicleModel = ConstantsTestVehicleModel.MODEL_VALID_1;
        assertFalse(vehicleModel.getYears().isEmpty());
        long entityForDeleteId = vehicleModel.getYears().stream()
                .findAny().get()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<ModelYear> entityForDelete = transactionTemplate.execute(status -> {
            Optional<ModelYear> entity = testRepository.findById(entityForDeleteId);
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

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void deleteById_WhenDeleteExistModelYearWithWeavedVehicle_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicle.VEHICLE_VALID_1
                .getYear()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<ModelYear> entityForDelete = transactionTemplate.execute(status -> {
            Optional<ModelYear> entity = testRepository.findById(entityForDeleteId);
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
