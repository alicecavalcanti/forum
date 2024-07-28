package com.apiRest.forum.config

import com.apiRest.forum.security.JWTAuthenticationFilter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity // informa a autorização dos pacotes para o spring
class SecurityConfiguration(private val jwtUtil: JWTUtil) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                    // quais solicitações a configuração de segurança do spring será aplicada
                it.requestMatchers(HttpMethod.POST,"/sign-in").permitAll()
                it.requestMatchers(HttpMethod.POST,"/sign-up").permitAll()
                it.requestMatchers("/topicos").hasAuthority("LEITURA-ESCRITA")
                it.anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(JWTAuthenticationFilter(jwtUtil = jwtUtil),
                UsernamePasswordAuthenticationFilter().javaClass)
        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager{
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}