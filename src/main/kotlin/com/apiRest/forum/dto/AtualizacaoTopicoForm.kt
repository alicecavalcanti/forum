package com.apiRest.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

// data class são classes mais simples, que não possuem regras de negócio e geralmente são usadas em DTOS.
data class AtualizacaoTopicoForm (
    //O @field permite que o atributo que não seja um var ou o get receba a validação
    @field:NotNull
    val id : Long,  // o val se comporta como uma constante

    @field:NotEmpty
    @field:Size(min = 5, max = 100)
    val titulo: String,

    @field:NotEmpty
    val mensagem: String
)
