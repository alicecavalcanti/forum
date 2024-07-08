package com.apiRest.forum.model

import org.hibernate.validator.constraints.br.TituloEleitoral
import java.time.LocalDateTime

data class Topico (
    var id: Long ?= null,
    val titulo: String,
    val mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val curso: Curso,
    val autor: Usuario,
    val status: StatusTopico= StatusTopico.NAO_RESPONDIDO,
    val respostas: List<Respostas> = ArrayList()
)
