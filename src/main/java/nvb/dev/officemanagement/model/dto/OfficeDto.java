package nvb.dev.officemanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvb.dev.officemanagement.model.Address;

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

}
