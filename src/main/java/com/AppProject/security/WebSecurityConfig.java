package com.AppProject.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.AppProject.services.DetailsService;

 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DetailsService detailsService;

//    @Autowired
//    CustomSuccessHandler customSuccessHandler;
//    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**","/app/**");
	}
    


//	.antMatchers("/admin").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
    	http
//    	.antMatcher("/admin/**").authorizeRequests().anyRequest().hasRole("ADMIN")
    	.authorizeRequests()  
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        .antMatchers("/file/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DEV')")
        .antMatchers("/project/**").access("hasRole('ROLE_ADMIN')  or hasRole('ROLE_DEV')")

//        .antMatchers("/admin/**").hasAnyRole("USER", "ADMIN") 
//    	.authorizeRequests().anyRequest().authenticated()
    	.and()
    	.httpBasic()
    	.authenticationEntryPoint(new AuthExceptionEntryPoint())
    	.and()
        .csrf().disable()
    	/*.and().formLogin()
    	.successHandler(customSuccessHandler)*/;
    	
//    	http.exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint()) ;

    	
        /*http
        .authorizeRequests()
        .antMatchers(HttpMethod.GET,"/admin","/redirectAccess","/reset-password","/console/**","/download-widget**").hasAnyRole("")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
        
        http.authorizeRequests()
		.antMatchers("/admin/**","/resources/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/dev/**").access("hasRole('ROLE_USER')")
		.antMatchers("/client").access("hasRole('ROLE_CLIENT')")
//		.antMatchers("/resources/**","/admin/**").hasAnyRole(roles)
		.and()
//			.formLogin().loginPage("/login")
//			.failureUrl("/login?error")
//			.and()
            .httpBasic();*/
        
    }
    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        BasicAuthenticationEntryPoint entryPoint = 
          new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("realm");
        return entryPoint;
    }
}
