package nvb.dev.officemanagement.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Address {

    @NotEmpty(message = "city cannot be empty.")
    private String city;

    @NotEmpty(message = "country cannot be empty.")
    private String country;

}
