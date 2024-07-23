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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER) // o fetch fará com que o jpa pegue todas as roles do user
    var role: List<Role> = mutableListOf()
)
