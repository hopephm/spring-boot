package kr.hope.springboot.jpa.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@ComponentScan(
    basePackages = ["kr.hope.springboot.jpa"],
)
@Configuration
class DefaultConfig {
    companion object {
        const val DEFAULT_PROFILES = "test"
        const val TEST_PROPERTY_SOURCE = "classpath:application-test.yml"
    }
}
