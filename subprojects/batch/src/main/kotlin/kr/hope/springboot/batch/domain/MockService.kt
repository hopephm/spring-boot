package kr.hope.springboot.batch.domain

import org.springframework.stereotype.Component
import kotlin.math.min

@Component
class MockService(
    private val mockRepository: MockRepository,
) {
    private val entities: List<MockEntity> = (1..150).map { MockEntity(it, it) }

    fun update(entity: MockEntity): MockEntity {
        return mockRepository.save(entity)
    }

    fun findAllMockEntities(
        page: Int,
        pageSize: Int,
    ): List<MockEntity> {
        val startIdx = (page - 1) * pageSize
        val endIdx = min(page * pageSize, entities.size - 1)
        return entities.filter {
            it.id in (startIdx + 1)..endIdx
        }
    }
}