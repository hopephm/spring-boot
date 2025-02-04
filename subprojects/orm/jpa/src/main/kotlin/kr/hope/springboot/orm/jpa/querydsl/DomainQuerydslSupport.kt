package kr.hope.springboot.orm.jpa.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import kr.hope.springboot.orm.jpa.config.DomainJpa
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Transactional(DomainJpa.Bean.TRANSACTION_MANAGER)
class DomainQuerydslSupport(
    entityClass: KClass<*>,
) : BaseQuerydslSupport(entityClass) {
    private var jpaQueryFactory: JPAQueryFactory? = null

    override fun getJpaQueryFactory(): JPAQueryFactory {
        return this.jpaQueryFactory ?: throw IllegalStateException("querydsl is null: check entity manager setting")
    }

    @PersistenceContext(unitName = DomainJpa.PERSISTENCE_UNIT_NAME)
    override fun setEntityManager(
        entityManager: EntityManager,
    ) {
        super.setEntityManager(entityManager)
        this.jpaQueryFactory = JPAQueryFactory(entityManager)
    }
}
