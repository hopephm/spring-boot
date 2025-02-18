val springCloudVersion: String by project
val awsSdkVersion: String by project

dependencies {
    implementation(platform("software.amazon.awssdk:bom:$awsSdkVersion"))
    implementation("software.amazon.awssdk:sts")

    api("org.springframework.cloud:spring-cloud-aws-autoconfigure:$springCloudVersion")
    api("org.springframework.cloud:spring-cloud-aws-messaging:$springCloudVersion")
    api("org.springframework.cloud:spring-cloud-starter-aws:$springCloudVersion")
}
