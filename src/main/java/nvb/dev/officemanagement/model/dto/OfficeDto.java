package nvb.dev.officemanagement.model.dto;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "officeName cannot be empty.")
    private String officeName;

    @NotEmpty(message = "officeCode cannot be empty.")
    private String officeCode;

    @NotEmpty(message = "officePhoneNumber cannot be empty.")
    private String officePhoneNumber;

    private Address address;

}
