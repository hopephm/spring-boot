val awsSdkVersion: String by project
val awsMskIamAuthVersion: String by project

dependencies {
    implementation("org.springframework.kafka:spring-kafka")
    implementation(platform("software.amazon.awssdk:bom:$awsSdkVersion"))
    implementation("software.amazon.msk:aws-msk-iam-auth:$awsMskIamAuthVersion")
}
