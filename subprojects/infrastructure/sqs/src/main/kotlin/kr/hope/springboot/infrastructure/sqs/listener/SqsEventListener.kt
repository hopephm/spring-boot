package kr.hope.springboot.infrastructure.sqs.listener

import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.stereotype.Component

/**
 * @see https://docs.spring.io/spring-cloud-aws/docs/2.2.6.RELEASE/2.2.6.RELEASE/reference/html/
 */
@Component
class SqsEventListener {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @SqsListener(value = ["\${sqs.hope.queue}"], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun consume(message: String, @Headers headers: Map<String, String>) {
        logger.info("Received headers: $headers")
        logger.info("Received message: $message")
    }
}
