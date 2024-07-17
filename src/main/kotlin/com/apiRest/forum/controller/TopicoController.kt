package com.apiRest.forum.controller

import com.apiRest.forum.dto.*
import com.apiRest.forum.service.TopicoService
import com.apiRest.forum.model.Respostas
import com.apiRest.forum.repositories.TopicoRepository
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder


@RestController
@RequestMapping("/topicos")
                        // construtor primário da classe
class TopicoController(private val topicoService: TopicoService) {

    @GetMapping
    @Cacheable("topicos") // armazena no cache recursos pouco atualizados
    fun listar(
        @RequestParam(required = false) nomeCurso : String?,
        //muda o padrão da paginação para os configurados
        @PageableDefault(size = 4, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable)
        // A classe pageable vai fazer com que a lista dos tópicos venha
        // dentro da paginação, por isso o retorno não é mais um List
        : Page<TopicoView>{

        return topicoService.listar(nomeCurso, paginacao)

    }
    @GetMapping("/{idTopico}/respostas")
    fun listarRespostasTopico(@PathVariable idTopico: Long,
                              @PageableDefault(size = 4, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable)
    : Page<RespostasView> {
        return topicoService.listarRespostasTopico(idTopico, paginacao);
    }
    @GetMapping("/{id}")
    fun buscarTopicoPorId(@PathVariable id : Long) : TopicoView {
        return topicoService.buscarTopicoPorId(id)
    }

    @PostMapping()
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrarTopico(
        // o @valid é do spring validation(somente é cadastrado o tópico que segue a validação)
        @RequestBody @Valid novoTopicoForm : NovoTopicoForm,
        uriBuilder: UriComponentsBuilder
        ) : ResponseEntity<TopicoView>{

        val topicoCadastrado = topicoService.cadastrarTopico(novoTopicoForm)

        val uri = uriBuilder.path("/topicos/${topicoCadastrado.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoCadastrado)

    }
    @PostMapping("/{idTopico}/respostas")
    @Transactional
    fun cadastrarRespostaCodigo(
        @PathVariable idTopico: Long,
        @RequestBody novaRespostaform: NovaRespostaForm,
        uriBuilder: UriComponentsBuilder): ResponseEntity<RespostasView>{

        val respostaCadastrada = topicoService.cadastrarRespostaTopico(novaRespostaform, idTopico)
        val uri = uriBuilder.path("/topicos/${idTopico}/respostas").build().toUri()
        return ResponseEntity.created(uri).body(respostaCadastrada)
    }

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun atualizarTopico (@RequestBody @Valid form: AtualizacaoTopicoForm): TopicoView{
        val topicoView = topicoService.atualizarTopico(form)
        return topicoView
    }
    @PutMapping("/{idTopico}/respostas")
    @Transactional
    fun atualizarRespostaTopico (@PathVariable idTopico: Long,@RequestBody @Valid form: AtualizacaoRespostasForm): RespostasView{
        val respostaView = topicoService.atualizarRespostaTopico(idTopico, form)
        return respostaView
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletarTopico (@PathVariable id: Long){
        topicoService.deletarTopico(id)
    }
    @DeleteMapping("/respostas/{id}")
    @Transactional
    fun deletarResposta(@PathVariable id: Long){
        topicoService.deletarRespostaTopico(id)
    }

    @GetMapping("/relatorio")
    fun relatorio(): List<TopicoPorCategoriaDto>{
        return topicoService.relatorio()
    }

}