package nvb.dev.officemanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_clerk")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClerkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "firstName cannot be empty.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "lastName cannot be empty.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "department cannot be empty")
    @Column(name = "department", nullable = false)
    private String department;

    @Range(min = 18, max = 55)
    @Column(name = "age", nullable = false)
    private Integer age;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private ManagerEntity manager;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private OfficeEntity office;

    public ClerkEntity(String firstName, String lastName, String department, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.age = age;
    }
}
