package kr.hope.springboot.batch.jobs.migrator

import kr.hope.springboot.batch.jobs.JobUtil
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchMigratorConfig {
    companion object {
        private const val JOB_BUILDER_NAME = "jobBuilder"
    }

    @Bean(JobUtil.Name.MIGRATION)
    fun migrationJob(
        jobRepository: JobRepository,
        migration: Step,
    ): Job {
        val jobBuilder = JobBuilder(JOB_BUILDER_NAME, jobRepository)
        return jobBuilder.start(migration).build()
    }
}
