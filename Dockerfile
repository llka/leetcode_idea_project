FROM openjdk:jdk-alpine
COPY target/leetcode-1.0-SNAPSHOT.jar /deployments/
CMD java -jar /deployments/leetcode-1.0-SNAPSHOT.jar