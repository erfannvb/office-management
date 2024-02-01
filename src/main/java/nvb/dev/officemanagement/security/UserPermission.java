package nvb.dev.officemanagement.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserPermission {

    MANAGER_WRITE("manager:write"),
    MANAGER_READ("manager:read"),
    CLERK_READ("clerk:read");

    private final String permission;

}
