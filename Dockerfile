FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY target/springboot-demo*.jar springboot-demo.jar
CMD java ${JAVA_OPTS} -jar springboot-demo.jar