package com.apiRest.forum.service
import com.apiRest.forum.dto.*
import com.apiRest.forum.exception.NotFoundException
import com.apiRest.forum.mapper.RespostaFormMapper
import com.apiRest.forum.mapper.RespostaViewMapper
import com.apiRest.forum.mapper.TopicoFormMapper
import com.apiRest.forum.mapper.TopicoViewMapper
import com.apiRest.forum.model.Topico
import com.apiRest.forum.repositories.RespostasRepository
import com.apiRest.forum.repositories.TopicoRepository
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class TopicoService(
    private val respostasRepository: RespostasRepository,
    private val respostaFormMapper: RespostaFormMapper,
    private val respostaViewMapper: RespostaViewMapper,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "tópico não encontrado",
    private val topicoRepository: TopicoRepository,
    private val em: EntityManager // há a possibilidade de configurar o entity manager manualmente, mesmo usando o repository
){
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
    fun listarRespostasTopico(id: Long, paginacao: Pageable) : Page<RespostasView> {
        val respostas = respostasRepository.findByTopicoId(id, paginacao)
        return respostas.map { t-> respostaViewMapper.map(t) }
    }

    fun buscarTopicoPorId(id: Long): TopicoView {
       var topicoBuscado = topicoRepository.findById(id)
           .orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topicoBuscado);
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
        topicoQueSeraAtualizado.dataAlteracao= LocalDate.now()

        topicoRepository.save(topicoQueSeraAtualizado)
        return topicoViewMapper.map(topicoQueSeraAtualizado)
    }
    fun atualizarRespostaTopico(idTopico: Long, form: AtualizacaoRespostasForm): RespostasView {
        var respostaQueSeraAtualizada=  respostasRepository.findById(form.id).orElseThrow({NotFoundException(notFoundMessage)})

            respostaQueSeraAtualizada.mensagem = form.mensagem

            respostasRepository.save(respostaQueSeraAtualizada)

            return respostaViewMapper.map(respostaQueSeraAtualizada)
    }

    fun deletarTopico(id : Long){
        topicoRepository.deleteById(id);
    }
    fun deletarRespostaTopico(id : Long){
        respostasRepository.deleteById(id);
    }

    fun relatorio(): List<TopicoPorCategoriaDto>{
        return topicoRepository.relatorio()
    }

    fun cadastrarRespostaTopico(novaRespostaform: NovaRespostaForm, idTopico: Long): RespostasView {
        val resposta = respostaFormMapper.map(novaRespostaform)
        resposta.topico = findTopico(idTopico)

        respostasRepository.save(resposta)

        return respostaViewMapper.map(resposta)

    }
    fun findTopico(idTopico: Long): Topico{
        return topicoRepository.findById(idTopico).orElseThrow({NotFoundException("Tópico não encontrado")})
    }



}