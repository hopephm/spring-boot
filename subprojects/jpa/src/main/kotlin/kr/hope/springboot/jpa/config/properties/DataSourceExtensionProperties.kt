package kr.hope.springboot.jpa.config.properties

class DataSourceExtensionProperties {
    var maxLifetime: Long? = null
    var dialect: String? = null
    var ddlAuto: String? = null
    var showSql: Boolean? = null
    var formatSql: Boolean? = null
    var useSqlComments: Boolean? = null
}
