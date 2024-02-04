package nvb.dev.officemanagement.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserPermission {

    DOC_WRITE("document:write"),
    DOC_READ("document:read");

    private final String permission;

}
