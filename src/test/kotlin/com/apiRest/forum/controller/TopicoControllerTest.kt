package com.apiRest.forum.controller

import com.apiRest.forum.config.JWTUtil
import com.apiRest.forum.configuration.RedisTestContainerConfiguration
import com.apiRest.forum.configuration.TestContainerMySQLExtension
import com.apiRest.forum.model.CursoTest
import com.apiRest.forum.model.Role
import com.apiRest.forum.model.TopicoTest
import com.apiRest.forum.model.UsuarioTest
import com.apiRest.forum.repositories.CursoRepository
import com.apiRest.forum.repositories.TopicoRepository
import com.apiRest.forum.repositories.UsuarioRepository
import com.apiRest.forum.service.TopicoService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(RedisTestContainerConfiguration::class, TestContainerMySQLExtension::class)
class TopicoControllerTest {

    @Autowired
    private lateinit var topicoRepository: TopicoRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var topicoService: TopicoService

    @Autowired
    private lateinit var cursoRepository: CursoRepository

    private var usuario = UsuarioTest.build()

    private var curso = CursoTest.build()

    private val topico = TopicoTest.build(curso, usuario)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var jwtUtil: JWTUtil
    companion object{
        private const val RECURSO = "/topicos"
        private val RECURSO_ID = RECURSO.plus("/"+"%s")
    }
    private var token : String? = null
    @BeforeEach
    fun setup() {
        usuarioRepository.deleteAll()
        cursoRepository.deleteAll()
        topicoRepository.deleteAll()

        token = gerarToken()
    }
    @Test
    fun `deve retornar codigo 400 quando chamar topicos sem token`(){
        usuarioRepository.save(usuario)
        cursoRepository.save(curso)
        topicoRepository.save(topico)
        mockMvc.get(RECURSO).andExpect { status { is4xxClientError() } }
    }

    fun gerarToken(): String? {
        usuarioRepository.save(usuario)
        cursoRepository.save(curso)
        topicoRepository.save(topico)
        val authorities = mutableListOf(Role(1, "LEITURA-ESCRITA"))
        return jwtUtil.generateToken("alice@email.com", authorities)
    }

    @Test
    fun `deve retornar codigo 200 quando chamar topicos com token`(){
        mockMvc.get(RECURSO){
            headers { token?.let { this.setBearerAuth(it) } }
        }.andExpect{ status { is2xxSuccessful() } }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar topico por id com token`(){
        mockMvc.get(RECURSO_ID.format("1")){
            headers{ token?.let{this.setBearerAuth(it)} }
        }.andExpect{status { is2xxSuccessful() }}
    }
}
