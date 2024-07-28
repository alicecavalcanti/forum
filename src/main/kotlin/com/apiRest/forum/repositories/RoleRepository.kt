package com.apiRest.forum.repositories

import com.apiRest.forum.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByNome(nome: String): Role
}