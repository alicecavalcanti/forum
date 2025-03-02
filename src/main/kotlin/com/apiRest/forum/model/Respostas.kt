package com.apiRest.forum.model
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Respostas (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?= null,
    var mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    val autor: Usuario,
    @ManyToOne
    var topico: Topico?= null,
    val solucao: Boolean
)
