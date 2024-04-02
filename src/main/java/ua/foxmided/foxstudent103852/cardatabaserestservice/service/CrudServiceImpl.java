package ua.foxmided.foxstudent103852.cardatabaserestservice.service;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityAddDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityNotFoundException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityUpdateDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.interfaces.Identifiable;
import ua.foxmided.foxstudent103852.cardatabaserestservice.repository.CrudEntityRepository;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.CrudService;

public abstract class CrudServiceImpl<T extends Identifiable<U>, U>
        extends ReadServiceImpl<T, U>
        implements CrudService<T, U> {

    private final CrudEntityRepository<T, U> entityCrudRepository;

    protected CrudServiceImpl(CrudEntityRepository<T, U> entityCrudRepository, Class<T> entityType) {
        super(entityCrudRepository, entityType);
        this.entityCrudRepository = entityCrudRepository;
    }

    protected T addImpl(T entity) {
        try {
            return entityCrudRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAddDataIntegrityViolationException(
                    addFailIntegrityViolationMessage(), e);
        } catch (DataAccessException | IllegalArgumentException e) {
            throw new DataProcessingException(addFailMessage(), e);
        }
    }

    protected T updateImpl(T entity) {
        try {
            if (!entityCrudRepository.existsById(entity.getId())) {
                throw new EntityNotFoundException(
                        entityType.getSimpleName() + " (ID=" + entity.getId() + ") not found");
            }
            return entityCrudRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityUpdateDataIntegrityViolationException(
                    updateFailIntegrityViolationMessage(), e);
        } catch (DataAccessException | IllegalArgumentException e) {
            throw new DataProcessingException(
                    String.format(updateFailMessage(), ("ID=" + entity.getId())), e);
        }
    }

    protected boolean deleteImpl(T entity) {
        try {
            entityCrudRepository.delete(entity);
            return !entityCrudRepository.existsById(entity.getId());
        } catch (DataIntegrityViolationException e) {
            throw new EntityDataIntegrityViolationException(
                    String.format(deleteFailIntegrityViolationMessage(), ("ID=" + entity.getId())), e);
        } catch (DataAccessException | IllegalArgumentException e) {
            throw new DataProcessingException(
                    String.format(deleteFailMessage(), ("ID=" + entity.getId())), e);
        }
    }

    protected boolean deleteByIdImpl(U id) {
        try {
            entityCrudRepository.deleteById(id);
            return !entityCrudRepository.existsById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityDataIntegrityViolationException(
                    String.format(deleteFailIntegrityViolationMessage(), ("ID=" + id)), e);
        } catch (DataAccessException | IllegalArgumentException e) {
            throw new DataProcessingException(
                    String.format(deleteFailMessage(), ("ID=" + id)), e);
        }
    }

    protected abstract String addFailMessage();

    protected abstract String updateFailMessage();

    protected abstract String deleteFailMessage();

    protected abstract String addFailIntegrityViolationMessage();

    protected abstract String updateFailIntegrityViolationMessage();

    protected abstract String deleteFailIntegrityViolationMessage();

}
