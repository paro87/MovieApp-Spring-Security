package tm.paro.resourceserver.jwt;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.RsaKeyUtil;
import org.jose4j.lang.JoseException;
import tm.paro.resourceserver.model.jwt.AuthenticatedJWT;
import tm.paro.resourceserver.model.jwt.Realm_access;
import tm.paro.resourceserver.model.jwt.Realms;
import tm.paro.resourceserver.model.jwt.Resource_access;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Map;

public class JWTVerify {

    public static void main(String[] args) {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJMQ1EzMm1HUE1oaGV2ejF6M1I0QzNDT3B5NTJEanZoejBjRFFkcVRIaDJRIn0.eyJleHAiOjE2MDA1NTAyNDIsImlhdCI6MTYwMDU0OTk0MiwianRpIjoiNzIyNjAxN2ItNTAzOC00YWFjLThlNTktZmZlMTllN2ZlZmUxIiwiaXNzIjoiaHR0cHM6Ly9zc28tZGV2Lm15bmtleS5jb20vYXV0aC9yZWFsbXMvbXlua2V5IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImJkZDk1Nzg4LWZiM2UtNDdkZC04MzQwLTllOWRiYWYwMDFmMSIsInR5cCI6IkJlYXJlciIsImF6cCI6InVjYXlpbSIsInNlc3Npb25fc3RhdGUiOiI3MjJiYTQyMC01YjFkLTQ0ZDUtYmM1OC1kNTI2Y2NmM2I0OTEiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHBzOi8vdWNheWltLmNvbSJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InVjYXlpbSI6eyJyb2xlcyI6WyJVQ0FZSU1fRkxJR0hUX0FETUlOIiwiVUNBWUlNX1VTRVIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiVGVzdCBVc2VyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidGVzdDM0QG15bmtleS5jb20iLCJnaXZlbl9uYW1lIjoiVGVzdCIsImZhbWlseV9uYW1lIjoiVXNlciIsImVtYWlsIjoidGVzdDM0QG15bmtleS5jb20ifQ.axAs8j518I3IA5_AVTXFfRDWpXpt-gN-8t8qF1NT660Vfl8oF1mZD6ivfKuiVrw3wPjELUal62w1qeXONyCrow58Q8mZ762SS44HzbIbgCVCp86jnzl6QImro0IOSdPsAEksNrNmeFQTl3LphX4IzEEy0Yqk9Gbd2JHeyC5PtJprac9-23nWl07R9RvsNQEfF_7X3SsRezjofy0DUCF8mHJI9hzraZG_gAyz5EZISzdOzKF50zitHEXvAklmRDDz5MHHckaBQVhr5D2dFtfBQOxxs-qYvgT18bdd16humGspUwBAUtfUR7f89p3tARvYB_LhPt9rAcpJroodjGaQwQ";

        //auth0jwt(token);
        jose4jjwt(token);
        //javajvt(token);

    }
    // com.auth0.jwt.
    private static void auth0jwt(String token) {
        try{

            String providerEnd="https://sso-dev.mynkey.com/auth/realms/mynkey/protocol/openid-connect/certs";
            JwkProvider provider = new UrlJwkProvider(new URL(providerEnd)); //"<my-provider>"
            Jwk jwk = provider.get("LCQ32mGPMhhevz1z3R4C3COpy52Djvhz0cDQdqTHh2Q");
            final PublicKey publicKey = jwk.getPublicKey();

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://sso-dev.mynkey.com/auth/realms/mynkey")
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            AuthenticatedJWT authenticatedJWT=getAuthenticatedJwt(claims);

            if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
                throw new RuntimeException("Expired token!");
            }
        } catch (InvalidPublicKeyException e) {
            e.printStackTrace();
        } catch (JwkException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static AuthenticatedJWT getAuthenticatedJwt(Map<String, Claim> claims){
        AuthenticatedJWT authenticatedJWT=new AuthenticatedJWT();
        authenticatedJWT.setSub(claims.get("sub").asString());
        authenticatedJWT.setResource_access(claims.get("resource_access").as(Resource_access.class));
        authenticatedJWT.setAllowed_origins(claims.get("allowed-origins").asList(String.class));
        authenticatedJWT.setIss(claims.get("iss").asString());
        authenticatedJWT.setTyp(claims.get("typ").asString());
        authenticatedJWT.setPreferred_username(claims.get("preferred_username").asString());
        authenticatedJWT.setAcr(claims.get("acr").asString());
        authenticatedJWT.setRealm_access(claims.get("realm_access").as(Realm_access.class));
        authenticatedJWT.setAzp(claims.get("azp").asString());
        authenticatedJWT.setScope(claims.get("scope").asString());
        authenticatedJWT.setExp(claims.get("exp").asInt());
        authenticatedJWT.setSession_state(claims.get("session_state").asString());
        authenticatedJWT.setIat(claims.get("iat").asInt());
        authenticatedJWT.setJti(claims.get("jti").asString());
        authenticatedJWT.setEmail(claims.get("email").asString());
        authenticatedJWT.setEmail_verified(claims.get("email_verified").asBoolean());
        authenticatedJWT.setGiven_name(claims.get("given_name").asString());
        authenticatedJWT.setAud(claims.get("aud").asString());
        authenticatedJWT.setName(claims.get("name").asString());
        authenticatedJWT.setFamily_name(claims.get("family_name").asString());
        return authenticatedJWT;
    }

    // org.jose4j.jwt
    private static void jose4jjwt(String token){
        Gson gson = new Gson();
        // read public key from a file or config or something
        String publicKeyPEM =
                "-----BEGIN PUBLIC KEY-----\n" +
                        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmoJgWLoc6Pb9BXfrLe25\n" +
                        "kM8nZG+78FKvu3G/hPmikP7+ToYVOx9B1QFGP50JLnL7bNG3/FiWp1v9FxlSDmOV\n" +
                        "45tMly6D4Nvf8Pjy416c4bYsBjzA9zyw6b/FVLWIRzFhPQOEe/WbvGftpZii7H9j\n" +
                        "aUg8CnvqTZO2+BvZCN5xlo4GoHmIedRN7Ritn59oKWWYR5WiDP142j/uVwZ/eFeS\n" +
                        "dSucHm8KGNT7QdjQjkOTnZ8mA2kBwPPGpKhKHWrMLeYwOWyS6OjoWqJ1fnpO5CLe\n" +
                        "4pPLbMD6XfDo0lg+f3E++GhP1Ti5zseFKrNdZva7TQOMarvDwwaq4U+QSCmTe/eP\n" +
                        "ZwIDAQAB\n" +
                        "-----END PUBLIC KEY-----";

        try{
            RsaKeyUtil rsaKeyUtil = new RsaKeyUtil();
            PublicKey publicKey = rsaKeyUtil.fromPemEncoded(publicKeyPEM);

            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRelaxVerificationKeyValidation()
                    .setRequireExpirationTime()
                    .setExpectedAudience("account")
                    //.setSkipDefaultAudienceValidation()
                    .setVerificationKey(publicKey)
                    .build();

            JwtContext jwtContext=jwtConsumer.process(token);
            JwtClaims jwtDecoded=jwtContext.getJwtClaims();
            String str=jwtDecoded.getRawJson();
            AuthenticatedJWT authenticatedJWT = gson.fromJson(str, AuthenticatedJWT.class);
        } catch (InvalidJwtException e) {
            e.printStackTrace();
        } catch (JoseException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // io.jsonwebtoken
    private static void javajvt(String token){
        Jws<Claims> claimsJws= Jwts.parserBuilder()
                //.setSigningKey(getKeyFromPublicKey(publicKeyPEM))
                .setSigningKey(getKeyFromPublicKey())
                .build()
                .parseClaimsJws(token);
        Claims body=claimsJws.getBody();
        final ObjectMapper mapper = new ObjectMapper();
        AuthenticatedJWT authenticatedJWT = mapper.convertValue(body, AuthenticatedJWT.class);

    }

    public static RSAPublicKey getKeyFromPublicKey() {
        RSAPublicKey pubKey = null;
        try{
            String publicKeyPEM =
                    "-----BEGIN PUBLIC KEY-----\n" +
                            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmoJgWLoc6Pb9BXfrLe25\n" +
                            "kM8nZG+78FKvu3G/hPmikP7+ToYVOx9B1QFGP50JLnL7bNG3/FiWp1v9FxlSDmOV\n" +
                            "45tMly6D4Nvf8Pjy416c4bYsBjzA9zyw6b/FVLWIRzFhPQOEe/WbvGftpZii7H9j\n" +
                            "aUg8CnvqTZO2+BvZCN5xlo4GoHmIedRN7Ritn59oKWWYR5WiDP142j/uVwZ/eFeS\n" +
                            "dSucHm8KGNT7QdjQjkOTnZ8mA2kBwPPGpKhKHWrMLeYwOWyS6OjoWqJ1fnpO5CLe\n" +
                            "4pPLbMD6XfDo0lg+f3E++GhP1Ti5zseFKrNdZva7TQOMarvDwwaq4U+QSCmTe/eP\n" +
                            "ZwIDAQAB\n" +
                            "-----END PUBLIC KEY-----";


//            String publicKeyPEM = key;
//            publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
//            publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
//            byte[] encoded = Base64.decodeBase64(publicKeyPEM);
            byte[] encoded = Base64.decodeBase64(getPublicKey());
            KeyFactory kf = KeyFactory.getInstance("RSA");


            pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return pubKey;
    }

    private static String getPublicKey(){
        String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmoJgWLoc6Pb9BXfrLe25\n" +
                "kM8nZG+78FKvu3G/hPmikP7+ToYVOx9B1QFGP50JLnL7bNG3/FiWp1v9FxlSDmOV\n" +
                "45tMly6D4Nvf8Pjy416c4bYsBjzA9zyw6b/FVLWIRzFhPQOEe/WbvGftpZii7H9j\n" +
                "aUg8CnvqTZO2+BvZCN5xlo4GoHmIedRN7Ritn59oKWWYR5WiDP142j/uVwZ/eFeS\n" +
                "dSucHm8KGNT7QdjQjkOTnZ8mA2kBwPPGpKhKHWrMLeYwOWyS6OjoWqJ1fnpO5CLe\n" +
                "4pPLbMD6XfDo0lg+f3E++GhP1Ti5zseFKrNdZva7TQOMarvDwwaq4U+QSCmTe/eP\n" +
                "ZwIDAQAB";
        Realms realms = new Realms();
        try{
            Gson gson = new Gson();
            HttpClient client=HttpClient.newHttpClient();
            HttpRequest request=HttpRequest.newBuilder()
                    .uri(URI.create("https://sso-dev.mynkey.com/auth/realms/mynkey"))
                    .build();
            HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
            realms=gson.fromJson(response.body(), Realms.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return realms.getPublic_key();
    }
}
