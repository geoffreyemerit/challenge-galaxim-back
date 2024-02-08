package galaxim.challenge.config;

import galaxim.challenge.filter.JwtAuthenticationFilter;
import galaxim.challenge.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configure(http))
                .sessionManagement(session -> session .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("").disable())


                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/v1/auth/**",
                                "/api/v1/games/{id}",
                                "/api/v1/games/team/{job}",
                                "/api/v1/offices/top20/caHtOfficeSsp",
                                "/api/v1/users/top20/agents/caHtAct",
                                "/api/v1/users/top20/agents/caHtSsp",
                                "/api/v1/users/top20/agents/salesSsp",
                                "/api/v1/users/top20/agents/mandates",
                                "/api/v1/users/top20/mandataires/caHtAct",
                                "/api/v1/users/top20/mandataires/caHtSsp",
                                "/api/v1/users/top20/mandataires/mandates",
                                "/api/v1/users/top20/mandataires/bestDev",
                                "/api/v1/users/top20/mandataires/caHtNetworkTeamSsp",
                                "/api/v1/users/top10/caAllActions").permitAll() /* n'importe qui ayant accès à cet url */
                        .requestMatchers("/api/v1/demo/users-only").hasAnyRole(Role.USER.name()) /* ROLE_USER */
                        .requestMatchers("/api/v1/demo/admin-only").hasAnyRole(Role.ADMIN.name()) /* ROLE_ADMIN */
                        .anyRequest().authenticated()
                )


                .exceptionHandling((exception) ->  exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
