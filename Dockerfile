FROM jenkins/jenkins:2.214-alpine

USER root
COPY init.groovy.d /var/jenkins_home/init.groovy.d/
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
RUN /usr/local/bin/install-plugins.sh git build-timeout credentials-binding \
                                      timestamper ws-cleanup kubernetes \
                                      workflow-aggregator pipeline-stage-view \
                                      gitea

USER jenkins
