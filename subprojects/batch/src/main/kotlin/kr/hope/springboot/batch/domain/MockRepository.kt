package kr.hope.springboot.batch.domain

import org.springframework.stereotype.Component

@Component
class MockRepository {
    fun save(entity: MockEntity): MockEntity {
        return entity
    }
}