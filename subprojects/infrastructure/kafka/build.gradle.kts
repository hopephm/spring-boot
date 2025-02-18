val awsSdkVersion: String by project
val awsMskIamAuthVersion: String by project

dependencies {
    implementation("org.springframework.kafka:spring-kafka")
    implementation("software.amazon.msk:aws-msk-iam-auth:$awsMskIamAuthVersion")
}
