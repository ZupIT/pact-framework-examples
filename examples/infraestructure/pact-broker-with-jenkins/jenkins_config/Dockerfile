# Starting off with the Jenkins base Image
FROM jenkins/jenkins:lts
USER root
# Installing the plugins we need using the in-built install-plugins.sh script

RUN apt-get update && \
    apt-get -y install apt-transport-https \
      ca-certificates \
      curl \
      gnupg2 \
      software-properties-common && \
    curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
    add-apt-repository \
      "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
      $(lsb_release -cs) \
      stable" && \
   apt-get update && \
   apt-get -y install docker-ce && \
   curl -L "https://github.com/docker/compose/releases/download/1.27.4/docker-compose-Linux-x86_64" -o /usr/local/bin/docker-compose && \
   chmod +x /usr/local/bin/docker-compose \
    && usermod -aG docker jenkins && \
      usermod -aG root jenkins \
      && chmod g+s /usr/bin/docker

RUN /usr/local/bin/install-plugins.sh workflow-aggregator git ssh ssh-agent docker-plugin  \
    docker-build-step dockerhub-notification oauth-credentials job-dsl simple-theme-plugin \
    docker-workflow blueocean matrix-auth gravatar pipeline-input-step nodejs \
    credentials-binding jquery permissive-script-security durable-task

# Setting up environment variables for Jenkins admin user
ENV JENKINS_USER admin
ENV JENKINS_PASS admin

# Skip the initial setup wizard
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false -Dpermissive-script-security.enabled=true -Dorg.jenkinsci.plugins.durabletask.BourneShellScript.LAUNCH_DIAGNOSTICS=true"

# Start-up scripts to set number of executors and creating the admin user
COPY executors.groovy /usr/share/jenkins/ref/init.groovy.d/executors.groovy
COPY default-user.groovy /usr/share/jenkins/ref/init.groovy.d/default-user.groovy
COPY create-jobs.groovy /usr/share/jenkins/ref/init.groovy.d/create-jobs.groovy
COPY jobs.groovy /var/jenkins-jobs/jobs.groovy
COPY jenkins-theme.groovy /usr/share/jenkins/ref/init.groovy.d/jenkins-theme.groovy
COPY config-node.groovy /usr/share/jenkins/ref/init.groovy.d/config-node.groovy

VOLUME /var/jenkins_home
RUN chmod -R 777 /var/jenkins_home