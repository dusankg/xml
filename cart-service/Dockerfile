FROM maven:3.6.3-ibmjava-8-alpine AS mavenService
WORKDIR /usr/src/server
COPY pom.xml /usr/src/server
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]
COPY . /usr/src/server
RUN ["mvn", "package", "-DskipTests"]

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=mavenService /usr/src/server/target/cart-service.jar ./
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "cart-service.jar"]
