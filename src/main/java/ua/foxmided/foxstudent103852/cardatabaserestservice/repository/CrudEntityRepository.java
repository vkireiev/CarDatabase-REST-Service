package ua.foxmided.foxstudent103852.cardatabaserestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudEntityRepository<T, U> extends JpaRepository<T, U>, JpaSpecificationExecutor<T> {

}
