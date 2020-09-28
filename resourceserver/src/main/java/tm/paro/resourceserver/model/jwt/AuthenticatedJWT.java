package tm.paro.resourceserver.model.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class AuthenticatedJWT {
//    private String exp;
//    private String iat;
    private int exp;
    private int iat;
    private String jti;
    private String iss;
    private String aud;
    private String sub;
    private String typ;
    private String azp;
    private String session_state;
    private String acr;
    @JsonProperty("allowed-origins")
    @SerializedName(value = "allowed-origins")
    private List<String> allowed_origins;
    private Realm_access realm_access;
    private Resource_access resource_access;
    private String scope;
    private boolean email_verified;
    private String name;
    private String preferred_username;
    private String given_name;
    private String family_name;
    private String email;

}
