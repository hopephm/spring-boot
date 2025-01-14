package kr.hope.springboot.jpa.querydsl

import com.querydsl.jpa.JPQLQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import kotlin.reflect.KClass

abstract class BaseQuerydslSupport(
    entityClass: KClass<*>,
) : QuerydslRepositorySupport(entityClass.java) {
    fun <T> JPQLQuery<T>.fetchPage(pageable: Pageable): Page<T> {
        val dsl = super.getQuerydsl() ?: throw IllegalStateException("querydsl is null: check entity manager setting")
        val jpqlQuery = dsl.applyPagination(pageable, this)
        return PageableExecutionUtils.getPage(
            jpqlQuery.fetch(),
            pageable,
            this::fetchCount,
        )
    }

    abstract fun getJpaQueryFactory(): JPAQueryFactory
}
