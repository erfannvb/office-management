package nvb.dev.officemanagement.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {

    private Long id;

    @NotEmpty(message = "username cannot be empty.")
    private String username;

    @NotEmpty(message = "password cannot be empty.")
    private String password;

}
