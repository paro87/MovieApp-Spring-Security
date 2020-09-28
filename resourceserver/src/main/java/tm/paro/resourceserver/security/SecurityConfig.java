package tm.paro.resourceserver.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final PasswordEncoder passwordEncoder;
//    @Autowired
//    public SecurityConfig(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)     //JWT
                .and()
                //.addFilter(new JwtFilter3())
                //.addFilter(new JWTAuthorizationFilter(authenticationManager()))
                //.addFilterBefore(new JwtTokenVerifier(), UsernamePasswordAuthenticationFilter.class)
                //.addFilter(new JwtTokenVerifier())
                .authorizeRequests()
                //.anyRequest()
                //.authenticated();
                //.antMatchers("/**").permitAll();
                //2nd way of securing API
//                .antMatchers(HttpMethod.DELETE, "movieapp/director/**").hasAnyAuthority(UserPermissions.DELETE.getPermission())
//                .antMatchers(HttpMethod.POST, "movieapp/director/**").hasAnyAuthority(UserPermissions.POST.getPermission())
//                .antMatchers(HttpMethod.PUT, "movieapp/director/**").hasAnyAuthority(UserPermissions.PUT.getPermission())
//                .antMatchers(HttpMethod.GET, "movieapp/director/**").hasAnyRole(UserRoles.UCAYIM_USER.name());
                .antMatchers("/","index", "/css/*", "/js/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "movieapp/director/**").hasAnyRole(UserRoles.UCAYIM_FLIGHT_ADMIN.name())
                .antMatchers(HttpMethod.POST, "movieapp/director/**").hasAnyRole(UserRoles.UCAYIM_USER.name())
                .antMatchers(HttpMethod.PUT, "movieapp/director/**").hasAnyRole(UserRoles.UCAYIM_FLIGHT_ADMIN.name())
                .antMatchers(HttpMethod.GET, "movieapp/director/**").hasAnyAuthority(UserPermissions.GET.getPermission())
                .antMatchers(HttpMethod.GET, "movieapp/director/**").hasAnyRole(UserRoles.UCAYIM_USER.name());

    }

    /*@Override
    protected UserDetailsService userDetailsService() {
        UserDetails user1=User.builder()
//                .username("test34@mynkey.com")
//                .password(passwordEncoder.encode("10SqfAr4mDd13aKms"))
                .username("user")
                .password(passwordEncoder.encode("user"))
                //.roles(UserRoles.UCAYIM_USER.name())
                .authorities(UserRoles.UCAYIM_USER.getGrantedAuthorities())
                .build();

        UserDetails user2=User.builder()
//                .username("test34@mynkey.com")
//                .password(passwordEncoder.encode("10SqfAr4mDd13aKms"))
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                //.roles(UserRoles.UCAYIM_FLIGHT_ADMIN.name())
                .authorities(UserRoles.UCAYIM_FLIGHT_ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }*/
}
