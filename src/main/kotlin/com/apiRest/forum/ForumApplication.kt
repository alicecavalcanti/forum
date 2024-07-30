package com.apiRest.forum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableCaching
@SpringBootApplication
class ForumApplication
fun main(args: Array<String>) {
	runApplication<ForumApplication>(*args)
}
