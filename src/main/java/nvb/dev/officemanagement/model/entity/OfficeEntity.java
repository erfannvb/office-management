package nvb.dev.officemanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvb.dev.officemanagement.model.Address;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_office")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OfficeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "officeName cannot be empty.")
    @Column(name = "office_name", nullable = false)
    private String officeName;

    @NotEmpty(message = "officeCode cannot be empty.")
    @Column(name = "office_code", nullable = false)
    private String officeCode;

    @NotEmpty(message = "officePhoneNumber cannot be empty.")
    @Column(name = "office_phone_number")
    private String officePhoneNumber;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL)
    private Set<ManagerEntity> managers = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "office")
    private Set<ClerkEntity> clerks = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL)
    private Set<DocumentEntity> documents = new HashSet<>();

    public OfficeEntity(String officeName, String officeCode, String officePhoneNumber, Address address) {
        this.officeName = officeName;
        this.officeCode = officeCode;
        this.officePhoneNumber = officePhoneNumber;
        this.address = address;
    }
}
