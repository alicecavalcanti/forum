package com.apiRest.forum.service

import com.apiRest.forum.model.Curso
import com.apiRest.forum.model.Topico
import com.apiRest.forum.model.Usuario
import org.springframework.stereotype.Service
import java.util.*

@Service
class TopicoService(private var topicos: List<Topico>) {
    init{
        val topico = Topico(
            id = 1,
            titulo = "Tirando dúvida sobre spring",
            mensagem = "Como fazer um crud, usando spring com kotlin ",
            curso = Curso(
                id = 1,
                nome= "Kotlin",
                categoria = "Programação"
            ),
            autor= Usuario(
                id = 1,
                nome = "Alice Pereira",
                email = "alice.pereira@tds.company"
            )
        )
        val topico2 = Topico(

            id = 2,
            titulo = "Tirando dúvida sobre spring",
            mensagem = "Como fazer um crud, usando spring com kotlin ",
            curso = Curso(
                id = 1,
                nome= "Kotlin",
                categoria = "Programação"
            ),
            autor= Usuario(
                id = 1,
                nome = "Alice Pereira",
                email = "alice.pereira@tds.company"
            )
        )
        val topico3 = Topico(

            id = 3,
            titulo = "Tirando dúvida sobre spring",
            mensagem = "Como fazer um crud, usando spring com kotlin ",
            curso = Curso(
                id = 1,
                nome= "Kotlin",
                categoria = "Programação"
            ),
            autor= Usuario(
                id = 1,
                nome = "Alice Pereira",
                email = "alice.pereira@tds.company"
            )
        )
        topicos = Arrays.asList(topico, topico2, topico3)
    }
    fun listar(): List<Topico>{
        return topicos;
    }

    fun buscarPorId(id: Long): Topico {
       return topicos.stream().filter({
           t -> t.id == id
       }).findFirst().get()
    }

    fun cadastrarTopico(topico: Topico){
        topicos.plus(topico)
    }

}