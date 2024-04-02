package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.interfaces.Identifiable;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.CrudEntityRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.ReadService;

public abstract class ReadServiceImpl<T extends Identifiable<U>, U>
        implements ReadService<T, U> {

    protected final Class<T> entityType;
    private final CrudEntityRepository<T, U> entityCrudRepository;

    protected ReadServiceImpl(CrudEntityRepository<T, U> entityCrudRepository,
            Class<T> entityType) {
        super();
        this.entityType = entityType;
        this.entityCrudRepository = entityCrudRepository;
    }

    protected Optional<T> getImpl(U id) {
        try {
            return entityCrudRepository.findById(id);
        } catch (DataAccessException | IllegalArgumentException e) {
            throw new DataProcessingException(
                    String.format(getFailMessage(), ("ID=" + id)), e);
        }
    }

    protected Page<T> getAllImpl(Pageable pageable) {
        try {
            return entityCrudRepository.findAll(pageable);
        } catch (DataAccessException e) {
            throw new DataProcessingException(getAllFailMessage(), e);
        }
    }

    protected long countImpl() {
        try {
            return entityCrudRepository.count();
        } catch (DataAccessException e) {
            throw new DataProcessingException(countFailMessage(), e);
        }
    }

    protected abstract String getFailMessage();

    protected abstract String getAllFailMessage();

    protected abstract String countFailMessage();

}
