FROM openjdk:11.0.14.1-oracle
ARG  JAR_FILE=target/*.jar
COPY $JAR_FILE discord.jar
EXPOSE 8085
ENTRYPOINT ["java", "-Dmaven.test.skip=true", "-jar","/discord.jar"]