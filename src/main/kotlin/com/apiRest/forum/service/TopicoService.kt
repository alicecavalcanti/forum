package com.apiRest.forum.service

import com.apiRest.forum.dto.*
import com.apiRest.forum.exception.NotFoundException
import com.apiRest.forum.mapper.RespostasViewMapper
import com.apiRest.forum.mapper.TopicoFormMapper
import com.apiRest.forum.mapper.TopicoViewMapper
import com.apiRest.forum.model.Curso
import com.apiRest.forum.model.Respostas
import com.apiRest.forum.model.Topico
import com.apiRest.forum.model.Usuario
import com.apiRest.forum.repositories.TopicoRepository
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList


@Service
class TopicoService(
    private var listaRespostas: List<Respostas> = ArrayList(),
    private val respostasViewMapper: RespostasViewMapper,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "tópico não encontrado",
    private val topicoRepository: TopicoRepository,
    private val em: EntityManager // há a possibilidade de configurar o entity manager manualmente, mesmo usando o repository
){
    init{
        val resposta1 = Respostas(
            id = 1,
            mensagem = "RESPOSTAS 1",
            autor = Usuario(
                id= 1,
                nome = "JOÃO",
                email = "alice@gmail.com"
            ),
            topico = Topico(
                id = 1,
                titulo = "Duvida Kotlin",
                mensagem = "Variaveis no Kotlin",
                curso = Curso (
                    id = 1,
                    nome = "Kotlin",
                    categoria ="Programacao"
                ),
                autor = Usuario(
                    id = 1,
                    nome = "Ana da Silva",
                    email = "ana@email.com"
                )
            ),
            solucao = true
        )
        val resposta2 = Respostas(
            id = 1,
            mensagem = "RESPOSTAS 2",
            autor = Usuario(
                id= 1,
                nome = "JOÃO",
                email = "alice@gmail.com"
            ),
            topico = Topico(
                id = 1,
                titulo = "Duvida Kotlin",
                mensagem = "Variaveis no Kotlin",
                curso = Curso (
                    id = 1,
                    nome = "Kotlin",
                    categoria ="Programacao"
                ),
                autor = Usuario(
                    id = 1,
                    nome = "Ana da Silva",
                    email = "ana@email.com"
                )
            ),
            solucao = true
        )
        val resposta3 = Respostas(
            id = 1,
            mensagem = "RESPOSTA3",
            autor = Usuario(
                id= 1,
                nome = "João",
                email = "alice@gmail.com"
            ),
            topico = Topico(
                id = 1,
                titulo = "Duvida Kotlin",
                mensagem = "Variaveis no Kotlin",
                curso = Curso (
                    id = 2,
                    nome = "Kotlin",
                    categoria ="Programacao"
                ),
                autor = Usuario(
                    id = 1,
                    nome = "Ana da Silva",
                    email = "ana@email.com"
                )
            ),
            solucao = true
        )
        listaRespostas= Arrays.asList(resposta1, resposta2, resposta3)

    }


    fun listar(nomeCurso : String?, paginacao: Pageable): Page<TopicoView>{
        // o metódo stream realiza uma ação para cada elemento e retorna o elemento como uma classe stream
                                // findAll pega todos os registros do banco de dados
        print(em)
        val topico = if(nomeCurso == null){
            topicoRepository.findAll(paginacao)

        }else {
            topicoRepository.findByCursoNome(nomeCurso, paginacao)
        }
        return topico.map{
            t -> topicoViewMapper.map(t)
        }
    }

    fun buscarTopicoPorId(id: Long): TopicoView {
       var topicoBuscado = topicoRepository.findById(id)
           .orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topicoBuscado);
    }

        fun buscarRespostasTopico(id: Long) : List<RespostasView> {
        val respostas = listaRespostas.stream().filter({
            t -> t.topico.id == id
        }).collect(Collectors.toList());

        var resp: MutableList<RespostasView> = ArrayList()

        for(resposta in respostas){
            resp.add(respostasViewMapper.map(resposta))
        }
        return resp
    }


    // form dto de entrada | view dto saida
    fun cadastrarTopico(novoTopicoForm: NovoTopicoForm): TopicoView{
        var topico = topicoFormMapper.map(novoTopicoForm)
        topicoRepository.save(topico)
        return topicoViewMapper.map(topico)
    }

    fun atualizarTopico(form: AtualizacaoTopicoForm): TopicoView{
                                        //o findById retorna um objeto option encontrado
        var topicoQueSeraAtualizado = topicoRepository.findById(form.id)
            .orElseThrow{NotFoundException(notFoundMessage)}
        topicoQueSeraAtualizado.titulo = form.titulo
        topicoQueSeraAtualizado.mensagem = form.mensagem

        return topicoViewMapper.map(topicoQueSeraAtualizado)
    }

    fun deletarTopico(id : Long){
        topicoRepository.deleteById(id);
    }

    fun relatorio(): List<TopicoPorCategoriaDto>{
        return topicoRepository.relatorio()
    }


}