package kr.hope.springboot.batch.jobs.migrator

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchMigratorConfig(
    private val jobBuilder: JobBuilder,
    private val migration: Step,
) {
    @Bean
    fun migrationJob(): Job {
        return jobBuilder.start(migration).build()
    }
}