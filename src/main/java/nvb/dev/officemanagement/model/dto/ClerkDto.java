package nvb.dev.officemanagement.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.model.entity.OfficeEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClerkDto {

    private Long id;

    @NotEmpty(message = "firstName cannot be empty.")
    private String firstName;

    @NotEmpty(message = "lastName cannot be empty.")
    private String lastName;

    @NotEmpty(message = "department cannot be empty.")
    private String department;

    @NotEmpty(message = "age cannot be empty.")
    private Integer age;

    private ManagerEntity manager;

    private OfficeEntity office;

}
