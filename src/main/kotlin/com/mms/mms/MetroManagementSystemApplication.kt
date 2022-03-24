package com.mms.mms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories("com.mms.mms.user.dao")
class MetroManagementSystemApplication

fun main(args: Array<String>) {
	runApplication<MetroManagementSystemApplication>(*args)
}
