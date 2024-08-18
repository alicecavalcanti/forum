package com.apiRest.forum.configuration

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.LoggerFactory
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

class TestContainerMySQLExtension : BeforeAllCallback {

    private val log = LoggerFactory.getLogger(TestContainerMySQLExtension::class.java)
    private val mySQLContainer = MySQLContainer(DockerImageName.parse("mysql:8.0.28"))

    override fun beforeAll(p0: ExtensionContext?) {
        mySQLContainer.start()
        System.setProperty("spring.datasource.url", mySQLContainer.jdbcUrl)
        System.setProperty("spring.datasource.password", mySQLContainer.password)
        System.setProperty("spring.datasource.username", mySQLContainer.username)
        log.info("mysql container started at ${mySQLContainer.jdbcUrl}")
    }
}