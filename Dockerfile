FROM adoptopenjdk/openjdk11:jre-11.0.14.1_1
ADD ./target/*.jar app.jar
ENTRYPOINT ["java"]
CMD ["-jar", "/app.jar"]
