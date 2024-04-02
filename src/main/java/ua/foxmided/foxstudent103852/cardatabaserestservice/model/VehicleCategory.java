package ua.foxmided.foxstudent103852.cardatabaserestservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.interfaces.Identifiable;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
@ToString
public class VehicleCategory implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 25)
    @Column(name = "name", nullable = false, length = 25, unique = true, columnDefinition = "VARCHAR(25) CONSTRAINT categories_name_length_check CHECK(LENGTH(name) >= 2 AND LENGTH(name) <= 25)")
    private String name;

}
