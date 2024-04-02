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

import org.hibernate.exception.ConstraintViolationException;
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
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleModel;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleModelRepositoryTest {

    @Autowired
    private VehicleModelRepository testRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void save_WhenAddVehicleModelWithNullAsVehicleModel_ThenException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> testRepository.save(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @CsvSource({
            ConstantsTestVehicleModel.MODEL_NAME_INVALID_1
    })
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenAddVehicleModelWithInvalidName_ThenException(String parameterizedName) {
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        newEntity.setName(parameterizedName);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenAddVehicleModelWithNullAsMake_ThenException() {
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        newEntity.setMake(null);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenAddVehicleModelWithNotExistMake_ThenException() {
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        newEntity.setMake(ConstantsTestVehicleMake.NOT_EXIST_MAKE);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(DataIntegrityViolationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenAddVehicleModelWithNullAsModelYears_ThenException() {
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        newEntity.setYears(null);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenAddVehicleModelWithNullAsModelYear_ThenException() {
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        newEntity.getYears().add(null);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class,
                () -> {
                    testRepository.save(newEntity);
                    testEntityManager.flush();
                },
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenAddVehicleModelWithNotExistModelYear_ThenException() {
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        newEntity.getYears().add(ConstantsTestModelYear.NOT_EXIST_YEAR);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(DataIntegrityViolationException.class,
                () -> {
                    testRepository.save(newEntity);
                    testEntityManager.flush();
                },
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql" })
    void save_WhenAddValidVehicleModel_ThenShouldAddAndReturnAddedEntity() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        newEntity.setName(ConstantsTestVehicleModel.MODEL_NAME_VALID_FOR_ADD);
        assertNull(this.testEntityManager.getId(newEntity));
        VehicleModel returnedEntity = testRepository.save(newEntity);
        assertNotNull(returnedEntity.getId());
        assertNotNull(this.testEntityManager.getId(newEntity));
        long countEntitiesAfterAttempt = testRepository.count();
        assertTrue(countEntitiesBeforeAttempt < countEntitiesAfterAttempt);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void save_WhenAddDuplicatedValidVehicleModel_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long existEntityId = ConstantsTestVehicleModel.MODEL_ID_VALID_1;
        Optional<VehicleModel> existEntity = testRepository.findById(existEntityId);
        assertTrue(existEntity.isPresent());
        VehicleModel newEntity = ConstantsTestVehicleModel.newValidVehicleModel();
        newEntity.setName(existEntity.get().getName());
        newEntity.setMake(existEntity.get().getMake());
        /*
         * unique constraint by 'models_make_name_unique'
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
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void existsById_WhenNotExistVehicleModel_ThenReturnFalse() {
        assertFalse(testRepository.existsById(ConstantsTestVehicleModel.MODEL_ID_NOT_EXIST));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void existsById_WhenExistVehicleModel_ThenReturnTrue() {
        assertTrue(testRepository.existsById(ConstantsTestVehicleModel.MODEL_ID_VALID_1));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findById_WhenIdIsNull_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.findById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void findById_WhenNotExistVehicleModel_ThenReturnOptionalNull() {
        assertFalse(testRepository.findById(ConstantsTestVehicleModel.MODEL_ID_NOT_EXIST).isPresent());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void findById_WhenExistVehicleModel_ThenReturnOptionalVehicleModel() {
        long entityId = ConstantsTestVehicleModel.MODEL_ID_VALID_1;
        Optional<VehicleModel> expectedEntity = Optional
                .of(ConstantsTestVehicleModel.getTestVehicleModel(entityId));

        Optional<VehicleModel> returnedEntity = testRepository.findById(entityId);
        assertTrue(returnedEntity.isPresent());
        assertThat(returnedEntity.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedEntity.get());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findAll_WhenNotExistsVehicleModels_ThenReturnEmptyPagedListOfVehicleModels() {
        assertEquals(0, testRepository.count());

        Page<VehicleModel> returnedResult = testRepository
                .findAll(Pageable.ofSize(Integer.MAX_VALUE));
        assertThat(returnedResult).isEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void findAll_WhenExistsVehicleModels_ThenReturnSortedAndPagedListOfVehicleModels() {
        List<VehicleModel> sortedEntities = ConstantsTestVehicleModel.getAllTestVehicleModels().stream()
                .sorted((model, model2) -> model2.getName().compareToIgnoreCase(model.getName()))
                .collect(Collectors.toList());
        assertThat(sortedEntities).isNotEmpty();
        int page = 1;
        int pageSize = 2;
        assertTrue(sortedEntities.size() > (pageSize * page));
        List<VehicleModel> expectedResult = sortedEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<VehicleModel> returnedResult = testRepository
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
            ConstantsTestVehicleModel.MODEL_NAME_INVALID_1
    })
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void save_WhenUpdateExistVehicleModelWithInvalidName_ThenException(String parameterizedName) {
        long entityForUpdateId = ConstantsTestVehicleModel.MODEL_ID_VALID_1;
        Optional<VehicleModel> entityForUpdate = testRepository.findById(entityForUpdateId);
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
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void save_WhenUpdateExistVehicleModelWithNullAsMake_ThenException() {
        long entityForUpdateId = ConstantsTestVehicleModel.MODEL_ID_VALID_1;
        Optional<VehicleModel> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        entityForUpdate.get().setMake(null);
        assertThrows(ValidationException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    testEntityManager.flush();
                },
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void save_WhenUpdateExistVehicleModelWithNotExistMake_ThenException() {
        long entityForUpdateId = ConstantsTestVehicleModel.MODEL_ID_VALID_1;
        Optional<VehicleModel> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        entityForUpdate.get().setMake(ConstantsTestVehicleMake.NOT_EXIST_MAKE);
        assertThrows(ConstraintViolationException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    testEntityManager.flush();
                },
                ConstantsTest.CONSTRAINT_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void save_WhenUpdateExistVehicleModelWithNullAsModelYears_ThenException() {
        long entityForUpdateId = ConstantsTestVehicleModel.MODEL_ID_VALID_1;
        Optional<VehicleModel> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        entityForUpdate.get().setYears(null);
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
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void save_WhenUpdateExistVehicleModelWithNotExistModelYear_ThenException() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long entityForUpdateId = ConstantsTestVehicleModel.MODEL_ID_VALID_1;
        Optional<VehicleModel> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });
        Optional<VehicleModel> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });
        entityForUpdate.get().getYears().add(ConstantsTestModelYear.NOT_EXIST_YEAR);
        assertThrows(DataAccessException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    this.testEntityManager.flush();
                },
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
        Optional<VehicleModel> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });
        assertEquals(entityBeforeAttempt.get(), entityAfterAttempt.get());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void save_WhenUpdateExistVehicleModelOnAnotherExistVehicleModel_ThenException() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long existEntityId = ConstantsTestVehicleModel.MODEL_ID_VALID_1;
        long entityForUpdateId = ConstantsTestVehicleModel.MODEL_ID_VALID_2;
        Optional<VehicleModel> existEntity = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(existEntityId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });
        Optional<VehicleModel> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });
        Optional<VehicleModel> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });
        assertNotEquals(existEntity.get(), entityForUpdate.get());
        entityForUpdate.get().setName(existEntity.get().getName());
        entityForUpdate.get().setMake(existEntity.get().getMake());
        assertThrows(DataAccessException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    this.testEntityManager.flush();
                },
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
        Optional<VehicleModel> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });
        assertEquals(entityBeforeAttempt.get(), entityAfterAttempt.get());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void save_WhenUpdateExistVehicleModelWithHappyPath_ThenShouldUpdate() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long entityForUpdateId = ConstantsTestVehicleModel.MODEL_ID_VALID_1;
        Optional<VehicleModel> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });
        Optional<VehicleModel> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });

        entityForUpdate.get().setName(ConstantsTestVehicleModel.MODEL_NAME_VALID_FOR_UPDATE);
        entityForUpdate.get().setMake(ConstantsTestVehicleModel.MODEL_MAKE_VALID_FOR_UPDATE);
        entityForUpdate.get().getYears().addAll(ConstantsTestVehicleModel.MODEL_YEARS_FOR_ADD);

        testRepository.save(entityForUpdate.get());

        Optional<VehicleModel> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleModel> vehicleModel = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleModel.isPresent());
            assertFalse(vehicleModel.get().getYears().isEmpty());
            return vehicleModel;
        });
        assertThat(entityAfterAttempt.get())
                .usingRecursiveComparison()
                .isNotEqualTo(entityBeforeAttempt.get());
        assertEquals(ConstantsTestVehicleModel.MODEL_NAME_VALID_FOR_UPDATE, entityAfterAttempt.get().getName());
        assertEquals(ConstantsTestVehicleModel.MODEL_MAKE_VALID_FOR_UPDATE, entityAfterAttempt.get().getMake());
        assertThat(entityAfterAttempt.get().getYears())
                .containsAll(ConstantsTestVehicleModel.MODEL_YEARS_FOR_ADD);
        assertThat(entityAfterAttempt.get().getYears())
                .containsAll(entityBeforeAttempt.get().getYears());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void delete_WhenDeleteNotExistVehicleModel_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleModel entityForDelete = ConstantsTestVehicleModel.NOT_EXIST_MODEL;
        assertFalse(testRepository.findById(entityForDelete.getId()).isPresent());
        testRepository.delete(entityForDelete);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void delete_WhenDeleteExistVehicleModelWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicleModel.MODEL_ID_FOR_DELETE;
        Optional<VehicleModel> entityForDelete = testRepository.findById(entityForDeleteId);
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
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void delete_WhenDeleteExistVehicleModelWithWeavedVehicle_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicle.VEHICLE_VALID_1
                .getModel()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<VehicleModel> entityForDelete = transactionTemplate.execute(status -> {
            Optional<VehicleModel> entity = testRepository.findById(entityForDeleteId);
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
    void count_WhenNotExistsVehicleModels_ThenReturnZero() {
        assertEquals(0L, testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void count_WhenExistsVehicleModels_ThenReturnCountVehicleModels() {
        assertEquals(ConstantsTestVehicleModel.getAllTestVehicleModels().size(), testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void deleteById_WhenDeleteWithNullAsVehicleModelId_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.deleteById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void deleteById_WhenDeleteNotExistVehicleModel_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicleModel.MODEL_ID_NOT_EXIST;
        assertFalse(testRepository.existsById(entityForDeleteId));
        testRepository.deleteById(entityForDeleteId);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void deleteById_WhenDeleteExistVehicleModelWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicleModel.MODEL_ID_FOR_DELETE;
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
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void deleteById_WhenDeleteExistVehicleModelWithWeavedVehicle_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicle.VEHICLE_VALID_1
                .getModel()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<VehicleModel> entityForDelete = transactionTemplate.execute(status -> {
            Optional<VehicleModel> entity = testRepository.findById(entityForDeleteId);
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
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void findAllByMake_WhenCalledWithNullAsVehicleMake_ThenReturnEmptyPagedListOfVehicleModels() {
        assertNotEquals(0, testRepository.count());
        int page = 1;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));

        Page<VehicleModel> returnedResult = testRepository
                .findAllByMake(null, pageable);
        assertThat(returnedResult).isEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findAllByMake_WhenNotExistsVehicleModels_ThenReturnEmptyPagedListOfVehicleModels() {
        assertEquals(0, testRepository.count());
        int page = 1;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(Order.desc("id"))));

        Page<VehicleModel> returnedResult = testRepository
                .findAllByMake(ConstantsTestVehicleMake.MAKE_VALID_1, pageable);
        assertThat(returnedResult).isEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void findAllByMake_WhenNotExistsVehicleModelsWithSuchVehicleMake_ThenReturnEmptyPagedListOfVehicleModels() {
        assertNotEquals(0, testRepository.count());
        VehicleMake searchMake = ConstantsTestVehicleMake.NOT_EXIST_MAKE;
        List<VehicleModel> filteredEntities = ConstantsTestVehicleModel.getAllTestVehicleModels().stream()
                .filter(model -> model.getMake().equals(searchMake))
                .sorted((model, model2) -> model2.getName().compareToIgnoreCase(model.getName()))
                .collect(Collectors.toList());
        assertThat(filteredEntities).isEmpty();
        int page = 1;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(List.of(Order.desc("name").ignoreCase())));

        Page<VehicleModel> returnedResult = testRepository
                .findAllByMake(searchMake, pageable);
        assertThat(returnedResult).isEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
        assertTrue(returnedResult.getSort().isSorted());
        assertEquals(pageSize, returnedResult.getPageable().getPageSize());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql" })
    void findAllByMake_WhenExistsVehicleModelsWithSuchVehicleMake_ThenReturnSortedAndPagedListOfVehicleModels() {
        assertNotEquals(0, testRepository.count());
        VehicleMake searchMake = ConstantsTestVehicleMake.MAKE_VALID_2;
        List<VehicleModel> filteredEntities = ConstantsTestVehicleModel.getAllTestVehicleModels().stream()
                .filter(model -> model.getMake().equals(searchMake))
                .sorted((model, model2) -> model2.getName().compareToIgnoreCase(model.getName()))
                .collect(Collectors.toList());
        assertThat(filteredEntities).isNotEmpty();
        int page = 1;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(List.of(Order.desc("name").ignoreCase())));
        assertTrue(filteredEntities.size() > (pageSize * page));
        List<VehicleModel> expectedResult = filteredEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<VehicleModel> returnedResult = testRepository
                .findAllByMake(searchMake, pageable);
        assertThat(returnedResult).isNotEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
        assertTrue(returnedResult.getSort().isSorted());
        assertEquals(pageSize, returnedResult.getPageable().getPageSize());
        assertEquals(filteredEntities.size(), returnedResult.getTotalElements());
        assertThat(returnedResult.getContent())
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

}
