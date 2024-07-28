package com.apiRest.forum.security

import com.apiRest.forum.config.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
@Component
class JWTAuthenticationFilter(private val jwtUtil: JWTUtil) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // validando o token: tirando o barer, validando a assinatura do token com o isValid
        // o getAuthentication tá pegando os dados da pessoa autenticada para passar no SecurityContextHolder
        // e assim identificar no contexto da aplicação o usuário que está logado
        val token = request.getHeader("Authorization")
        val jwt = getTokenDetail(token)

        if (jwt != null && jwtUtil.isValid(jwt)){
            val authentication = jwtUtil.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication
        }
       filterChain.doFilter(request, response)
    }

    private fun getTokenDetail(token: String?): String?{
        return token?.let{ jwt->
            jwt.startsWith("Bearer ")
            jwt.substring(7, jwt.length)
        }
    }

}
