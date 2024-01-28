package nvb.dev.officemanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.model.entity.OfficeEntity;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DocumentDto {

    private Long id;
    private String title;
    private String description;
    private Set<ManagerEntity> managers = new HashSet<>();
    private Set<ClerkEntity> clerks = new HashSet<>();
    private OfficeEntity office;

}
