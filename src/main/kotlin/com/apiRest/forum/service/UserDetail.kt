package com.apiRest.forum.service

import com.apiRest.forum.model.Usuario
import org.springframework.security.core.userdetails.UserDetails

// o userDatail é quem tem as informações do usuário logado
class UserDetail(private val usuario: Usuario) : UserDetails {
    override fun getAuthorities() = usuario.role

    override fun getPassword(): String = usuario.password

    override fun getUsername(): String = usuario.email
}