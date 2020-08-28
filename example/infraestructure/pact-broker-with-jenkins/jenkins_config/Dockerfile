# Starting off with the Jenkins base Image
FROM jenkins/jenkins:jdk11
 
# Installing the plugins we need using the in-built install-plugins.sh script
RUN /usr/local/bin/install-plugins.sh git matrix-auth workflow-aggregator \
    docker-workflow blueocean job-dsl credentials-binding simple-theme-plugin \
    jquery gravatar pipeline-input-step nodejs

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
COPY config-node.groovy /usr/share/jenkins/ref/init.groovy.d/

VOLUME /var/jenkins_home

USER root
# Set the Chrome repo.
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list
# Install Chrome.
RUN apt-get update && apt-get -y install google-chrome-stable