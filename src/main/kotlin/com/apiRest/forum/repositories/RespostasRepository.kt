package com.apiRest.forum.repositories


import com.apiRest.forum.model.Respostas
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RespostasRepository: JpaRepository<Respostas, Long> {
    fun findByTopicoId(id: Long, paginacao: Pageable): Page<Respostas>
}