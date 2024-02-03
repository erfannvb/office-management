package nvb.dev.officemanagement.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserPermission {

    ADMIN_WRITE("admin:write"),
    ADMIN_READ("admin:read"),
    USER_READ("user:read");

    private final String permission;

}
