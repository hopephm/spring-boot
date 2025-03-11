package kr.hope.springboot.batch.runner

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner

class BatchJobRegister(
    private val jobLauncher: JobLauncher,
    private val jobs: List<Job>,
) : ApplicationRunner  {
    override fun run(args: ApplicationArguments?) {
        jobs.forEach {
            jobLauncher.run(it, JobParameters())
        }
    }
}
