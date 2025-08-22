FROM amazoncorretto:17

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test


ENV PROJECT_NAME=discodeit \
    PROJECT_VERSION=1.2-M8 \
    JVM_OPTS=""

CMD ["sh", "-c", "java $JVM_OPTS -jar build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar"]

EXPOSE 80