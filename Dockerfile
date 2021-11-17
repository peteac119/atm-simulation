FROM maven:3.8.3-jdk-8 as build
MAINTAINER Pichan Vasantakitkumjorn

COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean package
CMD ["java", "-Xms128m", "-jar", "cash-dispensing-web.jar"]


FROM openjdk:8-jdk-slim as runner
RUN mkdir -p /app/cash-dispensing-web/
COPY --from=build /app/target/cash-dispensing-web.jar /app/cash-dispensing-web/cash-dispensing-web.jar
WORKDIR /app/cash-dispensing-web

CMD ["java", "-Xms128m", "-jar", "cash-dispensing-web.jar"]