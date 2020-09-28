package tm.paro.resourceserver.model.jwt;

import lombok.Data;

import java.util.List;
@Data
public class Account {
    private List<String> roles;
}
