package tm.paro.resourceserver.model.jwt;

import lombok.Data;

import java.util.List;

@Data
public class Realm_access {
    private List<String> roles;
}
