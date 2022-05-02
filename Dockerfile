FROM openjdk:11
COPY . /usr/src/technical-test-frontend
WORKDIR /usr/src/technical-test-frontend
RUN ./gradlew bootJar
EXPOSE 8080
CMD ["java","-jar", "build/libs/technical-test-frontend-0.0.1-SNAPSHOT.jar"]