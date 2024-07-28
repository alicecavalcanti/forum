package com.apiRest.forum.service

import com.apiRest.forum.exception.NotFoundException
import com.apiRest.forum.model.Usuario
import com.apiRest.forum.repositories.RoleRepository
import com.apiRest.forum.repositories.UsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import javax.print.attribute.standard.PrinterInfo


@Service
class UsuarioService(private val UsuarioRepository: UsuarioRepository, private val roleRepository: RoleRepository) : UserDetailsService {

    private val log = LoggerFactory.getLogger(PrinterInfo::class.java)

    fun cadatrarUser(usuario: Usuario) : Usuario{

        var user = Usuario(
            nome = usuario.nome,
            email = usuario.email,
            password = hashPassword(usuario.password),
            role = usuario.role.plus(roleRepository.findByNome("LEITURA-ESCRITA"))
        )
        return UsuarioRepository.save(user)
    }

    fun buscarAutorTopico(idAutorService: Long): Usuario {
        return UsuarioRepository.findById(idAutorService).orElseThrow { NotFoundException("Usuário não encontrado") }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = UsuarioRepository.findByEmail(username) // metódo para trazer o usuário e se for diferente de nulo lançar exceção
        log.info(usuario.password)
        return UserDetail(usuario)
    }

    fun hashPassword(password: String): String{
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt)
    }


}