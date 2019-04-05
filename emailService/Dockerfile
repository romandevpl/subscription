FROM java:8-jre
EXPOSE 8090 587/tcp
ADD /target/emailService-1.0.jar emailService-1.0.jar
ENTRYPOINT ["java", "-jar", "emailService-1.0.jar"]