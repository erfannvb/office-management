package nvb.dev.officemanagement.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    ROLE_ADMIN("role_admin"),
    ROLE_USER("role_user");

    private final String role;

}
