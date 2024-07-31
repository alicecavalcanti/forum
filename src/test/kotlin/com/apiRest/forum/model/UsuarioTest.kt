package com.apiRest.forum.model

object UsuarioTest {
    fun build() = Usuario(
        id = 1,
        nome = "Alice",
        email = "alice@email.com",
        password = "1234"
    )
}