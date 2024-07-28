package com.apiRest.forum.controller

import com.apiRest.forum.model.Usuario
import com.apiRest.forum.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign-up")
class SignUpController(private val usuarioService: UsuarioService) {
    @PostMapping
    fun cadastrarUser(@RequestBody usuario: Usuario): ResponseEntity<Usuario>{
       val usuarioCadastrado= usuarioService.cadatrarUser(usuario)

        return ResponseEntity.status(201).body(usuarioCadastrado)
    }

}