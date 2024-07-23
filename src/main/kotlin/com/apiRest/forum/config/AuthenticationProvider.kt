//package com.apiRest.forum.config
//
//import com.apiRest.forum.service.UsuarioService
//import org.slf4j.LoggerFactory
//import org.springframework.security.authentication.AuthenticationProvider
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.Authentication
//import org.springframework.security.crypto.bcrypt.BCrypt
//import org.springframework.stereotype.Component
//import javax.print.attribute.standard.PrinterInfo
//
//@Component
//class AuthenticationProvider(private val usuarioService: UsuarioService) : AuthenticationProvider
//{
//    override fun authenticate(authentication: Authentication?): Authentication {
//        val email: String = authentication!!.name
//        val password : String = authentication.credentials.toString()
//
//        var usuario = usuarioService.loadUserByUsername(email)
//
//        val comparacaoSenha = checkPassword(password, usuario.password)
//
//        if(usuario != null && comparacaoSenha == true) {
//            return UsernamePasswordAuthenticationToken(usuario.username, usuario.password, usuario.authorities)
//        }
//        return UsernamePasswordAuthenticationToken(usuario.username, usuario.password)
//    }
//    fun checkPassword(password: String, hash: String): Boolean {
//        return BCrypt.checkpw(password, hash)
//    }
//
//    // indica para o spring o tipo de autenticação usada para verificar se ele suporta
//    override fun supports(authentication: Class<*>): Boolean {
//        return authentication == UsernamePasswordAuthenticationToken::class.java
//    }
//}