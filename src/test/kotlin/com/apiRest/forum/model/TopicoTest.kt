package com.apiRest.forum.model

// o object cria objetos de classes anônimas( classes que não são declaradas com o class)
object TopicoTest {
    fun build(curso: Curso, usuario: Usuario) = Topico(
        titulo = "Esse é o tópico 1",
        mensagem = "testando",
        curso = curso,
        autor = usuario
    )

}