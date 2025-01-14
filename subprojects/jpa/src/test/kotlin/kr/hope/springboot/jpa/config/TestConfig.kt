package kr.hope.springboot.jpa.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@ComponentScan(
    basePackages = ["kr.hope.springboot.jpa"],
)
@Configuration
class TestConfig
