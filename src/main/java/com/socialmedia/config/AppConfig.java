package com.socialmedia.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity

public class AppConfig {

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(
//                        Authorize -> Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
//                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class).csrf(csrf -> csrf.disable()).cors(cors -> cors
//                .configurationSource(corsConfigurationSource())).httpBasic(withDefaults()).formLogin(withDefaults());
//
//		return http.build();
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/**", "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated())


                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(new JwtTokenValidator(), UsernamePasswordAuthenticationFilter.class);
		//http.authenticationProvider(BasicAuthenticationFilter.class);

		DefaultSecurityFilterChain build = http.build();
		return build;
	}
	private CorsConfigurationSource corsConfigurationSource() {

		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cfg = new CorsConfiguration();
				cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				//corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.addAllowedHeader("Accept");
				cfg.addAllowedHeader("POST");
				cfg.addAllowedHeader("GET");
				cfg.addAllowedHeader("DELETE");
				cfg.addAllowedHeader("PUT");
				cfg.addAllowedHeader("OPTIONS");
				cfg.addAllowedOriginPattern("*");
				//corsConfiguration.setMaxAge(3600L);
				cfg.setMaxAge(3600L);
				return cfg;
			}
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Component
	public class CORSFilter implements Filter {

	    @Override
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	        HttpServletResponse response = (HttpServletResponse) res;
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	        chain.doFilter(req, res);
	    }

	    @Override
	    public void init(FilterConfig filterConfig) {}

	    @Override
	    public void destroy() {}

	}

}
