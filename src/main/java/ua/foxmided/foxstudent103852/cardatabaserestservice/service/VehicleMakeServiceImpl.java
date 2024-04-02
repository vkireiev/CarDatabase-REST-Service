package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.VehicleMakeRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.VehicleMakeService;

@Service
public class VehicleMakeServiceImpl
        extends CrudServiceImpl<VehicleMake, Long>
        implements VehicleMakeService {

    @Value("${err.msg.service.make.add.fail}")
    private String addFailMessage;

    @Value("${err.msg.service.make.get.fail}")
    private String getFailMessage;

    @Value("${err.msg.service.make.getall.fail}")
    private String getAllFailMessage;

    @Value("${err.msg.service.make.update.fail}")
    private String updateFailMessage;

    @Value("${err.msg.service.make.delete.fail}")
    private String deleteFailMessage;

    @Value("${err.msg.service.make.count.fail}")
    private String countFailMessage;

    @Value("${err.msg.service.make.add.fail.integrity.violation}")
    private String addFailIntegrityViolationMessage;

    @Value("${err.msg.service.make.update.fail.integrity.violation}")
    private String updateFailIntegrityViolationMessage;

    @Value("${err.msg.service.make.delete.fail.integrity.violation}")
    private String deleteFailIntegrityViolationMessage;

    private final VehicleMakeRepository vehicleMakeRepository;

    public VehicleMakeServiceImpl(VehicleMakeRepository vehicleMakeRepository) {
        super(vehicleMakeRepository, VehicleMake.class);
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    @Override
    public Optional<VehicleMake> get(Long id) {
        return super.getImpl(id);
    }

    @Override
    public Page<VehicleMake> getAll(Pageable pageable) {
        return super.getAllImpl(pageable);
    }

    @Override
    public long count() {
        return super.countImpl();
    }

    @Override
    public VehicleMake add(VehicleMake make) {
        return super.addImpl(make);
    }

    @Override
    public VehicleMake update(VehicleMake make) {
        return super.updateImpl(make);
    }

    @Override
    public boolean delete(VehicleMake make) {
        return super.deleteImpl(make);
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
