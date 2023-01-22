package ma.hamza.security.config;

import lombok.RequiredArgsConstructor;
import ma.hamza.security.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*this class will hold all the application configuration such as Beans etc*/
@Configuration/*so that at the startup Spring will pick up this class and try tp implement and inject
all the beans that we will declare in this class*/
@RequiredArgsConstructor /*in case we want to inject something (final)*/
public class ApplicationConfig  {
    private final UserRepository userRepository;
    @Bean /*should always be public*/
    public UserDetailsService userDetailsService() {
        return username -> userRepository
                .findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("UserNotFound"));
    }
    @Bean
    /*The data acess object  which is responsible to fetch userDetails and encode passwd...*/
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    /*authentication manager has methods that help us to authenticate a user using just the un/psswd*/
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
