FROM openjdk:11-jre-slim
# no use latest
COPY target/awesome-product-service-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]
