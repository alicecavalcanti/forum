package com.apiRest.forum.controller

import com.apiRest.forum.config.JWTUtil
import com.apiRest.forum.model.Role
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {
    // precisa ter um tópico e usuario  inserido no banco para que esse teste funcione

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    // o lateinit é um modificador que permite que a variável seja inicializada tardiamente
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
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder?>(
                SecurityMockMvcConfigurers
                    .springSecurity()).build()

        token = gerarToken()
    }

    @Test
    fun `deve retornar codigo 400 quando chamar topicos sem token`(){
        mockMvc.get(RECURSO).andExpect { status { is4xxClientError() } }
    }

    fun gerarToken(): String? {
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