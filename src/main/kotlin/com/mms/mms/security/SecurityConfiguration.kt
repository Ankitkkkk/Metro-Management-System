package com.mms.mms.security

import com.mms.mms.security.service.UserAuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val userService: UserAuthService,
                            private val jwtAuthenticationFilter: JwtAuthenticationFilter
                            ) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http!!
            .csrf()
            .disable()
            .cors()
            .disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST,"/user/signup")
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET,"/user/login")
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers("/api/status")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http
            .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}