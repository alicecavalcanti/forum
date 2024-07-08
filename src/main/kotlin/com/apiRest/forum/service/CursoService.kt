package com.apiRest.forum.service

import com.apiRest.forum.model.Curso
import org.springframework.stereotype.Service
import java.util.*

@Service
class CursoService (var cursos: List<Curso>){
    // init é um bloco de inicialização que está no construtor quando ele é chamado
    init{
        val curso = Curso(
                id = 1,
                nome= "Kotlin",
                categoria = "Programação"
        )
        val curso2 = Curso(
            id = 2,
            nome= "Java",
            categoria = "Programação"
        )
        cursos = Arrays.asList(curso, curso2);
    }
    fun buscarPorId(idNovoTopico: Long) : Curso{
        return cursos.stream().filter({ c ->
            c.id == idNovoTopico
        }).findFirst().get()
    }

}