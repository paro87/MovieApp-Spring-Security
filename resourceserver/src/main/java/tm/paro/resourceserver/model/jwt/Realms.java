package tm.paro.resourceserver.model.jwt;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Realms {
    private String realm;
    private String public_key;
    @SerializedName(value = "token-service")
    private String token_service;
    @SerializedName(value = "account-service")
    private String account_service;
    @SerializedName(value = "tokens-not-before")
    private String token_not_before;
}
