# Starting off with the Jenkins base Image
FROM jenkins/jenkins:2.230-jdk11
 
# Installing the plugins we need using the in-built install-plugins.sh script
RUN JENKINS_UC_DOWNLOAD=http://archives.jenkins-ci.org /usr/local/bin/install-plugins.sh git matrix-auth workflow-aggregator \
    docker-workflow blueocean job-dsl credentials-binding simple-theme-plugin \
    jquery gravatar

# Setting up environment variables for Jenkins admin user
ENV JENKINS_USER admin
ENV JENKINS_PASS admin
 
# Skip the initial setup wizard
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
# Start-up scripts to set number of executors and creating the admin user
COPY executors.groovy /usr/share/jenkins/ref/init.groovy.d/executors.groovy
COPY default-user.groovy /usr/share/jenkins/ref/init.groovy.d/default-user.groovy
COPY create-jobs.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY jobs.groovy /var/jenkins-jobs/
COPY jenkins-theme.groovy /usr/share/jenkins/ref/init.groovy.d/jenkins-theme.groovy

VOLUME /var/jenkins_home

