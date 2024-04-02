package ua.foxmided.foxstudent103852.cardatabaserestservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.validation.ValidationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleCategory;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTest;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleCategory;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.ConstantsTestVehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.specification.SearchCriteria;
import ua.foxmided.foxstudent103852.cardatabaserestservice.util.specification.VehicleSearchSpecification;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository testRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void save_WhenAddVehicleWithNullAsVehicle_ThenException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> testRepository.save(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @CsvSource({
            ConstantsTestVehicle.VEHICLE_OBJECT_ID_INVALID_1
    })
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenAddVehicleWithInvalidObjectId_ThenException(String parameterizedObjectId) {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        newEntity.setObjectId(parameterizedObjectId);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenAddVehicleWithNullAsModel_ThenException() {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        newEntity.setModel(null);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenAddVehicleWithNotExistModel_ThenException() {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        newEntity.setModel(ConstantsTestVehicleModel.NOT_EXIST_MODEL);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(EntityDataIntegrityViolationException.class,
                () -> testRepository.save(newEntity),
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenAddVehicleWithNullAsModelYear_ThenException() {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        newEntity.setYear(null);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
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
    void save_WhenAddVehicleWithNotExistModelYear_ThenException() {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        newEntity.setYear(ConstantsTestModelYear.NOT_EXIST_YEAR);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(EntityDataIntegrityViolationException.class,
                () -> {
                    testRepository.save(newEntity);
                    testEntityManager.flush();
                },
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
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
    void save_WhenAddVehicleWithModelYearNotExistingInModelYears_ThenException() {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        ModelYear modelYear = ConstantsTestModelYear.getTestModelYear(ConstantsTestModelYear.YEAR_ID_FOR_UPDATE);
        assertFalse(newEntity.getModel().getYears().contains(modelYear));
        newEntity.setYear(modelYear);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(EntityDataIntegrityViolationException.class,
                () -> {
                    testRepository.save(newEntity);
                    testEntityManager.flush();
                },
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenAddVehicleWithNullAsCategories_ThenException() {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        newEntity.setCategories(null);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
        assertNull(this.testEntityManager.getId(newEntity));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenAddVehicleWithNullAsVehicleCategory_ThenException() {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        newEntity.getCategories().add(null);
        assertNull(this.testEntityManager.getId(newEntity));
        assertThrows(ValidationException.class, () -> testRepository.save(newEntity),
                ConstantsTest.VALIDATION_EXCEPTION_EXPECTED_MESSAGE);
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
    void save_WhenAddVehicleWithNotExistVehicleCategory_ThenException() {
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        newEntity.getCategories().add(ConstantsTestVehicleCategory.NOT_EXIST_CATEGORY);
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
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenAddValidVehicle_ThenShouldAddAndReturnAddedEntity() {
        long countEntitiesBeforeAttempt = testRepository.count();
        Vehicle newEntity = ConstantsTestVehicle.newValidVehicle();
        newEntity.setObjectId(ConstantsTestVehicle.VEHICLE_OBJECT_ID_VALID_FOR_ADD);
        assertTrue(newEntity.getModel().getYears().contains(newEntity.getYear()));
        assertNull(this.testEntityManager.getId(newEntity));
        Vehicle returnedEntity = testRepository.save(newEntity);
        assertNotNull(returnedEntity.getId());
        assertNotNull(this.testEntityManager.getId(newEntity));
        long countEntitiesAfterAttempt = testRepository.count();
        assertTrue(countEntitiesBeforeAttempt < countEntitiesAfterAttempt);
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
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void existsById_WhenNotExistVehicle_ThenReturnFalse() {
        assertFalse(testRepository.existsById(ConstantsTestVehicle.VEHICLE_ID_NOT_EXIST));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void existsById_WhenExistVehicle_ThenReturnTrue() {
        assertTrue(testRepository.existsById(ConstantsTestVehicle.VEHICLE_ID_VALID_1));
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
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void findById_WhenNotExistVehicle_ThenReturnOptionalNull() {
        assertFalse(testRepository.findById(ConstantsTestVehicle.VEHICLE_ID_NOT_EXIST).isPresent());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void findById_WhenExistVehicle_ThenReturnOptionalVehicle() {
        long entityId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> expectedEntity = Optional
                .of(ConstantsTestVehicle.getTestVehicle(entityId));

        Optional<Vehicle> returnedEntity = testRepository.findById(entityId);
        assertTrue(returnedEntity.isPresent());
        assertThat(returnedEntity.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedEntity.get());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void findAll_WhenNotExistsVehicles_ThenReturnEmptyPagedListOfVehicles() {
        assertEquals(0, testRepository.count());

        List<Specification<Vehicle>> conditions = new LinkedList<>();
        conditions.add(new VehicleSearchSpecification(
                SearchCriteria.of("objectId", "==", ConstantsTestVehicle.VEHICLE_OBJECT_ID_INVALID_1)));

        Page<Vehicle> returnedResult = testRepository
                .findAll(
                        Specification.allOf(conditions),
                        Pageable.ofSize(Integer.MAX_VALUE));
        assertThat(returnedResult).isEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void findAll_WhenExistsVehiclesAndNotExistSearchVehicles_ThenReturnEmptyPagedListOfVehicles() {
        assertNotEquals(0, testRepository.count());
        List<Vehicle> expectedResult = ConstantsTestVehicle.getAllTestVehicles().stream()
                .filter(vehicle -> vehicle.getObjectId().equals(ConstantsTestVehicle.VEHICLE_OBJECT_ID_INVALID_1))
                .filter(vehicle -> vehicle.getModel().getMake().equals(ConstantsTestVehicleMake.MAKE_VALID_2))
                .filter(vehicle -> vehicle.getYear().equals(ConstantsTestModelYear.YEAR_VALID_3))
                .collect(Collectors.toList());
        assertThat(expectedResult).isEmpty();

        List<Specification<Vehicle>> conditions = new LinkedList<>();
        conditions.add(new VehicleSearchSpecification(
                SearchCriteria.of("objectId", "==", ConstantsTestVehicle.VEHICLE_OBJECT_ID_INVALID_1)));
        conditions.add(new VehicleSearchSpecification(
                SearchCriteria.of("make", "==", ConstantsTestVehicleMake.MAKE_VALID_2.getName())));
        conditions.add(new VehicleSearchSpecification(
                SearchCriteria.of("year", "==", ConstantsTestModelYear.YEAR_VALID_3.getYear())));
        Page<Vehicle> returnedResult = testRepository
                .findAll(
                        Specification.allOf(conditions),
                        Pageable.ofSize(Integer.MAX_VALUE));
        assertThat(returnedResult).isEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void findAll_WhenExistsVehiclesAndExistSearchVehicles_ThenReturnSortedAndPagedListOfVehicles() {
        assertNotEquals(0, testRepository.count());
        List<Vehicle> filteredEntities = ConstantsTestVehicle.getAllTestVehicles().stream()
                .filter(vehicle -> vehicle.getCategories().contains(ConstantsTestVehicleCategory.CATEGORY_VALID_1))
                .filter(vehicle -> vehicle.getYear().equals(ConstantsTestModelYear.YEAR_VALID_3))
                .sorted((vehicle1, vehicle2) -> vehicle2.getId().compareTo(vehicle1.getId()))
                .collect(Collectors.toList());
        assertThat(filteredEntities).isNotEmpty();
        int page = 1;
        int pageSize = 2;
        assertTrue(filteredEntities.size() > (pageSize * page));
        List<Vehicle> expectedResult = filteredEntities.stream()
                .skip(page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        List<Specification<Vehicle>> conditions = new LinkedList<>();
        conditions.add(new VehicleSearchSpecification(
                SearchCriteria.of("category", "==", ConstantsTestVehicleCategory.CATEGORY_VALID_1.getName())));
        conditions.add(new VehicleSearchSpecification(
                SearchCriteria.of("year", "==", ConstantsTestModelYear.YEAR_VALID_3.getYear())));

        Page<Vehicle> returnedResult = testRepository
                .findAll(
                        Specification.allOf(conditions),
                        PageRequest.of(page, pageSize, Sort.by(List.of(Order.desc("id")))));
        assertThat(returnedResult).isNotEmpty();
        assertTrue(returnedResult.getPageable().isPaged());
        assertTrue(returnedResult.getSort().isSorted());
        assertEquals(pageSize, returnedResult.getPageable().getPageSize());
        assertEquals(filteredEntities.size(), returnedResult.getTotalElements());
        assertThat(returnedResult.getContent())
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @CsvSource({
            ConstantsTestVehicle.VEHICLE_OBJECT_ID_INVALID_1
    })
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenUpdateExistVehicleWithInvalidObjectId_ThenException(String parameterizedObjectId) {
        long entityForUpdateId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        entityForUpdate.get().setObjectId(parameterizedObjectId);
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
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenUpdateExistVehicleWithNullAsModel_ThenException() {
        long entityForUpdateId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        entityForUpdate.get().setModel(null);
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
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenUpdateExistVehicleWithNotExistModel_ThenException() {
        long entityForUpdateId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        entityForUpdate.get().setModel(ConstantsTestVehicleModel.NOT_EXIST_MODEL);
        assertThrows(EntityDataIntegrityViolationException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    testEntityManager.flush();
                },
                ConstantsTest.ENTITY_DATA_INTEGRITY_VIOLATION_EXCEPTION_EXPECTED_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenUpdateExistVehicleWithNullAsModelYear_ThenException() {
        long entityForUpdateId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> entityForUpdate = testRepository.findById(entityForUpdateId);
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
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenUpdateExistVehicleWithNotExistModelYear_ThenException() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long entityForUpdateId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            return vehicle;
        });
        Optional<Vehicle> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            return vehicle;
        });
        entityForUpdate.get().setYear(ConstantsTestModelYear.NOT_EXIST_YEAR);
        assertThrows(DataAccessException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    testEntityManager.flush();
                },
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
        Optional<Vehicle> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            return vehicle;
        });
        assertEquals(entityBeforeAttempt.get(), entityAfterAttempt.get());
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
    void save_WhenUpdateExistVehicleWithModelYearNotExistingInModelYears_ThenException() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long entityForUpdateId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            return vehicle;
        });
        Optional<Vehicle> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            return vehicle;
        });
        ModelYear modelYear = ConstantsTestModelYear.YEAR_VALID_2;
        assertFalse(entityForUpdate.get().getModel().getYears().contains(modelYear));
        entityForUpdate.get().setYear(ConstantsTestModelYear.NOT_EXIST_YEAR);
        assertThrows(DataAccessException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    testEntityManager.flush();
                },
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
        Optional<Vehicle> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            return vehicle;
        });
        assertEquals(entityBeforeAttempt.get(), entityAfterAttempt.get());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenUpdateExistVehicleWithNullAsCategories_ThenException() {
        long entityForUpdateId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> entityForUpdate = testRepository.findById(entityForUpdateId);
        assertTrue(entityForUpdate.isPresent());
        entityForUpdate.get().setCategories(null);
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
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void save_WhenUpdateExistVehicleWithNotExistVehicleCategory_ThenException() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long entityForUpdateId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            assertFalse(vehicle.get().getCategories().isEmpty());
            return vehicle;
        });
        Optional<Vehicle> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            assertFalse(vehicle.get().getCategories().isEmpty());
            return vehicle;
        });
        VehicleCategory vehicleCategory = ConstantsTestVehicleCategory.NOT_EXIST_CATEGORY;
        assertFalse(entityForUpdate.get().getCategories().contains(vehicleCategory));
        entityForUpdate.get().getCategories().add(vehicleCategory);
        assertThrows(DataAccessException.class,
                () -> {
                    testRepository.save(entityForUpdate.get());
                    testEntityManager.flush();
                },
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
        Optional<Vehicle> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            assertFalse(vehicle.get().getCategories().isEmpty());
            return vehicle;
        });
        assertEquals(entityBeforeAttempt.get(), entityAfterAttempt.get());
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
    void save_WhenUpdateExistVehicleWithHappyPath_ThenShouldUpdate() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        long entityForUpdateId = ConstantsTestVehicle.VEHICLE_ID_VALID_1;
        Optional<Vehicle> entityBeforeAttempt = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            assertFalse(vehicle.get().getCategories().isEmpty());
            return vehicle;
        });
        Optional<Vehicle> entityForUpdate = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            assertFalse(vehicle.get().getCategories().isEmpty());
            return vehicle;
        });

        entityForUpdate.get().setObjectId(ConstantsTestVehicle.VEHICLE_OBJECT_ID_VALID_FOR_UPDATE);
        entityForUpdate.get().setModel(ConstantsTestVehicle.VEHICLE_MODEL_VALID_FOR_UPDATE);
        entityForUpdate.get().setYear(ConstantsTestVehicle.VEHICLE_YEAR_VALID_1);
        entityForUpdate.get().setCategories(new HashSet<>(ConstantsTestVehicle.VEHICLE_CATEGORIES_VALID_1));

        testRepository.save(entityForUpdate.get());

        Optional<Vehicle> entityAfterAttempt = transactionTemplate.execute(status -> {
            Optional<Vehicle> vehicle = testRepository.findById(entityForUpdateId);
            assertTrue(vehicle.isPresent());
            assertNotNull(vehicle.get().getModel());
            assertNotNull(vehicle.get().getModel().getYears());
            assertFalse(vehicle.get().getModel().getYears().isEmpty());
            assertFalse(vehicle.get().getCategories().isEmpty());
            return vehicle;
        });
        assertThat(entityAfterAttempt.get())
                .usingRecursiveComparison()
                .isNotEqualTo(entityBeforeAttempt.get());
        assertEquals(ConstantsTestVehicle.VEHICLE_OBJECT_ID_VALID_FOR_UPDATE, entityAfterAttempt.get().getObjectId());
        assertEquals(ConstantsTestVehicle.VEHICLE_MODEL_VALID_FOR_UPDATE, entityAfterAttempt.get().getModel());
        assertEquals(ConstantsTestVehicle.VEHICLE_YEAR_VALID_1, entityAfterAttempt.get().getYear());
        assertThat(entityAfterAttempt.get().getCategories())
                .usingRecursiveComparison()
                .isEqualTo(ConstantsTestVehicle.VEHICLE_CATEGORIES_VALID_1);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void delete_WhenDeleteNotExistVehicle_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        Vehicle entityForDelete = ConstantsTestVehicle.NOT_EXIST_VEHICLE;
        assertFalse(testRepository.findById(entityForDelete.getId()).isPresent());
        testRepository.delete(entityForDelete);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void delete_WhenDeleteExistVehicleWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicle.VEHICLE_ID_FOR_DELETE;
        Optional<Vehicle> entityForDelete = testRepository.findById(entityForDeleteId);
        assertTrue(entityForDelete.isPresent());
        testRepository.delete(entityForDelete.get());
        testEntityManager.flush();
        assertFalse(testRepository.findById(entityForDeleteId).isPresent());
        long countEntitiesAfterAttempt = testRepository.count();
        assertTrue(countEntitiesBeforeAttempt > countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void count_WhenNotExistsVehicles_ThenReturnZero() {
        assertEquals(0L, testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void count_WhenExistsVehicles_ThenReturnCountVehicles() {
        assertEquals(ConstantsTestVehicle.getAllTestVehicles().size(), testRepository.count());
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void deleteById_WhenDeleteWithNullAsVehicleId_ThenException() {
        assertThrows(DataAccessException.class, () -> testRepository.deleteById(null),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void deleteById_WhenDeleteNotExistVehicle_ThenNothingWillBeDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicle.VEHICLE_ID_NOT_EXIST;
        assertFalse(testRepository.existsById(entityForDeleteId));
        testRepository.deleteById(entityForDeleteId);
        testEntityManager.flush();
        long countEntitiesAfterAttempt = testRepository.count();
        assertEquals(countEntitiesBeforeAttempt, countEntitiesAfterAttempt);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void deleteById_WhenDeleteExistVehicleWithHappyPath_ThenDeleted() {
        long countEntitiesBeforeAttempt = testRepository.count();
        long entityForDeleteId = ConstantsTestVehicle.VEHICLE_ID_FOR_DELETE;
        assertTrue(testRepository.existsById(entityForDeleteId));
        testRepository.deleteById(entityForDeleteId);
        testEntityManager.flush();
        assertFalse(testRepository.existsById(entityForDeleteId));
        long countEntitiesAfterAttempt = testRepository.count();
        assertTrue(countEntitiesBeforeAttempt > countEntitiesAfterAttempt);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForExistsByModelAndModelYearMethodWithResultFalse")
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void existsByModelAndYear_WhenCalledWithNullAsModelOrNullAsModelYear_ThenReturnFalse(
            VehicleModel parameterizedModel, ModelYear parameterizedModelYear) {
        assertFalse(testRepository.existsByModelAndYear(parameterizedModel, parameterizedModelYear));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArgumentsForExistsByModelAndModelYearMethod")
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void existsByModelAndYear_WhenCalledWithInvalidArguments_ThenException(
            VehicleModel parameterizedModel, ModelYear parameterizedModelYear) {
        assertThrows(DataAccessException.class,
                () -> testRepository.existsByModelAndYear(parameterizedModel, parameterizedModelYear),
                ConstantsTest.DATA_ACCESS_EXCEPTION_MESSAGE);
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    void existsByModelAndYear_WhenCalledAndNotExistsVehicles_ThenReturnFalse() {
        assertFalse(testRepository.count() > 0);
        assertFalse(testRepository.existsByModelAndYear(
                ConstantsTestVehicleModel.MODEL_VALID_1,
                ConstantsTestModelYear.YEAR_VALID_1));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void existsByModelAndYear_WhenCalledAndNotExistVehicleWithSuchVehicleModelAndModelYear_ThenReturnFalse() {
        assertTrue(testRepository.count() > 0);
        Vehicle entityForTest = ConstantsTestVehicle.VEHICLE_VALID_1;

        assertTrue(testRepository.existsById(entityForTest.getId()));
        assertFalse(testRepository.existsByModelAndYear(
                ConstantsTestVehicleModel.NOT_EXIST_MODEL,
                entityForTest.getYear()));
        assertFalse(testRepository.existsByModelAndYear(
                entityForTest.getModel(),
                ConstantsTestModelYear.NOT_EXIST_YEAR));
    }

    @Test
    @Sql(scripts = { "classpath:clear_tables.sql" })
    @Sql(scripts = {
            "classpath:testdata_for_VehicleCategories_in_categories.sql",
            "classpath:testdata_for_VehicleMakes_in_makes.sql",
            "classpath:testdata_for_VehicleModels_in_models.sql",
            "classpath:testdata_for_ModelYears_in_model_years.sql",
            "classpath:testdata_for_VehicleModels_in_models_model_years.sql",
            "classpath:testdata_for_Vehicles_in_vehicles.sql",
            "classpath:testdata_for_Vehicles_in_vehicles_categories.sql" })
    void existsByModelAndYear_WhenCalledAndExistVehicleWithSuchVehicleModelAndModelYear_ThenReturnTrue() {
        assertTrue(testRepository.count() > 0);
        Vehicle entityForTest = ConstantsTestVehicle.VEHICLE_VALID_1;

        assertTrue(testRepository.existsById(entityForTest.getId()));
        assertTrue(testRepository.existsByModelAndYear(
                entityForTest.getModel(),
                entityForTest.getYear()));
    }

    private static Stream<Arguments> provideArgumentsForExistsByModelAndModelYearMethodWithResultFalse() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(ConstantsTestVehicleModel.NOT_EXIST_MODEL, null),
                Arguments.of(null, ConstantsTestModelYear.NOT_EXIST_YEAR));
    }

    private static Stream<Arguments> provideInvalidArgumentsForExistsByModelAndModelYearMethod() {
        return Stream.of(
                Arguments.of(ConstantsTestVehicleModel.newValidVehicleModel(), null),
                Arguments.of(ConstantsTestVehicleModel.newValidVehicleModel(), ConstantsTestModelYear.NOT_EXIST_YEAR),
                Arguments.of(null, ConstantsTestModelYear.newValidModelYear()),
                Arguments.of(ConstantsTestVehicleModel.NOT_EXIST_MODEL, ConstantsTestModelYear.newValidModelYear()),
                Arguments.of(ConstantsTestVehicleModel.newValidVehicleModel(),
                        ConstantsTestModelYear.newValidModelYear()));
    }

}
