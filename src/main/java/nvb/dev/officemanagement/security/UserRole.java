package nvb.dev.officemanagement.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static nvb.dev.officemanagement.security.UserPermission.*;

@AllArgsConstructor
@Getter
public enum UserRole {

    MANAGER(Set.of(MANAGER_WRITE, MANAGER_READ)),
    CLERK(Set.of(CLERK_READ));

    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> grantedAuthorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }

}
