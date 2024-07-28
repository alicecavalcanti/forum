package com.apiRest.forum.config

import com.apiRest.forum.service.UsuarioService
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTUtil (private val usuarioService: UsuarioService ){

    private val expiration : Long = 3600000

    @Value("\${jwt.secret}")
    private lateinit var secret : String

    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String{
        try {
            return Jwts.builder()
                .setSubject(username)
                .claim("role", authorities)
                .setExpiration(Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.toByteArray())// criação de assinatura
                .compact()
        }catch (exception: JwtException){
            throw RuntimeException("Error " + exception)
        }
    }
    // verificando se a assinatura do token é valida
    fun isValid(jwt: String?): Boolean {
        return try{
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt)
            true
        }catch(e:IllegalArgumentException){
            false
        }
    }
    //o getAuthentication tá pegando os dados da pessoa autenticada para passar no SecurityContextHolder
    fun getAuthentication(jwt: String?) : Authentication {
        val username = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.subject
        val user= usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }

}