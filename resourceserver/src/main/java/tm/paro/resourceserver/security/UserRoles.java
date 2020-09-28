package tm.paro.resourceserver.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum UserRoles {
    UCAYIM_FLIGHT_ADMIN(Sets.newHashSet(UserPermissions.DELETE, UserPermissions.PUT)),
    UCAYIM_USER(Sets.newHashSet(UserPermissions.GET, UserPermissions.POST));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions){
        this.permissions=permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions=getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }
}
