package com.apiRest.forum.mapper

import com.apiRest.forum.dto.TopicoView
import com.apiRest.forum.model.Topico
import org.springframework.stereotype.Component

@Component
class TopicoViewMapper : Mapper<Topico, TopicoView> {
    override fun map(topico: Topico): TopicoView {
       return TopicoView(
            id = topico.id,
            titulo = topico.titulo,
            mensagem = topico.mensagem,
            status = topico.status,
            dataCriacao = topico.dataCriacao,
           dataAlteracao = topico.dataAlteracao
       )
    }

}