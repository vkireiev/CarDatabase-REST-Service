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
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleCategory;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTest;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleCategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleCategoryRepositoryTest {

    @Autowired
    private VehicleCategoryRepository testRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void save_WhenAddVehicleCategoryWithNullAsVehicleCategory_ThenException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> testRepository.save(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @CsvSource({
            ConstantsTestVehicleCategory.CATEGORY_NAME_INVALID_1,
            ConstantsTestVehicleCategory.CATEGORY_NAME_INVALID_2
    })
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void save_WhenAddVehicleCategoryWithInvalidName_ThenException(String parameterizedName) {
        VehicleCategory newEntity = ConstantsTestVehicleCategory.newValidVehicleCategory();
        newEntity.setName(parameterizedName);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void save_WhenAddValidVehicleCategory_ThenShouldAddAndReturnAddedEntity() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleCategory newEntity = ConstantsTestVehicleCategory.newValidVehicleCategory();
        newEntity.setName(ConstantsTestVehicleCategory.CATEGORY_NAME_VALID_FOR_ADD);
        assertNull(this.testEntityManager.getId(newEntity));
        VehicleCategory returnedEntity = testRepository.save(newEntity);
        assertNotNull(returnedEntity.getId());
        assertNotNull(this.testEntityManager.getId(newEntity));
        long countEntitiesAfterAttempt = testRepository.count();
        assertTrue(countEntitiesBeforeAttempt < countEntitiesAfterAttempt);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void save_WhenAddDuplicatedValidVehicleCategory_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long existEntityId = ConstantsTestVehicleCategory.CATEGORY_ID_VALID_1;
        Optional<VehicleCategory> existEntity = testRepository.findById(existEntityId);
        assertTrue(existEntity.isPresent());
        VehicleCategory newEntity = ConstantsTestVehicleCategory.newValidVehicleCategory();
        newEntity.setName(existEntity.get().getName());
        /*
         * unique constraint by VehicleCategory.name
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
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void existsById_WhenNotExistVehicleCategory_ThenReturnFalse() {
        assertFalse(testRepository.existsById(ConstantsTestVehicleCategory.CATEGORY_ID_NOT_EXIST));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void existsById_WhenExistVehicleCategory_ThenReturnTrue() {
        assertTrue(testRepository.existsById(ConstantsTestVehicleCategory.CATEGORY_ID_VALID_1));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findById_WhenIdIsNull_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.findById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void findById_WhenNotExistVehicleCategoryWithSuchId_ThenReturnOptionalNull() {
        assertFalse(testRepository.findById(ConstantsTestVehicleCategory.CATEGORY_ID_NOT_EXIST).isPresent());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void findById_WhenExistVehicleCategoryWithSuchId_ThenReturnOptionalVehicleCategory() {
        long entityId = ConstantsTestVehicleCategory.CATEGORY_ID_VALID_1;
        Optional<VehicleCategory> expectedEntity = Optional
                .of(ConstantsTestVehicleCategory.getTestVehicleCategory(entityId));

        Optional<VehicleCategory> returnedEntity = testRepository.findById(entityId);
        assertTrue(returnedEntity.isPresent());
        assertThat(returnedEntity.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedEntity.get());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findAll_WhenNotExistsVehicleCategories_ThenReturnEmptyPagedListOfVehicleCategories() {
        assertEquals(0, testRepository.count());

        Page<VehicleCategory> returnedResult = testRepository
                .findAll(Pageable.ofSize(Integer.MAX_VALUE));
        assertThat(returnedResult).isEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void findAll_WhenExistsVehicleCategories_ThenReturnSortedAndPagedListOfVehicleCategories() {
        List<VehicleCategory> sortedEntities = ConstantsTestVehicleCategory.getAllTestVehicleCategories().stream()
                .sorted((category1, category2) -> category2.getName().compareToIgnoreCase(category1.getName()))
                .collect(Collectors.toList());
        assertThat(sortedEntities).isNotEmpty();
        int page = 1;
        int pageSize = 3;
        assertTrue(sortedEntities.size() > (pageSize * page));
        List<VehicleCategory> expectedResult = sortedEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<VehicleCategory> returnedResult = testRepository
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
            ConstantsTestVehicleCategory.CATEGORY_NAME_INVALID_1,
            ConstantsTestVehicleCategory.CATEGORY_NAME_INVALID_2
    })
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void save_WhenUpdateExistVehicleCategoryWithInvalidName_ThenException(String parameterizedName) {
        long entityForUpdateId = ConstantsTestVehicleCategory.CATEGORY_ID_VALID_1;
        Optional<VehicleCategory> entityForUpdate = testRepository.findById(entityForUpdateId);
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
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void save_WhenUpdateExistVehicleCategoryOnAnotherExistVehicleCategory_ThenException() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long existEntityId = ConstantsTestVehicleCategory.CATEGORY_ID_VALID_1;
        long entityForUpdateId = ConstantsTestVehicleCategory.CATEGORY_ID_VALID_2;
        Optional<VehicleCategory> existEntity = transactionTemplate.execute(status -> {
            Optional<VehicleCategory> vehicleCategory = testRepository.findById(existEntityId);
            assertTrue(vehicleCategory.isPresent());
            return vehicleCategory;
        });
        Optional<VehicleCategory> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleCategory> vehicleCategory = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleCategory.isPresent());
            return vehicleCategory;
        });
        Optional<VehicleCategory> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<VehicleCategory> vehicleCategory = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleCategory.isPresent());
            return vehicleCategory;
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
        Optional<VehicleCategory> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<VehicleCategory> vehicleCategory = testRepository.findById(entityForUpdateId);
            assertTrue(vehicleCategory.isPresent());
            return vehicleCategory;
        });
        assertEquals(entityBeforeAttempt.get(), entityAfterAttempt.get());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void save_WhenUpdateExistVehicleCategoryWithValidName_ThenShouldUpdate() {
        long entityForUpdateId = ConstantsTestVehicleCategory.CATEGORY_ID_VALID_2;
        Optional<VehicleCategory> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        String nameEntityForUpdate = entityForUpdate.get().getName();
        assertNotEquals(ConstantsTestVehicleCategory.CATEGORY_NAME_VALID_FOR_UPDATE, entityForUpdate.get().getName());
        entityForUpdate.get().setName(ConstantsTestVehicleCategory.CATEGORY_NAME_VALID_FOR_UPDATE);
        testRepository.save(entityForUpdate.get());
        Optional<VehicleCategory> entityAfterUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityAfterUpdate.isPresent());
        assertNotEquals(nameEntityForUpdate, entityAfterUpdate.get().getName());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void delete_WhenDeleteNotExistVehicleCategory_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        VehicleCategory entityForDelete = ConstantsTestVehicleCategory.NOT_EXIST_CATEGORY;
        assertFalse(testRepository.findById(entityForDelete.getId()).isPresent());
        testRepository.delete(entityForDelete);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void delete_WhenDeleteExistVehicleCategoryWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicleCategory.CATEGORY_ID_VALID_2;
        Optional<VehicleCategory> entityForDelete = testRepository.findById(entityForDeleteId);
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
    void delete_WhenDeleteExistVehicleCategoryWithWeavedVehicle_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        Vehicle vehicle = ConstantsTestVehicle.VEHICLE_VALID_1;
        assertFalse(vehicle.getCategories().isEmpty());
        long entityForDeleteId = vehicle.getCategories().stream()
                .findAny().get()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<VehicleCategory> entityForDelete = transactionTemplate.execute(status -> {
            Optional<VehicleCategory> entity = testRepository.findById(entityForDeleteId);
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
    void count_WhenNotExistsVehicleCategories_ThenReturnZero() {
        assertEquals(0L, testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void count_WhenExistsVehicleCategories_ThenReturnCountVehicleCategories() {
        assertEquals(ConstantsTestVehicleCategory.getAllTestVehicleCategories().size(), testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void deleteById_WhenDeleteWithNullAsVehicleCategoryId_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.deleteById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void deleteById_WhenDeleteNotExistVehicleCategory_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicleCategory.CATEGORY_ID_NOT_EXIST;
        assertFalse(testRepository.existsById(entityForDeleteId));
        testRepository.deleteById(entityForDeleteId);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:testdata_for_VehicleCategories_in_categories.sql" })
    void deleteById_WhenDeleteExistVehicleCategoryWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicleCategory.CATEGORY_ID_VALID_2;
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
    void deleteById_WhenDeleteExistVehicleCategoryWithWeavedVehicle_ThenException() {
        long countEntitiesBeforeAttempt = testRepository.count();
        Vehicle vehicle = ConstantsTestVehicle.VEHICLE_VALID_1;
        assertFalse(vehicle.getCategories().isEmpty());
        long entityForDeleteId = vehicle.getCategories().stream()
                .findAny().get()
                .getId();

        transactionTemplate = new TransactionTemplate(transactionManager);
        Optional<VehicleCategory> entityForDelete = transactionTemplate.execute(status -> {
            Optional<VehicleCategory> entity = testRepository.findById(entityForDeleteId);
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
