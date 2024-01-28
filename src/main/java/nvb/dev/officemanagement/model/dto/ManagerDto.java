package nvb.dev.officemanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.model.entity.OfficeEntity;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ManagerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String department;
    private Integer age;
    private OfficeEntity office;
    private Set<ClerkEntity> clerks = new HashSet<>();
    private Set<DocumentEntity> documents = new HashSet<>();

}
