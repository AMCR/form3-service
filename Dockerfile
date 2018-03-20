FROM anapsix/alpine-java

ARG APP_NAME
ARG APP_VERSION

COPY service/target/${APP_NAME}-${APP_VERSION}.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=${ENVIRONMENT} -jar /app.jar"]
