package nvb.dev.officemanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvb.dev.officemanagement.model.Address;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.model.entity.ManagerEntity;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OfficeDto {

    private Long id;
    private String officeName;
    private String officeCode;
    private String officePhoneNumber;
    private Address address;
    private Set<ManagerEntity> managers = new HashSet<>();
    private Set<ClerkEntity> clerks = new HashSet<>();
    private Set<DocumentEntity> documents = new HashSet<>();

}
