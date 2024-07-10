FROM maven:3.8.7-eclipse-temurin-19-alpine
WORKDIR /app
COPY . .
RUN mvn package -DskipTests
EXPOSE 8099
CMD ["java","-jar","target/mslogin-0.0.1-SNAPSHOT.jar"]
