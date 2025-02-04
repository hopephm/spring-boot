package kr.hope.springboot.kafka.listener

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MskEventListener {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["\${kafka.hope.topic}"], groupId = "\${kafka.hope.group-id}", autoStartup = "\${kafka.hope.auto-start-up}")
    fun consumeCar(payload: ConsumerRecord<String, String>) {
        logger.info("Received message: ${payload.value()}")
    }
}
