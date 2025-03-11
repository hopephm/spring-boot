dependencies {
    // https://docs.spring.io/spring-batch/reference/index.html
    // https://docs.spring.io/spring-boot/how-to/batch.html
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation(project(":orm:jpa"))
    runtimeOnly("com.mysql:mysql-connector-j")
}
