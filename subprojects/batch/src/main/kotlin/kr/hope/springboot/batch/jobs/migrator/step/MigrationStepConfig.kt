package kr.hope.springboot.batch.jobs.migrator.step

import kr.hope.springboot.batch.domain.MockEntity
import kr.hope.springboot.batch.domain.MockService
import kr.hope.springboot.batch.jobs.migrator.reader.SourceItemReader
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.AbstractPagingItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class MigrationStepConfig(
    private val mockService: MockService,
    private val transactionManager: PlatformTransactionManager,
) {
    companion object {
        private const val CHUNK = 1000
    }

    @Bean
    @JobScope
    fun migration(
        stepBuilder: StepBuilder,
    ): Step {
        return stepBuilder
            .chunk<MockEntity, MockEntity>(CHUNK, transactionManager)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build()
    }

    private fun reader(): AbstractPagingItemReader<MockEntity> {
        return SourceItemReader(mockService).apply {
            this.pageSize = CHUNK
        }
    }

    private fun processor(): ItemProcessor<MockEntity, MockEntity> {
        return ItemProcessor { source ->
            source.apply {
                source.value += 1
            }
        }
    }

    private fun writer(): ItemWriter<MockEntity> {
        return ItemWriter { mockEntityList ->
            mockEntityList.forEach { mockEntity ->
                mockService.update(mockEntity)
            }
        }
    }
}