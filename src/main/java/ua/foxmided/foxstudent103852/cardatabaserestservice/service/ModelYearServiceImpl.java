package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.ModelYearRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.ModelYearService;

@Service
public class ModelYearServiceImpl
        extends CrudServiceImpl<ModelYear, Long>
        implements ModelYearService {

    @Value("${err.msg.service.year.add.fail}")
    private String addFailMessage;

    @Value("${err.msg.service.year.get.fail}")
    private String getFailMessage;

    @Value("${err.msg.service.year.getall.fail}")
    private String getAllFailMessage;

    @Value("${err.msg.service.year.update.fail}")
    private String updateFailMessage;

    @Value("${err.msg.service.year.delete.fail}")
    private String deleteFailMessage;

    @Value("${err.msg.service.year.count.fail}")
    private String countFailMessage;

    @Value("${err.msg.service.year.add.fail.integrity.violation}")
    private String addFailIntegrityViolationMessage;

    @Value("${err.msg.service.year.update.fail.integrity.violation}")
    private String updateFailIntegrityViolationMessage;

    @Value("${err.msg.service.year.delete.fail.integrity.violation}")
    private String deleteFailIntegrityViolationMessage;

    private final ModelYearRepository modelYearRepository;

    public ModelYearServiceImpl(ModelYearRepository modelYearRepository) {
        super(modelYearRepository, ModelYear.class);
        this.modelYearRepository = modelYearRepository;
    }

    @Override
    public Optional<ModelYear> get(Long id) {
        return super.getImpl(id);
    }

    @Override
    public Page<ModelYear> getAll(Pageable pageable) {
        return super.getAllImpl(pageable);
    }

    @Override
    public long count() {
        return super.countImpl();
    }

    @Override
    public ModelYear add(ModelYear year) {
        return super.addImpl(year);
    }

    @Override
    public ModelYear update(ModelYear year) {
        return super.updateImpl(year);
    }

    @Override
    public boolean delete(ModelYear year) {
        return super.deleteImpl(year);
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
