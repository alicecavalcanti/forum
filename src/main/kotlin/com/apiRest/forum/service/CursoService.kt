package com.apiRest.forum.service

import com.apiRest.forum.exception.NotFoundException
import com.apiRest.forum.model.Curso
import com.apiRest.forum.repositories.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService (val repository: CursoRepository) {
    fun buscarPorId(idNovoTopico: Long): Curso {
        return repository.findById(idNovoTopico).orElseThrow{NotFoundException("Curso não encontrado")}
    }
}