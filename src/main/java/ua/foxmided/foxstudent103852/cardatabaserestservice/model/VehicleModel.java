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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.interfaces.Identifiable;

@Entity
@Table(name = "models", uniqueConstraints = @UniqueConstraint(name = "models_make_name_unique", columnNames = { "name",
        "make_id" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
@ToString
public class VehicleModel implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name", nullable = false, length = 50, columnDefinition = "VARCHAR(50) CONSTRAINT models_name_length_check CHECK(LENGTH(name) > 0 AND LENGTH(name) <= 50)")
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "make_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private VehicleMake make;

    @NotNull
    @ManyToMany
    @JoinTable(name = "models_model_years", joinColumns = @JoinColumn(name = "model_id"), inverseJoinColumns = @JoinColumn(name = "model_year_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "model_id", "model_year_id" }))
    private Set<@NotNull ModelYear> years = new HashSet<>();

}
