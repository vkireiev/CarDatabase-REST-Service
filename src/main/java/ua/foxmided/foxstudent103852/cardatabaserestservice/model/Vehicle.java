package ua.foxmided.foxstudent103852.cardatabaserestservice.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.interfaces.Identifiable;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
@ToString
public class Vehicle implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 10)
    @Column(name = "object_id", nullable = false, length = 10, unique = true)
    private String objectId;

    @NotNull
    @ManyToOne(targetEntity = VehicleModel.class)
    @JoinColumn(name = "model_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private VehicleModel model;

    @NotNull
    @ManyToOne(targetEntity = ModelYear.class)
    @JoinColumn(name = "model_year_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private ModelYear year;

    @NotEmpty
    @ManyToMany(targetEntity = VehicleCategory.class)
    @JoinTable(name = "vehicles_categories", joinColumns = @JoinColumn(name = "vehicle_id"), inverseJoinColumns = @JoinColumn(name = "category_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "vehicle_id", "category_id" }))
    private Set<@NotNull VehicleCategory> categories = new HashSet<>();

    @PrePersist
    @PreUpdate
    public void checkModelYear() {
        if (this.getModel() == null) {
            throw new ValidationException("vehicle.model must not be null");
        }

        if (this.getModel().getYears() == null) {
            throw new ValidationException("vehicle.model.years must not be null");
        }

        if (this.getYear() == null) {
            throw new ValidationException("vehicle.year must not be null");
        }

        if (!this.getModel().getYears().contains(this.getYear())) {
            throw new EntityDataIntegrityViolationException("vehicle.year (ID=" + this.getYear().getId()
                    + ") is not present in model.years (ID=" + this.getModel().getId() + ")");
        }
    }

}
