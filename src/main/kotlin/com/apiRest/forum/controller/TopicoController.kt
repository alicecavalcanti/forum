package com.apiRest.forum.controller

import com.apiRest.forum.dto.AtualizacaoTopicoForm
import com.apiRest.forum.dto.NovoTopicoForm
import com.apiRest.forum.service.TopicoService
import com.apiRest.forum.dto.TopicoView
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriComponentsBuilder
import kotlin.io.path.toPath

@RestController
@RequestMapping("/topicos")
                        // construtor primário da classe
class TopicoController(private val topicoService: TopicoService) {

    @GetMapping
    fun listar() : List<TopicoView>{
        return topicoService.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id : Long) : TopicoView {
        return topicoService.buscarPorId(id)
    }

    @PostMapping()
    fun cadastrarTopico(
        // o @valid é do spring validation(somente é cadastrado o tópico que segue a validação)
        @RequestBody @Valid novoTopicoForm : NovoTopicoForm,
        uriBuilder: UriComponentsBuilder
        ) : ResponseEntity<TopicoView>{
        val topicoCadastrado = topicoService.cadastrarTopico(novoTopicoForm)

        val uri = uriBuilder.path("/topicos/${topicoCadastrado.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoCadastrado)

    }

    @PutMapping
    fun atualizarTopico (@RequestBody @Valid form: AtualizacaoTopicoForm): TopicoView{
        val topicoView = topicoService.atualizarTopico(form)

        return topicoView
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletarTopico (@PathVariable id: Long){
        topicoService.deletarTopico(id)
    }

}