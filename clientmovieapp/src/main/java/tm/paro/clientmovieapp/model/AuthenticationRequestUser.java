package tm.paro.clientmovieapp.model;

import lombok.Data;

@Data
public class AuthenticationRequestUser {
    private String username;
    private String password;
}
