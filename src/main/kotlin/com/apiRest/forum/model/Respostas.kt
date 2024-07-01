package com.apiRest.forum.model

import java.time.LocalDateTime

class Respostas (
    val id: Long?= null,
    val mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val autor: Usuario,
    val topico: Topico,
    val solucao: Boolean
)
