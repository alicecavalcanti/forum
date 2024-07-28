package com.apiRest.forum.repositories

import com.apiRest.forum.dto.TopicoPorCategoriaDto
import com.apiRest.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TopicoRepository: JpaRepository<Topico, Long> {
    // O jpa faz internamente a busca pelo curso pela ordem da nomeação da função e
    // o spring cuida da instanciação
    fun findByCursoNome(nomeCurso: String, paginacao: Pageable): Page<Topico>
    @Query("SELECT new com.apiRest.forum.dto.TopicoPorCategoriaDto(curso.categoria, count(t)) FROM Topico t JOIN t.curso nome GROUP BY curso.categoria")
    fun relatorio(): List<TopicoPorCategoriaDto>

}