package com.apiRest.forum.model

// o object cria objetos de classes anônimas( classes que não são declaradas com o class)
object TopicoTest {
    fun build() = Topico(
        id = 1,
        titulo = "Kotlin avançado",
        mensagem = "testando2",
        curso = CursoTest.build(),
        autor = UsuarioTest.build()

    )

}