package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleCategory;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.VehicleCategoryRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleCategoryService;

@Service
public class VehicleCategoryServiceImpl
        extends CrudServiceImpl<VehicleCategory, Long>
        implements VehicleCategoryService {

    @Value("${err.msg.service.category.add.fail}")
    private String addFailMessage;

    @Value("${err.msg.service.category.get.fail}")
    private String getFailMessage;

    @Value("${err.msg.service.category.getall.fail}")
    private String getAllFailMessage;

    @Value("${err.msg.service.category.update.fail}")
    private String updateFailMessage;

    @Value("${err.msg.service.category.delete.fail}")
    private String deleteFailMessage;

    @Value("${err.msg.service.category.count.fail}")
    private String countFailMessage;

    @Value("${err.msg.service.category.add.fail.integrity.violation}")
    private String addFailIntegrityViolationMessage;

    @Value("${err.msg.service.category.update.fail.integrity.violation}")
    private String updateFailIntegrityViolationMessage;

    @Value("${err.msg.service.category.delete.fail.integrity.violation}")
    private String deleteFailIntegrityViolationMessage;

    private final VehicleCategoryRepository vehicleCategoryRepository;

    public VehicleCategoryServiceImpl(VehicleCategoryRepository vehicleCategoryRepository) {
        super(vehicleCategoryRepository, VehicleCategory.class);
        this.vehicleCategoryRepository = vehicleCategoryRepository;
    }

    @Override
    public Optional<VehicleCategory> get(Long id) {
        return super.getImpl(id);
    }

    @Override
    public Page<VehicleCategory> getAll(Pageable pageable) {
        return super.getAllImpl(pageable);
    }

    @Override
    public long count() {
        return super.countImpl();
    }

    @Override
    public VehicleCategory add(VehicleCategory category) {
        return super.addImpl(category);
    }

    @Override
    public VehicleCategory update(VehicleCategory category) {
        return super.updateImpl(category);
    }

    @Override
    public boolean delete(VehicleCategory category) {
        return super.deleteImpl(category);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteByIdImpl(id);
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
