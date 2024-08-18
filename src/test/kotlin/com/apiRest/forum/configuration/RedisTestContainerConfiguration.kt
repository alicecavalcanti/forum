package com.apiRest.forum.configuration

import com.redis.testcontainers.RedisContainer
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.LoggerFactory
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.utility.DockerImageName


class RedisTestContainerConfiguration : BeforeAllCallback {

    private val log = LoggerFactory.getLogger(RedisTestContainerConfiguration::class.java)
    val redisContainer = RedisContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6379)

    override fun beforeAll(p0: ExtensionContext?) {
        redisContainer.start()
        System.setProperty("spring.redis.host", redisContainer.redisHost)
        System.setProperty("spring.redis.port", redisContainer.getMappedPort(6379).toString())
        log.info("redis container started at ${redisContainer.redisURI}")
    }
}
