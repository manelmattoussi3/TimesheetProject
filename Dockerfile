FROM openjdk:8
ADD target/Timesheet-spring-boot-core-data-jpa-mvc-REST-1-0.0.1.war timesheet.war
EXPOSE 8082
ENTRYPOINT ["java","-jar","timesheet.war"]