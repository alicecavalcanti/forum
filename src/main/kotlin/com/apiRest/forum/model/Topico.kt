package com.apiRest.forum.model
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class Topico (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int ?= null,
    var titulo: String,
    var mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    val curso: Curso,
    @ManyToOne
    val autor: Usuario,
    @Enumerated(value = EnumType.STRING)
    val status: StatusTopico= StatusTopico.NAO_RESPONDIDO,
    @OneToMany(mappedBy = "topico")
    val respostas: List<Respostas> = ArrayList(),
    var dataAlteracao: LocalDate?=null
)
