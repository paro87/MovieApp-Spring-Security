package tm.paro.resourceserver.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static tm.paro.resourceserver.jwt.JWTVerify.getKeyFromPublicKey;

public class JwtFilter3 extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        String authorizationHeader=req.getHeader("Authorization");
        if (Strings.isNullOrEmpty(authorizationHeader)||!authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            String token=authorizationHeader.replace("Bearer", "");
            Jws<Claims> claimsJws= Jwts.parserBuilder()
                    //.setSigningKey(getKeyFromPublicKey(publicKeyPEM))
                    .setSigningKey(getKeyFromPublicKey())
                    .build()
                    .parseClaimsJws(token);
            Claims body=claimsJws.getBody();
            //String username=body.getSubject();
//            List<Map<String, String>>  authorities=(List<Map<String, String>> )body.get("resource_access");
//            Set<SimpleGrantedAuthority> simpleGrantedAuthorities=authorities.stream()
//                    .map(m-> new SimpleGrantedAuthority("ucayim"))
//                    .collect(Collectors.toSet());
            String username="test34@mynkey.com";
            Set<SimpleGrantedAuthority> permissions=new HashSet<>();
//            permissions.add(new SimpleGrantedAuthority("ROLE_UCAYIM_FLIGHT_ADMIN"));
//            permissions.add(new SimpleGrantedAuthority("ROLE_UCAYIM_USER"));
            permissions.add(new SimpleGrantedAuthority("asda"));

            //permissions.add(new SimpleGrantedAuthority("GET"));

            Authentication authentication=new UsernamePasswordAuthenticationToken(username, null, permissions);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e){
            throw new IllegalStateException(String.format("Token cannot be trusted"));
        }

        filterChain.doFilter(request, response);
    }
}
