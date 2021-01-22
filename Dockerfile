FROM adoptopenjdk:11-jre-hotspot

COPY build/libs/*.jar opt/app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/application.jar"]
