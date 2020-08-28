def participants = [
    [
        name: "exemplo frontend (consumer)",
        description: "Consumer Angular APP",
        jenkinsfilePath: "example/frontend/consumer/Jenkinsfile",
        repository: {
            git {
                id("consumer-sample")
                remote("https://github.com/ZupIT/pact-framework-examples")
            }
        }
    ],
    [
        name: "exemplo backend (provider)",
        description: "Provider Node APP",
        jenkinsfilePath: "example/frontend/old_provider/Jenkinsfile",
        repository: {
            git {
                id("provider-sample")
                remote("https://github.com/ZupIT/pact-framework-examples")
            }
        }
    ]
]

participants.each { service ->
    println("Criando multipipeline para:: $service.name")

    multibranchPipelineJob("${service.name}") {

        displayName(service.name)

        description(service.description)

        branchSources service.repository

        factory {
            workflowBranchProjectFactory {
                scriptPath(service.jenkinsfilePath)
            }
        }
    }
}