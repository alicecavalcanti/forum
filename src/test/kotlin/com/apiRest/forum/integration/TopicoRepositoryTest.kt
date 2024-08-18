package com.apiRest.forum.integration

import com.apiRest.forum.configuration.TestContainerMySQLExtension
import com.apiRest.forum.dto.TopicoPorCategoriaDto
import com.apiRest.forum.model.CursoTest
import com.apiRest.forum.model.TopicoTest
import com.apiRest.forum.model.UsuarioTest
import com.apiRest.forum.repositories.CursoRepository
import com.apiRest.forum.repositories.TopicoRepository
import com.apiRest.forum.repositories.UsuarioRepository
import com.apiRest.forum.service.TopicoService
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import kotlin.test.Test
import kotlin.test.assertEquals

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(TestContainerMySQLExtension::class)
class TopicoRepositoryTest {

    @Autowired
    private lateinit var topicoRepository: TopicoRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var cursoRepository: CursoRepository

    private var usuario = UsuarioTest.build()
    private var curso = CursoTest.build()
    private val topico = TopicoTest.build(curso, usuario)


    @BeforeEach
    fun setup() {
        topicoRepository.deleteAll()
        usuarioRepository.deleteAll()
        cursoRepository.deleteAll()
    }

    @Test
    fun `deve gerar um relatorio`() {
        usuarioRepository.save(usuario)
        cursoRepository.save(curso)
        topicoRepository.save(topico)
        val relatorio = topicoRepository.relatorio()
        assertThat(relatorio).isNotNull
        assertThat(relatorio.first()).isExactlyInstanceOf(TopicoPorCategoriaDto::class.java)
    }

    @Test
    fun `deve buscar os topicos por nome do  curso e verificar quantos topicos tem com esse curso`() {
        usuarioRepository.save(usuario)
        cursoRepository.save(curso)
        topicoRepository.save(topico)
        val top = topicoRepository.findByCursoNome("Kotlin avançado", PageRequest.of(0, 5))
        assertEquals(1, top.content.size)
    }
}