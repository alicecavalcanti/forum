package com.apiRest.forum.service

import com.apiRest.forum.exception.NotFoundException
import com.apiRest.forum.model.Usuario
import com.apiRest.forum.repositories.UsuarioRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service



@Service
class UsuarioService(private val repository: UsuarioRepository) {
    fun buscarAutorTopico(idAutorService: Long): Usuario {
        return repository.findById(idAutorService).orElseThrow { NotFoundException("Usuário não encontrado") }
    }

}