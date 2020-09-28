package tm.paro.clientmovieapp.auth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientUserService  implements UserDetailsService {

    private final ClientUserDao clientUserDao;
    @Getter
    private ClientUser user;

    @Autowired
    public ClientUserService(ClientUserDao clientUserDao) {
        this.clientUserDao = clientUserDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         user=clientUserDao
                .getClientUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(String.format("Username %s not found", username)));


        return user;
    }
}
