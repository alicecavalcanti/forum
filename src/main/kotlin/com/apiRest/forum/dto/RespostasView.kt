package com.apiRest.forum.dto

import java.time.LocalDateTime

data class RespostasView (
    val id: Long?,
    var mensagem: String,
    val nomeAutor: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val solucao: Boolean

)