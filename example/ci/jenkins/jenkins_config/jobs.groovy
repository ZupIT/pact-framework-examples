def participants = [
    [
        name: "client-api",
        description: "Client microservice",
        jenkinsfilePath: "example/java/spring-boot/one_consumer_one_provider/client-api/Jenkinsfile",
        repository: {
            git {
                id("consumer-sample")
                remote("https://github.com/ZupIT/pact-framework-examples")
                credentialsId("githubCredentials")
            }
        }
    ]
]

participants.each { service ->
    println("Criando multipipeline for:: $service.name")

    multibranchPipelineJob("${service.name}") {
        description(service.description)

        branchSources service.repository

        factory {
            workflowBranchProjectFactory {
                scriptPath(service.jenkinsfilePath)
            }
        }
    }
}