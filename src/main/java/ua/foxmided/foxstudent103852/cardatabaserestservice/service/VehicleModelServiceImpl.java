package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityNotFoundException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityUpdateDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.VehicleModelRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleModelService;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleService;

@Service
@Validated
public class VehicleModelServiceImpl
        extends CrudServiceImpl<VehicleModel, Long>
        implements VehicleModelService {

    @Value("${err.msg.service.model.add.fail}")
    private String addFailMessage;

    @Value("${err.msg.service.model.get.fail}")
    private String getFailMessage;

    @Value("${err.msg.service.model.getall.fail}")
    private String getAllFailMessage;

    @Value("${err.msg.service.model.update.fail}")
    private String updateFailMessage;

    @Value("${err.msg.service.model.delete.fail}")
    private String deleteFailMessage;

    @Value("${err.msg.service.model.count.fail}")
    private String countFailMessage;

    @Value("${err.msg.service.model.add.fail.integrity.violation}")
    private String addFailIntegrityViolationMessage;

    @Value("${err.msg.service.model.update.fail.integrity.violation}")
    private String updateFailIntegrityViolationMessage;

    @Value("${err.msg.service.model.delete.fail.integrity.violation}")
    private String deleteFailIntegrityViolationMessage;

    @Value("${err.msg.service.model.update.modelYears.fail.integrity.violation}")
    private String updateModelYearsFailIntegrityViolationMessage;

    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleService vehicleService;

    public VehicleModelServiceImpl(VehicleModelRepository vehicleModelRepository,
            VehicleService vehicleService) {
        super(vehicleModelRepository, VehicleModel.class);
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleService = vehicleService;
    }

    @Override
    public Optional<VehicleModel> get(Long id) {
        return super.getImpl(id);
    }

    @Override
    public Page<VehicleModel> getAll(Pageable pageable) {
        return super.getAllImpl(pageable);
    }

    @Override
    public long count() {
        return super.countImpl();
    }

    @Override
    public VehicleModel add(VehicleModel model) {
        return super.addImpl(model);
    }

    @Override
    public VehicleModel update(VehicleModel model) {
        try {
            VehicleModel persistedEntity = vehicleModelRepository.findById(model.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "VehicleModel (ID=" + model.getId() + ") not found"));

            checkVehicleModelYearsDataIntegrityViolation(model.getYears(), persistedEntity);

            return vehicleModelRepository.save(model);
        } catch (DataIntegrityViolationException e) {
            throw new EntityUpdateDataIntegrityViolationException(
                    updateFailIntegrityViolationMessage(), e);
        } catch (DataAccessException | IllegalArgumentException e) {
            throw new DataProcessingException(
                    String.format(updateFailMessage(), ("ID=" + model.getId())), e);
        }
    }

    @Override
    public boolean delete(VehicleModel model) {
        return super.deleteImpl(model);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteByIdImpl(id);
    }

    @Override
    public Page<VehicleModel> findAllByMake(VehicleMake make, Pageable pageable) {
        try {
            return vehicleModelRepository.findAllByMake(make, pageable);
        } catch (DataAccessException | IllegalArgumentException e) {
            throw new DataProcessingException(
                    String.format(getFailMessage, ("make=" + make)), e);
        }
    }

    @Override
    public VehicleModel modifyYears(@NotNull Long modelId, @NotNull @Valid ModelYear modelYear, boolean isRemove) {
        try {
            VehicleModel persistedEntity = vehicleModelRepository.findById(modelId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "VehicleModel (ID=" + modelId + ") not found"));

            Set<ModelYear> modelYears = new HashSet<>(persistedEntity.getYears());
            if (isRemove) {
                modelYears.remove(modelYear);
            } else {
                modelYears.add(modelYear);
            }
            checkVehicleModelYearsDataIntegrityViolation(modelYears, persistedEntity);
            persistedEntity.setYears(modelYears);

            return vehicleModelRepository.save(persistedEntity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityUpdateDataIntegrityViolationException(
                    updateFailIntegrityViolationMessage(), e);
        } catch (DataAccessException | IllegalArgumentException e) {
            throw new DataProcessingException(
                    String.format(updateFailMessage(), ("ID=" + modelId)), e);
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

    private void checkVehicleModelYearsDataIntegrityViolation(@NotNull final Set<ModelYear> years,
            @NotNull final VehicleModel persistedModel) {
        Set<ModelYear> entityYears = new HashSet<>(persistedModel.getYears());
        entityYears.removeAll(years);
        for (ModelYear year : entityYears) {
            if (vehicleService.existsByModelAndYear(persistedModel, year)) {
                throw new EntityUpdateDataIntegrityViolationException(
                        String.format(updateModelYearsFailIntegrityViolationMessage, ("ID=" + persistedModel.getId())));
            }
        }
    }

}
