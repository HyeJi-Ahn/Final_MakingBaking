package com.ezen.makingbaking.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ezen.makingbaking.configuration.handler.LoginFailureHandler;
import com.ezen.makingbaking.oauth.Oauth2UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
		@Autowired
		private LoginFailureHandler loginFailureHandler;
		
		@Autowired
		private Oauth2UserService oauth2UserService;
		
		@Bean
		public static PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/css/**").permitAll()
									.antMatchers("/js/**").permitAll()
									.antMatchers("/images/**").permitAll()
									.antMatchers("/upload/**").permitAll()
									.antMatchers("/main/join").permitAll()
									.antMatchers("/main/login").permitAll()
									.antMatchers("/user/idcheck").permitAll()
									.antMatchers("/user/loginProc").permitAll()
									.anyRequest().permitAll();
			
			//로그인
			http.formLogin()
			.loginPage("/home/main?loginPage=Y")
			.usernameParameter("userId")
			.passwordParameter("userPw")
			.loginProcessingUrl("/user/loginProc")
			.defaultSuccessUrl("/main/main")
			.failureHandler(loginFailureHandler)
			
			//카카오 로그인
			.and()
			.oauth2Login()
			.loginPage("/home/main?loginPage=Y")
			.defaultSuccessUrl("/user/join")
			.userInfoEndpoint()
			.userService(oauth2UserService);
			
			
			//로그아웃
		http.logout()
			.logoutUrl("/main/logout")
			.invalidateHttpSession(true)
			.logoutSuccessUrl("/main/main");
		
			http.csrf().disable();
			
			return http.build();
		}
}
