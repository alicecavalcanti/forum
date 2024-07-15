package com.apiRest.forum.repositories

import com.apiRest.forum.model.Curso
import org.springframework.data.jpa.repository.JpaRepository

interface CursoRepository : JpaRepository<Curso, Long>{
}