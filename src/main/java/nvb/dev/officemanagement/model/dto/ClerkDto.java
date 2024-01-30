package nvb.dev.officemanagement.model.dto;

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
    private String firstName;
    private String lastName;
    private String department;
    private Integer age;
    private ManagerEntity manager;
    private OfficeEntity office;
}
