package com.apiRest.forum.mapper

import com.apiRest.forum.dto.NovoTopicoForm
import com.apiRest.forum.model.Topico
import com.apiRest.forum.service.CursoService
import com.apiRest.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper (private val cursoService: CursoService,
                        private val UsuarioService: UsuarioService): Mapper<NovoTopicoForm, Topico> {
    override fun map(t: NovoTopicoForm): Topico {
        return Topico(
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = cursoService.buscarPorId(t.idCurso),
            autor = UsuarioService.buscarPorId(t.idAutor)
        )
    }
}