package tm.paro.clientmovieapp.auth;

import java.util.Optional;

public interface ClientUserDao {
    Optional<ClientUser> getClientUserByUsername(String username);
}
