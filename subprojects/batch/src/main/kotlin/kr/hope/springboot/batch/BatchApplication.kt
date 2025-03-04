package kr.hope.springboot.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(scanBasePackages = ["kr.hope.springboot.batch"])
@EnableScheduling
class BatchApplication

// TODO: spring batch version migration runtime dependency error resolve
fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}