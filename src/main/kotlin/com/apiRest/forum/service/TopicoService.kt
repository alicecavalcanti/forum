package com.apiRest.forum.service

import com.apiRest.forum.dto.AtualizacaoTopicoForm
import com.apiRest.forum.dto.NovoTopicoForm
import com.apiRest.forum.dto.TopicoView
import com.apiRest.forum.exception.NotFoundException
import com.apiRest.forum.mapper.TopicoFormMapper
import com.apiRest.forum.mapper.TopicoViewMapper
import com.apiRest.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class TopicoService(private var listaTopicos: List<Topico> = ArrayList(),
                    private var topicoViewMapper: TopicoViewMapper,
                    private var topicoFormMapper: TopicoFormMapper,
                    private val notFoundMessage: String = "tópico não encontrado"
){

    fun listar(): List<TopicoView>{
        return listaTopicos.stream().map{
            t -> topicoViewMapper.map(t)
        }.collect(Collectors.toList())
    }

    fun buscarPorId(id: Long): TopicoView {
       var topicoBuscado = listaTopicos.stream().filter({
           t -> t.id == id
       }).findFirst().orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topicoBuscado);
    }

    fun cadastrarTopico(novoTopicoForm: NovoTopicoForm): TopicoView{
        var topico = topicoFormMapper.map(novoTopicoForm)
        topico.id = listaTopicos.size.toLong() + 1

        listaTopicos = listaTopicos.plus(topico)

        return topicoViewMapper.map(topico)
    }

    fun atualizarTopico(form: AtualizacaoTopicoForm): TopicoView{
        var topicoQueSeraAtualizado = listaTopicos.stream().filter { t ->
            t.id == form.id
        }.findFirst().orElseThrow{NotFoundException(notFoundMessage)}

        val topicoAtualizado = Topico(
            id = form.id,
            titulo = form.titulo,
            mensagem = form.mensagem,
            autor = topicoQueSeraAtualizado.autor,
            curso = topicoQueSeraAtualizado.curso,
            status = topicoQueSeraAtualizado.status,
            respostas = topicoQueSeraAtualizado.respostas
        )

        listaTopicos = listaTopicos.minus(topicoQueSeraAtualizado).plus(topicoAtualizado)

        return topicoViewMapper.map(topicoAtualizado)
    }

    fun deletarTopico(id : Long){
        // o stream permite utilizar um metódo a cada elemento da lista e retorna um stream como retorno
        val topico = listaTopicos.stream().filter({
            t -> t.id == id
            // o findFirst retorna um option(classe que tem o conteúdo do filtro)
        }).findFirst().orElseThrow{NotFoundException(notFoundMessage)}

        listaTopicos = listaTopicos.minus(topico)
    }

}