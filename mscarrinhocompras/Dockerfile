FROM maven:3.8.7-eclipse-temurin-19-alpine
WORKDIR /app
COPY . .
RUN mvn package -DskipTests
EXPOSE 8083
CMD ["java","-jar","target/app.jar"]
