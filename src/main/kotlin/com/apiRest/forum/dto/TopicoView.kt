package com.apiRest.forum.dto

import com.apiRest.forum.model.StatusTopico
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

data class TopicoView (
    val id: Int?,
    val titulo: String,
    val mensagem: String,
    val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    val dataAlteracao: LocalDate?
    ): Serializable
