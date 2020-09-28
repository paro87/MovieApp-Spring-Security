package tm.paro.resourceserver.model.jwt;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
@Data
public class Ucayim {
    @SerializedName("roles")
    private List<ROLES> roles;
}
