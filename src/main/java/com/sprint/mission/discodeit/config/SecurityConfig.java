package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.security.DiscodeitUserDetailsService;
import com.sprint.mission.discodeit.security.LoginFailureHandler;
import com.sprint.mission.discodeit.security.LoginSuccessHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;

@Configuration
@EnableWebSecurity(debug = true )
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

  private final LoginSuccessHandler loginSuccessHandler;
  private final LoginFailureHandler loginFailureHandler;
  private final DiscodeitUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.
            csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
        )

        .formLogin(login -> login
            .loginPage("/index.html")
            .loginProcessingUrl("/api/auth/login")
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailureHandler)
            .permitAll()
        )

        .logout(logout -> logout
            .logoutUrl("/api/auth/logout")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
            .deleteCookies("JSESSIONID"))

        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/",
                "/index.html",
                "/assets/**",
                "/css/**",
                "/js/**",
                "/api/auth/**",
                "/api/users"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint((request,
                response,
                authException) -> { response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.getWriter().write("인증이 필요합니다.");
            })
            .accessDeniedHandler((request,
                response,
                accessDeniedException) ->  { response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("권한이 없습니다.");
            }))

        .userDetailsService(userDetailsService)
    ;

    return http.build();
  }

  static class SpaCsrfTokenRequestHandler implements CsrfTokenRequestHandler {

    private final CsrfTokenRequestHandler plain = new CsrfTokenRequestAttributeHandler();
    private final CsrfTokenRequestHandler xor = new XorCsrfTokenRequestAttributeHandler();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        Supplier<CsrfToken> csrfToken) {

      this.xor.handle(request,response,csrfToken);

    }

    @Override
    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
      String headerValue = request.getHeader(csrfToken.getHeaderName());

      return (StringUtils.hasText(headerValue) ? this.plain : this.xor).resolveCsrfTokenValue(request, csrfToken);
    }
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
