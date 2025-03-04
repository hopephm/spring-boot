package kr.hope.springboot.batch.jobs.migrator.reader

import kr.hope.springboot.batch.domain.MockEntity
import kr.hope.springboot.batch.domain.MockService
import org.springframework.batch.item.database.AbstractPagingItemReader
import java.util.concurrent.CopyOnWriteArrayList

class SourceItemReader(
    private val mockService: MockService,
) : AbstractPagingItemReader<MockEntity>() {
    override fun doReadPage() {
        if (this.results == null) {
            this.results = CopyOnWriteArrayList()
        } else {
            this.results.clear()
        }

        val sourceItems = mockService.findAllMockEntities(this.page, this.pageSize)
        this.results.addAll(sourceItems)
    }

    override fun jumpToItem(itemIndex: Int) {}
}