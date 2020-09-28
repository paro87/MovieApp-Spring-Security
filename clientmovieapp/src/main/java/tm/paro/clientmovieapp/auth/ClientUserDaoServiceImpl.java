package tm.paro.clientmovieapp.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class ClientUserDaoServiceImpl implements ClientUserDao{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientUserDaoServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ClientUser> getClientUserByUsername(String username) {
        return getClientUsers()
                .stream()
                .filter(clientUser -> username.equals(clientUser.getUsername()))
                .findFirst();
    }

    private List<ClientUser> getClientUsers() {

        List<ClientUser> clientUsers= Lists.newArrayList(
                new ClientUser("test", passwordEncoder.encode("test"), getGrantedAuthorities(), true, true, true, true
                )
        );
        return clientUsers;
    }


    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions=new HashSet<>();
        permissions.add(new SimpleGrantedAuthority("ROLE_USER"));
        return permissions;
    }
}
