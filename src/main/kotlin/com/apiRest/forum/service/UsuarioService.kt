package com.apiRest.forum.service

import com.apiRest.forum.model.Usuario
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService(private var usuarios : List<Usuario>) {
    init{
       val usuario =
           Usuario(
           id= 1,
           nome = "Luis Pereira",
           email = "alicecavalcanti24@gmail.com"
       )
        val usuario2 = Usuario(
            id = 2,
            nome = "Júlia Soares",
            email = "alicecavalcanti24@gmail.com"
        )
        usuarios = Arrays.asList(usuario, usuario2);
    }
    fun buscarPorId(idAutorService: Long): Usuario{
        return usuarios.stream().filter({
            c -> c.id == idAutorService
        }).findFirst().get()
    }

}