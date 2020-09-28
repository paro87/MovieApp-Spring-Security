package tm.paro.resourceserver.model.jwt;

import lombok.Data;

@Data
public class Resource_access {
    private Ucayim ucayim;
    private Account account;
}
