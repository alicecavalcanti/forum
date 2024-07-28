package com.apiRest.forum.controller

import com.apiRest.forum.config.JWTUtil
import com.apiRest.forum.dto.TokenLoginResponse
import com.apiRest.forum.model.Credentials
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign-in")
class SignInController(private val jwtUtil: JWTUtil,
                       private val authenticationManager: AuthenticationManager){
    @PostMapping
    fun login (@RequestBody credentials: Credentials, response: HttpServletResponse) :ResponseEntity<TokenLoginResponse> {
        val user = UsernamePasswordAuthenticationToken(credentials.email, credentials.password)
        var userAuthenticate= this.authenticationManager.authenticate(user)

        val token = jwtUtil.generateToken(userAuthenticate.name, userAuthenticate.authorities)

        return ResponseEntity.ok(TokenLoginResponse(
            token = "Bearer $token"
        ))
    }

}