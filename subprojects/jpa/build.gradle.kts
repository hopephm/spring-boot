val queryDslVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
    runtimeOnly("com.mysql:mysql-connector-j")

    testRuntimeOnly("com.h2database:h2")
}
