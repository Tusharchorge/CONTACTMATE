FROM openjdk:21

WORKDIR /app

COPY dist/ContactMate-0.0.1-SNAPSHOT.jar /app/ContactMate-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ContactMate-0.0.1-SNAPSHOT.jar"]
