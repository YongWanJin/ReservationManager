package zerobase.ReservationManager.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JWTAuthoenticationFilter authoenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        System.out.println("filterChain start");

        http
                .httpBasic().disable()
                .csrf().disable()
                // 토큰 인증 필요 없는 url들
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/auth/signup", "/auth/signin").permitAll()
                        .anyRequest().authenticated())
                // 세션 사용 안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .addFilterBefore(this.authoenticationFilter, UsernamePasswordAuthenticationFilter.class);
        System.out.println("filterChain end");

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .invalidateHttpSession(true);

        return http.build();
    }
}
