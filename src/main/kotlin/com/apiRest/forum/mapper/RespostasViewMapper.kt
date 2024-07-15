package com.apiRest.forum.mapper

import com.apiRest.forum.dto.RespostasView
import com.apiRest.forum.model.Respostas
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class RespostasViewMapper: Mapper<Respostas, RespostasView> {
    override fun map(t: Respostas): RespostasView {
        return RespostasView(
            id = t.id,
            mensagem = t.mensagem,
            nomeAutor = t.autor.nome,
            solucao = t.solucao
        )
    }
}