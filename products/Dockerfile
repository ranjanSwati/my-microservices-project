# Eclipse Temurin (Java 21) use karein - Lightweight (Alpine) version
FROM eclipse-temurin:21-jdk-alpine

# 2. JAR file copy karein
COPY target/*.jar app.jar

# 3. App run karein
ENTRYPOINT ["java", "-jar", "app.jar"]
