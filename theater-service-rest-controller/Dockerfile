FROM amazoncorretto:11-alpine-jdk
MAINTAINER github.com/wenqiglantz
EXPOSE 9000
COPY mssql.jks /tmp/mssql.jks
COPY target/theater-service-rest-controller-0.0.1-SNAPSHOT-exec.jar theater-service-rest-controller-0.0.1-SNAPSHOT-exec.jar
ENTRYPOINT ["java","-jar","/theater-service-rest-controller-0.0.1-SNAPSHOT-exec.jar", "-p", "9000"]
