rootProject.name = "spring-boot"

include(
    "orm:jpa",
    "infrastructure:kafka",
    "infrastructure:redis",
    "infrastructure:sqs",
)

makeProjectDir(rootProject, "subprojects")

fun makeProjectDir(
    project: ProjectDescriptor,
    group: String,
) {
    project.children.forEach {
        println("$group -> ${it.name}")

        it.projectDir = file("$group/${it.name}")
        makeProjectDir(it, "$group/${it.name}")
    }
}
