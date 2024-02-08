package galaxim.challenge.config;

import galaxim.challenge.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@ComponentScan
public class ApplicationConfig {

    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Permet de définir la logique d'authentification personnalisée.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Le DaoAuthenticationProvider est une implémentation courante de AuthenticationProvider
        // Il permet de réaliser une authentification sur les données stockées dans une base de données
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Définit le service utilisé par l'AuthenticationProvider
        // userDetailsService est une Bean que nous avons défini
        // Dans lequel on précise à Spring comment récupérer un utilisateur dans notre BDD
        // Dans notre implémentation, nous récupérons un utilisateur selon son Email (et non son username).
        // La récupération via son username est l'implémentation par défaut.
        // C'est pour cela que nous l'avons redéfini
        authProvider.setUserDetailsService(userDetailsService()); /* Which UserDetailsService to user to fetch user from DB, because there are many of them */
        // De la même manière, on précise à l'AuthenticationProvider quel encodeur de MDP, nous allons utiliser
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* Authentication Manager : responsible to manage the authentication. It has a method to help us to authenticate the user with Username and Password */
    @Bean
    public AuthenticationManager customAuthenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); /* Owns already all the logic */
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://challenge.galaxim.com")); //Ajout de l'url pour deploiement
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
