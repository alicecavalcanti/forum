package com.apiRest.forum.service

import com.apiRest.forum.exception.NotFoundException
import com.apiRest.forum.mapper.RespostaFormMapper
import com.apiRest.forum.mapper.RespostaViewMapper
import com.apiRest.forum.mapper.TopicoFormMapper
import com.apiRest.forum.mapper.TopicoViewMapper
import com.apiRest.forum.model.Topico
import com.apiRest.forum.model.TopicoTest
import com.apiRest.forum.model.TopicoViewTest
import com.apiRest.forum.repositories.RespostasRepository
import com.apiRest.forum.repositories.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.util.Assert
import java.util.*

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginacao:Pageable = mockk()

    val topicoRepository: TopicoRepository = mockk{
        every { findByCursoNome(any(), any()) } returns topicos
        // every - indicar uma ação que irá acontecer toda vez que o metódodo dentro do tópico repository for chamado
        every { findAll(paginacao) } returns topicos
    }

    val respostasRepository: RespostasRepository = mockk()
    val respostaFormMapper: RespostaFormMapper =  mockk()
    val respostaViewMapper: RespostaViewMapper = mockk()
    val topicoViewMapper: TopicoViewMapper= mockk{
        every { map(any())} returns TopicoViewTest.build()
    }
    val topicoFormMapper: TopicoFormMapper= mockk()


    val topicoService = TopicoService(
        topicoRepository, respostasRepository, respostaFormMapper, respostaViewMapper, topicoViewMapper,
        topicoFormMapper
    )

    @Test
    fun `deve listar tópicos a partir do nome do curso`(){

        every { topicoViewMapper.map(capture(slot())) } returns TopicoViewTest.build()

        topicoService.listar("Kotlin avançado", paginacao)

        val slot = slot<Topico>()

        verify (exactly = 1) { topicoRepository.findByCursoNome(any(), any())}
        verify (exactly = 1) {topicoViewMapper.map(capture(slot))}
        verify (exactly = 0) {topicoRepository.findAll(paginacao)}

        val topico = TopicoTest.build()

        assertThat(slot.captured.titulo).isEqualTo(topico.titulo)
        assertThat(slot.captured.mensagem).isEqualTo(topico.mensagem)


    }

    @Test
    fun `deve listar todos os topicos quando o nome do curso for nulo`(){
        topicoService.listar(null, paginacao)

        verify (exactly = 0) { topicoRepository.findByCursoNome(any(), any())}
        verify (exactly = 1) {topicoViewMapper.map(any())}
        verify (exactly = 1) {topicoRepository.findAll(paginacao)}

    }

    @Test
    fun `deve listar not found exception quando topico nao for achado`(){
        every { topicoRepository.findById(any())} returns Optional.empty()

        val atual = assertThrows<NotFoundException>{
            topicoService.buscarTopicoPorId(1)
        }

        assertThat(atual.message).isEqualTo("tópico não encontrado")
    }

}