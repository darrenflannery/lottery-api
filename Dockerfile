FROM openjdk:11
EXPOSE 8080
ADD target/lottery.jar lottery.jar
ENTRYPOINT ["java","-jar","/lottery.jar"]