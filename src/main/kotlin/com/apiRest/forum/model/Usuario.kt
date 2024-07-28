package com.apiRest.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class Usuario (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null,
    val nome: String,
    val email: String,
    val password : String,

    @JsonIgnore // ignorar requisições de usuários para que não mostre uma lista de roles(loop na aplicação)
    @ManyToMany(fetch = FetchType.EAGER) // o fetch fará com que o jpa pegue todas as roles do user
    @JoinTable(
        name="usuario_role",
        joinColumns=
        [JoinColumn(name="usuario_id", referencedColumnName="id")],
        inverseJoinColumns=
        [JoinColumn(name="role_id", referencedColumnName="id")]
    )
    var role: List<Role> = mutableListOf()
)
