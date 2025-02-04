package kr.hope.springboot.infrastructure.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "kr.hope.springboot.kafka",
    ]
)
class KafkaConsumeApplication

fun main(args: Array<String>) {
    runApplication<KafkaConsumeApplication>(*args)
}
