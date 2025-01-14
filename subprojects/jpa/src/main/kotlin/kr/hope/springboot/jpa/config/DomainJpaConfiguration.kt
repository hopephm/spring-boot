package kr.hope.springboot.jpa.config

import jakarta.persistence.EntityManagerFactory
import kr.hope.springboot.jpa.config.properties.DataSourceExtensionProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

object DomainJpa {
    private const val DOMAIN = "domain"
    const val PACKAGE = "${JpaCommon.PACKAGE}.$DOMAIN"
    const val PERSISTENCE_UNIT_NAME = "$DOMAIN${JpaCommon.PERSISTENCE_UNIT_NAME}"

    object Resource {
        private const val RESOURCE_ROOT = "spring-domain-jpa"
        const val DATASOURCE = "$RESOURCE_ROOT.${JpaCommon.Resource.DATASOURCE}"
        const val EXTENSION = "$RESOURCE_ROOT.${JpaCommon.Resource.EXTENSION}"
    }

    object Bean {
        const val DATASOURCE_PROP = "$DOMAIN${JpaCommon.Bean.DATASOURCE_PROP}"
        const val EXTENSION_PROP = "$DOMAIN${JpaCommon.Bean.EXTENSION_PROP}"
        const val DATASOURCE = "$DOMAIN${JpaCommon.Bean.DATASOURCE}"
        const val ENTITY_MANAGER_FACTORY = "$DOMAIN${JpaCommon.Bean.ENTITY_MANAGER_FACTORY}"
        const val TRANSACTION_MANAGER = "$DOMAIN${JpaCommon.Bean.TRANSACTION_MANAGER}"
    }
}

@Configuration
@EnableJpaRepositories(
    basePackages = [DomainJpa.PACKAGE],
    entityManagerFactoryRef = DomainJpa.Bean.ENTITY_MANAGER_FACTORY,
    transactionManagerRef = DomainJpa.Bean.TRANSACTION_MANAGER,
)
@EnableTransactionManagement
class DomainJpaConfiguration : BaseJpaConfiguration() {
    override fun basePackage() = DomainJpa.PACKAGE
    override fun persistenceUnitName() = DomainJpa.PERSISTENCE_UNIT_NAME

    @Bean(DomainJpa.Bean.DATASOURCE_PROP)
    @ConfigurationProperties(DomainJpa.Resource.DATASOURCE)
    override fun dataSourceProperties() = super.dataSourceProperties()

    @Bean(DomainJpa.Bean.EXTENSION_PROP)
    @ConfigurationProperties(DomainJpa.Resource.EXTENSION)
    override fun dataSourceExtensionProperties() = super.dataSourceExtensionProperties()

    @Bean(DomainJpa.Bean.DATASOURCE)
    override fun dataSource(
        @Qualifier(DomainJpa.Bean.DATASOURCE_PROP)
        dataSourceProperties: DataSourceProperties,
        @Qualifier(DomainJpa.Bean.EXTENSION_PROP)
        dataSourceExtensionProperties: DataSourceExtensionProperties,
    ) = super.dataSource(dataSourceProperties, dataSourceExtensionProperties)

    @Bean(DomainJpa.Bean.ENTITY_MANAGER_FACTORY)
    override fun entityManagerFactory(
        @Qualifier(DomainJpa.Bean.DATASOURCE)
        dataSource: DataSource,
        @Qualifier(DomainJpa.Bean.EXTENSION_PROP)
        dataExtensionProperties: DataSourceExtensionProperties,
    ) = super.entityManagerFactory(dataSource, dataExtensionProperties)

    @Bean(DomainJpa.Bean.TRANSACTION_MANAGER)
    override fun transactionManager(
        @Qualifier(DomainJpa.Bean.ENTITY_MANAGER_FACTORY)
        entityManagerFactory: EntityManagerFactory,
    ) = super.transactionManager(entityManagerFactory)
}
