package ua.foxmided.foxstudent103852.cardatabaserestservice.util.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    protected final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return toCommonPredicate(root, query, criteriaBuilder);
    }

    private Predicate toCommonPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (criteria.getOperation().equalsIgnoreCase(">=")) {
            return criteriaBuilder
                    .greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        }

        if (criteria.getOperation().equalsIgnoreCase("<=")) {
            return criteriaBuilder
                    .lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        }

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder
                    .greaterThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        }

        if (criteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder
                    .lessThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        }

        if (criteria.getOperation().equalsIgnoreCase("==")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder
                        .lower(root.<String>get(criteria.getKey())).in(criteria.getValue().toString());
            } else {
                return criteriaBuilder
                        .equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }

        return null;
    }

}
