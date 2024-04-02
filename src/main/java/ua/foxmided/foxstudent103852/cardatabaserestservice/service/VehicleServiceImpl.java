package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.VehicleRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleService;

@Service
public class VehicleServiceImpl
        extends CrudServiceImpl<Vehicle, Long>
        implements VehicleService {

    @Value("${err.msg.service.vehicle.add.fail}")
    private String addFailMessage;

    @Value("${err.msg.service.vehicle.get.fail}")
    private String getFailMessage;

    @Value("${err.msg.service.vehicle.getall.fail}")
    private String getAllFailMessage;

    @Value("${err.msg.service.vehicle.update.fail}")
    private String updateFailMessage;

    @Value("${err.msg.service.vehicle.delete.fail}")
    private String deleteFailMessage;

    @Value("${err.msg.service.vehicle.count.fail}")
    private String countFailMessage;

    @Value("${err.msg.service.vehicle.add.fail.integrity.violation}")
    private String addFailIntegrityViolationMessage;

    @Value("${err.msg.service.vehicle.update.fail.integrity.violation}")
    private String updateFailIntegrityViolationMessage;

    @Value("${err.msg.service.vehicle.delete.fail.integrity.violation}")
    private String deleteFailIntegrityViolationMessage;

    @Value("${err.msg.service.vehicle.existsByModelAndYear.fail}")
    private String existsByModelAndYearFailMessage;

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        super(vehicleRepository, Vehicle.class);
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> get(Long id) {
        return super.getImpl(id);
    }

    @Override
    public Page<Vehicle> getAll(Pageable pageable) {
        return super.getAllImpl(pageable);
    }

    @Override
    public long count() {
        return super.countImpl();
    }

    @Override
    public Vehicle add(Vehicle vehicle) {
        return super.addImpl(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        return super.updateImpl(vehicle);
    }

    @Override
    public boolean delete(Vehicle vehicle) {
        return super.deleteImpl(vehicle);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteByIdImpl(id);
    }

    @Override
    public boolean existsByModelAndYear(VehicleModel model, ModelYear year) {
        try {
            return vehicleRepository.existsByModelAndYear(model, year);
        } catch (DataAccessException e) {
            throw new DataProcessingException(existsByModelAndYearFailMessage, e);
        }
    }

    @Override
    public Page<Vehicle> findAll(Specification<Vehicle> specification, Pageable pageable) {
        try {
            return vehicleRepository.findAll(specification, pageable);
        } catch (DataAccessException e) {
            throw new DataProcessingException(getAllFailMessage(), e);
        }
    }

    @Override
    protected String addFailMessage() {
        return addFailMessage;
    }

    @Override
    protected String getFailMessage() {
        return getFailMessage;
    }

    @Override
    protected String getAllFailMessage() {
        return getAllFailMessage;
    }

    @Override
    protected String updateFailMessage() {
        return updateFailMessage;
    }

    @Override
    protected String deleteFailMessage() {
        return deleteFailMessage;
    }

    @Override
    protected String countFailMessage() {
        return countFailMessage;
    }

    @Override
    protected String addFailIntegrityViolationMessage() {
        return addFailIntegrityViolationMessage;
    }

    @Override
    protected String updateFailIntegrityViolationMessage() {
        return updateFailIntegrityViolationMessage;
    }

    @Override
    protected String deleteFailIntegrityViolationMessage() {
        return deleteFailIntegrityViolationMessage;
    }

}
