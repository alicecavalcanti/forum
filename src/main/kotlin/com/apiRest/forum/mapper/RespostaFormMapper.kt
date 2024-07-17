package com.apiRest.forum.mapper

import com.apiRest.forum.dto.NovaRespostaForm
import com.apiRest.forum.model.Respostas
import com.apiRest.forum.repositories.RespostasRepository
import com.apiRest.forum.repositories.TopicoRepository
import com.apiRest.forum.service.TopicoService
import com.apiRest.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class RespostaFormMapper (private val usuarioService: UsuarioService): Mapper<NovaRespostaForm, Respostas>{
    override fun map(t:NovaRespostaForm): Respostas{
        return Respostas(
            mensagem = t.mensagem,
            autor = usuarioService.buscarAutorTopico(t.idAutor),
            solucao = true
        )
    }
}