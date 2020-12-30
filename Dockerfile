FROM adoptopenjdk/openjdk12:alpine-jre
#FROM openjdk:12-jdk-oracle
ARG DEPENDENCY=target
COPY ${DEPENDENCY}/classes/wait-for-it.sh /etc/wait-for-it.sh
RUN chmod -R 755 /etc/wait-for-it.sh
ENTRYPOINT exec sh /etc/wait-for-it.sh www.google.com:80


#COPY target/leetcode-1.0-SNAPSHOT.jar /deployments/
#CMD java -jar /deployments/leetcode-1.0-SNAPSHOT.jar