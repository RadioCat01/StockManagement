package com.synapse.StockMGT.Configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Security {

    private static final Logger logger = LoggerFactory.getLogger("AUDIT");

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(req -> req
                        .antMatchers("/login", "/login/**").permitAll()
                        .antMatchers(
                                "/pos", "/invoiceTemp"
                        ).hasAnyRole("PLATFORM_ADMIN", "COMPANY_ADMIN", "CASHIER")
                        .antMatchers(
                                "/inventory", "/grnSummery", "/category", "/brand", "/items", "/suppliers",
                                "/sales", "/invoice", "/customerJobs", "/company", "/subcompany", "/store",
                                "/storefront", "/counter", "/scanner", "/posTerminal", "/drawer", "/formsPage",
                                "/editInvoice"
                        ).hasAnyRole("PLATFORM_ADMIN", "COMPANY_ADMIN", "STOCK_CLERK")
                        .requestMatchers(
                                new AntPathRequestMatcher("/app-assets/**"),
                                new AntPathRequestMatcher("/JS/**"),
                                new AntPathRequestMatcher("/node_modules/**"),
                                new AntPathRequestMatcher("/auth/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(-1)
                        .sessionRegistry(sessionRegistry()))
                .build();
    }

    @EventListener
    public void handleSuccess(AuthenticationSuccessEvent event) {
        logger.info(event.getAuthentication().getPrincipal().toString() + " successfully logged in");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
