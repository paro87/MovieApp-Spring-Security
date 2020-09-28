package tm.paro.resourceserver.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static tm.paro.resourceserver.jwt.JWTVerify.getKeyFromPublicKey;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.replace("Bearer", "");
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    //.setSigningKey(getKeyFromPublicKey(publicKeyPEM))
                    .setSigningKey(getKeyFromPublicKey())
                    .build()
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            //String username=body.getSubject();
//            List<Map<String, String>>  authorities=(List<Map<String, String>> )body.get("resource_access");
//            Set<SimpleGrantedAuthority> simpleGrantedAuthorities=authorities.stream()
//                    .map(m-> new SimpleGrantedAuthority("ucayim"))
//                    .collect(Collectors.toSet());
            String username = "test34@mynkey.com";
            Set<SimpleGrantedAuthority> permissions = new HashSet<>();
            //permissions.add(new SimpleGrantedAuthority("ROLE_UCAYIM_FLIGHT_ADMIN"));
            //permissions.add(new SimpleGrantedAuthority("ROLE_UCAYIM_USER"));
            permissions.add(new SimpleGrantedAuthority("asda"));

            //permissions.add(new SimpleGrantedAuthority("GET"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, permissions);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
