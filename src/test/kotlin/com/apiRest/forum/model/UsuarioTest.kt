package com.apiRest.forum.model

object UsuarioTest {
    fun build() = Usuario(
        nome = "Alice",
        email = "alice@email.com",
        password = "1234",
        role = listOf(Role(1, "LEITURA-ESCRITA"))
    )

}