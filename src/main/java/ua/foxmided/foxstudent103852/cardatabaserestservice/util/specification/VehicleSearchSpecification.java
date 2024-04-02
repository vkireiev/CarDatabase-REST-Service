package ua.foxmided.foxstudent103852.cardatabaserestservice.util.specification;

import java.time.Year;
import java.util.Set;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.ModelYear;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.Vehicle;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleCategory;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleMake;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.VehicleModel;

public class VehicleSearchSpecification extends SearchSpecification<Vehicle> {

    public VehicleSearchSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (criteria.getOperation().equalsIgnoreCase("sort")) {
            Path<?> path = switch (criteria.getKey().toLowerCase()) {
            case "id" -> root.<String>get("id");
            case "make" -> makeJoin(root).<String>get("name");
            case "model" -> modelJoin(root).<String>get("name");
            case "year" -> yearJoin(root).<Year>get("year");
            default -> throw new DataProcessingException("Unexpected value: " + criteria.getKey());
            };

            if (criteria.isAscending()) {
                query.orderBy(criteriaBuilder.asc(path));
            } else {
                query.orderBy(criteriaBuilder.desc(path));
            }
        }

        if (criteria.getKey().equalsIgnoreCase("make") && criteria.getOperation().equalsIgnoreCase("==")) {
            return criteriaBuilder
                    .lower(makeJoin(root).<String>get("name")).in(criteria.getValue().toString().toLowerCase());
        }

        if (criteria.getKey().equalsIgnoreCase("model") && criteria.getOperation().equalsIgnoreCase("==")) {
            return criteriaBuilder
                    .lower(modelJoin(root).<String>get("name")).in(criteria.getValue().toString().toLowerCase());
        }

        if (criteria.getKey().equalsIgnoreCase("year") && criteria.getOperation().equalsIgnoreCase("==")) {
            return criteriaBuilder
                    .equal(yearJoin(root).<Year>get("year"), (Year) criteria.getValue());
        }

        if (criteria.getKey().equalsIgnoreCase("year") && criteria.getOperation().equalsIgnoreCase("<=")) {
            return criteriaBuilder
                    .lessThanOrEqualTo(yearJoin(root).<Year>get("year"), (Year) criteria.getValue());
        }

        if (criteria.getKey().equalsIgnoreCase("year") && criteria.getOperation().equalsIgnoreCase(">=")) {
            return criteriaBuilder
                    .greaterThanOrEqualTo(yearJoin(root).<Year>get("year"), (Year) criteria.getValue());
        }

        if (criteria.getKey().equalsIgnoreCase("category") && criteria.getOperation().equalsIgnoreCase("==")) {
            return criteriaBuilder
                    .lower(categoriesJoin(root).<String>get("name")).in(criteria.getValue().toString().toLowerCase());
        }

        return super.toPredicate(root, query, criteriaBuilder);
    }

    private Join<Vehicle, VehicleModel> modelJoin(Root<Vehicle> root) {
        return root.join("model");
    }

    private Join<Vehicle, ModelYear> yearJoin(Root<Vehicle> root) {
        return root.join("year");
    }

    private Join<Vehicle, Set<VehicleCategory>> categoriesJoin(Root<Vehicle> root) {
        return root.join("categories");
    }

    private Join<Vehicle, VehicleMake> makeJoin(Root<Vehicle> root) {
        return modelJoin(root).join("make");
    }

}
