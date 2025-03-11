package kr.hope.springboot.batch.config

import kr.hope.springboot.batch.runner.BatchJobRegister
import org.springframework.batch.core.Job
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Configuration
class BatchConfiguration {
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    fun batchJobRegister(
        jobLauncher: JobLauncher,
        jobs: List<Job>,
    ): BatchJobRegister {
        return BatchJobRegister(jobLauncher, jobs)
    }
}
