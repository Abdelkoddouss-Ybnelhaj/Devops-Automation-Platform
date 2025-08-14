FROM jenkins/jenkins:lts

USER root

# Install prerequisites
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        curl \
        docker.io

# Install Node.js (which includes npm) using NodeSource
# RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
#     apt-get install -y nodejs && \
#     npm install -g newman newman-reporter-html

# Add Maven Installation


# Add jenkins user to docker group
RUN usermod -aG docker jenkins && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install Jenkins plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

USER jenkins
WORKDIR /var/jenkins_home
