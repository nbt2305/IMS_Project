FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY . /app
#RUN ls -l target
EXPOSE 8088
ENTRYPOINT ["java","-jar","/app/target/G2_SE1630_SWD392-0.0.1-SNAPSHOT.jar"]
#

#FROM maven:3.8.4-openjdk-17 AS builder
#WORKDIR /app
#COPY target/G2_SE1630_SWD392-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/G2_SE1630_SWD392-0.0.1-SNAPSHOT.jar"]

