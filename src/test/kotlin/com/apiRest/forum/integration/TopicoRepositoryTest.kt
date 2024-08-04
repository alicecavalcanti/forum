package com.apiRest.forum.integration

import com.apiRest.forum.dto.TopicoPorCategoriaDto
import com.apiRest.forum.model.*
import com.apiRest.forum.repositories.CursoRepository
import com.apiRest.forum.repositories.TopicoRepository
import com.apiRest.forum.repositories.UsuarioRepository
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TopicoRepositoryTest {
    // ter um save para que ele limpe a caixa de teste, pois cada teste é independente

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
    fun setup(){
        usuarioRepository.save(usuario)
        cursoRepository.save(curso)
        topicoRepository.save(topico)
    }


    // expressões de objeto: objeto que seja uma modificação da classe sem ser subclasse,
    // são executadas imediatamente quando são usadas, são objetos anônimos de classes anônimas que
    // são classes declaradas sem o class
    companion object{
        @Container
        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0.28").apply {
            withDatabaseName("testdb")
            withUsername("alice")
            withPassword("123")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry){
            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", mysqlContainer::getPassword)
            registry.add("spring.datasource.username", mysqlContainer::getUsername)
        }

    }

    @Test
    fun `deve gerar um relatorio`(){
        val relatorio = topicoRepository.relatorio()
        assertThat(relatorio).isNotNull
        assertThat(relatorio.first()).isExactlyInstanceOf(TopicoPorCategoriaDto::class.java)
    }

    @Test
    fun `deve buscar os topicos por nome do  curso e verificar quantos topicos tem com esse curso`() {
        val top = topicoRepository.findByCursoNome("Kotlin avançado", PageRequest.of(0, 5))
        assertEquals(1, top.content.size)
    }
}