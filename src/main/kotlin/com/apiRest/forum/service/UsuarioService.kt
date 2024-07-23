package com.apiRest.forum.service

import com.apiRest.forum.exception.NotFoundException
import com.apiRest.forum.model.Usuario
import com.apiRest.forum.repositories.UsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.print.attribute.standard.PrinterInfo


@Service
class UsuarioService(private val repository: UsuarioRepository) : UserDetailsService {

    private val log = LoggerFactory.getLogger(PrinterInfo::class.java)

    fun buscarAutorTopico(idAutorService: Long): Usuario {
        return repository.findById(idAutorService).orElseThrow { NotFoundException("Usuário não encontrado") }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = repository.findByEmail(username) // metódo para trazer o usuário e se for diferente de nulo lançar exceção
        log.info(usuario.password)
        return UserDetail(usuario)
    }


}