package kr.hope.springboot.orm.jpa.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import kr.hope.springboot.orm.jpa.config.properties.DataSourceExtensionProperties
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

object JpaCommon {
    const val PACKAGE = "kr.hope.springboot.orm.jpa"
    const val PERSISTENCE_UNIT_NAME = "-persistence-unit"

    object Resource {
        const val DATASOURCE = "datasource"
        const val EXTENSION = "$DATASOURCE.extension"
    }

    object Bean {
        const val DATASOURCE_PROP = "DatasourceProperties"
        const val EXTENSION_PROP = "ExtensionProperties"
        const val DATASOURCE = "DataSource"
        const val ENTITY_MANAGER_FACTORY = "EntityManagerFactory"
        const val TRANSACTION_MANAGER = "TransactionManager"
    }
}

/**
 * Multiple Jpa Auditing configuration
 * @see https://github.com/spring-projects/spring-data-jpa/blob/0187162bfaae9eb3033093e9041b989312f4d036/spring-data-jpa/src/main/java/org/springframework/data/jpa/repository/config/JpaAuditingRegistrar.java
 * @see https://github.com/spring-projects/spring-data-commons/blob/main/src/main/java/org/springframework/data/auditing/config/AuditingBeanDefinitionRegistrarSupport.java
 */
@EnableJpaAuditing
abstract class BaseJpaConfiguration {
    abstract fun basePackage(): String
    abstract fun persistenceUnitName(): String

    open fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    open fun dataSourceExtensionProperties(): DataSourceExtensionProperties {
        return DataSourceExtensionProperties()
    }

    open fun dataSource(
        dataSourceProperties: DataSourceProperties,
        dataSourceExtensionProperties: DataSourceExtensionProperties,
    ): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = dataSourceProperties.url
        hikariConfig.username = dataSourceProperties.username
        hikariConfig.password = dataSourceProperties.password
        dataSourceExtensionProperties.maxLifetime?.let {
            hikariConfig.maxLifetime = it
        }

        return HikariDataSource(hikariConfig)
    }

    open fun entityManagerFactory(
        dataSource: DataSource,
        dataExtensionProperties: DataSourceExtensionProperties,
    ): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(true)

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan(basePackage())
        factory.persistenceUnitName = persistenceUnitName()
        factory.dataSource = dataSource
        factory.jpaPropertyMap = mapOf(
            "hibernate.physical_naming_strategy" to CamelCaseToUnderscoresNamingStrategy::class.java.name,
            "hibernate.hbm2ddl.auto" to dataExtensionProperties.ddlAuto,
            "hibernate.dialect" to dataExtensionProperties.dialect,
            "hibernate.show_sql" to dataExtensionProperties.showSql,
            "hibernate.format_sql" to dataExtensionProperties.formatSql,
            "hibernate.use_sql_comments" to dataExtensionProperties.useSqlComments,
        )

        return factory
    }

    open fun transactionManager(
        entityManagerFactory: EntityManagerFactory,
    ): PlatformTransactionManager {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}
